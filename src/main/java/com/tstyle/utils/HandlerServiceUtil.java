package com.tstyle.utils;

import com.tstyle.client.ProtocolHandlerFactory;
import com.tstyle.enums.ReturnCodeEnum;
import com.tstyle.handler.common.BaseRequest;
import com.tstyle.handler.common.BaseResponse;

/**
 * @description:请求工具类
 * @author weichanghuan
 * @date 2017年5月17日 下午6:05:28
 */
public class HandlerServiceUtil {

    /**
     * 
     * sendRequest:接口请求处理请求
     * 
     * @param request
     * @return
     * @author weichanghuan
     */
    public static <T> T process(ProtocolHandlerFactory protocolHandlerFactory, BaseRequest request) {
        return protocolHandlerFactory.process(request);
    }

    /**
     * 
     * isSuccess:判断是否成功
     * 
     * @param response
     * @return
     * @author weichanghuan
     */
    public static boolean isSuccess(BaseResponse response) {
        if (ReturnCodeEnum.SUCCESS.getCode().equals(response.getReturnCode())) {
            return true;
        } else {
            return false;
        }
    }

}
