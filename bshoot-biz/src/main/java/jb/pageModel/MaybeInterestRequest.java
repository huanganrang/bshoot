package jb.pageModel;

import java.util.Date;
import java.util.List;

/**
 * Created by zhou on 2016/1/7.
 */
public class MaybeInterestRequest extends PageHelper{
    private String userId;
    private int propertyCount;//共同属性数量
    private List<String> hobby;//用户兴趣
    private List<String> attSquare;//用户关注的吧
    private boolean isRand;
    private String loginArea;//登录地区
    private String lastLoginTime;
    private String loginLocation;//登录地点

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isRand() {
        return isRand;
    }

    public void setIsRand(boolean isRand) {
        this.isRand = isRand;
    }

    public String getLoginArea() {
        return loginArea;
    }

    public void setLoginArea(String loginArea) {
        this.loginArea = loginArea;
    }

    public int getPropertyCount() {
        return propertyCount;
    }

    public void setPropertyCount(int propertyCount) {
        this.propertyCount = propertyCount;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public List<String> getAttSquare() {
        return attSquare;
    }

    public void setAttSquare(List<String> attSquare) {
        this.attSquare = attSquare;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }

    @Override
    public String toString() {
        return "MaybeInterestRequest{" +
                "userId='" + userId + '\'' +
                ", propertyCount=" + propertyCount +
                ", hobby=" + hobby +
                ", attSquare=" + attSquare +
                ", isRand=" + isRand +
                ", loginArea='" + loginArea + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", loginLocation='" + loginLocation + '\'' +
                '}';
    }
}
