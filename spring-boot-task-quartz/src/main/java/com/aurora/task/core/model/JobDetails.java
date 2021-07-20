package com.aurora.task.core.model;

import lombok.Data;
import lombok.ToString;

/**
 * 作业对象
 * @author xzbcode
 */
@Data
@ToString
public class JobDetails {

    private Long id;
    private String jobName;
    private String jobGroup;
    private String jobCron;
    private String dataMap;
    private String invokedClassPath;
    private Boolean isEnabled = false;

    public JobDetails() {
    }

    public JobDetails(Long id, String jobName, String jobGroup, String jonCron, String dataMap, String invokedClassPath, Boolean isEnabled) {
        this.id = id;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobCron = jonCron;
        this.dataMap = dataMap;
        this.invokedClassPath = invokedClassPath;
        this.isEnabled = isEnabled;
    }

}
