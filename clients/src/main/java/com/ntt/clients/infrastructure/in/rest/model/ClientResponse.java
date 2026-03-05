package com.ntt.clients.infrastructure.in.rest.model;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
  private String name;
  private String gender;
  private Integer age;
  private String idNumber;
  private String address;
  private String phone;
  private String password;
  private Boolean status;
}
