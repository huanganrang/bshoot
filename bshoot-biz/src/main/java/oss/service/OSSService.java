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
 * oss�ƴ洢������
 * note:�뱣֤�ļ�����Ψһ��keyΨһ
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
     * �ϴ��ļ�
     * @param file �����ļ�
     * @param directory �ϴ����ƶ˵��Ǹ�Ŀ¼,����ϴ����ļ�����Ὣ���ļ����������ļ��ϴ����ƶ�ͬĿ¼��
     * @throws Exception
     */
    public List<FileUploadResult> uploadFiles(File file,String directory) throws Exception{
           if(null==directory)  directory="";
            String path = file.getAbsolutePath();
            List<FileUploadResult> fileUploadResults = new ArrayList<FileUploadResult>();
            if (file.isDirectory()) {
                //1.1������ļ��У����ļ����µ������ļ��ϴ���������fileName,ȥ�ļ�������
                File[] files = file.listFiles();
                for(File ifile:files){
                    uploadFiles(ifile,directory+"/"+file.getName());
                }
            } else if (file.isFile()) {
                //1.2������ļ������ļ��ϴ��������ļ�key����Ϊ������fileName
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
     * ɾ��ָ���ļ��к��ļ����µ������ļ�
     * @param dir
     */
    public void deleteDirFiles(String dir){
        ossUtils.deleteDirFiles(OSSUtils.getOssProperty("OSS.BUCKETNAME"),dir);
    }
}
