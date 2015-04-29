<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规章制度内容维护管理</title>
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
		<li class="active"><a href="#">规章制度内容维护列表</a></li>
		<shiro:hasPermission name="oa:oaZhiduContent:edit"><li><a href="${ctx}/oa/oaZhiduContent/form?oaZhiduMl.id=${oaZhiduContent.oaZhiduMl.id}">规章制度内容维护添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oaZhiduContent" action="${ctx}/oa/oaZhiduContent/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>更新时间</th>
				<th>排序号</th>
				<shiro:hasPermission name="oa:oaZhiduContent:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaZhiduContent">
			<tr>
				<td><a href="${ctx}/oa/oaZhiduContent/form?id=${oaZhiduContent.id}">
					${oaZhiduContent.name}
				</a></td>
				<td>
					<fmt:formatDate value="${oaZhiduContent.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${oaZhiduContent.sort}
				</td>
				<shiro:hasPermission name="oa:oaZhiduContent:edit"><td>
    				<a href="${ctx}/oa/oaZhiduContent/form?id=${oaZhiduContent.id}">修改</a>
					<a href="${ctx}/oa/oaZhiduContent/delete?id=${oaZhiduContent.id}" onclick="return confirmx('确认要删除该规章制度内容维护吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>