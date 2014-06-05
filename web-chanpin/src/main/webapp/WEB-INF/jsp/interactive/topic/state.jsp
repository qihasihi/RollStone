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
<div class="subpage_head"><span class="ico55"></span><strong>论题统计&mdash;&mdash;${tpc.topictitle}</strong></div>
<div class="content1">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 public_input">
        <colgroup span="2" class="w240"></colgroup>
        <colgroup span="2" class="w220"></colgroup>
        <caption>分班级查看：
            <select name="select" onchange=";location.href='tptopic?m=toTopicStatices&topicid=${param.topicid}&'+this.value;">
                <c:if test="${!empty tccList}">
                    <c:forEach items="${tccList}" var="tc">
                        <c:if test="${empty clsid||clsid==tc.CLASS_ID&&type==tc.CLASSTYPE}">
                            <option value="clsid=${tc.CLASS_ID}&type=${tc.CLASSTYPE}" selected>${tc.CLASSES}</option>
                        </c:if>
                        <c:if test="${clsid!=tc.CLASS_ID||type!=tc.CLASSTYPE}">
                             <option value="clsid=${tc.CLASS_ID}&type=${tc.CLASSTYPE}">${tc.CLASSES}</option>
                        </c:if>
                    </c:forEach>
                </c:if>
            </select></caption>
        <tr>
            <th>学号</th>
            <th>姓名</th>
            <th>发主帖数</th>
            <th>评论数</th>
        </tr>
        <c:if test="${!empty dataMapList}">
            <c:forEach items="${dataMapList}" var="dm" varStatus="dmidx">
                 <c:if test="${dmidx.index!=0&&dmidx.index%2==0}">
                     <tr class="trbg1">
                 </c:if>
                <c:if test="${dmidx.index!=0&&dmidx.index%2==0}">
                     <tr>
                 </c:if>
                <td>${dm.STU_NO}</td>
                <td>${dm.REALNAME}</td>
                <td>${dm.THEMECOUNT}</td>
                <td>${dm.REPLYCOUNT}</td>
                 </tr>
            </c:forEach>
        </c:if>

    </table>
    <br>
</div>
 <%@include file="/util/foot.jsp" %>
</body>
</html>
