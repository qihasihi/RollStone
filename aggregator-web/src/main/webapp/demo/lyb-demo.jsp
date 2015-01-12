<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-5-7
  Time: 下午6:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DEMO</title>
    <script src="../util/xheditor/jquery/jquery-1.4.4.min.js" type="text/javascript"></script>
    <!--Uploadify-->
    <script type="text/javascript" src="../ueditor/lyb/uploadify/jquery.uploadify.js"></script>
    <link rel="stylesheet" type="text/css" href="../ueditor/lyb/uploadify/uploadify.css"/>
    <!--ueditor-->
    <script  type="text/javascript" src="../ueditor/ueditor.config.js"></script>
    <script   type="text/javascript" src="../ueditor/ueditor.all.js"></script>
    <link rel="stylesheet" type="text/css" href="../ueditor/themes/default/css/ueditor.css">

    <script type="text/javascript" src="../ueditor/lyb/ett-lyb%200.1.js"></script>
    <script type="text/javascript">
var objUploadify=null;
var ettLyb=null;
        $(function() {
             ettLyb=new EttLyb({
                addressid:'dv_textarea',
                controlid:'ettLyb',
                 isHasImage:false
              });
        });
    </script>
</head>
<body>
    <div id="dv_textarea">
    </div>
</body>
</html>
