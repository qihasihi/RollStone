<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%

String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath() + "/";
%>
	<script src="<%=basePath %>js/common/jquery1.4.js"></script>

		<script type="text/javascript">
    	function getAjaxDate(){		
			$.ajax({
					url:"textlist?m=ajaxlistaaa",
					type:'post',
					dataType:'json',
					error:function(){alert('error');},
					success:function(json){
						if(json.type=='error'){
							alert(json.msg);
						}else{ 
							$.each(json.objList,function(idx,itm){
								alert(itm);	 
							}); 
							$("#ipt").html(json.objList[0]+"");     
						} 
					}  
				});  

        		
    	
       	}     
    </script>
	<body>
		<a href="javascript:getAjaxDate()" >查询数据"</a>
		<div id="ipt"></div> 
	</body>