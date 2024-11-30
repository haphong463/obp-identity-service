/**
 * @project identity-service
 * @author Phong Ha on 30/11/2024
 */

package com.windev.identity_service.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    private String id;

    @Column(unique = true)
    private String accountNumber;

    private BigDecimal balance;

    private String accountType; // EX: SAVINGS, CHECKING

    private Date createdAt;

    private Date updatedAt;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @PrePersist
    public void onCreate(){
        id = UUID.randomUUID().toString();
        createdAt = new Date();
        updatedAt = new Date();
        balance = BigDecimal.ZERO;
    }

    @PreUpdate
    public void onUpdate(){
        updatedAt = new Date();
    }
}

