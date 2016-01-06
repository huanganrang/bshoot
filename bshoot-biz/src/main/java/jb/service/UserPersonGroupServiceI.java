package jb.service;

import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.UserPersonGroup;

/**
 * Created by guxin on 2016/1/1.
 */
public interface UserPersonGroupServiceI {
    /**
     * 获取UserPersonGroup数据表格
     *
     * @param userPersonGroup
     *            参数
     * @param ph
     *            分页帮助类
     * @return
     */
    public DataGrid dataGrid(UserPersonGroup userPersonGroup, PageHelper ph);

    /**
     * 添加UserPersonGroup
     *
     * @param userPersonGroup
     */
    public void add(UserPersonGroup userPersonGroup);

    /**
     * 获得UserPersonGroup对象
     *
     * @param id
     * @return
     */
    public UserPersonGroup get(String id);

    /**
     * 修改UserPersonGroup
     *
     * @param userPersonGroup
     */
    public void edit(UserPersonGroup userPersonGroup);

    /**
     * 删除UserPersonGroup
     *
     * @param id
     */
    public void delete(String id);
}
