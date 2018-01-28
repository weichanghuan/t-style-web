package com.tstyle.handler.annotation;

import com.tstyle.enums.ServiceCodeEnum;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:接口响应类注解定义
 * @reason:TODO ADD REASON(可选)
 * @author jiong.peng
 * @date 2017年5月18日 下午4:20:00
 * @since JDK 1.6
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerResponseAnnotation {
    ServiceCodeEnum value();
}
