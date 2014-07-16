<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common.jsp" %> 
<html>
<head>
<title>数字化校园</title>
<link rel="stylesheet" type="text/css" href="css/dxzx.css"/>
<script type="text/javascript" src="js/sms.js"></script>
</head>

<body>
<%@include file="/util/head.jsp" %>
<%@include file="/util/nav-base.jsp" %>

<div class="jxpt_layout">
<div class="p_20">
<p class="public_title">查看信息</p>
<p class=" t_r">
<a href="javascript:toReply(${smsinfo.smsid});"  class="an_blue_small">回复</a>
<a href="javascript:toForward(${smsinfo.smsid});" class="an_blue_small">转发</a>
<c:if test="${type==1}">
<a href="javascript:doDelReceiveSMS(${sms_ref});" class="an_blue_small">删除</a>
</c:if>
<c:if test="${type==2}">
<a href="javascript:doDelSentSMS(${smsinfo.smsid});" class="an_blue_small">删除</a>
</c:if>
<c:if test="${type==1}">
<a href="sms?m=inbox" class="an_blue_small">返回</a></p>
</c:if>
<c:if test="${type==2}">
<a href="sms?m=sent" class="an_blue_small">返回</a></p>
</c:if>

<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 zt14">
  <col class="w120" />
  <col class="w780" />
  <tr>
    <th>信息主题：</th>
    <td>${smsinfo.smstitle}</td>
  </tr>
  <c:if test="${type==1}">
  <tr>
    <th>发&nbsp;送&nbsp;者：</th>
    <td>${smsinfo.sendername}</td>
  </tr>
  </c:if>
  <c:if test="${type==2}">
  <tr>
    <th>接&nbsp;收&nbsp;者：</th>
    <td>${smsinfo.receiverlist}</td>
  </tr>
  </c:if>
  <tr>
    <th>发送时间：</th>
    <td>${smsinfo.ctime}</td>
  </tr>
  <tr>
    <th>消息内容：</th>
    <td class="dxzx_info">${smsinfo.smscontent}</td>
  </tr>
</table>
</div>
</div>

<%@include file="/util/foot.jsp" %>
</body>
</html>
