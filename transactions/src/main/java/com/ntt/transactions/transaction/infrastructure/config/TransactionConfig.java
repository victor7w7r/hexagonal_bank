package com.ntt.transactions.transaction.infrastructure.config;

import com.ntt.transactions.transaction.infrastructure.out.persistence.TransactionRepositoryPersistenceAdapter;
import com.ntt.transactions.transaction.infrastructure.out.persistence.mapper.TransactionPersistenceMapper;
import com.ntt.transactions.transaction.infrastructure.out.persistence.repository.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfig {

  @Bean
  public TransactionRepositoryPersistenceAdapter transactionPersistenceAdapter(
          TransactionRepository transactionRepository,
          TransactionPersistenceMapper transactionPersistenceMapper
  ) {
    return new TransactionRepositoryPersistenceAdapter(
            transactionRepository,
            transactionPersistenceMapper
    );
  }
}
