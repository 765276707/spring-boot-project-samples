package com.aurora.encrypt.samples.controller;

import com.aurora.encrypt.core.annotation.ApiCryptBody;
import com.aurora.encrypt.core.annotation.ApiDecryptBody;
import com.aurora.encrypt.core.annotation.ApiEncryptBody;
import com.aurora.encrypt.samples.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * API数据传输加解密测试接口
 * @author xzbcode
 */
@RestController
@SuppressWarnings("all")
@Slf4j
public class IndexController {

    /**
     * 请求不加密，响应加密
     * @param user 请求参数
     * @return
     */
    @ApiEncryptBody
    @PostMapping("/encryptBody")
    public User encryptBody(@RequestBody User user) {
        log.info("==> 请求参数详情: {}", user.toString());
        return user;
    }

    /**
     * 请求加密，响应不加密
     * @param user 请求参数
     * @return
     */
    @ApiDecryptBody
    @PostMapping("/decryptBody")
    public User decryptBody(@RequestBody User user) {
        log.info("==> 请求参数详情: {}", user.toString());
        return user;
    }


    /**
     * 请求和响应都加密
     * @param user 请求参数
     * @return
     */
    @ApiCryptBody
    @PostMapping("/cryptBody")
    public User cryptBody(@RequestBody User user) {
        log.info("==> 请求参数详情: {}", user.toString());
        return user;
    }

}
