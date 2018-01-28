package com.tstyle.handler.annotation;

import com.tstyle.enums.web.ActionTypeEnum;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author weichanghuan
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessorServiceAnnotation {
    ActionTypeEnum value();
}
