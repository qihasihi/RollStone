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
				loadStuPerformance("${classList[0].classid}","${taskInfo.tasktype}");  
			</c:if>
		});
		</script>


	</head> 
	<body>
	<%@include file="/util/head.jsp" %>


    <div class="subpage_head"><span class="ico55"></span><strong>任务统计</strong></div>
    <div class="content1">
        <p class="font-black">
            <input type="radio" name="radio" id="radio" value="0">
            全部&nbsp;&nbsp;&nbsp;&nbsp;
            <c:if test="${!empty classList}">
                <c:forEach items="${classList}" var="c">
                    <!-- <li><a id="a_class_${c.classid }" href="javascript:loadStuPerformance(${c.classid })">${c.classname }</a></li>-->

                    <input type="radio" id="radio${c.classid}" name="classradio" onclick="loadStuPerformance('${c.classid }','${taskInfo.tasktype}')"/>${c.classname }
                </c:forEach>
            </c:if></p>
        <div class="jxxt_zhuanti_rw_tongji">
            <p><strong>完成比率：</strong>95%&nbsp;&nbsp;&nbsp;&nbsp;<strong>正确率：</strong>80%&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" target="_blank" class="font-darkblue">查看题目</a></p>
            <div class="right"><img src="../images/pic03_140226.jpg" width="193" height="140"></div>
            <div class="left"><strong>分项统计：</strong>
                <table id="initItemList" border="0" cellspacing="0" cellpadding="0" class="public_tab3">
                    <colgroup span="2" class="w100"></colgroup>
                    <tr>
                        <td>选项</td>
                        <td>百分比 </td>
                    </tr>
                    <tr>
                        <td>A</td>
                        <td>0%</td>
                    </tr>
                    <tr>
                        <td>B</td>
                        <td>80%</td>
                    </tr>
                    <tr>
                        <td>C</td>
                        <td>10%</td>
                    </tr>
                    <tr>
                        <td>D</td>
                        <td>10%</td>
                    </tr>
                </table>
            </div>
        </div>
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <colgroup span="4" class="w190"></colgroup>
            <colgroup class="w180"></colgroup>
            <tr>
                <th>学号</th>
                <th>姓名</th>
                <th>学习时间</th>
                <th>作答内容 </th>
                <th>是否完成</th>
            </tr>
            <tr>
                <td>123456789</td>
                <td>周靖<br></td>
                <td>2013-12-23 13:16:22</td>
                <td><span class="font-red">A</span></td>
                <td><span class="ico12" title="完成"></span></td>
            </tr>
            <tr class="trbg1">
                <td>123456789</td>
                <td>周靖<br></td>
                <td>&nbsp;</td>
                <td>B</td>
                <td><span class="ico24" title="进行中"></span></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr class="trbg1">
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
        </table>
        <br/>
    </div>
    <%@include file="/util/foot.jsp" %>
	</body>
</html>
