package cn.dev.framework.security.context;

import org.springframework.security.core.Authentication;

/**
 * @author YangXw
 * @date 2023/01/25 23:52
 * @description
 */
public class AuthenticationContextHolder {

    private static final ThreadLocal<Authentication> CONTEXT_HOLDER = new ThreadLocal<>();

    public static Authentication getContext() {
        return CONTEXT_HOLDER.get();
    }

    public static void setContext(Authentication context) {
        CONTEXT_HOLDER.set(context);
    }

    public static void clearContext() {
        CONTEXT_HOLDER.remove();
    }
}
