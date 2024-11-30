package com.windev.identity_service.service;

import com.windev.identity_service.dto.AccountDTO;
import com.windev.identity_service.payload.request.CreateAccountRequest;
import com.windev.identity_service.payload.request.DepositRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountService {
    AccountDTO createAccount(CreateAccountRequest request);
    AccountDTO findByAccountNumber(String accountNumber);
    AccountDTO deposit(DepositRequest request);
    AccountDTO withdraw(DepositRequest request);

}
