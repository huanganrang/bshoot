package jb.bizmodel;

import java.util.List;

/**
 * 动态 响应信息
 * Created by Zhou Yibing on 2015/10/21.
 */
public class Dynamic {

    private String userId;//用户id
    private String id;//动态id
    private String publishFrom;//发布来源
    private String iconImg;//用户头像
    private String userLevel;//用户等级
    private String publishTime;//发布时间
    private String nickName;//用户昵称
    private List<String> hobby;//用户兴趣
    private String bshootContent;//动态内容
    private String bsStream;//文件地址
    private int fileType;//文件类型
    private String bsIcon;//图标地址
    private int forwardNum;//转发数
    private int commentNum;//评论数
    private int praiseNum;//打赏/点赞数

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public String getBshootContent() {
        return bshootContent;
    }

    public void setBshootContent(String bshootContent) {
        this.bshootContent = bshootContent;
    }

    public String getBsStream() {
        return bsStream;
    }

    public void setBsStream(String bsStream) {
        this.bsStream = bsStream;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getBsIcon() {
        return bsIcon;
    }

    public void setBsIcon(String bsIcon) {
        this.bsIcon = bsIcon;
    }

    public int getForwardNum() {
        return forwardNum;
    }

    public void setForwardNum(int forwardNum) {
        this.forwardNum = forwardNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public String getPublishFrom() {
        return publishFrom;
    }

    public void setPublishFrom(String publishFrom) {
        this.publishFrom = publishFrom;
    }

    @Override
    public String toString() {
        return "Dynamic{" +
                "userId='" + userId + '\'' +
                ", id='" + id + '\'' +
                ", publishFrom='" + publishFrom + '\'' +
                ", iconImg='" + iconImg + '\'' +
                ", userLevel='" + userLevel + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", nickName='" + nickName + '\'' +
                ", hobby=" + hobby +
                ", bshootContent='" + bshootContent + '\'' +
                ", bsStream='" + bsStream + '\'' +
                ", fileType=" + fileType +
                ", bsIcon='" + bsIcon + '\'' +
                ", forwardNum=" + forwardNum +
                ", commentNum=" + commentNum +
                ", praiseNum=" + praiseNum +
                '}';
    }
}
