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

    </script>
</head>
<body>

<div id="dv_xuanxiang" style="padding-top: 50px">
    <div  align="center">
            <a href="CourseRecord.jsp">查询，添加课程纪实，</a>
            <br>
            <a href="testPaperTest.jsp">测试试卷JSP</a>
            <br>
            <a href="testImSaveUserScore.jsp">任务获得积分</a>
            <br>
    </div>

</div>






</body>
</html>
