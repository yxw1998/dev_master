package cn.dev.common.exception.auth;

/**
 * @author YangXw
 * @date 2023/01/26 12:18
 * @description 密码不匹配异常
 */
public class PasswordNotMatchException extends RuntimeException {

    public PasswordNotMatchException() {
    }

    public PasswordNotMatchException(String message) {
        super(message);
    }
}
