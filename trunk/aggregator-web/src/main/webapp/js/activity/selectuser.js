function rolechecked(rid,rname){
	if(typeof(rid)=="undefined"||rid==null||isNaN(rid)){
		alert('异常错误!系统未成功获取角色标识!请刷新页面后重试!');
		return;
	}
	//选中的
	var ckrole=$("input[name='rck']").filter(function(){
		return this.checked==true;
	});
	var stuid=1;
	var ckSign = $("input[name='rck']").filter(function(){
		return (this.checked==true&&this.value==stuid);
	});
	if(ckSign.length>0){
		//说明已经选中(显示年级，班级选项)
		if(rid==stuid){
			$("tr").filter(function(){
				return this.id.Trim()=='tr_student_grade';
			}).show('fast');
			//加载默认年级的班级选项
			$("input[name='ck_grade']").first().attr("checked",true);
			gradeChecked($("input[name='ck_grade']").first().val(),null);
		}
	}else{
		var testTeaLen=$("input[type='checkbox']").filter(function(){return this.value==2&&this.checked==true});
		if(testTeaLen.length>0){
			$("#tr_tea_course").show();
		}else{
			$("#tr_tea_course").hide();
		}
		//没有选中  (掩藏年级，班级选项)
		$("tr").filter(function(){
			return this.id.Trim().indexOf('tr_student')!=-1;
		}).hide('fast');
		//取消选项
		$("input[name='ck_cls']").attr("checked",false);
		$("input[name='ck_grade']").attr("checked",false);
	}
}

/**
 * 年级选中后
 * @return
 */
function gradeChecked(gname,cuyear){
	if(typeof(gname)=="undefined"||gname==null||gname.Trim().length<1){
		alert('异常错误!系统未成功获取班级标识!请刷新页面后重试!');
		return;
	}
	//当前的年份
	//if(typeof(cuyear)=="undefined"||cuyear==null||cuyear.Trim().length<1){
		//alert('异常错误!系统未成功获取到当前的年份标识!请刷新页面后重试!');
		//return;
	//}

	
	$("#tr_student_cls").show('fast');
	$("#td_cls").html("<b><img src='img/loading_smail.gif'/></b>");
	$.ajax({
		url:'cls?m=ajaxlist',//cls!??.action
		dataType:'json',
		type:'POST',
		data:{//year:cuyear.Trim(),
				classgrade:gname.Trim(),
				pattern:'行政班'},
		cache: false,
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			var shtml="";
			if(rps.objList==null){
				shtml="<b>没有发现班级信息!</b>";
			}else{
				$.each(rps.objList,function(idx,itm){
					shtml+='<b><input type="checkbox" value="'+itm.classid+'" name="ck_cls"/>'+itm.classname+' </b>';
				})
			}
			$("#td_cls").html(shtml);
		}
	});
}

function freedopagemethod(tobj){
	var roleidArray=$("input[name='rck']").filter(function(){return this.checked==true;});
	//班级
	var clsArray=$("input[name='ck_cls']").filter(function(){return this.checked==true});
	var ckCourseArray=$("input[type='checkbox']").filter(function(){return (this.name.Trim()=='ck_course'&&this.checked==true)});
	var ckgrade=$("input[name='ck_grade']").filter(function(){return this.checked==true});
	var username = $("#selInput").val().Trim();
	var paramStr="{";
	if(roleidArray.length>0){		
		var ridStr="";
		$.each(roleidArray,function(idx,itm){
			ridStr+=itm.value.Trim();
			if(idx!=roleidArray.length-1)
				ridStr+="|";
		});
		
		paramStr+="roleid:'"+ridStr+"',"
	}
	if(clsArray.length>0){
		var cStr="";
		$.each(clsArray,function(i,m){
			cStr+=m.value.Trim();
			if(i!=clsArray.length-1)
				cStr+="|";
		});
		paramStr+="clsid:'"+cStr+"',";
	}
	if(ckCourseArray.length>0){
		var cname='';
		$.each(ckCourseArray,function(i,m){
			if(cname.length>0)
				cname+='|';
			cname+=m.value.Trim();
			
		});
		paramStr+="cname:'"+cname+"',";
	}
	if(ckgrade.length>0){
		paramStr+="grade:'"+ckgrade[0].value+"',"
	}
	if(username.length>0){
		paramStr+="username:'"+username+"'"
	}	
	paramStr+="}";
	var paraObj=eval("("+paramStr+")");
	if(typeof(tobj)!="undefined"||typeof(tobj)=='object'){
		tobj.setPostParams(paraObj);	
	}
	
}

function findpostReturnMethod(rps){
	var shtml="";
	if(rps.objList==null){
		shtml+="<tr><th><b>没有发现您查询的用户信息!</b></th></tr>";
	}else{
		shtml+='<tr><th width="15%"><input type="checkbox" onclick="ckuserAll(this)" name="data_ck_all" id="data_ck_all" value=""/>全选/不选</th>';
		shtml+='<th width="15%">编号(学号)</th><th width="25%">姓名</th><th width="10%">性别</th><th>用户名</th> '; 
		$.each(rps.objList,function(idx,itm){
			shtml+='<tr>';
			shtml+='<td><input type="checkbox" onclick="ckOne(this)" name="data_ck" id="data_ck" value="'+itm.ref+'|'+itm.realname+'"/></td>';
			var seno=itm.identitynumber;
			if(seno==""||seno==null)
				seno=(idx+1);
			shtml+='<td>'+seno+'</td>';
			if(itm.realname==null||itm.realname==""){
				shtml+='<td>'+"未知"+'</td>';
			}else{
				shtml+='<td>'+itm.realname+'</td>';
			}
			var sex;
			if(itm.gender!=null&&itm.gender!=""){
				sex=itm.gender;
			}else{
				sex="未知"
			}
			
			if(sex=="0") 
				sex="女";
			if(sex=="1")
				sex="男";
			shtml+='<td>'+sex+'</td>';			
			shtml+='<td>'+itm.username+'</td>';			
			shtml+='</tr>';
		});
	}
	$("#datatbl").hide();
	$("#datatbl").html(shtml);
	$("#datatbl").show('fast');
	
	if (typeof (p1) != "undefined" && typeof (p1) == "object") {
		p1.setPagetotal(rps.presult.pageTotal);
		p1.setRectotal(rps.presult.recTotal);
		p1.setPageSize(rps.presult.pageSize);
		p1.setPageNo(rps.presult.pageNo);	
		p1.Refresh();
	}
	
	//选中以选中的
	var seuserarray=$("input[type='hidden']").filter(function(){
				return this.name=='seluserid'
			});
	if(seuserarray.length>0){
		$.each(seuserarray,function(idx,itm){
			var itmCkobj=$("input[type='checkbox']").filter(function(){
							return (this.name=='data_ck'&&this.value.Trim()==itm.value.Trim());
						});
			if(itmCkobj.length>0){
				itmCkobj.attr("checked",true);
			}
		});
	}
}

//删除全部
function ckuserAll(ck){
	var cked=$("input[type='checkbox']").
	filter(function(){return this.name=='data_ck'});
	if(cked.length>0){
		$.each(cked,function(idx,itm){
			itm.checked=ck.checked;
			if(ck.checked==true){
				var shtml="<input type='hidden' name='seluserid' value='"+itm.value.Trim()+"'/>";
				$("#seldiv").append(shtml);
			}else if(ck.checked==false){
				$("input[type='hidden']").filter(
						function(){
							return (this.name=='seluserid'&&this.value==itm.value.Trim());
						}
				).remove();
			}
		});
	}
}

/**
 * 单个选框选中
 * @param ck
 * @return
 */
function ckOne(ck){
	if(ck.checked==true){
		var ckarray=$("input[type='hidden']").filter(
				function(){
					return (this.name=='seluserid'&&this.value==ck.value.Trim());
				}
		);
		if(ckarray.length<1){
			var shtml="<input type='hidden' name='seluserid' value='"+ck.value.Trim()+"'/>";
			$("#seldiv").append(shtml);
		}
	}else{
		$("input[type='hidden']").filter(
				function(){
					return (this.name=='seluserid'&&this.value==ck.value.Trim());
				}
		).remove();
	}
}