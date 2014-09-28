<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.school.entity.teachpaltform.TpCourseInfo"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
	<head>
<script type="text/javascript">
    var currtenTab=${!empty param.pageidx?param.pageidx:1}; //当前学科导航栏页数
    var tabSize=6; //学科导航栏每页显示数量
    var teacherid="${teacherid}";
    var termid="${param.termid}";
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
        case 41:
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
        if(currtenTab==1){
            changeTab('back');
            changeTab('front');
        }else{
            changeTab('back');
            changeTab('front');
            changeTab('back');
        }
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
        location.href="teachercourse?m=toClassStudentList&classid="+clsid+"&classtype="+clstype+"&pageidx="+currtenTab+"&subjectid="+subjectid+"&gradeid="+gradeid;
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
            <%--<p class="back"><a href="1" target="_blank" class="one">科代表操作记录</a></p>--%>
            <%--<div class="jxxt_banji_xsmd">--%>
                <%--<p class="font-black"><a href="1"><strong>科代表</strong><span class="ico26"></span></a>：王王子仪<a href="1" class="ico_delete" title="删除"></a>&nbsp;&nbsp;王子仪<a href="1" class="ico_delete" title="删除"></a>&nbsp;&nbsp;王子仪<a href="1" class="ico_delete" title="删除"></a></p>--%>
                <%--<p>（科代表权限： 1. 删除恶意资源评论   2. 删除不符合要求的主帖  3. 删除主帖中的恶意评论  4. 主帖加精、置顶）</p>--%>
            <%--</div>--%>
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
                <colgroup span="3" class="w240"></colgroup>
                <tr>
                    <th>学号</th>
                    <th>姓名</th>
                    <th>任务完成率</th>
                </tr>

                <c:if test="${classtype==1}">
                    <c:forEach var="stu" items="${cuList }" varStatus="idx">
                        <tr ${(idx.index%2==1)?'class="trbg1"':''}>
                            <td>${stu.userinfo.stuno }</td>
                            <td>${stu.userinfo.realname }</td>
                            <td><a href="javascript:void(0);" class="font-blue" >${stu.completenum}%</a></td>
                        </tr>
                    </c:forEach>
                </c:if>

                <c:if test="${classtype==2}">
                    <c:forEach var="stu" items="${cuList }" varStatus="idx">
                        <tr ${(idx.index%2==1)?'class="trbg1"':''}>
                            <td>${stu.stuno }</td>
                            <td><a href="" id="showPerformance2">${stu.stuname }</a></td>
                            <td><a href="javascript:void(0);" class="font-blue" >${stu.completenum}%</a></td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
        </div>

        <div class="subpage_contentL">
            <ul>
                <li class="crumb"><a href="teachercourse?m=toClassStudentList&classid=${classid}&classtype=${classtype}&subjectid=${param.subjectid}&gradeid=${param.gradeid}">班级成员</a></li>
                <li><a href="group?m=toGroupManager&classid=${classid}&classtype=${classtype}&subjectid=${param.subjectid}&gradeid=${param.gradeid}">小组管理</a></li>
                <li><a href="teachercourse?m=toClassCourseList&classid=${classid}&classtype=${classtype}&subjectid=${param.subjectid}&gradeid=${param.gradeid}">课程表</a></li>
            </ul>
        </div>
        <div class="clear"></div>
    </div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
