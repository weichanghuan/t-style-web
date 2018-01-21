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

package com.tstyle.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:会话工具类
 * @author weichanghuan
 */
public class ThreadLocalSessionUtil {

    private static ThreadLocal<HttpServletRequest> local = new ThreadLocal<HttpServletRequest>();

    public static HttpServletRequest getRequest() {
        return local.get();
    }

    public static void setRequest(HttpServletRequest request) {
        local.set(request);
    }

    /**
     * getSessionValue:获取Session变量
     * 
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionValue(String key) {
        if (local.get() == null || local.get().getSession() == null || local.get().getSession().getAttribute(key) == null) {
            return null;
        }
        return (T) local.get().getSession().getAttribute(key);
    }

    /**
     * 
     * setSessionValue:设置Session变量
     * 
     * @param key
     * @param value
     */
    public static void setSessionValue(String key, Object value) {
        local.get().getSession().setAttribute(key, value);
    }

    /**
     * 
     * clearSession:清理Session
     * 
     */
    public static void clearSession() {
        local.get().getSession().invalidate();
    }

}
