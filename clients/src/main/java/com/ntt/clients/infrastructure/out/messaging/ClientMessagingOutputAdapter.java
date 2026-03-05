package com.ntt.clients.infrastructure.out.messaging;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntt.clients.application.port.out.ClientStatusAccountPort;
import com.ntt.clients.domain.model.Client;
import com.ntt.clients.domain.model.StatusAccountReceive;
import com.ntt.clients.infrastructure.out.messaging.mapper.ClientMessagingOutputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientMessagingOutputAdapter implements ClientStatusAccountPort {

  private final RabbitTemplate rabbitTemplate;
  private final ClientMessagingOutputMapper clientMessagingOutputMapper;

  @Override
  public List<StatusAccountReceive> requestStatusAccount(
          LocalDate startDate,
          LocalDate endDate,
          String idNumber,
          Client client
  ) {
    rabbitTemplate.setReplyTimeout(8000);

    final var response = rabbitTemplate.convertSendAndReceive(
            "bank-ntt",
            "account_client_queue",
            clientMessagingOutputMapper.toStatusAccountSendReq(
                    startDate,
                    endDate,
                    client.getId(),
                    client.getName()
            )
    );

    return new ObjectMapper().convertValue(
            response,
            new TypeReference<>() {
            }
    );
  }
}
