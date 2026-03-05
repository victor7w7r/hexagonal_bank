package com.ntt.clients.infrastructure.out.persistence.repository;

import com.ntt.clients.infrastructure.out.persistence.entity.ClientEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByIdNumber(String idNumber);
    void deleteByIdNumber(String idNumber);
}
