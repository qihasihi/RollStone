
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/util/common-jsp/common-im.jsp"%>
<html>
<head>

    <title></title>

</head>
<body>

<a id="go"><img src="http://192.168.8.238/sz_school/images/pic13_140704.jpg" width="200" height="100"/></a>
<div id="go2" style="display: none;">
<a style="float: right" href="javascript:;" onclick="$(this).parent().hide();"><font size="100px" style="color: #ffffff">×</font></a>

        <img id="img" align="middle" src=""  />

</div>
<script>

    // alert($(document).height()+" go "+$(document).width());
    var h=$(document).height();
    var w=$(document).width();
    if(h>w)
    {// for phone
        var temph=w;
        w=h;
        h=temph;
    }
</script>


</body>
</html>


<script type="text/javascript">
    function resizeimg(ImgD, iwidth, iheight,imPicSrc) {
    //   alert(iwidth+" www "+iheight);
        var image = new Image();
        ImgD.src=imPicSrc;
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

         //for phone
         var positionY =(w-ImgD.height)/2;
         var positionX = 0;
         var myOffset = new Object();
        // alert("w "+w+"         h "+h+"              img_w " +ImgD.width+"           img_h  "+ImgD.height+"             left "+positionX+"            top "+positionY);
         myOffset.left = positionX;
         myOffset.top = positionY;
         $("#img").offset(myOffset);
          //$('#aa').top("300px");
            $('#img').show();
    },300);
 }



    $(document).ready(function () {

        $("#go").click(function () {
            /*属性*/
            $("#go2").css({"display": "", "position": "absolute", "text-align": "center",  "top": "0px",
                "left": "0px", "right": "0px", "bottom": "0px", "background": "black", "visibility": "visible", "filter": "Alpha(opacity=100)"
            });


             /*高为屏幕的高*/
            $("#go2").css({
                height: function () {
                    return $(document).height();
                },
                width:"100%"

            });
            $("#img").hide();
            resizeimg($("#img").get(0),h,w,"http://192.168.8.238/sz_school/images/test.jpg");
        });
    });
</script>