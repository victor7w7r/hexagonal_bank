package com.ntt.clients.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccountReceive {

  private String date;
  private String client;
  private Long numAccount;
  private String accountType;
  private String transactionType;
  private BigDecimal transaction;
  private BigDecimal funds;
  private BigDecimal availableFunds;
}
