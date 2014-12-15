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
        table{background-color:white;}
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
        a {
            font-size: 20px;
            color:dodgerblue;
            text-align: center;
            text-decoration: underline;
        }
    </style>
    <title>IM爱学--测试JSP</title>
    <script type="text/javascript">
$(function(){
        $("a").attr("target","_blank");
})
        //生成加密串
        function genderSign(methodName){

            var jid=$("#txt_jid").val();
            var schoolId=$("#txt_schoolId").val();
            var classCode=$("#txt_classCode").val();
            var time=$("#txt_time").val();
            var param={
                jid:jid
                ,schoolId:schoolId
                ,time:time
                ,classCode:classCode
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

<div id="dv_xuanxiang" style="padding-top: 50px">
    <div  align="center">
            <a href="testCreateClass.jsp">创建班级</a>
            <br>
            <a href="testManagerClassList.jsp">管理班级</a>
            <br>
            <a href="testRegisterTeacher.jsp">注册教师</a>
            <br>
            <a href="testRegisterStudent.jsp">注册学生</a>
            <br>
            <a href="testSearchClass.jsp">学生查询班级信息</a>
            <br>
            <a href="testStuJoinClass.jsp">学生加入班级</a>
            <br>
            <a href="testTeaJoinClass.jsp">老师加入班级</a>
            <br>
            <a  href="testSchoolList.jsp">查询分校信息</a>
            <br>
            <a  href="testStudyModule.jsp">查询学习栏目(新)</a>
            <br>
            <a  href="testClassDetailInfo.jsp">查询班级详情</a>
        <br>
        <a  href="testSaveClassTimePoint.jsp">添加班级动态访问时间点</a>
        <br>
        <a  href="testRemoveClassMember.jsp">移出班级人员</a>
        <br>
        <a  href="testUpdateTeaClassSubject.jsp">修改教师班级身份</a>

        <br>
        <a  href="testCheckUserName.jsp">检测数校用户名</a>
        <br>
        <a  href="testUpdateUser.jsp">修改数校用户</a>

    </div>

</div>






</body>
</html>
