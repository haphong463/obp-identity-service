package com.windev.identity_service.mapper;

import com.windev.identity_service.dto.AccountDTO;
import com.windev.identity_service.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDTO toAccountDTO(Account account);
}
