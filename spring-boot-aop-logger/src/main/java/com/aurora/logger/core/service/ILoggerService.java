package com.aurora.logger.core.service;

import java.util.Date;

public interface ILoggerService {

    /**
     * 获取当前登录的用户名
     * @return
     */
    String getPrincipal();

    /**
     * 处理操作日志
     * @param operateDesc 操作描述
     * @param operateType 操作类型
     * @param method 操作的方法
     * @param uri 操作的URI
     * @param visitTime 操作时间
     * @param costTime 耗时，单位：毫秒
     */
    void handleLog(String operateDesc, String operateType, String method, String uri, Date visitTime, long costTime);

}
