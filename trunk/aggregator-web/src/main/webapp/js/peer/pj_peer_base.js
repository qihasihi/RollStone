//列表返回
function peerBaseListReturn(rps){
	if(rps.type=='error'){
		alert(rps.msg);
		if (typeof (p1) != "undefined" && typeof (p1) == "object") {
			// 设置空间不可用
			p1.setPagetotal(1);
			p1.setRectotal(0);
			p1.Refresh();
			// 设置显示值
			var shtml = '<tr><td align="center">暂时没有评价主题信息!';
			shtml += '</td></tr>';
			$("#mainTbl").html(shtml);			
			
		}		
	}else{
		var shtml = '';
		shtml+='<tr>';
		shtml+=	'<th>学年</th><th>评价时间</th><th>参评部门</th><th>评价主题</th><th>操作</th></tr>';
		if(rps.objList.length<1){
			shtml+="<tr><th colspan='8' style='height:65px' align='center'>暂无信息!</th></tr>";
		}else{
			$.each(rps.objList,function(idx,itm){
				if(idx%2==0){
					shtml+='<tr class="trbg2">';
				}else{
					shtml+='<tr>';
				}
				shtml+='<td>'+itm.year+'</td>';
				shtml+='<td>'+itm.btimestring+'~'+itm.etimestring+'</td>';
				shtml+='<td>'+itm.deptname+'</td>';
				shtml+='<td>'+itm.name+'</td>';
				shtml+='<td><a href="javascript:update(\''+itm.ref+'\')"  class="font-lightblue">修改</a>&nbsp;&nbsp;<a href="javascript:deletePeer(\''+itm.ref+'\')"  class="font-lightblue">删除</a>&nbsp;&nbsp;<a href="peeruser?m=list&peerbaseid='+itm.ref+'" target="_blank"  class="font-lightblue">问卷调整</a></td>';
				
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
}

function deletePeer(ref){
	$.ajax({
		url:'peerbase?m=del',
		dataType:'json',
		type:'post',
		data:{ref:ref
	    },
		cache: false,
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			alert(rps.msg);
			pageGo('p1');
		}
	});
}

function update(ref){
//	var h ='<a href="javascript:doSubmit()" class="an_blue">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:clearForm()" class="an_blue">重置</a>';
//	$("#dotd").html();
	$.ajax({
		url:'peerbase?m=ajaxlist',//cls!??.action
		dataType:'json',
		type:'post',
		data:{ref:ref
	    },
		cache: false,
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			if(rps.objList.length==1){
				$.each(rps.objList,function(idx,itm){
					clearForm(document.forms[0]);
					$("#ref").val("");
					$("#ref").val(itm.ref);
					$("#year").val(itm.year);	
					$("#name").val(itm.name);
					$("#btime").val(itm.btimestring);
					$("#etime").val(itm.etimestring);
					$("#remark").val(itm.remark);
					$("#dotd").html("<a href='javascript:doSubmit()' class='an_blue'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:clearForm(document.mainForm)' class='an_blue'>重置</a>");					
					var shtml='<th>修改参评部门：</th><td><input type="checkbox" id="is_upd"  name="is_upd" onclick="isupd(this)"/>是否修改 &nbsp;&nbsp;<span class="font-red">选择“修改“后将清空您对部门人员进行的问卷调整</span></td>';
					$("#isupd_tr").show('fast');
					var depts = document.getElementsByName("dept_ck");
					var deptrefs = itm.deptref.split("|");
					for(var i = 0;i<depts.length;i++){
						depts[i].disabled=true;
						for(var j = 0;j<deptrefs.length;j++){
							if(depts[i].value==deptrefs[j]){
								depts[i].checked=true;								
							}
						}
					}
				});
			}else{
				alert('异常错误!系统未响应!');
			}
		}
	});
}

//添加执行
function doSubmit(){
	var ref = $("#ref").val();
	var year = $("#year");
	var param = {};
	if(ref!=null||ref!=""){
		param.ref=ref;
	}
	if(year.val().Trim().length<1){
		alert("请选择学年");
		return;
	}else{
		param.year = year.val().Trim();
	}
	var btime = $("#btime");
	if(btime.val().Trim().length<1){
		alert("请选择评价开始时间");
		return;
	}else{
		param.btimestring = btime.val().Trim();
	}
	var etime = $("#etime");
	if(etime.val().Trim().length<1){
		alert("请选择评价结束时间");
		return;
	}else{
		param.etimestring = etime.val().Trim();
	}
	//判断开始时间是否小于现在的时间
	var btimeStr=btime.val().Trim()+" 00:00:00";
	var nowD=new Date().toFullString();
	//如果btime> nowd则返回FALSE
	if(validateTwoDate(btimeStr, nowD)){
		alert('评价开始时间不能晚于现在的时间，请重新选择!');
		btime.focus();
		return;
	}
	//如果开始时间大于结束时间
	var etimeStr=etime.val().Trim()+' 00:00:00';
	if(validateTwoDate(etimeStr,btimeStr)){
		alert('评价结束时间不能早于或等于评价开始时间，请重新选择!');
		etime.focus();
		return;
	}
	
	//评价主题
	var name = $("#name");
	if(name.val().Trim().length<1){
		alert("请填写评价主题");
		return;
	}else{
		param.name=name.val().Trim();
	}
	
	//评价描述
	var remark = $("#remark");
	if(remark.val().Trim().length>0){
		param.remark = remark.val().Trim();
	}
	
	//部门数组
	var deptidArray=$("input[name='dept_ck']").filter(function(){return this.checked==true;});
	var deptids="";
	if(deptidArray.length<1){
		
	}
	$.each(deptidArray,function(idx,itm){
		deptids+=itm.value.Trim();
		if(idx<deptidArray.length-1)
			deptids+="|";
	});
	param.deptref = deptids;
	param.isupddept=isupddept;
	$.ajax({
		url:'peerbase?m=doAddOrUpdate',//cls!??.action
		dataType:'json',
		type:'POST',
		data:param,
		cache: false,
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			alert(rps.msg);
            if(rps.type=="error"){
                return;
            }
			clearForm(document.forms[0]);
			$("#ref").val("");
			$("#year").val(-1);
			$("#dotd").html("<a href='javascript:doSubmit()' class='an_blue'>添加</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:clearForm(document.mainForm)' class='an_blue'>重置</a>");
			pageGo('p1');
		}
	});
}

function isupd(ck){
	if(ck.checked==true){
		isupddept=0;
		var depts = document.getElementsByName("dept_ck");
		for(var i = 0;i<depts.length;i++){
			depts[i].disabled=false;
		}
		//alert("您选择了修改参评部门，此操作会造成问卷设置的结果有所改变");
	}else{
		isupddept=1;
		var depts = document.getElementsByName("dept_ck");
		for(var i = 0;i<depts.length;i++){
			depts[i].disabled=true;
		}
	}
}
