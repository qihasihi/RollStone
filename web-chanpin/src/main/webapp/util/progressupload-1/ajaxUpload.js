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
$trim=function(t){
	return t.replace(/(^\s*)|(\s*$)/g, "");		
}
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
		if(settings._controlname.length<1){
			alert('控件变量名称您尚未填写，必须填写变量名称后才能正常操作!');
			return;
		}
		// 生成必要的DIV名称
		this.framename1 = "upF_"+new Date().getDate();
		
		this.modeldivname = "_md3" + new Date().getTime();
		this.zhezaodivname = 'zz' + new Date().getTime();
		this.showprogressoutdiv = "out" + new Date().getTime();
		this.showprogressindiv = "in" + new Date().getTime();
		this.showprogressdisdiv = "dis" + new Date().getTime();
	
		
		
		this.customSettings = {};
		this.settings = settings;
		this.initSettings();	
		if(this.settings._formname.length>0)
			this.frmname=this.settings._formname;
		
		
			
	}
	,getRootProject:function(){
		
		  var pathName = window.location.pathname.substring(1); 
		     var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/')); 
		     return window.location.protocol + '//' + window.location.host + '/'+ webName + '/';
	} ,
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
		this.ensureDefault("basepath", this.getRootProject()); // * 链接地址
		this.ensureDefault("_posturl", "progressupload-1/manager/progress.jsp"); // * 链接地址
		this.ensureDefault("_postparam", {}); // 参数
		this.ensureDefault("_framename", new Date().getTime() + "");
		this.ensureDefault("_formname", "");
		this.ensureDefault("_controlname", ""); // 变量的对象名称(String)
		this.ensureDefault("_free_operate_handler", function() {
		}); // 执行查询时，执行的方法
		this.ensureDefault("_operate_handler", null);// 正在访问中，执行的方法 *
		this.ensureDefault("_filesavepath", ""); // 文件存储的路径
		this.ensureDefault("_controlbasepath","util");	//整个控件所在的目录下。如在WeebRoot下，则填写""
		this.ensureDefault("_doprogresspath", "progressupload-1/manager/progress.jsp");
		this.ensureDefault("_douploadpath", "progressupload-1/manager/uploadfile.jsp");
		this.ensureDefault("_filename", "");// 文件的名称
		this.ensureDefault("_filearray", undefined);// 文件的名称
		this.ensureDefault("_error", function() {
		}); // 执行成功，返回的参数
		this.ensureDefault("_success", function() {
		}); // 执行失败，返回的参数
		this.ensureDefault("_returnType", ""); // 返回的参数类型 text,json,xml
		this.ensureDefault("_allowType",
				"jpg,gif,png,jpeg,bmp,xls,xlsx,doc,docx,ppt,pptx,xml,swf,rar,zip"); // 允许上传的参数

		this.customSettings = this.settings.custom_settings;
		delete this.ensureDefault; 
		if(typeof(this.settings._controlbasepath)!="undefined"&&this.settings._controlbasepath!=null&&this.settings._controlbasepath.length>0){
			this.settings._posturl=this.settings.basepath+"/"+this.settings._controlbasepath+"/"+this.settings._posturl;
			this.settings._doprogresspath=this.settings.basepath+"/"+this.settings._controlbasepath+"/"+this.settings._doprogresspath;
			this.settings._douploadpath=this.settings.basepath+"/"+this.settings._controlbasepath+"/"+this.settings._douploadpath;
			
		}
		
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
	genderHtml : function() {
		// ie不能修改iframe的name
				
		// 生成iframe
		if(document.getElementsByName(this.framename1).length>0)
			document.body.removeChild(document.getElementById(this.framename1));
		var oFrame1 = document.createElement("iframe");
		oFrame1.id=oFrame1.name=this.framename1;
		oFrame1.style.display = "none";
		var frm ;
		// 在ie文档未加载完用appendChild会报错
		document.getElementsByName(this.framename1).length
		if(this.frmname.length<1){
			this.frmname="ufrm_"+new Date().getTime();
			frm=document.createElement("form");
			frm.id=frm.name=this.frmname;
	//		frm.action = this.settings._doprogresspath;
	//		if(frm.action.indexOf("?")>0)
	//			frm.action+='&callback=parent.upload&key=';
			//oFrame.appendChild(frm);
			document.body.appendChild(frm);
			frm.style.display='none';
			this.initInput(frm);
			
		}else
			frm=document.getElementById(this.frmname);
		frm.target = this.framename1;
		frm.method = "post";
		// 注意ie的form没有enctype属性，要用encoding
		if (frm.encoding) {
				frm.encoding = 'multipart/form-data';
		} else {
			frm.enctype = 'multipart/form-data';
		}	
			
	
		document.body.appendChild(oFrame1); // 将空间添加到页面上。
	
		// 生成div显示区域
		var d1 = document.createElement("div");
		var d1child = document.createElement("div");
		var d2 = document.createElement("div");
		d1.id = this.showprogressoutdiv;
		d1.className="p_out";
		d1.align="left";
		d1child.id = this.showprogressindiv;
		d1child.className="p_in";
		d2.id = this.showprogressdisdiv;
		d2.className="dis";
		d1.appendChild(d1child);
		// 浮出层
		var modeld = document.createElement("div");
		modeld.id = this.modeldivname;
		modeld.className = 'white_content';
		modeld.style.width="220px";
		modeld.style.height="45px";
		modeld.style.display = 'none';
		modeld.align='center';
		modeld.style.backgroundColor='white';
		modeld.appendChild(d1);
		modeld.appendChild(d2);
		
		document.body.appendChild(modeld);
		// 遮罩层
		var zhezaodiv = document.createElement("div");
		zhezaodiv.id = this.zhezaodivname;
		zhezaodiv.className = "black_overlay";
		zhezaodiv.style.display = 'none';

		document.body.appendChild(zhezaodiv);
		return frm;
	},
	getScrollTop : function getScrollTop() {
		var scrollTop = 0;
		if (document.documentElement && document.documentElement.scrollTop) {
			scrollTop = document.documentElement.scrollTop;
		} else if (document.body) {
			scrollTop = document.body.scrollTop;
		}
		return scrollTop;
	},
	showModel : function(isdrop) {
		var showdiv=document.getElementById(this.modeldivname);
		var zhezaodiv=document.getElementById(this.zhezaodivname);
		zhezaodiv.style.height=window.screen.availHeight + parseFloat(this.getScrollTop());
		zhezaodiv.style.width=window.screen.availWidth;		
		try {
			showdiv.style.zIndex=1005;
			showdiv.style.position="absolute";
			showdiv.style.top=20 + parseInt(this.getScrollTop());
			showdiv.style.left=window.screen.availWidth/2-parseFloat(showdiv.style.width)/2;
		} catch (e) {
		}

		if (typeof (isdrop) == "undefined" || isdrop == true) {
			// 允许拖拽事件
			var wcontentflag=false;
			function movedown(e) {
				if (!wcontentflag) {
					wcontentflag = true;
					wcontentX = e.pageX;
					wcontentY = e.pageY;
					offsetWidth = e.pageX - parseFloat(this.style.left);
					offsetHeight = e.pageY - parseFloat(this.style.top);
					this.style.cursor="pointer";
				}
			}
			
			function move(e){
				if (wcontentflag) {
					var nowPageX = e.pageX;
					var nowPageY = e.pageY;

					var nowObjX = parseFloat(showdiv.style.left);
					var nowObjY = parseFloat(showdiv.style.top);

					if (typeof (nowObjX) == 'undefined'
							|| nowObjX == "" || nowObjX == null
							|| nowObjX == NaN) {
						alert('异常错误!');
					}
					if (typeof (nowObjY) == 'undefined'
							|| nowObjY == "" || nowObjY == null
							|| nowObjY == NaN) {
						alert('异常错误!');
					}
					nowObjX = parseFloat(nowObjX)
							+ (nowPageX - nowObjX);
					nowObjY = parseFloat(nowObjY)
							+ (nowPageY - nowObjY);

					nowObjX -= offsetWidth;
					nowObjY -= offsetHeight;
					showdiv.style.left=nowObjX;
					showdiv.style.top=nowObjY;
				}
			}
		
			function moveup(e){
				if (wcontentflag) {
					wcontentflag = false;
					isaddoffset = false;
					showdiv.style.cursor='default';					
				}
			}
			//添加监听
			if(document.attachEvent){
				showdiv.attachEvent('onmousedown',movedown);
				document.attachEvent('onmousemove',move);
				document.attachEvent('onmouseup',moveup);
			}else{				
				showdiv.addEventListener('mousedown',movedown,false);
				document.addEventListener('mousemove',move,false);
				document.addEventListener('mouseup',moveup,false);
			}
		}
		showdiv.style.display='block';
		zhezaodiv.style.display='block';
	},
	/**
	 * 上传
	 * 
	 * @return
	 */
	doUpload : function() {
		if (!this.valuedateFileId() || !this.validateTypes())
			return;
		if(this.settings._free_operate_handler)
		this.settings._free_operate_handler(this);
		// 生成必要的HTML
		this.frm=this.genderHtml();
		var operateMethod = [ this.settings._success, this.settings._error ];
		var returnType = this.settings._returnType;
		this.initParams(this.frm);

		// 准备提交
		this.progress();	
	
		// 提交立即显示Model
		this.showModel(false);
		var xml = {};
		var ifrom1name=this.framename1;
		
		var md=this.modeldivname;
		var zzd=this.zhezaodivname;
		var outd=this.showprogressoutdiv;
		var disd=this.showprogressdisdiv;
		
		function uploadCallback(isTimeout) {
			// Wait for a response to come back
			var io = document.getElementById(ifrom1name);
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
			if(xml.responseText.length>0&&returnType=='json'||returnType=='xml'&&xml.responseXML.length>0){
			if (xml || isTimeout == "timeout") {
					isTimeout = isTimeout == "timeout" ? "error" : "success";
					if(xml.responseText.length>0){
						xml.responseText = xml.responseText.replace("<pre>", "")
							.replace("</pre>", "");
					}
					xml.success = isTimeout;
	
					var param = null;
					if (returnType == "" || returnType == "text")
						param = xml.responseText;
					else if(xml.responseText.length>0||xml.responseXML.length>0)
						if(returnType == "json"&&xml.responseText.length>0)
							param =  eval("(" + xml.responseText+ ")");
						else if(returnType=='xml'&&xml.responseXML.length>0)
							param =  eval("(" + xml.responseXML+ ")");
	
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
			}
		
			
		
			
			setTimeout(function(){
				var mdiv=document.getElementById(md);
				if(document.detachEvent){
					document.detachEvent("onmousemove"); 
					document.detachEvent("onmouseup");
				}else{
					document.removeEventListener("mousemove",undefined,false);
					document.removeEventListener("mouseup",undefined,false);
				}
				//删除
				document.body.removeChild(document.getElementById(md));
				document.body.removeChild(document.getElementById(zzd));				
			}, 2000);
		//	document.body.removeChild(io);	
					
		}
		var disdivname=this.showprogressdisdiv,individ=this.showprogressindiv,executeCount=0;;
		function uploadCallBack_p(p1){		
		//	if(executeCount>0){
		    	 document.getElementById(individ).style.width="100%";	   
				var ht="上传成功，此窗口将在2秒钟后关闭!";
				document.getElementById(disdivname).innerHTML=ht;
				uploadCallback(p1);
	//	}
		//	executeCount++;
			
		}
		var opcontrol=this.settings._controlname;
		
	
		
		if (window.attachEvent) {
			document.getElementById(this.framename1).attachEvent('onload',uploadCallBack_p);
		} else {
			document.getElementById(this.framename1).addEventListener('load',uploadCallBack_p, false);
		}
	},
	/**
	 * 处理参数 */	
	initParams : function(frm) {
		var keyArray = this.getKeys();
		var valArray = this.getValues();
		if (keyArray.length > 0 && valArray.length > 0
				&& keyArray.length == valArray.length) {
			Each(keyArray, function(idx, itm) {
				var newInput = document.createElement("input");
				newInput.name = itm;
				newInput.value = valArray[idx];
				newInput.type = "textarea";
				newInput.style.display = 'none';
				frm.appendChild(newInput);
			})
		}
	},
	/**
	 * 初始化File空间（并替换）
	 * 
	 * @param frm
	 * @return
	 */
	initInput : function(frm) {
		var fileArray = $Name(this.settings._filename);
		len = fileArray.length;
		for ( var z = 0; z < len; z++) {
			var nodeAtt = fileArray[z - z].parentNode;
			var thiscid = typeof fileArray[z - z].id == "undefined" ? ""
					: fileArray[z - z].id;
			frm.appendChild(fileArray[z - z]);
			var newInput = document.createElement("input");
			newInput.type = "file";
			newInput.id = thiscid;
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
	 * 
	 * @return
	 */
	validateTypes : function() {
		var allowTypes = this.settings._allowType;
		var fnameArray = $Name(this.settings._filename);
		var returnVal = false;
		if (fnameArray.length > 0 && allowTypes.length > 0) {
			var allowTypeArray = allowTypes.split(",");
			for ( var z = 0; z < fnameArray.length; z++) {
				var fname = fnameArray[z].value;
				returnVal = false;
				for ( var n = 0; n < allowTypeArray.length; n++) {
					var altype = allowTypeArray[n];
					if (altype.length > 0) {
						if (fname.toLowerCase().indexOf(altype.toLowerCase()) != -1) {
							returnVal = true;
						}
					}
				}
				if (!returnVal) {
					alert('抱歉，ajaxUpload检测到您选择的附近中，有不允许上传的文件类型! \n具体提示：您当前第 '
							+ (z + 1) + ' 个文件类型不对!\n允许类型：'
							+ this.settings._allowType);
					break;
				}
			}
		} else
			alert('ajaxUpload尚未检测到您选择的文件，请选择!');
		return returnVal;
	},progress:function(){
		if(this.settings._controlname.length>0){
			if(typeof(this.settings._key)=="undefined")
				//得到Key
			var key=new Date().getTime();
			//设置页面引用。
			//表单提交至Iframe中
				
			document.getElementById(this.showprogressdisdiv).innerHTML = '初始化数据...';  
		    document.getElementById(this.showprogressindiv).style.width = "0%";   
		  //form进行提交		
		    this.uploadFile(key);
			//开始获取值
		    this.getUploadSize(key);
		}
	},upload:function(len,total){		
		 document.getElementById(this.showprogressindiv).style.width = (Math.round(len/total*100))+'%';  
	     document.getElementById(this.showprogressdisdiv).innerHTML = Math.floor(len/1024/1024*100)/100 + '/' + Math.floor(total/1024/1024*100)/100 + 'M';  
	     if(len === total) {  	
	         document.getElementById(this.showprogressdisdiv).innerHTML = "正在合并文件!";  
	     }  
	},uploadFile:function(key){
		this.frm.action = this.settings._douploadpath+'?key='+key;  
		this.frm.target=this.framename1;
		this.frm.submit();  
        document.getElementById(this.showprogressdisdiv).innerHTML = "开始传送数据...";   
	},
	//得到cook并显示
		getUploadSize:function(key){
			var controlname=this.settings._controlname,t;
			this.ajax(this.settings._doprogresspath+'?key='+key,undefined,'post','text',function(txt){
				var ckArray=txt.split("|");
				if(ckArray.length>1){
					eval(controlname+".upload("+parseFloat($trim(ckArray[0]))+","+parseFloat($trim(ckArray[1]))+")");
					if(parseFloat(ckArray[0])==parseFloat(ckArray[1])){
						clearTimeout(t);
					}else
						t=setTimeout(controlname+".getUploadSize("+key+")",800);
				}else
					t=setTimeout(controlname+".getUploadSize("+key+")",800);
								
			},function(){
					alert('网络异常！');		 		
			})			
		
	},	
	//执行查询
	ajax:function(u,p,type,returnType,sfun,efun){
	     var browserName = navigator.appName,xhr;
	     if(browserName == "Microsoft Internet Explorer") {
	             xhr = new ActiveXObject("Microsoft.XMLHTTP");
	     } else {
	             xhr = new XMLHttpRequest();
	     }   
	     //初始化参数
	     var text_p='';
	     if(typeof(p)=="object"&&p!=null){
	         var keys=[];
	         for (var property in p)
	             if (property != "getKeys" && property != "getValues")
	                 keys.push(property); // 将每个属性压入到一个数组中
	         for(var _p in keys){
	             if(text_p.length>0)
	                 text_p+='&';
	             text_p+=keys[_p]+"="+p[keys[_p]];
	         }
	     }else
	         text_p=p;
	    // u+=(u.indexOf("?")==-1?"?":"&")+text_p;
	     xhr.open(type, u, true);           
	     xhr.setRequestHeader('If-Modified-Since', '0');  //清除缓存
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
}
/**
 * 执行提交
 */
function doFileUpload_ajax_zz(tobj) {
	tobj = typeof object == tobj ? tobj : eval(tobj);
	tobj.doUpload();
}
