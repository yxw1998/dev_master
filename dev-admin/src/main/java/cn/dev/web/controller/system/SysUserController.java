package cn.dev.web.controller.system;

import cn.dev.common.core.controller.BaseController;
import cn.dev.common.core.domain.AjaxResult;
import cn.dev.system.domain.VO.UserVO;
import cn.dev.system.domain.entity.SysUser;
import cn.dev.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author YangXw
 * @date 2023/03/08 11:05
 * @description
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    private SysUserService userService;

    public SysUserController(SysUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/list")
    public AjaxResult list(@RequestBody SysUser user, Integer current, Integer size) {
        IPage<SysUser> page = userService.queryUserList(user, current, size);
        return success(page);
    }


    @GetMapping("/details/{userId}")
    public AjaxResult details(@PathVariable String userId) {
        UserVO userVO =  userService.getUserDetails(userId);
        return success(userVO);
    }

}
