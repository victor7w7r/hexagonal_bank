package com.ntt.clients.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ntt.clients.application.port.out.ClientRepositoryPort;
import com.ntt.clients.application.port.out.ClientStatusAccountPort;
import com.ntt.clients.domain.exception.EntityNotFoundException;
import com.ntt.clients.domain.model.Client;
import com.ntt.clients.domain.model.StatusAccountReceive;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @Mock
    private ClientStatusAccountPort clientStatusAccountPort;

    @InjectMocks
    private ClientSearchService clientService;

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

    @Test
    void findAll_shouldReturnClients() {
        List<Client> clients = List.of(client);
        when(clientRepositoryPort.findAll()).thenReturn(clients);
        assertEquals(clients, clientService.findAll());
    }

    @Test
    void envioClientRef_shouldReturnId() {
        when(clientRepositoryPort.findByIdNumber("1725082786")).thenReturn(
            Optional.of(client)
        );
        assertEquals(1L, clientService.sendClientRef("1725082786"));
    }

    @Test
    void envioClientRef_shouldThrowIfNotFound() {
        when(clientRepositoryPort.findByIdNumber("1725082786")).thenReturn(
            Optional.empty()
        );
        assertThrows(EntityNotFoundException.class, () ->
            clientService.sendClientRef("1725082786")
        );
    }

    @Test
    void requestStatusAccount_shouldReturnList() {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now();
        List<StatusAccountReceive> status = List.of(
            mock(StatusAccountReceive.class)
        );
        when(clientRepositoryPort.findByIdNumber("1725082786")).thenReturn(
            Optional.of(client)
        );
        when(
            clientStatusAccountPort.requestStatusAccount(
                start,
                end,
                "1725082786",
                client
            )
        ).thenReturn(status);
        assertEquals(
            status,
            clientService.requestStatusAccount(start, end, "1725082786")
        );
    }

    @Test
    void requestStatusAccount_shouldThrowIfNotFound() {
        when(clientRepositoryPort.findByIdNumber("1725082786")).thenReturn(
            Optional.empty()
        );
        assertThrows(EntityNotFoundException.class, () ->
            clientService.requestStatusAccount(
                LocalDate.now(),
                LocalDate.now(),
                "1725082786"
            )
        );
    }

    @Test
    void save_shouldSaveIfNotExists() {
        when(clientRepositoryPort.findByIdNumber("1725082786")).thenReturn(
            Optional.empty()
        );
        clientService.save(client);
        assertTrue(client.getStatus());
        verify(clientRepositoryPort).save(client);
    }

    @Test
    void save_shouldThrowIfExists() {
        when(clientRepositoryPort.findByIdNumber("1725082786")).thenReturn(
            Optional.of(client)
        );
        assertThrows(EntityNotFoundException.class, () ->
            clientService.save(client)
        );
    }

    @Test
    void update_shouldUpdateIfExists() {
        when(clientRepositoryPort.findByIdNumber("1725082786")).thenReturn(
            Optional.of(client)
        );
        clientService.update(client);
        verify(clientRepositoryPort).update(client);
    }

    @Test
    void update_shouldThrowIfNotFound() {
        when(clientRepositoryPort.findByIdNumber("1725082786")).thenReturn(
            Optional.empty()
        );
        assertThrows(EntityNotFoundException.class, () ->
            clientService.update(client)
        );
    }

    @Test
    void delete_shouldDeleteIfExists() {
        when(clientRepositoryPort.findByIdNumber("1725082786")).thenReturn(
            Optional.of(client)
        );
        clientService.delete("1725082786");
        verify(clientRepositoryPort).delete("1725082786");
    }

    @Test
    void delete_shouldThrowIfNotFound() {
        when(clientRepositoryPort.findByIdNumber("1725082786")).thenReturn(
            Optional.empty()
        );
        assertThrows(EntityNotFoundException.class, () ->
            clientService.delete("1725082786")
        );
    }
}
