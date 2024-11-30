/**
 * @project identity-service
 * @author Phong Ha on 30/11/2024
 */

package com.windev.identity_service.payload.request;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private String accountNumber;
    private String accountType;
}
