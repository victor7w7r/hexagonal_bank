package com.ntt.customers.application.port.in;

import com.ntt.customers.domain.model.Customer;

public interface CustomerUpdateUseCase {
  void update(Customer customer);

}
