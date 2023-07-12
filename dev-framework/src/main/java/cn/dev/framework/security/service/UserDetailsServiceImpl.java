package cn.dev.framework.security.service;

import cn.dev.common.constant.UserStatus;
import cn.dev.common.exception.ServiceException;
import cn.dev.framework.security.domain.LoginUser;
import cn.dev.system.domain.entity.SysUser;
import cn.dev.system.mapper.SysUserMapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author YangXw
 * @date 2023/01/25 20:59
 * @description
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private SysUserMapper sysUserMapper;

    private PasswordService passwordService;

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    public UserDetailsServiceImpl(SysUserMapper sysUserMapper, PasswordService passwordService) {
        this.sysUserMapper = sysUserMapper;
        this.passwordService = passwordService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 验证用户
        SysUser user = new LambdaQueryChainWrapper<>(sysUserMapper)
                .eq(SysUser::getUserName, username)
                .one();

        if (user == null) {
            log.info("登录用户: {} 不存在", username);
            throw new UsernameNotFoundException("对不起，您的账号 : " + username + " 不存在");
        } else if (UserStatus.DELETED.equals(user.getDelFlag())) {
            log.info("登录用户: {} 已删除", username);
            throw new LockedException("对不起，您的账号 : " + username + " 已删除");
        } else if (UserStatus.DISABLE.equals(user.getStatus())) {
            log.info("登录用户: {} 已被停用", username);
            throw new DisabledException("对不起，您的账号 : " + username + " 已被停用");
        }

        // 验证密码
        passwordService.valid(user);

        return createLoginUser(user);
    }

    /**
     * 创建认证用户
     * @param user
     * @return
     */
    private UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user.getUserId(), null, user);
    }
}
