package com.ntt.transactions.account.infrastructure.config;

import com.ntt.transactions.account.application.port.in.AccountDeleteUseCase;
import com.ntt.transactions.account.application.port.in.AccountSearchUseCase;
import com.ntt.transactions.account.infrastructure.in.messaging.AccountMessagingInputAdapter;
import com.ntt.transactions.account.infrastructure.in.messaging.mapper.AccountMessagingInputMapper;
import com.ntt.transactions.account.infrastructure.out.persistence.AccountRepositoryPersistenceAdapter;
import com.ntt.transactions.account.infrastructure.out.persistence.mapper.AccountPersistenceMapper;
import com.ntt.transactions.account.infrastructure.out.persistence.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

  @Bean
  public AccountRepositoryPersistenceAdapter accountPersistenceAdapter(
          AccountRepository accountRepository,
          AccountPersistenceMapper accountPersistenceMapper
  ) {
    return new AccountRepositoryPersistenceAdapter(
            accountRepository,
            accountPersistenceMapper
    );
  }

  @Bean
  public AccountMessagingInputAdapter accountMessagingInputAdapter(
          AccountSearchUseCase accountSearchUseCase,
          AccountDeleteUseCase accountDeleteUseCase,
          AccountMessagingInputMapper accountMessagingInputMapper
  ) {
    return new AccountMessagingInputAdapter(
            accountDeleteUseCase,
            accountSearchUseCase,
            accountMessagingInputMapper
    );
  }
}
