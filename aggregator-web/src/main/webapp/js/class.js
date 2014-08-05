//在执行ajax查询之前		
	function beforajaxList(p){
		var param={};
		var clsname=document.getElementById("sel_clsname");
		if(clsname.value.Trim().length>0&&clsname.value.Trim()!='输入班级名称进行查询!')
			param.classname=clsname.value.Trim();
		//年份
		var year=document.getElementById("sel_year");
		if(year.value.Trim().length>0)
			param.year=year.value.Trim();
		
		var grades=document.getElementById("sel_grade").value;
		
		if(grades.Trim().length>0)
			param.classgrade=grades.Trim();	
		
		var patterns=document.getElementById("sel_pattern").value;	 
		if(patterns.Trim().length>0){
			param.pattern=patterns.Trim();
		}
		var types=document.getElementById("sel_type");		
		if(types.value.Trim().length>0){ 
			param.dtype=types.value.Trim(); 
		}

        param.dcschoolid=$("#dcSchoolID").val();
		p.setPostParams(param);
	}
	
	//执行分页查询后
	function afterajaxList(rps){
        $("#page1address").show();
		var ghtml='';
		if(rps.type=="error"){
			alert(rps.msg);
		}else{			
			if(rps.objList.length>0){
				ghtml+='<tr>';
				ghtml+='<th>年级</th><th>班级</th><th>人数</th><th>文/理</th><th>分类</th><th>操作</th></tr>';				
				$.each(rps.objList,function(idx,itm){
					ghtml+='<tr';
					if(idx%2==0) 
						ghtml+=' class="trbg2"';  
					ghtml+=' >';   
					ghtml+='<td>'+itm.classgrade+'</td>';
					ghtml+='<td><a class="font-blue" href="cls?m=detail&classid='+itm.classid+'">'+itm.classname+'</td>'; 
					var type='未知';
					switch(itm.type){  
						case 'W':
							type="文科"  
							break; 
						case 'L':
							type="理科"
							break;
						case 'NORMAL':
							type="普通"
							break;
					}
						
					ghtml+='<td>'+itm.num+'</td>';					 
					ghtml+='<td>'+type+'</td>';
					ghtml+='<td>'+itm.pattern+'</td>';
					/***********功能能权限**************/ 
					ghtml+='<td>';
					//ghtml+='<a class="font-lightblue" href="javascript:void(0);" onclick="toUpd('+ itm.classid +')">修改</a>&nbsp;';
					//if(isUpdate){
					//	ghtml+='<a class="font-lightblue" href="javascript:void(0);" onclick="toUpd('+ itm.classid +')">修改</a>&nbsp;';    
					//}if(isDelete){  
					//	ghtml+='<a class="font-lightblue" href="javascript:;" onclick="doDelClass('+itm.classid+')">删除</a>&nbsp;';
					//}  
//					if(itm.isflag==1)	//是启用状态
//						ghtml+='<span id="sp_'+itm.classid+'"><a href="javascript:doUpdateIsflag('+itm.classid+',2);" ><span class="ico01" title="禁用"></span></a></span>|';
//					else if(itm.isflag==2)
//						ghtml+='<span id="sp_'+itm.classid+'"><a href="javascript:doUpdateIsflag('+itm.classid+',1);"><span class="ico02" title="启用"></span></a></span>|';
						
					//当班级人数为0时，删除启用，可以删除本班级。
//					if(parseInt(itm.num)>0)
//						ghtml+='|<a class="font-gray" href="javascript:alert(\'当前班级有学生，不能删除!\')"><span class="ico03" title="删除"></span></a>&nbsp;';
//					else if(parseInt(itm.num)<1)
						ghtml+='<a class="font-lightblue" href="javascript:;" onclick="doDelClass('+itm.classid+')"><span class="ico04" title="删除"></span></a>&nbsp;';
					
					ghtml+='</td>';
					/***********功能能权限**************/  
					ghtml+='</tr>';
				});		
			}else{
				ghtml+='<tr><th>暂无数据 !</th></tr>';
			}
			
				p1.setPageSize(rps.presult.pageSize);
				p1.setPageNo(rps.presult.pageNo);
				p1.setRectotal(rps.presult.recTotal);
				p1.setPagetotal(rps.presult.pageTotal);
				p1.Refresh();
		}
		$('#maintbl').html(ghtml);
	}
	/**
	 * 更新班级的启用状态
	 * @param id  要更改的班级ID
	 * @param state  状态。
	 * @return
	 */
	function doUpdateIsflag(id,state){
		if(typeof(id)=="undefined"||id==null){
			alert("异常错误，班级ID为空!请刷新页面后重试!");return;
		}
		if(typeof(state)=="undefined"||state==null){
			alert("异常错误，修改的状态为空!请刷新页面后重试!");return;	
		}
		if(!confirm("您确认 "+(state==1?"启用":"禁用")+" 该班级吗?" +
				"\n提示：\n禁用班级后，班级数据将会保留，但禁用期间的数据不会录入!" +
				"\n启用班级后，班级数据将会恢得到禁用时的数据!"))
			return;
		$.ajax({
			url:'cls?m=modify',
			data:{
				classid : id,
				isflag:state				
			},
			type:'post',
			dataType:'json',
			error:function(){
				alert('网络异常！');
			},
			success:function(rps){
				if(rps.type=="error"){
					alert(rps.msg);
					return;
				}
				//改变操作栏中的数据
				var h='';
				if(state==2){
					h+='<a href="javascript:doUpdateIsflag('+id+',1);"><span class="ico02" title="启用"></span></a>';
				}else if(state==1){
					h+='<a href="javascript:doUpdateIsflag('+id+',2);"><span class="ico01" title="禁用"></span></a>';
				}
				$("#sp_"+id).html(h);
				alert(rps.msg);
			}
		});
		
	}
	
	
	//删除
	function doDelClass(clsid){
		if(typeof(clsid)=="undefined"||isNaN(clsid)){
			alert('异常错误,系统未获取到班级标识!'); 
			return;
		}
		if(!confirm('确认删除该班级吗?\n\n提示：删除该班级后，系统将自动删除该班级下的任课教师!')){return;} 
		$ajax('cls?m=del&classid='+clsid,undefined,'POST','json',function(rps){
			if(rps.type=="success")
				pageGo('p1');	
			alert(rps.msg);	
		},function(){
			alert('网络异常！');
		})
	}
	//修改之前
	function toUpd(classid){
		$("#hidden_for_upd").val(classid);
		$ajax('cls?m=toupd',{classid:classid},'POST','json',function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
				return;
			}
			var html = '';
			if(rps.objList.length>0){
				$("#upd_sel_clsname").val(rps.objList[0].classname);
				$("#upd_sel_year").val(rps.objList[0].year);
				$("input[type='radio'][name='upd_sel_grade']").filter(function(){
					if(this.value==rps.objList[0].classgrade){
						this.checked = true;
					}
				});
				$("input[type='radio'][name='upd_sel_pattern']").filter(function(){
					if(this.value==rps.objList[0].pattern){
						this.checked = true;
					}
				});
				$("input[type='radio'][name='upd_sel_type']").filter(function(){
					if(this.value==rps.objList[0].type){
						this.checked = true;
					}
				}); 
				showModel('div_upd');
				 
			}
		},function(){
			alert("网络异常!")
		});
	} 
	//修改
	function doUpd(){
		var classid = $("#hidden_for_upd").val();
		if(classid.length<1){
			alert("系统未获取到班级标识，请刷新页面重试!");
			return;
		}
		var classname = $("#upd_sel_clsname").val();
		var year = $("#upd_sel_year").val();
		var classgrade =$("input[type='radio'][name='upd_sel_grade']").filter(function(){
			return this.checked && this.value.length>0;
		});
		classgrade = classgrade.val();
		if(classgrade.length<1){
			alert("您未选择年级，请选择!");
			return;
		}
		var pattern =$("input[type='radio'][name='upd_sel_pattern']").filter(function(){
			return this.checked && this.value.length>0;
		});
		pattern = pattern.val();
		if(pattern.length<1){
			alert("您未选择类型，请选择!");
			return;
		}
		var type = $("input[type='radio'][name='upd_sel_type']").filter(function(){
			return this.checked && this.value.length>0;
		});
		type = type.val();
		if(type.length<1){
			alert("您未选择类别，请选择!");
			return;
		}
		if(!confirm("数据验证成功，确认修改？")){
			return;
		}
		$.ajax({
			url:'cls?m=modify',
			data:{
				classid : classid,
				classgrade : classgrade,
				classname : classname,
				pattern : pattern,
				type : type,
				year : year
			},
			type:'post',
			dataType:'json',
			error:function(){
				alert('网络异常！');
			},
			success:function(rps){
				if(rps.type=="error"){
					alert(rps.msg);
					return;
				}
				pageGo('p1');
				closeModel('div_upd');
				alert(rps.msg);
			}
		});
		
	}
	//添加
	function doAdd(){
		var classnameObj = $("#add_sel_clsname");
		if(classnameObj.val().length<1){
			alert("请您填写班级名称!");
			return;
		}
		var classname=classnameObj.val().Trim()+"班"
		var year = $("#add_sel_year").val();
		var classgrade =$("#add_sel_grade").val();
		var pattern = $("#add_sel_pattern").val();
		var type=$("#add_sel_type").val();
        var dcschoolid=$("#dcSchoolID").val();
		var data1={
			classgrade : classgrade,
			classname : classname,
			dpattern : pattern,
			dtype : type,
            dcschoolid : dcschoolid,
			dyear : year
		};
		if(pattern=="分层班"){
			var subject=$("#sel_subject").val();
			data1.subjectid=subject;
		}
		if(!confirm("数据验证成功，确认添加？")){
			return;
		}
		$ajax('cls?m=ajaxsave',
			data1,
			'post',
			'json',
			function(rps){
				if(rps.type=="error"){ 
					alert(rps.msg);
					return;
				}
				pageGo('p1');
				closeModel('div_add');
				alert(rps.msg);  
			},
			function(){
				alert('网络异常！');
			}
		);
		
	}
	
	
/**
 * 上传Excel导入学生
 * @return
 */
function uploadStudentExcel(clsid){
	var ipt=$("#upload");
	if(ipt.val().Trim().length<1){
		alert('请选择您要上传的文件!');
		ipt.focus();
		return;
	}
	var lastname=ipt.val().substring(ipt.val().lastIndexOf(".")+1); 
	if(lastname.toLowerCase()!='xls'){
		alert('您上传的文件类型有误!请上传Excel文件!');
		return;
	}
	if(!confirm('确认导入?')){return;}
	$("#showLoadHtml").css("text-align","center");
	$("#showLoadHtml").html("<div style='text-align:center'><img src=\"images/loading.gif\"/>正在操作,请耐心等待!</div>");
	$("#showLoadHtml").show();
	$.ajaxFileUpload({
		 url:'student?m=loadExlForStudent&classid='+clsid, 
	     secureuri:false,   
	     fileElementId:'upload',
	     dataType: 'json',
		 success: function (data, status)   
	      {         
			 if(data.type=="success"){
				 	$("#showLoadHtml").html(data.msg);   
				 	location.reload();//刷新当前页面
				// else   
				//	closeModel("loadExcel");  
			 }else{
				 $("#showLoadHtml").html("<div style='color:red;text-align:center'>"+data.msg+"</div>");
			 }
	  	  },   
		 	  error: function (data, status, e)   
	      {  
	  		$("#showLoadHtml").html("<div style='color:red;text-align:center'>文件上传失败!请检查数据格式是否符合规则!</div>");  
	      }  
	});
	
}

function reset(div){
	$("div[id="+div+"] input").val(''); 
	$("div[id="+div+"] select").val('');
}

/**
*进行修改班级准备。
*/
function readlyToUpdate(clsid){
	if(typeof(clsid)=="undefined"||isNaN(clsid)){
		alert('异常错误，参数异常，请刷新后重试!');return;
	}

	$.ajax({
		url:'cls?m=ajaxlist',
		data:{classid:clsid},
		type:'post',
		dataType:'json',
		error:function(){
			alert('网络异常!');
		},success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else if(rps.type=="success"){
				if(rps.objList.length>0){
					$.each(rps.objList,function(idx,itm){
						//itm.grade								
						var ht='<select class="w160" name="sel_gd_up" id="sel_gd_up">';
							ht+=$("#sel_grade").html();
							ht+='</select>';
						$("#sp_classgrade").html(ht);
						$("#sel_gd_up").bind("change",function(){
							$("#sp_classgrade_copy").html(this.value.Trim());
						});
						$("#sel_gd_up").val(itm.classgrade);								
						
						//itm.classname
						 ht="<input type='text' name='txt_clsname' class='w80' id='txt_clsname' value='"+itm.classname.replace("班","")+"'/>&nbsp;班";
						$("#sp_classname").html(ht);
						//itm.type;
						ht='<select class="w160" name="sel_type_up" id="sel_type_up">';
						ht+='<option value="NORMAL">普通</option>';
						ht+='<option value="W">文科</option>';
						ht+='<option value="L">理科</option>';
						ht+='</select>';
						$("#sp_type").html(ht);
						$("#sel_type_up").val(itm.type);								
						//itm.pattern;
						h='<select class="w160" name="sel_pattern_up" id="sel_pattern_up">';
						h+='<option value="行政班">行政班</option>';
						h+='<option value="分层班">分层班</option>';
						h+='</select>';
						$("#sp_pattern").html(h);
						$("#sel_pattern_up").val(itm.pattern);
						//itm.subjectid;
						h='<select class="w160" name="sel_subject_up" id="sel_subject_up">';
						h+=$("#sel_subject").html();
						h+='</select>';
						$("#sp_subject").html(h);
						$("#sel_subject_up").val(itm.subjectid);
						$("#sel_pattern_up").bind("change",function(){
							if(this.value.Trim()=="分层班")
								$("#up_subject").show();
							else
								$("#up_subject").hide();
						})
						//显示
						$("#up_bottom_li").show();
						$("#sp_cannelUpdate").show();

                        $("#sp_update_cls").hide();
					});
				}
			}
		}				
	});			
}
/**
 * 提交修改
 * @return
 */
function doSubUpdate(clsid){
	var gradeObj=$("#sel_gd_up");//.val();
	var clsnameObj=$("#txt_clsname");
	var typeObj=$("#sel_type_up");
	var patternObj=$("#sel_pattern_up");
	var subjectObj=$("#sel_subject_up");
	if(gradeObj.val().Trim().length<1){
		alert('年级不能为空!请选择!');
		gradeObj.focus();return;
	}
	if(clsnameObj.val().Trim().length<1){
		alert('班级名称为空，请输入!');
		clsnameObj.focus();return;
	}
	if(typeObj.val().Trim().length<1){
		alert('文/理 为空，请选择!');
		typeObj.focus();return;
	}
	if(patternObj.val().Trim().length<1){
		alert('班级类型为空，请选择!');
		patternObj.focus();return;
	}
	if(patternObj.val().Trim()=="分层班"){
		if(subjectObj.val().Trim().length<1){
			alert('学科为空，请选择!');
			subjectObj.focus();return;
		}
	}
	var data={classid:clsid,
			classname:clsnameObj.val().replace("班","").Trim()+"班",
			classgrade:gradeObj.val().Trim(),
			type:typeObj.val().Trim(),
			pattern:patternObj.val().Trim()
	};
	if(patternObj.val().Trim()=="分层班")
		data.subjectid=subjectObj.val().Trim();	
	$.ajax({
		url:'cls?m=modify',
		data:data,
		type:'post',
		dataType:'json',
		error:function(){
			alert('网络异常!');
		},success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else if(rps.type=="success"){
				clsDataRest(gradeObj.val(),clsnameObj.val(),typeObj.val(),patternObj.val(),subjectObj.val());
                $("#sp_update_cls").show();
			}
		}
	})
}
//恢复修改
function clsDataRest(grade,clsname,type,pattern,subject){
	//grade
	$("#sp_classgrade").html(grade.Trim());
	//classname
	$("#sp_classname").html(clsname.Trim().replace("班","")+"班");
	//type
	var typeStr="未知";
	if(type.Trim()=="NORMAL")
		typeStr="普通";
	else if(type.Trim()=="W")
		typeStr="文科"
	else if(type.Trim()=="L")
		typeStr="理科"
	$("#sp_type").html(typeStr);
	//pattern
	$("#sp_pattern").html(pattern.Trim());
	//subject
	if(pattern.Trim()=="分层班"){
		var sbname=$("#sp_subject select option[value='"+subject+"']").text();
		$("#sp_subject").html(sbname);
		$("#up_subject").show();
	}else
		$("#up_subject").hide();
	$("#sel_subject").hide();
	$("#up_bottom_li").hide();
	$("#sp_cannelUpdate").hide();
}

function quxiaoClsUpdate(clsid){
	if(typeof(clsid)=="undefined"||isNaN(clsid)){
		alert('异常错误，参数异常，请刷新后重试!');return;
	}
	$.ajax({
		url:'cls?m=ajaxlist',
		data:{classid:clsid},
		type:'post',
		dataType:'json',
		error:function(){
			alert('网络异常!');
		},success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else if(rps.type=="success"){
				if(rps.objList.length>0){
					$.each(rps.objList,function(idx,itm){
						clsDataRest(itm.classgrade,itm.classname,itm.type,itm.pattern,itm.subjectid);
					})
				}
			}
		}
	});
}
/**
 * 导出模板
 * 得到的模板班级：
 * 	根据学年，年级，文/理,类型，班级名称查找班级 并生成模板Excel 
 * @return
 */
function explortClassTemplate(clsid){
	var param={};
    if(typeof(clsid)=="undefined"||clsid==null||(clsid+"").length<1){
        var clsname=document.getElementById("sel_clsname");
        if(typeof(clsname)!="undefined"&&clsname!=null
            &&clsname.value.Trim().length>0&&clsname.value.Trim()!='输入班级名称进行查询!')
            document.explortClsTemplate.classname.value=clsname.value.Trim();
        //年份
        var year=document.getElementById("sel_year");
        if(typeof(year)!="undefined"&&year!=null
                &&year.value.Trim().length>0)
            document.explortClsTemplate.year.value=year.value.Trim();

        var grades=document.getElementById("sel_grade");
        if(typeof(grades)!="undefined"&&grades!=null&&grades.value.Trim().length>0)
            document.explortClsTemplate.classgrade.value=grades.value.Trim();

        var patterns=document.getElementById("sel_pattern");
        if(typeof(patterns)!="undefined"&&patterns!=null
                &&patterns.value.Trim().length>0){
            document.explortClsTemplate.pattern.value=patterns.value.Trim();
        }
        var types=document.getElementById("sel_type");
        if(typeof(types)!="undefined"&&types!=null&&types.value.Trim().length>0){
            document.explortClsTemplate.dtype.value=types.value.Trim();
        }
    }
	if(typeof(clsid)!="undefined")
		document.explortClsTemplate.classid.value=clsid;
	document.explortClsTemplate.action="cls?m=explortClsTemplate";
	document.explortClsTemplate.submit();
} 
/**
 * 进入拷贝班级学生
 * @return
 */
function toCopyClassStudent(clsid){
	if(typeof(clsid)=="undefined"||clsid==null){
		alert('异常错误，班级ID为NULL !');
		return;
	}
	$.ajax({
		url:'cls?m=toCopyClsStudent',
		dataType:'json',
		type:'post',
		data:{classid:clsid},
		error:function(){
			alert('网络异常!');
		},success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				if(typeof(rps.objList)!="undefined"&&rps.objList!=null&&rps.objList.length>0){
					var h='';
					$.each(rps.objList,function(idx,itm){
						h+='<option value="'+itm.classid+'">'+itm.year+" "+itm.classgrade+itm.classname+'</option>';
					});	
					$("#sel_class").html(h);
					
					getClassStudent(document.getElementById("sel_class"));
					showModel("dv_tiaoban");
				}else{
					alert("没有发现相关学年的班级!");
				}
			}
		}
	});	
}
/**
 * 得到学生名单
 * @return
 */
function getClassStudent(obj){	
	var d={};
	if(typeof(obj)!="undefined"&&obj.value.length>0){
		d.classid=obj.value.Trim();
	}
	var pattern=$("#sp_pattern").html();
	if(pattern.indexOf("select")!=-1){
		alert('您现在处于编辑班级信息状态，请先编辑完后再试!');return;
	}
	if(pattern.Trim().length>0){
		d.pattern=pattern.Trim();
	}
	
	var year=$("#sp_year input[type='hidden']").val();	
	if(year.Trim().length>0){
		d.year=year.Trim();
	}
	
	$.ajax({
		url:'cls?m=tiaobancu',
		dataType:'json',
		type:'post',
		data:d,
		error:function(){ 
			alert('网络异常!');
		},success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				if(typeof(rps.objList)!="undefined"&&rps.objList!=null&&rps.objList.length>0){
					var h='';
					$.each(rps.objList,function(idx,itm){
						var len=$("#sel_op_2 option[value='"+itm.USER_ID+"']").length;
						if(len<1){
							if(itm.ISGRAY<1)
							h+='<option value="'+itm.USER_ID+'">'+itm.STU_NO+' / '+itm.STU_NAME+'</option>';
							else if(itm.ISGRAY>0)
								h+='<option value="'+itm.USER_ID+'" style="color:#38bab7">'+itm.STU_NO+' / '+itm.STU_NAME+'</option>';
						}
					});					
					$("#sel_op_1").html(h);	
					//
					$("#txt_search_1").autocomplete(rps.objList,{
						minChars: 0,
						width: 150,
						matchContains: true,
						autoFill: false,
						formatItem: function(row, i, max) {
							return  row.STU_NO+" / "+row.STU_NAME;
						},
						formatMatch: function(row, i, max) {
							return  row.STU_NO+" / "+row.STU_NAME;
						},
						formatResult: function(row) {						
							return  row.STU_NO+" / "+row.STU_NAME;
						},selectedoperate:function(v){
							//searchRecommend(v,'txtTuiJian');
							var objArray=v.split(" / ");
                            var idx=0;//记录当前查找的是第几位
							$("#sel_op_1 option").each(function(x,m){
								if(m.text.Trim()==v.Trim()){
									m.selected=true;
                                    idx=x+1;
                                    return;
								}
							});
							//option[0].select
							$("#txt_search_1").val(v);
                            //开始定位scrollTop
                            if(idx>0){
                                var top=0;
                                if(idx>10)
                                 top=(idx-10)*19;
                                document.getElementById("sel_op_1").scrollTop=top;
                            }
						}				
					});
					
					
				}
			}
		}		
	});
}
//页面按钮添加
function clsAddToRight(){
	var opts=$("#sel_op_1 option:selected");
	if(opts.length<1){
		alert('异常错误，没有发现您已经选择的学生!');return;
	}
	var msg='';
	var htm='';
	$.each(opts,function(idx,itm){		
		htm+='<option value="'+itm.value.Trim()+'"';
		if(typeof(itm.style.color)!="undefined"&&itm.style.color=='gray'){			
			if(msg.length>0)
				msg+=',';
			//msg+=itm.text.Trim();
			htm+=' style="color:gray"'
		}
		htm+='>'+itm.text.Trim()+'</option>';
	})
	//将选框一选中的添加至选框二中。
	$("#sel_op_2").append(htm);
	//将选框一中的删除
	$("#sel_op_1 option:selected").remove();
}
/**
 * 删除
 * @return
 */
function doDelSelected(){
	$("#sel_op_2 option:selected").remove();
	getClassStudent(document.getElementById("sel_class"));
}


/**
 * 提交分班调配
 * @return
 */
function doAddClsUsrToDetail(clsid){
	if(typeof(clsid)=="undefined"||clsid==null){
		alert('异常错误，参数异常。clsid is empty!');return;
	}
	var opts=$("#sel_op_2 option");
	if(opts.length<1){
		alert('当前没有选择需要添加的学生!请选择!');
		return;
	}
	var graymsg='',datauid='';
	$.each(opts,function(ix,im){
		if(im.style.color=="gray"){
			//学生信息
			graymsg+=graymsg.length>0?',':'';
			graymsg+=im.text;
		}
		//学生ID
		datauid+=datauid.length>0?',':'';
		datauid+=im.value;
	});
	if(datauid.length<1){
		alert('当前没有选择需要添加的学生!请选择!');
		return;
	}
	if(graymsg.length>0){
		graymsg="提示：\n"+graymsg+" 已经分配至同类型班级，继续操作会从这些班级调到本班！\n继续操作吗？"
		if(!confirm(graymsg)){
			alert('当前没有选择需要添加的学生!请选择!');
			return;
		
		}
	}else{
		if(!confirm("您确认提交吗?"))
			return;		
	}
	
		
	$.ajax({
		url:'cls?m=doAddClsUserDetail',
		data:{classid:clsid,uid:datauid},
		dataType:'json',
		type:'post',
		error:function(){
			alert('网络异常!');
		},success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
                isupdateclsstu=true;
				if(!confirm(rps.msg+"\n是否继续操作该班级")){
					$("#sel_op_2 option").remove();
					closeModel('dv_tiaoban');
				}				
			}
		}
	})
}
/**
 * 自动升级
 * @return
 */
function autoLeaveUp(year){
	
	if(typeof(year)=="undefined"||year==null||year.Trim().length<1){
		alert('异常错误，当前班级年份未知!无法进行升级!');
		return;
	}
	if(!confirm("你确定升级除毕业班外的所有行政班吗？"))
		return;
	$("#loading").show();
	$.ajax({
		url:'cls?m=levelup',
		data:{year:year},
		dataType:'json',
		type:'post',
		error:function(){
			$("#loading").hide();
			alert('网络异常!');
		},success:function(rps){
			$("#loading").hide();
			if(rps.type=="error"){
				alert(rps.msg);
			}else{				
				closeModel('dv_autoShenj');
			}
		}
	})
}




