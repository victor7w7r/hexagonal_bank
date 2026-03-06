package com.ntt.transactions.account.infrastructure.in.rest;

import com.ntt.transactions.account.application.port.in.AccountCreateUseCase;
import com.ntt.transactions.account.application.port.in.AccountDeleteUseCase;
import com.ntt.transactions.account.application.port.in.AccountSearchUseCase;
import com.ntt.transactions.account.application.port.in.AccountUpdateUseCase;
import com.ntt.transactions.account.infrastructure.in.rest.mapper.AccountRestMapper;
import com.ntt.transactions.account.infrastructure.in.rest.model.AccountOperationResponse;
import com.ntt.transactions.account.infrastructure.in.rest.model.AccountRequest;
import com.ntt.transactions.account.infrastructure.in.rest.model.AccountResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@Slf4j
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountRestAdapter {

  private final AccountSearchUseCase accountSearchUseCase;
  private final AccountCreateUseCase accountCreateUseCase;
  private final AccountUpdateUseCase accountUpdateUseCase;
  private final AccountDeleteUseCase accountDeleteUseCase;
  private final AccountRestMapper accountRestMapper;

  @GetMapping
  private List<AccountResponse> findAll() {
    log.info("Querying all clients");
    return accountRestMapper.toAccountResponseList(accountSearchUseCase.findAll());
  }

  @PostMapping("{idNumber}")
  private ResponseEntity<AccountOperationResponse> saveAccount(
          @Valid @RequestBody AccountRequest request,
          @PathVariable String idNumber
  ) {
    log.info("Creating account {}", request.getNumAccount());
    accountCreateUseCase.save(accountRestMapper.toAccount(request), idNumber);
    return ResponseEntity.status(201).body(
            AccountOperationResponse.builder().status("status").message("Cuenta guardado exitosamente").build()
    );
  }

  @PutMapping
  private ResponseEntity<AccountOperationResponse> updateAccount(
          @Valid @RequestBody AccountRequest request
  ) {
    log.info("Updating account {}", request.getNumAccount());
    accountUpdateUseCase.update(accountRestMapper.toAccount(request));
    return ResponseEntity.status(202).body(
            AccountOperationResponse.builder().status("status").message("Cuenta actualizada exitosamente").build()
    );
  }

  @DeleteMapping("{numAccount}")
  private ResponseEntity<AccountOperationResponse> deleteAccount(
          @PathVariable Long numAccount
  ) {
    log.info("Deleting account {}", numAccount);
    accountDeleteUseCase.delete(numAccount);
    return ResponseEntity.status(202).body(
            AccountOperationResponse.builder().status("status").message("Cliente eliminada exitosamente").build()
    );
  }
}
