<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-12-30
  Time: 上午11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/util/common-jsp/common-im.jsp"%>
<html>
<head>
    <title></title>
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
    <script type="text/javascript">
        //生成加密串
        function genderSign(methodName){

            var schoolId=$("#txt_schoolId").val();
            var jid=$("#txt_jid").val();
            var time=$("#txt_time").val();
            var gradeId = $("#txt_gradeId").val();
            var subjectId = $("#txt_subjectId").val();
            var classArray = $("#txt_classArray").val();
            var courseName = $("#txt_courseName").val();
            var param={schoolId:schoolId,jid:jid,time:time,gradeId:gradeId,subjectId:subjectId,courseName:courseName};
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
<div id="dv_daohao" align="center">
    <a class="selected" data-bind="select">测试添加专题</a>
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


        <form action="../../im1.1.5?m=createCourse"  target="ifm_result" method="post">
            <table style="width:650px;">
                 <tr>
                     <td colspan="3" align="center"><h3>http://192.168.8.238/sz_school/im1.1.5?m=createCourse</h3></td>
                 </tr>
                <tr>
                    <td style="width:150px">JID:</td>
                    <td><input type="text" name="jid" id="txt_jid" value="2470492"/></td>
                    <td style="width:250px"></td>
                </tr>
                <tr>
                    <td>schoolId:</td>
                    <td><input type="text" name="schoolId" id="txt_schoolId" value="50002"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>courseName:</td>
                    <td><input type="text" name="courseName" id="txt_courseName" value="测试手机专题"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>gradeId:</td>
                    <td><input type="text" name="gradeId" id="txt_gradeId" value="2"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>subjectId:</td>
                    <td><input type="text" name="subjectId" id="txt_subjectId" value="1"/></td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>classArray:</td>
                    <td><textarea  name="classArray" id="txt_classArray">
                        [
                        {"classId":689194,"startTime":"2015-01-04 00:00:00","endTime":"2015-01-13 00:00:00"},
                        {"classId":323911,"startTime":"2015-01-05 00:00:00","endTime":"2015-01-13 00:00:00"},
                        {"classId":752358,"startTime":"2015-01-06 00:00:00","endTime":"2015-01-13 00:00:00"}
                        ]
                    </textarea></td>
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
                    <td><input type="button" value="生成" onclick="genderSign('createCourse')"/>

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
    <iframe id="ifm_result" name="ifm_result" width="650px;height:300px;"></iframe>
</div>
</body>
</html>
