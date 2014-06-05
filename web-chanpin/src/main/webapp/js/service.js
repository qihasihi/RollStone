
/**
 * 加载功能服务列表
 * @param rps
 * @return
 */
function initList(rps){
	$.ajax({
		url:'service?m=ajaxlist',
		type:'post',
		dataType:'json',
		error:function(){alert("网络异常！")},
		success:function(rps){
			if(rps.type=='error'){
				alert(rps.msg);
				return;
			}else{
				d = new dTree('d');
				/******功能权限操作******/
				if(isAdd)
					d.add("0", "-1", "功能服务信息", "javascript:dTreeBindClick(0,'for_root','0')");	
				else{
					d.add("0", "-1", "功能服务信息");
				}
				if(rps.objList.length>0){
					$.each(rps.objList,function(idx,itm){
						/******功能权限操作******/
						if(isAdd || isUpdate || isDelete)
							d.add(itm.ref,itm.parentref,itm.servicename+"["+ itm.ref +"]","javascript:dTreeBindClick("+ itm.ref +",'for_node','"+ itm.servicename +"','"+ itm.enable +"')");
						else{
							d.add(itm.ref,itm.parentref,itm.servicename+"["+ itm.ref +"]");
						}
					});	
				}
				$("#_dtree").hide();
				$("#_dtree").html(d+"");
				$("#_dtree").show();
				setCssByEnable(rps.objList);
			}
		}
	});
}
/**
 * 节点样式
 * @param data
 * @return
 */
function setCssByEnable(data){
	if(typeof(data)=="undefined" || data.length<1){
		return;
	}
	$.each(data,function(idx,itm){
		if(itm.enable=="1")
			$("div[id="+ itm.ref +"]").children("a").css("color","gray");
	});	
}
/***********************DTREE**************************/

/**
 * 节点操作
 */
var showDivId = "";
function dTreeBindClick(ref,div,name,enable){
	if(typeof(ref)=="undefined" || isNaN(ref)){
		alert("数据异常错误！请刷新后重试！");
		return;
	}
	$("input[id='upd_servicename']").val(name);
	$("input[name='upd_enable']").filter(function(){
		return this.value.Trim() == enable 
	}).attr("checked",true);
	$("input[id='ref']").val(ref);
	var x = mousePostion.x;
	var y = mousePostion.y;
	if($.browser.msie && (parseInt($.browser.version)<= 7)){
		x+=parseInt(document.documentElement.scrollLeft);
		y+=parseInt(document.documentElement.scrollTop);
	}
	$("#"+div).css({"left":x+"px","top":y+"px"});
	showDivId = showDivId.replace(div+"!","");
	showDivId +=div+"!";
	$("#"+div).show("fast");
}
/**
 * 隐藏显示层
 * @return
 */
function bodyClick(){
	if(showDivId.length<1){
		return;
	}
	var divarr = showDivId.split("!");
	for(var i=0;i<divarr.length;i++){
		if(divarr[i].Trim().length>0){
			var dtmp = $("#"+divarr[i].Trim());
			if(dtmp.length>0 && dtmp.css("display")=="block"){
				var width = parseFloat(dtmp.css("width").replace("px",""));
				var height = parseFloat(dtmp.css("height").replace("px",""));
				var left = parseFloat(dtmp.css("left").replace("px",""));
				var top = parseFloat(dtmp.css("top").replace("px",""));
				if(mousePostion.x >(left+width) || mousePostion.x <left ||
						mousePostion.y >(top+height) || mousePostion.y < top){
					dtmp.hide();
				}
			}
		}
	}
}
function mouseoverLi(obj){
	$(obj).css({"background-color":"gray","cursor":"pointer"});
	$(obj).bind("mouseout",function(){
		$(this).css("background-color","");
	});
}
/**
 * 添加层
 * @param div
 * @param s_div
 * @return
 */
function to_addNode(div,s_div){
	showModel(s_div,false);	
}
/***********************DTREE**************************/
/**
 * 重置
 */
function reset(div){
	$("#"+div+" input[type='text']").val("");
}

/**
 * 显示DIV
 * @param div
 * @param ref
 * @return
 */
function toShowDIV(div,ref){
	if(typeof(ref)!="undefined" && !isNaN(ref)){
		$("#hidden_for_id").val(ref);
	}
	if(div=="upd"){
		$("#div_add").hide();
		$("#div_upd").show();
		$.ajax({
			url:'service?m=toupd&ref='+ref,
			type:'post',
			dataType:'json',
			error:function(){
				alert("网络异常！");
			},
			success:function(rps){
				if(rps.type=="error"){
					alert(rsp.msg);
					return;
				}
				if(rps.objList.length>0){
					$("#upd_servicename").val(rps.objList[0].servicename);
					$("input[name='upd_enable']").filter(function(){
						return this.value.Trim().length>0 && this.value==rps.objList[0].enable;
					}).attr("checked",true);
					$("#upd_ctime").html(rps.objList[0].ctimeString);
				}
			}
		});
	}else{
		$("#div_upd").hide();
		$("#div_add").show();
	}
}


/**
 * 添加
 * @return
 */
function doAdd(){
	var pref = $("input[id='ref']").val();
	if(typeof(pref)=="undefined"){
		alert("未获取到父节点标识,请刷新页面后重试！");
		return;
	}
	var servicename = $("#add_servicename").val();
	var enable = $("input[name='add_enable']").filter(function(){
		return this.value.Trim().length>0 && this.checked;
	}).val();
	if(servicename.Trim().length<1){
		alert("请您填写功能服务名称！");
		return;
	}
	if(enable.Trim().length<1){
		alert("请您选择功能服务状态！");
		return;
	}
	$.ajax({
		url:'service?m=ajaxsave',
		type:'post',
		data:{ 
			servicename : servicename,
			enable : enable,
			parentref : pref
		},
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
				return;
			}
			closeModel("div_add");
			reset("div_add");
			initList();
			alert(rps.msg);
		}
	});
}
/**
 * 删除
 * @param id
 * @return
 */
function doDel(){
	if(!confirm("确认删除该记录？\n\n提示：删除会连同子节点一同删除，请谨慎操作！")){
		return;
	}
	var id = $("input[id='ref']").val();
	if(typeof(id)=="undefined" || isNaN(id)){
		alert("未获取到功能服务标识！");
		return;
	}
	$.ajax({
		url:'service?m=ajaxdel&ref='+id,
		type:'post',		
		dataType:'json',
		error:function(){alert("网络异常！");},
		success:function(rps){
			if(rps.type=='error'){
				alert(rps.msg);
				return;
			}
			$("#for_node").hide();
			initList();
			alert(rps.msg);
		}
	});
}

/**
 * 修改
 * @return
 */
function doUpd(){
	var ref = $("#ref").val();
	if(typeof(ref)=="undefined" || isNaN(ref)){   
		alert("未获取到功能服务标识，请刷新后重试！");
		return;
	}
	var servicename = $("#upd_servicename").val();
	if(servicename.Trim().length<1){  
		alert("请您填写功能服务名称");
		return;
	}
	var enable = $("input[name='upd_enable']").filter(function(){
		return this.checked==true;
	}).val();
	if(enable.Trim().length<1){  
		alert("请您选择功能服务状态");
		return;
	}
	$.ajax({
		url:'service?m=modify&ref='+ref,  
		data:{
			servicename : servicename,
			enable : enable
		},
		type:'post',
		dataType:'json',
		error:function(){alert("网络异常!");},    
		success:function(rps){   
			if(rps.type=='error'){
				alert(rps.msg);
				return;
			}
			closeModel("div_upd");
			reset("div_upd");  
			initList();
			alert(rps.msg);
		}
	});
}
/**
 * 重置查询条件
 * @return
 */
function resetSel(){
	$("#sel_servicename").val("");
	$("input[name='sel_enable']").each(function(){
		$(this).attr("checked",false);
	})
}
/**
 * 批量操作：启用禁用
 * @param flag
 * @return
 */
function setEnable(flag){
	if(typeof(flag)=="undefined" || isNaN(flag)){
		alett("未获取到状态标识！");
		return;
	}
	var ckArray = $("input[name='ck_data']").filter(function(){
		return this.checked && this.value.Trim().length>0;
	});
	if(ckArray.length<1){
		alert("您没用选择数据，请先选择数据再进行操作！");
		return;
	}
	var refArr = "";
	$.each(ckArray,function(idx,itm){
		if(refArr.length>0)
			refArr+=",";
		refArr+=itm.value.Trim();
	});
	var msg = flag == "0" ? "启用" : "禁用"; 
	if(!confirm("您确认"+ msg +"该功能？")){
		return;
	}
	$.ajax({
		url:'service?m=modifyall',
		type:'post',
		data:{
			enable : flag,
			refArr : refArr
		},
		dataType:'json',
		error:function(){alert("网络异常！")},
		success:function(rps){
			if(rps.type=='error'){
				alert(rps.msg);
			}else{
				pageGo("p1");
				alert(rps.msg);
			}
		}
	});
}
/**
 * 全选
 * @param obj
 * @return
 */
function selAll(obj){
	var flag = $(obj).attr("checked");
	$("input[name='ck_data']").attr("checked",flag);
}
/**
 * 单选
 * @param obj
 * @return
 */
function selOne(obj){
	var ckLen = $("input[name='ck_data']");
	var ckedLen = $("#input[name='ck_data']").filter(function(){
		return this.checked = true && this.value.Trim().length>0;
	});
	$("#ck_all").attr("checked",ckLen.length==ckedLen.length);
}
