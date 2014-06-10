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
        location.href="teachercourse?m=toClassStudentList&classid="+clsid+"&classtype="+clstype+"&pageidx="+currtenTab;
    }


</script>
</head>
    <body>
    <div class="subpage_head"><span class="ico19"></span><strong>班级主页</strong></div>
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
                <li class="crumb"><a href="teachercourse?m=toClassStudentList&classid=${classid}&classtype=${classtype}">学生名单</a></li>
                <li><a href="group?m=toGroupManager&classid=${classid}&classtype=${classtype}">小组管理</a></li>
                <li><a href="teachercourse?m=toClassCourseList&classid=${classid}&classtype=${classtype}">专题列表</a></li>
            </ul>
        </div>
        <div class="clear"></div>
    </div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
