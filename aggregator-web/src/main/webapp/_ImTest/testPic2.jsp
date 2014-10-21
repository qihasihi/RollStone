<!doctype html>
<html lang="en">
<%@include file="/util/common-jsp/common-im.jsp"%>
<%response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
    //response.flushBuffer();%>



<head>
    <meta charset="utf-8">
    <title>jQuery UI Dialog - Animation</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>js/1.6/themes/ui-lightness/ui.all.css"/>
    <script src="<%=basePath %>js/1.6/jquery-ui-160.min.js"></script>

    <script>
        function closedailog()
        {   //alert("in close")
            $( "#dialog" ).dialog("close");


        }
        //alert("clientWidth="+document.documentElement.clientWidth+"    clientHeight="+document.documentElement.clientHeight)
        var w=document.documentElement.clientWidth;
        var h=document.documentElement.clientHeight;
        //phone
       // var h=800;
       // var w=480;
        $(function() {
            $( "#dialog" ).dialog({
                autoOpen: false,
                width:w,
               // height:window.screen.availHeight,
               // height:$(document).height(),

                height:h,
                close:function(){
                    $("#dialog").hide();
                },
                overlay: { opacity: 1 },
                modal:true
            });



            $( "#opener" ).click(function() {
               // $("#dialog").css({ "background": "black"  });





                $( "#dimg" ).hide();
                $( "#dialog" ).dialog( "open" );
                resizeimg($("#dimg").get(0),w,h,
                        "http://192.168.8.238/sz_school/images/test.jpg");


            });

            $( "#opener1" ).click(function() {
               // $("#dialog").css({ "background": "black"  });




                $( "#dimg" ).hide();
                $( "#dialog" ).dialog( "open" );
                resizeimg($("#dimg").get(0),w,h,
                        "http://192.168.8.238/sz_school/images/test1.jpg");



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
               <td align="middle"  width="100%">
                      <center><img id="dimg" src="" onclick="closedailog()" /></center>
            </td>
           </tr>
       </table>

</div><br>11111
<br>11111
<br>11111
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
333333333333
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
5555555555555
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<button id="opener">Open Dialog</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<button id="opener1">Open Dialog 1 </button>
r><br><br><br><br><br><br><br><br><br><br><br><br><br>
</body>
</html>


<script type="text/javascript">

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

                $("#dimg").show();
            }


        },200);


    }


</script>
