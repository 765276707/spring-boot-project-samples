package com.aurora.excel.samples.manager;

import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * 临时文件管理器
 * @author xzbcode
 */
@Component
public class TemporaryFileManager {

    /**
     * 获取临时文件目录
     * @return
     */
    private String getBaseSaveDir() {
        String baseDir = "";
        try {
            baseDir = this.getClass().getResource("/").toURI().getPath() + "temp";
        } catch (URISyntaxException e) {
            baseDir = (this.getClass().getResource("/").getPath() + "temp").replaceAll("%20", " ");
        }
        return baseDir;
    }

    /**
     * 创建临时部门EXCEL文件
     * @return
     */
    public File createUserExcelTempFile() {
        return new File(this.getBaseSaveDir() + "/user_" + UUID.randomUUID().toString() + ".xlsx");
    }


}
