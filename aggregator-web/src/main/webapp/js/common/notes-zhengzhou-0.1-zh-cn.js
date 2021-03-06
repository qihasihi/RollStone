$.fn.extend({	
		removeNotes:function(){
            $("#"+this.attr("id")).xheditor(false)
			$("#"+this.attr("id")).prev("ul[id='notes_control_tools']").remove();
		}, 
		notes:function(tools,xheditorobj){
			
				//所有标签存储
				this.alltools={
				   insert:'insert', 
				   del:'del',
				   update:'update',
				   note:'note',
				   errsen:'errsen',
				   goodsen:'goodsen',
				   errorword:'errorword',
				   misnomer:'misnomer',
				   critique:'critique',
				   cannel:'cannel'				
				};
				//样式存储
				this.toolsClass={
							insert:'notes_insert', 
							del:'notes_del',
							update:'notes_update',
							note:'notes_note',
							errsen:'notes_errsen', 
							goodsen:'notes_goodsen',
							errorword:'notes_errorword',
							insert:'notes_insert',
							misnomer:'notes_misnomer',
							critique:'notes_critique',
							cannel:'notes_cannel' 
						};
				this.simple='insert,del,update,note,errsen,goodsen,errorword';
				this.mfull='insert,del,update,note,errsen,goodsen,errorword,misnomer,critique,cannel';
				
	
			//交换数据
			if(tools){ 
				if(tools=="simple")
					this._tools=this.simple;
				else if(tools=="mfull")
					this._tools=this.mfull;
				else
					this._tools=tools;
			}
			if(xheditorobj)
				this._xheditorobj=xheditorobj;  		
			
			//开始生成
			this.gender(this._tools);
			this.bindClick(this._tools,this._xheditorobj,this.toolsClass);
				
		},gender:function(_tools){ 
			if(_tools){
				var toolsconfig=_tools.split(',');
				if(toolsconfig.length>0){
					var htm='';
					for(i=0;i<toolsconfig.length;i++){
						var txt='';
						switch(toolsconfig[i]){
							case 'insert':
								txt='插入';
								break;
							case 'del':
								txt='删除';
								break;
							case 'update':
								txt='修改';
								break;
							case 'note':
								txt='注释';
								break;
							case 'errsen':
								txt='病句';
								break;
							case 'goodsen':
								txt='好句';
								break;
							case 'errorword':
								txt='错别字';
								break;
							case 'misnomer':
								txt='用词不当';
								break;
							case 'critique':
								txt='点评';
								break;
							case 'cannel':
								txt='取消';
								break;
						}
						if(txt.length>0){							
							htm+='<li><a href="javascript:;" name="'+toolsconfig[i]+'">'+txt+'</a></li>';
						} 
					}
					if(htm.length>0)
						$(this).before("<ul id='notes_control_tools'>"+htm+"</ul>");
				}
			}
		},bindClick:function(_tools,_xheditorobj,_toolsClass){
			if(_tools){
				var toolsconfig=_tools.split(',');
				if(toolsconfig.length>0){
					var htm='';
					var xheditorV=_xheditorobj;
					var toolsClass=_toolsClass;
					var textarea=this;
					for(i=0;i<toolsconfig.length;i++){	
						var name=toolsconfig[i];
						this.prev("ul[id='notes_control_tools']").children("li").children("a[name='"+name+"']").bind("click",function(){
									var buttonName=this.name;
									var id='notes_tag_'+textarea.attr("id");
									var str=xheditorV.getSelect(); 
									if(str.indexOf('id="'+id+'"')>-1&&buttonName!='cannel'){
										alert('您选中的文字已经存在某种批注样式，请先清理后再添加!');
										return;
									} 	

									 var mousePo=mousePostion;
									//清空层
									 $("#notes_insert_div").remove();
									 $("#notes_critique_div").remove();
									 $("#notes_update_div").remove(); 
									 $("#notes_note_div").remove(); 									
										switch(buttonName){  
											case 'insert':  
												var h='<div id="notes_'+buttonName+'_div" class="xheDialog" ';
													h+='style="background-color:white;position:absolute;left:'+mousePo.x+'px;top:'+mousePo.y+'px;z-index:1006;';
													h+=';border:1px solid black; ">';
													h+='<div>输入您要插入的内容到方框里，按 确定</div>'
													h+='<div>';
													h+='<textarea style="width:300px;height:100px;" spellcheck="false" wrap="soft" id="xhePastetextValue"></textarea>';
													h+='</div>';
													h+='<div style="text-align:right;"><input type="button" value="确定" id="xheSave">&nbsp;';
													h+='<input type="button" value="取消" id="xheCancel">';
													h+='</div></div>';
													$("body").append(h);
													//防止特殊字符										
													$("#notes_"+buttonName+"_div textarea").bind("keyup",function(){ 
														var len=this.value.length;  		
														for(var i=0;i<=len;i++){ 
															this.value=this.value.replace(/[\|\<\>\'\＇\"\[\]\\$\^\&\|]+$/,"");
														}		
													});	
													var tmpid=id; 
													var clsName=eval("(toolsClass."+buttonName+")");//ClassName
													$("#notes_"+buttonName+"_div input[type='button']:first").click(function(){
														var txt=$("#notes_"+buttonName+"_div textarea").val().Trim();
														if(txt.length>0){
															var h='<span id="'+id+'_'+buttonName+'" class="'+clsName+'">';
																h+='('+txt+')';
																h+='</span>'; 
																xheditorV.pasteHTML(h);
															$("#notes_"+buttonName+"_div").remove(); 
														}
													});
													$("#notes_"+buttonName+"_div input[type='button']:last").click(function(){
														$("#notes_"+buttonName+"_div").hide('fast');  
														setTimeout($("#notes_"+buttonName+"_div").remove(),1000);
													});
												break; 
											case 'del':									 
												str='<span id="'+id+'" class="'+eval("(toolsClass."+buttonName+")")+'">'+str;
												str+='</span>';
												xheditorV.pasteHTML(str); 
												break;
											case 'update':	//显示的时候，打开层
													var h='<div id="notes_'+buttonName+'_div" class="xheDialog" ';
														h+='style="background-color:white;position:absolute;left:'+mousePo.x+'px;top:'+mousePo.y+'px;z-index:1006;';
														h+=';border:1px solid black; ">';
														h+='<div>输入您要修改的内容到方框里，按 确定</div>'
														h+='<div>';
														h+='<textarea style="width:300px;height:100px;" spellcheck="false" wrap="soft" id="xhePastetextValue"></textarea>';
														h+='</div>';
														h+='<div style="text-align:right;"><input type="button" value="确定" id="xheSave">&nbsp;';
														h+='<input type="button" value="取消" id="xheCancel">';
														h+='</div></div>';
														$("body").append(h);
														//防止特殊字符										
														$("#notes_"+buttonName+"_div textarea").bind("keyup",function(){ 
															var len=this.value.length;  		
															for(var i=0;i<=len;i++){ 
																this.value=this.value.replace(/[\|\<\>\'\＇\"\[\]\\$\^\&\|]+$/,"");
															}		
														});	
														var tmpid=id; 
														var clsName=eval("(toolsClass."+buttonName+")");//ClassName
														$("#notes_"+buttonName+"_div input[type='button']:first").click(function(){
															var txt=$("#notes_"+buttonName+"_div textarea").val().Trim();
															if(txt.length>0){
																var h='<span id="'+id+'_'+buttonName+'" class="'+clsName+'">';
																	h+='<del id="'+id+'">'+str+'</del>';
																	h+='<span id="'+id+'">';
																	h+='('+txt+')';
																	h+='</span></span>';
																	xheditorV.pasteHTML(h);
																$("#notes_"+buttonName+"_div").remove(); 
															}
														});
														$("#notes_"+buttonName+"_div input[type='button']:last").click(function(){
															$("#notes_"+buttonName+"_div").hide('fast');  
															setTimeout($("#notes_"+buttonName+"_div").remove(),1000);
														});
												break;  
											case 'note': //有问题
												var h='<div id="notes_'+buttonName+'_div" class="xheDialog" ';
												h+='style="background-color:white;position:absolute;left:'+mousePo.x+'px;top:'+mousePo.y+'px;z-index:1006;';
												h+=';border:1px solid black; ">';
												h+='<div>输入您要添加的注释到方框里，按 确定</div>'
												h+='<div>';
												h+='<textarea style="width:300px;height:100px;" spellcheck="false" wrap="soft" id="xhePastetextValue"></textarea>';
												h+='</div>';
												h+='<div style="text-align:right;"><input type="button" value="确定" id="xheSave">&nbsp;';
												h+='<input type="button" value="取消" id="xheCancel">';
												h+='</div></div>';
												$("body").append(h);
												//防止特殊字符										
												$("#notes_"+buttonName+"_div textarea").bind("keyup",function(){ 
													var len=this.value.length;  		
													for(var i=0;i<=len;i++){ 
														this.value=this.value.replace(/[\|\<\>\'\＇\"\[\]\\$\^\&\|]+$/,"");
													}		
												});
												var tmpid=id; 
												var clsName=eval("(toolsClass."+buttonName+")");//ClassName
												$("#notes_"+buttonName+"_div input[type='button']:first").click(function(){
													var txt=$("#notes_"+buttonName+"_div textarea").val().Trim();
													if(txt.length>0){
														var h='<span id="'+id+'" class="'+clsName+'">';														
															h+='<a id="'+id+'" alt="'+txt+'" title="'+txt+'" href="javascript:;">'+str+'</span>';															
															h+='</span>';
															xheditorV.pasteHTML(h);														
														$("#notes_"+buttonName+"_div").remove(); 
													}
												});
												$("#notes_"+buttonName+"_div input[type='button']:last").click(function(){
													$("#notes_"+buttonName+"_div").hide('fast');  
													setTimeout($("#notes_"+buttonName+"_div").remove(),1000);
												});												
												 
												break;
											case 'errsen':	//病句
												var tmpStr='<span id="'+id+'" class="'+eval("(toolsClass."+buttonName+")")+'">';
													tmpStr+='<u id="'+id+'">';
													tmpStr+='<span id="'+id+'">'+str+'</span>';
													tmpStr+='</u><span id="'+id+'">(病句)</span>';
													tmpStr+='</span>';
												xheditorV.pasteHTML(tmpStr); 
												break;
											case 'goodsen':	//好句
												var tmpStr='<span id="'+id+'" class="'+eval("(toolsClass."+buttonName+")")+'">';
													tmpStr+='<u id="'+id+'">';
													tmpStr+='<span id="'+id+'">'+str+'</span>';
													tmpStr+='</u><span id="'+id+'">(好句)</span>';
													tmpStr+='</span>';
												xheditorV.pasteHTML(tmpStr); 
												break;
											case 'errorword':	//错别字 
												var tmpStr='<span id="'+id+'" class="'+eval("(toolsClass."+buttonName+")")+'">';
													tmpStr+='<u id="'+id+'">';
													tmpStr+='<span id="'+id+'">'+str+'</span>';
													tmpStr+='</u><span id="'+id+'">(错别字)</span>';
													tmpStr+='</span>'; 
												xheditorV.pasteHTML(tmpStr);  
												break;  
											case 'misnomer':	//用词不当
												var tmpStr='<span id="'+id+'" class="'+eval("(toolsClass."+buttonName+")")+'">';
												tmpStr+='<span id="'+id+'">';
												tmpStr+='<span id="'+id+'">'+str+'</span>';
												tmpStr+='</span><span id="'+id+'">(用词不当)</span>';
												tmpStr+='</span>'; 
												xheditorV.pasteHTML(tmpStr);
												break;
											case 'critique':
												var h='<div id="notes_'+buttonName+'_div" class="xheDialog" ';
												h+='style="background-color:white;position:absolute;left:'+mousePo.x+'px;top:'+mousePo.y+'px;z-index:1006;';
												h+=';border:1px solid black; ">';
												h+='<div>输入您要添加的点评到方框里，按 确定</div>'
												h+='<div>';
												h+='<textarea style="width:300px;height:100px;" spellcheck="false" wrap="soft" id="xhePastetextValue"></textarea>';
												h+='</div>';
												h+='<div style="text-align:right;"><input type="button" value="确定" id="xheSave">&nbsp;';
												h+='<input type="button" value="取消" id="xheCancel">';
												h+='</div></div>';
												$("body").append(h);
												//防止特殊字符										
												$("#notes_"+buttonName+"_div textarea").bind("keyup",function(){ 
													var len=this.value.length;  		
													for(var i=0;i<=len;i++){ 
														this.value=this.value.replace(/[\|\<\>\'\＇\"\[\]\\$\^\&\|]+$/,"");
													}		
												});	
												var tmpid=id; 
												var clsName=eval("(toolsClass."+buttonName+")");//ClassName
												$("#notes_"+buttonName+"_div input[type='button']:first").click(function(){
													var txt=$("#notes_"+buttonName+"_div textarea").val().Trim();
													if(txt.length>0){
														var h='<span id="'+id+'_'+buttonName+'" class="'+clsName+'">';
															h+='<u><span>'+str+'</span></u>'; 
															h+='<span id="'+id+'">';
															h+='(点评：'+txt+')';
															h+='</span></span>';															 
															xheditorV.pasteHTML(h);														
														$("#notes_"+buttonName+"_div").remove(); 
													}
												});
												$("#notes_"+buttonName+"_div input[type='button']:last").click(function(){
													$("#notes_"+buttonName+"_div").hide('fast');  
													setTimeout($("#notes_"+buttonName+"_div").remove(),1000);
												});					
												break;
											case 'cannel':
												if(str.indexOf(id)>0){ 
													var isflag=false;
													if(str.indexOf('<span')>=0||str.indexOf('<u')>=0||str.indexOf('<strike')>=0
															||str.indexOf("<a")>=0||str.indexOf('<del')>=0)

                                                        if($("#dv_tmp_pizhu").length>0)
                                                            $("#dv_tmp_pizhu").remove();
                                                        if($("#dv_all_pz").length>0)
                                                            $("#dv_all_pz").remove();

                                                        var obj=$("<div style='display:none' id='dv_tmp_pizhu'>"+str+"</div><div  style='display:none' id='dv_all_pz'></div>");
                                                        $("body").append(obj);
                                                        //添加去除
                                                        $("#dv_tmp_pizhu .notes_insert").remove();
                                                        $("#dv_all_pz").html($("#dv_tmp_pizhu").html());
                                                        //删除去除
//                                                        $("#dv_tmp_pizhu .notes_del").each(function(ix,im){
//                                                            $("#dv_tmp_pizhu").html($("#dv_tmp_pizhu").html().replace($(im).attr("outerHTML"),$(this).html()));
//                                                        })
                                                        while($("#dv_all_pz .notes_del").length>0){
                                                            $("#dv_tmp_pizhu").html($("#dv_tmp_pizhu").html()
                                                                .replace($("#dv_tmp_pizhu .notes_del").eq(0).attr("outerHTML")
                                                                    ,$("#dv_tmp_pizhu .notes_del").eq(0).html()));
                                                            if($("#dv_tmp_pizhu .notes_del").length<1){
                                                                $("#dv_all_pz").html($("#dv_tmp_pizhu").html());
                                                                break;
                                                            }
                                                        }
//                                                        $("#dv_all_pz").html($("#dv_tmp_pizhu").val());
                                                        //修改
                                                        while($("#dv_all_pz .notes_update").length>0){
                                                            var h='';
                                                            if($("#dv_tmp_pizhu .notes_update").eq(0).find("strike").length>0)
                                                                h=$("#dv_tmp_pizhu .notes_update").eq(0).find("strike").html();
                                                            else if($("#dv_tmp_pizhu .notes_update").eq(0).find("del").length>0)
                                                                h=$("#dv_tmp_pizhu .notes_update").eq(0).find("del").html();


                                                            $("#dv_tmp_pizhu").html($("#dv_tmp_pizhu").html()
                                                                .replace($("#dv_tmp_pizhu .notes_update").eq(0).attr("outerHTML")
                                                                    ,h));
                                                            if($("#dv_tmp_pizhu .notes_update").length<1){
                                                                $("#dv_all_pz").html($("#dv_tmp_pizhu").html());
                                                                break;
                                                            }
                                                        }


                                                        //note
//                                                        $("#dv_tmp_pizhu .notes_note").each(function(ix,im){
//                                                            $("#dv_tmp_pizhu").html($("#dv_tmp_pizhu").html().replace($(im).attr("outerHTML")
//                                                                ,$(im).find("a").html()));
//                                                        })
                                                        while($("#dv_all_pz .notes_note").length>0){
                                                            $("#dv_tmp_pizhu").html($("#dv_tmp_pizhu").html()
                                                                .replace($("#dv_tmp_pizhu .notes_note").eq(0).attr("outerHTML")
                                                                    ,$("#dv_tmp_pizhu .notes_note").eq(0).find("a").html()));
                                                            if($("#dv_tmp_pizhu .notes_note").length<1){
                                                                $("#dv_all_pz").html($("#dv_tmp_pizhu").html());
                                                                break;
                                                            }
                                                        }
                                                        //病句
//                                                        $("#dv_tmp_pizhu .notes_errsen").each(function(ix,im){
//                                                            $("#dv_tmp_pizhu").html($("#dv_tmp_pizhu").html().replace($(im).attr("outerHTML")
//                                                                ,$(im).children().children().children().html()));
//                                                        })
                                                        while($("#dv_all_pz .notes_errsen").length>0){
                                                            $("#dv_tmp_pizhu").html($("#dv_tmp_pizhu").html()
                                                                .replace($("#dv_tmp_pizhu .notes_errsen").eq(0).attr("outerHTML")
                                                                    ,$("#dv_tmp_pizhu .notes_errsen").eq(0).children().children().children().html()));
                                                            if($("#dv_tmp_pizhu .notes_errsen").length<1){
                                                                $("#dv_all_pz").html($("#dv_tmp_pizhu").html());
                                                                break;
                                                            }
                                                        }
                                                        //好句
//                                                        $("#dv_tmp_pizhu .notes_goodsen").each(function(ix,im){
//                                                            $("#dv_tmp_pizhu").html($("#dv_tmp_pizhu").html().replace($(im).attr("outerHTML")
//                                                                ,$(im).children().children().children().html()));
//                                                        })
                                                    while($("#dv_all_pz .notes_goodsen").length>0){
                                                        $("#dv_tmp_pizhu").html($("#dv_tmp_pizhu").html()
                                                            .replace($("#dv_tmp_pizhu .notes_goodsen").eq(0).attr("outerHTML")
                                                                ,$("#dv_tmp_pizhu .notes_goodsen").eq(0).children().children().children().html()));

                                                        if($("#dv_tmp_pizhu .notes_goodsen").length<1){
                                                            $("#dv_all_pz").html($("#dv_tmp_pizhu").html());
                                                            break;
                                                        }
                                                    }
                                                    //错别字
                                                    while($("#dv_all_pz .notes_errorword").length>0){
                                                        $("#dv_tmp_pizhu").html($("#dv_tmp_pizhu").html()
                                                            .replace($("#dv_tmp_pizhu .notes_errorword").eq(0).attr("outerHTML")
                                                                ,$("#dv_tmp_pizhu .notes_errorword").eq(0).children().children().children().html()));

                                                        if($("#dv_tmp_pizhu .notes_errorword").length<1){
                                                            $("#dv_all_pz").html($("#dv_tmp_pizhu").html());
                                                            break;
                                                        }
                                                    }
                                                    //错别字
                                                    while($("#dv_all_pz .notes_misnomer").length>0){
                                                        $("#dv_tmp_pizhu").html($("#dv_tmp_pizhu").html()
                                                            .replace($("#dv_tmp_pizhu .notes_misnomer").eq(0).attr("outerHTML")
                                                                ,$("#dv_tmp_pizhu .notes_misnomer").eq(0).children().children().html()));

                                                        if($("#dv_tmp_pizhu .notes_misnomer").length<1){
                                                            $("#dv_all_pz").html($("#dv_tmp_pizhu").html());
                                                            break;
                                                        }
                                                    }
                                                    //点评
                                                    while($("#dv_all_pz .notes_critique").length>0){
                                                        $("#dv_tmp_pizhu").html($("#dv_tmp_pizhu").html()
                                                            .replace($("#dv_tmp_pizhu .notes_critique").eq(0).attr("outerHTML")
                                                                ,$("#dv_tmp_pizhu .notes_critique").eq(0).children().children().html()));

                                                        if($("#dv_tmp_pizhu .notes_critique").length<1){
                                                            $("#dv_all_pz").html($("#dv_tmp_pizhu").html());
                                                            break;
                                                        }
                                                    }
                                                    $("#dv_tmp_pizhu a[id='notes_tag_pizhu_txt']").remove();
//														if(str.indexOf('(病句)')>0)str=str.replace("(病句)",'');
//														if(str.indexOf('(好句)')>0)str=str.replace("(好句)",'');
//														if(str.indexOf('(错别字)')>0)str=str.replace("(错别字)",'');
//														if(str.indexOf('(用词不当)')>0)str=str.replace("(用词不当)",'');
                                                    $("#dv_all_pz").html($("#dv_tmp_pizhu").html());

                                                    xheditorV.pasteHTML($("#dv_all_pz").html());
                                                    break;
                                                }
                                        }
							});  
					}					
				}
			}			
		}	
	});