package jb.service;

import jb.pageModel.Bshoot;

import java.util.List;

/**
 * 首页热门引导
 * Created by Zhou Yibing on 2015/10/22.
 */
public interface HotGuideServiceI {
    /**
     * 引导外页
     * @param userId
     * @return
     */
    List<Bshoot> guideExternalPage(String userId,Integer start,Integer rows);

    /**
     * 引导内页
     * @param userId 登录用户id
     * @param guideType 引导类型
     * @param start 开始位置
     * @return
     */
    List<Bshoot> guideInternalPage(String userId,Integer guideType,Integer start,Integer rows);
}
