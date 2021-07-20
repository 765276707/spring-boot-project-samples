package com.aurora.task.core.config;

import com.aurora.task.core.model.JobDetails;
import com.aurora.task.core.provider.IJobDetailsProvider;
import com.aurora.task.core.util.ScheduleUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.boot.CommandLineRunner;
import java.util.List;

/**
 * 调度器执行初始化
 * @author xzbcode
 */
@Slf4j
public class JobInitializeRunner implements CommandLineRunner {

    private final Scheduler scheduler;
    private final IJobDetailsProvider jobDetailsProvider;

    public JobInitializeRunner(Scheduler scheduler, IJobDetailsProvider jobDetailsProvider) {
        this.scheduler = scheduler;
        this.jobDetailsProvider = jobDetailsProvider;
    }

    @Override
    public void run(String... args) throws Exception {
        // 从数据源中加载所有任务
        List<JobDetails> jobs = jobDetailsProvider.loadJobs();

        // 遍历启动每个任务
        for (JobDetails jobDetails : jobs) {
            ScheduleUtil.createJob(jobDetails, scheduler);
            if (jobDetails.getIsEnabled()) {
                log.info("running scheduled task: {} ", jobDetails.getJobName());
            }
        }
    }

}
