	    /**
	     * 翻页
	     * @param rps
	     * @return
	     */
		function columnsPageReturn(rps){
				if(rps.type=="error"){
					alert(rps.msg);return;
				}else{ 
					var htm='';
					$.each(rps.objList,function(idx,itm){
						htm+='<tr><td><a href="../activetheme/toThemeAdmin?columnsid='+itm.columnsid+'&teachercourseid='+courseteacher+'">';
						htm+=itm.columnsname+'</a></td>';
						htm+='<td>'+itm.classInfo.classgrade+itm.classname+'</td>';
						htm+='<td>'+itm.cuserinfo.realname+'</td>';
						htm+='<td>'+itm.ctimeShortString+'</td>';
						var stateStr=itm.state==1?'显示':'隐藏';
						htm+='<td>'+stateStr+'</td>';
						htm+='<td>';
						htm+='<a href="javascript:changeOrderIdx(\''+itm.columnsid+'\',1)"><img src="../images/an22_130423.png" width="9" height="10" /></a>&nbsp;';
						htm+='<a class="font-lightblue"  href="javascript:changeOrderIdx(\''+itm.columnsid+'\',2)"><img src="../images/an23_130423.png" width="9" height="10" /></a>&nbsp;';
						htm+='<a class="font-lightblue"  href="javascript:deleteColumns(\''+itm.columnsid+'\')">删除</a>&nbsp;';
						htm+='<a class="font-lightblue"  href="javascript:toFillUpdate(\''+itm.columnsid+'\')">编辑</a>&nbsp;';
						if(itm.state==1)
							htm+='<a class="font-lightblue" href="javascript:updateState(\''+itm.columnsid+'\',2)">隐藏</a>';
						else
							htm+='<a class="font-lightblue" href="javascript:updateState(\''+itm.columnsid+'\',1)">显示</a>';
						htm+='</td></tr>';
					})
					$("#tbl_body_data").html(htm);
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
			*修改显示顺序
			*/
			function changeOrderIdx(id,dict){
				if(typeof(courseteacher)=="undefined"||courseteacher==null){
					alert('异常错误，参数异常!原因：courseteacher is empty!');return;
				}
				if(typeof(id)=="undefined"||!isNaN(id)){
					alert('异常错误，参数异常!原因：id is empty!');return;
				}
				if(typeof(dict)=="undefined"||isNaN(dict)){
					alert('异常错误，参数异常!原因：dict is empty!');return;
				}
				$.ajax({
					url:"doChangeOrderIdx",
					dataType:'json',
					type:"post", 
					cache: false, 
					data:{columnsid:id,courseteacher:courseteacher,dict:dict},
					error:function(){
						alert('异常错误!系统未响应!');
					},success:function(rps){
						if(rps.type=="error"){
							alert(rps.msg);
						}else 
							pageGo('p1');
					}
				});		
			}
			
			/**
			*删除栏目(但不删除子版块)
			*/
			function deleteColumns(id){
				if(typeof(id)=="undefined"||!isNaN(id)){
					alert('异常错误，参数异常!原因：id is empty!');return;
				}				
				if(!confirm('您确认删除该栏目吗?'))
					return;					
				$.ajax({
					url:"doDeleteColumns",
					dataType:'json',
					type:"post", 
					cache: false, 
					data:{columnsid:id},
					error:function(){
						alert('异常错误!系统未响应!');
					},success:function(rps){
						if(rps.type=="error"){
							alert(rps.msg);
						}else
							pageGo('p1');
					}
				});		
			}			
			
			/**
			*修改状态  stateid: 1:开启   2:关闭
			*/
			function updateState(id,stateid){
				if(typeof(id)=="undefined"||!isNaN(id)){
					alert('异常错误，参数异常!原因：id is empty!');return;
				}
				if(typeof(stateid)=="undefined"||isNaN(stateid)){
					alert('异常错误，参数异常!原因：stateid is empty!');return;
				}				
				$.ajax({
					url:"doUpdateColumns",
					dataType:'json',
					type:"post", 
					cache: false, 
					data:{columnsid:id,state:stateid},
					error:function(){
						alert('异常错误!系统未响应!');
					},success:function(rps){
						if(rps.type=="error"){
							alert(rps.msg);
						}else
							pageGo('p1');
					}
				});				
			}	
			
			/**
			*组织参数
			*/
			function validateParam(tobj){
				var param=new Object();
				param.courseteacher=courseteacher;			
//				var columnsnameObj=$("#sel_columnsname");
//				if(columnsnameObj.val().Trim().length>0)
//					param.columnsname=columnsnameObj.val().Trim();
				tobj.setPostParams(param);	
			}			
			/**
			*添加栏目
			*/
			function addColumns(){
				var columnsName=$("#a_columnsname");
				if(columnsName.val().Trim().length<1){
					alert('您尚未填写栏目名称，请输入!');
					columnsName.focus();
					return;
				}
				var clsidObj=$("input[name='a_ck_cls']:checked");
				var clsid='';
				if(clsidObj.length<1){
					alert('您尚未选择班级，请选择!');return;
				}
				$.each(clsidObj,function(idx,itm){
					clsid=(clsid.Trim().length>0?(clsid+','+itm.value.Trim()):itm.value.Trim());
				});
				if(clsid.Trim().length<1){
					alert('班级验证失败，请刷新页面后重试!');return;
				}
				
				var state=$("input[name='a_rdo_type']:checked").val();
				var param={courseteacher:courseteacher};
				param.columnsname=columnsName.val().Trim();
				param.classid=clsid.Trim();
				param.state=state.Trim();
				param.year=year;
				
				if(!confirm("您确认添加栏目吗?"))
					return;
				$.ajax({
					url:"doSaveColumns",
					dataType:'json',
					type:"post", 
					cache: false,
					data:param,
					error:function(){
						alert('异常错误!系统未响应!');
					},success:function(rps){
						if(rps.type=="error"){
							alert(rps.msg);
						}else
							pageGo('p1',1);
							closeModel('add_columns');
					}
			});
			}
			
			function toFillUpdate(cid){
				if(typeof(cid)=="undefined"||!isNaN(cid)){
					alert('异常错误，参数异常!原因：cid is empty!');return;
				}				
				$.ajax({
					url:'getColumnsBy',
					type:"POST",
					cache:false,
					dataType:"json",
					data:{columnsid:cid},
					error:function(){
						alert('异常错误!系统未响应!');					
					},success:function(rps){
						if(rps.type=="error"){
							alert(rps.msg);
						}else{
							var h='';
							$("input[name='a_ck_cls']").each(function(ix,im){
								h='<input type="radio" name="a_rdo_cls" id="a_rdo_cls" value="'+im.value+'"/>';
								$(im).after(h);
								if($("input[name='a_ck_cls']").length-1==ix)
									$("input[name='a_ck_cls']").remove();
							});
							$("input[name='a_ck_cls']").remove();
							
							
							$.each(rps.objList,function(idx,itm){
								$("#u_columnsid").val(itm.columnsid);
								$("#a_columnsname").val(itm.columnsname);
								$("input[name='a_rdo_cls']:[value='"+itm.classid+"']").attr("checked",true);
								$("input[name='a_rdo_type']:[value='"+itm.state+"']").attr("checked",true);
							});
							if($("#u_columnsid").val().Trim().length>0){
								$("span[id='op_type']").html('修改栏目');
								$("#doadd").show();
							}
						}
					}
					
				});
			}
			function changeAddOperateType(){
				//改成添加
					$("span[id='op_type']").html('添加栏目');
					$("#u_columnsid").val('');
					$("#a_columnsname").val('');
					//$("input[name='a_rdo_cls']:first").attr("checked",true);
					var h='';
					$("input[name='a_rdo_cls']").each(function(ix,im){
						h='<input type="checkbox" name="a_ck_cls" id="a_ck_cls" value="'+im.value+'"/>';
						$(this).after(h);
						if($("input[name='a_rdo_cls']").length-1==ix)
							$("input[name='a_rdo_cls']").remove();
					});
					
					$("input[name='a_rdo_type']:first").attr("checked",true);				
					$("#doadd").hide();
			}
			/**
			 * 验证提交后确定执行的方法
			 * @return
			 */
			function operateColumns(){
				var colsid=$("#u_columnsid").val();
				if(colsid.Trim().length>0){
					updateColumns();
				}else{
					addColumns();
				}
			}
			
			/**
			执行修改
			*/
			function updateColumns(){
				var columnsName=$("#a_columnsname");
				if(columnsName.val().Trim().length<1){
					alert('您尚未填写栏目名称，请输入!');
					columnsName.focus();
					return;
				}
				var columnsid=$("#u_columnsid").val();
				if(columnsid.Trim().length<1){
					alert('异常错误，参数异常!原因：columnsid  is empty!');
					return;
				}
				var clsid=$("input[name='a_rdo_cls']:checked").val();
				var state=$("input[name='a_rdo_type']:checked").val();
				var param={columnsid:columnsid};
				param.columnsname=columnsName.val().Trim();
				param.classid=clsid.Trim();
				param.state=state.Trim(); 
				
				if(!confirm("您确认修改此栏目吗?"))
					return;
				$.ajax({
					url:"doUpdateColumns",
					dataType:'json',
					type:"post", 
					cache: false,
					data:param,
					error:function(){
						alert('异常错误!系统未响应!');
					},success:function(rps){
						if(rps.type=="error"){
							alert(rps.msg); 
						}else{					
							pageGo('p1'); 		
							alert(rps.msg);							
						}
					}
			})			
		}