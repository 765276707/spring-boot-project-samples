package com.aurora.excel.samples.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.aurora.excel.samples.listener.UserExcelAnalysisListener;
import com.aurora.excel.samples.manager.TemporaryFileManager;
import com.aurora.excel.samples.model.ResultResponse;
import com.aurora.excel.samples.model.UserModel;
import com.aurora.excel.samples.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserExcelController {

    @Autowired
    private UserService userService;
    @Autowired
    private TemporaryFileManager temporaryFileManager;

    @Value("${server.port}")
    private Integer port;
//    @Value("${server.servlet.context-path}")
//    private String contextPath;

    /**
     * 导入部门
     * @param file 部门数据文件, 测试时只需将【导出部门】方法导出的文件导入即可
     * @return
     */
    @PostMapping("/importExcel")
    @ResponseBody
    public ResultResponse importExcel(MultipartFile file) throws IOException {
        // 校验文件
//        FileValidator.validate(file, FileExtension.EXCEL_SET, 20, FileSizeUnit.MB);\
        // 导入Excel
        UserExcelAnalysisListener listener = new UserExcelAnalysisListener(userService);
        EasyExcel.read(file.getInputStream(), UserModel.class, listener)
                .sheet()
                .doRead();
        // 获取导入结果
        List<UserModel> resultList = listener.getResultList();
        // 创建Excel文件
        File excelFile = temporaryFileManager.createUserExcelTempFile();
        // 写入数据
        EasyExcel.write(excelFile, UserModel.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .excludeColumnFiledNames(Arrays.asList("gmtCreate")) //指定不输出的列
                .sheet("用户导入结果")
                .doWrite(resultList);
        // 输出文件流
        String fileName = excelFile.getName();
        String downloadURL = "http://localhost:" + port + "/importResult/" + fileName;
        return ResultResponse.success("导入结束", downloadURL);
    }


    /**
     * 导出部门
     * @return
     */
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        // 查询数据
        List<UserModel> excelList = userService.getExportList();
        // 创建Excel文件
        File excelFile = temporaryFileManager.createUserExcelTempFile();
        // 写入数据
        EasyExcel.write(excelFile, UserModel.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .excludeColumnFiledNames(Arrays.asList("importedType", "importedResult")) //指定不输出的列
                .sheet("用户")
                .doWrite(excelList);
        // 输出文件流
        try {
            FileInputStream fis = new FileInputStream(excelFile);
            writeExcel(response, fis, "用户导出结果");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 上传后删除临时文件
            if (excelFile.exists()) {
                excelFile.delete();
            }
        }
    }

    /**
     * 响应Excel文件
     * @param response
     * @param inputStream
     * @param exportFileName 导出文件的名称
     * @throws IOException
     */
    private void writeExcel(HttpServletResponse response,
                                  InputStream inputStream,
                                  String exportFileName) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(exportFileName, "UTF-8")
                .replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        // 写出文件
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, len);
        }
        response.flushBuffer();
    }

}
