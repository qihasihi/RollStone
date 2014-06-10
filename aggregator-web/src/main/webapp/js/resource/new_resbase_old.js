var LeibieControl;
if (LeibieControl == undefined) {
	LeibieControl = function(settings) {
		this.init(settings);
	}
	/**
	 * isnum:是否多选
	 */
	LeibieControl = function(settings,ismu) {
		if(typeof(settings)=="undefined"||settings==null)
			settings={};	
		settings.isMultiple=ismu;
		this.init(settings);
	}
	/**
	 * checkedval:要选中的节点,以逗号隔开,多个以|隔开。
	 */
	LeibieControl = function(settings,ismu,checkedval) {
		if(typeof(settings)=="undefined"||settings==null)
			settings={};	
		settings.checkedval=checkedval;
		settings.isMultiple=ismu;
		this.init(settings);
	}
};

LeibieControl.prototype={
			init:function(settings){	
				this.selectedValue=[];  //选中的				
				this.extendValueArray=new Array();	
				this.timerange="";	
				this.settings=settings;
				this.initSettings();				
				this.initPropertyArea();
			},			
			initSettings : function() {
				this.ensureDefault = function(settingName, defaultValue) {
					this.settings[settingName] = (this.settings[settingName] == undefined) ? defaultValue
							: this.settings[settingName];
				};
				this.ensureDefault("controlid", null); // * 链接地址
				this.ensureDefault("isMultiple",false); // 是否可以多选		
				this.ensureDefault("checkedval",""); // 是否可以多选		
				/*this.customSettings = this.settings.custom_settings;*/
				delete this.ensureDefault;
			},		
		//设置默认值
		initPropertyArea:function(){  
			//初始化属性			
			var paHtml="";
			paHtml+="<div id='selectedProperties' class='jxpt_ziyuan_zrss'><p><strong>已选择：</strong></p></div>";
			paHtml+="<div class='jxpt_ziyuan_zrss_option' id='extendMainDIV'></div>";
			$("#PropertyArea").html(paHtml);
			this.initSelectedArea(); 
			this.getExtendList("","extendMainDIV"); 
		},		
		initSelectedArea:function(){
			var controlobj=this;
			$.ajax({
				url:'extend?m=getExtendList',
				type:'POST',
				data:{dependextendvalueid:0},
				dataType:'json',
				error:function(){alert("网络异常")},
				success:function(rps){
					if(rps!=null&&rps.objList!=null){
						var trHtml='<ul>';
						$.each(rps.objList,function(idx,itm) {
							trHtml+='<li style="display:none;" id="et_'+itm.extendid+'_pro_selected"></li>';
							var obj=new Object();
							obj.rootid=itm.extendid;
							obj.values= new Array();
							obj.farthername="";
							obj.posisions="";
							controlobj.extendValueArray.push(obj);
							trHtml+='<li style="display:none;" id="et_timeRange_pro_selected"></li>';
						});
						trHtml+='</ul>';
						$("#selectedProperties").append(trHtml);
					}
				}
			});
		},
		getExtendList:function(extendValue,divId,rootId,fatherName,posisions,hideId){
			var controlobj=this;
			var params="";
			extendValue=String(extendValue);
			if(posisions==null||posisions==undefined){
				posisions="";
			}
			if(fatherName==null||fatherName==undefined){
				fatherName="";
			}
			if(extendValue==null||extendValue==undefined||extendValue.Trim()==""){
				extendValue=0;
			}else{
				posisions+=String(extendValue)+",";
			}
			params+="&dependextendvalueid="+extendValue;
			$.ajax({
				url:'extend?m=getExtendList'+params,
				type:'POST',
				data:{},
				dataType:'json',
				error:function(){alert("网络异常")},
				success:function(rps){
					if(rps!=null&&rps.objList!=null){
						if(rps.objList.length>0)
							$("#"+hideId).hide();
						var trHtml='<table border="0" cellpadding="0" cellspacing="0" class="public_tab1">';
						trHtml+='<colgroup><col class="w90"/><col class="w870"/></colgroup>';
						$.each(rps.objList,function(idx,itm) {
							trHtml+='<tr id="extend_'+itm.extendid+'"><th style="float:right">'+itm.extendname+'：</th>';
							trHtml+='<td id="ev_area_'+itm.extendid+'">&nbsp;</td></tr>';
						}); 
						trHtml+='<tr id="extend_timeRange"><th style="float:right">发布时间：</th><td id="ev_area_timeRange"><ul id="val_ls_timeRange">';
						trHtml+="<li id='value_item_day'><a href="+"javascript:"+controlobj.settings["controlid"]+".clickedProperty('day','timeRange','','今天','','timeRange',true)"+">今天</a></li>";
						trHtml+="<li id='value_item_week'><a href="+"javascript:"+controlobj.settings["controlid"]+".clickedProperty('week','timeRange','','一周内','','timeRange',true)"+">一周内</a></li>";
						trHtml+="<li id='value_item_halfMonth'><a href="+"javascript:"+controlobj.settings["controlid"]+".clickedProperty('halfMonth','timeRange','','15天以内','','timeRange',true)"+">15天以内</a></li>";
						trHtml+="<li id='value_item_month'><a href="+"javascript:"+controlobj.settings["controlid"]+".clickedProperty('month','timeRange','','一个月内','','timeRange',true)"+">一个月内</a></li>";
						trHtml+='</ul></td></tr>';
						trHtml+='<tr style="display:none;"><th>&nbsp;</th><td><a href="javascript:doSearch();" class="an_blue">搜索</a></td></tr>';
					    trHtml+='<tr><td colspan="2"></td></tr>';
						trHtml+='</table>';
						$("#"+divId).html(trHtml);
						var flag=true;
						if(rootId==null||rootId==undefined||rootId.Trim()=="")
							flag=false;
						$.each(rps.objList,function(idx,itm) {
							if(!flag)
								rootId=itm.extendid;
							controlobj.getExtendValueList(itm.extendid,rootId,fatherName,posisions);
						});						
					}
				}
			});
		
		}, 		
		getExtendValueList:function(extendid,rootId,fatherName,posisions,isMulCho){
			var controlobj=this;
			$.ajax({
				url:'extend?m=getextendvaluelist',
				type:'POST', 
				data:{extendid:extendid},
				dataType:'json',
				error:function(){alert("网络异常")},
				success:function(rps){
					var trHtml="";
					if(rps!=null&&rps.objList!=null&&rps.objList.length>0){
						if(isMulCho){
							$("#dp_area_"+extendid).html("");
							trHtml="<ul id='val_ls_"+extendid+"' style='float:left;'>";
							$.each(rps.objList,function(idx,itm) {
								trHtml+="<li id='value_item_"+itm.valueid+"'>";
								trHtml+="<input title='"+itm.valuename+"' name='ext_cb_"+extendid+"' type='checkbox' value='"+itm.valueid+"' />"+itm.valuename;
								trHtml+="</li>";
							});
							trHtml+="</ul>";
							trHtml+="<p class='p_b_10'>";
							trHtml+="<a href="+"javascript:"+controlobj.settings["controlid"]+".subMultipleChoice('"
							+extendid+"','"+rootId+"','"+fatherName+"','"+posisions+"');"+" class='an_blue_small'>确定</a>";
							trHtml+="<a href="+"javascript:"+controlobj.settings["controlid"]+".delPropertySelected('"+rootId+"','"+extendid+"','et_"+rootId+"_pro_selected')"
							+" class='an_blue_small'>取消</a></p>";
							
						}else{
							if(fatherName==null||fatherName==undefined||fatherName=="")
								fatherName="";
							else
								fatherName=fatherName+"→";
							trHtml="<ul id='val_ls_"+extendid+"'>";
							$.each(rps.objList,function(idx,itm) {
								trHtml+="<li id='value_item_"+itm.valueid+"'><a href="+"javascript:"+controlobj.settings["controlid"]+".clickedProperty('"
								+itm.valueid+"','"+extendid+"','"+fatherName+"','"+itm.valuename+"','"+posisions+"','"+rootId+"',false)"+">"+itm.valuename+"</a></li>";
							});
							
							if(controlobj.settings.isMultiple)
								trHtml+="<li class='more'><a id='mulcho_"+extendid+"' href="+"javascript:"+controlobj.settings["controlid"]+
								".getExtendValueList('"+extendid+"','"+rootId+"','"+fatherName+"','"+posisions+"',true);"+">+多选</a></li>";
							trHtml+="</ul>";
						}
					}
					if(isMulCho){
						$("#extend_"+extendid).attr("class","yellow");
					}
					$("#ev_area_"+extendid).html(trHtml);	
					
					if(rps!=null&&rps.objList!=null&&rps.objList.length>0){
						if(typeof(controlobj.settings.checkedval)!="undefined"&&controlobj.settings.checkedval.Trim().length>0){
							var ckvalArr=controlobj.settings.checkedval.split('|');
							if(ckvalArr.length>0){
								var flag=false;
								for(var i=0;i<ckvalArr.length;i++){
									var cktmpArr=ckvalArr[i].split(',');
									if(cktmpArr.length>0){										
										for(var j=0;j<cktmpArr.length;j++){
											if($("#value_item_"+cktmpArr[j]).attr("class")!="selected"){
												var hreftmp=$("#value_item_"+cktmpArr[j]+" a:last").attr("href");	
												if(hreftmp!=null){
													eval("("+hreftmp.replace("javascript:","")+")");
													controlobj.settings.checkedval=controlobj.settings.checkedval.replace(cktmpArr[j],"");
													flag=true;
												}
											}
										}
									} 
								}
								if(flag==false)
									controlobj.settings.checkedval="";
							}
						}			
					}
				}
			});		
		},


		creatPropertySelected:function(rootId,extendid,values,farthername,valuename,posisions,istime){
			var psHtml="";
			psHtml+=farthername+valuename+"<a href="+"javascript:"+this.settings["controlid"]+".delPropertySelected('"+rootId+"','"+extendid+"','et_"+rootId+"_pro_selected',"+istime+")"
			+"><img src='images/an33_130705.png' width='21' height='21'/></a>";
			$("#et_"+rootId+"_pro_selected").html(psHtml);
			$("#et_"+rootId+"_pro_selected").show();
			if(istime){
				this.timerange=values[0];
			}else{
				$.each(this.extendValueArray,function(idx,itm){
					if(itm.rootid==rootId){
						itm.values=values;
						itm.farthername=farthername;
						itm.posisions=posisions;
					}
				});
			}
			pageGo('p1');
		},		
		
		delPropertySelected:function(rootId,extendvalueid,ulId,istime){
			$("#"+ulId).html("");
			$("#"+ulId).hide();
			$("#dp_area_"+rootId).html("");
			$("#val_ls_"+rootId).children().attr("class","");
			if(istime){
				this.timerange="";
			}else{
				$.each(this.extendValueArray,function(idx,itm){
					if(itm.rootid==rootId){
						itm.values.splice(0,itm.values.length);
						itm.farthername="";
						itm.posisions="";
					}					
				});
				this.getExtendValueList(rootId,rootId,"","");
			}
			
			pageGo('p1');
		},		
		subMultipleChoice:function(extendid,rootId,fatherName,posisions){
			var values = new Array();
			var valuename = "";
			$.each($("input[type='checkbox'][name='ext_cb_"+extendid+"']:checked"),function(idx,itm){
				values.push($(this).val());
				valuename+="/"+$(this).attr("title");
			});
			this.creatPropertySelected(rootId,extendid,values,fatherName,valuename,posisions);
		},		
		clickedProperty:function(extendValue,extendid,farthername,valuename,posisions,rootId,istime){
			$("#mulcho_"+extendid).show();
			$("#val_ls_"+extendid).children("li").attr("class","");
			if(!istime)
				$("#val_ls_"+extendid).children("li:last").attr("class","more");
			
			$("#value_item_"+extendValue).attr("class","font-blue1");
			var values = new Array();
			values.push(extendValue);
			this.creatPropertySelected(rootId,extendid,values,farthername,valuename,posisions,istime);
			//this.getExtendList(extendValue,"dp_area_"+extendid,rootId,farthername+valuename,posisions,"mulcho_"+extendid);
		}		
};


/////////////////////////////////////////////////resource/add.jsp

/**
*验证
*/
function validateDate(){
	var hdid=$("#hd_id").val();
	if(hdid.Trim().length<1){
		alert("异常错误，原因：未知!");
		return;
	}
	var resourceObj=$("#resource_name");
	if(resourceObj.val().Trim().length<1){
		alert('资源名称尚未输入，请输入!');
		resourceObj.focus();
		return;
	}
/*	var selectedValue=lb.selectedValue;
	if(selectedValue.length<1){
		alert('资源类别尚未选择，请选择!');
		selectedValue.focus();
		return;
	}*/
	var keyword=$("#key_word");
	if(keyword.val().Trim().length<1){
		alert('资源关键字尚未输入，请输入!');
		keyword.focus();
		return;
	}
	var introduce=$("#introduce");
	if(introduce.val().Trim().length<1){
		alert('资源简介尚未输入，请输入!');
		introduce.focus();
		return;
	}
	var extendsvalueid="",extendsfaterid="",positions="",rootextendid="";
	var extendvalue=lb.extendValueArray;
	if(extendvalue.length>0){
		$.each(lb.extendValueArray,function(idx,itm){
			if(itm.values.length>0){
				$.each(itm.values,function(ix,im){
					if(extendsvalueid.Trim().length>0)
						extendsvalueid+="|";
					extendsvalueid+=im;
					//父节点名称
					if(extendsfaterid.Trim().length>0)	
						extendsfaterid+="|";
					extendsfaterid+=itm.farthername;
					//父节点集合  +  选中的底层结构
					if(positions.Trim().length>0)
						positions+="|";
					positions+=itm.posisions+im;
					
					if(rootextendid.Trim().length>0)	
						rootextendid+="|";
					rootextendid+=itm.rootid;
				})
			}
		}) 
	}
	if(extendsvalueid.Trim().length<1){
			alert('资源类别尚未选择，请选择!');return;
	}
//	if(exvaluecount.Trim().length>0&&parseInt(exvaluecount)!=extendsvalueid.Trim().split("|").length){
//		alert('资源类别您还未全部选择，请选择!');return;
//	}
	if(rootextendid.Trim().length<1||
			(exvaluecount.Trim().length>0&&parseInt(exvaluecount)!=rootextendid.Trim().split("|").length)){
			alert('异常错误，原因：rootextendid is empty!');return;
	}
	if(typeof(uploadControl.fileAttribute)=="undefined"||uploadControl.fileAttribute.length<1){
		alert('异常错误，原因：uploadControl.fileAttribute is empty!');return;
	}
	var fname='';
	$.each(uploadControl.fileAttribute,function(ix,im){
		if(typeof(im)!="undefined"&&im!=undefined&&im.filename.Trim().length>0){
			if(fname.Trim().length>0)
				fname+='|'; 
			fname+=im.filename.Trim()+'*'+im.size;
		}
	}) 
	if(fname.length<1){
		alert('异常错误，原因：fname is empty!');return;
	}
	var param={resid:hdid,resname:resourceObj.val().Trim()
			  ,reskeyword:keyword.val().Trim()
			  ,resintroduce:introduce.val().Trim()
			  ,extendsvalueid:extendsvalueid
			  ,positions:positions
			  ,extendsfater:extendsfaterid
			  ,rootextendidarr:rootextendid
			  ,filename:fname
			};
			
	
	//权限信息   浏览权限
	if($("#ck_right_view_all")[0].checked){
		var rightviewroletypeObj=$("input[name='ck_right_view']:checked");
		param.rightviewroletype=rightviewroletypeObj.val();
		var rightviewroletype=rightviewroletypeObj.val();
		if(rightviewroletype==3){
			var hd=$("p[id='p_right_view_teacher'] input[type='hidden']");
			var h='';
			hd.each(function(id,m){
				if(h.length>0)
					h+=',';
				h+=m.value.Trim();
			});
			if(h.Trim().length<1){
				alert('错误，浏览权限类型选择指定教师，系统没有获取到!');return;
			}
			param.rightviewuserid=h;
			if(typeof(param.rightviewroletype)=="undefined"){
				if(!confirm("提示：你尚未选择浏览权限，因此你提交后其它用户是无法搜索到!\n\n您确认吗?"))
					return;
			}
		}else if(rightviewroletype==2){
			var selViewSubject=$("select[id='right_view_subject']").val();
			if(typeof(selViewSubject)=="undefined"||selViewSubject==null){
				alert('异常，系统没有发现科目!');
				$("select[id='right_view_subject']").focus();
				return;
			}
			param.subjectviewid=selViewSubject;
		}
	}	
	//权限信息   浏览权限
	if($("#ck_right_down_all")[0].checked){
		var rightdownroletypeVal=$("input[name='ck_right_down']:checked");
		param.rightdownroletype=rightdownroletypeVal.val();		
		var rightdownroletype=rightdownroletypeVal.val();
		if(rightdownroletype==3){
			var hd=$("p[id='p_right_down_teacher'] input[type='hidden']");
			var h='';
			hd.each(function(id,m){
				if(h.length>0)
					h+=',';
				h+=m.value.Trim();
			});
			if(h.Trim().length<1){
				alert('错误，下载权限类型选择指定教师，系统没有获取到!');return;
			}
			param.rightdownuserid=h;
			if(typeof(param.rightdownroletype)=="undefined"){	
				if(!confirm("提示：你尚未选择下载权限，因此你提交后其它用户是无法对您的资源进行下载!\n\n您确认吗?"))
					return;
			}
		}else if(rightdownroletype==2){
			var selDownSubject=$("select[id='right_down_subject']").val();
			if(typeof(selDownSubject)=="undefined"||selDownSubject==null){
				alert('异常，系统没有发现科目!');
				$("select[id='right_down_subject']").focus();
				return;
			}
			param.subjectdownid=selDownSubject;
		}
	}	
	if(!confirm("验证完毕，您确认上传该资源吗？"))
		return;
	$.ajax({
		url:'resource?m=doadd',
		type:'POST',
		data:param,
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				//上传成功，更新UUID
				$("#hd_id").val(rps.objList[0]);
				alert("上传资源成功!");
			}
		}
	});
	//文件	
}
	
	
	///////////////////////////resource/add.jsp    ---end

////////////////////////////resource/update.jsp   ----begin
/**
*验证
*/
function validateUpdateDate(){
	var hdid=$("#hd_resid").val();
	if(hdid.Trim().length<1){
		alert("异常错误，原因：未知!");
		return;
	}
	var resourceObj=$("#resource_name");
	if(resourceObj.val().Trim().length<1){
		alert('资源名称尚未输入，请输入!');
		resourceObj.focus();
		return;
	}
/*	var selectedValue=lb.selectedValue;
	if(selectedValue.length<1){
		alert('资源类别尚未选择，请选择!');
		selectedValue.focus();
		return;
	}*/
	var keyword=$("#key_word");
	if(keyword.val().Trim().length<1){
		alert('资源关键字尚未输入，请输入!');
		keyword.focus();
		return;
	}
	var introduce=$("#introduce");
	if(introduce.val().Trim().length<1){
		alert('资源简介尚未输入，请输入!');
		introduce.focus();
		return;
	}
	var extendsvalueid="",extendsfaterid="",positions="",rootextendid="";
	var extendvalue=lb.extendValueArray;
	if(extendvalue.length>0){
		$.each(lb.extendValueArray,function(idx,itm){
			if(itm.values.length>0){
				$.each(itm.values,function(ix,im){
					if(extendsvalueid.Trim().length>0)
						extendsvalueid+="|";
					extendsvalueid+=im;
					//父节点名称
					if(extendsfaterid.Trim().length>0)	
						extendsfaterid+="|";					
						extendsfaterid+=itm.farthername;
					//父节点集合  +  选中的底层结构
					if(positions.Trim().length>0)	
						positions+="|";
					positions+=itm.posisions+im;
					
					if(rootextendid.Trim().length>0)	
						rootextendid+="|";
					rootextendid+=itm.rootid;
				})
			}
		}) 
	}
	if(extendsvalueid.Trim().length<1
		||extendsfaterid.Trim().length<1){
			alert('资源类别尚未选择，请选择!');return;
	}
//	if(exvaluecount.Trim().length<1&&parseInt(exvaluecount)!=extendsvalueid.Trim().split("|").length){
//		alert('资源类别您还未全部选择，请选择!');return;
//	}   
	if(rootextendid.Trim().length<1||
		(exvaluecount.Trim().length>0&&parseInt(exvaluecount)!=rootextendid.Trim().split("|").length)){
		alert('异常错误，原因：rootextendid is empty!');return;
	}
	if(typeof(uploadControl.fileAttribute)=="undefined"||uploadControl.fileAttribute.length<1){
		alert('异常错误，原因：uploadControl.fileAttribute is empty!');return;
	}
	var fname='';
	$.each(uploadControl.fileAttribute,function(ix,im){
		if(typeof(im)!="undefined"&&im!=undefined&&im.filename.Trim().length>0){
			if(fname.Trim().length>0)
				fname+='|'; 
			fname+=im.filename.Trim()+'*'+im.size;
		}
	})
	if(fname.length<1){
		alert('异常错误，原因：fname is empty!');return;
	}
	var param={resid:hdid,resname:resourceObj.val().Trim()
			  ,reskeyword:keyword.val().Trim()
			  ,resintroduce:introduce.val().Trim()
			  ,extendsvalueid:extendsvalueid
			  ,positions:positions
			  ,extendsfater:extendsfaterid
			  ,rootextendidarr:rootextendid	
			  ,filename:fname		
			};
			
	
	//权限信息   浏览权限
	if($("#ck_right_view_all")[0].checked){
		var rightviewroletypeObj=$("input[name='ck_right_view']:checked");
		param.rightviewroletype=rightviewroletypeObj.val();
		var rightviewroletype=rightviewroletypeObj.val();
		if(rightviewroletype==3){
			var hd=$("p[id='p_right_view_teacher'] input[type='hidden']");
			var h='';
			hd.each(function(id,m){
				if(h.length>0)
					h+=',';
				h+=m.value.Trim();
			});
			if(h.Trim().length<1){
				alert('错误，浏览权限类型选择指定教师，系统没有获取到!');return;
			}
			param.rightviewuserid=h;
			if(typeof(param.rightviewroletype)=="undefined"){
				if(!confirm("提示：你尚未选择浏览权限，因此你提交后其它用户是无法搜索到!\n\n您确认吗?"))
					return;
			}
		}else if(rightviewroletype==2){
			var selViewSubject=$("select[id='right_view_subject']").val();
			if(typeof(selViewSubject)=="undefined"||selViewSubject==null){
				alert('异常，系统没有发现科目!');
				$("select[id='right_view_subject']").focus();
				return;
			}
			param.subjectviewid=selViewSubject;
		}
	}	
	//权限信息   浏览权限
	if($("#ck_right_down_all")[0].checked){
		var rightdownroletypeVal=$("input[name='ck_right_down']:checked");
		param.rightdownroletype=rightdownroletypeVal.val();		
		var rightdownroletype=rightdownroletypeVal.val();
		if(rightdownroletype==3){
			var hd=$("p[id='p_right_down_teacher'] input[type='hidden']");
			var h='';
			hd.each(function(id,m){
				if(h.length>0)
					h+=',';
				h+=m.value.Trim();
			});
			if(h.Trim().length<1){
				alert('错误，下载权限类型选择指定教师，系统没有获取到!');return;
			}
			param.rightdownuserid=h;
			if(typeof(param.rightdownroletype)=="undefined"){	
				if(!confirm("提示：你尚未选择下载权限，因此你提交后其它用户是无法对您的资源进行下载!\n\n您确认吗?"))
					return;
			}
		}else if(rightdownroletype==2){
			var selDownSubject=$("select[id='right_down_subject']").val();
			if(typeof(selDownSubject)=="undefined"||selDownSubject==null){
				alert('异常，系统没有发现科目!');
				$("select[id='right_down_subject']").focus();
				return;
			}
			param.subjectdownid=selDownSubject;
		}
	}	
	if(!confirm("验证完毕，您确认上传该资源吗？"))
		return;
	$.ajax({
		url:'resource?m=doupdate',
		type:'POST',
		data:param,
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				//上传成功，更新UUID
				$("#hd_id").val(rps.objList[0]);
				alert("修改资源成功!");
			}
		}
	});
	//文件
}
////////////////////////resource/update.jsp ---end;

///////////////////////resource/detail.jsp  ---begin;
/**
 *添加收藏
 *@params type: 类型  1:添加收藏  2:取消收藏
 *@params resid: 资源ID
 *@params dvid:显示的地址ID
 */
function operateStore(type,resid,dvid){
	if(typeof(type)=="undefined"||type==null){
		alert('异常错误，请刷新页面后重试!原因：type is empty!');return;
	}
	if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
		alert("异常错误，请刷新页面后重试! 原因：resid is empty!");return;
	}
	var u='store?m='; 
	if(type==1)
		u+='doadd';
	else
		u+='dodelete';
	$.ajax({
		url:u,
		type:'POST',
		data:{resid:resid},
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				var htm='';
				if(type==1){
					htm+='<a href="javascript:;" onclick="operateStore(2,\''+resid+'\',\'dv_store\')">';
					htm+='取消收藏';
					htm+='</a>';
				}else{
					htm+='<a href="javascript:;" onclick="operateStore(1,\''+resid+'\',\'dv_store\')">';
					htm+='收藏';
					htm+='</a>';
				}
				$("#"+dvid).html(htm); 
			}
		}
	});    		
}

function addResourceComment(){
	var commenttype=$("#commenttype").val();
	var commentobjectid=$("#commentobjectid").val();
	var score=comment_star.value;
	var commentcontext=$("#commentcontext").val();
	var anonymous=0;	
	
	if(typeof(commenttype)=="undefined"||commenttype==null){
		alert('参数不足，无法提交评论！');return;
	}
	if(typeof(commentobjectid)=="undefined"||commentobjectid==null){
		alert('参数不足，无法提交评论！');return;
	}
	if(typeof(score)=="undefined"||score==null){
		alert('请打分，否则无法提交评论！');return;
	}
	if(typeof(commentcontext)=="undefined"||commentcontext==null||commentcontext.Trim().length<1){
		alert('请输入评论内容，否则无法提交评论！');return;
	}
	var anonymous=0;	
	if($("#anonymous").attr("checked"))
		anonymous=1;
	$.ajax({
		url:"commoncomment?m=ajaxsave",
		type:'POST',
		data:{
			commenttype:commenttype,
			commentobjectid:commentobjectid,
			score:score,
			commentcontext:commentcontext,
			anonymous:anonymous
			},
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				var htm='';
				htm+='<tr><td align="center">您已经进行过评论！</td></tr>';
				$("#newCommentTLB").html(htm); 
			}
		}
	});    		
}

//////////////////////resource/detail.jsp   ---end;
