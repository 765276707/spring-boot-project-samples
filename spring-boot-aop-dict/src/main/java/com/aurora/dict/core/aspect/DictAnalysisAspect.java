package com.aurora.dict.core.aspect;

import com.aurora.dict.core.datasource.IDictDataSource;
import com.aurora.dict.core.entry.DictEntry;
import com.aurora.dict.core.parser.DictParser;
import com.aurora.dict.samples.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Collection;

/**
 * 字典翻译切面
 * @author xzbcode
 */
@Slf4j
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DictAnalysisAspect {

    @Autowired
    private IDictDataSource dictDataSource;
    @Autowired
    private DictParser dictParser;

    // 指定切入点 @ResponseBody
    // "" +
    @Pointcut("@annotation(org.springframework.web.bind.annotation.ResponseBody)" +
            " || @within(org.springframework.web.bind.annotation.RestController)")
    public void pointCut(){}


    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 执行程序
        Object result = pjp.proceed();
        // 获取数据字典
        DictEntry dictEntry = dictDataSource.getDictSource();
        // 获取需要解析的对象
        if (dictEntry!=null && result!=null) {
            if (result instanceof Collection<?>) {
                Collection collection = (Collection) result;
                // 集合类型：List、Set
                for (Object obj : collection) {
                    dictParser.parseDictText(obj, dictEntry);
                }
            }
            else {
                // 对象类型
                dictParser.parseDictText(result, dictEntry);
            }
        }
        // 返回解析后的结果
        return result;
    }

}
