<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jb.model.TbshootSkill" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');		
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TbshootSkill.ALIAS_TITLE%></th>	
					<td colspan="3">
						${bshootSkill.title}
					</td>							
				</tr>
				<tr>	
					<th><%=TbshootSkill.ALIAS_TYPE%></th>	
					<td>
						${bshootSkill.typeZh}
					</td>							
					<th>创建时间</th>	
					<td>
						${bshootSkill.createTime}
					</td>							
				</tr>
				<tr>	
					<th width="8%"><%=TbshootSkill.ALIAS_HOT_STATUS%></th>	
					<td width="42%">
						${bshootSkill.hotStatus}
					</td>	
					<th width="8%"><%=TbshootSkill.ALIAS_HOT%></th>	
					<td width="42%">
						${bshootSkill.hot}
					</td>	
				</tr>		
				<tr>	
					<th><%=TbshootSkill.ALIAS_DESCRIPTION%></th>		
					<td colspan="3">
						${bshootSkill.description}
					</td>						
				</tr>	
		</table>
	</div>
</div>