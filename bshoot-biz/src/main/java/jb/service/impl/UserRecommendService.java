package jb.service.impl;

import jb.pageModel.*;
import jb.service.*;
import jb.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.util.CollectionUtil;
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
public class UserRecommendService implements UserRecommendServiceI{

    @Autowired
    private SolrUserService solrUserService;
    @Autowired
    protected UserProfileServiceI userProfileServiceImpl;
    @Autowired
    private UserHobbyServiceI userHobbyServiceImpl;
    @Autowired
    private UserAttentionServiceI userAttentionServiceImpl;
    @Autowired
    private UserMobilePersonServiceI userMobilePersonServiceImpl;
    @Autowired
    private BshootPraiseServiceI bshootPraiseServiceImpl;

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
    public List<String> notAttMobilePerson(UserMobilePersonRequest userMobilePersonRequest) {
        return userMobilePersonServiceImpl.notAttMobilePerson(userMobilePersonRequest);
    }

    @Override
    public List<String> mobilePersonPerson(UserMobilePersonRequest userMobilePersonRequest) {
        return userMobilePersonServiceImpl.noAttMobilePersonPerson(userMobilePersonRequest);
    }

    @Override
    public List<String> mabyeInterest(MaybeInterestRequest maybeInterestRequest) {
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
        criterias.ge("lastLoginTime",maybeInterestRequest.getLastLoginTime());
        criterias.addNativeFq(sb.toString());
        criterias.addField("id");
        criterias.ne("id",maybeInterestRequest.getUserId());
        if(maybeInterestRequest.isRand()){
            Random random = new Random();
            criterias.addOrder("rand_"+random.nextInt(1000),"asc");//随机排序字段
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
}
