package jb.service;

import jb.bizmodel.RecommendUser;
import jb.pageModel.Bshoot;

import java.util.List;

/**
 * Created by zhou on 2016/1/1.
 */
public interface RecommendServiceI {

    /**
     * 推荐用户
     */
    public List<RecommendUser> recommendUser(String userId);

    /**
     * 首页热门动态推荐
     * 1按一天以内的发布的动态（当前时间前10小时以内）2按获得打赏200以上的3这些动态按时间排序）（若没有满足规则用户50以上）
     *比如显示一天内获得打赏200以上的（按时间排序）
     */
    public  List<Bshoot> recommendHot(String userId,Integer start,Integer fileType,Integer interested);

    /**
     * 首页推荐
     * @param  userId 当前登录用户id
     * @param start 第几页 从0开始
     */
    public  List<Bshoot> recommend(String userId,Integer start,Integer rows);
}