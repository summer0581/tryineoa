<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规章制度目录维护管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")){
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
		<li><a href="${ctx}/oa/oaZhiduMl/">规章制度目录维护列表</a></li>
		<li class="active"><a href="${ctx}/oa/oaZhiduMl/form?id=${oaZhiduMl.id}&parent.id=${oaZhiduMlparent.id}">规章制度目录维护<shiro:hasPermission name="oa:oaZhiduMl:edit">${not empty oaZhiduMl.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaZhiduMl:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaZhiduMl" action="${ctx}/oa/oaZhiduMl/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="parent.id" value="0"/>
		<sys:message content="${message}"/>		
		<!-- <div class="control-group">
			<label class="control-label">上级父级编号:</label>
			<div class="controls">
				<sys:treeselect id="parent" name="parent.id" value="${oaZhiduMl.parent.id}" labelName="parent.name" labelValue="${oaZhiduMl.parent.name}"
					title="父级编号" url="/oa/oaZhiduMl/treeData" extId="${oaZhiduMl.id}" cssClass="" allowClear="true"/>
			</div>
		</div>
		 -->
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:oaZhiduMl:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>