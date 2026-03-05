package com.ntt.clients.application.port.out;

import com.ntt.clients.domain.model.Client;
import com.ntt.clients.domain.model.StatusAccountReceive;

import java.time.LocalDate;
import java.util.List;

public interface ClientStatusAccountPort {
  List<StatusAccountReceive> requestStatusAccount(
          LocalDate startDate,
          LocalDate endDate,
          String idNumber,
          Client client
  );
}
