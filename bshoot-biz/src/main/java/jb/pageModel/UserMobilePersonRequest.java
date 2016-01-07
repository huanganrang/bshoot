package jb.pageModel;

/**
 * Created by Zhou Yibing on 2015/10/22.
 */
public class UserMobilePersonRequest extends  PageHelper{
    private String userId;
    private boolean isRand;

    public UserMobilePersonRequest() {
    }

    public UserMobilePersonRequest(String userId,int page,int rows) {
        this.userId = userId;
        setPage(page);
        setRows(rows);
    }

    public boolean isRand() {
        return isRand;
    }

    public void setIsRand(boolean isRand) {
        this.isRand = isRand;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserMobilePersonRequest{" +
                "userId='" + userId + '\'' +
                ", isRand=" + isRand +
                '}';
    }
}
