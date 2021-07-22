package com.aurora.commons.utils.identity;

import java.util.UUID;

/**
 * UUID工具类
 * @author xzb
 */
public class UUIDUtil {

    public static String generator(int length, boolean replaced) {
        if (length <= 0) {
            throw new IllegalArgumentException("uuid length must greater than 0.");
        }
        if (length > 32) {
            throw new IllegalArgumentException("uuid length must less than 32.");
        }
        if (replaced) {
            return UUID.randomUUID().toString().replace("-", "").substring(0, length);
        }
        return UUID.randomUUID().toString().substring(0, length);
    }


    public static String generator32() {
        return UUID.randomUUID().toString();
    }


    public static String generator32Replaced() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
