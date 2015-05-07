<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户信息管理管理</title>
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
			citySelect.addressInit('quyu','province','city','','${oaCustomerRmanager.quyu}', '${oaCustomerRmanager.province}', '${oaCustomerRmanager.city}', '');
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">个人客户<shiro:hasPermission name="oa:oaCustomerRmanager:edit">${not empty oaCustomerRmanager.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaCustomerRmanager:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaCustomerRmanager" action="${ctx}/oa/oaCustomerRmanager/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="type" value="geren"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">客户姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">下次回访时间：</label>
			<div class="controls">
				<input name="hfTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${oaCustomerRmanager.hfTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select path="sex" class="input-mini ">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户种类：</label>
			<div class="controls">
				<form:select path="zhonglei" class="input-mini ">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('oa_customer_zhonglei')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户信用等级：</label>
			<div class="controls">
				<form:radiobuttons path="xinyongdj" items="${fns:getDictList('oa_customer_xinyongdj')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户行业：</label>
			<div class="controls">
				<form:select path="hangye" class="input-xlarge ">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('oa_customer_hangye')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户关系等级：</label>
			<div class="controls">
				<form:select path="guanxidj" class="input-xlarge ">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('oa_customer_guanxidj')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户阶段：</label>
			<div class="controls">
				<form:select path="jieduan" class="input-xlarge ">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('oa_customer_jieduan')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户来源：</label>
			<div class="controls">
				<form:select path="laiyuan" class="input-xlarge ">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('oa_customer_laiyuan')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户简介：</label>
			<div class="controls">
				<form:textarea path="jianjie" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="files" path="files" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="files" type="files" uploadPath="/oa/oaCustomerRmanager" selectMultiple="true"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">客户区域：</label>
			<div class="controls">
				<select id="quyu" name="quyu" class="input-xlarge ">
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省份：</label>
			<div class="controls">
				<select id="province" name="province" class="input-xlarge ">
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">城市：</label>
			<div class="controls">
				<select id="city" name="city" class="input-xlarge ">
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮箱：</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="telephone" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">传真：</label>
			<div class="controls">
				<form:input path="fax" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网址：</label>
			<div class="controls">
				<form:input path="website" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户共享：</label>
			<div class="controls">
				<form:select path="gongxiang" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('oa_customer_share')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">团队浏览：</label>
			<div class="controls">
				<form:radiobuttons path="tuanduiliulan" items="${fns:getDictList('oa_customer_tuanduiliulan')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户价值评估：</label>
			<div class="controls">
				<form:radiobuttons path="jiazhipg" items="${fns:getDictList('oa_customer_xinyongdj')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:oaCustomerRmanager:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>