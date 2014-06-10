/*
 * Name: ajaxUpload.js
 * authod:zhengzhou
 * create: 2012-03-26 13:55
 */

var isIE = (document.all) ? true : false;

$Id = function(id) {
	return "string" == typeof id ? document.getElementById(id) : id;
}
$Name = function(n) {
	return "string" == typeof n ? document.getElementsByName(n) : n;
}
var Each = function(list, fun) {
	for ( var i = 0; i < list.length; i++) {
		fun(i, list[i]);
	}
};
var Bind = function(object, fun) {
	return function() {
		return fun.apply(object, fun);
	}
}
var ajaxUpload;
if (ajaxUpload == undefined) {
	ajaxUpload = function(settings) {
		this.init(settings);
	}
};

ajaxUpload.prototype = {
	/**
	 * 初始化
	 * 
	 * @param settings
	 * @return
	 */
	init : function(settings) {
		this.customSettings = {};
		this.settings = settings;

		this.initSettings();
	},
	/**
	 * 设置参数
	 * 
	 * @return
	 */
	initSettings : function() {
		this.ensureDefault = function(settingName, defaultValue) {
			this.settings[settingName] = (this.settings[settingName] == undefined) ? defaultValue
					: this.settings[settingName];
		};
		this.ensureDefault("_posturl", ""); // * 链接地址
		this.ensureDefault("_postparam", {}); // 参数
		this.ensureDefault("_framename", new Date().getTime() + "");
		this.ensureDefault("_free_operate_handler", function(){}); // 执行查询时，执行的方法
		this.ensureDefault("_operate_handler", null);// 正在访问中，执行的方法 *
		this
				.ensureDefault("_http_operate_html",
						"<span align='center'><img src='img/loading_smail.gif'/>正在操作中!请稍候……</span>"); // 操作中执行的方法
		this.ensureDefault("_filename", "");// 文件的名称
		this.ensureDefault("_filearray", undefined);// 文件的名称
		this.ensureDefault("_error", function() {
		}); // 执行成功，返回的参数
		this.ensureDefault("_success", function() {
		}); // 执行失败，返回的参数
		this.ensureDefault("_returnType", ""); // 返回的参数类型  text,json,xml
		this.ensureDefault("_allowType", "jpg,gif,png,jpeg,bmp,xls,xlsx,doc,docx,ppt,pptx,xml,swf"); // 允许上传的参数

		this.customSettings = this.settings.custom_settings;
		delete this.ensureDefault;
	},
	setPosturl : function(posturl) {
		if (typeof (posturl) == "string") {
			this.settings._posturl = posturl;
		}
	},
	setPostparam : function(postparam) {
		if (typeof (postparam) == "object") {
			this.settings._postparam = postparam;
		}
	},
	/**
	 * 验证文件名称
	 */
	valuedateFileId : function() {
		var n = this.settings._filename;
		var returnBool = true;
		if (typeof (n) == "string") {
			var a_1 = $Name(this.settings._filename);
			if (typeof (a_1) == "object") {
				for (i = 0; i < a_1.length; i++) {
					if (a_1[i].value.length < 1) {
						returnBool = false;
						break;
					}
				}
			} else
				returnBool = false;
			if (!returnBool)
				alert('异常错误，还有File未选择!');

		} else {
			alert('请正确配置参数,filename!');
			returnBool = false;
		}
		return returnBool;
	},
	/**
	 * 上传
	 * @return
	 */
	doUpload : function() {
		if (!this.valuedateFileId()||!this.validateTypes())
			return;
		this.settings._free_operate_handler(this);
		var _framename = "uploadFrame_" + Math.floor(Math.random() * 1000);
		// ie不能修改iframe的name
		var oFrame =  document.createElement("iframe");
		oFrame.name = _framename;
		oFrame.id = _framename;
		oFrame.style.display = "none";
		// 在ie文档未加载完用appendChild会报错
		var frm = document.createElement("form");
		frm.action = this.settings._posturl;
		frm.target = _framename;
		frm.method = "post";
		// 注意ie的form没有enctype属性，要用encoding
		if (frm.encoding) {
			frm.encoding = 'multipart/form-data';
		} else {
			frm.enctype = 'multipart/form-data';
		}
		frm.id = "updateFrm_" + Math.floor(Math.random() * 1000);
		this.initInput(frm);
		document.body.appendChild(oFrame); // 将空间添加到页面上。
		oFrame.appendChild(frm);
		var operateMethod = [ this.settings._success, this.settings._error ];
		var returnType = this.settings._returnType;
		this.initParams(frm);
		// 准备提交
		frm.submit();
		var xml = {};
		var uploadCallback = function(isTimeout) {
			// Wait for a response to come back
			var io = document.getElementById(oFrame.id);
			try {
				if (io.contentWindow) {
					xml.responseText = io.contentWindow.document.body ? io.contentWindow.document.body.innerHTML
							: null;
					xml.responseXML = io.contentWindow.document.XMLDocument ? io.contentWindow.document.XMLDocument
							: io.contentWindow.document;

				} else if (io.contentDocument) {
					xml.responseText = io.contentDocument.document.body ? io.contentDocument.document.body.innerHTML
							: null;
					xml.responseXML = io.contentDocument.document.XMLDocument ? io.contentDocument.document.XMLDocument
							: io.contentDocument.document;
				}
			} catch (e) {
			}
			if (xml || isTimeout == "timeout") {
				isTimeout = isTimeout == "timeout" ? "error" : "success";
				xml.responseText = xml.responseText.replace("<pre>", "")
						.replace("</pre>", "");
				xml.success = isTimeout;

				var param = null;
				if (returnType == "" || returnType == "text")
					param = xml.responseText;
				else
					param = returnType == "json" ? eval("(" + xml.responseText
							+ ")") : xml.responseXML;

				if (xml.success == 'error') {
					if (operateMethod[1].length > 0) {
						operateMethod[1](param, isTimeout);
					}
				} else {
					if (operateMethod[0].length > 0) {
						operateMethod[0](param, isTimeout);
					}
				}
			}
			document.body.removeChild(io);
		}
		if (window.attachEvent) {
			document.getElementById(oFrame.id).attachEvent('onload',
					uploadCallback);
		} else {
			document.getElementById(oFrame.id).addEventListener('load',
					uploadCallback, false);
		}
	},
	/**
	 * 处理参数
	 */
	initParams : function(frm) {
		var keyArray=this.getKeys();
		var valArray=this.getValues();
		if(keyArray.length>0&&valArray.length>0&&keyArray.length==valArray.length){
			Each(keyArray,function(idx,itm){
				var newInput =document.createElement("input");
				newInput.name=itm;
				newInput.value=valArray[idx];
				newInput.type="textarea";
				newInput.style.display='none';
				frm.appendChild(newInput);
			})
		}
	},
	/**
	 * 初始化File空间（并替换）
	 * @param frm
	 * @return
	 */
	initInput : function(frm) {
		var fileArray = $Name(this.settings._filename);
		len = fileArray.length;
		for ( var z = 0; z < len; z++) {
			var nodeAtt = fileArray[z - z].parentNode;
			var thiscid=typeof fileArray[z-z].id== "undefined"?"":fileArray[z-z].id;
			frm.appendChild(fileArray[z - z]);
			var newInput =document.createElement("input");
			newInput.type = "file";
			newInput.id=thiscid; 
			newInput.name = this.settings._filename;
			nodeAtt.appendChild(newInput); 
		}
	},
	// 工具方法
	getKeys : function() {
		var keys = [];
		if (this.settings._postparam == null
				|| typeof (this.settings._postparam) == "undefined"
				|| typeof (this.settings._postparam) != "object")
			return keys;
		for ( var property in this.settings._postparam) {
			if (property != "getKeys" && property != "getValues") {
				keys.push(property); // 将每个属性压入到一个数组中
			}
		}
		return keys;
	},
	getValues : function() {
		var values = [];
		if (this.settings._postparam == null
				|| typeof (this.settings._postparam) == "undefined"
				|| typeof (this.settings._postparam) != "object")
			return values;
		for ( var property in this.settings._postparam) {
			if (property != "getKeys" && property != "getValues") {
				values.push(this.settings._postparam[property]); // 将每个属性的值压入到一个数组中
			}
		}
		return values;
	},
	/**
	 * 验证文件的类型，是否符合
	 * @return
	 */
	validateTypes:function(){
		var allowTypes=this.settings._allowType;
		var fnameArray=$Name(this.settings._filename);
		var returnVal=false;
		if(fnameArray.length>0&&allowTypes.length>0){
			var allowTypeArray=allowTypes.split(",");
			for(var z=0;z<fnameArray.length;z++){			
				var fname=fnameArray[z].value;
				returnVal=false;
				for(var n=0;n<allowTypeArray.length;n++){
					var altype=allowTypeArray[n];
					if(altype.length>0){
						if(fname.toLowerCase().indexOf(altype.toLowerCase())!=-1){
							returnVal=true;
						}
					}
				} 
				if(!returnVal){
					alert('抱歉，ajaxUpload检测到您选择的附近中，有不允许上传的文件类型! \n具体提示：您当前第 '+(z+1)+' 个文件类型不对!\n允许类型：'+this.settings._allowType);
					break;
				}
			} 
		}else
			alert('ajaxUpload尚未检测到您选择的文件，请选择!');
		return returnVal;
	}
}
/**
 * 执行提交
 */
function doFileUpload_ajax_zz(tobj){
	tobj=typeof object==tobj?tobj:eval(tobj);
	tobj.doUpload();
}
