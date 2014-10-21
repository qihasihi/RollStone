<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
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
<head>
    <script>
        $(function(){


        });
        function loadJsp(){
            $("#div").load("1.jsp");
        }
    </script>
</head>
<body>
<a href="javascript:loadJsp()">loadJsp</a>
<a href="javascript:b()">Click</a>
<div id="div" onclick="">

</div>
</body>