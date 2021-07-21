## 工程简介
本案例基于AOP对日志操作进行封装，开发者可以通过简单的配置，快速的上手和使用。


### 使用方式
- 1 开启AOP日志的支持，在配置类上添加注解 `@EnableLoggerSupport`
~~~java
@EnableLoggerSupport
@Configuration
public class LoggerConfig {

}
~~~

- 2 实现 `ILoggerService` 接口，编写自己的日志业务操作代码，
并且注册到项目的Spring容器中替换到默认操作，默认是在控制台打印日志信息。
~~~java
@Component
public class MyLoggerService implements ILoggerService {

    @Override
    public String getPrincipal() {
        /**
         * 此处只是模拟登录用户名，实际项目需要正确获取，
         * 例如在Spring Security环境中用 SecurityContextHolder.getContext().getAuthentication()
         */
        return null;
    }

    @Override
    public void handleLog(String operateDesc, String operateType, String method, String uri, Date visitTime, long costTime) {
        // 实现业务存储或打印
    }

}
~~~


- 3 在需要进行日志记录的接口上添加注解 `@AopLogger` 
  - `desc` ：操作描述
  - `type` : 操作类型，OperateType中进行选择，默认为  `OperateType.DEFAULT("操作")`
~~~java
@Data
@DictEntity(analysisProperty = true)
public class User {
    // ......
}
~~~


### 接口测试
~~~text
GET http://127.0.0.1:8090/user
ResponseBody:
{
    "id": 1,
    "username": "user001",
    "gender": 1,
    "genderText": "男",
    "status": 2,
    "statusText": "无效"
}    
~~~
控制台打印：
~~~shell script
c.a.l.samples.config.MyLoggerService     : current operate log ==> desc: 模拟查询用户, type: 查询, method:getUser, uri: /user, visitTime: Wed Jul 21 18:35:25 CST 2021, costTime: 10
~~~




