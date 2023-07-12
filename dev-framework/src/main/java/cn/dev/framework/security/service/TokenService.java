package cn.dev.framework.security.service;

import cn.dev.common.constant.CacheConstants;
import cn.dev.common.constant.Constants;
import cn.dev.common.core.redis.RedisCache;
import cn.dev.common.util.uuid.IdUtil;
import cn.dev.framework.security.domain.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author YangXw
 * @date 2023/01/26 13:38
 * @description
 */
@Service
public class TokenService {

    /**
     * 令牌自定义标识
     */
    @Value("${token.header}")
    private String header;

    /**
     * 令牌秘钥
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * 令牌有效期（默认30分钟）
     */
    @Value("${token.expireTime}")
    private int expireTime;

    @Autowired
    private RedisCache redisCache;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 10 * 60 * 1000L;

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        String tokenId = IdUtil.fastUUID();
        loginUser.setTokenId(tokenId);
        // TODO: 2023/1/26 设置用户代理信息

        // 刷新用户缓存
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>(1);
        claims.put(Constants.LOGIN_USER_KEY, tokenId);
        return createToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return token;
    }

    private void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据用户tokenID进行缓存
        String tokenKey = getTokenKey(loginUser.getTokenId());
        redisCache.setCacheObject(tokenKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    private String getTokenKey(String uuid) {
        return CacheConstants.LOGIN_TOKEN_KEY + uuid;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.hasText(token)) {
            try {
                Claims claims = parseToken(token);
                String tokenId = (String) claims.get(Constants.LOGIN_USER_KEY);
                String tokenKey = getTokenKey(tokenId);
//                LoginUser user =
//                        JSONObject.parseObject(JSON.toJSONString(redisCache.getCacheObject(tokenKey)),
//                                LoginUser.class);

                LoginUser user =
                        redisCache.getCacheObject(tokenKey);

                return user;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取请求携带的Token令牌
     *
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request) {
        // 请求携带的Token
        String token = request.getHeader(header);
        // ... 可以做一些处理
        return token;
    }

    /**
     * 验证令牌有效期，相差不足10分钟，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime < MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户令牌缓存
     *
     * @param tokenId
     */
    public void delLoginUser(String tokenId) {
        if (StringUtils.hasText(tokenId)) {
            String tokenKey = getTokenKey(tokenId);
            redisCache.deleteObject(tokenKey);
        }
    }
}
