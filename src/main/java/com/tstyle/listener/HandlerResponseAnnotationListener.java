package com.tstyle.listener;

import com.tstyle.TStyleWebApplication;
import com.tstyle.handler.annotation.HandlerResponseAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @description:接口响应类注解统一处理
 */
@Component
public class HandlerResponseAnnotationListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(HandlerResponseAnnotationListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        logger.debug("------初始化执行----");
        try {
            // 获取上下文
            ApplicationContext context = event.getApplicationContext();
            // 获取所有beanNames
            String[] beanNames = context.getBeanNamesForType(Object.class);
            for (String beanName : beanNames) {

                HandlerResponseAnnotation handlerResponse = context.findAnnotationOnBean(beanName, HandlerResponseAnnotation.class);
                // 判断该类是否含有HandlerResponseAnnotation注解
                if (handlerResponse != null) {
                    TStyleWebApplication.handlerResponseMap.put(handlerResponse.value().getValue(), context.getBean(beanName).getClass());
                }
            }
            logger.info("spring boot application startup success");
        } catch (Exception e) {
            logger.error("接口响应类注解统一处理发生异常", e);
        }

    }

}
