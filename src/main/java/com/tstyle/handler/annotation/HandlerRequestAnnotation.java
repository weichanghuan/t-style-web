package com.tstyle.handler.annotation;

import com.tstyle.enums.web.FunctionCodeEnum;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:功能请求参数类注解声明
 * @author weichanghuan
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerRequestAnnotation {
    FunctionCodeEnum value();
}
