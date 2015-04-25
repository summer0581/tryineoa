$(document).ready(function(){
	initView();
	assemble();
});

function assemble(){
	var formId = $(document.forms[0]).attr("id");
	var processInstanceId = requestUrlParam('processInstanceId');
	var processDefinitionKey = requestUrlParam('processDefinitionKey');
	var taskId = requestUrlParam('taskId');
	regFlowCommand(formId,processInstanceId,processDefinitionKey,taskId);
}
function initView(){//初始化界面元素
	var viewonly = $("input[name='viewonly']").val();
	if(viewonly){
		$("div[commandType],button[commandType]").not("[commandType='processStatus']").hide();
	}
}
