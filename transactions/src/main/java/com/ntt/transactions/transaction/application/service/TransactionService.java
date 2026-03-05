package com.ntt.transactions.transaction.application.service;

import com.ntt.transactions.account.application.port.out.AccountRepositoryPort;
import com.ntt.transactions.common.exception.EntityNotFoundException;
import com.ntt.transactions.common.exception.InsufficientFundsException;
import com.ntt.transactions.transaction.application.port.in.TransactionCreateUseCase;
import com.ntt.transactions.transaction.application.port.in.TransactionDeleteUseCase;
import com.ntt.transactions.transaction.application.port.in.TransactionSearchUseCase;
import com.ntt.transactions.transaction.application.port.in.TransactionUpdateUseCase;
import com.ntt.transactions.transaction.application.port.out.TransactionRepositoryPort;
import com.ntt.transactions.transaction.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionSearchUseCase, TransactionCreateUseCase, TransactionUpdateUseCase, TransactionDeleteUseCase {

  private final TransactionRepositoryPort transactionRepositoryPort;
  private final AccountRepositoryPort accountRepositoryPort;

  public List<Transaction> findAll() {
    return transactionRepositoryPort.findAll();
  }

  public void save(Transaction transaction, Long numAccount) {
    final var transactionFound = transactionRepositoryPort.findByUuid(
            transaction.getUuid()
    );
    if (transactionFound.isPresent()) {
      throw new EntityNotFoundException("ERROR: Esta transaccion ya existe");
    }

    final var accountFound = accountRepositoryPort
            .findByAccountNum(numAccount)
            .orElseThrow(() ->
                    new EntityNotFoundException(
                            "ERROR: Cuenta no encontrada con ese número de cuenta"
                    )
            );

    final var transactionFunds = transaction.getValue();
    final var availableQuantity = accountFound.getInitialFunds();
    final var balanceDiff = availableQuantity.add(transactionFunds);

    if (balanceDiff.compareTo(BigDecimal.ZERO) < 0) {
      throw new InsufficientFundsException("ERROR: Saldo insuficiente");
    }

    if (transaction.getUuid() == null) {
      transaction.setUuid(java.util.UUID.randomUUID().toString());
    }

    if (transaction.getDate() == null) {
      transaction.setDate(java.time.LocalDate.now());
    }

    if (transactionFunds.longValue() > 0) {
      transaction.setTypeTransaction("Deposito");
    } else {
      transaction.setTypeTransaction("Retiro");
    }

    accountFound.setInitialFunds(balanceDiff);
    accountRepositoryPort.saveOnly(accountFound);

    final var finalTransaction = Transaction.builder()
            .date(transaction.getDate())
            .typeTransaction(transaction.getTypeTransaction())
            .value(transactionFunds)
            .balance(balanceDiff)
            .uuid(transaction.getUuid())
            .accountId(accountFound.getId().toString())
            .build();

    transactionRepositoryPort.save(finalTransaction);
  }

  @Override
  public void update(Transaction transaction) {
    transactionRepositoryPort
            .findByUuid(transaction.getUuid())
            .orElseThrow(() ->
                    new EntityNotFoundException("ERROR: transaccion no encontrada")
            );
    transactionRepositoryPort.update(transaction);
  }

  @Override
  public void delete(String uuid) {
    transactionRepositoryPort
            .findByUuid(uuid)
            .orElseThrow(() ->
                    new EntityNotFoundException("ERROR: transaccion no encontrada")
            );
    transactionRepositoryPort.delete(uuid);
  }
}
