package jb.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import jb.bizmodel.GuideType;
import jb.pageModel.*;
import jb.service.CommonRecommendServiceI;
import jb.service.HotGuideServiceI;
import jb.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Zhou Yibing on 2015/10/22.
 */
@Service
public class HotGuideService extends  RecommendService implements HotGuideServiceI{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<Bshoot> guideExternalPage(String userId,Integer start,Integer rows) {
        //1.好友关注的人
        List<Bshoot> bshoots = new ArrayList<Bshoot>();
        Date oneMonthAgo = DateUtil.stringToDate(DateUtil.getDate(-30));
        AttentionRequest attentionRequest = new AttentionRequest(userId, oneMonthAgo,rows*start,rows);
        attentionRequest.setIsRand(true);
        List<String> friendCommonAtt = friendCommonAtt(attentionRequest);
        if(CollectionUtils.isNotEmpty(friendCommonAtt)) {
            bshoots.addAll(getLastBshoot(friendCommonAtt,null, GuideType.FRIEND_ATT.getId()));
        }

        //2.我评论/打赏过得人
        PraiseCommentRequest praiseCommentRequest = new PraiseCommentRequest(userId,oneMonthAgo,rows*start,rows);
        praiseCommentRequest.setIsRand(true);
        List<String> praiseComment = super.meCommentPraisedUser(praiseCommentRequest);
        if(CollectionUtils.isNotEmpty(praiseComment)) {
            bshoots.addAll(getLastBshoot(praiseComment, null, GuideType.ME_COMMENT_PRAISE.getId()));
        }

        //3.好友打赏过的人
        List<String> friendPraise = friendPraise(praiseCommentRequest);
        if(CollectionUtils.isNotEmpty(friendPraise))
            bshoots.addAll(getLastBshoot(friendPraise, null, GuideType.FRIEND_PRAISE.getId()));

        //4.可能感兴趣的人
        //获得当前用户画像
        UserProfile userProfile = userProfileServiceImpl.get(userId);
        MaybeInterestRequest maybeInterestRequest = new MaybeInterestRequest();
        maybeInterestRequest.setUserId(userId);
        maybeInterestRequest.setLoginArea(userProfile.getLoginArea());
        maybeInterestRequest.setPage(rows*start);
        maybeInterestRequest.setLastLoginTime(DateUtil.getDate(-1, DateUtil.DATETIME_FORMAT));
        maybeInterestRequest.setLgX(userProfile.getLgX());
        maybeInterestRequest.setLgY(userProfile.getLgY());
        maybeInterestRequest.setRows(rows);
        List<String> maybeInterest = mabyeInterest(maybeInterestRequest);
        if(CollectionUtils.isNotEmpty(maybeInterest)) {
            bshoots.addAll(getLastBshoot(maybeInterest.subList(0,1), null, GuideType.MAYBE_INTEREST.getId()));
        }

        //5.可能认识的人
        UserMobilePersonRequest userMobilePersonRequest = new UserMobilePersonRequest(userId,rows*start,rows);
        List<String> maybeKnow = maybeKnow(userMobilePersonRequest);
        if(CollectionUtils.isNotEmpty(maybeKnow))
            bshoots.addAll(getLastBshoot(maybeKnow.subList(0,1), null, GuideType.MAYBE_KNOW.getId()));
        return fillBshoot(bshoots);
    }

    @Override
    public List<Bshoot> guideInternalPage(String userId, Integer guideType, Integer start,Integer rows) {
        Date oneMonthAgo = DateUtil.stringToDate(DateUtil.getDate(-30));
        List<Bshoot> bshoots= new ArrayList<Bshoot>();
        if(guideType== GuideType.FRIEND_ATT.getId()){
            AttentionRequest attentionRequest = new AttentionRequest(userId, null,rows*start,rows);
            List<String> friendCommonAtt = friendCommonAtt(attentionRequest);
            if(CollectionUtils.isNotEmpty(friendCommonAtt)) {
                bshoots.addAll(getMaxPraiseBshoot(friendCommonAtt, oneMonthAgo, GuideType.FRIEND_ATT.getId()));
            }
        }else if(guideType==GuideType.ME_COMMENT_PRAISE.getId()){
            PraiseCommentRequest praiseCommentRequest = new PraiseCommentRequest(userId,null,rows*start,rows);
            List<String> praiseComment = super.meCommentPraisedUser(praiseCommentRequest);
            if(CollectionUtils.isNotEmpty(praiseComment)) {
                bshoots.addAll(getMaxPraiseBshoot(praiseComment, null, GuideType.ME_COMMENT_PRAISE.getId()));
            }
        }else if(guideType==GuideType.FRIEND_PRAISE.getId()){
            PraiseCommentRequest praiseCommentRequest = new PraiseCommentRequest(userId,null,rows*start,rows);
            List<String> friendPraise = friendPraise(praiseCommentRequest);
            if(CollectionUtils.isNotEmpty(friendPraise))
                bshoots.addAll(getMaxPraiseBshoot(friendPraise, oneMonthAgo, GuideType.FRIEND_PRAISE.getId()));
        }else if(guideType==GuideType.MAYBE_INTEREST.getId()){
            UserProfile userProfile = userProfileServiceImpl.get(userId);
            MaybeInterestRequest maybeInterestRequest = new MaybeInterestRequest();
            maybeInterestRequest.setUserId(userId);
            maybeInterestRequest.setLoginArea(userProfile.getLoginArea());
            maybeInterestRequest.setPage(rows*start);
            maybeInterestRequest.setLastLoginTime(DateUtil.getDate(-1, DateUtil.DATETIME_FORMAT));
            maybeInterestRequest.setLgX(userProfile.getLgX());
            maybeInterestRequest.setLgY(userProfile.getLgY());
            maybeInterestRequest.setRows(rows);
            List<String> maybeInterest = mabyeInterest(maybeInterestRequest);
            if(CollectionUtils.isNotEmpty(maybeInterest)) {
                bshoots.addAll(getMaxPraiseBshoot(maybeInterest, oneMonthAgo, GuideType.MAYBE_INTEREST.getId()));
            }
        }else if(guideType==GuideType.MAYBE_KNOW.getId()){
            UserMobilePersonRequest userMobilePersonRequest = new UserMobilePersonRequest(userId,rows*start,rows);
            List<String> maybeKnow = maybeKnow(userMobilePersonRequest);
            if(CollectionUtils.isNotEmpty(maybeKnow))
                bshoots.addAll(getMaxPraiseBshoot(maybeKnow, oneMonthAgo, GuideType.MAYBE_KNOW.getId()));
        }
        return fillBshoot(bshoots);
    }

    private List<Bshoot> getMaxPraiseBshoot(List<String> userIds,Date dateLimit,Integer guideType){
        List<Bshoot> bshoots = bshootServiceImpl.getMaxPraiseBshoot(userIds,dateLimit);
        for(Bshoot bshoot:bshoots){
            bshoot.setGuideType(guideType);
        }
        if(logger.isDebugEnabled()){
            StackTraceElement[] stack = (new Throwable()).getStackTrace();
            logger.debug(stack[0].getClassName()+"." + stack[0].getMethodName() + " result:" + JSONUtils.toJSONString(bshoots));
        }
        return bshoots==null?new ArrayList<Bshoot>():bshoots;
    }
}
