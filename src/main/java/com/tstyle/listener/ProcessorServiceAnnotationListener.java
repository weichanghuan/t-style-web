/**
 *Copyright (c) 2017, ShangHai HOWBUY INVESTMENT MANAGEMENT Co., Ltd.
 *All right reserved.
 *
 *THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF HOWBUY INVESTMENT
 *MANAGEMENT CO., LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED
 *TO THIRD PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *WITHOUT THE PRIOR WRITTEN PERMISSION OF HOWBUY INVESTMENT MANAGEMENT
 * CO., LTD.
*/

package com.tstyle.listener;

import com.tstyle.TStyleWebApplication;
import com.tstyle.handler.ProcessorService;
import com.tstyle.handler.annotation.ProcessorServiceAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 
 * @description:统一处理监听
 * @author weichanghuan
 */
@Component
public class ProcessorServiceAnnotationListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ProcessorServiceAnnotationListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        logger.debug("------初始化执行----");
        try {
            // 获取上下文
            ApplicationContext context = event.getApplicationContext();
            // 获取所有beanNames
            String[] beanNames = context.getBeanNamesForType(Object.class);
            for (String beanName : beanNames) {

                ProcessorServiceAnnotation processorService = context.findAnnotationOnBean(beanName, ProcessorServiceAnnotation.class);
                // 判断该类是否含有ProcessorServiceAnnotation注解
                if (processorService != null) {
                    TStyleWebApplication.processorServiceMap.put(processorService.value().getValue(), (ProcessorService) context.getBean(beanName));
                }
            }
        } catch (Exception e) {
            logger.error("接口服务类注解统一处理发生异常", e);
        }

    }

}
