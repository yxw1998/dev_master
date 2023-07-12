package cn.dev.framework.handler;

import cn.dev.common.core.domain.AjaxResult;
import cn.dev.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author ruoyi
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e) {
        e.printStackTrace();
        log.error("系统内部发生异常，异常原因: {}", e.getMessage());
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return AjaxResult.forbidden("没有权限，请联系管理员授权");
    }

//    /**
//     * 拦截未知的运行时异常
//     */
//    @ExceptionHandler(RuntimeException.class)
//    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request)
//    {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}',发生未知异常.", requestURI, e);
//        return AjaxResult.error(e.getMessage());
//    }
//
//    /**
//     * 系统异常
//     */
//    @ExceptionHandler(Exception.class)
//    public AjaxResult handleException(Exception e, HttpServletRequest request)
//    {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}',发生系统异常.", requestURI, e);
//        return AjaxResult.error(e.getMessage());
//    }
}
