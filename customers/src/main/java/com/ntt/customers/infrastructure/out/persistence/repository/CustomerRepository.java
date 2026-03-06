package com.ntt.customers.infrastructure.out.persistence.repository;

import com.ntt.customers.infrastructure.out.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
  Optional<CustomerEntity> findByIdNumber(String idNumber);

  void deleteByIdNumber(String idNumber);
}
