<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>


<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>
    <script type="text/javascript"
            src="<%=basePath %>js/teachpaltform/ques.js"></script>
    <script type="text/javascript">
        var courseid = "${param.courseid}";
        var operate_type="${param.operate_type}";
        var pQuestype='${param.questype}';
        var addflag='${param.flag}';
        var editor;
        var questionid="${questionid}";
        $(function () {
            if(addflag.length>0)
                $("input[name='rdo_ques_type']").attr("disabled",true);
            if(operate_type=="3")
                $("#li_quote").remove();
            //试题类型
            var questype =pQuestype.length>0?parseInt(pQuestype):$("input[name='rdo_ques_type']:checked").val();
            changeQuesType(questype);
            $("input[name='rdo_ques_type']").filter(function(){return this.value==questype}).attr("checked",true);

            editor = new UE.ui.Editor({
                //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
                    toolbars: [
                    ['Source', 'Undo', 'Redo', 'Bold', 'fillbank', 'fullscreen', 'removeformat','insertimage']
                ],
               // initialFrameWidth: "580px",
               // initialFrameHeight: "500px",
                autoHeightEnabled: false
            });
            textarea:'editor'; //与textarea的name值保持一致
            editor.render('editor');
            editor.setDataId(questionid);
            BindText();
        });
    </script>


</head>
<body>

<div class="subpage_head">
    <c:if test="${empty param.operate_type}">
        <span class="back"><a  href="javascript:history.go(-1)">返回</a></span>
    </c:if>
    <span class="ico55"></span><strong>添加试题</strong></div>
<div class="subpage_nav">
    <ul>
        <li class="crumb"><a>新建试题</a></li>

        <li id="li_quote">
            <c:if test="${!empty param.operate_type and !empty param.flag}">
                <a href="teachercourse?m=toCourseQuestionList&addCourseId=${param.courseid}&questype=${param.questype}&operate_type=${param.operate_type}">引用试题</a>
            </c:if>

            <c:if test="${empty param.operate_type}">
                <a href="teachercourse?m=toCourseQuestionList&addCourseId=${param.courseid}">引用试题</a>
            </c:if>
        </li>

    </ul>
</div>
<div class="content1">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input black">
        <col class="w100"/>
        <col class="w820"/>
        <tr>
            <th>题&nbsp;&nbsp;&nbsp;&nbsp;型：</th>
            <td>
                <c:if test="${!empty quesTypeList}">
                    <c:forEach items="${quesTypeList}" var="t" varStatus="idx">
                        <c:if test="${t.dictionaryvalue==3}">
                            <input checked onclick="changeQuesType(${t.dictionaryvalue})"
                                   id="rdo_type_${idx.index+1}" type="radio" value="${t.dictionaryvalue}"
                                   name="rdo_ques_type"/>
                        </c:if>

                        <c:if test="${t.dictionaryvalue ne 3}">
                            <input onclick="changeQuesType(${t.dictionaryvalue})" id="rdo_type_${idx.index+1}"
                                   type="radio" value="${t.dictionaryvalue}" name="rdo_ques_type"/>
                        </c:if>

                        <label for="rdo_type_${idx.index+1}">${t.dictionaryname}</label>
                    </c:forEach>
                </c:if>
            </td>
        </tr>
        <tr>
            <th>题&nbsp;&nbsp;&nbsp;&nbsp;目：</th>
            <td>
                <div class="jxxt_zhuanti_shiti_add">
                <table border="0" cellpadding="0" cellspacing="0">
                    <col class="w80"/>
                    <col class="w700"/>
                    <tr id="tr_ques_name">
                        <th><strong style="cursor: pointer" onclick="$('#ques_name').click();" >题&nbsp;&nbsp;&nbsp;&nbsp;干：</strong></th>
                        <td>
                            <a href="javascript:void(0)" onclick="ed_option(this)" id="ques_name" name="ques_name" class="font-black">点击编辑题干&hellip;&hellip;</a>
                        </td>
                    </tr>

                    <tr id="type_option">
                        <td><strong>选&nbsp;&nbsp;&nbsp;&nbsp;项：</strong></td>
                        <td>
                            <table id="ques_tbl">

                            </table>
                        </td>
                    </tr>
                    <tr>
                        <th colspan="2" id="td_ques_operate">

                        </th>
                    </tr>
                    <tr id="tr_right_answer">
                        <th><strong>正确答案：</strong></th>
                        <td>
                            <a class="font-black" id="correct_answer" name="correct_answer" href="javascript:void(0)" onclick="ed_option(this)">请编辑正确答案&hellip;&hellip;</a>
                            <span id="fill_answer" name="fill_answer" style="display: none">请点击图标设置正确答案</span>
                        </td>
                    </tr>
                    <tr>
                        <th><strong>答案解析：</strong></th>
                        <td>
                            <a class="font-black" id="ques_answer" name="ques_answer" href="javascript:void(0)" onclick="ed_option(this)">点击编辑答案解析&hellip;&hellip;</a>
                        </td>
                        <div id="dv_filter" style="display: none;"></div>
                    </tr>
                    <tr>
                        <td colspan="2"></td>
                    </tr>
                    <tr class="border">
                        <td><strong>正在编辑：</strong></td>
                        <td>
                            <span id="sp_edit"></span>
                            <span id="sp_right"></span>
                            <div class="p_tb_10">
                                <textarea id="editor" name="editor" style="width:600px;height:120px;"></textarea>
                            </div>
                            <p class="t_c"><a href="javascript:void(0);" onclick="sub_option()" class="an_public3">保&nbsp;存</a></p>
                            <input type="hidden" id="hd_id"/>
                        </td>
                    </tr>
                </table>
            </div>
            </td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td><a href="javascript:doAddQuestion();" id="btn_addQues" class="an_small">提&nbsp;交</a>
              <!--  <a  href="javascript:window.close()" class="an_small">取&nbsp;消</a>-->
            </td>
        </tr>
    </table>
</div>
<%@include file="/util/foot.jsp" %>

</body>
</html>
