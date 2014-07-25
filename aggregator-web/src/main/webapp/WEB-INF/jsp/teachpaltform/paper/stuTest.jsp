<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-6-30
  Time: 下午2:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <script type="text/javascript" src="js/teachpaltform/testpaper.js"></script>
    <script type="text/javascript">
        var quesSize="${quesSize}";
        var courseid="${courseid}";
        var taskid="${taskid}";
        var subQuesId=",";
        var paperid="${paperid}";
        var allquesidObj="${allquesidObj}";
        var sumScore=100,avgScore=parseInt(100/quesSize);
        var _QUES_IMG_URL="<%=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>";
        $(function(){
            loadQuesNumberTool(quesSize);
            nextNum(0);
        });
    </script>
</head>
<body>
<input type="hidden" value="${paperid}" name="hd_paper_id" id="hd_paper_id"/>
<div class="subpage_head">
    <p class="back"><span class="ico_time"></span>${taskstatus}</p>
    <span class="ico55"></span><strong>
    <c:if test="${!empty paperObj&&paperObj.papertype==3}">成卷测试</c:if>
    <c:if test="${!empty paperObj&&paperObj.papertype==4}">自主测试</c:if>
    <c:if test="${!empty paperObj&&paperObj.papertype==3}">
        —${paperObj.papername}
    </c:if>
    </strong>
</div>

<div class="content1">
    <div id="dv_test">
        <div class="jxxt_zhuanti_rw_ceshi">
            <p class="jxxt_zhuanti_rw_ceshi_an" id="p_operate">
            </p>
            <ul id="dv_ques_number">
            </ul>
            <div class="clear"></div>
     </div>
    <div class="jxxt_zhuanti_rw_ceshi_shiti font-black public_input" id="dv_question">
    </div>
</div>
<%@include file="/util/foot.jsp"%>
</body>
</html>
