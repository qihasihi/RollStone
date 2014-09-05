<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-8-20
  Time: 上午10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/util/common-jsp/common-im.jsp"%>
<html>
<head>

    <title></title>
    <script type="text/javascript">

    </script>
</head>
<body>



<a id="go"><img src="http://192.168.8.238/sz_school/images/test1.jpg" width="200" height="100"/></a>
<div id="go2" style="display: none;">
    <a style="float: right" href="javascript:;" onclick="$(this).parent().hide();"><h1 style="color: #ffffff">×</h1></a>


    <script>

       alert($(document).height()+" go "+$(document).width());
        var h=$(document).height();
        var w=$(document).width();
        if(h>w)
        {
            var temph=w;
            w=h;
            h=temph;
        }
    </script>

    <div id="imgdiv" >
        <img id="img" src="http://192.168.8.238/sz_school/images/test1.jpg"  />
    </div>
</div>



</body>
</html>


<script type="text/javascript">
    function resizeimg(ImgD, iwidth, iheight) {
       alert(iwidth+" www "+iheight);
        var image = new Image();

        image.src = ImgD.src ;


        setTimeout(function(){
    if (image.width > 0 && image.height > 0) {
        if(image.width>image.height)
        {
            if (image.width > iwidth) {
                ImgD.width = iwidth;
                ImgD.height = (image.height * iwidth) / image.width;
            } else {
                ImgD.width = image.width;
                ImgD.height = image.height;
            }

        }
        else{

            if (image.height > iheight) {
                ImgD.height = iheight;
                ImgD.width = (image.width * iheight) / image.height;
            } else {
                ImgD.width = image.width;
                ImgD.height = image.height;
            }

        }
    }
            alert("in here"+ImgD.width+"  "+ImgD.height);
            $('#img').show();
},300);




    }



    $(document).ready(function () {

        $("#go").click(function () {
            /*属性*/
            $("#go2").css({"display": "", "position": "absolute", "text-align": "center", "top": "0px", "left": "0px", "right": "0px", "bottom": "0px", "background": "black", "visibility": "visible", "filter": "Alpha(opacity=100)"
            });
            /*
            $("#img").css({
                height: function () {
                    return $(document).height();
                },
                width:"100%"

            });
            */
             /*高为屏幕的高*/
            $("#go2").css({
                height: function () {
                    return $(document).height();
                },
                width:"100%"

            });
            $("#img").hide();
            resizeimg($("#img").get(0),h,w);
        });
    });
</script>