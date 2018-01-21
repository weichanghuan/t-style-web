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

package com.tstyle.client;

import com.tstyle.TStyleWebApplication;
import com.tstyle.client.tio.TStyleClient;
import com.tstyle.constants.Constants;
import com.tstyle.enums.ReturnCodeEnum;
import com.tstyle.handler.common.BaseRequest;
import com.tstyle.handler.common.BaseResponse;
import com.tstyle.protocol.MessageTypeEnum;
import com.tstyle.protocol.packet.MessagePacket;
import com.tstyle.utils.JSONUtil;
import com.xiaoleilu.hutool.util.CharsetUtil;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProtocolHandlerFactory {

    private static final Logger logger = LoggerFactory.getLogger(ProtocolHandlerFactory.class);
    /**
     * socket协议
     */
    public final static String PROTOCOL_TYPE_SOCKET = "socket";

    /**
     * http协议TODO
     */
    public final static String PROTOCOL_TYPE_HTTP = "http";

    /**
     * nativeTODO
     */
    public final static String PROTOCOL_TYPE_NATIVE = "native";

    @Autowired
    private TStyleClient tStyleClient;

    // @Autowired
    // private TStyleHttpClient tStyleHttpClient;

    @Value("${protocol.type}")
    private String protocolType;

    @SuppressWarnings("unchecked")
    public <T> T process(BaseRequest request) {
        T resp = null;
        switch (protocolType) {
            case PROTOCOL_TYPE_SOCKET:
                try {
                    MessagePacket reqMessage = new MessagePacket();
                    String requestId = UUID.randomUUID().toString().replaceAll("-", "");
                    reqMessage.setRequestId(requestId);
                    reqMessage.setType(MessageTypeEnum.BUSSINESS_REQ.getType());
                    String jsonData = JSONUtil.toJSonString(request);
                    reqMessage.setContent(jsonData.getBytes(CharsetUtil.UTF_8));
                    logger.info("接口[{}]请求参数:{}", request.getServiceCode(), JSONUtil.toJSonString(request));
                    MessagePacket syncSendMessage = tStyleClient.syncSendMessage(reqMessage);
                    String respData = new String(syncSendMessage.getContent(), Constants.CHARSET);
                    logger.info("接口[{}]响应参数:{}", request.getServiceCode(), respData);
                    resp = (T) JSONUtil.toObject(respData, TStyleWebApplication.handlerResponseMap.get(request.getServiceCode()));
                    return resp;
                } catch (Exception e) {
                    // 请求处理发生异常，请求稍后再试或直接联系管理员
                    logger.error("请求处理发生异常", e);
                    return buildErrorResp(request, ReturnCodeEnum.SYSTEM_EXCEPTION.getCode(), "请求处理发生异常，请求稍后再试或直接联系管理员");
                }

            case PROTOCOL_TYPE_HTTP:

            case PROTOCOL_TYPE_NATIVE:

            default:
                // 暂不支持该协议类型
                logger.error("暂不支持该协议类型 :{}", protocolType);
                return buildErrorResp(request, ReturnCodeEnum.SYSTEM_EXCEPTION.getCode(), "请求处理发生异常，请求稍后再试或直接联系管理员");
        }

    }

    @SuppressWarnings("unchecked")
    private <T> T buildErrorResp(BaseRequest request, String code, String msg) {
        BaseResponse baseResp = new BaseResponse();
        baseResp.setRequestId(request.getRequestId());
        baseResp.setServiceCode(request.getServiceCode());
        baseResp.setReturnCode(code);
        baseResp.setReturnDesc(msg);
        return (T) JSONUtil.toObject(JSONUtil.toJSonString(baseResp), TStyleWebApplication.handlerResponseMap.get(request.getServiceCode()));
    }

}
