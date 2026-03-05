package com.ntt.transactions.account.infrastructure.in.rest;

import com.ntt.transactions.account.application.port.in.AccountUseCase;
import com.ntt.transactions.account.infrastructure.in.rest.mapper.AccountRestMapper;
import com.ntt.transactions.account.infrastructure.in.rest.model.AccountRequest;
import com.ntt.transactions.account.infrastructure.in.rest.model.AccountResponse;
import com.ntt.transactions.account.infrastructure.in.rest.model.ResponseInfo;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountRestAdapter {

  private final AccountUseCase accountUseCase;
  private final AccountRestMapper accountRestMapper;
  private static final Logger log = LoggerFactory.getLogger(AccountRestAdapter.class);

  @GetMapping
    private List<AccountResponse> findAll() {
        log.info("Querying all clients");
        return accountRestMapper.toAccountResponseList(accountUseCase.findAll());
    }

    @PostMapping("{idNumber}")
    private ResponseEntity<ResponseInfo> saveAccount(
        @Valid @RequestBody AccountRequest request,
        @PathVariable String idNumber
    ) {
      log.info("Creating account {}", request.getNumAccount());
      accountUseCase.save(accountRestMapper.toAccount(request), idNumber);
        return ResponseEntity.status(201).body(
              ResponseInfo.builder().status("status").message("Cuenta guardado exitosamente").build()
        );
    }

    @PutMapping
    private ResponseEntity<ResponseInfo> updateAccount(
        @Valid @RequestBody AccountRequest request
    ) {
        log.info("Updating account {}", request.getNumAccount());
        accountUseCase.update(accountRestMapper.toAccount(request));
        return ResponseEntity.status(202).body(
              ResponseInfo.builder().status("status").message("Cuenta actualizada exitosamente").build()
        );
    }

    @DeleteMapping("{numAccount}")
    private ResponseEntity<ResponseInfo> deleteAccount(
        @PathVariable Long numAccount
    ) {
      log.info("Deleting account {}", numAccount);
      accountUseCase.delete(numAccount);
        return ResponseEntity.status(202).body(
              ResponseInfo.builder().status("status").message("Cliente eliminada exitosamente").build()
        );
    }
}
