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
		<li class="active"><a href="${ctx}/oa/oaCustomerRmanager/">全部客户</a></li>	
		<li ><a href="${ctx}/oa/oaCustomerRmanager/qiyelist">企业客户</a></li>	
		<li ><a href="${ctx}/oa/oaCustomerRmanager/gerenlist">个人客户</a></li>	
		<li class="btns">
			<input id="btnQiye" class="btn btn-primary" type="button" value="增加企业客户" onclick="location='${ctx}/oa/oaCustomerRmanager/qiyeform'"/>
			<input id="btnGeren" class="btn btn-primary" type="button" value="增加个人客户" onclick="location='${ctx}/oa/oaCustomerRmanager/form'"/>
		</li>	
	</ul>
	<form:form id="searchForm" modelAttribute="oaCustomerRmanager" action="${ctx}/oa/oaCustomerRmanager/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>客户姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="clearfix"><label>客户阶段：</label>
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
			<li><label>回访时间：</label>
				<input name="hfTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${oaCustomerRmanager.hfTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<input id="btnHighSubmit" class="btn btn-primary" type="button" value="高级查询" onclick="$('#highSearch').fadeToggle('fast');"/></li>
			
			<li class="clearfix"></li>
			<div class="hide" id="highSearch">
			<li><label>客户种类：</label>
				<form:select path="zhonglei" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('oa_customer_zhonglei')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>客户行业：</label>
				<form:select path="hangye" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('oa_customer_hangye')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>电子邮箱：</label>
				<form:input path="email" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>电话：</label>
				<form:input path="telephone" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>关系等级：</label>
				<form:select path="guanxidj" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('oa_customer_guanxidj')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<li><label>客户来源：</label>
				<form:select path="laiyuan" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('oa_customer_laiyuan')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>价值评估：</label>
				<form:select path="jiazhipg" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('oa_customer_xinyongdj')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>地址：</label>
				<form:input path="address" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>信用等级：</label>
				<form:select path="xinyongdj" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('oa_customer_xinyongdj')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>人员规模：</label>
				<form:select path="guimo" class="input-medium">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('oa_customer_guimo')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			</div>
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
				<shiro:hasPermission name="oa:oaCustomerRmanager:edit"><th>操作</th></shiro:hasPermission>
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
				<shiro:hasPermission name="oa:oaCustomerRmanager:edit"><td>
					<c:choose >
						<c:when test="${'qiye' eq oaCustomerRmanager.type }">
							<a href="${ctx}/oa/oaCustomerRmanager/qiyeform?id=${oaCustomerRmanager.id}">修改</a>
						</c:when>
						<c:otherwise>
							<a href="${ctx}/oa/oaCustomerRmanager/form?id=${oaCustomerRmanager.id}">修改</a>
						</c:otherwise>
					</c:choose>
					<a href="${ctx}/oa/oaCustomerRmanager/delete?id=${oaCustomerRmanager.id}" onclick="return confirmx('确认要删除该客户信息管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>