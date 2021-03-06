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
        var g_subjectid="${param.subjectid}";
        $(function(){
            $("input[name='classradio']").eq(0).attr("checked",true);
            <c:if test="${!empty classList}">
                xzloadStuPerformance(${classList[0].classid},"${taskInfo.tasktype}","${taskInfo.taskvalueid}",${classList[0].classtype});
            </c:if>
            if(${taskInfo.tasktype==3}){
                var uri="question?m=todetail&id=${taskInfo.taskvalueid}&courseid=${courseid}";
                $("#showdetail").attr("href",uri);
            }
        });
    </script>


</head>
<body>


<div class="subpage_head">
    <span class="back"><a  href="javascript:history.go(-1)">返回</a></span>
    <span class="ico55"></span><strong>任务统计</strong></div>
<div class="content1">
    <p class="font-black">
        <c:if test="${!empty classList}">
            <c:forEach items="${classList}" var="c">
                <!-- <li><a id="a_class_${c.classid }" href="javascript:loadStuPerformance(${c.classid })">${c.classname }</a></li>-->

                <input type="radio" id="radio${c.classid}" name="classradio" onclick="xzloadStuPerformance('${c.classid }','${taskInfo.tasktype}','${taskInfo.taskvalueid}',${c.classtype})"/>${c.classname }&nbsp;&nbsp;&nbsp;&nbsp;
            </c:forEach>
        </c:if>
    <c:if test="${fn:length(classList)>1}">
        <input type="radio" name="classradio" id="radio"  value="0" onclick="xzloadStuPerformance(null,'${taskInfo.tasktype}','${taskInfo.taskvalueid}')">
        全部&nbsp;&nbsp;&nbsp;&nbsp;
    </c:if>
    </p>
    <div class="jxxt_zhuanti_rw_tongji">
        <p><strong>完成比率：</strong><span id="finishnum"></span>&nbsp;&nbsp;&nbsp;&nbsp;<strong>正确率：</strong><span id="rightnum"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="" id="showdetail" target="_blank" class="font-darkblue"></a></p>
        <p class="p_tb_10"><span class="font-black"><strong>未完成任务：</strong><span id="notcomplete" onmousemove="var dvstyle=dv_nocomplete.style;dvstyle.left=(mousePostion.x+5)+'px';dvstyle.top=(mousePostion.y+5)+'px';dvstyle.display='block'"
                                                                                 onmouseout="dv_nocomplete.style.display='none';"></span>人</span>&nbsp;&nbsp;<span  id="sendMsg"></span></p>
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
<div class="public_windows" id="dv_nocomplete" style="display: none;position: absolute;" >
    <h3></a>未完成任务人员</h3>
    <div class="jxxt_student_rw_float" id="dv_nocomplete_data">

    </div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
