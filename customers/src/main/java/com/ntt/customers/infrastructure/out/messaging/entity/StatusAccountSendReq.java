package com.ntt.customers.infrastructure.out.messaging.entity;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccountSendReq {

  private LocalDate startDate;
  private LocalDate endDate;
  private Long clientRef;
  private String clientName;
}
