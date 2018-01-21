package com.tstyle;

import com.tstyle.handler.ProcessorService;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = "classpath:applicationContext.xml")
public class TStyleWebApplication {
    /**
     * 接口处理响应类集合
     */
    public final static ConcurrentHashMap<String, Class<?>> handlerResponseMap = new ConcurrentHashMap<String, Class<?>>();
    /**
     * 接口处理服务类集合
     */
    public final static ConcurrentHashMap<String, ProcessorService> processorServiceMap = new ConcurrentHashMap<String, ProcessorService>();

    public static void main(String[] args) {
        SpringApplication.run(TStyleWebApplication.class, args);
    }

}
