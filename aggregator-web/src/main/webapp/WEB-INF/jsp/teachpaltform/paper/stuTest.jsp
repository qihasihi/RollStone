<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-6-30
  Time: 下午2:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <script type="text/javascript" src="js/teachpaltform/testpaper.js"></script>
    <script type="text/javascript">
        var quesSize="${quesSize}";
        var courseid="${courseid}";
        var taskid="${taskid}";
        var subQuesId=",";
        $(function(){
           // h1=replaceAll(h1,'<span name="fillbank"></span>','');
            $("span[name='fillbank']").each(function(idx,itm){
                $(this).replaceWith("p",'<input type="text" name="txt_tk" id="txt_tk_'+$(this).parent().parent().children("input[name='hd_quesid']").val()+'"/>');
            })


            loadQuesNumberTool(quesSize);
            next(1);
        });
    </script>
</head>
<body>
<input type="hidden" value="${paperid}" name="hd_paper_id" id="hd_paper_id"/>
<div class="subpage_head"><span class="ico55"></span><strong>
    <c:if test="${!empty paperObj&&paperObj.papertype==3}">成卷测试</c:if>
    <c:if test="${!empty paperObj&&paperObj.papertype==4}">自主测试</c:if>
</strong></div>
<div class="content1">
    <p class="t_r"><span class="ico_time"></span><strong>${taskstatus}</strong></p>
    <div id="dv_test">
    <div class="jxxt_zhuanti_rw_ceshi">
        <h2> <c:if test="${!empty paperObj&&paperObj.papertype==3}">
         ${paperObj.papername}
        </c:if></h2>
        <ul id="dv_ques_number">
            <%--<li class="blue"><a href="1">1</a></li>--%>
            <%--<li class="blue"><a href="1">2</a></li>--%>
            <%--<li class="blue"><a href="1">3</a></li>--%>
            <%--<li class="yellow"><a href="1">4</a></li>--%>

        </ul>
    </div>
    <div class="jxxt_zhuanti_rw_ceshi_shiti font-black public_input" id="dv_question">
<c:if test="${!empty quesList}">
    <c:forEach items="${quesList}" var="q" varStatus="qidx">
        <div  id="dv_qs_${q.questionid}">
            <input type="hidden" value="${qidx.index}" id="hd_ques_${q.questionid}_idx"/>
            <input type="hidden" value="${q.questionid}" id="hd_quesid_${q.questionid}" name="hd_quesid"/>
            <input type="hidden" value="" name="hd_answer" id="hs_val_${q.questionid}"/>
            <input type="hidden" value="0" name="hd_stu_score" id="hs_val_stu_${q.questionid}"/>
            <input type="hidden" value="${q.score}" name="hd_score" id="hs_score_${q.questionid}"/>
            <input  type="hidden" value="${q.questiontype}" name="hd_questiontype" id="hd_questiontype_${q.questionid}"/>
        <p> ${qidx.index+1}.
             ${q.content}
            <c:if test="${q.questiontype==1}">
                <div class="p_t_20"><textarea name="txt_answer" id="txt_answer_${q.questionid}"  placeholder="输入你的答案"></textarea></div>
            </c:if>
            </p>
            <c:if test="${q.questiontype==1||q.questiontype==2}">
                <div class="files"><strong>上传附件：</strong><input   id="txt_f2${q.questionid}" readonly type="text" />
                    <a href="javascript:;"   onclick="document.getElementById('txt_f_${q.questionid}').click();"  class="an_public3">选择</a></div>
                    <input type="file" onchange="document.getElementById('txt_f2${q.questionid}').value=this.value.substring(this.value.lastIndexOf('\\')+1);" name="txt_f_${q.questionid}2" style="display:none" id="txt_f_${q.questionid}"/></p>
            </c:if>
            <%--<c:if test="${q.questiontype==4}">--%>
                <%--<input type="button"  onclick="doAnswerOne(${q.questionid},undefined,undefined,${q.questiontype})" value="确定"/>--%>
            <%--</c:if>--%>
            <c:if test="${q.questiontype==3||q.questiontype==4}">
                <c:if test="${!empty q.questionOption}">
                    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1" id="quesOption_${q.questionid}">
                        <col class="w30"/>
                        <col class="w860"/>
                        <c:forEach items="${q.questionOption}" var="qo">
                        <tr>
                            <th>
                                <c:if test="${q.questiontype==3}">
                                    <input type="radio"  id="rdo_answer${qo.questionid}${qo.optiontype}"  onclick="doAnswerOne(${q.questionid},this.value,${qo.isright})" value="${qo.optiontype}" name="rdo_answer${qo.questionid}">
                                </c:if>
                                <%//多选 %>
                                <c:if test="${q.questiontype==4}">
                                    <input type="checkbox" value="${qo.optiontype}|${qo.isright}" id="rdo_answer${qo.questionid}${qo.optiontype}" name="rdo_answer${qo.questionid}">
                                </c:if>
                                </th>
                            <td><label for="rdo_answer${qo.questionid}${qo.optiontype}">${qo.optiontype}.${qo.content}</label></td>
                        </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </c:if>
            <p class="jxxt_zhuanti_rw_ceshi_an">
                <a  href="javascript:;" onclick="next(undefined,-1)" class="an_test1">上一题</a>
                <a href="javascript:;" onclick="doAnswerOne(${q.questionid},undefined,undefined,${q.questiontype})" class="an_test1">下一题</a>
                <a href="javascript:;" onclick="subPaper()"  class="an_test2">交卷</a></p>
        </div>
    </c:forEach>
</c:if>


    </div>
    </div>
</div>
<%@include file="/util/foot.jsp"%>
</body>
</html>
