## 工程简介
基于JSR303标准拓展多个验证注解，同时支持密码强度校验、敏感词校验和过滤

### 1.JSR303注解拓展

- IsAssignableFrom : 验证是否为目标类的可转换的类
- IsChinese : 是否为中文
- IsChineseAreaCode : 是否为中国省份区域代码
- IsChineseIDNumber : 是否为中国身份证号码
- IsChinesePhoneNumber : 是否为合法的中国（大陆地区）电话号码
- IsChinesePlateNumber : 是否为中国车牌号码
- IsChineseZipCode : 是否为合法的中国地区邮政编码
- IsEmailAddress : 是否为合法的电子邮箱地址 
- IsHttpOrHttpsURL : 是否为合法的Http/Https地址
- IsURL : 是否为合法的URL地址
- IsIPAddress : 是否为合法的IP地址（包含IPv4或IPv6）
- IsIPv4Address : 是否为合法的IPv4地址
- IsIPv6Address : 是否为合法的IPv6地址
- IsMacAddress : 是否为合法的Mac地址
- IsPort : 是否为合法的端口号
- IsWord : 是否为字母(不区分大小写)
- IsUppercaseWord : 是否为大写字母
- IsLowercaseWord : 是否为小写字母
- IsNumber : 是否为数字
- IsWordNumberUnderline : 是否为数字字母和下划线组合
- IsMoney : 是否为合法的金额数值
- IsEqualFieldValue : 对象中的两个字段值是否一致，如旧密码和新密码字段

### 2.添加默认分组

提供6个默认分组，用于使用JSR303注解验证的时候进行分组验证
- Select.class 查询操作
- Insert.class 新增操作
- Update.class 更新操作
- Delete.class 删除操作
- Import.class 导入操作
- Export.class 导出操作

### 3.密码校验

- 基于passay包，使用PasswordChecker类可以进行密码校验
- 默认设定 简单、中等、复杂 三种密码规则
---
    简单 不允许连续6个字母字符
         不允许连续6个数字字符
         不允许有空白字符
    中等 至少包含一个大写，一个小写，一个数字
         不允许连续6个字母字符
         不允许连续6个数字字符
         不允许有空白字符
    复杂 至少包含一个大写，一个小写，一个数字，一个特殊字符
        不允许连续8个字母字符
        不允许连续8个数字字符
        不允许连续8个键盘字符
        不允许有空白字符
---
- 如果有需要可以自己定制规则
~~~java
@RestController
public class ValidateController {

    /**
     * 校验密码及其强度
     * @param password 密码
     */
    private void validPassword(String password) {
        PasswordChecker.isValid(password, PasswordComplexity.MEDIUM, "密码强度较低");
    }
    
}
~~~

### 4.敏感词校验

- 敏感词校验是基于DFA算法，使用SensitiveWordChecker进行校验和替换
- 需要实现 ISensitiveWordDataSource实现一个数据源 提供给检测器
~~~java
@Component("sensitiveWordDataSource")
public class FileSensitiveWordDataSource implements ISensitiveWordDataSource {

    // TODO 待添加敏感词数据源
    @Override
    public Map<String, String> getData() {
        Map<String, String> data = new HashMap<>(8);
        return data;
    }

}

@RestController
public class ValidateController {

    @Resource
    private ISensitiveWordDataSource sensitiveWordDataSource;

    /**
     * 校验敏感词
     * @param sensitiveWord 敏感词内容
     */
    private void validSensitiveWord(String sensitiveWord) {
        SensitiveWordChecker.contain(sensitiveWord, MatchRule.MIN_DEPTH, sensitiveWordDataSource);
    }
}
~~~


