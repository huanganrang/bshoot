
/*
 * @author John
 * @date - 2015-08-20
 */

package jb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "bshoot_region")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TbshootRegion implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "BshootRegion";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_REGION_LEVEL = "区域级别";
	public static final String ALIAS_REGION_NAME_ZH = "区域中文名";
	public static final String ALIAS_REGION_NAME_EN = "区域英文名";
	public static final String ALIAS_REGION_PARENT_ID = "区域父编码";
	public static final String ALIAS_REGION_ID = "区域编码";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//
	private java.lang.Integer id;
	//
	private java.lang.Integer regionLevel;
	//@Length(max=128)
	private java.lang.String regionNameZh;
	//@Length(max=128)
	private java.lang.String regionNameEn;
	//@Length(max=10)
	private java.lang.String regionParentId;
	//@Length(max=10)
	private java.lang.String regionId;
	//columns END


		public TbshootRegion(){
		}
		public TbshootRegion(Integer id) {
			this.id = id;
		}
		public TbshootRegion(String regionId) {
			this.regionId = regionId;
		}
	

	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public java.lang.Integer getId() {
		return this.id;
	}
	
	@Column(name = "region_level", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getRegionLevel() {
		return this.regionLevel;
	}
	
	public void setRegionLevel(java.lang.Integer regionLevel) {
		this.regionLevel = regionLevel;
	}
	
	@Column(name = "region_name_zh", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public java.lang.String getRegionNameZh() {
		return this.regionNameZh;
	}
	
	public void setRegionNameZh(java.lang.String regionNameZh) {
		this.regionNameZh = regionNameZh;
	}
	
	@Column(name = "region_name_en", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public java.lang.String getRegionNameEn() {
		return this.regionNameEn;
	}
	
	public void setRegionNameEn(java.lang.String regionNameEn) {
		this.regionNameEn = regionNameEn;
	}
	
	@Column(name = "region_parent_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getRegionParentId() {
		return this.regionParentId;
	}
	
	public void setRegionParentId(java.lang.String regionParentId) {
		this.regionParentId = regionParentId;
	}
	
	@Column(name = "region_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getRegionId() {
		return this.regionId;
	}
	
	public void setRegionId(java.lang.String regionId) {
		this.regionId = regionId;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("RegionLevel",getRegionLevel())
			.append("RegionNameZh",getRegionNameZh())
			.append("RegionNameEn",getRegionNameEn())
			.append("RegionParentId",getRegionParentId())
			.append("RegionId",getRegionId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BshootRegion == false) return false;
		if(this == obj) return true;
		BshootRegion other = (BshootRegion)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

