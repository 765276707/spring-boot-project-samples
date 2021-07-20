package com.aurora.security.samples.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * 账号密码登录参数
 * @author xzbcode
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountLoginDTO {

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "验证码编号不能为空")
    private String verifyId;

    @NotEmpty(message = "验证码不能为空")
    private String verifyCode;

}
