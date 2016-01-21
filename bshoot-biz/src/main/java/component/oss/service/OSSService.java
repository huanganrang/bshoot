package component.oss.service;

import com.aliyun.oss.model.CopyObjectResult;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.util.Daemon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * oss云存储服务类
 * note:请保证文件名称唯一，key唯一
 * Created by zhou on 2015/12/27.
 */
public class OSSService {

    public static Logger logger = LoggerFactory.getLogger(OSSUtils.class);
    private static OSSUtils ossUtils = null;
    private static OSSService instance;
    @PostConstruct
    private void init(){
       ossUtils = new OSSUtils(OSSUtils.getOssProperty("OSS.ACCESSKEYID"),OSSUtils.getOssProperty("OSS.ACCESSKEYSECRET"),
               OSSUtils.getOssProperty("OSS.BUCKETNAME"),OSSUtils.getOssPropertyInt("OSS.TIMEOUT"));
    }

    public static OSSService getInstance(){
        if(instance == null){
            synchronized (OSSService.class){
                if(instance == null){
                    instance = new OSSService();
                    instance.init();
                }
            }
        }
        return instance;
    }
   public static void main(String[] args){
       OSSService ossService = new OSSService();
       ossService.init();
       try {
           List<FileUploadResult> fileUploadResults = new ArrayList<FileUploadResult>();
           ossService.uploadFiles(new File("E:\\temp"),"audio",fileUploadResults);
           for(FileUploadResult fileUploadResult:fileUploadResults){
               System.out.println(fileUploadResult);
           }
           //CopyObjectResult fileCopyResult = ossService.moveFile("audio/temp/a/621717931.aac", "video/62171796.aac");
           //System.out.print(fileCopyResult);
           //CopyObjectResult fileCopyResult = ossService.copyFile("audio/temp/191769963.aac", "video/62171797.aac");
           //System.out.print(fileCopyResult);
           //ossService.deleteFile("video/62171796.aac");
           //ossService.deleteFile("audio/temp");
           //ossService.deleteDirFiles("audio");
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

    public  List<FileUploadResult>  uploadFile(File file,String directory)throws Exception{
        List<FileUploadResult> fileUploadResults = new ArrayList<FileUploadResult>();
        uploadFiles(file,directory,fileUploadResults);
        return fileUploadResults;
    }

    /**
     * 上传文件
     * @param file 本地文件
     * @param directory 上传到云端的那个目录,如果上传的文件夹则会将该文件夹下所有文件上传到云端同目录下
     * @throws Exception
     */
    public void uploadFiles(File file,String directory, List<FileUploadResult> fileUploadResults) throws Exception{
           if(null==fileUploadResults) throw new NullPointerException("the filUploadResults can not be null");
           if(null==directory)  directory="";
            String path = file.getAbsolutePath();
            if (file.isDirectory()) {
                //1.1如果是文件夹，将文件夹下的所有文件上传，并忽略fileName,去文件的名称
                File[] files = file.listFiles();
                for(File ifile:files){
                    uploadFiles(ifile, directory + "/" + file.getName(), fileUploadResults);
                }
            } else if (file.isFile()) {
                //1.2如果是文件，则将文件上传，并将文件key命名为给定的fileName
                String eTag = ossUtils.uploadFile(directory+"/"+file.getName(), path, OSSUtils.getFileType(file));
                System.out.println(eTag);
                if(StringUtils.isNotEmpty(eTag)){
                    FileUploadResult fileUploadResult = new FileUploadResult();
                    fileUploadResult.seteTag(eTag);
                    fileUploadResult.setFileName(file.getName());
                    fileUploadResult.setFilePath(path);
                    fileUploadResult.setRemotePath(directory+"/"+file.getName());
                    fileUploadResults.add(fileUploadResult);
                }
            }
    }

    public String uploadFile(String fileName,InputStream input){
        PutObjectResult putObjectResult = ossUtils.uploadStream(fileName,input);
        return putObjectResult.getETag();
    }
    public void deleteFile(String key) throws Exception{
        ossUtils.deleteFile(key);
    }

    public InputStream getFile(String key) throws IOException {
        return ossUtils.getObject(key);
    }

    public CopyObjectResult moveFile(String srcKey,String destKey)throws Exception{
      return  ossUtils.moveObject(srcKey, destKey);
    }

    public CopyObjectResult copyFile(String srcKey,String destKey)throws Exception{
        return ossUtils.copyObject(srcKey,destKey);
    }

    /**
     * 删除指定文件夹和文件夹下的所有文件
     * @param dir
     */
    public void deleteDirFiles(String dir){
        ossUtils.deleteDirFiles(OSSUtils.getOssProperty("OSS.BUCKETNAME"),dir);
    }
}
