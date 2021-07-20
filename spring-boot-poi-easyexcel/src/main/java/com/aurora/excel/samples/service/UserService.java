package com.aurora.excel.samples.service;

import com.aurora.excel.samples.model.UserModel;
import java.util.List;

public interface UserService {

    /**
     * 查询要导出的用户列表
     * @return
     */
    List<UserModel> getExportList();

    /**
     * 导入用户数据
     * @param model 用户参数
     * @param importBatchNumber 导入批次号
     */
    void importExcelModel(UserModel model, String importBatchNumber);
}
