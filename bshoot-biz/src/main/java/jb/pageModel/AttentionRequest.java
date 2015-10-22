package jb.pageModel;

import java.util.Date;

/**
 * Created by Zhou Yibing on 2015/10/22.
 */
public class AttentionRequest extends PageHelper{
    private String userId;
    private Date attentionAfterDate;
    private boolean isRand;//是否随机

    public AttentionRequest() {
    }

    public AttentionRequest(String userId, Date attentionAfterDate,int page,int rows) {
        this.userId = userId;
        this.attentionAfterDate = attentionAfterDate;
        setPage(page);
        setRows(rows);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getAttentionAfterDate() {
        return attentionAfterDate;
    }

    public void setAttentionAfterDate(Date attentionAfterDate) {
        this.attentionAfterDate = attentionAfterDate;
    }

    public boolean isRand() {
        return isRand;
    }

    public void setIsRand(boolean isRand) {
        this.isRand = isRand;
    }

    @Override
    public String toString() {
        return "AttentionRequest{" +
                "userId='" + userId + '\'' +
                ", attentionAfterDate=" + attentionAfterDate +
                ", isRand=" + isRand +
                '}';
    }

    public static void main(String[] args){
        AttentionRequest attentionRequest = new AttentionRequest("1",new Date(),0,6);
        System.out.println(attentionRequest.getPage());
    }
}
