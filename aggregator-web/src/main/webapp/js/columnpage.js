
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
	var sel_colrightname=$("#sel_columnrightname").val();
	if(sel_colrightname.Trim().length>0&&sel_colrightname.Trim()!="栏目权限名称 进行查询!")
		param.columnrightname=sel_colrightname.Trim();
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
				htm+='<td>'+itm.columnrightid+'</td>';
				htm+='<td><a href="columnright?m=detail&ref='+itm.ref+'" class="font-blue">'+itm.columnrightname+'</a></td>';
				htm+='<td>'+itm.columnname+'</td>';
				htm+='<td><a href="javascript:;" class="font-blue" onclick="doDel(\''+itm.ref+'\')">删除</a>';
				/*
				 <a href="javascript:;" class="font-blue" onclick="void(\'div_upd\',\''+itm.ref+'\')">编辑</a>
				 <a href="javascript:;" class="font-blue" onclick="toUpdatePageRight(\'div_upd\',\''+itm.ref+'\')">编辑</a>
				 * */
				htm+='</td>';
				htm+='</tr>';
			});			
		}
		$("#datatbl").html(htm);
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

/**
 * pageright/list.jsp中查询后
 * @param p
 * @return
 */
function afterajaxListByDetail(rps){
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
				var remark='';
				if(typeof(itm.pagerightinfo.remark)!="undefined"
					&&itm.pagerightinfo.remark!=null&&itm.pagerightinfo.remark.length>0)
					remark=itm.pagerightinfo.remark;
				htm+='<td>'+replaceAll(remark,"'","‘")+'</td>';				
				htm+='</tr>';
			});			
		}
		$("#datatbl").html(htm);
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
/**
 * 编辑
 * @return
 */
function toEdit(ref){
	if(typeof(ref)=="undefined"||ref.Trim().length<1){
		alert('异常错误，参数异常!');return;
	}
	$.ajax({
		url:'columnright?m=ajaxlist',
		type:'post',
		data:{ref:ref.Trim()},
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
				return;
			}
			if(rps.objList.length>0){
				var obj=rps.objList[0];
				//columnrightname
				var htm='<input type="text" value="'+obj.columnrightname+'" name="columnrightname" id="columnrightname"/>';
				$("span[id='sp_columnrightname']").html(htm);
				//column
				htm='<select name="up_sel_columnid" id="up_sel_columnid">';
				htm+=$("#sel_column").html();
				htm+='</select>';
				$("#sp_columnid").html(htm);
				$("#up_sel_columnid").val(obj.columnid);
				$("li[id='up_bottom_li']").show();
			}
		}
	});
}
/**
 * 修改
 * @return
 */
function doUpdate(ref){
	if(typeof(ref)=="undefined"||ref.Trim().length<1){
		alert('异常错误，参数异常!');return;
	}
	var columnrightname=$("#columnrightname");
	var columnid=$("#up_sel_columnid");
	if(columnrightname.val().Trim().length<1){
		alert('您尚未输入栏目权限!请输入!');
		columnrightname.focus();return;
	}
	
	if(columnid.val().Trim().length<1){
		alert('您尚未选择所属栏目!请输入!');
		columnid.focus();return;
	}
	if(!confirm("您确认修改吗?"))return;
	var dparam={ref:ref.Trim(),columnrightname:columnrightname.val().Trim(),columnid:columnid.val().Trim()};
	
	$.ajax({
		url:'columnright?m=doUpdate',
		type:'post',
		data:dparam,
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error")
				alert(rps.msg);
			else{				
				alert(rps.msg);
				cannelEdit(ref);
			}
		}
	});
	
}

/**
 * 关闭编辑状态
 * @return
 */
function cannelEdit(ref){	
	if(typeof(ref)=="undefined"||ref.Trim().length<1){
		alert('异常错误，参数异常!');return;
	}
	$.ajax({
		url:'columnright?m=ajaxlist',
		type:'post',
		data:{ref:ref.Trim()},
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
				return;
			}
			if(rps.objList.length>0){
				var obj=rps.objList[0];
				//columnrightname
				$("span[id='sp_columnrightname']").html(obj.columnrightname);
				//column				
				htm=obj.columnname+'<input type="hidden" value="'+obj.columnid+'"/>';
				$("#sp_columnid").html(htm);				
				$("li[id='up_bottom_li']").hide();
			}
		}
	});	
}
/**
 * 删除
 * @param ref
 * @return
 */
function doDel(ref){
	if(typeof(ref)=="undefined"||ref.Trim().length<1){
		alert('异常错误，参数异常!');return;
	}
	if(!confirm("您确认要删除该记录吗?"))
		return;
	$.ajax({
		url:'columnright?m=doDel',
		type:'post',
		data:{ref:ref.Trim()},
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
				return;
			}
			pageGo("p1");
			alert(rps.msg);
		}
	})
}
/**
 * 执行添加
 * @return
 */
function doAdd(){
	var columnrightname=$("#add_columnright_name");
	var columnid=$("#add_columns_id");
	if(columnrightname.val().Trim().length<1){
		alert("栏目权限名称 不能为空，请输入!");
		columnrightname.focus();return;
	}
	if(columnid.val().length<1){
		alert("您尚未选择 所属栏目 ，请选择!");
		columnid.focus();return;
	}
//	if(!confirm("提示：添加后需分配相应的页面权限!\n\n您确认添加吗?"))
//		return ;
	//执行添加
	$.ajax({
		url:'columnright?m=doadd',
		type:'post',
		data:{
			columnrightname:columnrightname.val().Trim(),
			columnid:columnid.val().Trim()
		},
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
				return;
			}
			if(rps.objList.length>0){
				if(confirm(rps.msg+"\n\n您需要进入详情页分配页面权限吗?"))
					location.href='columnright?m=detail&ref='+rps.objList[0];
			}
		}
	})
}
/*8
 * 根据页面权限名称进行查询
 */
function pagerightSearch(txtid,selid){
	var txtval=$("#"+txtid).val().Trim();
	var param={};
	if(txtval.length>0&&txtval!="模糊查询（页面权限名称/路径）"){
		param.pagenamevalue=txtval;
	}
	var columnid=$("#sel_columnid").val().Trim();
	if(columnid.length>0)
		param.columnid=columnid;
	$.ajax({
		url:'pageright?m=ajaxlist',
		type:'post',
		data:param,
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
				return;
			}
			var h='';
			if(rps.objList.length>0){				
				$.each(rps.objList,function(idx,itm){
					
					h+='<option value="'+itm.pagerightid+'"';
					if($("#sel_op_2 option[value='"+itm.pagerightid+"']").length>0)
						h+=' style="color:gray"';
					h+='>'+itm.pagename+'['+itm.pagevalue+']</option>';					
				});			
			}
			$("#"+selid).html(h);
		}
	});
}

//页面按钮添加
function pageRightAddToRight(){
	var opts=$("#sel_op_1 option:selected");
	if(opts.length<1){
		alert('没有发现您已经选择的页面权限!');return;
	}
	var msg='';
	var htm='';
	$.each(opts,function(idx,itm){		
		if($("#sel_op_2 option[value='"+itm.value.Trim()+"']").length<1){
			htm+='<option value="'+itm.value.Trim()+'"';		
			//	htm+=' style="color:gray"'
			
			htm+='>'+itm.text.Trim()+'</option>';
		}
	})
	//将选框一选中的添加至选框二中。
	$("#sel_op_2").append(htm);
	//将选框一中的删除
	$("#sel_op_1 option:selected").css("color","gray");
}
/**
 * 页面权限分配(删除)
 * @return
 */
function doDelPageRight(){
	var opts=$("#sel_op_2 option:selected");
	if(opts.length<1){
		alert('没有发现您已经选择的页面权限!');return;
	}
	$.each(opts,function(idx,itm){	
		$("#sel_op_1 option[value='"+itm.value+"']").css("color","black");
	})
	//删除选中项
	opts.remove();
}
/**
 * 执行修改页面权限
 * @param ref
 * @return
 */
function doSubUpdatePageRight(columnpagerightid){
	if(typeof(columnpagerightid)=="undefined"||columnpagerightid==null){
		alert('异常错误，参数异常!');return;
	}
	//组织数据
	var opts=$("#sel_op_2 option");
	if(opts.length<1){
		alert("您尚未选择完页面权限，请重新选择!");
		$("#sel_op_2").focus();return;
	}
	var optsid='';
	$.each(opts,function(idx,itm){
		if(itm.value.Trim().length>0){
			optsid+=(optsid.Trim().length>0?",":"")+itm.value.Trim();
		}
	});
	if(optsid.trim().length<1){
		alert("您尚未选择完页面权限，请重新选择!");
		return;
	}
	var param={
			columnrightid:columnpagerightid,
			pagerightidarr:optsid
			};
	if(!confirm("数据验证完毕，您确认要对该栏目权限进行修改吗?"))
		return;
	$.ajax({
		url:'columnright?m=doUpdateRight',
		type:'post',
		data:param,
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				//更新列表
				pageGo('p1');
				alert(rps.msg);
				//关闭模式窗口
				closeModel('dv_editpageright');
				//隐藏select
				document.getElementById('sel_column').style.display='none';				
				
			}
		}
	});
}
/**
 * 加载pagerightid BY columnrightid
 * @return
 */
function loadColumnRightByColumnRightId(ref){
	if(typeof(ref)=="undefined"||ref==null){
		alert('异常错误，参数异常!');return;
	}
	
	var param={ref:ref};
	$.ajax({
		url:'columnright?m=getpageright',
		type:'post',
		data:param,
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){alert(rps.msg);}
			else{
				var h='';
				if(rps.objList.length>0){
					$.each(rps.objList,function(ix,im){
						h+='<option value="'+im.pagerightid+'">'+im.pagename+'['+im.pagevalue+']</option>';
					});
				}
				$("#sel_op_2").html(h);
				
				showModel('dv_editpageright');
			}
		}
	});
} 

