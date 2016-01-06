package jb.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by guxin on 2016/1/1.
 */
@Entity
@Table(name = "user_person_group")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TuserPersonGroup implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    //alias
    public static final String TABLE_ALIAS = "TuserPersonGroup";
    public static final String ALIAS_ID = "主键";
    public static final String ALIAS_USER_ID = "用户ID";
    public static final String ALIAS_GROUP_NAME = "分组名称";
    public static final String ALIAS_CREATE_DATETIME = "创建时间";
    public static final String ALIAS_UPDATE_DATETIME = "修改时间";

    //date formats
    public static final String FORMAT_CREATE_DATETIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
    public static final String FORMAT_UPDATE_DATETIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;

    //可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
    //columns START
    //@Length(max=36)
    private String id;
    //@Length(max=36)
    private String userId;
    //@Length(max=36)
    private String groupName;
    private java.util.Date createDatetime;
    private java.util.Date updateDatetime;
    private java.lang.Integer isDelete;

    public TuserPersonGroup(){}
    public TuserPersonGroup(String id){this.id = id;}

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "group_name", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Column(name = "create_datetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Column(name = "update_datetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    @Column(name = "is_delete", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

}
