<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户信息管理管理</title>
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
		<li class="active"><a href="${ctx}/oa/oaCustomerRmanager/shareList">共享客户</a></li>	
	</ul>
	<form:form id="searchForm" modelAttribute="oaCustomerRmanager" action="${ctx}/oa/oaCustomerRmanager/shareList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>客户姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>客户阶段：</label>
				<form:select path="jieduan" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('oa_customer_jieduan')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li ><label>创建时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaCustomerRmanager.createDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>客户姓名</th>
				<th>助记简称</th>
				<th>下次回访时间</th>
				<th>客户种类</th>
				<th>客户阶段</th>
				<th>电子邮箱</th>
				<th>电话</th>
				<th>客户共享</th>
				<th>创建时间</th>
				<th>备注信息</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="oaCustomerRmanager">
			<tr>
				<td><a href="${ctx}/oa/oaCustomerRmanager/form?id=${oaCustomerRmanager.id}">
					${oaCustomerRmanager.name}
				</a></td>
				<td>
					${oaCustomerRmanager.zhujijc}
				</td>
				<td>
					<fmt:formatDate value="${oaCustomerRmanager.hfTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(oaCustomerRmanager.zhonglei, 'oa_customer_zhonglei', '')}
				</td>
				<td>
					${fns:getDictLabel(oaCustomerRmanager.jieduan, 'oa_customer_jieduan', '')}
				</td>
				<td>
					${oaCustomerRmanager.email}
				</td>
				<td>
					${oaCustomerRmanager.telephone}
				</td>
				<td>
					${fns:getDictLabel(oaCustomerRmanager.gongxiang, 'oa_customer_share', '')}
				</td>
				<td>
					<fmt:formatDate value="${oaCustomerRmanager.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${oaCustomerRmanager.remarks}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>