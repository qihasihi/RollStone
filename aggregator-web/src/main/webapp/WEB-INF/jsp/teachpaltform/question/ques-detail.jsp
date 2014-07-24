<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-2-28
  Time: 上午10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.school.entity.UserInfo"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <title>试题详细页面</title>
    <script type="text/javascript">
        $(function(){
            var type=${question.questiontype};
            var typename="";
            switch(parseInt(type)){
                case 1:typename="问答";break;
                case 2:typename="填空";break;
                case 3:typename="单选";break;
                case 4:typename="多选";break;
            }
            $("#question_type").html(typename);
            $("#questionname  p>span[name='fillbank']").html("_______");
        });
    </script>
</head>
<body>
<div class="subpage_head"><span class="ico55"></span><strong>试题详情&mdash;&mdash;<span id="question_type"></span></strong></div>
<div class="content1">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 black">
        <col class="w100"/>
        <col class="w700"/>
        <tr>
            <th>题&nbsp;&nbsp;&nbsp;&nbsp;干：</th>
            <td id="questionname">${question.content}<br>
                <c:if test="${question.questiontype==3||question.questiontype==4}">
                    <c:forEach items="${question.questionOptionList}" var="itm">
                        <c:if test="${question.questiontype==3}">
                            <input type="radio" name="option"/>${itm.optiontype}.&nbsp;
                        </c:if>
                        <c:if test="${question.questiontype==4}">
                            <input type="checkbox" name="option"/>${itm.optiontype}.&nbsp;
                        </c:if>
                        <script>
                            var content=replaceAll(replaceAll(replaceAll('${itm.content}'.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                            document.write(content);
                        </script>
                        <c:if test="${itm.isright==1}"><span class="ico12"></span></c:if><br>
                    </c:forEach>
                </c:if>
            </td>
        </tr>
        <tr>
            <th>正确答案：</th>
            <td>
                <c:if test="${question.correctanswer!=null}">
                    ${question.correctanswer}
                </c:if>
                <c:if test="${question.questionOptionList!=null}">
                    <c:forEach items="${question.questionOptionList}" var="itm">
                        <c:if test="${itm.isright==1}">${itm.optiontype}</c:if>
                    </c:forEach>
                </c:if>
            </td>
        </tr>
        <tr>
            <th>答案解析：</th>
            <td> ${question.analysis}</td>
        </tr>
    </table>
    <br>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
