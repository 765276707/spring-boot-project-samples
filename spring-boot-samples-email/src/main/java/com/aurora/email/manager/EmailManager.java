package com.aurora.email.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * 邮件模板
 * @author xzbcode
 */
@Component
public class EmailManager {

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 获取email模板的html字符串
     * @return
     */
    public String getEmailText() {
        // 设置变量，在模板网页中展示
        Context context = new Context();
        context.setVariable("username", "user001");
        context.setVariable("gender", "男");
        context.setVariable("birthday", "2020-10-01");
        // 解析成字符串
        return templateEngine.process("email.html", context);
    }

}
