\<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统配置管理</title>
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
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		
		function clearAllCache(){
			function initData(){
				$("#inputForm").attr("action","${ctx}/sys/config/clearAllCache");
				$("#inputForm").submit();
			}
			confirmx("确定清空所有用户缓存吗?一旦缓存被清除，所有用户将会强制退出系统",initData,null);
		}
		function initUserTiaoxiuTime(){
			function initData(){
				$("#inputForm").attr("action","${ctx}/sys/config/initUserTiaoxiuTime");
				$("#inputForm").submit();
			}
			confirmx("确定初始化用户调休时长吗?一旦数据被初始化，历史数据将无法找回",initData,null);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/config/">系统配置</a></li>
	</ul><br/>
	<form:form id="inputForm"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">去除全部缓存:</label>
			<div class="controls">
                <input id="btnClearCache" class="btn btn-primary" onclick="clearAllCache()" type="button" value="清除缓存"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">初始化所有用户调休时长:</label>
			<div class="controls">
                <input id="btnClearCache" class="btn btn-primary" onclick="initUserTiaoxiuTime()" type="button" value="初始化用户调休时长"/>
			</div>
		</div>
		
	</form:form>
</body>
</html>