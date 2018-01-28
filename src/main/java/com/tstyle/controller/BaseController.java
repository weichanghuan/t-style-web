package com.tstyle.controller;

import com.tstyle.TStyleWebApplication;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author weichanghuan
 * @description:统一请求控制中心
 * @date 2017年5月24日 下午2:35:38
 * @since JDK 1.6
 */
@Controller
public class BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * ajaxToDoBusiness:ajax请求. <br/>
     * 所有后缀带ajax的请求入口.<br/>
     *
     * @param actionType
     *            请求路径
     * @param param
     *            参数
     * @param request
     *            请求参数
     * @return java.lang.String
     * @author weichanghuan
     * @date 2017/8/25 18:43
     */
    // @RequestBody接收的是一个Json对象，一直在调试代码都没有成功，后来发现，其实
    // @RequestBody接收的是一个Json对象的字符串，
    // 而不是一个Json对象。然而在ajax请求往往传的都是Json对象，后来发现用
    // JSON.stringify(data)的方式就能将对象变成字符串。
    // 同时ajax请求的时候也要指定dataType: "json",contentType:"application/json"
    // 这样就可以轻易的将一个对象或者List传到Java端，使用@RequestBody即可绑定对象或者List.
    @RequestMapping(path = "/{actionType}_ajax.htm")
    @ResponseBody
    public <K, V> String ajaxToDoBusiness(@PathVariable String actionType, @RequestBody LinkedHashMap<K, V> param, HttpServletRequest request) {
        // TODO对参数进行验证
        logger.debug("进入基础控制器1");
        return TStyleWebApplication.processorServiceMap.get(actionType).execute(param);
    }

    /**
     * urlToDoBusiness:url发送的get请求. <br/>
     * 非后缀ajax的所有请求入口.<br/>
     *
     * @param actionType
     *            请求路径
     * @param request
     *            请求参数
     * @return org.springframework.web.servlet.ModelAndView
     * @author weichanghuan
     * @date 2017/8/25 18:50
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{actionType}.htm")
    public ModelAndView urlToDoBusiness(@PathVariable String actionType, HttpServletRequest request, HttpServletResponse response) {
        // TODO对参数进行验证
        return (ModelAndView) TStyleWebApplication.processorServiceMap.get(actionType).execute(request.getParameterMap());
    }

}
