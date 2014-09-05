<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<%@ page import="com.school.util.UtilTool" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    response.setHeader("Cache-Control", "Public");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires",  -10);
    String proc_name= UtilTool.utilproperty.getProperty("PROC_NAME");
    String basePath = request.getScheme() + "://"
            + UtilTool.utilproperty.getProperty("IP_ADDRESS")
            +"/"+proc_name + "/";
%>

<link rel="stylesheet" type="text/css" href="<%=basePath %>css/master.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/ios.css"/>
<script src="<%=basePath %>util/xheditor/jquery/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="<%=basePath %>js/common/common.js"></script>




<%--<div id="dv_award_zs" class="jxxt_float_jiangli" style="display:none">获得<strong>1</strong>积分及<strong>1</strong>颗<span class="ico90"></span></div>--%>
<%--<div id="dv_award_jf"  class="jxxt_float_jiangli" style="display:none">获得<strong>1</strong>积分</div>--%>
<%--<script type="text/javascript">--%>
    <%--var alertMsg="${sessionScope.msg}";--%>
    <%--var WinAlerts = window.alert;--%>
    <%--window.alert = function (e) {--%>
        <%--if (e != null &&e.indexOf("获得了")>-1&&(e.indexOf("蓝宝石")>-1|| e.indexOf("积分")))--%>
        <%--{--%>
            <%--//和谐了--%>
            <%--if(e.indexOf("蓝宝石")!=-1){--%>
                <%--showModel('dv_award_zs','fade');--%>
                <%--$("#fade").hide();--%>
            <%--}else if(e.indexOf("积分")!=-1){--%>
                <%--showModel('dv_award_jf','fade');--%>
                <%--$("#fade").hide();--%>
            <%--}--%>
            <%--hideAwardDiv();--%>
        <%--}--%>
        <%--else--%>
        <%--{--%>
            <%--WinAlerts (e);--%>
        <%--}--%>
    <%--};--%>
    <%--$(function(){--%>
        <%--if(alertMsg.length>0){--%>
            <%--alert(alertMsg);--%>
        <%--}--%>
    <%--})--%>
    <%--function closeAwardDiv(){--%>
        <%--$("#dv_award_zs").hide('slow');--%>
        <%--$("#dv_award_jf").hide('slow');--%>
    <%--}--%>

    <%--//显示一秒后自动消失--%>
    <%--var awardTime=0;--%>
    <%--function hideAwardDiv(){--%>
        <%--if(awardTime==1){--%>
            <%--closeAwardDiv();--%>
            <%--awardTime=0;return;--%>
        <%--}--%>
        <%--awardTime++;--%>
        <%--setTimeout("hideAwardDiv()",3000);--%>
    <%--}--%>

<%--</script>--%>
<%--<%--%>
    <%--if(session.getAttribute("msg")!=null&&session.getAttribute("msg").toString().trim().length()>0)--%>
        <%--session.removeAttribute("msg");--%>
<%--%>--%>
