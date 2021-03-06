package jb.service;

import jb.model.TmessageCount;
import jb.pageModel.DataGrid;
import jb.pageModel.Message;
import jb.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface MessageServiceI {
	 static final String MT_03 = "MT03";//评论
	 static final String MT_04 = "MT04";
	 static final String MT_05 = "MT05";//收藏
	 static final String MT_06 = "MT06";//分享
	 static final String MT_07 = "MT07";//转发

	/**
	 * 获取Message数据表格
	 * 
	 * @param message
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(Message message, PageHelper ph);

	/**
	 * 添加Message
	 * 
	 * @param message
	 */
	public void add(Message message);
	
	/**
	 * 添加Message
	 * @param message
	 */
	public TmessageCount addAndCount(Message message);

	/**
	 * 获得Message对象
	 * 
	 * @param id
	 * @return
	 */
	public Message get(String id);

	/**
	 * 修改Message
	 * 
	 * @param message
	 */
	public void edit(Message message);

	/**
	 * 删除Message
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 获取@我的消息列表
	 * @param message
	 * @param ph
	 * @return
	 */
	public DataGrid dataGridAtMine(Message message, PageHelper ph);

	/**
	 * 获取评论消息列表
	 * @param message
	 * @param ph
	 * @return
	 */
	public DataGrid dataGridComment(Message message, PageHelper ph);
	
	/**
	 * 获取赞消息列表
	 * @param message
	 * @param ph
	 * @return
	 */
	public DataGrid dataGridPraise(Message message, PageHelper ph);

	/**
	 * 添加并发送消息
	 * @param mType
	 * @param userId
	 * @param relationId
	 * @param content
	 * @return
	 */
	String addAndSendMessage(String mType,String userId,String relationId,String content);

	/**
	 * 发送消息
	 * @param userId
	 * @param message
	 * @return
	 */
	boolean sendMessage(String userId,Object message);

}
