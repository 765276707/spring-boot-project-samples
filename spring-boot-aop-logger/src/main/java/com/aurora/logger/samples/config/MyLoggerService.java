package com.aurora.logger.samples.config;

import com.aurora.logger.core.service.ILoggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Date;

@Slf4j
@Component
public class MyLoggerService implements ILoggerService {

    @Override
    public String getPrincipal() {
        /**
         * 此处只是模拟登录用户名，实际项目需要正确获取，
         * 例如在Spring Security环境中用 SecurityContextHolder.getContext().getAuthentication()
         */
        return "user001";
    }

    @Override
    public void handleLog(String operateDesc, String operateType, String method, String uri, Date visitTime, long costTime) {
        // 此处模拟只做日志打印
        if (log.isInfoEnabled()) {
            log.info("current operate log ==> desc: {}, type: {}, method:{}, uri: {}, visitTime: {}," +
                    " costTime: {}", operateDesc, operateType, method, uri, visitTime, costTime);
        }
    }

}
