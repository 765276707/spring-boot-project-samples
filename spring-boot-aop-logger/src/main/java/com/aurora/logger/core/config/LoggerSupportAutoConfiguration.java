package com.aurora.logger.core.config;

import com.aurora.logger.core.aspect.LoggerAspectj;
import com.aurora.logger.core.service.ILoggerService;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import java.util.Date;

public class LoggerSupportAutoConfiguration {

    private org.slf4j.Logger log = LoggerFactory.getLogger(LoggerAspectj.class);

    @Bean
    public LoggerAspectj loggerAspectj() {
        return new LoggerAspectj();
    }

    @Bean
    @ConditionalOnMissingBean(ILoggerService.class)
    public ILoggerService loggerService() {
        return new ILoggerService() {
            @Override
            public String getPrincipal() {
                return null;
            }

            @Override
            public void handleLog(String operateDesc, String operateType, String method, String uri, Date visitTime, long costTime) {
                // 此处模拟只做日志打印
                if (log.isInfoEnabled()) {
                    log.info("current operate log ==> desc: {}, type: {}, method:{}, uri: {}, visitTime: {}," +
                            " costTime: {}", operateDesc, operateType, method, uri, visitTime, costTime);
                }
            }
        };
    }

}
