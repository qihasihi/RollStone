<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common.jsp" %>
  <head>
  	<script type="text/javascript" src="<%=basePath %>js/service.js"></script>
  	<script type="text/javascript">
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
		$("a").filter(function(){
			var href = this.href;
			href = href.substr(href.indexOf(",")+2);
			href = href.substr(0,href.indexOf("'"));
			return this.id.length>0 && prid==href;
		}).each(function(){
			$(this).prev("img").attr("src",tsrc)
		});
	}
	//选中全部
	function selAll(){
		//根节点
		var tsrc = $("#id0").attr("src");
		tsrc = tsrc.indexOf("check1") != -1 ? icon.node : icon1.node;
		$("#id0").attr("src",tsrc);
		
		//选中效果
		$("img").filter(function(){
			return this.id.indexOf("id") != -1;
		}).attr("src",tsrc);
	
	}
	
	function selComplete(){
		//获取权限标识
		var serviceArray = $("img").filter(function(){
			return this.id.indexOf("id") != -1 && this.id!="id0" && this.src.indexOf("check1") != -1;
		});
		var sidstr = "";
		if(serviceArray.length>0){
			$.each(serviceArray,function(idx,itm){
				if(sidstr.length>0)
					sidstr+=",";
				sidstr += $(itm).parent("div").attr("id");
			});
		}
		var msg ="";
		if(sidstr.length<1){
			sidstr = "isRemove";
			msg = "您确定不给指定用户分配功能？";
		}else{
			msg = "您确定给指定用户分配功能？";		
		}
		if(!confirm(msg)){
			return;
		}
		window.returnValue = sidstr;
		window.close();
	}
  	</script>
  	<script type="text/javascript">
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
  	</script>
  </head>
  
  <body>
  	<div style="font-size:13px;width:70%;position:absolute;left:50px;border:1px dashed black;"> 
  		<br>系统管理&gt;指定功能服务<br>
  		<!-- dtree -->
  		<div class="dtree">
  			<p><a href="javascript: d.openAll();"><img src="images/an26.gif" alt="展开" title="展开" width="61" height="21" border="0" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript: d.closeAll();"><img src="images/an27.gif" alt="关闭" title="关闭" width="61" height="21" border="0" /></a></p>
  			<p style="color:red;">鼠标左键点击节点选择权限[名称 --- 标识]</p>
  			<p><input type="button" onclick="selComplete()" value="选择完毕"/></p>
  			<div id="_dtree">
  				<script type="text/javascript">
  					var d = new dTree('d');
  					d.changeIcon(icon);
  					d.add("0","-1","全选/不选","javascript:selAll()");
  					<c:if test="${!empty serviceList}">
  						<c:forEach items="${serviceList}" var="si">
  							d.add(${si.ref},${si.parentref},"${si.servicename}[${si.ref}]","javascript:clickNode('${si.ref}','${si.parentref}')");
  						</c:forEach>
  					</c:if>
  					document.write(d);
  				</script>
  			</div>
  		</div>
  		<!-- dtree -->
  	</div>
    
     <script type="text/javascript">
    	<c:if test="${!empty usList}">
    		<c:forEach items="${usList}" var="us">
    			var prid = "${us.serviceid}";
    			$("div").filter(function(){
    				return this.id == prid
    			}).children("img").filter(function(){
    				return this.id.length>0;
    			}).attr("src",icon1.node);
    		</c:forEach>
    	</c:if>
    </script>
    
    
    <!-- ----遮蔽层--- -->
    <input type="hidden" id="ref">
    <div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>
  </body>

