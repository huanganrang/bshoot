package jb.service;

import jb.pageModel.*;

import java.util.List;

/**
 * Created by Zhou Yibing on 2016/1/8.
 */
public interface CommonRecommendServiceI {

    //好友打共同赏过的人
    List<String> friendPraisedUser(PraiseCommentRequest praiseCommentRequest);

    //单一好友打赏过的人
    List<String> singleFriendPraisedUser(PraiseCommentRequest praiseCommentRequest);

    //好友共同关注的人
    List<String> friendCommonAttUser(AttentionRequest attentionRequest);

    //单一好友关注的人
    List<String> singleFriendAttUser(AttentionRequest attentionRequest);

    //我评论打赏过的人
    List<String> meCommentPraisedUser(PraiseCommentRequest praiseCommentRequest);

    //我未关注的手机联系人
    List<String> notAttMobileUser(UserMobilePersonRequest userMobilePersonRequest);

    //我手机联系人的联系人
    List<String> mobileUserUser(UserMobilePersonRequest userMobilePersonRequest);

    //共同属性的人
    List<String>  mabyeInterestedUser(MaybeInterestRequest maybeInterestRequest);

    //附近的人
    List<String> nearbyUser(NearbyRequest nearbyRequest);

    //同城的人
    List<String> sameCityUser(SameCityRequest sameCityRequest);
}
