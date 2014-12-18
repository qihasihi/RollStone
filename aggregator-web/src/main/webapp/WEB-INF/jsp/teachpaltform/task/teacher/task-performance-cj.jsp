<!DOCTYPE HTML>
<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-12-9
  Time: 上午10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="<%=basePath %>js/teachpaltform/tptask.js"></script>
    <script type="text/javascript">
        var taskid="${taskid}";
        var questype="${questype}";
        var tasktype="${taskInfo.tasktype}";
        var g_subjectid="${param.subjectid}";
        $(function(){
            $("input[name='classradio']").eq(0).attr("checked",true);
            <c:if test="${!empty classList}">
                cjloadStuPerformance(${classList[0].classid},${classList[0].classtype});
            </c:if>

        });
    </script>
</head>
<body>
<div class="subpage_head"><span class="back"><a href="javascript:history.go(-1)">返回</a></span><span class="ico55"></span><strong>任务统计</strong></div>
<div class="content1">
<p class="font-black">
    <c:if test="${!empty classList}">
        <c:forEach items="${classList}" var="c">
            <!-- <li><a id="a_class_${c.classid }" href="javascript:loadStuPerformance(${c.classid })">${c.classname }</a></li>-->

            <input type="radio" id="radio${c.classid}" name="classradio" onclick="cjloadStuPerformance('${c.classid }',${c.classtype})"/>${c.classname }&nbsp;&nbsp;&nbsp;&nbsp;
        </c:forEach>
    </c:if>
    <c:if test="${fn:length(classList)>1}">
        <input type="radio" name="classradio" id="radio" value="0" onclick="cjloadStuPerformance(null,null)">
        全部&nbsp;&nbsp;&nbsp;&nbsp;
    </c:if>
</p>
<p class="font-black p_t_10"><strong>完成比率：</strong><span id="finishnum"></span></p>
<p class="p_tb_10"><span class="font-black"><strong>未完成任务：</strong><span id="notcomplete" onmousemove="var dvstyle=dv_nocomplete.style;dvstyle.left=(mousePostion.x+5)+'px';dvstyle.top=(mousePostion.y+5)+'px';dvstyle.display='block'"
                                                                         onmouseout="dv_nocomplete.style.display='none';"></span>人</span>&nbsp;&nbsp;<span  id="sendMsg"></span></p>
<div id="mainTbl"></div>
<br/>
</div>
<div class="public_windows" id="dv_nocomplete" style="display: none;position: absolute;" >
    <h3></a>未完成任务人员</h3>
    <div class="jxxt_student_rw_float" id="dv_nocomplete_data">

    </div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
