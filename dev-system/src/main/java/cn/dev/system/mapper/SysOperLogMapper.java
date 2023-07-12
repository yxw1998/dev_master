package cn.dev.system.mapper;

import cn.dev.system.domain.entity.SysOperLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author YangXw
* @description 针对表【sys_oper_log(操作日志记录)】的数据库操作Mapper
* @createDate 2023-01-27 15:54:26
* @Entity cn.dev.system.domain.entity.SysOperLog
*/
@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

}




