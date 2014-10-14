<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
   // response.sendRedirect(baseUrl+"/1.jsp");
    String a="/group1/1.jsp";
    if(a==null){
        a=request.getContextPath().replaceAll("/","");
    }else{
        ///group1/1.jsp
        a=a.substring(0,a.substring(1).indexOf("/")+1).replaceAll("/","");
    }
    System.out.println(a);
%>

<body><iframe src="APP.html" frameborder="0" scrolling="no" style="width: 50%;height: 100%;" ></iframe></body>