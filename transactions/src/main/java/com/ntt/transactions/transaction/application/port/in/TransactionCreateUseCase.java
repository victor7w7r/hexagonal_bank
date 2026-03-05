package com.ntt.transactions.transaction.application.port.in;

import com.ntt.transactions.transaction.domain.model.Transaction;

public interface TransactionCreateUseCase {
  void save(Transaction transaction, Long numAccount);
}
