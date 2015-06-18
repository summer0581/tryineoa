<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>可写共享客户与用户关系表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/oaCustomerWriteShareRelation/">可写共享客户与用户关系表列表</a></li>
		<li class="active"><a href="${ctx}/oa/oaCustomerWriteShareRelation/form?id=${oaCustomerWriteShareRelation.id}">可写共享客户与用户关系表<shiro:hasPermission name="oa:oaCustomerWriteShareRelation:edit">${not empty oaCustomerWriteShareRelation.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaCustomerWriteShareRelation:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaCustomerWriteShareRelation" action="${ctx}/oa/oaCustomerWriteShareRelation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">可写客户ID：</label>
			<div class="controls">
				<form:input path="oaCustomerWrite.id" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">可写人员ID：</label>
			<div class="controls">
				<form:input path="writeUser.id" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:oaCustomerWriteShareRelation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>