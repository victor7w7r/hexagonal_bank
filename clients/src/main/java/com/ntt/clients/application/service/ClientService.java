package com.ntt.clients.application.service;

import com.ntt.clients.application.port.in.ClientCreateUseCase;
import com.ntt.clients.application.port.in.ClientDeleteUseCase;
import com.ntt.clients.application.port.in.ClientSearchUseCase;
import com.ntt.clients.application.port.in.ClientUpdateUseCase;
import com.ntt.clients.application.port.out.ClientRepositoryPort;
import com.ntt.clients.application.port.out.ClientStatusAccountPort;
import com.ntt.clients.domain.exception.EntityNotFoundException;
import com.ntt.clients.domain.model.Client;
import com.ntt.clients.domain.model.StatusAccountReceive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService implements ClientSearchUseCase, ClientCreateUseCase, ClientUpdateUseCase, ClientDeleteUseCase {

  private final ClientRepositoryPort clientPort;
  private final ClientStatusAccountPort clientStatusAccountPort;

  @Override
  public List<Client> findAll() {
    return clientPort.findAll();
  }

  @Override
  public Long sendClientRef(String idNumber) {
    final var client = clientPort
            .findByIdNumber(idNumber)
            .orElseThrow(() ->
                    new EntityNotFoundException("ERROR: Cliente no encontrado")
            );
    return client.getId();
  }

  @Override
  public List<StatusAccountReceive> requestStatusAccount(
          LocalDate startDate,
          LocalDate endDate,
          String idNumber
  ) {
    final var clientFound = clientPort
            .findByIdNumber(idNumber)
            .orElseThrow(() ->
                    new EntityNotFoundException("ERROR: Cliente no encontrado")
            );

    return clientStatusAccountPort.requestStatusAccount(
            startDate,
            endDate,
            idNumber,
            clientFound
    );
  }

  @Override
  public void save(Client client) {
    final var clientFound = clientPort.findByIdNumber(
            client.getIdNumber()
    );
    if (clientFound.isPresent()) {
      throw new EntityNotFoundException("ERROR: Cliente ya existe");
    }
    client.setStatus(true);
    clientPort.save(client);
  }

  @Override
  public void update(Client client) {
    clientPort
            .findByIdNumber(client.getIdNumber())
            .orElseThrow(() ->
                    new EntityNotFoundException("ERROR: Cliente no encontrado")
            );
    clientPort.update(client);
  }

  @Override
  public void delete(String idNumber) {
    clientPort
            .findByIdNumber(idNumber)
            .orElseThrow(() ->
                    new EntityNotFoundException("ERROR: Cliente no encontrado")
            );
    clientPort.delete(idNumber);
  }
}
