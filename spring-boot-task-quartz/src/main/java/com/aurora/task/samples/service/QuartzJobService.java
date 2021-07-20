package com.aurora.task.samples.service;

import com.aurora.task.core.model.JobDetails;
import org.quartz.SchedulerException;

/**
 * 定时任务业务接口
 * @author xzbcode
 */
public interface QuartzJobService {

    /**
     * 新增定时任务
     * @param jobDetails 任务参数
     * @return
     */
    boolean saveJob(JobDetails jobDetails) throws ClassNotFoundException, SchedulerException;

    /**
     * 更新定时任务
     * @param jobDetails 任务参数
     * @return
     */
    boolean updateJob(JobDetails jobDetails) throws ClassNotFoundException, SchedulerException;

    /**
     * 删除定时任务
     * @param jobDetails 任务参数
     * @return
     * @throws SchedulerException
     */
    boolean deleteJob(JobDetails jobDetails) throws SchedulerException;

    /**
     * 更改任务运行状态
     * @param jobDetails 任务参数
     * @return
     * @throws SchedulerException
     */
    boolean changeRunStatus(JobDetails jobDetails) throws SchedulerException;

    /**
     * 立即执行一次任务
     * @param jobDetails 任务参数
     * @return
     * @throws SchedulerException
     */
    boolean runOnceImmediately(JobDetails jobDetails) throws SchedulerException;

}
