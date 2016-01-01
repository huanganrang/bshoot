package jb.service;

import jb.bizmodel.RecommendUser;

import java.util.List;

/**
 * Created by zhou on 2016/1/1.
 */
public interface RecommendServiceI {

    /**
     * 推荐用户
     * @param  loginArea 当前用户登录区域
     */
    public List<RecommendUser> recommendUser(String loginArea);
}
