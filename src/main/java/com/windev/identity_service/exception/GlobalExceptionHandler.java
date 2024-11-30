/**
 * @project identity-service
 * @author Phong Ha on 30/11/2024
 */

package com.windev.identity_service.exception;

import com.windev.identity_service.payload.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleAccountNotFoundException(Exception e) {
        ApiResponse<String> res = new ApiResponse<>();
        res.setData(e.getMessage());
        res.setStatusCode(HttpStatus.NOT_FOUND.value());

        return ResponseEntity.internalServerError().body(res);
    }
}
