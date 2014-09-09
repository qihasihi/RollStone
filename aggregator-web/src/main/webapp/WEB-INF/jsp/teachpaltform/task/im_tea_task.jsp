<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-9-9
  Time: 下午8:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <script type="text/javascript">
        $(function(){

        });
        function headError(obj){
            obj.src='images/defaultheadsrc_big.jpg';
        }
        var ht=0;
        function loadContent(clsid){
            if(ht>0){
                alert('请勿重复刷新!');return;
            }
            if(ht==0){
                $("#content").load("tptask?m=toAnswerList&taskid=${param.taskid}&classid=${classid}");
                ht=1;
                //1.5秒后，自动刷新=0
                setTimeout(function(){
                    ht=0;
                },1500);
            }
        }
    </script>
</head>
<body>
<div class="subpage_head"><span class="ico55"></span><strong>任务统计</strong></div>
<div class="content1">
    <p class="font-black">
        <c:if test="${!empty clsList}">
            <c:forEach items="${clsList}" var="cls">
                <input type="radio" name="rdo_cls" id="rdo_cls_${cls.classid}" value="${cls.classid}">
                <label for="rdo_cls_${cls.classid}">小学</label>
            </c:forEach>
        </c:if>
    </p>
   <div id="content"></div>

</div>
<%@include file="/util/foot.jsp"%>
</body>
</html>