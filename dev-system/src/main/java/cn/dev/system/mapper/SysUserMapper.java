package cn.dev.system.mapper;

import cn.dev.system.domain.VO.UserVO;
import cn.dev.system.domain.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YangXw
 * @description 针对表【sys_user(用户信息表)】的数据库操作Mapper
 * @createDate 2023-01-25 23:02:14
 * @Entity cn.dev.system.domain.entity.SysUser
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询用户列表
     *
     * @param page 分页
     * @param user
     * @return
     */
    List<SysUser> queryUserList(IPage<SysUser> page, @Param("user") SysUser user);

    /**
     * 获取用户详情
     * @param userId
     * @return
     */
    UserVO getUserDetails(@Param("userId") String userId);
}




