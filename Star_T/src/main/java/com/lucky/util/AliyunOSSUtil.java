package com.lucky.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.OSSObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import static com.lucky.util.ConfigReader.getProperty;

public class AliyunOSSUtil {
    private static final String ACCESS_KEY_ID = getProperty("ACCESS_KEY_ID");
    private static final String ACCESS_KEY_SECRET = getProperty("ACCESS_KEY_SECRET");
    private static final String ENDPOINT = getProperty("ENDPOINT");

    /**
     * 上传文件到 OSS
     *
     * @param bucketName    存储空间名称
     * @param objectName    对象名称，包含存储路径，如 dir1/dir2/filename.txt
     * @param localFilePath 本地文件路径
     */
    public static void uploadFile(String bucketName, String objectName, String localFilePath) {
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new File(localFilePath));
            ossClient.putObject(putObjectRequest);
            System.out.println("文件上传成功: " + objectName);
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 从 OSS 下载文件
     *
     * @param bucketName    存储空间名称
     * @param objectName    对象名称，包含存储路径，如 dir1/dir2/filename.txt
     * @param localFilePath 本地文件保存路径
     */
    public static void downloadFile(String bucketName, String objectName, String localFilePath) {
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, objectName);
            ossClient.getObject(getObjectRequest, new File(localFilePath));
//            System.out.println("文件下载成功: " + objectName);
        } finally {
            ossClient.shutdown();
        }
    }

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
     * 从 OSS 删除文件
     *
     * @param bucketName 存储空间名称
     * @param objectName 对象名称，包含存储路径，如 dir1/dir2/filename.txt
     */
    public static void deleteFile(String bucketName, String objectName) {
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            ossClient.deleteObject(bucketName, objectName);
            System.out.println("文件删除成功: " + objectName);
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

            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            PutObjectResult result = ossClient.putObject(putObjectRequest);
//            System.out.println("文件从 URL 上传成功: " + objectName + ", ETag: " + result.getETag());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("文件从 URL 上传失败: " + e.getMessage());
        } finally {
            ossClient.shutdown();
        }
    }
}