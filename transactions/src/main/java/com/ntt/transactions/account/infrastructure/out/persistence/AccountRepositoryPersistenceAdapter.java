package com.ntt.transactions.account.infrastructure.out.persistence;

import com.ntt.transactions.account.application.port.out.AccountRepositoryPort;
import com.ntt.transactions.account.domain.model.Account;
import com.ntt.transactions.account.infrastructure.out.persistence.mapper.AccountPersistenceMapper;
import com.ntt.transactions.account.infrastructure.out.persistence.repository.AccountRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountRepositoryPersistenceAdapter implements AccountRepositoryPort {

    private final AccountRepository accountRepository;
    private final AccountPersistenceMapper accountPersistenceMapper;

    @Override
    @Transactional
    public List<Account> findAll() {
        return accountPersistenceMapper.toAccountList(accountRepository.findAll());
    }

    @Override
    public List<Account> findAllByClientRef(Long clientRef) {
        return accountPersistenceMapper.toAccountList(
            accountRepository.findAllByClientRef(clientRef)
        );
    }

    @Override
    public Optional<Account> findByAccountNum(Long accountNum) {
        return accountRepository
            .findByNumAccount(accountNum)
            .map(accountPersistenceMapper::toAccount);
    }

    @Override
    public void save(Account account, Long clientRef) {
        account.setAccountRef(clientRef);
        accountRepository.save(accountPersistenceMapper.toAccountEntity(account));
    }

    @Override
    public void saveOnly(Account account) {
        final var accountFound = accountRepository.findByNumAccount(
            account.getNumAccount()
        );

        if (accountFound.isPresent()) {
            final var accountEntity = accountFound.get();
            accountPersistenceMapper.updateWithoutNumAccount(
                    account,
                accountEntity
            );
            accountRepository.save(accountEntity);
        }
    }

    @Override
    @Transactional
    public void update(Account account) {
        final var accountFound = accountRepository.findByNumAccount(
            account.getNumAccount()
        );

        if (accountFound.isPresent()) {
            final var accountEntity = accountFound.get();
            accountPersistenceMapper.update(account, accountEntity);
            accountRepository.save(accountEntity);
        }
    }

    @Override
    @Transactional
    public void deleteByNumAccount(Long numAccount) {
        accountRepository.deleteByNumAccount(numAccount);
    }

    @Override
    @Transactional
    public void deleteByClientRef(Long clientRef) {
        accountRepository.deleteByClientRef(clientRef);
    }
}
