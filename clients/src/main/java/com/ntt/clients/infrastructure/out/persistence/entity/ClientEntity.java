package com.ntt.clients.infrastructure.out.persistence.entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity extends PersonEntity {
    private String password;
    private Boolean status;
}
