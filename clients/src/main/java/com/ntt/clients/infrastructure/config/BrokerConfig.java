package com.ntt.clients.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BrokerConfig {

  public static final String EXCHANGE_NAME = "bank-ntt";

  public static final String ACCOUNT_CLIENT_QUEUE = "account_client_queue";
  public static final String ACCOUNT_CLIENT_ROUTING_KEY = "created.client";

  public static final String CLIENT_ERASE_QUEUE = "client_erase";
  public static final String CLIENT_ERASE_ROUTING_KEY = "client.deleted";

  public static final String ACCOUNT_STATUS_QUEUE = "account_status_queue";
  public static final String ACCOUNT_STATUS_ROUTING_KEY =
          "account.status.requested";

  @Bean
  public DirectExchange exchange() {
    return new DirectExchange(EXCHANGE_NAME);
  }

  @Bean
  public Queue clientAccountQueue() {
    return new Queue(ACCOUNT_CLIENT_QUEUE, true);
  }

  @Bean
  public Queue borrarClientQueue() {
    return new Queue(CLIENT_ERASE_QUEUE, true);
  }

  @Bean
  public Queue accountStatusQueue() {
    return new Queue(ACCOUNT_STATUS_QUEUE, true);
  }

  @Bean
  public Binding bindingAccountClient() {
    return BindingBuilder.bind(clientAccountQueue())
            .to(exchange())
            .with(ACCOUNT_CLIENT_ROUTING_KEY);
  }

  @Bean
  public Binding bindingEraseClient() {
    return BindingBuilder.bind(borrarClientQueue())
            .to(exchange())
            .with(CLIENT_ERASE_ROUTING_KEY);
  }

  @Bean
  public Binding bindingAccountStatus() {
    return BindingBuilder.bind(accountStatusQueue())
            .to(exchange())
            .with(ACCOUNT_STATUS_ROUTING_KEY);
  }

  @Bean
  public MessageConverter jsonConverter() {
    final var converter = new Jackson2JsonMessageConverter();

    Map<String, Class<?>> typeMappings = new HashMap<>();
    typeMappings.put(
            "com.ntt.transactions.account.infrastructure.in.messaging.entity.StatusAccountReceiveRes",
            com.ntt.clients.domain.model.StatusAccountReceive.class
    );

    final var classMapper = new DefaultClassMapper();
    classMapper.setIdClassMapping(typeMappings);
    converter.setClassMapper(classMapper);
    return converter;
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    final var template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(jsonConverter());
    return template;
  }
}
