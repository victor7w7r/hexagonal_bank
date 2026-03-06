package com.ntt.customers.application.port.out;

import com.ntt.customers.domain.model.Customer;
import com.ntt.customers.domain.model.StatusAccountReceive;

import java.time.LocalDate;
import java.util.List;

public interface CustomerStatusAccountPort {
  List<StatusAccountReceive> requestStatusAccount(
          LocalDate startDate,
          LocalDate endDate,
          String idNumber,
          Customer customer
  );
}
