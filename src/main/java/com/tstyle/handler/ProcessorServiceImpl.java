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
