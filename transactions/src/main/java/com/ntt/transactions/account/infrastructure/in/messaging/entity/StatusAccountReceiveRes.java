package com.ntt.transactions.account.infrastructure.in.messaging.entity;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccountReceiveRes {

  private String date;
  private String client;
  private Long numAccount;
  private String accountType;
  private String typeTransaction;
  private BigDecimal transaction;
  private BigDecimal balance;
  private BigDecimal availableFunds;
}
