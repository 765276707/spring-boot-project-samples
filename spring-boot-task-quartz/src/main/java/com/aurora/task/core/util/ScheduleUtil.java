package com.aurora.task.core.util;

import cn.hutool.core.date.DateUtil;
import com.aurora.task.core.exception.TaskException;
import com.aurora.task.core.model.JobDetails;
import org.quartz.*;
import java.util.Date;

/**
 * 调度器工具类
 * @author xzbcode
 */
public class ScheduleUtil {

    /**
     * 创建定时任务
     * @param scheduler 调度器
     * @throws SchedulerException
     */
    public static void createJob(JobDetails jobDetails, Scheduler scheduler)
                             {
        // 获取 JobKey / JobDataMap / JobClass
        JobKey key = JobUtil.getJobKey(jobDetails.getId(), jobDetails.getJobGroup());
        JobDataMap dataMap = JobUtil.convertJsonToDataMap(jobDetails.getDataMap());
        dataMap.put("jobId", jobDetails.getId());
        dataMap.put("jobName", jobDetails.getJobName());
        try {
            Class<? extends Job> jobClass = JobUtil.getClass(jobDetails.getInvokedClassPath());

            // 创建 JobDetail
            JobDetail jobDetail = JobUtil.getJobDetail(key, jobClass, dataMap);

            // 创建 Trigger
            Trigger trigger = null;
            if (!Boolean.TRUE.equals(jobDetails.getIsEnabled())) {
                // 不立即触发
                trigger = JobUtil.getTrigger(key, jobDetails.getJobCron(), dataMap, DateUtil.offsetMinute(new Date(), 60));
            } else {
                // 立即触发
                trigger = JobUtil.getTrigger(key, jobDetails.getJobCron(), dataMap, new Date());
            }

            // 判断是否已经有了
            boolean existJob = scheduler.checkExists(key);
            if (existJob) {
                scheduler.deleteJob(key);
            }

            // 绑定 scheduler
            scheduler.scheduleJob(jobDetail, trigger);

            // 判断 刚创建的任务是否需要暂停
            if (!Boolean.TRUE.equals(jobDetails.getIsEnabled())) {
                scheduler.pauseJob(key);
            }
        } catch (SchedulerException | ClassNotFoundException e) {
            throw new TaskException(e.getMessage());
        }
    }

    /**
     * 是否存在该任务
     * @param jobId
     * @param jobGroup
     * @param scheduler
     * @return
     */
    public static boolean existJob(Long jobId, String jobGroup, Scheduler scheduler) {
        try {
            JobKey key = JobUtil.getJobKey(jobId, jobGroup);
            return scheduler.checkExists(key);
        } catch (SchedulerException e) {
            throw new TaskException(e.getMessage());
        }
    }


    /**
     * 重启任务
     * @param scheduler 调度器
     * @throws SchedulerException
     */
    public static void resumeJob(Long jobId, String jobGroup, Scheduler scheduler) {
        try {
            JobKey key = JobUtil.getJobKey(jobId, jobGroup);
            scheduler.resumeJob(key);
        } catch (SchedulerException e) {
            throw new TaskException(e.getMessage());
        }
    }


    /**
     * 暂停任务
     * @param scheduler 调度器
     */
    public static void pauseJob(Long jobId, String jobGroup, Scheduler scheduler) {
        try {
            JobKey key = JobUtil.getJobKey(jobId, jobGroup);
            scheduler.pauseJob(key);
        } catch (SchedulerException e) {
            throw new TaskException(e.getMessage());
        }
    }


    /**
     * 删除任务
     * @param scheduler 调度器
     * @throws SchedulerException
     */
    public static boolean removeJob(Long jobId, String jobGroup, Scheduler scheduler) {
        try {
            JobKey key = JobUtil.getJobKey(jobId, jobGroup);
            return scheduler.deleteJob(key);
        } catch (SchedulerException e) {
            throw new TaskException(e.getMessage());
        }
    }

    /**
     * 立即执行一次
     * @param scheduler 调度器
     * @return
     */
    public static void triggerJob(Long jobId, String jobGroup, Scheduler scheduler) {
        try {
            JobKey key = JobUtil.getJobKey(jobId, jobGroup);
            scheduler.triggerJob(key);
        } catch (SchedulerException e) {
            throw new TaskException(e.getMessage());
        }
    }
}
