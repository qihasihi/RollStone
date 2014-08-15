<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<%@include file="../common.jsp"%>
<%
    //资源系统
    modelType=1;%>
<%String noAjaxRefreshTime=UtilTool.utilproperty.getProperty("NO_AJAX_REFRESH_TIME");%>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/zyxt.css"/>
 <script type="text/javascript">
     var allowRefreshTime="<%=noAjaxRefreshTime%>";
     var isallowRefresh=true;

     var t=0;
     function beginJs(){
         t++;
         if(t<allowRefreshTime){
             setTimeout("beginJs()",1000);
         }else{
             t=0;
             isallowRefresh=true;
         }
     }

   var allowRefresh=function(){
         $("body").ajaxSend(function(evt, request, settings){
             if(isallowRefresh){
                 isallowRefresh=false;
                 beginJs();
             }
         });
     }

 </script>
<!-- 
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/sz_style.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/szbbs.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/szkjj.css"/>
	 -->