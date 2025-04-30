package com.lucky.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;


public class AliyunOSSUtil {
    private static final String ACCESS_KEY_ID = System.getenv("ACCESS_KEY_ID");
    private static final String ACCESS_KEY_SECRET = System.getenv("ACCESS_KEY_SECRET");
    private static final String ENDPOINT = System.getenv("ENDPOINT");

    /**
     * 生成文件的访问 URL
     *
     * @param bucketName     存储空间名称
     * @param objectName     对象名称，包含存储路径，如 dir1/dir2/filename.txt
     * @param expirationTime 过期时间（毫秒）
     * @return 文件的访问 URL
     */
    public static String generateFileUrl(String bucketName, String objectName, long expirationTime) {
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            Date expiration = new Date(new Date().getTime() + expirationTime);
            URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
            return url.toString();
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 通过文件 URL 上传到 OSS，若存在同名文件则跳过
     *
     * @param bucketName 存储空间名称
     * @param objectName 对象名称，包含存储路径，如 dir1/dir2/filename.txt
     * @param fileUrl    文件的网络 URL
     */
    public static void uploadFileByUrl(String bucketName, String objectName, String fileUrl) {
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            // 检查文件是否已存在
            if (ossClient.doesObjectExist(bucketName, objectName)) {
//                System.out.println("文件已存在，跳过上传: " + objectName);
                return;
            }
            URI uri = new URI(fileUrl);
            URL url = uri.toURL();
            URLConnection connection = url.openConnection();
            connection.connect();
        } catch (IOException e) {
//            e.printStackTrace();
            System.err.println("文件从 URL 上传失败: " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } finally {
            ossClient.shutdown();
        }
    }
}