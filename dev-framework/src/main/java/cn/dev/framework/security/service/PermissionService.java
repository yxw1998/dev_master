package cn.dev.framework.security.service;

import cn.dev.framework.security.domain.LoginUser;
import cn.dev.framework.security.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author YangXw
 * @date 2023/01/26 22:06
 * @description 自定义权限实现
 */
@Service("ps")
public class PermissionService {

    /**
     * 验证用户是否具备某个权限
     * @param permission
     * @return
     */
    public boolean hasPerm(String permission){

        if (!StringUtils.hasText(permission)) {
            return false;
        }
        LoginUser loginUser = SecurityUtil.getLoginUser();
        return true;
    }
}
