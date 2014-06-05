
//重新查询
/*function reSearchList(){
	$.ajax({
		url:'pageright?m=ajaxlist',
		type:'post',
		dataType:'json',
		error:function(){alert("网络异常！")},
		success:function(rps){
			if(rps.type=='error'){
				alert(rps.msg);
				return;
			}else{
				d = new dTree('d');
				d.add("0", "-1", "权限信息", "javascript:dTreeBindClick(0,'for_root')");
				if(rps.objList.length>0){
					$.each(rps.objList,function(idx,itm){
						var content =itm.pagerighttype==1?itm.pagename+"&nbsp;---&nbsp;"+itm.pagevalue:itm.pagename+"["+itm.pagerightid+"]";
						if(itm.pagerightparentid==0)
							content=itm.pagename; 
						if(isSelect || isUpdate || isAdd){
							d.add(itm.pagerightid,itm.pagerightparentid,content,"javascript:dTreeBindClick("+ itm.pagerightid +",'for_node','"+ itm.pagename +"','"+ itm.pagevalue +"','"+ itm.pagerighttype +"')");
						}else{ 
							d.add(itm.pagerightid,itm.pagerightparentid,content,"javascript:void(0)");
						}
					});
				}
				$("#_dtree").hide();
				$("#_dtree").html(d+"");
				$("#_dtree").show();  
			}
		}
	});
}
*/
//重置
function reset(div){
	$("#"+div+" input[type='text']").val("");
	$("#"+div+" textarea").val("");
}
//进入修改DIV
function toUpdatePageRight(dvid,id){
	if(typeof(id)=="undefined"||id==null){
		alert("异常错误，参数异常!");return;
	}
		$("#div_add").hide();
		$("#div_upd").show();
		$.ajax({
			url:'pageright?m=toupd',
			data:{ref:id},
			type:'post',
			dataType:'json',
			error:function(){
				alert("网络异常！");
			},
			success:function(rps){
				if(rps.type=="error"){
					alert(rps.msg);
					return;
				}
				if(rps.objList.length>0){
					$("#update_page_name").val(rps.objList[0].pagename);
					$("#update_page_value").val(rps.objList[0].pagevalue);
					$("#update_remark").val(rps.objList[0].remark);
					$("#update_columns_id").val(rps.objList[0].columnid);
					$("#update_id").val(rps.objList[0].ref);
					showModel(dvid);
				}
			}
		});	
}


//添加
function doAdd(){
	var ref= $("#ref");
	var pagename=$("#add_page_name");
	var pagevalue=$("#add_page_value");
	var remark=$("#add_remark").val().Trim();
	var columnid=$("#add_columns_id").val();
//	if(ref.val().Trim().length<1){
//		alert("系统未获取到您要添加的上级信息，请刷新后重试！");
//		return;
//	}
//	var pref =ref.val().Trim();
//	if(pref==-1){
//		pref="0";
//	}
	if(pagename.val().Trim().length<1){
		alert("请您填写权限名称！");
		pagename.focus();
		return;
	}
	if(pagevalue.val().Trim().length<1){
		alert("请您填写权限路径！");
		pagevalue.focus(); 
		return;
	}
	if(columnid.Trim().length<1){
		alert("您尚未选择功能栏目，请选择!");return;		
	}
	var param={
			pagename:pagename.val().Trim(),
			pagevalue:pagevalue.val().Trim(),			
			columnid:columnid
	};
	if(remark.length<1){
		param.remark=remark;
	}
	
//	if(pagetype.Trim().length<1){
//		alert("请您填选择权限类型！");
//		return;
//	}
	$.ajax({
		url:'pageright?m=ajaxsave',
		data:param,
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
				closeModel("div_add");
				reset("div_add");
				//reSearchList();
				pageGo('p1');
				alert(rps.msg);
			}
		}
	});
}

//删除
function doDel(id){
	if(!confirm("您确认删除该记录？\n\n提示：删除操作会影响到所有用户的权限，请谨慎操作！")){
		return;
	}
	if(typeof(id)=="undefined"||id==null){
		alert("系统未获取到删除所需标识，请刷新页面重试！");
		return;
	}
	$.ajax({
		url:'pageright?m=del&ref='+id,
		type:'post', 
		dataType:'json',
		error:function(){alert("网络异常！");},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg); 
				return;
			}
			pageGo('p1');
			alert(rps.msg);
		},
	});
}

//修改
function doUpd(){
	var ref = $("#update_id");
	var pagevalue = $("#update_page_value");
	var pagename = $("#update_page_name");
	var columnid=$("#update_columns_id");
	var remark=$("#update_remark").val().Trim();
	
	if(ref.val().Trim().length<1){
		alert("异常错误，系统未获取到权限标识，请刷新后重试！");
		return;
	}
	if(pagename.val().Trim().length<1){
		alert("请您填写权限名称！");
		pagename.focus();
		return;
	}
	if(pagevalue.val().Trim().length<1){
		alert("请您填写权限描述！");
		pagevalue.focus();
		return;
	}
	if(columnid.val().Trim().length<1){
		alert("请您选择栏目！");
		columnid.focus();
		return;
	}
	var param={
			ref:ref.val(),
			pagename:pagename.val().Trim(),
			pagevalue:pagevalue.val().Trim(),
			columnid:columnid.val().Trim()
	};
	if(remark.length>0)
		param.remark=remark;
	
	$.ajax({
		url:'pageright?m=modify',
		data:param,
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
			closeModel("div_upd");
			pageGo("p1");
			alert(rps.msg);
		}
	});
}

/**
 * pageright/list.jsp中查询前
 * @param tmpObj
 * @return
 */
function beforajaxList(tmpObj){
	var param={};
	
	var selColumnId=$("#sel_column_id").val();
	if(selColumnId.Trim().length>0)
		param.columnid=selColumnId.Trim();
	var selPageValue=$("#sel_pagevalue").val();
	if(selPageValue.Trim().length>0&&selPageValue.Trim()!="权限名称/路径值 进行查询!")
		param.pagenamevalue=selPageValue.Trim();
	tmpObj.setPostParams(param);
}

/**
 * pageright/list.jsp中查询后
 * @param p
 * @return
 */
function afterajaxList(rps){
	if(rps.type=="error"){
		alert(rps.msg);return;
	}else{
		var htm='';
		if(rps.objList.length>0){
			$.each(rps.objList,function(idx,itm){
				htm+='<tr';
				if(idx%2!=0)
					htm+=' class="trbg2"';
				htm+='>';
				htm+='<td>'+itm.pagename+'</td>';
				htm+='<td>'+itm.pagevalue+'</td>';
				htm+='<td>'+itm.columnname+'</td>';
				htm+='<td><a href="javascript:;" class="font-blue" onclick="toUpdatePageRight(\'div_upd\',\''+itm.ref+'\')">编辑</a>';
				htm+='|<a href="javascript:;" class="font-blue" onclick="doDel(\''+itm.ref+'\')">删除</a>';
				htm+='</td>';
				htm+='</tr>';
			});
			$("#datatbl").html(htm);
		}
		//翻页信息
		if (typeof (p1) != "undefined" && typeof (p1) == "object") {
			p1.setPagetotal(rps.presult.pageTotal);
			p1.setRectotal(rps.presult.recTotal);
			p1.setPageSize(rps.presult.pageSize);
			p1.setPageNo(rps.presult.pageNo);
			p1.Refresh();
		}
	}
}



