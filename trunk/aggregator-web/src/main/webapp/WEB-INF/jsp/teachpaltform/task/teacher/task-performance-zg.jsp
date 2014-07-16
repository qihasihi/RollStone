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
        var taskid="${taskid}";
        var questype="${questype}";
        var tasktype="${taskInfo.tasktype}";
        $(function(){
            <c:if test="${!empty classList}">
            zgloadStuPerformance(null,"${taskInfo.tasktype}");
            </c:if>
            if(${taskInfo.tasktype==1}){
               // var uri="resource?m=todetail&resid=${taskInfo.taskvalueid}";
                var uri="tpres?toTeacherIdx&courseid=${courseid}&tpresdetailid=${taskInfo.taskvalueid}&taskid=${taskInfo.taskid}"
                $("#showdetail").attr("href",uri);
            }else if(${taskInfo.tasktype==2}){
                var uri="tptopic?m=toDetailTopic&topicid=${taskInfo.taskvalueid}";
                $("#showdetail").attr("href",uri);
            }else{
                var uri="question?m=todetail&id=${taskInfo.taskvalueid}";
                $("#showdetail").attr("href",uri);
            }
        });
    </script>


</head>
<body>
<%@include file="/util/head.jsp" %>


<div class="subpage_head"><span class="ico55"></span><strong>任务统计</strong></div>
<div class="content1">
    <p class="font-black">
        <input type="radio" name="classradio" id="radio" checked="checked" value="0" onclick="zgloadStuPerformance(null,'${taskInfo.tasktype}','${taskInfo.taskvalueid}')">
        全部&nbsp;&nbsp;&nbsp;&nbsp;
        <c:if test="${!empty classList}">
            <c:forEach items="${classList}" var="c">
                <!-- <li><a id="a_class_${c.classid }" href="javascript:loadStuPerformance(${c.classid })">${c.classname }</a></li>-->

                <input type="radio" id="radio${c.classid}" name="classradio" onclick="zgloadStuPerformance('${c.classid }','${taskInfo.tasktype}','${taskInfo.taskvalueid}',${c.classtype})"/>${c.classname }
            </c:forEach>
        </c:if></p>
    <p class="font-black p_t_10"><strong>完成比率：</strong><span id="finishnum"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="" id="showdetail" target="_blank" class="font-darkblue">查看题目</a></p>
    <div id="mainTbl">

    </div>
    <br/>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
