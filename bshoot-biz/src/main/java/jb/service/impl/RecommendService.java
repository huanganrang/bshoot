package jb.service.impl;

import com.google.common.collect.Lists;
import jb.bizmodel.RecommendUser;
import jb.pageModel.BaseData;
import jb.pageModel.Bshoot;
import jb.pageModel.User;
import jb.service.BasedataServiceI;
import jb.service.BshootServiceI;
import jb.service.RecommendServiceI;
import jb.service.UserServiceI;
import jb.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
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

    @Autowired
    private UserServiceI userServiceImpl;
    @Autowired
    private SolrUserService solrUserService;
    @Autowired
    private BasedataServiceI basedataServiceImpl;
    @Autowired
    private BshootServiceI bshootServiceImpl;

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
        criterias.between("createTime","", DateUtil.convert2SolrDate(DateUtil.getDate(-3,DateUtil.DATETIME_FORMAT)));
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
    public void recommendHot() {
        Date tenHousrago = DateUtil.getDateBeforeHours(-10);
       List<Bshoot> bshoots =  bshootServiceImpl.getHotBshoots(tenHousrago, 200, 50);
       if(bshoots.size()<50){
           Date oneDayago = DateUtil.stringToDate(DateUtil.getDate(-1));
           List<Bshoot> bs2 = bshootServiceImpl.getHotBshoots(oneDayago,200,50-bshoots.size());
           if(CollectionUtils.isNotEmpty(bs2))
             bshoots.addAll(bs2);
       }
        //TODO
    }

    @Override
    public void recommend() {

    }

    public static void main(String[] args){
        System.out.println(DateUtil.convert2SolrDate(DateUtil.getDate(-3,DateUtil.DATETIME_FORMAT)));
        System.out.println(DateUtil.convert2SolrDate(DateUtil.getDateBeforeHours(-10, DateUtil.DATETIME_FORMAT)));
    }
}
