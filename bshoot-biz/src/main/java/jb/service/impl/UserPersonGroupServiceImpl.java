package jb.service.impl;

import jb.absx.F;
import jb.dao.UserPersonGroupDaoI;
import jb.model.TuserPersonGroup;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.UserPersonGroup;
import jb.service.UserPersonGroupServiceI;
import jb.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by guxin on 2016/1/1.
 */
@Service
public class UserPersonGroupServiceImpl extends BaseServiceImpl<UserPersonGroup> implements UserPersonGroupServiceI {

    @Autowired
    private UserPersonGroupDaoI userPersonGroupDao;

    @Override
    public DataGrid dataGrid(UserPersonGroup userPersonGroup, PageHelper ph) {
        List<UserPersonGroup> ol = new ArrayList<UserPersonGroup>();
        String hql = " from TuserPersonGroup t ";
        DataGrid dg = dataGridQuery(hql, ph, userPersonGroup, userPersonGroupDao);
        @SuppressWarnings("unchecked")
        List<TuserPersonGroup> l = dg.getRows();
        if (l != null && l.size() > 0) {
            for (TuserPersonGroup t : l) {
                UserPersonGroup o = new UserPersonGroup();
                BeanUtils.copyProperties(t, o);
                ol.add(o);
            }
        }
        dg.setRows(ol);
        return dg;
    }

    protected String whereHql(UserPersonGroup userPersonGroup, Map<String, Object> params) {
        String whereHql = "";
        if (userPersonGroup != null) {
            whereHql += " where 1=1 ";
            if (!F.empty(userPersonGroup.getUserId())) {
                whereHql += " and t.userId = :userId";
                params.put("userId", userPersonGroup.getUserId());
            }
            if (!F.empty(userPersonGroup.getGroupName())) {
                whereHql += " and t.groupName = :groupName";
                params.put("groupName", userPersonGroup.getGroupName());
            }
        }
        return whereHql;
    }

    @Override
    public UserPersonGroup get(String id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TuserPersonGroup t = userPersonGroupDao.get("from TuserPersonGroup t  where t.id = :id", params);
        UserPersonGroup o = new UserPersonGroup();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public void add(UserPersonGroup userPersonGroup) {
        TuserPersonGroup t = new TuserPersonGroup();
        BeanUtils.copyProperties(userPersonGroup, t);
        t.setId(UUID.randomUUID().toString());
        //t.setCreatedatetime(new Date());
        userPersonGroupDao.save(t);
    }

    @Override
    public void edit(UserPersonGroup userPersonGroup) {
        TuserPersonGroup t = userPersonGroupDao.get(TuserPersonGroup.class, userPersonGroup.getId());
        if (t != null) {
            MyBeanUtils.copyProperties(userPersonGroup, t, new String[] { "id" , "createdatetime" },true);
            //t.setModifydatetime(new Date());
        }
    }

    @Override
    public void delete(String id) {
        userPersonGroupDao.delete(userPersonGroupDao.get(TuserPersonGroup.class, id));
    }


    private UserPersonGroup get(String userId, String groupName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("groupName", groupName);
        TuserPersonGroup t = userPersonGroupDao.get("from TuserPersonGroup t  where t.userId = :userId and t.groupName = :groupName", params);
        if(t==null)
            return null;
        UserPersonGroup o = new UserPersonGroup();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public int addPersonGroup(UserPersonGroup userPersonGroup) {
        if(get(userPersonGroup.getUserId(), userPersonGroup.getGroupName())!=null && userPersonGroup.getIsDelete()==0){
            return -1;//已存在分组
        }else if(get(userPersonGroup.getUserId(), userPersonGroup.getGroupName())!=null && userPersonGroup.getIsDelete()!=0){
            UserPersonGroup ua = get(userPersonGroup.getUserId(), userPersonGroup.getGroupName());
            TuserPersonGroup t = new TuserPersonGroup();
            BeanUtils.copyProperties(ua, t);
            t.setIsDelete(0);
            t.setUpdateDatetime(new Date());
            userPersonGroupDao.save(t);
            return 1;
        }else {
            TuserPersonGroup t = new TuserPersonGroup();
            BeanUtils.copyProperties(userPersonGroup, t);
            t.setId(UUID.randomUUID().toString());
            t.setCreateDatetime(new Date());
            t.setUpdateDatetime(new Date());
            userPersonGroupDao.save(t);
            return 1;
        }
    }

    @Override
    public int editPersonGroupName(UserPersonGroup userPersonGroup) {
        TuserPersonGroup t = userPersonGroupDao.get(TuserPersonGroup.class, userPersonGroup.getId());
        if (t != null) {
            t.setGroupName(userPersonGroup.getGroupName());
            userPersonGroupDao.save(t);
            return 1;
        }
        return -1;
    }

}
