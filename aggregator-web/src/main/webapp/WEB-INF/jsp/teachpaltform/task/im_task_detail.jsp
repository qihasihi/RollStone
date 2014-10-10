<%@ page import="com.school.util.UtilTool" %>
<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-9-9
  Time: 下午7:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%   String proc_name=request.getHeader("x-etturl");
    if(proc_name==null){
        proc_name=request.getContextPath().replaceAll("/","");
    }else{
        ///group1/1.jsp
        proc_name=proc_name.substring(0,proc_name.substring(1).indexOf("/")+1).replaceAll("/","");
    }
    String ipStr=request.getServerName()+":"+request.getServerPort();
    //UtilTool.utilproperty.getProperty("PROC_NAME");
    String basePath = request.getScheme() + "://"
            + ipStr
            +"/"+proc_name + "/";
    session.setAttribute("IP_PROC_NAME",basePath);
    int ado_idx=0;
%>
<script src="<%=basePath %>util/xheditor/jquery/jquery-1.4.4.min.js" type="text/javascript"></script>
<html>
<head>
</head>
<body>
<div id="dv_data">
    <div class="font-black jxxt_zhuanti_rw_tongji_yidongduan_js">
        <p><strong>${tk.taskobjname}</strong></p>
        <p>${tk.imtaskcontent}</p>
        <c:if test="${tk.imtaskattachtype==1}">
        <ul>
        </c:if>
            <c:if test="${!empty tk.imtaskattachList}">
                <c:forEach items="${tk.imtaskattachList}" var="attach">
                    <c:if test="${tk.imtaskattachtype==1}">
                        <li>
                            <%--<img src="imapi1_1?m=makeImImg&w=90&h=70&p=${attach}" data-src="${attach}">--%>
                                <img src="${attach}" data-src="${attach}" height="70">
                        </li>
                    </c:if>
                    <c:if test="${tk.imtaskattachtype==2}">
                        <p><img style="cursor:pointer" width="99px" height="22px"  src="images/pic02_140811.png" onclick="ado_<%=ado_idx%>.play()"/>
                            <span style="display:none"><audio controls="controls" id="ado_<%=ado_idx++%>">
                            <source src="${attach}" type="audio/ogg">
                            <source src="${attach}" type="audio/mpeg">
                            您的浏览器不支持 audio 标签。
                           </audio>
                            </span>
                        </p>
                    </c:if>
                </c:forEach>
            </c:if>
                <c:if test="${tk.imtaskattachtype==1}">
                </ul>
                    </c:if>
        <div class="p_t_20">

            <p><strong>解析：</strong>${tk.imtaskanalysis}</p>
            <%--<ul>--%>
                <%--<li><a href="1"><img src="../images/pic15_140704.jpg" width="90" height="70"></a></li>--%>
                <%--<li><a href="1"><img src="../images/pic15_140704.jpg" width="90" height="70"></a></li>--%>
                <%--<li><a href="1"><img src="../images/pic15_140704.jpg" width="90" height="70"></a></li>--%>
                <%--<li><a href="1"><img src="../images/pic15_140704.jpg" width="90" height="70"></a></li>--%>
                <%--<li><a href="1"><img src="../images/pic15_140704.jpg" width="90" height="70"></a></li>--%>
            <%--</ul>--%>
        </div>
    </div>
    <c:if test="${!empty qaList}">
        <c:forEach items="${qaList}" var="qa" varStatus="qaIdx">
             <c:if test="${qaIdx.index!=0}"><h6></h6></c:if>
            <div class="font-black jxxt_zhuanti_rw_tongji_yidongduan_xs">
                <p class="pic"><img onerror="headError(this)" src="${empty qa.headimage?'asds':qa.headimage}" width="75" height="75"></p>
                <p><b>${qa.xgctime}</b><strong>${qa.realname}</strong></p>
                <p>${qa.answercontent}</p>
                <c:if test="${!empty qa.imtaskattachList}">
                <c:if test="${qa.replyattachtype==1}">
                    <ul>
                </c:if>

                   <c:forEach items="${qa.imtaskattachList}" var="at">
                    <c:if test="${qa.replyattachtype==1}">
                        <li>
                            <%--<img src="imapi1_1?m=makeImImg&w=90&h=70&p=${at}" data-src="${at}">--%>
                                <img src="${at}" data-src="${at}" height="70">
                        </li>

                    </c:if>
                    <c:if test="${qa.replyattachtype==2}">
                        <p>
                            <img style="cursor:pointer" width="99px" height="22px"  src="images/pic02_140811.png" onclick="ado_<%=ado_idx%>.play()"/>
                            <span style="display:none"><audio controls="controls" id="ado_<%=ado_idx++%>">
                                <source src="${at}" type="audio/ogg">
                                <source src="${at}" type="audio/mpeg">
                                您的浏览器不支持 audio 标签。
                            </audio>
                            </span>
                          </p>
                    </c:if>
                    </c:forEach>
                    <c:if test="${qa.replyattachtype==1}">
                    </ul>
                     </c:if>
                    </c:if>
            </div>
        </c:forEach>
    </c:if>
</div>
</body>
</html>
