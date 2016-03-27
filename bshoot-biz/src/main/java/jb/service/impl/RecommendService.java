package jb.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.common.collect.Lists;
import component.redis.model.BshootCounter;
import component.redis.service.CounterServiceI;
import jb.absx.F;
import jb.bizmodel.GuideType;
import jb.bizmodel.RecommendUser;
import jb.listener.Application;
import jb.pageModel.*;
import jb.service.*;
import jb.util.ConfigUtil;
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
public class RecommendService extends UserRecommendService implements RecommendServiceI,CommonRecommendServiceI{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BasedataServiceI basedataService;
    @Autowired
    protected BshootServiceI bshootService;
    @Autowired
    protected UserProfileServiceI userProfileService;
    @Autowired
    private UserHobbyServiceI userHobbyService;
    @Autowired
    private CounterServiceI counterService;
    @Autowired
    private BshootSquareServiceI bshootSquareService;

    @Override
    public List<RecommendUser> recommendUser( String userId) {
        //获得6个大v,4个小v
        String[] fileds = new String[]{"id","headImg","bardian","utype","usex","member_v","hobby","nickname","is_star","is_tarento","att_square","login_area","j"};
        Criterias criterias = new Criterias();
        criterias.qc("fansNum:[200 TO *]");
        criterias.gt("month_praise","50");
        Random random = new Random();
        criterias.addOrder("rand_"+random.nextInt(1000),"asc");//随机排序字段
        criterias.addField(fileds);
        criterias.setStart(0);
        criterias.setRows(6);
        SolrResponse<UserEntity> userV = solrUserService.query(criterias);
        List<UserEntity> userList = new ArrayList<UserEntity>();
        if(null!=userV&&CollectionUtils.isNotEmpty(userV.getDocs()))
            userList = userV.getDocs();

        Criterias criterias2 = new Criterias();
        criterias2.qc("fansNum:[* TO 10]");
        UserProfile profile = userProfileService.get(userId);
        criterias2.eq("login_area",profile.getLoginArea());
        criterias2.ge("createTime", DateUtil.convert2SolrDate(DateUtil.getDateStart(-3)));
        criterias2.addOrder("rand_" + random.nextInt(1000), "asc");//随机排序字段
        criterias2.addField(fileds);
        criterias2.setStart(0);
        criterias2.setRows(4);
        SolrResponse<UserEntity> userv = solrUserService.query(criterias2);
        if(null!=userv&&CollectionUtils.isNotEmpty(userv.getDocs()))
            userList.addAll(userv.getDocs());
        return fillUser(userList);
    }

    protected List<RecommendUser> fillUser(List<UserEntity> userList){
        List<RecommendUser> recommendUsers = new ArrayList<RecommendUser>();
        Set<String> baseDataIds = new HashSet<String>();
        for(UserEntity userEntity:userList){
            if(null!=userEntity.getLogin_area())
                baseDataIds.add(userEntity.getLogin_area());
            if(null!=userEntity.getUsex())
                baseDataIds.add(userEntity.getUsex());
            if(null!=userEntity.getHobby())
                baseDataIds.addAll(userEntity.getHobby());
            if(null!= userEntity.getUtype())
                baseDataIds.add(userEntity.getUtype());
            if(null!=userEntity.getMember_v())
                baseDataIds.add(userEntity.getMember_v());
            if(null!=userEntity.getJob())
                baseDataIds.addAll(userEntity.getJob());
        }
        Map<String,BaseData> baseDataMap = basedataService.getBaseDatas(baseDataIds,Map.class);
        BaseData baseData;
        for(UserEntity userEntity:userList){
            RecommendUser recommendUser = new RecommendUser();
            recommendUser.setId(userEntity.getId());
            recommendUser.setNickname(userEntity.getNickname());
            recommendUser.setHeadImage(userEntity.getHeadImg());
            recommendUser.setBardian(userEntity.getBardian());
            recommendUser.setIsStar(userEntity.getIs_star());
            recommendUser.setIsTarento(userEntity.getIs_tarento());
            baseData = baseDataMap.get(userEntity.getLogin_area());
            if(null!=baseData)
                recommendUser.setArea(baseData.getName());
            List<String> hobbies = userEntity.getHobby();
            if(hobbies!=null) {
                List<String> hobbyNames = Lists.newArrayList();
                for (String hobby : hobbies) {
                    baseData = baseDataMap.get(hobby);
                    if (null != baseData)
                        hobbyNames.add(baseData.getName());
                }
                recommendUser.setHobby(hobbyNames);
            }
            baseData = baseDataMap.get(userEntity.getUsex());
            if(null!=baseData)
                recommendUser.setSex(baseData.getName());
            baseData = baseDataMap.get(userEntity.getUtype());
            if(null!=baseData)
                recommendUser.setuType(baseData.getName());
            baseData = baseDataMap.get(userEntity.getMember_v());
            if(null!=baseData)
                recommendUser.setMemberV(baseData.getName());
            baseData = baseDataMap.get(userEntity.getJob());
            List<String> jobs = userEntity.getJob();
            if(null!=jobs){
                List<String> jobNames = Lists.newArrayList();
                for(String job:jobs){
                baseData = baseDataMap.get(job);
                if(null!=baseData)
                    jobNames.add(baseData.getName());
                 }
                recommendUser.setJob(jobNames);
            }
            recommendUsers.add(recommendUser);
        }
        return recommendUsers;
    }

    @Override
    public List<Bshoot> recommendHot(String userId,Integer start,Integer fileType,Integer interested) {
        //可以使用solr查询，但是在用户帅选同兴趣的情况下，需要solr的core_user和core_bs联合查询，但是在分布式环境下solr联合查询有文档说是可以实现，考虑风险暂且使用库查询 _query_:"{!join fromIndex=core_user from=id to=userId v='hobby:(HO01 OR HO02 HO03)'}"
        String rhRule = ConfigUtil.getValue("RL0001","-1|200");
        String[] rules = rhRule.split("[|;]");
        Date oneDayago = DateUtil.stringToDate(DateUtil.getDate(Integer.parseInt(rules[0]), DateUtil.DATETIME_FORMAT),DateUtil.DATETIME_FORMAT);
        String hobby = null;
        if(interested==1) {
            UserHobby userHobby = userHobbyService.getUserHobby(userId);
            String[] hobbyType = userHobby.getHobbyType().split(",");
            List<String> out = new ArrayList<String>();
            SystemUtils.combineStr(hobbyType, hobbyType.length, out, "OR");
            hobby = out.get(0);
        }
        HotShootRequest hotShootRequest = new HotShootRequest(oneDayago,Integer.parseInt(rules[1]),start,fileType,hobby,50);
        List<Bshoot> bshoots =  bshootService.getHotBshoots(hotShootRequest);
        return fillBshoot(bshoots);
    }

    protected List<Bshoot> fillBshoot(List<Bshoot> bshoots){
        if(CollectionUtils.isEmpty(bshoots)) return bshoots;
        Set<String> userIds = new HashSet<String>();
        List<String> bshootIds = new ArrayList<String>();
        for(Bshoot bshoot:bshoots) {
            userIds.add(bshoot.getUserId());
            bshootIds.add(bshoot.getId());
        }
        StringBuffer out = new StringBuffer();
        for(String user:userIds){
            out.append(SystemUtils.solrStringTrasfer(user)).append(" ");
        }
        Criterias criterias = new Criterias();
        criterias.addNativeFq("id:("+out.toString()+")");
        criterias.addField(new String[]{"id","headImg","member_v","hobby","nickname"});
        criterias.setStart(0);
        criterias.setRows(userIds.size());
        SolrResponse<UserEntity> userEntitySolrResponse = solrUserService.query(criterias);
        if(null!=userEntitySolrResponse&&CollectionUtils.isNotEmpty(userEntitySolrResponse.getDocs())){
            List<UserEntity> userEntities = userEntitySolrResponse.getDocs();
            Set<String> hobbies = new HashSet<String>();
            Map<String,UserEntity> userEntityMap = new HashMap<String, UserEntity>();
            UserEntity userEntity;
            for(int i=0;i<userEntities.size();i++){
                userEntity = userEntities.get(i);
                userEntityMap.put(userEntity.getId(),userEntity);
                if(CollectionUtils.isNotEmpty(userEntity.getHobby()))
                hobbies.addAll(userEntity.getHobby());
            }
            List<String> h;
            //获得各动态的计数
            List<BshootCounter> bshootCounters = counterService.getCounterByBshoots(bshootIds);
            Bshoot bshoot = null;
            BshootCounter bshootCounter = null;
            for(int i=0;i<bshoots.size();i++){
                bshoot = bshoots.get(i);
                userEntity = userEntityMap.get(bshoot.getUserId());
                bshoot.setUserName(userEntity.getNickname());
                bshoot.setMemberV(userEntity.getMember_v());
                bshoot.setUserHeadImage(userEntity.getHeadImg());
                h = new ArrayList<String>();
                if(CollectionUtils.isNotEmpty(userEntity.getHobby()))
                for(String hob:userEntity.getHobby()){
                    h.add(ConfigUtil.getValue(hob));
                }
                bshoot.setHobby(h);
                bshootCounter = bshootCounters.get(i);
                if(null!=bshootCounter){
                    if(0!=bshootCounter.getCollectCount())
                    bshoot.setBsCollect(bshootCounter.getCollectCount());
                    if(0!=bshootCounter.getCommentCount())
                    bshoot.setBsComment(bshootCounter.getCommentCount());
                    if(0!=bshootCounter.getForwardCount())
                    bshoot.setBsForward(bshootCounter.getForwardCount());
                    if(0!=bshootCounter.getPlayCount())
                    bshoot.setBsPlay(bshootCounter.getPlayCount());
                    if(0!=bshootCounter.getShareCount())
                    bshoot.setBsShare(bshootCounter.getShareCount());
                    if(0!=bshootCounter.getPraiseCount())
                    bshoot.setBsPraise(bshootCounter.getPraiseCount());
                }
            }
        }
        return bshoots;
    }

    @Override
    public List<Bshoot> recommend(String userId,Integer start,Integer rows) {
        List<Bshoot> bshoots = new ArrayList<Bshoot>();

        Date threeDayAgo = DateUtil.stringToDate(DateUtil.getDateStart(-3));
        //2.好友共同关注的人 done
        AttentionRequest attentionRequest = new AttentionRequest(userId,null,rows*start,rows);
        List<String> friendCommonBshoot = friendCommonAtt(attentionRequest);
        if(CollectionUtils.isNotEmpty(friendCommonBshoot)) {
            bshoots.addAll(getLastBshoot(friendCommonBshoot,threeDayAgo, GuideType.FRIEND_ATT.getId()));
        }

        //3.我评论打赏过的人 done
        PraiseCommentRequest praiseCommentRequest = new PraiseCommentRequest(userId,null,rows*start,rows);
        List<String> comment_praise = meCommentPraisedUser(praiseCommentRequest);
        if(CollectionUtils.isNotEmpty(comment_praise)) {
            bshoots.addAll(getLastBshoot(comment_praise, threeDayAgo, GuideType.ME_COMMENT_PRAISE.getId()));
        }

        //4.好友打赏过的人 done
        PraiseCommentRequest praiseCommentRequest2 = new PraiseCommentRequest(userId,null,rows*start,rows);
        List<String> friendPraise = friendPraise(praiseCommentRequest2);
        if(CollectionUtils.isNotEmpty(friendPraise)){
            bshoots.addAll(getLastBshoot(friendPraise, threeDayAgo, GuideType.FRIEND_PRAISE.getId()));
        }

        //获得当前用户画像
        UserProfile userProfile = userProfileService.get(userId);

        //5、可能感兴趣的人 done
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
            bshoots.addAll(getLastBshoot(mabyeInterested, threeDayAgo, GuideType.MAYBE_INTEREST.getId()));
        }

        //6.可能认识的人 done
        UserMobilePersonRequest userMobilePersonRequest = new UserMobilePersonRequest(userId,rows*start,rows);
        List<String> maybeKnow = maybeKnow(userMobilePersonRequest);
        if(CollectionUtils.isNotEmpty(maybeKnow)) {
            bshoots.addAll(getLastBshoot(maybeKnow,threeDayAgo, GuideType.MAYBE_KNOW.getId()));
        }

        //7.附近的人 done
        NearbyRequest sameCityRequest = new NearbyRequest();
        sameCityRequest.setUserId(userId);
        sameCityRequest.setLoginArea(userProfile.getLoginArea());
        sameCityRequest.setPage(rows*start);
        sameCityRequest.setRows(rows);
        List<String> nearbyUsers = super.nearbyUser(sameCityRequest);
        if(CollectionUtils.isNotEmpty(nearbyUsers)) {
            bshoots.addAll(getLastBshoot(nearbyUsers,threeDayAgo, GuideType.NEARBY_PEOPLE.getId()));
        }

        fillBshoot(bshoots);
        Collections.shuffle(bshoots);
        //1.新人推荐 done
        String uid = recommendNewUser(userId,userProfile.getFansNum(),userProfile.getLoginArea(),start);
        if(null==uid){
            bshoots.add(0,null);
        }else {
            bshoots.addAll(0,getLastBshoot(Lists.newArrayList(uid), DateUtil.stringToDate(DateUtil.getDateStart(-1)), GuideType.NEW_USER.getId()));//固定在第一位
        }
        return bshoots;
    }

    protected List<String> friendPraise(PraiseCommentRequest praiseCommentRequest){
        List<String> userIds = bshootPraiseServiceImpl.friendHasPraisedUser(praiseCommentRequest);
        if(CollectionUtils.isEmpty(userIds)){
            userIds = bshootPraiseServiceImpl.singleFriendHasPraisedUser(praiseCommentRequest);
        }
       return userIds;
    }

    //好友共同关注的人
    protected List<String> friendCommonAtt(AttentionRequest attentionRequest){
        List<String> userAttentionList = super.friendCommonAttUser(attentionRequest);
        if(CollectionUtils.isEmpty(userAttentionList)){
            //显示单个好友关注的人动态
            userAttentionList = super.singleFriendAttUser(attentionRequest);
        }
        return userAttentionList;
    }

    //可能认识的人
    protected List<String> maybeKnow(UserMobilePersonRequest request){
        List<String> userMobilePersonList = super.notAttMobileUser(request);
        List<String> userIds = new ArrayList<String>();
        int rows = request.getRows();
        if(CollectionUtils.isEmpty(userMobilePersonList)){
            userMobilePersonList = new ArrayList<String>();
            rows = 2*rows;
        }else if(userMobilePersonList.size()<request.getRows()){
            rows = 2*rows-userMobilePersonList.size();
        }
        request.setRows(rows);
        List<String> userMobilePersonList2 = super.notAttMobileUser(request);
        if(CollectionUtils.isNotEmpty(userMobilePersonList2)) userMobilePersonList.addAll(userMobilePersonList2);
        return userMobilePersonList;
    }

    //可能感兴趣的人
    protected List<String> mabyeInterest(MaybeInterestRequest maybeInterestRequest){
        UserHobby userHobby  = userHobbyService.getUserHobby(maybeInterestRequest.getUserId());
        if(null!=userHobby&& StringUtils.isNotEmpty(userHobby.getHobbyType())){
            maybeInterestRequest.setHobby(Lists.newArrayList(userHobby.getHobbyType().split(",")));
            maybeInterestRequest.setPropertyCount(3);
            maybeInterestRequest.setRows(maybeInterestRequest.getRows()<2?1:maybeInterestRequest.getRows()/2);
            List<String> userIds = new ArrayList<String>();
            userIds = super.mabyeInterestedUser(maybeInterestRequest);

            //附近共同属性1个以上的人显示三个
            maybeInterestRequest.setPropertyCount(1);
            maybeInterestRequest.setDistance(5);
            maybeInterestRequest.setRows(maybeInterestRequest.getRows()<2?1:maybeInterestRequest.getRows()/2);
            List<String> userIds2 = super.mabyeInterestedUser(maybeInterestRequest);
            if(CollectionUtils.isNotEmpty(userIds2))
                userIds.addAll(userIds2);
            return userIds;

           /* String[] hobbyType = userHobby.getHobbyType().split(",");
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
            }*/
        }
        return  null;
    }

    protected String recommendNewUser(String userId,Integer fansNum,String loginArea,int start){
        //1.新人推荐
        fansNum = fansNum ==null?0:fansNum;
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
            if(!F.empty(loginArea))
            criterias.eq("login_area",loginArea);
            //criterias.ge("lastPublishTime", DateUtil.convert2SolrDate(DateUtil.getDateStart(0)));
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

    protected List<Bshoot> getLastBshoot(List<String> userIds,Date dateLimit,Integer guideType){
        if(CollectionUtils.isEmpty(userIds)) return null;
        List<Bshoot> bshoots= bshootService.getUserLastBshoot(userIds,dateLimit);
        for(Bshoot bshoot:bshoots){
            bshoot.setGuideType(guideType);
        }
        if(logger.isDebugEnabled()){
            StackTraceElement[] stack = (new Throwable()).getStackTrace();
            logger.debug(stack[0].getClassName()+"." + stack[0].getMethodName() + " result:" + JSONUtils.toJSONString(bshoots));
        }
        return bshoots==null?new ArrayList<Bshoot>():bshoots;
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