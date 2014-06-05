<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
<html>
<head>
<title>${sessionScope.CURRENT_TITLE}</title>
<script type="text/javascript">
$(function(){
	$(".header_ico1 a").attr("href","../"+$(".header_ico1 a").attr("href"));
})
</script>
</head>

<body>
  <%@include file="/util/head.jsp" %>
<div class="jxpt_layout">
<%@include file="/util/nav.jsp" %>
  <%/*<p class="jxpt_icon06">
  	<c:if test="${!empty param.clsid}">班级统计</c:if> 
  	<c:if test="${empty param.clsid}">论题统计</c:if>  	
  	</p>  	
  	*/ %>
  <p class="jxpt_icon05">
	个人统计(${user.realname })
</p>
  <div class="jxpt_content_layout">
   <%/* <p class="font_strong p_l_10">
  	<c:if test="${!empty clsinfo}">${clsinfo.classGrade}${clsinfo.className}</c:if>
  	 <c:if test="${!empty themeinfo}">论题详细统计（ ${themeinfo.themetitle}）</c:if>
  	 <c:if test="${empty themeinfo}">班级论坛详细统计</c:if> 
    </p>
    */ %>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
      <colgroup span="2" class="w270"></colgroup>
      <colgroup class="w160"></colgroup>      
  	 <colgroup span="2" class="w70"></colgroup>
  	 <colgroup class="w220"></colgroup>
      <tr>
        <th>主题名</th>
        <th>所属论题</th>
        <th>发表时间</th>
        <th>查看</th>       
        <th>回复</th>
        <th>最后发表</th>       
      </tr>
      <c:if test="${!empty mapObjList}">
      	<c:forEach items="${mapObjList}" var="mpobj" varStatus="mpidx">   	
      		<c:if test="${mpidx.index%2==1}"><tr class="trbg2"></c:if>
      		<c:if test="${mpidx.index%2!=1}"><tr></c:if>
      			<td><a class="font-lightblue" href="../activetopic/toTopicDetail?topicid=${mpobj.TOPIC_ID }&themeid=${mpobj.THEME_ID}">${mpobj.TOPIC_TITLE}</a></td>
      			<td>${mpobj.THEME_TITLE}</td>
      			<td>${mpobj.FABTIME}</td>
      			<td>${mpobj.WATCH_COUNT}</td>      			
      			<td>${mpobj.RESTORECOUNT}</td>
      			<td>${mpobj.LASTFB }</td>
      		</tr> 
      	</c:forEach>  
      </c:if>     
    </table>
  </div>
</div>
 <%@include file="/util/foot.jsp" %>
</body>
</html>
