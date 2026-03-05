package com.ntt.transactions.transaction.application.port.in;

import com.ntt.transactions.transaction.domain.model.Transaction;
import java.util.List;

public interface TransactionUseCase {
    List<Transaction> findAll();
    void save(Transaction transaction, Long numAccount);
    void update(Transaction transaction);
    void delete(String uuid);
}
