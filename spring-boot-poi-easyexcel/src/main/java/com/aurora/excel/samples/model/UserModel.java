package com.aurora.excel.samples.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.aurora.excel.samples.convert.IsEnabledConvert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户的Excel模型
 * @author xzbcode
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    public UserModel(@NotNull(message = "用户编号不能为空") Long id, @NotBlank(message = "用户名不能为空") String username, Integer age, String telephone, @NotNull(message = "是否启用不能为空") Boolean isEnabled) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.telephone = telephone;
        this.isEnabled = isEnabled;
    }

    @NotNull(message = "用户编号不能为空")
    @ExcelProperty("用户编号")
    private Long id;

    @NotBlank(message = "用户名不能为空")
    @ExcelProperty("用户名")
    private String username;

    @ExcelProperty("用户年龄")
    private Integer age;

    @ExcelProperty("联系电话")
    private String telephone;

    @NotNull(message = "是否启用不能为空")
    @ExcelProperty(value = "是否启用", converter = IsEnabledConvert.class)
    private Boolean isEnabled;




    // 导入类型为：DB / EXCEL
    @NotBlank(message = "导入类型不能为空")
    @ExcelProperty("导入类型")
    private String importedType;

    @ExcelProperty("导入结果")
    private String importedResult;

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", telephone='" + telephone + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
