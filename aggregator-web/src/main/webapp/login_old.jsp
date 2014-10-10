<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String proc_name=request.getHeader("x-etturl");
    if(proc_name==null){
        proc_name=request.getContextPath();
    }else{
        ///group1/1.jsp
        proc_name=proc_name.substring(0,proc_name.substring(1).indexOf("/")+1);
    }

    out.println(proc_name);
%>