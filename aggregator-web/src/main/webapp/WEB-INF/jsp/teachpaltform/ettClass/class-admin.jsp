<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>

<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>
    <script type="text/javascript" src="<%=basePath %>js/ett/ettcls.js"></script>
    <script type="text/javascript">
        var schoolid="${dcschoolid}";
        $(function(){

            loadCls(1);
            changeClsType('dv_add');
            changeClsType('dv_edit');
        });

        function changeClsType(dvObj){
            var clstype=$("#"+dvObj+" select[id='type']").val();
            if(clstype==2&&schoolid=="1")
                $("#"+dvObj+" tr[id='tr_invite']").show();
            else
                $("#"+dvObj+" tr[id='tr_invite']").hide();
        }
    </script>


</head>
<body>
<div class="subpage_head"><span class="ico28"></span><strong>班级管理</strong></div>
<div class="content3">
    <div class="jxxt_banji_jiaowuT public_input">
        <a href="javascript:void(0);" onclick="showModel('dv_add');" class="font-darkblue font_strong"><span class="ico26"></span>添加班级</a>
        &nbsp;
        <select id="sel_year" onchange="loadCls(1)">
            <option value="">请选择学年</option>
            <c:if test="${!empty classYearList}">
                <c:forEach items="${classYearList}" var="c">
                    <option value="${c.classyearvalue}">${c.classyearname}</option>
                </c:forEach>
            </c:if>
        </select>

        <select id="sel_grade" onchange="loadCls(1)">
            <option value="">请选择年级</option>
            <c:if test="${!empty gradeList}" >
                <c:forEach items="${gradeList}" var="c">
                    <option value="${c.gradevalue}">${c.gradename}</option>
                </c:forEach>
            </c:if>
        </select>

        <select id="sel_type" onchange="loadCls(1)">
            <option value="">请选择班级类型</option>
            <c:if test="${!empty classType}">
                <c:forEach items="${classType}" var="c">
                    <option value="${c.dictionaryvalue}">${c.dictionaryname}</option>
                </c:forEach>
            </c:if>
        </select>

    </div>

    <div class="jxxt_banji_jiaowuR font-black" id="dv_detail">
        <div class="jxxt_banji_info">
            <div class="pic"><img id="bzr_img" src="" width="135" height="135"></div>
            <p class="t_r" id="p_edit"></p>
            <p><strong id="s_clsname">2014年网下指导教师培训初一普通班</strong></p>
            <p id="s_bzr"><b i>班主任：欧阳小诗</b>班级类型：网校班</p>
        </div>

        <div class="jxxt_banji_list">
            <p>任课教师（<span id="tea_count">0</span>）&nbsp;&nbsp;&nbsp;&nbsp;<a id="a_addTeacher" href="javascript:;"  class="font-darkblue"><span class="ico26"></span>添加教师</a></p>
            <ul id="ul_tea">
                <!--<li><img src="../images/pic01_140226.jpg" width="80" height="80">王小红<b>语文</b><p class="ico"><a class="ico34" title="移出"></a></p></li>-->
            </ul>

            <p class="p_t_5">本班学员（<span id="stu_count">0</span>）&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" id="a_addStudent" class="font-darkblue"><span class="ico26"></span>学员管理</a></p>
            <ul id="ul_stu">

              <!--  <li><img src="../images/pic05_140226.jpg" width="80" height="80">李春天
                    <p class="ico"><a class="ico34" title="移出"></a></p></li> -->

            </ul>
        </div>

    </div>
    <div class="subpage_contentL">
        <ul id="ul_class">
           <!-- <li class="crumb"><a href="1">2014年网下指导教师培训初一普通班...</a></li>
            <li><a href="1">2014年网下指导教师培训初三普通班</a></li> -->
        </ul>
        <div class="nextpage" id="dv_page"><span><b class="before"></b></span>&nbsp;1/1&nbsp;<span><a href="1"><b class="after"></b></a></span></div>
    </div>
    <div class="clear"></div>
</div>
<%@include file="/util/foot.jsp" %>
</body>


<div class="public_windows" id="dv_add_teacher" style="display: none;">
    <h3><a href="javascript:closeModel('dv_add_teacher')" title="关闭"></a>添加教师</h3>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
        <col class="w50"/>
        <col class="w170"/>
        <col class="w50"/>
        <col class="w170"/>
        <tr>
            <th>班级：</th>
            <td colspan="3" id="add_teacher_clsname">2014年网下指导教师培训初一普通班</td>
        </tr>
        <tr>
            <th>学科：</th>
            <td>
                <select id="add_subject" class="w120">
                <option value="">选择学科</option>
                <c:if test="${!empty subjectList}">
                    <c:forEach items="${subjectList}" var="c">
                        <option value="${c.subjectid}">${c.subjectname}</option>
                    </c:forEach>
                </c:if>
                </select>
            </td>
            <th>教师：</th>
            <td><select id="teacherList"  class="w120"></select></td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td colspan="3"><a href="javascript:;" id="a_sub_teacher"  class="an_public1">提&nbsp;交</a></td>
        </tr>
    </table>
</div>


<div class="public_windows public_input" id="dv_add_student" style="display: none;">
    <h3><a href="javascript:closeModel('dv_add_student')"  title="关闭"></a>学员管理</h3>
    <p class="p_tb_10">&nbsp;&nbsp;&nbsp;姓名：<input name="textfield5" id="txt_stuName" type="text" class="w140"/><a href="javascript:;" id="a_stuName" class="ico57" title="查询"></a></p>
    <div class="jxxt_float_bjgl">
        <p class="font-black"><a href="javascript:;" onclick="setAllStudent()" class="font-darkblue">全选</a>未添加成员：</p>
        <ul id="ul_wx_stu">
            <!--<li><span class="ico85a"></span>王小小</li>-->
        </ul>
    </div>
    <div class="jxxt_float_bjgl">
        <p class="font-black"><a href="javascript:;" onclick="reSetStudent()"  class="font-darkblue">清空</a>已添加成员：</p>
        <ul id="ul_cls_stu">
            <!--<li><span class="ico85b"></span>王小小</li>-->
        </ul>
    </div>
    <p class="t_c"><a href="javascript:;" id="a_sub_student" class="an_public1">确&nbsp;定</a>&nbsp;&nbsp;<a href="javascript:closeModel('dv_add_student')"  class="an_public1">取&nbsp;消</a></p>
</div>



<div class="public_windows" id="dv_add" style="display: none;">
    <h3><a href="javascript:closeModel('dv_add')"  title="关闭"></a>新建班级</h3>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
        <col class="w110"/>
        <col class="w130"/>
        <col class="w100"/>
        <col class="w120"/>
        <tr>
            <th><span class="ico06"></span>班级名称：</th>
            <td colspan="3"><input name="textfield" id="cls_name" type="text" class="w320" /></td>
        </tr>
        <tr>
            <th><span class="ico06"></span>班级类型：</th>
            <td><select id="type"  class="w100" onchange="changeClsType('dv_add')">
                <option value="">选择班级类型</option>
                <c:if test="${!empty classType}">
                    <c:forEach items="${classType}" var="c">
                        <option value="${c.dictionaryvalue}">${c.dictionaryname}</option>
                    </c:forEach>
                </c:if>
            </select>
            </td>
            <th><span class="ico06"></span>班&nbsp;主&nbsp;任：</th>
            <td><select id="bzr" class="w100">
                <option value="">选择班主任</option>
                <c:if test="${!empty bzrList}">
                    <c:forEach items="${bzrList}" var="c">
                        <option value="${c.ettuserid}">${c.realname}</option>
                    </c:forEach>
                </c:if>
            </select></td>
        </tr>
        <tr>
            <th><span class="ico06"></span>学&nbsp;&nbsp;&nbsp;&nbsp;年：</th>
            <td>
                <select id="year" class="w100">
                    <option value="">选择学年 </option>
                    <c:if test="${!empty classYearList}">
                        <c:forEach items="${classYearList}" var="c">
                            <option value="${c.classyearvalue}">${c.classyearname}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </td>
            <th><span class="ico06"></span>年&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
            <td>
                <select id="grade" class="w100">
                    <option value="">选择年级</option>
                    <c:if test="${!empty gradeList}">
                        <c:forEach items="${gradeList}" var="c">
                            <option value="${c.gradevalue}">${c.gradename}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </td>

        </tr>
        <tr>
            <th>&nbsp;&nbsp;失效日期：</th>
            <td colspan="">
                <input placeholder="班级失效时间" class="w120"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="verify_time"  name="verify_time" type="text" />
            </td>
            <th>&nbsp;&nbsp;班级限额：</th>
            <td><input  id="num" type="text" class="w100" /></td>
        </tr>
        <c:if test="${!empty dcschoolid and dcschoolid eq 1}">
            <tr id="tr_invite" style="display: none;">
                <td colspan="4">&nbsp;&nbsp;公共家长邀请码：<input readonly="readonly" class="w120" id="invite_code"  type="text" /> &nbsp;&nbsp;<a id="btn_invitecode" href="javascript:genderInviteCode('dv_add');" style="color: #0000ff">生成邀请码</a>
                </td>
            </tr>
        </c:if>

        <!--<tr>
            <th>允许学生加入：</th>
            <td colspan="3"><input  type="radio" name="rdo" checked="checked" value="1"> 是&nbsp;&nbsp;&nbsp;&nbsp;<input  type="radio" name="rdo" value="0"> 否</td>
        </tr>-->
        <tr>
            <th>&nbsp;</th>
            <td colspan="3" ><a href="javascript:sub_cls();"  class="an_public1">确&nbsp;认</a></td>
        </tr>
    </table>
</div>

<div class="public_windows" id="dv_edit" style="display: none;">
    <h3><a href="javascript:closeModel('dv_edit')"  title="关闭"></a>修改班级</h3>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
        <col class="w110"/>
        <col class="w130"/>
        <col class="w100"/>
        <col class="w120"/>
        <tr>
            <th><span class="ico06"></span>班级名称：</th>
            <td colspan="3"><input name="textfield" id="cls_name" type="text" class="w320" /></td>
        </tr>
        <tr>
            <th><span class="ico06"></span>班级类型：</th>
            <td><select id="type"  class="w100" onchange="changeClsType('dv_edit')">
                <option value="">选择班级类型</option>
                <c:if test="${!empty classType}">
                    <c:forEach items="${classType}" var="c">
                        <option value="${c.dictionaryvalue}">${c.dictionaryname}</option>
                    </c:forEach>
                </c:if>
            </select>
            </td>
            <th><span class="ico06"></span>班&nbsp;主&nbsp;任：</th>
            <td><select id="bzr" class="w100">
                <option value="">选择班主任</option>
                <c:if test="${!empty bzrList}">
                    <c:forEach items="${bzrList}" var="c">
                        <option value="${c.ettuserid}">${c.realname}</option>
                    </c:forEach>
                </c:if>
            </select></td>
        </tr>
        <tr>
            <th><span class="ico06"></span>学&nbsp;&nbsp;&nbsp;&nbsp;年：</th>
            <td>
                <select id="year" class="w100">
                    <option value="">选择学年 </option>
                    <c:if test="${!empty classYearList}">
                        <c:forEach items="${classYearList}" var="c">
                            <option value="${c.classyearvalue}">${c.classyearname}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </td>
            <th><span class="ico06"></span>年&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
            <td>
                <select id="grade" class="w100">
                    <c:if test="${!empty gradeList}">
                        <c:forEach items="${gradeList}" var="c">
                            <option value="${c.gradevalue}">${c.gradename}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </td>

        </tr>
        <tr>
            <th>&nbsp;&nbsp;失效日期：</th>
            <td colspan="">
                <input placeholder="班级失效时间" class="w120"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="verify_time"  name="verify_time" type="text" />
            </td>
            <th>&nbsp;&nbsp;班级限额：</th>
            <td><input  id="num" type="text" class="w100" /></td>
        </tr>
       <!-- <tr>
            <th>允许学生加入：</th>
            <td colspan="3"><input  type="radio" name="rdo"  value="1"> 是&nbsp;&nbsp;&nbsp;&nbsp;<input  type="radio" name="rdo" value="0"> 否</td>
        </tr> -->

        <c:if test="${!empty dcschoolid and dcschoolid eq 1}">
            <tr id="tr_invite" style="display: none;">
                <td colspan="4">&nbsp;&nbsp;公共家长邀请码：<input readonly="readonly" class="w120" id="invite_code"  type="text" /> &nbsp;&nbsp;<a id="btn_invitecode" href="javascript:genderInviteCode('dv_edit');" style="color: #0000ff">生成邀请码</a>
                </td>
            </tr>
        </c:if>

        <tr>
            <th>&nbsp;</th>
            <td colspan="3" ><a id="a_sub_upd" href="javascript:;"  class="an_public1">确&nbsp;认</a></td>
        </tr>
    </table>
</div>

</html>
