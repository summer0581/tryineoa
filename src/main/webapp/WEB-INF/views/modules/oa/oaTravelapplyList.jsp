<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>出差申请管理</title>
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
		<li class="active"><a href="${ctx}/oa/oaTravelapply/">出差申请列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="oaTravelapply" action="${ctx}/oa/oaTravelapply/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><span><label>姓名：</label></span>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="clearfix"><label>外出时间：</label>
				<input name="startOutTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaTravelapply.startOutTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/> - 
				<input name="endOutTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaTravelapply.endOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:00:00'});"/>
			</li>
			<li ><span><label>预计返回时间：</label></span>
				<input name="startPlantobacktime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaTravelapply.startPlantobacktime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/> - 
				<input name="endPlantobacktime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaTravelapply.endPlantobacktime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th class="sort-column createDate">填写日期</th>
				<th>岗位</th>
				<th>联系电话</th>
				<th class="sort-column outTime">外出时间</th>
				<th class="sort-column plantobacktime">预计返回时间</th>
				<th>随行人员</th>
				<th>外出地点</th>
				<th>客户姓名</th>
				<th>客户电话</th>
				<th>外出事由</th>
				<shiro:hasPermission name="oa:oaTravelapply:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaTravelapply">
			<tr>
				<td><a href="${ctx}/oa/oaTravelapply/form?id=${oaTravelapply.id}&processDefinitionKey=${oaTravelapply.processDefinitionKey}">
					${oaTravelapply.name}
				</a></td>
				<td>
					${oaTravelapply.office.name }
				</td>
				<td>
					<fmt:formatDate value="${oaTravelapply.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${oaTravelapply.post }
				</td>
				<td>
					${oaTravelapply.telephone }
				</td>
				<td>
					<fmt:formatDate value="${oaTravelapply.outTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${oaTravelapply.plantobacktime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${oaTravelapply.joinPeople }
				</td>
				<td>
					${oaTravelapply.outPlace }
				</td>
				<td>
					${oaTravelapply.customerInfo }
				</td>
				<td>
					${oaTravelapply.customerTelephone }
				</td>
				<td>
					${oaTravelapply.outReason }
				</td>
				<shiro:hasPermission name="oa:oaTravelapply:edit"><td>
    				<a href="${ctx}/oa/oaTravelapply/form?id=${oaTravelapply.id}&processDefinitionKey=${oaTravelapply.processDefinitionKey}">查看</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>