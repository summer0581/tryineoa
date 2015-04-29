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
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/oaMessage/">内部消息列表</a></li>
		<li class="active"><a href="${ctx}/oa/oaMessage/form?id=${oaMessage.id}">内部消息<shiro:hasPermission name="oa:oaMessage:edit">${not empty oaMessage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaMessage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaMessage" action="${ctx}/oa/oaMessage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接收人名称：</label>
			<div class="controls">
				<sys:treeselect id="receivedUser" name="receivedUserids" value="${oaMessage.receivedUserids}" labelName="receivedUsernames" labelValue="${oaMessage.receivedUsernames}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">内部消息发送记录：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>接受人</th>
								<th>阅读标记</th>
								<th>阅读时间</th>
								<shiro:hasPermission name="oa:oaMessage:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="oaMessageRecordList">
						</tbody>
						<shiro:hasPermission name="oa:oaMessage:edit"><tfoot>
							<tr><td colspan="5"><a href="javascript:" onclick="addRow('#oaMessageRecordList', oaMessageRecordRowIdx, oaMessageRecordTpl);oaMessageRecordRowIdx = oaMessageRecordRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="oaMessageRecordTpl">//<!--
						<tr id="oaMessageRecordList{{idx}}">
							<td class="hide">
								<input id="oaMessageRecordList{{idx}}_id" name="oaMessageRecordList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="oaMessageRecordList{{idx}}_delFlag" name="oaMessageRecordList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<sys:treeselect id="oaMessageRecordList{{idx}}_user" name="oaMessageRecordList[{{idx}}].user.id" value="{{row.user.id}}" labelName="oaMessageRecordList{{idx}}.user.name" labelValue="{{row.user.name}}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
							</td>
							<td>
								<input id="oaMessageRecordList{{idx}}_readFlag" name="oaMessageRecordList[{{idx}}].readFlag" type="text" value="{{row.readFlag}}" maxlength="1" class="input-small "/>
							</td>
							<td>
								<input id="oaMessageRecordList{{idx}}_readDate" name="oaMessageRecordList[{{idx}}].readDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
									value="{{row.readDate}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</td>
							<shiro:hasPermission name="oa:oaMessage:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#oaMessageRecordList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var oaMessageRecordRowIdx = 0, oaMessageRecordTpl = $("#oaMessageRecordTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(oaMessage.oaMessageRecordList)};
							for (var i=0; i<data.length; i++){
								addRow('#oaMessageRecordList', oaMessageRecordRowIdx, oaMessageRecordTpl, data[i]);
								oaMessageRecordRowIdx = oaMessageRecordRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="oa:oaMessage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>