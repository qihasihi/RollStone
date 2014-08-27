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
            var userId=$("#txt_userId").val();
            var time=$("#txt_time").val();
            var param={schoolId:schoolId,jId:jid,timeStamp:time,userId:userId};
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
        <%--//获取参数--%>
            <%--String jId = request.getParameter("jId");--%>
            <%--String userId=request.getParameter("userId");--%>
            <%--String schoolid = request.getParameter("schoolId");--%>
            <%--String timestamp = request.getParameter("timeStamp");--%>
            <%--String key = request.getParameter("sign");--%>

        <form action="../toEttFace?m=bindJID" target="ifm_result" method="post">
            <table style="width:650px;">
                <tr>
                    <td colspan="3" align="center"><h3>bindJID</h3></td>
                </tr>
                <tr>
                    <td style="width:150px">jId:</td>
                    <td><input type="text" name="jId" id="txt_jid" value=""/></td>
                    <td style="width:250px"></td>
                </tr>
                <tr>
                    <td>schoolId:</td>
                    <td><input type="text" name="schoolId" id="txt_schoolId" value="50000"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>userId:</td>
                    <td><input type="text" name="userId" id="txt_userId" value="152"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>timeStamp:</td>
                    <td><input type="text" name="timeStamp" id="txt_time" value=""/></td>
                    <td><input type="button" value="当前时间" onclick="txt_time.value=new Date().getTime()+'';"/>
                        <span style="color:red;font-size:12px;">提示：三分钟内有效!</span>
                    </td>
                </tr>
                <tr>
                    <td>sign:</td>
                    <td><input type="text" name="sign" id="txt_sign" value=""/></td>
                    <td><input type="button" value="生成" onclick="genderSign('ETT-SCHOOL-MODEL')"/>
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
