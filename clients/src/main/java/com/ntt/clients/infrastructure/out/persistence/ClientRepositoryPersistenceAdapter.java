package com.ntt.clients.infrastructure.out.persistence;

import com.ntt.clients.application.port.out.ClientRepositoryPort;
import com.ntt.clients.domain.model.Client;
import com.ntt.clients.infrastructure.out.persistence.mapper.ClientPersistenceMapper;
import com.ntt.clients.infrastructure.out.persistence.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientRepositoryPersistenceAdapter implements ClientRepositoryPort {

  private final ClientRepository clientRepository;
  private final ClientPersistenceMapper clientPersistenceMapper;

  @Override
  public List<Client> findAll() {
    return clientPersistenceMapper.toClientList(
            clientRepository.findAll()
    );
  }

  @Override
  public Optional<Client> findByIdNumber(String idNumber) {
    return clientRepository
            .findByIdNumber(idNumber)
            .map(clientPersistenceMapper::toClient);
  }

  @Override
  public void save(Client client) {
    clientRepository.save(
            clientPersistenceMapper.toClientEntity(client)
    );
  }

  @Override
  @Transactional
  public void update(Client client) {
    final var clientFound = clientRepository.findByIdNumber(
            client.getIdNumber()
    );

    if (clientFound.isPresent()) {
      final var clientEntity = clientFound.get();
      clientPersistenceMapper.update(client, clientEntity);
      clientRepository.save(clientEntity);
    }
  }

  @Override
  @Transactional
  public void delete(String idNumber) {
    clientRepository.deleteByIdNumber(idNumber);
  }
}
