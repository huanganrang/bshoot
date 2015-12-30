package solr.model;

import org.apache.solr.client.solrj.beans.Field;
import java.util.List;

/**
 * 用户实体类
 * Created by Zhou Yibing on 2015/12/29.
 */
public class UserEntity {
    @Field
    private String id;
    @Field
    private String utype;
    @Field
    private String nickname;
    @Field
    private String usex;
    @Field
    private String areaCode;
    @Field
    private String areaName;
    @Field
    private String birthday;
    @Field
    private String bardian;
    @Field
    private String member_v;
    @Field
    private long createTime;
    @Field
    private List<String> hobby;
    @Field
    private long lastLoginTime;
    @Field
    private long lastPublishTime;
    @Field
    private long fansNum;
    @Field
    private String login_location;
    @Field
    private List<String> mobile;
    @Field
    private int is_star;
    @Field
    private int is_tarento;
    @Field
    private long _reward;
    @Field
    private long _att;
    @Field
    private List<String> _square;
    @Field
    private List<String> job;
    @Field
    private String _location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsex() {
        return usex;
    }

    public void setUsex(String usex) {
        this.usex = usex;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBardian() {
        return bardian;
    }

    public void setBardian(String bardian) {
        this.bardian = bardian;
    }

    public String getMember_v() {
        return member_v;
    }

    public void setMember_v(String member_v) {
        this.member_v = member_v;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public long getLastPublishTime() {
        return lastPublishTime;
    }

    public void setLastPublishTime(long lastPublishTime) {
        this.lastPublishTime = lastPublishTime;
    }

    public long getFansNum() {
        return fansNum;
    }

    public void setFansNum(long fansNum) {
        this.fansNum = fansNum;
    }

    public String getLogin_location() {
        return login_location;
    }

    public void setLogin_location(String login_location) {
        this.login_location = login_location;
    }

    public List<String> getMobile() {
        return mobile;
    }

    public void setMobile(List<String> mobile) {
        this.mobile = mobile;
    }

    public int getIs_star() {
        return is_star;
    }

    public void setIs_star(int is_star) {
        this.is_star = is_star;
    }

    public int getIs_tarento() {
        return is_tarento;
    }

    public void setIs_tarento(int is_tarento) {
        this.is_tarento = is_tarento;
    }

    public long get_reward() {
        return _reward;
    }

    public void set_reward(long _reward) {
        this._reward = _reward;
    }

    public long get_att() {
        return _att;
    }

    public void set_att(long _att) {
        this._att = _att;
    }

    public List<String> get_square() {
        return _square;
    }

    public void set_square(List<String> _square) {
        this._square = _square;
    }

    public List<String> getJob() {
        return job;
    }

    public void setJob(List<String> job) {
        this.job = job;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", utype='" + utype + '\'' +
                ", nickname='" + nickname + '\'' +
                ", usex='" + usex + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", areaName='" + areaName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", bardian='" + bardian + '\'' +
                ", member_v='" + member_v + '\'' +
                ", createTime=" + createTime +
                ", hobby=" + hobby +
                ", lastLoginTime=" + lastLoginTime +
                ", lastPublishTime=" + lastPublishTime +
                ", fansNum=" + fansNum +
                ", login_location='" + login_location + '\'' +
                ", mobile=" + mobile +
                ", is_star=" + is_star +
                ", is_tarento=" + is_tarento +
                ", _reward=" + _reward +
                ", _att=" + _att +
                ", _square=" + _square +
                ", job=" + job +
                ", _location='" + _location + '\'' +
                '}';
    }
}
