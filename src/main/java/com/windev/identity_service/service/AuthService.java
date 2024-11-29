package com.windev.identity_service.service;

import com.windev.identity_service.dto.UserDTO;
import com.windev.identity_service.payload.request.SignInRequest;
import com.windev.identity_service.payload.request.SignUpRequest;

public interface AuthService {
    String login(SignInRequest request);
    UserDTO register(SignUpRequest request);
}
