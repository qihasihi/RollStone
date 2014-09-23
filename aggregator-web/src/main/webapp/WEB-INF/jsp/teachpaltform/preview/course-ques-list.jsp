<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<script type="text/javascript">
			var courseid="${courseid}";
			var p1;
            var quesType=0;
			$(function(){
				p1=new PageControl({
						post_url:'question?m=questionAjaxList',
						page_id:'page1',
						page_control_name:"p1",		//分页变量空间的对象名称
						post_form:document.page1form,		//form
						gender_address_id:'page1address',		//显示的区域
						http_free_operate_handler:validateParam,		//执行查询前操作的内容
						http_operate_handler:questionReturn,	//执行成功后返回方法
						return_type:'json',								//放回的值类型
						page_no:1,					//当前的页数
						page_size:10,				//当前页面显示的数量
						rectotal:0,				//一共多少
						pagetotal:1,
						operate_id:"tbl_body_data" 
					});
					pageGo('p1');
			});



            function validateParam(tObj){
                var param=new Object();
                param.courseid=courseid;
                //var quesType=$("input[name='rdo_ques_type']:checked").val();
                //if(quesType.length>0)
                param.questype=quesType;
                tObj.setPostParams(param);
            }

            function questionReturn(rps){
                if(rps.type=="error"){
                    alert(rps.msg);return;
                }else{
                    var htm='';
                    $.each(rps.objList,function(idx,itm){
                        var content=itm.content;
                        var queslevel=itm.questionlevel==3?'':'<span class="ico44"></span>';
                        var questype=itm.questiontypename;
                        if(itm.questiontype==2){
                            content=replaceAll(content.toLowerCase(),'<span name="fillbank"></span>','______');
                        }
                        htm+='<table class="public_tab1 w940" border="0" cellspacing="0" cellpadding="0">';
                        htm+='<tr id="tr_'+itm.questionid+'"';
                        if(idx%2==1)
                            htm+=' class="trbg2"';
                        htm+='>';
                        htm+='<td><span class="bg">'+questype+'</span>';

                        if(itm.extension=='4'){
                            htm+='<div  class="p_t_10" id="sp_mp3_'+itm.questionid+'" ></div>'
                            htm+='<script type="text/javascript">';
                            htm+='playSound(\'play\',\''+ques_mp3_path+itm.questionid+'/001.mp3\',270,22,\'sp_mp3_'+itm.questionid+'\',false)';
                            htm+='<\/script>';
                        }
                        if(itm.extension!=4)
                            htm+=content;
                        if(typeof itm.questionOptionList!='undefined'&&itm.questionOptionList.length>0&&itm.questiontype!=1){
                            htm+='<table border="0" cellpadding="0" cellspacing="0" >';
                            htm+='<col class="w30"/>';
                            htm+='<col class="910"/>';
                            $.each(itm.questionOptionList,function(ix,im){
                                htm+='<tr>';
                                var type=itm.questiontype==3?"radio":"checkbox";
                                htm+='<th><input  type="'+type+'"/></th>';
                                htm+='<td>'+im.optiontype+".&nbsp;";
                                htm+=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                                if(im.isright==1)
                                    htm+='<span class="ico12"></span>';
                                htm+='</td>';
                                htm+='</tr>';
                            });
                            htm+='</table>';
                        }

                        if(typeof itm.questionTeam!='undefined'&&itm.questionTeam.length>0&&itm.extension!='5'){
                            $.each(itm.questionTeam,function(ix,m){
                                htm+='<tr>';
                               // htm+='<td>&nbsp;</td>';
                                htm+='<td>'+(ix+1)+'.'+m.content;
                                if(typeof m.questionOptionList!='undefined'&&m.questionOptionList.length>0){
                                    htm+='<table border="0" cellpadding="0" cellspacing="0" >';
                                    htm+='<col class="w30"/>';
                                    htm+='<col class="910"/>';
                                    $.each(m.questionOptionList,function(ix,im){
                                        htm+='<tr>';
                                        var type=im.questiontype==3||im.questiontype==7?"radio":"checkbox";
                                        htm+='<th><input  type="'+type+'"/></th>';
                                        htm+='<td>'+im.optiontype+".&nbsp;";
                                        htm+=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                                        if(im.isright==1)
                                            htm+='<span class="ico12"></span>';
                                        htm+='</td>';
                                        htm+='</tr>';
                                    });
                                    htm+='</table>';
                                }
                                htm+='</td>';
                                htm+='</tr>';



                                htm+='<tr>';
                                htm+='<td>';
                                if(m.questiontype<3){
                                    if(m.questiontype==1&& m.questionid>0){
                                        if(typeof m.questionOptionList!='undefined'&&m.questionOptionList.length>0){
                                            htm+='<p><strong>正确答案：</strong>'+m.questionOptionList[0].content+'</p>';
                                        }
                                    }else
                                        htm+='<p><strong>正确答案：</strong>'+m.correctanswer+'</p>';
                                }
                                htm+='<p><strong>答案解析：</strong>'+m.analysis+'</p>';
                                htm+='</td>';
                                htm+='</tr>';
                            });
                        }
                        htm+='</td>';
                        htm+='</tr>';

                        if(itm.questiontype<6){
                            htm+='<tr>';
                            htm+='<td>';
                            if(itm.questiontype!=3&&itm.questiontype!=4){
                                htm+='<p><strong>正确答案：</strong>';
                                if(itm.questiontype<3){
                                    if(itm.questiontype==1&& itm.questionid>0){
                                        if(typeof itm.questionOptionList!='undefined'&&itm.questionOptionList.length>0){
                                            htm+=itm.questionOptionList[0].content+'</p>';
                                        }
                                    }else
                                        htm+=itm.correctanswer;
                                }


                                if(itm.questiontype==3||itm.questiontype==4){
                                    $.each(itm.questionOptionList,function(ix,im){
                                        if(im.isright==1)
                                            htm+=im.optiontype+'&nbsp;'
                                    });
                                }
                                htm+='</p>';
                            }

                            htm+='<p><strong>答案解析：</strong>'+itm.analysis+'</p>';
                            htm+='</td>';
                            htm+='</tr>';
                        }


                        if(itm.questionTeam.length>0&&itm.extension==5){
                            htm+='<tr>';
                            htm+='<td><p><strong>正确答案及答案解析：</strong></p>';
                            $.each(itm.questionTeam,function(ix,m){
                                htm+='<p><span   class="font-blue">'+(ix+1)+'</span>.';
                                $.each(m.questionOptionList,function(x,im){
                                    if(im.isright==1)
                                        htm+=im.optiontype;
                                });
                                htm+='&nbsp;&nbsp;'+ m.analysis;
                                htm+='</p>';
                            });
                            htm+='</td>';
                            htm+='</tr>';
                        }
                        htm+='</table>';
                    })
                    $("#tbl_body_data").html(htm);
                    $("#sp_ques_count").html('');
                    //翻页信息
                    if (typeof (p1) != "undefined" && typeof (p1) == "object") {
                        p1.setPagetotal(rps.presult.pageTotal);
                        p1.setRectotal(rps.presult.recTotal);
                        p1.setPageSize(rps.presult.pageSize);
                        p1.setPageNo(rps.presult.pageNo);
                        p1.Refresh();
                        $("#sp_ques_count").html(rps.presult.recTotal);
                    }
                }
            }

        </script>
	</head>
    <div class="content2">
        <p class="jxxt_trzt_number"><strong>试题数：</strong><span id="sp_ques_count"></span></p>
        <div class="jxxt_zhuanti_shijuan_add font-black public_input"  id="tbl_body_data">

        </div>

        <form name="page1form" id="page1form" action="" method="post">
            <div id="page1address" ></div>
        </form>
    </div>
</html>
