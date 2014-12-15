<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-8-20
  Time: 上午10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../util/common-base.jsp"%>
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
    <title>ETT修改数校用户-检查用户名</title>
    <script type="text/javascript">

        //生成加密串
        function genderSign(methodName){

            var jid=$("#txt_jid").val();
            var userName=$("#txt_userName").val();
            var timestamp=$("#txt_timestamp").val();
            var param={
                jid:jid
                ,userName:userName
                ,timestamp:timestamp
            };
            param.method=methodName;

            $.ajax({
                url:'../_manager/GenderCourseRecordSign.jsp',
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

        <form action="../../operateEtt?m=checkUserName.do" target="ifm_result" method="post">
            <table style="width:650px;">
                <tr>
                    <td colspan="3" align="center"><h3>checkUserName.do</h3>
                        <br/>
                        <script type="text/javascript">document.write(document.forms[0].action);</script>

                    </td>
                </tr>
                <tr>
                    <td style="width:150px">jid:</td>
                    <td><input type="text" name="jid" id="txt_jid" value=""/></td>
                    <td style="width:250px"></td>
                </tr>
                <tr>
                    <td>userName:</td>
                    <td><input type="text" name="userName" id="txt_userName" value=""/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>timestamp:</td>
                    <td><input type="text" name="timestamp" id="txt_timestamp" value=""/></td>
                    <td><input type="button" value="当前时间" onclick="txt_timestamp.value=new Date().getTime()+'';"/>
                        <span style="color:red;font-size:12px;">提示：三分钟内有效!</span>
                    </td>
                </tr>
                <tr>
                    <td>sign:</td>
                    <td><input type="text" name="sign" id="txt_sign" value=""/></td>
                    <td><input type="button" value="生成" onclick="genderSign('checkUserName.do')"/>
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
    <iframe id="ifm_result" name="ifm_result" width="650px;height:300px;"></iframe>
</div>

</body>
</html>
