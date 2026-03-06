package com.ntt.customers.infrastructure.out.messaging;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntt.customers.application.port.out.CustomerStatusAccountPort;
import com.ntt.customers.domain.model.Customer;
import com.ntt.customers.domain.model.StatusAccountReceive;
import com.ntt.customers.infrastructure.out.messaging.mapper.CustomerMessagingOutputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerMessagingOutputAdapter implements CustomerStatusAccountPort {

  private final RabbitTemplate rabbitTemplate;
  private final CustomerMessagingOutputMapper customerMessagingOutputMapper;

  @Override
  public List<StatusAccountReceive> requestStatusAccount(
          LocalDate startDate,
          LocalDate endDate,
          String idNumber,
          Customer customer
  ) {
    rabbitTemplate.setReplyTimeout(8000);

    final var response = rabbitTemplate.convertSendAndReceive(
            "bank-ntt",
            "account_client_queue",
            customerMessagingOutputMapper.toStatusAccountSendReq(
                    startDate,
                    endDate,
                    customer.getId(),
                    customer.getName()
            )
    );

    return new ObjectMapper().convertValue(
            response,
            new TypeReference<>() {
            }
    );
  }
}
