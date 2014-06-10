 /**
     * 根据年级查询班级信息
     */
    function getClasssByGrade(){
        var termId = $("#termId");
        var grade = $("#grade");
        if(termId.val().Trim() =="")
            return;
        if(grade.val().Trim() =="") 
            return;
        var wkid=$("#weekId");
        if(wkid.val().Trim()==""||wkid.val().Trim()=="-1")
        	return;
        clearClassOption();
        var year=document.getElementById("termId").options[document.getElementById("termId").selectedIndex].text.split(' ')[0];
        //alert(year+"  "+grade.val().Trim()+"   ");
        $.ajax({
			url:"cls?m=ajaxlist",
			dataType:'json',
			type:"post",
			cache: false,
			 data:{year:year,
			      classgrade:grade.val(),
			      pattern:'行政班'
			    }, 
			error:function(){
				alert('系统未响应，请稍候重试!');
			},
			
			success:function(json){			    
			    if(json.type == "success"){
			    	var tmpht="<option value=''>==请选择==</option>";			        
			        $.each(json.objList,function(idx,item){
					    tmpht+="<option value='"+item.classid+"'>"+item.classname+"</option>";
				    });				    
				    $("#classId").html(tmpht);
			    }else{
			        alert(json.msg);
			    }                
			}				
		});
    }
function clearClassOption(isgray){       	
   		
   		//禁止使用文本
		   //禁用文本区域。        
        $("textarea").attr("readonly",true);
        $("input[type='text']").attr("readonly",true);;
        $("textarea").val('');
   		$("input[type='text']").val('');
   		 
   		$("p a img").filter(function(){return $(this).attr("groupType")=="operateBtn"})
   			.each(function(){
   			var src=$(this).attr("src");   			
   			if(src.indexOf("_g")==-1)
	   			src=src.substring(0,src.indexOf("."))+"_g"+src.substring(src.indexOf('.')); 
   			$(this).attr("src",src);
   		});
   		$("p a img").filter(function(){return $(this).attr("groupType")=="operateBtn"}).parent().unbind("click");
   	//初始化CLassId Select
		if(typeof(isgray)=="undefined"||isgray){
			$("#classId").html('<option>==请选择==</option>');			
		}
		try{
		if($("#termId").val().Trim().length>0
				&&$("#weekId").val().Trim().length>0&&$("#grade").val().Trim().length>0){
			var srcV=$("#gradeView").children()[0].src;
			$("#gradeView").children()[0].src=srcV.replace("_g","");
			$("#gradeView").bind("click",function(){			
				eval($(this).attr("action"));
			});
		}
		}catch(e){}
   	}
/**
 * 根据学期，获取对应的周次
 * @param isautoSearch
 * @return
 */
function getTermWeeks(isautoSearch){        
//	 var termId = $("#termId");
//        if(termId.val().Trim() ==""){
//         $("#weekId").empty();
//            return;
//        }
//       // clearClassOption();
//     
//        $("textarea").attr("readonly",true);
//        $("input[type='text']").attr("readonly",true);;
//        $("textarea").val('');
//   		$("input[type='text']").val('');
//   		
//        var year=document.getElementById("termId").options[document.getElementById("termId").selectedIndex].text.split(' ')[0];
//        $("#year").val(year);
//        $.ajax({
//			url:"week!getWeeksByTerm.action",
//			type:"post",
//			data:{termid:termId.val().Trim()},
//			dataType:'json',
//			cache: false,
//			error:function(){
//				alert('系统未响应，请稍候重试!');
//			},
//			success:function(json){
//			    if(json.type == "success"){
//			    	var tmpht="<option value=''>==请选择==</option>";			        
//			        $.each(json.objList,function(idx,item){
//					    tmpht+="<option value='"+item.ref+"'>"+item.weekname+"</option>";
//				    });				    
//				    $("#weekId").html(tmpht);
//				    if(typeof(isautoSearch)!="undefined"&&isautoSearch){
//				    	//开始查询初始化统计数据
//				    	setTimeout(function() { 
//				    		adminSearchClassDate('dataTbl',false,false); 
//				    	},1);
//				    }
//			    }else{
//			        alert(json.msg);
//			    }                
//			}				
//		});
    }
/**
 * 获取班级的校风
 * @return
 */
function getClassXFDetail(){
	var wk=$("#weekId");
	if(wk.val().Trim().length<1){
		alert('您尚未选择周次情况，请选择!\n\n提示：学期直接关联周次的情况，请先确认学期!');
		wk.focus();
		return;
	}
	var grade=$("#grade");
	if(grade.val().Trim().length<1){
		alert('您尚未选择年级情况，请选择!');;
		grade.focus();
		return;
	}
	var cls=$("#classId");
	if(cls.val().Trim().length<1){
		alert('您尚未选择班级情况，请选择！');
		cls.focus();
		return;    		
	}

//	初始化空间
//	$("#dataTbl").html('');   
	doclearforclass(); 
				//将文本填写区域设置不可用
	$("#assemblyremark").attr("readonly",true);
	$("#assemblyscore").attr("readonly",true);
	//卫生情况
	$("#hygieneremark").attr("readonly",true);
	$("#hygienescore").attr("readonly",true);
	//财产情况
	$("#moneyremark").attr("readonly",true);
	$("#moneyscore").attr("readonly",true);						
	//宿舍集体情况
	$("#dormitoryremark").attr("readonly",true);;
	$("#dormitoryscore").attr("readonly",true);
	//其他情况
	$("#otherremark").attr("readonly",true);
	$("#otherscore").attr("readonly",true);
	//奖励扣分
	$("#awardremark").attr("readonly",true);
	$("#awardscore").attr("readonly",true)
	       //将按钮设置不可用。		;
//	$("#doadd").attr("disabled",true);
//	$("#doupdate").attr("disabled",true);
//	$("#dodelete").attr("disabled",true);
	$("p a img").filter(function(){return $(this).attr("groupType")=="operateBtn"})
			.each(function(){
			var src=$(this).attr("src");
			if(src.indexOf("_g")!=-1)
   			src=src.replace("_g","");	
			$(this).attr("src",src);
		});
	
	$("p a img").filter(function(){return $(this).attr("groupType")=="operateBtn"}).parent()
		.bind("click",function(){
		eval('('+$(this).attr("action")+')');
	});

	
	$.ajax({
		url:'classethos?m=loadethos',
		type:'post',
		dataType:'json',
		cache: false,
		data:{
			weekid:wk.val().Trim(), 
			grade:grade.val().Trim(),
			classid:cls.val().Trim()
		},
		error:function(){
			alert('系统未响应，请稍候重试!');
		},success:function(rjson){
			
			if(rjson.type=="error"){
				alert(rjson.msg);
				return;
			}else{			
				//付参数
				if(rjson.objList.length>0){					
					$.each(rjson.objList,function(idx,itm){
						//集会情况
						$("#assemblyremark").val(itm.assemblyremark);
						var assemscore='';
						if(typeof(itm.assemblyscore)!="undefined"&&!isNaN(itm.assemblyscore.toString().replace('-','')))		
							assemscore=(parseFloat(itm.assemblyscore)>0?'+':'')+itm.assemblyscore;
						$("#assemblyscore").val(assemscore);
						//卫生情况
						$("#hygieneremark").val(itm.hygieneremark);
						var hyscore='';
						if(typeof(itm.hygienescore)!="undefined"&&!isNaN(itm.hygienescore.toString().replace('-','')))	
							hyscore=(parseFloat(itm.hygienescore)>0?'+':'')+itm.hygienescore;
						$("#hygienescore").val(hyscore);
						//财产情况
						$("#moneyremark").val(itm.moneyremark); 
						var monscore='';
						if(typeof(itm.moneyscore)!="undefined"&&!isNaN(itm.moneyscore.toString().replace('-','')))
							monscore=(parseFloat(itm.moneyscore)>0?'+':'')+itm.moneyscore;
						$("#moneyscore").val(monscore);						
						//宿舍集体情况
						$("#dormitoryremark").val(itm.dormitoryremark); 
						var dormscore='';
						if(typeof(itm.dormitoryscore)!="undefined"&&!isNaN(itm.dormitoryscore.toString().replace('-','')))
							dormscore=(parseFloat(itm.dormitoryscore)>0?'+':'')+itm.dormitoryscore;
						$("#dormitoryscore").val(dormscore);
						//其他情况
						$("#otherremark").val(itm.otherremark);
						var othscore='';
						if(typeof(itm.otherscore)!="undefined"&&!isNaN(itm.otherscore.toString().replace('-','')))
							othscore=(parseFloat(itm.otherscore)>0?'+':'')+itm.otherscore;
						$("#otherscore").val(othscore);
						//奖励扣分
						$("#awardremark").val(itm.awardremark);
						var awarscore=''; 
						if(typeof(itm.awardscore)!="undefined"&&!isNaN(itm.awardscore.toString().replace('-','')))
							awarscore=(parseFloat(itm.awardscore)>0?'+':'')+itm.awardscore;
						$("#awardscore").val(awarscore);	
						//考勤
				 		var kqremark="";
						if(typeof(itm.kqremark)!="undefined")
							kqremark=itm.kqremark;
						$("#kqremark").val(kqremark);
						//考勤分数
						var kqscore="";
						if(typeof(itm.kqscore)!="undefined")
							kqscore=itm.kqscore;
						$("#kqscore").val(kqscore);
						
						//违纪
						var wjremark="";
						if(typeof(itm.wjremark)!="undefined")
							wjremark=itm.wjremark;
						$("#wjremark").val(wjremark);
						//违纪分数
						var wjscore="";
						if(typeof(itm.wjscore)!="undefined")
							wjscore=itm.wjscore;
						$("#wjscore").val(wjscore);		
						//好人好事
						var gdremark="";
						if(typeof(itm.gdremark)!="undefined")
							gdremark=itm.gdremark;
						$("#gdremark").val(gdremark);
						//违纪分数
						var gdscore="";
						if(typeof(itm.gdscore)!="undefined")
							gdscore=itm.gdscore;
						$("#gdscore").val(gdscore);		
						
						$("#doadd").attr("disabled",true); 
						$("#doupdate").removeAttr("disabled");
						$("#doclear").removeAttr("disabled",true);
						adminSearchClassDate('dataTbl',false,false);
					});					 
				}else{
					$("#doadd").removeAttr("disabled");
					$("#doupdate").attr("disabled",true);					
					$("textarea").val('');
				}
				
				//启用控件
				$("#assemblyremark").attr("readonly",false);
				$("#assemblyscore").attr("readonly",false);
				//卫生情况
				$("#hygieneremark").attr("readonly",false);
				$("#hygienescore").attr("readonly",false);
				//财产情况
				$("#moneyremark").attr("readonly",false);
				$("#moneyscore").attr("readonly",false);						
				//宿舍集体情况  
				$("#dormitoryremark").attr("readonly",false);;
				$("#dormitoryscore").attr("readonly",false);
				//其他情况
				$("#otherremark").attr("readonly",false);
				$("#otherscore").attr("readonly",false); 
				//奖励扣分
				//$("#awardremark").attr("readonly",false);
				//$("#awardscore").attr("readonly",false);	
				
				getClsPosition(false);
			}
		}
	});
}
/**
 * 添加或者修改班级校风
 * @return
 */
function doAddOrUpdateClassXF(){
	  var wk=$("#weekId");
	if(wk.val().Trim().length<1){
		alert('您尚未选择周次情况，请选择!\n\n提示：学期直接关联周次的情况，请先确认学期!');
		wk.focus();
		return;
	}
	var grade=$("#grade");
	if(grade.val().Trim().length<1){
		alert('您尚未选择年级情况，请选择!');
		grade.focus();
		return;
	}
	var cls=$("#classId");
	if(cls.val().Trim().length<1){
		alert('您尚未选择班级情况，请选择！');
		cls.focus();
		return;    		
	}
	var assemblyremark	=$("#assemblyremark").val();
	var assemblyscore=$("#assemblyscore").val();
	if(assemblyscore.Trim().length>0&&isNaN(assemblyscore.Trim().replace('+','').replace('-',''))){  
		alert('集会分数必须为整数，请正确输入！');
		$("#assemblyscore").focus();
		return;
	}	
	if(assemblyscore.Trim().replace('+','').replace('-','').length>5){
		alert('集会分数不能大于99999，请正确输入！');
		$("#assemblyscore").focus();
		return;
	}
	
	//卫生情况
	var hygieneremark=$("#hygieneremark").val(); 
	var hygienescore=$("#hygienescore").val();
	if(hygienescore.Trim().length>0&&isNaN(hygienescore.Trim().replace('+','').replace('-',''))){
		alert('卫生分数必须为整数，请正确输入！');
		$("#hygienescore").focus();
		return;
	}
	if(hygienescore.Trim().replace('+','').replace('-','').length>5){
		alert('卫生分数不能大于99999，请正确输入！');
		$("#hygienescore").focus();
		return;
	}
	
	//财产情况
	var moneyremark=$("#moneyremark").val();
	var moneyscore=$("#moneyscore").val();		
	if(moneyscore.Trim().length>0&&isNaN(moneyscore.Trim().replace('+','').replace('-',''))){
		alert("财产分数必须为整数，请正确输入！\n\n提示：‘+’表示加分，‘-'表示扣分");
		$("#moneyscore").focus();
		return;
	}
	
	if(moneyscore.Trim().replace('+','').replace('-','').length>5){
		alert('财产分数不能大于99999，请正确输入！');
		$("#moneyscore").focus();
		return;
	}	
	
	//宿舍集体情况
	var dormitoryremark=$("#dormitoryremark").val();
	var dormitoryscore=$("#dormitoryscore").val();
	if(dormitoryscore.Trim().length>0&&isNaN(dormitoryscore.Trim().replace('+','').replace('-',''))){
		alert("宿舍集体分数必须为整数，请正确输入！\n\n提示：‘+’表示加分，‘-'表示扣分");
		$("#dormitoryscore").focus();
		return;
	}
	if(dormitoryscore.Trim().replace('+','').replace('-','').length>5){
		alert('宿舍集体分数不能大于99999，请正确输入！');
		$("#dormitoryscore").focus();
		return;
	}
	
	//其他情况
	var otherremark=$("#otherremark").val();
	var otherscore=$("#otherscore").val();
	if(otherscore.Trim().length>0&&isNaN(otherscore.Trim().replace('+','').replace('-',''))){
		alert("其他分数必须为整数，请正确输入！\n\n提示：‘+’表示加分，‘-'表示扣分");
		$("#otherscore").focus();
		return;
	}
	
	if(otherscore.Trim().replace('+','').replace('-','').length>5){
		alert('其他分数不能大于99999，请正确输入！');
		$("#otherscore").focus();
		return;
	}
	
	//奖励
	var awardremark=$("#awardremark").val();
	var awardscore=$("#awardscore").val();	
	if(awardscore.Trim().length>0&&isNaN(awardscore.Trim().replace('+','').replace('-',''))){
		alert("奖励分数必须为整数，请正确输入！\n\n提示：‘+’表示加分，‘-'表示扣分");
		$("#awardscore").focus();
		return;
	}
	if(awardscore.Trim().replace('+','').replace('-','').length>5){
		alert('奖励分数不能大于99999，请正确输入！');
		$("#awardscore").focus();  
		return;
	}
	
	if(assemblyremark.Trim().length<1&&assemblyscore.Trim().length<1&&
			hygieneremark.Trim().length<1&&hygienescore.Trim().length<1&&
			moneyremark.Trim().length<1&&moneyscore.Trim().length<1&&
			dormitoryremark.Trim().length<1&&dormitoryscore.Trim().length<1&&
			otherremark.Trim().length<1&&otherscore.Trim().length<1&&
			awardremark.Trim().length<1&&awardscore.Trim().length<1
	){
		alert('您尚未对该班级输入任何扣分奖励情况，无法进行录入！请输入信息后点击！');
		return;
		
	}
	if(assemblyremark.length<1){
		assemblyremark='';
	}
	if(assemblyscore.length<1){
		assemblyscore=0;
	}
	if(hygieneremark.length<1){
		hygieneremark='';
	}
	if(hygienescore.length<1){
		hygienescore=0;
	}
	if(moneyremark.length<1){
		moneyremark='';
	}
	if(moneyscore.length<1){
		moneyscore=0;
	}
	if(dormitoryremark.length<1){
		dormitoryremark='';
	}
	if(dormitoryscore.length<1){
		dormitoryscore=0;
	}
	if(otherremark.length<1){
		otherremark='';
	}
	if(otherscore.length<1){
		otherscore=0;
	}
	if(awardremark.length<1){
		awardremark='';
	}
	if(awardscore.length<1){
		awardscore=0;
	}
	//添加或者修改班级校风。
	if(!confirm("您确定该操作吗？")){
		return;
	}
	$.ajax({
		url:'classethos?m=ajaxsave',
		type:'post',
		dataType:'json',
		cache: false,
		data:{
			weekid:wk.val().Trim(),
			grade:grade.val().Trim(),
			classid:cls.val(),
			
			assemblyremark:assemblyremark,
			assemblyscore:assemblyscore,
			hygieneremark:hygieneremark,
			hygienescore:hygienescore,
			moneyremark:moneyremark,
			moneyscore:moneyscore,
			dormitoryremark:dormitoryremark,
			dormitoryscore:dormitoryscore,
			otherremark:otherremark,
			otherscore:otherscore,
			awardremark:awardremark,
			awardscore:awardscore
		},
		error:function(){
			alert('系统未响应，请稍候重试!');
		},success:function(rjson){
			if(rjson.type=="success"){
				alert(rjson.msg);
				adminSearchClassDate('dataTbl',false,false);
			}else{
				alert(rjson.msg);
			}
		}
	});    	
}
/**
 * 清空
 * @return
 */
function doclearforclass(){
	  
		//集会情况
		$("#assemblyremark").val('');
		$("#assemblyscore").val('');
		//卫生情况
		$("#hygieneremark").val('');
		$("#hygienescore").val('');
		//财产情况
		$("#moneyremark").val('');
		$("#moneyscore").val('');				
		//宿舍集体情况
		$("#dormitoryremark").val('');
		$("#dormitoryscore").val('');
		//其他情况
		$("#otherremark").val('');
		$("#otherscore").val('');
		//奖励扣分
		$("#awardremark").val('');
		$("#awardscore").val('');
}
/**
 * 管理员查询班级统计情况
 * @param showAddress 数据显示的地址
 * @param iscludeclass 是否加载班级作为参数
 * @param isshowCk 是否显示复选框列
 * @return
 */
function adminSearchClassDate(showAddress,iscludeclass,isshowCk){
	  $("#"+showAddress).html('');
	  
//	  if(classObj.val().Trim().length<1){
//		  return;		  
//	  }
//	  
	  var termObj=$("#termId");
	  if(termObj.val().Trim().length<1){
		  if(document.getElementById("grade").options.length<2){
			  alert('您尚未选择学期，无法选择学期，请先选择学期！');
			  termObj.focus();
			  return;  
		  }else{
			  $("#termId option").filter(function(){return this.value.Trim().length>0&&this.value.Trim()!="-1"})
		  	  .first().attr("selected",true);
		  }
	  }
	
	  var weekObj=$("#weekId");
	  if(weekObj.val().Trim().length<1){	
		  if(document.getElementById("weekId").options.length<2){			  
			  alert('您尚未选择周次，无法进行查询统计，请先选择周次！');
			  weekObj.focus();
			  return;  
		  }else{		
		  	  $("#weekId option").filter(function(){return this.value.Trim().length>0&&this.value.Trim()!="-1"})
		  	  .first().attr("selected",true);
		  }
	  }

	  var gradeObj=$("#grade");
	
	  if(gradeObj.val().Trim().length<1){
		  if(document.getElementById("grade").options.length<2){
			  alert('您尚未选择年级，无法选择班级，请先选择年级！');
			  gradeObj.focus();
			  return;  
		  }else{		
			  	  $("#grade option").filter(function(){return this.value.Trim().length>0&&this.value.Trim()!="-1"}).first()
			  	  .attr("selected",true);			
				  getClasssByGrade();	
			 
		  }
	  }
	 
	 //根据显示的TermId，字符串，进行分割，
	  var grade=gradeObj.val().Trim();
	  var weekid=weekObj.val().Trim();
	  //var classid=classObj.val().Trim(); 
	  if(typeof(iscludeclass)!="undefined"&&iscludeclass==false){
		  classid='';
	  }
	 
	  url='classethos?m=getClassEthosList';
	  if(classid.length>0){
		  url+='&classid='+classid;
	  }
	  $.ajax({
		  url:url,
		  type:'post',
		  dataType:'json',
		  cache: false,
		  data:{
		  termid:termObj.val().Trim(),
		  		grade:grade,
		  		weekid:weekid		  		
	  		},
		  error:function(){
			alert('系统未响应，请稍候重试!');
			$("#"+showAddress).html("<tr><td align='center'>系统未响应，请稍候重试!</td></tr>");
		  },success:function(rjson){
			if(rjson.type=="success"){
				var showObj="<tr><td align='center'>没有该班级的数据信息!</td></tr>";
				var coLength=0;
				if(rjson.objList.length>0){
					
					
					showObj="<tr><th id='ckAll'>&nbsp;<input type='checkbox' name='checkbox4' value='checkbox' /> </th>" +
							"<th>班级</th><th>集会分数</th><th>卫生分数</th><th>财产分数</th><th>宿舍集体分</th><th>其他分数</th><th>考勤汇总分</th>" +
							"<th>违纪汇总分</th><th>好人好事</th><th>奖励加分</th><th>总分</th><th>排名</th></tr>";
					//循环行
					$.each(rjson.objList,function(idx,itm){						
						if(typeof(itm)=="object"){
							
							showObj+='<tr id="tr_'+itm[0]+'">'; 
							//循环列
							$.each(itm,function(ix,im){ 
								//alert(im);
								if(ix==0)						
									showObj+='<td><input type="checkbox" name="seldel" value="'+im+'"/></td>';
									
								showObj+='<td>'+im+'</td>';
								if(coLength>ix)
									coLength=ix;
							});	
							showObj+='</tr>';
						}	 
						
					});
					$("#doupdate").removeAttr("disabled");
					$("#doadd").attr("disabled",true);		   
		       		$("#dodelete").removeAttr("disabled");		       		
				}else{
					//没有数据，初始化
		       		$("#doadd").removeAttr("disabled");
		       		$("#doupdate").attr("disabled",true);
		       		$("#dodelete").attr("disabled",true);
				}
				$("#dataColumn").show();
				$("#"+showAddress).html(showObj);
				//如果行数大于13.出横向滚动条
							
				
				$("#"+showAddress+" tr").hover( 
					function() {
					    $(this).addClass("Trbg1");
				    },
				    function() {
				     $(this).removeClass("Trbg1");
				    }
				 )
				$("#ckAll").show();
				 getClsPosition(isshowCk);
			}else{
				alert(rjson.msg);
			}
		}
	  });	   
}
/**
 * 迅速定位班级信息
 * @return
 */
function getClsPosition(isshowCk){
	 //自动定位到该班级
	 var classObj=document.getElementById("classId");
	if(classObj.value.Trim().length>0&&classObj.value.Trim()!="-1"){
		$("#dataTbl tr").css("background-color","");
		var clsName=classObj.options[classObj.selectedIndex].text;		
		$("tr[id='tr_"+clsName+"']").css("background-color",'red'); 
		
	}	
		//判断是否显示列明
		$("#dataTbl tr").each(function(ix,im){
			if(typeof(isshowCk)!="undefined"&&isshowCk)	
					$(this).children().first().show();
			else
				if($("#ckAll").css("display")!="none")
					$(this).children().first().hide();	
		});	
		
		//判断是否显示CheckBox列	(由于复选框列的显示掩藏是根据表头进行判断，所以先操作列头，再操作表体)
		if(typeof(isshowCk)!="undefined"&&isshowCk){			
			$("#ckAll").show();
			$("#delA img").attr("src",$("#delA img").attr("src").replace("_g",''));
			
			$("#delA").unbind("click");
			$("#delA").bind("click",function(){
				eval($("#delA").attr("action"));					
			});
		}else
			if($("#ckAll").css("display")!="none"){
				$("#ckAll").hide();
				var src=$("#delA img").attr("src");
				if(src.indexOf("_g")==-1)
		   			src=src.substring(0,src.indexOf("."))+"_g"+src.substring(src.indexOf('.')); 
				$("#delA img").attr("src",src);
				$("#delA").unbind("click");
			}
}



/**
 * 跳转到学生校风页面*/
function toStuEthosList(){
	 var termId = $("#termId");
     var grade = $("#grade");
     var classId = $("#classId");
     if(classId.val().Trim()=="")
    	 return;
     if(termId.val().Trim() =="")
         return;
     if(grade.val().Trim() =="") 
         return;
     var wkid=$("#weekId");
     if(wkid.val().Trim()==""||wkid.val().Trim()=="-1")
     	return;
     var year=document.getElementById("termId").options[document.getElementById("termId").selectedIndex].text.split(' ')[0];
	toPostURL('stuethos?m=list',{
		classId:classId.val().Trim(),
		year:year,
		gradeName:grade.val().Trim(),
		weekId:wkid.val().Trim()
	},true);
}
 
/**
 * 用户查询班级统计情况
 * @param showAddress 数据显示的地址
 * @param iscludeclass 是否加载班级作为参数
 * @return
 */
function showGradeStatices(showAddress){
	  $("#"+showAddress).html('');
	//  var classObj=$("#classId");
//	  if(classObj.val().Trim().length<1){
//		  return;		  
//	  }
//	  
	  var termObj=$("#termId");
	  if(termObj.val().Trim().length<1){
		  alert('您尚未选择学期，无法选择班级，请先选择学期！');
		  termObj.focus();
		  return;		  
	  }
	 
	
	  var gradeObj=$("#grade");
	  if(gradeObj.val().Trim().length<1){
		  alert('您尚未选择年级，无法选择班级，请先选择年级！');
		  gradeObj.focus();
		  return;
	  }
	  
	  
	 //根据显示的TermId，字符串，进行分割，
	  var grade=gradeObj.val().Trim();


	  url='classethos?m=getClassEthosList';
	  //WEEK_ID WEEKID_BEGIN WEEKID_END	 
	  
	  if($("#wk_one").attr("checked")){
		  var weekObj=$("#weekId");
		  if(weekObj.val().Trim().length<1||weekObj.val().Trim()=="-1"){
			  alert('您尚未选择周次，请选择周次后点击查询，进行查看！');
			  weekObj.focus();
			  return;
		  }
		  url+="&weekid="+weekObj.val().Trim();		  
	  }else{
		  var weekBeginObj=$("#weekid_begin");
		  var weekEndObj=$("#weekid_end");
		  if(weekBeginObj.val().Trim().length<1||weekBeginObj.val().Trim()=="-1"){
			  alert('您尚未选择查询的周次开始时间，请选择!');
			  weekBeginObj.focus();
			  return;
		  }
		  if(weekEndObj.val().Trim().length<1||weekEndObj.val().Trim()=="-1"){
			  alert('您尚未选择查询的周次结束时间，请选择!');
			  weekEndObj.focus();
			  return;
		  }
		  url+="&weekid="+weekBeginObj.val().Trim()+"&weekend="+weekEndObj.val().Trim();
		  
	  }  
	  
	  //排序
	  var orderColumn=$("#orderColumn");
	
	  
	  $.ajax({
		  url:url,
		  type:'post',
		  dataType:'json',
		  cache: false,
		  data:{termid:termObj.val().Trim(),
		  		grade:grade,
		  		ordercolumn:orderColumn.val().Trim()},
		  error:function(){
			alert('系统未响应，请稍候重试!');
			$("#"+showAddress).html("<tr><td align='center'>系统未响应，请稍候重试!</td></tr>");
		  },success:function(rjson){
			if(rjson.type=="success"){
				var showObj="<tr><td align='center'>没有该班级的数据信息!</td></tr>";
				if(rjson.objList.length>0){
					showObj=" <tr><th>班级</th><th>集会分数</th><th>卫生分数</th><th>财产分数</th><th>宿舍集体分</th><th>其他分数</th><th>考勤汇总分</th>" +
							"<th>违纪汇总分</th><th>好人好事</th><th>奖励加分</th><th>总分</th><th>排名</th></tr>";
					//循环行
					$.each(rjson.objList,function(idx,itm){						
						if(typeof(itm)=="object"){
							showObj+='<tr';
							if(idx%2!=0)
								showObj+=' class="Trbg1"';
							showObj+='>';
							//循环列
							$.each(itm,function(ix,im){ 
								showObj+='<td>'+(im==null?"&nbsp;":im)+'</td>';
							});	
							showObj+='</tr>';
						}	 
						
					});	
					 $("#dataTbl td ").css("width",76.1*12+"px");
					 var posturl="classethos?m=expClassList";
					  if($("#wk_one").attr("checked")){
						  var weekObj=$("#weekId");						
						  posturl+="&weekid="+weekObj.val().Trim();		  
					  }else{
						  var weekBeginObj=$("#weekid_begin");
						  var weekEndObj=$("#weekid_end");						  
						  posturl+="&weekid="+weekBeginObj.val().Trim()+"&weekend="+weekEndObj.val().Trim();						  
					  }
					//设置导出Excel的图片					  					  
					var clickPost='javascript:toPostURL(\''+posturl+'\',{termid:'+termObj.val().Trim()+',grade:\''+grade.Trim()+'\',ordercolumn:\''+orderColumn.val().Trim()+'\'},true)';					
					var showExpObj='<a href="';
					showExpObj+=clickPost+'"><img src="images/an03.gif" width="25" height="25" />导出excel表</a>';
					$("#explortExl").html(showExpObj);
					$("#explortExl").show();
				}
				$("#"+showAddress).html(showObj);
				$("#"+showAddress+" tr ").hover(function(){
					$(this).attr("backupClass",this.className);
					$(this).addClass("Trbg2");
				},function(){
					$(this).removeClass("Trbg2");
					$(this).addClass($(this).attr("backupClass"));
				});
				
			}else{
				alert(rjson.msg); 
			}
		}
	  });	   
	  
}

/**
 * 按班级查询
 * 获取查询结果 
 * @return
 */
function showClassStatices(){
	var termidObj=$("#termId");
	if(termidObj.val().Trim().length<1||termidObj.val().Trim()=='-1'){
		alert('您尚未选择学期，请选择!\n\n提示：查看班级统计，请以此选择 "学期","周次","年级","班级"，最后点击查询!');
		termidObj.focus();
		return;
	}
	var weekidObj=$("#weekId");
	if(weekidObj.val().Trim().length<1||weekidObj.val().Trim()=='-1'){
		alert('您尚未选择周次，请选择!\n\n提示：查看班级统计，请以此选择 "学期","周次","年级","班级"，最后点击查询!');
		weekidObj.focus();
		return;
	}
	var gradeObj=$("#grade");
	if(gradeObj.val().Trim().length<1||gradeObj.val().Trim()=='-1'){
		alert('您尚未选择年级，请选择!\n\n提示：查看班级统计，请以此选择 "学期","周次","年级","班级"，最后点击查询!');
		gradeObj.focus();
		return;
	}
	var classObj=$("#classId");
	if(classObj.val().Trim().length<1||classObj.val().Trim()=='-1'){
		alert('您尚未选择班级，请选择!\n\n提示：查看班级统计，请以此选择 "学期","周次","年级","班级"，最后点击查询!');
		classObj.focus();
		return;
	}
	
	//$("#dataTbl").empty();
	$("#dataTbl").html('<tr><th colspan=4><img src="img/loading_smail.gif"/>正在加载中!请稍后!</th></tr>');

 $.ajax({
	 url:"classethos?m=getEthosForClass", 
		type:'post',
		dataType:'json',
		cache:false,
		data:{termid:termidObj.val().Trim(),
		 	classid:classObj.val().Trim(),
		 	grade:gradeObj.val().Trim(),
		 	weekid:weekidObj.val().Trim()
 		},error:function(){
	    	alert("系统为响应，请稍后重试！");
	    },
	    success:function(rjson){
	    	if(rjson.type=="success"){
	    		var colhtml='';
	    		if(rjson.objList.length<1){
	    			colhtml+='<tr><th width="100%" align="center">暂无数据！</tr>';
	    		}else{
	    			var trbegin=false;
	    			$.each(rjson.objList,function(idx,itm){	    			
	    				$.each(itm,function(ix,im){
	    					if((ix)%4==0){
	    						if(!trbegin){
		    						colhtml+='<tr>';
		    						trbegin=true;
	    						}else{
	    							trbegin=false;
	    							colhtml+='</tr>';
	    							if(ix!=itm.length-1){
	    								colhtml+='<tr>';
	    								trbegin=ix;
	    							}
	    						}
	    					} 
	    					colhtml+='<t';
	    					 if(ix%2==0)
	    						colhtml+='h>';
	    					else if((ix+1)%4==0)
	    						colhtml+='d>'; 
	    					else 
	    						colhtml+='d>';
	    					 var sht;
	    					 if(im!=null&&isNaN(im)&&im.length>0){
		    					sht=(im==null?"&nbsp;":replaceAll(im,'/n','<br/>').Trim());
		    					sht=replaceAll(sht,'<br/><br/><br/>','<br/>');
		    					var imArray=sht.split("<br/>");
		    					if(imArray.length>2)
		    					{
		    						sht=imArray[0]+"<textarea style='display:none' id='txt_"+idx+"_"+ix+"'>"+sht
		    						sht+="</textarea><a style=\"color:blue\" href=\"javascript:showWatchDiv('showDetailDiv','txt_"+idx+"_"+ix+"','detailText')\">更多</a>";
		    					}
	    					}else{
	    						 if(im==null)
	    							 sht='';
	    						 else
	    							 sht=im;
	    					 }
	    					colhtml+=sht;		    	 				
	    					colhtml+='</td>';
	    						 
	    				})
	    			});	    
	    			 var posturl="classethos?m=expClass";
		    			var clickPost='javascript:toPostURL(\''+posturl+'\',{termid:'+termidObj.val().Trim()+',classid:'+classObj.val().Trim();
		    			clickPost+=',grade:\''+gradeObj.val().Trim()+'\',weekid:'+weekidObj.val().Trim()+'},true)';					//,ordercolumn:\''+ordercolumn.Trim()+'\',dict:\''+dict+'\'
		    		var exportExlTable='<a href="'+clickPost+'"><img src="images/an03.gif" width="25" height="25" />导出excel表</a>';
		    		
		    		$("#explortExl").html(exportExlTable);
	    		}		
	    		
	    		colhtml=colhtml+'</tr>'; 
	    		$("#dataTbl").html(colhtml);
	    	}else{
	    		alert(rjson.msg);
	    		$("#dataTbl").html('<tr><th width="100%" align="center"><img src="img/loading_smail.gif"/>'+rjson.msg+'</td></tr>');
	    	}
	    }	 
 });
}

/**
 * 单选按钮选择改变的时候
 */
function radioClicked(obj){
 		var rdoId=obj.id;
 		if(rdoId=="wk_one"){	       		
     			//禁用多周查询的选择
	       		$("select").filter(function(){return this.id.indexOf('weekid_')!=-1}).val('');
	       		$("select").filter(function(){return this.id.indexOf('weekid_')!=-1}).attr("disabled",true);
	       		//将单周开启   
	       		$("#weekId").removeAttr("disabled");  
 		}else{
 		 		//设置多周
	       		var html=$("#weekId").html();
	       		$("#weekid_begin").html(html);
	       		//启用
	       		$("select").filter(function(){return this.id.indexOf('weekid_')!=-1}).removeAttr("disabled");
	       				       		
	       		$("#weekid_begin").bind("change",function(){
	       			var opts=document.getElementById("weekid_begin").options;
	       			
	       			var newhtml="";//"<option value=''>==请选择==</option>";
	       			$.each(opts,function(idx,itm){
	       				if(itm.value.Trim().length>0){
		       				if(parseFloat($("#weekid_begin").val().toString().Trim())<=parseFloat(itm.value.Trim())){
		       					newhtml+='<option value="'+itm.value+'">'+itm.text.Trim()+'</option>';
		       				}	
	       				}
	       			});
	       			
	       			$("#weekid_end").html(newhtml);
	       		});
	       			//将单周禁用  
	       		$("#weekId").attr("disabled",true);  
 		}
}

/**
 * 显示查看详情 DIV层
 * @param div
 * @return
 */
function showWatchDiv(div,showObjControlId,textObjStrId){
	if(typeof(div)=="undefined"||div==null||!isNaN(div)){
		alert('异常调用，请刷新页面后重试!');
		return;
	}
	if(typeof(showObjControlId)=="undefined"||showObjControlId==null||!isNaN(showObjControlId)){
		alert('调用异常，请刷新页面后重试!');
		return;
	}
	textObjStrId=typeof(textObjStrId)=="undefined"?"detailText":textObjStrId;
	$("#"+textObjStrId).html($("#"+showObjControlId).val());
	$("select").hide();
	showModel(div, false);	
}
