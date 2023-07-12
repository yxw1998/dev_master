package cn.dev.framework.aspect;

import cn.dev.common.annotation.Log;
import cn.dev.common.config.DevConfig;
import cn.dev.common.constant.OperationStatus;
import cn.dev.common.util.IpUtil;
import cn.dev.common.util.ServletUtil;
import cn.dev.common.util.StringUtil;
import cn.dev.framework.filter.PropertyPreExcludeFilter;
import cn.dev.framework.manager.AsyncFactory;
import cn.dev.framework.manager.AsyncManager;
import cn.dev.framework.security.domain.LoginUser;
import cn.dev.framework.security.util.SecurityUtil;
import cn.dev.system.domain.entity.SysOperLog;
import com.alibaba.fastjson2.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author YangXw
 * @date 2023/01/18 21:37
 * @description 日志记录切面
 */
@Component
@Aspect
public class LogAspect {

    public static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 忽略敏感字段
     */
    public static final String[] EXCLUDE_PROPERTIES = {"password"};

    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    protected void handleLog(JoinPoint joinPoint, Log controllerLog, Exception e, Object jsonResult) {
        try {
            // 获取当前用户
            LoginUser loginUser = SecurityUtil.getLoginUser();

            // 创建数据库日志对象
            SysOperLog sysOperLog = new SysOperLog();
            sysOperLog.setMoudule(controllerLog.module());
            sysOperLog.setBusiness(controllerLog.business());

            // 请求地址
            String ip = IpUtil.getIp(ServletUtil.getRequest());
            sysOperLog.setIp(ip);
            sysOperLog.setUrl(StringUtil.substring(ServletUtil.getRequest().getRequestURI(), 0, 255));

            // 是否开启获取location选项
            if (DevConfig.isAddressEnabled()) {
                // 获取IP真实地址
                String location = IpUtil.getLocation(ip);
                sysOperLog.setLocation(location);
            }

            if (loginUser != null) {
                // 操作人
                sysOperLog.setOperator(loginUser.getUsername());
            }

            if (e != null) {
                // 异常信息
                sysOperLog.setStatus(OperationStatus.FAIL);
                sysOperLog.setErrorMsg(StringUtil.substring(e.getMessage(), 0, 2000));
            }

            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();

            sysOperLog.setMethod(className + "." + methodName);

            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, sysOperLog, jsonResult);
            // 操作时间
            sysOperLog.setOperationTime(new Date());
            //保存数据库
            AsyncManager.instance().execute(AsyncFactory.recordOperationLog(sysOperLog));
        } catch (Exception exp) {
            // 记录本地异常
            logger.error("日志处理发生异常 {}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息，用于Controller层注解
     *
     * @param joinPoint
     * @param controllerLog
     * @param sysLog        操作日志持久化对象
     * @param jsonResult
     */
    private void getControllerMethodDescription(JoinPoint joinPoint, Log controllerLog, SysOperLog sysLog, Object jsonResult) {

        logger.info(controllerLog.module() + " -- " + controllerLog.business());

        // 是否需要保存request，参数和值
        if (controllerLog.isSaveRequestData()) {
            // 获取参数信息，保存到数据库中
            setRequestData(joinPoint, sysLog);
        }

        // 是否需要保存response，参数和值
        if (controllerLog.isSaveResponseData()) {
            // 获取返回信息，保存到数据库中
            String resParam = JSON.toJSONString(jsonResult);
            logger.info("返回参数: {}", resParam);
            sysLog.setResParam(StringUtil.substring(resParam, 0, 2000));
        }
    }

    private void setRequestData(JoinPoint joinPoint, SysOperLog sysOperLog) {
        String params = argsArrayToString(joinPoint.getArgs());
        // 设置dbLog的params值
        logger.info("请求参数: {}", params);
        sysOperLog.setReqParam(StringUtil.substring(params, 0, 2000));
    }

    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (Objects.nonNull(o) && !isFilterObject(o)) {
                    try {
                        String jsonObj = JSON.toJSONString(o, excludePropertyPreFilter());
                        params += jsonObj + " ";
                    } catch (Exception e) {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }

    /**
     * 忽略敏感属性
     */
    public PropertyPreExcludeFilter excludePropertyPreFilter() {
        return new PropertyPreExcludeFilter().addExcludes(EXCLUDE_PROPERTIES);
    }

}
