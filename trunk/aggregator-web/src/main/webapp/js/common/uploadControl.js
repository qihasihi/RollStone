
/**
 * UploadController控件初始化。根据浏览器进行初始化。
 * @param url
 * @return
 */
var uploadControl={};    
uploadControl.ismultiple=true;
uploadControl.fileAttribute=[];
function InitUpload(url,ismutil) {
	var OsObject = "";
	if (navigator.userAgent.indexOf("MSIE") > 0) {		
		UploadInit(url,ismutil);
	} else if (isMozilla = navigator.userAgent.indexOf("Chrome") > 0) {	
		UploadInit(url,ismutil);		
	} else if (isFirefox = navigator.userAgent.indexOf("Firefox") > 0) {
		alert("异常，上传控件目前只支持Chrome,IE! \n\n您当前所用的是Firefox");
	} else if (isSafari = navigator.userAgent.indexOf("Safari") > 0) {
		alert("异常，上传控件目前只支持Chrome,IE! \n\n您当前所用的是Safari");
	} else if (isCamino = navigator.userAgent.indexOf("Camino") > 0) {
		alert("异常，上传控件目前只支持Chrome,IE! \n\n您当前所用的是Camino");
	} else {
		alert("异常，上传控件目前只支持Chrome,IE! ");
	}	
}


var infosVM = {};
var UploadInfoStatus = {
	usWaiting : 0,
	usPaused : 1,
	usUploading : 2,
	usCompleted : 3,
	usAborted : 4,
	usCanceling : 5,
	usCanceled : 6
};
function formatFileSize(fileSize) {
	var unit = ' B';

	if (fileSize > 1024) {
		unit = " KB";
		fileSize = fileSize / 1024
		if (fileSize > 1024) {
			unit = " MB";
			fileSize = fileSize / 1024

			if (fileSize > 1024) {
				unit = " G";
				fileSize = fileSize / 1024
			}
		}
	}

	return Math.round(fileSize * 100) / 100 + unit;
}

function reconvert(str) {
	str = str.replace(/(\\u)(\w{4})/g, function($0) {
		return (String.fromCharCode(parseInt((escape($0).replace(/(%5Cu)(\w{4})/g, "$2")), 16)));
	});
	str = str.replace(/(&#x)(\w{4});/g, function($0) {
		return String.fromCharCode(parseInt(escape($0).replace(/(%26%23x)(\w{4})(%3B)/g, "$2"), 16));
	});
	return str;
}

function uploadInfoVM(item, index) {
	var self = this;
	self.filePath = item.FileName;
	self.filename = self.filePath.substr(self.filePath.lastIndexOf('\\') + 1);
	self.fileSize = ko.observable(formatFileSize(item.FileSize));
	self.rate = ko.observable(formatFileSize(item.TransferRate));
	self.pos = ko.observable(item.UploadRate);
	self.index = index;

	self.update = function(item) {
		self.fileSize(formatFileSize(item.FileSize));
		self.rate(formatFileSize(item.TransferRate));
		self.pos(item.UploadRate);
	}
}

function uploadListVM() {
	var self = this;
	self.infos = ko.observableArray( []);

	self.addInfo = function(item) {
		var list = self.infos();
		var exists = null;
		 var i = 0;
		for (; i < list.length; i++) {
			if (list[i].filePath == item.FileName) {
				exists = list[i];
				if (item.Status == UploadInfoStatus.usCanceled) {
					self.infos.remove(list[i]);
					return null;
				}
				break;
			}
		}

		if (exists == null) {
			if (item.Status != UploadInfoStatus.usCanceled) {
				var index = self.infos().length;
				self.infos.push(new uploadInfoVM(item, index));
				return {
					index : index,
					isNew : true
				};
			} else
				return null;
		} else {
			exists.update(item);
			return {
				index : exists.index,
				isNew : false
			};
		}
	}
}

function upadteUploadControl(item, control) { 
	if (control != null) {
		var play = jQuery("#upload_play_" + control.index);
		var pause = jQuery("#upload_pause_" + control.index);
		var cancel = jQuery("#upload_cancel_" + control.index);
		var pb = jQuery("#upload_pb_" + control.index);
		var pbtext = pb.children(".info-pb-label");
		if(!uploadControl.ismultiple)
			$("#btnUpload").hide();		
		if (control.isNew) {
			play.button( {
				icons : {
					primary : "ui-icon-play"
				},
				text : false
			}).click(function() {
				var data = {
					cmd : "start",
					filename : item.FileName
				};
				uploader.ExecuteCmd($.toJSON(data));
			});
			pause.button( {
				icons : {
					primary : "ui-icon-pause"
				},
				text : false 
			}).click(function() {
				var data = {
					cmd : "pause",
					filename : item.FileName
				};
				uploader.ExecuteCmd($.toJSON(data));
			});
			cancel.button( {
				icons : {
					primary : "ui-icon-close"
				},
				text : false
			}).click(function() {
				var data = {
					cmd : "cancel",
					filename : item.FileName
				};
				uploader.ExecuteCmd($.toJSON(data));

                    if(uploadControl.fileAttribute.length>0){
                        var fname=item.FileName.substring(item.FileName.lastIndexOf("\\")+1);
                        $.each(uploadControl.fileAttribute,function(ixd,imd){
                            if(typeof(imd)!="undefined"&&imd.filename==fname&&imd.size==item.FileSize){
                                uploadControl.fileAttribute[ixd]=undefined;
                                return;
                            }
                        })
                    }

                    if(!uploadControl.ismultiple)
                        $("#btnUpload").show();
			});
			;
			pb.progressbar( {
				value : false,
				change : function() {
					pbtext.text(pb.progressbar("value") + "%");
				},
				complete : function() {
					pbtext.text("完成!");
				}  
			});    
		}
		pb.progressbar("value", parseFloat(item.UploadRate));

		switch (item.Status) {
		case UploadInfoStatus.usWaiting:
		case UploadInfoStatus.usPaused:
			play.button("enable");
			pause.button("disable");
			break;
		case UploadInfoStatus.usUploading:
			play.button("disable");
			pause.button("enable");
			break;
		case UploadInfoStatus.usCompleted:
			var fname=item.FileName.substring(item.FileName.lastIndexOf("\\")+1);
			var ishas=false;
			if(uploadControl.fileAttribute.length>0){
				var flag=false;
				$.each(uploadControl.fileAttribute,function(ixd,imd){
					if(typeof(imd)!="undefined"&&imd.filename==fname&&imd.size==item.FileSize){
						flag=true;
						return;
					}
				})
				if(!flag)
					uploadControl.fileAttribute[uploadControl.fileAttribute.length]={filename:fname,size:item.FileSize};
			}else
				uploadControl.fileAttribute[uploadControl.fileAttribute.length]={filename:fname,size:item.FileSize};
			if(uploadControl.fileAttribute.length>0){
				$("#btn_editinfo").show();
			}
			break;
		case UploadInfoStatus.usCanceling: 
		case UploadInfoStatus.usCanceled:
			play.hide();
			pause.hide();
			cancel.hide();
			//cancel.hide();
			break;
		case UploadInfoStatus.usAborted:
			play.hide();
			pause.hide();
			break;
		}
	}
}

function showUploadInfo() {
	try {
		var data = {};
		data.cmd = "getinfo";
		var info = reconvert(uploader.ExecuteCmd($.toJSON(data)));
        upcontrolIdx=jQuery.evalJSON(info).length-1;
		jQuery.each(jQuery.evalJSON(info), function(i, v) {
			var retval = infosVM.addInfo(v);
			upadteUploadControl(v, retval);
		});
	} catch (e) {

	}
	window.setTimeout("showUploadInfo()", 1000);
}

function uploadFile(filename) {
	var data = {};
	data.cmd = "upload";
	if (filename) {
		data.filename = filename;
	}
	uploader.ExecuteCmd($.toJSON(data));
}
var init1 = {};
function UploadInit(url,ismu) {
    $("#fileUploader").change(function() {
        alert(getFullPath(this));
    });
    if(typeof(ismu)!="undefined")
        uploadControl.ismultiple=ismu;  //是否可以多选
    init1.url = url;
    if(typeof(ismu)!="undefined")
        init1.multiselect=ismu;  //是否可以多选
    //加载BTN事件
    loadUploadClick();

	infosVM = new uploadListVM();
	ko.applyBindings(infosVM, $("#divUploadInfo")[0]);
	showUploadInfo();
}
var upcontrolIdx=0;
/**
 * 加载upload但不加载选择文件事件
 * @param url URL
 * @param ismu 是否多选
 * @constructor
 */
function UploadInit_NOLoadClick(url,ismu) {

    var h='<div class="jxpt_ziyuan_sczrL" style="width:660px;padding: 10px 50px 0 10px;"><span id="span_obj_add"></span>';
    // 加载控件
     if (isMozilla = navigator.userAgent.indexOf("Chrome") > 0) {
        h+=' <object id="uploader" type="application/x-itst-activex" clsid="{787D6738-39C4-458C-BE8B-0084503FC021}" style="width: 1px; height: 1px"></object>';
    }
    h+='<button id="btnUpload">上传资源</button>';
    h+='</div>';

    h+='<div id="divUploadInfo" class="info-container" data-bind="foreach:infos" style="width:335px;padding-top:15px;">';
    h+=' <div style="height: 15px">';
    h+=' <span class="info-filename" data-bind="text: filename"></span>';
    h+='</div>';
    h+=' <div class="info-pb-container" style="width:100%;height:25px">';
    h+=' <div data-bind="attr: { id:\'upload_pb_'+upcontrolIdx+'\' }" class="info-pb" style="width:100%">';
    h+=' <div class="info-pb-label">等待上传..</div>';
    h+='</div>';
    h+=' </div>';
    h+='<div style="height: 30px;padding-top:8px;">';
    h+='<button data-bind="attr: { id:\'upload_play_'+upcontrolIdx+'\' }" class="info-button">开始</button>';
    h+='<button data-bind="attr: { id:\'upload_pause_'+upcontrolIdx+'\' }" class="info-button">暂停</button>';
    h+='<button data-bind="attr: { id:\'upload_cancel_'+upcontrolIdx+'\'}" class="info-button">取消</button>';
    h+=' <div class="info-text-right">速度:<span data-bind="text: rate"></span>/s <span data-bind="text: fileSize"></span></div>';
    h+='</div>';
    h+='</div>';
    $("#dv_load_core_spfile").html('');
    $("#dv_load_core_spfile").html(h);
    if (navigator.userAgent.indexOf("MSIE") > 0) {
        //$("#uploader").attr("classid","clsid:787D6738-39C4-458C-BE8B-0084503FC021");
        window.uploader = new ActiveXObject("EttUploadPlugin.CoEttUploadPlugin");
    }

    $("#fileUploader").change(function() {
        alert(getFullPath(this));
    });
    if(typeof(ismu)!="undefined")
        uploadControl.ismultiple=ismu;  //是否可以多选
    init1.url = url;
    if(typeof(ismu)!="undefined")
        init1.multiselect=ismu;  //是否可以多选
    //加载BTN事件
   // loadUploadClick();
    init1.chunksize = 1024 * 256;
    uploader.Init($.toJSON(init1));
    $("#btnUpload").button( {
        icons : {
            primary : "ui-icon-circle-plus"
        }
    }).click(function() {
            uploadFile();
        });
    infosVM = new uploadListVM();
    ko.applyBindings(infosVM, $("#divUploadInfo")[0]);
    showUploadInfo();
}


/**
 * 加载upload但不加载选择文件事件
 * @param url URL
 * @param ismu 是否多选
 * @constructor
 */
function UploadInit_CourseResource(url,ismu) {

    var h='<span id="span_obj_add"></span>';
    // 加载控件
    if (isMozilla = navigator.userAgent.indexOf("Chrome") > 0) {
        h+=' <object id="uploader" type="application/x-itst-activex" clsid="{787D6738-39C4-458C-BE8B-0084503FC021}" style="width: 1px; height: 1px"></object>';
    }
    h+='<button id="btnUpload">上传资源</button>';
    h+='<div id="divUploadInfo" class="info-container" data-bind="foreach:infos" style="width:335px;padding-top:15px;">';
    h+=' <div style="height: 15px">';
    h+=' <span class="info-filename" data-bind="text: filename"></span>';
    h+='</div>';
    h+=' <div class="info-pb-container" style="width:100%;height:25px">';
    h+=' <div data-bind="attr: { id:\'upload_pb_'+upcontrolIdx+'\' }" class="info-pb" style="width:100%">';
    h+=' <div class="info-pb-label">等待上传..</div>';
    h+='</div>';
    h+=' </div>';
    h+='<div style="height: 30px;padding-top:8px;">';
    h+='<button data-bind="attr: { id:\'upload_play_'+upcontrolIdx+'\' }" class="info-button">开始</button>';
    h+='<button data-bind="attr: { id:\'upload_pause_'+upcontrolIdx+'\' }" class="info-button">暂停</button>';
    h+='<button data-bind="attr: { id:\'upload_cancel_'+upcontrolIdx+'\'}" class="info-button">取消</button>';
    h+=' <div class="info-text-right">速度:<span data-bind="text: rate"></span>/s <span data-bind="text: fileSize"></span></div>';
    h+='</div>';
    h+='</div>';
    $("#uploadcontrol_div").html('');
    $("#uploadcontrol_div").html(h);
    if (navigator.userAgent.indexOf("MSIE") > 0) {
        //$("#uploader").attr("classid","clsid:787D6738-39C4-458C-BE8B-0084503FC021");
        window.uploader = new ActiveXObject("EttUploadPlugin.CoEttUploadPlugin");
    }


    if(typeof(ismu)!="undefined")
        uploadControl.ismultiple=ismu;  //是否可以多选
    init1.url = url;
    if(typeof(ismu)!="undefined")
        init1.multiselect=ismu;  //是否可以多选
    //加载BTN事件
    // loadUploadClick();
    init1.chunksize = 1024 * 256;
    uploader.Init($.toJSON(init1));
    $("#btnUpload").button( {
        icons : {
            primary : "ui-icon-circle-plus"
        }
    }).click(function() {
            uploadFile();
        });
    infosVM = new uploadListVM();
    ko.applyBindings(infosVM, $("#divUploadInfo")[0]);
    showUploadInfo();
}