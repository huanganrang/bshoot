package jb.service.impl;

import jb.pageModel.*;
import jb.service.HotGuideServiceI;
import jb.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Zhou Yibing on 2015/10/22.
 */
public class HotGuideService extends  RecommendService implements HotGuideServiceI{

    @Override
    public List<Bshoot> guideExternalPage(String userId,int start) {
        //1.好友关注的人
        List<String> userIds = new ArrayList<String>();
        Date oneMonthAgo = DateUtil.stringToDate(DateUtil.getDate(-30));
        AttentionRequest attentionRequest = new AttentionRequest(userId, oneMonthAgo,1*start,1);
        attentionRequest.setIsRand(true);
        List<String> friendCommonAtt = friendCommonAtt(attentionRequest);
        if(CollectionUtils.isNotEmpty(friendCommonAtt))
            userIds.addAll(friendCommonAtt);

        //2.我评论/打赏过得人
        PraiseCommentRequest praiseCommentRequest = new PraiseCommentRequest(userId,oneMonthAgo,1*start,1);
        praiseCommentRequest.setIsRand(true);
        List<String> praiseComment = have_comment_praise(praiseCommentRequest);
        if(CollectionUtils.isNotEmpty(praiseComment))
            userIds.addAll(praiseComment);


        //3.好友打赏过的人
        List<String> friendPraise = friendPraise(praiseCommentRequest);
        if(CollectionUtils.isNotEmpty(friendPraise))
            userIds.addAll(friendPraise);

        //4.可能感兴趣的人
        //获得当前用户画像
        UserProfile userProfile = userProfileServiceImpl.get(userId);
        MaybeInterestRequest maybeInterestRequest = new MaybeInterestRequest(userId,userProfile.getLoginArea(),1*start,1);
        List<String> maybeInterest = mabyeInterest(maybeInterestRequest);
        if(CollectionUtils.isNotEmpty(maybeInterest))
            userIds.addAll(maybeInterest);

        //5.可能认识的人
        UserMobilePersonRequest userMobilePersonRequest = new UserMobilePersonRequest(userId,1*start,1);
        List<String> maybeKnow = maybeKnow(userMobilePersonRequest);
        if(CollectionUtils.isNotEmpty(maybeKnow))
            userIds.addAll(maybeKnow);
        //TODO
        HotShootRequest hotShootRequest = new HotShootRequest(null,null,start,null,null,50);
        List<Bshoot> bshoots = bshootServiceImpl.getHotBshoots(hotShootRequest);
        fillBshoot(bshoots);
        return null;
    }

    @Override
    public List<Bshoot> guideInternalPage(String userId, String guideType, int start) {
        return null;
    }
}
