package jb.service;

import jb.pageModel.AttentionRequest;
import jb.pageModel.MaybeInterestRequest;
import jb.pageModel.PraiseCommentRequest;
import jb.pageModel.UserMobilePersonRequest;

import java.util.List;

/**
 * Created by Zhou Yibing on 2016/1/8.
 */
public interface UserRecommendServiceI {

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
    List<String> notAttMobilePerson(UserMobilePersonRequest userMobilePersonRequest);

    //我手机联系人的联系人
    List<String> mobilePersonPerson(UserMobilePersonRequest userMobilePersonRequest);

    //共同属性的人
    List<String>  mabyeInterest(MaybeInterestRequest maybeInterestRequest);
}
