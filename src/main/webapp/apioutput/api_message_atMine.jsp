<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
String path = request.getContextPath()+"/api/apiMessageController/message_atMine";
%>
</head>
<body>
<script type="text/javascript">
	$(function() {
	 	parent.$.messager.progress('close');
		$('#message_atMine_Form').form({
			url : '<%=path%>',
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
				$("#message_atMine_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">
		
		<div data-options="region:'center'">
			<form id="message_atMine_Form" action="">
				<table align="center" width="90%" class="tablex">
					<tr>
						<td align="right" style="width: 80px;"><label>url：</label></td>
						<td><%=path%></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>tokenId(token值)：</label></td>
						<td><input name="tokenId" type="text" class="span2" value=""/></td>
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
						<td colspan="2" align="center">
						<input type="button"
							value="提交" onclick="javascript:$('#message_atMine_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：</label>
				<div id="message_atMine_result">
				</div>
			<div>
				结果说明：1、json格式<br/>
					2、success:true 成功<br/>
					3、obj:数组格式<br/>
					  userId 用户ID	         <br/>
					  headImage 用户头像	         <br/>
						nickname	用户姓名 <br/>
						bshootId	视频ID    <br/>
						bsIcon	视频封面          <br/>
						description			内容描述         <br/>
						createdate	时间         <br/>
						type	类型：COMMENT表示在评论中@了你；BSHOOT表示在视频分享@了你

					  
			</div>
		</div>
	</div>
</body>
</html>