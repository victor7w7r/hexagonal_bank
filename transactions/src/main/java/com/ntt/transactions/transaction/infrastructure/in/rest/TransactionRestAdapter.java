package com.ntt.transactions.transaction.infrastructure.in.rest;

import com.ntt.transactions.account.infrastructure.in.rest.model.AccountOperationResponse;
import com.ntt.transactions.transaction.application.port.in.TransactionUseCase;
import com.ntt.transactions.transaction.infrastructure.in.rest.mapper.TransactionRestMapper;
import com.ntt.transactions.transaction.infrastructure.in.rest.model.TransactionRequest;
import com.ntt.transactions.transaction.infrastructure.in.rest.model.TransactionResponse;
import jakarta.validation.Valid;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionRestAdapter {

    private final TransactionRestMapper transactionRestMapper;
    private final TransactionUseCase transactionUseCase;
    private static final Logger log = LoggerFactory.getLogger(TransactionRestAdapter.class);

    @GetMapping
    private List<TransactionResponse> findAll() {
        return transactionRestMapper.toTransactionResponseList(
            transactionUseCase.findAll()
        );
    }

    @PostMapping("{numAccount}")
    private ResponseEntity<AccountOperationResponse> saveTransaction(
        @RequestBody @Valid TransactionRequest req,
        @PathVariable Long numAccount
    ) {
        log.info("Creating transaction {}", req.getUuid());
        transactionUseCase.save(
            transactionRestMapper.toTransaction(req),
            numAccount
        );
        return ResponseEntity.status(201).body(
              AccountOperationResponse.builder().status("status").message("Transaccion guardada exitosamente").build()
        );
    }

    @PutMapping
    private ResponseEntity<AccountOperationResponse> updateTransaction(
        @RequestBody @Valid TransactionRequest req
    ) {
      log.info("Updating transaction {}", req.getUuid());
        transactionUseCase.update(transactionRestMapper.toTransaction(req));
        return ResponseEntity.status(202).body(
              AccountOperationResponse.builder().status("status").message("Transaccion actualizada exitosamente").build()
        );
    }

    @DeleteMapping("{uuid}")
    private ResponseEntity<AccountOperationResponse> deleteTransaction(
        @PathVariable String uuid
    ) {
      log.info("Deleting transaction {}", uuid);
        transactionUseCase.delete(uuid);
        return ResponseEntity.status(202).body(
                AccountOperationResponse.builder().status("status").message("Transaccion eliminada exitosamente").build()
        );
    }
}
