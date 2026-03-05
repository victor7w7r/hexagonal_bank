package com.ntt.transactions.account.infrastructure.out.persistence.repository;

import com.ntt.transactions.account.infrastructure.out.persistence.entity.AccountEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByNumAccount(Long numAccount);
    List<AccountEntity> findAllByClientRef(Long clientRef);
    void deleteByClientRef(Long clientRef);
    void deleteByNumAccount(Long numAccount);
}
