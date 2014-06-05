<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-5-20
  Time: 下午4:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="util/common.jsp"%>
<html>
<head>
    <title>测试图片轮换</title>
    <link type="text/css"  rel="stylesheet" href="util/ImageRotate/ImageRotate.css"/>
    <script type="text/javascript" src="util/ImageRotate/ett-viewphoto 0.1.js"></script>
    <script type="text/javascript" src="util/ImageRotate/ImageRotate 0.1.js"></script>
    <script type="text/javascript">
        var ettViewPhoto=null;
        $(function(){
            $.get("util/ImageRotate/data-demo.txt",function(rps){
               /// var d=eval("("+$("#datajson").html()+")");
                ettViewPhoto=new EttViewPhoto({
                    "imgIdx":0,
                    "basePath":'util/ImageRotate/',
                    "addressid":'dv_rotateme',
                    "photoObj":rps,
                    "pageObj":{},
                    "photoRemarkObjName":"remark",
                    "photoUrlObjName":"url",
                    "controlid":"ettViewPhoto"
                    //,"width":""
                });
            },'json');
        })
    </script>
</head>
<body>
<div id="dv_rotateme" style="width:1100px;background-color: black;">

</div>
</body>
</html>
