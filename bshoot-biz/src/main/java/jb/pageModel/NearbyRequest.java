package jb.pageModel;

/**
 * Created by Zhou Yibing on 2016/1/8.
 */
public class NearbyRequest extends PageHelper{
    private String userId;
    private String lgX;//登录经度
    private String lgY;//登录维度
    private int distance;//附近多少公里
    private String loginArea;
    private String lastLoginTime;
    private boolean isRand;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLgX() {
        return lgX;
    }

    public void setLgX(String lgX) {
        this.lgX = lgX;
    }

    public String getLgY() {
        return lgY;
    }

    public void setLgY(String lgY) {
        this.lgY = lgY;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    @Override
    public String toString() {
        return "NearbyRequest{" +
                "userId='" + userId + '\'' +
                ", lgX='" + lgX + '\'' +
                ", lgY='" + lgY + '\'' +
                ", distance=" + distance +
                ", loginArea='" + loginArea + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", isRand=" + isRand +
                '}';
    }
}
