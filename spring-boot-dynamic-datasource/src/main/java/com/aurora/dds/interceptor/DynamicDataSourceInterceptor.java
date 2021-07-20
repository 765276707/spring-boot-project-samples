package com.aurora.dds.interceptor;

import com.aurora.dds.datasource.DataSourceHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 动态数据源拦截器
 * @author xzbcode
 */
public class DynamicDataSourceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 模拟获取数据源标识，正式项目可以存放在JWT信息中
        String tenantCode = request.getHeader("TenantCode");
        // 设置当前线程的租户
        DataSourceHolder.set(tenantCode);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String tenantCode = request.getHeader("TenantCode");
        // 完成请求，清除租户信息
        DataSourceHolder.clear();
    }
}
