<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
	$(function() {
	 	parent.$.messager.progress('close');
		$('#user_mybshoots_Form').form({
			url : '${pageContext.request.contextPath}/api/apiUserController/user_mybshoots',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				$("#user_mybshoots_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">
		
		<div data-options="region:'center'">
			<form id="user_mybshoots_Form" action="">
				<table align="center" width="90%" class="tablex">
					<tr>
						<td align="right" style="width: 80px;"><label>url：</label></td>
						<td>${pageContext.request.contextPath}/api/apiUserController/user_mybshoots</td>
					</tr>
					
					<tr>
						<td align="right" style="width: 180px;"><label>tokenId(token值)：</label></td>
						<td><input name="tokenId" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>userId(用户ID，空值时为当前用户)：</label></td>
						<td><input name="userId" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>page(第几页)：</label></td>
						<td><input name="page" type="text" class="span2" value="1"/></td>
					</tr>
					
					<tr>
						<td align="right" style="width: 180px;"><label>rows(每页数)：</label></td>
						<td><input name="rows" type="text" class="span2" value="10"/></td>
					</tr>
					
					<tr>
						<td align="right" style="width: 180px;"><label>sort(排序字段)：</label></td>
						<td><input name="sort" type="text" class="span2" value="createDatetime"/></td>
					</tr>
					
					<tr>
						<td align="right" style="width: 180px;"><label>order(排序方式)：</label></td>
						<td><input name="order" type="text" class="span2" value="desc"/></td>
					</tr>
					
					<tr>
						<td colspan="2" align="center">
						<input type="button"
							value="提交" onclick="javascript:$('#user_mybshoots_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：</label>
				<div id="user_mybshoots_result">
				</div>
			<div>
				结果说明：1、json格式<br/>
					2、success:true 成功<br/>
					3、obj:数组格式<br/>
					  total：总数<br/>
					  bs_title		标题	           <br/>
					bs_topic		主题	           <br/>
					bs_icon		icon图标路径	     <br/>
					bs_stream		视频文件地址	   <br/>
					bs_collect		收藏数	       <br/>
					bs_praise		赞美数	         <br/>
					bs_type		类别	分类的       <br/>
					bs_comment		评论数	       <br/>
					user_id	用户ID	             <br/>
					bs_description		描述	     <br/>
					bs_remark		备注	           <br/>
					create_datetime		创建时间	 <br/>
					update_datetime		修改时间	 <br/>
					create_person			创建人	   <br/>
					update_person			修改人	   <br/>

					  
			</div>
		</div>
	</div>
</body>
</html>