/**
 * Created by zhengzhou on 14-9-10.
 * 大图片，控件的显示
 * 例子:
 *   ImageShow({
                        image:$("#content li img"),
                        fadeId:'dv_imageShow1-1',
                        sw:90,
                        sh:70
                    });
 * 注意:img 下必须带data-src 的属性
 */
function EttImageShow(settings){

    ImageShow_init();
    /**
     * 初始化
     * @constructor
     */
    function ImageShow_init(){
        //验证是否存在DIV层
        var fadeId=settings.fadeId;
        var fadeDivLen=$("div[id='fadeId']").length;
        if(fadeDivLen<1){
            ImageShow_initPage(fadeId);
        }
        //图片集合
        if(typeof(settings.image)=="undefined"||settings.image.length<1){
            alert("错误，图片集合不存在!");return;
        }
        ImageShow_InitImage(settings.image);
    }
    /**
     * 加载图片信息
     * @constructor
     */

    function ImageShow_InitImage(){
        var sets=settings;
        $.each(settings.image,function(idx,itm){
            var bindData=$(itm).attr("data-src");
            $(itm).css("cursor","pointer");
            //如果小图加载失败，则进行切割加载
//            $(itm).bind("error",function(){
//                if(bindData.lastIndexOf("/")!=-1){
//                    if(bindData.substring(bindData.lastIndexOf("/")).indexOf(".")!=-1){
//                        $(this).attr("src","imapi1_1?m=makeImImg&w="+sets.sw+"&h="+sets.sh+"&p="+bindData);
//                    }
//                }
//            });
            //添加点击事件
            $(itm).click(function(){
                /*属性*/
                $("#"+sets.fadeId).css({"background": "black"});
                $("#dimg").hide();

//                var myOffset = new Object();
//                myOffset.left = 0;
//                myOffset.top = 0;
//                $('#img_'+sets.fadeId).offset(myOffset);
//                $('#img_'+sets.fadeId).hide();
                var whObj=findDimensions();
                var h=whObj.h;
                var w=whObj.w;

//                if(isIE()){
//                    resizeimg($('#img_'+sets.fadeId).get(0),
//                        w-160,
//                        h-160,
//                        $(this).attr("data-src")
//                    );
//                }
                var srcStr=$(this).attr("data-src");
                if(srcStr.indexOf(".")!=-1){
                    srcStr=srcStr.substring(0,srcStr.lastIndexOf("."))+srcStr.substring(srcStr.lastIndexOf("."))
                }
                $( "#"+sets.fadeId).dialog("open");
                $('#img_'+sets.fadeId).hide();

                resizeimg($('#img_'+sets.fadeId).get(0),
                    w,
                    h,
                    srcStr
                );
            });
        });
    }

    function findDimensions() //函数：获取尺寸
    {
        var winWidth= 0,winHeight=0;
        //获取窗口宽度
        if (window.innerWidth)
            winWidth = window.innerWidth;
        else if ((document.body) && (document.body.clientWidth))
            winWidth = document.body.clientWidth;
        //获取窗口高度
        if (window.innerHeight)
            winHeight = window.innerHeight;
        else if ((document.body) && (document.body.clientHeight))
            winHeight = document.body.clientHeight;
        //通过深入Document内部对body进行检测，获取窗口大小
        if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth)
        {
            winHeight = document.documentElement.clientHeight;
            winWidth = document.documentElement.clientWidth;
        }
        //结果输出至两个文本框
        return {w:winWidth,h:winHeight};
    }

    /**
     * 加载页面大层
     */
    function ImageShow_initPage(fadeId){
        var h='<div id="'+fadeId+'">';
        h+='<table width="100%" height="100%" align="center">';
        h+='<tr><td align="middle"  width="100%">';
        h+='<center><img id="img_'+fadeId+'" src="" onclick="closedailog()" /></center>';
        h+='</td></tr></table>';
        h+=' </div>';
        $("body").append(h);
        //定义dialog
        var whObj=findDimensions();
        $("#"+fadeId).dialog({
            autoOpen: false,
            width:whObj.w,
            // height:window.screen.availHeight,
            // height:$(document).height(),
            height:whObj.h,
            close:function(){
                $("#"+fadeId).hide();
            },
            overlay: { opacity: 1, background: "black" },
            modal:true
        });
        $(".ui-dialog-titlebar a").html("&nbsp;");
        //定义事件
        $("#img_"+fadeId).bind("click",function(){
            $( "#"+fadeId).dialog("close");
        });

    }

    /**
     * 等比例缩放
     * @param ImgD
     * @param iwidth
     * @param iheight
     * @param imPicSrc
     */
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
                ImgD.style.display='block';
            }
        },200);
    }



    /**
     * 工具方法js获取项目根路径，如： http://localhost:8083/uimcardprj
     * @returns {string}
     */
    function getRootPath(){
        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var curWwwPath=window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName=window.document.location.pathname;
        var pos=curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht=curWwwPath.substring(0,pos);
        //获取带"/"的项目名，如：/uimcardprj
        var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
        return(localhostPaht+projectName);
    }

    /**
     * 工具方法，是否是IE浏览器
     * @returns {boolean}
     */
    function isIE() { //ie?
        if (!!window.ActiveXObject || "ActiveXObject" in window)
            return true;
        else
            return false;
    }

}


