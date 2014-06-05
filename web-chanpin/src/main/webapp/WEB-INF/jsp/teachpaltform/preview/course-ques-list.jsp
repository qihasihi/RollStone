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
                        //var queslevel=itm.questionlevel==3?'':'<span class="ico44"></span>';
                        var questype='';
                        switch (itm.questiontype){
                            case 1:questype='其他';break;
                            case 2:questype='填空题';break;
                            case 3:questype='单选题';break;
                            case 4:questype='多选题';break;
                        }
                        /*
                         <p class="title"><strong>任务8：单选题</strong></p>
                         <div class="text">
                         <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
                         <col class="w80"/>
                         <col class="w820"/>
                         <tr>
                         <td>题&nbsp;&nbsp;&nbsp;&nbsp;干：</td>
                         <td>《失踪》、《孔乙己》、《药》等小说有一个共同的主题，《失踪》、《孔乙己》、《药》等小说有一个共同的主题即使对中国的批判《药》等小说有一个共同的主题即使对中国的批判<br>
                         <input type="radio" name="radio" id="radio3" value="radio">
                         A. 民族性<br>
                         <input type="radio" name="radio" id="radio3" value="radio">
                         B. 国民性<br>
                         <input type="radio" name="radio" id="radio9" value="radio">
                         C. 传统性<br>
                         <input type="radio" name="radio" id="radio10" value="radio">
                         D. 文化性 </td>
                         </tr>
                         <tr>
                         <td>正确答案：</td>
                         <td>A</td>
                         </tr>
                         <tr>
                         <td>答案解晰：</td>
                         <td>《失踪》、《孔乙己》、《药》等小说有一个共同的主题，《失踪》、《孔乙己》、《药》等小说有一个共同的主题即使对中国的批判《药》等小说有一个共同的主题即使对中国的批判</td>
                         </tr>
                         </table>
                         </div>
                         */

                        if(itm.questiontype==2){
                            content=replaceAll(content.toLowerCase(),'<span name="fillbank"></span>','_____');
                        }

                        htm+='<p class="title"><strong>'+questype+'</strong></p>';
                        htm+='<div class="text">';
                                htm+='<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">';
                                htm+='<col class="w80"/>';
                                htm+='<col class="w820"/>';
                                htm+='<tr>';
                                htm+='<td>题&nbsp;&nbsp;&nbsp;&nbsp;干：</td>';
                                htm+='<td>'+replaceAll(replaceAll(replaceAll(content.toLowerCase(),"<p>",""),"</p>",""),"<br>","")+'<br>';
                                if(itm.questionOptionList!=null&&itm.questionOptionList.length>0){
                                    $.each(itm.questionOptionList,function(idx,im){
                                        var type=itm.questiontype==3?"radio":"checkbox";
                                        htm+='<input disabled type="'+type+'"/>';
                                        htm+=im.optiontype+".&nbsp;";
                                        htm+=replaceAll(replaceAll(replaceAll(im.content.toLowerCase(),"<p>",""),"</p>",""),"<br>","");
                                        if(im.isright==1)
                                            htm+='<span class="ico12"></span>';
                                        htm+='<br>';
                                    });
                                }
                                htm+='</td>';
                                htm+='</tr>';
                               /* htm+='<tr>';
                                htm+='<td>正确答案：</td>';
                                htm+='<td>A</td>';
                                htm+='</tr>';*/
                                htm+='<tr>';
                                htm+='<td>答案解析：</td>';
                                htm+='<td>'+itm.analysis+'</td>';
                                htm+='</tr>';
                                htm+='</table>';
                        htm+='</div>';
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
        <div class="jxxt_trzt_rw"  id="tbl_body_data">

        </div>

        <form name="page1form" id="page1form" action="" method="post">
            <div id="page1address" ></div>
        </form>
    </div>
</html>
