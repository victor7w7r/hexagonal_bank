package com.ntt.transactions.account.domain.model;

import com.ntt.transactions.transaction.domain.model.Transaction;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private Long id;
    private Long numAccount;
    private String accountType;
    private BigDecimal initialFunds;
    private Long accountRef;
    private Boolean status;
    private List<Transaction> transactionEntities;
}
