package jb.pageModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhou on 2016/1/5.
 */
public class HotShootRequest extends PageHelper{

    private Date pubTime;
    private Integer praiseNum;
    private Integer fileType;
    private String hobby;
    private List<String> orderBy = new ArrayList<String>();

    public HotShootRequest() {
    }

    public HotShootRequest(Date pubTime, Integer praiseNum, Integer start, Integer fileType, String hobby, Integer rows) {
        this.pubTime = pubTime;
        this.praiseNum = praiseNum;
        setPage(start);
        this.fileType = fileType;
        this.hobby = hobby;
        setRows(rows);
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

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public List<String> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(List<String> orderBy) {
        this.orderBy = orderBy;
    }

    public HotShootRequest addOrder(String order){
        orderBy.add(order);
        return this;
    }

    @Override
    public String toString() {
        return "HotShootRequest{" +
                "pubTime=" + pubTime +
                ", praiseNum=" + praiseNum +
                ", fileType=" + fileType +
                ", hobby='" + hobby + '\'' +
                ", orderBy=" + orderBy +
                '}';
    }
}
