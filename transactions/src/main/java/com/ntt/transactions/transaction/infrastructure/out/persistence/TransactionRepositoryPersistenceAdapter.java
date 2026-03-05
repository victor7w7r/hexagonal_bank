package com.ntt.transactions.transaction.infrastructure.out.persistence;

import com.ntt.transactions.transaction.application.port.out.TransactionRepositoryPort;
import com.ntt.transactions.transaction.domain.model.Transaction;
import com.ntt.transactions.transaction.infrastructure.out.persistence.mapper.TransactionPersistenceMapper;
import com.ntt.transactions.transaction.infrastructure.out.persistence.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionRepositoryPersistenceAdapter implements TransactionRepositoryPort {

    private final TransactionRepository transactionRepository;
    private final TransactionPersistenceMapper transactionPersistenceMapper;

    @Override
    public List<Transaction> findAll() {
        return transactionPersistenceMapper.toTransactionList(
            transactionRepository.findAll()
        );
    }

    @Override
    public Optional<Transaction> findByUuid(String uuid) {
        return transactionRepository
            .findByUuid(uuid)
            .map(transactionPersistenceMapper::toTransaction);
    }

    @Override
    @Transactional
    public void save(Transaction transaction) {
        transactionRepository.save(
            transactionPersistenceMapper.toTransactionEntity(transaction)
        );
    }

    @Override
    @Transactional
    public void update(Transaction transaction) {
        final var transactionFound = transactionRepository.findByUuid(
            transaction.getUuid()
        );

        if (transactionFound.isPresent()) {
            final var transactionEntity = transactionFound.get();
            transactionPersistenceMapper.update(transaction, transactionEntity);
            transactionRepository.save(transactionEntity);
        }
    }

    @Override
    @Transactional
    public void delete(String uuid) {
        transactionRepository.deleteByUuid(uuid);
    }
}
