<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String baseUrl=request.getSession().getAttribute("IP_PROC_NAME").toString();

    response.sendRedirect(baseUrl+"/1.jsp");%>