<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-7-4
  Time: 上午10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var questiontype = "${detail.QUESTION_TYPE2!=null?detail.QUESTION_TYPE2:detail.QUESTION_TYPE}";
        var extension = "${detail.EXTENSION2!=null?detail.EXTENSION2:detail.EXTENSION}";
        var tabidx = "${param.tabidx}";
        var quesids = "${allscoreObj}";
        var currentQuesId="${quesid}";
        var typename;
        var extenname;
        var sign=${sign};
        var isHaveParent = ${sign};

        switch (parseInt(extension)){
            case 0:
                extenname="客观";
                break;
            case 1:
                extenname="主观";
                break;
            case 2:
                extenname="阅读理解";
                break;
            case 3:
                extenname="完形填空";
                break;
            case 4:
                extenname="英语听力";
                break;
            case 5:
                extenname="七选五";
                break;
            case 6:
                extenname="组试题";
                break;
        }
        switch (parseInt(questiontype)){
            case 1:
                typename="问答题";
                break;
            case 2:
                typename="填空题";
                break;
            case 3:
            case 7:
                typename="单选";
                break;
            case 4:
            case 8:
                typename="多选";
                break;
            case 5:
                typename="资源学习";
                break;
            case 6:
                typename=extenname;
                break;
        }

        function nextPiYue(pid,tid){
            if(typeof(pid)=="undefined"||pid==null||typeof(tid)=='undefined'||tid==null){
                alert('异常错误，原因：参数错误!');return;
            }
            $.ajax({
                url:"paper?m=nextPiYue",
                type:"post",
                data:{paperid:pid,taskid:tid,ismarking:1},
                dataType:'json',
                cache: false,
                error:function(){
                    alert('系统未响应，请稍候重试!');
                },success:function(rmsg){
                    if(rmsg.type=="error"){
                        alert(rmsg.msg);return;
                    }
                    if(rmsg.objList.length>0){
                        var qsid=rmsg.objList[0].quesid;
                        if(confirm('当前试题已批改完成，是否继续批改？')){
                            var htm='paper?m=toMarkingDetail';
                            <%--if(isHaveParent){--%>
                            <%--htm+='&questionid=${param.questionid}';--%>
                            <%--}--%>
                            //根据quesid得到值tabidx
                            var quesNo=1;
                            var quesArray=quesids.split(",");
                            if(quesArray.length>0){
                                $.each(quesArray,function(ix,im){
                                    var q=im;
                                    if(im.indexOf("|")!=-1){
                                        q=im.split("|")[1];
                                    }
                                    if(qsid==q){
                                        quesNo=ix+1;
                                    }
                                });
                            }


                            htm+='&tabidx='+(quesNo-1)+'&paperid=${param.paperid}&quesid='+qsid+'&idx='+quesNo;
                            htm+='&classid=${param.classid}&classtype=${param.classtype}&sign='+sign+'&taskid=${param.taskid}';
                            window.location.href=htm;
                        }
                    }
                }
            });
        }


        var currentIdx=-1;
        $(function(){
            if($("span[name='fillbank']").length>0){
                $("span[name='fillbank']").css({
                    "border-bottom":'1px solid black'
                });
                $("span[name='fillbank']").html(
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                );
            }
            //上一题，下一题控制
            showQuesOperate();

            if(sign){
                if(questiontype==1||questiontype==2){
                        <c:if test="${!empty param.isvalidatePiYue&&param.isvalidatePiYue==1}">
                         nextPiYue(${param.paperid},${param.taskid});
                        </c:if>
                    $("#score_div").hide();
                }else{
                    $("#score").hide();
                }
            }else{
                var totalnum= "${score}";
                var n = Math.ceil((parseFloat(totalnum)+0.5)/10);
                var divhtm='';
                for(var i = 0;i<n;i++){
                    divhtm+='<div class="layout">';
                    var ulhtm='<ul>';
                    var lihtm='';
                    for(var j =0;j<10;j++){
                        lihtm+='<li style="padding-left:1px"';
//                        if(j==0){
//                            lihtm+=' class="crumb"';
//                        }
                        lihtm+=' ><a href="1">'+(j+i*10)+'</a></li>';
                        if(parseFloat((j+i*10))>=parseFloat(totalnum-0.5)){
                            break;
                        }
                    }
                    ulhtm+=lihtm+'</ul>';

                    var ulhtm2='<ul>';
                    var lihtm2='';
                    for(var k =0.5;k<10.5;k++){
                        if(parseFloat((k+i*10))>=parseFloat(totalnum))
                            break;
                        lihtm2+='<li style="padding-left:1px"><a href="javascript:submitScore(${detail.REF},'+(k+i*10)+')">'+(k+i*10)+'</a></li>';

                    }
                    ulhtm2+=lihtm2+'</ul>';
                    divhtm+=ulhtm+ulhtm2+'</div>';
                }
                $("#score_div").append(divhtm);
            }
            $("#tname").html(typename);

        });
        /**
        *显示，隐藏上一题，下一题
         */
        function showQuesOperate(){
            if(quesids.length>0){
                var quesObjArray=quesids.split(",");
                if(quesObjArray.length>0){
                    $.each(quesObjArray,function(ix,im){
                        var tmpQuesid=im;
                        if(im.indexOf("|")!=-1){
                            tmpQuesid=im.split("|")[1];
                        }
                        if(tmpQuesid==currentQuesId){
                            currentIdx=ix;
                        }
                    })
                }
                if(currentIdx==0)
                    $("#upquestion").remove();
                if(currentIdx==quesObjArray.length-1)
                    $("#nextquestion").remove();
            }
        }



        function submitScore(ref,score){

            $.ajax({
                url:"paper?m=doMarking",
                type:"post",
                data:{ref:ref,score:score},
                dataType:'json',
                cache: false,
                error:function(){
                    alert('系统未响应，请稍候重试!');
                },success:function(rmsg){
                    <%--nextPiYue(${param.paperid},${param.taskid});                    --%>
                    var htm='paper?m=toMarkingDetail';
                    if(isHaveParent){
                        htm+='&questionid=${param.questionid}';
                    }
                    htm+='&tabidx=${param.tabidx}&paperid=${param.paperid}&quesid=${quesid}&idx=${param.idx}';
                    htm+='&classid=${param.classid}&classtype=${param.classtype}&sign='+sign+'&taskid=${param.taskid}&isvalidatePiYue=1';
                    window.location.href=htm;
                }
            });

        }
        function tabQuestion(type){
            /**
            *判断是否在该题中
             */
            if(quesids.length>0){
                var quesObjArray=quesids.split(",");
                if(quesObjArray.length>0){
                    if(currentIdx==-1){
                        alert('异常错误，没有该题!');
                        return;
                    }
                }
                //上一题
                if(type==1){
                    //处理上一题下一题按钮
                    if(currentIdx==0){
                        alert('你发现一个严重的问题!');return;
                    }

                        //var orderidx = tabidx;
                        var id =quesObjArray[currentIdx-1];
                        var sign = false;
                        var questionid;
                     var quesidsArray=quesids.split(",");
                        if(quesObjArray[currentIdx-1].indexOf("|")!=-1){
                            questionid = quesObjArray[currentIdx-1].split("|")[0];
                            sign = true;
                            id=quesObjArray[currentIdx-1].split("|")[1];
                        }
                       var  htm='paper?m=toMarkingDetail';
                        if(sign){
                            htm+='&questionid='+questionid;
                        }
                    //'&tabidx='+(tabidx-1)+
                        htm+='&taskid=${param.taskid}&paperid=${param.paperid}&quesid='+id+'&idx='+currentIdx;
                    htm+='&classid=${param.classid}&classtype=${param.classtype}&sign='+sign;
                        $("#upquestion").attr("href",htm);

                }else{
                    //处理上一题下一题按钮
                    if(quesids.length>0){
                        var quesidsArray=quesids.split(",");
                        var id = quesidsArray[currentIdx+1];
                        var sign = false;
                        var questionid;
                        if(quesidsArray[currentIdx+1].split("|").length>1){
                            questionid = quesidsArray[currentIdx+1].split("|")[0];
                            sign = true;
                            id=quesidsArray[currentIdx+1].split("|")[1];
                        }
                       var  htm='paper?m=toMarkingDetail';
                        if(sign){
                            htm+='&questionid='+questionid;
                        }
                        htm+='&taskid=${param.taskid}&tabidx='+(currentIdx+1)+'&paperid=${param.paperid}&quesid='+id+'&idx='+(currentIdx+2);
                        htm+='&classid=${param.classid}&classtype=${param.classtype}&sign='+sign;
                        $("#nextquestion").attr("href",htm);
                    }
                }
            }
        }
    </script>
</head>
<body>
    <div class="subpage_head"><span class="ico55"></span><strong>批阅试卷</strong></div>
    <div class="content2">
        <p class="jxxt_zhuanti_rw_piyue_info"><a href="javascript:;" onclick="tabQuestion(2)"  id="nextquestion" class="an_public3">下一题</a>
            <a id="upquestion" href="javascript:;" onclick="tabQuestion(1)" class="an_public3">上一题</a><strong class="font-blue">${param.idx}</strong>&nbsp;&nbsp;试题分数：<span class="font-blue">${score}</span>&nbsp;分</p>
        <div class="jxxt_zhuanti_shijuan_add public_input font-black">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 w940">
                <c:if test="${!empty detail.CONTENT2}">
                    <tr>
                        <td><span class="bg"  id="tname"></span>${detail.CONTENT2}</td>
                    </tr>
                    <tr>
                        <td>${detail.ORDERIDX}、${detail.CONTENT}
                            <c:if test="${!empty option}">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <col class="w910"/>
                                    <c:forEach items="${option}" var="itm">
                                        <tr>
                                            <%--<th><input type="radio" name="radio"  disabled  value="radio"></th>--%>
                                            <td><span>${itm.optiontype}</span>. ${itm.content}
                                                    <c:if test="${empty rightoption&&itm.isright==1}">
                                                        <span class="ico12"></span>
                                                    </c:if>
                                                    <c:if test="${!empty rightoption}">
                                                        <c:forEach items="${rightoption}" var="im">
                                                            <c:if test="${itm.optiontype==im.optiontype}">
                                                                <span class="ico12"></span>
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:if>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${empty detail.CONTENT2}">
                    <tr>
                        <td><span class="bg"><span id="tname"></span>：</span>${detail.CONTENT}
                            <c:if test="${!empty option}">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <%--<col class="w30"/>--%>
                                    <col class="w910"/>
                                    <c:forEach items="${option}" var="itm">
                                        <tr>
                                            <%--<th><input type="radio" disabled name="rdo_option" value="${itm.isright}|${itm.optiontype}"  value="radio"></th>--%>
                                            <td><span>${itm.optiontype}</span>. ${itm.content}
                                                <c:if test="${itm.isright==1}">
                                                    <span class="ico12"></span>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:if>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td><p><strong>正确答案：</strong><span id="right_answer">
                        <c:if test="${empty detail.CORRECT_ANSWER}">
                            <script type="text/javascript">
                                var rightHtm="";
                                $(".ico12").each(function(idx,itm){
                                    rightHtm+=$(itm).parent().children().first().html()+"&nbsp;";
                                });
                                document.write(rightHtm);
                            </script>
                        </c:if>
                        <c:if test="${!empty detail.CORRECT_ANSWER}">${detail.CORRECT_ANSWER}</c:if>
                    </span></p>
                        <p><strong>答案解析：</strong>${detail.ANALYSIS}</p></td>
                </tr>
            </table>


            <%--选择题--%>
            <c:if test="${!empty detail.QUESTION_TYPE&&(detail.QUESTION_TYPE==7||detail.QUESTION_TYPE==8||detail.QUESTION_TYPE==3||detail.QUESTION_TYPE==4)}">
                <p class="p_t_10"><strong>平均分：</strong><span class="font-blue">
                <script type="text/javascript">var avgFloat="${num.AVGSCORE}";document.write(parseFloat(avgFloat).toFixed(2));</script>
                </span>分&nbsp;&nbsp;&nbsp;&nbsp;
                        <%--<strong>已批改：</strong><span class="font-blue">4/20</span>&nbsp;&nbsp;&nbsp;&nbsp;--%>
                    <a target="_blank" href="paper?m=toMarkingLogs&paperid=${param.paperid}&quesid=${quesid}&idx=${param.idx}&classid=${param.classid}&taskid=${param.taskid}" class="font-blue">查看统计</a></p>
                <p><strong>正确率：</strong>${zqlMap.ZQL}%</p>
                <div class="jxxt_zhuanti_rw_tongji">
                    <div class="right"><img src="paper/img/${param.paperid}/${quesid}/${param.classid}/${param.taskid}/write" width="193" height="140"></div>
                    <div class="left"><strong>分项统计：</strong>
                        <table border="0" cellspacing="0" cellpadding="0" class="public_tab3">
                            <colgroup span="2" class="w100">
                            </colgroup>
                            <tr>
                                <td>选项</td>
                                <td>百分比 </td>
                            </tr>
                            <c:if test="${!empty optTJMapList}">
                                <c:forEach var="optTj" items="${optTJMapList}">
                                    <tr>
                                        <td>${optTj.OPTION_TYPE}</td>
                                        <td>${optTj.BILI}%</td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </table>
                    </div>
                </div>

            </c:if>
        </div>
        <div class="jxxt_zhuanti_rw_piyue_defen" id="score">
            <h5></h5>
            <p class="p_b_20"><strong>平均分：</strong><span class="font-blue">
                <c:if test="${num.MARKINGNUM==0}">---</c:if>
                <c:if test="${num.MARKINGNUM>0}">
                <script type="text/javascript">var avgFloat="${num.AVGSCORE}";document.write(parseFloat(avgFloat).toFixed(2));</script></span>分
                </c:if>
                <!--<span class="font-blue"></span>-->
                &nbsp;&nbsp;&nbsp;&nbsp;<strong>已批改：</strong>${num.MARKINGNUM}/${num.SUBMITNUM}&nbsp;&nbsp;&nbsp;&nbsp;<a href="paper?m=toMarkingLogs&taskid=${param.taskid}&paperid=${param.paperid}&quesid=${quesid}&idx=${param.idx}&classid=${param.classid}" target="_blank" class="font-blue">查看统计</a></p>
            <div id="score_div">
            <p><strong>学生：</strong>${detail.STU_NAME} </p>
            <p><strong>答案：</strong>${detail.ANSWER}</p>
            <c:if test="${!empty detail.ANNEXNAME}">

                <p><strong>附件：</strong><a href="<%=basePath%>/<%=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")%>/${detail.ANNEXNAME}" target="_blank" class="font-blue">${detail.ANNEXNAME}</a></p>
            </c:if>

            <p><strong>评分：</strong>请选择该学生的得分。</p>
            </div>
        </div>
    </div>
    <%@include file="/util/foot.jsp" %>
</body>
</html>
