
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/util/common-jsp/common-im.jsp"%>

<html>
<head>

    <title></title>

</head>
<body>

<a id="go"><img src="http://192.168.8.238/sz_school/images/pic13_140704.jpg" width="200" height="100"/></a>
<div id="go2" style="display: none;">

    <div id="go3">
        <a style="float: right" href="javascript:;" onclick="$(this).parent().parent().hide();">

    <img src="http://192.168.8.238/sz_school/images/close.png" width="40" height="40"/></a>
     <img id="img"  src=""  />
    <div>
</div>
<script>

    // alert($(document).height()+" go "+$(document).width());
    var h=$(document).height();
    var w=$(document).width();
    var phone=1;//是否为手机
    if(phone==1)
    {
        if(h>w)
        {// for phone
            var temph=w;
            w=h;
            h=temph;
        }
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
                if( (image.width/image.height) >=(iwidth/iheight) )//宽高比做比较，看处理宽还是高
                {
                    if (image.width > iwidth) {
                        ImgD.width = iwidth;
                        ImgD.height = (image.height * iwidth) / image.width;
                    } else {
                        ImgD.width = image.width;
                        ImgD.height = image.height;
                    }

                }
                else
                {

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
         var positionY =0;
         if (phone==1)  positionY =(w-40-ImgD.height)/2-20;
         else  positionY =(h-ImgD.height)/2;

         if(positionY<0) positionY =0;

         var positionX = 0;
         if (phone==1)  positionX =(h-40-ImgD.width)/2+20;
         else  positionX =(w-ImgD.width)/2;
         if(positionX<0) positionX =0;

       // alert("w "+w+"         h "+h+"              img_w " +ImgD.width+"           img_h  "+ImgD.height+"             left "+positionX+"            top "+positionY);
         var myOffset = new Object();


         myOffset.left = positionX;
         myOffset.top = positionY;
         $("#img").offset(myOffset);

            $('#img').show();
    },300);
 }



    $(document).ready(function () {




        $("#go").click(function () {
            /*属性*/
            $("#go2").css({"display": "", "position": "absolute",   "top": "0px",
                "left": "0px", "right": "0px", "bottom": "0px", "background": "black", "visibility": "visible", "filter": "Alpha(opacity=100)"
            });


             /*高为屏幕的高*/
            $("#go2").css({
                height: function () {
                    return $(document).height();
                },
                width:"100%"

            });
            var myOffset = new Object();
            myOffset.left = 0;
            myOffset.top = 0;
            $("#img").offset(myOffset);
            $("#img").hide();
            if(phone==1)  resizeimg($("#img").get(0),h-40,w-40,"http://192.168.8.238/sz_school/images/test.jpg");
            else resizeimg($("#img").get(0),w-40,h-40,"http://192.168.8.238/sz_school/images/test.jpg");


        });
    });
</script>