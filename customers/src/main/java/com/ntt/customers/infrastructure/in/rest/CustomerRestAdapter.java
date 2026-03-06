package com.ntt.customers.infrastructure.in.rest;

import com.ntt.customers.application.port.in.CustomerCreateUseCase;
import com.ntt.customers.application.port.in.CustomerDeleteUseCase;
import com.ntt.customers.application.port.in.CustomerSearchUseCase;
import com.ntt.customers.application.port.in.CustomerUpdateUseCase;
import com.ntt.customers.infrastructure.in.rest.mapper.CustomerRestMapper;
import com.ntt.customers.infrastructure.in.rest.model.CustomerOperationResponse;
import com.ntt.customers.infrastructure.in.rest.model.CustomerRequest;
import com.ntt.customers.infrastructure.in.rest.model.CustomerResponse;
import com.ntt.customers.infrastructure.in.rest.model.StatusAccountReceiveRes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
@Slf4j
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerRestAdapter {

  private final CustomerSearchUseCase customerSearchUseCase;
  private final CustomerCreateUseCase customerCreateUseCase;
  private final CustomerUpdateUseCase customerUpdateUseCase;
  private final CustomerDeleteUseCase customerDeleteUseCase;
  private final CustomerRestMapper customerRestMapper;

  @GetMapping
  public List<CustomerResponse> getAllClients() {
    log.info("Querying all clients");
    return customerRestMapper.toClientResponseList(
            customerSearchUseCase.findAll()
    );
  }

  @GetMapping("reports")
  public List<StatusAccountReceiveRes> getAccountStatus(
          @RequestParam LocalDate startDate,
          @RequestParam LocalDate endDate,
          @RequestParam String idNumber
  ) {
    log.info("Querying status account for client {}", idNumber);
    return customerRestMapper.toStatusAccountReceiveResList(
            customerSearchUseCase.requestStatusAccount(
                    startDate,
                    endDate,
                    idNumber
            )
    );
  }

  @PostMapping
  public ResponseEntity<CustomerOperationResponse> saveClient(
          @Valid @RequestBody CustomerRequest request
  ) {
    log.info("Creating client {}", request.getName());
    customerCreateUseCase.save(customerRestMapper.toClient(request));
    return ResponseEntity.status(201).body(
            CustomerOperationResponse.builder().status("status").message("Cliente guardado exitosamente").build()
    );
  }

  @PutMapping
  public ResponseEntity<CustomerOperationResponse> updateClient(
          @Valid @RequestBody CustomerRequest request
  ) {
    log.info("Updating client {}", request.getIdNumber());
    customerUpdateUseCase.update(customerRestMapper.toClient(request));
    return ResponseEntity.status(202).body(
            CustomerOperationResponse.builder().status("status").message("Cliente actualizado exitosamente").build()
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<CustomerOperationResponse> deleteClient(@PathVariable String id) {
    log.info("Deleting client {}", id);
    customerDeleteUseCase.delete(id);
    return ResponseEntity.status(202).body(
            CustomerOperationResponse.builder().status("status").message("Cliente eliminado exitosamente").build()
    );
  }
}
