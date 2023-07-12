package cn.dev.system.mapper;

import cn.dev.system.domain.entity.SysLoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author YangXw
* @description 针对表【sys_login_log(系统访问日志)】的数据库操作Mapper
* @createDate 2023-01-28 17:44:20
* @Entity cn.dev.system.domain.entity.SysLoginLog
*/
@Mapper
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {

}




