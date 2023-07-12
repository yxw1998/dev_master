package cn.dev.common.annotation;

import java.lang.annotation.*;

/**
 * @author YangXw
 * @date 2023/01/17 21:36
 * @description 自定义操作日志记录注解
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     */
    public String module() default "";

    /**
     * 功能
     */
    public String business() default "";

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    public boolean isSaveResponseData() default true;
}
