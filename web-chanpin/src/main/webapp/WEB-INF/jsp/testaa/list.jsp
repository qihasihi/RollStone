<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  
  <script src="<%=basePath %>js/common/jquery1.4.js"></script>

		<script type="text/javascript">
    	function getAjaxDate(){		
    		
			$.ajax({
				url:"testaa?m=ajaxtest",
				type:'post',
				dataType:'json',
				error:function(){alert('error');},
				success:function(json){
					if(json.type=='error'){
						alert(json.msg);
					}else{ 
						<%--	$.each(json.objList[0],function(idx,itm){
							alert(itm);	 
							$("#div_1").html(json.objList[0][idx]+"");
						}); --%>
						$.each(json.objList,function(idx,itm){
							alert(itm);	 
							$("#div_1").html(json.objList[0][idx]+"");
						}); 
					//	$("#div_1").html(json.objList[0]+"");     
					} 
				}  
			});  

			<%--	$.post("testaa?m=ajaxtest",function(){
				alert("1234556");
				}); --%>		
    	
       	}    
   </script>
  <body>
        <a href="javascript:getAjaxDate()" >查询数据"</a>
		<div id="div_1"></div>
  </body>
</html>
