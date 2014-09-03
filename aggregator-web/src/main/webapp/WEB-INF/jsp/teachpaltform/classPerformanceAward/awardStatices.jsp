<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-9-2
  Time: 下午5:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
<html>
<head>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8"/>
    <script type="text/javascript">
        $(function(){
            //总积分
            $("#sp_zjf").html($("#tr_${currentLoginUID} input[name='hd_zjf']").val());
            //任务数
            $("#sp_rws").html($("#tr_${currentLoginUID} input[name='hd_rws']").val());
            //出勤数
            $("#sp_cqs").html($("#tr_${currentLoginUID} input[name='hd_cqs']").val());
        })
    </script>
</head>

<body>
<div class="subpage_head"><span class="ico19"></span><strong>课程积分</strong></div>
<div class="content1">
    <p class="t_r">总积分：<span class="font-red" id="sp_zjf">--</span>&nbsp;&nbsp;&nbsp;任务数：<span class="font-red" id="sp_rws">--</span>&nbsp;&nbsp;&nbsp;出勤数：<span class="font-red" id="sp_cqs">--</span></p>
   <c:if test="${clsObj.dctype==3}">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
        <colgroup span="2" class="w150"></colgroup>
        <colgroup class="w140"></colgroup>
        <colgroup span="2" class="w130"></colgroup>
        <colgroup class="w150"></colgroup>
        <tr>
            <th>姓名</th>
            <th>网上得分</th>
            <th>网下表现得分</th>
            <th>小组得分</th>
            <th>课程积分</th>
            <th>积分排行榜<a href="1" class="ico48a"></a><a href="1" class="ico48b"></a></th>
        </tr>
        <c:if test="${!empty dataList}">
               <c:forEach items="${dataList}" var="d" varStatus="dIdx">
                   <tr class="${dIdx.index%2==0?'trbg1':''}" id="tr_${d.USER_ID}">
                       <td><a href="1" target="_blank">${d.STU_NAME}</a></td>
                       <td>${!empty d.WSDF?d.WSDF:'--'}</td>
                       <td>${!empty d.WXDF?d.WXDF:'--'}</td>
                       <td>${!empty d.GROUP_SCORE?d.GROUP_SCORE:'--'}</td>
                       <td>${!empty d.COURSE_TOTAL_SCORE?d.COURSE_TOTAL_SCORE:'--'}</td>
                       <td>${d.RNUM}
                           <input type="hidden" value="${!empty d.WSDF?d.WSDF:'--'}" name="hd_wsdf"/>
                           <input type="hidden" value="${!empty d.WXDF?d.WXDF:'--'}" name="hd_wxdf"/>
                           <input type="hidden" value="${!empty d.GROUP_SCORE?d.GROUP_SCORE:'--'}" name="hd_xzdf"/>
                           <input type="hidden" value="${!empty d.TASK_SCORE?d.TASK_SCORE:'--'}" name="hd_rwdf"/>
                           <input type="hidden" value="${!empty d.CQS?d.CQS:'--'}" name="hd_cqs"/>
                           <input type="hidden" value="${!empty d.RWS?d.RWS:'--'}" name="hd_rws"/>
                           <input type="hidden" value="${!empty d.COURSE_TOTAL_SCORE?d.COURSE_TOTAL_SCORE:'--'}" name="hd_zjf"/>
                       </td>
                   </tr>
               </c:forEach>
        </c:if>
    </table>
   </c:if>
<c:if test="${empty clsObj.dctype||clsObj.dctype!=3}">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
        <colgroup span="4" class="w200"></colgroup>
        <colgroup class="w310"></colgroup>
        <tr>
            <th>姓名</th>
            <th>网上得分</th>
            <th>小组得分</th>
            <th>课程积分</th>
            <th>积分排行榜<a href="1" class="ico48a"></a><a href="1" class="ico48b"></a></th>
        </tr>
        <c:if test="${!empty dataList}">
            <c:forEach items="${dataList}" var="d" varStatus="dIdx">
                <tr  class="${dIdx.index%2==0?'trbg1':''}" id="tr_${d.USER_ID}">
                    <td><a href="1" target="_blank">${d.STU_NAME}</a></td>
                    <td>${!empty d.WSDF?d.WSDF:'--'}</td>
                    <%--<td>${!empty d.WXDF?d.WXDF:'--'}</td>--%>
                    <td>${!empty d.GROUP_SCORE?d.GROUP_SCORE:'--'}</td>
                    <td>${!empty d.COURSE_TOTAL_SCORE?d.COURSE_TOTAL_SCORE:'--'}</td>
                    <td>${d.RNUM}
                        <input type="hidden" value="${!empty d.WSDF?d.WSDF:'--'}" name="hd_wsdf"/>
                        <input type="hidden" value="${!empty d.WXDF?d.WXDF:'--'}" name="hd_wxdf"/>
                        <input type="hidden" value="${!empty d.GROUP_SCORE?d.GROUP_SCORE:'--'}" name="hd_xzdf"/>
                        <input type="hidden" value="${!empty d.TASK_SCORE?d.TASK_SCORE:'--'}" name="hd_rwdf"/>
                        <input type="hidden" value="${!empty d.CQS?d.CQS:'--'}" name="hd_cqs"/>
                        <input type="hidden" value="${!empty d.RWS?d.RWS:'--'}" name="hd_rws"/>
                        <input type="hidden" value="${!empty d.COURSE_TOTAL_SCORE?d.COURSE_TOTAL_SCORE:'--'}" name="hd_zjf"/>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
    </c:if>
</div>
<%@include file="/util/foot.jsp"%>
</body>
</html>
