## 工程简介
OSS的使用案例，当前有MinIO、阿里云OSS，后续会继续增加其他服务商的OSS

### MinIO OSS
MinIO是本地环境下的一款基于Apache License v2.0开源协议的对象存储服务

#### 1.安装
- 本地安装
格式exe下载地址： http://dl.minio.org.cn/server/minio/release/windows-amd64/minio.exe
~~~shell script
minio.exe server D:\Photos
~~~

- docker安装
~~~shell script
docker pull minio/minio
docker run -p 9000:9000 minio/minio server /data
~~~

#### 2.配置
工程目录中 resources/config/oss-minio.properties 中进行配置
~~~properties
# 服务器地址
oss.minio.endpoint=http://127.0.0.1:9000
# 访问账号/密码
oss.minio.accessKey=minioadmin
oss.minio.secretKey=minioadmin
~~~

#### 3.使用方式
~~~java
@Component
public class TestMinIO {
    
    @Autowired
    private OssMinioTemplate ossMinioTemplate;
    
    public void getObject() {
        // 下载文件
        InputStream is = ossMinioTemplate.getObject("bucket-name", "/da/miniofile.txt");
        // 响应到前端 ......
    }
}
~~~


### AliYun OSS
MinIO是本地环境下的一款基于Apache License v2.0开源协议的对象存储服务

#### 1.配置
工程目录中 resources/config/oss-aliyun.properties 中进行配置
~~~properties
# 服务器地址
oss.aliyun.endpoint=http://oss-cn-hangzhou.aliyuncs.com/
# 访问账号/密码
oss.aliyun.accessKey=aliyunadmin
oss.aliyun.secretKey=aliyunadmin
~~~

#### 2.使用方式
~~~java
@Component
public class TestAliyunOSS {
    
    @Autowired
    private OssAliyunTemplate ossAliyunTemplate;
    
    public void getObject() {
        // 下载文件
        InputStream is = ossAliyunTemplate.getObject("bucket-name", "/da/miniofile.txt");
        // 响应到前端 ......
    }
}
~~~