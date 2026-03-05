package com.ntt.transactions.account.infrastructure.in.rest.model;

import com.ntt.transactions.transaction.domain.model.Transaction;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private Long numAccount;
    private String accountType;
    private BigDecimal initialFunds;
    private Boolean status;
    private List<Transaction> transactionEntities;
}
