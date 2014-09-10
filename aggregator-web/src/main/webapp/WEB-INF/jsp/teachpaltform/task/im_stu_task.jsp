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
            //加载另一个页面
           $("#content").load("task?m=toAnswerList&taskid=${param.taskid}&classid=${classid}",function(){
               //图片添加事件
               if($("#content li img").length>0)
                   EttImageShow({
                       image:$("#content li img"),
                       fadeId:'dv_imageShow1-1',
                       sw:90,
                       sh:70
                   });
           });
        });
        function headError(obj){
            obj.src='images/defaultheadsrc_big.jpg';
        }
    </script>
</head>
<div class="subpage_head"><span class="ico55"></span><strong>移动端任务</strong></div>
<div class="content1" id="content">

</div>
<%@include file="/util/foot.jsp"%>
</body>
</html>