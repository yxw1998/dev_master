package cn.dev.system.mapper;

import cn.dev.system.domain.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author YangXw
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2023-03-05 15:33:00
* @Entity cn.dev.system.domain.entity.SysMenu
*/
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}




