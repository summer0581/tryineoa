<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>调休申请管理</title>
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
		<li class="active"><a href="${ctx}/oa/oaTiaoxiuapply/">调休申请列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="oaTiaoxiuapply" action="${ctx}/oa/oaTiaoxiuapply/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="clearfix"><label>休息时间：</label>
				<input name="restStarttime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaTiaoxiuapply.restStarttime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/> - 
				<input name="restEndtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaTiaoxiuapply.restEndtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:00:00'});"/>
			</li>
			<li><label>加班时间：</label>
				<input name="jiabanStarttime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaTiaoxiuapply.jiabanStarttime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/> - 
				<input name="jiabanEndtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaTiaoxiuapply.jiabanEndtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>岗位</th>
				<th class="sort-column createDate">填写日期</th>
				<th>是否转正</th>
				<th class="sort-column restStarttime">休息开始时间</th>
				<th class="sort-column restEndtime">休息结束时间</th>
				<th>休息总小时</th>
				<th class="sort-column jiabanStarttime">加班开始时间</th>
				<th class="sort-column jiabanEndtime">加班结束时间</th>
				<th>加班总小时</th>
				<th>岗位代理</th>
				<th>调休原因</th>
				<shiro:hasPermission name="oa:oaTiaoxiuapply:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaTiaoxiuapply">
			<tr>
				<td><a href="${ctx}/oa/oaTiaoxiuapply/form?id=${oaTiaoxiuapply.id}&processDefinitionKey=${oaTravelapply.processDefinitionKey}">
					${oaTiaoxiuapply.name}
				</a></td>
				<td>
					${oaTiaoxiuapply.post }
				</td>
				<td>
					<fmt:formatDate value="${oaTiaoxiuapply.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(oaTiaoxiuapply.isPositive, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${oaTiaoxiuapply.restStarttime}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>
					<fmt:formatDate value="${oaTiaoxiuapply.restEndtime}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>
					${oaTiaoxiuapply.restHours}
				</td>
				<td>
					<fmt:formatDate value="${oaTiaoxiuapply.jiabanStarttime}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>
					<fmt:formatDate value="${oaTiaoxiuapply.jiabanEndtime}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>
					${oaTiaoxiuapply.jiabanHours}
				</td>
				<td>
					${oaTiaoxiuapply.postAgent }
				</td>
				<td>
					${oaTiaoxiuapply.reason }
				</td>
				<shiro:hasPermission name="oa:oaTiaoxiuapply:edit"><td>
    				<a href="${ctx}/oa/oaTiaoxiuapply/form?id=${oaTiaoxiuapply.id}&processDefinitionKey=${oaTravelapply.processDefinitionKey}">查看</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>