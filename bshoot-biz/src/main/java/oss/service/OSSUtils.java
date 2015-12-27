package oss.service;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * oss操作工具类
 * Created by zhou on 2015/12/27.
 */
public class OSSUtils {

    public static Logger logger = LoggerFactory.getLogger(OSSUtils.class);
    private OSSClient ossClient = null;
    private String bucketName = null;
    private int timeout;
    private final static String END_POINT="http://oss-cn-shanghai.aliyuncs.com";
    private final static String JS_CONTENT_TYPE = "application/javascript";
    private final static String CSS_CONTENT_TYPE = "text/css";
    private final static String JPEG_CONTENT_TYPE = "image/jpeg";
    private final static String PNG_CONTENT_TYPE = "image/png";
    private final static String GIF_CONTENT_TYPE = "image/gif";
    private final static String APK_CONTENT_TYPE = "application/vnd.android.package-archive";
    private final static String IPA_CONTENT_TYPE = "application/vnd.iphone";
    private final static long cacheTime = 360 * 24 * 60 * 60 * 1000L;

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("oss");

    public static String getOssProperty(String key){return resourceBundle.getString(key);};

    public static int getOssPropertyInt(String key){
        String value = resourceBundle.getString(key);
        return value ==null?0:Integer.valueOf(value);
    }

    public static long getOssPropertyLong(String key){
        String value = resourceBundle.getString(key);
        return value ==null?0L:Long.parseLong(value);
    }

    protected OSSUtils(String accessKeyId,String accessKeySecret,String bucketName,int timeout){
        this(END_POINT,accessKeyId,accessKeySecret,bucketName,timeout);
    }

    protected  OSSUtils(String endpoint,String accessKeyId,String accessKeySecret,String bucketName,int timeout){
        this.bucketName = bucketName;
        this.timeout = timeout;
        this.ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);
    }

    /**
     * 设置bucket访问模式-公共读
     */
    public void setBucketAcl(){
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }


    public String uploadJSFile(String key, String filePath) {
        try {
            logger.debug("js=" + key);
            PutObjectResult uploadPartResult =  uploadFile(ossClient, bucketName, key, filePath, JS_CONTENT_TYPE);
            return uploadPartResult.getETag();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String uploadCSSFile(String key, String filePath) {
        try {
            logger.debug("css=" + key);
            PutObjectResult uploadPartResult = uploadFile(ossClient, bucketName,key, filePath, CSS_CONTENT_TYPE);
            return uploadPartResult.getETag();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String uploadJPGFile(String key, String filePath) {
        try {
            logger.debug("jpg=" + key);
            PutObjectResult uploadPartResult = uploadFile(ossClient, bucketName, key, filePath, JPEG_CONTENT_TYPE);
            return uploadPartResult.getETag();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String uploadPNGFile(String key, String filePath) {
        try {
            logger.debug("png=" + key);
            PutObjectResult uploadPartResult = uploadFile(ossClient, bucketName, key, filePath, PNG_CONTENT_TYPE);
            return uploadPartResult.getETag();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String uploadGIFFile(String key, String filePath) {
        try {
            logger.debug("gif=" + key);
            PutObjectResult uploadPartResult = uploadFile(ossClient, bucketName, key, filePath, GIF_CONTENT_TYPE);
            return uploadPartResult.getETag();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String uploadFile(String key, String filePath, String contentType) {
        try {
            logger.debug(contentType+"=" + key);
            PutObjectResult uploadPartResult = uploadFile(ossClient, bucketName, key, filePath, contentType);
            return uploadPartResult.getETag();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String uploadAPKFile(String key, String filePath) {
        try {
            PutObjectResult uploadPartResult = uploadFile(ossClient, bucketName, key, filePath, APK_CONTENT_TYPE);
            return uploadPartResult.getETag();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String uploadIPAFile(String key, String filePath) {
        try {
            PutObjectResult uploadPartResult =  uploadFile(ossClient, bucketName, key, filePath, IPA_CONTENT_TYPE);
            return uploadPartResult.getETag();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 批量文件上传
     * @param map key ,文件名
     * @param contentType 文件类型
     */
    public void uploadFile(Map<String,String> map,String contentType){
        Map<String,String> uploadResult = new HashMap<String, String>();
        try{
            for(Map.Entry<String,String> entry:map.entrySet()){
                PutObjectResult uploadPartResult = uploadFile(ossClient,bucketName,entry.getKey(),entry.getValue(),contentType);
                uploadResult.put(entry.getKey(), uploadPartResult.getETag());
            }
        }catch(Exception e){
            logger.error("upload file failed."+e.getMessage());
        }
    }

    public void deleteFile(String key){
        ossClient.deleteObject(bucketName,key);
        logger.debug("deleted... bucketName="+bucketName+",key="+key);
    }

    private PutObjectResult uploadFile(OSSClient ossClient, String bucketName, String key, String filePath, String contentType) throws OSSException,ClientException,IOException {
        logger.debug("uploading... bucketName="+bucketName+",key="+key+",filePath="+filePath
        +",contentType="+contentType);
        File file = new File(filePath);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        Date now = new Date();
        objectMetadata.setContentLength(file.length());
        objectMetadata.setContentType(contentType);
        objectMetadata.setLastModified(now);
        objectMetadata.setCacheControl("max-age="+(cacheTime/1000));
        objectMetadata.setExpirationTime(new Date(now.getTime()+cacheTime));
        if (contentType.equals("eot") || contentType.equals("svg") || contentType.equals("ttf")
                || contentType.equals("woff"))
            objectMetadata.setHeader("Access-Control-Allow-Origin", "*");
        InputStream input = new FileInputStream(file);
        PutObjectResult putObjectResult = ossClient.putObject(bucketName, key, input, objectMetadata);
        input.close();

        logger.debug("uploaded... bucketName="+bucketName+",key="+key+",filePath="+filePath
                +",contentType="+contentType);
        return putObjectResult;
    }

    public static String getFileType(File file){
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }

    private File downloadFile(OSSClient client, String bucketName, String key, String filePath) throws OSSException,
            ClientException {
        File file = new File(filePath);
        client.getObject(new GetObjectRequest(bucketName, key), file);
        return file;
    }

    public InputStream getObject(String key) throws IOException {
        return ossClient.getObject(bucketName, key).getObjectContent();
    }

    /**
     * 移动文件
     *
     * @param srcKey
     * @param destKey
     */
    public CopyObjectResult moveObject(String srcKey, String destKey) {
        CopyObjectResult result = ossClient.copyObject(bucketName, srcKey, bucketName, destKey);
        ossClient.deleteObject(bucketName, srcKey);
        logger.debug("move success... bucketName= " + bucketName + ", srcKey=" + srcKey + ", destKey=" + destKey
                + ",ETag= " + result.getETag() + ",LastModified= " + result.getLastModified());
        return result;
    }

    public CopyObjectResult copyObject(String srcKey, String destKey) {
        CopyObjectResult result = ossClient.copyObject(bucketName, srcKey, bucketName, destKey);
        logger.debug("move success... bucketName= " + bucketName + ", srcKey=" + srcKey + ", destKey=" + destKey
                + ",ETag= " + result.getETag() + ",LastModified= " + result.getLastModified());
        return result;
    }

    /**
     *动态生成经过签名的URL
     * @param key
     * @param timeout
     * @return
     */
    public String getUrl(String key,int timeout){
        return ossClient.generatePresignedUrl(bucketName,key,new Date(new Date().getTime()+timeout)).toString();
    }

    public String getUrl(String key){
        return getUrl(key, timeout);
    }

    /**
     * 删除Bucket下的所有文件
     *只删除js,css,img类型文件
     * @param bucketName
     */
    public void deleteDirFiles(String bucketName,String specialDir) {
        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        // List Objects
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);

        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            if (objectSummary.getKey().startsWith(specialDir)) {
                System.out.println(objectSummary.getKey());
                deleteFile(objectSummary.getKey());
            }
        }
        // 遍历所有CommonPrefix
        System.out.println("CommonPrefixs:");
        for (String commonPrefix : listing.getCommonPrefixes()) {
            System.out.println(commonPrefix);
        }
    }
}
