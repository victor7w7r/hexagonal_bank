package com.ntt.transactions.transaction.infrastructure.in.rest.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private LocalDate date;
    private String typeTransaction;
    private BigDecimal value;
    private BigDecimal balance;
    private String uuid;
}
