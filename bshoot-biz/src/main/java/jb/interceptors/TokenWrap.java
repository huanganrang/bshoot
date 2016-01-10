package jb.interceptors;

/**
 * Created by john on 16/1/10.
 */
public class TokenWrap  implements java.io.Serializable{
    private String tokenId;
    private String name;
    private String uid;
    private long ctime;//上一次使用时间
    private TokenManage tokenManage;
    public TokenWrap(String tokenId,String uid,String name,TokenManage tokenManage){
        this.tokenId = tokenId;
        this.uid = uid;
        this.name = name;
        this.tokenManage =tokenManage;
    }
    public void retime(){
        if(tokenManage.enableRedis){
            tokenManage.refreshRedisToken(tokenId);
        }else {
            ctime = System.currentTimeMillis();
        }
    }
    public String getTokenId() {
        return tokenId;
    }
    public long getCtime() {
        return ctime;
    }
    public String getName() {
        return name;
    }
    public String getUid() {
        return uid;
    }
}