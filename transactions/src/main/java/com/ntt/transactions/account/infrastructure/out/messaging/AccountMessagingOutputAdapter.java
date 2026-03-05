package com.ntt.transactions.account.infrastructure.out.messaging;

import com.ntt.transactions.account.application.port.out.AccountMessagingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMessagingOutputAdapter implements AccountMessagingPort {

  private final RabbitTemplate rabbitTemplate;

  @Override
  public Long sendIdReceiveRef(String idNumber) {
    rabbitTemplate.setReplyTimeout(2000);
    return (Long) rabbitTemplate.convertSendAndReceive(
            "bank-ntt",
            "account.created",
            idNumber
    );
  }
}
