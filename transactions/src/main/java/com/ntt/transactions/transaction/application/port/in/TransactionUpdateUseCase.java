package com.ntt.transactions.transaction.application.port.in;

import com.ntt.transactions.transaction.domain.model.Transaction;

public interface TransactionUpdateUseCase {
  void update(Transaction transaction);
}
