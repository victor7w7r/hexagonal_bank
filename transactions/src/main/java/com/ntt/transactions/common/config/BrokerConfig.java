package com.ntt.transactions.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {

  public static final String EXCHANGE_NAME = "bank-ntt";

  public static final String CLIENT_ACCOUNT_QUEUE = "account_client_queue";
  public static final String CLIENT_ACCOUNT_ROUTING_KEY = "created.client";

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
  public Queue clientCuentaQueue() {
    return new Queue(CLIENT_ACCOUNT_QUEUE, true);
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
  public Binding bindingClientCuenta() {
    return BindingBuilder.bind(clientCuentaQueue())
            .to(exchange())
            .with(CLIENT_ACCOUNT_ROUTING_KEY);
  }

  @Bean
  public Binding bindingBorrarClient() {
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
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(jsonConverter());
    return template;
  }
}
