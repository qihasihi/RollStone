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
    <title>im接品---课堂纪实</title>
    <script type="text/javascript">

        //生成加密串
        function genderSign(methodName){

            var schoolId=$("#txt_schoolId").val();
            var jid=$("#txt_jid").val();
            var gradeId=$("#txt_gradeId").val();
            var className=$("#txt_className").val();
            var subjectId=$("#txt_subjectId").val();
            var isAdmin=$("input[type='radio'][name='isAdmin']:checked").val();
            var time=$("#txt_time").val();
            var param={
                schoolId:schoolId
                ,jid:jid
                ,time:time
                ,className:className
                ,gradeId:gradeId
                ,subjectId:subjectId
                ,isAdmin:isAdmin
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

        <form action="../../im1.1.3?m=createClass.do" target="ifm_result" method="post">
            <table style="width:650px;">
                <tr>
                    <td colspan="3" align="center"><h3>createClass.do</h3>
                        <br/>
                        <script type="text/javascript">document.write(document.forms[0].action);</script>

                    </td>
                </tr>
                <tr>
                    <td style="width:150px">jid:</td>
                    <td><input type="text" name="jid" id="txt_jid" value="2470492"/></td>
                    <td style="width:250px"></td>
                </tr>
                <tr>
                    <td>schoolId:</td>
                    <td><input type="text" name="schoolId" id="txt_schoolId" value="50002"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>gradeId:</td>
                    <td>
                        <input type="text" name="gradeId" id="txt_gradeId" value="2"/>
                    </td>
                    <td>数字：1:高三 2:高二 3:高一 4：初三 5:初二 6:初一<br/>
                        7:小六 7:小五 7:小四 7:小三 7:小二 7:小一 0:其他 </td>
                </tr>
                <tr>
                    <td>className:</td>
                    <td><input type="text" name="className" id="txt_className" value="测试一班"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>subjectId:</td>
                    <td><input type="text" name="subjectId" id="txt_subjectId" value="1"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>isAdmin:</td>
                    <td><input type="radio" name="isAdmin" id="rdo_isAdmin_1" value="1"/>是
                        <input type="radio"  checked name="isAdmin" id="rdo_isAdmin_0" value="0"/>不是
                    </td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>time:</td>
                    <td><input type="text" name="time" id="txt_time" readonly value=""/></td>
                    <td><input type="button" value="当前时间" onclick="txt_time.value=new Date().getTime()+'';"/>
                        <span style="color:red;font-size:12px;">提示：三分钟内有效!</span>
                    </td>
                </tr>
                <tr>
                    <td>sign:</td>
                    <td><input type="text" name="sign" id="txt_sign" value=""/></td>
                    <td><input type="button" value="生成" onclick="genderSign('createClass.do')"/>
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
    <iframe id="ifm_result"  name="ifm_result" width="650px;height:300px;"></iframe>
</div>

</body>
</html>
