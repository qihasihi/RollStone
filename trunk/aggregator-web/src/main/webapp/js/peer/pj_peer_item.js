
/**
 * 添加评价
 */
function addInputParent(){
	//查询已经有多少个题目了。
	var divArrayObj=$("div").filter(function(){return this.id.indexOf('div_ques_')!=-1});
	var len=divArrayObj.length;
	var orderIdx=len+1000;
	
	var h=' <div class="thpj_bjpj" id="div_ques_'+orderIdx+'">';
		h+='  <p  id="tr'+orderIdx+'_first"><label>评价'+(len+1)+'：</label> ';
		h+='<input type="text" value="" name="investname'+orderIdx+'" id="investname'+orderIdx+'" class="public_input w450"/>';
		h+=' <label>分数：</label><select id="sel_score_parent_'+orderIdx+'"name="sel_score_'+orderIdx+'">';
		for(i=0;i<=maxscore;i++)
			h+='<option value="'+i+'">'+i+'</option>';
		h+='</select> ';
		h+=' <a href="javascript:void(\'\')" onclick="delInputParent(\'div_ques_'+orderIdx+'\')" class="F_blue">删除</a></p>';
		h+='<p class="dj" id="tr'+orderIdx+'_answer_1"><label>等级1</label>&nbsp;';
		h+='<input type="text" id="answer'+orderIdx+'_1" class="public_input w410"/>&nbsp;';
		h+='<label>分数：</label><select onchange="changescore(this,'+orderIdx+')" id="sel_score1_'+orderIdx+'_1" name="sel_score_'+orderIdx+'">';
		for(i=0;i<=maxscore;i++)
			h+='<option value="'+i+'">'+i+'</option>';
		h+='</select>';
		h+=' <a href="javascript:addinput_level('+orderIdx+')"><img width="16" height="16" border="0" src="images/an38_130705.png" title="添加"></a>&nbsp;&nbsp;';
		h+='<a href="javascript:removeinput_level('+orderIdx+',1)"><img width="16" height="17" border="0" src="images/an39_130705.png" title="删除"></a>';
		h+='</p></div>';
		 
	if(len<1){
		$("#addQuesP").after(h);
		$("#addQuesP").insertAfter($("#div_ques_"+orderIdx));
	}else
		divArrayObj.last().after(h);
	//添加特殊字符验证
	clearSpecialCharacter();
}

function changescore(sel,idx){	
  	var levelscore = 0;
	var scoreparent=$("#sel_score_parent_"+idx).val(); 
	levelscore=parseInt(sel.value);
	if(levelscore>parseInt(scoreparent)){
		alert("当前设置等级分数已超过总分，请调整后再操作");
		sel.focus();
		sel.value="0";
	}
}

/**
 * 删除 评价题目
 * @param divid
 * @return
 */
function delInputParent(divid){
	if(typeof(divid)=="undefined"||divid.Trim().length<1){
		alert('无法删除，异常错误，请刷新页面后重试!');
		return;
	}
	var inputArray=$("#"+divid+" input[type='text']");
	var flag=false;
	$.each(inputArray,function(idx,itm){
		if(itm.value.Trim().length>0){
			flag=true;
			return;
		}
	});
	if(flag){
		if(!confirm("您要删除的选框中尚存在数据，你确认要删除吗?"))
			return;
	}
	$("#"+divid).remove();	
	var divArrayObj=$("div").filter(function(){return this.id.indexOf('div_ques_')!=-1});
	var len=divArrayObj.length;
	if(len<1)
		addInputParent();
	else
		updateQuesNo();
}
/**
 * 添加选框，等级
 * @param parentref
 * @param currentidx
 * @return
 */
function addinput_level(parentref){
	if(typeof(parentref)=="undefined"||isNaN(parentref)){
		alert('无法删除，异常错误，请刷新页面后重试!');
		return;
	}
	var answerpArray=$("p").filter(function(){return this.id.indexOf('tr'+parentref+'_answer_')!=-1});
	var len=answerpArray.length;
	var h='<p class="dj" id="tr'+parentref+'_answer_'+(len+1)+'"><label>等级1</label>&nbsp;';
		h+='<input type="text"  id="answer'+parentref+'_'+(len+1)+'" class="public_input w410"/>&nbsp;';
		h+='<label>分数：<label><select onchange="changescore(this,'+parentref+')" id="sel_score1_'+parentref+'_'+(len+1)+'" name="sel_score_'+parentref+'">';
		for(i=0;i<=maxscore;i++)
			h+='<option value="'+i+'">'+i+'</option>'; 
		h+='</select>'; 
		h+=' <a href="javascript:addinput_level('+parentref+')"><img width="16" height="16" border="0" src="images/an38_130705.png" title="添加"></a>&nbsp;&nbsp;';
		h+='<a href="javascript:removeinput_level('+parentref+','+(len+1)+')"><img width="16" height="17" border="0" src="images/an39_130705.png" title="删除"></a></p>';
	if(len<1){
		$("#tr"+parentref+"_first").after(h);    
	}else
		answerpArray.last().after(h);  
	//添加特殊字符验证
	clearSpecialCharacter();	
	updateLevelNo(parentref);
}
/**
 * 删除选框，等级
 * @param parentref 
 * @param idx
 * @return
 */
function removeinput_level(parentref,idx){
	var inputArray=$("#tr"+parentref+"_answer_"+idx+" input[type='text']");
	var flag=false;
	$.each(inputArray,function(idx,itm){
		if(itm.value.Trim().length>0){
			flag=true;
			return;
		}
	});
	if(flag){
		if(!confirm("您要删除的等级中尚存在数据，你确认要删除吗?"))
			return;
	}
	$("#tr"+parentref+"_answer_"+idx).remove();
	//验证
	var p=$("p").filter(function(){return this.id.indexOf('tr'+parentref+'_answer_')!=-1});
	if(p.length<1)
		addinput_level(parentref);
	else
		updateLevelNo(parentref);
}
/**
 * 修改等级的题号
 * @param parentref
 * @return
 */
function updateLevelNo(parentref){
	var p=$("p").filter(function(){return this.id.indexOf('tr'+parentref+'_answer_')!=-1});
	$.each(p,function(idx,itm){
		$(this).children('label').first().html('等级'+(idx+1));
	})
}


/**
 * 修改评价索引题号
 * @return
 */
function updateQuesNo(){
	var divArrayObj=$("div").filter(function(){return this.id.indexOf('div_ques_')!=-1});
	if(divArrayObj.length>0){
		$.each(divArrayObj,function(idx,itm){
			$(this).children("p").filter(function(){return this.id.indexOf('_first')!=-1}).children("label").first().html('评价'+(idx+1));
		});		
	}
	
}


/**
 * 执行
 * @return
 */
function doOperateItem(){
	if(baseref.Trim().length<1){
		alert('异常错误!系统未获取到您要操作的评价信息! 错误代码：baseref is failed!');
		return;
	}
	if(pjtype.Trim().length<1){
		alert('异常错误!系统未获取到您要操作的评价类型! 错误代码：pjtype is failed!');
		return;
	}
	//获取大题
	var txtQues=$("input[type='text']").filter(function(){return this.id.indexOf('investname')!=-1});
	if(txtQues.length<1){
		alert('异常错误，系统未获取到您所填写的评价!错误代码: txtQues is failed!');
		return;
	}
	var flag=false;
	$.each(txtQues,function(idx,itm){
		if(itm.value.Trim().length<1){
			flag=true;
			itm.focus();
			return;
		}
	});
	if(flag){
		alert('异常错误，系统检测到，您还有大题没有填写，请填写或者删除!');
		return;
	}
	//大题的参数设定
	var pjparam='t=1';
	//id参数设定
	var pjparentid='';
	//level参数设定
	var levelparam='t=1';
	//levelscore参数设定
	var levelscoreparam='';
	//大题score参数设定
	var pjscoreparam=''; 
	flag=false;hasempty=false;levelscoreflag=false;scoreparentflag=false;
	var pjname = "";
	var pjlevel = "";
	var pjscore ="";
	var pjlevelscore="";
	$.each(txtQues,function(idx,itm){
		var id=itm.id.substring(itm.id.indexOf('investname')+10);
		pjname+=itm.value;
		if(idx<txtQues.length-1)
			pjname+="|";
			
		//获取等级
		var answerObjArray=$("input[type='text']").filter(function(){return this.id.indexOf('answer'+id+'_')!=-1});
		if(answerObjArray.length<1){
			flag=true;
			return;
		}
		//设置 pjparentid参数
		if(id.Trim().length>0)
		{
			if(pjparentid.Trim().length<1)
				pjparentid=id;
			else
				pjparentid+="|"+id;
		}
		//得到总分数
		var scoreparent=$("#sel_score_parent_"+id);
		if(scoreparent.length<1){
			scoreparentflag=true;
			return;
		}
		pjscore+=scoreparent[0].value;
		if(idx<txtQues.length-1){
			pjscore+="|";
		}
			
		///////////////////////////////////////等级
		//设置  pjparent参数
		//pjparam+='&pjparent'+id+'='+itm.value.Trim();
		//验证 并 设置  pjchild参数
		$.each(answerObjArray,function(ix,im){
			if(im.value.Trim().length<1){
				hasempty=true;
				im.focus();
				return;
			}	
			pjlevel+=im.value;
			if(ix<answerObjArray.length-1){
				pjlevel+="|";
			}
		});
		///////////////////////////////////////等级分数
		var levelScoreArray=$("select").filter(function(){return this.id.indexOf("sel_score1_"+id+"_")!=-1});
		if(levelScoreArray.length<1){
			levelscoreflag=true;
			return;
		}
		$.each(levelScoreArray,function(i,m){
			pjlevelscore+=m.value;
			if(i<levelScoreArray.length-1){
				pjlevelscore+="|";
			}
		});	
		if(idx<txtQues.length-1){
			pjlevel+="*";
			pjlevelscore+="*";
		}		
	});
	if(flag||pjname.Trim().length<1){
		alert('系统异常错误，读取等级出错!错误代码：answerObjArray.length < 1 ');
		return;
	}
	if(hasempty||pjlevel.Trim().length<4){
		alert('系统检测到，您还有等级没有填写，请填写或者删除!');
		return;
	}
	if(levelscoreflag||pjlevelscore.length<1){
		alert('系统异常错误，读取等级分数出错！错误代码：levelScoreArray.length<1');
		return;
	}
	if(scoreparentflag||pjscore.length<1){
		alert('系统异常错误，读取总分出错！错误代码：scoreparent.length<1');
		return;
	}
	if(pjparentid.length<1){
		alert('异常错误，错误原因：未知! 错误代码：pjparentid is empty！');
	}
	//得到备注并执行AJAX请求
	var remarkObj=$("#remark");
	if(remarkObj.length<1){
		alert('异常错误，系统尚未获取到备注信息!请刷新页面重试!');
	}
	if(remarkObj.val().Trim().length<1){
		alert('您尚未填写备注信息，请填写!');
		remarkObj.focus();
		return;
	}
	if(remarkObj.val().Trim().length>500){
		alert('请重新输入备注，备注的长度过长!请限制在500字以内!');
		remarkObj.focus();
		return;
	}
	if(!confirm("信息验证成功!您确认进行数据更新操作吗?\n\n提示：评价开始或结束后，所有人都无法修改评价项目了哦!"))
		return;
	var remark=replaceAll(replaceAll(replaceAll(replaceAll(remarkObj.val(),'\'','‘'),'=','＝'),"?","？"),"%","％"); 
	
	var param={};
	
	param.pjname=pjname;
	param.pjlevel=pjlevel;
	param.pjscore = pjscore;
	param.pjlevelscore=pjlevelscore;
	param.remark=remark;
	param.peerbaseref=baseref;
	param.ptype=pjtype;
	$.ajax({
		url:'peeritem?m=dosave',
		dataType:'json',
		type:'post',
		data:param,
		cache: false,
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			alert(rps.msg);
			//pageGo('p1');
		}
	});
}

/**
 * 年份更换的时候
 * @param obj
 * @return
 */
function  itemyearChange(obj){
//	baseref = obj.value.Trim();
//	if(obj.value.Trim().length<1||obj.value.Trim()=="-1"){
//		return;
//	}
//	$.ajax({
//		url: 'peeritem?m=list',
//			type:'post',
//			dataType:'json', 
//			cache: false,
//			data:{peerbaseref:baseref,
//					ptype:ptype},
//			error:function(){
//				alert('程序异常!');
//			},success:function(rmsg){
//				
//			}			
//		});	
}

/**
 * 预览
 * @return
 */
function operateitemView(){
	var type = $("input[type='radio']").filter(function(){return this.checked==true});
	if(baseref.Trim().length<1){
		alert('异常错误!系统未获取到您要操作的评价信息! 错误代码：baseref is failed!');
		return;
	}
	if(pjtype.Trim().length<1){
		alert('异常错误!系统未获取到您要操作的评价类型! 错误代码：pjtype is failed!');
		return;
	}
	//获取大题
	var txtQues=$("input[type='text']").filter(function(){return this.id.indexOf('investname')!=-1});
	if(txtQues.length<1){
		alert('异常错误，系统未获取到您所填写的评价!错误代码: txtQues is failed!');
		return;
	}
	var flag=false;
	$.each(txtQues,function(idx,itm){
		if(itm.value.Trim().length<1){
			flag=true;
			itm.focus();
			return;
		}
	});
	if(flag){
		alert('异常错误，系统检测到，您还有大题没有填写，请填写或者删除!');
		return;
	}
	//大题的参数设定
	var pjparam='t=1';
	//id参数设定
	var pjparentid='';
	//level参数设定
	var levelparam='t=1';
	//levelscore参数设定
	var levelscoreparam='';
	//大题score参数设定
	var pjscoreparam='';
	//最终成生的FORM并提交
	var frmName=new Date().getTime();
	var genderform='<form style="display:none" action="peeritem?m=itemview" id="TARGET_FRM_'+frmName+'" method="post" target="_BLANK">';
	flag=false;hasempty=false;levelscoreflag=false;scoreparentflag=false;
	genderform+='<input type="hidden" name="peerbaseref" value="'+baseref+'"/>';
	genderform+='<input type="hidden" name="ptype" value="'+type.val()+'"/>';
	$.each(txtQues,function(idx,itm){
		var id=itm.id.substring(itm.id.indexOf('investname')+10);
		
		//获取等级
		var answerObjArray=$("input[type='text']").filter(function(){return this.id.indexOf('answer'+id+'_')!=-1});
		if(answerObjArray.length<1){
			flag=true;
			return;
		}
		//设置 pjparentid参数
		if(id.Trim().length>0)
		{
			if(pjparentid.Trim().length<1)
				pjparentid=id;
			else
				pjparentid+="|"+id;
		}
		//得到总分数
		var scoreparent=$("#sel_score_parent_"+id);
		if(scoreparent.length<1){
			scoreparentflag=true;
			return;
		}else{
			genderform+='<input type="text" name="pjscore'+id+'" value="'+scoreparent.val().Trim()+'"/>';
		}
			
		///////////////////////////////////////等级
		//设置  大的评价项内容参数		
		genderform+='<input type="text" name="pjparent'+id+'" value="'+replaceAll(itm.value.Trim(),"|","丨")+'"/>';
		//验证 并 设置  pjchild参数
		$.each(answerObjArray,function(ix,im){
			if(im.value.Trim().length<1){
				hasempty=true;
				im.focus();
				return;
			}
			
			
			genderform+='<input type="text" name="pjchild'+id+'" value="'+replaceAll(im.value.Trim(),"|","丨")+'"/>';
		});
		///////////////////////////////////////等级分数
		var levelScoreArray=$("select").filter(function(){return this.id.indexOf("sel_score1_"+id+"_")!=-1});
		if(levelScoreArray.length<1){
			levelscoreflag=true;
			return;
		}
		$.each(levelScoreArray,function(i,m){			
			
			genderform+='<input type="text" name="levelscore'+id+'" value="'+m.value.Trim()+'"/>';
		});		
		//pjlevel+="*";pjlevelscore+="*";
	});
	if(flag){
		alert('系统异常错误，读取等级出错!错误代码：answerObjArray.length < 1 ');
		return;
	}
	if(hasempty){
		alert('系统检测到，您还有等级没有填写，请填写或者删除!');
		return;
	}
	if(levelscoreflag){
		alert('系统异常错误，读取等级分数出错！错误代码：levelScoreArray.length<1');
		return;
	}
	if(scoreparentflag){
		alert('系统异常错误，读取总分出错！错误代码：scoreparent.length<1');
		return;
	}
	if(pjparentid.length<1){
		alert('异常错误，错误原因：未知! 错误代码：pjparentid is empty！');
		return;
	}
	//得到备注并执行AJAX请求
	var remarkObj=$("#remark");
	if(remarkObj.length<1){
		alert('异常错误，系统尚未获取到备注信息!请刷新页面重试!');
	}
	if(remarkObj.val().Trim().length<1){
		alert('您尚未填写备注信息，请填写!');
		remarkObj.focus();
		return;
	}
	if(remarkObj.val().Trim().length>500){
		alert('请重新输入备注，备注的长度过长!请限制在500字以内!');
		remarkObj.focus();
		return;
	}
	var remark=replaceAll(replaceAll(replaceAll(replaceAll(remarkObj.val(),'\'','‘'),'=','＝'),"?","？"),"%","％"); 
	//数据验证完毕
	genderform+='<textarea name="remark">'+remark+'</textarea>';
	genderform+='<input type="text" name="pjid" value="'+pjparentid+'"/>';
	genderform+='</form>';
	
	$("form").filter(function(){return this.id.indexOf('TARGET_FRM_')!=-1}).remove();
	
	
	$("body").append(genderform);
	/**
	 * 提交并去查看
	 */
	$("form[id='TARGET_FRM_"+frmName+"']").submit();
}

