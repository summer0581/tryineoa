<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>发件类型管理</title>
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
		<li class="active"><a href="${ctx}/oa/oaFeedbackBox/">类型列表</a></li>
		<shiro:hasPermission name="oa:oaFeedbackBox:edit"><li><a href="${ctx}/oa/oaFeedbackBox/form">类型添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oaFeedbackBox" action="${ctx}/oa/oaFeedbackBox/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>类型名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>类型名称</th>
				<th>管理人员</th>
				<shiro:hasPermission name="oa:oaFeedbackBox:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaFeedbackBox">
			<tr>
				<td><a href="${ctx}/oa/oaFeedbackBox/form?id=${oaFeedbackBox.id}">
					${oaFeedbackBox.name}
				</a></td>
				<td>
					${oaFeedbackBox.usernames}
				</td>
				<shiro:hasPermission name="oa:oaFeedbackBox:edit"><td>
    				<a href="${ctx}/oa/oaFeedbackBox/form?id=${oaFeedbackBox.id}">修改</a>
					<a href="${ctx}/oa/oaFeedbackBox/delete?id=${oaFeedbackBox.id}" onclick="return confirmx('确认要删除该类型吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>