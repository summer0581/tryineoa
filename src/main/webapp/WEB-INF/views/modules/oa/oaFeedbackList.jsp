<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业信箱管理</title>
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
		<li class="active"><a href="${ctx}/oa/oaFeedback/">已发件</a></li>
		<shiro:hasPermission name="oa:oaFeedback:edit"><li><a href="${ctx}/oa/oaFeedback/form">写信</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oaFeedback" action="${ctx}/oa/oaFeedback/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>填写时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaFeedback.createDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>填写时间</th>
				<th>类型</th>
				<th>是否回复</th>
				<shiro:hasPermission name="oa:oaFeedback:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaFeedback">
			<tr>
				<td><a href="${ctx}/oa/oaFeedback/viewform?id=${oaFeedback.id}">
					${oaFeedback.name}
				</a></td>
				<td>
					<fmt:formatDate value="${oaFeedback.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${oaFeedback.box.name}
				</td>
				<td>
					<c:choose>
						<c:when test="${oaFeedback.isReply > 0}">
						已回复
						</c:when>
						<c:otherwise>
						未回复
						</c:otherwise>
					</c:choose>
					
				</td>
				<shiro:hasPermission name="oa:oaFeedback:edit"><td>
    				<a href="${ctx}/oa/oaFeedback/form?id=${oaFeedback.id}">修改</a>
					<a href="${ctx}/oa/oaFeedback/delete?id=${oaFeedback.id}" onclick="return confirmx('确认要删除该条信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>