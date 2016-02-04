package component.redis.model;

/**
 * Created by Zhou Yibing on 2016/2/4.
 * 用户画像计数
 */
public class UserProfileCounter {
    private String id;
    @Counter(CounterType.ATT)
    private int attCount;
    @Counter(CounterType.BEATT)
    private int fansCount;
    @Counter(CounterType.PRAISE)
    private int praiseCount;

    public int getAttCount() {
        return attCount;
    }

    public void setAttCount(int attCount) {
        this.attCount = attCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserProfileCounter{" +
                "id='" + id + '\'' +
                ", attCount=" + attCount +
                ", fansCount=" + fansCount +
                ", praiseCount=" + praiseCount +
                '}';
    }
}
