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
		<li class="active"><a href="${ctx}/oa/oaFeedback/viewform?id=${oaFeedback.id}">信件查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaFeedback" action="${ctx}/oa/oaFeedback/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group text-center">
			基本信息
		</div>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				${oaFeedback.name }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				${box.name }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详细内容：</label>
			<div class="controls">
				${oaFeedback.content }
			</div>
		</div>
	<div class="control-group">
			<label class="control-label">填写人：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${'1' eq oaFeedback.isHidename }">
						匿名
					</c:when>
					<c:otherwise>
						${oaFeedback.createBy.name }
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">填写时间：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${'1' eq oaFeedback.isHidename }">
						匿名
					</c:when>
					<c:otherwise>
						<fmt:formatDate value="${oaFeedback.createDate}" pattern="yyyy-MM-dd hh:mm:ss"/>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group text-center">
			回复信息
		</div>
		<div class="control-group">
			<label class="control-label">已阅读人员：</label>
			<div class="controls">
				${isReadUsernames }
			</div>
		</div>
		<c:forEach items="${ oaFeedback.oaFeedbackReceivedList}" var="ofr">
			<c:if test="${ofr.backTime ne null}">
				<div class="control-group">
					<label class="control-label">回复内容：</label>
					<div class="controls">
						${ofr.content }
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">回复人：</label>
					<div class="controls">
						${ofr.user.name }
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">回复时间：</label>
					<div class="controls">
						<fmt:formatDate value="${ofr.backTime }" pattern="yyyy-MM-dd hh:mm:ss"/>
					</div>	
				</div>
			</c:if>
		</c:forEach>
		<div class="form-actions">
			<%-- <shiro:hasPermission name="oa:oaFeedback:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission> --%>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>