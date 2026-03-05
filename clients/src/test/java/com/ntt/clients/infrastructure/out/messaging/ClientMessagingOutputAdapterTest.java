package com.ntt.clients.infrastructure.out.messaging;

import com.ntt.clients.domain.model.Client;
import com.ntt.clients.domain.model.StatusAccountReceive;
import com.ntt.clients.infrastructure.out.messaging.entity.StatusAccountSendReq;
import com.ntt.clients.infrastructure.out.messaging.mapper.ClientMessagingOutputMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientMessagingOutputAdapterTest {

  private final Client client = Client.builder()
          .id(1L)
          .name("Victor")
          .gender("Masculino")
          .age(20)
          .idNumber("1725082786")
          .address("Call Segovia y Raices")
          .phone("0984565509")
          .password("victorContrasena")
          .status(true)
          .build();
  @Mock
  private RabbitTemplate rabbitTemplate;
  @Mock
  private ClientMessagingOutputMapper clientMessagingOutputMapper;
  @InjectMocks
  private ClientMessagingOutputAdapter dclientMessagingOutputAdapter;
  private LocalDate startDate;
  private LocalDate endDate;
  private final StatusAccountSendReq requestMock =
          StatusAccountSendReq.builder()
                  .startDate(startDate)
                  .endDate(endDate)
                  .clientRef(client.getId())
                  .clientName(client.getName())
                  .build();

  @BeforeEach
  void setUp() {
    startDate = LocalDate.of(2023, 1, 1);
    endDate = LocalDate.of(2023, 12, 31);
  }

  @Test
  void requestStatusAccount_shouldSendMessageAndReturnList() {
    List<StatusAccountReceive> expectedList = List.of(
            mock(StatusAccountReceive.class)
    );
    when(
            clientMessagingOutputMapper.toStatusAccountSendReq(
                    startDate,
                    endDate,
                    client.getId(),
                    client.getName()
            )
    ).thenReturn(requestMock);
    when(
            rabbitTemplate.convertSendAndReceive(
                    anyString(),
                    anyString(),
                    eq(requestMock)
            )
    ).thenReturn(expectedList);

    List<StatusAccountReceive> result =
            dclientMessagingOutputAdapter.requestStatusAccount(
                    startDate,
                    endDate,
                    "123",
                    client
            );

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(rabbitTemplate).setReplyTimeout(8000);
    verify(rabbitTemplate).convertSendAndReceive(
            eq("bank-ntt"),
            eq("account_client_queue"),
            eq(requestMock)
    );
    verify(clientMessagingOutputMapper).toStatusAccountSendReq(
            startDate,
            endDate,
            client.getId(),
            client.getName()
    );
  }

  @Test
  void requestStatusAccount_shouldHandleNullResponse() {
    when(
            clientMessagingOutputMapper.toStatusAccountSendReq(
                    any(),
                    any(),
                    any(),
                    any()
            )
    ).thenReturn(requestMock);
    when(
            rabbitTemplate.convertSendAndReceive(
                    anyString(),
                    anyString(),
                    eq(requestMock)
            )
    ).thenReturn(null);

    List<StatusAccountReceive> result =
            dclientMessagingOutputAdapter.requestStatusAccount(
                    startDate,
                    endDate,
                    "123",
                    client
            );
    assertNull(result);
  }
}
