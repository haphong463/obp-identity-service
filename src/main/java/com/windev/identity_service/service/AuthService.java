package com.windev.identity_service.service;

import com.windev.identity_service.dto.UserDTO;
import com.windev.identity_service.payload.request.SignInRequest;
import com.windev.identity_service.payload.request.SignUpRequest;
import com.windev.identity_service.security.user_details.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthService {
    String login(SignInRequest request);
    UserDTO register(SignUpRequest request);
    CustomUserDetails currentUser();
}
