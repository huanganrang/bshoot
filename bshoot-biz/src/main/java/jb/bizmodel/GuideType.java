package jb.bizmodel;

/**
 * Created by Zhou Yibing on 2016/1/8.
 */
public enum GuideType {

    NEW_USER(1,"新人推荐"),FRIEND_ATT(2,"好友关注的人"),ME_COMMENT_PRAISE(3,"我评论/打赏过的人"),FRIEND_PRAISE(4,"好友打赏过的人"),MAYBE_INTEREST(5,"可能感兴趣的人"),MAYBE_KNOW(6,"可能认识的人"),NEARBY_PEOPLE(7,"附近的人");

    private Integer id;
    private String name;

    GuideType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
