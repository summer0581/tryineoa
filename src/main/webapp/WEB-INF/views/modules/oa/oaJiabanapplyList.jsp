<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>加班申请管理</title>
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
		<li class="active"><a href="${ctx}/oa/oaJiabanapply/">加班申请列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="oaJiabanapply" action="${ctx}/oa/oaJiabanapply/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>开始时间：</label>
				<input name="beginStarttime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaJiabanapply.beginStarttime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/> - 
				<input name="endStarttime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaJiabanapply.endStarttime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:00:00'});"/>
			</li>
			<li><label>结束时间：</label>
				<input name="beginEndtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaJiabanapply.beginEndtime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/> - 
				<input name="endEndtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaJiabanapply.endEndtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:00:00'});"/>
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
				<th>部门</th>
				<th>岗位</th>
				<th class="sort-column createDate">填报日期</th>
				<th class="sort-column starttime">开始时间</th>
				<th class="sort-column endtime">结束时间</th>
				<th>总小时</th>
				<th>加班时段</th>
				<th>加班地点</th>
				<th>加班事由</th>
				<shiro:hasPermission name="oa:oaJiabanapply:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaJiabanapply">
			<tr>
				<td><a href="${ctx}/oa/oaJiabanapply/form?id=${oaJiabanapply.id}&processDefinitionKey=${oaTravelapply.processDefinitionKey}">
					${oaJiabanapply.name}
				</a></td>
				<td>
					${oaJiabanapply.office.name}
				</td>
				<td>
					${oaJiabanapply.post}
				</td>
				<td>
					<fmt:formatDate value="${oaJiabanapply.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${oaJiabanapply.starttime}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>
					<fmt:formatDate value="${oaJiabanapply.endtime}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>
					${oaJiabanapply.hours}
				</td>
				<td>
					${fns:getDictLabel(oaJiabanapply.shiduan, 'jiaban_shiduan', '')}
				</td>
				<td>
					${oaJiabanapply.address}
				</td>
				<td>
					${oaJiabanapply.reason}
				</td>
				<shiro:hasPermission name="oa:oaJiabanapply:edit"><td>
    				<a href="${ctx}/oa/oaJiabanapply/form?id=${oaJiabanapply.id}&processDefinitionKey=${oaTravelapply.processDefinitionKey}">查看</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>