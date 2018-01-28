package com.tstyle.handler;

import com.tstyle.client.ProtocolHandlerFactory;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author weichanghuan
 */
public class ProcessorServiceImpl implements ProcessorService {

    @Autowired
    public ProtocolHandlerFactory protocolHandlerFactory;

    @Value("${invest.coopMerchantId}")
    public String coopMerchantId;

    @Override
    public <K, V, T> T execute(Map<K, V> param) {
        // TODO Auto-generated method stub
        return null;
    }

}
