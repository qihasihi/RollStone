<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-7-8
  Time: 下午2:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <title></title>
    <script src="js/teachpaltform/resource.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){
            getRemoteResources();
        });
    </script>
</head>
<body>
    <p>高清课堂</p><p><input type="text" id="keyword"/><input type="button" onclick="getLikeRemoteResources()" value="查询"/> </p>
    <div>
        <ul id="gaoqing">
        </ul>
    </div>
</body>
</html>
