package com.aurora.task.samples.service.impl;

import com.aurora.task.core.exception.TaskException;
import com.aurora.task.core.model.JobDetails;
import com.aurora.task.core.util.ScheduleUtil;
import com.aurora.task.samples.service.QuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class QuartzJobServiceImpl implements QuartzJobService {

    @Autowired
    private Scheduler scheduler;


    @Override
    public boolean saveJob(JobDetails jobDetails) throws ClassNotFoundException, SchedulerException {
        // 新增数据库成功后，判断该任务状态
        ScheduleUtil.createJob(jobDetails, scheduler);
        return true;
    }

    @Override
    public boolean updateJob(JobDetails jobDetails) throws ClassNotFoundException, SchedulerException {
        // 更新数据库成功后，重新创建和替换定时任务
        ScheduleUtil.createJob(jobDetails, scheduler);
        return true;
    }

    @Override
    public boolean deleteJob(JobDetails jobDetails) throws SchedulerException {
        // 是否存在该任务
        boolean existJob = ScheduleUtil.existJob(jobDetails.getId(), jobDetails.getJobGroup(), scheduler);
        if (!existJob) {
            throw new TaskException("该任务不存在");
        }
        // 删除数据成功，删除任务
        return ScheduleUtil.removeJob(jobDetails.getId(), jobDetails.getJobGroup(), scheduler);
    }

    @Override
    public boolean changeRunStatus(JobDetails jobDetails) throws SchedulerException {
        // 是否存在该任务
        boolean existJob = ScheduleUtil.existJob(jobDetails.getId(), jobDetails.getJobGroup(), scheduler);
        if (!existJob) {
           throw new TaskException("该任务不存在");
        }
        // 数据更新成功后，判断状态
        if (Boolean.TRUE.equals(jobDetails.getIsEnabled())) {
            // 恢复任务
            ScheduleUtil.resumeJob(jobDetails.getId(), jobDetails.getJobGroup(), scheduler);
        } else {
            // 暂停任务
            ScheduleUtil.pauseJob(jobDetails.getId(), jobDetails.getJobGroup(), scheduler);
        }
        return true;
    }

    @Override
    public boolean runOnceImmediately(JobDetails jobDetails) throws SchedulerException {
        // 是否存在该任务
        boolean existJob = ScheduleUtil.existJob(jobDetails.getId(), jobDetails.getJobGroup(), scheduler);
        if (!existJob) {
            throw new TaskException("该任务不存在");
        }
        // 立即执行一次任务
        ScheduleUtil.triggerJob(jobDetails.getId(), jobDetails.getJobGroup(), scheduler);
        return true;
    }
}
