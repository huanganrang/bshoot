package jb.pageModel;

/**
 * Created by Zhou Yibing on 2016/1/8.
 */
public class SameCityRequest extends PageHelper{
    private String userId;
    private String loginArea;
    private boolean isRand;
    private String lastLoginTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginArea() {
        return loginArea;
    }

    public void setLoginArea(String loginArea) {
        this.loginArea = loginArea;
    }

    public boolean isRand() {
        return isRand;
    }

    public void setIsRand(boolean isRand) {
        this.isRand = isRand;
    }
}
