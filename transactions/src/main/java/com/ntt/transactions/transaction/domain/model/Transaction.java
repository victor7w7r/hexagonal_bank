package com.ntt.transactions.transaction.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

  private LocalDate date;
  private String typeTransaction;
  private BigDecimal value;
  private BigDecimal balance;
  private String uuid;
  private String accountId;
}
