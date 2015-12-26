
/*
 * @author John
 * @date - 2015-09-15
 */

package jb.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private String id;
	//@Length(max=256)
	private String title;
	//@Length(max=2147483647)
	private String description;
	//@Length(max=4)
	private String type;
	//
	private Boolean hotStatus;
	//
	private Float hot;
	//
	private java.util.Date createTime;
	//columns END


		public TbshootSkill(){
		}
		public TbshootSkill(String id) {
			this.id = id;
		}


	public void setId(String id) {
		this.id = id;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	@Column(name = "title", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 2147483647)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "hot_status", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getHotStatus() {
		return this.hotStatus;
	}

	public void setHotStatus(Boolean hotStatus) {
		this.hotStatus = hotStatus;
	}

	@Column(name = "hot", unique = false, nullable = true, insertable = true, updatable = true, length = 12)
	public Float getHot() {
		return this.hot;
	}

	public void setHot(Float hot) {
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

