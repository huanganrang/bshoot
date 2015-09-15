<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TbshootSkill" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	var editor;
	$(function() {
		window.setTimeout(function() {
			editor = KindEditor.create('#description', {
				width : '580px',
				height : '300px',
				items : [ 'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript', 'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/', 'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak', 'anchor', 'link', 'unlink' ],
				uploadJson : '${pageContext.request.contextPath}/fileController/upload',
				fileManagerJson : '${pageContext.request.contextPath}/fileController/fileManage',
				allowFileManager : true
			});
		}, 1);
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/bshootSkillController/edit',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				editor.sync();
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;">
		<form id="form" method="post">
			<input type="hidden" name="id" value = "${bshootSkill.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TbshootSkill.ALIAS_TITLE%></th>	
					<td colspan="3">
						<input class="span2" name="title" type="text" style="width: 500px;" value="${bshootSkill.title}"/>
					</td>							
				</tr>
				<tr>	
					<th><%=TbshootSkill.ALIAS_TYPE%></th>	
					<td colspan="3">
						<select name="type" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
						<option value="">请选择</option>
						<c:forEach items="${btlist}" var="bt">
							<option value="${bt.id}" <c:if test="${bt.id == bshootSkill.type}">selected="selected"</c:if>>${bt.name}</option>
						</c:forEach>
					</select>	
					</td>							
				</tr>
				<tr>	
					<th width="8%"><%=TbshootSkill.ALIAS_HOT_STATUS%></th>	
					<td width="42%">
						<input type="radio" name="hotStatus" value="1" <c:if test="${bshootSkill.hotStatus==true}">checked</c:if>/> 是
						<input type="radio" name="hotStatus" value="0" <c:if test="${bshootSkill.hotStatus==false}">checked</c:if>/> 否
					</td>	
					<th width="8%"><%=TbshootSkill.ALIAS_HOT%></th>	
					<td width="42%">
						<input class="span2" name="hot" type="text" value="${bshootSkill.hot}"/>
					</td>	
				</tr>		
				<tr>	
					<th><%=TbshootSkill.ALIAS_DESCRIPTION%></th>		
					<td colspan="3">
						<textarea  name="description" id="description" style="height:180px;visibility:hidden;">${bshootSkill.description}</textarea>
					</td>						
				</tr>
			</table>				
		</form>
	</div>
</div>