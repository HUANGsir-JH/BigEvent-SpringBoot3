package org.huang.bigevent.utils;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.exceptions.ClientException;

import java.io.InputStream;

public class AliyunOssPicturesUtil {
    // 服务器地址
    private static final String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
    private static final String endpointWithoutHttps = "oss-cn-shenzhen.aliyuncs.com";
    // Bucket名称
    private static final String bucketName = "big-event-2025-3-5";
    // Region
    private static final String region = "cn-shenzhen";
    // 从环境变量获取访问凭证
    static EnvironmentVariableCredentialsProvider credentialsProvider;
    static {
        try {
            credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public static String upload(String objectName, InputStream in){
        // 需要返回的url
        String url="";
        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, in);
            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);
            
            // 上传文件。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 获取上传文件的URL
            url = "https://"+bucketName+"."+endpointWithoutHttps+"/"+objectName;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (com.aliyun.oss.ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        
        return url;
    }
    
    public static boolean delete(String objectName) {
        // 创建OSSClient实例
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();
        
        try {
            // 删除文件
            ossClient.deleteObject(bucketName, objectName);
            return true; // 删除成功
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException: " + oe.getErrorMessage());
            return false; // 删除失败
        } catch (com.aliyun.oss.ClientException ce) {
            System.out.println("Caught a ClientException: " + ce.getMessage());
            return false; // 删除失败
        } finally {
            if (ossClient != null) {
                ossClient.shutdown(); // 关闭OSSClient
            }
        }
    }
    
    
    
}
