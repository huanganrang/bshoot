package jb.service.impl;

import jb.bizmodel.GuideType;
import jb.bizmodel.RecommendUser;
import jb.pageModel.AttentionRequest;
import jb.pageModel.MaybeInterestRequest;
import jb.pageModel.NearbyRequest;
import jb.pageModel.UserProfile;
import jb.service.ContractGuideServiceI;
import jb.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import solr.common.SystemUtils;
import solr.model.SolrResponse;
import solr.model.UserEntity;
import solr.model.criteria.Criterias;

import java.util.*;

/**
 * Created by Zhou Yibing on 2016/1/8.
 */
@Service
public class ContractGuideService extends RecommendServiceImpl implements ContractGuideServiceI{

    @Override
    public Map<Integer,List<RecommendUser>> guideExternalPage(String userId, Integer start, Integer rows) {
        Map<Integer,List<RecommendUser>> listMap = new HashMap<Integer,List<RecommendUser>>();
        UserProfile userProfile = userProfileService.get(userId);
        //1.可能感兴趣的人
        List<RecommendUser> maybeInterestedUser = maybeInterestedUser(userProfile,start,rows);
        listMap.put(GuideType.MAYBE_INTEREST.getId(),maybeInterestedUser);
        //2.好友关注的人
        List<RecommendUser>  friendCommonAtt = friendCommonAtt(userId,start,rows);
        listMap.put(GuideType.FRIEND_ATT.getId(),friendCommonAtt);

        //3.附近的人
        List<RecommendUser>  nearByUser = nearByUser(userProfile,start,rows);
        listMap.put(GuideType.NEARBY_PEOPLE.getId(),nearByUser);
        return listMap;
    }

    private List<RecommendUser> nearByUser(UserProfile userProfile,Integer start,Integer rows){
        NearbyRequest sameCityRequest = new NearbyRequest();
        sameCityRequest.setUserId(userProfile.getId());
        sameCityRequest.setLoginArea(userProfile.getLoginArea());
        sameCityRequest.setLastLoginTime(DateUtil.getDate(-1, DateUtil.DATETIME_FORMAT));
        sameCityRequest.setPage(rows*start);
        sameCityRequest.setRows(rows);
        List<String> nearbyUsers = super.nearbyUser(sameCityRequest);
        if(CollectionUtils.isNotEmpty(nearbyUsers)) {
            return searchUser(nearbyUsers);
        }
        return null;
    }

    private List<RecommendUser> friendCommonAtt(String userId,Integer start,Integer rows){
        AttentionRequest attentionRequest = new AttentionRequest(userId,null,rows*start,rows);
        List<String> friendCommonAtt = friendCommonAtt(attentionRequest);
        if(CollectionUtils.isNotEmpty(friendCommonAtt)) {
            return searchUser(friendCommonAtt);
        }
        return null;
    }

    private List<RecommendUser> maybeInterestedUser(UserProfile userProfile,Integer start,Integer rows){
        //1.可能感兴趣的人
        MaybeInterestRequest maybeInterestRequest = new MaybeInterestRequest();
        maybeInterestRequest.setUserId(userProfile.getId());
        maybeInterestRequest.setLoginArea(userProfile.getLoginArea());
        maybeInterestRequest.setPage(rows*start);
        maybeInterestRequest.setLastLoginTime(DateUtil.getDate(-1, DateUtil.DATETIME_FORMAT));
        maybeInterestRequest.setLgX(userProfile.getLgX());
        maybeInterestRequest.setLgY(userProfile.getLgY());
        maybeInterestRequest.setRows(rows);
        List<String> mabyeInterested = mabyeInterest(maybeInterestRequest);
        if(CollectionUtils.isNotEmpty(mabyeInterested))
            return searchUser(mabyeInterested);
        return null;
    }

    @Override
    public List<RecommendUser> guideInternalPage(String userId, Integer guideType, Integer start, Integer rows) {
        if(guideType==GuideType.MAYBE_INTEREST.getId()){
            UserProfile userProfile = userProfileService.get(userId);
            return maybeInterestedUser(userProfile,start,rows);
        }else if(guideType==GuideType.FRIEND_ATT.getId()){
            return friendCommonAtt(userId, start, rows);
        }else if(guideType==GuideType.NEARBY_PEOPLE.getId()){
            UserProfile userProfile = userProfileService.get(userId);
            return nearByUser(userProfile,start,rows);
        }
        return null;
    }

    public List<RecommendUser> searchUser(List<String> userIds){
        Criterias criterias = new Criterias();
        StringBuffer out = new StringBuffer();
        for(String user:userIds){
            out.append(SystemUtils.solrStringTrasfer(user)).append(" ");
        }
        criterias.addNativeFq("id:("+out.toString()+")");
        criterias.addField(new String[]{"id","headImg","bardian","utype","usex","member_v","hobby","nickname","is_star","is_tarento","att_square","login_area","job"});
        criterias.setStart(0);
        criterias.setRows(userIds.size());
        SolrResponse<UserEntity> userEntitySolrResponse = solrUserService.query(criterias);
        if(null==userEntitySolrResponse||CollectionUtils.isEmpty(userEntitySolrResponse.getDocs())) return null;
        return super.fillUser(userEntitySolrResponse.getDocs());
    }
}
