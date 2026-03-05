package com.ntt.transactions.transaction.infrastructure.out.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String typeTransaction;
    private BigDecimal value;
    private BigDecimal balance;
    private String uuid;

    @Column(name = "account_id")
    private Long accountId;
}
