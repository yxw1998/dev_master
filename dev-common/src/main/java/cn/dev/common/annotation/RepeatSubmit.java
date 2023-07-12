package cn.dev.common.annotation;

import java.lang.annotation.*;

/**
 * @author YangXw
 * @date 2023/01/17 22:22
 * @description 自定义注解防止表单重复提交
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {

    /**
     * 间隔时间（ms），小于此时间视为重复提交
     */
    public int interval() default 5000;
}
