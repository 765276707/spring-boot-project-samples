## 工程简介
传统的数据字典一般有两种方式：

- 一是在数据库查询的是使用关联字段直接联查

- 二是借助AOP在字段映射的时候进行解析赋值

本案例是用了对第二种方式进行封装，开发者可以快速的上手和使用。


### 使用方式
- 1 开启AOP字典的支持，在配置类上添加注解 `@EnableAopDictSupport`
~~~java
@EnableAopDictSupport
@Configuration
public class DictConfig {
    // ......
}
~~~

- 2 初始化字典数据源，实现 `IDictDataSource` 接口，从数据库或其他位置加载系统的字典到 getDictSource() 方法中，
并且注册到项目的Spring容器中就会替换默认的字典数据源。
~~~java
@Component
public class MyDictDataSource implements IDictDataSource {
    
    @Override
    public DictEntry getDictSource() {
        return DictEntry.create(2)
                // 模拟添加用户性别字典
                .addDict("user_gender",
                        DictItemEntry.create(2)
                                .addItem("1", "男")
                                .addItem("2", "女")
                                .addItem("3", "保密")
                )
                // 模拟添加用户状态字典
                .addDict("user_status",
                        DictItemEntry.create(2)
                                .addItem("1", "有效")
                                .addItem("2", "无效")
                        );
    }

}
~~~


- 3 在需要进行字段字典解析的类上添加注解 `@DictEntity` 标识该类含有需要进行字典解析的属性，
  - `analysisProperty` ： 是否解析属性中的对象、集合中的对象、数组中对象， 默认为true
~~~java
@Data
@DictEntity(analysisProperty = true)
public class User {
    // ......
}
~~~

- 4 在需要解析的字段上，添加注解 `@DictField` 标识该属性需要进行字典解析
  - `keyFieldName` ：字典Key是哪个字段的值
  - `code` ：对应的字典码
  - `defaultValue` ：如果未找到对应字典，则采用默认值，默认为 空字符串
  
~~~java
private Integer gender;

@DictField(keyFieldName = "gender", code = "user_gender", defaultValue = "保密")
private String genderText;
~~~

### 复杂的类
- 如果类的某个属性是一个对象或集合，该对象或集合内的对象也需要进行字典解析，则这个类也
需要添加注解 `@DictEntity`

- 一个类继承了一个带有 `@DictEntity` 的父类，则该类也会自动继承 `@DictEntity`


### 支持的注解
默认支持以下两种注解标注的接口
- @RestController
- @Controller + @ResponseBody


### 接口测试
~~~text
GET http://127.0.0.1:8090/user
ResponseBody:
{
    "code": 200,
    "message": "查询成功",
    "data": {
        "id": 1,
        "username": "user001",
        "gender": 1,
        "genderText": "男",
        "status": 3,
        "statusText": "未知状态"
    }
}    

GET http://127.0.0.1:8090/person
ResponseBody:
{
    "code": 200,
    "message": "查询成功",
    "data": {
        "id": 1,
        "personName": "person666",
        "user": {
            "id": 1,
            "username": "user001",
            "gender": 1,
            "genderText": "男",
            "status": 1,
            "statusText": "有效"
        }
    }
}
~~~



