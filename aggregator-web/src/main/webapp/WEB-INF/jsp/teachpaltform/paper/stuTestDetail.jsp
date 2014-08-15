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
    <script type="text/javascript" src="js/common/videoPlayer/new/jwplayer.js"></script>
    <script type="text/javascript" src="flexpaper/swfobject/swfobject.js"></script>
    <script type="text/javascript">
        var paperSumScore=${!empty paperObj.score?paperObj.score:100};
        var papertype="${paperObj.papertype}";
        var allquesid="${allquesidObj}";
        var allscore="${allscoreObj}";
        var isshowfen=true;

        function userAnswer(t,t1,score,nexName){
            var qtObj=$("#hd_questiontype_"+t);
            if(qtObj.length>0&&qtObj.val().length>0){
                $("#you_score"+t).html(score);
                  var qtype=qtObj.val().Trim();
                if(qtype==7) //单选组 按单选入库
                    qtype=3;
                else if(qtype==8)   //复选组，按复选入库
                    qtype=4;

                if(qtype==1){
                    $("#you_score"+t).html("待批改");
                    $("#dv_you_as"+t).html(t1);
                    if(nexName!="undefined"&&nexName.length>0){
                        $("#fujian"+t).html("<a target='_blank' href='<%=basePath%>/"+nexName+"'>"+nexName.substring(nexName.lastIndexOf("/")+1)+"</a>");
                        $("#fujian"+t).parent().show();
                    }

                }else if(qtype==2){ //1:问题  2：填空 3：单选  4：多选
                    $("#you_score"+t).html("待批改");
                    var t2=t1.split("|");
                    var t3=$("#dv_qs_"+t+" span[name='fillbank']");
                    if(t2.length==t3.length){
                        $.each(t3,function(a,b){
                            $(this).html("&nbsp;&nbsp;"+t2[a]+"&nbsp;&nbsp;");
                        });
                    }


                    if(nexName!="undefined"&&nexName.length>0){
                        $("#fujian"+t).html("<a target='_blank' href='<%=basePath%>/"+nexName+"'>"+nexName.substring(nexName.lastIndexOf("/")+1)+"</a>");
                        $("#fujian"+t).parent().show();
                    }

                }else if(qtype==3){
                    var obj=$("input[name='rdo_answer"+t+"']").filter(function(){return this.value.Trim()==t1});
                    var answerObj={val:obj[0].value,id:obj[0].id,name:obj[0].name};
                    obj.parent().html("<input type='radio' checked=true id='"+answerObj.id+"' disabled=true name='"+answerObj.name+"' value='"+answerObj.value+"'>");
                }else if(qtype==4){
                    var splitChar="|";
                    if(t1.indexOf("%7C")!=-1)
                        splitChar="%7C";
                    var tv2=t1.split(splitChar);

                    $("input[name='ckx_answer"+t+"']").each(function(d,c){
                        for(var l=0;l<tv2.length;l++){
                            if(tv2[l]==c.value.split("|")[0]){
                                var answerObj={val:c.value,id:c.id,name:c.name};
                                $(c).parent().html("<input type='checkbox' checked=true id='"+answerObj.id+"' disabled=true name='"+answerObj.name+"' value='"+answerObj.value+"'>");
                          }
                        }
                    });
                }
            }
        }


        function updateQuesIdx(){
            $("table[id*='dv_pqs_']").each(function(idx,itm){
                //得到主题干的ID
                var pqid=$(this).attr("id").replace("dv_pqs_","");
                //子项的数量
                var childLen=$("#td_child_"+pqid+">table").length;
                //得到他当前的索引
                var cidx= $.trim($("#sp_qidx"+pqid).html());
                var h='';
                if(childLen<1)
                    h=cidx;
                else
                    h=cidx+"-"+(parseInt(cidx)+childLen-1);
                $("#sp_qidx"+pqid).html(h);
            })

        }
    </script>
</head>
<body>
<body>
<div class="subpage_head"><span class="ico55"></span><strong>查看试卷</strong>
    <c:if test="${taskIsend==1&&empty stuAnswer}">
     <span style="float:right;color:red;font-weight:bold;font-size:14px;vertical-align: middle;">该任务已结束，您没有作答!&nbsp;&nbsp;&nbsp;&nbsp;</span>
    </c:if>
</div>
<div class="content2" id="dv_test">
    <div class="jxxt_zhuanti_shijuan_add font-black public_input"  id="dv_question">
        <c:if test="${!empty paperObj&&(paperObj.papertype==3||paperObj.papertype==1||paperObj.papertype==2)}">
        <p class="title"><span class="f_right"><strong>得分：<span class="font-blue"><span id="sp_sumScore"></span></span></strong></span>
            <strong>${paperObj.papername}&nbsp;</strong>
        </p>
        </c:if>
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
                h+='<caption class="font-blue"><span class="f_right"><strong id="you_sum">0</strong>/<span id=p_s_score>';
                if(papertype==3||papertype==1||papertype==2)
                    h+=${q.score}+"";
                h+='</span>分</span><span id="sp_qidx${q.parentQues.questionid}"">${qidx.index+1}</span></caption>';
                h+='<tr><td><span class="bg">';
                if(pextension==2)//2阅读理解  3完型填空  4英语听力  5七选五
                    h+='阅读理解</span>：${q.parentQues.content}';
                else if(pextension==3)
                    h+='完形填空</span>：${q.parentQues.content}';
                else if(pextension==4){
                    //如果是英语听力，则还需要添加控件，不用添加内容。
                    h+='英语听力</span>：<br><div class="p_t_10" id="sp_mp3_${q.parentQues.questionid}"></div><br/>${q.parentQues.content}';
                }else if(pextension==5){
                    h+='七选五</span>：${q.parentQues.content}';
                    h+='<div style="display:none" id="p_option_${q.parentQues.questionid}">';
                    //选项
                    <c:if test="${!empty q.parentQues.questionOption}">
                                <c:forEach items="${q.parentQues.questionOption}" var="pqm">
                                h+='<div id="p_optchild${pqm.optiontype}"><label for="rdo_answer${pqm.questionid}${pqm.optiontype}">${pqm.optiontype}.${pqm.content}'
                                +'</label><input type="hidden" name="opt_quesid" value="${pqm.questionid}"/>' +
                                '<input type="hidden" name="opt_optiontype" value="${pqm.optiontype}"/></div>';
                    </c:forEach>
                    </c:if>
                    h+='</div>';
                }
                h+='</td></tr>';
                h+='<tr><td id="td_child_${q.parentQues.questionid}"></td></tr>';
                h+='</table>';
                //document.write(h);
                $("#dv_question").append(h);
                if(pextension==4){
                    var mp3url="<%=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>/${q.parentQues.questionid}/001.mp3";
                    playSound('play',mp3url,300,35,'sp_mp3_${q.parentQues.questionid}',false);
                }
            }
            </c:if>
            var questype="${q.questiontype}";

            var h1=' <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 w940"  id="dv_qs_${q.questionid}">';
            <c:if test="${empty q.parentQues}">
                    h1+='<caption class="font-blue"><span class="f_right"><strong id="you_score${q.questionid}">0</strong>/<span id="avg_score">0</span>分</span><span class="font-blue">${qidx.index+1}</span></caption>';
            </c:if>
            <c:if test="${!empty q.parentQues}">
                    h1+='<caption style="display:none"><span class="font-blue f_right"><strong id="you_score${q.questionid}">0</strong>/<span id="avg_score">0</span>分</span></caption>';
            </c:if>
            h1+=' <tr><td>';
            h1+='<input  type="hidden" value="${q.questiontype}" name="hd_questiontype" id="hd_questiontype_${q.questionid}"/>';
            <c:if test="${empty q.parentQues}">
            //试题类型 1：其它 2：填空 3：单选 4：多选 6:试题组 7：单选组试题 8：多选组试题
                if(questype==1){
                    h1+='<span class="bg">问答题</span>：';
                    isshowfen=false;
                } else if(questype==2){
                    h1+='<span class="bg">填空题</span>：';
                    isshowfen=false;
                }else if(questype==3)
                    h1+='<span class="bg">单选题</span>：';
                else if(questype==4)
                    h1+='<span class="bg">多选题</span>：';
            </c:if>
            h1+='${qidx.index+1}.';
            <c:if test="${!empty q.content}">
              h1+='${q.content}';
            </c:if>
            <c:if test="${empty q.parentQues||q.parentQues.extension!=5}">
                if(questype==2){
                    h1+='</td></tr><tr><td><p  style="display:none"><strong>附件：</strong><span class="font-blue" id="fujian${q.questionid}"></span></p>';
                    h1+='<p><strong>正确答案：</strong>${q.correctanswer}</p>';
                    h1+='<p><strong>答案解析：</strong><span id="dv_right_as${q.questionid}">${q.analysis}</span></p></td></tr>';
                }else if(questype==1){
                    h1+='</td></tr><tr><td><p><strong>学生答案：</strong><span id="dv_you_as${q.questionid}"></span></p>';
                    h1+='<p style="display:none"><strong>附件：</strong><span  class="font-blue" id="fujian${q.questionid}"></span></p>';
                    h1+='<p><strong>正确答案：</strong>${q.correctanswer}</p>';
                    h1+='<p><strong>答案解析：</strong><span id="dv_right_as${q.questionid}">${q.analysis}</span></p>';
                    h1+='</td></tr>';
                }
            </c:if>
             h1+='<span id="quesOption_${q.questionid}">';
            <c:if test="${!empty q.parentQues&&q.parentQues.extension==5}">
                    var optionPObj=$("#p_option_${q.parentQues.questionid}>div");
                if(optionPObj.length>0){
                    h1+='<table border="0" cellpadding="0" cellspacing="0">';
                    h1+='<col class="w30"/><col class="w910"/>';
                    $.each(optionPObj,function(x,m){
                        var opttype=$("#"+ m.id+" input[name='opt_optiontype']").val();
                        var optid=$("#"+ m.id+" input[name='opt_quesid']").val();
                         h1+='<tr><th>'
                        var ishas=false;
                        <c:if test="${!empty q.questionOption}">
                                <c:forEach items="${q.questionOption}" var="im">
                        <c:if test="${!empty im.isright&&im.isright==1}">
                            var optype="${im.optiontype}";
                        if(opttype==optype){
                            ishas=true;
                        }
                        </c:if>
                        </c:forEach>
                        </c:if>
                        h+='<tr><th>' ;
                        <c:if test="${q.questiontype==3||q.questiontype==7}">
                                h1+='<input type="radio" id="rdo_answer${q.questionid}'+opttype+'"  disabled="true"  value="'+ opttype+'" name="rdo_answer${q.questionid}">';
                        </c:if>
                        <c:if test="${q.questiontype==4||q.questiontype==8}">
                                h1+='<input type="checkbox" value="'+opttype+'|'+(ishas?1:0)+'"  disabled="true"  id="rdo_answer${q.questionid}'+opttype+'" name="rdo_answer${q.questionid}">';
                        </c:if>
                        h1+='</th>';
                        h1+=' <td>'+$(m).html().Trim()+(ishas?'<span class="ico12"></span>':'')+'</td>';
                        h1+='</tr>';
                    });
                    h1+='</table>';
                }
            </c:if>
            <c:if test="${empty q.parentQues||q.parentQues.extension!=5}">
                    if(questype==3||questype==4||questype==7||questype==8){
                    <c:if test="${!empty q.questionOption}">
                                h1+='<table border="0" cellpadding="0" cellspacing="0">';
                        h1+='<col class="w30"/><col class="w910"/>';
                    <c:forEach items="${q.questionOption}" var="qo">
                                h1+=' <tr><th>';
                        if(questype==3||questype==7)
                            h1+='<input type="radio" disabled="true" value="${qo.optiontype}" name="rdo_answer${qo.questionid}" id="rdo_answer${qo.ref}">';
                        else
                            h1+='<input type="checkbox" disabled="true" value="${qo.optiontype}|${qo.isright}" id="ckx_answer${qo.ref}" name="ckx_answer${qo.questionid}">';

                        h1+='</th><td>';
                        if(questype==3||questype==7)
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
                    }
            </c:if>
                h1+='</span>'
                h1+='</td></tr>';
            <%--<c:if test="${empty q.parentQues||q.parentQues.extension!=5}">--%>
            if(questype==3||questype==4||questype==7||questype==8){
            h1+='<tr><td>';
            h1+='<p><strong>答案解析：</strong><span id="dv_right_as${q.questionid}">${q.analysis}</span></p>';
            h1+='</td></tr>';
            }
            <%--</c:if>--%>
            h1+='</table>';
            <c:if test="${!empty q.parentQues}">
                    $("#td_child_${q.parentQues.questionid}").append(h1);
            </c:if>
            <c:if test="${empty q.parentQues}">
             $("#dv_question").append(h1);
            </c:if>
            </c:forEach>
            </c:if>
        </script>



        <!--答案-->
        <script type="text/javascript">
            $("span[name='fillbank']").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            $("span[name='fillbank']").css("border-bottom"," 1px solid black ");

            //序号重排
            updateQuesIdx();
            <c:if test="${!empty stuAnswer}">
                <c:forEach items="${stuAnswer}" var="sa">
                //得到quesid的questype
                userAnswer(${sa.quesid},"${sa.answerString}","${sa.score}","${sa.annexNameFull}");
                </c:forEach>
            </c:if>
            //计算每题的分数
            if(papertype!=3&&papertype!=1&&papertype!=2){
                var allqueslength=allquesid.split(",").length;
                var avgScore=parseInt(paperSumScore/allqueslength);
                $("span[id='avg_score']").html(avgScore);
                if(paperSumScore%allqueslength>0){
                    //$("span[id='avg_score']").last().html(avgScore+());
                    //// 使用数组翻转函数
                    var yuScore=paperSumScore%allqueslength;
                    $.each(jQuery.makeArray($("span[id='avg_score']")).reverse(),function(idx,itm){
                        if(yuScore<1)return;
                        $(itm).html(parseFloat($(itm).html())+1);
                        yuScore-=1;

                    });
                }
            }else{
                var scoreArray=allscore.split(",");
                $("span[id='avg_score']").each(function(idx,itm){
                    if(typeof(scoreArray[idx])!="undefined"){
                        $(itm).html(scoreArray[idx]);
                    }
                })
            }
            //计算是否存在试题组等题
            $("table[id*='dv_pqs_']").each(function(idx,itm){
                var teamScore=$("#"+this.id+" td[id*='td_child_']").children().length*avgScore;
                if(papertype==3||papertype==1||papertype==2){
                    teamScore=0;
                    $("#"+itm.id+" #avg_score").each(function(x,m){
                        teamScore=parseFloat(teamScore)+parseFloat($(this).html());
                    });
                }
                 $("#"+this.id+" #p_s_score").html(teamScore);
                var YSumScore=0;
                $("#"+this.id+" strong[id*='you_score']").each(function(x,m){
                    YSumScore+=parseInt($(this).html());
                });
                $("#"+this.id+" #you_sum").html(YSumScore);
            });
            if(isshowfen){
                var sumScore=0;
                $("strong[id*='you_score']").each(function(idx,itm){
                    sumScore+=parseFloat($(itm).html().Trim());
                });
                if((sumScore+"")=="NaN"){
                    $("#sp_sumScore").parent().parent().hide();
                }else
                    $("#sp_sumScore").html(sumScore+"分");
            }else{
                var fen="待批改";
                <c:if test="${empty stuAnswer}">
                    fen="0分";
                </c:if>
                $("#sp_sumScore").html(fen);
            }


        </script>

    </div>




</div>

<%@include file="/util/foot.jsp"%>
</body>
</html>
