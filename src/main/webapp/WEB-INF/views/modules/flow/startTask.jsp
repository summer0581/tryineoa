<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程状态</title>
	<meta name="decorator" content="default"/>
<style>
a{text-decoration: none;}
/* 分类 */
.type-box { padding:20px; height:100%; text-align:center;}
.type-box .type {  margin:0 auto 10px auto; padding-bottom:12px; width:85%; height:auto; overflow:hidden; background:#bbb;}
.type-box .type h1 { display:block; padding:6px 10px; height:16px; line-height:16px; background:#999; color:#fff; font-family:"微软雅黑"; font-size:14px; font-weight:bold; text-align:left;}
.type-box .type a { float:left; margin:12px 0 0 15px; padding:15px 10px 15px 15px; display:block; width:100px; height:40px; overflow:hidden; text-decoration:none; line-height:21px; background:#eee ; border:3px solid #eee;}
.type-box .type a div { font-size:14px; text-align:left; font-family:"微软雅黑";}
.type-box .type a div:first-letter {font-size:200%;font-weight:bold;float:left;margin:0 5px;margin-top: 9px;}
.type-box .type a:hover { background:#eee ;}
</style>


</head>
<body >
	<div class="type-box">
			
			<div class="type">
	        	<h1>最近使用的流程</h1>
	        	<c:forEach items="${lastest}" var="process" varStatus="status">
	        		<a href="#" formUrl="${process.formUrl}" processDefinitionKey="${process.processDefinitionKey}"><div>${process.processDefinitionName}</div></a>
	        	</c:forEach>
	    	</div>
	    	
		<c:forEach items="${result}" var="row" varStatus="status">
	    	<div class="type">
	        	<h1>${row.key}</h1>
	        	<c:forEach items="${row.value}" var="tmp" varStatus="status">
	        		<a href="#" formUrl="${tmp.formUrl}" processDefinitionKey="${tmp.processDefinitionKey}"><div>${tmp.processDefinitionName}</div></a>
	        	</c:forEach>
	    	</div>
		</c:forEach>
    </div>
   <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="title"></h3>
	  </div>
	  <div class="modal-body">
	    <iframe id="body"></iframe>
	  </div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	  </div>
	</div>
    <script>
$(function(){
  $("a[processDefinitionKey]").click(function(){
     var pdk = $(this).attr("processDefinitionKey");
     var flowname = $(this).children("div").text();
     var formUrl = $(this).attr("formUrl");//"FlowCenter?action=startOneTask";
     var url = "${ctx}/"+formUrl;
     if(formUrl.indexOf("?")!=-1){
    	 url+="&";
    	
     }else{
    	 url+="?";
     }
     url+="userId=<%=request.getAttribute("userId")%>&processDefinitionKey="+pdk;
    var obj = {};
    var aw = screen.width-100;
    var ah = screen.height-200;
	    loc_x=(screen.availWidth-aw)/2;
	    loc_y=(screen.availHeight-ah)/2;
    //window.open (url, "_blank",
    //"height=" + ah + ", width=" + aw + ",toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no,top=" + loc_y + ",left=" + loc_x + "")
    location = url;
    
  });
});
</script>
</body>
</html>
