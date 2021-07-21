package com.aurora.dict.samples.controller;

import com.aurora.dict.samples.domain.Person;
import com.aurora.dict.samples.domain.User;
import com.aurora.dict.samples.response.ResultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    /**
     * 模拟查询用户
     * @return
     */
    @GetMapping("/user")
    public ResultResponse<User> getUser() {
        return ResultResponse.success("查询成功",
                    new User(1, "user001", 1, null, 3, null));
    }

    /**
     * 模拟查询人类
     * @return
     */
    @GetMapping("/person")
    public ResultResponse<Person> getPerson() {
        return ResultResponse.success("查询成功", new Person(1, "person666",
                        new User(1, "user001", 1, null, 1, null))
                );
    }
}
