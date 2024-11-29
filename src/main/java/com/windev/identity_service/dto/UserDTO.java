/**
 * @project identity-service
 * @author Phong Ha on 29/11/2024
 */

package com.windev.identity_service.dto;

import java.util.Date;
import java.util.Set;
import lombok.Data;

@Data
public class UserDTO {
    private String id;

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private String password;

    private String phoneNumber;

    private Date createdAt;

    private Date updatedAt;

    private Set<RoleDTO> roles;
}
