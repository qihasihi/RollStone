<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>

<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>
    <script type="text/javascript"
            src="<%=basePath %>js/personalspace/friend.js"></script>
    <script type="text/javascript">
        $(function(){
            loadGroup();
        });
    </script>


</head>
<body>
<div style="width: 900px;">
    <h1>好友管理</h1>
    <div style="width: 30%;float: left">
        <ul id="ul_group">

        </ul>
        <a href="#" onclick="showModel('dv_group')">创建分组</a>
    </div>

    <div style="width: 70%;float: left">
        <p id="p_operate"></p>
        <ul id="ul_friend">

        </ul>
    </div>

</div>

<div id="dv_group" style="display: none;">
    分组名：<input type="text" id="groupname"/>
    <input value="保存" type="button" onclick="doAddGroup()"/><input type="button" onclick="closeModel('dv_group')" value="取消"/>
</div>
<div id="dv_upd" style="display: none;">
    分组名：<input type="text" id="groupname"/>
    <input value="保存" type="button" onclick="doUpdGroup()"/><input type="button" onclick="closeModel('dv_upd')" value="取消"/>
    <input id="hd_groupid" type="hidden"/>
</div>

<%@include file="/util/foot.jsp" %>
</body>
</html>
