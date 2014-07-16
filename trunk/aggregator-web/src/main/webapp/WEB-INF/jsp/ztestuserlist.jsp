<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>数字化校园</title>


</head>
 
<body >
<c:forEach items="${ztestuserlist}" var="user" >
   <a href="ztestuserinfo?m=test"> ${user.id} ________________  ${user.name}</a>  </br>
</c:forEach>

</body>
</html>