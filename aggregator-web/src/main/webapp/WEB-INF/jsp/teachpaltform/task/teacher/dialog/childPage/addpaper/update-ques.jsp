<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.school.util.UtilTool" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>
    <script type="text/javascript"
            src="js/teachpaltform/ques.js"></script>
    <script type="text/javascript">
        var courseid = "${courseid}";
        var paperid="${paperid}";
        var editor;
        $(function () {
            //试题类型
            $("input[name='rdo_ques_type_dialog']").filter(function () {
                return this.value ==
                ${question.questiontype}
            }).attr({checked:true}).andSelf().attr("disabled",true);
            $("#ques_name_dialog").html('${question.content}');

            $("#ques_answer_dialog").html('${question.analysis}');
            $("#correct_answer_dialog").html('${question.correctanswer}');
            var correctanswer="";
            <c:if test="${question.questiontype==3||question.questiontype==4}">
                $("#tr_right_answer").hide();
                <c:forEach var="itm" items="${quesOptionList}">
                    <c:if test="${itm.isright eq 1}">
                        correctanswer+="${itm.optiontype}";
                    </c:if>
                </c:forEach>
                 $("#correct_answer_dialog").hide();
                 $("#fill_answer").html(correctanswer);
                 $("#fill_answer").show();
            </c:if>
            <c:if test="${question.questiontype==2}">
                //$("#tr_analysis").hide();
                $("#correct_answer_dialog").html('${question.correctanswer}');
                $("#dv_filter").html('${question.content}');
                var spanObj=$("#dv_filter span[name=fillbank]");
                var answerArray="${question.correctanswer}".split("|");
                $.each(spanObj,function(idx,itm){
                   var tmpobj='<span name="span" style="text-decoration:underline;color:#FF0000;">'+answerArray[idx]+'</span>';
                   $(itm).after(tmpobj).andSelf().remove();
                });
                 $("#ques_name_dialog").html($("#dv_filter").html());
            </c:if>




            editor = new UE.ui.Editor({
                //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
                toolbars: [
                    ['Source', 'Undo', 'Redo', 'Bold', 'fillbank', 'fullscreen', 'removeformat', 'insertimage']
                ],
                initialFrameWidth: "580px",
                initialFrameHeight: "500px",
                autoHeightEnabled: false
            });
            textarea:'editor'; //与textarea的name值保持一致
            editor.render('editor');
            editor.setDataId('${question.questionid}');
            BindText();
        });
    </script>


</head>
<body>


<div class="public_float public_float960" id="dv_edit_ques">
    <p id="p_create_ques" class="float_title"><a href="javascript:;" class="ico93" onclick="loadDiv(3)" title="返回"></a><span id="dv_model_mdname">编辑试题</span></p>
    <p class="float_text public_input w820">
        <c:if test="${!empty quesTypeList}">
            <c:forEach items="${quesTypeList}" var="t" varStatus="idx">
                <c:if test="${t.dictionaryvalue==3}">
                    <input checked onclick="changeDialogQuesType(${t.dictionaryvalue})"
                           id="rdo_type_${idx.index+1}" type="radio" value="${t.dictionaryvalue}"
                           name="rdo_ques_type_dialog"/>
                </c:if>

                <c:if test="${t.dictionaryvalue ne 3}">
                    <input onclick="changeDialogQuesType(${t.dictionaryvalue})" id="rdo_type_${idx.index+1}"
                           type="radio" value="${t.dictionaryvalue}" name="rdo_ques_type_dialog"/>
                </c:if>

                <label for="rdo_type_${idx.index+1}">${t.dictionaryname}</label>
            </c:forEach>
        </c:if>
    </p>
    <div class="jxxt_float_h560">
        <div class="jxxt_zhuanti_shiti_add">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input black">
                <col class="w80"/>
                <col class="w700"/>
                <tr  id="tr_ques_name">
                    <td><strong style="cursor: pointer" onclick="$('#ques_name').click();">题&nbsp;&nbsp;&nbsp;&nbsp;干：</strong></td>
                    <td>
                        <a href="javascript:void(0)" onclick="ed_option_dialog(this)" id="ques_name_dialog" name="ques_name_dialog" class="font-black">点击编辑题干&hellip;&hellip;</a>
                    </td>
                </tr>

                <!--
                <tr>
                    <td><strong>选&nbsp;&nbsp;&nbsp;&nbsp;项：</strong></td>
                    <td><input type="radio" name="radio" id="radio2" value="radio">
                        A. <a href="1" class="font-black">点击编辑选项&hellip;&hellip;</a><br>
                        <input type="radio" name="radio" id="radio" value="radio">
                        B. <a href="1" class="font-black">点击编辑选项&hellip;&hellip;</a><br>
                        <input type="radio" name="radio" id="radio4" value="radio">
                        C. <a href="1" class="font-black">点击编辑选项&hellip;&hellip;</a><br>
                        <input type="radio" name="radio" id="radio5" value="radio">
                        D. <a href="1" class="font-black">点击编辑选项&hellip;&hellip;</a>
                        <p class="p_t_10"><a href="1" title="添加选项" class="ico36"></a><a href="1" title="移出选项" class="ico34"></a></p>
                    </td>
                </tr>  -->



                <c:if test="${!empty quesOptionList}">
                    <tr id="type_option">
                        <td><strong>选&nbsp;&nbsp;&nbsp;&nbsp;项：</strong></td>
                        <td>
                            <table id="ques_tbl">
                                <%
                                    int i=0;
                                %>
                                <c:forEach var="q" items="${quesOptionList}" varStatus="idx">
                                    <%
                                        Random r = new Random();
                                        int sysid = r.nextInt(10000000);
                                    %>
                                    <tr id="tr_quesoption_<%=sysid%>">
                                        <th>
                                            <%=UtilTool.AZ[i]+"：&nbsp;"%>
                                            <%i+=1;%>
                                        </th>
                                        <td>

                                            <c:if test="${q.questiontype==3 and q.isright eq 1}">
                                                <input style="display:none;" name="rdo_right_option" checked="checked" type="radio" value="1"
                                                       id="rdo_right_<%=sysid%>"/>
                                            </c:if>

                                            <c:if test="${q.questiontype==3 and q.isright eq 0}">
                                                <input style="display:none;" name="rdo_right_option" type="radio" value="1"
                                                       id="rdo_right_<%=sysid%>"/>
                                            </c:if>


                                            <c:if test="${q.questiontype eq 4 and q.isright eq 1}">
                                                <input style="display:none;" name="ck_right_option" checked="checked" type="checkbox" value="1"
                                                       id="ck_right_<%=sysid%>"/>
                                            </c:if>

                                            <c:if test="${q.questiontype eq 4 and q.isright eq 0}">
                                                <input style="display:none;" name="ck_right_option" type="checkbox" value="1"
                                                       id="ck_right_<%=sysid%>"/>
                                            </c:if>
                                            <a id="ques_option_<%=sysid%>" name="ques_option_<%=sysid%>" href="javascript:void(0)" onclick="ed_option_dialog(this)">${q.content}</a>

                                            <c:if test="${q.isright eq 1}">
                                                    <span id="sp_img_<%=sysid%>" class="ico12">
                                                    </span>
                                            </c:if>

                                            <c:if test="${q.isright ne 1}">
                                                    <span id="sp_img_<%=sysid%>" >
                                                    </span>
                                            </c:if>

                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                </c:if>


                <tr>
                    <th colspan="2" id="td_ques_operate">
                        <c:if test="${question.questiontype eq 4 or question.questiontype eq 3}">
                            <p class="p_t_10"><a href="javascript:addQuesOption('','${question.questiontype}',true)" title="添加选项" class="ico36"></a><a href="javascript:void(0);" onclick="delQuesOption(true)" title="移出选项" class="ico34"></a></p>
                        </c:if>
                    </th>
                </tr>



                <tr id="tr_right_answer">
                    <td><strong>正确答案：</strong></td>
                    <td>
                        <a class="font-black" id="correct_answer_dialog" name="correct_answer_dialog" href="javascript:void(0)" onclick="ed_option_dialog(this)">请编辑正确答案&hellip;&hellip;</a>
                        <span id="fill_answer" name="fill_answer" style="display: none">请点击图标设置正确答案</span>
                    </td>
                </tr>
                <tr>
                    <td><strong>答案解析：</strong></td>
                    <td>
                        <a class="font-black" id="ques_answer_dialog" name="ques_answer_dialog" href="javascript:void(0)" onclick="ed_option_dialog(this)">点击编辑答案解析&hellip;&hellip;</a>
                    </td>
                    <div id="dv_filter" style="display: none;"></div>
                </tr>


            </table>
        </div>
        <p class="t_c p_t_20"><a  href="javascript:doUpdDialogQues(${question.questionid});" id="btn_addQues"  class="an_public1">提&nbsp;交</a></p>
    </div>
</div>



<div class="public_float public_float960" id="dv_edit_option" style="display: none;">
    <p class="float_title" id="p_edit_ques"><a href="javascript:;" onclick="$('#dv_edit_ques').show();$('#dv_edit_option').hide();" class="ico93" title="返回"></a>编辑题干</p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input black w820">
        <!-- <tr>
             <td><span class="f_right font-red">点击图标插入填空占位符</span><input type="checkbox" name="checkbox" id="checkbox"> <span class="font-blue">设为正确答案 </span></td>
         </tr> -->
        <tr>
            <td>
                <span id="sp_edit"></span>
                <span id="sp_right"></span>
                <div class="p_tb_10">
                    <textarea id="editor" name="editor" style="width:600px;height:120px;"></textarea>
                </div>
                <input type="hidden" id="hd_id"/>
            </td>
        </tr>
        <tr>
            <td class="t_c"><a href="javascript:void(0);" onclick="sub_option_dialog()" class="an_public1">保&nbsp;存</a></td>
            <%--onclick="doUpdQuestion('${question.questionid}');"--%>
        </tr>
    </table>
</div>







</body>
</html>