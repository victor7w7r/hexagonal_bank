package com.ntt.transactions.account.infrastructure.in.messaging;

import com.ntt.transactions.account.application.port.in.AccountUseCase;
import com.ntt.transactions.account.infrastructure.in.messaging.entity.StatusAccountReceiveRes;
import com.ntt.transactions.account.infrastructure.in.messaging.entity.StatusAccountSendReq;
import com.ntt.transactions.account.infrastructure.in.messaging.mapper.AccountMessagingInputMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMessagingInputAdapter {

    private final AccountUseCase clientUseCase;
    private final AccountMessagingInputMapper accountMessagingInputMapper;

    @RabbitListener(queues = "borrar_client")
    public Long deleteAccountPerQueue(Long clientRef) {
        return clientUseCase.deleteByClientRef(clientRef);
    }

    @RabbitListener(queues = "account_status_queue")
    public List<StatusAccountReceiveRes> requestEstadoAccount(
        @Payload StatusAccountSendReq req
    ) {
        return accountMessagingInputMapper.toStatusAccountReceiveResList(
            clientUseCase.requestStatusAccount(
                accountMessagingInputMapper.toStatusAccountSend(req)
            )
        );
    }
}
