package jb.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.common.collect.Lists;
import jb.bizmodel.RecommendUser;
import jb.pageModel.*;
import jb.service.*;
import jb.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solr.model.SolrResponse;
import solr.model.UserEntity;
import solr.model.criteria.Criterias;
import solr.service.SolrUserService;

import java.util.*;

/**
 * Created by zhou on 2016/1/1.
 */
@Service
public class RecommendService implements RecommendServiceI{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserServiceI userServiceImpl;
    @Autowired
    private SolrUserService solrUserService;
    @Autowired
    private BasedataServiceI basedataServiceImpl;
    @Autowired
    private BshootServiceI bshootServiceImpl;
    @Autowired
    private UserProfileServiceI userProfileServiceImpl;
    @Autowired
    private UserHobbyServiceI userHobbyServiceImpl;
    @Autowired
    private UserAttentionServiceI userAttentionServiceImpl;
    @Autowired
    private UserMobilePersonServiceI userMobilePersonServiceImpl;
    @Autowired
    private BshootPraiseServiceI bshootPraiseServiceImpl;

    @Override
    public List<RecommendUser> recommendUser( String loginArea) {
        //获得6个大v,4个小v
        Criterias criterias = new Criterias();
        criterias.qc("fans_num:[200 TO *]");
        criterias.gt("month_praise","50");
        Random random = new Random();
        criterias.addOrder("rand_"+random.nextInt(1000),"asc");//随机排序字段
        criterias.addField(new String[]{"id","usex","hobby","login_area"});
        criterias.setStart(0);
        criterias.setRows(6);
        SolrResponse<UserEntity> userV = solrUserService.query(criterias);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        if(null!=userV&&CollectionUtils.isNotEmpty(userV.getDocs()))
            userList = userV.getDocs();

        Criterias criterias2 = new Criterias();
        criterias.qc("fans_num:[* TO 10]");
        criterias.eq("login_area",loginArea);
        criterias.eq("createTime", DateUtil.convert2SolrDate(DateUtil.getDate(-3)));
        criterias.addOrder("rand_" + random.nextInt(1000), "asc");//随机排序字段
        criterias.addField(new String[]{"id","usex","hobby","areaCode"});
        criterias.addField();
        criterias.setStart(0);
        criterias.setRows(4);
        SolrResponse<UserEntity> userv = solrUserService.query(criterias2);
        if(null!=userv&&CollectionUtils.isNotEmpty(userv.getDocs()))
            userList.addAll(userv.getDocs());
        List<RecommendUser> recommendUsers = new ArrayList<RecommendUser>();
        for(UserEntity userEntity:userList){
            User u = userServiceImpl.get(userEntity.getId());
            RecommendUser recommendUser = new RecommendUser();
            recommendUser.setId(u.getId());
            recommendUser.setNickname(u.getNickname());
            recommendUser.setHeadImage(u.getHeadImage());
            recommendUser.setBardian(u.getBardian());
            BaseData area = basedataServiceImpl.get(userEntity.getLogin_area());
            if(null!=area)
              recommendUser.setArea(area.getName());
            List<BaseData> hobbys = basedataServiceImpl.getBaseDatas(userEntity.getHobby());
            List<String> h = Lists.newArrayList();
            for(BaseData hobby:hobbys){
                h.add(hobby.getName());
            }
            recommendUser.setHobby(h);
            BaseData sex = basedataServiceImpl.get(u.getSex());
            if(null!=sex)
            recommendUser.setSex(sex.getName());
            recommendUsers.add(recommendUser);
        }
        return recommendUsers;
    }

    @Override
    public List<Bshoot> recommendHot() {
        Date tenHousrago = DateUtil.getDateBeforeHours(-10);
       List<Bshoot> bshoots =  bshootServiceImpl.getHotBshoots(tenHousrago, 200, 50);
       if(bshoots.size()<50){
           Date oneDayago = DateUtil.stringToDate(DateUtil.getDate(-1));
           List<Bshoot> bs2 = bshootServiceImpl.getHotBshoots(oneDayago,200,50-bshoots.size());
           if(CollectionUtils.isNotEmpty(bs2))
             bshoots.addAll(bs2);
       }
        return bshoots;
    }

    @Override
    public List<Bshoot> recommend(String userId,int start) {
        //获得当前用户画像
        UserProfile userProfile = userProfileServiceImpl.get(userId);
        List<Bshoot> bshoots = new ArrayList<Bshoot>();
        //1.新人推荐 done
        bshoots.add(recommendNewUser(userProfile.getFansNum(),userProfile.getLoginArea(),start,DateUtil.stringToDate(DateUtil.getDate(-1))));
        Date threeDaysAgo = DateUtil.stringToDate(DateUtil.getDate(-3));
        //2.好友共同关注的人 done
        List<Bshoot> friendCommonBshoot = friendCommonAtt(userId, start,threeDaysAgo);
        if(CollectionUtils.isNotEmpty(friendCommonBshoot))
            bshoots.addAll(friendCommonBshoot);

        //3.我评论打赏过的人
        List<Bshoot> comment_praise = have_comment_praise(userId, start,threeDaysAgo);
        if(CollectionUtils.isNotEmpty(comment_praise))
            bshoots.addAll(comment_praise);

        //4.好友打赏过的人
        List<Bshoot> friendPraise = friendPraise(userId, start,threeDaysAgo);
        if(CollectionUtils.isNotEmpty(friendPraise))
            bshoots.addAll(friendPraise);

        //5、可能感兴趣的人
        List<Bshoot> mabyeInterestBshoot = mabyeInterest(userId,userProfile.getLoginArea(), start,threeDaysAgo);
        if(CollectionUtils.isNotEmpty(mabyeInterestBshoot))
            bshoots.addAll(mabyeInterestBshoot);

        //6.可能认识的人 done
        List<Bshoot> maybeKnow = maybeKnow(userId,start,threeDaysAgo);
        if(CollectionUtils.isNotEmpty(maybeKnow))
            bshoots.addAll(maybeKnow);

        //7.附近的人 done
        List<Bshoot> nearbyBshoot = nearbyBshoot(userProfile.getLoginArea(), start,threeDaysAgo);
        if(CollectionUtils.isNotEmpty(nearbyBshoot))
           bshoots.addAll(nearbyBshoot);
        Collections.shuffle(bshoots);
        return bshoots;
    }

    private List<Bshoot> friendPraise(String userId,int start,Date dateLimit){
        List<String> userIds = bshootPraiseServiceImpl.friendHasPraisedUser(userId,6*start, 6);
        if(CollectionUtils.isEmpty(userIds)){
            userIds = bshootPraiseServiceImpl.singleFriendHasPraisedUser(userId, 6 * start, 6);
        }
        return getLastBshoot(userIds,dateLimit);
    }

    //好友共同关注的人
    private List<Bshoot> friendCommonAtt(String userId,int start,Date dateLimit){
        List<UserAttention> userAttentionList = userAttentionServiceImpl.friendCommonAtt(userId,6*start,6);
        if(CollectionUtils.isEmpty(userAttentionList)){
            //显示单个好友关注的人动态
            userAttentionList = userAttentionServiceImpl.singleFriednAtt(userId,6*start,6);
        }
        if(CollectionUtils.isNotEmpty(userAttentionList)) {
            List<String> userIds = new ArrayList<String>();
            for (UserAttention userAttention : userAttentionList) {
                userIds.add(userAttention.getAttUserId());
            }
            return getLastBshoot(userIds,dateLimit);
        }
        return null;
    }

    //我评论/打赏过的人的动态
    private List<Bshoot> have_comment_praise(String userId,int start,Date dateLimit){
        List<String> userIds =  bshootPraiseServiceImpl.mePraiseCommentUser(userId,6*start, 6);
        return getLastBshoot(userIds,dateLimit);
    }

    //可能认识的人
    private List<Bshoot> maybeKnow(String userId,int start,Date dateLimit){
        List<UserMobilePerson> userMobilePersonList = userMobilePersonServiceImpl.notAttMobilePerson(userId,6*start,6);
        List<String> userIds = new ArrayList<String>();
        int rows = 6;
        if(CollectionUtils.isEmpty(userMobilePersonList)){
            userMobilePersonList = new ArrayList<UserMobilePerson>();
            rows = 12;
        }else if(userMobilePersonList.size()<6){
            rows = 12-userMobilePersonList.size();
        }
        List<UserMobilePerson> userMobilePersonList2 = userMobilePersonServiceImpl.noAttMobilePersonPerson(userId,6*start,rows);
        if(CollectionUtils.isNotEmpty(userMobilePersonList2)) userMobilePersonList.addAll(userMobilePersonList2);

        for(UserMobilePerson u:userMobilePersonList){
            userIds.add(u.getFriendId());
        }
        return getLastBshoot(userIds,dateLimit);
    }

    //可能感兴趣的人
    private List<Bshoot> mabyeInterest(String userId,String loginArea,int start,Date dateLimit){
        UserHobby userHobby  = userHobbyServiceImpl.getUserHobby(userId);
        if(null!=userHobby&& StringUtils.isNotEmpty(userHobby.getHobbyType())){
            String[] hobbyType = userHobby.getHobbyType().split(",");
            StringBuffer qc = new StringBuffer();
            for(String hobby:hobbyType){

            }
            Criterias criterias = new Criterias();
            criterias.qc("");
            criterias.lt("lastLoginTime", DateUtil.convert2SolrDate(DateUtil.getDate(0)));
            criterias.addField("id");
            criterias.setStart(6*start);
            criterias.setRows(6);
            SolrResponse<UserEntity> solrResponse = solrUserService.query(criterias);
            if(null!=solrResponse&&CollectionUtils.isNotEmpty(solrResponse.getDocs())){
                List<UserEntity> user = solrResponse.getDocs();
                //return bshootServiceImpl.getUserLastBshoot(user.get(0).getId());
                //bshootServiceImpl.getUserLastBshoot();
            }
        }
        return  null;
    }

    /**
     * 附近的人动态
     * @param loginArea
     * @return
     */
    private List<Bshoot> nearbyBshoot(String loginArea ,int start,Date dateLimit){
        Criterias criterias = new Criterias();
        criterias.qc("login_area:"+loginArea);
        criterias.addField("id");
        criterias.addOrder("lastPublishTime","desc");
        criterias.setStart(start*6);
        criterias.setRows(6);
        SolrResponse<UserEntity> solrResponse = solrUserService.query(criterias);
        if(null!=solrResponse&&CollectionUtils.isNotEmpty(solrResponse.getDocs())){
            List<UserEntity> users = solrResponse.getDocs();
            List<String> userIds = new ArrayList<String>();
            for(UserEntity userEntity:users){
                userIds.add(userEntity.getId());
            }
            return getLastBshoot(userIds,dateLimit);
        }
        return null;
    }

    private Bshoot recommendNewUser(Integer fansNum,String loginArea,int start,Date dateLimit){
        //1.新人推荐
        if(null!=fansNum&&fansNum<50) {
            //当前用户粉丝量大于50则不推荐，新人推荐根据用户粉丝量，获取同城用户且当天发布了动态
            String qc = null;
            if(fansNum<10)
                qc="fans_num:[0 TO 9]";
            else if(fansNum>10&&fansNum<30)
                qc="fans_num:[10 TO 29]";
            else if(fansNum>30&&fansNum<50)
                qc="fans_num:[30 TO 49]";
            Criterias criterias = new Criterias();
            criterias.qc(qc);
            criterias.eq("login_area",loginArea);
            criterias.lt("lastPublishTime", DateUtil.convert2SolrDate(DateUtil.getDate(0)));
            criterias.addField("id");
            criterias.setStart(start*1);
            criterias.setRows(1);
            SolrResponse<UserEntity> solrResponse = solrUserService.query(criterias);
            if(null!=solrResponse&&CollectionUtils.isNotEmpty(solrResponse.getDocs())){
                List<UserEntity> user = solrResponse.getDocs();
               return bshootServiceImpl.getUserLastBshoot(user.get(0).getId(),dateLimit);
            }
        }
        return null;
    }

    private List<Bshoot> getLastBshoot(List<String> userIds,Date dateLimit){
        List<Bshoot> bshoots =  bshootServiceImpl.getUserLastBshoot(userIds,dateLimit);
        if(logger.isDebugEnabled()){
            StackTraceElement[] stack = (new Throwable()).getStackTrace();
            logger.debug(stack[0].getClassName()+"." + stack[0].getMethodName() + " result:" + JSONUtils.toJSONString(bshoots));
        }
        return bshoots;
    }
    public static void main(String[] args){
        //System.out.println(DateUtil.convert2SolrDate(DateUtil.getDate(0,DateUtil.DATETIME_FORMAT)));
        //System.out.println(DateUtil.convert2SolrDate(DateUtil.getDateBeforeHours(-10, DateUtil.DATETIME_FORMAT)));
        a();
    }
    private static void a(){
        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        System.out.println(stack[0].getClassName()+"." + stack[0].getMethodName() + " result:");
    }
}
