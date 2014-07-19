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
            $("#tbl tr:even").addClass("trbg1");
        });

    </script>


</head>
<body>


<div class="subpage_head"><span class="ico55"></span><strong>微课程—试卷&mdash;查看回答</strong></div>
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
            <div class="right"><img src="images/taskMicPie.png" width="193" height="140"></div>
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
