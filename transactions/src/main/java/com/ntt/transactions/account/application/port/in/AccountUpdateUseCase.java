package com.ntt.transactions.account.application.port.in;

import com.ntt.transactions.account.domain.model.Account;

public interface AccountUpdateUseCase {
  void update(Account account);
}
