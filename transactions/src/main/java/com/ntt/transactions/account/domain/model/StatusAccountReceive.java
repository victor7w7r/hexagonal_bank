package com.ntt.transactions.account.domain.model;

import java.math.BigDecimal;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusAccountReceive {

    private String date;
    private String client;
    private Long numAccount;
    private String accountType;
    private String typeTransaction;
    private BigDecimal transactionQuantity;
    private BigDecimal balance;
    private BigDecimal actualBalance;
}
