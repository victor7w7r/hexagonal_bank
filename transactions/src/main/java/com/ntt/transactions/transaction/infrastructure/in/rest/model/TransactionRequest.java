package com.ntt.transactions.transaction.infrastructure.in.rest.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

  @NotNull
  private LocalDate date;

  private String typeTransaction;

  @NotNull
  private BigDecimal value;

  private BigDecimal balance;
  private String uuid;
}
