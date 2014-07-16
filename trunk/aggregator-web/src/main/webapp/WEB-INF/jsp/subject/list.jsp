<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp" %> 
<head>
<script type="text/javascript">
  function showUpdateDiv(nid,sname){
	  var name=sname;
	  $("#updateSubjectName").val(name);
	  $("#udtSubjectName").html(name);
	  $("#subjectid").val(nid);
	  showModel("update");
  }
	
	
  function upd(ref,inputObj){
      if($(inputObj).val().Trim().length<1)
      {
          alert("学科名称不能为空，请填写");
          inputObj.focus();
          return;
      }
      $.ajax({
          url:'subject?m=checkname',
          type:'post',
          dataType:'json',
          data:{subjectname:inputObj.value},
          error:function(){alert("网络异常!");},
          success:function(rps){
              if(rps.type=='success'){
                  if($(inputObj).val().Trim().length>0){
                      var name = $(inputObj).val().Trim();
                      $.ajax({
                          url:'subject?m=update',
                          data:{
                              subjectid : ref,
                              subjectname : name
                          },
                          type:'post'
                      });
                  }else{
                      alert("请填写学科名称");
                      inputObj.focus();
                  }
              }else{
                  $(inputObj).val("");
                  alert(rps.msg);
                  inputObj.focus();
              }

          }
      });

  }

//添加学科
function addli(){
    var subLiLength=$("#subject li").length;
    var n = subLiLength+1;
    var txt = '<li id="li'+n+'"><input id="add'+n+'" onblur="ajaxSubmit(this)"  name="add'+n+'" type="text" value="" /><a href="javascript:delSubject(\'li'+n+'\')"><span title="删除"></span></a></li>';
    var subLiLength=$("#subject li").length;
    $("#subject").append(txt);
    $("#add"+n)[0].focus();
	
}
//删除新添加的li标签
function delSubject(id){
    if(!confirm('是否要删除？'))
        return;
    $("#subject li[id='"+id+"']").remove();
}
function del(id){
    if(!confirm('是否要删除学科？'))
        return;
    window.location.href="subject?m=del&subjectid="+id;
}

//动态提交新建学科
function ajaxSubmit(obj){
    if($(obj).val().Trim().length<1)
    {
        alert("学科名称不能为空，请填写");
        obj.focus();
        return;
    }
    if($(obj).val().Trim().length>6)
    {
        alert("学科名称长度超出限制，请填写不超过六个字的名称");
        obj.focus();
        return;
    }
    $.ajax({
    url:'subject?m=checkname',
    type:'post',
    dataType:'json',
    data:{subjectname:obj.value},
    error:function(){alert("网络异常!");},
    success:function(rps){
        if(rps.type=='success'){
            if($(obj).val().Trim().length>0){
                $("#subjectname").val($(obj).val().Trim());
                document.forms['formAdd'].submit();
            }else{

                alert("请填写学科名称");
                obj.focus();
            }
        }else{
            $(obj).val("");
            alert(rps.msg);
            obj.focus();
        }

        }
    });
}

  //指定教师
  function selTeacher(subjectid){
  	if(typeof(subjectid)=="undefined" || isNaN(subjectid)){
  		alert("系统未获取到学科标识!请刷新后重试!"); 
  		return; 
  	}	 
  	var param = "dialogWidth:500px;dialogHeight:500px;status:no;location:no";
  	var returnValue=window.showModalDialog("teacher?m=toSetTeacherCourse",param);
  	if(returnValue==null||returnValue.Trim().length<1){  
  		alert("您未选择用户或未提交!"); 
  		return;
  	}
  	var uidarr = returnValue;  
  	if(uidarr.length<1){  
  		alert("系统未获取到用户标识!请刷新后重试!");  
  		return;
  	}
  	$.ajax({ 
  		url:'subjectuser?m=setTeaSubject', 
  		data:{ 
  			subjectid : subjectid,
  			useridArray : uidarr 
  		}, 
  		type:'post', 
  		dataType:'json',
  		error:function(){alert("网络异常!");},
  		success:function(rps){
  			if(rps.type=='error'){
  				alert(rps.msg);
  				return;
  			}else
  				alert(rps.msg);
  		}
  	});
  }
</script>
</head>
  <body>
  <%@include file="/util/head.jsp" %>
  
  <%@include file="/util/nav-base.jsp" %>

				<div id="nav">
    <ul>
     <li><a href="user?m=list">用户管理</a></li>
      <li><a href="cls?m=list">组织管理</a></li>
      <li class="crumb"><a href="term?m=list">系统设置</a></li>
    </ul>
</div>
				<div class="content">
    <div class="contentR public_input">
    <h4 class="m_t_15">固定学科</h4>
    <div class="jcpt_xtsz_xueke">
				<ul class="public_list2">
					<c:if test="${!empty subjectList}">
						<c:forEach var ="sub" items="${subjectList}">
							<c:if test="${sub.subjecttype==1}">
								<li><input type="text" value="${sub.subjectname} " disabled="disabled"/></li>
							</c:if>
						</c:forEach>
					</c:if>
				</ul>
				</div>
			    <h6></h6>
			    <h4 class="m_t_15">自定义学科</h4>
			    <div class="jcpt_xtsz_xueke">
			 <p><a href="javascript:addli()" class="an_small m_t_5">新建学科</a></p>
				<ul class="public_list2" id="subject">
					<c:if test="${!empty subjectList}">
						<c:forEach var ="sub" items="${subjectList}">
							<c:if test="${sub.subjecttype==2}">
								<li><input onblur="upd('${sub.subjectid }',this)" type="text" value="${sub.subjectname} " /><a href="javascript:del('${sub.subjectid }')"><span title="删除"></span></a></li>
							</c:if>
						</c:forEach>
					</c:if>
				</ul>
			</div>
			</div>
			 <div class="contentL">
      <ul>
        <li><a href="term?m=list">学年管理</a></li>
        <li class="crumb"><a href="subject?m=list">学科管理</a></li>
        <li><a href="sysm?m=logoconfig">Logo设置</a></li>
      </ul>
    </div>
    <div class="clear"></div>
</div>

<!--<div class="public_windows" id="add" style="display:none;">
  <h3><a href="javascript:closeModel('add')"  title="关闭">新建学科</h3>
  <div class="jcpt_yfgl_float02">  
  <form id="formAdd" name="formAdd" method="post" action="subject?m=add">
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab3">
    <col class="w100"/>
    <col class="w320"/>
    <tr>
      <td>学科名称：</td>
      <td><input name="subjectname" type="text" class="public_input w200" /></td>
    </tr>
  </table>
  <p class="p_t_10 t_c"><a class="an_gray"  href="javascript:document.forms['formAdd'].submit();">添加</a>
  <a class="an_gray"  href="javascript:closeModel('add')">取消</a></p>
  </form>
  </div>
</div>
--><div class="public_windows" style="display:none;" id="add">
<h3><a href="javascript:closeModel('add')"  title="关闭"></a>新建学科</h3>
  <div class="jcpt_yfgl_float02">
  <form id="formAdd" name="formAdd" method="post" action="subject?m=add">
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab3">
  <col class="w100" />
  <col class="w320" />
     <tr>
       <td>学科名称：</td>
       <td><input id="subjectname" name="subjectname" type="text" class="public_input w200" /></td>
     </tr>
      <tr>
        
        <td colspan="2"><p><a class="an_gray"  href="javascript:document.forms['formAdd'].submit();">添加</a>
  <a class="an_gray"  href="javascript:closeModel('add')">取消</a></p></td>
      </tr>
     
    </table>
    </form>
    </div>
</div>

  	<%@include file="/util/foot.jsp" %>
  </body>

