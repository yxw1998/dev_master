package cn.dev.system.service;

import cn.dev.system.domain.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author YangXw
 * @date 2023/03/05 15:25
 * @description
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> queryMenuList(Long userId);
}
