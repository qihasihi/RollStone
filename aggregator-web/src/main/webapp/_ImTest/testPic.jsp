<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-8-20
  Time: 上午10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../util/common-base.jsp"%>
<html>
<head>
    <style>
        table{background-color:ghostwhite;}
        #dv_daohao{
            padding:10px 10px 30px 30px;
        }
        #dv_daohao a{
            padding-right:20px;
            font-size:16px;
            font-weight: bold;
            color:black;
        }
        #dv_daohao a:hover{
            color:red;
        }
        #dv_daohao .selected{
            color:blue;
            text-decoration: brown;
        }
    </style>
    <title>im接品---课堂纪实</title>
    <script type="text/javascript">
        $(function(){
            $("#dv_daohao a").click(function(){
                var dVal=$(this).attr("data-bind");
                $(this).parent().children().removeClass("selected");
                $(this).addClass("selected");
                $("#dv_xuanxiang>div").hide();
               $("#dv_xuanxiang #"+dVal).show();
            });
        });
        //生成加密串
        function genderSign(methodName,mtype){

            var schoolId=$("#txt_"+mtype+"_schoolId").val();
            var jid=$("#txt_"+mtype+"_jid").val();
            var classId=$("#txt_"+mtype+"_classId").val();
            var time=$("#txt_"+mtype+"_time").val();
            var param={schoolId:schoolId,jid:jid,classId:classId,time:time};
            if(mtype=='a'){
                var courseId=$("#txt_"+mtype+"_courseId").val();
                param.courseId=courseId;
                var attachDescribe=$("#txt_"+mtype+"_attachDescribe").val();
                var attach=$("#txt_"+mtype+"_attach").val();
                param.attach=attach;
                if(attachDescribe.Trim().length>0)
                  param.attachDescribe=attachDescribe;
            }else if(mtype=='d'){
                var recordId=$("#txt_"+mtype+"_recordId").val();
                param.recordId=recordId;
            }else if(mtype=='s'){
                var courseId=$("#txt_"+mtype+"_courseId").val();
                param.courseId=courseId;
                var userType=$("#txt_"+mtype+"_userType").val();
                param.userType=userType;
            }
            param.method=methodName;

            $.ajax({
                url:'_manager/GenderCourseRecordSign.jsp',
                type:'post',
                dataType:'text',
                data:param,
                success:function(rps){
                    $("#txt_"+mtype+"_sign").val(rps.Trim());
                }
            })
        }

    </script>
</head>
<body>



<a id="go"><img src="../images/test.jpg" width="200" height="100"/></a>
<div id="go2" style="display: none;">
    <a style="float: right" href="javascript:;" onclick="$(this).parent().hide();"><h1 style="color: #ffffff">×</h1></a>
    <div style="width: 100%;height: 100%">
        <img id="img" src="../images/test.jpg"  />
    </div>
</div>



</body>
</html>


<script type="text/javascript">
    function resizeimg(ImgD, iwidth, iheight) {

        var image = new Image();
        image.src = ImgD.src;
        setTimeout(function () {
            if (image.width > 0 && image.height > 0) {
                if (image.width / image.height >= iwidth / iheight) {
                    if (image.width > iwidth) {
                        ImgD.width = iwidth;
                        ImgD.height = (image.height * iwidth) / image.width;
                    } else {
                        ImgD.width = image.width;
                        ImgD.height = image.height;
                    }

                } else {
                    if (image.height > iheight) {
                        ImgD.height = iheight;
                        ImgD.width = (image.width * iheight) / image.height;
                    } else {
                        ImgD.width = image.width;
                        ImgD.height = image.height;
                    }

                }
            }
        }, 300);
    }



    $(document).ready(function () {

        $("#go").click(function () {
            /*属性*/
            $("#go2").css({"display": "", "position": "absolute", "text-align": "center", "top": "0px", "left": "0px", "right": "0px", "bottom": "0px", "background": "black", "visibility": "visible", "filter": "Alpha(opacity=100)"
            });
            $("#img").css({
                height: function () {
                    return $(document).height();
                },
                width:"100%"

            });
            /*高为屏幕的高*/
            $("#go2").css({
                height: function () {
                    return $(document).height();
                },
                width:"100%"

            });
        });
    });
</script>