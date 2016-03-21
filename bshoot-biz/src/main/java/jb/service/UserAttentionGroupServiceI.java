package jb.service;

import jb.model.TuserAttentionGroup;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.pageModel.UserAttentionGroup;

/**
 * Created by guxin on 2016/1/1.
 */
public interface UserAttentionGroupServiceI {
    /**
     * 获取UserAttentionGroup数据表格
     *
     * @param userAttentionGroup
     *            参数
     * @param ph
     *            分页帮助类
     * @return
     */
    public DataGrid dataGrid(UserAttentionGroup userAttentionGroup, PageHelper ph);

    /**
     * 添加UUserAttentionGroup,若无记录则新添加，若有记录则把is_delete改为0
     *
     * @param userAttentionGroup
     */
    public Json addAttention(UserAttentionGroup userAttentionGroup);

    /**
     * 获得UserAttentionGroup对象
     *
     * @param id
     * @return
     */
    public UserAttentionGroup get(String id);

    /**
     * 获得UserAttentionGroup对象
     *
     * @param userId
     * * @param groupName
     * @return
     */
    public TuserAttentionGroup get(String userId, String groupName);

    /**
     * 修改UserAttentionGroup
     *
     * @param userAttentionGroup
     */
    public int edit(UserAttentionGroup userAttentionGroup);

    /**
     * 删除UserAttentionGroup
     *
     * @param id
     */
    public void delete(String id);

    /**
     * 删除UserAttentionGroup
     *
     * @param userAttentionGroup
     */
    public  int deleteAttentionGroup(UserAttentionGroup userAttentionGroup);
}
