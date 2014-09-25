<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<div class="foot">
<%
    //如果是乐知行过来，则不显示头尾
    Object ft=request.getSession().getAttribute("fromType");
if(ft==null||!ft.toString().trim().equals("lzx")){
%>
    北京四中网校<br>Copyright &copy; <script type="text/javascript">document.write(new Date().getFullYear().toString());</script> etiantian.ALL Rights Reserved.<a href="http://www.hd315.gov.cn/beian/view.asp?bianhao=010202001070300043" target="_blank"></a>
<%}%>
</div>
<div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;" >

</div>