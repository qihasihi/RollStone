<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<%@ page import="com.school.util.UtilTool" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    response.setHeader("Cache-Control", "Public");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
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


