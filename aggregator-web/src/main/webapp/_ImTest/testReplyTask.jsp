<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-8-20
  Time: 上午10:56
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
            var isvir = $("#isVirtual").val();
            var time=$("#txt_a_time").val();
            var taskId = $("#taskId").val();
            var userType = $("#userType").val();
            var replyDetail = $("#replyDetail").val();
            var replyAttach = $("#replyAttach").val();
            var attachType = $("#attachType").val();
            var param={schoolId:schoolId,jid:jid,classId:classId,isVirtual:isvir,time:time,taskId:taskId,userType:userType,replyDetail:replyDetail,replyAttach:replyAttach,attachType:attachType};

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
    <form action="../imapi1_1?m=ReplyTask" method="post">
        <table>
            <tr>
                <td>任务id</td>
                <td><input type="text" name="taskId" id="taskId" value="-5005412435847"/></td>
            </tr>
            <tr>
                <td>班级id</td>
                <td><input type="text" name="classId" id="classId" value="4"/> </td>
            </tr>
            <tr>
                <td>是否虚拟</td>
                <td><input type="text" name="isVirtual" id="isVirtual" value="0"/> </td>
            </tr>
            <tr>
                <td>用户id</td>
                <td><input type="text" name="jid" id="jid" value="2250913"/> </td>
            </tr>
            <tr>
                <td>用户类型</td>
                <td><input type="text" name="userType" id="userType" value="3"/> </td>
            </tr>
            <tr>
                <td>分校id</td>
                <td><input type="text" name="schoolId" id="schoolId" value="50000"/> </td>
            </tr>
            <tr>
                <td>回答内容</td>
                <td><input type="text" name="replyDetail" id="replyDetail" value="测试回答资源任务"/> </td>
            </tr>
            <tr>
                <td>回答附件</td>
                <td><input type="text" name="replyAttach" id="replyAttach" value="啊啊啊.jpg"/> </td>
            </tr>
            <tr>
                <td>附件类型</td>
                <td><input type="text" name="attachType" id="attachType" value="2"/> </td>
            </tr>
            <tr>
                <td>时间戳</td>
                <td><input type="text" name="time" id="txt_a_time" value=""/> <input type="button" value="生成时间" onclick="txt_a_time.value=new Date().getTime()+'';"/></td>
            </tr>
            <tr>
                <td>sign:</td>
                <td><input type="text" name="sign" id="txt_a_sign" value=""/> <input type="button" value="生成" onclick="genderSign('ReplyTask')"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="提交">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
