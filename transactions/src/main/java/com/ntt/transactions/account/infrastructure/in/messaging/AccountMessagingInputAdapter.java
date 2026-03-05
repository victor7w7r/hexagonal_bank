package com.ntt.transactions.account.infrastructure.in.messaging;

import com.ntt.transactions.account.application.port.in.AccountDeleteUseCase;
import com.ntt.transactions.account.application.port.in.AccountSearchUseCase;
import com.ntt.transactions.account.infrastructure.in.messaging.entity.StatusAccountReceiveRes;
import com.ntt.transactions.account.infrastructure.in.messaging.entity.StatusAccountSendReq;
import com.ntt.transactions.account.infrastructure.in.messaging.mapper.AccountMessagingInputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountMessagingInputAdapter {

  private final AccountDeleteUseCase accountDeleteUseCase;
  private final AccountSearchUseCase accountSearchUseCase;
  private final AccountMessagingInputMapper accountMessagingInputMapper;

  @RabbitListener(queues = "borrar_client")
  public Long deleteAccountPerQueue(Long clientRef) {
    return accountDeleteUseCase.deleteByClientRef(clientRef);
  }

  @RabbitListener(queues = "account_status_queue")
  public List<StatusAccountReceiveRes> requestEstadoAccount(
          @Payload StatusAccountSendReq req
  ) {
    return accountMessagingInputMapper.toStatusAccountReceiveResList(
            accountSearchUseCase.requestStatusAccount(
                    accountMessagingInputMapper.toStatusAccountSend(req)
            )
    );
  }
}
