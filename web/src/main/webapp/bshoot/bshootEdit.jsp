<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.Tbshoot" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/bshootController/edit',
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
				<input type="hidden" name="id" value = "${bshoot.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=Tbshoot.ALIAS_BS_TITLE%></th>	
					<td>
					<input class="span2" name="bsTitle" type="text" class="easyui-validatebox span2" data-options="required:true" value="${bshoot.bsTitle}"/>
					</td>							
					<th><%=Tbshoot.ALIAS_BS_TOPIC%></th>	
					<td>
					<input class="span2" name="bsTopic" type="text" class="span2"  value="${bshoot.bsTopic}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=Tbshoot.ALIAS_BS_ICON%></th>	
					<td>
					<input class="span2" name="bsIcon" type="text" class="span2"  value="${bshoot.bsIcon}"/>
					</td>							
					<th><%=Tbshoot.ALIAS_BS_STREAM%></th>	
					<td>
					<input class="span2" name="bsStream" type="text" class="span2"  value="${bshoot.bsStream}"/>
					</td>							
			</tr>	
			
			<tr>	
					<th><%=Tbshoot.ALIAS_BS_TYPE%></th>	
					<td>
					<select name="bsType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
						<c:forEach items="${bshootSquare}" var="bshootSquare">
							<option value="${bshootSquare.id}" <c:if test="${bshootSquare.id == bshoot.bsType}">selected="selected"</c:if>>${bshootSquare.bssName}</option>
						</c:forEach>
					</select>	
					</td>	
					<th><%=Tbshoot.ALIAS_USER_ID%></th>	
					<td>
					<input class="span2" name="userId" type="text" class="span2"  value="${bshoot.userId}"/>
					</td>							
			</tr>	
			<tr>	
											
					<th><%=Tbshoot.ALIAS_BS_DESCRIPTION%></th>	
					<td>
					<textarea class="span2" name="bsDescription" rows="2" cols="">
					${bshoot.bsDescription}
					</textarea>
					</td>							
			</tr>	
			<tr>	
					<th><%=Tbshoot.ALIAS_BS_REMARK%></th>	
					<td>
					<input class="span2" name="bsRemark" type="text" class="span2"  value="${bshoot.bsRemark}"/>
					</td>							
			</tr>	
				
			</table>				
		</form>
	</div>
</div>