package com.aurora.logger.core.aspect;

import com.aurora.logger.core.annotation.AopLogger;
import com.aurora.logger.core.service.ILoggerService;
import com.aurora.logger.core.util.RequestUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LoggerAspectj {

    @Autowired
    private ILoggerService loggerService;

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.aurora.logger.core.annotation.AopLogger)")
    public void pointCut(){}


    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 获取登录用户
        String operator = loggerService.getPrincipal();
        // 获取注解
        AopLogger logger = this.getAnnotation(pjp);
        String desc = logger.desc();
        String type = logger.type().getType();
        String uri = this.getURI();
        String method = pjp.getSignature().getName();
        // 执行程序
        Date visitTime = new Date();
        long startTime = visitTime.getTime();
        Object result = pjp.proceed();
        long costTime = System.currentTimeMillis() - startTime;
        // 处理日志信息
        loggerService.handleLog(desc, type, method, uri, visitTime, costTime);
        // 返回解析后的结果
        return result;
    }

    /**
     * 获取API的URI地址
     * @return
     */
    private String getURI() {
        HttpServletRequest request = RequestUtil.getRequest();
        return request!=null ? request.getRequestURI():"";
    }

    /**
     * 获取注解
     * @param point
     * @return
     */
    private AopLogger getAnnotation(JoinPoint point) {
        Signature signature = point.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            return method!=null ? method.getAnnotation(AopLogger.class) : null;
        }
        return null;
    }
}
