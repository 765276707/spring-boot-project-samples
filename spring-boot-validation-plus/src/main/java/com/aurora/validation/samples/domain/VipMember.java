package com.aurora.validation.samples.domain;

import com.aurora.validation.core.contraint.annotation.IsChinese;
import com.aurora.validation.core.contraint.annotation.IsChineseGender;
import com.aurora.validation.core.contraint.annotation.*;
import com.aurora.validation.core.contraint.group.Insert;
import com.aurora.validation.core.contraint.group.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * VIP会员
 * @author xzbcode
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VipMember {

    @NotNull(message = "编号不能为空", groups = {Update.class})
    private Integer id;

    @IsChinese(message = "真实姓名必须为中文", groups = {Insert.class, Update.class})
    @NotBlank(message = "真实姓名不能为空", groups = {Insert.class, Update.class})
    @Length(max = 20, message = "真实姓名最大不能超过20个字", groups = {Insert.class, Update.class})
    private String realName;

    // 密码字段在程序代码段中校验
    private String password;

    @IsChineseGender(message = "性别只能为男你、女或保密", groups = {Insert.class, Update.class})
    private String gender;

    @IsEmailAddress(groups = {Insert.class, Update.class})
    private String email;

    private Date birthday;

    @IsChinesePhoneNumber(groups = {Insert.class, Update.class})
    private String phoneNumber;

    @IsChineseIDNumber(groups = {Insert.class, Update.class})
    private String idNumber;

    @IsMoney(message = "账户余额不符合规范", groups = {Insert.class, Update.class})
    private BigDecimal money;

    @IsHttpOrHttpsURL(message = "不合法的个人主页链接地址", groups = {Insert.class, Update.class})
    private String showUrl;

    @IsWord(message = "备注只能为字母", groups = {Insert.class, Update.class})
    private String remark;

    // 敏感词字段在程序代码段中校验
    private String sensitiveWord;

}
