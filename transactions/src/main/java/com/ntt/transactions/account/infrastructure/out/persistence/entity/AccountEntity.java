package com.ntt.transactions.account.infrastructure.out.persistence.entity;

import com.ntt.transactions.transaction.infrastructure.out.persistence.entity.TransactionEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long numAccount;

    private String accountType;
    private BigDecimal initialFunds;
    private Boolean status;
    private Long clientRef;

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = CascadeType.REMOVE,
        orphanRemoval = true
    )
    @JoinColumn(name = "account_id")
    private List<TransactionEntity> transactionEntities;
}
