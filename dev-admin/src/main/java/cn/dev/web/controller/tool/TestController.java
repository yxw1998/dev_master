package cn.dev.web.controller.tool;

import cn.dev.common.annotation.Log;
import cn.dev.common.annotation.RateLimiter;
import cn.dev.common.core.controller.BaseController;
import cn.dev.common.core.domain.AjaxResult;
import cn.dev.common.enums.LimitType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author YangXw
 * @date 2023/01/18 21:03
 * @description
 */
@RestController
@RequestMapping("/test/user")
public class TestController extends BaseController {

    public static final HashMap<Integer, UserEntity> users = new LinkedHashMap<>();

    {
        users.put(1, new UserEntity(1, "admin", "admin123", "15888888888"));
        users.put(2, new UserEntity(2, "dev", "dev123", "15666666666"));
    }

    @RateLimiter(limitType = LimitType.IP)
//    @PreAuthorize("@ps.hasPerm('123')")
    @Log(module = "测试", business = "获取用户列表", isSaveRequestData = true, isSaveResponseData = true)
    @GetMapping("/list")
    public AjaxResult userList(@RequestBody UserEntity user, String context) {
        List<UserEntity> userList = new ArrayList<UserEntity>(users.values());
        return success(userList);
    }

}

class UserEntity {
    private Integer userId;

    private String username;

    private String password;

    private String mobile;

    public UserEntity() {

    }

    public UserEntity(Integer userId, String username, String password, String mobile) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.mobile = mobile;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

