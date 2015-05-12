<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="flowInitMap" type="java.util.Map" required="true" description="流程初始化参数map"%>
	<style type="text/css">
		.row_border div{
			border:1px solid black;
		}
		.btn-normal {
		  display: inline-block;
		  margin: 8px 8px;
		 }
	</style>
<script type="text/javascript"
src="${ctxStatic}/flow/js/flowcommand.js"></script>
<script type="text/javascript"
src="${ctxStatic}/flow/js/flowautoassemble.js"></script>
<div class="toolbar">
		<input type="hidden" id="action" name="action" value="${flowInitMap.action }" />
		<input type="hidden" id="viewonly" name="viewonly" value="${flowInitMap.viewonly }" />
		<input type="hidden" id="nodeId" name="nodeId" value="${flowInitMap.nodeId }" />
		<input type="hidden" id="processDefinitionKey" name="processDefinitionKey" value="${flowInitMap.processDefinitionKey }" />
		<c:forEach items="${flowInitMap.commandList}" var="row"
			varStatus="status">
			<div class="btn-normal" id="btn_${status.index+1}"
				commandId="${row.id}" commandName="${row.name}"
				commandType="${row.type}" isAdmin="${row.isAdmin}"
				isVerification="${row.isVerification}"
				isSaveData="${row.isSaveData}"
				isSimulationRun="${row.isSimulationRun}" nodeId="${row.nodeId}"
				nodeName="${row.nodeName}">
				<a href="#" class="btn btn-primary">${row.name}</a>
			</div>
		</c:forEach>
</div>