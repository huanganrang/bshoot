package solr.model;

import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.common.SolrDocumentList;

import java.util.List;

/**
 * 动态实体类
 * Created by Zhou Yibing on 2015/12/29.
 */
public class BsEntity {

    @Field
    private String id;
    @Field
    private long publishTime;
    @Field
    private String userId;
    @Field
    private long praise_num;
    @Field
    private long comment_num;
    @Field
    private long read_num;
    @Field
    private String pub_location;
    @Field
    private int file_type;
    @Field
    private long forward_num;
    @Field
    private String _square;
    @Field
    private String _text;
    @Field
    private long reward_num;
    @Field
    private long _shareNum;
    @Field
    private long favorigte_num;
    @Field
    private int isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public long getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(long praise_num) {
        this.praise_num = praise_num;
    }

    public long getComment_num() {
        return comment_num;
    }

    public void setComment_num(long comment_num) {
        this.comment_num = comment_num;
    }

    public long getRead_num() {
        return read_num;
    }

    public void setRead_num(long read_num) {
        this.read_num = read_num;
    }

    public String getPub_location() {
        return pub_location;
    }

    public void setPub_location(String pub_location) {
        this.pub_location = pub_location;
    }

    public int getFile_type() {
        return file_type;
    }

    public void setFile_type(int file_type) {
        this.file_type = file_type;
    }

    public long getForward_num() {
        return forward_num;
    }

    public void setForward_num(long forward_num) {
        this.forward_num = forward_num;
    }

    public String get_square() {
        return _square;
    }

    public void set_square(String _square) {
        this._square = _square;
    }

    public String get_text() {
        return _text;
    }

    public void set_text(String _text) {
        this._text = _text;
    }

    public long getReward_num() {
        return reward_num;
    }

    public void setReward_num(long reward_num) {
        this.reward_num = reward_num;
    }

    public long get_shareNum() {
        return _shareNum;
    }

    public void set_shareNum(long _shareNum) {
        this._shareNum = _shareNum;
    }

    public long getFavorigte_num() {
        return favorigte_num;
    }

    public void setFavorigte_num(long favorigte_num) {
        this.favorigte_num = favorigte_num;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "BsEntity{" +
                "id='" + id + '\'' +
                ", publishTime=" + publishTime +
                ", userId='" + userId + '\'' +
                ", praise_num=" + praise_num +
                ", comment_num=" + comment_num +
                ", read_num=" + read_num +
                ", pub_location='" + pub_location + '\'' +
                ", file_type=" + file_type +
                ", forward_num=" + forward_num +
                ", _square='" + _square + '\'' +
                ", _text='" + _text + '\'' +
                ", reward_num=" + reward_num +
                ", _shareNum=" + _shareNum +
                ", favorigte_num=" + favorigte_num +
                ", isDelete=" + isDelete +
                '}';
    }
}
