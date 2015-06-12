<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业信箱管理</title>
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
		<li><a href="${ctx}/oa/oaFeedback/">已发件</a></li>
		<li class="active"><a href="${ctx}/oa/oaFeedback/form?id=${oaFeedback.id}">写信<shiro:hasPermission name="oa:oaFeedback:edit">${not empty oaFeedback.id?'修改':''}</shiro:hasPermission><shiro:lacksPermission name="oa:oaFeedback:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaFeedback" action="${ctx}/oa/oaFeedback/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="box.id" class="input-xlarge required">
                     <form:options items="${boxlist}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详细内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接收人名称：</label>
			<div class="controls">
				<sys:treeselect id="receivedUser" name="receivedUserids" value="${oaMessage.receivedUserids}" labelName="receivedUsernames" labelValue="${oaMessage.receivedUsernames}"
					title="用户" url="/sys/office/treeData?type=3&isAll=true" cssClass="required" checked="true" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否匿名：</label>
			<div class="controls">
				<form:checkbox path="isHidename" value="1"  htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:oaFeedback:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="发送"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>