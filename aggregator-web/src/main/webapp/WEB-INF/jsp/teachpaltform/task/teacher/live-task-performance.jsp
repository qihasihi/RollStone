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
        var orderstr="";
        $(function(){
            $("input[name='classradio']").eq(0).attr("checked",true);

            <c:if test="${!empty classList}">
                loadLiveLessionPerformance(${classList[0].classid},"${taskInfo.tasktype}","${taskInfo.taskvalueid}",${classList[0].classtype});
            </c:if>
        });

        var cssObj=1;
        function DataSort(orderby,obj){
            cssObj=cssObj==0?1:0;
            var cls=$(obj).attr("class");
            if(cls=='ico48a'){
                orderstr=orderby;
            }else{
                orderstr=orderby+' desc';
            }
            var clsid=typeof $("input[name='classradio']:checked").val()=='undefined'?0:$("input[name='classradio']:checked").val().split('|')[0];

            var clstype=typeof $("input[name='classradio']:checked").val()=='undefined'?0:$("input[name='classradio']:checked").val().split('|')[1];

            loadLiveLessionPerformance(clsid,"${taskInfo.tasktype}","",clstype);
        }
    </script>


</head>
<body>
<%@include file="/util/head.jsp" %>


<div class="subpage_head"><span class="ico55"></span><strong>任务统计</strong></div>
<div class="content1">
    <p class="font-black">

        <c:if test="${!empty classList}">
            <c:forEach items="${classList}" var="c">
                <input type="radio" id="radio${c.classid}" value="${c.classid}|${c.classtype}"  name="classradio" onclick="loadLiveLessionPerformance('${c.classid }','${taskInfo.tasktype}','${taskInfo.taskvalueid}',${c.classtype})"/>${c.classname }
            </c:forEach>
        </c:if>&nbsp;&nbsp;&nbsp;&nbsp;
        <c:if test="${fn:length(classList)>1}">
            <input type="radio" name="classradio" id="radio"  value="0" onclick="loadLiveLessionPerformance(null,'${taskInfo.tasktype}','${taskInfo.taskvalueid}')">
            全部
        </c:if>
    </p>
    <p class="font-black p_t_10"><strong>完成比率：</strong><span id="finishnum"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!--a href="" id="showdetail" target="_blank" class="font-darkblue">查看题目</a--></p>
    <p class="p_tb_10"><span class="font-black"><strong>未完成任务：</strong><span id="notcomplete" onmousemove="var dvstyle=dv_nocomplete.style;dvstyle.left=(mousePostion.x+5)+'px';dvstyle.top=(mousePostion.y+5)+'px';dvstyle.display='block'"
                                                                             onmouseout="dv_nocomplete.style.display='none';"></span>人</span>&nbsp;&nbsp;<span  id="sendMsg"></span></p>



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
