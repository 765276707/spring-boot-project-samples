package com.aurora.task.core.provider;

import com.aurora.task.core.model.JobDetails;
import java.util.List;

/**
 * 加载所有的定时任务
 * @author xzbcode
 */
public interface IJobDetailsProvider {

    // 定时任务集合
    List<JobDetails> loadJobs();

}
