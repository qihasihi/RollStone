<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>

<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>
    <script type="text/javascript" src="<%=basePath %>js/teachpaltform/tptask.js"></script>
    <script type="text/javascript">
        $(function(){

        });
    </script>


</head>
<body>
<div class="subpage_head"><span class="ico55"></span><strong>班级管理</strong></div>
<div>

</div>
<div class="content1">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
        <colgroup span="2" class="w110"></colgroup>
        <colgroup class="w450"></colgroup>
        <colgroup class="w270"></colgroup>
        <tr>
            <th>学号</th>
            <th>姓名</th>
            <th>作答内容 </th>
            <th>附件</th>
        </tr>

    </table>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
