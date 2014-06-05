/**
 * Created by zhengzhou on 14-5-9.
 */
var EttViewPhoto=undefined;
if(EttViewPhoto==undefined){
    EttViewPhoto = function(settings) {
        this.init(settings);
    }
}
EttViewPhoto.prototype={
    init:function(settings){
        //初始化设置
        this.settings=settings;
        this.settings.nd=new Date().getTime()+"";
        this.initSettings();
        this.GenderHtml();
    },initSettings : function() {
        this.ensureDefault = function(settingName, defaultValue) {
            this.settings[settingName] = (this.settings[settingName] == undefined) ? defaultValue
                : this.settings[settingName];
        };
        this.ensureDefault("imgIdx",0);
        this.ensureDefault("basePath","");
        this.ensureDefault("addressid", null); // * 链接地址
        this.ensureDefault("photoObj",[]);   //图片JSON
        this.ensureDefault("photoRemarkObjName",'remark');//照片路径在photoObj JSON中的参数名称  例：[{"url":"uploadfile/aaa.jpg","remark":"测试图片"}] 此处添remark
        this.ensureDefault("photoUrlObjName",'path');//照片路径在photoObj JSON中的参数名称  例：[{"url":"uploadfile/aaa.jpg","remark":"测试图片"}] 此处添path
        this.ensureDefault("controlid","");
        //this.ensureDefault("height",100); //说说框的高度
        /*this.customSettings = this.settings.custom_settings;*/
        delete this.ensureDefault;
    }
    ,GenderHtml:function(){
        var zzLen=$("#fade").length;
        var h='';
        if(zzLen<1){//如果不存在遮造层，则创建
            h='<div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>';// filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;
            $("body").append(h);
        }
        if(this.settings.photoObj.length<1){
            alert('异常错误，没有预览文件!');return;
        }
        this.settings.ulAddressId="rotateme";
        h='<ul id="'+this.settings.ulAddressId+'"></ul>';
        $("#"+this.settings.addressid).append(h);
        var sets=this.settings;

        //开始得生成Image
        var htm='';
        $.each(this.settings.photoObj,function(idx,itm){
            htm+='<li><img rastate-src="'+eval("(itm."+sets.photoUrlObjName+")")+'" src="'+sets.basePath+'getImage.jsp?path='+eval("(itm."+sets.photoUrlObjName+")")+'&issmail=1" alt="" title="'+eval("(itm."+sets.photoRemarkObjName+")")+'"/></li>' ;
            if(idx%20==0&&idx>0){
                $("#"+sets.ulAddressId).append(htm);
                htm='';
            }
        });
        if(htm.length>0){
            $("#"+sets.ulAddressId).append(htm);
            htm='';
        }
        //生成完后，加载数据
    //    $("#"+sets.ulAddressId).hide();
        $("#"+sets.ulAddressId).ImageRotate({basePath:sets.basePath});

        showModel(sets.addressid);
    }
}
