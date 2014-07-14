<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-7-1
  Time: 下午3:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <script type="text/javascript">
        $(function(){
            <c:if test="${!empty stuAnswer}">
            <c:forEach items="${stuAnswer}" var="sa">
            //得到quesid的questype
            userAnswer(${sa.quesid},"${sa.answerString}","${sa.score}","${sa.annexNameFull}");
            </c:forEach>
            var sumScore=0;
             $("strong[id*='you_score']").each(function(idx,itm){
                sumScore+=parseFloat($(itm).html().Trim());
             });
            $("#sp_sumScore").html(sumScore);
            </c:if>
        });

        function userAnswer(t,t1,score,nexName){
           var qtObj=$("#hd_questiontype_"+t);
            if(qtObj.val().length>0){
                $("#you_score"+t).html(score);

                if(qtObj.val().Trim()==1){
                    $("#dv_you_as"+t).html(t1);
                    if(nexName!="undefined"&&nexName.length>0){
                        $("#fujian"+t).html("<a target='_blank' href='<%=basePath%>/"+nexName+"'>"+nexName.substring(nexName.lastIndexOf("/")+1)+"</a>");
                        $("#fujian"+t).parent().show();
                    }

                }else if(qtObj.val().Trim()==2){ //1:问题  2：填空 3：单选  4：多选
                    var t2=t1.split("|");
                    var t3=$("#dv_qs_"+t+" span[name='fillbank']");
                    if(t2.length==t3.length){
                        $.each(t3,function(a,b){
                            $(this).html("&nbsp;&nbsp;"+t2[a]+"&nbsp;&nbsp;");
                        });
                    }
                    $("span[name='fillbank']").css("border-bottom","1px solid black");

                    if(nexName!="undefined"&&nexName.length>0){
                        $("#fujian"+t).html("<a target='_blank' href='<%=basePath%>/"+nexName+"'>"+nexName.substring(nexName.lastIndexOf("/")+1)+"</a>");
                        $("#fujian"+t).parent().show();
                    }

                }else if(qtObj.val().Trim()==3){
                    $("input[name='rdo_answer"+t+"']").filter(function(){return this.value.Trim()==t1}).attr("checked",true);
                }else if(qtObj.val().Trim()==4){
                   var tv2=t1.split("|");
                    $("input[name='ckx_answer"+t+"']").each(function(d,c){
                        for(var l=0;l<tv2.length;l++){
                            if(tv2[l]==c.value.split('|')[0]){
                                c.checked=true;
                            }
                        }
                    });
                }
            }
        }
    </script>
</head>
<body>
<body>
<div class="subpage_head"><span class="ico55"></span><strong>查看试卷</strong></div>
<div class="content2">
    <div class="jxxt_zhuanti_shijuan_add font-black public_input"  id="dv_question">
        <p class="title"><span class="f_right"><strong>得分：<span class="font-blue"><span id="sp_sumScore"></span>分/待批改</span></strong></span>
            <c:if test="${!empty paperObj&&paperObj.papertype==3}">
            <strong>${paperObj.papername}</strong>
            </c:if>
       </p>
<c:if test="${!empty quesList}">
    <c:forEach items="${quesList}" var="q" varStatus="qidx">
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 w940"  id="dv_qs_${q.questionid}">
            <caption><span class="font-blue f_right"><strong id="you_score${q.questionid}">0</strong>分/${q.score}分</span><span class="font-blue">${qidx.index+1}</span>/10</caption>
            <tr>
                <td><span class="bg">
                     <input type="hidden" value="${q.questionid}" id="hd_quesid_${q.questionid}" name="hd_quesid"/>
                <input type="hidden" value="" name="hd_answer" id="hs_val_${q.questionid}"/>
                <input type="hidden" value="0" name="hd_stu_score" id="hs_val_stu_${q.questionid}"/>
                <input type="hidden" value="${q.score}" name="hd_score" id="hs_score_${q.questionid}"/>
                <input  type="hidden" value="${q.questiontype}" name="hd_questiontype" id="hd_questiontype_${q.questionid}"/><c:if test="${q.questiontype==1}">问答题</c:if><c:if test="${q.questiontype==2}">填空题</c:if><c:if test="${q.questiontype==3}">单选题</c:if><c:if test="${q.questiontype==4}">多选题</c:if>：</span>
                    ${q.content}
                    <c:if test="${q.questiontype==2}"><%//填空题%>
                    <p><strong>答题附件：</strong><span class="font-blue" id="fujian${q.questionid}"></span></p>
                      </td></tr>
                       <tr><td><p>
                           <strong>正确答案：</strong>${q.correctanswer}</p>
                            <p><strong>题目解析：</strong><div id="dv_right_as${q.questionid}">${q.analysis}</div></p>
                       </td>
                        </tr>
                    </c:if>
                    <c:if test="${q.questiontype==1}">
                        </td></tr>
                        <tr><td>
                            <p><strong>学生答案：</strong><span id="dv_you_as${q.questionid}"></span></p>
                            <p><strong>答题附件：</strong><span  class="font-blue" id="fujian${q.questionid}"></span></p>
                        <p><strong>正确答案：</strong>${q.correctanswer}</p>
                        <p><strong>题目解析：</strong><span id="dv_right_as${q.questionid}">${q.analysis}</span></p>
                         </td>
                         </tr>
                    </c:if>
            <c:if test="${q.questiontype==3||q.questiontype==4}">
                     <span id="quesOption_${q.questionid}">
                            <c:if test="${!empty q.questionOption}">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <col class="w30"/>
                            <col class="w910"/>
                                <c:forEach items="${q.questionOption}" var="qo">
                                    <tr>
                                        <th>
                                            <%//单选 %>
                                            <c:if test="${q.questiontype==3}">
                                                <input type="radio" disabled="true" value="${qo.optiontype}" name="rdo_answer${qo.questionid}" id="rdo_answer${qo.ref}">
                                            </c:if>
                                            <%//多选 %>
                                            <c:if test="${q.questiontype==4}">
                                                <input type="checkbox" disabled="true" value="${qo.optiontype}|${qo.isright}" id="ckx_answer${qo.ref}" name="ckx_answer${qo.questionid}">
                                            </c:if>
                                        </th>
                                        <td>
                                            <%//单选 %>
                                            <c:if test="${q.questiontype==3}">
                                                <label for="rdo_answer${qo.ref}">${qo.optiontype}.${qo.content}</label>
                                            </c:if>
                                            <%//多选 %>
                                            <c:if test="${q.questiontype==4}">
                                                <label for="rdo_answer${qo.ref}">${qo.optiontype}.${qo.content}</label>
                                            </c:if>
                                            <c:if test="${qo.isright==1}">
                                                <span class="ico12"></span>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                        </table>
                            </c:if>
                     </span>
                </td>
                </tr>
            </c:if>

        </table>
    </c:forEach>
</c:if>
    </div>
</div>
</body>
</html>
