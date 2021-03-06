<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TbshootToSquare" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/bshootToSquareController/add',
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
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">	
		<form id="form" method="post">		
				<input type="hidden" name="id"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TbshootToSquare.ALIAS_BSHOOT_ID%></th>	
					<td>
					<input class="span2" name="bshootId" type="text" class="span2"/>
					</td>							
					<th><%=TbshootToSquare.ALIAS_SQUARE_ID%></th>	
					<td>
					<input class="span2" name="squareId" type="text" class="span2"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TbshootToSquare.ALIAS_AUDITOR_ID%></th>	
					<td>
					<input class="span2" name="auditorId" type="text" class="span2"/>
					</td>							
					<th><%=TbshootToSquare.ALIAS_AUDITOR_TIME%></th>	
					<td>
					<input class="span2" name="auditorTime" type="text" onclick="WdatePicker({dateFmt:'<%=TbshootToSquare.FORMAT_AUDITOR_TIME%>'})"  maxlength="0" class="" />
					</td>							
				</tr>	
				<tr>	
					<th><%=TbshootToSquare.ALIAS_REASON%></th>	
					<td>
					<input class="span2" name="reason" type="text" class="span2"/>
					</td>							
					<th><%=TbshootToSquare.ALIAS_STATUS%></th>	
					<td>
					<input class="span2" name="status" type="text" class="span2"/>
					</td>							
				</tr>	
			</table>		
		</form>
	</div>
</div>