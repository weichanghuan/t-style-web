package com.tstyle.handler.process;

import com.tstyle.enums.web.ActionTypeEnum;
import com.tstyle.handler.ProcessorServiceImpl;
import com.tstyle.handler.annotation.ProcessorServiceAnnotation;
import com.tstyle.utils.HandlerServiceUtil;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
@ProcessorServiceAnnotation(ActionTypeEnum.QUERY_ROLELIST)
public class QueryRoleListProcess extends ProcessorServiceImpl {

    @Override
    public <K, V, T> T execute(Map<K, V> param) {

        // 将前端参数封装到对象请求参数实体
        rep = HandlerServiceUtil.process(protocolHandlerFactory, req);
    }

}
