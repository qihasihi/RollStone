<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<script type="text/javascript">
    function paly(){
        jwplayer("div").setup({
            'width': '560',
            'height': '500',
            'analytics': {
                'cookies': false,
                'enabled': false
            },
            'file':'http://web.etiantian.com/ett20/hls/hd.m3u8?p=wkczsx000178&s=b&t=1421552604&v=421a67d3f6501c4d59afc146fd55095f&h=http%3A%2F%2Fhd.etiantian.net',
            'primary': "flash",
            'androidhls':"true",
            'autostart': "true",
            'startparam': "start"
        });
    }

    $(function(){
        paly();
    })
</script>
<body>
    <div id="div">

    </div>
</body>
</html>
