package component.oss.service;

/**
 * Created by zhou on 2015/12/27.
 */
public class FileUploadResult {
    private String filePath;
    private String fileName;
    private String remotePath;
    private String eTag;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    @Override
    public String toString() {
        return "FileUploadResult{" +
                "filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", remotePath='" + remotePath + '\'' +
                ", eTag='" + eTag + '\'' +
                '}';
    }
}
