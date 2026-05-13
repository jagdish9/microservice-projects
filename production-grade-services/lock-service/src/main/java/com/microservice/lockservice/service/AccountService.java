package com.microservice.lockservice.service;

import com.microservice.lockservice.dto.AccountDto;
import com.microservice.lockservice.dto.TransferRequest;
import com.microservice.lockservice.entity.Account;
import com.microservice.lockservice.exception.InsufficientBalance;
import com.microservice.lockservice.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transferOptimistic(TransferRequest request) {
        Account from = accountRepository.findById(request.fromId())
                .orElseThrow();

        Account to = accountRepository.findById(request.toId())
                .orElseThrow();

        if(from.getBalance() < request.amount()) {
            throw new InsufficientBalance("Insufficient balance");
        }

        from.setBalance(from.getBalance() - request.amount());
        to.setBalance(to.getBalance() + request.amount());

        //version check happens automatically
        accountRepository.save(from);
        accountRepository.save(to);
    }

    @Transactional
    public void transferPessimistic(TransferRequest request) {
        //DEADLOCK prevention: always lock in ascending order
        Long first = Math.min(request.fromId(), request.toId());
        Long second = Math.max(request.fromId(), request.toId());

        Account a1 = accountRepository.findByIdForUpdate(first).orElseThrow();
        Account a2 = accountRepository.findByIdForUpdate(second).orElseThrow();

        Account from = request.fromId().equals(a1.getId()) ? a1 : a2;
        Account to = request.toId().equals(a1.getId()) ? a1 : a2;

        if(from.getBalance() < request.amount()) {
            throw new InsufficientBalance("Insufficient balance");
        }

        from.setBalance(from.getBalance() - request.amount());
        to.setBalance(to.getBalance() + request.amount());

        accountRepository.save(from);
        accountRepository.save(to);
    }

    //Retry wrapper (DEADLOCK Handling)
    public void transferWithRetry(TransferRequest request) {
        int attempts = 3;

        while (attempts > 0) {
            try {
                transferPessimistic(request);
                return;
            } catch (Exception exception) {
                attempts--;

                if(attempts == 0) {
                    throw exception;
                }

                try {
                    Thread.sleep(100L * (4 - attempts)); //backoff
                } catch (InterruptedException interruptedException) {
                }
            }
        }
    }

    public Account account(AccountDto accountDto) {
        Account account = new Account();
        account.setOwner(accountDto.owner());
        account.setBalance(accountDto.balance());
        return accountRepository.save(account);
    }
}
