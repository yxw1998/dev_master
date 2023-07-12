package cn.dev.system.service;

import cn.dev.system.domain.VO.UserVO;
import cn.dev.system.domain.entity.SysUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author YangXw
* @description 针对表【sys_user(用户信息表)】的数据库操作Service
* @createDate 2023-01-25 23:02:14
*/
public interface SysUserService extends IService<SysUser> {

    /**
     * 查询用户列表
     * @param user 查询对象
     * @param current  当前页
     * @param size 规模
     * @return
     */
    IPage<SysUser> queryUserList(SysUser user, Integer current, Integer size);

    /**
     * 获取用户详情
     * @param userId
     * @return
     */
    UserVO getUserDetails(String userId);
}
