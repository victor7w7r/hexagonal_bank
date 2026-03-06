package com.ntt.customers.application.service;

import com.ntt.customers.application.port.in.CustomerCreateUseCase;
import com.ntt.customers.application.port.in.CustomerDeleteUseCase;
import com.ntt.customers.application.port.in.CustomerSearchUseCase;
import com.ntt.customers.application.port.in.CustomerUpdateUseCase;
import com.ntt.customers.application.port.out.CustomerRepositoryPort;
import com.ntt.customers.application.port.out.CustomerStatusAccountPort;
import com.ntt.customers.domain.exception.EntityNotFoundException;
import com.ntt.customers.domain.model.Customer;
import com.ntt.customers.domain.model.StatusAccountReceive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerSearchUseCase, CustomerCreateUseCase, CustomerUpdateUseCase, CustomerDeleteUseCase {

  private final CustomerRepositoryPort clientPort;
  private final CustomerStatusAccountPort customerStatusAccountPort;

  @Override
  public List<Customer> findAll() {
    return clientPort.findAll();
  }

  @Override
  public Long sendCustomerRef(String idNumber) {
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

    return customerStatusAccountPort.requestStatusAccount(
            startDate,
            endDate,
            idNumber,
            clientFound
    );
  }

  @Override
  public void save(Customer customer) {
    final var clientFound = clientPort.findByIdNumber(
            customer.getIdNumber()
    );
    if (clientFound.isPresent()) {
      throw new EntityNotFoundException("ERROR: Cliente ya existe");
    }
    customer.setStatus(true);
    clientPort.save(customer);
  }

  @Override
  public void update(Customer customer) {
    clientPort
            .findByIdNumber(customer.getIdNumber())
            .orElseThrow(() ->
                    new EntityNotFoundException("ERROR: Cliente no encontrado")
            );
    clientPort.update(customer);
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
