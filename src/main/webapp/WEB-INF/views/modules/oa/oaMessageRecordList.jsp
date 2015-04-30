<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内部消息管理</title>
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
		<c:choose>
			<c:when test="${oaMessageRecord.type eq 'noread'}">
				<c:set var="typeStr" value="未读"></c:set>
			</c:when>
			<c:when test="${oaMessageRecord.type eq 'readed'}">
				<c:set var="typeStr" value="已读"></c:set>
			</c:when>
			<c:when test="${oaMessageRecord.type eq 'sended'}">
				<c:set var="typeStr" value="已发"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="typeStr" value="内部"></c:set>
			</c:otherwise>
		</c:choose>
		<li class="active"><a href="${ctx}/oa/oaMessageRecord/">${typeStr }消息列表</a></li>
		<shiro:hasPermission name="oa:oaMessageRecord:edit"><li><a href="${ctx}/oa/oaMessage/noListForm">内部消息发送</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="oaMessageRecord" action="${ctx}/oa/oaMessageRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="type" htmlEscape="false" class="input-medium"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="oaMessage.content" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>发送时间：</label>
				<input name="oaMessage.createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaMessageRecord.oaMessage.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="60%">标题</th>
				<th>
					<c:if test="${oaMessageRecord.type eq 'sended'}">
					接收人
					</c:if>
					<c:if test="${oaMessageRecord.type ne 'sended'}">
					发送人
					</c:if>
				</th>
				<th>发送时间</th>
				<th>是否阅读</th>
				<th>阅读时间</th>
				<shiro:hasPermission name="oa:oaMessageRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="model">
			<tr>
				<td><a href="${ctx}/oa/oaMessageRecord/form?id=${model.id}&type=${oaMessageRecord.type}">
					${model.oaMessage.content}
				</a></td>
				
				<td>
					<c:if test="${oaMessageRecord.type eq 'sended'}">
						${model.user.name}
					</c:if>
					<c:if test="${oaMessageRecord.type ne 'sended'}">
						${model.oaMessage.createBy.name}
					</c:if>
				</td>
				<td>
					<fmt:formatDate value="${model.oaMessage.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(model.readFlag, 'oa_notify_read', '')}
				</td>
				<td>
					<fmt:formatDate value="${model.readDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="oa:oaMessageRecord:edit"><td>
					<a href="${ctx}/oa/oaMessageRecord/delete?id=${model.id}&type=${oaMessageRecord.type}" onclick="return confirmx('确认要删除该内部消息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>