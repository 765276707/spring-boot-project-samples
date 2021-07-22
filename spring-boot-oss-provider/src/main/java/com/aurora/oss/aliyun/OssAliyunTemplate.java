package com.aurora.oss.aliyun;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectRequest;
import com.aurora.oss.exception.OssClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.*;
import java.util.List;


@Slf4j
@Component
public class OssAliyunTemplate {

    @Resource
    private OSS aliyunOssClient;

    /**
     * 获取文件
     * @param bucketName 文件桶名
     * @param objectName 文件完整路径名
     * @return
     */
    public InputStream getObject(String bucketName, String objectName) {
        OSSObject object = aliyunOssClient.getObject(bucketName, objectName);
        // object不为null
        return object!=null?object.getObjectContent():null;
    }


    /**
     * 获取文件列表
     * @param bucketName 文件桶名
     * @param prefix 文件路径前缀
     * @return
     */
    List<OSSObjectSummary> listObjects(String bucketName, String prefix) {
        ObjectListing objectListing = aliyunOssClient.listObjects(bucketName, prefix);
        return objectListing!=null?objectListing.getObjectSummaries():null;
    }


    /**
     * 上传文件
     * @param bucketName 文件桶名
     * @param objectName 文件完整路径名
     * @param isMakeIfNoExistBucket 如果文件桶不存在是否创建
     * @param file 文件
     * @throws OssClientException
     */
    public void putObject(String bucketName, String objectName, boolean isMakeIfNoExistBucket, MultipartFile file) throws OssClientException {
        try {
            // 创建上传请求对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, file.getInputStream());
            // 执行上传
            aliyunOssClient.putObject(putObjectRequest);
        } catch (OSSException | ClientException | IOException e) {
            log.error("aliyun oss put object failure, cause by {}", e.getMessage());
            throw new OssClientException("文件上传失败");
        }
    }

    /**
     * 上传文件
     * @param bucketName 文件桶名
     * @param objectName 文件完整路径名
     * @param isMakeIfNoExistBucket 如果文件桶不存在是否创建
     * @param file 文件
     * @throws OssClientException
     */
    public void putObject(String bucketName, String objectName, boolean isMakeIfNoExistBucket, File file) throws OssClientException {
        try {
            // 创建上传请求对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new FileInputStream(file));
            // 执行上传
            aliyunOssClient.putObject(putObjectRequest);
        } catch (OSSException | ClientException | IOException e) {
            log.error("aliyun oss put object failure, cause by {}", e.getMessage());
            throw new OssClientException("文件上传失败");
        }
    }

    /**
     * 删除文件
     * @param bucketName 文件桶名
     * @param objectName 文件完整路径名
     * @throws OssClientException
     */
    public void deleteObject(String bucketName, String objectName) throws OssClientException {
        try {
            // 删除文件
            aliyunOssClient.deleteObject(bucketName, objectName);
        } catch (OSSException | ClientException e) {
            log.error("aliyun oss put object failure, cause by {}", e.getMessage());
            throw new OssClientException("文件删除失败");
        }

    }

}
