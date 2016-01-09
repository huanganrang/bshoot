package jb.service;

import jb.pageModel.ApiTest;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ApiTestServiceI {

	/**
	 * 获取ApiTest数据表格
	 * 
	 * @param apiTest
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ApiTest apiTest, PageHelper ph);

	/**
	 * 添加ApiTest
	 * 
	 * @param apiTest
	 */
	public void add(ApiTest apiTest);

	/**
	 * 获得ApiTest对象
	 * 
	 * @param id
	 * @return
	 */
	public ApiTest get(String id);

	/**
	 * 修改ApiTest
	 * 
	 * @param apiTest
	 */
	public void edit(ApiTest apiTest);

	/**
	 * 删除ApiTest
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 获取接口列表
	 */
	public List<ApiTest> getApiList(String name);

	/**
	 * 获取接口信息
	 */
	public ApiTest apiTest(String name);
}
