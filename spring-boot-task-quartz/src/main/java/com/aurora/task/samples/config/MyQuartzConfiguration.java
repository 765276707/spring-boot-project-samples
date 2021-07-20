package com.aurora.task.samples.config;

import com.aurora.task.core.config.QuartzConfiguration;
import com.aurora.task.core.provider.IJobDetailsProvider;
import com.aurora.task.samples.quartz.MyJobDetailsProvider;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyQuartzConfiguration extends QuartzConfiguration {

    /**
     * 提供任务类
     * @return
     */
    @Override
    public IJobDetailsProvider jobDetailsProvider() {
        return new MyJobDetailsProvider();
    }

}
