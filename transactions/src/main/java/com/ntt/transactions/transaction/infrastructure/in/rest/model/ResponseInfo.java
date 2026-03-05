package com.ntt.transactions.transaction.infrastructure.in.rest.model;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ResponseInfo {
  private String status;
  private String message;
}
