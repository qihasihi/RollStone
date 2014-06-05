var LeibieControl;
if (LeibieControl == undefined) {
	LeibieControl = function(settings) {
		this.init(settings);
	}
	/**
	 * isnum:是否多选
	 */
	LeibieControl = function(settings,ismu) {
		if(typeof(settings)=="undefined"||settings==null)
			settings={};	
		settings.isMultiple=ismu;
		this.init(settings);
	}
	/**
	 * checkedval:要选中的节点,以逗号隔开,多个以|隔开。
	 */
	LeibieControl = function(settings,ismu,checkedval) {
		if(typeof(settings)=="undefined"||settings==null)
			settings={};	
		settings.checkedval=checkedval;
		settings.isMultiple=ismu;
		this.init(settings);
	}
};

LeibieControl.prototype={
			init:function(settings){
                this.params=typeof(settings.params)=="undefined"?{}:settings.params;
				this.settings=settings;
                this.sub_isMultiple=false;
                this.grade_isMultiple=false;
                this.rt_isMultiple=false;
                this.ft_isMultiple=false;
				this.initSettings();				
				this.initPropertyArea();
			},			
			initSettings : function() {
				this.ensureDefault = function(settingName, defaultValue) {
					this.settings[settingName] = (this.settings[settingName] == undefined) ? defaultValue
							: this.settings[settingName];
				};
				this.ensureDefault("controlid", null); // * 链接地址
				this.ensureDefault("isMultiple",false); // 是否可以多选		
				this.ensureDefault("checkedval",""); // 是否可以多选		
				/*this.customSettings = this.settings.custom_settings;*/
				delete this.ensureDefault;
			},		
		//设置默认值
		initPropertyArea:function(){  
			//初始化属性			
			var paHtml="";
			paHtml+="<div id='selectedProperties' class='yixuan'><p><strong>已选择：<span id='wu'>无</span></strong></p></div>";
			paHtml+="<div class='jxpt_ziyuan_zrss_option' id='extendMainDIV'></div>";
			$("#PropertyArea").html(paHtml);
			this.initSelectedArea();
            this.createExtendList();
		},

		initSelectedArea:function(){
//            this.params.sharestatusvalues=null;
//            this.params.subjectvalues=null;
//            this.params.gradevalues=null;
//            this.params.restypevalues=null;
//            this.params.filetypevalues=null;
//            this.params.timerange=null;
            var trHtml='<ul>';
            trHtml+='<li style="display:none;" id="et_sharestatusvalues_selected"></li>';
            trHtml+='<li style="display:none;" id="et_subjectvalues_selected"></li>';
            trHtml+='<li style="display:none;" id="et_gradevalues_selected"></li>';
            trHtml+='<li style="display:none;" id="et_restypevalues_selected"></li>';
            trHtml+='<li style="display:none;" id="et_filetypevalues_selected"></li>';
            trHtml+='<li style="display:none;" id="et_timerange_selected"></li>';
            trHtml+='</ul>';
            $("#selectedProperties").append(trHtml);
		},getShareStatusList:function(){
        var controlobj=this;
                var trHtml="";
                    trHtml="<div id='div_sharestatusvalues_mul'><ul id='val_ls_mul_sharestatusvalues' style='float:left;'>";
                    trHtml+="<li id='value_sharestatusvalues_mul_2'>";
                    trHtml+="<input title='云端' name='ext_ck_sharestatusvalues'id='ext_ck_sharestatusvalues_2' type='checkbox' value='2' />&nbsp;<label for='ext_ck_sharestatusvalues_2'>云端</label>";
                    trHtml+="</li>";
                    trHtml+="<li id='value_sharestatusvalues_mul_1'>";
                    trHtml+="<input title='校内' name='ext_ck_sharestatusvalues'id='ext_ck_sharestatusvalues_1' type='checkbox' value='1' />&nbsp;<label for='ext_ck_sharestatusvalues_1'>校内</label>";
                    trHtml+="</li>";

                    trHtml+="</ul>";
                    trHtml+="<p class='p_b_10'>";
                    trHtml+="<a class='an_public3' href="+"javascript:"+controlobj.settings['controlid']+".subMultipleChoice('sharestatusvalues');"+" class='an_blue_small'>确定</a>";
                    trHtml+="&nbsp;&nbsp;&nbsp;&nbsp;";
                    trHtml+="<a class='an_public3' href="+"javascript:"+controlobj.settings["controlid"]
                        +".toggleMultiple('sharestatusvalues',false)"+" class='an_blue_small'>取消</a></p>";
                    trHtml+="</div>";
                    trHtml+="<div id='div_sharestatusvalues'><ul id='val_ls_sharestatusvalues'>";
                trHtml+="<li id='value_sharestatusvalues_1'><a href="+"javascript:"+controlobj.settings["controlid"]
                    +".clickedProperty('sharestatusvalues','1','校内',false)"+">校内</a></li>";
                trHtml+="<li id='value_sharestatusvalues_2'><a href="+"javascript:"+controlobj.settings["controlid"]
                    +".clickedProperty('sharestatusvalues','2','云端',false)"+">云端</a></li>";
                                if(controlobj.settings.isMultiple)
                                    trHtml+="<li class='more'><a id='mulcho_sharestatusvalues' href="+"javascript:"+controlobj.settings["controlid"]
                                        +".toggleMultiple('sharestatusvalues',true)"+">+多选</a></li>";
                    trHtml+="</ul></div>";
                $("#ev_area_sharestatusvalues").html(trHtml);
                 $("#div_sharestatusvalues_mul").hide();
                if(typeof(controlobj.params.sharestatusvalues)!="undefined"&&controlobj.params.sharestatusvalues!=null){
                    controlobj.clickedProperty('sharestatusvalues',controlobj.params.sharestatusvalues,
                        $("#value_sharestatusvalues_"+controlobj.params.sharestatusvalues+" a").text(),false,false);
                }
         },createExtendList:function(){
            var controlobj=this;
            var trHtml='';
            trHtml+='<input id="sharestatusvalues_title" type="hidden" value="共享类型"/>';
            trHtml+='<input id="subjectvalues_title" type="hidden" value="学科">';
            trHtml+='<input id="gradevalues_title" type="hidden" value="年级">';
            trHtml+='<input id="restypevalues_title" type="hidden" value="资源类型">';
            trHtml+='<input id="filetypevalues_title" type="hidden" value="文件类型">';
            trHtml+='<input id="timerange_title" type="hidden" value="发布时间">';
            trHtml+='<table border="0" cellpadding="0" cellspacing="0" class="public_tab1" style="float: left">';
            trHtml+='<tr id="extend_sharestatusvalues"><th>共享类型：</th><td id="ev_area_sharestatusvalues"></td></tr>';
            trHtml+='<tr id="extend_subjectvalues"><th>学&nbsp;&nbsp;&nbsp;&nbsp;科：</th>';
            trHtml+='<td id="ev_area_subjectvalues">&nbsp;</td></tr>';
            trHtml+='<tr id="extend_gradevalues"><th>年&nbsp;&nbsp;&nbsp;&nbsp;级：</th>';
            trHtml+='<td id="ev_area_gradevalues">&nbsp;</td></tr>';
            trHtml+='<tr id="extend_restypevalues"><th>资源类型：</th>';
            trHtml+='<td id="ev_area_restypevalues">&nbsp;</td></tr>';
            trHtml+='<tr id="extend_filetypevalues"><th>文件类型：</th>';
            trHtml+='<td id="ev_area_filetypevalues">&nbsp;</td></tr>';
            trHtml+='<tr id="extend_timerange"><th>发布时间：</th><td id="ev_area_timerange"><ul id="val_ls_timerange">';
            trHtml+="<li id='value_timerange_day'><a href="+"javascript:"+controlobj.settings["controlid"]
                +".clickedProperty('timerange','day','今天',true)"+">今天</a></li>";
            trHtml+="<li id='value_timerange_week'><a href="+"javascript:"+controlobj.settings["controlid"]
                +".clickedProperty('timerange','week','一周内',true)"+">一周内</a></li>";
            trHtml+="<li id='value_timerange_halfMonth'><a href="+"javascript:"+controlobj.settings["controlid"]
                +".clickedProperty('timerange','halfMonth','15天以内',true)"+">15天以内</a></li>";
            trHtml+="<li id='value_timerange_month'><a href="+"javascript:"+controlobj.settings["controlid"]
                +".clickedProperty('timerange','month','一个月内',true)"+">一个月内</a></li>";
            trHtml+='</ul></td></tr>';
            trHtml+='<tr style="display:none;"><th>&nbsp;</th><td><a href="javascript:doSearch();" class="an_blue">搜索</a></td></tr>';
            trHtml+='<tr><td colspan="2"></td></tr>';
            trHtml+='</table>';
            $("#extendMainDIV").html(trHtml);
            this.getShareStatusList();
            this.getExtendSubList();
            this.getExtendGradeList();
            this.getExtendRTList();
            this.getExtendFTList();
		},

		getExtendSubList:function(){
            var controlobj=this;
			$.ajax({
				url:'subject?m=ajaxlist',
				type:'POST', 
				data:{},
                dataType:'json',
				error:function(){alert("网络异常")},
				success:function(rps){
					var trHtml="";
					if(rps!=null&&rps.objList!=null&&rps.objList.length>0){
                        trHtml="<div id='div_subjectvalues_mul'><ul id='val_ls_mul_subjectvalues' style='float:left;'>";
                        $.each(rps.objList,function(idx,itm) {
                            trHtml+="<li id='value_subjectvalues_mul_"+itm.subjectid+"'>";
                            trHtml+="<input title='"+itm.subjectname+"' name='ext_ck_subjectvalues' type='checkbox' value='"+itm.subjectid+"' />&nbsp;"+itm.subjectname;
                            trHtml+="</li>";
                        });
                        trHtml+="</ul>";
                        trHtml+="<p class='p_b_10'>";
                        trHtml+="<a class='an_public3' href="+"javascript:"+controlobj.settings['controlid']+".subMultipleChoice('subjectvalues');"+" class='an_blue_small'>确定</a>";
                        trHtml+="&nbsp;&nbsp;&nbsp;&nbsp;";
                        trHtml+="<a class='an_public3' href="+"javascript:"+controlobj.settings["controlid"]
                            +".toggleMultiple('subjectvalues',false)"+" class='an_blue_small'>取消</a></p>";
                        trHtml+="</div>";
						trHtml+="<div id='div_subjectvalues'><ul id='val_ls_subjectvalues'>";
                        $.each(rps.objList,function(idx,itm) {
                            trHtml+="<li id='value_subjectvalues_"+itm.subjectid+"'><a href="+"javascript:"+controlobj.settings["controlid"]
                                +".clickedProperty('subjectvalues','"+itm.subjectid+"','"+itm.subjectname+"',true)"+">"+itm.subjectname+"</a></li>";
                        });

//                        if(controlobj.settings.isMultiple)
//                            trHtml+="<li class='more'><a id='mulcho_subjectvalues' href="+"javascript:"+controlobj.settings["controlid"]
//                                +".toggleMultiple('subjectvalues',true)"+">+多选</a></li>";
                        trHtml+="</ul></div>";
					}
					$("#ev_area_subjectvalues").html(trHtml);
                    $("#div_subjectvalues_mul").hide();

                    if(typeof(controlobj.params.subjectvalues)!="undefined"&&controlobj.params.subjectvalues!=null){
                        controlobj.clickedProperty('subjectvalues',controlobj.params.subjectvalues,
                        $("#value_subjectvalues_"+controlobj.params.subjectvalues+" a").text(),true,true);
                    }
		        }
            })
        },

        getExtendGradeList:function(){
            var controlobj=this;
            $.ajax({
                url:'grade?m=ajaxlist',
                type:'POST',
                data:{},
                dataType:'json',
                error:function(){alert("网络异常")},
                success:function(rps){
                    var trHtml="";
                    if(rps!=null&&rps.objList!=null&&rps.objList.length>0){
                        trHtml="<div id='div_gradevalues_mul'><ul id='val_ls_mul_gradevalues' style='float:left;'>";
                        $.each(rps.objList,function(idx,itm) {
                            trHtml+="<li id='value_gradevalues_mul_"+itm.gradeid+"'>";
                            trHtml+="<input title='"+itm.gradename+"' name='ext_ck_gradevalues' type='checkbox' value='"+itm.gradeid+"' />&nbsp;"+itm.gradename;
                            trHtml+="</li>";
                        });
                        trHtml+="</ul>";
                        trHtml+="<p class='p_b_10'>";
                        trHtml+="<a class='an_public3' href="+"javascript:"+controlobj.settings['controlid']+".subMultipleChoice('gradevalues');"+" class='an_blue_small'>确定</a>";
                        trHtml+="&nbsp;&nbsp;&nbsp;&nbsp;";
                        trHtml+="<a class='an_public3' href="+"javascript:"+controlobj.settings["controlid"]
                            +".toggleMultiple('gradevalues',false)"+" class='an_blue_small'>取消</a></p>";
                        trHtml+="</div>";
                        trHtml+="<div id='div_gradevalues'><ul id='val_ls_gradevalues'>";
                        $.each(rps.objList,function(idx,itm) {
                            trHtml+="<li id='value_gradevalues_"+itm.gradeid+"'><a href="+"javascript:"+controlobj.settings["controlid"]
                                +".clickedProperty('gradevalues','"+itm.gradeid+"','"+itm.gradename+"',false)"+">"+itm.gradename+"</a></li>";
                        });

                        if(controlobj.settings.isMultiple)
                            trHtml+="<li class='more'><a id='mulcho_gradevalues' href="+"javascript:"+controlobj.settings["controlid"]
                                +".toggleMultiple('gradevalues',true)"+">+多选</a></li>";
                        trHtml+="</ul></div>";
                    }
                    $("#ev_area_gradevalues").html(trHtml);
                    $("#div_gradevalues_mul").hide();
                }
            })
        },

        getExtendRTList:function(){
            var controlobj=this;
            $.ajax({
                url:'dictionary?m=ajaxlist',
                type:'POST',
                data:{dictionarytype:'RES_TYPE'},
                dataType:'json',
                error:function(){alert("网络异常")},
                success:function(rps){
                    var trHtml="";
                    if(rps!=null&&rps.objList!=null&&rps.objList.length>0){
                        trHtml="<div id='div_restypevalues_mul'><ul id='val_ls_mul_restypevalues' style='float:left;'>";
                        $.each(rps.objList,function(idx,itm) {
                            trHtml+="<li id='value_restypevalues_mul_"+itm.dictionaryvalue+"'>";
                            trHtml+="<input title='"+itm.dictionaryname+"' name='ext_ck_restypevalues' type='checkbox' value='"+itm.dictionaryvalue+"' />&nbsp;"+itm.dictionaryname;
                            trHtml+="</li>";
                        });
                        trHtml+="</ul>";
                        trHtml+="<p class='p_b_10'>";
                        trHtml+="<a class='an_public3' href="+"javascript:"+controlobj.settings['controlid']+".subMultipleChoice('restypevalues');"+" class='an_blue_small'>确定</a>";
                        trHtml+="&nbsp;&nbsp;&nbsp;&nbsp;";
                        trHtml+="<a class='an_public3' href="+"javascript:"+controlobj.settings["controlid"]
                            +".toggleMultiple('restypevalues',false)"+" class='an_blue_small'>取消</a></p>";
                        trHtml+="</div>";
                        trHtml+="<div id='div_restypevalues'><ul id='val_ls_restypevalues'>";
                        $.each(rps.objList,function(idx,itm) {
                            trHtml+="<li id='value_restypevalues_"+itm.dictionaryvalue+"'><a href="+"javascript:"+controlobj.settings["controlid"]
                                +".clickedProperty('restypevalues','"+itm.dictionaryvalue+"','"+itm.dictionaryname+"',false)"+">"+itm.dictionaryname+"</a></li>";
                        });
                        if(controlobj.settings.isMultiple)
                            trHtml+="<li class='more'><a id='mulcho_restypevalues' href="+"javascript:"+controlobj.settings["controlid"]
                                +".toggleMultiple('restypevalues',true)"+">+多选</a></li>";
                        trHtml+="</ul></div>";
                    }
                    $("#ev_area_restypevalues").html(trHtml);
                    $("#div_restypevalues_mul").hide();
                }
            })
        },

        getExtendFTList:function(){
            var controlobj=this;
            $.ajax({
                url:'dictionary?m=ajaxlist',
                type:'POST',
                data:{dictionarytype:'RES_FILE_TYPE'},
                dataType:'json',
                error:function(){alert("网络异常")},
                success:function(rps){
                    var trHtml="";
                    if(rps!=null&&rps.objList!=null&&rps.objList.length>0){
                        trHtml="<div id='div_filetypevalues_mul'><ul id='val_ls_mul_filetypevalues' style='float:left;'>";
                        $.each(rps.objList,function(idx,itm) {
                            trHtml+="<li id='value_filetypevalues_mul_"+itm.dictionaryvalue+"'>";
                            trHtml+="<input title='"+itm.dictionaryname+"' name='ext_ck_filetypevalues' type='checkbox' value='"+itm.dictionaryvalue+"' />&nbsp;"+itm.dictionaryname;
                            trHtml+="</li>";
                        });
                        trHtml+="</ul>";
                        trHtml+="<p class='p_b_10'>";
                        trHtml+="<a class='an_public3' href="+"javascript:"+controlobj.settings['controlid']+".subMultipleChoice('filetypevalues');"+" class='an_blue_small'>确定</a>";
                        trHtml+="&nbsp;&nbsp;&nbsp;&nbsp;";
                        trHtml+="<a class='an_public3' href="+"javascript:"+controlobj.settings["controlid"]
                            +".toggleMultiple('filetypevalues',false)"+" class='an_blue_small'>取消</a></p>";
                        trHtml+="</div>";
                        trHtml+="<div id='div_filetypevalues'><ul id='val_ls_filetypevalues'>";
                        $.each(rps.objList,function(idx,itm) {
                            trHtml+="<li id='value_filetypevalues_"+itm.dictionaryvalue+"'><a href="+"javascript:"+controlobj.settings["controlid"]
                                +".clickedProperty('filetypevalues','"+itm.dictionaryvalue+"','"+itm.dictionaryname+"',false)"+">"+itm.dictionaryname+"</a></li>";
                        });

                        if(controlobj.settings.isMultiple)
                            trHtml+="<li class='more'><a id='mulcho_filetypevalues' href="+"javascript:"+controlobj.settings["controlid"]
                                +".toggleMultiple('filetypevalues',true)"+">+多选</a></li>";
                        trHtml+="</ul></div>";
                    }
                    $("#ev_area_filetypevalues").html(trHtml);
                    $("#div_filetypevalues_mul").hide();
                }
            })
        },

        toggleMultiple:function(type,status){
            if(status){
                $("#extend_"+type).attr("class","blue");
                $("#div_"+type+"_mul").show();
                $("#div_"+type).hide();
            }else{
                $("#extend_"+type).attr("class","");
                $("#div_"+type+"_mul").hide();
                $("#div_"+type).show();
            }
        },

        clickedProperty:function(type,value,valuename,istime,isselected){
            $("#val_ls_"+type).children("li").attr("class","");
            if(!istime){
                $("#val_ls_"+type).children("li:last").attr("class","more");
            }
            $("#value_"+type+"_"+value).attr("class","font-blue1");
            this.creatPropertySelected(type,value,valuename,isselected);
        },

        subMultipleChoice:function(type){
            var values = new Array();
            var valuename = "";
            $.each($("input[type='checkbox'][name='ext_ck_"+type+"']:checked"),function(idx,itm){
                values.push($(this).val());
                if(idx>0)
                    valuename+="/";
                valuename+=$(this).attr("title");
            });
            this.creatPropertySelected(type,values.join(','),valuename);
            this.toggleMultiple(type,false);
        },

        creatPropertySelected:function(type,value,valuename,isselected){
			var psHtml="";
            var title=$("#"+type+"_title").val();
            psHtml+=title+"："+valuename+"<a href="+"javascript:"+this.settings["controlid"]+".delPropertySelected('"+type+"')"
                +" title='删除' class='ico_delete'></a>";
			$("#et_"+type+"_selected").html(psHtml);
			$("#et_"+type+"_selected").show();
            $("#wu").hide();
            this.params[type]=value;
            if(typeof(isselected)=="undefined"||isselected)
                pageGo("p1",1);
		},		
		
		delPropertySelected:function(type){
           // $("#val_ls_"+type).children("li").attr("class","");
            //$("#val_ls_"+type).children("li").last().attr("class","more");
            $("#et_"+type+"_selected").html("");
            $("#et_"+type+"_selected").hide();
            this.params[type]=null;
            if(this.params.subjectvalues==null&&this.params.gradevalues==null&&this.params.restypevalues==null&&this.params.filetypevalues==null&&this.params.timerange==null)
                $("#wu").show();
            pageGo("p1",1);
		}
};


/////////////////////////////////////////////////resource/add.jsp

/**
*验证
*/
function validateDate(){
	var hdid=$("#hd_id").val();
	if(hdid.Trim().length<1){
		alert("异常错误，原因：未知!");
		return;
	}
	var resourceObj=$("#resource_name");
	if(resourceObj.val().Trim().length<1){
		alert('资源名称尚未输入，请输入!');
		resourceObj.focus();
		return;
	}
/*	var selectedValue=lb.selectedValue;
	if(selectedValue.length<1){
		alert('资源类别尚未选择，请选择!');
		selectedValue.focus();
		return;
	}*/
	var keyword=$("#key_word");
	if(keyword.val().Trim().length<1){
		alert('资源关键字尚未输入，请输入!');
		keyword.focus();
		return;
	}
	var introduce=$("#introduce");
	if(introduce.val().Trim().length<1){
		alert('资源简介尚未输入，请输入!');
		introduce.focus();
		return;
	}
	var extendsvalueid="",extendsfaterid="",positions="",rootextendid="";
	var extendvalue=lb.extendValueArray;
	if(extendvalue.length>0){
		$.each(lb.extendValueArray,function(idx,itm){
			if(itm.values.length>0){
				$.each(itm.values,function(ix,im){
					if(extendsvalueid.Trim().length>0)
						extendsvalueid+="|";
					extendsvalueid+=im;
					//父节点名称
					if(extendsfaterid.Trim().length>0)	
						extendsfaterid+="|";
					extendsfaterid+=itm.farthername;
					//父节点集合  +  选中的底层结构
					if(positions.Trim().length>0)
						positions+="|";
					positions+=itm.posisions+im;
					
					if(rootextendid.Trim().length>0)	
						rootextendid+="|";
					rootextendid+=itm.rootid;
				})
			}
		}) 
	}
	if(extendsvalueid.Trim().length<1){
			alert('资源类别尚未选择，请选择!');return;
	}
//	if(exvaluecount.Trim().length>0&&parseInt(exvaluecount)!=extendsvalueid.Trim().split("|").length){
//		alert('资源类别您还未全部选择，请选择!');return;
//	}
	if(rootextendid.Trim().length<1||
			(exvaluecount.Trim().length>0&&parseInt(exvaluecount)!=rootextendid.Trim().split("|").length)){
			alert('异常错误，原因：rootextendid is empty!');return;
	}
	if(typeof(uploadControl.fileAttribute)=="undefined"||uploadControl.fileAttribute.length<1){
		alert('异常错误，原因：uploadControl.fileAttribute is empty!');return;
	}
	var fname='';
	$.each(uploadControl.fileAttribute,function(ix,im){
		if(typeof(im)!="undefined"&&im!=undefined&&im.filename.Trim().length>0){
			if(fname.Trim().length>0)
				fname+='|'; 
			fname+=im.filename.Trim()+'*'+im.size;
		}
	}) 
	if(fname.length<1){
		alert('异常错误，原因：fname is empty!');return;
	}
	var param={resid:hdid,resname:resourceObj.val().Trim()
			  ,reskeyword:keyword.val().Trim()
			  ,resintroduce:introduce.val().Trim()
			  ,extendsvalueid:extendsvalueid
			  ,positions:positions
			  ,extendsfater:extendsfaterid
			  ,rootextendidarr:rootextendid
			  ,filename:fname
			};
			
	
	//权限信息   浏览权限
	if($("#ck_right_view_all")[0].checked){
		var rightviewroletypeObj=$("input[name='ck_right_view']:checked");
		param.rightviewroletype=rightviewroletypeObj.val();
		var rightviewroletype=rightviewroletypeObj.val();
		if(rightviewroletype==3){
			var hd=$("p[id='p_right_view_teacher'] input[type='hidden']");
			var h='';
			hd.each(function(id,m){
				if(h.length>0)
					h+=',';
				h+=m.value.Trim();
			});
			if(h.Trim().length<1){
				alert('错误，浏览权限类型选择指定教师，系统没有获取到!');return;
			}
			param.rightviewuserid=h;
			if(typeof(param.rightviewroletype)=="undefined"){
				if(!confirm("提示：你尚未选择浏览权限，因此你提交后其它用户是无法搜索到!\n\n您确认吗?"))
					return;
			}
		}else if(rightviewroletype==2){
			var selViewSubject=$("select[id='right_view_subject']").val();
			if(typeof(selViewSubject)=="undefined"||selViewSubject==null){
				alert('异常，系统没有发现科目!');
				$("select[id='right_view_subject']").focus();
				return;
			}
			param.subjectviewid=selViewSubject;
		}
	}	
	//权限信息   浏览权限
	if($("#ck_right_down_all")[0].checked){
		var rightdownroletypeVal=$("input[name='ck_right_down']:checked");
		param.rightdownroletype=rightdownroletypeVal.val();		
		var rightdownroletype=rightdownroletypeVal.val();
		if(rightdownroletype==3){
			var hd=$("p[id='p_right_down_teacher'] input[type='hidden']");
			var h='';
			hd.each(function(id,m){
				if(h.length>0)
					h+=',';
				h+=m.value.Trim();
			});
			if(h.Trim().length<1){
				alert('错误，下载权限类型选择指定教师，系统没有获取到!');return;
			}
			param.rightdownuserid=h;
			if(typeof(param.rightdownroletype)=="undefined"){	
				if(!confirm("提示：你尚未选择下载权限，因此你提交后其它用户是无法对您的资源进行下载!\n\n您确认吗?"))
					return;
			}
		}else if(rightdownroletype==2){
			var selDownSubject=$("select[id='right_down_subject']").val();
			if(typeof(selDownSubject)=="undefined"||selDownSubject==null){
				alert('异常，系统没有发现科目!');
				$("select[id='right_down_subject']").focus();
				return;
			}
			param.subjectdownid=selDownSubject;
		}
	}	
	if(!confirm("验证完毕，您确认上传该资源吗？"))
		return;
	$.ajax({
		url:'resource?m=doadd',
		type:'POST',
		data:param,
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				//上传成功，更新UUID
				$("#hd_id").val(rps.objList[0]);
				alert("上传资源成功!");
			}
		}
	});
	//文件	
}
	
	
	///////////////////////////resource/add.jsp    ---end

////////////////////////////resource/update.jsp   ----begin
/**
*验证
*/
function validateUpdateDate(){
	var hdid=$("#hd_resid").val();
	if(hdid.Trim().length<1){
		alert("异常错误，原因：未知!");
		return;
	}
	var resourceObj=$("#resource_name");
	if(resourceObj.val().Trim().length<1){
		alert('资源名称尚未输入，请输入!');
		resourceObj.focus();
		return;
	}
/*	var selectedValue=lb.selectedValue;
	if(selectedValue.length<1){
		alert('资源类别尚未选择，请选择!');
		selectedValue.focus();
		return;
	}*/
	var keyword=$("#key_word");
	if(keyword.val().Trim().length<1){
		alert('资源关键字尚未输入，请输入!');
		keyword.focus();
		return;
	}
	var introduce=$("#introduce");
	if(introduce.val().Trim().length<1){
		alert('资源简介尚未输入，请输入!');
		introduce.focus();
		return;
	}
	var extendsvalueid="",extendsfaterid="",positions="",rootextendid="";
	var extendvalue=lb.extendValueArray;
	if(extendvalue.length>0){
		$.each(lb.extendValueArray,function(idx,itm){
			if(itm.values.length>0){
				$.each(itm.values,function(ix,im){
					if(extendsvalueid.Trim().length>0)
						extendsvalueid+="|";
					extendsvalueid+=im;
					//父节点名称
					if(extendsfaterid.Trim().length>0)	
						extendsfaterid+="|";					
						extendsfaterid+=itm.farthername;
					//父节点集合  +  选中的底层结构
					if(positions.Trim().length>0)	
						positions+="|";
					positions+=itm.posisions+im;
					
					if(rootextendid.Trim().length>0)	
						rootextendid+="|";
					rootextendid+=itm.rootid;
				})
			}
		}) 
	}
	if(extendsvalueid.Trim().length<1
		||extendsfaterid.Trim().length<1){
			alert('资源类别尚未选择，请选择!');return;
	}
//	if(exvaluecount.Trim().length<1&&parseInt(exvaluecount)!=extendsvalueid.Trim().split("|").length){
//		alert('资源类别您还未全部选择，请选择!');return;
//	}   
	if(rootextendid.Trim().length<1||
		(exvaluecount.Trim().length>0&&parseInt(exvaluecount)!=rootextendid.Trim().split("|").length)){
		alert('异常错误，原因：rootextendid is empty!');return;
	}
	if(typeof(uploadControl.fileAttribute)=="undefined"||uploadControl.fileAttribute.length<1){
		alert('异常错误，原因：uploadControl.fileAttribute is empty!');return;
	}
	var fname='';
	$.each(uploadControl.fileAttribute,function(ix,im){
		if(typeof(im)!="undefined"&&im!=undefined&&im.filename.Trim().length>0){
			if(fname.Trim().length>0)
				fname+='|'; 
			fname+=im.filename.Trim()+'*'+im.size;
		}
	})
	if(fname.length<1){
		alert('异常错误，原因：fname is empty!');return;
	}
	var param={resid:hdid,resname:resourceObj.val().Trim()
			  ,reskeyword:keyword.val().Trim()
			  ,resintroduce:introduce.val().Trim()
			  ,extendsvalueid:extendsvalueid
			  ,positions:positions
			  ,extendsfater:extendsfaterid
			  ,rootextendidarr:rootextendid	
			  ,filename:fname		
			};
			
	
	//权限信息   浏览权限
	if($("#ck_right_view_all")[0].checked){
		var rightviewroletypeObj=$("input[name='ck_right_view']:checked");
		param.rightviewroletype=rightviewroletypeObj.val();
		var rightviewroletype=rightviewroletypeObj.val();
		if(rightviewroletype==3){
			var hd=$("p[id='p_right_view_teacher'] input[type='hidden']");
			var h='';
			hd.each(function(id,m){
				if(h.length>0)
					h+=',';
				h+=m.value.Trim();
			});
			if(h.Trim().length<1){
				alert('错误，浏览权限类型选择指定教师，系统没有获取到!');return;
			}
			param.rightviewuserid=h;
			if(typeof(param.rightviewroletype)=="undefined"){
				if(!confirm("提示：你尚未选择浏览权限，因此你提交后其它用户是无法搜索到!\n\n您确认吗?"))
					return;
			}
		}else if(rightviewroletype==2){
			var selViewSubject=$("select[id='right_view_subject']").val();
			if(typeof(selViewSubject)=="undefined"||selViewSubject==null){
				alert('异常，系统没有发现科目!');
				$("select[id='right_view_subject']").focus();
				return;
			}
			param.subjectviewid=selViewSubject;
		}
	}	
	//权限信息   浏览权限
	if($("#ck_right_down_all")[0].checked){
		var rightdownroletypeVal=$("input[name='ck_right_down']:checked");
		param.rightdownroletype=rightdownroletypeVal.val();		
		var rightdownroletype=rightdownroletypeVal.val();
		if(rightdownroletype==3){
			var hd=$("p[id='p_right_down_teacher'] input[type='hidden']");
			var h='';
			hd.each(function(id,m){
				if(h.length>0)
					h+=',';
				h+=m.value.Trim();
			});
			if(h.Trim().length<1){
				alert('错误，下载权限类型选择指定教师，系统没有获取到!');return;
			}
			param.rightdownuserid=h;
			if(typeof(param.rightdownroletype)=="undefined"){	
				if(!confirm("提示：你尚未选择下载权限，因此你提交后其它用户是无法对您的资源进行下载!\n\n您确认吗?"))
					return;
			}
		}else if(rightdownroletype==2){
			var selDownSubject=$("select[id='right_down_subject']").val();
			if(typeof(selDownSubject)=="undefined"||selDownSubject==null){
				alert('异常，系统没有发现科目!');
				$("select[id='right_down_subject']").focus();
				return;
			}
			param.subjectdownid=selDownSubject;
		}
	}	
	if(!confirm("验证完毕，您确认上传该资源吗？"))
		return;
	$.ajax({
		url:'resource?m=doupdate',
		type:'POST',
		data:param,
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				//上传成功，更新UUID
				$("#hd_id").val(rps.objList[0]);
				alert("修改资源成功!");
			}
		}
	});
	//文件
}
////////////////////////resource/update.jsp ---end;

///////////////////////resource/detail.jsp  ---begin;
/**
 *添加收藏
 *@params type: 类型  1:添加收藏  2:取消收藏
 *@params resid: 资源ID
 *@params dvid:显示的地址ID
 */
function operateStore(type,resid,dvid){
	if(typeof(type)=="undefined"||type==null){
		alert('异常错误，请刷新页面后重试!原因：type is empty!');return;
	}
	if(typeof(resid)=="undefined"||resid==null||resid.Trim().length<1){
		alert("异常错误，请刷新页面后重试! 原因：resid is empty!");return;
	}
	var u='store?m='; 
	if(type==1)
		u+='doadd';
	else
		u+='dodelete';
	$.ajax({
		url:u,
		type:'POST',
		data:{resid:resid},
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				var htm='';
				if(type==1){
					htm+='<a href="javascript:;" onclick="operateStore(2,\''+resid+'\',\'dv_store\')">';
					htm+='取消收藏';
					htm+='</a>';
				}else{
					htm+='<a href="javascript:;" onclick="operateStore(1,\''+resid+'\',\'dv_store\')">';
					htm+='收藏';
					htm+='</a>';
				}
				$("#"+dvid).html(htm); 
			}
		}
	});    		
}

function addResourceComment(){
	var commenttype=$("#commenttype").val();
	var commentobjectid=$("#commentobjectid").val();
	var score=comment_star.value;
	var commentcontext=$("#commentcontext").val();
	var anonymous=0;	
	
	if(typeof(commenttype)=="undefined"||commenttype==null){
		alert('参数不足，无法提交评论！');return;
	}
	if(typeof(commentobjectid)=="undefined"||commentobjectid==null){
		alert('参数不足，无法提交评论！');return;
	}
	if(typeof(score)=="undefined"||score==null){
		alert('请打分，否则无法提交评论！');return;
	}
	if(typeof(commentcontext)=="undefined"||commentcontext==null||commentcontext.Trim().length<1){
		alert('请输入评论内容，否则无法提交评论！');return;
	}
	var anonymous=0;	
	if($("#anonymous").attr("checked"))
		anonymous=1;
	$.ajax({
		url:"commoncomment?m=ajaxsave",
		type:'POST',
		data:{
			commenttype:commenttype,
			commentobjectid:commentobjectid,
			score:score,
			commentcontext:commentcontext,
			anonymous:anonymous
			},
		dataType:'json',
		error:function(){alert("网络异常")},
		success:function(rps){
			if(rps.type=="error"){
				alert(rps.msg);
			}else{
				var htm='';
				htm+='<tr><td align="center">您已经进行过评论！</td></tr>';
				$("#newCommentTLB").html(htm); 
			}
		}
	});    		
}

//////////////////////resource/detail.jsp   ---end;
