## 工程简介
EasyExcel时阿里开源的一款高性能的Excel工具包。本工程对其导入Excel进行了简单的封装，结合实际业务场景用起来就更加方便。

### 导入Excel
- 1.Excel模型需要增加两个字段属性
~~~java
public class XxxExcelModel {
    
    // 自己的属性......
   
    // 导入类型为：DB / EXCEL
    @NotBlank(message = "导入类型不能为空")
    @ExcelProperty("导入类型")
    private String importedType;

    @ExcelProperty("导入结果")
    private String importedResult;
}
~~~

- 2.导入的业务监听器实现 `ExcelAnalysisEventListener`
~~~java
public class UserExcelAnalysisListener extends ExcelAnalysisEventListener<UserModel> {
    
    // 提供一个业务层的实现类来支持数据导入数据库
    private final UserService userService;

    public UserExcelAnalysisListener(UserService userService) {
        super("用户");
        this.userService = userService;
    }

    @Override
    public UserModel analysisModel(UserModel model, AnalysisContext analysisContext) {
        // 导入成功
        userService.importExcelModel(model, this.getImportBatchNumber());
        model.setImportedResult("导入成功");
        return model;
    }

    @Override
    public UserModel handleException(UserModel model, Exception e) {
        model.setImportedResult("导入失败");
        return model;
    }

    @Override
    public boolean isValidateModel() {
        // 开启默认的JSR303校验
        return false;
    }
}
~~~

- 3.调用该监听器执行导入
~~~java
// 导入Excel
UserExcelAnalysisListener listener = new UserExcelAnalysisListener(userService);
EasyExcel.read(file.getInputStream(), UserModel.class, listener)
        .sheet()
        .doRead();
~~~

### 导出Excel
导出方式参看官方文档

### 接口测试
- 模拟导出用户，请在浏览器请求
~~~text
接口： GET http://127.0.0.1:8090/exportExcel
参数： 无
~~~

- 模拟导入用户，导入文件为上一步导出的文件
~~~text
接口： POST http://127.0.0.1:8090/importExcel
参数： file: 选择文件
响应： 
{
    "code": 200,
    "message": "导入结束",
    "data": "http://localhost:8090/importResult/user_1ec741f0-c5bc-4cd2-b507-7ae0fb4e57c6.xlsx"
}
最后拿data返回的地址到浏览器中请求获取导出结果文件
~~~