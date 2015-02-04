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
        var courseid="${courseid}";
        var paperid="${paperid}";
        var questype="${questype}";
        var tasktype="${taskInfo.tasktype}";
        var orderstr="";
        var allQuesId="${allquesId}";
        var currentQuesId="${currentQuesId}";
        var currentIdx=-1;

        $(function(){
            $("#tbl tr:even").addClass("trbg1");

            if(allQuesId.length>0){
                var quesArray=allQuesId.split(",");
                $.each(quesArray,function(ix,im){
                    if(currentQuesId==im)
                       currentIdx=ix;
                });

                if(currentIdx==0)
                    $("#upquestion").remove();
                else if(currentIdx==quesArray.length-1)
                    $("#nextquestion").remove();
            }
        });

        /**
         * 1上一题
         * 2下一题
         * @param type
         */
        function tabQuestion(type){
            var quesArray=allQuesId.split(",");
            if(currentIdx==-1||quesArray.length<1)
                return;
            var id;
            if(type==1){
                id=quesArray[currentIdx-1];
            }else{
                id=quesArray[currentIdx+1];
            }
            location.href='task?loadStuMicQuesPerformance&courseid='+courseid+'&taskid='+taskid+'&questionid='+id+'&type=1&paperid='+paperid+'';
        }

    </script>


</head>
<body>


<div class="subpage_head"><span class="ico55"></span><strong>微课程—试卷&mdash;查看回答</strong>
    <p style="float: right;">
        <a  id="upquestion" href="javascript:;" onclick="tabQuestion(1)" class="an_public3">上一题</a>
        &nbsp;&nbsp;
        <a  href="javascript:;" onclick="tabQuestion(2)"  id="nextquestion" class="an_public3">下一题</a>
    </p>
</div>
<div class="content1">
    <c:if test="${!empty optionList and !empty logList}">

        <div class="jxxt_zhuanti_rw_tongji">
            <p><strong>正确率：</strong>
                <script type="text/javascript">
                    var right="${right}";
                    var total="${fn:length(logList)}";
                    var h=parseInt(right) / parseInt(total) *100;
                    document.write(h.toFixed(2)+"%")
                </script>
            </p>
            <div class="right"><img src="images/imgCache/tkP${taskid}${currentQuesId}.png" width="193" height="140"></div>
            <div class="left"><strong>分项统计：</strong>
                <table border="0" cellspacing="0" cellpadding="0" class="public_tab3">
                    <colgroup span="2" class="w100">
                    </colgroup>
                    <tr>
                        <td>选项</td>
                        <td>百分比 </td>
                    </tr>
                    <c:forEach items="${optionList}" var="o">
                        <tr>
                            <td>${o.OPTION_TYPE}</td>
                            <td>${o.NUM}%</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </c:if>

    <table id="tbl" border="0" cellpadding="0" cellspacing="0" class="public_tab2">
        <colgroup class="w220"></colgroup>
        <colgroup span="3" class="w240"></colgroup>
        <tr>
            <th>学号</th>
            <th>姓名</th>
            <th>作答内容 </th>
            <th>是否正确</th>
        </tr>
        <c:if test="${empty logList}">
            <tr>
                <td colspan="4">暂无数据!</td>
            </tr>
        </c:if>

        <c:if test="${!empty logList}">
            <c:forEach items="${logList}" var="l" varStatus="idx">
                <tr>
                    <td>${l.stuno}</td>
                    <td>${l.stuname}<br></td>
                    <td><p>${fn:replace(l.answer,'%7C',',')}</p></td>
                    <td>
                        <c:if test="${l.isright==1}">
                            √
                        </c:if>
                        <c:if test="${l.isright ne 1}">
                            ×
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
    <br>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
