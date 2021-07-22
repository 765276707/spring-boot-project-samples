package com.aurora.commons.utils.web;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * <h1>响应工具类</h1>
 * @author xzb
 */
public class ResponseUtil {

    /**
     * <h2>响应数据</h2>
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

    /**
     * <h2>响应图片文件</h2>
     * @param response
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    public static void writeImage(HttpServletResponse response,
                                  InputStream inputStream,
                                  ServletOutputStream outputStream) throws IOException {
        response.setContentType("image/jpeg;charset=utf-8");
        outputStream = response.getOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, len);
        }
        response.flushBuffer();
    }


    /**
     * <h2>响应Excel文件</h2>
     * @param response
     * @param inputStream
     * @param outputStream
     * @param exportFileName 导出文件的名称
     * @throws IOException
     */
    public static void writeExcel(HttpServletResponse response,
                                  InputStream inputStream,
                                  ServletOutputStream outputStream,
                                  String exportFileName) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(exportFileName, "UTF-8")
                                .replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        // 写出文件
        outputStream = response.getOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, len);
        }
        response.flushBuffer();
    }

}
