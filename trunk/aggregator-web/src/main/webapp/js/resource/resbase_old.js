
	//指定教师
function appointTeacher(rdothis,showid,valueId,subid){	    	
	if(typeof(rdothis)=="undefined"||typeof(showid)=="undefined"||typeof(valueId)=="undefined"){
		return;
	} 
	$("#"+rdothis.name+"_all")[0].checked=true;
	$("#"+subid).hide();
	$("#"+valueId).hide();	
	if(rdothis.value==3){
		showModel(showid);
		var vaSpan=$("#"+valueId+" span");
		var h='';
		if(vaSpan.length>0){	    		
			vaSpan.each(function(ix,im){
				var val=$(im).children("input[type='hidden']").val();
				var text=$(im).children("font").html();
				h+='<option value="'+val+'">'+text+'</option>';
			})	    			
		}
		$("#sel_result").html(h);
		$("#valShowPath").val(valueId);
	}else if(rdothis.value==2){
		$("#"+subid).show('fast');
	}
}

//权限设置 联动     
function ckChild(ck,childname,idx){
	if(typeof(childname)!="undefined"&&$("input[name='"+childname+"']").length>0&&ck.checked)
		$("input[name='"+childname+"']")[idx].checked=ck.checked;
	else
		$("input[name='"+childname+"']").each(function(idx,itm){
			itm.checked=ck.checked;
		})
	if(ck.id=="ck_right_down_all"&&ck.checked){
		document.getElementById('ck_right_view_all').checked=ck.checked;
		ckChild(document.getElementById('ck_right_view_all'),"ck_right_view",idx);
	}
}
	
	/**
*点击树节点，加载教师
*/
function clickDTreeLoadUser(deptid){
	if(typeof(deptid)=="undefined"){
		alert('异常错误，原因：部门ID为空!');
		return;
	}
	//设置树节点颜色
		//清除重置全部的
	$(".dtree div a").css("color","black");
	$(".dtree div[id='"+deptid+"'] a:last").css("color","red");
	
	$.ajax({
		url:'deptuser?m=getListByProperty',
		data:{deptid:deptid},
		type:'post',
		dataType:'json',
		error:function(){
			alert('异常错误，网络异常!无法连接报务器!');
		},success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				var h='';
				if(rps.objList.length>0){
						//给文件框加检索
					$.each(rps.objList,function(idx,itm){								
						h+='<li';
						if(idx%2!=0)
							h+='  class="bg"';
						h+='><input type="hidden" value="'+itm.userid+'"/><img src="images/an31_130705.png" width="12" height="13" style="cursor:pointer" onclick="optsChange(\'sel_noresult\',\'sel_result\',this.parentNode)" /><span>'+itm.realname+'<span></li>';
					})				
				}
				$("#sel_noresult").html(h);
			}
		}
	});
}

	/**
* 更换Opts
*/
function optsChange(firstOptsId,lastOptsId,liObj){
	//var selOpts=$("#"+firstOptsId+" option:selected");
	var selOpts=$(liObj).children("input[type='hidden']");
	if(selOpts.length<1){
		return;
	}
	var opts=$("#"+lastOptsId+" li input[type='hidden']");
	var h='';
	selOpts.each(function(idx,itm){
		var flag=true;
		if(opts.length>0){
			opts.each(function(ix,im){
				if(im.value.Trim()==itm.value.Trim())
					flag=false;
			})
		}
		if(flag){
			h+='<li';
			if($("#"+lastOptsId+" li").length%2!=0)
				h+='  class="bg"';
			var realname=$(liObj).children("span").html();
			var img='<img src="images/an32_130705.png" width="12" height="13" style="cursor:pointer" onclick="optsChange(\'sel_result\',\'sel_noresult\',this.parentNode)" />';
			if(firstOptsId=="sel_result")
				img='<img src="images/an31_130705.png" width="12" height="13" style="cursor:pointer" onclick="optsChange(\'sel_noresult\',\'sel_result\',this.parentNode)" />';
			h+='><input type="hidden" value="'+itm.value+'"/>'+img+'<span>'+realname+'<span></li>';						
		}
	});
	//转移
	$("#"+lastOptsId).append(h);
	//清除
	$(liObj).remove();
}
/**
*指定教师码浮动层确定
*/
function appointTeacherSub(){
	var option=$("#sel_result li");
	var sadd=$("#valShowPath").val();
	var h='';
	if(option.length>0){
		option.each(function(idx,itm){
			var val=$(itm).children("input[type='hidden']").val().Trim();
			var text=$(itm).children("span").html().Trim();
			h+='<span id="sp_'+sadd+'_'+val+'"><font>'+text+'</font>';
			h+='<input type="hidden" value="'+val+'"/><a style="color:gray" href="javascript:delAppoint(\'sp_'+sadd+'_'+val+'\');">[X]</a></span>';
		});
	}
	$("#"+sadd).html(h);
	//关闭浮动层
	closeModel('dv_appointright');
	if(h.length>0){
		//showModel(sadd,false);
		$("#"+sadd).show('fast');
	}
}

function delAppoint(id){
	if($("#"+id).parent().children().length==1)
		$("#"+id).parent().hide();
	$("#"+id).remove();
}
function delEditDiv(cruuid){
	var fname=$("#dv_edit_"+cruuid+" input[name='fname']").val().Trim();
	if(confirm("您当前选择了删除‘"+fname+"’资源!\n\n您确认要删除吗?")){    		
	//	$("#dv_edit_"+cruuid+" + div[id*='dv_edit_']").show('fast');
		$("#dv_edit_"+cruuid).remove();
	}
	}

/**
*验证
*/
function validateDate(cruuid,idx,ttype){
	if(typeof(cruuid)=="undefined"||cruuid.Trim().length<1
			||typeof(idx)=="undefined"||isNaN(idx)){
		alert("异常错误，原因：未知!");
		return;
	}

	var path=$("#path").val();
	if(path.Trim().length<2){ //除去'/'
		alert("异常错误，原因：未知!");
		return;
	}
	
	var resourceObj=$("#resource_name"+cruuid);
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
	var keyword=$("#key_word"+cruuid);
	if(keyword.val().Trim().length<1){
		alert('资源关键字尚未输入，请输入!');
		keyword.focus();
		return;
	}
	var introduce=$("#introduce"+cruuid);
//	if(introduce.val().Trim().length<1){
//		alert('资源简介尚未输入，请输入!');
//		introduce.focus();
//		return;
//	}
	var extendsvalueid="",extendsfaterid="",positions="",rootextendid="";
	var extendvalue=eval("(lb_"+idx+".extendValueArray)");
	if(extendvalue.length>0){
		$.each(extendvalue,function(idx,itm){
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
	if(rootextendid.Trim().length<1){
			alert('异常错误，原因：rootextendid is empty!');return;
	}	
	
	var isyingyong=false;
	if(idx==0){
		isyingyong=$("#ck_yingyong")[0].checked;
	}
	if(typeof(ttype)!="undefined"||ttype==2){
		isyingyong=true;
	}
	var fname=$("#dv_edit_"+cruuid+" input[name='fname']").val();
	var fsize=$("#dv_edit_"+cruuid+" input[name='fsize']").val();
	if(fname==undefined||fname==null||fname.length<1){
		alert("异常错误，原因：fname is empty!");return;
	}
	if(fsize==undefined||fsize==null||fsize.length<1){
		alert("异常错误，原因：fsize is empty!");return;
	}
	var fullname=fname+'*'+fsize,resid=cruuid;
	if(isyingyong){
		fullname='',resid='';
		var fnameObj=$("input[name='fname']");
		var fsizeObj=$("input[name='fsize']");
		var uuidObj=$("input[name='uuid']");		
		if(uuidObj.length!=fnameObj.length||fnameObj.length!=fsizeObj.length){
			alert('异常错误，原因：未知!');	
		}
		$.each(fnameObj,function(ix,im){
			if(im.value.Trim().length>0
				&&typeof(fsizeObj[ix])!="undefined"){
				if(fullname.Trim().length>0)
					fullname+='|'; 
				fullname+=im.value.Trim()+'*'+fsizeObj[ix].value.Trim();
			}
		});
		$.each(uuidObj,function(idx,itm){
			if(resid.Trim().length>0)
				resid+='|';
			resid+=itm.value.Trim();
		});
	}
	if(fullname.length<1){
		alert('异常错误，原因：fname is empty!');return;
	}
	var param={resid:resid,resname:resourceObj.val().Trim()
			  ,reskeyword:keyword.val().Trim()
			  ,resintroduce:introduce.val().Trim()
			  ,extendsvalueid:extendsvalueid
			  ,positions:positions
			  ,extendsfater:extendsfaterid
			  ,rootextendidarr:rootextendid
			  ,filename:fullname
			  ,path:path
			};
			
	//权限信息   浏览权限
	if($("#ck_right_view"+cruuid+"_all")[0].checked){
		var rightviewroletypeObj=$("input[name='ck_right_view"+cruuid+"']:checked");
		param.rightviewroletype=rightviewroletypeObj.val();
		var rightviewroletype=rightviewroletypeObj.val();
		if(rightviewroletype==3){
			var hd=$("p[id='p_right_view"+cruuid+"_teacher'] input[type='hidden']");
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
			var selViewSubject=$("select[id='right_view"+cruuid+"_subject']").val();
			if(typeof(selViewSubject)=="undefined"||selViewSubject==null){
				alert('异常，系统没有发现科目!');
				$("select[id='right_view"+cruuid+"_subject']").focus();
				return;
			}
			param.subjectviewid=selViewSubject;
		}
	}
	//权限信息   浏览权限
	if($("#ck_right_down"+cruuid+"_all")[0].checked){
		var rightdownroletypeVal=$("input[name='ck_right_down"+cruuid+"']:checked");
		param.rightdownroletype=rightdownroletypeVal.val();		
		var rightdownroletype=rightdownroletypeVal.val();
		if(rightdownroletype==3){
			var hd=$("p[id='p_right_down"+cruuid+"_teacher'] input[type='hidden']");
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
			var selDownSubject=$("select[id='right_down"+cruuid+"_subject']").val();
			if(typeof(selDownSubject)=="undefined"||selDownSubject==null){
				alert('异常，系统没有发现科目!');
				$("select[id='right_down"+cruuid+"_subject']").focus();
				return;
			}
			param.subjectdownid=selDownSubject;
		}
	}	
	var msg='验证完毕，您确认上传该资源吗？';
	
	if(isyingyong&&$("input[name='uuid']").length>1)
		msg="验证完毕，您确认‘批量应用到下列文件’中吗?"
	if(!confirm(msg))
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
				
				//location.href='resource?m=tomyreslist';
				//自动关闭				
					/*if($("#p_img_"+cruuid).parent().next().length>0){
						alert("上传资源成功!");
						changeTableShow(cruuid,1);
						changeTableShow($("#p_img_"+cruuid).parent().next().children("input[name='uuid']").val().Trim(),2);
					}else{*/
						var msg='上传资源并应用到下列文件完毕，是否返回“我的资源”进行查看？';
						if(!isyingyong||$("input[name='uuid']").length<2)
							msg='上传资源完毕，是否返回“我的资源”进行查看？';
						if(confirm(msg)){
							location.href='resource?m=tomyreslist';
						}else{
							var firstUUid='';
							$("#edit_resource div input[name='uuid']").each(function(x,m){
								if(x==0)
									firstUUid=m.value;
								$("#introduce"+m.value).val(param.resintroduce);
								$("#key_word"+m.value).val(param.reskeyword);
								/*if(param.extendsfater.length>0){
									param.extendsfater.split("\\|")
								}*/
								//定义资源分类
								if(param.extendsvalueid.length>0){
									var evObj=param.extendsvalueid.split("|");
									if(evObj.length>0){
										$.each(evObj,function(z,zm){
											eval("("+$("li[id='value_item_"+zm+"lb_"+x+"'] a").attr("href").split(":")[1]+")");
										});
									}
								}
								$("#ck_right_view"+m.value+"_all").attr("checked",true);
								//浏览权限
								$("input[id='ck_right_view"+m.value+"_"+param.rightviewroletype+"']").attr("checked",true);
								if(param.rightviewroletype==2){
									/*rightdownroletype: "1"
										rightviewroletype: "2"
										*/
									$("#right_view"+m.value+"_subject option[value='"+param.subjectviewid+"']")[0].selected=true;
									$("#p_right_view"+m.value+"_subject").show();
								}else if(param.rightviewroletype==3){
									var hdidArray=$("#p_right_view"+firstUUid+"_teacher span input[type='hidden']");
									var hdhtm=$("#p_right_view"+firstUUid+"_teacher span font");
									$("#p_right_view"+m.value+"_teacher").html('');
									$.each(hdidArray,function(z,zm){
										var id=zm.value;
										var text=hdhtm[z].innerHTML;
										var htm='<span id="sp_p_right_view'+m.value+'_teacher_'+id+'"><font>'+text+'</font><input type="hidden" value="'+id+'">';
										htm+='<a style="color:gray" href="javascript:delAppoint(\'sp_p_right_view'+m.value+'_teacher_'+id+'\');">[X]</a></span>';
										$("p[id='p_right_view"+m.value+"_teacher']").append(htm);
									})
									$("p[id='p_right_view"+m.value+"_teacher']").show();
								}
								$("#ck_right_down"+m.value+"_all").attr("checked",true);
								//浏览权限
								$("input[id='ck_right_down"+m.value+"_"+param.rightdownroletype+"']").attr("checked",true);
								if(param.rightdownroletype==2){									
									$("#right_down"+m.value+"_subject option[value='"+param.subjectviewid+"']")[0].selected=true;
									$("#p_right_down"+m.value+"_subject").show();									
								}else if(param.rightdownroletype==3){
									var hdidArray=$("#p_right_down"+firstUUid+"_teacher span input[type='hidden']");
									var hdhtm=$("#p_right_down"+firstUUid+"_teacher span font");
									$("#p_right_down"+m.value+"_teacher").html('');
									$.each(hdidArray,function(z,zm){
										var id=zm.value;
										var text=hdhtm[z].innerHTML;
										var htm='<span id="sp_p_right_down'+m.value+'_teacher_'+id+'"><font>'+text+'</font><input type="hidden" value="'+id+'">';
										htm+='<a style="color:gray" href="javascript:delAppoint(\'sp_p_right_down'+m.value+'_teacher_'+id+'\');">[X]</a></span>';
										$("#p_right_down"+m.value+"_teacher").append(htm);										
									})
									$("#p_right_down"+m.value+"_teacher").show();
								}
								$("a[id='a_"+m.value+"']").show();
							});
							$("a[id='a_yyong']").hide();
							$("#img_del").hide();
						}
					//}				
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
function validateUpdateDate(resid,lbobj){	
	if(typeof(resid)=="undefined"||resid.Trim().length<1){
		var hdid=$("#hd_resid").val();
		resid=hdid;
	}
	var resourceObj=$("#resource_name"+resid);
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
	var keyword=$("#key_word"+resid);
	if(keyword.val().Trim().length<1){
		alert('资源关键字尚未输入，请输入!');
		keyword.focus();
		return;
	}
	var introduce=$("#introduce"+resid);

	var extendsvalueid="",extendsfaterid="",positions="",rootextendid="";
	
	var extendvalue={};
	if(typeof(lbobj)!="undefined")
		extendvalue=lbobj.extendValueArray;
	else
		extendvalue=lb.extendValueArray;
	if(extendvalue.length>0){
		$.each(extendvalue,function(idx,itm){
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
//	if(exvaluecount.Trim().length<1&&parseInt(exvaluecount)!=extendsvalueid.Trim().split("|").length){
//		alert('资源类别您还未全部选择，请选择!');return;
//	}   
	if(rootextendid.Trim().length<1){
		alert('异常错误，原因：rootextendid is empty!');return;
	}
//	if(typeof(uploadControl.fileAttribute)=="undefined"||uploadControl.fileAttribute.length<1){
//		alert('异常错误，原因：uploadControl.fileAttribute is empty!');return;
//	}
	var fname=$("#dv_edit_"+resid+" input[name='fname']").val();
	var fsize=$("#dv_edit_"+resid+" input[name='fsize']").val();
	fname+='*'+fsize;
	if(fname.length<2){
		alert('异常错误，原因：fname and fsize is empty!');return;
	}
	var path=$("#dv_edit_"+resid+" input[name='path']").val();
	if(path)
	var param={resid:resid,resname:resourceObj.val().Trim()
			  ,reskeyword:keyword.val().Trim()
			  ,resintroduce:introduce.val().Trim()
			  ,extendsvalueid:extendsvalueid
			  ,positions:positions
			  ,extendsfater:extendsfaterid
			  ,rootextendidarr:rootextendid	
			  ,filename:fname
			  ,path:path
			};
	
	//权限信息   浏览权限
	if($("#ck_right_view"+resid+"_all")[0].checked){
		var rightviewroletypeObj=$("input[name='ck_right_view"+resid+"']:checked");
		param.rightviewroletype=rightviewroletypeObj.val();
		var rightviewroletype=rightviewroletypeObj.val();
		if(rightviewroletype==3){
			var hd=$("p[id='p_right_view"+resid+"_teacher'] input[type='hidden']");
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
			var selViewSubject=$("select[id='right_view"+resid+"_subject']").val();
			if(typeof(selViewSubject)=="undefined"||selViewSubject==null){
				alert('异常，系统没有发现科目!');
				$("select[id='right_view"+resid+"_subject']").focus();
				return;
			}
			param.subjectviewid=selViewSubject;
		}
	}	
	//权限信息   浏览权限
	if($("#ck_right_down"+resid+"_all")[0].checked){
		var rightdownroletypeVal=$("input[name='ck_right_down"+resid+"']:checked");
		param.rightdownroletype=rightdownroletypeVal.val();		
		var rightdownroletype=rightdownroletypeVal.val();
		if(rightdownroletype==3){
			var hd=$("p[id='p_right_down"+resid+"_teacher'] input[type='hidden']");
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
			var selDownSubject=$("select[id='right_down"+resid+"_subject']").val();
			if(typeof(selDownSubject)=="undefined"||selDownSubject==null){
				alert('异常，系统没有发现科目!');
				$("select[id='right_down"+resid+"_subject']").focus();
				return;
			}
			param.subjectdownid=selDownSubject;
		}
	}	
	var msg="验证完毕，您确认上传该资源吗？";
	if(/\.(txt|wps|xls|xlsx|doc|docx|ppt|pptx)$/.test(param.resname.toLowerCase()))
		msg+="\n\n提示：txt,office文件(word,excel,ppt,wps等)上传后会进行转换,请耐心等候!"
	if(!confirm(msg))
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
			//	$("#hd_id").val(rps.objList[0]);
				if(confirm("修改资源成功!是否返回“我的资源”页面？"))
					location.href='resource?m=tomyreslist';
			}
		}
	});
}

    function doUpdateRes(resid){
        if(typeof(resid)=="undefined"||resid.Trim().length<1){
            alert("错误，无法提交！！");
            return ;
        }

        var resname=$("#resname").val();
        if(resname==null || resname.Trim().length<1){
            alert('资源名称尚未输入，请输入!');
            return;
        }

        var reskeyword=$("#reskeyword").val();
        if(reskeyword==null || reskeyword.Trim().length<1){
            alert('资源关键字尚未输入，请输入!');
            return;
        }

        var subject=$("#subject").val();
        if(subject==null || subject.Trim().length<1){
            alert('请选择学科!');
            return;
        }

        var grade=$("#grade").val();
        if(grade==null || grade.Trim().length<1){
            alert('请选择年级!');
            return;
        }

        var restype=$("#restype").val();
        if(restype==null || restype.Trim().length<1){
            alert('请选择资源类型!');
            return;
        }

        var filetype=$("#filetype").val();
        if(filetype==null || filetype.Trim().length<1){
            alert('请选择资源文件类型!');
            return;
        }

        var resintroduce=$("#resintroduce").val();
        var msg="确认修改吗？";
        if(!confirm(msg))
            return;
        $.ajax({
            url:'resource?m=doupdate',
            type:'POST',
            data:{
                resid:resid,
                resname:resname,
                reskeyword:reskeyword,
                subject:subject,
                grade:grade,
                restype:restype,
                filetype:filetype,
                resintroduce:resintroduce
            },
            dataType:'json',
            error:function(){alert("网络异常")},
            success:function(rps){
                if(rps.type=="success")
                    location.reload();
                else
                    alert(rps.msg);
            }
        });
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
	var commentobjectid=$("#objectid").val();
	var commentcontext=$("#commentcontext").val();
	var anonymous=0;	
	
	if(typeof(commenttype)=="undefined"||commenttype==null){
		alert('参数不足，无法提交评论！');return;
	}
	if(typeof(commentobjectid)=="undefined"||commentobjectid==null){
		alert('参数不足，无法提交评论！');return;
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
			commentcontext:commentcontext,
			anonymous:anonymous
			},
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				$("#commentcontext").val("");
				alert("评论成功！");
				pageGo('p1');
			}
		}
	});    		
}

function doSupportOrOppose(commentid,type){
	$.ajax({
		url:"commoncomment?m=supportOrOppose",
		type:'POST',
		data:{
			commentid:commentid,
			type:(type?1:0)
			},
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				if(type)
					$("#"+commentid+"_support").html(parseInt($("#"+commentid+"_support").html())+1);
				else
					$("#"+commentid+"_oppose").html(parseInt($("#"+commentid+"_oppose").html())+1);
			}
		}
	});    		
}

function addResourceScore(){
	var scoretype=$("#scoretype").val();
	var scoreobjectid=$("#objectid").val();
	var score=comment_star.value;
	
	if(typeof(scoretype)=="undefined"||scoretype==null){
		alert('参数不足，无法提交评论！');return;
	}
	if(typeof(scoreobjectid)=="undefined"||scoreobjectid==null){
		alert('参数不足，无法提交评论！');return;
	}
	$.ajax({
		url:"score?m=ajaxResScore",
		type:'POST',
		data:{
			scoretype:scoretype,
			scoreobjectid:scoreobjectid,
			score:score
			},
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			var html="";
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				for(var i=1;i<=5;i++){
					if(i<=score)
						html+='<img width="17" height="16" src="images/star04_130423.png">';
					else
						html+='<img width="17" height="16" src="images/star05_130423.png">';
				}
				html+='&nbsp;&nbsp;&nbsp;<font color="#ffffff"><b>'+score+'分</b></font>'
				$("#commentScore").html(html);
				alert("打分成功！");
			}
		}
	});    		
}
/**
 * 添加关链字
 * @param kword
 * @return
 */
function addKeyWord(kword,inputid){
		var inputVal=$("#"+inputid).val();
		if(inputVal.Trim().indexOf(","+kword)!=-1||inputVal.Trim().indexOf(kword+",")!=-1
				||(inputVal.Trim().length==kword.length&&kword==inputVal.Trim()))
			return;
		if(inputVal.Trim().length<1||inputVal.Trim()==",")
			inputVal=kword;
		else if(inputVal.Trim().length>0&&inputVal.substring(inputVal.length-1)==",")
			inputVal+=kword;
		else
			inputVal+=","+kword;
		$("#"+inputid).val(inputVal);
		
}
//////////////////////resource/detail.jsp   ---end;
