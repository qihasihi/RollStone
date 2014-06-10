<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
        <script type="text/javascript" src="js/teachpaltform/stucourselist.js"></script>
<script type="text/javascript">
$(function(){
	$("#classes").change(function(){
        window.location.href="teachercourse?m=toStudeClassList&classid="+$("#classes").val()
                +"&classtype="+$("#classes").find("option:selected").attr("classtype");
	});
});

function tabCutover(n){
    $("#tabs").children().attr("class","");
    $("#content").children().hide();
    if(n==1){
        $("#li_tab_cla").attr("class","crumb");
        $("#classstu").show();
    }else{
        $("#li_tab_grp").attr("class","crumb");
        $("#groupstu").show();
    }
}
</script>
</head>
    <body>
    <div class="subpage_head"><span class="ico19"></span><strong>我的班级</strong>&nbsp;&nbsp;&nbsp;&nbsp;
        <select name="classes" id="classes">
            <c:forEach var="cu" items="${cuList }">
                <option value="${cu.classinfo.classid }" classtype="1" ${classtype==1&&cu.classinfo.classid==classid?"selected":"" }>${cu.classinfo.classname }</option>
            </c:forEach>
            <c:forEach var="vs" items="${vsList }">
                <option value="${vs.virtualclassid }" classtype="2" ${classtype==2&&vs.virtualclassid==classid?"selected":"" }>${vs.virtualclassname }</option>
            </c:forEach>
        </select>
    </div>

    <div class="subpage_nav">
        <ul id="tabs">
            <li id="li_tab_cla" class="crumb"><a href="javascript:tabCutover(1)">班级成员</a></li>
            <li id="li_tab_grp"><a href="javascript:tabCutover(2)">小组</a></li>
        </ul>
    </div>

    <div id="content" class="content1">
        <div id="classstu">
        <%--
        <p class="font-black"><strong>科代表</strong>：----&nbsp;&nbsp;----&nbsp;&nbsp;----</p>
        <p>（科代表权限： 1. 删除恶意资源评论   2. 删除不符合要求的主帖  3. 删除主帖中的恶意评论  4. 主帖加精、置顶）</p>
        --%>

        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <colgroup span="3" class="w310"></colgroup>
            <tr>
                <th>学号</th>
                <th>姓名</th>
                <th>任务完成率</th>
            </tr>
            <c:if test="${classtype==1}">
            <c:forEach var="stu" items="${students }" varStatus="idx">
            <tr ${(idx.index%2==1)?'class="trbg1"':''}>
                <td>${stu.stuno}</td>
                <td>${stu.realname}</td>
                <td><a href="javascript:void(0);" class="font-blue">${stu.completenum}%</a></td>
            <tr>
            </c:forEach>
            </c:if>

            <c:if test="${classtype==2}">
            <c:forEach var="stu" items="${students }" varStatus="idx">
            <tr ${(idx.index%2==1)?'class="trbg1"':''}>
                <td>${stu.stuno}</td>
                <td>${stu.stuname}</td>
                <td><a href="javascript:void(0);" class="font-blue">${stu.completenum}%</a></td>
            <tr>
            </c:forEach>
            </c:if>
        </table>
        </div>
        <div id="groupstu" style="display:none;">
            <c:if test="${empty gsList}">
                <p class="font-black"><strong>我的小组</strong>：没有找到小组</p>
            </c:if>
    <c:if test="${!empty gsList}">
        <c:forEach var="gs" items="${gsList }"  varStatus="idx">
        <p class="font-black"><strong>我的小组</strong>：${(empty gs.groupname)?"没有找到小组":gs.groupname }</p>
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <colgroup span="3" class="w310"></colgroup>
            <tr>
                <th>学号</th>
                <th>姓名</th>
                <th>任务完成率</th>
            </tr>
            <c:if test="${!empty gs.tpgroupstudent}">
                <c:forEach var="cgs" items="${gs.tpgroupstudent }"  varStatus="ix">
                        <tr ${(ix.index%2==1)?'class="trbg1"':''}>
                            <td>${cgs.stuno}</td>
                            <td>${cgs.stuname}</td>
                            <td><a href="javascript:void(0);" class="font-blue">${cgs.completenum}%</a></td>
                        <tr>
                </c:forEach>
            </c:if>
        </table>
            </c:forEach>
        </c:if>
        </div>
    </div>
<%@include file="/util/foot.jsp" %>
</body>
</html>  
