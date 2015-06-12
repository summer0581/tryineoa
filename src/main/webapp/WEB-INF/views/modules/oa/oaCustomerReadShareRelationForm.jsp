<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>只读共享客户与用户关系表管理</title>
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
		<li><a href="${ctx}/oa/oaCustomerReadShareRelation/">只读共享客户与用户关系表列表</a></li>
		<li class="active"><a href="${ctx}/oa/oaCustomerReadShareRelation/form?id=${oaCustomerReadShareRelation.id}">只读共享客户与用户关系表<shiro:hasPermission name="oa:oaCustomerReadShareRelation:edit">${not empty oaCustomerReadShareRelation.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaCustomerReadShareRelation:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaCustomerReadShareRelation" action="${ctx}/oa/oaCustomerReadShareRelation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">客户只读共享ID：</label>
			<div class="controls">
				<form:input path="oaCustomerRead.id" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">只读人员ID：</label>
			<div class="controls">
				<form:input path="readUser.id" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:oaCustomerReadShareRelation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>