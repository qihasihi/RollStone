//AJAX分页查询之前
function beforajaxList(p){
	var param = {};
	var dictionaryname = $("#sel_dictionaryname").val();
	if(dictionaryname.length>0){
		param.dictionaryname = dictionaryname;
	}
	p.setPostParams(param);
}  
//分页查询之后
function afterajaxList(rps){
	var html = '';
	if(rps.type=="error"){
		alert(rps.msg);
	}else{
		if(rps.objList.length>0){
			html+='<tr><th>显示的值</th><th>实际的值</th>';
			html+='<th>备注</th><th>区分类型</th>';
			html+='<th>操作</th></tr>';
			$.each(rps.objList,function(idx,itm){
				html+='<tr ';  
				if(idx%2==0)
					html+=' class="trbg2"';  
				html+='>';
				html+='<td>'+ itm.dictionaryname +'</td>';
				html+='<td>'+ itm.dictionaryvalue +'</td>';
				html+='<td>'+ itm.dictionarydescription +'</td>';
				html+='<td>'+ itm.dictionarytype +'</td>';
				/*************功能权限*******************/
				html+='<td>';
				//if(isUpdate){
					html+="<a class='font-lightblue' href="+"javascript:toShowDIv('upd','"+ itm.ref +"')"+">修改</a>&nbsp;&nbsp;";
				//}
				//if(isDelete){  
					html+="<a class='font-lightblue' href="+"javascript:doDel('"+ itm.ref +"')"+">删除</a></td>";
				//}
				html+='</td>';
				/*************功能权限*******************/
				html+='</tr>';
			});
			 
		}else{
			html+="<tr><th>暂无数据!</th></tr>"
		}
		p1.setPageSize(rps.presult.pageSize);  
		p1.setPageNo(rps.presult.pageNo);
		p1.setRectotal(rps.presult.recTotal);
		p1.setPagetotal(rps.presult.pageTotal);
		p1.Refresh();
	}
	$("#datatbl").html(html);	
}

//重置
function reset(div){
	$("#"+div+" input[type='text']").val("");
	$("#"+div+" textarea").val("");
}
//显示DIV
function toShowDIv(div,id){
	$("#hidden_for_id").val(id);
	if(div=="upd"){
		$("#div_add").hide();
		$("#div_upd").show();
		$.ajax({
			url:'dictionary?m=toupd&ref='+id,
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
					$("#upd_dictionary_name").val(rps.objList[0].dictionaryname);
					$("#upd_dictionary_value").val(rps.objList[0].dictionaryvalue);
					$("#upd_dictionary_type").val(rps.objList[0].dictionarytype);
					$("#upd_dictionary_description").val(rps.objList[0].dictionarydescription);
					showModel('div_upd');
				}
			}
		});
	}else{
		$("#div_upd").hide();
		$("#div_add").show(); 
	}
}
  

//添加字典
function doAdd(){
	var dictionaryname = $("#add_dictionary_name").val();
	if(dictionaryname.Trim().length<1){
		alert("请您填写要显示的值！");
		return;
	}
	var dictionaryvalue = $("#add_dictionary_value").val();
	if(dictionaryvalue.Trim().length<1){
		alert("请您填写实际的值！");
		return;
	}
	var dictionarytype = $("#add_dictionary_type").val();
	if(dictionarytype.Trim().length<1){
		alert("请您填写区分类型！");
		return;
	}
	var dictionarydescription = $("#add_dictionary_description").val();
	//var dictionaryremark = $("#add_dictionary_remark").val();
	$.ajax({
		url:'dictionary?m=ajaxsave',
		data:{
			dictionaryname: dictionaryname,
			dictionaryvalue : dictionaryvalue,
			dictionarytype : dictionarytype,
			dictionarydescription : dictionarydescription
		},
		type:'post',
		dataType:'json',
		error:function(){
			alert("网络异常!");
		},
		success:function(rps){
			if(rps.type=='error'){
				alert(rps.msg);
				return;
			}else{
				pageGo("p1");
				closeModel('div_add');
				alert(rps.msg); 
			}
		}
	});
}

//删除
function doDel(id){
	if(!confirm("确认删除？")){
		return;
	}
	if(typeof(id)=="undefined" || typeof(id)=="null" || typeof(id).length==0){
		alert("系统未获取到字典标识，请刷新页面重试！");
		return;
	}
	$.ajax({
		url:'dictionary?m=del&ref='+id,  
		type:'post',
		dataType:'json',
		error:function(){alert("网络异常！");},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
				return;
			}
			pageGo("p1");
			alert(rps.msg);
		},
	});
}

//修改
function doUpd(){
	var dictionaryid = $("#hidden_for_id").val();
	if(typeof(dictionaryid)=="undefined" || typeof(dictionaryid)=="null" || typeof(dictionaryid).length==0){
		alert("系统未获取到字典标识，请刷新后重试！");
		return;
	}
	var dictionaryname = $("#upd_dictionary_name").val();
	if(dictionaryname.Trim().length<1){
		alert("请您填写显示的值！");
		return;
	}
	var dictionaryvalue = $("#upd_dictionary_value").val();
	if(dictionaryvalue.Trim().length<1){
		alert("请您填写实际的值！");
		return;
	}
	var dictionarytype = $("#upd_dictionary_type").val();
	if(dictionarytype.Trim().length<1){
		alert("请您填写区分类型！");
		return;
	}  
	var dictionarydescription = $("#upd_dictionary_description").val();
	$.ajax({
		url:'dictionary?m=modify&ref='+dictionaryid,
		data:{
			dictionaryname : dictionaryname,
			dictionaryvalue : dictionaryvalue,
			dictionarytype : dictionarytype,
			dictionarydescription : dictionarydescription
		},
		type:'post',
		dataType:'json',
		error:function(){
			alert("网络异常！");
		},
		success:function(rps){
			if(rps.type=='error'){
				alert(rps.msg);
				return;
			}
			closeModel('div_upd');
			pageGo("p1");  
			alert(rps.msg);
		}
	});
}
