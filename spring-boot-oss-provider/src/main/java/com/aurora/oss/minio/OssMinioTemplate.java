package com.aurora.oss.minio;

import com.aurora.oss.exception.OssClientException;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * OssMinio常用方法调用模板
 * @author xzbcode
 */
@Slf4j
@Component
public class OssMinioTemplate {

    @Resource
    private MinioClient minioClient;

    /**
     * 获取文件
     * @param bucketName 文件桶名
     * @param objectName 文件完整路径名
     * @return
     * @throws OssClientException
     */
    public InputStream getObject(String bucketName, String objectName) throws OssClientException {
        // 构建请求参数
        GetObjectArgs args = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();
        try {
            return minioClient.getObject(args);
        } catch (MinioException | InvalidKeyException |
                NoSuchAlgorithmException | IOException e) {
            log.error("minio get object failure, cause by {}", e.getMessage());
            throw new OssClientException("文件不存在或OSS服务器未响应");
        }
    }

    /**
     * 获取文件列表
     * @param bucketName 文件桶名
     * @param prefix 文件完整路径名前缀
     * @return
     */
    public Iterable<Result<Item>> listObjects(String bucketName, String prefix) {
        ListObjectsArgs.Builder builder = ListObjectsArgs.builder();
        if (prefix!=null && !"".equals(prefix)) {
            builder.prefix(prefix);
        }
        ListObjectsArgs listObjectsArgs = builder
                .bucket(bucketName)
                .build();
        return minioClient.listObjects(listObjectsArgs);
    }

    /**
     * 生成文件下载链接
     * @param bucketName 文件桶名
     * @param objectName 文件完整路径名
     * @param expires 链接过期时间
     * @return
     * @throws OssClientException
     */
    public String presignedGetObject(String bucketName, String objectName, Integer expires) throws OssClientException {
        // 获取下载地址
        GetPresignedObjectUrlArgs presignedArgs = GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .expiry(expires, TimeUnit.MINUTES)
                .method(Method.GET)
                .build();
        try {
            return minioClient.getPresignedObjectUrl(presignedArgs);
        } catch (MinioException | InvalidKeyException |
                NoSuchAlgorithmException | IOException e) {
            log.error("minio presigned get object failure, cause by {}", e.getMessage());
            throw new OssClientException("获取文件下载链接失败");
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
    public void putObject(String bucketName, String objectName, boolean isMakeIfNoExistBucket, MultipartFile file) throws OssClientException {
        // 上传图像到MinIO服务
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            // 判断Bucket是否存在
            if (isMakeIfNoExistBucket) {
                BucketExistsArgs existsArgs = BucketExistsArgs.builder()
                        .bucket(bucketName)
                        .build();
                boolean exist = minioClient.bucketExists(existsArgs);
                if (!exist) {
                    // 不存在则新建一个
                    MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build();
                    minioClient.makeBucket(makeBucketArgs);
                }
            }
            // 执行上传
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, file.getSize(), 20971520)
                    .build();
            minioClient.putObject(args);
        } catch (MinioException | InvalidKeyException |
                NoSuchAlgorithmException | IOException e) {
            log.error("minio put object failure, cause by {}", e.getMessage());
            throw new OssClientException("文件上传失败");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("minio put object failure, cause by {}", e.getMessage());
            }
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
        // 上传图像到MinIO服务
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            // 判断Bucket是否存在
            if (isMakeIfNoExistBucket) {
                BucketExistsArgs existsArgs = BucketExistsArgs.builder()
                        .bucket(bucketName)
                        .build();
                boolean exist = minioClient.bucketExists(existsArgs);
                if (!exist) {
                    // 不存在则新建一个
                    MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build();
                    minioClient.makeBucket(makeBucketArgs);
                }
            }
            // 执行上传
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, file.length(), 20971520)
                    .build();
            minioClient.putObject(args);
        } catch (MinioException | InvalidKeyException |
                NoSuchAlgorithmException | IOException e) {
            log.error("minio put object failure, cause by {}", e.getMessage());
            throw new OssClientException("文件上传失败");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("minio put object failure, cause by {}", e.getMessage());
            }
        }
    }

    /**
     * 删除文件
     * @param bucketName 文件桶名
     * @param objectName 文件完整路径名
     * @throws OssClientException
     */
    public void deleteObject(String bucketName, String objectName) throws OssClientException {
        // 构建请求参数
        RemoveObjectArgs args = RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();
        try {
            minioClient.removeObject(args);
        }  catch (MinioException | InvalidKeyException |
                NoSuchAlgorithmException | IOException e) {
            log.error("minio remove object failure, cause by {}", e.getMessage());
            throw new OssClientException("文件删除失败");
        }
    }

}
