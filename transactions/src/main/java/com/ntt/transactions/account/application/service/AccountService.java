package com.ntt.transactions.account.application.service;

import com.ntt.transactions.common.exception.EntityNotFoundException;
import com.ntt.transactions.account.application.port.in.AccountUseCase;
import com.ntt.transactions.account.application.port.out.AccountMessagingPort;
import com.ntt.transactions.account.application.port.out.AccountRepositoryPort;
import com.ntt.transactions.account.domain.model.Account;
import com.ntt.transactions.account.domain.model.StatusAccountReceive;
import com.ntt.transactions.account.domain.model.StatusAccountSend;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountUseCase {

    private final AccountRepositoryPort accountRepositoryPort;
    private final AccountMessagingPort accountMessagingPort;

    @Override
    public List<Account> findAll() {
        return accountRepositoryPort.findAll();
    }

    @Override
    public void update(Account account) {
        accountRepositoryPort
            .findByAccountNum(account.getNumAccount())
            .orElseThrow(() ->
                new EntityNotFoundException("ERROR: Cuenta no encontrada")
            );
        accountRepositoryPort.update(account);
    }

    @Override
    public void delete(Long numAccount) {
        accountRepositoryPort
            .findByAccountNum(numAccount)
            .orElseThrow(() ->
                new EntityNotFoundException("ERROR: Cuenta no encontrada")
            );
        accountRepositoryPort.deleteByNumAccount(numAccount);
    }

    @Override
    public void save(Account client, String idNumber) {
        final var accountFound = accountRepositoryPort.findByAccountNum(
            client.getNumAccount()
        );

        if (accountFound.isPresent()) {
            throw new EntityNotFoundException("ERROR: Esta cuenta ya existe");
        }

        final var clientRef = accountMessagingPort.sendIdReceiveRef(
                idNumber
        );

        if (clientRef == null) {
            throw new EntityNotFoundException(
                "ERROR: Cliente con dicha idNumber no encontrado"
            );
        }

        if (client.getStatus() == null) {
            client.setStatus(true);
        }

        accountRepositoryPort.save(client, clientRef);
    }

    @Override
    public Long deleteByClientRef(Long clientRef) {
        if (!accountRepositoryPort.findAllByClientRef(clientRef).isEmpty()) {
            accountRepositoryPort.deleteByClientRef(clientRef);
        }
        return 0L;
    }

    @Override
    public List<StatusAccountReceive> requestStatusAccount(
        StatusAccountSend req
    ) {
        final var requestData = accountRepositoryPort.findAllByClientRef(
            req.getClientRef()
        );

        final var response = new ArrayList<StatusAccountReceive>();

        for (final var account : requestData) {
            for (final var transaction : account.getTransactionEntities()) {
                if (
                    (transaction.getDate().isAfter(req.getStartDate()) ||
                        transaction.getDate().isEqual(req.getStartDate())) &&
                    (transaction.getDate().isBefore(req.getEndDate()) ||
                        transaction.getDate().isEqual(req.getEndDate()))
                ) {
                    final var initialBalance = transaction.getBalance();
                    final var balanceDiff = initialBalance.add(
                        transaction.getValue()
                    );

                    var statusAccount = StatusAccountReceive.builder()
                        .date(
                                transaction
                                .getDate()
                                .format(
                                    java.time.format.DateTimeFormatter.ofPattern(
                                        "dd/MM/yyyy"
                                    )
                                )
                        )
                        .client(req.getNameClient())
                        .numAccount(account.getNumAccount())
                        .accountType(account.getAccountType())
                        .typeTransaction(transaction.getTypeTransaction())
                        .transactionQuantity(transaction.getValue())
                        .balance(transaction.getBalance())
                        .actualBalance(balanceDiff)
                        .build();
                    response.add(statusAccount);
                }
            }
        }
        return response;
    }
}
