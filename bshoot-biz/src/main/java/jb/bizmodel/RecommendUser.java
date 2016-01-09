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
    private String uType;
    private Integer isStar;
    private Integer isTarento;
    private List<String> attSquare;
    private String memberV;//级别
    private String headImage;//头像
    private String sex;//性别
    private List<String> job;//职业
    private Integer recommendType;//1:新人推荐 2:好友关注的人 3：我评论/打赏过的人 4：好友打赏过的人 5：可能感兴趣的人 6：可能认识的人 7：附近的人

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

    public Integer getRecommendType() {
        return recommendType;
    }

    public void setRecommendType(Integer recommendType) {
        this.recommendType = recommendType;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public Integer getIsStar() {
        return isStar;
    }

    public void setIsStar(Integer isStar) {
        this.isStar = isStar;
    }

    public Integer getIsTarento() {
        return isTarento;
    }

    public void setIsTarento(Integer isTarento) {
        this.isTarento = isTarento;
    }

    public List<String> getAttSquare() {
        return attSquare;
    }

    public void setAttSquare(List<String> attSquare) {
        this.attSquare = attSquare;
    }

    @Override
    public String toString() {
        return "RecommendUser{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", hobby=" + hobby +
                ", area='" + area + '\'' +
                ", bardian='" + bardian + '\'' +
                ", uType='" + uType + '\'' +
                ", isStar=" + isStar +
                ", isTarento=" + isTarento +
                ", attSquare=" + attSquare +
                ", memberV='" + memberV + '\'' +
                ", headImage='" + headImage + '\'' +
                ", sex='" + sex + '\'' +
                ", job=" + job +
                ", recommendType=" + recommendType +
                '}';
    }
}
