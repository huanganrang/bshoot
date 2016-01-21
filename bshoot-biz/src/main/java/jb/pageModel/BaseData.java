package jb.pageModel;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@ApiModel("BaseData")
public class BaseData implements java.io.Serializable{
	@ApiModelProperty("id")
	private String id;
	@ApiModelProperty("父级id")
	private String pid;
	@ApiModelProperty("名称")
	private String name;
	@ApiModelProperty("序号")
	private Integer seq;
	@ApiModelProperty("类型编码")
	private String basetypeCode;
	@ApiModelProperty("编码名称")
	private String codeName;
	@ApiModelProperty("描述")
	private String description;
	private List<BaseData> children = new ArrayList<>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getBasetypeCode() {
		return basetypeCode;
	}
	public void setBasetypeCode(String basetypeCode) {
		this.basetypeCode = basetypeCode;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public List<BaseData> getChildren() {
		return children;
	}

	public void setChildren(List<BaseData> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "BaseData{" +
				"id='" + id + '\'' +
				", pid='" + pid + '\'' +
				", name='" + name + '\'' +
				", seq=" + seq +
				", basetypeCode='" + basetypeCode + '\'' +
				", codeName='" + codeName + '\'' +
				", description='" + description + '\'' +
				", children=" + children +
				'}';
	}
}
