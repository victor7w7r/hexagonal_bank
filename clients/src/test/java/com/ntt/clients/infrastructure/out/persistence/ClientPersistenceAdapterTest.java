package com.ntt.clients.infrastructure.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ntt.clients.domain.model.Client;
import com.ntt.clients.infrastructure.out.persistence.entity.ClientEntity;
import com.ntt.clients.infrastructure.out.persistence.mapper.ClientPersistenceMapper;
import com.ntt.clients.infrastructure.out.persistence.repository.ClientRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientPersistenceAdapterTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientPersistenceMapper clientPersistenceMapper;

    @InjectMocks
    private ClientRepositoryPersistenceAdapter clientPersistenceAdapter;

    private final Client client = Client.builder()
        .id(1L)
        .name("Victor")
        .gender("Masculino")
        .age(20)
        .idNumber("1725082786")
        .address("Call Segovia y Raices")
        .phone("0984565509")
        .password("victorContrasena")
        .status(true)
        .build();

    private final ClientEntity clientEntity = ClientEntity.builder()
        .name("Victor")
        .gender("Masculino")
        .age(20)
        .idNumber("1725082786")
        .address("Call Segovia y Raices")
        .phone("0984565509")
        .password("victorContrasena")
        .status(true)
        .build();

    @Test
    void findAll_shouldReturnClientList() {
        var entityList = List.of(clientEntity);
        var clientList = List.of(client);
        when(clientRepository.findAll()).thenReturn(entityList);
        when(clientPersistenceMapper.toClientList(entityList)).thenReturn(
            clientList
        );
        var result = clientPersistenceAdapter.findAll();
        assertEquals(clientList, result);
    }

    @Test
    void findByIdNumber_shouldReturnClient() {
        when(clientRepository.findByIdNumber("1725082786")).thenReturn(
            Optional.of(clientEntity)
        );
        when(clientPersistenceMapper.toClient(clientEntity)).thenReturn(
            client
        );
        var result = clientPersistenceAdapter.findByIdNumber(
            "1725082786"
        );
        assertTrue(result.isPresent());
        assertEquals(client, result.get());
    }

    @Test
    void findByIdNumber_shouldReturnEmpty() {
        when(clientRepository.findByIdNumber("1725082786")).thenReturn(
            Optional.empty()
        );
        var result = clientPersistenceAdapter.findByIdNumber(
            "1725082786"
        );
        assertTrue(result.isEmpty());
    }

    @Test
    void save_shouldCallRepositorySave() {
        var entity = new Object();
        when(clientPersistenceMapper.toClientEntity(client)).thenReturn(
                clientEntity
        );
        clientPersistenceAdapter.save(client);
        verify(clientRepository).save(clientEntity);
    }

    @Test
    void update_shouldUpdateAndSaveIfFound() {
        when(clientRepository.findByIdNumber("1725082786")).thenReturn(
            Optional.of(clientEntity)
        );
        doNothing()
            .when(clientPersistenceMapper)
            .update(client, clientEntity);
        clientPersistenceAdapter.update(client);
        verify(clientPersistenceMapper).update(client, clientEntity);
        verify(clientRepository).save(clientEntity);
    }

    @Test
    void update_shouldDoNothingIfNotFound() {
        when(clientRepository.findByIdNumber("1725082786")).thenReturn(
            Optional.empty()
        );
        clientPersistenceAdapter.update(client);
        verify(clientPersistenceMapper, never()).update(any(), any());
        verify(clientRepository, never()).save(any());
    }

    @Test
    void delete_shouldCallRepositoryDelete() {
        clientPersistenceAdapter.delete("123");
        verify(clientRepository).deleteByIdNumber("123");
    }
}
