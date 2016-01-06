package jb.service;

import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.UserAttention;
import jb.pageModel.UserAttentionGroup;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface UserAttentionServiceI {

	/**
	 * 获取UserAttention数据表格
	 * 
	 * @param userAttention
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(UserAttention userAttention, PageHelper ph);
	
	public DataGrid dataGridUser(UserAttention userAttention, PageHelper ph);
	
	public DataGrid dataGridUser(UserAttention userAttention, PageHelper ph,
								 String id);

	public DataGrid dataGridUserByGroup(UserAttention userAttention, PageHelper ph);

	/**
	 * 添加UserAttention
	 * 
	 * @param userAttention
	 */
	public int add(UserAttention userAttention);

	/**
	 * 添加UserAttention,若无记录则新添加，若有记录则把is_delete改为0
	 *
	 * @param userAttention
	 */
	public int addAttention(UserAttention userAttention);
	
	/**
	 * 取消关注
	 * @param userAttention
	 */
	public int deleteUa(UserAttention userAttention);

	/**
	 * 取消关注,把is_delete参数改为1
	 * @param userAttention
	 */
	public int deleteAttention(UserAttention userAttention);

	/**
	 * 查询是否关注该用户
	 * @param userAttention
	 */
	public int idAttention(UserAttention userAttention);

	/**
	 * 获得UserAttention对象
	 * 
	 * @param id
	 * @return
	 */
	public UserAttention get(String id);

	/**
	 * 修改UserAttention
	 * 
	 * @param userAttention
	 */
	public void edit(UserAttention userAttention);

	/**
	 * 修改UserAttention分组
	 *
	 * @param userAttention
	 */
	public int editAttentionGroup(UserAttention userAttention);

	/**
	 * 删除UserAttention分组
	 *
	 * @param userAttentionGroup
	 */
	public int delUserAtteGroup(UserAttentionGroup userAttentionGroup);

	/**
	 * 删除UserAttention
	 * 
	 * @param id
	 */
	public void delete(String id);
	
	/**
	 * @param userId
	 * @param attUserId
	 * @return
	 */
	public UserAttention get(String userId, String attUserId);

	/**
	 * 好友共同关注的人
	 * @param userId
	 * @return
	 */
	public List<String> friendCommonAtt(String userId,int start,int rows);

	List<String> singleFriednAtt(String userId,int start,int rows);
}
