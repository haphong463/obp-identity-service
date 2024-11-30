/**
 * @project identity-service
 * @author Phong Ha on 30/11/2024
 */

package com.windev.identity_service.constant;

import java.util.concurrent.TimeUnit;

public class AuthConstant {
    public static final TimeUnit TIME_UNIT_TOKEN = TimeUnit.MILLISECONDS;
    public static final long TOKEN_EXPIRATION_TIME = 600000;
    public static final String TOKEN_PREFIX = "auth_token_";
}
