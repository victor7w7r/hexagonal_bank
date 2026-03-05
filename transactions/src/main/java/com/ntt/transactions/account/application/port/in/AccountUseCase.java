package com.ntt.transactions.account.application.port.in;

import com.ntt.transactions.account.domain.model.Account;
import com.ntt.transactions.account.domain.model.StatusAccountReceive;
import com.ntt.transactions.account.domain.model.StatusAccountSend;
import java.util.List;

public interface AccountUseCase {
    List<Account> findAll();
    void save(Account client, String idNumber);
    void update(Account account);
    void delete(Long numAccount);
    Long deleteByClientRef(Long clientRef);
    List<StatusAccountReceive> requestStatusAccount(StatusAccountSend req);
}
