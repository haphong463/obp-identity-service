/**
 * @project identity-service
 * @author Phong Ha on 30/11/2024
 */

package com.windev.identity_service.payload.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class DepositRequest {
    private String accountNumber;
    private BigDecimal amount;
}
