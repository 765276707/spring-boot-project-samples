package com.aurora.task.samples.controller;

import com.aurora.task.core.exception.TaskException;
import com.aurora.task.core.model.JobDetails;
import com.aurora.task.samples.service.QuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务控制器
 * @author xzbcode
 */
@Slf4j
@RestController
public class QuartzJobController {

    @Autowired
    private QuartzJobService jobService;

    /**
     * 暂停/恢复定时任务
     * @param jobDetails 任务参数
     * @return
     */
    @PutMapping(value = "/job/change")
    public Map<String, Object> changeRunStatus(@RequestBody JobDetails jobDetails) {
        try {
            // 执行更新
            boolean result = jobService.changeRunStatus(jobDetails);
            // 返回结果
            String message = "任务" + (Boolean.TRUE.equals(jobDetails.getIsEnabled())?"运行":"暂停") + (result?"成功":"失败");
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", result);
            resp.put("message", message);
            return resp;
        }
        catch (TaskException te) {
            // 任务操作异常
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message", te.getMessage());
            return resp;
        } catch (SchedulerException se) {
            // 任务操作异常
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message", "定时任务工作异常");
            return resp;
        }
    }


    /**
     * 立即执行一次
     * @param jobDetails 任务参数
     * @return
     */
    @PutMapping(value = "/job/run")
    public Map<String, Object> runOnceImmediately(@RequestBody JobDetails jobDetails) {
        try {
            // 立即执行
            boolean result = jobService.runOnceImmediately(jobDetails);
            // 返回结果
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", result);
            resp.put("message", "任务立即运行一次" + (result?"成功":"失败"));
            return resp;
        }
        catch (TaskException te) {
            // 任务操作异常
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message", te.getMessage());
            return resp;
        } catch (SchedulerException se) {
            // 任务操作异常
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message", "定时任务工作异常");
            return resp;
        }
    }


    /**
     * 删除定时任务
     * @param jobDetails 任务参数
     * @return
     */
    @PutMapping(value = "/job/delete")
    public Map<String, Object> deleteJob(@RequestBody JobDetails jobDetails) {
        try {
            // 执行删除
            boolean result = jobService.deleteJob(jobDetails);
            // 返回结果
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", result);
            resp.put("message", "任务删除" + (result?"成功":"失败"));
            return resp;
        }
        catch (TaskException te) {
            // 任务操作异常
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message", te.getMessage());
            return resp;
        }
        catch (SchedulerException se) {
            // 任务操作异常
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message", "定时任务工作异常");
            return resp;
        }
    }

}
