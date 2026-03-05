package com.ntt.clients.application.port.in;

import com.ntt.clients.domain.model.Client;
import com.ntt.clients.domain.model.StatusAccountReceive;

import java.time.LocalDate;
import java.util.List;

public interface ClientCreateUseCase {
    void save(Client client);
    void update(Client client);
    void delete(String idNumber);
}
