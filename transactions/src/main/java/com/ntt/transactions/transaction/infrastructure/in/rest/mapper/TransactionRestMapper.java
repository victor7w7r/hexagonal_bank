package com.ntt.transactions.transaction.infrastructure.in.rest.mapper;

import com.ntt.transactions.transaction.domain.model.Transaction;
import com.ntt.transactions.transaction.infrastructure.in.rest.model.TransactionRequest;
import com.ntt.transactions.transaction.infrastructure.in.rest.model.TransactionResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TransactionRestMapper {
    Transaction toTransaction(TransactionRequest transactionRequest);
    TransactionResponse toTransactionResponse(Transaction transaction);
    List<TransactionResponse> toTransactionResponseList(
        List<Transaction> transactionList
    );
}
