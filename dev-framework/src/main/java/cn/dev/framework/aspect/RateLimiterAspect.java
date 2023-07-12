package cn.dev.framework.aspect;

import cn.dev.common.annotation.RateLimiter;
import cn.dev.common.enums.LimitType;
import cn.dev.common.exception.ServiceException;
import cn.dev.common.util.IpUtil;
import cn.dev.common.util.ServletUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author YangXw
 * @date 2023/01/29 13:15
 * @description 限流切面
 */
@Aspect
@Component
public class RateLimiterAspect {
    public static final Logger log = LoggerFactory.getLogger(RateLimiterAspect.class);

    private final RedisTemplate redisTemplate;

    private final RedisScript<Long> redisScript;

    public RateLimiterAspect(RedisTemplate redisTemplate, RedisScript<Long> redisScript) {
        this.redisTemplate = redisTemplate;
        this.redisScript = redisScript;
    }

    @Before("@annotation(rateLimiter)")
    public void doRateLimit(JoinPoint joinpoint, RateLimiter rateLimiter) {
        int time = rateLimiter.time();
        int count = rateLimiter.count();

        String rateLimitKey = getRateLimitKey(rateLimiter, joinpoint);
        List<String> keys = Collections.singletonList(rateLimitKey);
        try {
            Long num = (Long) redisTemplate.execute(redisScript, keys, count, time);
            if (num == null || num.intValue() > count) {
                throw new ServiceException("访问过于频繁, 请稍后再试！");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("限流处理发生异常，异常原因：{}", e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    private String getRateLimitKey(RateLimiter rateLimiter, JoinPoint joinpoint) {
        StringBuffer stringBuffer = new StringBuffer(rateLimiter.key());
        if (rateLimiter.limitType() == LimitType.IP) {
            String ip = IpUtil.getIp(ServletUtil.getRequest());
            stringBuffer.append(ip).append("-");
        }
        String methodName = joinpoint.getSignature().getName();
        String className = joinpoint.getTarget().getClass().getName();
        stringBuffer.append(className).append("-").append(methodName);
        return stringBuffer.toString();
    }
}
