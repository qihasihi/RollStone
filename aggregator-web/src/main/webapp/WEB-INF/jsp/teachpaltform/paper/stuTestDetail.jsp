<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-7-1
  Time: 下午3:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<%

%>
<html>
<head>
    <script type="text/javascript">
        var paperSumScore=100;
        var allquesid="${allquesidObj}";
        $(function(){

            <c:if test="${!empty stuAnswer}">
            <c:forEach items="${stuAnswer}" var="sa">
            //得到quesid的questype
            userAnswer(${sa.quesid},"${sa.answerString}","${sa.score}","${sa.annexNameFull}");
            </c:forEach>
            //计算每题的分数
            var allqueslength=allquesid.split(",").length;
            var avgScore=parseInt(paperSumScore/allqueslength);
            $("span[id='avg_score']").html(avgScore);
            if(paperSumScore%allqueslength>0)
                $("span[id='avg_score']").last().html(avgScore+(paperSumScore%allqueslength));
            //计算是否存在试题组等题
            $("table[id*='dv_pqs_']").each(function(idx,itm){
                $("#"+this.id+" #p_s_score").html($("#"+this.id+" td[id*='td_child_']").children().length*avgScore);
                var YSumScore=0;
                $("#"+this.id+" strong[id*='you_score']").each(function(x,m){
                    YSumScore+= parseInt($(this).html());
                });
                $("#"+this.id+" #you_sum").html(YSumScore);
            });

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
                    var splitChar="|";
                    if(t1.indexOf("%7C")!=-1)
                        splitChar="%7C";
                   var tv2=t1.split(splitChar);

                    $("input[name='ckx_answer"+t+"']").each(function(d,c){
                        for(var l=0;l<tv2.length;l++){
                            if(tv2[l]==c.value.split("|")[0]){
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
        <script type="text/javascript">
            <c:if test="${!empty quesList}">
                <c:forEach items="${quesList}" var="q" varStatus="qidx">
                   var h='';
                    //如果有父节点，则显示
                   <c:if test="${!empty q.parentQues}">
                        var qslength=$("#dv_pqs_${q.parentQues.questionid}");
                        if(qslength.length<1){
                            var pextension="${q.parentQues.extension}";
                            h+=' <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 w940"  id="dv_pqs_${q.parentQues.questionid}">';
                            h+='<caption><span class="font-blue f_right"><strong id="you_sum">0</strong>分/<span id=p_s_score></span>分</span><span class="font-blue">${qidx.index+1}</span>/10</caption>';
                            h+='<tr><td><span class="bg">';
                            if(pextension==2)//2阅读理解  3完型填空  4英语听力  5七选五
                              h+='阅读理解：';
                            else if(pextension==3)
                              h+='完型填空：';
                            else if(pextension==4)
                                h+='英语听力：';
                            else if(pextension==5)
                                h+='七选五：';
                            h+='</span>';
                            h+='${q.parentQues.content}</td></tr>';
                            h+='<tr><td id="td_child_${q.parentQues.questionid}"></td></tr>';
                            h+='</table>';
                            document.write(h);
                        }
                    </c:if>
                    var questype="${q.questiontype}";
                    var h1=' <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 w940"  id="dv_qs_${q.questionid}">';
                        <c:if test="${empty q.parentQues}">
                            h1+='<caption><span class="font-blue f_right"><strong id="you_score${q.questionid}">0</strong>分/<span id="avg_score">0</span>分</span><span class="font-blue">${qidx.index+1}</span>/10</caption>';
                         </c:if>
                        <c:if test="${!empty q.parentQues}">
                            h1+='<caption style="display:none"><span class="font-blue f_right"><strong id="you_score${q.questionid}">0</strong>分/<span id="avg_score">0</span>分</span></caption>';
                        </c:if>
                        h1+=' <tr><td>';
                        h1+='<input  type="hidden" value="${q.questiontype}" name="hd_questiontype" id="hd_questiontype_${q.questionid}"/>';
                        <c:if test="${empty q.parentQues}">
            //试题类型 1：其它 2：填空 3：单选 4：多选 6:试题组 7：单选组试题 8：多选组试题
                            if(questype==1)
                                h1+='<span class="bg">问答：</span>';
                            else if(questype==2)
                                h1+='<span class="bg">填空：</span>';
                            else if(questype==3)
                                h1+='<span class="bg">单选：</span>';
                            else if(questype==4)
                                h1+='<span class="bg">多选：</span>';
                        </c:if>
                        h1+='${qidx.index+1}、${q.content}';
                        if(questype==2){
                            h1+=' <p><strong>答题附件：</strong><span class="font-blue" id="fujian${q.questionid}"></span></p></td></tr>';
                            h1+='<tr><td><p><strong>正确答案：</strong>${q.correctanswer}</p>';
                            h1+='<p><strong>题目解析：</strong><div id="dv_right_as${q.questionid}">${q.analysis}</div></p></td></tr>';
                        }else if(questype==1){
                            h1+='</td></tr><tr><td><p><strong>学生答案：</strong><span id="dv_you_as${q.questionid}"></span></p>';
                            h1+='<p><strong>答题附件：</strong><span  class="font-blue" id="fujian${q.questionid}"></span></p>';
                            h1+='<p><strong>正确答案：</strong>${q.correctanswer}</p>';
                            h1+='<p><strong>题目解析：</strong><span id="dv_right_as${q.questionid}">${q.analysis}</span></p>';
                            h1+='</td></tr>';
                        }else if(questype==3||questype==4){
                            h1+='<span id="quesOption_${q.questionid}">';
                           <c:if test="${!empty q.questionOption}">
                              h1+='<table border="0" cellpadding="0" cellspacing="0">';
                              h1+='<col class="w30"/><col class="w910"/>';
                               <c:forEach items="${q.questionOption}" var="qo">
                                     h1+=' <tr><th>';
                                     if(questype==3)
                                        h1+='<input type="radio" disabled="true" value="${qo.optiontype}" name="rdo_answer${qo.questionid}" id="rdo_answer${qo.ref}">';
                                     else
                                        h1+='<input type="checkbox" disabled="true" value="${qo.optiontype}|${qo.isright}" id="ckx_answer${qo.ref}" name="ckx_answer${qo.questionid}">';

                                     h1+='</th><td>';
                                    if(questype==3)
                                        h1+='<label for="rdo_answer${qo.ref}">${qo.optiontype}.${qo.content}</label>';
                                    else
                                        h1+='<label for="rdo_answer${qo.ref}">${qo.optiontype}.${qo.content}</label>';
                                    <c:if test="${qo.isright==1}">
                                        h1+='<span class="ico12"></span>';
                                    </c:if>
                                    h1+='</td></tr>';
                                </c:forEach>
                            h1+='</table>';
                           </c:if>
                            h1+='</span>'
                            h1+='</td></tr>';
                        }
                        h1+='</table>';
            <c:if test="${!empty q.parentQues}">
                 $("#td_child_${q.parentQues.questionid}").append(h1);
            </c:if>
            <c:if test="${empty q.parentQues}">
                document.write(h1);
            </c:if>
                </c:forEach>
            </c:if>
    </script>
    </div>
</div>
</body>
</html>
