<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<%
    //如果是乐知行过来，则不显示头尾
    Object fromType=session.getAttribute("fromType");
if(fromType==null||!fromType.toString().trim().equals("lzx")){
%>
<div class="foot">主办方：${sessionScope.CURRENT_SCHOOL_NAME}&nbsp;&nbsp;&nbsp;&nbsp;协办方：北京四中龙门网络教育技术有限公司</div>
<div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>
<%}%>