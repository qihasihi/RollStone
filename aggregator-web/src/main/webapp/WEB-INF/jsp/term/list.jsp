<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp" %>
  <head>
  	<script type="text/javascript" src="<%=basePath %>js/term.js"></script>
  	<script type="text/javascript">
  	
  	/*********功能权限控制**********/ 
    $(function(){
    	//$("#add_btn").hide();
    	//$("#sel_btn").hide();
    	
    	//if(isSelect){ $("#sel_btn").show();}
    	//if(isAdd){ $("#add_btn").show();}
    }); 
    var isSelect = true,isUpdate=true,isSetRoleUser=true,isDelete=true;  
  	var p1,uploadControl; 
	$(function(){
	 	//if(isSelect){
			//翻页控件 
				
				$.ajax({
					url:'term?m=ajaxlist',
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
						afterajaxList(rps);
					}
				});
			//}
	});
	
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
    <br>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
        <input type="hidden" id="termyear"/>
      <colgroup span="5" class="w140"></colgroup>
      <tbody id="datatbl" >
      <tr>
        <th rowspan="2">学年</th>
        <th colspan="2">第一学期</th>
        <th colspan="2">第二学期</th>
        </tr>
      <tr>
        <th><span>开始时间</span></th>
        <th><span>结束时间</span></th>
        <th><span>开始时间</span></th>
        <th><span>结束时间</span></th>
        </tr>
      <tr>
        <td>2014-2015学年</td>
        <td><input name="textfield28" type="text" value="2013-11-18"  class="w80"/></td>
        <td><input name="textfield" type="text" value="2013-11-18"  class="w80"/></td>
        <td>2015-09-01</td>
        <td>2015-07-15</td>
        </tr>
      <tr class="trbg1">
        <td>2014-2015学年</td>
        <td><input  name="textfield28" type="text" value="2013-11-18"  class="w80"/></td>
        <td><input name="textfield" type="text" value="2013-11-18"  class="w80"/></td>
        <td>2015-09-01</td>
        <td>2015-07-15</td>
        </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
      <tr class="trbg1">
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        </tr>
        </tbody>
    </table>
    </div>
    <div class="contentL">
      <ul>
        <li class="crumb" ><a href="term?m=list">学年管理</a></li>
        <li><a href="subject?m=list">学科管理</a></li>
        <li><a href="sysm?m=logoconfig">Logo设置</a></li>
      </ul>
    </div>
    <div class="clear"></div>
</div>

  	 <%@include file="/util/foot.jsp" %>
  </body>

