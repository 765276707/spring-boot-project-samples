package com.aurora.excel.samples.service.impl;

import com.aurora.excel.samples.model.UserModel;
import com.aurora.excel.samples.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<UserModel> getExportList() {
        // 模拟一些用户列表
        List<UserModel> userModels = new ArrayList<>();
        userModels.add(new UserModel(1L, "user001", 20, "18066035466", true));
        userModels.add(new UserModel(2L, "user002", 21, "18066035466", true));
        userModels.add(new UserModel(3L, "user003", 22, "18066035466", true));
        userModels.add(new UserModel(4L, "user004", 23, "18066035466", true));
        userModels.add(new UserModel(5L, "user005", 24, "18066035466", true));
        return userModels;
    }

    @Override
    public void importExcelModel(UserModel model, String importBatchNumber) {
        // 此处模拟导入到数据库
        log.info("模拟导入用户_{}： {}", model.getId(), model.toString());
    }

}
