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
    <title>im接口---获取试卷试题</title>
    <script type="text/javascript">
        $(function(){
            $("#dv_daohao a").click(function(){
                var dVal=$(this).attr("data-bind");
                $(this).parent().children().removeClass("selected");
                $(this).addClass("selected");
                $("#dv_xuanxiang>div").hide();
               $("#dv_xuanxiang #"+dVal).show();
            });
        });
        //生成加密串
        function genderSign(methodName){
            var requestMonth=$("#txt_requestMonth").val();
            var requestYear=$("#txt_requestYear").val();
            var schoolId=$("#txt_schoolId").val();
            var jid=$("#txt_jid").val();
            var userType=$("#txt_userType").val();
            var classId=$("#txt_classId").val();

            var time=$("#txt_time").val();
            var param={classId:classId,requestMonth:requestMonth,requestYear:requestYear,time:time,schoolId:schoolId,jid:jid,userType:userType};
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

<div id="dv_daohao" align="center">
    <a class="selected" data-bind="select">测试查询</a>
</div>
<div id="dv_xuanxiang">

<div id="select"  align="center">
    <%--//获取参数--%>
    <%--String jid=paramMap.get("jid");--%>
    <%--String schoolId=paramMap.get("schoolId");--%>
    <%--String classId=paramMap.get("classId");--%>
    <%--String userType=paramMap.get("userType");--%>
    <%--String courseId=paramMap.get("courseId");--%>
    <%--String time=paramMap.get("time");--%>
    <%--String sign=paramMap.get("sign");--%>


        <form action="../imapi1_1?m=StuClassCalendar"  target="ifm_result" method="post">
            <table style="width:650px;">
               <!-- <tr>
                    <td colspan="3" align="center"><h3>getTaskPaperQuestion</h3></td>
                </tr> -->
                <tr>
                    <td style="width:150px">requestMonth:</td>
                    <td><input type="text" name="requestMonth" id="txt_requestMonth" value="08"/></td>
                    <td style="width:250px"></td>
                </tr>
                <tr>
                    <td>requestYear:</td>
                    <td><input type="text" name="requestYear" id="txt_requestYear" value="2014"/></td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td style="width:150px">JID:</td>
                    <td><input type="text" name="jid" id="txt_jid" value="2250913"/></td>
                    <td style="width:250px"></td>
                </tr>
                <tr>
                    <td>schoolId:</td>
                    <td><input type="text" name="schoolId" id="txt_schoolId" value="50000"/></td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>classId:</td>
                    <td><input type="text" name="classId" id="txt_classId" value="4"/></td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>userType:</td>
                    <td><input type="text" name="userType" id="txt_userType" value="3"/></td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>time:</td>
                    <td><input type="text" name="time" id="txt_time" value=""/></td>
                    <td><input type="button" value="当前时间" onclick="txt_time.value=new Date().getTime()+'';"/>
                        <span style="color:red;font-size:12px;">提示：三分钟内有效!</span></td>
                </tr>
                <tr>
                    <td>sign:</td>
                    <td><input type="text" name="sign" id="txt_sign" value=""/></td>
                    <td><input type="button" value="生成" onclick="genderSign('StuClassCalendar')"/>

                    </td>
                </tr>
                <tr>
                    <td colspan="3" align="center"><input type="submit" value="提交--测试"/></td>
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
