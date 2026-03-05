package com.ntt.clients.infrastructure.config;

import com.ntt.clients.application.port.in.ClientSearchUseCase;
import com.ntt.clients.infrastructure.in.messaging.ClientMessagingInputAdapter;
import com.ntt.clients.infrastructure.out.messaging.ClientMessagingOutputAdapter;
import com.ntt.clients.infrastructure.out.messaging.mapper.ClientMessagingOutputMapper;
import com.ntt.clients.infrastructure.out.persistence.ClientRepositoryPersistenceAdapter;
import com.ntt.clients.infrastructure.out.persistence.mapper.ClientPersistenceMapper;
import com.ntt.clients.infrastructure.out.persistence.repository.ClientRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Bean
  public ClientRepositoryPersistenceAdapter clientPersistenceAdapter(
          ClientRepository clientRepository,
          ClientPersistenceMapper clientPersistenceMapper
  ) {
    return new ClientRepositoryPersistenceAdapter(
            clientRepository,
            clientPersistenceMapper
    );
  }

  @Bean
  public ClientMessagingInputAdapter clientMessagingInputAdapter(
          ClientSearchUseCase clientUseCase
  ) {
    return new ClientMessagingInputAdapter(clientUseCase);
  }

  @Bean
  public ClientMessagingOutputAdapter clientMessagingOutputAdapter(
          RabbitTemplate rabbitTemplate,
          ClientMessagingOutputMapper clientMessagingOutputMapper
  ) {
    return new ClientMessagingOutputAdapter(
            rabbitTemplate,
            clientMessagingOutputMapper
    );
  }
}
