<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	<script type="text/javascript" src="js/teachpaltform/group.js"></script>
<script type="text/javascript">
    var currtenTab=1; //当前学科导航栏页数
    var tabSize=6; //学科导航栏每页显示数量
    var isTrust="${isTrust}";
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
        getNoGroupStudentsByClassId();
        getClassGroups();
//        $("#stu_name").bind("input propertychange",function(){
//            filterResult();
//        });

        // 导航栏调整为正确显示
        changeTab('back');
        changeTab('front');

        <%--//已托管--%>
        <%--<c:if test="${!empty isTrust}">--%>
             <%--$("a[name='a_hide']").remove();--%>
        <%--</c:if>--%>
        $("#grade_subject").html(grade_name);
    });

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
        location.href="group?m=toGroupManager&classid="+clsid+"&classtype="+clstype+"&pageidx="+currtenTab+"&subjectid="+subjectid+"&gradeid="+gradeid;
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
            <input type="hidden" id="classId" value="${classid}"/>
            <input type="hidden" id="classType" value="${classtype}"/>
            <div class="jxxt_banji_xzgl">
                <p class="font-darkblue"><a name="a_hide" href="javascript:showModel('addGroupDiv',false);"><span class="ico26"></span>新建小组</a></p>
                <div class="jxxt_banji_xzgl_text" id="nogroup">
                    <p class="font-black">未分配成员：</p>
                    <ul id="noGroupStudents">
                    </ul>
                    <p class="p_t_10"><a name="a_hide" href="javascript:showModel('selectStudent_Div');" class="an_public3">分配到组</a></p>
                </div>
                <table id="group_list" border="0" cellpadding="0" cellspacing="0" class="public_tab2">

                </table>
            </div>
        </div>

        <div class="subpage_contentL">
            <ul>
                <li><a href="teachercourse?m=toClassStudentList&classid=${classid}&classtype=${classtype}&subjectid=${param.subjectid}&gradeid=${param.gradeid}">班级成员</a></li>
                <li class="crumb"><a href="group?m=toGroupManager&classid=${classid}&classtype=${classtype}&subjectid=${param.subjectid}&gradeid=${param.gradeid}">小组管理</a></li>
                <li><a href="teachercourse?m=toClassCourseList&classid=${classid}&classtype=${classtype}&subjectid=${param.subjectid}&gradeid=${param.gradeid}">专题列表</a></li>
            </ul>
        </div>
        <div class="clear"></div>
    </div>
    <span id="no_gs_bk" style="display:none">
    </span>
    <%@include file="/util/foot.jsp" %>
    </body>
</html>

<div id="updateGroupName" style="margin: 5px;background-color: white" class="white_content"  >
    <input id="uGroupID" type="hidden" value="" />
    输入新组名：<input id="editGN" type="text" />
    <input type="button" value="修改" onclick="updateGroup();"/>
    <input type="button" value="返回" onclick="closeModel('updateGroupName');"/>
</div>

<div id="addGroupDiv" class="public_windows public_input" style="display:none;">
    <h3><a href="javascript:closeModel('addGroupDiv');" title="关闭"></a>新建小组</h3>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 ">
        <col class="w80"/>
        <col class="w320"/>
        <tr>
            <th>小组名称：</th>
            <td><input id="groupName" name="groupName" type="text" class="w300"/></td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td><a href="javascript:addNewGroup();" class="an_public1">确&nbsp;定</a></td>
        </tr>
    </table>
</div>

<div id="changeGroupDiv" class="public_windows" style="display:none;">
    <input type="hidden" id="gs_ref" value=""/>
    <h3><a href="javascript:closeModel('changeGroupDiv');" title="关闭"></a>小组调整</h3>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
        <col class="w80"/>
        <col class="w320"/>
        <tr>
            <th>调组到：</th>
            <td><select name="changeGroups" id="changeGroups">
            </select>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td><input type="checkbox" id="isleader" name="isleader" value="1">
                指定为组长</td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td><a href="javascript:changeStuGroup();" class="an_public1">提&nbsp;交</a></td>
        </tr>
    </table>
</div>

<div id="selectStudent_Div" class="public_windows public_input" style="display:none;">
    <h3><a href="javascript:closeModel('selectStudent_Div');" title="关闭"></a>分配到组</h3>
    <div class="jxxt_float_fpdzR">
        <p class="font-black">分配到</p>
        <ul id="forGroupList"></ul>
        <p><a href="javascript:void(0);" onclick="addStudentsToGroup();" class="an_public1">确&nbsp;定</a>&nbsp;&nbsp;<a href="javascript:closeModel('selectStudent_Div');" class="an_public1">取&nbsp;消</a></p>
    </div>
    <div class="jxxt_float_fpdzL">
        <p class="font-black">未分配人员</p>
        <p><input id="stu_name" type="text" value="" class="w120"/><a href="javascript:filterResult();" class="ico57" title="查询"></a></p><br/>
        <select id="no_gs" size="10" style="height: 200px;width: 150px;overflow-y:auto;"  multiple="multiple"></select>
    </div>

</div>
