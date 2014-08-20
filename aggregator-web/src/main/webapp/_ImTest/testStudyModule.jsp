<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-8-20
  Time: 下午4:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/util/common-jsp/common-im.jsp"%>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        //生成加密串
        function genderSign(methodName){

            var schoolId=$("#schoolId").val();
            var jid=$("#jid").val();
            var classId=$("#classId").val();
            var time=$("#txt_a_time").val();
            var taskId = $("#taskId").val();
            var classId = $("#classId").val();
            var userType = $("#userType").val();
            var replyDetail = $("#replyDetail").val();
            var replyAttach = $("#replyAttach").val();
            var attachType = $("#attachType").val();
            var param={schoolId:schoolId,jid:jid,classId:classId,time:time,taskId:taskId,classId:classId,userType:userType,replyDetail:replyDetail,replyAttach:replyAttach,attachType:attachType};

            param.method=methodName;

            $.ajax({
                url:'_manager/GenderCourseRecordSign.jsp',
                type:'post',
                dataType:'text',
                data:param,
                success:function(rps){
                    $("#txt_a_sign").val(rps.Trim());
                }
            })
        }
    </script>
</head>
<body>

</body>
</html>
