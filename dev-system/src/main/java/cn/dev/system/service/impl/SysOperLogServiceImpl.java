package cn.dev.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dev.system.domain.entity.SysOperLog;
import cn.dev.system.service.SysOperLogService;
import cn.dev.system.mapper.SysOperLogMapper;
import org.springframework.stereotype.Service;

/**
* @author YangXw
* @description 针对表【sys_oper_log(操作日志记录)】的数据库操作Service实现
* @createDate 2023-01-27 15:54:26
*/
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog>
    implements SysOperLogService{

}




