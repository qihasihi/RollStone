<!DOCTYPE HTML>
<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 15-1-11
  Time: 上午9:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp"%>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        $(function(){
           loadDiv(1);

            $("li[name='li_stat']").bind("click",function(){
               $(this).siblings().removeClass("crumb");
               $(this).addClass("crumb");
            });
        });

        function loadDiv(type){
            var url="";
            if(type==1){
                url="sysm?m=toAdminPerformanceTea";
            }else{
                url="sysm?m=toAdminPerformanceStu";
            }
            $("#dv_stat").load(url,function(){

            });
        }
    </script>
</head>
<body>

<%@include file="/util/head.jsp" %>
<%@include file="/util/nav-base.jsp" %>
<div id="nav">
    <ul>
        <li><a href="user?m=list">用户管理</a></li>
        <li><a href="cls?m=list">组织管理</a></li>
        <li><a href="sysm?m=logoconfig">系统设置</a></li>
        <li class="crumb"><a href="sysm?m=toAdminPerformance">使用统计</a></li>
    </ul>
</div>

<div class="content2" >
    <div class="subpage_lm">
        <ul>
            <li name="li_stat" class="crumb" ><a href="javascript:loadDiv(1);" >教师统计</a></li>
            <li name="li_stat" ><a href="javascript:loadDiv(2);">学生统计</a></li>
        </ul>
    </div>

    <div class="jcpt_sytj public_input" id="dv_stat">
    </div>
</div>

<%@include file="/util/foot.jsp" %>
</body>
</html>
