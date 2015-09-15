
/*
 * @author John
 * @date - 2015-09-15
 */

package jb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "bshoot_skill")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TbshootSkill implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "BshootSkill";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_TITLE = "标题";
	public static final String ALIAS_DESCRIPTION = "详情描述";
	public static final String ALIAS_TYPE = "分类";
	public static final String ALIAS_HOT_STATUS = "hot标记";
	public static final String ALIAS_HOT = "热门值";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = jb.util.Constants.DATE_FORMAT;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=256)
	private java.lang.String title;
	//@Length(max=2147483647)
	private java.lang.String description;
	//@Length(max=4)
	private java.lang.String type;
	//
	private java.lang.Boolean hotStatus;
	//
	private java.lang.Float hot;
	//
	private java.util.Date createTime;
	//columns END


		public TbshootSkill(){
		}
		public TbshootSkill(String id) {
			this.id = id;
		}
	

	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public java.lang.String getId() {
		return this.id;
	}
	
	@Column(name = "title", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public java.lang.String getTitle() {
		return this.title;
	}
	
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 2147483647)
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setDescription(java.lang.String description) {
		this.description = description;
	}
	
	@Column(name = "type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getType() {
		return this.type;
	}
	
	public void setType(java.lang.String type) {
		this.type = type;
	}
	
	@Column(name = "hot_status", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.lang.Boolean getHotStatus() {
		return this.hotStatus;
	}
	
	public void setHotStatus(java.lang.Boolean hotStatus) {
		this.hotStatus = hotStatus;
	}
	
	@Column(name = "hot", unique = false, nullable = true, insertable = true, updatable = true, length = 12)
	public java.lang.Float getHot() {
		return this.hot;
	}
	
	public void setHot(java.lang.Float hot) {
		this.hot = hot;
	}
	

	@Column(name = "create_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Title",getTitle())
			.append("Description",getDescription())
			.append("Type",getType())
			.append("HotStatus",getHotStatus())
			.append("Hot",getHot())
			.append("CreateTime",getCreateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BshootSkill == false) return false;
		if(this == obj) return true;
		BshootSkill other = (BshootSkill)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

