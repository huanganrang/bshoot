package jb.pageModel;

import java.util.Date;

/**
 * Created by Zhou Yibing on 2015/10/22.
 */
public class  PraiseCommentRequest extends PageHelper{
    private String userId;
    private Date praiseCommentAfterDate;
    private boolean isRand;

    public PraiseCommentRequest() {
    }

    public PraiseCommentRequest(String userId, Date praiseCommentAfterDate,int page,int rows) {
        this.userId = userId;
        this.praiseCommentAfterDate = praiseCommentAfterDate;
        setPage(page);
        setRows(rows);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getPraiseCommentAfterDate() {
        return praiseCommentAfterDate;
    }

    public void setPraiseCommentAfterDate(Date praiseCommentAfterDate) {
        this.praiseCommentAfterDate = praiseCommentAfterDate;
    }

    public boolean isRand() {
        return isRand;
    }

    public void setIsRand(boolean isRand) {
        this.isRand = isRand;
    }

    @Override
    public String toString() {
        return "PraiseCommentRequest{" +
                "userId='" + userId + '\'' +
                ", praiseCommentAfterDate=" + praiseCommentAfterDate +
                ", isRand=" + isRand +
                '}';
    }
}
