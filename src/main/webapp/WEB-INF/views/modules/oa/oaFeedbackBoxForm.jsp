<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>意见箱管理</title>
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
		<li><a href="${ctx}/oa/oaFeedbackBox/">意见箱列表</a></li>
		<li class="active"><a href="${ctx}/oa/oaFeedbackBox/form?id=${oaFeedbackBox.id}">意见箱<shiro:hasPermission name="oa:oaFeedbackBox:edit">${not empty oaFeedbackBox.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaFeedbackBox:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaFeedbackBox" action="${ctx}/oa/oaFeedbackBox/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">意见箱名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">管理人员：</label>
			<div class="controls">
				<sys:treeselect id="userids" name="userids" value="${oaFeedbackBox.userids}" labelName="usernames" labelValue="${oaFeedbackBox.usernames}"
						title="用户" url="/sys/office/treeData?type=3" cssClass="input-xlarge required" notAllowSelectParent="true" checked="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:oaFeedbackBox:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>