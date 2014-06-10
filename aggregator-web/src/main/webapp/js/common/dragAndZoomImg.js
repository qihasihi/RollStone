/**
 * 切图JS工具类
 * AUTHOD: zhengzhou
 * CREATE: 2012-03-27 10:34 
 */


var dragAndZoomImg;
if (dragAndZoomImg == undefined) {
	dragAndZoomImg = function(settings) {
		this.init(settings);
	}
}

/**
 * 定义属性 动作
 */
dragAndZoomImg.prototype = {
	getId : function(id) {
		return "string" == typeof id ? document.getElementById(id) : id;
	},
	getName : function(n) {
		return "string" == typeof n ? document.getElementById(n) : n;
	},
	init : function(settings) {
		this.customSettings = {};
		this.settings = settings;
		this.initSettings();
		// 初始化控件	
		this.initGender(); 
		// 初始化移动
		this.initDrag();
		// 初始化放大镜
		this.initZoom();
	},
	initSettings : function() {
		this.ensureDefault = function(settingName, defaultValue) {
			this.settings[settingName] = (this.settings[settingName] == undefined) ? defaultValue
					: this.settings[settingName];
		};
		this.ensureDefault("_imageurl", ""); // * 图片的链接
		this.ensureDefault("_genderid", "");// 生成的位置
		this.ensureDefault("_imagecontrol", ""); // * 图片的ID
		this.ensureDefault("_control", ""); // * _control的ID
		this.ensureDefault("_subfun", ""); // * 点击保存头像显示的数据
		// * 切割的图片大小
		this.ensureDefault("_qiege_width", "120px");
		this.ensureDefault("_qiege_height", "120px");

		this.ensureDefault("_freeimgwidth", "0px"); // 原始图片宽度
		this.ensureDefault("_freeimgheight", "0px");// 原始图片高度
		this.ensureDefault("_control", ""); // * 如果不生成 _control的ID
		this.ensureDefault("_fd_img_url", [ "img/+h.gif", "img/+c.gif" ]); // *
																			// 放大(+)图片地址
		this.ensureDefault("_sx_img_url", [ "img/_h.gif", "img/_c.gif" ]); // *
																			// - 图片地址
	},
	genderHtml : function() {
		var tblid = "tbl_" + new Date().getTime() + Math.random() * 1000;
		var imgid = "img_" + new Date().getTime() + Math.random() * 1000;
		var bigImageId = "big_img_" + new Date().getTime() + Math.random()	* 1000;
		var smlImageId = "sm_img_" + new Date().getTime() + Math.random()	* 1000;
		var subBtnId = "btn_" + new Date().getTime() + Math.random()	* 1000;
		this.settings._imagecontrol = imgid;
		this.settings._control = tblid;
		this.settings._bigImageId = bigImageId;
		this.settings._smlImageId = smlImageId;
		this.settings._subbtnId=subBtnId;
		if((this.settings._qiege_width+"").indexOf("px")==-1)
			this.settings._qiege_width=this.settings._qiege_width+"px";
		if((this.settings._qiege_height+"").indexOf("px")==-1)
			this.settings._qiege_height=this.settings._qiege_height+"px";
		if((this.settings._freeimgwidt+"").indexOf("px")==-1)
			this.settings._freeimgwidt=this.settings._freeimgwidt+"px";
		if((this.settings._freeimgheight+"").indexOf("px")==-1)
			this.settings._freeimgheight=this.settings._freeimgheight+"px";
		var divHeight=73*2+parseFloat(this.settings._qiege_height);
		var divWidth=82*2+parseFloat(this.settings._qiege_width);
		var inHtml = '<div style="width: '+divWidth+'px;border: 2px solid #888888;cursor:  ';
		inHtml += 'pointer;height:'+divHeight+'px;	margin-left: 4px;overflow: hidden;position: relative;">';
		inHtml += '<table  style="-moz-user-select: none;top: 0;left: 0;width: '+divWidth+'px; opacity: 0.75;position: relative;z-index: 7;"';
		inHtml += 'id="' + this.settings._control + '" border="0" cellpadding="0" cellspacing="0" >';
		inHtml += '<tbody><tr>';
		inHtml += '<td style="background-color: #CCCCCC;; height: 73px;" colspan="3"></td></tr>';
		inHtml += '<tr><td style="width: 82px; background-color: #CCCCCC;"></td>';
		inHtml += '<td style="width: '
				+ this.settings._qiege_width 
				+ '; height: '
				+ this.settings._qiege_height 
				+ '; border-width: 1px; border-style: solid;background-color:white;opacity: 0.01;"></td>';
		inHtml += '<td style="width: 82px; background-color: #CCCCCC;"></td></tr>';
		inHtml += '<tr><td style="height: 73px; background-color: #CCCCCC;" colspan="3"></td></tr>';
		inHtml += '</tbody></table>';
		divHeight="-"+divHeight;
		inHtml += '<div style="position: relative;  left: 0px; top: '+divHeight+'px;">';
		inHtml += '<img id="' + this.settings._imagecontrol + '" src="'
				+ this.settings._imageurl + '"  style="width:'
				+ this.settings._freeimgwidth + ';height:' 
				+ this.settings._freeimgheight
				+ ';border-width: 0px; position: relative;">';
		inHtml += '</div></div>';
		inHtml += '<div style="width:284px;" align="center">';
		inHtml += '<img id="' + this.settings._bigImageId + '" alt="放大" src="'
				+ this.settings._fd_img_url[0] + '">&nbsp;&nbsp;&nbsp;&nbsp;';
		inHtml += '<img id="' + this.settings._smlImageId + '" alt="缩小" src="'
				+ this.settings._sx_img_url[0] + '">&nbsp;&nbsp;&nbsp;&nbsp;';
		inHtml += '</div>';
		inHtml+='<div align="center" style="width: 284px"><input type="button" id="'+subBtnId+'" value="保存头像"/></div>';
		this.getId(this.settings._genderid).innerHTML = inHtml;
		
	},
	initGender : function() {
	
		this.genderHtml();
		
		var bigobj = this.getId(this.settings._bigImageId);
		var smlobj = this.getId(this.settings._smlImageId);
		var fsSrcArray = this.settings._fd_img_url;
		var sxSrcArray = this.settings._sx_img_url;
		
		var imgid=this.settings._imagecontrol;
		//生成图片的宽高
		var genderSize={width:this.settings._qiege_width,height:this.settings._qiege_height};
		var fun=this.settings._subfun;		//返回的方法
		var imgurl=this.settings._imageurl;//图片的地址
		function doGenderSmailImg(e){
			var imgObj=document.getElementById(imgid);
			// image's top and lfet; 
			var left=82-parseFloat(imgObj.style.left||0);
			var top=73-parseFloat(imgObj.style.top||0)
			var imgSize={width:parseInt(imgObj.style.width||0),height:parseInt(imgObj.style.height||0)}
			var imgPosition={left:left,top:top};
			eval("("+fun+"(imgSize,imgPosition,genderSize,imgurl))"); //执行返回参数
		}
		
		if (window.addEventListener) 
			this.getId(this.settings._subbtnId).addEventListener("click",doGenderSmailImg,false);
		else
			this.getId(this.settings._subbtnId).attachEvent("onclick",doGenderSmailImg);
		
		
		
		
		/**
		 * 鼠标移入事件
		 * 
		 * @param e
		 * @return
		 */
		function FandajmouseOver(e) {
			var obj = e.currentTarget;
			obj.freeImgUrl = obj.src;
			if (obj.id.indexOf("big_img_") != -1)
				obj.src = fsSrcArray[1];
			else if (obj.id.indexOf("sm_img_") != -1)
				obj.src = sxSrcArray[1];
		}
		/**
		 * 鼠标移出
		 * 
		 * @param e
		 * @return
		 */
		function FandajmouseOut(e) {
			var obj = e.currentTarget;
			obj.src = obj.freeImgUrl;
		}
		if (window.addEventListener) {
			bigobj.addEventListener('mouseover', FandajmouseOver, false);
			bigobj.addEventListener('mouseout', FandajmouseOut, false);

			smlobj.addEventListener('mouseover', FandajmouseOver, false);
			smlobj.addEventListener('mouseout', FandajmouseOut, false);
			// IE 的事件代码 在原先事件上添加 add 方法
		} else {
			bigobj.attachEvent('onmouseover', FandajmouseOver);
			bigobj.attachEvent('onmouseout', FandajmouseOut);

			smlobj.attachEvent('onmouseover', FandajmouseOver);
			smlobj.attachEvent('onmouseout', FandajmouseOut);
		}

	},
	/**
	 * 放大缩小
	 * 
	 * @return
	 */
	initZoom : function() {
		var bigobj = this.getId(this.settings._bigImageId);
		var smlobj = this.getId(this.settings._smlImageId);
		var controlSize={width:this.settings._qiege_width,height:this.settings._qiege_height};
		var imgId = this.settings._imagecontrol;
		function zoomClick(e) {

			var imgObj = document.getElementById(imgId);
			var currentPostion = {
				left : imgObj.style.left,
				top : imgObj.style.top
			};
			var obj = e.currentTarget;
			if (obj.id.indexOf("big_img_") != -1) {
				// 放大
				zoom = 0.05;
				imgObj.style.width = parseFloat(imgObj.style.width)
						+ parseFloat(imgObj.style.width) * zoom+"px";
				imgObj.style.height = parseFloat(imgObj.style.height)
						+ parseFloat(imgObj.style.height) * zoom+"px";
			} else if (obj.id.indexOf("sm_img_") != -1) {
				// 缩小
				zoom = 0.05;
				imgObj.style.width = parseFloat(imgObj.style.width)
						- parseFloat(imgObj.style.width) * (zoom)+"px";
				imgObj.style.height = parseFloat(imgObj.style.height)
						- parseFloat(imgObj.style.height) * (zoom)+"px";
				// imgObj.style.
			}
			// imgObj.style.left=currentPostion[0];
			// imgObj.style.top=currentPostion[1];
		}

		if (window.addEventListener) {
			bigobj.addEventListener('click', zoomClick, false);
			smlobj.addEventListener('click', zoomClick, false);
		} else {
			bigobj.addEventListener('onclick', zoomClick, false);
			smlobj.addEventListener('click', zoomClick, false);
		}
	},
	initDrag : function() {
		if (typeof this.settings._imageurl == "string") {
			var imagecontrol = this.settings._imagecontrol;
			if (this.getId(this.settings._imagecontrol)
					&& this.getId(this.settings._control)) {
				var isflag = false, isfirst = true, _mpost = null;
				function mouseClickControl(ev) {

					if (ev.pageX || ev.pageY)
						_mpost = {
							x : ev.pageX,
							y : ev.pageY
						};
					else
						_mpost = {
							x : ev.clientX + document.body.scrollLeft
									- document.body.clientLeft,
							y : ev.clientY + document.body.scrollTop
									- document.body.clientTop
						};
					isflag = true;
				}
				var tblPosition = {
					left : this.getId(this.settings._control).style.left,
					top : this.getId(this.settings._control).style.top
				}
				var freetop, client = {
					width : this.settings._qiege_width || 0,
					height : this.settings._qiege_height || 0
				};
				function mouseMoveControl(ev) {
					if (isflag) {
						if (ev.pageX || ev.pageY)
							mPostion = {
								x : ev.pageX,
								y : ev.pageY
							};
						else
							mPostion = {
								x : ev.clientX + document.body.scrollLeft
										- document.body.clientLeft,
								y : ev.clientY + document.body.scrollTop
										- document.body.clientTop
							};

						var left = parseFloat(mPostion.x)
								- parseFloat(_mpost.x);
						var top = parseFloat(mPostion.y) - parseFloat(_mpost.y);
						var image = document.getElementById(imagecontrol);
						left = parseInt(image.style.left || 0) + left / 5;
						top = parseInt(image.style.top || 0) + top / 5;
//						if (left < parseFloat(tblPosition.left) + 79
//								&& left > -(parseFloat(image.style.width || 0) - (82 + parseFloat(client.width || 0)))) {
							// 存在问题
							image.style.left = left + "px";
//						}
//						if (top < 82
//								&& top > -(parseFloat(image.style.height) - 73 - parseFloat(client.height)))
							image.style.top = top + "px";
					}
				}
				function mouseUp(e) {
					isflag = false;
					isfirst = true;
				}
				var obj = this.getId(this.settings._control);
				var imgObj=this.getId(this.settings._imagecontrol);
				if (window.addEventListener){
					obj.addEventListener('mousedown', mouseClickControl, false);
					//imgObj.addEventListener('mousedown', mouseClickControl, false);
				// IE 的事件代码 在原先事件上添加 add 方法
				}else{
					obj.attachEvent('onmousedown', mouseClickControl);
					//imgObj.addEventListener('mousedown', mouseClickControl, false);
				}
				
				
				document.onmousemove = mouseMoveControl;
				document.onmouseup = mouseUp;

			}
		}
	}
}
