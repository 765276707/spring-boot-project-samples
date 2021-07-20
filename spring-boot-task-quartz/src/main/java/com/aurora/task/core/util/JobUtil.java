package com.aurora.task.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.quartz.*;

import java.util.Date;


/**
 * 任务工具类
 * @author xzbcode
 */
public class JobUtil {

    /**
     * 获取任务的标识
     * @return
     */
    public static JobKey getJobKey(Long jobId, String jobGroup) {
        return new JobKey(String.valueOf(jobId), jobGroup);
    }


    /**
     *  获取任务类
     * @param classPath 类路径
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends Job> getClass(String classPath) throws ClassNotFoundException {
        return (Class<? extends Job>) Class.forName(classPath);
    }


    /**
     * 将 Json字符串 转成 JobDataMap
     * @param json
     * @return
     */
    public static JobDataMap convertJsonToDataMap(String json) {
        JobDataMap dataMap = new JobDataMap();
        if (json!=null && !"".equals(json)) {
            try {
                dataMap.putAll(JSON.parseObject(json));
            } catch (JSONException e) {
                // 非法 json, 默认不传递参数
                dataMap.clear();
            }
        }
        return dataMap;
    }


    /**
     * 获取 JobDetail
     * @param key 组合键
     * @param jobClass 定时任务类
     * @param dataMap 参数
     * @return
     */
    public static JobDetail getJobDetail(JobKey key, Class<? extends Job> jobClass, JobDataMap dataMap) {
        return JobBuilder.newJob(jobClass)
                .withIdentity(key.getName(), key.getGroup())
                .usingJobData(dataMap)
                .storeDurably()
                .build();
    }


    /**
     * 获取 Trigger
     * @param key 组合键
     * @param cron 定时表达式
     * @param dataMap 参数
     * @return
     */
    public static Trigger getTrigger(JobKey key, String cron, JobDataMap dataMap, Date startTime) {
        return TriggerBuilder.newTrigger()
                .withIdentity(key.getName(), key.getGroup())
                .usingJobData(dataMap)
                // CRON 表达式
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                // 启动时间
                .startAt(startTime)
                .build();
    }
}
