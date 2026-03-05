package com.ntt.transactions.transaction.application.port.out;

import com.ntt.transactions.transaction.domain.model.Transaction;
import java.util.List;
import java.util.Optional;

public interface TransactionRepositoryPort {
    List<Transaction> findAll();
    Optional<Transaction> findByUuid(String uuid);
    void save(Transaction transaction);
    void update(Transaction transaction);
    void delete(String uuid);
}
