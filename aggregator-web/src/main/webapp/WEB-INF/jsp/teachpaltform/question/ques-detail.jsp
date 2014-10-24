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

        });
    </script>
</head>
<body>
<div class="subpage_head"><span class="ico55"></span><strong>试题详情</strong></div>
<div class="content2">
    <div class="jxxt_zhuanti_shijuan_add font-black public_input">
<c:if test="${!empty pqList}">
    <c:forEach items="${pqList}" var="pq">
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 w940">
            <tr>
                <td>
                    <span class="bg">${pq.questiontypename}</span>
                     ${fn:replace(pq.content,'<span name="fillbank"></span>' ,"_____" )}
                    <c:if test="${pq.extension eq 4}">
                        <div  class="p_t_10" id="sp_mp3_${pq.questionid}" ></div>
                        <script type="text/javascript">
                            playSound('play','<%=basePath+UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>/${pq.questionid}/001.mp3',270,22,'sp_mp3_${pq.questionid}',false);
                        </script>
                        ${pq.analysis}
                    </c:if>
                    <c:if test="${!empty pq.questionOptionList and pq.questiontype ne 1}">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <col class="w30"/>
                            <col class="w880"/>
                            <c:forEach items="${pq.questionOptionList}" var="option">
                                <tr>
                                    <th>
                                        <c:if test="${pq.questiontype eq 3 or pq.questiontype eq 7 }">
                                            <input disabled type="radio">
                                        </c:if>
                                        <c:if test="${pq.questiontype eq 4 or pq.questiontype eq 8 }">
                                            <input disabled type="checkbox">
                                        </c:if>
                                    </th>
                                    <td>
                                            ${option.optiontype}&nbsp;${option.content}
                                        <c:if test="${option.isright eq 1}">
                                            <span class="ico12"></span>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>


                </td>
            </tr>

            <c:if test="${!empty pq.questionTeam and fn:length(pq.questionTeam)>0 and pq.extension ne 5}">
                <c:forEach items="${pq.questionTeam}" var="c" varStatus="cidx">
                    <tr>
                        <td><p><span   class="font-blue">${(cidx.index+1)}</span>. ${c.content}</p>
                            <table border="0" cellpadding="0" cellspacing="0">
                                <col class="w30"/>
                                <col class="w880"/>
                                <c:forEach items="${c.questionOptionList}" var="option">
                                    <tr>
                                        <th>
                                            <c:if test="${c.questiontype eq 3 or c.questiontype eq 7}">
                                                <input disabled type="radio">
                                            </c:if>
                                            <c:if test="${c.questiontype eq 4 or c.questiontype eq 8 }">
                                                <input disabled type="checkbox">
                                            </c:if>
                                        </th>
                                        <td>
                                                ${option.optiontype}&nbsp;${option.content}
                                            <c:if test="${option.isright eq 1}">
                                                <span class="ico12"></span>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <c:if test="${c.questiontype<3 }">
                                <p>
                                    <strong>正确答案：</strong>${c.correctanswer}
                                </p>
                            </c:if>
                            <p><strong>答案解析：</strong>${c.analysis}</p>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>


            <c:if test="${pq.questiontype<6}">
                <tr>
                    <td>
                        <p>
                            <strong>正确答案：</strong>
                            <c:if test="${pq.questiontype eq 1 and pq.questionid>0}">
                                <c:if test="${!empty pq.questionOptionList}">
                                    ${pq.questionOptionList[0].content}
                                </c:if>
                            </c:if>

                            <c:if test="${pq.questiontype eq 1 and pq.questionid<0 }">
                                ${pq.correctanswer}
                            </c:if>

                            <c:if test="${pq.questiontype eq 2 }">
                                ${pq.correctanswer}
                            </c:if>
                            <c:if test="${pq.questiontype eq 3 or  pq.questiontype eq 4 }">
                                <c:if test="${!empty pq.questionOptionList}">
                                    <c:forEach items="${pq.questionOptionList}" var="option">
                                        <c:if test="${option.isright eq 1}">
                                            ${option.optiontype}&nbsp;
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </c:if>
                        </p>
                        <p>
                            <strong>答案解析：</strong>${pq.analysis}
                        </p>
                    </td>
                </tr>
            </c:if>


            <c:if test="${!empty pq.questionTeam and fn:length(pq.questionTeam)>0 and pq.extension eq 5}">
                <tr>
                    <td><p><strong>正确答案及答案解析：</strong></p>
                        <c:forEach items="${pq.questionTeam}" var="c" varStatus="cidx">
                            <p><span data-bind="${c.questionid}"  class="font-blue">${(cidx.index+1)}</span>.
                                <c:forEach items="${c.questionOptionList}" var="option">
                                    <c:if test="${option.isright eq 1}">
                                        ${option.optiontype}
                                    </c:if>
                                </c:forEach>
                                &nbsp;&nbsp;${c.analysis}
                            </p>
                        </c:forEach>
                    </td>
                </tr>
            </c:if>
        </table>
    </c:forEach>
 </c:if>
    </div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
