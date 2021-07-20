## Quartz石英调度
一般项目中需要用到复杂的定时任务，并且可以对定时任务进行界面化管理，Quartz就是一个比较适合此类需求的定时任务框架。

### 如何使用
本项目已经对Quartz进行一定的封装，开发人员只需要进行简单的实现几个接口和配置即可快速使用。

- 继承`QuartzJobBean`, 编写定时任务类
~~~java
@Slf4j
public class TestJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("这是测试任务 test job ...... {}", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

}
~~~

- 实现`IJobDetailsService`, 这是加载所有的定时任务接口, 提供一组 `JobDetails` 管理对象
~~~java
public class MyJobDetailsService implements IJobDetailsService {

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
        // 任务名
        myJobDetails.setJobName("test job");
        // 任务分组
        myJobDetails.setJobGroup("test group");
        // 从0秒开始，每5秒执行一次
        myJobDetails.setJonCron("0/5 * * * * ?");
        // 任务类的路径
        myJobDetails.setInvokedClassPath("com.xzbcode.task.samples.TestJob");
        // 是否启用
        myJobDetails.setEnabled(true);
        return myJobDetails;
    }
}
~~~

- 继承`QuartzConfiguration`, 提供一个已实现的 `IJobDetailsService` 的bean
~~~java
@Configuration
public class MyQuartzConfiguration extends QuartzConfiguration {

    @Override
    public IJobDetailsService jobDetailsService() {
        return new MyJobDetailsService();
    }

}
~~~

### 接口测试
默认：案例中在启动时会生成一个测试定时任务，每5秒钟运行一次, 分组为 test_group，编号为 1
~~~java
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
~~~

- 暂停/重启任务
~~~text
接口： PUT http://127.0.0.1:8090/job/change
参数： 
      {
          "id": 1,
          "jobGroup": "test_group",
          "isEnabled": true
      }
说明： 参数isEnabled为true时重启，为false则暂停
~~~

- 立即运行一次
~~~text
接口： PUT http://127.0.0.1:8090/job/run
参数： 
      {
          "id": 1,
          "jobGroup": "test_group"
      }
~~~

- 删除任务
~~~text
接口： PUT http://127.0.0.1:8090/job/delete
参数： 
      {
          "id": 1,
          "jobGroup": "test_group"
      }
~~~

任务的运行状态可以看控制台打印，如：
~~~shell script
2021-07-21 00:41:17.602  INFO 4268 --- [eduler_Worker-7] com.aurora.task.samples.quartz.TestJob   : 这是测试任务 test job ...... 2021-07-21 00:41:17
~~~
