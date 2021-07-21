package com.aurora.logger.core.service;

/**
 * <h1>操作类型</h1>
 * @author xzb
 */
public enum OperateType {

    SEARCH("查询"),
    INSERT("新增"),
    UPDATE("更新"),
    DELETE("删除"),
    IMPORT("导入"),
    EXPORT("导出"),
    UPLOAD("上传"),
    DOWNLOAD("下载"),
    STARTUP("启动"),
    SHUTDOWN("关闭"),
    PAUSE("暂停"),
    RESUME("重启"),
    TRIGGER("触发"),
    CONNECT("连接"),
    DISCONNECT("断开"),
    DEFAULT("操作"),
    OTHERS("其它");

    private String type;

    OperateType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

}
