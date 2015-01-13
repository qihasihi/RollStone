var EttLyb=undefined;
if(EttLyb==undefined){
    EttLyb = function(settings) {
        this.init(settings);
    }
}
EttLyb.prototype={
    init:function(settings){
        //初始化设置
        this.settings=settings;
        this.initSettings();
        this.GenderHtml();
    },initSettings : function() {
        this.ensureDefault = function(settingName, defaultValue) {
            this.settings[settingName] = (this.settings[settingName] == undefined) ? defaultValue
                : this.settings[settingName];
        };
        this.ensureDefault("addressid", null); // * 链接地址
        this.ensureDefault("basePath","../ueditor/lyb"); // * 链接地址
        this.ensureDefault("controlid",false); // 是否可以多选
        this.ensureDefault("width",600); //说说框的宽度
        this.ensureDefault("height",100); //说说框的高度

        this.ensureDefault("readlly",function(){});
        this.ensureDefault("isHasImage",true); //本地图片上传，最多上传的个数
        this.ensureDefault("uploadLimit",9); //本地图片上传，最多上传的个数
        this.ensureDefault("fileSizeLimit",2048); //每张图片最大的大小
        this.ensureDefault("previewAddress",'dv_preview'); //图片上传后显示的地址
        /*this.customSettings = this.settings.custom_settings;*/
        delete this.ensureDefault;
    },GenderHtml:function(){
        var textareaObj=$("#"+this.settings.addressid+" textarea");
        if(textareaObj.length<1){
            var h='<div><textarea></textarea><div style="float:left;width:31px;"></div></div>';
            $("#"+this.settings.addressid).append(h);
            $("#"+this.settings.addressid+" textarea").attr("id",new Date().getTime()+"_lyb");
            textareaObj=$("#"+this.settings.addressid+" textarea");
        }
        $("#"+this.settings.addressid+" textarea").css({width:this.settings.width,height:this.settings.height});
        var edotpr_opt = {
            autoHeightEnabled:false,
            autoWidthEnabled:true,
            toolbars:[
                ['emotion']
            ]
        };
        //加载富文本框
        this.editor= new UE.ui.Editor(edotpr_opt)
        this.editor.render(textareaObj.attr("id"));
        //ueditor.setDataId(newthemeid);
        var addressid=this.settings.addressid;
        this.settings.upImgLength=0;
        var ControlObj=this;
        this.editor.addListener( 'ready', function( editor ) {
            //准备完毕后
            //将工具栏复制入底部栏中
            $("#"+addressid+" .edui-editor-bottomContainer").html($("#"+addressid+" .edui-editor-toolbarbox").html());
             //删除
            $("#"+addressid+" .edui-editor-toolbarbox").remove();
            ControlObj.settings.readlly();
            //向
            var nt=new Date().getTime();

            var dvidVal=nt+"_dv";
            ControlObj.settings.previewid=nt+'_preview';
            if(ControlObj.settings.isHasImage){

            $("#"+addressid).append('<input id="'+nt+'_up" name="'+nt+'_up" type="file" multiple="true">' +
                '<br/><br/><div id="'+nt+'_preview" style="display:none;word-break:break-all;width:550px;height:510px;padding-top:15px;' +
                'padding-left:15px;overflow-y:auto;position: absolute;background-color: white' +
                'border: 1px solid #696;-webkit-border-radius: 2px;-moz-border-radius: 8px;'+
           'border-radius: 8px;-webkit-box-shadow: #666 0px 0px 10px;-moz-box-shadow: #666 0px 0px 10px;box-shadow: #666 0px 0px 10px;' +
                '"></div>');

                ControlObj.objUploadify = $('#'+nt+'_up').uploadify({
                    'swf'      : ControlObj.settings.basePath+'/uploadify/uploadify.swf',
                    'uploader' : ControlObj.settings.basePath+'/uploadify/imageUp.jsp'
                    ,'fileTypeDesc':'图片'
                    ,'fileTypeExts':'*.jpg;*.png;*.bmp;*.jpeg'
                    ,'fileSizeLimit':2048,              //2M
                  //  ,'uploadLimit':9,
                    'buttonText':'图片',
                    'buttonImage':ControlObj.settings.basePath+'/uploadify/img/img_normal.png',
                    'width':24,
                    'height':24,
                    removeTimeout:1
                    ,onUploadSuccess:function(file,data, response){//文件上传成功后触发
                        var rj=eval("("+data+")");
                       if(rj.state=="SUCCESS"){
                            var h="<div style='width:250px;height:250px;float:left;padding-right:15px;' id='"+nt+"_prev_"+file.name+"'>" +
                                "<div style='width:100%;height:100%;;position:relative'>";
                                h+="<img src='../ueditor/lyb/uploadify/getImage.jsp?url="+rj.url+"' title='"+file.name+"'/>" +
                                "<span style='top:3px;right:3px;position:absolute;z-index:2;" +
                                "'><span style='background-color: black;" +
                                    "filter:alpha(opacity=50);-moz-opacity:0.5;-khtml-opacity: 0.5;opacity: 0.5;'>" +
                                    "<a style='color:white' href='javascript:;' onclick=\""+ControlObj.settings.controlid+".delImage(\'"+rj.url+"\',\'"+nt+"_prev_"+file.name+"\')\">X</a></span></span>" +
                                "&nbsp;&nbsp;</div></div>";
                           $("#"+nt+"_preview").append(h);
                           $("#"+ControlObj.settings.previewid).show();
                       }
                    },onDialogClose:function(filesSelected){
                        var selFile=filesSelected.files;
                        var pvlen=$("#"+nt+"_preview>div").length;
                        $.each(selFile,function(idx,itm){
                            pvlen=pvlen+1;
                            if(pvlen>ControlObj.settings.uploadLimit){
                                $('#'+nt+'_up').uploadify('cancel', 'SWFUpload_0_'+itm.index);
                                if($("#"+nt+"_preview span[id='sp_error']").length<1){
                                    $("#"+nt+"_preview").append("<span id='sp_error' style='color:red'>您选择的文件已经操过最大上限数量!系统自动上传前"+ControlObj.settings.uploadLimit+"个文件!</span>");
                                }
                                document.getElementById(nt+'_preview').scrollTop=document.getElementById(nt+'_preview').scrollHeight;
                                setTimeout(function(){
                                    $("#"+nt+"_preview span[id='sp_error']").hide('fast');
                                    $("#"+nt+"_preview span[id='sp_error']").remove();
                                },10000);
                                return;
                            }
                        })
                    }
                });
            }
            $('div[id="'+nt+'_up"]').css({float:'left',width:'30px',top:'4px'});
           $("#"+nt+"_up-queue").hide();
        });
    },delImage:function(path,id){
        var cobj=this;
        //服务器删除图片
        $.ajax({
            url:cobj.settings.basePath+"/uploadify/delImage.jsp?url="+path,
            type:'post',
            dataType:'json',
            error:function(){
                alert('网络异常！');
            },
            success:function(rps){
                if(rps.state=="error"){
                    alert(rps.msg);
                    return;
                }
                //删除成功后，将页面上的图像删除
                $("div[id='"+id+"']").remove();
                if($("#"+cobj.settings.previewid+">div").length<1)
                    $("#"+cobj.settings.previewid).hide('fast');
         //       ControlObj.settings.upImgLength--;
            }
        })
    },destroy:function(){
        this.editor.destroy();
    }
}
