package jb.service.impl;

import jb.bizmodel.GuideType;
import jb.pageModel.*;
import jb.service.HotGuideServiceI;
import jb.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Created by Zhou Yibing on 2015/10/22.
 */
public class HotGuideService extends  RecommendService implements HotGuideServiceI{

    @Override
    public List<Bshoot> guideExternalPage(String userId,int start) {
        //1.好友关注的人
        List<Bshoot> bshoots = new ArrayList<Bshoot>();
        Date oneMonthAgo = DateUtil.stringToDate(DateUtil.getDate(-30));
        AttentionRequest attentionRequest = new AttentionRequest(userId, oneMonthAgo,1*start,1);
        attentionRequest.setIsRand(true);
        List<Bshoot> friendCommonAtt = friendCommonAtt(attentionRequest,null,false);
        if(CollectionUtils.isNotEmpty(friendCommonAtt))
            bshoots.addAll(friendCommonAtt);

        //2.我评论/打赏过得人
        PraiseCommentRequest praiseCommentRequest = new PraiseCommentRequest(userId,oneMonthAgo,1*start,1);
        praiseCommentRequest.setIsRand(true);
        List<Bshoot> praiseComment = have_comment_praise(praiseCommentRequest,null,false);
        if(CollectionUtils.isNotEmpty(praiseComment))
            bshoots.addAll(praiseComment);


        //3.好友打赏过的人
        List<Bshoot> friendPraise = friendPraise(praiseCommentRequest,null,false);
        if(CollectionUtils.isNotEmpty(friendPraise))
            bshoots.addAll(friendPraise);

        //4.可能感兴趣的人
        //获得当前用户画像
        UserProfile userProfile = userProfileServiceImpl.get(userId);
        MaybeInterestRequest maybeInterestRequest = new MaybeInterestRequest(userId,userProfile.getLoginArea(),1*start,1);
        List<Bshoot> maybeInterest = mabyeInterest(maybeInterestRequest,null,false);
        if(CollectionUtils.isNotEmpty(maybeInterest))
            bshoots.add(maybeInterest.get(0));

        //5.可能认识的人
        UserMobilePersonRequest userMobilePersonRequest = new UserMobilePersonRequest(userId,1*start,1);
        List<Bshoot> maybeKnow = maybeKnow(userMobilePersonRequest,null,false);
        if(CollectionUtils.isNotEmpty(maybeKnow))
            bshoots.add(maybeKnow.get(0));
        return fillBshoot(bshoots);
    }

    @Override
    public List<Bshoot> guideInternalPage(String userId, Integer guideType, int start) {
        Date oneMonthAgo = DateUtil.stringToDate(DateUtil.getDate(-30));
        List<Bshoot> bshoots=null;
        if(guideType== GuideType.FRIEND_ATT.getId()){
            AttentionRequest attentionRequest = new AttentionRequest(userId, oneMonthAgo,6*start,6);
            bshoots = friendCommonAtt(attentionRequest, oneMonthAgo, true);
        }else if(guideType==GuideType.ME_COMMENT_PRAISE.getId()){
            PraiseCommentRequest praiseCommentRequest = new PraiseCommentRequest(userId,null,6*start,6);
            bshoots = have_comment_praise(praiseCommentRequest,oneMonthAgo,true);
        }else if(guideType==GuideType.FRIEND_PRAISE.getId()){
            PraiseCommentRequest praiseCommentRequest = new PraiseCommentRequest(userId,null,3*start,3);
            bshoots = friendPraise(praiseCommentRequest, oneMonthAgo, true);
        }else if(guideType==GuideType.MAYBE_INTEREST.getId()){
            UserProfile userProfile = userProfileServiceImpl.get(userId);
            MaybeInterestRequest maybeInterestRequest = new MaybeInterestRequest(userId,userProfile.getLoginArea(),3*start,6);
            bshoots = mabyeInterest(maybeInterestRequest, oneMonthAgo, true);
        }else if(guideType==GuideType.MAYBE_KNOW.getId()){
            UserMobilePersonRequest userMobilePersonRequest = new UserMobilePersonRequest(userId,6*start,6);
            bshoots = maybeKnow(userMobilePersonRequest, oneMonthAgo, true);
        }
        return fillBshoot(bshoots);
    }
}
