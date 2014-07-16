<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.school.entity.UserInfo"%>
<%@include file="/util/common.jsp"%>
<%
UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
String userid=user.getRef();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>数字化校园</title>
<script type="text/javascript">
</script>
</head>

<body>

<%@include file="/util/head.jsp" %>
    <div class="ej_content">
        <div class="text">
            <p class="title1">${notice.noticetitle }</p>
            <p class="title2">发布时间:${notice.ctimestring}&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最后编辑时间:${notice.mtimestring}&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;浏览(${notice.clickcount})</p>
            <p>　${notice.noticecontent }</p>
        </div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
