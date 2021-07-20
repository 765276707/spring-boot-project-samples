package com.aurora.excel.samples.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.aurora.excel.core.listener.ExcelAnalysisEventListener;
import com.aurora.excel.samples.model.UserModel;
import com.aurora.excel.samples.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * 部门导入解析器
 * @author xzbcode
 */
@Slf4j
public class UserExcelAnalysisListener extends ExcelAnalysisEventListener<UserModel> {

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
