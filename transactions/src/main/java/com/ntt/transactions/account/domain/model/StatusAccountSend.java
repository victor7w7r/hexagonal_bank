package com.ntt.transactions.account.domain.model;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccountSend {

  private LocalDate startDate;
  private LocalDate endDate;
  private Long clientRef;
  private String nameClient;
}
