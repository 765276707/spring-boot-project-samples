package com.aurora.email.manager;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

@Component
public class FileManager {

    /**
     * 获取测试附件
     * @return
     */
    public File getAttachmentFile() {
        return new File(Objects.requireNonNull(
                this.getClass().getClassLoader().getResource("attachment/email.txt")).getFile());
    }

    /**
     * 获取测试图片
     * @return
     */
    public File getImageFile() {
        return new File(Objects.requireNonNull(
                this.getClass().getClassLoader().getResource("image/email.jpg")).getFile());
    }

}
