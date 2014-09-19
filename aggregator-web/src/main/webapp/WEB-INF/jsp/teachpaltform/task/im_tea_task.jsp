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
    <script type="text/javascript" src="js/common/image-show.js"></script>
    <script type="text/javascript">
        $(function(){
            <c:if test="${!empty classid}">
                $("input[name='rdo_cls'][value='${classid}']").attr("checked",true)
            </c:if>
            <c:if test="${empty classid}">
                $("input[name='rdo_cls']:first").attr("checked",true)
            </c:if>
            loadContent($("input[name='rdo_cls']:checked").val());
        });
        function headError(obj){
            obj.src='images/defaultheadsrc_big.jpg';
        }
        var ht=0;
        function loadContent(clsid){
            if(ht>0){
                alert('请勿重复刷新!');return false;
            }
            if(ht==0){
                //加载另一个页面
                $("#content").load("task?m=toAnswerList&taskid=${param.taskid}&classid="+ clsid+" .content1",function(){
                    //图片添加事件
                    if($("#content li img").length>0)
                        EttImageShow({
                            image:$("#content li img"),
                            fadeId:'dv_imageShow1-1',
                            sw:90,
                            sh:70
                        });
                });
                ht=1;
                //1.5秒后，自动刷新=0
                setTimeout(function(){
                    ht=0;
                },1500);

            }
            return true;
        }
    </script>
</head>
<body>
<div class="subpage_head"><span class="ico55"></span><strong>任务统计</strong></div>
<div class="content1">
    <p class="font-black">
        <c:if test="${!empty clsList}">
            <c:forEach items="${clsList}" var="cls">
                <input type="radio" name="rdo_cls" onchange="return loadContent(this.value);" id="rdo_cls_${cls.classid}" value="${cls.classid}">
                <label for="rdo_cls_${cls.classid}">${cls.classgrade}${cls.classname}</label>
            </c:forEach>
        </c:if>
    </p>
   <div id="content"></div>

</div>
<%@include file="/util/foot.jsp"%>
</body>
</html>