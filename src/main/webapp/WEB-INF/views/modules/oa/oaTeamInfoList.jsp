<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团队信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/oa/oaTeamInfo/">团队信息列表</a></li>
		<shiro:hasPermission name="oa:oaTeamInfo:edit"><li><a href="${ctx}/oa/oaTeamInfo/form">团队信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oaTeamInfo" action="${ctx}/oa/oaTeamInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>团队名称：</label>
				<form:input path="teamName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>负责人：</label>
				<sys:treeselect id="headUser" name="headUser.id" value="${oaTeamInfo.headUser.id}" labelName="headUser.name" labelValue="${oaTeamInfo.headUser.name}"
						title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true" cssClass="input-medium"/>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>团队名称</th>
				<th>负责人</th>
				<th>团队成员</th>
				<shiro:hasPermission name="oa:oaTeamInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaTeamInfo">
			<tr>
				<td><a href="${ctx}/oa/oaTeamInfo/form?id=${oaTeamInfo.id}">
					${oaTeamInfo.teamName}
				</a></td>
				<td>
					${oaTeamInfo.headUser.name}
				</td>
				<td>
					${fns:abbr(oaTeamInfo.oaTeamInfoRelationNamesList,40)}
				</td>
				<shiro:hasPermission name="oa:oaTeamInfo:edit"><td>
    				<a href="${ctx}/oa/oaTeamInfo/form?id=${oaTeamInfo.id}">修改</a>
					<a href="${ctx}/oa/oaTeamInfo/delete?id=${oaTeamInfo.id}" onclick="return confirmx('确认要删除该团队信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>