package com.aurora.task.samples.quartz;

import com.aurora.task.core.model.JobDetails;
import com.aurora.task.core.provider.IJobDetailsProvider;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务提供者实现
 * @author xzbcode
 */
public class MyJobDetailsProvider implements IJobDetailsProvider {

    @Override
    public List<JobDetails> loadJobs() {
        List<JobDetails> jobDetails = new ArrayList<>();
        jobDetails.add(createJob());
        return jobDetails;
    }

    /**
     * 模拟创建一个定时任务
     * @return
     */
    private JobDetails createJob() {
        JobDetails myJobDetails = new JobDetails();
        myJobDetails.setId(1L);
        myJobDetails.setJobName("test_job");
        myJobDetails.setJobGroup("test_group");
        myJobDetails.setJobCron("0/5 * * * * ?");
        myJobDetails.setInvokedClassPath("com.aurora.task.samples.quartz.TestJob");
        myJobDetails.setIsEnabled(true);
        return myJobDetails;
    }
}
