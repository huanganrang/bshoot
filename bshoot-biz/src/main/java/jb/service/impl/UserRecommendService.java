package jb.service.impl;

import jb.pageModel.*;
import jb.service.*;
import jb.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import solr.common.SystemUtils;
import solr.model.SolrResponse;
import solr.model.UserEntity;
import solr.model.criteria.Criterias;
import solr.service.SolrUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Zhou Yibing on 2016/1/8.
 */
public class UserRecommendService implements CommonRecommendServiceI{

    @Autowired
    protected SolrUserService solrUserService;
    @Autowired
    protected UserAttentionServiceI userAttentionServiceImpl;
    @Autowired
    protected UserMobilePersonServiceI userMobilePersonServiceImpl;
    @Autowired
    protected BshootPraiseServiceI bshootPraiseServiceImpl;

    @Override
    public List<String> friendPraisedUser(PraiseCommentRequest praiseCommentRequest) {
        return bshootPraiseServiceImpl.friendHasPraisedUser(praiseCommentRequest);
    }

    @Override
    public List<String> singleFriendPraisedUser(PraiseCommentRequest praiseCommentRequest) {
        return bshootPraiseServiceImpl.singleFriendHasPraisedUser(praiseCommentRequest);
    }

    @Override
    public List<String> friendCommonAttUser(AttentionRequest attentionRequest) {
        return  userAttentionServiceImpl.friendCommonAtt(attentionRequest);
    }

    @Override
    public List<String> singleFriendAttUser(AttentionRequest attentionRequest) {
        return userAttentionServiceImpl.singleFriendAtt(attentionRequest);
    }

    @Override
    public List<String> meCommentPraisedUser(PraiseCommentRequest praiseCommentRequest) {
        return bshootPraiseServiceImpl.mePraiseCommentUser(praiseCommentRequest);
    }

    @Override
    public List<String> notAttMobileUser(UserMobilePersonRequest userMobilePersonRequest) {
        return userMobilePersonServiceImpl.notAttMobilePerson(userMobilePersonRequest);
    }

    @Override
    public List<String> mobileUserUser(UserMobilePersonRequest userMobilePersonRequest) {
        return userMobilePersonServiceImpl.noAttMobilePersonPerson(userMobilePersonRequest);
    }

    @Override
    public List<String> mabyeInterestedUser(MaybeInterestRequest maybeInterestRequest) {
        List<String> property = new ArrayList<String>();
        if(null!=maybeInterestRequest.getLoginArea())
          property.add(maybeInterestRequest.getLoginArea());
        if(CollectionUtils.isNotEmpty(maybeInterestRequest.getHobby()))
          property.addAll(maybeInterestRequest.getHobby());
        if(CollectionUtils.isNotEmpty(maybeInterestRequest.getAttSquare()))
          property.addAll(maybeInterestRequest.getAttSquare());

        List<String> out = new ArrayList<String>();
        String[] str = new String[property.size()];
        property.toArray(str);
        SystemUtils.combineStr(str, maybeInterestRequest.getPropertyCount(), out, "AND");
        StringBuffer sb = new StringBuffer();
        for(String s:out){
            sb.append("property:(").append(s).append(") OR ");
        }
        sb.delete(sb.lastIndexOf("OR "),sb.length());

        Criterias criterias = new Criterias();
        if(null!=maybeInterestRequest.getLastLoginTime())
           criterias.ge("lastLoginTime", DateUtil.convert2SolrDate(maybeInterestRequest.getLastLoginTime()));
        criterias.addNativeFq(sb.toString());
        criterias.addField("id");
        criterias.ne("id",maybeInterestRequest.getUserId());

        //用户登录的经纬度
        if(null!=maybeInterestRequest.getLgX()&&null!=maybeInterestRequest.getLgY()&&0!=maybeInterestRequest.getDistance()){
            criterias.addLocation("login_location",maybeInterestRequest.getLgY()+","+maybeInterestRequest.getLgX(),maybeInterestRequest.getDistance(),"asc");//附近的用户，按距离升序排序
        }
        if(maybeInterestRequest.isRand()){
            Random random = new Random();
            criterias.addOrder("rand_"+random.nextInt(1000),"asc");//随机排序字段
        }else{
            criterias.addOrder("lastPublishTime","desc");
        }
        criterias.setStart(maybeInterestRequest.getPage());
        criterias.setRows(maybeInterestRequest.getRows());

        SolrResponse<UserEntity> solrResponse = solrUserService.query(criterias);
        List<String> userIds = new ArrayList<String>();
        if(null!=solrResponse&&CollectionUtils.isNotEmpty(solrResponse.getDocs())){
            List<UserEntity> users = solrResponse.getDocs();
            for(UserEntity userEntity:users){
                userIds.add(userEntity.getId());
            }
        }
        return userIds;
    }

    @Override
    public List<String> nearbyUser(NearbyRequest nearbyRequest) {
        Criterias criterias = new Criterias();
        criterias.addField("id");
        criterias.ne("id",nearbyRequest.getUserId());//去掉自己
        if(nearbyRequest.isRand()){
            Random random = new Random();
            criterias.addOrder("rand_"+random.nextInt(1000),"asc");//随机排序字段
        }else{
            criterias.addOrder("lastPublishTime","desc");
        }
        //用户登录的经纬度
        if(null!=nearbyRequest.getLgX()&&null!=nearbyRequest.getLgY()&&0!=nearbyRequest.getDistance()){
            criterias.addLocation("login_location",nearbyRequest.getLgY()+","+nearbyRequest.getLgX(),nearbyRequest.getDistance(),"asc");//附近的用户，按距离升序排序
        }
        if(null!=nearbyRequest.getLastLoginTime()){
            criterias.ge("lastLoginTime",  DateUtil.convert2SolrDate(nearbyRequest.getLastLoginTime()));
        }
        if(null!=nearbyRequest.getLoginArea()){
            criterias.eq("login_area", nearbyRequest.getLoginArea());
        }
        criterias.setStart(nearbyRequest.getPage());
        criterias.setRows(nearbyRequest.getRows());
        SolrResponse<UserEntity> solrResponse = solrUserService.query(criterias);
        List<String> userIds = new ArrayList<String>();
        if(null!=solrResponse&&CollectionUtils.isNotEmpty(solrResponse.getDocs())){
            List<UserEntity> users = solrResponse.getDocs();
            for(UserEntity userEntity:users){
                userIds.add(userEntity.getId());
            }
        }
        return userIds;
    }
}
