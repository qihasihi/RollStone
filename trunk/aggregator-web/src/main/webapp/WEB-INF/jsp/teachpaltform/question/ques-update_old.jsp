<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.school.entity.teachpaltform.QuestionOption" %>
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
        var editor;
        $(function () {
            //试题类型
            $("input[name='rdo_ques_type']").filter(function () {
                return this.value ==
                ${question.questiontype}
            }).attr({checked:true}).andSelf().attr("disabled",true);

            $("#ques_name").val("${question.content}");
            $("#ques_answer").val("${question.analysis}");
            $("#correct_answer").val("${question.correctanswer}");
            var correctanswer="";
            <c:if test="${question.questiontype==3||question.questiontype==4}">
                <c:forEach var="itm" items="${quesOptionList}">
                    <c:if test="${itm.isright eq 1}">
                        correctanswer+="${itm.optiontype}";
                    </c:if>
                </c:forEach>
                 $("#correct_answer").hide();
                 $("#fill_answer").html(correctanswer);
                 $("#fill_answer").show();
            </c:if>
            <c:if test="${question.questiontype==2}">
                //$("#tr_analysis").hide();
                $("#correct_answer").val("${question.correctanswer}");
                $("#dv_filter").html("${question.content}");
                var spanObj=$("#dv_filter p > span[name=fillbank]");
                var answerArray="${question.correctanswer}".split("|");
                $.each(spanObj,function(idx,itm){
                   var tmpobj='<span name="span" style="text-decoration:underline;color:#FF0000;">'+answerArray[idx]+'</span>';
                   $(itm).after(tmpobj).andSelf().remove();
                });
                 $("#ques_name").val($("#dv_filter").html());
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
            BindText();
        });
    </script>


</head>
<body>
<%@include file="/util/head.jsp" %>
<div class="jxpt_layout">
    <%@include file="/util/nav.jsp" %>
    <p class="jxpt_icon04">修改试题</p>

    <div class="jxpt_content_layout">
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_10 zt14">
            <col class="w100"/>
            <col class="w780"/>
            <tr>
                <th>题型：</th>
                <td>
                    <c:if test="${!empty quesTypeList}">
                        <c:forEach items="${quesTypeList}" var="t" varStatus="idx">
                            <input  id="rdo_type_${idx.index+1}"
                                       type="radio" value="${t.dictionaryvalue}" name="rdo_ques_type"/>
                            <label for="rdo_type_${idx.index+1}">${t.dictionaryname}</label>
                        </c:forEach>
                    </c:if>
                </td>
            </tr>


            <!-- 问题编辑 -->
            <tr id="tr_ques_name">
                <th>题&nbsp;&nbsp;&nbsp;&nbsp;干：</th>
                <td>
                    <textarea id="ques_name" name="ques_name" style="height:100px;width:500px;" readonly></textarea>
                </td>
            </tr>
            <%
                int i=0;
            %>
            <c:if test="${!empty quesOptionList}">
                <c:forEach var="q" items="${quesOptionList}" varStatus="idx">
                    <%
                        Random r = new Random();
                        int sysid = r.nextInt(100000);
                    %>
                    <tr id="tr_quesoption_<%=sysid%>">
                        <th>
                            <%=UtilTool.AZ[i]+"：&nbsp;"%>
                            <%i+=1;%>
                        </th>
                        <td>
                            <div class="p_b_14"><p>

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
                                           id="ck_right<%=sysid%>"/>
                                </c:if>

                                <c:if test="${q.questiontype eq 4 and q.isright eq 0}">
                                    <input style="display:none;" name="ck_right_option" type="checkbox" value="1"
                                           id="ck_right<%=sysid%>"/>
                                </c:if>

                                <textarea id="ques_option_<%=sysid%>" name="ques_option_<%=sysid%>" readonly rows="1"  cols="50">${q.content}</textarea>
                                <span id="sp_img_<%=sysid%>">
                                    <c:if test="${q.isright eq 1}">√</c:if>
                                </span>
                            </p>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>


            <tr>
                <th colspan="2" id="td_ques_operate">
                    <c:if test="${question.questiontype eq 4 or question.questiontype eq 3}">
                        <a href="javascript:addQuesOption(undefined,'${question.questiontype}')">添加</a><a href="javascript:void(0);" onclick="delQuesOption()">删除</a>
                    </c:if>
                </th>
            </tr>
            <tr id="tr_correctanswer">
                <th id="th_correctanswer">正确答案：</th>
                <td>
                    <textarea id="correct_answer" name="correct_answer" rows="1" cols="50" readonly>请编辑正确答案..</textarea>
                    <span id="fill_answer" name="fill_answer" style="display: none"></span>
                </td>
            </tr>
            <tr id="tr_analysis">
                <th id="th_analysis">答案解析：</th>
                <td>
                    <textarea id="ques_answer" name="ques_answer" rows="1" cols="50" readonly></textarea>
                </td>
                <div id="dv_filter" style="display: none;"></div>
            </tr>

            <tr>
                <th id="th_editor">正在编辑：</th>
                <td>
                    <span id="sp_edit"></span>
                    <span id="sp_right"></span>
                </td>
            </tr>

            <tr>
                <th>&nbsp;</th>
                <td>
                    <textarea id="editor" name="editor" rows="1" cols="50"></textarea>
                    <a href="javascript:void(0);" onclick="sub_option()" class="an_blue">保&nbsp;存</a>
                    <input type="hidden" id="hd_id"/>
                </td>
            </tr>


            <tr>
                <th>&nbsp;</th>
                <td><a href="javascript:void(0);" onclick="doUpdQuestion('${question.questionid}');" class="an_blue">提&nbsp;交</a><a
                        href="javascript:history.go(-1);" class="an_blue">取&nbsp;消</a></td>
            </tr>
            <tr>
                <td colspan="2"></td>
            </tr>
        </table>
    </div>
</div>
<%@include file="/util/foot.jsp" %>

</body>
</html>
