package com.ntt.transactions.account.application.port.in;

import com.ntt.transactions.account.domain.model.Account;
import com.ntt.transactions.account.domain.model.StatusAccountReceive;
import com.ntt.transactions.account.domain.model.StatusAccountSend;

import java.util.List;

public interface AccountSearchUseCase {
  List<Account> findAll();
  List<StatusAccountReceive> requestStatusAccount(StatusAccountSend req);
}
