<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body >

		<div id="index_find_tabs" class="easyui-tabs" data-options="fit:true">
			<div title="新的好友" data-options="href:'api_message_newfriend.jsp'"
				style="padding: 1px"></div>
			<div title="@我的" data-options="href:'api_message_atMine.jsp'"
				style="padding: 1px"></div>
			<div title="评论" data-options="href:'api_message_comment.jsp'"
				style="padding: 1px"></div>	
			<div title="赞" data-options="href:'api_message_like.jsp'"
				style="padding: 1px"></div>
			<div title="上线获取消息数量" data-options="href:'api_message_getCount.jsp'"
				style="padding: 1px"></div>	
			<div title="推送消息测试" data-options="href:'api_message_test.jsp'"
				style="padding: 1px"></div>	
			<!-- 
			<div title="最近联系人（开发ing）" data-options="href:'api_doing.jsp'"
				style="padding: 1px"></div>	
			<div title="写私信好友选择（开发ing）" data-options="href:'api_doing.jsp'"
				style="padding: 1px"></div>		
			<div title="消息数量统计（开发ing）" data-options="href:'api_doing.jsp'"
				style="padding: 1px"></div>	
			 -->								
		</div>

</body>
</html>