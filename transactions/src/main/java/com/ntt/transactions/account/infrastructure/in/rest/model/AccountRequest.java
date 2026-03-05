package com.ntt.transactions.account.infrastructure.in.rest.model;

import com.ntt.transactions.transaction.domain.model.Transaction;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    @NotNull
    private Long numAccount;

    @NotNull
    private String accountType;

    @NotNull
    private BigDecimal initialFunds;

    private Long accountRef;
    private Boolean status;

    private List<Transaction> transactions;
}
