package com.ntt.customers.infrastructure.out.persistence;

import com.ntt.customers.application.port.out.CustomerRepositoryPort;
import com.ntt.customers.domain.model.Customer;
import com.ntt.customers.infrastructure.out.persistence.mapper.CustomerPersistenceMapper;
import com.ntt.customers.infrastructure.out.persistence.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryPersistenceAdapter implements CustomerRepositoryPort {

  private final CustomerRepository customerRepository;
  private final CustomerPersistenceMapper customerPersistenceMapper;

  @Override
  public List<Customer> findAll() {
    return customerPersistenceMapper.toClientList(
            customerRepository.findAll()
    );
  }

  @Override
  public Optional<Customer> findByIdNumber(String idNumber) {
    return customerRepository
            .findByIdNumber(idNumber)
            .map(customerPersistenceMapper::toClient);
  }

  @Override
  public void save(Customer customer) {
    customerRepository.save(
            customerPersistenceMapper.toClientEntity(customer)
    );
  }

  @Override
  @Transactional
  public void update(Customer customer) {
    final var clientFound = customerRepository.findByIdNumber(
            customer.getIdNumber()
    );

    if (clientFound.isPresent()) {
      final var clientEntity = clientFound.get();
      customerPersistenceMapper.update(customer, clientEntity);
      customerRepository.save(clientEntity);
    }
  }

  @Override
  @Transactional
  public void delete(String idNumber) {
    customerRepository.deleteByIdNumber(idNumber);
  }
}
