package jb.pageModel;

/**
 * Created by zhou on 2016/1/7.
 */
public class MaybeInterestRequest extends PageHelper{
    private String userId;
    private boolean isRand;
    private String loginArea;

    public MaybeInterestRequest() {
    }

    public MaybeInterestRequest(String userId, String loginArea,int page,int rows) {
        this.userId = userId;
        this.loginArea = loginArea;
        setPage(page);
        setRows(rows);
    }

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

    @Override
    public String toString() {
        return "MaybeInterestRequest{" +
                "userId='" + userId + '\'' +
                ", isRand=" + isRand +
                ", loginArea='" + loginArea + '\'' +
                '}';
    }
}
