package com.ntt.transactions.transaction.infrastructure.out.persistence.repository;

import com.ntt.transactions.transaction.infrastructure.out.persistence.entity.TransactionEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository
    extends JpaRepository<TransactionEntity, Long>
{
    Optional<TransactionEntity> findByUuid(String uuid);
    void deleteByUuid(String uuid);
}
