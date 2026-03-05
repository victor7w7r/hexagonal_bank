package com.ntt.clients.infrastructure.in.messaging;

import com.ntt.clients.application.port.in.ClientSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientMessagingInputAdapter {

  private final ClientSearchUseCase clientSearchUseCase;

  @RabbitListener(queues = "account_client_queue")
  public Long sendClientRef(String idNumber) {
    return clientSearchUseCase.sendClientRef(idNumber);
  }
}
