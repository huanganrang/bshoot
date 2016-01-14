package component.redis.model;

/**
 * Created by zhou on 2016/1/14.
 * 计数器类型
 */
public enum CounterType {
    PRAISE(0,"打赏"),COMMENT(1,"评论"),COLLECT(2,"收藏"),FORWARD(3,"转发"),READ(4,"阅读"),PLAY(5,"播放"),SHARE(6,"分享");
    private Integer type;
    private String description;

    CounterType(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType(){
        return this.type;
    }

    public String getDescription(){
        return this.description;
    }
}
