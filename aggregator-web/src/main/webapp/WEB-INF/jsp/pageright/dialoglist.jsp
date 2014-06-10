<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp" %>
  <head>
  	<script type="text/javascript" src="<%=basePath %>js/pageright.js"></script>
  	<script type="text/javascript">
  	  
  		var useridarray=window.dialogArguments;   
  		var icon = {
		root				: '<%=basePath%>images/check0.gif',
		folder			: '<%=basePath%>images/check0.gif',
		folderOpen	: '<%=basePath%>images/check0.gif',
		node				: '<%=basePath%>images/check0.gif',
		empty				: '<%=basePath%>images/empty.gif',
		line				: '<%=basePath%>images/line.gif', 
		join				: '<%=basePath%>images/join.gif',
		joinBottom	: '<%=basePath%>images/joinbottom.gif',
		plus				: '<%=basePath%>images/plus.gif',
		plusBottom	: '<%=basePath%>images/plusbottom.gif',
		minus				: '<%=basePath%>images/minus.gif',
		minusBottom	: '<%=basePath%>images/minusbottom.gif',
		nlPlus			: '<%=basePath%>images/nolines_plus.gif',
		nlMinus			: '<%=basePath%>images/nolines_minus.gif'
	};
	var icon1 = {
		root				: '<%=basePath%>images/base.gif',
		folder			: '<%=basePath%>images/check1.gif',
		folderOpen	: '<%=basePath%>images/check1.gif',
		node				: '<%=basePath%>images/check1.gif',
		empty				: '<%=basePath%>images/empty.gif',
		line				: '<%=basePath%>images/line.gif',
		join				: '<%=basePath%>images/join.gif',
		joinBottom	: '<%=basePath%>images/joinbottom.gif',
		plus				: '<%=basePath%>images/plus.gif',
		plusBottom	: '<%=basePath%>images/plusbottom.gif',
		minus				: '<%=basePath%>images/minus.gif',
		minusBottom	: '<%=basePath%>images/minusbottom.gif',
		nlPlus			: '<%=basePath%>images/nolines_plus.gif',
		nlMinus			: '<%=basePath%>images/nolines_minus.gif'
	};
	//单个选中
	function clickNode(prid,ppid){
		var tsrc = $("#"+prid).children("img").filter(function(){ 
			return this.id.length>0;
		}).attr("src");
		tsrc = tsrc.indexOf("check1") != -1 ? icon.node : icon1.node;

		  
		  
		$("#"+prid).children("img").filter(function(){
			return this.id.length>0;
		}).attr("src",tsrc);
 		
		//点击父节点自动关联子节点 
		$("#"+prid).siblings().eq(0).children("div").find("img").filter(
				function(){   
					return this.id.length>0&&this.src.indexOf("check")!=-1
				}).each(function(){$(this).attr("src",tsrc)})
	}    
	//选中全部
	function selAll(){
		var tsrc = $("#id0").attr("src");
		tsrc = tsrc.indexOf("check1") != -1 ? icon.node : icon1.node;
		$("#id0").attr("src",tsrc);
		
		$("img").filter(function(){return this.id.indexOf("id")!=-1&&this.src.indexOf("check")!=-1}).attr("src",tsrc); 
	}
	
	function selComplete(){
		//获取权限标识
		var rightidstr =$("img").filter(function(){
			return this.id.indexOf("id") != -1 && this.id!="id0" && this.src.indexOf("check1") != -1;
		});
		var ridstr='',msg='';
		if(rightidstr.length>0){
			$.each(rightidstr,function(idx,itm){
				if(ridstr.length>0)
					ridstr+=",";
				ridstr += $(itm).parent("div").attr("id");
			});
		}
		if(ridstr.length<1){
			msg='确认清空权限？';
			ridstr='isRemove';  
		}else{ 
			msg='确认分配权限？'; 
		}   
		if(!confirm(msg)){return;}    
		window.returnValue = ridstr;
		window.close();
	} 
  	</script>
  
  </head>
  
  <body>
  <%--@include file="/util/nav.jsp" --%>  

  <div class="jxpt_layout">
<p class="jxpt_icon01"><!-- <span><a href="1">返回</a></span> -->权限分配</p>
<div class="p_20">
  <p class="p_b_10"><a href="javascript: d.openAll();"><img src="images/an34_130705.png" title="展开树" width="76" height="27" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript: d.closeAll();"><img src="images/an35_130705.png" title="关闭树" width="76" height="27" /></a></p>
   <!-- dtree -->
  		<div class="dtree">
  			<p style="color:red;">鼠标左键点击节点选择权限</p> 
  			<div id="_dtree">
  				<script type="text/javascript">
  					var d = new dTree('d');
  					d.changeIcon(icon);
  					d.add("0","-1","全选/不选","javascript:selAll()");
  					<c:if test="${!empty prList}">
  						<c:forEach items="${prList}" var="pr">
  							var content=${pr.pagerighttype}==1?"${pr.pagename}&nbsp;---&nbsp;${pr.pagevalue}":"${pr.pagename}[${pr.pagerightid}]";
  							if(${pr.pagerightparentid}==0){
  								content="${pr.pagename}";     
  							}
  							d.add(${pr.pagerightid},${pr.pagerightparentid},content,"javascript:clickNode('${pr.pagerightid}','${pr.pagerightparentid}')");
  						</c:forEach> 
  					</c:if> 
  					document.write(d);
  				</script> 
  			</div>   
  		</div> 
  		<!-- dtree -->
    <p class="p_t_20"><a href="javascript:selComplete()"  class="an_blue">确&nbsp;定</a></p>
</div>  
</div>

  
    <script type="text/javascript">
    	<c:if test="${!empty prDataList}">
    		<c:forEach items="${prDataList}" var="pr">
    			var prid = "${pr.pagerightid}";
    			$("div").filter(function(){
    				return this.id == prid 
    			}).children("img").filter(function(){
    				return this.id.length>0;
    			}).attr("src",icon1.node);  
    		</c:forEach>
    		d.openAll();
    	</c:if>  
    </script>  
    
    
    <!-- ----遮蔽层--- -->
    <input type="hidden" id="ref">

<%@include file="/util/foot.jsp" %>
  </body>

