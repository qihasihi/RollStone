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
    <script type="text/javascript">
        var quesSize="${quesSize}";
        var courseid="${courseid}";
        var taskid="${taskid}";
        var subQuesId=",";
        $(function(){
            loadQuesNumberTool();
            next(1);
        });
        function loadQuesNumberTool(){
            var h='';
            for(i=1;i<=quesSize;i++){
                h+=' <li class="blue"><a href="javascript:next('+i+');">'+i+'</a></li>'
            }
            $("#dv_ques_number").html(h);
        }

        function next(no,t){
            var pnoDiv=$("#dv_question>div").filter(function(){return this.style.display!='none';});
            $("#dv_ques_number li").attr("class","");
            //加载已经做过的题
            if(subQuesId.length>1){
                var tmpsubid=subQuesId.substring(1)
                tmpsubid=tmpsubid.substring(0,tmpsubid.length-1);
                var idxArray=tmpsubid.split(",");
                if(idxArray.length>0){
                    for(z=0;z<idxArray.length;z++){
                        var orderIdx=$("#hd_ques_"+idxArray[z]+"_idx").val();
                        $("#dv_ques_number li").eq(orderIdx).attr("class","blue");
                    }
                }
            }
            //定位正在做的任务（题号及题正文）
                if(typeof(t)=="undefined"||t>=0)
                    pnoDiv=pnoDiv.next();
                else
                    pnoDiv=pnoDiv.prev();
            if(typeof(no)=="undefined"||isNaN(no))
                $("#dv_ques_number li").eq(pnoDiv.children("input[id*='_idx']").val()).attr("class","yellow");
            if(typeof(no)!="undefined"&&!isNaN(no)){
                var tmpno=no;
                if(no>0&&parseInt(quesSize)>=no)
                   tmpno=tmpno-1;
                else
                    tmpno=tmpno-2;
                pnoDiv=$("#dv_question>div").eq(tmpno);
                $("#dv_ques_number li").eq(tmpno).attr("class","yellow");
            }




            if(pnoDiv.length<1){
                if(typeof(t)=="undefined"||t>=0)
                    if(confirm('已经答到最后一题了!是否提交试卷?'))
                      //  location.href="paperques?m=toTestDetail&paperid="+$("#hd_paper_id").val();
                        subPaper();//交卷
                else if(t==-1)
                    alert('已经是第一题了!');
            }else{
                $("#dv_question>div").css("display","none");
                pnoDiv.show();
            }
        //ques=pnoDiv.children().filter(function(){return this.name=='hd_quesid'}).val()
        }
        /**
        *提交测试
         */
        function subPaper(){

            //提交
            var pid=$("#hd_paper_id").val();
            if(pid.length<1){
                alert('异常错误，参数异常!');return;
            }
            if(!confirm('您确认提交该试卷吗?\n\n提示：每道题答完后都已经存入，如您现在交卷则在相关时间段内无法再次做答!'))
            return;

            $.ajax({
                url:"paperques?m=doInPaper",
                dataType:'json',
                type:'post',
                data:{paperid:pid,courseid:courseid,taskid:taskid},
                cache: false,
                error:function(){
                    alert('异常错误!系统未响应!');
                },success:function(rps){
                    if(rps.type=="success"){
                        //alert(rps.msg);
                        // 进入详情页面
                        location.href="paperques?m=toTestDetail&paperid="+pid;
                    }else
                         alert(rps.msg);
                }
            })
        }

        /**
         *提交测试
         */
        function subOnePaper(quesid){
            var quesAnswerObj=$("#dv_question div input[id='hs_val_"+quesid+"']");
            var quesidObj=$("#dv_question div input[id='hd_quesid_"+quesid+"']");
            var scoreObj=$("#dv_question div input[id='hs_val_stu_"+quesid+"']");
            var questype=$("#dv_question div input[id='hd_questiontype_"+quesid+"']");
            var data=new Array(),noanswer=new Array();
            if(quesAnswerObj.length>0&&quesAnswerObj.length==quesidObj.length&&scoreObj.length==quesidObj.length){
                for(var i=0;i<quesidObj.length;i++){
                    if(quesAnswerObj[i].value.length<1){
                        noanswer[noanswer.length]=i+1;
                    }
                    data[i]={questionid:quesidObj[i].value,answer:quesAnswerObj[i].value,score:scoreObj[i].value,questype:questype[i].value};
                }
            }
//            if(noanswer.length>0){
//                var msg="您还有 "+noanswer.length+"道题没有做!是"
//                for(var i=0;i<noanswer.length;i++){
//                    msg+="第"+noanswer[i]+"题 "
//                }
//                msg+="是否继续提交?";
//                if(!confirm(msg)){
//                    next(noanswer[0]);
//                }
//            }
            //提交
            var pid=$("#hd_paper_id").val();
            if(pid.length<1){
                alert('异常错误，参数异常!');return;
            }
            $.ajax({
                url:"paperques?m=doSaveTestPaper",
                dataType:'json',
                type:'post',
                data:{paperid:pid,testQuesData:$.toJSON(data)},
                cache: false,
                error:function(){
                    alert('异常错误!系统未响应!');
                },success:function(rps){
                    if(rps.type=="success"){
                        if(subQuesId.indexOf(","+quesid+",")<0)
                            subQuesId+=quesid+","
                        next();
                    }else
                        alert(rps.msg);
                }
            })
        }


        /**
        *答完一题
        * @param quesid 问题ID
        * @param quesAnswerid 问题答案ID
         */
        function doAnswerOne(quesid,quesAnswerid,o){
            var isanswer=true;
            if(typeof(quesid)!="undefined"){
                //得到题目类型   3,4自动判断     其它的教师评阅
                var questype=$("#hd_questiontype_"+quesid).val();
                if(questype==3){
                    var answerid=quesAnswerid;
                    if(typeof(quesAnswerid)=="undefined"||quesAnswerid.length<1){
                        var rdoVal=$("#quesOption_"+quesid+"~th input[type='radio']:checked");
                        if(rdoVal.length>0){
                            answerid=rdoVal.val();
                        }else
                            isanswer=false;
                    }
                    if(isanswer){
                        $("#hs_val_"+quesid).val(quesAnswerid);
                        if(o==1){
                            $("#hs_val_stu_"+quesid).val($("#hs_score_"+quesid).val());
                        }else
                            $("#hs_val_stu_"+quesid).val(0);
                    }
                  }else if(questype==4){
                    var ckSeledtedBox=$("#quesOption_"+quesid+" th input[type='checkbox']:checked");
                    var answer="";
                    var isright=true;
                    if(ckSeledtedBox.length>0){
                        $.each(ckSeledtedBox,function(idx,itm){
                            var val=itm.value;
                            if(val.split("|")[1]!=1)
                                isright=false;
                            answer+=(answer.length>0?"|":"")+val.split("|")[0];
                        });
                        if(isright)
                            $("#hs_val_stu_"+quesid).val($("#hs_score_"+quesid).val());
                        else
                            $("#hs_val_stu_"+quesid).val(0);
                    }
                    if(answer.length>0){
                     $("#hs_val_"+quesid).val(answer);
                    }else
                        isanswer=false;
                }else if(questype==2){
                    var tkObj=$("#dv_qs_"+quesid+" input[name='txt_tk']");
                    var answer="";
                    if(tkObj.length>0){
                        $.each(tkObj,function(idx,itm){
                            answer+=(answer.length>0?"|":"")+itm.value;
                        });
                    }
                    if(answer.length>0){
                        $("#hs_val_"+quesid).val(answer);
                        $("#hs_val_stu_"+quesid).val($("#hs_score_"+quesid).val());
                    }else
                        isanswer=false;
                }else if(questype==1){
                    var answerVal=$("#txt_answer_"+quesid).val();
                    if(answerVal.length>0){
                        $("#hs_val_"+quesid).val(answerVal);
                        $("#hs_val_stu_"+quesid).val($("#hs_score_"+quesid).val());
                    }else
                        isanswer=false;
                }
                //
                if(isanswer)
                    subOnePaper(quesid);
                else
                    next();
            }
        }
    </script>
</head>
<body>
<input type="hidden" value="${paperid}" name="hd_paper_id" id="hd_paper_id"/>
<div class="subpage_head"><span class="ico55"></span><strong>
    <c:if test="${!empty paperObj&&paperObj.papertype==3}">成卷测试</c:if>
    <c:if test="${!empty paperObj&&paperObj.papertype==4}">自主测试</c:if>
</strong></div>
<div class="content1">
    <p class="t_r"><span class="ico_time"></span><strong>14天19时54分</strong></p>
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
            <c:if test="${q.questiontype==2}">
                <script type="text/javascript">
                    var h1='${q.content}';
                    h1=replaceAll(h1,'<span name="fillbank"></span>','<input type="text" name="txt_tk" id="txt_tk_${q.questionid}"/>');
                    document.write(h1);
                </script>
            </c:if>
            <c:if test="${q.questiontype!=2}">
                ${q.content}
            </c:if>
            </p>
            <c:if test="${q.questiontype==1}">
                <div class="p_t_20"><textarea name="txt_answer" id="txt_answer_${q.questionid}"  placeholder="输入你的答案"></textarea></div>
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
<%@include file="/util/foot.jsp"%>
</body>
</html>
