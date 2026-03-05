package com.ntt.clients.infrastructure.in.messaging;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ntt.clients.application.port.in.ClientSearchUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientMessagingInputAdapterTest {

    @Mock
    private ClientSearchUseCase clientSendUseCase;

    @InjectMocks
    private ClientMessagingInputAdapter clientMessagingInputAdapter;

    @Test
    public void clientMessagingInputAdapterTest_sendClientRefReturnsClientId() {
        clientMessagingInputAdapter.sendClientRef("1725082786");
        verify(clientSendUseCase, times(1)).sendClientRef("1725082786");
    }
}
