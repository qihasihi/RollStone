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
        var courseid = "${courseid}";
        var paperid="${paperid}";
        var editor;
        $(function () {
            //试题类型
            $("input[name='rdo_ques_type']").filter(function () {
                return this.value ==
                ${question.questiontype}
            }).attr({checked:true}).andSelf().attr("disabled",true);
            $("#ques_name").html('${question.content}');

            $("#ques_answer").html('${question.analysis}');
            $("#correct_answer").html('${question.correctanswer}');
            var correctanswer="";
            <c:if test="${question.questiontype==3||question.questiontype==4}">
                $("#tr_right_answer").hide();
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
                $("#correct_answer").html('${question.correctanswer}');
                $("#dv_filter").html('${question.content}');
                var spanObj=$("#dv_filter span[name=fillbank]");
                var answerArray="${question.correctanswer}".split("|");
                $.each(spanObj,function(idx,itm){
                   var tmpobj='<span name="span" style="text-decoration:underline;color:#FF0000;">'+answerArray[idx]+'</span>';
                   $(itm).after(tmpobj).andSelf().remove();
                });
                 $("#ques_name").html($("#dv_filter").html());
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

<div class="subpage_head">
    <span class="back"><a  href="javascript:history.go(-1)">返回</a></span>
    <span class="ico55"></span><strong>修改试题</strong>
</div>
<!--<div class="subpage_nav">
    <ul>
        <li class="crumb"><a href="1">新建试题</a></li>
        <li><a href="1">通过专题查找</a></li>
    </ul>
</div> -->
<div class="content1">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input black">
        <col class="w100"/>
        <col class="w820"/>
        <tr>
            <th>题&nbsp;&nbsp;&nbsp;&nbsp;型：</th>
            <td>
                <c:if test="${!empty quesTypeList}">
                    <c:forEach items="${quesTypeList}" var="t" varStatus="idx">
                        <c:if test="${t.dictionaryvalue==1}">
                            <input checked onclick="changeQuesType(${t.dictionaryvalue})"
                                   id="rdo_type_${idx.index+1}" type="radio" value="${t.dictionaryvalue}"
                                   name="rdo_ques_type"/>
                        </c:if>

                        <c:if test="${t.dictionaryvalue ne 1}">
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
            <td><div class="jxxt_zhuanti_shiti_add">
                <table border="0" cellpadding="0" cellspacing="0">
                    <col class="w80"/>
                    <col class="w700"/>
                    <tr id="tr_ques_name">
                        <th><strong>题&nbsp;&nbsp;&nbsp;&nbsp;干：</strong></th>
                        <td>
                            <a href="javascript:void(0)" onclick="ed_option(this)" id="ques_name" name="ques_name" class="font-black">点击编辑题干&hellip;&hellip;</a>
                        </td>
                    </tr>

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
                                                <a id="ques_option_<%=sysid%>" name="ques_option_<%=sysid%>" href="javascript:void(0)" onclick="ed_option(this)">${q.content}</a>

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
                                <p class="p_t_10"><a href="javascript:addQuesOption(undefined,'${question.questiontype}')" title="添加选项" class="ico36"></a><a href="javascript:void(0);" onclick="delQuesOption()" title="移出选项" class="ico34"></a></p>
                            </c:if>
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
            <td><a href="javascript:void(0);" onclick="doUpdQuestion('${question.questionid}');" class="an_small">提&nbsp;交</a>
                <%--<a href="javascript:history.go(-1);" class="an_small">取&nbsp;消</a>--%>
            </td>
        </tr>
    </table>
</div>
<%@include file="/util/foot.jsp" %>

</body>
</html>