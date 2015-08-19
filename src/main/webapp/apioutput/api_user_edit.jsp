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
		$('#user_edit_Form').form({
			url : '${pageContext.request.contextPath}/api/apiUserController/edit',
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
				$("#user_edit_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">
		
		<div data-options="region:'center'">
			<form id="user_edit_Form" method="post"  enctype="multipart/form-data">
				<table align="center" width="90%" class="tablex">
					<tr>
						<td align="right" style="width: 80px;"><label>url：</label></td>
						<td>${pageContext.request.contextPath}/api/apiUserController/edit</td>
					</tr>
					
					<tr>
						<td align="right" style="width: 180px;"><label>tokenId(token值)：</label></td>
						<td><input name="tokenId" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>headImageFile(头像)：</label></td>
						<td><input name="headImageFile" type="file" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>nickname(昵称)：</label></td>
						<td><input name="nickname" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>email(邮箱)：</label></td>
						<td><input name="email" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>sex(性别)：</label></td>
						<td><input name="sex" type="text" class="span2" value="SX01"/>(SX01:男；SX02：女)</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>area(地区)：</label></td>
						<td><input name="area" type="text" class="span2" value=""/>（格式：中国_上海_浦东新区）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>birthday(生日)：</label></td>
						<td><input name="birthday" type="text" class="span2" value=""/>(格式：yyyy-MM-dd)</td>
					</tr> 
					<tr>
						<td align="right" style="width: 180px;"><label>bardian(个性签名)：</label></td>
						<td><input name="bardian" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td colspan="2" align="center">
						<input type="button"
							value="提交" onclick="javascript:$('#user_edit_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：</label>
				<div id="user_edit_result">
				</div>
			<div>
				结果说明：1、json格式<br/>
					2、success:true 成功<br/>
			</div>
		</div>
	</div>
</body>
</html>