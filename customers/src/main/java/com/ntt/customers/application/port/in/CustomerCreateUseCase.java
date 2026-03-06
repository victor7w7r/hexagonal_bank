package com.ntt.customers.application.port.in;

import com.ntt.customers.domain.model.Customer;

public interface CustomerCreateUseCase {
  void save(Customer customer);
}
