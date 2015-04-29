<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内部消息管理</title>
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
		function toUrl(){
			location = "${ctx}/"+$("input[name='oaMessage.url']").val();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">内部消息查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaMessageRecord" action="" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="oaMessage.url"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">消息标题：</label>
			<div class="controls">
				${oaMessageRecord.oaMessage.content }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发送人：</label>
			<div class="controls">
				${oaMessageRecord.oaMessage.createBy.name }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发送时间：</label>
			<div class="controls">
				<fmt:formatDate value="${oaMessageRecord.oaMessage.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接收人：</label>
			<div class="controls">
				${oaMessageRecord.user.name }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否阅读：</label>
			<div class="controls">
				${fns:getDictLabel(oaMessageRecord.readFlag, 'oa_notify_read', '')}
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">阅读时间：</label>
			<div class="controls">
				<fmt:formatDate value="${oaMessageRecord.readDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnToUrl" class="btn btn-primary" type="button" value="链接" onclick="toUrl()"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>