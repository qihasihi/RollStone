<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-8-11
  Time: 下午2:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/util/common-jsp/common-im.jsp"%>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var tasktype = ${taskType};
        var quesType = ${quesType};
        var userRef = "${userRef}";
        $(function(){
            var content='${content}';
            var usertype = ${usertype};
            $("#title").html(content);
            if("${type}"=="填空题"){
                if(usertype!=2&&${empty answer}){
                    content = content.replaceAll('<span name="fillbank"></span>','<input name="txt_fb_${quesid}" type="text" />');
                    $("#title").html(content);
                }
                if(usertype!=2&&${!empty answer}){
                    var currentanswer = '${currentanswer}';
                    var myanswer = '${myanswer}';
                    var answers = currentanswer.split("|");
                    var myanswers = myanswer.split("|");
                    var htm = '<b>正确答案：</b>';
                    for(var i = 0;i<answers.length;i++){
                        if(myanswers.length>0){
                            content = content.replace('<span name="fillbank"></span>','<u class="huida">'+myanswers[i]+'</u>');
                            htm+=' <p>'+answers[i]+'<span>（'+myanswers[i]+'）</span></p>';
                        }else{
                            htm+=' <p>'+answers[i]+'<span>（）</span></p>';
                        }
                    }
                    $("#title").html(content);
                    $("#zhengquedaan").html(htm);
                }
                if(usertype==2){
                    var currentanswer = '${currentanswer}';
                    var answers = currentanswer.split("|");
                    var htm = '<b>正确答案：</b>';
                    for(var i = 0;i<answers.length;i++){
                       htm+=' <p>'+answers[i]+'</p>';
                    }
                    $("#zhengquedaan").html(htm);
                    content = content.replaceAll('<span name="fillbank"></span>','<u class="huida">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>');
                    $("#title").html(content);
                }

            }
        });
        function submitTask(){
            var paramStr = 't=' + new Date().getTime();
            var param = {userRef:userRef,tasktype: ${taskType}, taskid: ${taskid}, quesid: ${quesid}, courseid: ${courseid}};
            //填空
            var txt_fb_option = $("input[name='txt_fb_" + ${quesid} + "']");
            var txt_fb_answer = $("input[name='txt_fb_" + ${quesid} + "']").filter(function () {
                return this.value.Trim().length > 0
            });
            if (typeof(quesType) != 'undefined' && !isNaN(quesType))
                param.questype = quesType;
            //选择
            var optionArray = new Array();
            if(tasktype==3){
                $("ul li[class='crumb'] span input[type='hidden']").each(function(){
                        optionArray.push(this);
                });
            }
            if (quesType == 2) {
                if (txt_fb_option.length < 1) {
                    alert('抱歉,未发现填空题回答输入框!');
                    return false;
                }
                if (txt_fb_option.length > txt_fb_answer.length) {
                    alert('请完成填空录入后提交!');
                    return false;
                }
                $.each(txt_fb_answer, function (idx, itm) {
                    if (paramStr.length > 0)
                        paramStr += "&";
                    paramStr += "fbanswerArray=" + $(itm).val();
                });

            } else if (quesType == 3 || quesType == 4) {
                if (optionArray.length < 1) {
                    alert('请选择选项后提交!');
                    return false;
                }
                $.each(optionArray, function (idx, itm) {
                    if (paramStr.length > 0)
                        paramStr += "&";
                    paramStr += "optionArray=" + $(itm).val();
                });
            }
            $.ajax({
                url:"imapi1_1?m=doStuSubmitTask",
                type:"post",
                data:paramStr + '&' + $.param(param),
                dataType:'json',
                cache: false,
                error:function(){
                    alert('系统未响应，请稍候重试!');
                },success:function(rmsg){
                    if (rmsg.type == "error") {
                        alert(0);
                    } else {
                        alert(1);
                    }
                }
            });
            return false;
        }

        function changeOption(idx){
            if(quesType==3){
                $("ul>li").each(function(){
                    $(this).removeClass("crumb");
                });
            }
            if($("#li"+idx).hasClass("crumb")){
                $("#li"+idx).removeClass("crumb");
            }else{
                $("#li"+idx).addClass("crumb");
            }
        }
    </script>
</head>
<body>
<div class="zxcs_test"><form id="questionForm" onsubmit="return submitTask()"><input type="button" value="aaaa" onclick="submitTask()"></form>
    <h1>${type}</h1>
    <div class="title" id="title"></div>
    <div class="zhengquedaan" id="zhengquedaan">

    </div>
    <c:if test="${!empty optionNum}">
        <ul class="daan">
            <c:forEach items="${optionNum}" var="itm">
                <li><span class="blue">${itm.OPTION_TYPE}、</span>${itm.CONTENT}
                    <c:if test="${itm.ISRIGHT eq 1}"><p class="green">${itm.NUM}%正确</p></c:if> <c:if test="${itm.ISRIGHT eq 0}"><p class="red">${itm.NUM}%错误</p></c:if></li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${!empty option}">
        <c:if test="${empty answer}">
            <ul class="test">
        </c:if>
        <c:if test="${!empty answer}">
            <ul class="daan">
        </c:if>
            <c:forEach items="${option}" var="itm" varStatus="idx">
                    <c:if test="${empty answer}">
                        <li id="li${idx.index}">
                       <a href="javascript:changeOption(${idx.index})">
                    </c:if>
                    <c:if test="${!empty answer}">
                        <li>
                    </c:if>
                    <span class="blue"><input type="hidden" id="h_${itm.ref}" value="${itm.ref}"/>${itm.optiontype}、</span>${itm.content}
                    <c:if test="${!empty answer}">
                        <c:forEach items="${answer}" var="im">
                            <c:if test="${ itm.isright==1}">
                                <b class="right"></b>
                            </c:if>
                            <c:if test="${itm.optiontype eq im.answercontent and itm.isright==0}">
                                <b class="wrong"></b>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty answer}">
                       </a>
                    </c:if>
                </li>
            </c:forEach>
        </ul>
    </c:if>

    <c:if test="${!empty answer}">
        <p class="jiexi"><c:if test="${!empty rightNum}"><span>${rightNum}%答对</span></c:if> 答案与解析</p>
        <div>${analysis}</div>
    </c:if>
    <c:if test="${usertype eq 2}">
        <p class="jiexi"> 答案与解析</p>
        <div>${analysis}</div>
    </c:if>
    <c:if test="${!empty userRecord}">
        <c:forEach items="${userRecord}" var="itm">
            <div class="wenda">
                <b><img src="images/pic01_140811.png" width="36" height="36"></b>
                <p class="title"><span>${itm.REPLYDATE}前</span>${itm.USERNAME}</p>
                <p>${itm.REPLYDETAIL}</p>
            </div>
        </c:forEach>
    </c:if>

</body>
</html>
