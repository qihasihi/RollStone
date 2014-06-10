<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<%
    UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
    Integer userid=user.getUserid();
%>
<html>
	<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="js/teachpaltform/virclass.js"></script>
    <script type="text/javascript">
        var userid="<%=userid%>";
        var subjectid="${subjectid}";
        var gradeid="${param.gradeid}";
    </script>
    </head>
    <body>
    <div class="subpage_head"><span class="ico28"></span><strong>班级管理</strong></div>
    <div class="content1">
        <p class="font-darkblue"><a href="javascript:void(0)" onclick="showModel('add_virtual_class_Div');"><span class="ico26"></span>新建虚拟班级</a></p>
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <col class="w350"/>
            <col class="w200"/>
            <col class="w200"/>
            <col class="w190"/>
            <tr>
                <th>班级名称</th>
                <th>班级类型</th>
                <th>学生人数</th>
                <th>操作</th>
            </tr>
            <c:forEach var="cla" items="${classes }">
                <tr>
                    <td>
                        <a href="teachercourse?m=toClassStudentList&classid=${cla.CLASS_ID }&classtype=${cla.CLASS_TYPE}" target="_blank">${cla.CLASS_NAME }</a>
                    </td>
                    <td>
                        <c:if test="${cla.CLASS_TYPE==1}">
                            教学班
                        </c:if>
                        <c:if test="${cla.CLASS_TYPE==2}">
                            虚拟班
                        </c:if>
                    </td>
                    <td>${cla.STU_NUM }</td>
                    <td>
                       <!-- <c:if test="${cla.TRUSTEESHIP_TYPE==1}">
                            <a href="javascript:void(0)"  class="ico31" title="取消托管"
                               onclick="delTrusteeShip(${cla.CLASS_ID },${cla.CLASS_TYPE});"></a>
                        </c:if>
                        <c:if test="${cla.TRUSTEESHIP_TYPE==0 and cla.CLASS_TYPE==1 }">
                            <a href="javascript:void(0)"  class="ico30" title="托管"
                               onclick="getTrusteeShipTeacher('${cla.CLASS_NAME }',${cla.CLASS_ID },${cla.CLASS_TYPE});showModel('TrusteeShipClass_Div')"></a>
                        </c:if>-->
                        <c:if test="${cla.CLASS_TYPE==2}">
                            <a href="javascript:void(0)" class="ico36" title="添加学生"
                               onclick="getVirClassStudents(${cla.CLASS_ID });
                                       showModel('class_student_manager');"></a>&nbsp;
                            <a href="javascript:void(0)" class="ico01" title="删除"
                               onclick="changeVirClassStatus(${cla.CLASS_ID },false);"></a>
                            <%--
                              <c:if test="${cla.STATUS==1}">
                            <span id="virclass_status_${cla.CLASS_ID }">
                            <a href="javascript:void(0)" class="ico01" title="禁用"
                               onclick="changeVirClassStatus(${cla.CLASS_ID },false);"></a></span>
                              </c:if>
                              <c:if test="${cla.STATUS==2}">
                            <span id="virclass_status_${cla.CLASS_ID }">
                            <a href="javascript:void(0)" class="ico02" title="启用"
                               onclick="changeVirClassStatus(${cla.CLASS_ID },true);"></a></span>
                              </c:if>
                              --%>
                        </c:if>

                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
<%@include file="/util/foot.jsp" %>
</body>
</html>

<div id="add_virtual_class_Div" class="public_windows public_input" style="display:none;">
    <h3><a href="javascript:closeModel('add_virtual_class_Div');" title="关闭"></a>新建虚拟班级</h3>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 ">
        <col class="w80"/>
        <col class="w320"/>
        <tr>
            <th>班级名称：</th>
            <td><input id="new_virtual_class_name" type="text" class="w300"/></td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td><a href="javascript:void(0);" onclick="addVirtualClass();" class="an_public1">确&nbsp;定</a></td>
        </tr>
    </table>
</div>

<div id="class_student_manager" class="public_windows public_input" style="display:none;">
    <h3><a href="javascript:closeModel('class_student_manager');" title="关闭"></a>添加虚拟班级学生</h3>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-b">
        <col class="w40"/>
        <col class="w180"/>
        <col class="w40"/>
        <col class="w180"/>
        <tr>
            <td>年级：</td>
            <td><select id="grade">
                <option value="0">—请选择—</option>
                <c:forEach var="grade" items="${grades }">
                    <option value="${grade.gradevalue }">${grade.gradename }</option>
                </c:forEach>
            </select></td>
            <td>班级：</td>
            <td><select id="class_id">
                <option value="0">—请选择—</option>
                <c:forEach var="cls" items="${classList }">
                    <option value="${cls.classid }">${cls.classgrade }${cls.classname }</option>
                </c:forEach>
            </select></td>
        </tr>
        <tr>
            <td>姓名：</td>
            <td><input id="stu_name" type="text" class="w140"/><a href="javascript:searchStudent();" class="ico57" title="查询"></a></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
    </table>
    <div class="jxxt_float_bjgl">
        <p class="font-black">
             <a href="javascript:void(0);" onclick="moveAllStudent('add');"   class="font-darkblue">全选</a>
            未添加成员：</p>
        <ul id="searchStuList">
        </ul>
    </div>
    <div class="jxxt_float_bjgl">
        <p class="font-black">
             <a  href="javascript:void(0);" onclick="moveAllStudent('del');"   class="font-darkblue">清空</a>
            已添加成员：</p>
        <ul id="checkedStuList">
        </ul>
    </div>
    <p class="t_c"><a href="javascript:void(0);" onclick="submitCheckedStu();" class="an_public1">确&nbsp;定</a>&nbsp;&nbsp;
        <a href="javascript:closeModel('class_student_manager');" class="an_public1">取&nbsp;消</a></p>
</div>

<div id="TrusteeShipClass_Div" class="public_windows" style="display:none;">
    <h3><a href="javascript:closeModel('TrusteeShipClass_Div');" title="关闭"></a>班级托管</h3>
    <input type="hidden" id="shipClassId" value="">
    <input type="hidden" id="shipClassType" value="">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
        <col class="w80"/>
        <col class="w320"/>
        <tr>
            <th>托管班级：</th>
            <td id="trusteeShipTitle"></td>
        </tr>
        <tr>
            <th>受托教师：</th>
            <td><select id="ts_grade">
                <c:forEach var="grade" items="${grades }">
                    <option value="${grade.gradevalue }">${grade.gradename }</option>
                </c:forEach></select>
                <input  id="tchname" type="text" value="" placeholder="姓名模糊查找"  class="w160"/></td>
        </tr>
        <tr>
            <td colspan="2" class="font-gray">（注意：受托教师同意后，托管才算成功）</td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <select id="ts_teacher" size="5" style="height: 100px;width: 220px;overflow-y:auto;">
            </select>
            </td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td><a href="javascript:void(0);" onclick="addTrusteeShip();" class="an_public1">确&nbsp;定</a></td>
        </tr>
    </table>
</div>