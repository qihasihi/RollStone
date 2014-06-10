function doAdd(){
	var atname = $("#atName").val();
	var begintime = $("#beginTime").val();
	var endtime = $("#endTime").val();
	var audiovisual = $("#audiovisual").val();
	var estimationnum = $("#estimationNum").val();
	var content = $("#content").val();
	if(estimationnum.Trim().length<1){
		estimationnum=0;
	}
	if(begintime.Trim().length<1){
		alert('您尚未选择开始时间，请选择!');
		//begintime.focus();
		return;
	}
	if(endtime.Trim().length<1){
		alert('您尚未选择结束时间，请选择!');
		//endtime.focus();
		return;
	}
	//判断开始时间是否小于现在的时间
	var btimeStr=begintime+":00";
	var nowD=new Date().toFullString();
	//如果btime> nowd则返回FALSE
	if(validateTwoDate(btimeStr, nowD)){
		alert('活动开始时间不能晚于现在的时间，请重新选择!');
		//btime.focus();
		return;
	}
	//如果开始时间大于结束时间
	var etimeStr=endtime+':00';
	if(validateTwoDate(etimeStr,btimeStr)){
		alert('活动结束时间不能早于或等于活动开始时间，请重新选择!');
		//etime.focus();
		return;
	}
	//获取已经选择的场地
	var sArray=$("input[type='hidden']").filter(function(){return this.name=='selSiteId'});
	//获取已经选择的场地容纳人数总和
	var siteContains=$("input[type='hidden']").filter(function(){return this.name=='selSiteNum'});
	var siteContain="";
	var siteContain2=0;
	$.each(siteContains,function(idx,itm){
		if(itm!=null&&itm.value.Trim().length>0){
			siteContain=itm.value.Trim();
			siteContain2 =parseInt(siteContain2)+parseInt(siteContain);
		}
	});	
	if(sArray.length<1){
		alert('您尚未选择场地信息，请选择!');
		return;
	}
	if(parseInt(siteContain2)<parseInt(estimationnum)){
		//alert('友情提示：您选择的场地容纳人数总和为'+siteContain+',不足于您的活动估计人数');
		if(!confirm('友情提示：您选择的场地容纳人数总和为'+siteContain2+',不足于您的活动估计人数\n\n您确认操作吗？'))
			return;
	}
	//开始组织参数
	var tmpSite="";
	$.each(sArray,function(idx,itm){
		if(itm!=null&&itm.value.Trim().length>0){
			if(tmpSite.Trim().length>0)
				tmpSite+="|";
			tmpSite+=itm.value.Trim();
		}
	});	
	if(tmpSite.Trim().length<1){
		alert('您尚未选择场地信息，请选择!');
		return;
	}
	var issignnum=0;	var tmpUid="";	
	var issign=$("#issign");
	if(issign.attr("checked")==false){
		//查看邀请人
		var selUserArray=$("input[type='hidden']").filter(function(){return this.name.Trim()=='selUserId'});
		if(selUserArray.length<1){
			alert('您尚未选择，邀请参加的人员，请操作！');
			return;
		}
	
		$.each(selUserArray,function(idx,itm){
			if(itm!=null&&itm.value.Trim().length>0){
				if(tmpUid.Trim().length>0)
					tmpUid+='|';
				tmpUid+=itm.value.Trim();
			}
		});
		if(tmpUid.length<1){
			alert('您尚未选择，邀请参加的人员，请操作！');
			return;
		}
		issignnum=1;
	}
	$ajax('activity?m=ajaxsave',
			{
		atname : atname,
		begintime : begintime,
		endtime : endtime,
		audiovisual : audiovisual,
		estimationnum : estimationnum,
		content : content,
		tmpsite : tmpSite,
		tmpuid : tmpUid,
		issign : issignnum
			},
			'post',
			'json',
			function(rps){
				if(rps.type=="error"){ 
					alert(rps.msg);
					return;
				}				
				alert(rps.msg);
				window.location.href='activity?m=list';
			},
			function(){
				alert('网络异常！');
			}
		);
}

function doUpd(){
	var ref = $("#ref").val();
	var atname = $("#atName").val();
	var begintime = $("#beginTime").val();
	var endtime = $("#endTime").val();
	var audiovisual = $("#audiovisual").val();
	var estimationnum = $("#estimationNum").val();
	var content = $("#content").val();
	//获取已经选择的场地
	var sArray=$("input[type='hidden']").filter(function(){return this.name=='selSiteId'});
	if(sArray.length<1){
		alert('您尚未选择场地信息，请选择!');
		return;
	}
	//开始组织参数
	var tmpSite="";
	$.each(sArray,function(idx,itm){
		if(itm!=null&&itm.value.Trim().length>0){
			if(tmpSite.Trim().length>0)
				tmpSite+="|";
			tmpSite+=itm.value.Trim();
		}
	});	
	if(tmpSite.Trim().length<1){
		alert('您尚未选择场地信息，请选择!');
		return;
	}
	var issignnum=0;	var tmpUid="";	
	var issign=$("#issign");
	if(issign.attr("checked")==false){
		//查看邀请人
		var selUserArray=$("input[type='hidden']").filter(function(){return this.name.Trim()=='selUserId'});
		if(selUserArray.length<1){
			alert('您尚未选择，邀请参加的人员，请操作！');
			return;
		}
	
		$.each(selUserArray,function(idx,itm){
			if(itm!=null&&itm.value.Trim().length>0){
				if(tmpUid.Trim().length>0)
					tmpUid+='|';
				tmpUid+=itm.value.Trim();
			}
		});

		if(tmpUid.length<1){
			alert('您尚未选择，邀请参加的人员，请操作！');
			return;
		}
		issignnum=1;
	}
	$ajax('activity?m=ajaxupd',
			{
		ref : ref,
		atname : atname,
		begintime : begintime,
		endtime : endtime,
		audiovisual : audiovisual,
		estimationnum : estimationnum,
		content : content,
		tmpsite : tmpSite,
		tmpuid : tmpUid,
		issign : issignnum
			},
			'post',
			'json',
			function(rps){
				if(rps.type=="error"){ 
					alert(rps.msg);
					return;
				}
				
				alert(rps.msg);
			},
			function(){
				alert('网络异常！');
			}
		);
}

/**
 * 添加活动，添加用户
 * @return
 */
function add_addUser(){
	var param="dialogWidth:1000px;dialogHeight:700px;status:no;location:no;";
	var returnVal=window.showModalDialog("roleuser?m=toSetRoleUser&sign=2",new Date().getTime()+"",param);
	if(returnVal==null||returnVal.Trim()=="null"){
		alert('您尚未选择用户或您未提交关闭!');
		return;
	}
	//放入掩藏层中.
	var tmpSHtml="";
	//对进行返回的用户进行处理
	$.each(returnVal.split(','),function(idx,itm){
		if(typeof(itm.split('|')[0])!="undefined"&&itm.split('|')[0]!=null){	
			var id=itm.split('|')[0];  
			var name=itm.split('|')[1];
			var tmpArray=$("input[type='hidden']").filter(function(){return (this.name=='seUserId'&&this.value.Trim()==id)});
			if(tmpArray.length<1){			
				tmpSHtml+="<b id='selUserId_"+id+"'>"+name+"[<a  href=\"javascript:delDemo('"+id+"','selUserId_')\">X</a>]";
				tmpSHtml+='<input type="hidden" value="'+id+'" name="selUserId"/>';
				tmpSHtml+='</b>';		
			}
		}
	});
	if($("#user_span").children().length<1){
		$("#user_span").html(tmpSHtml);
	}else{
		$("#user_span").append(tmpSHtml);		
	}
}

/**
 * 页面操作，删除已经选择的用户
 * @param ref
 * @return
 */
function delDemo(ref,biaoshi){
	if(typeof(ref)=="undefined"){
		alert('异常错误!系统尚未获取到您要删除的标识!请刷新页面后重新操作');
		return;
	}
	$("#"+biaoshi+ref).remove();
}
/**
 * 打开选择场地的层
 * @param sid
 * @param isdrop
 * @param isselectedId
 * @return
 */
function showSiteAddressModel(sid,isdrop){
	$("input[type='checkbox']").filter(function(){return this.name=='ck_child'}).attr("checked",false);
	var array=$("input[type='hidden']").filter(function(){return this.name=='selSiteId'});
	if(array.length>0){
		$.each(array,function(idx,item){
			var v = item.value.Trim();
			$("input[type='checkbox']").filter(function(){
				return (this.name=='ck_child'&&this.id=='ck_site_'+v.Trim());
			}).attr("checked",true);
		});
	}
	showModel(sid,isdrop);
}
/**
 * 选择完毕后，点击关闭
 * @param divid
 * @return
 */
function selectedSite(divid){
	
	var ckArray=$("input[type='checkbox']").filter(function(){
								return (this.name=='ck_child'&&this.checked==true);
							});
	//如果选择了
	var tmpSHtml="";
	var num="";
	if(ckArray.length>0){
		$.each(ckArray,function(idx,itm){
			//分割 保存格式 id +þ|ñ+ name
			var tmpArray=itm.value.Trim().split("þ|ñ");
			var id=tmpArray[0];
			var name=tmpArray[1].Trim();
			num=tmpArray[2].Trim(); 
			tmpSHtml+="<b id='selSiteAddress_"+id+"'>"+name+"[<a class='F_blue' href=\"javascript:delDemo("+id+",'selSiteAddress_')\">X</a>]";
			tmpSHtml+='<input type="hidden" value="'+id+'" name="selSiteId"/>';
			tmpSHtml+='<input type="hidden" value="'+num+'" name="selSiteNum"/>';
			tmpSHtml+='</b>';		
		});
	}	
	$("#address_span").html(tmpSHtml);
	closeModel(divid);
}

/**
 * 重置
 * @return
 */
function doReset(){
	clearForm(document.forms[0]);
	
	//清空选中场地
	$("#address_span").empty();
	//清空选中的邀请人
	$("#user_span").empty();
	//清空不选择人员 add by liyanqing 2012-01-29 11:06:24
	$("#issign").removeAttr("checked");
    //显示添加按钮 add by liyanqing 2012-01-29 11:06:24
	$("#addUser").show();
	//
	$("#atName").focus();
}

function ckLength(cObj,maxlength){
	if(cObj.value.Trim().length>maxlength){
		cObj.value=cObj.value.Trim().substring(0,maxlength);
	}
}

/**
 * 全选，发生改变的时候
 * @param ckObj
 * @param ckname
 * @return
 */
function ckAllCheckChange(ckObj,ckname){
	$("input[type='checkbox']").filter(function(){
		return this.name==ckname.Trim();
	}).attr("checked",ckObj.checked);;
}