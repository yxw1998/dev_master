package cn.dev.web.controller.system;

import cn.dev.common.core.controller.BaseController;
import cn.dev.common.core.domain.AjaxResult;
import cn.dev.framework.security.util.SecurityUtil;
import cn.dev.system.domain.entity.SysMenu;
import cn.dev.system.service.ISysMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author YangXw
 * @date 2023/03/05 15:20
 * @description 菜单信息
 */

@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {

    private ISysMenuService menuService;

    public SysMenuController(ISysMenuService menuService) {
        this.menuService = menuService;
    }


    /**
     * 获取菜单列表
     * @return
     */
    @GetMapping("/list")
    public AjaxResult list() {
        List<SysMenu> menus = menuService.queryMenuList(SecurityUtil.getLoginUser().getUserId());
        return success(menus);
    }

}
