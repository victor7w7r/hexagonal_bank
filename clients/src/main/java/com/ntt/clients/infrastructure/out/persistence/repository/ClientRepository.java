package com.ntt.clients.infrastructure.out.persistence.repository;

import com.ntt.clients.infrastructure.out.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
  Optional<ClientEntity> findByIdNumber(String idNumber);

  void deleteByIdNumber(String idNumber);
}
