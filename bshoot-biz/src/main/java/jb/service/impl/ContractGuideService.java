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
import solr.common.SystemUtils;
import solr.model.SolrResponse;
import solr.model.UserEntity;
import solr.model.criteria.Criterias;

import java.util.*;

/**
 * Created by Zhou Yibing on 2016/1/8.
 */
public class ContractGuideService extends RecommendService implements ContractGuideServiceI{

    @Override
    public Map<Integer,List<RecommendUser>> guideExternalPage(String userId, Integer start, Integer rows) {
        Map<Integer,List<RecommendUser>> listMap = new HashMap<Integer,List<RecommendUser>>();
        UserProfile userProfile = userProfileServiceImpl.get(userId);
        //1.可能感兴趣的人
        MaybeInterestRequest maybeInterestRequest = new MaybeInterestRequest();
        maybeInterestRequest.setUserId(userId);
        maybeInterestRequest.setLoginArea(userProfile.getLoginArea());
        maybeInterestRequest.setPage(rows*start);
        maybeInterestRequest.setLastLoginTime(DateUtil.getDate(-1, DateUtil.DATETIME_FORMAT));
        maybeInterestRequest.setLgX(userProfile.getLgX());
        maybeInterestRequest.setLgY(userProfile.getLgY());
        maybeInterestRequest.setRows(rows);
        List<String> mabyeInterested = mabyeInterest(maybeInterestRequest);
        if(CollectionUtils.isNotEmpty(mabyeInterested)) {
            listMap.put(GuideType.MAYBE_INTEREST.getId(),fillUser(mabyeInterested,GuideType.MAYBE_INTEREST.getId()));
        }

        //2.好友关注的人
        AttentionRequest attentionRequest = new AttentionRequest(userId,null,rows*start,rows);
        List<String> friendCommonBshoot = friendCommonAtt(attentionRequest);
        if(CollectionUtils.isNotEmpty(friendCommonBshoot)) {
            listMap.put(GuideType.MAYBE_INTEREST.getId(),fillUser(friendCommonBshoot, GuideType.MAYBE_INTEREST.getId()));
        }

        //3.附近的人
        NearbyRequest sameCityRequest = new NearbyRequest();
        sameCityRequest.setUserId(userId);
        sameCityRequest.setLoginArea(userProfile.getLoginArea());
        sameCityRequest.setLastLoginTime(DateUtil.getDate(-1, DateUtil.DATETIME_FORMAT));
        sameCityRequest.setPage(rows*start);
        sameCityRequest.setRows(rows);
        List<String> nearbyUsers = super.nearbyUser(sameCityRequest);
        if(CollectionUtils.isNotEmpty(nearbyUsers)) {
            listMap.put(GuideType.MAYBE_INTEREST.getId(),fillUser(nearbyUsers,GuideType.MAYBE_INTEREST.getId()));
        }
        return listMap;
    }

    @Override
    public List<RecommendUser> guideInternalPage(String userId, Integer guideType, Integer start, Integer rows) {
        if(guideType==GuideType.MAYBE_INTEREST.getId()){

        }else if(guideType==GuideType.NEARBY_PEOPLE.getId()){

        }else if(guideType==GuideType.FRIEND_ATT.getId()){

        }
        return null;
    }

    public List<RecommendUser> fillUser(List<String> userIds,Integer recommendType){
        Criterias criterias = new Criterias();
        StringBuffer out = new StringBuffer();
        for(String user:userIds){
            out.append(SystemUtils.escapeToSolr(user)).append(" ");
        }
        criterias.addNativeFq("id:("+out.toString()+")");
        criterias.addField(new String[]{"id","headImg","member_v","hobby","nickname"});
        criterias.setStart(0);
        criterias.setRows(userIds.size());
        SolrResponse<UserEntity> userEntitySolrResponse = solrUserService.query(criterias);
        List<RecommendUser> recommendUsers=new ArrayList<RecommendUser>();
        if(null!=userEntitySolrResponse&&CollectionUtils.isNotEmpty(userEntitySolrResponse.getDocs())){
            List<UserEntity> userEntities = userEntitySolrResponse.getDocs();
            RecommendUser recommendUser;
            for(UserEntity userEntity:userEntities){
                recommendUser = new RecommendUser();
                recommendUser.setId(userEntity.getId());
                recommendUser.setArea(userEntity.getLogin_area());
                recommendUser.setBardian(userEntity.getBardian());
                recommendUser.setHeadImage(userEntity.getHeadImg());
                recommendUser.setHobby(userEntity.getHobby());
                recommendUser.setSex(userEntity.getUsex());
                recommendUser.setRecommendType(recommendType);
                recommendUsers.add(recommendUser);
            }
        }
        return recommendUsers;
    }
}
