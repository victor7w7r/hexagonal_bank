package com.ntt.transactions.transaction.infrastructure.out.persistence.repository;

import com.ntt.transactions.transaction.infrastructure.out.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository
        extends JpaRepository<TransactionEntity, Long> {
  Optional<TransactionEntity> findByUuid(String uuid);

  void deleteByUuid(String uuid);
}
