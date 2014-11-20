/**
 * 退出
 *
 * @return
 */
function loginDestory(baseIp){
    if(confirm("您确认退出吗?")){
        location.href=baseIp+"user?m=userquit";
    }
}
$(function(){
    document.onmousemove = mouseMove;
    /*if(typeof window.showModalDialog=='undefined'){
        window.showModalDialog= function(sUrl,vArguments,sFeatures) {
            window.open(sUrl,"newwindow",vArguments);
        };
    }*/
});


var qhs = {};
qhs.GetLength = function(str) {
    ///<summary>获得字符串实际长度，中文2，英文1</summary>
    ///<param name="str">要获得长度的字符串</param>
    var realLength = 0, len = str.length, charCode = -1;
    for (var i = 0; i < len; i++) {
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) realLength += 1;
        else realLength += 2;
    }
    return realLength;
};

var isIE = (document.all) ? true : false;

var AZarray=["A","B","C","D","E","F","G","H",
    "I","G","K","L","M","N","O","P",
    "Q","R","S","T","U","V","W","X","Y","Z"];



var tarray=new Array();
/**
 * 视频文件处理(包括两种状态: 1.未转换完成时，2.转换完成)
 * @param resid	资源标识
 * @param fname	资源正确名称
 * @param md5fname	资源MD5加密后的名称
 * @param idxstate	第几个视频资源
 * @return
 */
function videoConvertProgress(resid,fname,md5fname,idxstate,path,baseIpPort,imgpath,width,height,isshowBar,playEndMethod){
    tarray[idxstate]=null;
    if(typeof(resid)!="undefined"&&resid!=null&&
        typeof(fname)!="undefined"&&fname!=null){
        var urlpath="http://202.99.47.104/fileoperate/get_video_state.jsp?jsoncallback=?";
        if(typeof(baseIpPort)!="undefined"&&baseIpPort.Trim().length>0){
            urlpath=baseIpPort+"get_video_state.jsp?jsoncallback=?";
        }
        var lastname="";
        if(typeof(fname)!='undefined'&&fname.Trim().length>0&&fname.indexOf(".")!=-1)
            lastname=fname.substring(fname.lastIndexOf("."));
        //var urlpath="http://localhost:1990/fileoperate/getvideostate.jsp?res_id="+resid+"&filename="+encodeURI(fname);
//        $.getJSON(urlpath,{res_id:resid,file_name_md5:md5fname+lastname,path:path},function(data){
//            if(data!=null&&data!=""){
//                if(data.type=="success"){
//                    var progress=data.progress;
//                    if(parseFloat(progress)>=95){
//                        if(typeof(tarray[idxstate])!="undefined"&&tarray[idxstate]!=null)
//                            clearTimeout(tarray[idxstate]);
//                        progress="完成";
//                        //$("#img_video").unbind("click");
//                        //$("#img_video").bind("click",function(){
//                        $("#progress_"+idxstate).html("转换进度:"+progress);
        var tmp="uploadfile/";
        if(resid>0)
            tmp="clouduploadfile/";
        loadSWFPlayer(baseIpPort+tmp+path+"/"+fname,'div_show'+idxstate,imgpath,resid,width,height,isshowBar,playEndMethod);
        return;
//                        //});
//
//                    }else{
//                        progress+="%";
//                        $("#img_video").unbind("click");
//                        $("#img_video").bind("click",function(){
//                            alert('该视频文件正在进行转换!无法进行预览!\n当前进度:'+progress+"\n\n提示：2分钟更新一次!");
//                        });
//                    }
//                    $("#progress_"+idxstate).html("转换进度:"+progress);
//                    tarray[idxstate]=setTimeout("videoConvertProgress('"+resid+"','"+fname+"','"+md5fname+"',"+idxstate+",'"+path+"','"+baseIpPort+"','"+imgpath+"')",1000*60*2);
//                    return;
//                }
//            }
//            $("#progress_"+idxstate).html("转换进度:排列中!");
//            $("#img_video").attr({"src":"images/video_gszh.jpg","width":"650px","height":"550px"});
//            tarray[idxstate]=setTimeout("videoConvertProgress('"+resid+"','"+fname+"','"+md5fname+"',"+idxstate+",'"+path+"','"+baseIpPort+"','"+imgpath+"')",1000*60*2);
//            $("#img_video").unbind("click");
//            $("#img_video").bind("click",function(){
//                alert('该视频文件正在排列准备进行转换!无法进行预览!\n\n提示：2分钟更新一次!');
//            });
//            //tarray[idxstate]=setTimeout("videoConvertProgress('"+resid+"','"+fname+"','"+md5fname+"',"+idxstate+",'"+path+"','"+baseIpPort+"','"+imgpath+"')",1000*60*2);
//
//        });
    }
}
var jwplayerSetup={};
/**
 * 加载SWF视频播放器
 * @param resmd5id    RES_ID标识的MD5加密
 * @param filemd5name	FILE_NAME 的MD5加密[加后缀名]例：10a588b503909378cde366ef95b41d25.rmvb
 * @param playeraddressid 播放器的位置
 * @return
 */
function loadSWFPlayer(filepath,playeraddressid,imagepath,resid,width,height,isshowBar,playEndMethod){
    if(typeof(width)=="undefined"&&typeof(height)=="undefined"){
        width=435;
        height:270;
    }

    //清空
    //由于SWFplayer生成的时候，是替换DIV层。如果想重新生成一个,需重新生成DIV并且删除SWFPlayer
    jwplayerSetup={
        'id': 'player1',
        'width': width,
        'height': height,
        //'playlistfile': "/ett20/studyrvice_class/free/toxml.jsp?resourceID=<%=resourceID%>&frame=1",
        'file':  filepath,
        'primary': 'flash',
        'controlbar':'bottom',
        'controlbar.idlehide':'false',
        'modes': [
            {type: 'flash', src: 'js/common/videoPlayer/new/jwplayer.flash.swf', //
                config: {
                    provider: "http",
                    autostart:"false",
                    menu:"false"
                }
            }
            ,{type: 'html5'}
        ]
//        ,events:{
//            onReady:function(){
//                jwplayer().play();
//            }
//        }
    };

    if(typeof(isshowBar)!="undefined"&&!isshowBar){
        jwplayerSetup.controls=false;
    }


    if(typeof(imagepath)!="undefined"&&imagepath.Trim().length>0)
        jwplayerSetup.image=imagepath;
    var returnPobj= jwplayer(playeraddressid).setup(jwplayerSetup);
    if(typeof(playEndMethod)!="undefined"&&typeof(playEndMethod)=="function")
        jwplayer(playeraddressid).onPlaylistComplete(playEndMethod);
    if(typeof(isshowBar)!="undefined"&&!isshowBar){
        jwplayer(playeraddressid).onReady(function(){
            jwplayer(playeraddressid).play(); //.seek(0)
        });
    }

//     if(typeof(resid)!="undefined"){
//        var suffixName=filepath;
//        if(filepath.lastIndexOf(".mp4")!=-1){
//            suffixName=filepath.substring(filepath.lastIndexOf(".mp4",filepath.lastIndexOf("/")),filepath.lastIndexOf(".mp4"));
//        }else
//            suffixName=filepath.substring(filepath.lastIndexOf("."));
//        if(suffixName.indexOf(".")>-1){
//            jwplayer().addButton(
//                "images/down.png",
//                "下载文件",
//                function() {
//                    resourceDownLoadFile(resid+"",suffixName);
//                    try{sp_downnum.innerHTML=parseInt(sp_downnum.innerHTML)+1;}catch(e){}
//                },
//                "download"
//            );
//        }
//    }
    return returnPobj;
}

/**
 * 加载SWF视频播放器
 * @param resmd5id    RES_ID标识的MD5加密
 * @param filemd5name	FILE_NAME 的MD5加密[加后缀名]例：10a588b503909378cde366ef95b41d25.rmvb
 * @param playeraddressid 播放器的位置
 * @return
 */
function loadSWFPlayerLitterView(filepath,playeraddressid,imagepath,resid,width,height,isshowBar,playEndMethod){
    if(typeof(width)=="undefined"&&typeof(height)=="undefined"){
        width=435;
        height:270;
    }

    //清空
    //由于SWFplayer生成的时候，是替换DIV层。如果想重新生成一个,需重新生成DIV并且删除SWFPlayer
    jwplayerSetup={
        'id': 'player1',
        'width': width,
        'height': height,
        //'playlistfile': "/ett20/studyrvice_class/free/toxml.jsp?resourceID=<%=resourceID%>&frame=1",
        'file':  filepath,
        'primary': 'flash',
        'controlbar':'bottom',
        'controlbar.idlehide':'false',
        'modes': [
            {type: 'flash', src: 'js/common/videoPlayer/new/jwplayer.flash.swf', //
                config: {
                    provider: "http",
                    autostart:"false",
                    menu:"false"
                }
            }
            ,{type: 'html5'}
        ]
//        ,events:{
//            onReady:function(){
//                jwplayer().play();
//            }
//        }
    };

    if(typeof(isshowBar)!="undefined"&&!isshowBar){
        jwplayerSetup.controls=false;
    }


    if(typeof(imagepath)!="undefined"&&imagepath.Trim().length>0)
        jwplayerSetup.image=imagepath;
    var returnPobj= jwplayer(playeraddressid).setup(jwplayerSetup);
    if(typeof(playEndMethod)!="undefined"&&typeof(playEndMethod)=="function")
        jwplayer(playeraddressid).onPlaylistComplete(playEndMethod);
    if(typeof(isshowBar)!="undefined"&&!isshowBar){
        jwplayer(playeraddressid).onReady(function(){
            jwplayer(playeraddressid).play(); //.seek(0)
        });
    }

//     if(typeof(resid)!="undefined"){
//        var suffixName=filepath;
//        if(filepath.lastIndexOf(".mp4")!=-1){
//            suffixName=filepath.substring(filepath.lastIndexOf(".mp4",filepath.lastIndexOf("/")),filepath.lastIndexOf(".mp4"));
//        }else
//            suffixName=filepath.substring(filepath.lastIndexOf("."));
//        if(suffixName.indexOf(".")>-1){
//            jwplayer().addButton(
//                "images/down.png",
//                "下载文件",
//                function() {
//                    resourceDownLoadFile(resid+"",suffixName);
//                    try{sp_downnum.innerHTML=parseInt(sp_downnum.innerHTML)+1;}catch(e){}
//                },
//                "download"
//            );
//        }
//    }
    return returnPobj;
}

function swfobjPlayer(path, playeraddressid,width,height, isshow,imagepath) {
    // 清空
    // 由于SWFplayer生成的时候，是替换DIV层。如果想重新生成一个,需重新生成DIV并且删除SWFPlayer
    if (typeof ($("object[name='player1']")) != "undefined")
        $("object[name='player1']").parent()
            .append('<div id="' + playeraddressid + '" style="width:560px;height:370px;"></div>');
    $("object[name='player1']").remove();


    if (typeof ($("object[name='div_show']")) != "undefined")
        $("object[name='div_show']").after(
            '<div id="'+playeraddressid+'" style="width:560px;height:500px;"></div>');
    $("object[name='div_show']").remove();


    var flashvars = "";
    var params = {
        menu: "true",
        scale: "noScale",
        allowFullscreen: "true",
        allowScriptAccess: "always",
        bgcolor: "#FFFFFF",
        wmode: "transparent"
    };
    var attributes = {
        id: "player1",
        name: "player1"
    };
    attributes.wmode="transparent";
    swfobject.embedSWF(path, playeraddressid, width, height, "9.0.0", "flexpaper/swfobject/expressInstall.swf", flashvars, params, attributes);
    if (isshow)
        showModel("swfplayer", false);
}

/**
 * 加载FLEXPAPER控件
 * @param swfpath 文件SWF的路径（绝对路径）
 * @param showdiv   显示在页面中的ID
 * @param width   宽度
 * @param height   高度
 */
function loadSwfReview(swfpath,showdiv,width,height){
    var swfVersionStr = "9.0.124";
    var xiSwfUrlStr = "${expressInstallSwf}";
    var flashvars = {
        SwfFile : swfpath+"?v1.4.0final",//flexpaper/swf/test_1352107155307_1365411058359.swf?v1.4.0final
        Scale : 0.8, //放大因子，是一个0以上的数(带小数 1 = 100%) 。
        ZoomTransition : "easeOut",//光学变焦过渡，默认值是easeOut，可取值: easenone, easeout, linear, easeoutquad
        ZoomTime : 0.5, //时间过渡让变焦达到新的放大因子，值为0或更大的数。
        ZoomInterval : 0.1,//区间的滑动缩放。放大因子缺省值是0.1。如同在工具栏上使用滑动条按钮的效果。
        FitPageOnLoad : true, //(布尔) 适合初始页大小(依高度而定)的装载页。如同在工具栏上使用fit-page按钮的效果。
        FitWidthOnLoad : true, // (布尔)适合初始页宽度大小的装载页。如同在工具栏上使用fit-width按钮的效果。
        PrintEnabled : true,   //是否支持打印
        FullScreenAsMaxWindow :false,  //是否支持全屏
        ProgressiveLoading : false,  //是否支持延迟加载
        SearchMatchAll : true,//设置为true时，单击搜索所有符合条件的地方高亮显示
        PrintToolsVisible : false,
        ViewModeToolsVisible : true,//(布尔)显示或隐藏视图模式与工具栏
        ZoomToolsVisible : true,//(布尔) 从工具栏显示或隐藏变焦工具
        FullScreenVisible : true,//(布尔)以最大化方式打开一个新浏览器窗口。
        NavToolsVisible : true,//(布尔)显示或隐藏导航工具
        CursorToolsVisible : false,//(布尔) 显示或隐藏光标工具
        SearchToolsVisible : true,
        localeChain: "en_US" //语言
    };
    var params = {

    }
    params.quality = "high";
    params.bgcolor = "#ffffff";
    params.allowscriptaccess = "sameDomain";
    params.allowfullscreen = "true";
    var attributes = {};
    attributes.id = "FlexPaperViewer";
    attributes.name = "FlexPaperViewer";
    attributes.wmode="transparent";
    swfobject.embedSWF(
        "flexpaper/swf/FlexPaperViewer.swf", showdiv,
        width, height,
        swfVersionStr, xiSwfUrlStr,
        flashvars, params, attributes);
    swfobject.createCSS("#"+showdiv+"", "z-index:500");
}

/**
 * 播放或停止音乐
 *
 * @param idx
 * @param filereadlyname
 * @return
 */
function operateSound(idx, md5id, filereadlyname, type,tmppathipport) {
    // 判断操作类型
    if (typeof (type) == "undefined" || type == null || $.trim(type).length < 1)
        return;
    if ($.trim(type) == "stop") {

        // $("#b_sound").stop();
        // 修改提示文字
        $("#play_operate_" + idx).html(
            '<a href="javascript:;" onclick="operateSound(' + idx + ',\''
                + md5id + '\',\'' + filereadlyname
                + '\',\'play\',\''+tmppathipport+'\')">试听</a>');
        $("#b_sound").remove();
        return;
    }
    var resourceHead=tmppathipport;
    if(typeof(resourceHead)=="undefined")
        resourceHead="http://202.99.47.104:81/uploadfile/";
    else
        resourceHead+="uploadfile";

}

function playSound(type,mp3Url,width,height,address,autoplay){
    // 路径
    if ($.trim(type) == "play") {
        // 播放文件地址
        var mp3url =mp3Url;
       // $("#b_sound").remove();
        // 修改提示文字
//        $("span[id*='play_operate_']").each(
//            function(ix, itm) {
//                itm.innerHTML = '<a href="javascript:;" onclick="operateSound('
//                    + ix
//                    + ',\''
//                    + md5id
//                    + '\',\''
//                    + $(this).attr("filemd5name")
//                    + '\',\'play\')">播放</a>';
//            });
        if(typeof(autoplay)=="undefined"||autoplay==true)
            autoplay='yes';
        else
            autoplay='no';
        // 播放器。
        var mp3Play = '<object  id="b_sound" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" ' + 'codebase="js/common/videoPlayer/new/swflash.cab#version=7,0,19,0"' + ' width="'+width+'" height="'+height+'">';
        mp3Play += '<param name="movie" '
            + 'value="js/common/videoPlayer/new/musicPlay.swf?'
            + 'soundFile='
            + mp3url
            + '&bg=0xCDDFF3&'
            + 'leftbg=0x357DCE&lefticon=0xF2F2F2&rightbg=0x357DCE&rightbghover=0x4499EE&'
            + 'righticon=0xF2F2F2&righticonhover=0xFFFFFF&text=0x357DCE&slider=0x357DCE&'
            + 'track=0xFFFFFF&border=0xFFFFFF&loader=0x8EC2F4&autostart='+autoplay+'&loop=no&" />';
        mp3Play += ' <param name="quality" value="high" />'
        mp3Play += '<param value="transparent" name="wmode" />';
        mp3Play+='<param value="CONTROLS" name="playbutton"/>';
        mp3Play += '<embed controls="playbutton" src="js/common/videoPlayer/new/musicPlay.swf?soundFile='
            + mp3url
            + '&bg=0xCDDFF3&leftbg=0x357DCE&lefticon=0xF2F2F2&rightbg='
            + '0x357DCE&rightbghover=0x4499EE&righticon=0xF2F2F2&righticonhover='
            + '0xFFFFFF&text=0x357DCE&slider=0x357DCE&track=0xFFFFFF&border='
            + '0xFFFFFF&loader=0x8EC2F4&autostart='+autoplay+'&loop=no" width="'+width+'" '
            + 'height="'+height+'" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" '
            + ' type="application/x-shockwave-flash">' +
            '</embed>';
        mp3Play += '</object>';
        // var mp3Play='<embed src="'+mp3url+'" style="FILTER: invert()"
        // type=audio/mpeg loop="1" autostart="true" volume="100" id="b_sound"
        // name="b_sound" hidden="true"/>';
        $("#"+address).append(mp3Play);
        // 修改提示文件
        /* $("#play_operate_" + idx).html(
         '<a href="javascript:;" onclick="operateSound(' + idx + ',\''
         + md5id + '\',\'' + filereadlyname
         + '\',\'stop\',\''+tmppathipport+'\')">停止</a>');
         */
    }else{
        $("#"+address).empty();
    }
}


function showMp3Resource(divid, pidx, filemd5name, md5Id) {
    // 删除播放器
    if (typeof ($("object[name='player1']")) != "undefined")
        $("object[name='player1']")
            .parent()
            .append(
                '<div id="div_show" style="width:560px;height:370px;"></div>');
    $("object[name='player1']").remove();

    var htm = '';
    htm += '<img src="images/mp3.jpg" width="75" height="52" />';
    htm += '<span style="color:blue;font-size:10px" id="play_operate_' + pidx
        + '" filemd5name="' + filemd5name + '">';
    htm += '<a href="javascript:;" onclick="operateSound(' + pidx + ',\''
        + md5Id + '\',\'' + filemd5name + '\',\'play\')">试听</a>';
    htm += '</span>';
    $("#" + divid).html(htm);
}


function loadSwfReview(swfpath,showdiv,width,height){
    var swfVersionStr = "9.0.124";
    var xiSwfUrlStr = "${expressInstallSwf}";
    var flashvars = {
        SwfFile : swfpath+"?v1.4.0final",//flexpaper/swf/test_1352107155307_1365411058359.swf?v1.4.0final
        Scale : 0.8, //放大因子，是一个0以上的数(带小数 1 = 100%) 。
        ZoomTransition : "easeOut",//光学变焦过渡，默认值是easeOut，可取值: easenone, easeout, linear, easeoutquad
        ZoomTime : 0.5, //时间过渡让变焦达到新的放大因子，值为0或更大的数。
        ZoomInterval : 0.1,//区间的滑动缩放。放大因子缺省值是0.1。如同在工具栏上使用滑动条按钮的效果。
        FitPageOnLoad : true, //(布尔) 适合初始页大小(依高度而定)的装载页。如同在工具栏上使用fit-page按钮的效果。
        FitWidthOnLoad : true, // (布尔)适合初始页宽度大小的装载页。如同在工具栏上使用fit-width按钮的效果。
        PrintEnabled : true,   //是否支持打印
        FullScreenAsMaxWindow :false,  //是否支持全屏
        ProgressiveLoading : false,  //是否支持延迟加载
        SearchMatchAll : true,//设置为true时，单击搜索所有符合条件的地方高亮显示
        PrintToolsVisible : false,
        ViewModeToolsVisible : true,//(布尔)显示或隐藏视图模式与工具栏
        ZoomToolsVisible : true,//(布尔) 从工具栏显示或隐藏变焦工具
        FullScreenVisible : true,//(布尔)以最大化方式打开一个新浏览器窗口。
        NavToolsVisible : true,//(布尔)显示或隐藏导航工具
        CursorToolsVisible : false,//(布尔) 显示或隐藏光标工具
        SearchToolsVisible : true,
        localeChain: "en_US" //语言
    };
    var params = {

    }
    params.quality = "high";
    params.bgcolor = "#ffffff";
    params.allowscriptaccess = "sameDomain";
    params.allowfullscreen = "true";
    var attributes = {};
    attributes.id = "FlexPaperViewer";
    attributes.name = "FlexPaperViewer";
    attributes.wmode="transparent";
    swfobject.embedSWF(
        "flexpaper/swf/FlexPaperViewer.swf", showdiv,
        width, height,
        swfVersionStr, xiSwfUrlStr,
        flashvars, params, attributes);
    //swfobject.createCSS("#"+showdiv+"", "display:block;text-align:left;");
}



/**
 *	下载
 *@param resid:资源ID
 *@param 下载的文件后缀名
 */
function resourceDownLoadFile(resid,fname,isval){
    if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1
        ||typeof(fname)=="undefined"||fname==null||fname.Trim().length<1){
        alert("异常错误，原因：参数不齐，无法进行下载!");return;
    }
    //进行下载
    $.ajax({
        url: 'resdown?m=doadd',
        type:'post',
        dataType:'json',
        cache: false,
        data:{resid:resid,filename:fname,isval:isval},
        error:function(){
            alert('程序异常!');
        },success:function(rmsg){
            if(rmsg.type=='error'){
                alert(rmsg.msg);
            }else{
                var pathurl=rmsg.objList[0];

                // window.open(pathurl);
                location.href=pathurl;
            }
        }
    });
}



// 鼠标当前的坐标
var mousePostion;
function mouseMove(ev) {
    ev = ev || window.event;
    var mousePos = mouseCoords(ev);
    mousePostion=mousePos;
}
function mouseCoords(ev) {
    if (ev.pageX || ev.pageY) {
        return {
            x : ev.pageX,
            y : ev.pageY
        };
    }

    return {
        x : ev.clientX + document.body.scrollLeft - document.body.clientLeft,
        y : ev.clientY + document.body.scrollTop - document.body.clientTop
    };
}
/**
 * 暂停几秒,执行某个方法 method:方法函数及参数 如: method('aaa') String类型注意引号 min:暂停秒数
 */
var tmin=0,tmitem=null;
function Pause(method,min) {
    tmin++;
    if(typeof(min)=="undefined")
        return;

    if (tmin >= min) {
        if(method!=null&&method!="")
            eval(method);   // 将字符串转换成方法，并执行
        clearTimeout(tmitem);
        tmitem = null;
        tmin = 0;
        return;
    }
    tmitem=setTimeout('Pause(' + min + ')', 1000);
}
/**
 * 正则表达式判断
 *
 * @param s
 *            要验证的字符串
 * @param regs
 *            正则
 * @return true false
 */
function validateByRegx(s,regs){
    return regs.exec(s);
}

function getKeys(obj){

    var keys = [];
    if(obj==null||typeof(obj)=="undefined"||typeof(obj)!="object")
        return keys;
    for (var property in obj){
        if(property!="getKeys"&&property!="getValues"){
            keys.push(property);     // 将每个属性压入到一个数组中
        }
    }
    return keys;
}

function  getValues(obj){
    var values = [];
    var keys = [];
    if(obj==null||typeof(obj)=="undefined"||typeof(obj)!="object")
        return keys;
    for (var property in obj) {
        if(property!="getKeys"&&property!="getValues"){
            values.push(obj[property]); // 将每个属性的值压入到一个数组中
        }
    }
    return values;
}
/**
 * 跳转
 *
 * @param {string}
 *            url 跳转链接
 * @param {Object}
 *            params 参数
 * @param {Object}
 *            isblank 是否打开新窗口
 * @return {TypeName} null
 */
function toPostURL(url,params,isblank,windowName,type){
    if(typeof(url)=="undefined"||url==null||url.Trim()=="")
        return ;

    if(typeof(document.targetToAddFrm)!="undefined")
        $("#targetToAddFrm").remove();

    $("body").append('<form name="targetToAddFrm" id="targetToAddFrm" style="display: none"></form>');

    if(typeof(params)!="undefined"&&typeof(params)=="object"&&params!=null)	{
        var ary=getKeys(params);
        var valry=getValues(params);
        if(ary.length>0&&valry.length>0){
            for(var z=0;z<ary.length;z++){
                $("#targetToAddFrm").append("<textarea name='"+ary[z]+"' id='"+ary[z]+"'/>");
                eval("document.targetToAddFrm."+ary[z]).value=valry[z];
            }
        }
    }
    if(typeof(type)=="undefiend"||type==null)
    type="post";
    $("#targetToAddFrm").attr("action",url);
    $("#targetToAddFrm").attr("method",type);
    if(typeof(isblank)=="undefined"||isblank==true){

        if(typeof(windowName)!="undefined"&&windowName!=""&&windowName!=null){
            $("#targetToAddFrm").attr("target","_blank");
        }else{
            var datawindow=new Date().getTime();
            $("#targetToAddFrm").attr("target","window_"+datawindow);
           // $("#targetToAddFrm").submit();
        }

    }else{
        if(typeof(windowName)!="undefined"&&windowName!=""&&windowName!=null){
            $("#targetToAddFrm").attr("target",windowName);
        }else
            $("#targetToAddFrm").removeAttr("target");

    }
    $("#targetToAddFrm").submit();
}


// +---------------------------------------------------
// | 比较日期差 dtEnd 格式为日期型或者 有效日期格式字符串
// +---------------------------------------------------
Date.prototype.DateDiff = function(strInterval, dtEnd) {
    var dtStart = this;
    if (typeof dtEnd == 'string' )// 如果是字符串转换为日期型
    {
        dtEnd = StringToDate(dtEnd);
    }
    switch (strInterval){
        case 's' :return parseInt((dtEnd - dtStart) / 1000);
        case 'n' :return parseInt((dtEnd - dtStart) / 60000);
        case 'h' :return parseInt((dtEnd - dtStart) / 3600000);
        case 'd' :return parseInt((dtEnd - dtStart) / 86400000);
        case 'w' :return parseInt((dtEnd - dtStart) / (86400000 * 7));
        case 'm' :return (dtEnd.getMonth()+1)+((dtEnd.getFullYear()-dtStart.getFullYear())*12) - (dtStart.getMonth()+1);
        case 'y' :return dtEnd.getFullYear() - dtStart.getFullYear();
    }
}
// +---------------------------------------------------
// | 字符串转成日期类型
// | 格式 MM/dd/YYYY MM-dd-YYYY YYYY/MM/dd YYYY-MM-dd
// +---------------------------------------------------
function StringToDate(DateStr)
{

    var converted = Date.parse(DateStr);
    var myDate = new Date(converted);
    if (isNaN(myDate))
    {
        // var delimCahar = DateStr.indexOf('/')!=-1?'/':'-';

        var arys= DateStr.split('-');
        myDate = new Date(arys[0],--arys[1],arys[2]);
    }
    return myDate;
}




/**
 * 验证日期是否合法
 *
 * @param fdata
 * @param edate
 * @return
 */
function validateDate(fdata,edate){

    var timestep=fdata.split('-');
    var endstep=edate.split('-');
    var fd=new Date(timestep[0],timestep[1],timestep[2]);
    var da=new Date(endstep[0],endstep[1],endstep[2]);
// if(edtime!=undefined&&edtime!=null&&edtime.length>0)
// da.setHours(edtime[0],edtime[1], edtime[2],59);
    if(fd.getTime()>da.getTime()){
        return false;
    }
    return true;

}

/**
 * 验证日期是否合法
 *
 * @param fdata
 * @param edate
 * @return
 */
function validateTwoDate(fdata,edate){

    // 获取fdata的年月
    var fdatestep=fdata.split('-');
    // 获取日 和 具体时间
    var ftimeday=fdatestep[2].split(' ');
    var fday=ftimeday[0];  // 获取日
    var ftime=ftimeday[1].split(":");  // 获取时间

    var endstep=edate.split('-');
    var etimeday=endstep[2].split(' ');
    var eday=etimeday[0]; // 获取日
    var etime=etimeday[1].split(":");
    if(fdatestep[1].length>1){
        if(fdatestep[1].substring(0,1)=="0"){
            fdatestep[1]=fdatestep[1].substring(1);
        }
    }

    if(endstep[1].length>1){
        if(endstep[1].substring(0,1)=="0"){
            endstep[1]=endstep[1].substring(1);
        }
    }
    var fdate=new Date(fdatestep[0],parseInt(fdatestep[1])+1,fday,ftime[0],ftime[1],ftime[2]);
    var edate=new Date(endstep[0],parseInt(endstep[1])+1,eday,etime[0],etime[1],etime[2]);

    if(fdate.getTime()>=edate.getTime()){
        return false;
    }
    return true;
}
/**
 * 将字符串转换成Date(YYYY-MM-DD hh24:mi:ss)
 *
 * @param dateStr
 * @return
 */
function StringToFullDate(dateStr){
    if(typeof(dateStr)=="undefined"||dateStr==null||!isNaN(dateStr)){
        return undefined;
    }
    var dateArray=[];
    var dateAndHour=dateStr.split(" ");

    for(var i=0;i<dateAndHour.length;i++){
        if(dateAndHour[i].Trim().length>0){
            var dateMonth=dateAndHour[i].Trim().split("-");
            if(dateMonth.length>1)
                for(j=0;j<dateMonth.length;j++)
                    dateArray[dateArray.length]=dateMonth[j];
            var dateHours=dateAndHour[i].Trim().split(":");
            if(dateHours.length>1)
                for(var j=0;j<dateHours.length;j++)
                    dateArray[dateArray.length]=dateHours[j];
        }
    }
    if(dateArray.length>0){
        if(dateArray.length<=3){
            return StringToDate(dateStr);
        }else{
            return new Date(dateArray[0],parseInt(dateArray[1])-1,dateArray[2],dateArray[3],dateArray[4],dateArray[5]);
        }
    }else
        return undefined;
}
/**
 * 数组排序(必须是数字类型)
 *
 * @return
 */
Array.prototype.numberSort=function(){
    if(this!=null){
        var len = this.length,empty;
        for(var k=0; k<len-1; k++) {
            for(var i=0; i<len-k-1; i++) {
                if(parseFloat(this[i])>parseFloat(this[i+1])) {
                    empty = this[i+1];
                    this[i+1] = this[i];
                    this[i] = empty
                }
            }
        }
    }
}

/**
 * 范围全选
 *
 * @param ckthis
 *            传本身，this
 * @param ckStr
 *            传Id为? 选上
 * @return
 */
function checkCKboxToList(ckthis,ckStr){
    $("input[type='checkbox']").filter("input[id='"+ckStr+"']").attr("checked",ckthis.checked);
}

/**
 * 与checkCKboxToList级联 子节点checked 发生改变的时候执行
 *
 * @param ckthis
 *            本身 this
 * @param ckall
 *            父CheckBoxId
 * @return
 */
function childCheckChange(ckthis,ckall){
    var cklist=$("input[type='checkbox']").filter("input[id='"+ckthis.id+"']");
    var flag=true;
    for ( var n = 0; n < cklist.length; n++) {
        if(cklist[n].checked==false){
            flag=false;
            break;
        }else{
            continue;
        }
    }
    $("#"+ckall).attr("checked",flag);
}



/**
 * 模式窗口的背景
 *
 * @return
 */
function modleBack(optype){
    try{
        if(optype=="block")
        {
            $("#operateNow").html("正在操作....");
            $("select").css("display","none");
            // $("te").css("display","block");
        }else{
            $("#operateNow").html("正在加载....");
            $("select").css("display","");
        }
    }catch(e){}
    $("#loading").css({left:screen.width/2-50,top:screen.height/2-5});
    var scrHeight=parseInt(document.body.scrollHeight);
    var bodyHeight=parseInt(document.body.clientHeight);
    var va=bodyHeight+100;
    if(scrHeight>bodyHeight)
        va=scrHeight;
    $("#fades").css("height",va);
    $("#fades").css("width",parseInt(document.body.clientWidth));
    $("#loading").css("display",optype);
    $("#fades").css("display",optype);
}


/**
 * 所有文本框，不允许输入特殊字符。
 *
 * @return
 */
function clearSpecialCharacter(){
    $("input[type='password']").bind("keypress",inputkeypress);
    $("input[type='text']").bind("keypress",inputkeypress);
    $("input[type='text']").bind("blur",inputkeypress);
    $("textarea").bind("keypress",inputkeypress);
    $("textarea").bind("blur",inputkeypress);
    function inputkeypress(e){
        var returnFlag=true;
        var keyCode=e.charCode;
        switch(keyCode){
            case 37:	//%
            case 39:	//'
            case 44:    //<
            //case 46:	//>
            case 34:	//"
                returnFlag=false;
                break;
            default:
                returnFlag=true;
        }
        return returnFlag;
    }

// $("input[type='text']").bind("keypress",function(){
// var len=this.value.length;
// for(var i=0;i<len;i++){
// this.value=this.value.replace(/[!\?\%\|\=\+\<\>\{\}\'\、\‘\’\；\＇\�\"\＼\/\\!\#\$\%\^\&\*\(\)\|]+$/,"");
// }
// });
// $("input[type='text']").bind("change",function(){
// var len=this.value.length;
// for(var i=0;i<len;i++){
// this.value=this.value.replace(/[!\?\%\|\=\+\<\>\{\}\'\、\‘\’\；\＇\�\"\＼\/\\!\#\$\%\^\&\*\(\)\|]+$/,"");
// }
// });
//    $("input[type='text']").bind("blur",function(){
//        var len=this.value.length;
//        for(var i=0;i<len;i++){
//            this.value=this.value.replace(/[,\|\%\<\>\'\＇\"\\$\^\&\|]+$/,"");
//        }
//    });
}
/**
 * 取出初数字以外的字符
 *
 * @param crl
 * @return
 */
function validateNum(crl){
    if(isNaN(crl.value.Trim()) 	){
        var len=crl.value.length;
        for(var i=0;i<len;i++){
            crl.value=crl.value.replace(/\D/,"");
        }
    }
}

$(function(){
    clearSpecialCharacter();
});

/**
 *
 * 屏蔽 Enter键
 */
document.onkeypress=function(e)
{
    var code;
    if  (!e)
    {
        var e=window.event;
    }
    if(e.keyCode)
    {
        code=e.keyCode;
    }
    else if(e.which)
    {
        code   =   e.which;
    }

    if(code==13)
    {
        return false;
    }
}
String.prototype.replaceAll=function(findchar,replacechar){
    return replaceAll(this, findchar, replacechar);
}


/**
 *
 * 替换全部
 *
 * @param strOrg
 * @param strFind
 * @param strReplace
 * @return
 */
function replaceAll(strOrg,strFind,strReplace){
    var index = 0;
    while(strOrg.indexOf(strFind,index) != -1){
        strOrg = strOrg.replace(strFind,strReplace);
        index = strOrg.indexOf(strFind,index);
    }
    return strOrg
}

function getScroll(){
    try{


        var black_overlalywidth=document.body.clientWidth;
        var black_overlalyheight=document.body.clientHeight;

        var black_scollLeft=document.body.scrollLeft;
        if(document.documentElement&&document.documentElement.scrollLeft){
            black_scollLeft=document.documentElement.scrollLeft;
        }
        if(document.documentElement&&document.documentElement.clientWidth){
            black_overlalywidth=document.documentElement.clientWidth;
        }
        if(document.documentElement&&document.documentElement.clientHeight){
            black_overlalyheight=document.documentElement.clientHeight;
        }
        var scroltop=getScrollTop();
        $(".black_overlay").css("height",black_overlalyheight+scroltop);// +black_overlayheight
        $(".black_overlay").css("width",black_overlalywidth+black_scollLeft);

        // $(".white_content").css("top",leaveTop-parseFloat($(".white_content").css("top"))/2);
        // alert(leaveTop-parseFloat($(".white_content").css("height"))/2);

        if(window.screen.availHeight<=(parseFloat($(".white_content").css("height"))+50)){
            return;
        }
        if(window.screen.availHeight>=768){
            scroltop+=40;
        }
        $(".white_content").css("top",scroltop);
        $(".white_content1").css("top",getScrollTop());
    }catch(e){}
}
window.onscroll=getScroll;


/**
 * 验证是否为空
 *
 * @param obj
 * @return
 */
function onchangeValidateValue(obj){
    var val=obj.value.Trim();
    if(val==""){
        obj.focus();
        $("#"+obj.id).parent().append("<span id='divError' style='color:red'>"+$("#"+obj.id).attr("msg")+"</span>");

    }else{
        $("#divError").remove();
    }
}
/*******************************************************************************
 * 取窗口滚动条高度
 ******************************************************************************/
function getScrollTop()
{
    var scrollTop=0;
    if(document.documentElement&&document.documentElement.scrollTop)
    {
        scrollTop=document.documentElement.scrollTop;
    }
    else if(document.body)
    {
        scrollTop=document.body.scrollTop;
    }
    return scrollTop;
}
/**
 * 得到字符串的字符数量(汉字算2个字符)
 * @param chars
 * @returns {number}
 */
function checkStrLength(chars)
{
    var sum = 0;
    for (var i=0; i<chars.length; i++)
    {
        var c = chars.charCodeAt(i);
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f))

        {
            sum++;
        }
        else
        {
            sum+=2;
        }
    }
    return sum;
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
    return {width:winWidth,height:winHeight};
}

var wcontentflag=false,wcontentX=null,wcontentY=null,offsetHeight=null,offsetWidth=null,isaddoffset=false;


function showModel(showId,isdrop,height){
// $("body").css("overflow","hidden");
    var h=0;
    if(typeof height!='undefined')
        h=height;
    $("#fade").css("height",findDimensions().height+parseFloat(getScrollTop()));
    $("#fade").css("width",findDimensions().width);
    try{
        $("#"+showId).css("z-index",1002);
        $("#"+showId).css("top",(20+parseInt(getScrollTop()))+h);
        $("#"+showId).css("left",(findDimensions().width/2-parseFloat($("#"+showId).css("width"))/2));
        $("#"+showId).css("position","absolute");
        // $(".white_content").css("top",15);
    }catch(e){}
    if(typeof(isdrop)=="undefined")
        isdrop=false;
    if(isdrop==true){
        // 允许拖拽事件
        $("#"+showId).bind("mousedown",function(e){
            if(!wcontentflag){
                wcontentflag=true;
                wcontentX=e.pageX;
                wcontentY=e.pageY;
                offsetWidth=e.pageX-parseFloat($(this).css("left"));
                offsetHeight=e.pageY-parseFloat($(this).css("top"));

                $(this).css("cursor","pointer");
            }
        });
        $("body").bind("mousemove",function(e){
            if(wcontentflag){
                var nowPageX=e.pageX;
                var nowPageY=e.pageY;

                var nowObjX=parseFloat($("#"+showId).css("left"));
                var nowObjY=parseFloat($("#"+showId).css("top"));

                if(typeof(nowObjX)=='undefined'||nowObjX==""||nowObjX==null||nowObjX==NaN){
                    alert('异常错误!');
                }
                if(typeof(nowObjY)=='undefined'||nowObjY==""||nowObjY==null||nowObjY==NaN){
                    alert('异常错误!');
                }

                nowObjX=parseFloat(nowObjX)+(nowPageX-nowObjX);
                nowObjY=parseFloat(nowObjY)+(nowPageY-nowObjY);

                nowObjX-=offsetWidth;
                nowObjY-=offsetHeight;

                $("#"+showId).css({left:nowObjX,top:nowObjY});
                $("#"+showId).css({left:nowObjX,top:nowObjY});
            }
        });

        $("body").bind("mouseup",function(e){
            if(wcontentflag){
                wcontentflag=false;
                isaddoffset=false;
                $("#"+showId).css("cursor","default");
            }
        });
    }
    showAndHidden(showId,'show');
    showAndHidden('fade','show');

}
function closeModel(showId){
    showAndHidden(showId,'hide');
    showAndHidden('fade','hide');
    try{
        if(yearSelect!=undefined&&yearSelect!="undefined"&&yearSelect==null){
            yearSelect=false;
        }
    }catch(e){}
    $("body").css("overflow","auto");
    $("select").show();
    $("select").show("fast");
}

/**
 * 提交
 *
 * @param id
 * @param idName
 * @param url
 * @return
 */
function onSub(id, idName, url) {
    document.forms[0].action = url + ".action";
    if(idName!="null")
        document.forms[0][idName].value = id;
    document.forms[0].submit();

}
/**
 * 显示 隐藏一个层
 *
 * @param divId
 * @param type
 * @return
 */
function showAndHidden(divId, type) {
    if (type == "show"){
        $("#" + divId).show("fast");
        // $("body").css("overflow","hidden");
    }else if (type == "hide"){
        // $("body").css("overflow","auto");
        $("#" + divId).hide("fast");

    }
    try{
        $("#divError").remove();
    }catch(e){}
}
/**
 * 判断复选框是否选中了。
 *
 * @param ckid
 * @return
 */
function ckChecked(ckid){
    var ck=$("input[name='"+ckid+"']");
    var flag=false;
    for ( var i = 0; i < ck.length; i++) {
        if(ck[i].type=="checkbox"&&ck[i].checked){
            flag=true;
            break;
        }
    }
    return flag;
}

/**
 * 全选效果
 *
 * @param all
 * @return
 */
function checkedAll(all) {
    $("input[type='checkbox']").attr("checked", all.checked);
}

/**
 * 给JavaScript脚本的String添加一个Trim方法
 *
 * @return
 */
String.prototype.Trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 清楚页面上所有控件的值
 *
 * @param frm
 * @return
 */
function clearForm(frm){
    var obj = frm || event.srcElement;
    var count = obj.elements.length;
    for(var i=0;i<count;i++){
        with(obj.elements[i]){
            if(type=="text"||type=="password"||type=="textarea"){
                value="";
            }else if(type=="checkbox"||type=="radio")
                checked=false;

            continue;
        }
    }
}

var xmlHttpRequest;
var method;
var resVal;
var msg = new Array();
msg["saveSuccess"] = "\u4fdd\u5b58\u6210\u529f";
msg["saveFail"] = "\u3010\u5931\u8d25\u3011\u4fdd\u5b58\u5931\u8d25";
function isEdit(keyArray, valueArray) {
    for (var i = 0; i < keyArray.length; i++) {
        if (j(keyArray[i]).value != valueArray[i]) {
            return true;
        }
    }
    return false;
}
function getParameters(i) {
    var str = "";
    if (i == null) {
        i = 0;
    }
    var node = j(null, i);
    var child = node.getElementsByTagName("input");
    for (var i = 0; i < child.length; i++) {
        if (child[i].type == "checkbox") {
            if (child[i].checked) {
                str += "&" + child[i].name + "=" + child[i].value;
            }
        } else {
            str += "&" + child[i].name + "=" + child[i].value;
        }
    }
    child = node.getElementsByTagName("textarea");
    for (var i = 0; i < child.length; i++) {
        str += "&" + child[i].name + "=" + child[i].value;
    }
    child = node.getElementsByTagName("select");
    for (var i = 0; i < child.length; i++) {
        str += "&" + child[i].name + "=" + child[i].value;
    }
    return str;
}
function getJsonParameters(i) {
    var str = "";
    if (i == null) {
        i = 0;
    }
    var node = j(null, i);
    var child = node.getElementsByTagName("input");
    for (var i = 0; i < child.length; i++) {
        if (child[i].type == "checkbox") {
            if (child[i].checked) {
                str += "\",\"" + child[i].name + "\":\"" + child[i].value;
            }
        } else {
            str += "\",\"" + child[i].name + "\":\"" + child[i].value;
        }
    }
    child = node.getElementsByTagName("textarea");
    for (var i = 0; i < child.length; i++) {
        str += "\",\"" + child[i].name + "\":\"" + child[i].value;
    }
    child = node.getElementsByTagName("select");
    for (var i = 0; i < child.length; i++) {
        str += "\",\"" + child[i].name + "\":\"" + child[i].value;
    }
    str=str.substring(str.indexOf(",")+1)+"\"";

    return str;
}

function getData(i) {

    var checkedStr="\"";
    $("input:checked").each(function(){
        checkedStr+=this.value+"\",\"";
    });
    checkedStr=checkedStr.substring(0,checkedStr.length-2);

    var str="\"";
    $("input:not(:checkbox)").each(function(){
        str += this.name + "\":\"" +this.value+"\",\"";
    });

    str=str.substring(0,str.length-2);
    str=str+",\"rights\""+checkedStr;
    return str;
}


/**
 *
 */
function doSubmit(op) {
    var oForm = document.forms[0];
    if (Validator.Validate(oForm, 3)) {
        oForm.elements["o"].value = op;
        oForm.submit();
    }
}

/**
 *
 */
function doAjaxSubmit(URL, imethod, useValidate) {
    if (useValidate) {
// var oForm = document.forms[0];
// if (Validator.Validate(oForm, 3)) {
        method = imethod;
        xmlHttpRequest = createXmlHttpRequest();
        xmlHttpRequest.onreadystatechange = resMethod;
        xmlHttpRequest
            .open("post", URL + "&t=" + new Date().getTime(), true);
        xmlHttpRequest.send(null);
        // }
    } else {
        method = imethod;
        xmlHttpRequest = createXmlHttpRequest();
        xmlHttpRequest.onreadystatechange = resMethod;
        xmlHttpRequest.open("post", URL + "&t=" + new Date().getTime(), true);
        xmlHttpRequest.send(null);
    }
}

function createXmlHttpRequest() {
    if (window.ActiveXObject) {
        return new ActiveXObject("microsoft.XMLHTTP");
    } else {
        return new XmlHttpRequest();
    }
}

function resMethod() {
    if (xmlHttpRequest.readystate == 4) {
        if (xmlHttpRequest.status == 200) {
            resVal = xmlHttpRequest.responseText;
            resVal = processSpace(resVal);
            eval(method + "('" + resVal + "')");
        }
    }
}
function processSpace(value) {
    var index = value.indexOf("\n");
    var ret = "";
    if (index >= 0) {
        ret = value.substring(0, index - 1);
        ret += value.substring(index + 1);
        return ret;
    }
    return value;
}

function j(obj, i) {
    if (i == null) {
        i = 0;
    }
    if (obj == null) {
        return document.forms[i];
    }
    if (document.getElementById(obj) != null) {
        if (document.getElementById(obj).type == "checkbox") {
            return document.getElementsByName(obj);
        } else {
            return document.getElementById(obj);
        }
    } else {
        return document.forms[i].elements[obj];
    }
}

function mout(obj) {
    obj.className = "op_button" + obj.innerText.length;
}
function mover(obj) {
    obj.className = "op_button_in";
}
function tr_mout(obj) {
    obj.className = "tr_out";
}
function tr_mover(obj) {
    obj.className = "tr_in";
}

function doDel(url, op) {
    if (!op) {
        op = "doDel";
    }
    if (confirm("\u786e\u8ba4\u5220\u9664\uff1f")) {
        window.location.href = "?o=" + op + "&" + url;
    }
}
function CloseDiv(id) {
    var oDiv = document.getElementById(id);
    if (oDiv) {
        oDiv.style.display = "none";
    }
}
function ShowDiv(id) {
    var oDiv = document.getElementById(id);
    if (oDiv) {
        oDiv.style.display = "block";
    }
}
function ForDight(Dight, How) {
    Dight = Math.round(Dight * Math.pow(10, How)) / Math.pow(10, How);
    return Dight;
}

// page
var pageTotal = 1;
var recTotal = 0;
function page_go() {
    page_validate();
    document.forms[0].submit();
}
function page_first() {
    document.forms[0].elements["pageResult.pageNo"].value = 1;
    document.forms[0].submit();
}
function page_pre() {
    var pageNo = document.forms[0].elements["pageResult.pageNo"].value;
    document.forms[0].elements["pageResult.pageNo"].value = parseInt(pageNo)
        - 1;
    page_validate();
    document.forms[0].submit();
}
function page_next() {
    var pageNo = document.forms[0].elements["pageResult.pageNo"].value;
    document.forms[0].elements["pageResult.pageNo"].value = parseInt(pageNo)
        + 1;
    page_validate();
    document.forms[0].submit();
}
function page_last() {
    document.forms[0].elements["pageResult.pageNo"].value = pageTotal;
    document.forms[0].submit();
}
function page_validate() {
    var pageNo = document.forms[0].elements["pageResult.pageNo"].value;
    if (pageNo < 1) {
        pageNo = 1;
    }
    if (pageNo > pageTotal) {
        pageNo = pageTotal;
    }
    document.forms[0].elements["pageResult.pageNo"].value = pageNo;
    var pageSize = document.forms[0].elements["pageResult.pageSize"].value;
    if (pageSize < 1) {
        pageSize = 1;
    }
    document.forms[0].elements["pageResult.pageSize"].value = pageSize;
}
function order_by(field) {
    document.forms[0].elements["pageResult.orderBy"].value = field;
    page_first();
}
function reload() {
    window.location.reload();
}
function help(msg) {
    alert("" + msg);
}
function to(url) {
    window.location.href = url;
}
function back() {
    history.go(-1);
}
function save(url) {
    to(url);
}
function add(url) {
    to(url);
}
function del(msg) {
    if (window.confirm("" + msg + "")) {
        reload();
    }
}
function setCurTime(oid) {
    var now = new Date();
    var year = now.getYear();
    var month = now.getMonth();
    var day = now.getDate();
    var hours = now.getHours();
    var minutes = now.getMinutes();
    var seconds = now.getSeconds();
    var timeString = year + "-" + (month+1) + "-" + day;
    // + " " + hours + ":" + minutes + ":" + seconds;
    var oCtl = document.getElementById(oid);
    oCtl.value = timeString;
}
/**
 * 强制转成两位小数
 *
 * @param num
 * @return
 */
function formartFloat(num){
    var f =parseFloat(num);
    if(isNaN(f)){
        return false;
    }
    var f=Math.floor(num*100)/100;
    var s =f.toString();
    var rs =s.indexOf('.');
    if(rs<0){
        rs=s.length;
        s+=".";
    }
    while(s.length<=rs+2){
        s+='0';
    }
    return s;
}

/**
 * 重置按钮属性
 * @param className
 * @param rClassName
 * @param funcName
 * @param type 1:可用 2：不可用
 */
function resetBtnAttr(obj,className,rClassName,funcName,type){
    if(type==1){
        $("#"+obj).removeClass(rClassName);
        $("#"+obj).addClass(className);
        $("#"+obj).attr("href","javascript:"+funcName+";");
    }else{
        $("#"+obj).removeClass(className);
        $("#"+obj).addClass(rClassName);
        $("#"+obj).attr("href","javascript:;");
    }
}

// 注册ajax事件
// u:url p:parameter obj|string returnType: text|json fun: reutrn method;
$ajax=function(u,p,type,returnType,sfun,efun){
    var browserName = navigator.appName,xhr;
    if(browserName == "Microsoft Internet Explorer") {
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    } else {
        xhr = new XMLHttpRequest();
    }
    // 初始化参数
    var text_p='';
    if(typeof(p)=="object"&&p!=null){
        var keys=[];
        for (var property in p)
            if (property != "getKeys" && property != "getValues")
                keys.push(property); // 将每个属性压入到一个数组中
        for(i=0;i<keys.length;i++){
            if(text_p.length>0)
                text_p+='&';
            text_p+=keys[i]+"="+p[keys[i]];
        }
    }else
        text_p=p;
    // u+=(u.indexOf("?")==-1?"?":"&")+text_p;
    xhr.open(type, u, true);
    xhr.setRequestHeader('If-Modified-Since', '0');  // 清除缓存
    if(type.toLowerCase()=="post")
        xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhr.send(text_p);
    xhr.onreadystatechange = function() {

        if(xhr.readyState == 4) {
            if(xhr.status == 200){
                var o;
                if(returnType.toLowerCase()=="json")
                    o=eval("("+xhr.responseText+")");
                else if(returnType.toLowerCase()=="xml")
                    o=xhr.responseXML;
                else
                    o=xhr.responseText;
                sfun(o);
            }else
                efun();
        }
    };
}

jQuery.extend(
    {
        /**
         * @see   将json字符串转换为对象
         * @param   json字符串
         * @return 返回object,array,string等对象
         */
        evalJSON : function (strJson)
        {
            return eval( "(" + strJson + ")");
        }
    });
jQuery.extend(
    {
        /**
         * @see   将javascript数据类型转换为json字符串
         * @param 待转换对象,支持object,array,string,function,number,boolean,regexp
         * @return 返回json字符串
         */
        toJSON : function (object)
        {
            var type = typeof object;
            if ('object' == type)
            {
                if (Array == object.constructor)
                    type = 'array';
                else if (RegExp == object.constructor)
                    type = 'regexp';
                else
                    type = 'object';
            }
            switch(type)
            {
                case 'undefined':
                case 'unknown':
                    return;
                    break;
                case 'function':
                case 'boolean':
                case 'regexp':
                    return object.toString();
                    break;
                case 'number':
                    return isFinite(object) ? object.toString() : 'null';
                    break;
                case 'string':
                    return '"' + object.replace(/(\/\/|\/\/")/g,"//$1").replace(/n|\/r|\/t/g,  function(){
                        var a = arguments[0];
                        return (((a=='/n'?'//n':a)=='/r'?'//r':a)=='/t'?'//t':a);
                    }) + '"';
                    break;
                case 'object':
                    if (object === null) return 'null';
                    var results = [];
                    for (var property in object) {
                        var value = jQuery.toJSON(object[property]);
                        if (value !== undefined)
                            results.push(jQuery.toJSON(property) + ':' + value);
                    }
                    return '{' + results.join(',') + '}';
                    break;
                case 'array':
                    var results = [];
                    for(var i = 0; i < object.length; i++)
                    {
                        var value = jQuery.toJSON(object[i]);
                        if (value !== undefined) results.push(value);
                    }
                    return '[' + results.join(',') + ']';
                    break;
            }
        }
    });

