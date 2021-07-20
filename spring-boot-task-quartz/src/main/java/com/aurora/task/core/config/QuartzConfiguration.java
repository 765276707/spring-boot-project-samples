package com.aurora.task.core.config;

import com.aurora.task.core.provider.IJobDetailsProvider;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;

/**
 * 定时任务配置
 * @author xzbcode
 */
@Configuration
public abstract class QuartzConfiguration {

    @Resource
    private Scheduler scheduler;

    @Bean
    public JobInitializeRunner jobInitializeRunner() {
        return new JobInitializeRunner(scheduler, jobDetailsProvider());
    }

    public abstract IJobDetailsProvider jobDetailsProvider();

}
