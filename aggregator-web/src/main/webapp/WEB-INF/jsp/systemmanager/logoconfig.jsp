<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp"%>
<HTML>		
<HEAD>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="imagetoolbar" content="no">
<script src="<%=basePath %>js/common/ajaxfileupload.js"></script>
<script src="<%=basePath %>js/common/jquery.form.pack.2.24.js"></script>
<script src="<%=basePath %>js/common/jquery.imgareaselect.js"></script>
<link  rel="stylesheet" type="text/css" href="css/imgareaselect-default.css"/>
</HEAD>
<BODY>
<script type="text/javascript">
	var basePath ="<%=basePath%>";
	var imgwidth = 0;
	var imgheight = 0;
	var imgareaselectObj;
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

        var sz_schoolIdx=$(img).attr("src").indexOf('sz_school');
        var loginSrc_min=$(img).attr("src");
        if(sz_schoolIdx>=0){
            loginSrc_min=loginSrc_min.substring(parseInt(sz_schoolIdx)+10);
        }
		$('input[name="src"]').val(loginSrc_min);
		$('input[name="x1"]').val(selection.x1);
		$('input[name="y1"]').val(selection.y1);
		$('input[name="x2"]').val(selection.x2);
		$('input[name="y2"]').val(selection.y2); 
	 
	}

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
							 fadeSpeed: 200, onSelectChange: preview
						});
                        //imgAreaSelectApi.setSelection(0,0,320,96);
                       // imgAreaSelectApi.update();
//                        $("#myimage").load(function(){
//                            imgAreaSelectApi.setSelection(0,0,320,96);
//                            imgAreaSelectApi.update();
//                        });
						 
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
	   		//alert("请选择截图区域，默认将按全图比例缩放！");
            if(!confirm('请选择截图区域，默认将按全图比例缩放！'))
                return;
	   	}
             if(src.length<1){
                 src= $("#previewimg").attr("src");
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
                self.reload();  //重新刷新
		    } 
		}
	});  
}
</script>

  	<%@include file="/util/head.jsp" %>
  <%@include file="/util/nav-base.jsp" %>
<div id="nav">
    <ul>
        <li><a href="user?m=list">用户管理</a></li>
        <li><a href="cls?m=list">组织管理</a></li>
        <li class="crumb"><a href="sysm?m=logoconfig">系统设置</a></li>
    </ul>
</div>
  
<div class="content" >
  <div class="contentT">
    <div class="contentR public_input" >
     <div class="jcpt_xtsz_logo" >
      <p><input type="file" id="upload" name="upload" class="w320">&nbsp;<a href="javascript:uploadImg();"  class="an_lightblue">上传</a></p>
      <div id="cuthear_div" style="display:none;">	 
      <input type="hidden" name="src" value="" />       
  <input type="hidden" name="x1" value="" />	   
  <input type="hidden" name="y1" value="" />   
  <input type="hidden" name="x2" value=""  />
  <input type="hidden" name="y2" value="" />     
	          <div class="right">
		        <p><strong>预览效果：</strong>253×64</p>
		      
			        <div id="preview" style="width: 253px; height: 64px; overflow: hidden;">
			        	<img id="previewimg" name="myimage" src="" width="253" height="64">
			          </div>
		        
		        <p class="t_c"><a href="javascript:subheadcut();"  class="an_small">保 存</a></p>
	    	</div>
	      <div class="left"  id="myPreview" ><img id="myimage" src="" width="352" height="127"></div>
      </div>
     </div>
    </div>

    <div class="contentL">
      <ul>
          <%if(visible){%>
        <li><a href="term?m=list">学年管理</a></li>
        <li><a href="subject?m=list">学科管理</a></li>
          <%}%>
        <li class="crumb"><a href="sysm?m=logoconfig">Logo设置</a></li>
      </ul>
    </div>
    <div class="clear"></div>
</div>
<div class="contentB"></div>
</div>
<%@include file="/util/foot.jsp" %>
</BODY>
</HTML>	