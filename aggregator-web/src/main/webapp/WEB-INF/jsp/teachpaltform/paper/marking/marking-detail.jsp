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
        var quesids = "${param.quesids}";
        var typename;
        var extenname;
        var sign=${sign};
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
                extenname="完型填空";
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
                typename="其它";
                break;
            case 2:
                typename="填空";
                break;
            case 3:
                typename="单选";
                break;
            case 4:
                typename="多选";
                break;
            case 5:
                typename="资源学习";
                break;
            case 6:
                typename=extenname;
                break;
            case 7:
                typename="单选组试题";
                break;
            case 8:
                typename="多选组试题";
                break;
        }
        $(function(){
            if(sign){
                $("#score").hide();
            }else{
                var totalnum= "${num.SCORE}";
                var totalnum2 = "${detail.SCORE}";
                if(totalnum2!=null){
                    totalnum=totalnum2;
                }
                var n = Math.ceil((parseFloat(totalnum)+0.5)/10);
                var divhtm='';
                for(var i = 0;i<n;i++){
                    divhtm+='<div class="layout">';
                    var ulhtm='<ul>';
                    var lihtm='';
                    for(var j =0;j<10;j++){
                        lihtm+='<li';
                        if(j==0){
                            lihtm+=' class="crumb"';
                        }
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
                        lihtm2+='<li><a href="1">'+(k+i*10)+'</a></li>';
                    }
                    ulhtm2+=lihtm2+'</ul>';
                    divhtm+=ulhtm+ulhtm2+'</div>';
                }
                $("#score").append(divhtm);
            }
            $("#tname").html(typename);

        });
        function submitScore(ref,score){

        }
        function tabQuestion(type){
            if(type==1){
                //处理上一题下一题按钮
                if(quesids.length>0){
                    var htm = '';
                    var orderidx = tabidx;
                    var id = quesids[tabidx-1];
                    var sign = false;
                    var questionid;
                    if(quesids[tabidx-1].split("|").length>1){
                        questionid = quesids[tabidx-1].split("|")[0];
                        sign = true;
                        id=quesids[tabidx-1].split("|")[1];
                    }
                    htm+='paper?m=toMarkingDetail';
                    if(sign){
                        htm+='&questionid='+questionid;
                    }
                    htm+='&quesids='+quesids+'&tabidx='+(tabidx-1)+'&paperid=${param.paperid}&quesid='+id+'&idx='+orderidx;
                    $("#upquestion").attr("href",htm);
                }
            }else{
                //处理上一题下一题按钮
                if(quesids.length>0){
                    var htm = '';
                    var orderidx = tabidx+2;
                    var id = quesids[tabidx+1];
                    var sign = false;
                    var questionid;
                    if(quesids[tabidx+1].split("|").length>1){
                        questionid = quesids[tabidx+1].split("|")[0];
                        sign = true;
                        id=quesids[tabidx+1].split("|")[1];
                    }
                    htm+='paper?m=toMarkingDetail';
                    if(sign){
                        htm+='&questionid='+questionid;
                    }
                    htm+='&quesids='+quesids+'&tabidx='+(tabidx+1)+'&paperid=${param.paperid}&quesid='+id+'&idx='+orderidx;
                    $("#nextquestion").attr("href",htm);
                }
            }
        }
    </script>
</head>
<body>
    <div class="subpage_head"><span class="ico55"></span><strong>批阅试卷</strong></div>
    <div class="content2">
        <p class="jxxt_zhuanti_rw_piyue_info"><a href="" id="nextquestion" class="an_public3">下一题</a>
            <a href="" id="upquestion" class="an_public3">上一题</a><strong class="font-blue">${param.idx}</strong>&nbsp;&nbsp;试题分数：<span class="font-blue">${num.SCORE}</span>&nbsp;分</p>
        <div class="jxxt_zhuanti_shijuan_add public_input font-black">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 w940">
                <c:if test="${!empty detail.CONTENT2}">
                    <tr>
                        <td><span class="bg"><span id="tname"></span>：</span>${detail.CONTENT2}

                        </td>
                    </tr>
                    <tr>
                        <td>${detail.ORDERIDX}、${detail.CONTENT}
                            <c:if test="${!empty option}">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <col class="w30"/>
                                    <col class="w910"/>
                                    <c:forEach items="${option}" var="itm">
                                        <tr>
                                            <th><input type="radio" name="radio"  value="radio"></th>
                                            <td>${itm.optiontype}. ${itm.content}
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
                                    <col class="w30"/>
                                    <col class="w910"/>
                                    <c:forEach items="${option}" var="itm">
                                        <tr>
                                            <th><input type="radio" name="radio"  value="radio"></th>
                                            <td>${itm.optiontype}. ${itm.content}
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
                    <td><p><strong>正确答案：</strong>${detail.CURRENTANSWER}</p>
                        <p><strong>答案解析：</strong>${detail.ANALYSIS}</p></td>
                </tr>
            </table>
        </div>
        <div class="jxxt_zhuanti_rw_piyue_defen" id="score">
            <h5></h5>
            <p class="p_b_20"><strong>平均分：</strong><span class="font-blue">${num.AVGSCORE}</span>分
                &nbsp;&nbsp;&nbsp;&nbsp;<strong>已批改：</strong><span class="font-blue">${num.MARKINGNUM}/${num.SUBMITNUM}</span>&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" class="font-blue">查看统计</a></p>
            <p><strong>学生：</strong>${detail.STU_NAME} </p>
            <p><strong>答案：</strong>${detail.ANSWER}</p>
            <p><strong>附件：</strong><a href="${detail.ANNEXNAME}" target="_blank" class="font-blue">我的答案</a></p>
            <p><strong>评分：</strong>请选择该学生的得分。</p>
        </div>
    </div>
    <%@include file="/util/foot.jsp" %>
</body>
</html>
