package com.aurora.security.core.util;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {

    /**
     * 响应数据
     * @param response
     * @param data
     * @throws IOException
     */
    public static <T> void writeJSON(HttpServletResponse response,
                                     HttpStatus status,
                                     @NonNull T data) throws IOException {
        // 构建响应数据
        String jsonResult = JSON.toJSONString(data);
        // 设置响应类型
        if (status != null) {
            response.setStatus(status.value());
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        // 输出
        PrintWriter pw = response.getWriter();
        pw.write(jsonResult);
        pw.flush();
        pw.close();
    }

}
