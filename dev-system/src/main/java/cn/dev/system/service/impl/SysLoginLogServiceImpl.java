package cn.dev.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dev.system.domain.entity.SysLoginLog;
import cn.dev.system.service.SysLoginLogService;
import cn.dev.system.mapper.SysLoginLogMapper;
import org.springframework.stereotype.Service;

/**
* @author YangXw
* @description 针对表【sys_login_log(系统访问日志)】的数据库操作Service实现
* @createDate 2023-01-28 17:44:20
*/
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog>
    implements SysLoginLogService{

}




