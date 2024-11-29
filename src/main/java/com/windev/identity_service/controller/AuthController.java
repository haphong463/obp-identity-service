/**
 * @project identity-service
 * @author Phong Ha on 29/11/2024
 */

package com.windev.identity_service.controller;

import com.windev.identity_service.dto.UserDTO;
import com.windev.identity_service.payload.request.SignInRequest;
import com.windev.identity_service.payload.request.SignUpRequest;
import com.windev.identity_service.payload.response.ApiResponse;
import com.windev.identity_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDTO>> signUp(@RequestBody SignUpRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(authService.register(request), HttpStatus.CREATED.value()),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody SignInRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(authService.login(request), HttpStatus.OK.value()),
                HttpStatus.OK);
    }
}
