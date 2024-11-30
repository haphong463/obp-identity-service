/**
 * @project identity-service
 * @author Phong Ha on 30/11/2024
 */

package com.windev.identity_service.service.impl;

import com.windev.identity_service.dto.AccountDTO;
import com.windev.identity_service.entity.Account;
import com.windev.identity_service.entity.User;
import com.windev.identity_service.exception.AccountNotFoundException;
import com.windev.identity_service.mapper.AccountMapper;
import com.windev.identity_service.payload.request.CreateAccountRequest;
import com.windev.identity_service.payload.request.DepositRequest;
import com.windev.identity_service.repository.AccountRepository;
import com.windev.identity_service.repository.UserRepository;
import com.windev.identity_service.security.user_details.CustomUserDetails;
import com.windev.identity_service.service.AccountService;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AccountDTO createAccount(CreateAccountRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found."));


        Account account = new Account();
        account.setAccountNumber(request.getAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setUser(existingUser);

        Account savedAccount = accountRepository.save(account);
        return accountMapper.toAccountDTO(savedAccount);
    }

    @Override
    public AccountDTO findByAccountNumber(String accountNumber) {
        Account existingAccount = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account with code: " + accountNumber +
                        " not found."));

        return accountMapper.toAccountDTO(existingAccount);
    }

    @Override
    @Transactional
    public AccountDTO deposit(DepositRequest request) {
        Account existingAccount = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account with code: " + request.getAccountNumber() +
                        " not found."));

        BigDecimal currentBalance = existingAccount.getBalance();

        BigDecimal updatedBalance = currentBalance.add(request.getAmount());

        existingAccount.setBalance(updatedBalance);

        Account updatedAccount = accountRepository.save(existingAccount);
        return accountMapper.toAccountDTO(updatedAccount);
    }
}
