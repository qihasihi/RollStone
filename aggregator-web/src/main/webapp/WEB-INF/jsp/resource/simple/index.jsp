<%@page contentType="text/html;charset=GBK" import="java.util.*"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<html>
<head>
    <script type="text/javascript" src="js/resource/new_resbase.js"></script>
</head>
<body>
<div id="header">
    <div class="title">����ѧУ��Դϵͳ<a target="_blank" href="helps/help.html" class="help" title="����"></a></div>
    <p><span></span><img src="images/logo_140616.png" width="253" height="64" /></p>
</div>
<div class="content2">
    <ul class="zyxt_jb_home_pic">
        <li><a href="<%=UtilTool.utilproperty.getProperty("SIMPLE_INTO_ETT_TEA_URL")%>" target="_blank"><img src="images/pic10_140616.jpg" width="125" height="74" title="��������ʦ��"></a></li>
        <li><a href="<%=UtilTool.utilproperty.getProperty("SIMPLE_INTO_ETT_TEA_URL")%>?freeModelName=tutor" target="_blank"><img src="images/pic11_140616.jpg" width="125" height="74" title="Զ�̽������"></a></li>
        <li><a href="<%=UtilTool.utilproperty.getProperty("SIMPLE_INTO_ETT_TEA_URL")%>?freeModelName=digitalCampus" target="_blank"><img src="images/pic12_140616.jpg" width="748" height="74" title="����������У����У԰"></a></li>
    </ul>
    <div class="zyxt_jb_homeR">
        <p><strong class="font-darkblue">��Դ����</strong></p>
        <ul class="font-darkblue">
            <li><a href="simpleRes?m=toAllResPage&subjectid=1" target="_blank" class="yw">����</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=2" target="_blank" class="sx">��ѧ</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=3" target="_blank" class="yy">Ӣ��</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=4" target="_blank" class="wl">����</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=5" target="_blank" class="hx">��ѧ</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=7" target="_blank" class="sw">����</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=6" target="_blank" class="ls">��ʷ</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=8" target="_blank" class="dl">����</a></li>
            <li><a href="simpleRes?m=toAllResPage&subjectid=9" target="_blank" class="zz">����</a></li>
        </ul>
    </div>
    <div class="zyxt_jb_homeL public_input">
        <form action="<%=UtilTool.utilproperty.getProperty("SIMPLE_LOGIN_ETT_TEA_URL")%>" method="post" name="ettFrm" id="ettFrm">
        <p><strong class="font-darkblue">��ʦ���¼</strong></p>
        <p><input  type="text" class="w200 t_c" name="user.user_name" placeholder="---�����û���---" /></p>
        <p><input type="password" class="w200 t_c" name="user.password" placeholder="---��������---"/></p>
        <p class="t_c"><a href="javascript:;" onclick="ettFrm.submit()" class="an_big">��¼</a><a href="http://web.etiantian.com/cooperateschool/common/registe2013.jsp" target="_blank" class="an_big">ע��</a></p>
        </form>
    </div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>
