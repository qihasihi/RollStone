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
        $(function(){
            loadQuesNumberTool();
            next(1);
        });
        function loadQuesNumberTool(){
            var h='';
            for(i=1;i<=quesSize;i++){
                h+='<a href="javascript:;" onclick="next('+i+')">['+i+']</a>&nbsp;&nbsp;'
            }
            $("#dv_ques_number").html(h);
        }

        function next(no,t){
            var pnoDiv=$("#dv_question div").filter(function(){return this.style.display!='none';});
                if(typeof(t)=="undefined"||t>=0)
                    pnoDiv=pnoDiv.next();
                else
                    pnoDiv=pnoDiv.prev();
            if(typeof(no)!="undefined"&&!isNaN(no)){
                if(no>0&&parseInt(quesSize)>=no)
                    pnoDiv=$("#dv_question div").eq(no-1);
                else
                    pnoDiv=$("#dv_question div").eq(no-2);
            }
            if(pnoDiv.length<1){
                if(typeof(t)=="undefined"||t>=0)
                    if(confirm('已经答到最后一题了，是否交卷?'))
                        subPaper();//交卷
                else if(t==-1)
                    alert('已经是第一题了!');
            }else{
                $("#dv_question div").css("display","none");
                pnoDiv.show();
            }
        //ques=pnoDiv.children().filter(function(){return this.name=='hd_quesid'}).val()
        }
        /**
        *提交测试
         */
        function subPaper(){
            var quesAnswerObj=$("#dv_question div input[name='hd_answer']");
            var quesidObj=$("#dv_question div input[name='hd_quesid']");
            var scoreObj=$("#dv_question div input[name='hd_stu_score']");
            var questype=$("#dv_question div input[name='hd_questiontype']");
            var data=new Array(),noanswer=new Array();
            if(quesAnswerObj.length>0&&quesAnswerObj.length==quesidObj.length&&scoreObj.length==quesidObj.length){
                for(var i=0;i<quesidObj.length;i++){
                    if(quesAnswerObj[i].value.length<1){
                        noanswer[noanswer.length]=i+1;
                    }
                    data[i]={questionid:quesidObj[i].value,answer:quesAnswerObj[i].value,score:scoreObj[i].value,questype:questype[i].value};
                }
            }
            if(noanswer.length>0){
                var msg="您还有 "+noanswer.length+"道题没有做!是"
                for(var i=0;i<noanswer.length;i++){
                    msg+="第"+noanswer[i]+"题 "
                }
                msg+="是否继续提交?";
                if(!confirm(msg)){
                    next(noanswer[0]);
                }
            }
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
                    if(rps.type=="sucess"){
                        //alert(rps.msg);
                        // 进入详情页面
                        location.href="paperques?m=watchAnswer&paperid="+pid;
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
            if(typeof(quesid)!="undefined"){
                //得到题目类型   3,4自动判断     其它的教师评阅
                var questype=$("#hd_questiontype_"+quesid).val();
                if(questype==3){
                    $("#hs_val_"+quesid).val(quesAnswerid);
                    if(o==1){
                        $("#hs_val_stu_"+quesid).val($("#hs_score_"+quesid).val());
                    }else
                        $("#hs_val_stu_"+quesid).val(0);
                  }else if(questype==4){
                    var ckSeledtedBox=$("#quesOption_"+quesid+" ul li input[type='checkbox']:checked");
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
                    $("#hs_val_"+quesid).val(answer);
                }else if(questype==2){
                    var tkObj=$("#dv_qs_"+quesid+" input[name='txt_tk']");
                    var answer="";
                    if(tkObj.length>0){
                        $.each(tkObj,function(idx,itm){
                            answer+=(answer.length>0?"|":"")+itm.value;
                        });
                    }
                    $("#hs_val_"+quesid).val(answer);
                    $("#hs_val_stu_"+quesid).val($("#hs_score_"+quesid).val());
                }else if(questype==1){
                    $("#hs_val_"+quesid).val($("#txt_answer_"+quesid).val());;

                    $("#hs_val_stu_"+quesid).val($("#hs_score_"+quesid).val());
                }
                //
                next();
            }
        }
    </script>
</head>
<body>
<input type="hidden" value="${paperid}" name="hd_paper_id" id="hd_paper_id"/>
    <div id="dv_ques_number">
    </div>
   <div id="dv_question">
        <c:if test="${!empty quesList}">
            <c:forEach items="${quesList}" var="q" varStatus="qidx">
            <div style="display:none" id="dv_qs_${q.questionid}">
                <input type="hidden" value="${q.questionid}" id="hd_quesid_${q.questionid}" name="hd_quesid"/>
                <input type="hidden" value="" name="hd_answer" id="hs_val_${q.questionid}"/>
                <input type="hidden" value="0" name="hd_stu_score" id="hs_val_stu_${q.questionid}"/>
                <input type="hidden" value="${q.score}" name="hd_score" id="hs_score_${q.questionid}"/>
                <input  type="hidden" value="${q.questiontype}" name="hd_questiontype" id="hd_questiontype_${q.questionid}"/>
                    ${qidx.index+1}、
               <c:if test="${q.questiontype==2}">
                <script type="text/javascript">
                    var h1='${q.content}';
                    h1=replaceAll(h1,'<span name="fillbank"></span>','<input type="text" name="txt_tk" id="txt_tk_${q.questionid}" style="width:50px"/>');
                    document.write(h1);
                </script>
                   <p><input type="button" value="确定" onclick="doAnswerOne(${q.questionid},undefined,undefined,${q.questiontype})"/></p>
                </c:if>
                <c:if test="${q.questiontype!=2}">
                    ${q.content}
                </c:if>
                <c:if test="${q.questiontype==1}">
                    <textarea id="txt_answer_${q.questionid}" name="txt_answer"></textarea>
                    <input type="button" value="确定" onclick="doAnswerOne(${q.questionid},undefined,undefined,${q.questiontype})"/>
                </c:if>
                    <span id="quesOption_${q.questionid}">
                         <c:if test="${q.questiontype==3||q.questiontype==4}">
                             <c:if test="${!empty q.questioninfo.questionOption}">
                                 <ul>
                                     <c:forEach items="${q.questioninfo.questionOption}" var="qo">
                                         <li>
                                             <%//单选 %>
                                             <c:if test="${q.questiontype==3}">
                                                 <input type="radio" onclick="doAnswerOne(${q.questionid},this.value,${qo.isright})" value="${qo.optiontype}" name="rdo_answer${qo.questionid}">${qo.optiontype}.${qo.content}
                                             </c:if>
                                             <%//多选 %>
                                             <c:if test="${q.questiontype==4}">
                                                 <input type="checkbox" value="${qo.optiontype}|${qo.isright}" name="rdo_answer${qo.questionid}">${qo.optiontype}.${qo.content}
                                             </c:if>
                                         </li>
                                     </c:forEach>
                                 </ul>
                                 <c:if test="${q.questiontype==4}">
                                     <input type="button"  onclick="doAnswerOne(${q.questionid},undefined,undefined,${q.questiontype})" value="确定"/>
                                 </c:if>
                             </c:if>
                         </c:if>
                    </span>


            </div>
            </c:forEach>
        </c:if>
   </div>
    <div id="dv_ques_tool">
        <a href="javascript:;" onclick="next(undefined,-1)">上一题</a>
        <a href="javascript:;" onclick="next()">下一题</a>
        <a href="javascript:;" onclick="subPaper()">交卷</a>
    </div>
</body>
</html>
