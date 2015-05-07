<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>意见反馈管理</title>
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
		<li ><a href="${ctx}/oa/oaFeedbackReceived/">未阅读</a></li>
		<li class="active"><a href="${ctx}/oa/oaFeedbackReceived/readedList">已阅读</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="oaFeedbackReceived" action="${ctx}/oa/oaFeedbackReceived/readedList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>意见标题：</label>
				<form:input path="feedback.name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="feedback.createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaFeedbackReceived.feedback.createDate}" pattern="yyyy-MM-dd"/>"
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
				<th>意见标题</th>
				<th>填写时间</th>
				<th>意见箱</th>
				<th>填写人</th>
				<shiro:hasPermission name="oa:oaFeedbackReceived:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaFeedbackReceived">
			<tr>
				<td><a href="${ctx}/oa/oaFeedbackReceived/form?id=${oaFeedbackReceived.id}">
					${oaFeedbackReceived.feedback.name}
				</a></td>
				<td>
					<fmt:formatDate value="${oaFeedbackReceived.feedback.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${oaFeedbackReceived.feedback.box.name}
				</td>
				<td>
					${oaFeedbackReceived.feedback.isHidename eq '1'?'匿名':oaFeedbackReceived.feedback.createBy.name}
				</td>
				<shiro:hasPermission name="oa:oaFeedbackReceived:edit"><td>
					<a href="${ctx}/oa/oaFeedbackReceived/delete?id=${oaFeedbackReceived.id}" onclick="return confirmx('确认要删除该意见反馈吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>