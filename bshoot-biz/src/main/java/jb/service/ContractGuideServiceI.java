package jb.service;

import jb.bizmodel.RecommendUser;
import jb.pageModel.Bshoot;

import java.util.List;
import java.util.Map;

/**
 * Created by Zhou Yibing on 2016/1/8.
 */
public interface ContractGuideServiceI {
    Map<Integer,List<RecommendUser>> guideExternalPage(String userId, Integer start,Integer rows);

    List<RecommendUser> guideInternalPage(String userId, Integer guideType, Integer start, Integer rows);
}
