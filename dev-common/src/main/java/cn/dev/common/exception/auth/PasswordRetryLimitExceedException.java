package cn.dev.common.exception.auth;

import org.springframework.security.core.AuthenticationException;

/**
 * @author YangXw
 * @date 2023/01/26 0:23
 * @description 密码超过次数异常
 */
public class PasswordRetryLimitExceedException extends AuthenticationException {

    public PasswordRetryLimitExceedException(String message) {
        super(message);
    }
}
