package cn.dev.system.service.impl;

import cn.dev.system.domain.entity.SysUser;
import cn.dev.system.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dev.system.domain.entity.SysMenu;
import cn.dev.system.mapper.SysMenuMapper;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YangXw
 * @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
 * @createDate 2023-03-05 15:33:00
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
        implements ISysMenuService {

    private final SysMenuMapper menuMapper;

    public SysMenuServiceImpl(SysMenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public List<SysMenu> queryMenuList(Long userId) {

        List<SysMenu> menuList = new ArrayList<>();
        // 管理员显示所有菜单
        if (SysUser.isAdmin(userId)) {
            menuList = new LambdaQueryChainWrapper<>(menuMapper).list();
        }

        SysMenu zeroMenu = getMenuTree(menuList);
        return zeroMenu.getChildren();
    }

    private SysMenu getMenuTree(List<SysMenu> menuList) {
        Map<Long, List<SysMenu>> childMap = new HashMap<>(16);

        for (SysMenu sysMenu : menuList) {
            Long parentId = sysMenu.getParentId();
            if (childMap.containsKey(parentId)) {
                childMap.get(parentId).add(sysMenu);
            } else {
                List<SysMenu> childList = new ArrayList<>();
                childList.add(sysMenu);
                childMap.put(parentId, childList);
            }
        }

        SysMenu zeroMenu = new SysMenu();
        zeroMenu.setMenuId(0L);
        setMenuChild(zeroMenu,childMap);
        return zeroMenu;
    }

    private void setMenuChild(SysMenu zeroMenu, Map<Long, List<SysMenu>> childMap) {
        Long menuId = zeroMenu.getMenuId();
        if (!childMap.containsKey(menuId)){
            return;
        }
        List<SysMenu> children = childMap.get(menuId);
        for (SysMenu child : children) {
            setMenuChild(child,childMap);
        }
        zeroMenu.setChildren(children);
    }
}




