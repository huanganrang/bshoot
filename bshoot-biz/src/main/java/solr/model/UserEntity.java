package solr.model;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 用户实体类
 * Created by Zhou Yibing on 2015/12/29.
 */
public class UserEntity {
    private long id;
    private int utype;
    private String nickname;
    private int usex;
    private int areaCode;
    private String areaName;
    private long birthday;
    private String bardian;
    private int member_v;
    private long createTime;
    private List<String> hobby;
    private long lastLoginTime;
    private long lastPublishTime;
    private long fansNum;
    private String login_location;
    private List<String> mobile;
    private int is_star;
    private long _reward;
    private long _att;
    private List<String> _square;
    private List<String> job;
    private String _location;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUtype() {
        return utype;
    }

    public void setUtype(int utype) {
        this.utype = utype;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUsex() {
        return usex;
    }

    public void setUsex(int usex) {
        this.usex = usex;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getBardian() {
        return bardian;
    }

    public void setBardian(String bardian) {
        this.bardian = bardian;
    }

    public int getMember_v() {
        return member_v;
    }

    public void setMember_v(int member_v) {
        this.member_v = member_v;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    public int getIs_star() {
        return is_star;
    }

    public void setIs_star(int is_star) {
        this.is_star = is_star;
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

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public List<String> getMobile() {
        return mobile;
    }

    public void setMobile(List<String> mobile) {
        this.mobile = mobile;
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
                "id=" + id +
                ", utype=" + utype +
                ", nickname='" + nickname + '\'' +
                ", usex=" + usex +
                ", areaCode=" + areaCode +
                ", areaName='" + areaName + '\'' +
                ", birthday=" + birthday +
                ", bardian='" + bardian + '\'' +
                ", member_v=" + member_v +
                ", createTime=" + createTime +
                ", hobby='" + hobby + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", lastPublishTime=" + lastPublishTime +
                ", fansNum=" + fansNum +
                ", login_location='" + login_location + '\'' +
                ", mobile='" + mobile + '\'' +
                ", is_star=" + is_star +
                ", _reward=" + _reward +
                ", _att=" + _att +
                ", _square='" + _square + '\'' +
                ", job='" + job + '\'' +
                ", _location='" + _location + '\'' +
                '}';
    }
}