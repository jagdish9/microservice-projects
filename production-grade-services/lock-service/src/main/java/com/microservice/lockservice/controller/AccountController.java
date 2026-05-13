package com.microservice.lockservice.controller;

import com.microservice.lockservice.dto.AccountDto;
import com.microservice.lockservice.dto.TransferRequest;
import com.microservice.lockservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/account")
    public ResponseEntity<?> account(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.account(accountDto), HttpStatus.CREATED);
    }

    @PostMapping("/transfer/optimistic")
    public String optimistic(@RequestBody TransferRequest request) {
        accountService.transferOptimistic(request);
        return "Optimistic transfer successful";
    }

    @PostMapping("/transfer/pessimistic")
    public String pessimistic(@RequestBody TransferRequest request) {
        accountService.transferPessimistic(request);
        return "Pessimistic transfer successful";
    }

    @PostMapping("/transfer/retry")
    public String retry(@RequestBody TransferRequest request) {
        accountService.transferWithRetry(request);
        return "Retry based transfer successful";
    }
}
