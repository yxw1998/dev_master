package cn.dev.framework.security.service;


import cn.dev.common.constant.CacheConstants;
import cn.dev.common.core.redis.RedisCache;
import cn.dev.common.exception.auth.PasswordRetryLimitExceedException;
import cn.dev.framework.security.context.AuthenticationContextHolder;
import cn.dev.framework.security.util.SecurityUtil;
import cn.dev.system.domain.entity.SysUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author YangXw
 * @date 2023/01/25 21:11
 * @description
 */
@Component
public class PasswordService {

    private RedisCache redisCache;

    @Value(value = "${user.password.maxRetry}")
    private int maxRetry;

    @Value(value = "${user.password.lockTime}")
    private int lockTime;

    public PasswordService(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    private String getCacheKey(String username) {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    public void valid(SysUser user) {
        Authentication context = AuthenticationContextHolder.getContext();
        String username = context.getName();
        String password = context.getCredentials().toString();

        Integer retryCount = redisCache.getCacheObject(getCacheKey(username));

        if (retryCount == null) {
            retryCount = 0;
        }


        if (retryCount > maxRetry) {
            throw new PasswordRetryLimitExceedException("重试次数过多，请稍后再试");
        }

        if (!matches(user, password)) {
            retryCount += 1;
            redisCache.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            throw new BadCredentialsException("密码错误");
        }

        clearLoginRecordCache(username);
    }

    /**
     * 清除用户登录状态
     *
     * @param username
     */
    private void clearLoginRecordCache(String username) {
        if (redisCache.hasKey(getCacheKey(username))) {
            redisCache.deleteObject(getCacheKey(username));
        }
    }


    private boolean matches(SysUser user, String password) {
        return SecurityUtil.matchesPassword(password, user.getPassword());
    }

}
