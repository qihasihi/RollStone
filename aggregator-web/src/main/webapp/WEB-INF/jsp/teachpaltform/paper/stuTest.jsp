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
            var data=new Array(),noanswer=new Array();
            if(quesAnswerObj.length>0&&quesAnswerObj.length==quesidObj.length&&scoreObj.length==quesidObj.length){
                for(var i=0;i<quesidObj.length;i++){
                    if(quesAnswerObj[i].value.length<1){
                        noanswer[noanswer.length]=i+1;
                    }
                    data[i]={questionid:quesidObj[i].value,answer:quesAnswerObj[i].value,score:quesidObj[i].value};
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
            var js=$.toJSON(data);
            $.ajax({
                url:"paperques?m=doSaveTestPaper&testQuesData="+js,
                dataType:'json',
                type:'post',
                data:{paperid:pid},
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
                $("#hs_val_"+quesid).val(quesAnswerid);
                if(o==1){
                    $("#hd_stu_score"+quesid).val($("#hs_score_"+quesid).val());
                }else
                    $("#hd_stu_score"+quesid).val(0);
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
                <div style="display:none">
                    <input type="hidden" value="${q.questionid}" id="hd_quesid_${q.questionid}" name="hd_quesid"/>
                    <input type="hidden" value="" name="hd_answer" id="hs_val_${q.questionid}"/>
                    <input type="hidden" value="0" name="hd_stu_score" id="hs_val_stu_${q.questionid}"/>
                    <input type="hidden" value="${q.score}" name="hd_score" id="hs_score_${q.questionid}"/>
                    <span id="quesName" style="font-size:16px;">${qidx.index+1}、${q.content}
                    </span>
                    <span id="quesOption_${q.questionid}">
                        <ul>
                            <c:if test="${!empty q.questioninfo.questionOption}">
                                <c:forEach items="${q.questioninfo.questionOption}" var="qo">
                                 <li><input type="radio" onclick="doAnswerOne(${q.questionid},this.value,${qo.isright})" value="${qo.optiontype}" name="rdo_answer${qo.questionid}">${qo.optiontype}.${qo.content}</li>
                                </c:forEach>
                            </c:if>
                        </ul>
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
