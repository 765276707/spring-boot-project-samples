package com.aurora.task.samples.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 模拟一个任务
 * @apiNote 这个任务只是为了模拟，仅供测试
 * @author xzbcode
 */
@Slf4j
public class TestJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("这是测试任务 test job ...... {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

}
