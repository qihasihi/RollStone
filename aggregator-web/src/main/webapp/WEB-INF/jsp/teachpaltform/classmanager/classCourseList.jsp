<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.school.entity.teachpaltform.TpCourseInfo"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
<script type="text/javascript">
    var currtenTab=1; //当前学科导航栏页数
    var tabSize=8; //学科导航栏每页显示数量
    var teacherid="${teacherid}";
    var subjectid="${param.subjectid}";
    var gradeid="${param.gradeid}";
    var subjectname='';
    var gradename='';
    switch(parseInt(subjectid)){
        case 1:
            subjectname='语文';
            break;
        case 2:
            subjectname='数学';
            break;
        case 3:
            subjectname='英语';
            break;
        case 4:
            subjectname='物理';
            break;
        case 5:
            subjectname='化学';
            break;
        case 6:
            subjectname='历史';
            break;
        case 7:
            subjectname='生物';
            break;
        case 8:
            subjectname='地理';
            break;
        case 9:
            subjectname='政治';
            break;
    }

    switch(parseInt(gradeid)){
        case 1:
            gradename='高三';
            break;
        case 2:
            gradename='高二';
            break;
        case 3:
            gradename='高一';
            break;
        case 4:
            gradename='初三';
            break;
        case 5:
            gradename='初二';
            break;
        case 6:
            gradename='初一';
            break;
    }
    var grade_name=gradename+subjectname;
    $(function(){
        <c:if test="${currtTerm!=null}">
        $("#termid").val("${currtTerm}");
        </c:if>

        $("#termid").change(function(){
            window.location.href="teachercourse?m=toClassCourseList&classid=${tempclass.classid}&termid="+$("#termid").val();
        });

        // 导航栏调整为正确显示
        changeTab('back');
        changeTab('front');
        $("#grade_subject").html(grade_name);
    });

    function changeAvailable(courseid,available){
        $.ajax({
            url:'teachercourse!updateCourse.action',
            data:{courseid:courseid,available:available},
            type:'post',
            dataType:'json',
            error:function(){
                alert('异常错误,系统未响应！');
            },success:function(rps){
                if(rps.type=="success"){
                    var strHtml="";
                    if(available==1)
                        strHtml+="<span class='font-gray1'>开启</span>&nbsp;&nbsp;<span class='font-lightblue'><a href='javascript:changeAvailable("+courseid+",0);'>关闭</a></span>";
                    else
                        strHtml+="<span class='font-lightblue'><a href='javascript:changeAvailable("+courseid+",1);'>开启</a></span>&nbsp;&nbsp;<span class='font-gray1'>关闭</span>";
                    $("#ava_"+courseid).html(strHtml);
                }else{
                    alert("修改失败!");
                }
            }
        });
    }


    function changeTab(direct){
        tabTotal=$("#ul_class").children().length; //标签总数
        var i=0,j=0;
        if(direct=="front"){
            if(currtenTab==1){
                $("#ul_class").children(":gt("+(tabTotal-1)+")").hide();
                return;
            }else{
                currtenTab-=1;
                i=(currtenTab-1)*tabSize;
                j=currtenTab*tabSize-1;
                $("#ul_class").children().show();
                $("#ul_class").children(":lt("+i+")").hide();
                $("#ul_class").children(":gt("+j+")").hide();
                return;
            }
        }
        if(direct=="back"){
            if((currtenTab*tabSize)>=tabTotal){
                $("#ul_class").children(":gt("+(tabSize*currtenTab-1)+")").hide();
                return;
            }else{
                currtenTab+=1;
                i=(currtenTab-1)*tabSize;
                j=currtenTab*tabSize-1;
                $("#ul_class").children().show();
                $("#ul_class").children(":lt("+i+")").hide();
                $("#ul_class").children(":gt("+j+")").hide();
                return;
            }
        }
    }
    function abc(clsid,clstype){
        location.href="teachercourse?m=toClassCourseList&classid="+clsid+"&classtype="+clstype+"&pageidx="+currtenTab+"&subjectid="+subjectid+"&gradeid="+gradeid;
    }

</script>
</head>
    <body>
    <div class="subpage_head"><span class="ico19"></span><strong>班级主页--<span id="grade_subject"></span></strong></div>
    <div class="subpage_nav">
        <div class="arr"><a href="javascript:changeTab('front');"><span class="up"></span></a><a href="javascript:changeTab('back');"><span class="next"></span></a></div>
        <ul id="ul_class">
            <c:forEach var="cl" items="${classes }">
                <li class="${(classtype==1&&cl.classid==classid)?'crumb':''}"><a href="javascript:;" onclick="abc(${cl.classid},1)">${cl.classgrade}${cl.classname}</a></li>
            </c:forEach>

            <c:forEach var="vcl" items="${vclasses }">
                <li class="${(classtype==2&&vcl.virtualclassid==classid)?'crumb':''}"><a href="javascript:;" onclick="abc(${vcl.virtualclassid},2)">${vcl.virtualclassname}</a></li>
            </c:forEach>
        </ul>
    </div>

    <div class="content">
        <div class="contentR">
            <div class="jxxt_tab_layout">
                <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
                    <col class="w450"/>
                    <col class="w130"/>
                    <col class="w160"/>
                    <tr>
                        <th>专题名称</th>
                        <th>课堂表现</th>
                        <th>开始时间</th>
                    </tr>
                    <c:if test="${empty courseList}">
                        <tr>
                            <td colspan="3">没有专题数据！</td>
                        </tr>
                    </c:if>
                    <c:if test="${!empty courseList}">
                        <c:forEach var="tc" items="${courseList }" varStatus="idx">
                            <tr ${(idx.index%2==1)?'class="trbg1"':''}>
                                <td><p class="one">
                                    <c:if test="${tc.quoteid==0}">
                                        <span class="ico16" title="自建"></span>
                                    </c:if>
                                    <c:if test="${tc.quoteid!=0 and tc.cuserid==sessionScope.CURRENT_USER.userid}">
                                        <span class="ico16" title="自建"></span>
                                    </c:if>
                                    <c:if test="${tc.quoteid!=0 and tc.courselevel==1}">
                                        <span class='ico18' title='标准'></span>
                                    </c:if>
                                    <c:if test="${tc.quoteid!=0 and tc.courselevel==2}">
                                        <span class='ico17' title='共享'></span>
                                    </c:if>
                                    <c:if test="${tc.quoteid!=0 and tc.courselevel==3}">
                                        <span class='ico17' title='共享'></span>
                                    </c:if>
                                    <a href="task?toTaskList&courseid=${tc.courseid}">${tc.coursename}</a></p></td>
                                <td><a target="_blank" href="clsperformance?m=toIndex&courseid=${tc.courseid}&subjectid=${param.subjectid}&classid=${param.classid}&classtype=${param.classtype}">课堂表现</a></td>
                                <td>${tc.ctimeString}</td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </table>
            </div>
        </div>

        <div class="subpage_contentL">
            <ul>
                <li><a href="teachercourse?m=toClassStudentList&classid=${classid}&classtype=${classtype}&subjectid=${param.subjectid}&gradeid=${param.gradeid}">班级成员</a></li>
                <li><a href="group?m=toGroupManager&classid=${classid}&classtype=${classtype}&subjectid=${param.subjectid}&gradeid=${param.gradeid}">小组管理</a></li>
                <li class="crumb"><a href="teachercourse?m=toClassCourseList&classid=${classid}&classtype=${classtype}&subjectid=${param.subjectid}&gradeid=${param.gradeid}">课程表</a></li>
            </ul>
        </div>
        <div class="clear"></div>
    </div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
