package jb.service.impl;

import jb.absx.F;
import jb.dao.UserAttentionDaoI;
import jb.dao.UserAttentionGroupDaoI;
import jb.model.TuserAttentionGroup;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.pageModel.UserAttentionGroup;
import jb.service.UserAttentionGroupServiceI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by guxin on 2016/1/1.
 */
@Service
public class UserAttentionGroupServiceImpl extends BaseServiceImpl<UserAttentionGroup> implements UserAttentionGroupServiceI {

    @Autowired
    private UserAttentionGroupDaoI userAttentionGroupDao;

    @Autowired
    private UserAttentionDaoI userAttentionDao;

    @Override
    public DataGrid dataGrid(UserAttentionGroup userAttentionGroup, PageHelper ph) {
        List<UserAttentionGroup> ol = new ArrayList<UserAttentionGroup>();
        String hql = " from TuserAttentionGroup t ";
        DataGrid dg = dataGridQuery(hql, ph, userAttentionGroup, userAttentionGroupDao);
        @SuppressWarnings("unchecked")
        List<TuserAttentionGroup> l = dg.getRows();
        if (l != null && l.size() > 0) {
            for (TuserAttentionGroup t : l) {
                UserAttentionGroup o = new UserAttentionGroup();
                BeanUtils.copyProperties(t, o);
                ol.add(o);
            }
        }
        dg.setRows(ol);
        return dg;
    }

    protected String whereHql(UserAttentionGroup userAttentionGroup, Map<String, Object> params) {
        String whereHql = "";
        if (userAttentionGroup != null) {
            whereHql += " where 1=1 and t.isDelete=0 ";
            if (!F.empty(userAttentionGroup.getUserId())) {
                whereHql += " and t.userId = :userId";
                params.put("userId", userAttentionGroup.getUserId());
            }
            if (!F.empty(userAttentionGroup.getGroupName())) {
                whereHql += " and t.groupName = :groupName";
                params.put("groupName", userAttentionGroup.getGroupName());
            }
        }
        return whereHql;
    }

    @Override
    public UserAttentionGroup get(String id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TuserAttentionGroup t = userAttentionGroupDao.get("from TuserAttentionGroup t  where t.id = :id", params);
        UserAttentionGroup o = new UserAttentionGroup();
        if(null!=t)
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public TuserAttentionGroup get(String userId,String groupName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("groupName", groupName);
        TuserAttentionGroup t = userAttentionGroupDao.get("from TuserAttentionGroup t  where t.userId = :userId and t.groupName = :groupName", params);
        return t;
    }

    @Override
    public Json addAttention(UserAttentionGroup userAttentionGroup) {
        Json j = new Json();
        if(get(userAttentionGroup.getUserId(), userAttentionGroup.getGroupName())!=null){
            TuserAttentionGroup t = get(userAttentionGroup.getUserId(), userAttentionGroup.getGroupName());
            if(t.getIsDelete()==0){
                j.setSuccess(false);
                j.setMsg("已存在该分组");
                j.setObj(t);
                return j;//已存在分组
            }else {
                t.setIsDelete(0);
                t.setUpdateDatetime(new Date());
                userAttentionGroupDao.save(t);
                j.setSuccess(true);
                j.setMsg("添加分组成功");
                j.setObj(t);
                return j;
            }
        }else {
            TuserAttentionGroup t = new TuserAttentionGroup();
            BeanUtils.copyProperties(userAttentionGroup, t);
            t.setId(UUID.randomUUID().toString());
            t.setIsDelete(0);
            t.setCreateDatetime(new Date());
            t.setUpdateDatetime(new Date());
            userAttentionGroupDao.save(t);
            j.setSuccess(true);
            j.setMsg("添加分组成功");
            j.setObj(t);
            return j;
        }
    }

    @Override
    public int edit(UserAttentionGroup userAttentionGroup) {
        TuserAttentionGroup t = userAttentionGroupDao.get(TuserAttentionGroup.class, userAttentionGroup.getId());
        if (t != null) {
            t.setGroupName(userAttentionGroup.getGroupName());
            userAttentionGroupDao.save(t);
            return 1;
        }
        return -1;
    }

    @Override
    public void delete(String id) {
        userAttentionGroupDao.delete(userAttentionGroupDao.get(TuserAttentionGroup.class, id));
    }

    @Override
    public int deleteAttentionGroup(UserAttentionGroup userAttentionGroup){
        if(!F.empty(userAttentionGroup.getId())){
            TuserAttentionGroup t = userAttentionGroupDao.get(TuserAttentionGroup.class, userAttentionGroup.getId());
            if(t==null){
                return -1;
            }
            t.setIsDelete(1);
            userAttentionGroupDao.save(t);
            String sql = "update user_attention set attention_group=null where attention_group="+userAttentionGroup.getId();
            userAttentionDao.executeSql(sql);
            return 1;
        }
        return -1;
    }
}
