package cn.dev.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系统访问日志
 * @author YangXw
 * @TableName sys_login_log
 */
@TableName(value ="sys_login_log")
@Data
public class SysLoginLog implements Serializable {
    /**
     * 访问ID
     */
    @TableId(type = IdType.AUTO)
    private Long loginId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 登录IP地址
     */
    private String ip;

    /**
     * 登录地点
     */
    private String location;

    /**
     * 登录状态（0成功 1失败）
     */
    private String status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 访问时间
     */
    private Date loginTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}