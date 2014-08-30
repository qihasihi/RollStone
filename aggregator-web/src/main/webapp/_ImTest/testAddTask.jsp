<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-8-23
  Time: 下午5:52
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
            var time=$("#txt_a_time").val();
            var param={schoolId:schoolId,jid:jid,time:time};
            param.method=methodName;

            $.ajax({
                url:'_manager/GenderCourseRecordSign.jsp',
                type:'post',
                dataType:'text',
                data:param,
                success:function(rps){
                    $("#txt_a_sign").val(rps.Trim());
                }
            });
        }
    </script>
</head>
<body>
<form action="../imapi1_1?m=AddTask" method="post">
    <table>
        <tr>
            <td>用户id</td>
            <td><input type="text" name="jid" id="jid" value="2470145"/> </td>
        </tr>
        <tr>
            <td>分校id</td>
            <td><input type="text" name="schoolId" id="schoolId" value="50000"/> </td>
        </tr>
        <tr>
            <td>data</td>
            <td>
                <textarea  name="data" id="data" rows="10" cols="50">
                    {"courseId":-2040956248279,"taskType":7,"taskTitle":"测试手机端添加任务","taskContent":"这是一个添加任务的内容主体","taskAnalysis":"这是答案解析","taskAttach":"fujian.txt","attachType":1,"classes":
                    [{"taskUserTeamId":4,"taskUserType":0,"startTime":"2014-08-29 00:00:00","endTime":"2014-10-30 00:00:00"},{},{}]}
                </textarea>
            </td>
        </tr>
        <tr>
            <td>时间戳</td>
            <td><input type="text" name="time" id="txt_a_time" value=""/> <input type="button" value="生成时间" onclick="txt_a_time.value=new Date().getTime()+'';"/></td>
        </tr>
        <tr>
            <td>sign:</td>
            <td><input type="text" name="sign" id="txt_a_sign" value=""/> <input type="button" value="生成" onclick="genderSign('AddTask')"/></td>
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
