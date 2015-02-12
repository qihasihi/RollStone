<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>

<%
    Object lession=request.getAttribute("isLession");
    boolean isLession=lession==null?false:true;

    Object banzhuren=request.getAttribute("isBanzhuren");
    boolean isBanzhuren=banzhuren==null?false:true;


%>
<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	<script type="text/javascript" src="js/teachpaltform/group.js"></script>
        <script type="text/javascript" src="js/teachpaltform/tchcourselist.js"></script>
<script type="text/javascript">

    var subjectid="${param.subjectid}";
    var gradeid="${param.gradeid}";
    var subjectname='';
    var gradename='';

    var isLession=<%=isLession%>;
    var isBanzhuren=<%=isBanzhuren%>;

    var termid="${selTerm.ref}";
    var currtterm="${currtTerm.ref}";
    var teacherid="${teacherid}";
    var global_gradeid=0;
    var global_subjectid=0;

    $(function(){
        $("#a_course").attr("href",'teachercourse?toTeacherCourseList&termid='+termid+'&subjectid='+subjectid+'&gradeid='+gradeid+'');
        $("#a_clsid").attr("href",'group?m=toGroupManager&termid='+termid+'&subjectid='+subjectid+'&gradeid='+gradeid+'');
        $("#a_calendar").attr("href",'teachercourse?m=toTeacherCalendarPage&termid='+termid+'&subjectid='+subjectid+'&gradeid='+gradeid+'');

        loadDiv(1);

        $("li[name='li_stat']").bind("click",function(){
            $(this).siblings().removeClass("crumb");
            $(this).addClass("crumb");
        });
    });



    function loadDiv(type){
        var url="";
        if(type==1){
            url="sysm?m=toAdminPerformanceTea";
        }else{
            url="sysm?m=toAdminPerformanceStu";
        }
        $("#dv_stat").load(url,function(){

        });
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
            <li ><a href="javascript:;"  id="a_calendar">课程日历</a></li>
            <%if(isSXJw){%>
                <li class="crumb"><a id="a_stat" href="javascript:;">教务统计</a></li>
            <%}%>
        </ul>
    </div>

    <div class="content2" >
        <div class="subpage_lm">
            <ul>
                <li name="li_stat" class="crumb" ><a href="javascript:loadDiv(1);" >教师统计</a></li>
                <li name="li_stat" ><a href="javascript:loadDiv(2);">学生统计</a></li>
            </ul>
        </div>

        <div class="jxxt_jxtj  public_input" id="dv_stat">
        </div>
    </div>

    <%@include file="/util/foot.jsp" %>
    </body>
</html>