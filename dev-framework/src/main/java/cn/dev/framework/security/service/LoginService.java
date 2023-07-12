package cn.dev.framework.security.service;

import cn.dev.common.constant.CacheConstants;
import cn.dev.common.constant.Constants;
import cn.dev.common.core.redis.RedisCache;
import cn.dev.common.exception.ServiceException;
import cn.dev.framework.manager.AsyncFactory;
import cn.dev.framework.manager.AsyncManager;
import cn.dev.framework.security.context.AuthenticationContextHolder;
import cn.dev.framework.security.domain.LoginUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author YangXw
 * @date 2023/01/26 13:31
 * @description
 */
@Service
public class LoginService {


    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    private RedisCache redisCache;

    public LoginService(AuthenticationManager authenticationManager, TokenService tokenService, RedisCache redisCache) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.redisCache = redisCache;
    }


    public String login(String username, String password, String code, String uuid) {
        validateCaptcha(username, code, uuid);

        Authentication authentication = null;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        AuthenticationContextHolder.setContext(authenticationToken);
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            AsyncManager.instance().execute(AsyncFactory.recordLoginLog(username, Constants.LOGIN_STATUS_FAIL, e.getMessage()));
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        } finally {
            AuthenticationContextHolder.clearContext();
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        AsyncManager.instance().execute(AsyncFactory.recordLoginLog(username, Constants.LOGIN_STATUS_SUCCESS, null));
        // 生产Token
        return tokenService.createToken(loginUser);

    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     */
    private void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + Optional.ofNullable(uuid).orElse("");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.instance().execute(AsyncFactory.recordLoginLog(username, Constants.LOGIN_STATUS_FAIL, "验证码已失效"));
            throw new ServiceException("验证码已失效");
        }
        if (!code.equals(captcha)) {
            AsyncManager.instance().execute(AsyncFactory.recordLoginLog(username, Constants.LOGIN_STATUS_FAIL, "验证码错误"));
            throw new ServiceException("验证码错误");
        }
    }
}
