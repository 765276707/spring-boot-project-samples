## 工程简介
SpringBoot工程常见配置

### custom
- `AsyncConfig` : 异步任务线程池配置
- `PageHelperConfig` : 分页配置
- `RedisConfig` : Redis配置
- `RestTemplateConfig` : RestTemplate配置
- `SwaggerConfig` : Swagger2配置
- `WebMvcConfig` : WebMvc配置，过滤器、拦截器、静态资源等

### error_advice
- 请求参数错误响应

### version
- 接口版本控制 `@ApiVersion`

### tk.mybatis
配置在mybatis目录下
~~~yaml
# mybatis 配置
mybatis:
  config-location: classpath:mybatis/mybatis-config.xml
  mapper-locations:
    - classpath:mybatis/mapper/*.xml
~~~

mybatis-generator配置pom.xml插件
~~~xml
<!-- mybatis-generator插件 -->
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.3.5</version>
    <configuration>
        <configurationFile>${basedir}/src/main/resources/mybatis/mybatis-generator.xml</configurationFile>
        <verbose>true</verbose>
        <!--每次生成xml是覆盖而不是追加-->
        <overwrite>true</overwrite>
    </configuration>
    <dependencies>
        <!-- mysql -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.19</version>
        </dependency>
        <!-- tk.mybatis -->
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper</artifactId>
            <version>${tk.mybatis}</version>
        </dependency>
    </dependenci>
</plugin>
~~~