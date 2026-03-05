package com.ntt.transactions.account.application.port.in;

public interface AccountDeleteUseCase {
  void delete(Long numAccount);
  Long deleteByClientRef(Long clientRef);
}
