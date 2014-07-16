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
        $(function(){
            <c:if test="${!empty classList}">
            xzloadStuPerformance(null,"${taskInfo.tasktype}","${taskInfo.taskvalueid}");
            </c:if>
            if(${taskInfo.tasktype==3}){
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
        <input type="radio" name="classradio" id="radio" checked="checked" value="0" onclick="xzloadStuPerformance(null,'${taskInfo.tasktype}','${taskInfo.taskvalueid}')">
        全部&nbsp;&nbsp;&nbsp;&nbsp;
        <c:if test="${!empty classList}">
            <c:forEach items="${classList}" var="c">
                <!-- <li><a id="a_class_${c.classid }" href="javascript:loadStuPerformance(${c.classid })">${c.classname }</a></li>-->

                <input type="radio" id="radio${c.classid}" name="classradio" onclick="xzloadStuPerformance('${c.classid }','${taskInfo.tasktype}','${taskInfo.taskvalueid}',${c.classtype})"/>${c.classname }
            </c:forEach>
        </c:if></p>
    <div class="jxxt_zhuanti_rw_tongji">
        <p><strong>完成比率：</strong><span id="finishnum"></span>&nbsp;&nbsp;&nbsp;&nbsp;<strong>正确率：</strong><span id="rightnum"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="" id="showdetail" target="_blank" class="font-darkblue">查看题目</a></p>
        <div id="piediv" class="right"></div>
        <div class="left"><strong>分项统计：</strong>
            <table id="optionList" border="0" cellspacing="0" cellpadding="0" class="public_tab3">
                <colgroup span="2" class="w100"></colgroup>
                <tbody id="optionTbl"></tbody>

            </table>
        </div>
    </div>
    <div id="mainTbl">

    </div>

    <br/>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
