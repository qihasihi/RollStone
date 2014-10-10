<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String proc_name=request.getHeader("x-etturl");
    if(proc_name==null){
        proc_name=request.getContextPath();
    }else{
        ///group1/1.jsp
        proc_name=proc_name.substring(0,proc_name.substring(1).indexOf("/")+1);
    }
    String ipStr=request.getServerName()+":"+request.getServerPort();
    //UtilTool.utilproperty.getProperty("PROC_NAME");
    String basePath = request.getScheme() + "://"
            + ipStr
            +"/"+proc_name + "/";
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
