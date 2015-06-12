<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团队信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				debug:true,
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
		<li><a href="${ctx}/oa/oaTeamInfo/">团队信息列表</a></li>
		<li class="active"><a href="${ctx}/oa/oaTeamInfo/form?id=${oaTeamInfo.id}">团队信息<shiro:hasPermission name="oa:oaTeamInfo:edit">${not empty oaTeamInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaTeamInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaTeamInfo" action="${ctx}/oa/oaTeamInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">团队名称：</label>
			<div class="controls">
				<form:input path="teamName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">负责人：</label>
			<div class="controls">
				<sys:treeselect id="headUser" name="headUser.id" value="${oaTeamInfo.headUser.id}" labelName="headUser.name" labelValue="${oaTeamInfo.headUser.name}"
						title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true" cssClass="input-medium"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="64" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">团队成员：</label>
			<div class="controls">
             	<sys:treeselect id="oaTeamInfoRelation" name="oaTeamInfoRelationIds" value="${oaTeamInfo.oaTeamInfoRelationIds}" labelName="oaTeamInfoRelationNames" labelValue="${oaTeamInfo.oaTeamInfoRelationNames}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-xxlarge required" notAllowSelectParent="true" checked="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:oaTeamInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>