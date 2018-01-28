package com.tstyle.handler.annotation;

import com.tstyle.enums.ServiceCodeEnum;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:接口处理服务类注解声明
 * @author weichanghuan
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerServiceAnnotation {
    ServiceCodeEnum value();
}
