
function getSubjectResource(subValueId){
		def_sub=subValueId;
		$("#sub_li_"+subValueId).siblings().attr("class","");
		$("#sub_li_"+subValueId).attr("class","crumb");
		pageGo('p_exe_video');	
		pageGo('p_exe_doc');	
		pageGo('p_exe_other');
		$.each(rts,function(n,rt){
			if(n>0){
				$("#ul_"+rt).hide();
				$("#ul_"+rt).html("<li><img src='img/validateload.gif'/> 数据加载中……</li>");
				$("#more_"+rt).attr("href","resource?m=tosubjectpage&sub="+subValueId+"&type="+rt);
				getResourceType(rt,subValueId,rt,null);
				$("#ul_"+rt).show("fast");
			}
		});
	}
	
function getResourceType(rt,sub,type,orderBy){
    if(orderBy==null)
        orderBy="C_TIME";
    $.ajax({
        url:'resource?m=ajaxList',
        type:'POST',
        data:{subject:sub,restype:type,'pageResult.pageSize':5,'pageResult.orderBy':orderBy},
        dataType:'json',
        error:function(){alert("网络异常")},
        success:function(rps){
            var html="";
            if(rps!=null&&rps.presult.list.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+="<li><s>"+itm.ctimeString.substring(0,10)+"</s><b><a href='resource?m=toteacherreslist&userid="+itm.userref+"' target='_blank'>"
                        +(itm.username.length>3?(itm.username.substring(0,3)+"..."):itm.username)+"</a></b></li>";
                    html+="<a title='"+itm.resname+"' href='resource?m=todetail&resid="+itm.resid+"' target='_blank'>"+ (itm.resname.length>14?(itm.resname.substring(0,13)+"..."):itm.resname)+"</a>";
                    html+="</li>";
                });
            }else{
                html+="<li>暂无数据！</li>";
            }
            $("#ul_"+rt).html(html);
        }
    });
}

function getLastUploads(time){
    $("#ulLastUploads").html("<li><img src='img/validateload.gif'/> 数据加载中……</li>");
    $.ajax({
        url:'resource?m=ajaxList',
        type:'POST',
        data:{'pageResult.orderBy':'C_TIME',
            'pageResult.pageSize':10,
            timerange:time},
        dataType:'json',
        error:function(){alert("网络异常")},
        success:function(rps){
            var html="";
            html+="<li><p class='p_lr_15 t_r'>"
                +"<a href="+"javascript:getLastUploads('day');"+" class='"+(time=="day"?"font-blue1":"")+"'>日</a>|"
                +"<a href="+"javascript:getLastUploads('week');"+" class='"+(time=="week"?"font-blue1":"")+"'>周</a>|"
                +"<a href="+"javascript:getLastUploads('month');"+" class='"+(time=="month"?"font-blue1":"")+"'>月</a>&nbsp;&nbsp;"
                +"<a target='_blank' href='resource?m=toresourcetop&topType=C_TIME'>更多</a></p></li>";
            if(rps!=null&&rps.presult.list.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+="<li><span>"+itm.ctimeString.substring(5,10)+"</span><a target='_blank' title='"+itm.resname+"' href='resource?m=todetail&resid="+itm.resid+"'>";
                    html+=(itm.resname.length>10?itm.resname.substring(0,8)+"....":itm.resname)+"</a></li>";
                });
            }else{
                html+="<li>暂无数据！</li>";
            }
            $("#ulLastUploads").html(html);
        }
    });
}

function getScoreTop(time){
    $("#ulScoreTop").html("<li><img src='img/validateload.gif'/> 数据加载中……</li>");
    $.ajax({
        url:'resource?m=ajaxList',
        type:'POST',
        data:{'pageResult.orderBy':'RES_SCORE',
            'pageResult.pageSize':10,
            timerange:time},
        dataType:'json',
        error:function(){alert("网络异常")},
        success:function(rps){
            var html="";
            html+="<li><p class='p_lr_15 t_r'>"
                +"<a href="+"javascript:getScoreTop('day');"+" class='"+(time=="day"?"font-blue1":"")+"'>日</a>|"
                +"<a href="+"javascript:getScoreTop('week');"+" class='"+(time=="week"?"font-blue1":"")+"'>周</a>|"
                +"<a href="+"javascript:getScoreTop('month');"+" class='"+(time=="month"?"font-blue1":"")+"'>月</a>&nbsp;&nbsp;"
                +"<a target='_blank' href='resource?m=toresourcetop&topType=RES_SCORE'>更多</a></p></li>";
            if(rps!=null&&rps.presult.list.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+="<li><span>"+itm.resscore+"分</span><a target='_blank' title='"+itm.resname+"' href='resource?m=todetail&resid="+itm.resid+"'>";
                    html+=(itm.resname.length>10?itm.resname.substring(0,8)+"....":itm.resname)+"</a></li>";
                });
            }else{
                html+="<li>暂无数据！</li>";
            }
            $("#ulScoreTop").html(html);
        }
    });
}

function getViewClickTop(time){
    $("#ulViewClickTop").show();
    $("#ulDownloadTop").hide();
    $("#ulViewClickTop").html("<li><img src='img/validateload.gif'/> 数据加载中……</li>");
    $.ajax({
        url:'resource?m=ajaxList',
        type:'POST',
        data:{'pageResult.orderBy':'CLICKS',
            'pageResult.pageSize':7,
            timerange:time},
        dataType:'json',
        error:function(){alert("网络异常")},
        success:function(rps){
            var html="";
            html+="<li>"
                +"<a href="+"javascript:getViewClickTop('week');"+" class='"+(time=="week"?"font-blue":"")+"'>周</a>&nbsp;|&nbsp;"
                +"<a href="+"javascript:getViewClickTop('month');"+" class='"+(time=="month"?"font-blue":"")+"'>月</a>&nbsp;|&nbsp;"
                +"<a href="+"javascript:getDownloadTop('week');"+">最热下载</a></li>";
            if(rps!=null&&rps.presult.list.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+="<li><span class='f_right'>"+itm.clicks+"次</span><a target='_blank' href='resource?m=todetail&resid="+itm.resid+"'>";
                    html+=(itm.resname.length>10?itm.resname.substring(0,8)+"....":itm.resname)+"</a></li>";
                });
               // html+="<li class='t_r'><a href='resource?m=toresourcetop&topType=CLICKS&timeType="+time+"' target='_blank' title='more' class='ico_more'></a></li>";
            }else{
                html+="<li>暂无数据！</li>";
            }
            $("#ulViewClickTop").html(html);
        }
    });
}

function getDownloadTop(time){
    $("#ulDownloadTop").show();
    $("#ulViewClickTop").hide();
    $("#ulDownloadTop").html("<li><img src='img/validateload.gif'/> 数据加载中……</li>");
    $.ajax({
        url:'resource?m=ajaxList',
        type:'POST',
        data:{'pageResult.orderBy':'DOWNLOADNUM',
            'pageResult.pageSize':7,
            timerange:time},
        dataType:'json',
        error:function(){alert("网络异常")},
        success:function(rps){
            var html="";
            html+="<li>"
                +"<a href="+"javascript:getDownloadTop('week');"+" class='"+(time=="week"?"font-blue":"")+"'>周</a>&nbsp;|&nbsp;"
                +"<a href="+"javascript:getDownloadTop('month');"+" class='"+(time=="month"?"font-blue":"")+"'>月</a>&nbsp;|&nbsp;"
                +"<a href="+"javascript:getViewClickTop('week');"+">最热浏览</a></li>";
            if(rps!=null&&rps.presult.list.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+="<li><span class='f_right'>"+itm.clicks+"次</span><a target='_blank' href='resource?m=todetail&resid="+itm.resid+"'>";
                    html+=(itm.resname.length>10?itm.resname.substring(0,8)+"....":itm.resname)+"</a></li>";
                });
                html+="<li class='t_r'><a href='resource?m=toresourcetop&topType=DOWNLOADNUM&timeType="+time+"' target='_blank' title='more' class='ico_more'></a></li>";
            }else{
                html+="<li>暂无数据！</li>";
            }
            $("#ulDownloadTop").html(html);
        }
    });
}

function getPublicUserTop(time){
    $("#ulPublicUserTop").html("<li><img src='img/validateload.gif'/> 数据加载中……</li>");
    $.ajax({
        url:'resource?m=ajaxUserList',
        type:'POST',
        data:{'pageResult.orderBy':'RESNUM',
            'pageResult.pageSize':10,
            timerange:time},
        dataType:'json',
        error:function(){alert("网络异常")},
        success:function(rps){
            var html="";
            html+="<li><p class='p_lr_15 t_r'>"
                +"<a href="+"javascript:getPublicUserTop('day');"+" class='"+(time=="day"?"font-blue1":"")+"'>日</a>|"
                +"<a href="+"javascript:getPublicUserTop('week');"+" class='"+(time=="week"?"font-blue1":"")+"'>周</a>|"
                +"<a href="+"javascript:getPublicUserTop('month');"+" class='"+(time=="month"?"font-blue1":"")+"'>月</a>&nbsp;&nbsp;"
                +"<a href='resource?m=topublicusertop'>更多</a></p></li>";
            if(rps!=null&&rps.presult.list.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+="<li><a target='_blank' title='"+itm.username+"' href='resource?m=toteacherreslist&userid="+itm.userid+"'>"+ itm.username+"</a>";
                    html+="<span style='float:right;'>"+itm.resnum+"</span>";
                    html+="</li>";
                });
            }else{
                html+="<li>暂无数据！</li>";
            }
            $("#ulPublicUserTop").html(html);
        }
    });
}

function cutoverDiv(div1,div2){
    $("#"+div1).show();
    $("#"+div2).hide();
    $("#li_"+div1).attr("class","crumb");
    $("#li_"+div2).attr("class","");
}

function doSearch(){
    var searchType=$("#searchType").val();
    if(searchType==1){
        var url="resource?m=resList&srhValue="+$("#searchValue").val();
        url=encodeURI(encodeURI(url));
        window.open(url);
    }
    if(searchType==2){
        var url="resource?m=toteacherreslist&srhValue="+$("#searchValue").val();
        url=encodeURI(encodeURI(url));
        window.open(url);
    }
    if(searchType==3){
        var url="resource?m=courseList&srhValue="+$("#searchValue").val();
        url=encodeURI(encodeURI(url));
        window.open(url);
    }
}

function beforeVideoAjaxList(p){
    var param = {};
    param.type ="video";
    p.setPostParams(param);
}

function beforeOtherAjaxList(p){
    var param = {};
    param.subject = subject;
    p.setPostParams(param);
}

function beforeDocAjaxList(p){
    var param = {};
    param.type ="doc";
    p.setPostParams(param);
}

function afterVideoAjaxList(rps){
    var html="";
    if(rps.type=="success"){
        if(rps!=null&&rps.presult.list.length>0){
            $.each(rps.objList,function(idx,itm){
                html+="<li><p class='one'>";
                html+="<a href='resource?m=todetail&resid="+itm.resid+"' target='_blank' title='"+itm.resname+"'><img src='"+itm.imagepath+"' width='160' height='90' /></a></p>";
                var name = (itm.resname.length>18?itm.resname.substring(0,18)+"…":itm.resname)+itm.filesuffixname;
                html+="<p class='two'><a href='resource?m=todetail&resid="+itm.resid+"' target='_blank'>"+name+"</a></p></li>";
            });

        }else{
            html="暂无数据！";
        }
        p_exe_video.setPageSize(rps.presult.pageSize);
        p_exe_video.setPageNo(rps.presult.pageNo);
        p_exe_video.setRectotal(rps.presult.recTotal);
        p_exe_video.setPagetotal(rps.presult.pageTotal);
        p_exe_video.Refresh();

        $("#p_exe_video_lastcontrol").hide();
    }else{
        html="页面错误，无法获取数据！";
    }
    $("#excellentRes_Video").html(html);
}

function afterDocAjaxList(rps){
    var html="";
    if(rps.type=="success"){
        if(rps!=null&&rps.presult.list.length>0){
            $.each(rps.objList,function(idx,itm){
                html+="<li><b>"+itm.ctimeString.substring(0,10)+"</b><b>" ;
                if(typeof(itm.userref)=="undefined"||itm.userref.length<1)
                    html+="<a href='resource?m=toteacherreslist&srhValue="+encodeURI(encodeURI(itm.username))+"' target='_blank'>";
                else
                    html+="<a href='resource?m=toteacherreslist&userid="+itm.userref+"' target='_blank'>";
                html+=(itm.username.length>3?(itm.username.substring(0,3)+"..."):itm.username)+"</a></b>";
                html+="<a title='"+itm.resname+"' href='resource?m=todetail&resid="+itm.resid+"' target='_blank'>";
                if(itm.filesuffixname==".doc"||itm.filesuffixname==".docx")
                    html+="<span class='ico_doc1'></span>";
                else if(itm.filesuffixname==".xls"||itm.filesuffixname==".xlsx")
                    html+="<span class='ico_xls1'></span>";
                else if(itm.filesuffixname==".ppt"||itm.filesuffixname==".pptx")
                    html+="<span class='ico_ppt1'></span>";
                else if(itm.filesuffixname==".swf")
                    html+="<span class='ico_swf1'></span>";
                else if(itm.filesuffixname==".pdf")
                    html+="<span class='ico_pdf1'></span>";
                else if(itm.filesuffixname==".vsd")
                    html+="<span class='ico_vsd1'></span>";
                else if(itm.filesuffixname==".rtf")
                    html+="<span class='ico_rtf1'></span>";
                else if(itm.filesuffixname==".txt")
                    html+="<span class='ico_txt1'></span>";
                else if(audiosuffix.indexOf(itm.filesuffixname)!=-1)
                    html+="<span class='ico_mp31'></span>";
                else if(videosuffix.indexOf(itm.filesuffixname)!=-1)
                    html+="<span class='ico_mp41'></span>";
                else if(imagesuffix.indexOf(itm.filesuffixname)!=-1)
                    html+="<span class='ico_jpg1'></span>";
                else
                    html+="<span class='ico_other2'></span>";
                var name = (itm.resname.length>38?itm.resname.substring(0,38)+"…":itm.resname)+itm.filesuffixname;
                html+=name+"</a></li>";
            });
        }else{
            html+="<li>暂无数据！</li>";
        }
        p_exe_doc.setPageSize(rps.presult.pageSize);
        p_exe_doc.setPageNo(rps.presult.pageNo);
        p_exe_doc.setRectotal(rps.presult.recTotal);
        p_exe_doc.setPagetotal(rps.presult.pageTotal);
        p_exe_doc.Refresh();
        $("#p_exe_doc_lastcontrol").hide();
    }else{
        html+="<li>页面错误，无法获取数据！</li>";
    }
    $("#excellentRes_Doc").html(html);
}

function afterOtherAjaxList(rps){
    var html="";
    if(rps.type=="success"){
        if(rps!=null&&rps.presult.list.length>0){
            $.each(rps.objList,function(idx,itm){
                html+="<li";
                if(idx%2==1)
                    html+=" class='right'";
                html+="><s>"+itm.ctimeString.substring(0,10)+"</s><b><a href='resource?m=toteacherreslist&userid="+itm.userref+"' target='_blank'>"+(itm.username.length>3?(itm.username.substring(0,3)+"..."):itm.username)+"</a></b>";
                var name = (itm.resname.length>11?itm.resname.substring(0,11)+"...":itm.resname)
                +(itm.filename.substring(itm.filename.lastIndexOf("."),itm.filename.length));
                html+="<a title='"+name+"' href='resource?m=todetail&resid="+itm.resid+"' target='_blank'>"+name+"</a></li>";
            });
        }else{
            html+="<li>暂无数据！</li>";
        }
        p_exe_other.setPageSize(rps.presult.pageSize);
        p_exe_other.setPageNo(rps.presult.pageNo);
        p_exe_other.setRectotal(rps.presult.recTotal);
        p_exe_other.setPagetotal(rps.presult.pageTotal);
        p_exe_other.Refresh();
    }else{
        html+="<li>页面错误，无法获取数据！</li>";
    }
    $("#excellentRes_Other").html(html);
}

function cutoverExcellentRes(n){
    switch (n){
    case 1:
        $("#excellentRes_Video").show();
        $("#excellentRes_Doc").hide();
        //$("#excellentRes_Other_Div").hide();
        $("#exe_video").attr("class","crumb");
        $("#exe_doc").attr("class","");
        //$("#exe_other").attr("class","");
        $("#pageVaddress").show();
        $("#pageDaddress").hide();
        //$("#pageOaddress").hide();
        break;
    case 2:
        $("#excellentRes_Video").hide();
        $("#excellentRes_Doc").show();
        //$("#excellentRes_Other_Div").hide();
        $("#exe_video").attr("class","");
        $("#exe_doc").attr("class","crumb");
        //$("#exe_other").attr("class","");
        $("#pageVaddress").hide();
        $("#pageDaddress").show();
        //$("#pageOaddress").hide();
        break;
    case 3:
        $("#excellentRes_Video").hide();
        $("#excellentRes_Doc").hide();
        //$("#excellentRes_Other_Div").show();
        $("#exe_video").attr("class","");
        $("#exe_doc").attr("class","");
        //$("#exe_other").attr("class","crumb");
        $("#pageVaddress").hide();
        $("#pageDaddress").hide();
        //$("#pageOaddress").show();
        break;
    }
}
