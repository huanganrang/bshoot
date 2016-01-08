package jb.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.common.collect.Lists;
import jb.bizmodel.GuideType;
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
import solr.common.SystemUtils;
import solr.model.SolrResponse;
import solr.model.UserEntity;
import solr.model.criteria.Criterias;
import solr.service.SolrUserService;

import java.util.*;

/**
 * Created by zhou on 2016/1/1.
 */
@Service
public class RecommendService extends UserRecommendService implements RecommendServiceI{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserServiceI userServiceImpl;
    @Autowired
    private SolrUserService solrUserService;
    @Autowired
    private BasedataServiceI basedataServiceImpl;
    @Autowired
    protected BshootServiceI bshootServiceImpl;
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
    public List<RecommendUser> recommendUser( String loginArea) {
        //获得6个大v,4个小v
        Criterias criterias = new Criterias();
        criterias.qc("fansNum:[200 TO *]");
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
        criterias2.qc("fansNum:[* TO 10]");
        criterias2.eq("login_area",loginArea);
        criterias2.ge("createTime", DateUtil.convert2SolrDate(DateUtil.getDateStart(-3)));
        criterias2.addOrder("rand_" + random.nextInt(1000), "asc");//随机排序字段
        criterias2.addField(new String[]{"id","usex","hobby","login_area"});
        criterias2.setStart(0);
        criterias2.setRows(4);
        SolrResponse<UserEntity> userv = solrUserService.query(criterias2);
        if(null!=userv&&CollectionUtils.isNotEmpty(userv.getDocs()))
            userList.addAll(userv.getDocs());
        List<RecommendUser> recommendUsers = new ArrayList<RecommendUser>();
        Set<String> baseDataIds = new HashSet<String>();
        for(UserEntity userEntity:userList){
            if(null!=userEntity.getLogin_area())
               baseDataIds.add(userEntity.getLogin_area());
            if(null!=userEntity.getUsex())
                baseDataIds.add(userEntity.getUsex());
            if(null!=userEntity.getHobby())
                baseDataIds.addAll(userEntity.getHobby());
        }
        Map<String,BaseData> baseDataMap = basedataServiceImpl.getBaseDatas(baseDataIds,Map.class);
        BaseData baseData;
        for(UserEntity userEntity:userList){
            User u = userServiceImpl.get(userEntity.getId());
            RecommendUser recommendUser = new RecommendUser();
            recommendUser.setId(u.getId());
            recommendUser.setNickname(u.getNickname());
            recommendUser.setHeadImage(u.getHeadImage());
            recommendUser.setBardian(u.getBardian());
            baseData = baseDataMap.get(userEntity.getLogin_area());
            if(null!=baseData)
              recommendUser.setArea(baseData.getName());
            List<String> hobbies = userEntity.getHobby();
            List<String> hobbyNames = Lists.newArrayList();
            for(String hobby:hobbies){
                baseData = baseDataMap.get(hobby);
                if(null!=baseData)
                    hobbyNames.add(baseData.getName());
            }
            recommendUser.setHobby(hobbyNames);
            baseData = baseDataMap.get(userEntity.getUsex());
            if(null!=baseData)
              recommendUser.setSex(baseData.getName());
            recommendUsers.add(recommendUser);
        }
        return recommendUsers;
    }

    @Override
    public List<Bshoot> recommendHot(String userId,Integer start,Integer fileType,Integer interested) {
        //可以使用solr查询，但是在用户帅选同兴趣的情况下，需要solr的core_user和core_bs联合查询，但是在分布式环境下solr联合查询有文档说是可以实现，考虑风险暂且使用库查询 _query_:"{!join fromIndex=core_user from=id to=userId v='hobby:(HO01 OR HO02 HO03)'}"
        Date oneDayago = DateUtil.stringToDate(DateUtil.getDate(-1, DateUtil.DATETIME_FORMAT),DateUtil.DATETIME_FORMAT);
        String hobby = null;
        if(interested==1) {
            UserHobby userHobby = userHobbyServiceImpl.getUserHobby(userId);
            String[] hobbyType = userHobby.getHobbyType().split(",");
            List<String> out = new ArrayList<String>();
            SystemUtils.combineStr(hobbyType, hobbyType.length, out, "OR");
            hobby = out.get(0);
        }
        HotShootRequest hotShootRequest = new HotShootRequest(oneDayago,200,start,fileType,hobby,50);
        List<Bshoot> bshoots =  bshootServiceImpl.getHotBshoots(hotShootRequest);
        return fillBshoot(bshoots);
    }

    protected List<Bshoot> fillBshoot(List<Bshoot> bshoots){
        if(CollectionUtils.isEmpty(bshoots)) return bshoots;
        Set<String> userIds = new HashSet<String>();
        for(Bshoot bshoot:bshoots)
            userIds.add(bshoot.getUserId());
        Criterias criterias = new Criterias();
        List<String> out = new ArrayList<String>();
        String[] u = new String[userIds.size()];
        userIds.toArray(u);
        SystemUtils.combineStr(u,2,out,null);
        criterias.addNativeFq("id:("+out.toString()+")");
        criterias.addField(new String[]{"id","headImg","member_v","hobby","nickname"});
        criterias.setStart(0);
        criterias.setRows(u.length);
        SolrResponse<UserEntity> userEntitySolrResponse = solrUserService.query(criterias);
        if(null!=userEntitySolrResponse&&CollectionUtils.isNotEmpty(userEntitySolrResponse.getDocs())){
            List<UserEntity> userEntities = userEntitySolrResponse.getDocs();
            Set<String> hobbies = new HashSet<String>();
            Map<String,UserEntity> userEntityMap = new HashMap<String, UserEntity>();
            UserEntity userEntity;
            for(int i=0;i<userEntities.size();i++){
                userEntity = userEntities.get(i);
                userEntityMap.put(userEntity.getId(),userEntity);
                hobbies.addAll(userEntity.getHobby());
            }
            Map<String,BaseData> baseDatas = basedataServiceImpl.getBaseDatas(hobbies,Map.class);
            List<String> h;
            for(Bshoot bshoot:bshoots){
                userEntity = userEntityMap.get(bshoot.getUserId());
                bshoot.setUserName(userEntity.getNickname());
                bshoot.setMemberV(userEntity.getMember_v());
                bshoot.setUserHeadImage(userEntity.getHeadImg());
                h = new ArrayList<String>();
                for(String hob:userEntity.getHobby()){
                    h.add(baseDatas.get(hob).getName());
                }
                bshoot.setHobby(h);
            }
        }
        return bshoots;
    }

    @Override
    public List<Bshoot> recommend(String userId,Integer start) {
        List<Bshoot> bshoots = new ArrayList<Bshoot>();

        Date threeDayAgo = DateUtil.stringToDate(DateUtil.getDateStart(-3));
        //2.好友共同关注的人 done
        AttentionRequest attentionRequest = new AttentionRequest(userId,null,6*start,6);
        List<Bshoot> friendCommonBshoot = friendCommonAtt(attentionRequest,threeDayAgo,false);
        if(CollectionUtils.isNotEmpty(friendCommonBshoot))
            bshoots.addAll(friendCommonBshoot);

        //3.我评论打赏过的人 done
        PraiseCommentRequest praiseCommentRequest = new PraiseCommentRequest(userId,null,6*start,6);
        List<Bshoot> comment_praise = have_comment_praise(praiseCommentRequest,threeDayAgo,false);
        if(CollectionUtils.isNotEmpty(comment_praise))
            bshoots.addAll(comment_praise);

        //4.好友打赏过的人 done
        PraiseCommentRequest praiseCommentRequest2 = new PraiseCommentRequest(userId,null,3*start,3);
        List<Bshoot> friendPraise = friendPraise(praiseCommentRequest2,threeDayAgo,false);
        if(CollectionUtils.isNotEmpty(friendPraise))
            bshoots.addAll(friendPraise);

        //获得当前用户画像
        UserProfile userProfile = userProfileServiceImpl.get(userId);

        //5、可能感兴趣的人 done
        MaybeInterestRequest maybeInterestRequest = new MaybeInterestRequest();
        maybeInterestRequest.setUserId(userId);
        maybeInterestRequest.setLoginArea(userProfile.getLoginArea());
        maybeInterestRequest.setPage(3*start);
        maybeInterestRequest.setLastLoginTime(DateUtil.convert2SolrDate(DateUtil.getDate(-1, DateUtil.DATETIME_FORMAT)));
        maybeInterestRequest.setRows(6);
        List<Bshoot> mabyeInterestBshoot = mabyeInterest(maybeInterestRequest,threeDayAgo,false);
        if(CollectionUtils.isNotEmpty(mabyeInterestBshoot))
            bshoots.addAll(mabyeInterestBshoot);

        //6.可能认识的人 done
        UserMobilePersonRequest userMobilePersonRequest = new UserMobilePersonRequest(userId,6*start,6);
        List<Bshoot> maybeKnow = maybeKnow(userMobilePersonRequest,threeDayAgo,false);
        if(CollectionUtils.isNotEmpty(maybeKnow))
            bshoots.addAll(maybeKnow);

        //7.附近的人 done
        List<Bshoot> nearbyBshoot = nearbyBshoot(userId,userProfile.getLoginArea(), start,threeDayAgo,false);
        if(CollectionUtils.isNotEmpty(nearbyBshoot))
            bshoots.addAll(nearbyBshoot);

        fillBshoot(bshoots);
        Collections.shuffle(bshoots);
        //1.新人推荐 done
        String uid = recommendNewUser(userId,userProfile.getFansNum(),userProfile.getLoginArea(),start);
        List<Bshoot> newUserBshoot = getLastBshoot(Lists.newArrayList(uid), DateUtil.stringToDate(DateUtil.getDateStart(-1)),GuideType.NEW_USER.getId(),false);
        if(CollectionUtils.isNotEmpty(newUserBshoot))
            bshoots.addAll(0, newUserBshoot);//固定在第一位
        return bshoots;
    }

    protected List<Bshoot> friendPraise(PraiseCommentRequest praiseCommentRequest,Date dateLimit,boolean maxPraise){
        List<String> userIds = bshootPraiseServiceImpl.friendHasPraisedUser(praiseCommentRequest);
        if(CollectionUtils.isEmpty(userIds)){
            userIds = bshootPraiseServiceImpl.singleFriendHasPraisedUser(praiseCommentRequest);
        }
       return getLastBshoot(userIds, dateLimit, GuideType.FRIEND_PRAISE.getId(),maxPraise);
    }

    //好友共同关注的人
    protected List<Bshoot> friendCommonAtt(AttentionRequest attentionRequest,Date dateLimit,boolean maxPraise){
        List<String> userAttentionList = super.friendCommonAttUser(attentionRequest);
        if(CollectionUtils.isEmpty(userAttentionList)){
            //显示单个好友关注的人动态
            userAttentionList = super.singleFriendAttUser(attentionRequest);
        }
       return getLastBshoot(userAttentionList,dateLimit, GuideType.FRIEND_ATT.getId(),maxPraise);
    }

    //我评论/打赏过的人的动态
    protected List<Bshoot> have_comment_praise(PraiseCommentRequest praiseCommentRequest,Date dateLimit,boolean maxPraise){
        return getLastBshoot(super.meCommentPraisedUser(praiseCommentRequest), dateLimit, GuideType.FRIEND_ATT.getId(), maxPraise);
    }

    //可能认识的人
    protected List<Bshoot> maybeKnow(UserMobilePersonRequest request,Date dateLimit,boolean maxPraise){
        List<String> userMobilePersonList = super.notAttMobilePerson(request);
        List<String> userIds = new ArrayList<String>();
        int rows = request.getRows();
        if(CollectionUtils.isEmpty(userMobilePersonList)){
            userMobilePersonList = new ArrayList<String>();
            rows = 2*rows;
        }else if(userMobilePersonList.size()<request.getRows()){
            rows = 2*rows-userMobilePersonList.size();
        }
        request.setRows(rows);
        List<String> userMobilePersonList2 = super.notAttMobilePerson(request);
        if(CollectionUtils.isNotEmpty(userMobilePersonList2)) userMobilePersonList.addAll(userMobilePersonList2);
        return getLastBshoot(userMobilePersonList,dateLimit, GuideType.MAYBE_KNOW.getId(),maxPraise);
    }

    //可能感兴趣的人
    protected List<Bshoot> mabyeInterest(MaybeInterestRequest maybeInterestRequest,Date dateLimit,boolean maxPraise){
        UserHobby userHobby  = userHobbyServiceImpl.getUserHobby(maybeInterestRequest.getUserId());
        if(null!=userHobby&& StringUtils.isNotEmpty(userHobby.getHobbyType())){
            /*maybeInterestRequest.setHobby(Lists.newArrayList(userHobby.getHobbyType().split(",")));
            maybeInterestRequest.setPropertyCount(3);
            super.mabyeInterest(maybeInterestRequest);*/
            String[] hobbyType = userHobby.getHobbyType().split(",");
            List<String> out1 = new ArrayList<String>();
            List<String> out2 = new ArrayList<String>();
            SystemUtils.combineStr(hobbyType,2,out1,"AND");
            SystemUtils.combineStr(hobbyType,3,out2,"AND");
            StringBuffer sb = new StringBuffer();
            for(String s1 :out1){
                sb.append("login_area:").append(maybeInterestRequest.getLoginArea()).append(" AND ").append("hobby:(").append(s1).append(") OR ");
            }
            for(String s2:out2){
                sb.append("hobby:(").append(s2).append(") OR ");
            }
            sb.delete(sb.lastIndexOf("OR "),sb.length());
            Criterias criterias = new Criterias();
            criterias.ge("lastLoginTime", DateUtil.convert2SolrDate(DateUtil.getDate(-1, DateUtil.DATETIME_FORMAT)));
            criterias.addNativeFq(sb.toString());
            criterias.addField("id");
            criterias.ne("id",maybeInterestRequest.getUserId());
            if(maybeInterestRequest.isRand()){
                Random random = new Random();
                criterias.addOrder("rand_"+random.nextInt(1000),"asc");//随机排序字段
            }
            criterias.setStart(maybeInterestRequest.getPage());
            criterias.setRows(maybeInterestRequest.getRows()<2?1:maybeInterestRequest.getRows()/2);

            SolrResponse<UserEntity> solrResponse = solrUserService.query(criterias);
            //附近共同属性1个以上的人显示三个
            Criterias criterias2 = new Criterias();
            StringBuffer qc = new StringBuffer();
            List<String> out3 = new ArrayList<String>();
            SystemUtils.combineStr(hobbyType,hobbyType.length,out3,null);
            qc.append("login_area:").append(maybeInterestRequest.getLoginArea()).append("^5 AND ").append("hobby:(").append(out3.get(0)).append(")^3");
            criterias2.qc(qc.toString());
            criterias2.ge("lastLoginTime", DateUtil.convert2SolrDate(DateUtil.getDate(-1, DateUtil.DATETIME_FORMAT)));
            criterias2.addField("id");
            criterias2.ne("id", maybeInterestRequest.getUserId());
            if(maybeInterestRequest.isRand()){
                Random random = new Random();
                criterias2.addOrder("rand_"+random.nextInt(1000),"asc");//随机排序字段
            }
            criterias2.setStart(maybeInterestRequest.getPage());
            criterias2.setRows(maybeInterestRequest.getRows()<2?1:maybeInterestRequest.getRows()/2);
            SolrResponse<UserEntity> solrResponse2 = solrUserService.query(criterias2);
            List<String> userIds = new ArrayList<String>();
            if(null!=solrResponse&&CollectionUtils.isNotEmpty(solrResponse.getDocs())){
                List<UserEntity> users = solrResponse.getDocs();
                for(UserEntity userEntity:users){
                    userIds.add(userEntity.getId());
                }
            }
            if(null!=solrResponse2&&CollectionUtils.isNotEmpty(solrResponse2.getDocs())){
                List<UserEntity> users = solrResponse2.getDocs();
                for(UserEntity userEntity:users){
                    userIds.add(userEntity.getId());
                }
            }
            return getLastBshoot(userIds, dateLimit, GuideType.MAYBE_INTEREST.getId(),maxPraise);
        }
        return  null;
    }

    /**
     * 附近的人动态
     * @param loginArea
     * @return
     */
    protected List<Bshoot> nearbyBshoot(String userId,String loginArea ,int start,Date dateLimit,boolean maxPraise){
        Criterias criterias = new Criterias();
        criterias.qc("login_area:"+loginArea);
        criterias.addField("id");
        criterias.ne("id",userId);//去掉自己
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
            return getLastBshoot(userIds, dateLimit, GuideType.NEARBY_PEOPLE.getId(),maxPraise);
        }
        return null;
    }

    protected String recommendNewUser(String userId,Integer fansNum,String loginArea,int start){
        //1.新人推荐
        if(null!=fansNum&&fansNum<50) {
            //当前用户粉丝量大于50则不推荐，新人推荐根据用户粉丝量，获取同城用户且当天发布了动态
            String qc = null;
            if(fansNum<10)
                qc="fansNum:[0 TO 9]";
            else if(fansNum>10&&fansNum<30)
                qc="fansNum:[10 TO 29]";
            else if(fansNum>30&&fansNum<50)
                qc="fansNum:[30 TO 49]";
            Criterias criterias = new Criterias();
            criterias.qc(qc);
            criterias.eq("login_area",loginArea);
            criterias.ge("lastPublishTime", DateUtil.convert2SolrDate(DateUtil.getDateStart(0)));
            criterias.ne("id",userId);//不能是自己
            criterias.addField("id");
            criterias.setStart(start*1);
            criterias.setRows(1);
            SolrResponse<UserEntity> solrResponse = solrUserService.query(criterias);
            if(null!=solrResponse&&CollectionUtils.isNotEmpty(solrResponse.getDocs())){
                List<UserEntity> user = solrResponse.getDocs();
                return user.get(0).getId();
            }
        }
        return null;
    }

    protected List<Bshoot> getLastBshoot(List<String> userIds,Date dateLimit,Integer guideType,boolean maxPraise){
        if(CollectionUtils.isEmpty(userIds)) return null;
        List<Bshoot> bshoots=null;
        if(maxPraise)
            bshoots = bshootServiceImpl.getMaxPraiseBshoot(userIds,dateLimit);
        else
            bshoots =  bshootServiceImpl.getUserLastBshoot(userIds,dateLimit);
        if(logger.isDebugEnabled()){
            StackTraceElement[] stack = (new Throwable()).getStackTrace();
            logger.debug(stack[0].getClassName()+"." + stack[0].getMethodName() + " result:" + JSONUtils.toJSONString(bshoots));
        }
        return bshoots;
    }

    public static void main(String[] args){
        System.out.println(DateUtil.convert2SolrDate(DateUtil.getDate(-1, DateUtil.DATETIME_FORMAT)));
        //System.out.println(DateUtil.convert2SolrDate(DateUtil.getDateBeforeHours(-10, DateUtil.DATETIME_FORMAT)));
        //a();
    }
    private static void a(){
        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        System.out.println(stack[0].getClassName()+"." + stack[0].getMethodName() + " result:");
    }
}