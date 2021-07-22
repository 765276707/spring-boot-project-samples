package com.aurora.configurations.costom;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步任务线程池配置
 * @author xzbcode
 */
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    /**
     * 创建线程池执行器
     * AbortPolicy 丢弃任务并抛出RejectedExecutionException异常(默认)。
     * DiscardPolic 丢弃任务，但是不抛出异常。
     * DiscardOldestPolicy 丢弃队列最前面的任务，然后重新尝试执行任务
     * CallerRunsPolic 由调用线程处理该任务
     * @return
     */
    private ThreadPoolTaskExecutor createExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Thread-Async-");
        executor.setKeepAliveSeconds(300);
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(6);
        executor.setQueueCapacity(200);
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }


    @Override
    public Executor getAsyncExecutor() {
        return createExecutor();
    }

}
