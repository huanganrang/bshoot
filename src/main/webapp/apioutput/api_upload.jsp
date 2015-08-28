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
		$('#upload_Form').form({
			url : '${pageContext.request.contextPath}/api/bshootController/upload',
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
				$("#upload_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">
		
		<div data-options="region:'center'">
			<form id="upload_Form" method="post"  enctype="multipart/form-data">
				<table align="center" width="90%" class="tablex">
					<tr>
						<td align="right" style="width: 80px;"><label>url：</label></td>
						<td>${pageContext.request.contextPath}/api/bshootController/upload</td>
					</tr>
					
					<tr>
						<td align="right" style="width: 180px;"><label>tokenId(token值，登录获取)：</label></td>
						<td><input name="tokenId" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>bsDescription(描述)：</label></td>
						<td><textarea rows="2" cols="" name="bsDescription" >#话题# @用户 我的地盘我做主</textarea>（话题用##，用户以@开头空格结尾,话题列表接口：首页-广场话题，@好友接口：用户-我的关注）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>movies(视频文件)：</label></td>
						<td><input type="file" name="movies"></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>icons(图标文件)：</label></td>
						<td><input type="file" name="icons"></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>lgX(坐标经度)：</label></td>
						<td><input name="lgX" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>lgY(坐标纬度)：</label></td>
						<td><input name="lgY" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>lgName(地点名称)：</label></td>
						<td><input name="lgName" type="text" class="span2" value=""/>(位置详细地址)</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>bsArea(*地点)：</label></td>
						<td><input name="bsArea" type="text" class="span2" value=""/>(非客户端显示数据，用于搜索同城、国内、国外数据。格式：国省市，如：中国上海市浦东新区,具体名称按定位api为准)</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
						<input type="button"
							value="提交" onclick="javascript:$('#upload_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：</label>
				<div id="upload_result">
				</div>
			<div>
				结果说明：1、json格式<br/>
					2、success:true 成功<br/>
			</div>
		</div>
	</div>
</body>
</html>