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
    private long id;
    @Field
    private long publishTime;
    @Field
    private long userId;
    @Field
    private String nickname;
    @Field
    private long praise_num;
    @Field
    private long comment_num;
    @Field
    private long read_num;
    @Field
    private String pub_location;
    @Field
    private int hasVideo;
    @Field
    private int hasImg;
    @Field
    private int hasAudio;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public int getHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(int hasVideo) {
        this.hasVideo = hasVideo;
    }

    public int getHasImg() {
        return hasImg;
    }

    public void setHasImg(int hasImg) {
        this.hasImg = hasImg;
    }

    public int getHasAudio() {
        return hasAudio;
    }

    public void setHasAudio(int hasAudio) {
        this.hasAudio = hasAudio;
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
                "id=" + id +
                ", publishTime=" + publishTime +
                ", userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", praise_num=" + praise_num +
                ", comment_num=" + comment_num +
                ", read_num=" + read_num +
                ", pub_location='" + pub_location + '\'' +
                ", hasVideo=" + hasVideo +
                ", hasImg=" + hasImg +
                ", hasAudio=" + hasAudio +
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
