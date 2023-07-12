package cn.dev.web.controller.system;

import cn.dev.common.constant.Constants;
import cn.dev.common.core.domain.AjaxResult;
import cn.dev.framework.security.domain.LoginBody;
import cn.dev.framework.security.domain.LoginUser;
import cn.dev.framework.security.service.LoginService;
import cn.dev.framework.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YangXw
 * @date 2023/01/26 12:52
 * @description
 */
@RestController
public class SysLoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 结果
     */
    @GetMapping("/getUserInfo")
    public AjaxResult getUserInfo() {
        AjaxResult ajax = AjaxResult.success();
        LoginUser user = SecurityUtil.getLoginUser();
        ajax.put("user",user);
        return ajax;
    }
}
