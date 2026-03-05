package com.ntt.transactions.transaction.infrastructure.out.persistence.mapper;

import com.ntt.transactions.transaction.domain.model.Transaction;
import com.ntt.transactions.transaction.infrastructure.out.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionPersistenceMapper {
  List<Transaction> toTransactionList(
          List<TransactionEntity> transactionEntities
  );

  Transaction toTransaction(TransactionEntity transactionEntity);

  TransactionEntity toTransactionEntity(Transaction transaction);

  @Mapping(target = "uuid", ignore = true)
  void update(Transaction source, @MappingTarget TransactionEntity target);
}
