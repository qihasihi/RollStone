
function validatePagePoatParam(o){
	if(typeof(o)=="undefined"||o==null){
		return;
	}
	
	var param = new Object();
	param.state=0;
	var sitename = $("#sitename");
	if(sitename.val().Trim().length>0){
		param.sitename = sitename.val().Trim();
	}
	
	var sitecontain = $("#sitecontain");
	if(sitecontain.val().Trim().length>0){		
		param.sitecontain = parseInt(sitecontain.val().Trim());
	}
	
	var sitecontain2 = $("#sitecontain2");
	if(sitecontain2.val().Trim().length>0){		
		param.sitecontain2 = parseInt(sitecontain2.val().Trim());
	}
	o.setPostParams(param);
}

function siteListReturn(rps){	
	if(rps.type=="error"){
		alert(rps.msg);
		if(typeof(p1)!="undefined"&&typeof(p1)=="object"){
			p1.setPagetotal(1);
			p1.setRectotal(0);
			p1.Refresh();
			var shtml = '<tr><td align="center">暂时没有场地信息';
			shtml += '</td></tr>';
			$("#mainTbl").html(shtml);
		}
	}else{		
		var shtml = "";
		shtml+='<tr><th >名称';
		shtml+='</th><th>位置';
		shtml+='</th><th >容纳人数';
		shtml+='</th><th >创建时间';
		shtml+='</th><th >修改时间';
//		shtml+='</th><th>使用次数';
//		shtml+='</th><th>使用状态';
		shtml+='</th><th>操作</th></tr>';
		
		$.each(rps.objList,function(idx,item){
			if(idx%2==0){
				shtml+='<tr class="trbg2">';
			}else{
				shtml+='<tr>';
			}
			shtml+='<td ><a target="_blank" href="activitysite?m=todetail&ref='+item.ref+'">'+item.sitename+'</a></td>';
			shtml+='<td >'+item.siteaddress+'</td>';
			shtml+='<td >'+item.sitecontain+'</td>';
			shtml+='<td >'+item.ctimeString+'</td>';
			shtml+='<td >'+item.mtimeString+'</td>';
			shtml+='<td ><a href="javascript:;" onclick="toupdate('+item.ref+',\'uSiteDiv\')" class="font-lightblue">修改</a>&nbsp;&nbsp;<a href="javascript:;" onclick="doDelSite('+item.ref+')" class="font-lightblue">删除</a></td></tr>';
			 
		});
	}
	$("#mainTbl").hide();
	$("#mainTbl").html(shtml);
	$("#mainTbl").show('fast');
	//翻页信息
	if (typeof (p1) != "undefined" && typeof (p1) == "object") {
		p1.setPagetotal(rps.presult.pageTotal);
		p1.setRectotal(rps.presult.recTotal);
		p1.setPageSize(rps.presult.pageSize);
		p1.setPageNo(rps.presult.pageNo);	
		p1.Refresh();
	}
}

function showAddDiv(div){
	clearForm(document.addform);
	showModel(div,'fade',false);
}

function doSubmitAdd(frm){
	var sitename = $("#asitename");
	if(sitename.val().Trim().length<1){
		alert("请输入场地名称");
		sitename.focus();
		return;
	}
	var siteaddress = $("#siteaddress");
	if(siteaddress.val().Trim().length<1){
		alert("请输入场地位置");
		siteaddress.focus();
		return;
	}
	var sitecontain = $("#asitecontain");
	if(sitecontain.val().Trim().length<1){
		alert("请输入场地容纳人数");
		sitecontain.focus();
		return;
	}
	var reg=/^(0|([1-9]\d*))$/;
	if(!reg.test(sitecontain.val().Trim())){
		alert('您输入的场地容纳人数不是正整数，请重新输入!');
		sitecontain.select();
		sitecontain.focus();
		return;
	}
	var baseinfo = $("#baseinfo");
	if(baseinfo.val().Trim().length<1){
		alert("请输入场地容纳人数");
		baseinfo.focus();
		return;
	}
	$.ajax({
		url:'activitysite?m=ajaxsave',
		dataType:'json',
		type:'post',
		cache:false,
		data:{
			sitename:sitename.val().Trim(),
			siteaddress:siteaddress.val().Trim(),
			sitecontain:sitecontain.val().Trim(),
			baseinfo:baseinfo.val().Trim(),
	        returnType:'json'
		},
		error:function(){
			alert("异常错误!系统未响应!");
		},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				alert(rps.msg);
				pageGo('p1');
				clearForm(frm);
				closeModel('aSiteDiv');
			}
		}
	});
}

function doDelSite(ref){
	if(typeof(ref)=="undefined"||isNaN(ref)){
		alert('异常错误，未获取到主键');
		return;
	}
	if(!confirm('确认删除？')){
		return;
	}
	$.ajax({
		url:'activitysite?m=ajaxdel',
		dataType:'json',
		type:'post',
		cache:false,
		data:{
			ref:ref
		},
		error:function(){
			alert('异常错误!系统未响应!');
		},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				alert(rps.msg);
				pageGo('p1');
			}
		}
	});
}
function toupdate(ref,div){
	if(typeof(ref)=="undefined"||isNaN(ref)){
		alert('异常错误，未获取到主键');
		return;
	}
	$.ajax({
		url:'activitysite?m=toupdate',
		dataType:'json',
		type:'post',
		cache:false,
		data:{
			ref:ref,
			returnType:'json'
		},
		error:function(){
			alert('异常错误!系统未响应!');
		},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				if(rps.objList.length<1){
					alert('数据没有拿到，请稍后重试');
				}
				$.each(rps.objList,function(idx,item){
					$("#usitename").val(item.sitename);
					$("#usiteaddress").val(item.siteaddress);
					$("#usitecontain").val(item.sitecontain);
					if(typeof(item.baseinfo)=="undefined")
						$("#ubaseinfo").val('');
					else
						$("#ubaseinfo").val(item.baseinfo);
					$("#ref").val(item.ref);
				});
				if($("#"+div).css("display")!="block"){
					showModel(div,false);
				}
			}
		}
	});
}

function doSubmitUpdate(frm){
	var refid = $("#ref").val().Trim();
	if(refid==null||refid.Trim().length<1){
		alert('异常错误，系统未获取到场地主键');
		return;
	}
	var sitename = $("#usitename");
	if(sitename.val().Trim().length<1){
		alert("请输入场地名称");
		sitename.focus();
		return;
	}
	var siteaddress = $("#usiteaddress");
	if(siteaddress.val().Trim().length<1){
		alert("请输入场地位置");
		siteaddress.focus();
		return;
	}
	var sitecontain = $("#usitecontain");
	if(sitecontain.val().Trim().length<1){
		alert("请输入场地容纳人数");
		sitecontain.focus();
		return;
	}
	var reg=/^(0|([1-9]\d*))$/;
	if(!reg.test(sitecontain.val().Trim())){
		alert('您输入的场地容纳人数不是正整数，请重新输入!');
		sitecontain.select();
		sitecontain.focus();
		return;
	}
	var baseinfo = $("#ubaseinfo");
	if(baseinfo.val().Trim().length<1){
		alert("请输入场地容纳人数");
		baseinfo.focus();
		return;
	}
	$.ajax({
		url:'activitysite?m=doupdate',
		dataType:'json',
		type:'post',
		cache:false,
		data:{
			ref:refid,
			state:0,
			sitename:sitename.val().Trim(),
			siteaddress:siteaddress.val().Trim(),
			sitecontain:sitecontain.val().Trim(),
			baseinfo:baseinfo.val().Trim(),
	        returnType:'json'
		},
		error:function(){
			alert("异常错误!系统未响应!");
		},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				alert(rps.msg);
				pageGo('p1');
				//clearForm(frm);
				closeModel('uSiteDiv');
			}
		}
	});
}