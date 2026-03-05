package com.ntt.clients.application.port.out;

import com.ntt.clients.domain.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepositoryPort {
  List<Client> findAll();

  Optional<Client> findByIdNumber(String idNumber);

  void save(Client client);

  void update(Client client);

  void delete(String idNumber);
}
