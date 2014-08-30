<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-8-20
  Time: 上午10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../util/common-base.jsp"%>
<html>
<head>
    <style>
        table{background-color:ghostwhite;}
        #dv_daohao{
            padding:10px 10px 30px 30px;
        }
        #dv_daohao a{
            padding-right:20px;
            font-size:16px;
            font-weight: bold;
            color:black;
        }
        #dv_daohao a:hover{
            color:red;
        }
        #dv_daohao .selected{
            color:blue;
            text-decoration: brown;
        }
    </style>
    <title>im接品---课堂纪实</title>
    <script type="text/javascript">

        //生成加密串
        function genderSign(methodName){

            var schoolId=$("#txt_schoolId").val();
            var jid=$("#txt_jid").val();
            var classId=$("#txt_classId").val();
            var taskId=$("#txt_taskId").val();
            var time=$("#txt_time").val();
            var paperId=$("#txt_paperId").val();
            var userType=$("#txt_userType").val();
            var param={schoolId:schoolId,jid:jid,classId:classId,time:time,paperId:paperId,userType:userType};
                 param.taskId=taskId;
            param.method=methodName;

            $.ajax({
                url:'_manager/GenderCourseRecordSign.jsp',
                type:'post',
                dataType:'text',
                data:param,
                success:function(rps){
                    $("#txt_sign").val(rps.Trim());
                }
            })
        }

    </script>
</head>
<body>
<div id="dv_xuanxiang">
    <div id="add"  align="center">
        //获取参数
        <%--String jid=paramMap.get("jid");--%>
        <%--String schoolId=paramMap.get("schoolId");--%>
        <%--String classId=paramMap.get("classId");--%>
        <%--String courseId=paramMap.get("courseId");--%>
        <%--String content=paramMap.get("attachDescribe");--%>
        <%--String attach=paramMap.get("attach");--%>
        <%--String time=paramMap.get("time");--%>
        <%--String sign=paramMap.get("sign");--%>
        <form action="../imapi1_1?m=toTestPaper" target="_blank" method="post">
            <table style="width:650px;">
                <tr>
                    <td colspan="3" align="center"><h3>toTestPaper</h3></td>
                </tr>
                <tr>
                    <td style="width:150px">JID:</td>
                    <td><input type="text" name="jid" id="txt_jid" value="-12542"/></td>
                    <td style="width:350px"></td>
                </tr>
                <tr>
                    <td>schoolId:</td>
                    <td><input type="text" name="schoolId" id="txt_schoolId" value="50000"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>classId:</td>
                    <td><input type="text" name="classId" id="txt_classId" value="18"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>taskId:</td>
                    <td><input type="text" name="taskId" id="txt_taskId" value="-3878335575180"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>paperId:</td>
                    <td><input type="text" name="paperId" id="txt_paperId" value="544536001443"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>userType:</td>
                    <td><input type="text" name="userType" id="txt_userType" value="1"/></td>
                    <td>提示：1，2：为老师&nbsp;&nbsp;3，4：为学生&nbsp;&nbsp; 6：为家长</td>
                </tr>
                <tr>
                    <td>time:</td>
                    <td><input type="text" name="time" id="txt_time" value=""/></td>
                    <td><input type="button" value="当前时间" onclick="txt_time.value=new Date().getTime()+'';"/>
                        <span style="color:red;font-size:12px;">提示：三分钟内有效!</span>
                    </td>
                </tr>
                <tr>
                    <td>sign:</td>
                    <td><input type="text" name="sign" id="txt_sign" value=""/></td>
                    <td><input type="button" value="生成" onclick="genderSign('toTestPaper')"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="3"  align="center"><input type="submit" value="提交--测试"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>



<div id="dv_result" align="center">
    <br/><br/><br/><br/>
    <h3>结果：</h3>
    <iframe id="ifm_result" width="650px;height:300px;"></iframe>
</div>

</body>
</html>
