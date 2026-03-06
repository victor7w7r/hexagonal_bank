package com.ntt.customers.application.port.in;

import com.ntt.customers.domain.model.Customer;
import com.ntt.customers.domain.model.StatusAccountReceive;

import java.time.LocalDate;
import java.util.List;

public interface CustomerSearchUseCase {
  List<StatusAccountReceive> requestStatusAccount(
          LocalDate startDate,
          LocalDate endDate,
          String idNumber
  );

  Long sendCustomerRef(String idNumber);

  List<Customer> findAll();
}
