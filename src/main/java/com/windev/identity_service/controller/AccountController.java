/**
 * @project identity-service
 * @author Phong Ha on 30/11/2024
 */

package com.windev.identity_service.controller;

import com.windev.identity_service.dto.AccountDTO;
import com.windev.identity_service.payload.request.CreateAccountRequest;
import com.windev.identity_service.payload.request.DepositRequest;
import com.windev.identity_service.payload.response.ApiResponse;
import com.windev.identity_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<ApiResponse<AccountDTO>> createAccount(@RequestBody CreateAccountRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(accountService.createAccount(request),
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @PatchMapping("/deposit")
    public ResponseEntity<ApiResponse<AccountDTO>> deposit(@RequestBody DepositRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(accountService.deposit(request),
                HttpStatus.OK.value()), HttpStatus.OK);
    }

    @PatchMapping("/withdraw")
    public ResponseEntity<ApiResponse<AccountDTO>> withDraw(@RequestBody DepositRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(accountService.withdraw(request), HttpStatus.OK.value()),
                HttpStatus.OK);
    }

    @GetMapping("{accountNumber}")
    public ResponseEntity<ApiResponse<AccountDTO>> getAccountByAccountNumber(@PathVariable String accountNumber) {
        return new ResponseEntity<>(new ApiResponse<>(accountService.findByAccountNumber(accountNumber),
                HttpStatus.OK.value()), HttpStatus.OK);
    }

}
