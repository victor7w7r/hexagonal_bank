package com.ntt.clients.infrastructure.in.rest.model;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ClientOperationResponse {
  private String status;
  private String message;
}
