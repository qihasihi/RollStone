
//执行分页查询后
function afterajaxList(rps){
	var ghtml='';
	if(rps.type=="error"){
		alert(rps.msg);
	}else{			
		if(rps.objList.length>0){
			ghtml+='<tr><th rowspan="2">学年</th><th colspan="2">第一学期</th><th colspan="2">第二学期</th></tr>';
			ghtml+='<tr><th>开始时间</th><th>结束时间</th><th>开始时间</th><th>结束时间</th>'
			ghtml+='</tr>'; 				
			$.each(rps.objList,function(idx,itm){
				if(idx==0){						
					ghtml+='<tr id="tr1"';
					ghtml+='>';
                    $("#termyear").val(itm.CLASS_YEAR_VALUE);
					ghtml+='<td>'+itm.CLASS_YEAR_NAME+'</td>';
					ghtml+='<td id="tdbtime1" title="点击编辑" onclick="btime1clk(\''+itm.REF1+'\',\''+"tdbtime1"+'\',this,\''+itm.BTIME1+'\')">'+itm.BTIME1+'</td>';
					ghtml+='<td id="tdetime1" title="点击编辑" onclick="etime1clk(\''+itm.REF1+'\',\''+"tdetime1"+'\',this,\''+itm.ETIME1+'\')">'+itm.ETIME1+'</td>';
					ghtml+='<td id="tdbtime2" title="点击编辑" onclick="btime2clk(\''+itm.REF2+'\',\''+"tdbtime2"+'\',this,\''+itm.BTIME2+'\')">'+itm.BTIME2+'</td>';
					ghtml+='<td id="tdetime2" title="点击编辑" onclick="etime2clk(\''+itm.REF2+'\',\''+"tdetime2"+'\',this,\''+itm.ETIME2+'\')">'+itm.ETIME2+'</td>';
					ghtml+='<input type="hidden" id="hbtime1" value="'+itm.BTIME1+'"/>';
					ghtml+='<input type="hidden" id="hetime1" value="'+itm.ETIME1+'"/>';
					ghtml+='<input type="hidden" id="hbtime2" value="'+itm.BTIME2+'"/>';
					ghtml+='<input type="hidden" id="hetime2" value="'+itm.ETIME2+'"/>';
					ghtml+='</tr>';
				}else{
					ghtml+='<tr class="trbg1 font-gray"';
					ghtml+='>';
					ghtml+='<td>'+itm.CLASS_YEAR_NAME+'</td>';
					ghtml+='<td >'+itm.BTIME1+'</td>';
					ghtml+='<td>'+itm.ETIME1+'</td>';
					ghtml+='<td>'+itm.BTIME2+'</td>';
					ghtml+='<td>'+itm.ETIME2+'</td>';
					ghtml+='</tr>';
				}
			});		 
		}else{
			ghtml+='<tr><th>暂无数据 !</th></tr>';
		}
	}
	$('#datatbl').html(ghtml);
}

//第一学期开始时间，点击和提交方法
function btime1clk(ref,columnnum,tobj,text){
	var shtml = "";
	shtml+='<input type="text" id="inputbtime1"   value=\''+text+'\'/>';
	
	//解除事件
	$(tobj).attr("onclick","");
	$(tobj).unbind("click");
	
	$(tobj).html(shtml);
    $("#inputbtime1").bind("blur",function(){
        btime1submit(ref,columnnum,this);

    });
    $("#inputbtime1").focus();
    //弹出日历控件
    WdatePicker({el:'inputbtime1',dateFmt: 'yyyy-MM-dd', skin: 'whyGreen'});
    $("#inputbtime1").click(function(){
        var iframe=$("iframe");
        if(iframe.length>0&&iframe.parent().css("display")!="none")
            return;
        WdatePicker({el:'inputbtime1',dateFmt: 'yyyy-MM-dd', skin: 'whyGreen'});
    });
}
function btime1submit(ref,columnnum,iobj){
    var iframe=$("iframe");
    if(iframe.length>0&&iframe.parent().css("display")!="none")
        return;
    if($(iobj).val().Trim()==$("#hbtime1").val().Trim()){
        $(iobj).val($("#hbtime1").val());
        var shtml="";
        shtml+='<span>'+iobj.value;
        //shtml+='<input type="hidden" id="hbtime1" value="'+iobj.value+'"/>';
        shtml+='</span>';
        $("#tdbtime1").html(shtml);
        $("#tdbtime1").bind("click",function(){
            btime1clk(ref,columnnum,this,$(this).children("span").html().Trim());
        });
        return;
    }

	var date = new Date();
	var year = $("#termyear").val().split("~")[0];
    var btime1 = $(iobj).val();
    var etime1 = $("#hetime1").val();

	if(iobj.value.split("-")[0]!=year){

		$(iobj).val($("#hbtime1").val());
        var shtml="";
        shtml+='<span>'+iobj.value;
        //shtml+='<input type="hidden" id="hbtime1" value="'+iobj.value+'"/>';
        shtml+='</span>';
        $("#tdbtime1").html(shtml);
        $("#tdbtime1").bind("click",function(){
            btime1clk(ref,columnnum,this,$(this).children("span").html().Trim());
        });
        alert('第一学期开始时间不在当前学年，请重新设置。');
		return;
	}

    if(!validateTwoDate(btime1.Trim()+" 00:00:00",etime1.Trim()+" 00:00:00")){

        $(iobj).val($("#hbtime1").val());
        var shtml="";
        shtml+='<span>'+iobj.value;
        //shtml+='<input type="hidden" id="hbtime1" value="'+iobj.value+'"/>';
        shtml+='</span>';
        $("#tdbtime1").html(shtml);
        $("#tdbtime1").bind("click",function(){
            btime1clk(ref,columnnum,this,$(this).children("span").html().Trim());
        });
        alert("同一学期中，开始时间不能晚于结束时间，请重新设置或先修改结束时间再设置。");
        return;
    }

	var param={};
	param.ref=ref;
	param.semesterbegindatestring=iobj.value+" 00:00:00";
//	}else{
//		param.semesterenddate=iobj.value;
//	}
	$.ajax({
		url:'term?m=modify',
		data:param, 
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
			$("#hbtime1").val(iobj.value);
            var shtml="";
            shtml+='<span>'+iobj.value;
            //shtml+='<input type="hidden" id="hbtime1" value="'+iobj.value+'"/>';
            shtml+='</span>';
			$("#tdbtime1").html(shtml);
			$("#tdbtime1").bind("click",function(){
				btime1clk(ref,columnnum,this,$(this).children("span").html().Trim());
			});
		}
	});
}

//第一学期结束时间点击和提交时间
function etime1clk(ref,columnnum,tobj,text){
	var shtml = "";
	shtml+='<input type="text" id="inputetime1"   value=\''+text+'\'/>';
	
	//解除事件
	$(tobj).attr("onclick","");
	$(tobj).unbind("click");
	
	$(tobj).html(shtml);

    $("#inputetime1").bind("blur",function(){
        etime1submit(ref,columnnum,this);

    });
    $("#inputetime1").focus();
    //弹出日历控件
    WdatePicker({el:'inputetime1',dateFmt: 'yyyy-MM-dd', skin: 'whyGreen'});
    $("#inputetime1").click(function(){
        var iframe=$("iframe");
        if(iframe.length>0&&iframe.parent().css("display")!="none")
            return;
        WdatePicker({el:'inputetime1',dateFmt: 'yyyy-MM-dd', skin: 'whyGreen'});
    });
}
function etime1submit(ref,columnnum,iobj){
    var iframe=$("iframe");
    if(iframe.length>0&&iframe.parent().css("display")!="none")
        return;
    if($(iobj).val().Trim()==$("#hetime1").val().Trim()){
        $(iobj).val($("#hetime1").val());
        var shtml="";
        shtml+='<span>'+iobj.value;
        //shtml+='<input type="hidden" id="hetime1" value="'+iobj.value+'"/>';
        shtml+='</span>';
        $("#tdetime1").html(shtml);
        $("#tdetime1").bind("click",function(){
            etime1clk(ref,columnnum,this,$(this).children("span").html().Trim());
        });
        return;
    }

	var btime1 = $("#hbtime1").val();
	var etime1 = $(iobj).val();
    var btime2 = $("#hbtime2").val();
	if(!validateTwoDate(btime1.Trim()+" 00:00:00",etime1.Trim()+" 00:00:00")){
		$(iobj).val($("#hetime1").val());
        var shtml="";
        shtml+='<span>'+iobj.value;
        //shtml+='<input type="hidden" id="hetime1" value="'+iobj.value+'"/>';
        shtml+='</span>';
        $("#tdetime1").html(shtml);
        $("#tdetime1").bind("click",function(){
            etime1clk(ref,columnnum,this,$(this).children("span").html().Trim());
        });
        alert("同一学期中，结束时间不能早于开始时间，请重新设置或先修改开始时间再设置。");
		return;
	}

    if(!validateTwoDate(etime1.Trim()+" 00:00:00",btime2.Trim()+" 00:00:00")){
        $(iobj).val($("#hetime1").val());
        var shtml="";
        shtml+='<span>'+iobj.value;
        //shtml+='<input type="hidden" id="hetime1" value="'+iobj.value+'"/>';
        shtml+='</span>';
        $("#tdetime1").html(shtml);
        $("#tdetime1").bind("click",function(){
            etime1clk(ref,columnnum,this,$(this).children("span").html().Trim());
        });
        alert("第一学期结束时间不能晚于第二学期开始时间，请重新设置或先修改第二学期开始时间再设置。");
        return;
    }
	var param={};
	param.ref=ref;
	//param.semesterbegindate=iobj.value;
//	}else{
	param.semesterenddatestring=iobj.value+" 00:00:00";
//	}
	$.ajax({
		url:'term?m=modify',
		data:param, 
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
			$("#hetime1").val(iobj.value);
            var shtml="";
            shtml+='<span>'+iobj.value;
            //shtml+='<input type="hidden" id="hetime1" value="'+iobj.value+'"/>';
            shtml+='</span>';
			$("#tdetime1").html(shtml);
			$("#tdetime1").bind("click",function(){
				etime1clk(ref,columnnum,this,$(this).children("span").html().Trim());
			});
		}
	});
}

//第二学期开始时间点击和提交时间
function btime2clk(ref,columnnum,tobj,text){
	var shtml = "";
	shtml+='<input type="text" id="inputbtime2"   value=\''+text+'\'/>';
	
	//解除事件
	$(tobj).attr("onclick","");
	$(tobj).unbind("click");
	
	$(tobj).html(shtml);

    $("#inputbtime2").bind("blur",function(){
        btime2submit(ref,columnnum,this);

    });
    $("#inputbtime2").focus();
    //弹出日历控件
    WdatePicker({el:'inputbtime2',dateFmt: 'yyyy-MM-dd', skin: 'whyGreen'});
    $("#inputbtime2").click(function(){
        var iframe=$("iframe");
        if(iframe.length>0&&iframe.parent().css("display")!="none")
            return;
        WdatePicker({el:'inputbtime2',dateFmt: 'yyyy-MM-dd', skin: 'whyGreen'});
    });
}
function btime2submit(ref,columnnum,iobj){
    var iframe=$("iframe");
    if(iframe.length>0&&iframe.parent().css("display")!="none")
        return;
    if($(iobj).val().Trim()==$("#hbtime2").val().Trim()){
        $(iobj).val($("#hbtime2").val());
        var shtml="";
        shtml+='<span>'+iobj.value;
        //shtml+='<input type="hidden" id="hbtime2" value="'+iobj.value+'"/>';
        shtml+='</span>';
        $("#tdbtime2").html(shtml);
        $("#tdbtime2").bind("click",function(){
            btime2clk(ref,columnnum,this,$(this).children("span").html().Trim());
        });
        return;
    }

	var etime1 = $("#hetime1").val();
	var btime2 = $(iobj).val();
    var etime2 = $("#hetime2").val();
	if(!validateTwoDate(etime1.Trim()+" 00:00:00",btime2.Trim()+" 00:00:00")){

		$(iobj).val($("#hbtime2").val());
        var shtml="";
        shtml+='<span>'+iobj.value;
        //shtml+='<input type="hidden" id="hbtime2" value="'+iobj.value+'"/>';
        shtml+='</span>';
        $("#tdbtime2").html(shtml);
        $("#tdbtime2").bind("click",function(){
            btime2clk(ref,columnnum,this,$(this).children("span").html().Trim());
        });
        alert("第二学期开始时间不能早于第一学期结束时间，请重新设置或先修改第一学期结束时间再设置。");
		return;
	}

    if(!validateTwoDate(btime2.Trim()+" 00:00:00",etime2.Trim()+" 00:00:00")){

        $(iobj).val($("#hbtime2").val());
        var shtml="";
        shtml+='<span>'+iobj.value;
        //shtml+='<input type="hidden" id="hbtime2" value="'+iobj.value+'"/>';
        shtml+='</span>';
        $("#tdbtime2").html(shtml);
        $("#tdbtime2").bind("click",function(){
            btime2clk(ref,columnnum,this,$(this).children("span").html().Trim());
        });
        alert("同一学期中，开始时间不能晚于结束时间，请重新设置或先修改结束时间再设置。");
        return;
    }
	var param={};
	param.ref=ref;
	param.semesterbegindatestring=iobj.value+" 00:00:00";
//	}else{
	//param.semesterenddate=iobj.value;
//	}
	$.ajax({
		url:'term?m=modify',
		data:param, 
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
			$("#hbtime2").val(iobj.value);
            var shtml="";
            shtml+='<span>'+iobj.value;
            //shtml+='<input type="hidden" id="hbtime2" value="'+iobj.value+'"/>';
            shtml+='</span>';
			$("#tdbtime2").html(shtml);
			$("#tdbtime2").bind("click",function(){
				btime2clk(ref,columnnum,this,$(this).children("span").html().Trim());
			});
		}
	});
}

//第二学期结束时间点击和提交方法
function etime2clk(ref,columnnum,tobj,text){
	var shtml = "";
	shtml+='<input type="text" id="inputetime2"   value=\''+text+'\'/>';
	
	//解除事件
	$(tobj).attr("onclick","");
	$(tobj).unbind("click");
	
	$(tobj).html(shtml);

    $("#inputetime2").bind("blur",function(){
        etime2submit(ref,columnnum,this);

    });
    $("#inputetime2").focus();
    //弹出日历控件
    WdatePicker({el:'inputetime2',dateFmt: 'yyyy-MM-dd', skin: 'whyGreen'});
    $("#inputetime2").click(function(){
        var iframe=$("iframe");
        if(iframe.length>0&&iframe.parent().css("display")!="none")
            return;
        WdatePicker({el:'inputetime2',dateFmt: 'yyyy-MM-dd', skin: 'whyGreen'});
    });
}
function etime2submit(ref,columnnum,iobj){
    var iframe=$("iframe");
    if(iframe.length>0&&iframe.parent().css("display")!="none")
        return;
    if($(iobj).val().Trim()==$("#hetime2").val().Trim()){
        $(iobj).val($("#hetime2").val());
        var shtml="";
        shtml+='<span>'+iobj.value;
        //shtml+='<input type="hidden" id="hetime2" value="'+iobj.value+'"/>';
        shtml+='</span>';
        $("#tdetime2").html(shtml);
        $("#tdetime2").bind("click",function(){
            etime2clk(ref,columnnum,this,$(this).children("span").html().Trim());
        });
        return;
    }

	var btime2 = $("#hbtime2").val();
	var etime2 = $(iobj).val();
	var date = new Date();
	var year = $("#termyear").val().split("~")[0];
	if(!validateTwoDate(btime2.Trim()+" 00:00:00",etime2.Trim()+" 00:00:00")){

		$(iobj).val($("#hetime2").val());
        var shtml="";
        shtml+='<span>'+iobj.value;
        //shtml+='<input type="hidden" id="hetime2" value="'+iobj.value+'"/>';
        shtml+='</span>';
        $("#tdetime2").html(shtml);
        $("#tdetime2").bind("click",function(){
            etime2clk(ref,columnnum,this,$(this).children("span").html().Trim());
        });
        alert("同一学期中，结束时间不能早于开始时间，请重新设置或先修改开始时间再设置。");
		return;
	}
	if(etime2.split("-")[0]!=parseInt(year)+1){

		$(iobj).val($("#hetime2").val());
        var shtml="";
        shtml+='<span>'+iobj.value;
        //shtml+='<input type="hidden" id="hetime2" value="'+iobj.value+'"/>';
        shtml+='</span>';
        $("#tdetime2").html(shtml);
        $("#tdetime2").bind("click",function(){
            etime2clk(ref,columnnum,this,$(this).children("span").html().Trim());
        });
        alert("第二学期结束时间不在当前学年，请重新设置。");
		return;
	}

	var param={};
	param.ref=ref;
	//param.semesterbegindate=iobj.value;
//	}else{
	param.semesterenddatestring=iobj.value+" 00:00:00";
//	}
	$.ajax({
		url:'term?m=modify',
		data:param, 
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
			$("#hetime2").val(iobj.value);
            var shtml="";
            shtml+='<span>'+iobj.value;
            //shtml+='<input type="hidden" id="hetime2" value="'+iobj.value+'"/>';
            shtml+='</span>';
			$("#tdetime2").html(shtml);
			$("#tdetime2").bind("click",function(){
				etime2clk(ref,columnnum,this,$(this).children("span").html().Trim());
			});
		}
	});
}

//删除
function doDelGrade(termid){
	if(typeof(termid)=="undefined"||termid.length<1){
		alert('异常错误，没有获取到删除所需标识!');
		return;
	}
	if(!confirm('确认删除?')){return;}      
	$ajax('term?m=del&ref='+termid,undefined,'POST','json',function(rps){
		if(rps.type=="success")
			pageGo('p1');	
		alert(rps.msg);	
	},function(){
		alert('网络异常！');
	})
}
//修改之前
function toUpd(termid){
	$("#hidden_for_id").val(termid);
	$ajax('term?m=toupd',{ref:termid},'POST','json',function(rps){  
		if(rps.type=="error"){  
			alert(rps.msg);  
			return; 
		}
		var html = '';
		if(rps.objList.length>0){
			$("#upd_termname").val(rps.objList[0].termname);
			$("#upd_year").val(rps.objList[0].year);
			$("#upd_btime").val(rps.objList[0].btimeString);
			$("#upd_etime").val(rps.objList[0].etimeString);
			showModel('div_upd');
		} 
	},function(){
		alert("网络异常！")
	}); 
}
//修改
function doUpd(){
	var termid=$("#hidden_for_id").val();
	if(typeof(termid)=='undefined'||termid.length<1){
		alert("系统未获取到学期标识，请刷新页面重试!");
		return;
	}
	var termname=$("#upd_termname");
	if(termname.val().Trim().length<1){
		alert("请输入学期名称!");
		termname.focus();
		return;
	}
	var year=$("#upd_year");
	if(year.val().Trim().length<1){
		alert("请选择年份!");
		year.focus();
		return;
	}
	var btime=$("#upd_btime");
	if(btime.val().Trim().length<1){
		alert("请选择开始时间!");
		btime.focus(); 
		return;
	}
	var etime=$("#upd_etime");
	if(etime.val().Trim().length<1){
		alert("请选择结束时间!");
		etime.focus();
		return;
	}
	var rpcval=rpc.PageUtilTool.BeginDiffEndDate(btime.val(),etime.val());
	if(!(rpcval=="<")){ 
		alert("学期开始时间不能大于或等于结束时间!");
		return;
	}
	if(!confirm("数据验证成功，确认修改？")){
		return;
	}
	$.ajax({
		url:'term?m=modify',
		data:{
			ref : termid,
			termname : termname.val(),
			year : year.val(),
			semesterbegindatestring:btime.val(),
			semesterenddatestring:etime.val()
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
	var termname=$("#add_termname");
	if(termname.val().Trim().length<1){
		alert("请输入学期名称!");
		termname.focus();
		return;
	}
	var year=$("#add_year");
	if(year.val().Trim().length<1){
		alert("请选择年份!");
		year.focus();
		return;
	}
	var btime=$("#add_btime");
	if(btime.val().Trim().length<1){
		alert("请选择开始时间!");
		btime.focus();
		return;
	}
	var etime=$("#add_etime");
	if(etime.val().Trim().length<1){
		alert("请选择结束时间!");
		etime.focus();
		return;
	}
	var rpcval=rpc.PageUtilTool.BeginDiffEndDate(btime.val(),etime.val());
	if(!(rpcval=="<")){ 
		alert("学期开始时间不能大于或等于结束时间!");
		return;
	}
	if(!confirm("数据验证成功，确认添加？")){
		return;
	}
	$ajax('term?m=ajaxsave',
		{ 
			termname : termname.val(),
			year : year.val(),
			semesterbegindatestring:btime.val(),
			semesterenddatestring:etime.val()
		},
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

function reset(div){
	$("div[id="+div+"] input").val('');
	$("div[id="+div+"] select").val('');
}