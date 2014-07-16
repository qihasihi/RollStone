<%@page contentType="text/html;charset=GBK" import="java.util.*"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<html>
<head>
    <script type="text/javascript" src="js/resource/new_resbase.js"></script>
</head>
<body>
<div id="header">
    <div class="title">合作学校资源系统<a target="_blank" href="helps/help.html" class="help" title="帮助"></a></div>
    <p><span></span><img src="images/logo_140616.png" width="253" height="64" /></p>
</div>
<div class="content2">
    <ul class="zyxt_jb_home_pic">
        <li><a href="<%=UtilTool.utilproperty.getProperty("SIMPLE_INTO_ETT_TEA_URL")%>" target="_blank"><img src="images/pic10_140616.jpg" width="125" height="74" title="点击进入教师版"></a></li>
        <li><a href="<%=UtilTool.utilproperty.getProperty("SIMPLE_INTO_ETT_TEA_URL")%>?freeModelName=tutor" target="_blank"><img src="images/pic11_140616.jpg" width="125" height="74" title="远程教研入口"></a></li>
        <li><a href="<%=UtilTool.utilproperty.getProperty("SIMPLE_INTO_ETT_TEA_URL")%>?freeModelName=digitalCampus" target="_blank"><img src="images/pic12_140616.jpg" width="748" height="74" title="北京四中网校数字校园"></a></li>
    </ul>
    <div class="zyxt_jb_homeR">
        <p><strong class="font-darkblue">资源中心</strong></p>
        <ul class="font-darkblue">
            <li><a href="simpleRes?m=toAllResPage&subjectid=1" target="_blank" class="yw">语文</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=2" target="_blank" class="sx">数学</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=3" target="_blank" class="yy">英语</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=4" target="_blank" class="wl">物理</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=5" target="_blank" class="hx">化学</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=7" target="_blank" class="sw">生物</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=6" target="_blank" class="ls">历史</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=8" target="_blank" class="dl">地理</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=9" target="_blank" class="zz">政治</a></li>
        </ul>
    </div>
    <div class="zyxt_jb_homeL public_input">
        <form action="<%=UtilTool.utilproperty.getProperty("SIMPLE_LOGIN_ETT_TEA_URL")%>" method="post" name="ettFrm" id="ettFrm">
        <p><strong class="font-darkblue">教师版登录</strong></p>
        <p><input  type="text" class="w200 t_c" name="user.user_name" placeholder="---输入用户名---" /></p>
        <p><input type="password" class="w200 t_c" name="user.password" placeholder="---输入密码---"/></p>
        <p class="t_c"><a href="javascript:;" onclick="ettFrm.submit()" class="an_big">登录</a><a href="http://web.etiantian.com/cooperateschool/common/registe2013.jsp" target="_blank" class="an_big">注册</a></p>
        </form>
    </div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
