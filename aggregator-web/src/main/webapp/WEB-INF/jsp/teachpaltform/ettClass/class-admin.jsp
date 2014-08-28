<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>

<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>
    <script type="text/javascript" src="<%=basePath %>js/ett/ettcls.js"></script>
    <script type="text/javascript">
        $(function(){

        });
    </script>


</head>
<body>
<div class="subpage_head"><span class="ico28"></span><strong>班级管理</strong></div>
<div class="content3">
    <div class="jxxt_banji_jiaowuT public_input">
        <select>
            <option value="">--请选择年级--</option>
            <c:if test="${!empty gradeList}">
                <c:forEach items="${gradeList}" var="c">
                    <option value="${c.gradevalue}">${c.gradename}</option>
                </c:forEach>
            </c:if>
        </select>

        <select>
            <option value="">--请选择学科--</option>
            <c:if test="${!empty subjectList}">
                <c:forEach items="${subjectList}" var="c">
                    <option value="${c.subjectid}">${c.subjectname}</option>
                </c:forEach>
            </c:if>
        </select>

        <select>
            <option value="">--请选择班级类型--</option>
            <c:if test="${!empty classType}">
                <c:forEach items="${classType}" var="c">
                    <option value="${c.dictionaryvalue}">${c.dictionaryname}</option>
                </c:forEach>
            </c:if>
        </select>
        <a href="javascript:void(0);" onclick="showModel('dv_add');" class="an_small">添加班级</a>
    </div>

    <div class="jxxt_banji_jiaowuR font-black">
        <div class="jxxt_banji_info">
            <div class="pic"><img src="" width="135" height="135"></div>
            <p class="t_r"><a href="1" class="ico11" title="编辑"></a><a href="1" class="ico04" title="删除"></a></p>
            <p><strong>2014年网下指导教师培训初一普通班</strong></p>
            <p><b>班主任：欧阳小诗</b>班级类型：网校班</p>
        </div>

        <div class="jxxt_banji_list">
            <p>任课教师（5）&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" target="_blank" class="font-darkblue"><span class="ico26"></span>添加课程</a></p>
            <ul>
                <li><img src="../images/pic01_140226.jpg" width="80" height="80">王小红<b>语文</b></li>
                <li><img src="../images/pic05_140226.jpg" width="80" height="80">李春天<b>英语</b></li>
                <li><img src="../images/pic05_140226.jpg" width="80" height="80">谢明亮<b>物理</b></li>
                <li><img src="../images/pic05_140226.jpg" width="80" height="80">白高兴<b>化学</b></li>
                <li><img src="../images/pic05_140226.jpg" width="80" height="80">王小红<b>历史</b></li>
                <li><img src="../images/pic05_140226.jpg" width="80" height="80">李春天<b>地理</b></li>
                <li><img src="../images/pic05_140226.jpg" width="80" height="80">谢明亮<b>生物</b></li>
            </ul>

            <p class="p_t_5">本班学员（5）&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" target="_blank" class="font-darkblue"><span class="ico26"></span>学员管理</a></p>
            <ul>
                <li><img src="../images/pic05_140226.jpg" width="80" height="80">王小红</li>
                <li><img src="../images/pic05_140226.jpg" width="80" height="80">李春天
                    <p class="ico"><a class="ico34" title="移出"></a></p></li>
                <li><img src="../images/pic05_140226.jpg" width="80" height="80">谢明亮</li>
                <li><img src="../images/pic05_140226.jpg" width="80" height="80">白高兴</li>
                <li><img src="../images/pic05_140226.jpg" width="80" height="80">王小红</li>
                <li><img src="../images/pic05_140226.jpg" width="80" height="80">李春天</li>
                <li><img src="../images/pic05_140226.jpg" width="80" height="80">谢明亮</li>
                <li><img src="../images/pic05_140226.jpg" width="80" height="80">白高兴</li>
            </ul>
        </div>

    </div>
    <div class="subpage_contentL">
        <ul id="ul_class">
            <li class="crumb"><a href="1">2014年网下指导教师培训初一普通班...</a></li>
            <li><a href="1">2014年网下指导教师培训初三普通班</a></li>
        </ul>
        <div class="nextpage" id="dv_page"><span><b class="before"></b></span>&nbsp;1/1&nbsp;<span><a href="1"><b class="after"></b></a></span></div>
    </div>
    <div class="clear"></div>
</div>
<%@include file="/util/foot.jsp" %>
</body>



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
            <td><select id="type"  class="w100">
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
            <th>&nbsp;&nbsp;班级限额：</th>
            <td><input  id="num" type="text" class="w90" /></td>
        </tr>
        <tr>
            <th>&nbsp;&nbsp;失效日期：</th>
            <td colspan="3">
                <input placeholder="班级失效时间" class="w320"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="verify_time"  name="verify_time" type="text" />
            </td>
        </tr>
        <tr>
            <th>允许学生加入：</th>
            <td colspan="3"><input  type="radio" name="rdo" checked="checked" value="1"> 是&nbsp;&nbsp;&nbsp;&nbsp;<input  type="radio" name="rdo" value="0"> 否</td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td colspan="3" ><a href="javascript:sub_cls();"  class="an_public1">确&nbsp;认</a></td>
        </tr>
    </table>
</div>

<div class="public_windows" id="dv_edit" style="display: none;">
    <h3><a href="javascript:closeModel('dv_edit')"  title="关闭"></a>新建班级</h3>
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
            <td><select id="type"  class="w100">
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
            <th>&nbsp;&nbsp;班级限额：</th>
            <td><input  id="num" type="text" class="w90" /></td>
        </tr>
        <tr>
            <th>&nbsp;&nbsp;失效日期：</th>
            <td colspan="3">
                <input placeholder="班级失效时间" class="w320"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"   id="verify_time"  name="verify_time" type="text" />
            </td>
        </tr>
        <tr>
            <th>允许学生加入：</th>
            <td colspan="3"><input  type="radio" name="rdo"  value="1"> 是&nbsp;&nbsp;&nbsp;&nbsp;<input  type="radio" name="rdo" value="0"> 否</td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td colspan="3" id="td_edit"><a href="javascript:sub_cls();"  class="an_public1">确&nbsp;认</a></td>
        </tr>
    </table>
</div>

</html>
