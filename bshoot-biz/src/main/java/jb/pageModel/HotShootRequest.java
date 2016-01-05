package jb.pageModel;

import java.util.Date;

/**
 * Created by zhou on 2016/1/5.
 */
public class HotShootRequest {

    private Date pubTime;
    private Integer praiseNum;
    private Integer start;
    private Integer fileType;
    private Integer rows;

    public HotShootRequest() {
    }

    public HotShootRequest(Date pubTime, Integer praiseNum, Integer start, Integer fileType, Integer rows) {
        this.pubTime = pubTime;
        this.praiseNum = praiseNum;
        this.start = start;
        this.fileType = fileType;
        this.rows = rows;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "HotShootRequest{" +
                "pubTime=" + pubTime +
                ", praiseNum=" + praiseNum +
                ", start=" + start +
                ", fileType=" + fileType +
                ", rows=" + rows +
                '}';
    }
}
