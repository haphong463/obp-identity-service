/**
 * @project identity-service
 * @author Phong Ha on 29/11/2024
 */

package com.windev.identity_service.payload.request;

import lombok.Data;

@Data
public class SignUpRequest {
    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private String password;

    private String phoneNumber;

}
