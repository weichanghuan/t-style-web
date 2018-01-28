package com.tstyle.handler;

import java.util.Map;

/**
 * @description:业务功能实现接口
 * @author weichanghuan
 */
public interface ProcessorService {
    <K, V, T> T execute(Map<K, V> param);
}
