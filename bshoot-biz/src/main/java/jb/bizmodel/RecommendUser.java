package jb.bizmodel;

import java.util.List;

/**
 * Created by zhou on 2016/1/1.
 */
public class RecommendUser {
    private String id;//用户id
    private String nickname;//用户昵称
    private List<String> hobby;//兴趣
    private String area;//地区
    private String bardian;//个性签名
    private String memberV;//级别
    private String headImage;//头像
    private String sex;//性别
    private List<String> job;//职业

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBardian() {
        return bardian;
    }

    public void setBardian(String bardian) {
        this.bardian = bardian;
    }

    public String getMemberV() {
        return memberV;
    }

    public void setMemberV(String memberV) {
        this.memberV = memberV;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<String> getJob() {
        return job;
    }

    public void setJob(List<String> job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "RecommendUser{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", hobby=" + hobby +
                ", area='" + area + '\'' +
                ", bardian='" + bardian + '\'' +
                ", memberV='" + memberV + '\'' +
                ", headImage='" + headImage + '\'' +
                ", sex='" + sex + '\'' +
                ", job=" + job +
                '}';
    }
}
