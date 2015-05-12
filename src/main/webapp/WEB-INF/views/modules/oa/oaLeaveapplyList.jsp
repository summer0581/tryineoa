<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>请假信息管理</title>
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
		<li class="active"><a href="${ctx}/oa/oaLeaveapply/">请假信息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="oaLeaveapply" action="${ctx}/oa/oaLeaveapply/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>开始时间：</label>
				<input name="beginStarttime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaLeaveapply.beginStarttime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/> - 
				<input name="endStarttime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaLeaveapply.endStarttime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:00'});"/>
			</li>
			<li><label>结束时间：</label>
				<input name="beginEndtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaLeaveapply.beginEndtime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/> - 
				<input name="endEndtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaLeaveapply.endEndtime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:00'});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th class="sort-column createDate">填表日期</th>
				<th>部门</th>
				<th>岗位</th>
				<th>联系电话</th>
				<th>转正否</th>
				<th class="sort-column starttime">请假起始时间</th>
				<th class="sort-column endtime">请假结束时间</th>
				<th>岗位代理</th>
				<th>请假原因</th>
				<shiro:hasPermission name="oa:oaLeaveapply:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaLeaveapply">
			<tr>
				<td><a href="${ctx}/oa/oaLeaveapply/form?id=${oaLeaveapply.id}&processDefinitionKey=${oaTravelapply.processDefinitionKey}">
					${oaLeaveapply.name}
				</a></td>
				<td>
					<fmt:formatDate value="${oaLeaveapply.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${oaLeaveapply.office.name}
				</td>
				<td>
					${oaLeaveapply.post}
				</td>
				<td>
					${oaLeaveapply.telephone}
				</td>
				<td>
					${fns:getDictLabel(oaLeaveapply.isPositive, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${oaLeaveapply.starttime}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>
					<fmt:formatDate value="${oaLeaveapply.endtime}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>
					${oaLeaveapply.postAgent}
				</td>
				<td>
					${oaLeaveapply.reason}
				</td>
				<shiro:hasPermission name="oa:oaLeaveapply:edit"><td>
    				<a href="${ctx}/oa/oaLeaveapply/form?id=${oaLeaveapply.id}&processDefinitionKey=${oaTravelapply.processDefinitionKey}">查看</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>