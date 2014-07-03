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
    <title></title>

    <script type="text/javascript">
        $(function(){
            <c:if test="${!empty stuAnswer}">
            <c:forEach items="${stuAnswer}" var="sa">
            //得到quesid的questype
            userAnswer(${sa.quesid},"${sa.answerString}");
            </c:forEach>

            </c:if>
        });

        function userAnswer(t,t1){
           var qtObj=$("#hd_questiontype_"+t);
            if(qtObj.val().length>0){
                if(qtObj.val().Trim()==1){
                    $("#dv_you_as"+t).html(t1);
                }else if(qtObj.val().Trim()==2){ //1:问题  2：填空 3：单选  4：多选
                    var t2=t1.split("|");
                    var t3=$("#dv_qs_"+t+" span[name='fillbank']");
                    if(t2.length==t3.length){
                        $.each(t3,function(a,b){
                            $(this).html(t2[a]);
                        });
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
<div id="dv_question">
    <c:if test="${!empty quesList}">
        <c:forEach items="${quesList}" var="q" varStatus="qidx">
            <div id="dv_qs_${q.questionid}">
                <input type="hidden" value="${q.questionid}" id="hd_quesid_${q.questionid}" name="hd_quesid"/>
                <input type="hidden" value="" name="hd_answer" id="hs_val_${q.questionid}"/>
                <input type="hidden" value="0" name="hd_stu_score" id="hs_val_stu_${q.questionid}"/>
                <input type="hidden" value="${q.score}" name="hd_score" id="hs_score_${q.questionid}"/>
                <input  type="hidden" value="${q.questiontype}" name="hd_questiontype" id="hd_questiontype_${q.questionid}"/>
                    ${qidx.index+1}、<span style="font-size:18px;font-weight:bold">
                <%--<c:if test="${q.questiontype==2}">--%>
                    <%--<script type="text/javascript">--%>
                        <%--var h1='${q.content}';--%>
                        <%--h1=replaceAll(h1,'<span name="fillbank"></span>','<input type="text" name="txt_tk" id="txt_tk_${q.questionid}" style="width:50px"/>');--%>
                        <%--document.write(h1);--%>
                    <%--</script>--%>
                <%--</c:if>--%>
                <%--<c:if test="${q.questiontype!=2}">--%>
                    <c:if test="${q.questiontype==1}">
                        【问答题】
                    </c:if>
                    <c:if test="${q.questiontype==2}">
                        【填空题】
                    </c:if>
                   <c:if test="${q.questiontype==3}">
                       【单选题】
                   </c:if>
                    <c:if test="${q.questiontype==4}">
                        【多选题】
                    </c:if>
                    ${q.content}
                <%--</c:if>--%>
                    </span>
                <c:if test="${q.questiontype==2}">
                    <p>题目解析：<div id="dv_right_as${q.questionid}">${q.analysis}</div></p>
                </c:if>
                <c:if test="${q.questiontype==1}">
                   <p>你的答案：<div id="dv_you_as${q.questionid}"></div></p>
                   <p>题目解析：<div id="dv_right_as${q.questionid}">${q.analysis}</div></p>
                </c:if>
                    <span id="quesOption_${q.questionid}">
                         <c:if test="${q.questiontype==3||q.questiontype==4}">
                             <c:if test="${!empty q.questioninfo.questionOption}">
                                 <ul>
                                     <c:forEach items="${q.questioninfo.questionOption}" var="qo">
                                         <li>
                                             <%//单选 %>
                                             <c:if test="${q.questiontype==3}">
                                                 <input type="radio" disabled="true" value="${qo.optiontype}" name="rdo_answer${qo.questionid}" id="rdo_answer${qo.ref}">
                                                 <label for="rdo_answer${qo.ref}">${qo.optiontype}.${qo.content}</label>
                                                 <c:if test="${qo.isright==1}">
                                                    <img src="img/u36.png"/>
                                                 </c:if>
                                             </c:if>
                                             <%//多选 %>
                                             <c:if test="${q.questiontype==4}">
                                                 <input type="checkbox" disabled="true" value="${qo.optiontype}|${qo.isright}" id="ckx_answer${qo.ref}" name="ckx_answer${qo.questionid}">
                                                 <label for="ckx_answer${qo.ref}">${qo.optiontype}.${qo.content}</label>
                                                 <c:if test="${qo.isright==1}">
                                                     <img src="img/u36.png"/>
                                                 </c:if>
                                             </c:if>
                                         </li>
                                     </c:forEach>
                                 </ul>
                             </c:if>
                         </c:if>
                    </span>


            </div>
        </c:forEach>
    </c:if>
</div>

</body>
</html>
