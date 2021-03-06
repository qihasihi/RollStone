<%--
Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-7-4
  Time: 上午10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<%
    String quesImgPath=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH");
    Object quesid=request.getAttribute("quesid");
    if(quesid!=null||quesid.toString().length()>0)
    pageContext.setAttribute("quesImgpath",quesImgPath+"/"+quesid+"/");
%>
<html>
<head>
    <title></title>
    <script type="text/javascript">
         var _QUES_IMG_URL="<%=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>";
        var questiontype = "${detail.QUESTION_TYPE2!=null?detail.QUESTION_TYPE2:detail.QUESTION_TYPE}";
        var extension = "${detail.EXTENSION2!=null?detail.EXTENSION2:detail.EXTENSION}";
        var tabidx = "${param.tabidx}";
        var quesids = "${allquesidObj}";
        var currentQuesId="${quesid}";
        var typename;
        var extenname;
        var sign=${sign};
        var isHaveParent = ${sign};
         var papertype="${papertype}";
         var paperScore=${!empty paperScore?paperScore:0};

         var isShowComment="${!empty sessionScope.isShowComment?sessionScope.isShowComment:2}";//默认为关闭
        switch (parseInt(extension)){
            case 0:
                extenname="客观题";
                break;
            case 1:
                extenname="主观题";
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
                typename="单选题";
                break;
            case 4:
            case 8:
                typename="多选题";
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
        /**
         * 加载分数
         */
         function getScore(){
             if(papertype==1||papertype==2){
                 var allqueslength=quesids.split(",").length;
                 //得到平均分
                 var avgScore=parseInt(paperScore/allqueslength);
                 //如果还有多余
                 if(paperScore%allqueslength>0){
                     //$("span[id='avg_score']").last().html(avgScore+());
                     //// 使用数组翻转函数
                     var yuScore=paperScore%allqueslength;
                     if(tabidx.length>0&&yuScore>0){
                         var jlength=allqueslength-yuScore;
                         if(jlength<=yuScore){
                             avgScore+=1;
                         }
                     }
                 }
                 $("#sp_q_score").html(avgScore);
             }
         }

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
            //去掉内容开头P
            if($("#sp_ct2").length>0&&$("#sp_ct2>p").length>0){
                $("#sp_ct2").html($("#sp_ct2>p").html());
            }
            //上一题，下一题
            getScore();
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
                        lihtm+='<li';
                        if(j==0&&i==0){
                            lihtm+=' class="crumb"';
                        }
                        lihtm+=' style="padding-left:1px"><a href="javascript:submitScore(${detail.REF},'+(j+i*10)+')">'+(j+i*10)+'</a></li>';
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


            operateCommentP(isShowComment);
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
            var p={ref:ref,score:score};
            var txtMComment=$("#txt_mark_comment");
            if(txtMComment.length>0){
                if(txtMComment.val().Trim().length>0){
                    p.markComment=txtMComment.val().Trim();
                }
            }
            p.isShowComment=isShowComment;
            $.ajax({
                url:"paper?m=doMarking",
                type:"post",
                data:p,
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
            /**
            * 显示隐藏评语x
            * @param flag
            */
         function operateCommentP(flag){
              isShowComment=flag=typeof(flag)=="undefined"?1:flag;
             if(flag==1){//显示
                 $("#p_mark_comment").show();
                 $("#sp_op_comment").html('<a href="javascript:;" onclick="operateCommentP(2)"  style="color:blue">收起评语</a>');
             }else{
                 $("#p_mark_comment").hide();
                 $("#sp_op_comment").html('<a href="javascript:;" onclick="operateCommentP(1)"  style="color:blue">添加评语</a>');
             }
         }
    </script>
</head>
<body>
    <div class="subpage_head">
          <span class="back">
             <%--<a href="paper?m=toMarking&taskid=${param.taskid}&paperid=${param.paperid}">返回</a>--%>
         <a href="javascript:history.go(-1)">返回</a>
     </span><span class="ico55"></span><strong>批阅试卷</strong></div>
    <div class="content2">
        <p class="jxxt_zhuanti_rw_piyue_info"><a href="javascript:;" onclick="tabQuestion(2)"  id="nextquestion" class="an_public3">下一题</a>
            <a id="upquestion" href="javascript:;" onclick="tabQuestion(1)" class="an_public3">上一题</a><strong class="font-blue">${param.idx}</strong>&nbsp;&nbsp;试题分数：<span class="font-blue" id="sp_q_score">${score}</span>&nbsp;分</p>
        <div class="jxxt_zhuanti_shijuan_add public_input font-black">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 w940">
                <c:if test="${!empty detail.CONTENT2}">
                    <tr>
                        <td><span class="bg"  id="tname"></span>
                            <c:if test="${!empty showExamYearMsg}">
                                <span>(${showExamYearMsg})</span>
                            </c:if>
                            <span id="sp_ct2">${fn:replaceAll(detail.CONTENT2,"_QUESTIONPIC+",pageScope.quesImgpath)}</span><br/>--%>
                            <script type="text/javascript">
                                if(extension==4){
                                    var mp3url=_QUES_IMG_URL+"/${param.questionid}/001.mp3";
                                    var h='<img src="images/pic05_140722.png" style="cursor:pointer" onclick="ado_audio.play()" alt="听力"/>' ;
                                        h+='<span style="display:none"><audio controls="controls" id="ado_audio">';
                                        h+='<source src="'+mp3url+'" type="audio/ogg">';
                                        h+='<source src="'+mp3url+'" type="audio/mpeg">';
                                        h+='您的浏览器不支持 audio 标签。</audio></span>';
                                    document.write(h);
                                }
                            </script>
                        </td>
                    </tr>

                    <tr>
                        <td>${detail.ORDERIDX}、${fn:replaceAll(detail.CONTENT,"_QUESTIONPIC+",pageScope.quesImgpath)}
                            <c:if test="${!empty option}">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <col class="w910"/>
                                    <c:forEach items="${option}" var="itm">
                                        <tr>
                                            <%--<th><input type="radio" name="radio"  disabled  value="radio"></th>--%>
                                            <td><span>${itm.optiontype}</span>. ${fn:replaceAll(itm.content,"_QUESTIONPIC+",pageScope.quesImgpath)}
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
                        <td><span class="bg"><span id="tname"></span></span>
                            <c:if test="${!empty showExamYearMsg}">
                                <span>(${showExamYearMsg})</span>
                            </c:if>
                        <p>${fn:replaceAll(detail.CONTENT,"_QUESTIONPIC\\+",pageScope.quesImgpath)}</p>
                            <c:if test="${!empty option}">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <%--<col class="w30"/>--%>
                                    <col class="w910"/>
                                    <c:forEach items="${option}" var="itm">
                                        <tr>
                                            <%--<th><input type="radio" disabled name="rdo_option" value="${itm.isright}|${itm.optiontype}"  value="radio"></th>--%>
                                            <td><span>${itm.optiontype}</span>. ${fn:replaceAll(itm.content,"_QUESTIONPIC+",pageScope.quesImgpath)}
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
                        <c:if test="${!empty detail.CORRECT_ANSWER}">${fn:replaceAll(detail.CORRECT_ANSWER,"_QUESTIONPIC\\+",pageScope.quesImgpath)}</c:if>
                    </span></p>
                        <p><strong>答案解析：</strong>
                            <c:if test="${!empty detail.ANALYSIS}">
                                ${fn:replaceAll(detail.ANALYSIS,"_QUESTIONPIC\\+",pageScope.quesImgpath)}
                            </c:if>
                            <c:if test="${empty detail.ANALYSIS}">
                                无
                            </c:if>
                        </p>
                    </td>
                </tr>
            </table>


            <%--选择题--%>
            <c:if test="${!empty detail.QUESTION_TYPE&&(detail.QUESTION_TYPE==7||detail.QUESTION_TYPE==8||detail.QUESTION_TYPE==3||detail.QUESTION_TYPE==4)}">
                <p class="p_t_10"><strong>平均分：</strong><span class="font-blue">
                <script type="text/javascript">var avgFloat="${num.AVGSCORE}";document.write(parseFloat(avgFloat).toFixed(2));</script>
                </span>分&nbsp;&nbsp;&nbsp;&nbsp;
                        <%--<strong>已批改：</strong><span class="font-blue">4/20</span>&nbsp;&nbsp;&nbsp;&nbsp;--%>
                    <a href="" id="a_searchTJ" class="font-blue">查看统计</a>
                    <script type="text/javascript">
                        $("#a_searchTJ").attr("href","paper?m=toMarkingLogs&paperid=${param.paperid}&quesid=${quesid}&idx=${param.idx}&classid=${param.classid}&taskid=${param.taskid}&freeLoca="+encodeURIComponent(location.href));
                    </script>
                </p>
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
                <p><strong>附件：</strong>
                    <script type="text/javascript">
                        var annextName="${detail.ANNEXNAME}";
                        var h='';
                        if(annextName.Trim().length>0){
                            if(annextName.Trim().indexOf(",")!=-1){
                                var axArr=annextName.split(",");
                                if(axArr.length>0){
                                    $.each(axArr,function(ix,im){
                                        var annex='';
                                       var tanname=im.substring(im.lastIndexOf("/")+1);
                                        if(im.indexOf("http:")==-1&&im.indexOf("https:")==-1){
                                            h+='<a href="<%=basePath%>/uploadfile/'+im+'" target="_blank" class="font-blue">'+tanname+'</a>&nbsp;';
                                        }else
                                            h+='<a href="'+im+'" target="_blank" class="font-blue">'+tanname+'</a>&nbsp;';
                                    })
                                }
                            }else{
                                var tanname=annextName.substring(annextName.lastIndexOf("/")+1);
                                if(annextName.indexOf("http:")==-1&&annextName.indexOf("https:")==-1){
                                    h+='<a href="<%=basePath%>/uploadfile/'+annextName+'" target="_blank" class="font-blue">'+tanname+'</a>&nbsp;';
                                }else
                                    h+='<a href="'+annextName+'" target="_blank" class="font-blue">'+tanname+'</a>&nbsp;';
                            }
                        }
                        document.write(h);
                    </script>
                </p>
            </c:if>
            <p><strong>评分：</strong>请选择该学生的得分。&nbsp;&nbsp;
                <span id="sp_op_comment">
                    <a href="javascript:;" onclick="operateCommentP(1)"  style="color:blue">添加评语</a></span>
            </p>
            <p style="display:none;padding-top:5px" id="p_mark_comment"><strong>&nbsp;&nbsp;&nbsp;&nbsp;</strong>
                <textarea id="txt_mark_comment" name="mark_comment" style="height: 100px;width:875px;"></textarea>
            </p>
            </div>
        </div>
    </div>
    <%@include file="/util/foot.jsp" %>
</body>
</html>
