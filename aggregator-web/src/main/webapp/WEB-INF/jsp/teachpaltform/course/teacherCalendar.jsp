<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>

<%
    Object lession=request.getAttribute("isLession");
    boolean isLession=lession==null?false:true;
%>
<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	<script type="text/javascript" src="js/teachpaltform/group.js"></script>
        <script type="text/javascript" src="js/teachpaltform/tchcourselist.js"></script>
        <script type="text/javascript" src="js/teachpaltform/course_calendar.js"></script>
<script type="text/javascript">

    var subjectid="${param.subjectid}";
    var gradeid="${param.gradeid}";
    var subjectname='';
    var gradename='';

    var isLession=<%=isLession%>;

    var termid="${selTerm.ref}";
    var currtterm="${currtTerm.ref}";
    var teacherid="${teacherid}";
    var global_gradeid=0;
    var global_subjectid=0;

    $(function(){
        $("#a_course").attr("href",'teachercourse?toTeacherCourseList&termid='+termid+'&subjectid='+subjectid+'&gradeid='+gradeid+'');
        $("#a_clsid").attr("href",'group?m=toGroupManager&termid='+termid+'&subjectid='+subjectid+'&gradeid='+gradeid+'');
    });




    $(function(){

        var selYear = $("#year").html().Trim();
        var selMonth = $("#month").html().Trim();
        //显示当前月日历
        showCalendar(selYear,selMonth);
        //获取日历对应数据

    });




    function prev(){
        var year=$("#year").html().Trim();
        var month=$("#month").html().Trim();
        month=parseInt(month)-1;
        if(month<1){
            year=parseInt(year)-1;
            month=12;
        }

        showCalendar(year,month);
    }

    function next(){
        var year=$("#year").html().Trim();
        var month=$("#month").html().Trim();
        month=parseInt(month)+1;
        if(month>12){
            year=parseInt(year)+1;
            month=1;
        }
        showCalendar(year,month);
    }






</script>
</head>
    <body>
    <%@include file="/util/head.jsp" %>
    <%@include file="/util/nav-base.jsp" %>


    <div id="nav">
        <ul>
            <%if(isLession){%>
               <li><a href="javascript:;" id="a_course">教学组织</a></li>
            <%}%>
            <li ><a href="javascript:;" id="a_clsid" >班级管理</a></li>
            <li class="crumb"><a href="javascript:;"  id="a_calendar">课程日历</a></li>
        </ul>
    </div>



    <div class="content1">
        <div class="jxxt_rili_layout">
            <div class="jxxt_rili">
               <!-- <select style="display: none;" id="selYear" name="year" onchange="changeCalendar()"></select>
                <select style="display: none;" id="selMonth" name="month" onchange="changeCalendar()"></select> -->
                <h1>
                    <a href="javascript:;" onclick="prev()" class="left"></a>
                    <span id="year">
                        ${fn:split(selTerm.byear,'-')[0]}
                    </span>
                    年
                    <span id="month">
                        ${fn:split(selTerm.byear,'-')[1]}
                    </span>
                    月
                    <a href="javascript:;" onclick="next()" class="right"></a></h1>
                <table border="0" cellspacing="0" cellpadding="0">
                  <!--  <tr>
                        <td><a href="1">1</a></td>
                        <td><a href="1">2</a></td>
                        <td><a href="1">3</a></td>
                        <td><a href="1">4</a></td>
                        <td><a href="1">5</a></td>
                        <td><a href="1">6</a></td>
                        <td><a href="1">7</a></td>
                    </tr>
                    <tr>
                        <td><a href="1">8</a></td>
                        <td class="border"><a href="1">9<span class="ico89b"></span></a></td>
                        <td class="crumb"><a href="1">10<span class="ico89a"></span></a></td>
                        <td><a href="1">11<span class="ico89a"></span></a></td>
                        <td><a href="1">12</a></td>
                        <td><a href="1">13</a></td>
                        <td><a href="1">14</a></td>
                    </tr>
                    <tr>
                        <td><a href="1">15</a></td>
                        <td><a href="1">16</a></td>
                        <td><a href="1">17</a></td>
                        <td><a href="1">18</a></td>
                        <td><a href="1">19</a></td>
                        <td><a href="1">20</a></td>
                        <td><a href="1">21</a></td>
                    </tr>
                    <tr>
                        <td><a href="1">22</a></td>
                        <td><a href="1">23</a></td>
                        <td><a href="1">24</a></td>
                        <td><a href="1">25</a></td>
                        <td><a href="1">26</a></td>
                        <td><a href="1">27</a></td>
                        <td><a href="1">28</a></td>
                    </tr>
                    <tr>
                        <td><a href="1">29</a></td>
                        <td><a href="1">30</a></td>
                        <td><a href="1">31</a></td>
                        <td><a href="1"></a></td>
                        <td><a href="1"></a></td>
                        <td><a href="1"></a></td>
                        <td><a href="1"></a></td>
                    </tr> -->
                    <tbody id="calendar"></tbody>
                </table>
                <p class="bg"></p>
            </div>
            <ul id="ul_lession">
                <li>我的课表</li>
              <!--  <li><a href="1" target="_blank">高二（1）班</a>&nbsp;&nbsp;<a href="1" target="_blank">三角函数的基础应用总共最长38个汉字</a><a class="ico33"></a><br>
                    10：15&mdash;&mdash;11：50</li>
                <li><a href="1" target="_blank">高二提高班</a>&nbsp;&nbsp;<a href="1" target="_blank">三角函数的基础应用</a><a class="ico33"></a><br>
                    10：15&mdash;&mdash;11：50</li>
                <li><a href="1" target="_blank">好好学习天天向上一起进步上升班</a>&nbsp;&nbsp;<a href="1" target="_blank">三角基础应用角函数基础应三角函数的基础应好长...</a><a class="ico33"></a><br>
                    10：15&mdash;&mdash;11：50</li>
                <li><a href="1" target="_blank">高二（1）班</a>&nbsp;&nbsp;<a href="1" target="_blank">三角函数的基础应用</a><a class="ico33"></a><br>
                    10：15&mdash;&mdash;11：50</li> -->
            </ul>
        </div>
    </div>


    <%@include file="/util/foot.jsp" %>
    </body>
</html>