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
        $(function(){
            var content='${content}';
            var usertype = ${param.userType};
            content = content.replaceAll('<span name="fillbank"></span>','<input name="textfield4" type="text" />');
            $("#title").html(content);
            if("${type}"=="填空题"){
                if(usertype!=2){
                    var currentanswer = '${currentanswer}';
                    var myanswer = '${myanswer}';
                    var answers = currentanswer.split("|");
                    var myanswers = myanswer.split("|");
                    var htm = '<b>正确答案：</b>';
                    for(var i = 0;i<answers.length;i++){
                        if(myanswers.length>0){
                            content = content.replace('<input name="textfield4" type="text" />','<u class="huida">'+myanswers[i]+'</u>');
                            htm+=' <p>'+answers[i]+'<span>（'+myanswers[i]+'）</span></p>';
                        }else{
                            htm+=' <p>'+answers[i]+'<span>（）</span></p>';
                        }
                    }
                    $("#title").html(content);
                    $("#zhengquedaan").html(htm);
                }else{
                    var currentanswer = '${currentanswer}';
                    var answers = currentanswer.split("|");
                    var htm = '<b>正确答案：</b>';
                    for(var i = 0;i<answers.length;i++){
                       htm+=' <p>'+answers[i]+'</p>';
                    }
                    $("#zhengquedaan").html(htm);
                }
            }
        });
    </script>
</head>
<body>
<div class="zxcs_test">
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
        <ul class="daan">
            <c:forEach items="${option}" var="itm">
                <li><span class="blue">${itm.optiontype}、</span>${itm.content}
                    <c:if test="${!empty answer}">
                        <c:forEach items="${answer}" var="im">
                            <c:if test="${itm.optiontype eq im.answercontent and itm.isright==1}">
                                <b class="right"></b>
                            </c:if>
                            <c:if test="${itm.optiontype eq im.answercontent and itm.isright==0}">
                                <b class="wrong"></b>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty answer}">
                        <c:if test="${itm.isright==1}">
                            <b class="right"></b>
                        </c:if>
                    </c:if>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <p class="jiexi"><c:if test="${!empty rightNum}"><span>${rightNum}%答对</span></c:if> 答案与解析</p>
    <div>${analysis}</div>
    <c:if test="${!empty userRecord}">
        <c:forEach items="${userRecord}" var="itm">
            <div class="wenda">
                <b><img src="images/pic01_140811.png" width="36" height="36"></b>
                <p class="title"><span>${itm.REPLYDATE}前</span>ceshixiao01</p>
                <p>${itm.REPLYDETAIL}</p>
            </div>
        </c:forEach>
    </c:if>

</body>
</html>
