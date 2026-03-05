package com.ntt.transactions.account.application.port.out;

import com.ntt.transactions.account.domain.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepositoryPort {
  List<Account> findAll();

  List<Account> findAllByClientRef(Long clientRef);

  Optional<Account> findByAccountNum(Long accountNum);

  void save(Account account, Long clientRef);

  void saveOnly(Account account);

  void update(Account account);

  void deleteByNumAccount(Long numAccount);

  void deleteByClientRef(Long clientRef);
}
