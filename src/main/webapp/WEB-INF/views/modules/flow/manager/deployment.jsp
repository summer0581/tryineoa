<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程发布</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/flow/css/flow.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/uploadify/3.2.1/uploadify.css">
	<script src="${ctxStatic}/uploadify/3.2.1/jquery.uploadify.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		bodyOnLoad();
		$("#file_upload_1").uploadify({
			method : "post", 
			buttonText : '选择文件',
	        height        : 30,
	        swf           : '${ctxStatic}/uploadify/3.2.1/uploadify.swf',
	        uploader      : '${ctx}/flow/manager/uploadify',
	        width         : 120,
	        fileObjName : "file_upload_1"
	    });
	});
	uploads = function (){
		var fileName = $("#ProcessFile").val();
		var d=/\.[^\.]+$/.exec(fileName);
		if(d == ".zip" || d == ".ZIP"){
			//$("#subForm").submit();
			var data = $("#subForm").serializeObject();
			$.ajaxFileUpload({  
		        url : '${ctx}/flow/manager/deploy',  
		        secureuri : false,//安全协议  
		        fileElementId:'ProcessFile',//id  
		        type : 'POST',  
		        dataType : 'json',  
		        data:data,  
		        async : false,  
		        error : function(data,status,e) {  
		            alert(e+'::Operate Failed!');  
		        },  
		        success : function(json) {  
					alert("操作成功！");
					parentwin.searchProcess();
					window.close();
		        }  
		    }); 
		}else{
			alert("请选择正确的文件类型");
			return false;
		}
	}
	
	function bodyOnLoad(){
		var deployId= $("#deploymentId").val();
		if(deployId == ""){
			$("#trDeploy").css("display","none");
		}
	}
	
	function testajax(){
		var data = $("#subForm").serializeObject();
		$.ajax({
			url : '${ctx}/flow/manager/testajax',
			dataType : 'json', 
			type : 'POST',  
	        data:data, 
	        async : false,  
	        error : function(data,status,e) {  
	            alert(e+'::Operate Failed!');  
	        },  
	        success : function(json) {  
				alert("Sucessful");
	        }  
		})
	}
	
	function testiframeajax(){
        if(window.attachEvent){  
            document.getElementById("jUploadFrame1429862704954").attachEvent('onload', uploadCallback);  
        }  
        else{  
            document.getElementById("jUploadFrame1429862704954").addEventListener('load', uploadCallback, false);  
        } 
        jQuery("#jUploadForm1429862704954").submit();
	}
	
    var uploadCallback = function()
	{			
		var io = document.getElementById("jUploadFrame1429862704954");
		var xml = {};
        try 
		{				
			if(io.contentWindow)
			{
				 xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerText:null;
            	 xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;
			}else if(io.contentDocument)
			{
				 xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerText:null;
            	xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;
			}						
        }catch(e)
		{
			//jQuery.handleError(s, xml, null, e);
		}
        alert(xml.responseText);
    }

</script>
</head>
<body >
<form action="${ctx}/flow/manager/deploy" method="post" id="subForm" ENCTYPE="multipart/form-data">
<input type="hidden" name="action" value="deploy">
<div class="tpl-form-border">
			<table class="table-form">
				<tr>
					<td class="title-r" style="width:300px"><p>请选择文件：</p>(<span style="color:red">注：只能选择设计器或者web下载的zip格式文件</span>)：</td>
					<td><input type="file" name="ProcessFile" id="ProcessFile"/></td>
				</tr>
				<tr id="trDeploy">
					<td class="title-r">发布号：</td>
					<td><input type="text" name="deploymentId" id="deploymentId" readOnly value="${deploymentId }"></td>
				</tr>
			</table>
			<div class="toolbar">
					
					<div class="btn-normal" style="width:60px" onclick="uploads()">
						<a href="#">发布</a>
					</div>
					<div class="hide btn-normal" style="width:80px" onclick="testajax()">
						<a href="#">测试ajax</a>
					</div>
					<div class="hide btn-normal" style="width:80px" onclick="testiframeajax()">
						<a href="#">测试iframe_ajax</a>
					</div>
			</div>
			<div>
				<table>
					<tr>
						<td><span id="btn_upload"></span>
						<input type="file" name="file_upload_1" id="file_upload_1"/></td>
					</tr>
				</table>
			</div>
	</div>
</form>

<form action="/tryineoa/a/flow/manager/testajax" method="POST" name="jUploadForm1429862704954" id="jUploadForm1429862704954" enctype="multipart/form-data" style="position: absolute; top: -1200px; left: -1200px;" target="jUploadFrame1429862704954">
	<input type="hidden" name="action" value="deploy">
	<input type="hidden" name="deploymentId" value="92b4c8a2-280b-4b6e-9039-a30e1c0459aa">
	<input type="file" name="ProcessFile" id="jUploadFile1429862704954">
</form>
<iframe id="jUploadFrame1429862704954" name="jUploadFrame1429862704954" style="position: absolute; top: -1000px; left: -1000px;"></iframe>
</body>
</html>