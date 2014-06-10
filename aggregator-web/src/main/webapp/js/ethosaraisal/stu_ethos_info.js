/**
 * 获取学员校风情况
 * @return
 */
var stuno="";
function getStuEthosDetail(){	

//	var stuno="";
//	if(document.getElementById("stuinfo").selectedIndex!=-1){	
//		var stuno_stuname=document.getElementById("stuinfo").options[document.getElementById("stuinfo").selectedIndex].text.Trim();		
//		if(stuno_stuname.Trim().length>0){
//			$("#txt_stuno_stuname").val(stuno_stuname); 
//			stuno=stuno_stuname.Trim().split('/')[0];		
//		}
//	}
//	var userid="";
//	if(stuno.Trim().length>0){
//		userid=$("#stuinfo").val().Trim();
//	}
    
    var classid = cls_id;
    if(classid.Trim().length<1){
    	alert("系统尚未获取到需要操作的学员的班级信息，请到年级校风汇总页面选择班级信息！");
    	toback();
    }
    var weekid = weekId; 
    if(weekid.Trim().length<1){
    	alert("系统尚未获取到需要操作的学员的周次信息，请到年级校风汇总页面选择周次信息！");
    	toback();
    }	
       
    $("textarea").val(''); 
    $("#stuethTab tr:gt(0) td input").val("");

	$.ajax({
		url:"stuethos?m=loadethos",
		type:'post',
		dataType:'json',
		cache:false,
		data:{			    
		    classid:classid.Trim(),
		    weekid:weekid.Trim()
	    },
	    error:function(){
	    	alert("系统为响应，请稍后重试！");
	    },
	    success:function(rjson){
	    	if(rjson.type=="error"){
	    		alert(rjson.msg);
	    		return;
	    	}else{
	    		var showObj = "";
	    		showObj+="<tr>";
				showObj+='<th width="30"><input type="checkbox" name="checkAll" id="checkAll" onClick="check_all()" /></th>';		
				showObj+="<th width='80'>学号</th>";
				showObj+="<th width='80'>姓名</th>";
				showObj+="<th width='80'>病假情况</th>";
				showObj+="<th width='80'>病假数</th>";
				showObj+="<th width='80'>事假情况</th>";
				showObj+="<th width='80'>事假数</th>";
				showObj+="<th width='80'>早退情况</th>";
				showObj+="<th width='80'>早退数</th>";
				showObj+="<th width='80'>旷课情况</th>";
				showObj+="<th width='80'>旷课数</th>";
				showObj+="<th width='80'>迟到情况</th>";
				showObj+="<th width='80'>迟到数</th>";
				showObj+="<th width='80'>胸卡未带</th>";
				showObj+="<th width='80'>胸卡未带次数</th>";
				showObj+="<th width='80'>校服情况</th>";
				showObj+="<th width='80'>校服情况数</th>";
				showObj+="<th width='80'>行规情况</th>";
				showObj+="<th width='80'>行规次数</th>";
				showObj+="<th width='80'>未还书记录</th>";
				showObj+="<th width='80'>未还书次数</th>";
				showObj+="<th width='80'>好人好事</th>";
				showObj+="<th width='80'>好人好事次数</th>";					
				showObj+="</tr>";
	    		if(rjson.objList.length>0){
	    			var ishas=false;	    			
	    			$.each(rjson.objList,function(idx,itm){	    				    	    					
	    				if(typeof(itm)=="object"){	   
	    					itm.sickleave = typeof(itm.sickleave)  == "undefined"?"&nbsp;":itm.sickleave;
	    					itm.thingleave = typeof(itm.thingleave) == "undefined"?"&nbsp;":itm.thingleave;
	    					itm.leaveearly = typeof(itm.leaveearly ) == "undefined"?"&nbsp;":itm.leaveearly;
	    					itm.absenteeism = typeof(itm.absenteeism ) == "undefined"?"&nbsp;":itm.absenteeism;
	    					itm.late = typeof( itm.late )== "undefined"?"&nbsp;":itm.late;
	    					itm.discipline = typeof(itm.discipline)  == "undefined"?"&nbsp;":itm.discipline;
	    					itm.goodthing = typeof(itm.goodthing)  == "undefined"?"&nbsp;":itm.goodthing;
	    					itm.badge = typeof( itm.badge )== "undefined"?"&nbsp;":itm.badge;
	    					itm.uniforms = typeof( itm.uniforms )== "undefined"?"&nbsp;":itm.uniforms;
	    					itm.linere = typeof( itm.linere )== "undefined"?"&nbsp;":itm.linere;
	    					itm.rebook = typeof( itm.rebook )== "undefined"?"&nbsp;":itm.rebook;
	    					
	    					itm.sickleavenum = typeof(itm.sickleavenum)  == "undefined"?"&nbsp;":itm.sickleavenum;
	    					itm.thingleavenum = typeof(itm.thingleavenum)  == "undefined"?"&nbsp;":itm.thingleavenum;
	    					itm.leaveearlynum = typeof(itm.leaveearlynum) == "undefined"?"&nbsp;":itm.leaveearlynum;
	    					itm.absennum = typeof(itm.absennum)  == "undefined"?"&nbsp;":itm.absennum;
	    					itm.latenum = typeof(itm.latenum) == "undefined"?"&nbsp;":itm.latenum;
	    					itm.disciplinenum = typeof(itm.disciplinenum) == "undefined"?"&nbsp;":itm.disciplinenum;
	    					itm.goodthingnum = typeof(itm.goodthingnum) == "undefined"?"&nbsp;":itm.goodthingnum;
	    					itm.badgenum = typeof(itm.badgenum) == "undefined"?"&nbsp;":itm.badgenum;
	    					itm.uniformsnum = typeof(itm.uniformsnum) == "undefined"?"&nbsp;":itm.uniformsnum;
	    					itm.linerenum = typeof(itm.linerenum) == "undefined"?"&nbsp;":itm.linerenum;
	    					itm.rebooknum = typeof(itm.rebooknum) == "undefined"?"&nbsp;":itm.rebooknum;
	    					
	    					showObj+="<tr>";
		    				showObj+='<td><input type="checkbox" name="seId" onclick="showDeleteBtn();" value="'+itm.ref+'" /></td>';
		    				showObj+='<td>'+itm.stuno+'</td>';
	    					showObj+='<td>'+itm.stuname+'</td>';
	    					var sickleaveLen=itm.sickleave;
	    					if(itm.sickleave.replace("\n",'').replace("&nbsp;",'').Trim().length>5){
	    						sickleaveLen='<a href="javascript:showWatchDiv(\'showDetailDiv\',\'txtare_'+idx+'_sickleave\',\'detailText\')">';
	    						sickleaveLen+=itm.sickleave.replace("\n",'').replace("&nbsp;","").substring(0,5)+'...</a><textarea style="display:none"  id="txtare_'+idx+'_sickleave">'+itm.sickleave+'</textarea>';
	    					}
	    					showObj+='<td>'+sickleaveLen+'</td>';
	   						showObj+='<td>'+itm.sickleavenum+'</td>';
	   						var thingleavelen=itm.thingleave;
	    					if(itm.thingleave.replace("\n",'').replace("&nbsp;",'').Trim().length>5){
	    						thingleavelen='<a href="javascript:showWatchDiv(\'showDetailDiv\',\'txtare_'+idx+'_thingleave\',\'detailText\')">';
	    						thingleavelen+=itm.thingleave.substring(0,5)+'...</a><textarea style="display:none"  id="txtare_'+idx+'_thingleave">'+itm.thingleave+'</textarea>';
	    					}
	   						showObj+='<td>'+thingleavelen+'</td>';
	   						showObj+='<td>'+itm.thingleavenum+'</td>';
	   						var leaveearlylen=itm.leaveearly;
	    					if(itm.leaveearly.replace("\n",'').replace("&nbsp;",'').Trim().length>5){
	    						leaveearlylen='<a href="javascript:showWatchDiv(\'showDetailDiv\',\'txtare_'+idx+'_leaveearly\',\'detailText\')">';
	    						leaveearlylen+=itm.leaveearly.replace("\n",'').replace("&nbsp;","").substring(0,5)+'...</a><textarea style="display:none"  id="txtare_'+idx+'_leaveearly">'+itm.leaveearly+'</textarea>';
	    					}
	   						showObj+='<td>'+leaveearlylen+'</td>';
	    					showObj+='<td>'+itm.leaveearlynum+'</td>';
	    					
	   						var absenteeismlen=itm.absenteeism;
	    					if(itm.absenteeism.replace("\n",'').replace("&nbsp;",'').Trim().length>5){
	    						absenteeismlen='<a href="javascript:showWatchDiv(\'showDetailDiv\',\'txtare_'+idx+'_absent\',\'detailText\')">';
	    						absenteeismlen+=itm.absenteeism.replace("\n",'').replace("&nbsp;","").substring(0,5)+'...</a><textarea style="display:none"  id="txtare_'+idx+'_absent">'+itm.absenteeism+'</textarea>';
	    					}
	    					showObj+='<td>'+absenteeismlen+'</td>';
	    					showObj+='<td>'+itm.absennum+'</td>';
	    					
	    					var latelen=itm.late;
	    					if(itm.late.replace("\n",'').replace("&nbsp;",'').Trim().length>5){
	    						latelen='<a href="javascript:showWatchDiv(\'showDetailDiv\',\'txtare_'+idx+'_late\',\'detailText\')">';
	    						latelen+=itm.late.replace("\n",'').replace("&nbsp;","").substring(0,5)+'...</a><textarea style="display:none"  id="txtare_'+idx+'_late">'+itm.late+'</textarea>';
	    					}
	    					showObj+='<td>'+latelen+'</td>';
	    					showObj+='<td>'+itm.latenum+'</td>';    						
	    					
	    					var disciplinelen=itm.discipline;
	    					if(itm.late.replace("\n",'').replace("&nbsp;",'').Trim().length>5){
	    						disciplinelen='<a href="javascript:showWatchDiv(\'showDetailDiv\',\'txtare_'+idx+'_late\',\'detailText\')">';
	    						disciplinelen+=itm.discipline.replace("\n",'').replace("&nbsp;","").substring(0,5)+'...</a><textarea style="display:none"  id="txtare_'+idx+'_late">'+itm.discipline+'</textarea>';
	    					}	    					
//	    					showObj+='<td>'+itm.discipline+'</td>';    						
//	   						showObj+='<td>'+itm.disciplinenum+'</td>';
	    					//胸卡未带，
	    					var badge=itm.badge; 
	    					if(itm.badge.replace("\n",'').replace("&nbsp;","").Trim().length>5){
	    						badge='<a href="javascript:showWatchDiv(\'showDetailDiv\',\'txtare_'+idx+'_badge\',\'detailText\')">';
	    						badge+=itm.badge.replace("\n",'').replace("&nbsp;","").Trim().substring(0,5)+'...</a><textarea style="display:none"  id="txtare_'+idx+'_badge">'+itm.badge+'</textarea>';
	    						
	    					}
	    					showObj+='<td>'+badge+'</td>';
	    					showObj+='<td>'+itm.badgenum+'</td>'; 
	    					//校服情况，  --UNIFORMS
	    					var uniforms=itm.uniforms;
	    					if(itm.uniforms.replace("\n",'').replace("&nbsp;","").Trim().length>5){
	    						uniforms='<a href="javascript:showWatchDiv(\'showDetailDiv\',\'txtare_'+idx+'_uniforms\',\'detailText\')">';
	    						uniforms+=itm.uniforms.replace("\n",'').replace("&nbsp;","").Trim().substring(0,5)+'...</a><textarea style="display:none"  id="txtare_'+idx+'_uniforms">'+itm.uniforms+'</textarea>';
	    						
	    					}
	    					showObj+='<td>'+uniforms+'</td>';
	    					showObj+='<td>'+itm.uniformsnum+'</td>'; 
	    					//行规情况，  --LINERE
	    					var linere=itm.linere;
	    					if(itm.linere.replace("\n",'').replace("&nbsp;","").Trim().length>5){
	    						linere='<a href="javascript:showWatchDiv(\'showDetailDiv\',\'txtare_'+idx+'_linere\',\'detailText\')">';
	    						linere+=itm.linere.replace("\n",'').replace("&nbsp;","").Trim().substring(0,5)+'...</a><textarea style="display:none"  id="txtare_'+idx+'_linere">'+itm.linere+'</textarea>';
	    						
	    					}
	    					showObj+='<td>'+linere+'</td>';
	    					showObj+='<td>'+itm.linerenum+'</td>'; 
	    					
	    					//还书情况，  --REBOOK
	    					var rebook=itm.rebook;
	    					if(itm.rebook.replace("\n",'').replace("&nbsp;","").Trim().length>5){
	    						rebook='<a href="javascript:showWatchDiv(\'showDetailDiv\',\'txtare_'+idx+'_rebook\',\'detailText\')">';
	    						rebook+=itm.rebook.replace("\n",'').replace("&nbsp;","").Trim().substring(0,5)+'...</a><textarea style="display:none"  id="txtare_'+idx+'_rebook">'+itm.rebook+'</textarea>';
	    						
	    					}
	    					showObj+='<td>'+rebook+'</td>';
	    					showObj+='<td>'+itm.rebooknum+'</td>'; 
	    					
	    					
	   						var goodthinglen=itm.goodthing;
	   						if(itm.goodthing.replace("\n",'').replace("&nbsp;",'').Trim().length>5){
	   							goodthinglen='<a href="javascript:showWatchDiv(\'showDetailDiv\',\'txtare_'+idx+'_goodthing\',\'detailText\')">';
	   							goodthinglen+=itm.goodthing.replace("\n",'').replace("&nbsp;","").substring(0,5)+'...</a><textarea style="display:none"  id="txtare_'+idx+'_goodthing">'+itm.goodthing+'</textarea>';
	    					}	
	   						showObj+='<td>'+goodthinglen+'</td>';
	   						showObj+='<td>'+itm.goodthingnum+'</td>';
	    					showObj+="</tr>"; 				    					
	    				    				
  	    				    if(itm.userinfo.ref==userid){ 
	    				
			    				goodthingscore=disciplinescore=latescore=absenscore=leaveearlyscore=thingleavescore=sickleavescore='';
			    				badgescore =uniformsscore=linerescore= rebookscore='';
			    			    //病假情况
			    				$("#sickleave").val(itm.sickleave.replace("&nbsp;",""));
			    				if(typeof(itm.sickleavescore)!="undefined"&&!isNaN(itm.sickleavescore.toString().replace('-','')))		
			    					sickleavescore=(parseFloat(itm.sickleavescore)>0?'-':'')+itm.sickleavescore;
			    				$("#sickleavescore").val(sickleavescore);
			    				if(typeof(itm.sickleavenum)!="undefined"&&!isNaN(itm.sickleavenum.toString()))		
			    					$("#sickleavenum").val(itm.sickleavenum);
			    				
			    				//事假情况
			    				$("#thingleave").val(itm.thingleave.replace("&nbsp;",""));
			    				if(typeof(itm.thingleavescore)!="undefined"&&!isNaN(itm.thingleavescore.toString().replace('-','')))		
			    					thingleavescore=(parseFloat(itm.thingleavescore)>0?'-':'')+itm.thingleavescore;
			    				$("#thingleavescore").val(thingleavescore);	    				
			    				if(typeof(itm.thingleavenum)!="undefined"&&!isNaN(itm.thingleavenum.toString()))		
			    					$("#thingleavenum").val(itm.thingleavenum);
			    				//早退情况
			    				$("#leaveearly").val(itm.leaveearly.replace("&nbsp;",""));
			    				if(typeof(itm.leaveearlyscore)!="undefined"&&!isNaN(itm.leaveearlyscore.toString().replace('-','')))		
			    					leaveearlyscore=(parseFloat(itm.leaveearlyscore)>0?'-':'')+itm.leaveearlyscore;
			    				$("#leaveearlyscore").val(leaveearlyscore);	 
			    				if(typeof(itm.leaveearlynum)!="undefined"&&!isNaN(itm.leaveearlynum.toString()))		
			    					$("#leaveearlynum").val(itm.leaveearlynum);
			    				//旷课情况
			    				$("#absenteeism").val(itm.absenteeism.replace("&nbsp;",""));
			    				if(typeof(itm.absenscore)!="undefined"&&!isNaN(itm.absenscore.toString().replace('-','')))		
			    					absenscore=(parseFloat(itm.absenscore)>0?'-':'')+itm.absenscore;
			    				$("#absenscore").val(absenscore);	 	    
			    				if(typeof(itm.absennum)!="undefined"&&!isNaN(itm.absennum.toString()))		
			    					$("#absennum").val(itm.absennum);
			    				//迟到情况
			    				$("#late").val(itm.late.replace("&nbsp;",""));
			    				if(typeof(itm.latescore)!="undefined"&&!isNaN(itm.latescore.toString().replace('-','')))		
			    					latescore=(parseFloat(itm.latescore)>0?'-':'')+itm.latescore;
			    				$("#latescore").val(latescore);	 
			    				if(typeof(itm.latenum)!="undefined"&&!isNaN(itm.latenum.toString()))		
			    					$("#latenum").val(itm.latenum);
			    				
			    				//胸卡情况
			    				$("#badge").val(itm.badge.replace("&nbsp;",""));
			    				if(typeof(itm.badgescore)!="undefined"&&!isNaN(itm.badgescore.toString().replace('-','')))		
			    					badgescore=(parseFloat(itm.badgescore)>0?'-':'')+itm.badgescore;
			    				$("#badgescore").val(badgescore);	 
			    				if(typeof(itm.badgenum)!="undefined"&&!isNaN(itm.badgenum.toString()))		
			    					$("#badgenum").val(itm.badgenum);
			    				
			    				//校服情况
			    				$("#uniforms").val(itm.uniforms.replace("&nbsp;",""));
			    				if(typeof(itm.uniformsscore)!="undefined"&&!isNaN(itm.uniformsscore.toString().replace('-','')))		
			    					uniformsscore=(parseFloat(itm.uniformsscore)>0?'-':'')+itm.uniformsscore;
			    				$("#uniformsscore").val(uniformsscore);	 
			    				if(typeof(itm.uniformsnum)!="undefined"&&!isNaN(itm.uniformsnum.toString()))
			    					$("#uniformsnum").val(itm.uniformsnum);
			    				
			    				//行规情况
			    				$("#linere").val(itm.linere.replace("&nbsp;",""));
			    				if(typeof(itm.linerescore)!="undefined"&&!isNaN(itm.linerescore.toString().replace('-','')))		
			    					linerescore=itm.linerescore; 
			    				$("#linerescore").val(linerescore);	 
			    				if(typeof(itm.linerenum)!="undefined"&&!isNaN(itm.linerenum.toString()))		
			    					$("#linerenum").val(itm.linerenum);
			    				
			    				//还书记录情况
			    				$("#rebook").val(itm.rebook.replace("&nbsp;",""));
			    				if(typeof(itm.rebookscore)!="undefined"&&!isNaN(itm.rebookscore.toString().replace('-','')))		
			    					rebookscore=(parseFloat(itm.rebookscore)>0?'-':'')+itm.rebookscore;
			    				$("#rebookscore").val(rebookscore);	 
			    				if(typeof(itm.rebooknum)!="undefined"&&!isNaN(itm.rebooknum.toString()))		
			    					$("#rebooknum").val(itm.rebooknum);
			    				/*
			    				//违纪情况
			    				$("#discipline").val(itm.discipline.replace("&nbsp;",""));
			    				if(typeof(itm.disciplinescore)!="undefined"&&!isNaN(itm.disciplinescore.toString().replace('-','')))		
			    					disciplinescore=(parseFloat(itm.disciplinescore)>0?'-':'')+itm.disciplinescore;
			    				$("#disciplinescore").val(disciplinescore);	 
			    				if(typeof(itm.disciplinenum)!="undefined"&&!isNaN(itm.disciplinenum.toString()))		
			    					$("#disciplinenum").val(itm.disciplinenum);
			    			    */		
			    				//好人好事情况
			    				$("#goodthing").val(itm.goodthing.replace("&nbsp;",""));
			    				if(typeof(itm.goodthingscore)!="undefined"&&!isNaN(itm.goodthingscore.toString().replace('+','')))		
			    					goodthingscore=(parseFloat(itm.goodthingscore)>0?'+':'')+itm.goodthingscore;
			    				$("#goodthingscore").val(goodthingscore);
			    				if(typeof(itm.goodthingnum)!="undefined"&&!isNaN(itm.goodthingnum.toString()))		
			    					$("#goodthingnum").val(itm.goodthingnum);
			    			
			    				ishas=true;			    			
	    				    }	    				
	    				}
	    			});
	    			if(ishas){
	    				//修改
	    				$("#doadd").attr("disabled",true);
	    				$("#doclear").removeAttr("disabled");
	    			   	$("#doupdate").removeAttr("disabled");
	    			   	$("#dodelete").removeAttr("disabled");	    			   	
	    			}else{	  
	    				//添加
	    				$("#doupdate").attr("disabled",true);
						$("#dodelete").attr("disabled",true);
						$("#doadd").removeAttr("disabled");
					  	$("#doclear").removeAttr("disabled");
	    			}
	    		}else{
		    	    //开始查询统计数据
		    		showObj = "<tr><td align='center'>没有该学员校风评比信息！</td></tr>";		    	
	    			$("#doadd").removeAttr("disabled");
					$("#doupdate").attr("disabled",true);
					$("#dodelete").attr("disabled",true);
				  	$("#doclear").attr('disabled',true);
	    		}
	    		
	    		$("#dataTbl").html(showObj);
	    		//获取有多少列显示。每列是81px.那么这个表格总宽度是 81*列数
	    		var tdColumnLen=22;
	    		$("#dataTbl").css("width",tdColumnLen*81);
	    		//启用控件
	    		$("textarea").removeAttr("disabled");	    		
	    		$("text").removeAttr("disabled");
	    		//初始化
	    		$("#dataTbl>tbody>tr").css("background-color",""); 
	    		$("#dataTbl>tbody>tr").hover(function(){
	    			$(this).attr("preBgColor",this.className);
	    			$(this).addClass("Trbg2");	    			
	    		},function(){
	    			$(this).removeClass("Trbg2");
	    			$(this).addClass($(this).attr("preBgColor"));
	    		});
	    		
	    		
	    		$("#dataTbl").parent().bind("scroll",function(){
	    			$(".TabLayoutColumn").attr("scrollLeft",this.scrollLeft);			
	    		});
	    		$("#dataTbl tr td").filter(function(){return stuno.Trim().length>0&&$(this).html().Trim().indexOf(stuno)!=-1}).parent().css("background-color","red");
	    		$("#dataTbl tr td").filter(function(){return stuno.Trim().length>0&&$(this).html().Trim().indexOf(stuno)!=-1}).parent().children().first().children().attr("checked",true);
	    	}
	    }	    
	});
}

/**
 * 加载选择学生的文本框-----自动补齐
 * @return
 */	
function loadStudentByClass(clsid,year,pattern){
	$.ajax({
		url:"student?m=getstudent",
		type:'post',
		dataType:'json',
		cache:false,
		data:{			    
			classId:clsid,
			year:year,
			pattern:pattern
	    },
	    error:function(){
	    	alert("系统为响应，请稍后重试！");
	    },
	    success:function(rps){
			if(rps.type=="error")
				alert(rps.msg);
			else{
				$("#stuinfo").autocomplete(rps.objList,{
					minChars: 0,
					width: 150,
					matchContains: true,
					autoFill: false,
					formatItem: function(row, i, max) {
						return  row.stuno+"/"+row.stuname;
					},
					formatMatch: function(row, i, max) {
						return  row.stuno+"/"+row.stuname;
					},
					formatResult: function(row) {						
						return  row.stuno+"/"+row.stuname;
					},selectedoperate:function(v){
						//
						stuno=v.split("/")[0];
						getUseridByStuno(v.split("/")[0]);
					}				
			    }); 
			}
	    }
	});
}

//根据学号查询学生主键
var userid='';//记录所选学生的主键
function getUseridByStuno(stuno){
	$.ajax({
		url:"student?m=getref",
		type:'post',
		dataType:'json',
		cache:false,
		data:{			    
			stuno:stuno
	    },
	    error:function(){
	    	alert("系统为响应，请稍后重试！");
	    },
	    success:function(rps){
			if(rps.type=="error")
				alert(rps.msg);
			else{
				userid=rps.objList[0].userref;
				//alert(userid);
				getStuEthosDetail();
			}
	    }
	});
}

/**
 * 扣分级联
 * @param 扣分项描述
 * @param 扣分次数
 * @param 扣分数量
 * @param 扣分类型
 * @return
 */
function Minuspoint(objname,numname,scorename,type){
    var objval = $("#"+objname).val();
    var numval=$("#"+numname).val();
    var scoreval=$("#"+scorename).val();
	if(!checkNumber( objval, objname, numval, numname, scoreval, scorename))
		return;
	if(typeof(type)=="undefined"||type==null||type.length<1){
		alert('系统异常，尚未获取到扣分类型，请刷新页面后重试!');
		return;
	}
	 $.ajax({
		 url:'stuethos?m=getscore',
		 type:'post',
		dataType:'json',
		cache:false,
		data:{type:type},
		error:function(){
			alert('服务器未响应，请稍后重试!');			
		},success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				if(rps.objList.length<1){
					alert('在数据文件中，没有发现您要‘'+type+'’的扣分数据！请联系管理员！');
				}else{
					var scval=$("#"+scorename).val().Trim();
					var s=(scval.length<1||isNaN(scval))?0:parseFloat(scval);	
					
					//var freeval=$("#"+numname).attr("freeval");
					
					s=numval*parseFloat(rps.objList[0]);
					
					//if(freeval==$("#"+numname).val().Trim())
						//return;
					$("#"+scorename).val(s);
					//$(this).attr("freeval",'');
				}				
			}
		}		
	 });	
}

function checkNumber( obj, objname, objnum, objnumname, objscore, objscorename){
	if (obj.Trim().length > 0 && objnum.Trim().length==0 ) {
		alert("请输入数量，方便完成操作！");
		$("#"+objnumname).focus();
		return false;
	}
	if(objnum.Trim().length>=0&&isNaN(objnum.Trim())){  
		alert('数量必须为整数，请正确输入！');
		$("#"+objnumname).focus();
		return false;
	}
	/*if (objnum.Trim().length>=0 && objnum.Trim() == '0') {
		alert('数量必须是大于等于0 的整数，请正确输入！');
		$("#"+objnumname).select();
		return false;
	} */ 
	if(objname == 'linere' || objname == 'goodthing'){
		if(objscore.Trim().length>0&&isNaN(objscore.Trim().replace('+','').replace('-',''))){  
			alert('分数必须为整数，请正确输入！');
			$("#"+objscorename).select();
			return false;
		}	
		if(objscore.Trim().replace('+','').replace('-','').length>5){
			alert('分数不能大于99999，请正确输入！');
			$("#"+objscorename).select();
			return false;
		}
	/*	if(objscore.Trim().length>0){  
			alert('分数必须整数，请正确输入！');
			$("#"+objscorename).select();
			return false;
		}*/
		if ((obj.Trim().length>0||objnum.Trim().length>0)&&objscore.Trim().length==0) {
			alert("请输入分数，方便完成操作");
			$("#"+objscorename).select();
			return false;
		}
	}
	return true;
}

/**
 * 添加修改学员校风信息
 * @return
 */
function doAddOrUpdateStuEthos(){
	var stu=userid;		
	if(stu== null){
		alert("您尚未选择需要进行操作的学员信息，请选择！");
		return ;
	}    
    var weekid = weekId;
	if(weekid.Trim().length<1){
		alert("您尚未选择需要操作的周次信息，请返回年级校风信息汇总页面选择周次信息！");
		toback();
	}
	var grade1 = grade;
	if(grade1.Trim().length<1){
		alert("您尚未选择需要操作的年级信息，请返回年级校风信息汇总页面选择年级信息！");
		toback();
	}
	var classid = cls_id;
	if(classid.Trim().length<1){
		alert("您尚未选择需要操作的班级信息，请返回年级校风信息汇总页面选择班级信息！");
		toback();
	}
	
	var sickleave = $("#sickleave").val();
	var sickleavenum = $("#sickleavenum").val();
	var sickleavescore = $("#sickleavescore").val();
	if(!checkNumber( sickleave, "sickleave", sickleavenum, "sickleavenum", sickleavescore, "sickleavescore"))
		return;

    var thingleave = $("#thingleave").val();
    var thingleavenum=$("#thingleavenum").val();
    var thingleavescore=$("#thingleavescore").val();
	if(!checkNumber( thingleave, "thingleave", thingleavenum, "thingleavenum", thingleavescore, "thingleavescore"))
		return;
	
    var leaveearly = $("#leaveearly").val();
    var leaveearlynum=$("#leaveearlynum").val();
    var leaveearlyscore=$("#leaveearlyscore").val();
	if(!checkNumber( leaveearly, "leaveearly", leaveearlynum, "leaveearlynum", leaveearlyscore, "leaveearlyscore"))
		return;
	
    var absenteeism = $("#absenteeism").val();
    var absennum=$("#absennum").val();
    var absenscore=$("#absenscore").val();
	if(!checkNumber( absenteeism, "absenteeism", absennum, "absennum", absenscore, "absenscore"))
		return;
	
    var late = $("#late").val();
    var latenum=$("#latenum").val();
    var latescore=$("#latescore").val();
	if(!checkNumber( late, "late", latenum, "latenum", latescore, "latescore"))
		return;
	/*
    var discipline = $("#discipline").val();
    var disciplinenum=$("#disciplinenum").val();
    var disciplinescore=$("#disciplinescore").val();
	if(!checkNumber( discipline, "discipline", disciplinenum, "disciplinenum", disciplinescore, "disciplinescore"))
		return;
	*/
	var badge = $("#badge").val();
    var badgenum=$("#badgenum").val();
    var badgescore=$("#badgescore").val();
	if(!checkNumber( badge, "badge", badgenum, "badgenum", badgescore, "badgescore"))
		return;
	
	var uniforms = $("#uniforms").val();
    var uniformsnum=$("#uniformsnum").val();
    var uniformsscore=$("#uniformsscore").val();
	if(!checkNumber( uniforms, "uniforms", uniformsnum, "uniformsnum", uniformsscore, "uniformsscore"))
		return;
	
	var linere = $("#linere").val();
    var linerenum=$("#linerenum").val();
    var linerescore=$("#linerescore").val();
	if(!checkNumber( linere, "linere", linerenum, "linerenum", linerescore, "linerescore"))
		return;
	
	var rebook = $("#rebook").val();
    var rebooknum=$("#rebooknum").val();
    var rebookscore=$("#rebookscore").val();
	if(!checkNumber( rebook, "rebook", rebooknum, "rebooknum", rebookscore, "rebookscore"))
		return;
	
    var goodthing = $("#goodthing").val(); 
    var goodthingnum=$("#goodthingnum").val();
    var goodthingscore=$("#goodthingscore").val();
	if(!checkNumber( goodthing, "goodthing", goodthingnum, "goodthingnum", goodthingscore, "goodthingscore"))
		return;
	
	if(sickleave.Trim().length<1&&sickleavenum.Trim().length<1&&sickleavescore.Trim().length<1&&
			thingleave.Trim().length<1&&thingleavenum.Trim().length<1&&thingleavescore.Trim().length<1&&
			leaveearly.Trim().length<1&&leaveearlynum.Trim().length<1&&leaveearlyscore.Trim().length<1&&
			absenteeism.Trim().length<1&&absennum.Trim().length<1&&absenscore.Trim().length<1&&
			late.Trim().length<1&&latenum.Trim().length<1&&latescore.Trim().length<1&&
			badge.Trim().length<1&&badgenum.Trim().length<1&&badgescore.Trim().length<1&&
			uniforms.Trim().length<1&&uniformsnum.Trim().length<1&&uniformsscore.Trim().length<1&&
			linere.Trim().length<1&&linerenum.Trim().length<1&&linerescore.Trim().length<1&&
			rebook.Trim().length<1&&rebooknum.Trim().length<1&&rebookscore.Trim().length<1&&			
			goodthing.Trim().length<1&&goodthingnum.Trim().length<1&&goodthingscore.Trim().length<1
	){
		alert('您尚未对该学员输入任何扣分奖励情况，无法进行录入！请输入信息后点击！');
		return;		
	}
	if(!confirm("确定要进行此操作吗？"))
		return;	

	if(sickleave.length<1)
		sickleave='';
	if(sickleavescore.length<1)
		sickleavescore=0;
	if(sickleavenum.length<1)
		sickleavenum=0;
	if(thingleave.length<1)
		thingleave='';
	if(thingleavenum.length<1)
		thingleavenum=0;
	if(thingleavescore.length<1)
		thingleavescore=0;
	if(leaveearly.length<1)
		leaveearly='';
	if(leaveearlynum.length<1)
		leaveearlynum=0;
	if(leaveearlyscore.length<1)
		leaveearlyscore=0;
	if(absenteeism.length<1)
		absenteeism='';
	if(absennum.length<1)
		absennum=0;
	if(absenscore.length<1)
		absenscore=0;
	if(late.length<1)
		late='';
	if(latenum.length<1)
		latenum=0;
	if(latescore.length<1)
		latescore=0;
	if(badge.length<1)
		badge='';
	if(badgenum.length<1)
		badgenum=0;
	if(badgescore.length<1)
		badgescore=0;
	if(uniforms.length<1)
		uniforms='';
	if(uniformsnum.length<1)
		uniformsnum=0;
	if(uniformsscore.length<1)
		uniformsscore=0;
	if(linere.length<1)
		linere='';
	if(linerenum.length<1)
		linerenum=0;
	if(linerescore.length<1)		
		linerescore=0;
	if(rebook.length<1)
		rebook='';
	if(rebooknum.length<1)
		rebooknum=0;
	if(rebookscore.length<1)
		rebookscore=0;	
	if(goodthing.length<1)
		goodthing='';
	if(goodthingnum.length<1)
		goodthingnum=0;
	if(goodthingscore.length<1)
		goodthingscore=0;	
	
	$.ajax({
		url:'stuethos?m=ajaxsave',
		type:'post',
		dataType:'json',
		cache:false,
		data:{
		    userid:stu.Trim(),
		    weekid:weekid.Trim(),
		    grade:grade1.Trim(),
		    classid:classid.Trim(),
			sickleave:sickleave,
			sickleavenum:sickleavenum,
			sickleavescore:sickleavescore,
			thingleave:thingleave,
			thingleavenum:thingleavenum,
			thingleavescore:thingleavescore,
			leaveearly:leaveearly,
			leaveearlynum:leaveearlynum,
			leaveearlyscore:leaveearlyscore,
			absenteeism:absenteeism,
			absennum:absennum,
            absenscore:absenscore,
			late:late,
			latenum:latenum,
			latescore:latescore,          
            
			badge:badge,
			badgenum:badgenum,
			badgescore:badgescore,
			uniforms:uniforms,
			uniformsnum:uniformsnum,
			uniformsscore:uniformsscore,
			linere:linere,
			linerenum:linerenum,
			linerescore:linerescore,
			rebook:rebook,
			rebooknum:rebooknum,
			rebookscore:rebookscore,
			/*
			discipline:discipline,
			disciplinenum:disciplinenum,
			disciplinescore:disciplinescore,
			*/ 
			goodthing:goodthing,
			goodthingnum:goodthingnum,
			goodthingscore:goodthingscore
	    },
	    error:function(){
	        alert("系统未响应，请稍后重试！");	
	    },success:function(rjson){
	    	if(rjson.type=="success"){
	    		alert(rjson.msg);
	    		getStuEthosDetail();
	    	}else{
	    		alert(rjson.msg);
	    	}
	    }	    
	});
}
/**
 * 返回班级校风信息汇总页面
 */
function toback(){
	location.href="stusethos?m=tolist";
}
/**
 * 清空
 * @return
 */
function toClear(){
	$("textarea").val('');
   	$("#stuethTab tr input").val("")		
}

/**
 * 批量删除
 */
function doArrayDelete(){
	
	var delIdArrayObj=$("input[type='checkbox']").filter(function(){return this.name=='seId'&&this.checked==true});
	var delid='';
	$.each(delIdArrayObj,function(idx,itm){
		delid += itm.value.Trim()+",";
	});
	
	if (delid.Trim().length<1) {
		alert('系统尚未获取您要删除的正确数据标识，请重新选择后操作！\n\n提示：选中网页下方的统计表格前的复选框后，点击删除，进行删除!');
		return ;
	}
	if(!confirm("您确定要删除吗？"))
		return;
	$.ajax({
		url:"stuethos?m=ajaxdel",
	    type:"post",
	    dataType:"json",
	    cache:false,
	    data:{
		    delid:delid
	    },error:function(){
	    	alert("系统为响应，请稍后重试！");
	    },success:function(rjson){
	    	if(rjson.type=="error"){
	    		alert("删除错误，原因：未知！");	    
	    		return;
	    	}else{
	    		alert(rjson.msg);
	    		getStuEthosDetail();
	    	}
	    }
	});	
}
/**
 * 全选
 * @return
 */
function check_all(){
	var ischecked = $("#checkAll").attr("checked");
	
	$("input[name='seId']").filter(function(){return this.type=='checkbox'}).attr("checked",ischecked);
	
	if (ischecked == true && 
			$("input[name='seId']").filter(function(){return this.type=='checkbox'}).length > 0 ) {
		$("#dodelete").removeAttr("disabled");
	}else{
		$("#dodelete").attr("disabled",true);
	}	
}
function showDeleteBtn(){
	var isshow=$("input[name='seId']").filter(function(){return this.type=='checkbox'&&this.checked==true}).length ;
    if (isshow > 0) {
    	$("#dodelete").removeAttr("disabled");
	}else{
		$("#dodelete").attr("disabled",true);	
	}		
}


//------------------------------------以学生查询 考勤
/**
 * 加载班级
 */
function loadClass(){
var termId = $("#termId");
var grade = $("#grade");
if(termId.val().Trim() =="")
    return;
if(grade.val().Trim() =="") 
    return;
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
		  //加载选中项所在的学生
    		//loadStuName();
    		
    		//启用加载
    		//禁止多周，单周选择 
       		//$("input[type='radio']").removeAttr("disabled");
       		//rdoCheckedRetrive();
	    }else{
	        alert(json.msg);
	    }                
	}				
});
}
/**
 * 加载学生
 * @return
 */
function loadStuName(){
	var classObj=$("#classId");
	if(classObj.val().Trim().length<1){
		alert('您尚未选择班级。请选择后操作!\n\n提示：加载班级，必须先依次选择学期，年级!');
		classObj.focus();	
		return;
	}
	var year=document.getElementById("termId").options[document.getElementById("termId").selectedIndex].text.split(' ')[0];
	$.ajax({
		url:"student?m=getstudent",
		type:'post',
		dataType:'json',
		cache:false,
		data:{
		  classId:classObj.val().Trim(),
		  year:year,
		  pattern:'行政班'
	    },
	    error:function(){
	    	alert("系统为响应，请稍后重试！");
	    },
	    success:function(rjson){
	    	if(typeof(rjson.objList)!="undefined"){	
	    		$("#txtSelStuNo").flushCache();//清空缓存
	    		var dat=rjson.objList;	    		
	    		
	    		$("#txtSelStuNo").autocomplete(rjson.objList,{
					minChars: 0,
					width: 150,
					matchContains: true,
					autoFill: false,
					formatItem: function(row, i, max) {
						return  row.stuno+"　"+row.stuname;
					},
					formatMatch: function(row, i, max) {
						return  row.stuno+"　"+row.stuname;
					},
					formatResult: function(row) {						
						return  row.stuno+"　"+row.stuname;
					},selectedoperate:function(v){
						$("#selStuId").val(v.split('　')[0].Trim()); //将学号保存。
					}				
				});
	    	}else{
	    		$("#selStuId").val('');
	    		alert(rjson);
	    	}
	    }
	});
}
/**
 * 用户查看统计  综合选项卡
 * @return
 */
function loadUserStudent_ZH(){
	var termObj=$("#termId");
	if(termObj.val().Trim().length<1){
		alert('您尚未选择您要查看的学期，请选择学期!\n\n提示：一次选择学期，年级，班级后进行查询!');;
		termObj.focus();
		return;
	}
	var gradeObj=$("#grade");
	if(gradeObj.val().Trim().length<1){
		alert('您尚未选择年级，请选择年级!\n\n提示：一次选择学期，年级，班级后进行查询!');;
		gradeObj.focus();
		return;
	}
	var classObj=$("#classId");
	if(classObj.val().Trim().length<1){
		alert('您尚未选择班级，请选择班级!\n\n提示：一次选择学期，年级，班级后进行查询!');;
		classObj.focus();
		return;
	}
	var stuno="";
	var ck=$("#selStuNoOrStuName").attr("checked");
	if(ck==true){
		var stunoObj=$("#txtSelStuNo");
		if(stunoObj.val().Trim().length<1){
			alert("您选择了精确查询学生，但您尚未输入学生'学号/姓名'，请输入!");
			stunoObj.focus();
			return;
		}
		 stuno=stunoObj.val().Trim().split('　')[0];
		 if(stuno.Trim().length<1)
			 stuno=""; 
	}	
	loadUserStudentColumn_ZH(termObj.val(),classObj.val(),stuno);			//查询列，

}
/**
 * 用户查看统计  综合选项卡(查询标题)
 * @return
 */
function loadUserStudentColumn_ZH(termid,classid,stuno){
	//改变按钮li的样式
	$("#liZH").parent().children().removeClass("up");
	$("#liZH").addClass("up");
	
	//初始化显示数据框
	$("#tdcolumn").parent().css("width",826);
	$("#tdcolumn").css("width",81*10);	
	//初始化滚动条
	$("#tdcolumn").parent().attr("scrollLeft",0);
	$("#dataTbl").parent().attr("scrollLeft",0);
	
	$("#tdcolumn").html('<tr><th width="100%" align="center"><img src="img/loading_smail.gif"/>正在加载中!请稍后!</td></tr>');
	var columns = [['周次','学号','姓名','病假情况','病假数','病假分数','事假情况','事假数','事假分数','早退情况','早退分数','旷课情况','旷课数',
	                '旷课分数','迟到','迟到次数','迟到分数','未持胸卡次','胸卡扣分','校服情况','校服次数','校服扣分','行规情况',
	                '行规违纪次','行规扣分','迟还书情况','迟还书次数','还书扣分','好人好事','好人好事次','好人好事分','总分','排名']];
	var imlength=0;
	var colhtml='';
	
		$.each(columns,function(idx,itm){
			colhtml+='<tr';	  
			if(idx%2==0)
				colhtml+=' class="Trbg1"';
			colhtml+='>';
			$.each(itm,function(ix,im){
				colhtml+='<th>'+(im==null?"&nbsp;":im)+'</th>';	
			})	
			colhtml+='</tr>';
			if(imlength<1)
				imlength=itm.length;
		});	    			
	   		
	$("#tdcolumn").parent().css("width",826);
	$("#tdcolumn").css("width",imlength*81);	
	
	$("#tdcolumn").html(colhtml);
	//开始查询数据 
	loadUserStudentData_ZH(termid,classid,stuno);//查询数据
	    	
}
/**
 * 用户查看统计  综合选项卡(查询数据)
 * @return
 */
function loadUserStudentData_ZH(termid,classid,stuno){
	
	
	$("#dataTbl").html('<tr><th width="100%" align="center"><img src="img/loading_smail.gif"/>正在加载中!请稍后!</td></tr>');
 $.ajax({
	 url:"stuethos?m=getethoszh",
		type:'post',
		dataType:'json',
		cache:false,
		data:{termid:termid,
		 	classid:classid,
		 	stuno:stuno,
		 	isshowrank:0
 		},error:function(){
	    	alert("系统为响应，请稍后重试！");
	    },
	    success:function(rjson){
	    	if(rjson.type=="success"){
	    		var imlength=0;
	    		var colhtml='';
	    		if(rjson.objList.length<1){
	    			colhtml+='<tr><th width="100%" align="center">暂无数据！</tr>';
	    		}else{
	    			
	    			$.each(rjson.objList,function(idx,itm){
	    				colhtml+='<tr';
	    					if(idx%2==0)
	    						colhtml+=' class="Trbg1"';
	    					colhtml+='>';
	    				$.each(itm,function(ix,im){
	    					var htm=(im==null?"&nbsp;":im); 
	    					if(im!=null&&im.length>=5&&ix>1&&ix<itm.length-1){  
	    						htm="<a href=\"javascript:showWatchDiv('showDetailDiv','tare_"+idx+"_"+ix+"','detailText');\">"+im.substring(0,5)+"…</a><textarea style='display:none' id=\"tare_"+idx+"_"+ix+"\">"+htm+"</textarea>";
	    					}
	    					colhtml+='<td>'+htm+'</td>';
	    				})	
	    				colhtml+='</tr>';
	    				if(imlength<1)
	    					imlength=itm.length; 
	    			});	
	    			 var posturl="stuethos?m=expStuzh";
		    			var clickPost='javascript:toPostURL(\''+posturl+'\',{termid:'+termid+',classid:'+classid;
		    			clickPost+=',stuno:\''+stuno.Trim()+'\'},true)';					//,ordercolumn:\''+ordercolumn.Trim()+'\',dict:\''+dict+'\'
		    			var explortObjF='<a href="';
		    			explortObjF+=clickPost+'"/><img src="images/an03.gif" width="25" alt="导出Excel" height="25" />导出excel表</a>';
		    			$("#explortExl").html(explortObjF);
		    			
		    		$("#dataTbl").parent().css("width",826); 
		    			//表
		    		$("#dataTbl").parent().css("overflow-x","auto");	    			
		    		$("#dataTbl").css("width",(81*imlength));
			    	if(imlength>=11){			    	
			    		$("#dataTbl").parent().bind("scroll",function(){
			    			$("#tdcolumn").parent().attr("scrollLeft",this.scrollLeft);
			    		});
			    	}		    			
	    		}	    	
	    		
	    		$("#dataTbl").html(colhtml);
	    		
	    		$("#dataTbl tr ").hover(function(){
					$(this).attr("backupClass",this.className);
					$(this).addClass("Trbg2");
				},function(){
					$(this).removeClass("Trbg2");
					$(this).addClass($(this).attr("backupClass"));
				});
	    	}else{
	    		alert(rjson.msg);
	    		$("#dataTbl").html('<tr><th width="100%" align="center"><img src="img/loading_smail.gif"/>'+rjson.msg+'</td></tr>');
	    	}
	    }	 
 });
}

/**
 * 用户查看统计  考勤选项卡
 * @return
 */
function loadUserStudent_KQ(ordercolumn,sort){
	var termObj=$("#termId");
	if(termObj.val().Trim().length<1){
		alert('您尚未选择您要查看的学期，请选择学期!\n\n提示：一次选择学期，年级，班级后进行查询!');;
		termObj.focus();
		return;
	}
	var gradeObj=$("#grade");
	if(gradeObj.val().Trim().length<1){
		alert('您尚未选择年级，请选择年级!\n\n提示：一次选择学期，年级，班级后进行查询!');;
		gradeObj.focus();
		return;
	}
	var classObj=$("#classId");
	if(classObj.val().Trim().length<1){
		alert('您尚未选择班级，请选择班级!\n\n提示：一次选择学期，年级，班级后进行查询!');;
		classObj.focus();
		return;
	}
	var stuno="";
	var ck=$("#selStuNoOrStuName").attr("checked");
	if(ck==true){
		var stunoObj=$("#txtSelStuNo");
		if(stunoObj.val().Trim().length<1){
			alert("您选择了精确查询学生，但您尚未输入学生'学号/姓名'，请输入!");
			stunoObj.focus();
			return;
		}
		 stuno=stunoObj.val().Trim().split('　')[0];
		 if(stuno.Trim().length<1)
			 stuno=""; 
	}
	if(typeof(ordercolumn)=="undefined"||ordercolumn==null)
		ordercolumn="";
	if(typeof(sort)=="undefined"||sort==null)
		sort="";
	loadUserStudentColumn_KQ(termObj.val(),classObj.val(),stuno,ordercolumn,sort);			//查询列，
	
	
}
/**
 * 用户查看统计  考勤选项卡(查询标题)
 * @return
 */
function loadUserStudentColumn_KQ(termid,classid,stuno,ordercolumn,sort){
	//改变按钮li的样式
	$("#liKQ").parent().children().removeClass("up");
	$("#liKQ").addClass("up");
	//初始化框
	$("#tdcolumn").parent().css("width",826);
	$("#tdcolumn").css("width",81*10);	
	//初始化滚动条
	$("#tdcolumn").parent().attr("scrollLeft",0);
	$("#dataTbl").parent().attr("scrollLeft",0);
	
	$("#tdcolumn").html('<tr><th width="100%" align="center"><img src="img/loading_smail.gif"/>正在加载中!请稍后!</td></tr>');
    var columns = [['周次','学号','姓名','病假情况','病假次数','事假情况','事假次数','早退情况','早退次数','旷课情况','旷课次数','迟到情况','迟到次数']];
	var colhtml='';
	var imlength=0;
    $.each(columns,function(idx,itm){
		colhtml+='<tr>' 
		$.each(itm,function(ix,im){
			var imgsrc='<a  onclick="loadUserStudent_KQ(\''+im+'\',\'asc\')"><img src="images/an22.gif"/></a>';
			if(typeof(ordercolumn)!="undefined"&&ordercolumn==im){
				if(typeof(sort)=="undefined"){
					sort="";
				}		    					
				imgsrc=sort.Trim()=="asc"?'<a  onclick="loadUserStudent_KQ(\''+im+'\',\'desc\')"><img src="images/an21.gif"/></a>'
							:'<a  onclick="loadUserStudent_KQ(\''+im+'\',\'asc\')"><img src="images/an22.gif"/></a>';	
			}
			
			colhtml+='<th>'+im+imgsrc+'</th>';	    					
		})	
		colhtml+='</tr>';
		if(imlength<1)
			imlength=itm.length;
	});	    			
	
	$("#tdcolumn").parent().css("width",826);
	$("#tdcolumn").css("width",imlength*81);	    		
	$("#tdcolumn").html(colhtml);
	//开始查询数据 
	loadUserStudentData_KQ(termid,classid,stuno,ordercolumn,sort);//查询数据
	    	
}
/**
 * 用户查看统计  考勤选项卡(查询数据)
 * @return
 */
function loadUserStudentData_KQ(termid,classid,stuno,ordercolumn,dict){
	$("#dataTbl").html('<tr><th width="100%" align="center"><img src="img/loading_smail.gif"/>正在加载中!请稍后!</td></tr>');
	if(ordercolumn.Trim().length<1)
		ordercolumn="";
	if(dict.Trim().length<1)
		dict="";
 $.ajax({
	 url:"stuethos?m=getethoskq", 
		type:'post',
		dataType:'json',
		cache:false,
		data:{termid:termid,
		 	classid:classid,
		 	stuno:stuno,
		 	ordercolumn:ordercolumn,
		 	dict:dict
 		},error:function(){
	    	alert("系统为响应，请稍后重试！");
	    },
	    success:function(rjson){
	    	if(rjson.type=="success"){
	    		var imlength=0;
	    		var colhtml='';
	    		if(rjson.objList.length<1){
	    			colhtml+='<tr><th width="100%" align="center">暂无数据！</tr>';
	    		}else{
	    		
	    			$.each(rjson.objList,function(idx,itm){
	    				colhtml+='<tr'
	    					if(idx%2==0)
	    						colhtml+=' class="Trbg1"';
	    				colhtml+='>';
	    				$.each(itm,function(ix,im){
	    					var htm=(im==null?"&nbsp;":im);
	    					if(im!=null&&im.length>=5&&ix>2){ 
	    						htm="<a href=\"javascript:showWatchDiv('showDetailDiv','tare_"+idx+"_"+ix+"','detailText');\">"+im.substring(0,5)+"…</a><textarea style='display:none' id=\"tare_"+idx+"_"+ix+"\">"+htm+"</textarea>";
	    					}
	    					colhtml+='<td>'+htm+'</td>';
	    				})	
	    				colhtml+='</tr>';
	    				if(imlength<1)
	    					imlength=itm.length;
	    			});	   
	    			
	    			 var posturl="stuethos?m=expStukq";
	    			var clickPost='javascript:toPostURL(\''+posturl+'\',{termid:\''+termid+'\',classid:'+classid;
	    			 clickPost+=',stuno:\''+stuno.Trim()+'\',ordercolumn:\''+ordercolumn.Trim()+'\',dict:\''+dict+'\'},true)';					
	    			var explortObjF='<a href="';
	    			explortObjF+=clickPost+'"/><img src="images/an03.gif" width="25" alt="导出Excel" height="25" />导出excel表</a>';
	    			$("#explortExl").html(explortObjF);
	    		}	  
	    		$("#dataTbl").parent().css("width",826); 
    			//表
    			$("#dataTbl").parent().css("overflow-x","auto");	    			
    			$("#dataTbl").css("width",(81*imlength));
	    		if(imlength>=11){	    	
	    			$("#dataTbl").parent().bind("scroll",function(){
	    				$("#tdcolumn").parent().attr("scrollLeft",this.scrollLeft);
	    			});
	    		}
	    		$("#dataTbl").html(colhtml);	    		
	    	
	    		$("#dataTbl tr").hover(function(){
					this.backupClass=this.className; 
					this.className="Trbg2"; 
				},function(){
					this.className=this.backupClass;
				});
	    	
	    	}else{
	    		alert(rjson.msg);
	    		$("#dataTbl").html('<tr><th width="100%" align="center"><img src="img/loading_smail.gif"/>'+rjson.msg+'</td></tr>');
	    	}
	    }	 
 });
}


//------------------------------------以学生查询 违纪

/**
 * 用户查看统计  违纪选项卡
 * @return
 */
function loadUserStudent_WJ(ordercolumn,sort){
	var termObj=$("#termId");
	if(termObj.val().Trim().length<1){
		alert('您尚未选择您要查看的学期，请选择学期!\n\n提示：一次选择学期，年级，班级后进行查询!');;
		termObj.focus();
		return;
	}
	var gradeObj=$("#grade");
	if(gradeObj.val().Trim().length<1){
		alert('您尚未选择年级，请选择年级!\n\n提示：一次选择学期，年级，班级后进行查询!');;
		gradeObj.focus();
		return;
	}
	var classObj=$("#classId");
	if(classObj.val().Trim().length<1){
		alert('您尚未选择班级，请选择班级!\n\n提示：一次选择学期，年级，班级后进行查询!');;
		classObj.focus();
		return;
	}
	var stuno="";
	var ck=$("#selStuNoOrStuName").attr("checked");
	if(ck==true){
		var stunoObj=$("#txtSelStuNo");
		if(stunoObj.val().Trim().length<1){
			alert("您选择了精确查询学生，但您尚未输入学生'学号/姓名'，请输入!");
			stunoObj.focus();
			return;
		}
		 stuno=stunoObj.val().Trim().split('　')[0];
		 if(stuno.Trim().length<1)
			 stuno=""; 
	}
	if(typeof(ordercolumn)=="undefined"||ordercolumn==null)
		ordercolumn="";
	if(typeof(sort)=="undefined"||sort==null)
		sort="";
	loadUserStudentColumn_WJ(termObj.val(),classObj.val(),stuno,ordercolumn,sort);			//查询列，
	
	
}
/**
 * 用户查看统计  违纪选项卡(查询标题) 
 * @return
 */
function loadUserStudentColumn_WJ(termid,classid,stuno,ordercolumn,sort){
	$("#liHG").parent().children().removeClass("up");
	$("#liHG").addClass("up");
	//初始化显示数据框
	$("#tdcolumn").parent().css("width",826);
	$("#tdcolumn").css("width",81*10);	
	//初始化滚动条
	$("#tdcolumn").parent().attr("scrollLeft",0);
	$("#dataTbl").parent().attr("scrollLeft",0);
	
	
	$("#tdcolumn").html('<tr><th width="100%" align="center"><img src="img/loading_smail.gif"/>正在加载中!请稍后!</td></tr>');
 
	var imlength=0;
	var columns = [['周次','学号','姓名','胸卡总分','胸卡未带次','校服总分','校服次数','行规总分','行规次数','还书总分','未还书数']];
	var colhtml='';
	$.each(columns,function(idx,itm){
		colhtml+='<tr>' 
		$.each(itm,function(ix,im){
			var imgsrc='<a  onclick="loadUserStudent_WJ(\''+im+'\',\'asc\')"><img src="images/an22.gif"/></a>';
			if(typeof(ordercolumn)!="undefined"&&ordercolumn==im){
				if(typeof(sort)=="undefined"){
					sort="";
				}		    					
				imgsrc=sort.Trim()=="asc"?'<a  onclick="loadUserStudent_WJ(\''+im+'\',\'desc\')"><img src="images/an21.gif"/></a>'
							:'<a  onclick="loadUserStudent_WJ(\''+im+'\',\'asc\')"><img src="images/an22.gif"/></a>';	
			} 
			
			colhtml+='<th>'+replaceAll(im,'#','/')+imgsrc+'</th>';	
		});	
		if(imlength<1)
			imlength=itm.length;
		colhtml+='</tr>';
	});	  
	$("#tdcolumn").parent().css("width",826);
	$("#tdcolumn").css("width",imlength*81);
		
	$("#tdcolumn").html(colhtml);	    		
	
	//开始查询数据 
	loadUserStudentData_WJ(termid,classid,stuno,ordercolumn,sort);//查询数据
}

/**
 * 用户查看统计  违纪选项卡(查询数据)
 * @return
 */
function loadUserStudentData_WJ(termid,classid,stuno,ordercolumn,dict){
	$("#dataTbl").html('<tr><th width="100%" align="center"><img src="img/loading_smail.gif"/>正在加载中!请稍后!</td></tr>');
	if(ordercolumn.Trim().length<1)
		ordercolumn="";
	if(dict.Trim().length<1)
		dict="";
 $.ajax({
	 url:"stuethos?m=getethoswj", 
		type:'post',
		dataType:'json',
		cache:false,
		data:{termid:termid,
		 	classid:classid,
		 	stuno:stuno,
		 	ordercolumn:ordercolumn,
		 	dict:dict
 		},error:function(){
	    	alert("系统为响应，请稍后重试！");
	    },
	    success:function(rjson){
	    	if(rjson.type=="success"){
	    		var imlength=0;
	    		var colhtml='';
	    		if(rjson.objList.length<1){
	    			colhtml+='<tr><th width="800px" align="center">暂无数据！</tr>';
	    		}else{
	    			
	    			$.each(rjson.objList,function(idx,itm){
	    				colhtml+='<tr '
	    					if(idx%2==0)
	    						colhtml+=' class="Trbg1"';
	    					colhtml+='>';
		    				$.each(itm,function(ix,im){
		    					var htm=(im==null?"&nbsp;":im);		    					
		    					colhtml+='<td>'+htm+'</td>';
		    				})	
	    				colhtml+='</tr>';
	    				if(imlength<1)
	    					imlength=itm.length;
	    			});	    
	    			 var posturl="stuethos?m=expStuwj";
		    			var clickPost='javascript:toPostURL(\''+posturl+'\',{termid:'+termid+',classid:'+classid;
		    			clickPost+=',stuno:\''+stuno.Trim()+'\',ordercolumn:\''+ordercolumn.Trim()+'\',dict:\''+dict+'\'},true)';
	    			
	    			var explortObjF='<a href="';
	    			explortObjF+=clickPost+'"/><img src="images/an03.gif" width="25" alt="导出Excel" height="25" />导出excel表</a>';
	    			$("#explortExl").html(explortObjF);
	    			
	    			$("#dataTbl").parent().css("width",826); 
	    			$("#dataTbl").css("width",(81*imlength));
	    			if(imlength>=11){	    				
		    			//表
		    			$("#dataTbl").parent().css("overflow-x","auto");
		    			$("#dataTbl").parent().bind("scroll",function(){
		    				$("#tdcolumn").parent().attr("scrollLeft",this.scrollLeft);
		    			});
		    		}
	    		}	    		
	    		$("#dataTbl").html(colhtml);
	    		
	    		$("#dataTbl tr ").hover(function(){
					$(this).attr("backupClass",this.className);
					$(this).addClass("Trbg2");
				},function(){
					$(this).removeClass("Trbg2");
					$(this).addClass($(this).attr("backupClass"));
				});
	    	}else{
	    		alert(rjson.msg);
	    		$("#dataTbl").html('<tr><th width="100%" align="center"><img src="img/loading_smail.gif"/>'+rjson.msg+'</td></tr>');
	    	}
	    }	 
 });
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
	var s=replaceAll($("#"+showObjControlId).val(),"\n",'<br/>'); //将\n换成<br/>在DIV中换行
	$("#"+textObjStrId).html(s);
	$("select").hide();
	showModel(div, false);	
}