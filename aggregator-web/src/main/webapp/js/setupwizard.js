		//关闭弹出串口
		function shutdownpop(id){
			//window.parent.document.body.removeChild(window.parent.document.getElementById("dialogCase"));
			closeModel(id);
            showAndHidden('fade','hide');
            //window.location.href="user?m=toIndex";
		}
		//添加学科
		function addli(){
			var subLiLength=$("#subject li").length;
			var n = subLiLength+1;
			var txt = '<li id="li'+n+'"><input id="add'+n+'"  name="add'+n+'" type="text" value="" /><a href="javascript:delSubject(\'li'+n+'\')"><span title="删除"></span></a></li>';
			var subLiLength=$("#subject li").length;
			if(subLiLength>0&&n%5!=0)
				txt+='<font id="li'+n+'">&nbsp;</font>';
			$("#subject").append(txt);
			$("#add"+n)[0].focus();
		}

		//删除学科
		function delSubject(id){
            if(!confirm('是否要删除学科？'))
                return;
			$("#subject li[id='"+id+"']").remove();
			$("#subject font[id='"+id+"']").remove();
		}
		//li点击事件，把li变为输入框，进行编辑
		function clk(id,name,ref){ 
			alert(id+"---"+name);
			var shtml="<input onblur='addOrUpdateSubject(this)'  type='text' value='"+name+"' id='"+ref+"'/>";
			$("#li"+id).html(shtml);
			//解除事件
			$("#li"+id).attr("onclick","");
			$("#li"+id).unbind("click");
		}
		//实现下一步动作
		function next(div1,div2){
            $("#"+div1).hide();
            $("#"+div2).show();
		}
		//弹出窗口
		 function ShowIframe()
		{
            var pop=new Popup({ contentType:1,scrollType:'yes',isReloadOnClose:false,width:500,height:400,isShowShadow:true});
            pop.setContent("contentUrl","http://localhost:8080/sz_school/sysm?m=list");
            pop.setContent("title","iframe框架示例");
            pop.build();
            pop.show();
        }
		 
		 //显示上传后的图片，也就是要切割的图片
		 function preview(img, selection) {
				if (!selection.width || !selection.height)
					return;

				var scaleX = 253 / selection.width;
				var scaleY = 64 / selection.height;

				$('#previewimg').css({
					width : Math.round(scaleX * imgwidth),
					height : Math.round(scaleY * imgheight),
					marginLeft : -Math.round(scaleX * selection.x1),
					marginTop : -Math.round(scaleY * selection.y1)
				});
               // alert('<%=basePath%>');
				$('input[name="src"]').val(
				$(img).attr("src").replace(basePath,''));
				$('input[name="x1"]').val(selection.x1);
				$('input[name="y1"]').val(selection.y1);
				$('input[name="x2"]').val(selection.x2);
				$('input[name="y2"]').val(selection.y2); 
			 
			}
		 	//提交上传图片
		 	function uploadImg(){
			   var t=document.getElementById('upload');
			   		if(t.value.Trim().length<1){
			   			alert('您尚未选择图片，请选择！');
			   			return;
			   		}
			   		var lastname=t.value.Trim().substring(t.value.Trim().lastIndexOf('.')).toLowerCase();;
			   		if(lastname!='.jpg'&&lastname!='.png'&&lastname!='.gif'&&lastname!='.bmp'){
			   			alert('您选择的图片不正确，目前只支持jpg,gif,png,bmp图片!');
			   			return;
			   		} 
			   		var filePath = $("#upload").val();
			   		$.ajaxFileUpload({
						url:"sysm?m=saveheadsrcfile", 
						fileElementId:'upload',
						dataType: 'json',
						type:'POST',
						success: function (data, status)
						{
							if(typeof(data.error) != 'undefined')
							{
								if(data.error != '')
								{
									alert(data.error);
								}else
								{
									alert(data.msg);
								}
							}else{
							//初始化组件
								//showModel('cuthear_div',false);
								darray=data.success.split("|"); 
								$("#myimage").attr("src",darray[0]);
								$("#previewimg").attr("src",darray[0]);
								imgwidth=darray[1];
								imgheight=darray[2];   
								//显示出来
								$("#current_photo").attr("src",darray[0]);
								$("#cuthear_div").show('fast');
								imgareaselectObj=$('#myimage').imgAreaSelect({
									 aspectRatio: '320:96',
									 handles: true, 
									 instance:true,
									 fadeSpeed: 200, 
									 onSelectChange: preview
								});
								 
							}
						}, 
						error: function (data, status, e)
						{
							alert(e);
						}
			   		});
			 
			   }
			   //提交切割图片
			   
			   function subheadcut(){
			   	 var src=$('input[name="src"]').val();
				 var x1=$('input[name="x1"]').val();
				 var y1=$('input[name="y1"]').val();
				 var x2=$('input[name="x2"]').val();
				 var y2=$('input[name="y2"]').val();  
			   	  
			   	  //参数是否正确
				   	if(src.length<1
				   		||x1.Trim().length<1||isNaN(x1.Trim())
				   		||x2.Trim().length<1||isNaN(x2.Trim())
				   		||y1.Trim().length<1||isNaN(y1.Trim())
				   		||y2.Trim().length<1||isNaN(y2.Trim())
				   	){
                        if(!confirm('请选择截图区域，默认将按全图比例缩放！'))
                            return;
				   		
				   	}
				   	if(src.length<1){
				   		src=$("#previewimg").attr("src").replace(basePath,'');
				   	}
				  $.ajax({
				 	url:"sysm?m=docuthead",
					type:"post",			
					dataType:'json',
					cache: false,
					data:{src:src,x1:x1,y1:y1,x2:x2,y2:y2},
					error:function(){
						alert('系统未响应，请稍候重试!');
					},
					success:function(json){
					    if(json.type == "error"){
					    	alert(json.msg);
					    }else{
					    	alert("设置成功");
					   	 	closeModel('cuthear_div');
					   	 	$("#current_photo").attr("src",json.objList[0]);
					    	imgareaselectObj.setOptions({remove:true,hide:true});
					    } 
					}
				});  
			  }
//密码设置提交方法			   
function submitUpdatePwd(div1,div2){
	var pass = $("#newPass").val();
	if(pass.Trim().length<1){
		$("#passDiv1").html("请输入密码");
		$("#passDiv1").show();
		return;
	}else{
		$("#passDiv1").hide();
	}
	if(pass.Trim().length<6){
		$("#passDiv1").show();
		return;
	}else{
		$("#passDiv1").hide();
	}
	
	var pass2 = $("#morePass").val();
	if(pass2.Trim().length<1){
		$("#passDiv2").html("请再次输入密码");
		$("#passDiv2").show();
		return;
	}
	if(pass!=pass2){
		$("#passDiv2").show();
		return;
	}
	
	$.ajax({
		url:'init?m=changepassword',//cls!??.action
		dataType:'json',
		type:'POST',
		data:{new_password:pass
	    },
		cache: false,
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			next(div1,div2);
		}
	});
}

//学期设置提交方法
function submitUpdateTerm(div1,div2){
	var btime1 = $("#beginTime1").val();
	var etime1 = $("#endTime1").val();
	var btime2 = $("#beginTime2").val();
	var etime2 = $("#endTime2").val();	
	var ref1 = $("#termref1").val();
	var ref2 = $("#termref2").val();
	var date = new Date();
	var year = $("#termyear").val().split("~")[0];
	if(btime1.split("-")[0]!=parseInt(year)){
		$("#termDiv1").html("第一学期开始时间不在当前学年");
		$("#termDiv1").show();
		return;
	}else{
		$("#termDiv1").hide();
	}
	if(!validateTwoDate(btime1.Trim()+" 00:00:00",etime1.Trim()+" 00:00:00")){
		$("#termDiv1").html("结束时间不能早于开始时间，请重新设置");
		$("#termDiv1").show();
		return;
	}else{
		$("#termDiv1").hide();
	}
	if(!validateTwoDate(etime1.Trim()+" 00:00:00",btime2.Trim()+" 00:00:00")){
		$("#termDiv2").html("第二学期开始时间不能早于第一学期结束时间，请重新设置");
		$("#termDiv2").show();
		return;
	}else{
		$("#termDiv2").hide();
	}
	if(!validateTwoDate(btime2.Trim()+" 00:00:00",etime2.Trim()+" 00:00:00")){
		$("#termDiv2").html("结束时间不能早于开始时间，请重新设置");
		$("#termDiv2").show();
		return;
	}else{
		$("#termDiv2").hide();
	}
	if(etime2.split("-")[0]!=parseInt(year)+1){
		$("#termDiv2").html("第二学期结束时间不在当前学年");
		$("#termDiv2").show();
		return;
	}else{
		$("#termDiv2").hide();
	}
	$.ajax({
		url:'init?m=setupmodify',//cls!??.action
		dataType:'json',
		type:'POST',
		data:{ref1:ref1,
			  ref2:ref2,
			  btime1:btime1,
			  btime2:btime2,
			  etime1:etime1,
			  etime2:etime2
	    },
		cache: false,
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			next(div1,div2);
		}
	});
}

//学科设置提交方法
function submitSubject(div1,div2){
	var subjectnames = $("input").filter(function(){return this.id.indexOf('add')!=-1});
	var subjectname="";
	var b = false;
	if(subjectnames.length>0){
		$.each(subjectnames,function(idx,itm){
            if(itm.value.Trim()=="语文"||itm.value.Trim()=="数学"||itm.value.Trim()=="英语"||itm.value.Trim()=="政治"+
                itm.value.Trim()=="地理"||itm.value.Trim()=="历史"||itm.value.Trim()=="化学"||itm.value.Trim()=="物理"||itm.value.Trim()=="生物"){
                alert("您输入的学科与固定学科重复，请重新填写！");
                itm.focus();
                return;
            }
            if(!b){
                if(itm.value.Trim().length>0&&itm.value.Trim().length<7){
                    subjectname+=itm.value.Trim()+"-";
                }else{
                    if(itm.value.Trim().length<0){
                        alert("学科名称不能为空，请填写");
                        itm.focus();
                        b=true;
                    }
                    if(itm.value.Trim().length>6){
                        alert("学科名称长度超出限制");
                        itm.focus();
                        b=true;
                    }
                }
            }
        });
    }
        if(b)
		return;
	$.ajax({
		url:'init?m=setupadd',//cls!??.action
		dataType:'json',
		type:'POST',
		data:{subjectname:subjectname
	    },
		cache: false,
		error:function(){
			alert('异常错误!系统未响应!');
		},success:function(rps){
			next(div1,div2);
		}
	});
}
//学科设置修改
function updSubject(ref,inputObj){
    var name = inputObj.value;
	  $.ajax({ 
		url:'subject?m=update', 
		data:{ 
			subjectid : ref,
			subjectname : name 
		}, 
		type:'post'
	});
}
function validatePass1(obj){
	var pass = obj.value;
	if(pass.Trim().length<1){
		$("#passDiv1").html("请输入密码");
		$("#passDiv1").show();
		return;
	}else{
		$("#passDiv1").hide();
	}
	if(pass.Trim().length<6){
		$("#passDiv1").html("请输入大于等于6个字符的密码长度");
		$("#passDiv1").show();
		return;
	}else{
		$("#passDiv1").hide();
	}
}

function validatePass2(obj){
	var pass = $("#newPass").val();
	var pass2 = obj.value;
	if(pass2.Trim().length<1){
		$("#passDiv2").html("请再次输入密码");
		$("#passDiv2").show();
		return;
	}else{
		$("#passDiv2").hide();
	}
	if(pass!=pass2){
		$("#passDiv2").html("两次输入密码不一致，请重新输入");
		$("#passDiv2").show();
		return;
	}else{
		$("#passDiv2").hide();
	}
}

function validateBtime1(){
	var iframe=$("iframe");
	if(iframe.length>0&&iframe.parent().css("display")!="none")
		return;
	var btime1 = $("#beginTime1").val();
	var date = new Date();
	var year =  $("#termyear").val().split("~")[0];
	if(btime1.split("-")[0]!=parseInt(year)){
		$("#termDiv1").html("第一学期开始时间不在当前学年");
		$("#termDiv1").show();
		return;
	}else{
		$("#termDiv1").hide();
	}
}

function validateEtime1(){
	var iframe=$("iframe");
	if(iframe.length>0&&iframe.parent().css("display")!="none")
		return;
	var btime1 = $("#beginTime1").val();
	var etime1 = $("#endTime1").val();
	if(!validateTwoDate(btime1.Trim()+" 00:00:00",etime1.Trim()+" 00:00:00")){
		$("#termDiv1").html("结束时间不能早于开始时间，请重新设置");
		$("#termDiv1").show();
		return;
	}else{
		$("#termDiv1").hide();
	}
}

function validateBtime2(){
	var iframe=$("iframe");
	if(iframe.length>0&&iframe.parent().css("display")!="none")
		return;
	var etime1 = $("#endTime1").val();
	var btime2 = $("#beginTime2").val();	
	if(!validateTwoDate(etime1.Trim()+" 00:00:00",btime2.Trim()+" 00:00:00")){
		$("#termDiv2").html("第二学期开始时间不能早于第一学期结束时间，请重新设置");
		$("#termDiv2").show();
		return;
	}else{
		$("#termDiv2").hide();
	}
}

function validateEtime2(){
	var iframe=$("iframe");
	if(iframe.length>0&&iframe.parent().css("display")!="none")
		return;
	var btime2 = $("#beginTime2").val();
	var etime2 = $("#endTime2").val();
	var date = new Date();
	var year =  $("#termyear").val().split("~")[0];
	if(!validateTwoDate(btime2.Trim()+" 00:00:00",etime2.Trim()+" 00:00:00")){
		$("#termDiv2").html("结束时间不能早于开始时间，请重新设置");
		$("#termDiv2").show();
		return;
	}else{
		$("#termDiv2").hide();
	}
	if(etime2.split("-")[0]!=parseInt(year)+1){
		$("#termDiv2").html("第二学期结束时间不在当前学年");
		$("#termDiv2").show();
		return;
	}else{
		$("#termDiv2").hide();
	}
}

        function showSetupWizard(showId){
// $("body").css("overflow","hidden");
            $("#fade").css("height",window.screen.availHeight+parseFloat(getScrollTop()));
            $("#fade").css("width",window.screen.availWidth);
            try{
                $("#"+showId).css("z-index",1005);
                $("#"+showId).css("position","absolute");
                $("#"+showId).css("top",window.screen.availHeight/2-parseFloat($("#"+showId).css("height"))/2);
                $("#"+showId).css("left",window.screen.availWidth/2-parseFloat($("#"+showId).css("width"))/2);

                // $(".white_content").css("top",15);
            }catch(e){}


            showAndHidden(showId,'show');
            showAndHidden('fade','show');


        }


        /*********************设置用户名************************/


        /**
         * 设置用户名、邮箱
         * @param objName
         * @param div1
         * @param div2
         */
        function setUserName(objName,div1,div2){
            var objVal=$("#"+objName).val().Trim();
            if(objVal.length<1){
                $("#"+objName+"_err").html('请输入'+(objName=='username'?"用户名":"邮箱")+'!');
                return;
            }else
                $("#"+objName+"_err").html('');

            var param={};
            if(objName=='username'){
                if(objVal.indexOf('@')!=-1){
                    $("#"+objName+"_err").html('用户名不可包含@符号!');
                    return;
                }
                param.username=objVal;
            }

            if(objName=='email'){
                if(objVal.indexOf('@')==-1){
                    $("#"+objName+"_err").html('邮箱必须包含@符号!');
                    return;
                }
                param.mailaddress=objVal;
            }

            $.ajax({
                url:'user?m=updateUserInfo',
                dataType:'json',
                type:'POST',
                data:param,
                cache: false,
                error:function(){
                    alert('异常错误!系统未响应!');
                },success:function(rps){
                    if(rps.type=="success")
                        next(div1,div2);
                    else
                        $("#"+objName+"_err").html((objName=='username'?"用户名":"邮箱")+rps.msg);
                }
            });
        }


        /**
         * 验证用户名
         * @param txtid
         * @return
         */
        function validateUsername(objName){
            var objVal=$("#"+objName).val().Trim();
            if(objVal.length<1){
                $("#"+objName+"_err").html('请输入'+(objName=='username'?"用户名":"邮箱")+'后再检测!');
                return;
            }else
                $("#"+objName+"_err").html('');

            var param={};
            if(objName=='username'){
                if(objVal.indexOf('@')!=-1){
                    $("#"+objName+"_err").html('用户名不可包含@符号!');
                    return;
                }
                param.username=objVal;
            }

            if(objName=='email'){
                if(objVal.indexOf('@')==-1){
                    $("#"+objName+"_err").html('邮箱必须包含@符号!');
                    return;
                }
                param.mailaddress=objVal;
            }


            $ajax("user?m=validateUserInfo",param,'POST','json',
                function(rps){
                    if(rps.objList[0]!=null&&rps.objList[0]=='err'){
                        $("#"+objName).focus();
                        $("#"+objName).select();
                        if(rps.objList[1]!=null&&rps.objList[1].length>0){
                            var unamestr='该用户名已被使用，您还可用：';
                            unamestr+=rps.objList[1].join(",");
                            $("#username_err").html(unamestr);
                        }else{
                            $("#"+objName+"_err").html((objName=='username'?"用户名":"邮箱")+rps.msg);
                        }
                    }else{
                        $("#"+objName+"_err").html((objName=='username'?"用户名":"邮箱")+rps.msg);
                    }

                },function(){alert('网络异常!');});
        }