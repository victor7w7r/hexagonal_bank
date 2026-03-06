package com.ntt.customers.application.port.out;

import com.ntt.customers.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {
  List<Customer> findAll();

  Optional<Customer> findByIdNumber(String idNumber);

  void save(Customer customer);

  void update(Customer customer);

  void delete(String idNumber);
}
