package jb.service.impl;

import jb.absx.F;
import jb.dao.ApiTestDaoI;
import jb.model.TapiTest;
import jb.pageModel.ApiTest;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.ApiTestServiceI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApiTestServiceImpl extends BaseServiceImpl<ApiTest> implements ApiTestServiceI {

	@Autowired
	private ApiTestDaoI apiTestDao;

	@Override
	public DataGrid dataGrid(ApiTest apiTest, PageHelper ph) {
		List<ApiTest> ol = new ArrayList<ApiTest>();
		String hql = " from TapiTest t ";
		DataGrid dg = dataGridQuery(hql, ph, apiTest, apiTestDao);
		@SuppressWarnings("unchecked")
		List<TapiTest> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TapiTest t : l) {
				ApiTest o = new ApiTest();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ApiTest apiTest, Map<String, Object> params) {
		String whereHql = "";
		if (apiTest != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(apiTest.getName())) {
				whereHql += " and t.name = :name";
				params.put("name", apiTest.getName());
			}
			if (!F.empty(apiTest.getUrl())) {
				whereHql += " and t.url = :url";
				params.put("url", apiTest.getUrl());
			}
			if (!F.empty(apiTest.getParam())) {
				whereHql += " and t.param = :param";
				params.put("param", apiTest.getParam());
			}
			if (!F.empty(apiTest.getResult())) {
				whereHql += " and t.result = :result";
				params.put("result", apiTest.getResult());
			}
			if (apiTest.getIsSuccess() != null) {
				whereHql += " and t.isSuccess = :isSuccess";
				params.put("isSuccess", apiTest.getIsSuccess());
			}
			if (!F.empty(apiTest.getCompleteUrl())) {
				whereHql += " and t.completeUrl = :completeUrl";
				params.put("completeUrl", apiTest.getCompleteUrl());
			}
			if (!F.empty(apiTest.getInfo())) {
				whereHql += " and t.info = :info";
				params.put("info", apiTest.getInfo());
			}
			if (!F.empty(apiTest.getParamDes())) {
				whereHql += " and t.paramDes = :paramDes";
				params.put("paramDes", apiTest.getParamDes());
			}
			if (!F.empty(apiTest.getResultDes())) {
				whereHql += " and t.resultDes = :resultDes";
				params.put("resultDes", apiTest.getResultDes());
			}
			if (!F.empty(apiTest.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", apiTest.getRemark());
			}
		}
		return whereHql;
	}

	@Override
	public void add(ApiTest apiTest) {
		TapiTest t = new TapiTest();
		BeanUtils.copyProperties(apiTest, t);
		t.setId(UUID.randomUUID().toString());
		t.setIsSuccess(0);
		apiTestDao.save(t);
	}

	@Override
	public ApiTest get(String id) {
		TapiTest t = apiTestDao.get(TapiTest.class, id);
		ApiTest o = new ApiTest();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ApiTest apiTest) {
		TapiTest t = apiTestDao.get(TapiTest.class, apiTest.getId());
		if (t != null) {
			BeanUtils.copyProperties(apiTest, t, new String[] { "id" , "createdatetime" });
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		apiTestDao.delete(apiTestDao.get(TapiTest.class, id));
	}

	@Override
	public List<ApiTest> getApiList(String name) {
		List<ApiTest> ol = new ArrayList<ApiTest>();
		String hql = "";
		if(F.empty(name)){
			hql = " from TapiTest t";
		}else{
			hql = " from TapiTest t where t.name like %"+name+"%";
		}
		List<TapiTest> ts = apiTestDao.find(hql);
		for (TapiTest t : ts){
			ApiTest o = new ApiTest();
			BeanUtils.copyProperties(t, o);
			ol.add(o);
		}
		return ol;
	}

	@Override
	public ApiTest apiTest(String name){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		TapiTest t = apiTestDao.get("from TapiTest t where t.name = :name", params);
		ApiTest o = new ApiTest();
		BeanUtils.copyProperties(t, o);
		return o;
	}

}
