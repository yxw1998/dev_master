package cn.dev.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 操作日志记录
 * @TableName sys_oper_log
 */
@TableName(value ="sys_oper_log")
@Data
public class SysOperLog implements Serializable {
    /**
     * 日志主键
     */
    @TableId(type = IdType.AUTO)
    private Long operId;

    /**
     * 模块
     */
    private String moudule;

    /**
     * 业务描述
     */
    private String business;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 主机地址
     */
    private String ip;

    /**
     * 操作地点
     */
    private String location;

    /**
     * 请求参数
     */
    private String reqParam;

    /**
     * 返回参数
     */
    private String resParam;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    private Date operationTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}