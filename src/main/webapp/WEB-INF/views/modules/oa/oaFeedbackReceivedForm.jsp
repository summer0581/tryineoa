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
		<li><a href="#">查看信件<shiro:hasPermission name="oa:oaFeedbackReceived:edit">${not empty oaFeedback.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaFeedbackReceived:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaFeedbackReceived" action="${ctx}/oa/oaFeedbackReceived/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="feedback.id"/>
		<form:hidden path="feedback.createBy.id"/>
		<sys:message content="${message}"/>		
		<div class="control-group text-center">
			基本信件信息
		</div>
		<div class="control-group">
			<label class="control-label">信件标题：</label>
			<div class="controls">
				${oaFeedbackReceived.feedback.name }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">信件箱：</label>
			<div class="controls">
				${oaFeedbackReceived.feedback.box.name }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详细内容：</label>
			<div class="controls">
				${oaFeedbackReceived.feedback.content }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">填写人：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${'1' eq oaFeedbackReceived.feedback.isHidename }">
						匿名
					</c:when>
					<c:otherwise>
						${oaFeedbackReceived.feedback.createBy.name }
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">填写时间：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${'1' eq oaFeedbackReceived.feedback.isHidename }">
						匿名
					</c:when>
					<c:otherwise>
						<fmt:formatDate value="${oaFeedbackReceived.feedback.createDate}" pattern="yyyy-MM-dd hh:mm:ss"/>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group text-center">
			回复信息
		</div>
		<div class="control-group">
			<label class="control-label">回复人：</label>
			<div class="controls">
				${fns:getUser().name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回复时间：</label>
			<div class="controls">
				${fns:getDate('yyyy-MM-dd hh:mm:ss') }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回复内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通知填写人：</label>
			<div class="controls">
				<form:checkbox path="isSendMessage"  label="内部消息"  htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:oaFeedbackReceived:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>