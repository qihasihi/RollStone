
/**
 * 点击分数效果
 * @param parentref
 * @param index
 * @return
 */
function chooseScore(parentref,index){
	var totalscore = 0;
	//重置对号颜色
	$("td").filter(function(){
		return this.id.indexOf("td_child_score_"+parentref+"") != -1;
	}).children("a").each(function(ix,im){
		$(this).css("color","black");
	});
	//给每项得分赋值
	$("#td_child_score_"+parentref+"_"+index+"").children().filter(function(){
		return this.tagName=='A';
	}).each(function(idx,im){
		$(this).css("color","red");
		var html =  $(this).prev("span").html();
		var score = $(this).prev("span").html();
		var id   =  $(this).next("input").val();
		//分数、小题ID
		html+='<input type="hidden" name="hd_for_score" value="'+ parseInt(score)+'" />';
		html+='<input type="hidden" name="hd_for_id" value="'+ id +'" />';
		//LOGREF
		var hd_upd_ref =$("#td_last_score_"+parentref+"").children("input").last();
		if(hd_upd_ref.length>0){
			var upd_ref =hd_upd_ref.val().Trim(); 
			html+='<input type="hidden" name="hd_for_upd_ref" value="'+ upd_ref +'" />';
		}
		$("#td_last_score_"+parentref+"").html(html);
	});
	//个人得分总计
	$("td").filter(function(){
		return this.id.indexOf("td_last_score_") != -1 && this.innerHTML.Trim()!="&nbsp;" ;
	}).each(function(idx,itm){
		if(itm.innerHTML.Trim().length>0 ){
			var score =parseInt($(this).html());
			totalscore += score;
		}
	});
	$("#totalScore").html(totalscore);
}
/**
 * 提交自我评价
 * @return
 */
function sub_peer_self(){
	if(typeof(peerbaseref)=="undefined" || isNaN(peerbaseref)){
		alert("异常错误,系统未获取到评价基本信息标识！请刷新后重试！");
		return;
	}
	if(typeof(ptype)=="undefined" || isNaN(ptype)){
		alert("异常错误,系统未获取到评价类型标识！请刷新后重试！");
		return;
	}
	var scoreData = "",refData ="";
	//题目数
	var scoreArr=$("td").filter(function(){
			return this.id.indexOf("td_last_score_") != -1 	
		});
	//分数
	var sArr =$("td input[type='hidden'][name='hd_for_score']").filter(function(){
		return this.value.Trim().length>0;
	}).each(function(ix,im){
		if(scoreData.length>0)
			scoreData+="&";
		scoreData+="scoreData="+im.value.Trim();
	});
	//ID
	var idArr =$("td input[type='hidden'][name='hd_for_id']").filter(function(){
		return this.value.Trim().length>0;
	}).each(function(ix,im){
		if(refData.length>0)
			refData+="&";
		refData+="refData="+im.value.Trim();
	});
	if(scoreArr.length>0 &&  sArr.length<scoreArr.length){
		alert("您好，系统检测到您当前还有未选择的题目，请完成后提交！");
		return;
	}
	
	if(refData.length>0 && scoreData.length>0){
		var sendData ="t=1&peerbaseref="+peerbaseref;
		sendData+="&ptype="+ptype;
		sendData+="&"+refData;
		sendData+="&"+scoreData;
		$.ajax({
			url:'peerlog!subPeerTeaSelf.action',
			type:'post',
			dataType:'json', 
			data:sendData,
			error:function(){
				alert("异常错误，系统未响应！");
			},
			success:function(rps){
				if(rps.type=='error'){
					alert(rps.msg);
					return;
				}
				$("#p_for_sub").hide();
				alert(rps.msg);
			}
		});
		
	}
}
/**
 * 修改自我评价 教师自己
 * @return
 */
function upd_peer_self(){
	if(typeof(peerbaseref)=="undefined" || isNaN(peerbaseref)){
		alert("异常错误,系统未获取到评价基本信息标识！请刷新后重试！");
		return;
	}
	if(typeof(ptype)=="undefined" || isNaN(ptype)){
		alert("异常错误,系统未获取到评价类型标识！请刷新后重试！");
		return;
	}
	var scoreData = "",refData ="",logRef="";
	//题目数
	var scoreArr=$("td").filter(function(){
			return this.id.indexOf("td_last_score_") != -1 	
		});
	//分数
	var sArr =$("td input[type='hidden'][name='hd_for_score']").filter(function(){
		return this.value.Trim().length>0;
	}).each(function(ix,im){
		if(scoreData.length>0)
			scoreData+="&";
		scoreData+="scoreData="+im.value.Trim();
	});
	//ID
	var idArr =$("td input[type='hidden'][name='hd_for_id']").filter(function(){
		return this.value.Trim().length>0;
	}).each(function(ix,im){
		if(refData.length>0)
			refData+="&";
		refData+="refData="+im.value.Trim();
	});
	//LOGREF
	var logRefArr= $("td input[type='hidden'][name='hd_for_upd_ref']").filter(function(){
		return this.value.Trim().length>0;
	}).each(function(ix,im){
		if(logRef.length>0)
			logRef+="&";
		logRef+="logRef="+im.value.Trim();
	});
	if(scoreArr.length>0 &&  sArr.length<1){
		alert("您好，请选择您要修改的题目！");
		return;
	}
	
	if(refData.length>0 && scoreData.length>0 && logRef.length>0){
		var sendData ="t=1&peerbaseref="+peerbaseref;
		sendData+="&ptype="+ptype;
		sendData+="&"+refData;
		sendData+="&"+scoreData;
		sendData+="&"+logRef;
		$.ajax({
			url:'peerlog!updPeerTeaSelf.action',
			type:'post',
			dataType:'json', 
			data:sendData,
			error:function(){
				alert("异常错误，系统未响应！");
			},
			success:function(rps){
				if(rps.type=='error'){
					alert(rps.msg);
					return;
				}
				//$("#p_for_upd").hide();
				alert(rps.msg);
			}
		});
		
	}
}
/*******************教研组长****************************/
/**
 * 修改自我评价 by 教研组长
 * @return
 */
function upd_peer_leader(){
	if(typeof(peerbaseref)=="undefined" || isNaN(peerbaseref)){
		alert("异常错误,系统未获取到评价基本信息标识！请刷新后重试！");
		return;
	}
	if(typeof(ptype)=="undefined" || isNaN(ptype)){
		alert("异常错误,系统未获取到评价类型标识！请刷新后重试！");
		return;
	}
	if(typeof(uid)=="undefined" || isNaN(uid)){
		alert("异常错误,系统未获取到用户标识！请刷新后重试！");
		return;
	}
	var scoreData = "",refData ="",logRef="";
	//题目数
	var scoreArr=$("td").filter(function(){
			return this.id.indexOf("td_last_score_") != -1 	
		});
	//分数
	var sArr =$("td input[type='hidden'][name='hd_for_score']").filter(function(){
		return this.value.Trim().length>0;
	}).each(function(ix,im){
		if(scoreData.length>0)
			scoreData+="&";
		scoreData+="scoreData="+im.value.Trim();
	});
	//ID
	var idArr =$("td input[type='hidden'][name='hd_for_id']").filter(function(){
		return this.value.Trim().length>0;
	}).each(function(ix,im){
		if(refData.length>0)
			refData+="&";
		refData+="refData="+im.value.Trim();
	});
	//LOGREF
	var logRefArr= $("td input[type='hidden'][name='hd_for_upd_ref']").filter(function(){
		return this.value.Trim().length>0;
	}).each(function(ix,im){
		if(logRef.length>0)
			logRef+="&";
		logRef+="logRef="+im.value.Trim();
	});
	if(scoreArr.length>0 &&  sArr.length<1){
		alert("您好，请选择您要修改的题目！");
		return;
	}
	if(!confirm("您确定修改吗？")){
		return;
	}
	if(refData.length>0 && scoreData.length>0 && logRef.length>0){	
		var sendData ="t=1&peerbaseref="+peerbaseref;
		sendData+="&ptype="+ptype;
		sendData+="&uid="+uid;
		sendData+="&"+refData;
		sendData+="&"+scoreData;
		sendData+="&"+logRef;
		$.ajax({
			url:'peerlog!updPeerTeaLeader.action',
			type:'post',
			dataType:'json', 
			data:sendData,
			error:function(){
				alert("异常错误，系统未响应！");
			},
			success:function(rps){
				if(rps.type=='error'){
					alert(rps.msg);
					return;
				}
				//$("#p_for_upd").hide();
				alert(rps.msg);
			}
		});
		
	}
}
/**
 * 重置自我评价
 * @return
 */
function self_reset(){
	//取消选中效果 清空每项得分
	$("td").each(function(){
		if(this.id.indexOf("td_last_score_") != -1){
			$(this).html("");
		}else if(this.id.indexOf("td_child_score_") != -1){
			$(this).children("a").each(function(i,im){
				$(this).css("color","black");
			});
		}
	});
	//清空个人得分总计
	$("#totalScore").html("");
}
/**
 * 加载自我评价
 * 教研组长查看列表
 * @return
 */
function initLogDataList(){
	if(typeof(peerbaseref)=="undefined" || isNaN(peerbaseref)){
		alert("异常错误！系统未获取到评价基本信息标识！");
		return;
	}
	if(typeof(deptidStr)=="undefined"){
		alert("异常错误！系统未获取到部门信息标识！");
		return;
	}	
	var deptstr="";
	var deptArr = $("input[type='checkbox'][name='ck_dept']").filter(function(){
		return this.checked;
	});
	if(deptArr.length>0){
		deptArr.each(function(idx,im){
			if(deptstr.length>0)
				deptstr+=",";
			deptstr+=im.value.Trim();
		});
	}else{
		deptstr=deptidStr;
	}
	$.ajax({
		url:'peerlog!toQryPeerLogLeader.action',
		type:'post',
		data:{peerbaseref:peerbaseref,
			  deptstr:deptstr
			},
		dataType:'json',
		error:function(){
			alert("异常错误！系统为响应！");
		},
		success:function(rps){
			var bo = false;
			var itemLen=null;
			if(rps.objList.length>0){
				var html="";
				var title =rps.objList[0].TBLTITLE;
				var titleArr =title.split(",");
				if(titleArr.length>0){
					$.each(titleArr,function(index,da){
						if(!isNaN(da)){
							itemLen+=1;
						}
					});
				}
				$.each(rps.objList,function(idx,itm){
					if(idx%2==0)
						html+="<tr class='Trbg1'>";
					else
						html+="<tr>";
					if(idx!=0){
						var uid =null;
						$.each(titleArr,function(ix,im){
							bo =true;
							if(im.Trim()=="USER_ID"){
								uid =itm[im];
							}else if(im.Trim()=="IS601"){
								//是否为实验员 此处无用
							}
							else if(im.Trim()=="TEACHER_NAME"){
								html+="<td><a href='peerlog!toPriviewTeaLog.action?uid="+uid+"&peerbaseref="+peerbaseref+"'>"+ itm[im] +"</a></td>";
							}else
								html+="<td>"+ itm[im] +"</td>";
						});
					}
					html+="</tr>";
				});
				$("#initList").hide();
				var len=0;
				if(itemLen!=null){
					len= itemLen;
				}else{
					len=titleArr.length;
				}
				if(len>=8) 
					$("#initList").css("width",(len+3)*81);
				if(!bo){
					$("#initList").html("<tr><td>暂无数据!</td></tr>");
					$("#initList").show("fast");
				}else
					$("#initList").html(html);
					$("#initList").show("fast");		
			}
			$("#initList tr").hover( 
					function(){
						$(this).addClass("Trbg2");
					},
					function(){
						$(this).removeClass("Trbg2");
					}
			);
		}
	});
	
}



//-----------------------------------teacher_pj.jsp 
/**
 *给teacher_pj.jsp中datatbl每个td添加事件(onmouseover,onmouseout)
 */
 function addDateLister(){
	 //添加第一行的浮动层特效和除第一行外的点击效果
	 var lenth=$("#tbl_data tr").length;
	 $("#tbl_data tr").each(function(idx,itm){
		 //第一行是姓名，忽略
		 if(idx>0&&idx!=lenth-1){
			 $(this).children("td").bind("click",function(){
				 dataTblTdClick(idx,this);
			 });
			 $(this).hover(
				 function(){
					 this.freeClassName=this.className;
					 this.className='Trbg2';
				 },function(){
					 this.className=this.freeClassName;
				 }
			);
		 }else{
			$(itm).children("th").hover(
				function(){
					genderShowdiv(this);
				},function(){
					var divObj=$("#div_tmp_show01");
					if(divObj.length>0)
						divObj.remove();
				}
			)			 
		 }
	 });
	 
	 
 }
 /**
  * 给列每行编号
  * @return
  */
 function setTrNumber(){
	 //给tbl_col行编号
	 $("#tbl_col tr").each(function(idx,itm){
		 if(itm.id.length<1)
			 this.id='tbl_col_tr_'+(idx+1);
	 });	 
 }
 
 /**
  * 
  * @param obj
  * @return
  */
 function dataTblTdClick(idx,obj){
	 var sescorechange=$("select[id='score_change']");
	 if(sescorechange.length>0){		 
		 scoreChange(undefined,sescorechange);
	 }
		
	 var score='';
	 var scoreObj=$(obj).children("span");
	 if(scoreObj.length<1)
		 score="未评";
	 else
		 score=scoreObj.html().Trim();
	 var uid='';
	 var uidObj=$(obj).children("input[type='hidden']");
	 if(uidObj.length>0)
		 uid=uidObj.val().Trim();
	 if(uid.Trim().length<1){
		 alert('异常错误，系统异常，请刷新页面后重试! 错误代码：uid is empty!');
		 return;
	 }
	//得到最高分数
    var maxScore=$("#tbl_col_tr_"+(idx+1)).children().filter(function(){
    	return this.id.indexOf('child_score_')!=-1;
    	
    }).html().Trim();
    var h="<select id='score_change'><option value='-1' >请选择</option>";
	if(maxScore.length>0&&!isNaN(maxScore)){
		for(z=maxScore;z>=0;z--){
			h+='<option value="'+z+'">'+z+'</option>';
		} 
	}
    h+='</select><input type="hidden" value="'+uid+'"/>';
    $(obj).unbind("click");
    obj.onclick="";
    $(obj).html(h);       
    if(!isNaN(score)){
         $(obj).attr("freeScore",score);
         $("#score_change").val(score);
     }
    $("#score_change").bind("change",function(){
    	var s=parseFloat($(this).parent().attr("freeScore"));
    	if(s!=parseFloat(this.value.Trim()))
    		scoreChange(idx,this);
    })	 
 }
 
 /**
  * 分数确定
  * @param idx
  * @param obj
  * @return
  */
 function scoreChange(idx,obj){
	 if(typeof(idx)=="undefined"||idx<0){
		 $("#tbl_data tr").each(function(ix,im){
			 var seObjLen=$(im).children().children("select").length;
			 if(seObjLen>0){
				 idx=ix;
				 return;
			 }
		 });
	 }	 
	 var uid=$(obj).parent().children("input[type='hidden']").val();
 	//得到最高分数
	    var maxScore=$("#tbl_col_tr_"+(idx+1)).children().filter(function(){
	    	return this.id.indexOf('child_score_')!=-1;	    	
	    }).html().Trim();
	    var score=$("#score_change").val().Trim();
	    if(maxScore.length>0&&!isNaN(maxScore)){	    	 
	    	if(parseFloat(maxScore)<parseFloat(score)){
	    		alert('错误，最高分数只能在0-'+maxScore+'之间!');
	    		score=maxScore;
	    	}			    	
	    }			   
	    var h='<span>'+score+'</span>';
	        h+='<input type="hidden" value="'+uid+'"/>';
	   //恢复事件
	    $(obj).parent().bind("click",function(){
	    	dataTblTdClick(idx,this);	    	
	    });
	    if(score<=0){ 
	    	$(obj).parent().css("background-color","yellow");
	    }else
	    	$(obj).parent().css("background-color","#66FF99");
	    //改变值 
	    $(obj).parent().html(h);	
	     
	    //计算总分
	    calSumScore();	   
 }
 
 /**
  * 计算总分
  * @return
  */
 function calSumScore(){
	 //计算总分
	    var trtmpObj=$("#tbl_data tr");
	    var t1=trtmpObj.length;
	    $("#tbl_data tr:last td").each(function(idx,itm){
	    	var sumScore=0;
	    	$.each(trtmpObj,function(ix,im){
	    		if(ix!=0&&ix!=t1-1){
	    			$(this).children("td").each(function(i,m){
	    				if(i==idx){
	    					var score=$(m).children("span").html().Trim();
	    					if(!isNaN(score))
	    						sumScore+=parseFloat(score);
	    				}
	    			});	 
	    		}
	    	});	
	    	itm.innerHTML=sumScore;
	    });	 
 }

 /**
  * 执行添加
  * @return
  */
 function doAddPJScore(peerbaseref,p_type){
	 if(typeof(peerbaseref)=="undefined"){
		 alert('异常错误！系统尚未检测到peerbaseref，请刷新后重试!错误代码：peerbaseref is undefined!');
		 return;;
	 }
	 if(typeof(p_type)=="undefined"||isNaN(p_type)){	
		 alert('异常错误！系统尚未获取到P_type,请刷新页面后重试!错误代码：p_type is undefined!');
		 return;
	 }
//	 if(p_type=="4"){
//		 p_type="2";
//	 }else if(p_type=="2" && isStuOffice=="1"){
//		 p_type="3";
//	 }
	 var seobjArray=$("#tbl_data select");
	 if(seobjArray.length>0){
		 alert('您尚未选择您要选择的分数，请选择!');
		 return;
	 }
	 //获取数据
	 var dataTrArray=$("#tbl_data tr");
	 var dataTrArraylen=dataTrArray.length;
	 if(dataTrArraylen.length<1){
		alert('系统未发现您要评价的对像，请联系相关管理人员！dataTrArray is empty!');
		return;
	 }
	 var scorewarn = false;
	 var iserror=0,paramStr='',iswarn=false;
	 $.each(dataTrArray,function(idx,itm){
		 //每一行是评价人姓名
		 if(idx>0&&idx!=dataTrArraylen-1){
			 //获取评价项REF
			 var coltrObj=$("#tbl_col_tr_"+(idx+1));
			 if(coltrObj.length<1){
				 iserror=1; 
				 return;
			 }
			 //得到评价项REF和最高分
			 var pjitemid='',maxscore=0;
			 coltrObj.children("td").each(function(ix,im){
				 if(im.id.indexOf('child_name_')!=-1){
					 pjitemid=$(im).children("input[type='hidden']").val().Trim();
				 }else if(im.id.indexOf('child_score_')!=-1){
					 maxscore=parseFloat($(im).html().Trim());
				 }
			 });
			 //得到被评价人ID,和分数
			 var lenTmp=$(itm).children('td').length;
			 $(itm).children('td').each(function(ix,im){				
				 	 var pjscore=$(im).children("span").html().Trim();
				 	 if(pjscore.length>=0&&!isNaN(pjscore)){					 		 
					 	 pjscore=parseFloat(pjscore);					 	
					 	 if(pjscore<1)
					 		iswarn=true;
					 	 if(pjscore==-1){
					 		 scorewarn=true;
					 	 }
//					 	 if(maxscore<pjscore){
//					 		 iserror=3;
//					 		 im.style.backgroundColor='red';
//					 		 return;
//					 	 }				 	 
					 	 var pjuserid=$(im).children("input[type='hidden']").val().Trim();
					 	 if(pjuserid.length<1){
					 		 iserror=4;
					 		im.style.backgroundColor='red';
					 		 return;
					 	 }
					 	if(paramStr.Trim().length>0) 
					 		paramStr+='&';
					 	paramStr+=pjitemid+'|'+pjuserid+'|'+pjscore;

				 	 }				 
			 });
			 if(iserror>0)
				 return;
		 }
	 });
	 if(iserror==1){
		 alert('异常错误，系统尚未获取到评价项标识！刷新后重试！错误代码：coltrObj is empty!');
		 return;
	 }else if(iserror==2){
		 alert('异常错误，系统尚未获取到评价的分数信息或者分数信息不正确！刷新后重试！错误代码：pjscore is empty!');
		 return;
	 }else if(iserror==3){
		 alert('异常错误，您选择的分数过大！刷新后重试！错误代码：pjscore is empty!');
		 return;
	 }else if(iserror==4){
		 alert('异常错误，系统尚未获取到被评价人标识！刷新后重试！错误代码：pjuserid is empty!');
		 return;
	 }
	 if(paramStr.Trim().length<1){
		 alert("错误，信息验证失败，请重试!");
		 return;
	 }
	 //分数有存在0的
	 var confirmStr='请仔细核对分数信息，录入后在执行期可以修改，时间结束后将无法修改! 您确认吗？';
	 if(iswarn){		 
		 confirmStr='您录入的分数中有存在 小于或等于 0 分的分数!\n\n'+confirmStr;		
	 }
	 if(scorewarn){		 
		 alert("您录入的分数中有没有选择的项目，请选择——黄色部分");	
		 return;
	 }
	 if(!confirm(confirmStr))
		 return;
	//开始向后台添加数据
	 $.ajax({
			url: 'peerlog?m=dosave',
			type:'post',
			dataType:'json', 
			cache: false,
			data:{peerbaseref:peerbaseref,
		 		  ptype:p_type,
		 		  paramStr:paramStr
	        },
			error:function(){
				alert('程序异常!');
			},success:function(rmsg){
				alert(rmsg.msg);
			}
	 });
	 
 }
 
 
 /**
  * 年份更换的时候
  * @param obj
  * @return
  */
 function  logyearChange(obj){
 	if(obj.value.Trim().length<1||obj.value.Trim()=="-1"){
 		return;
 	}
 	var year=obj.value.Trim();

 	$.ajax({
 		url: 'peerbase!getBaseList.action',
 			type:'post',
 			dataType:'json', 
 			cache: false,
 			data:{year:year},
 			error:function(){
 				alert('程序异常!');
 			},success:function(rmsg){
 				if(rmsg.type=="error"){
 					alert(rmsg.msg);
 				}else{
 					var h='<option value="">==请选择==</option>';
 					if(rmsg.objList.length<1){
 						alert('暂无数据!');						
 					}else{					
 						$.each(rmsg.objList,function(idx,itm){
 							h+='<option value="'+itm.ref+'">'+itm.name+'</option>';
 						});							
 					}
 					$("#sel_base").html(h);
 				}
 			}			
 		});	
 }
 /**
  * 根据rdo显示部门或者教研组
  * @param obj
  * @return
  */
 function loadDeptByPtype(obj){
	 if(obj.length<1){
		 alert("异常错误！系统获取参数错误，错误代码：rdo is undefined!");
		 return;
	 }
	 $("input[name='ck_dept'][type='checkbox']").attr("checked",false);
	 var objVal = $(obj).val().Trim();
	 if(objVal=='all'){
		 $("input[name='ck_dept'][type='checkbox']").attr("checked",false);
	 	 $("#sp_ck_dept").hide();
	 }
	 else{
		 $("input[name='ck_dept'][type='checkbox']").attr("checked",false);
		 $("input[name='ck_dept'][type='checkbox']").eq(0).attr("checked",true);
		 $("#sp_ck_dept").show();
	 }
 }
 
 
	/**
	 * 统计
	 * 显示教师、实验员姓名
	 * @return
	 */
	function removeTeaNameDiv(){
//		$("#initList td").filter(function(){return this.id.Trim().length>0}).hover(
//				function(){
//					showTeaName(this);
//				},
//				function(){
//					var div =$("#div_tea_name");
//					if(div.length>0){
//						div.remove();
//					}
//				}
//		);
		var div =$("#div_tea_name");
		if(div.length>0){
			div.remove();
		}
	}
	function showTeaName(obj){
		var htm ="";
		if(obj.id.indexOf("td_exp_")!=-1){
			htm = $(obj).html()+"(实验员)";
		}else if(obj.id.indexOf("td_tea_")!=-1){
			htm = $(obj).html()+"(教师)";
		}
		$("#div_tea_name").remove();
		var x =mousePostion.x;
		var y =mousePostion.y;
		if($.browser.msie && (parseInt($.browser.version)) <=7){
			x+=parseFloat(document.documentElement.scrollLeft);
			y+=parseFloat(document.documentElement.scrollTop);
		}
		y+=5;
		var html ='<div id="div_tea_name"'
			+'style="position:absolute;padding:5px;border:1px solid green;'
			+'left:'+ x +'px;top:'+ y +'px;background-color:white">'+ htm +'</div>';
		$("body").append(html);
	}

	
 /**
  * 获取统计评价项、统计结果
  * 根据peerbaseref、ptype
  * @return
  */
 function qryLogAndItem(){
	
	if(typeof(ptype)=="undefined" || isNaN(ptype)){
		alert("异常错误！系统获取 ptype 参数错误，错误代码：ptype is undefined!");
		 return;
	}
	var peerbaseref = $("#sel_base").val();
	
	if(peerbaseref.length<1){
		alert("请先选择评价主题！");
		 return;
	}
	$("#p_title").children("a").each(function(idx,im){
		var h ='peerlog!toAdminWatchLog.action?ptype='+ (idx+1) +'&peerbaseref='+ peerbaseref +'';
		$(this).attr("href",h);
	});
	$.ajax({
		url:'peerlog!qryLogAndItemByRef.action',
		type:'post',
		data:{peerbaseref:peerbaseref,
			  ptype:ptype},
		dataType:'json',
	    error:function(){
		  alert("异常错误！系统未响应！");
		},
	    success:function(rps){
			//统计结果 
			var isStu =  false;
			if(rps.objList[3]!=null && rps.objList[3]=="1"){
				isStu =  true;
			}
			var titleArr=""; 
			var html ="";
			var itemLen =null;
			var tempLen =null;
			var bo=false;  
			$("#initList").removeAttr("style");
			//评价项
			if(rps.objList[0].length>0){
				var pjtypeStr ="";
				switch (ptype){
					case "1":
						pjtypeStr="教师";
						break;
					case "2":
						pjtypeStr="教师";
						break;
					case "3":
						pjtypeStr="职工";
						break;
				}
				html+="说明("+ pjtypeStr +"评价项)：<br/>";
				$.each(rps.objList[0],function(idx,itm){
					html+="第"+ (idx+1) +"项评价内容为：" +itm.name+"";
					html+="<br/>";
				});
				tempLen = rps.objList[0].length;  
				$("#p_item").html(html);
			}else{
				$("#p_item").html("该主题暂无评价项!");    
			}
			//实验员评价项
			if(ptype=="2"){
				if(rps.objList[2]!= null && rps.objList[2].length>0){
					var htm ="";
					htm+="说明(实验员评价项)：<br/>";   
					$.each(rps.objList[2],function(idx,itm){
						htm+="第"+ (idx+1) +"项评价内容为：" +itm.name+"";
						htm+="<br/>";
					});
					$("#p_employee_item").html(htm);
				}else{
					$("#p_employee_item").html("该主题暂无职工评价项!");  
				}
			//特殊教师
			}else if(ptype=="3" && isStu){  
				$("#p_employee_item").show();
				if(rps.objList[2]!= null && rps.objList[2].length>0){
					var htm ="";
					htm+="说明(教师评价项)：<br/>";   
					$.each(rps.objList[2],function(idx,itm){
						htm+="第"+ (idx+1) +"项评价内容为：" +itm.name+"";
						htm+="<br/>";
					});
					$("#p_employee_item").html(htm);
				}else{
					$("#p_employee_item").html("该主题暂无教师评价项!");  
				}
			}else
				$("#p_employee_item").hide();
				
				if(rps.objList[1]!=null){
					var h ="";
					$.each(rps.objList[1],function(ix,im){
						//表头
						if(ix==0){
							var title =im.TBLTITLE;
							titleArr=title.split(",");
							if(titleArr.length>0){
								$.each(titleArr,function(index,da){
									if(!isNaN(da)){
										itemLen+=1;
									}
								});
							}
						}else{
							if(ix%2==0){
								h+="<tr class='Trbg1'>";
							}else{
								h+="<tr>";
							}
							
							$.each(titleArr,function(cix,cim){
									bo =true;  
									//是否为实验员
									var isexp =false;
									if(ptype=="2" && im["IS601"]!=null && im["IS601"]=="1"){
										isexp=true;
									}
									if(cim.Trim()=="USER_ID"){  
										var uid =im[cim];
									}else if(cim.Trim()=="IS601"){   
									   
									}else if(cim.Trim()=="TEACHER_NAME"){
										if(ptype=="2"){
											if(isexp)
												h+="<td onmouseover='showTeaName(this)' onmouseout='removeTeaNameDiv()' id='td_exp_"+ (ix+1) +"' style='color:#228B22'>"+ im[cim] +"</td>";
											else
												h+="<td onmouseover='showTeaName(this)' onmouseout='removeTeaNameDiv()' id='td_tea_"+ (ix+1) +"' style='color:#1E90FF'>"+ im[cim] +"</td>";
										}else
											h+="<td >"+ im[cim] +"</td>";
										
									}else{
										if(im[cim]!=null){
											var formartNum = 0;
											if(!isNaN(im[cim])){
												formartNum = Math.round(parseFloat(im[cim])*100)/100;
											}
											h+="<td>"+ formartNum +"</td>";  
										}else{    
											h+="<td id='tr_"+ix+"_"+cix+"'>未评</td>";
										}
									}
							});
							h+="</tr>";
						}
					});
					
					if(!bo)
						$("#initList").html("<tr><td>暂无数据!</td></tr>");					
					else
						$("#initList").html(h);
					
				}else{
					$("#initList").html("<tr><td>暂无数据!</td></tr>");
				}
				//鼠标划过样式
				$("#initList tr").hover(function(){
					$(this).addClass("Trbg2");
				},function(){
					$(this).removeClass("Trbg2");  
				});
				
				//设置表头
				var len =0;
				var hl = "<tr><th>姓名</th>";
				if(itemLen != null && itemLen>0){
					if(ptype=="2"){
						len = itemLen/2;
					}else if(ptype=="3" && isStu){
						len = itemLen/2;
					}else	
						len = itemLen;
				}else{
					len = tempLen;
				}
				for(var i =1;i<=len;i++){
					hl+="<th>第"+ i +"项得分</th>";
				}
				hl+="<th>总分</th>";
				hl+="<th>排名</th></tr>";
				//滚动条
				$("#titleTbl").removeAttr("style");
				if(len>=8){
					$("#titleTbl").css("width",(len+3)*81);
				}
				$("#titleTbl").html(hl);
				
				$("#initList tr").each(function(idx,itm){
					var tmCount = 0;
					$(itm).children("td").each(function(ix,im){
						if(ix>0){
							if(im.innerHTML.Trim()=="未评"){
								tmCount++;
							}else
								tmCount=0;
							if(tmCount==len){
								var id=im.id;
								if(typeof(id)!="undefined"){
									var trrow=id.substring(id.indexOf('_')+1,id.lastIndexOf('_'));
									var cols=id.substring(id.lastIndexOf('_')+1);
									for(i=0;i<tmCount;i++){
										$("#tr_"+trrow+"_"+(cols-i)).remove();
									}
								}
							}
						}
					});
				});
				if(ptype=="2"){
					if(itemLen/2>=8)
						$("#initList").css("width",(itemLen+3)*81);
				}else if(ptype=="3" && isStu){
					if(itemLen/2>=8)
						$("#initList").css("width",(itemLen+3)*81);
				}else{
					if(itemLen>=8)
						$("#initList").css("width",(itemLen+3)*81);
				}
				$("#initList").hide();
				$("#initList").show("fast");
		}
	});
		
 }
 /**
  * 管理员查询统计结果(根据部门ID)
  * @return
  */
 function qryByDept(){
	 if(typeof(ptype)=="undefined" || isNaN(ptype)){
			alert("异常错误！系统获取 ptype 参数错误，错误代码：ptype is undefined!");
			 return;
		}
	 var peerbaseref =$("#sel_base").val();
	 if(peerbaseref.length<1){
		 alert("请选择评价主题!");
		 return;
	 }
	 var deptstr ="-1";
	 var deptArr =$("input[type='checkbox'][name='ck_dept']").filter(function(){
		 return this.checked && this.value.Trim().length>0;
	 });
	 if(deptArr.length>0){
		 $.each(deptArr,function(idx,im){
			 if(deptstr.length>0)
				 deptstr+=",";
			 deptstr+=im.value.Trim();
		 });
	 }
	 var rdo_region =$("input[type='radio'][name='rdo_region']").filter(function(){return this.checked});
	 if(rdo_region.val().Trim()=='all'){
		 deptstr="";
	 }
	 $.ajax({
			url:'peerlog!qryLogAndItemByRef.action',
			type:'post',
			data:{peerbaseref:peerbaseref,
				  ptype:ptype,
				  deptstr:deptstr
				  },
			dataType:'json',
		    error:function(){
			  alert("异常错误！系统未响应！");
			},
		    success:function(rps){  
				var len =0;
				//统计结果
				var isStu =  false;
				if(rps.objList[3]!=null && rps.objList[3]=="1"){
					 isStu =  true;
				}
				if(rps.objList[1]!=null && rps.objList[1].length>1){
					var h ="";
					$.each(rps.objList[1],function(ix,im){
						if(ix==0){
							var title =im.TBLTITLE;
							titleArr=title.split(",");
							if(titleArr.length>0){
								for(var j=0;j<titleArr.length;j++){
									if(!isNaN(titleArr[j])){
										len+=1;
									}
								}
							}
						}else{
							if(ix%2==0){
								h+="<tr class='Trbg1'>";
							}else{
								h+="<tr>";
							}
							 
							$.each(titleArr,function(cix,cim){
									bo =true;
									//是否为实验员
									var isexp =false;
									if(ptype=="2" && im["IS601"]!=null && im["IS601"]=="1"){
										isexp=true;
									}
									if(cim.Trim()=="USER_ID"){
										var uid =im[cim];
									}else if(cim.Trim()=="IS601"){
									
									}else if(cim.Trim()=="TEACHER_NAME"){
										if(ptype=="2"){
											if(isexp)
												h+="<td onmouseover='showTeaName(this)' onmouseout='removeTeaNameDiv()' id='td_exp_"+ (ix+1) +"' style='color:#228B22'>"+ im[cim] +"</td>";
											else
												h+="<td onmouseover='showTeaName(this)' onmouseout='removeTeaNameDiv()' id='td_tea_"+ (ix+1) +"' style='color:#1E90FF'>"+ im[cim] +"</td>";
										}else
											h+="<td >"+ im[cim] +"</td>";									
									}else{
										if(im[cim]!=null){
											var formartNum = 0;
											if(!isNaN(im[cim])){
												formartNum = Math.round(parseFloat(im[cim])*100)/100;
											}
											h+="<td>"+ formartNum +"</td>";
										}else{
											h+="<td id=tr_"+ ix +"_"+ cix +">未评</td>";
										}
									}
							});
							h+="</tr>";
						}
					});
					//恢复表格宽度
					$("#titleTbl").removeAttr("style");
					$("#initList").removeAttr("style");
					$("#initList").html(h);
				}else{
					$("#initList").html("<tr><td>暂无数据!</td></tr>");
				}
				//鼠标划过样式
				$("#initList tr").hover(function(){
					$(this).addClass("Trbg2");
				},function(){
					$(this).removeClass("Trbg2");
				});
				
				if(ptype=="2"){
					len = len/2
				}else if(ptype=="3" && isStu){
					len = len/2
				}
				
				$("#initList tr").each(function(idx,itm){
					var tmCount = 0;
					$(itm).children("td").each(function(ix,im){
						if(ix>0){
							if(im.innerHTML.Trim()=="未评"){
								tmCount++;
							}else
								tmCount=0;
							if(tmCount==len){
								var id=im.id;
								if(typeof(id)!="undefined"){
									var trrow=id.substring(id.indexOf('_')+1,id.lastIndexOf('_'));
									var cols=id.substring(id.lastIndexOf('_')+1);
									for(i=0;i<tmCount;i++){
										$("#tr_"+trrow+"_"+(cols-i)).remove();
									}
								}
							}
						}
					});
				});
				//出横向滚动条
				if(len>=8){
					$("#initList").css("width",(len+3)*81);
					$("#titleTbl").css("width",(len+3)*81);
				}
				$("#initList").hide();
				$("#initList").show("fast");
			}
	 });
 }
 
 /**
	添加页面浮动层特效
	*/
	function addColLister(){
		$("#tbl_col td").filter(function(){return this.id.indexOf('child_name_')!=-1}).hover(
			function(){
				genderShowdiv(this);
			},function(){
				var divObj=$("#div_tmp_show01");
				if(divObj.length>0)
					divObj.remove();
			}
		);
	}
	/**
	 生成浮动层，并显示到鼠标位置
	*/
	function genderShowdiv(obj){
				//获取数据
				var htm=$(obj).children("textarea").val().Trim();
				$("#div_tmp_show01").remove();
				//得到准确的 鼠标位置
				var y=mousePostion.y;
				var x=mousePostion.x;
				//判断是不是IE8以下的浏览器浏览
				if ($.browser.msie && (parseInt($.browser.version) <= 7)){
					y+=parseFloat(document.documentElement.scrollTop);
					x+=parseFloat(document.documentElement.scrollLeft); 
				}		
				y+=5;
				var h='<div id="div_tmp_show01" '
						+'style="position:absolute;padding:5px;border:1px solid green;left:'
						+x+'px;top:'+y+'px;background-color:white">'+htm+'</div>';
				$("body").append(h);
	}
	
	
	function addTdListener(){
		$("#initList td").filter(function(){return this.id.indexOf("td_child_name_")!=-1}).hover(
					function(){
						showDetailContents(this);
					},
					function(){
						var divObj = $("#div_show_details");
						if(divObj.length>0){
							divObj.remove();
						}
					}
				);
	}
	
	function showDetailContents(obj){
		var htm = $(obj).children("input").val().Trim();
		//$(obj).css("cursor","pointer");
		$("#div_show_details").remove();
		//鼠标位置
		var x = mousePostion.x;
		var y = mousePostion.y;
		if($.browser.msie && (parseInt($.browser.version) <=7)){
			x+=parseFloat(document.documentElement.scrollLeft);
			y+=parseFloat(document.documentElement.scrollTop);
		}
		y+=5;
		var html ='<div id="div_show_details"'
					+'style="position:absolute;padding:5px;border:1px solid green;'
					+'left:'+ x +'px;top:'+ y +'px;background-color:white">'+ htm +'</div>';
		$("body").append(html);
	}

 
 
 
 
 
	