package com.ntt.clients.application.port.in;

import com.ntt.clients.domain.model.Client;
import com.ntt.clients.domain.model.StatusAccountReceive;
import java.time.LocalDate;
import java.util.List;

public interface ClientSearchUseCase {
    List<StatusAccountReceive> requestStatusAccount(
        LocalDate startDate,
        LocalDate endDate,
        String idNumber
    );
    Long sendClientRef(String idNumber);
    List<Client> findAll();
}
