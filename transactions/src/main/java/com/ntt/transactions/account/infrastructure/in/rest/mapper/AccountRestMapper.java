package com.ntt.transactions.account.infrastructure.in.rest.mapper;

import com.ntt.transactions.account.domain.model.Account;
import com.ntt.transactions.account.infrastructure.in.rest.model.AccountRequest;
import com.ntt.transactions.account.infrastructure.in.rest.model.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AccountRestMapper {
  Account toAccount(AccountRequest accountRequest);

  AccountResponse toAccountResponse(Account account);

  List<AccountResponse> toAccountResponseList(List<Account> accountList);
}
