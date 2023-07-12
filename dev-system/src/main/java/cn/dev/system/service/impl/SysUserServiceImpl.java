package cn.dev.system.service.impl;

import cn.dev.system.domain.VO.UserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dev.system.domain.entity.SysUser;
import cn.dev.system.service.SysUserService;
import cn.dev.system.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author YangXw
 * @description 针对表【sys_user(用户信息表)】的数据库操作Service实现
 * @createDate 2023-01-25 23:02:14
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {

    private SysUserMapper userMapper;

    public SysUserServiceImpl(SysUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public IPage<SysUser> queryUserList(SysUser user, Integer current, Integer size) {
        IPage<SysUser> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        page.setRecords(userMapper.queryUserList(page, user));
        return page;
    }

    @Override
    public UserVO getUserDetails(String userId) {
        return userMapper.getUserDetails(userId);
    }
}




