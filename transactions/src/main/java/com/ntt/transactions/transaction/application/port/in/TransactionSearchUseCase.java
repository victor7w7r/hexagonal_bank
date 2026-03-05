package com.ntt.transactions.transaction.application.port.in;

import com.ntt.transactions.transaction.domain.model.Transaction;

import java.util.List;

public interface TransactionSearchUseCase {
  List<Transaction> findAll();
}
