package cn.dev.framework.manager;

import cn.dev.common.util.IpUtil;
import cn.dev.common.util.ServletUtil;
import cn.dev.common.util.StringUtil;
import cn.dev.common.util.spring.SpringUtil;
import cn.dev.framework.security.util.SecurityUtil;
import cn.dev.system.domain.entity.SysLoginLog;
import cn.dev.system.domain.entity.SysOperLog;
import cn.dev.system.service.SysLoginLogService;
import cn.dev.system.service.SysOperLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.TimerTask;

/**
 * @author YangXw
 * @date 2023/01/27 14:23
 * @description 异步工厂（产生任务用）
 */
public class AsyncFactory {

    private static final Logger log = LoggerFactory.getLogger(AsyncFactory.class);

    /**
     * 记录操作日志
     *
     * @return
     */
    public static TimerTask recordOperationLog(SysOperLog sysOperLog) {

        return new TimerTask() {
            @Override
            public void run() {
                try {
                    SpringUtil.getBean(SysOperLogService.class).save(sysOperLog);
                } catch (Exception e) {
                    log.error("记录操作日志发生错误，错误原因: {}", e.getMessage());
                }
            }
        };
    }

    /**
     * 记录登录日志
     *
     * @param username 用户名
     * @param status   登录状态
     * @param errorMsg 错误消息
     * @return
     */
    public static TimerTask recordLoginLog(String username, String status, String errorMsg) {
        String ip = IpUtil.getIp(ServletUtil.getRequest());
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    SysLoginLog sysLoginLog = new SysLoginLog();
                    // 用户名
                    sysLoginLog.setUserName(username);
                    // IP
                    sysLoginLog.setIp(ip);
                    // 真实地址
                    sysLoginLog.setLocation(IpUtil.getLocation(ip));
                    // 登录状态
                    sysLoginLog.setStatus(status);
                    // 提示消息
                    if (StringUtils.hasText(errorMsg)) {
                        sysLoginLog.setErrorMsg(errorMsg);
                    }
                    // 登录时间
                    sysLoginLog.setLoginTime(new Date());
                    SpringUtil.getBean(SysLoginLogService.class).save(sysLoginLog);
                } catch (Exception e) {
                    log.error("记录登录日志发生错误，错误原因: {}", e.getMessage());
                }
            }
        };
    }

}
