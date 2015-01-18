<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<script type="text/javascript">
    function paly(){
        jwplayer.key='TS4qsaxnmU61G+MTcWh8YKllWcQ=';
        jwplayer("div").setup({
            'width': '560',
            'height': '500',
            'analytics': {
                'cookies': false,
                'enabled': false
            },
            'primary': "flash",
            'androidhls':"true",
            'autostart': "false",
            'startparam': "start",
            'file':'http://web.etiantian.com/ett20/hls/hd.m3u8?p=wkczsx000178&s=b&t=1421559031&v=4d1dc3b94857cf558b36bcd734649080&h=http%3A%2F%2Fhd.etiantian.net'
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
