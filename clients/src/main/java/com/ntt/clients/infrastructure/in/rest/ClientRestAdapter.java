package com.ntt.clients.infrastructure.in.rest;

import com.ntt.clients.application.port.in.ClientCreateUseCase;
import com.ntt.clients.application.port.in.ClientSearchUseCase;
import com.ntt.clients.infrastructure.in.rest.mapper.ClientRestMapper;
import com.ntt.clients.infrastructure.in.rest.model.ClientRequest;
import com.ntt.clients.infrastructure.in.rest.model.ClientResponse;
import com.ntt.clients.infrastructure.in.rest.model.ClientOperationResponse;
import com.ntt.clients.infrastructure.in.rest.model.StatusAccountReceiveRes;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientRestAdapter {

    private final ClientSearchUseCase clientSearchUseCase;
    private final ClientCreateUseCase clientCreateUseCase;
    private final ClientRestMapper clientRestMapper;
    private static final Logger log = LoggerFactory.getLogger(ClientRestAdapter.class);

    @GetMapping
    public List<ClientResponse> getAllClients() {
      log.info("Querying all clients");
        return clientRestMapper.toClientResponseList(
            clientSearchUseCase.findAll()
        );
    }

  @GetMapping("reports")
  public List<StatusAccountReceiveRes> getAccountStatus(
          @RequestParam LocalDate startDate,
          @RequestParam LocalDate endDate,
          @RequestParam String idNumber
  ) {
      log.info("Querying status account for client {}", idNumber);
    return clientRestMapper.toStatusAccountReceiveResList(
            clientSearchUseCase.requestStatusAccount(
                    startDate,
                    endDate,
                    idNumber
            )
    );
  }

  @PostMapping
  public ResponseEntity<ClientOperationResponse> saveClient(
          @Valid @RequestBody ClientRequest request
  ) {
    log.info("Creating client {}", request.getName());
    clientCreateUseCase.save(clientRestMapper.toClient(request));
    return ResponseEntity.status(201).body(
        ClientOperationResponse.builder().status("status").message("Cliente guardado exitosamente").build()
    );
  }

  @PutMapping
  public ResponseEntity<ClientOperationResponse> updateClient(
          @Valid @RequestBody ClientRequest request
  ) {
    log.info("Updating client {}", request.getIdNumber());
    clientCreateUseCase.update(clientRestMapper.toClient(request));
    return ResponseEntity.status(202).body(
            ClientOperationResponse.builder().status("status").message("Cliente actualizado exitosamente").build()
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<ClientOperationResponse> deleteClient(@PathVariable String id) {
    log.info("Deleting client {}", id);
    clientCreateUseCase.delete(id);
    return ResponseEntity.status(202).body(
        ClientOperationResponse.builder().status("status").message("Cliente eliminado exitosamente").build()
    );
  }
}
