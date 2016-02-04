package component.redis.model;

/**
 * Created by zhou on 2016/1/14.
 * 计数器
 */
public class BshootCounter {

    private String id;
    @Counter(CounterType.PRAISE)
    private int praiseCount;
    @Counter(CounterType.COMMENT)
    private int commentCount;
    @Counter(CounterType.COLLECT)
    private int collectCount;
    @Counter(CounterType.FORWARD)
    private int forwardCount;
    @Counter(CounterType.READ)
    private int readCount;
    @Counter(CounterType.PLAY)
    private int playCount;
    @Counter(CounterType.SHARE)
    private int shareCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public int getForwardCount() {
        return forwardCount;
    }

    public void setForwardCount(int forwardCount) {
        this.forwardCount = forwardCount;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    @Override
    public String toString() {
        return "BshootCounter{" +
                "id='" + id + '\'' +
                ", praiseCount=" + praiseCount +
                ", commentCount=" + commentCount +
                ", collectCount=" + collectCount +
                ", forwardCount=" + forwardCount +
                ", readCount=" + readCount +
                ", playCount=" + playCount +
                ", shareCount=" + shareCount +
                '}';
    }
}
