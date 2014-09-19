<!doctype html>
<html lang="en">
<%response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
    //response.flushBuffer();%>
<head>
    <meta charset="utf-8">
    <title>jQuery UI Dialog - Animation</title>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script>
        $(function() {
            $( "#dialog" ).dialog({
                autoOpen: false,
                width:$(document).width(),
                height:$(document).height(),
                close:function(){
                    $("#dialog").hide();
                },
                overlay: { opacity: 0.1, background: "black" },
                modal:true
            });
            $( "#opener" ).click(function() {
                $("#dialog").css({ "background": "black"  });

                $( "#dialog" ).dialog( "open" );
                $("#dialog").show();
            });
        });
    </script>
</head>
<body >
<%--<div id="dialog" title="" style="background:url('http://192.168.8.238/sz_school/images/test.jpg')  center center no-repeat">--%>
<div id="dialog" title="" >
    <%----%>
    <%--<img src="http://192.168.8.238/sz_school/images/pic13_140704.jpg" style="position:absolute;left:50%;top:50%"/>--%>
       <table width="100%" height="100%" align="center">
           <tr>
               <td align="middle">
                      <img src="http://192.168.8.238/sz_school/images/test1.jpg" style=""/>
            </td>
           </tr>
       </table>

</div><br>11111
<br>11111
<br>11111

<button id="opener">Open Dialog</button>
</body>
</html>


<script type="text/javascript">
    function isIE() { //ie?
        if (!!window.ActiveXObject || "ActiveXObject" in window)
            return true;
        else
            return false;
    }
    function resizeimg(ImgD, iwidth, iheight,imPicSrc) {
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

            alert("realh  "+realh+"w    "+w+"window.screen.availHeight     "+window.screen.availHeight+"#imgtop   =="+$("#img").offset().top+
                    "ImgD.height    "+ImgD.height+"window.innerHeight  "+window.innerHeight);
            var positionY =0;
            //positionY =(h-ImgD.height)/2;
            positionY =(h-ImgD.height)/2+$("#img").offset().top;

            alert("positionY"+positionY+"            sTop"+sTop)
            if(positionY<0) positionY =0;

            var positionX = 0;
            positionX =(w-ImgD.width)/2;
            if(positionX<0) positionX =0;

            // alert("w "+w+"         h "+h+"              img_w " +ImgD.width+"           img_h  "+ImgD.height+"             left "+positionX+"            top "+positionY);
            var myOffset = new Object();
            myOffset.left = positionX;
            myOffset.top = positionY;
            $("#img").offset(myOffset);
            $('#img').show();
        },100);
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


            if(isIE())
            {
                var myOffset = new Object();
                myOffset.left = 0;
                myOffset.top = 0;
                $("#img").offset(myOffset);
                $("#img").hide();
                resizeimg($("#img").get(0),w-40,h-40,"http://192.168.8.238/sz_school/images/test.jpg");

            }
            var myOffset = new Object();
            myOffset.left = 0;
            myOffset.top = 0;
            $("#img").offset(myOffset);
            $("#img").hide();
            //    resizeimg($("#img").get(0),w-40,h-40,"http://192.168.8.238/sz_school/images/test.jpg");

            $('#isgame').dialog("open");
        });


        $("#got").click(function () {
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


            if(isIE())
            {
                var myOffset = new Object();
                myOffset.left = 0;
                myOffset.top = 0;
                $("#img").offset(myOffset);
                $("#img").hide();
                resizeimg($("#img").get(0),w-40,h-40,"http://192.168.8.238/sz_school/images/test1.jpg");

            }
            var myOffset = new Object();
            myOffset.left = 0;
            myOffset.top = 0;
            $("#img").offset(myOffset);
            $("#img").hide();
            resizeimg($("#img").get(0),w-40,h-40,"http://192.168.8.238/sz_school/images/test1.jpg");
        });
    });
</script>
