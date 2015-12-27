package oss.service;

import com.aliyun.oss.model.CopyObjectResult;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * oss云存储服务类
 * note:请保证文件名称唯一，key唯一
 * Created by zhou on 2015/12/27.
 */
public class OSSService {

    public static Logger logger = LoggerFactory.getLogger(OSSUtils.class);
    private static OSSUtils ossUtils = null;

    @PostConstruct
    private void init(){
       ossUtils = new OSSUtils(OSSUtils.getOssProperty("OSS.ACCESSKEYID"),OSSUtils.getOssProperty("OSS.ACCESSKEYSECRET"),
               OSSUtils.getOssProperty("OSS.BUCKETNAME"),OSSUtils.getOssPropertyInt("OSS.TIMEOUT"));
    }

   public static void main(String[] args){
       OSSService ossService = new OSSService();
       ossService.init();
       try {
           //ossService.uploadFiles(new File("E:\\temp"),"audio");
           //CopyObjectResult fileCopyResult = ossService.moveFile("audio/temp/a/621717931.aac", "video/62171796.aac");
           //System.out.print(fileCopyResult);
           //CopyObjectResult fileCopyResult = ossService.copyFile("audio/temp/191769963.aac", "video/62171797.aac");
           //System.out.print(fileCopyResult);
           //ossService.deleteFile("video/62171796.aac");
           //ossService.deleteFile("audio/temp");
           ossService.deleteDirFiles("audio");
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

    /**
     * 上传文件
     * @param file 本地文件
     * @param directory 上传到云端的那个目录,如果上传的文件夹则会将该文件夹下所有文件上传到云端同目录下
     * @throws Exception
     */
    public List<FileUploadResult> uploadFiles(File file,String directory) throws Exception{
           if(null==directory)  directory="";
            String path = file.getAbsolutePath();
            List<FileUploadResult> fileUploadResults = new ArrayList<FileUploadResult>();
            if (file.isDirectory()) {
                //1.1如果是文件夹，将文件夹下的所有文件上传，并忽略fileName,去文件的名称
                File[] files = file.listFiles();
                for(File ifile:files){
                    uploadFiles(ifile,directory+"/"+file.getName());
                }
            } else if (file.isFile()) {
                //1.2如果是文件，则将文件上传，并将文件key命名为给定的fileName
                String eTag = ossUtils.uploadFile(directory+"/"+file.getName(), path, OSSUtils.getFileType(file));
                if(StringUtils.isNotEmpty(eTag)){
                    FileUploadResult fileUploadResult = new FileUploadResult();
                    fileUploadResult.seteTag(eTag);
                    fileUploadResult.setFileName(file.getName());
                    fileUploadResult.setFilePath(path);
                    fileUploadResult.setRemotePath(directory+"/"+file.getName());
                    fileUploadResults.add(fileUploadResult);
                }
            }
        return  fileUploadResults;
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
