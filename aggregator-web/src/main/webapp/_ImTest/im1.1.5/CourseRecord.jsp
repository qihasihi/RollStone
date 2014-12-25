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
        function genderSign(methodName,mtype){

            var schoolId=$("#txt_"+mtype+"_schoolId").val();
            var jid=$("#txt_"+mtype+"_jid").val();
            var classId=$("#txt_"+mtype+"_classId").val();
            var time=$("#txt_"+mtype+"_time").val();
            var param={schoolId:schoolId,jid:jid,classId:classId,time:time};
            if(mtype=='a'){
                var courseId=$("#txt_"+mtype+"_courseId").val();
                param.courseId=courseId;
                var attachDescribe=$("#txt_"+mtype+"_attachDescribe").val();
                var attach=$("#txt_"+mtype+"_attach").val();
                param.attach=attach;
                if(attachDescribe.Trim().length>0)
                  param.attachDescribe=attachDescribe;
            }else if(mtype=='d'){
                var recordId=$("#txt_"+mtype+"_recordId").val();
                param.recordId=recordId;
            }else if(mtype=='s'){
                var courseId=$("#txt_"+mtype+"_courseId").val();
                param.courseId=courseId;
                var userType=$("#txt_"+mtype+"_userType").val();
                param.userType=userType;
            }
            param.method=methodName;

            $.ajax({
                url:'../_manager/GenderCourseRecordSign.jsp',
                type:'post',
                dataType:'text',
                data:param,
                success:function(rps){
                    $("#txt_"+mtype+"_sign").val(rps.Trim());
                }
            })
        }

    </script>
</head>
<body>
<div id="dv_daohao" align="center">
    <a class="selected" data-bind="select">测试查询</a>
    <a  data-bind="add">测试添加</a>
</div>
<div id="dv_xuanxiang">
<div id="add" style="display:none" align="center">
    //获取参数
    <%--String jid=paramMap.get("jid");--%>
    <%--String schoolId=paramMap.get("schoolId");--%>
    <%--String classId=paramMap.get("classId");--%>
    <%--String courseId=paramMap.get("courseId");--%>
    <%--String content=paramMap.get("attachDescribe");--%>
    <%--String attach=paramMap.get("attach");--%>
    <%--String time=paramMap.get("time");--%>
    <%--String sign=paramMap.get("sign");--%>
    <form action="../../im1.1.5?m=AddCourseRecord" target="ifm_result" method="post">
    <table style="width:650px;">
        <tr>
            <td colspan="3" align="center"><h3>AddCourseRecord</h3></td>
        </tr>
        <tr>
            <td style="width:150px">JID:</td>
            <td><input type="text" name="jid" id="txt_a_jid" value="1843365"/></td>
            <td style="width:250px"></td>
        </tr>
        <tr>
            <td>schoolId:</td>
            <td><input type="text" name="schoolId" id="txt_a_schoolId" value="50002"/></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>classId:</td>
            <td><input type="text" name="classId" id="txt_a_classId" value="1"/></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>courseId:</td>
            <td><input type="text" name="courseId" id="txt_a_courseId" value="1"/></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>attachDescribe:</td>
            <td><input type="text" name="attachDescribe" id="txt_a_attachDescribe" value="abc"/></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>attach:</td>
            <td><input type="text" name="attach" id="txt_a_attach" value="abc"/></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>time:</td>
            <td><input type="text" name="time" id="txt_a_time" value=""/></td>
            <td><input type="button" value="当前时间" onclick="txt_a_time.value=new Date().getTime()+'';"/>
                <span style="color:red;font-size:12px;">提示：三分钟内有效!</span>
            </td>
        </tr>
        <tr>
            <td>sign:</td>
            <td><input type="text" name="sign" id="txt_a_sign" value=""/></td>
            <td><input type="button" value="生成" onclick="genderSign('AddCourseRecord','a')"/>
              </td>
        </tr>
        <tr>
            <td colspan="3"  align="center"><input type="submit" value="提交--测试"/>

            </td>
        </tr>
    </table>
    </form>
</div>


<div id="select"  align="center">
    <%--//获取参数--%>
    <%--String jid=paramMap.get("jid");--%>
    <%--String schoolId=paramMap.get("schoolId");--%>
    <%--String classId=paramMap.get("classId");--%>
    <%--String userType=paramMap.get("userType");--%>
    <%--String courseId=paramMap.get("courseId");--%>
    <%--String time=paramMap.get("time");--%>
    <%--String sign=paramMap.get("sign");--%>


        <form action="../../im1.1.5?m=GetClassRecord"  target="ifm_result" method="post">
            <table style="width:650px;">
                <tr>
                    <td colspan="3" align="center"><h3>GetClassRecord</h3></td>
                </tr>
                <tr>
                    <td style="width:150px">JID:</td>
                    <td><input type="text" name="jid" id="txt_s_jid" value="1843365"/></td>
                    <td style="width:250px"></td>
                </tr>
                <tr>
                    <td>schoolId:</td>
                    <td><input type="text" name="schoolId" id="txt_s_schoolId" value="50002"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>classId:</td>
                    <td><input type="text" name="classId" id="txt_s_classId" value="1"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>userType:</td>
                    <td><input type="text" name="userType" id="txt_s_userType" value="1"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>courseId:</td>
                    <td><input type="text" name="courseId" id="txt_s_courseId" value="1"/></td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>time:</td>
                    <td><input type="text" name="time" id="txt_s_time" value=""/></td>
                    <td><input type="button" value="当前时间" onclick="txt_s_time.value=new Date().getTime()+'';"/>
                        <span style="color:red;font-size:12px;">提示：三分钟内有效!</span></td>
                </tr>
                <tr>
                    <td>sign:</td>
                    <td><input type="text" name="sign" id="txt_s_sign" value=""/></td>
                    <td><input type="button" value="生成" onclick="genderSign('GetClassRecord','s')"/>

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
