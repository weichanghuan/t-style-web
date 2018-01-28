package com.tstyle.listener;

import com.tstyle.client.ProtocolHandlerFactory;
import com.tstyle.handler.annotation.HandlerServiceAnnotation;
import com.tstyle.handler.common.HandlerService;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @description:接口服务类注解统一处理
 */
@Component
public class NativeHandlerServiceAnnotationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${protocol.type}")
    private String protocolType;

    private static final Logger logger = LoggerFactory.getLogger(NativeHandlerServiceAnnotationListener.class);

    /**
     * 接口处理服务类集合
     */
    public final static ConcurrentHashMap<String, HandlerService> handlerServiceMap = new ConcurrentHashMap<String, HandlerService>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (ProtocolHandlerFactory.PROTOCOL_TYPE_NATIVE.equals(protocolType)) {
            logger.debug("------初始化执行----");
            try {
                // 获取上下文
                ApplicationContext context = event.getApplicationContext();
                // 获取所有beanNames
                String[] beanNames = context.getBeanNamesForType(Object.class);
                for (String beanName : beanNames) {

                    HandlerServiceAnnotation handlerService = context.findAnnotationOnBean(beanName, HandlerServiceAnnotation.class);
                    // 判断该类是否含有HandlerServiceAnnotation注解
                    if (handlerService != null) {
                        handlerServiceMap.put(handlerService.value().getValue(), (HandlerService) context.getBean(beanName));
                    }
                }
            } catch (Exception e) {
                logger.error("接口服务类注解统一处理发生异常", e);
            }
        }

    }

}
