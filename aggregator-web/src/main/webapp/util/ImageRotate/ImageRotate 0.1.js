
jQuery.iImageRotate = {
	build : function(user_options)
	{
		var user_options;
		var defaults = {
			show_captions: true,
			slide_enabled: true,
			auto_play: false,
			show_prev_next: true,
			slide_speed: 5000,
			thumb_width: 50,
			thumb_height: 42,
            basePath:'',
            show_auto_play:false
		};

		return $(this).each(
			function() {
				//bring in options
				var options = $.extend(defaults, user_options);
				// grab our images
				var $images = $(this).children('li').children('img');
				//hide the images so the user doesn't see crap
				$images.fadeOut(1);
				
				//save our list for future ref
				var $ulist = $(this);
				$images.each(LoadImages);
				//start building structure
				$(this).before("<div class='rotate_main'></div>");
                $(".rotate_main").css({"height":(findDimensions().height-100)+"px","text-align":"center"});
				// houses eveything about the UL
				var $main_div = $(this).prev(".rotate_main");
				
				//add in slideshow elements when appropriate
				if(options.slide_enabled){
					$main_div.append("<div class='rotate_play'></div>");
					var $play_div = $(this).prev(".rotate_main").children(".rotate_play");
					$play_div.html("<a class='rotate_play_button'><img src='' alt='Play'></a><a class='rotate_stop_button'><img src='' alt='Stop'></a>");
					$play_div.fadeOut(1);
					var $play_anchor = $play_div.children('a:first');
					var $stop_anchor = $play_div.children('a:last');
				}
                if(options.show_auto_play){
                    $(".rotate_play").show();
                }else
                    $(".rotate_play").remove();
				//this div is used to make image and caption fade together
				$main_div.append("<div class='rotate_subdiv'></div>");
				var $sub_div = $main_div.children(".rotate_subdiv");
				
				//the main image we'll be using to load
				$sub_div.append("<img />");
				var $main_img = $sub_div.children("img");
				
				//create the caption div when appropriate
				if(options.show_captions){
					$sub_div.append("<div class='rotate_caption'></div>");
					var $caption_div = $sub_div.children(".rotate_caption");
				}
				
				//navigation div ALWAYS gets created, its refrenced a lot				
				$(this).after("<div class='rotate_navigation'></div>");
				var $navigation_div = $(this).next(".rotate_navigation");
				//fill in sub elements
				$navigation_div.prepend("<a>Previous</a> :: <a>Next</a>");
				var $previous_image_anchor = $navigation_div.children('a:first');
				var $next_image_anchor = $navigation_div.children('a:last');
				
				//hide the navigation if the user doesn't want it
				if(!options.show_prev_next){
					$navigation_div.css("display","none");
				}
				
				//$playing triggers the loop for the slideshow
				var $playing = options.auto_play;
				
				$main_img.wrap("<a></a>");
				var $main_link = $main_img.parent("a");
				
				function LoadImages(){
					$(this).bind("load", function(){
					//had to make a seperate function so that the thumbnails wouldn't have problems
					//from beings resized before loaded, thus not h/w

					var $w = $(this).width();
					var $h = $(this).height();
					if($w===0){$w = $(this).attr("width");}
					if($h===0){$h = $(this).attr("height");}
					//grab a ratio for image to user defined settings
					var $rw = options.thumb_width/$w;
					var $rh = options.thumb_height/$h;

					//determine which has the smallest ratio (thus needing
					//to be the side we use to scale so our whole thumb is filled)
					if($rw<$rh){
						//we'll use ratio later to scale and not distort
						var $ratio = $rh;
						var $left = (($w*$ratio-options.thumb_width)/2)*-1;
						$left = Math.round($left);
						//set images left offset to match
						$(this).css({left:$left});
					}else{
						var $ratio = $rw;
						//you can uncoment this lines to have the vertical picture centered
						//but usually tall photos have the focal point at the top...
						//var $top = (($h*$ratio-options.thumb_height)/2)*-1;
						//var $top = Math.round($top);
						$top = 0;
						$(this).css({top:$top});
					}
					//use those ratios to calculate scale
					var $width = Math.round($w*$ratio);
					var $height = Math.round($h*$ratio);
					$(this).css("position","relative");
//					$(this).width($width).height($height);
					var imgcss={
						width: $width,
						height: $height
					};
					$(this).css(imgcss);

					$(this).fadeTo(250);
					if($(this).hasClass('rotate_first')){
						$(this).trigger("click",["auto"]);
					}
				});
				
				//clone so the on loads will fire correctly
				$(this).clone(true).insertAfter(this);
				
				$(this).remove();
				//reset images to the clones
				$images = $ulist.children('li').children('img');
				}
			function activate(){
				//sets the intial phase for everything
				
				//image_click is controls the fading
				$images.bind("click",image_click);
				//hiding refrence to slide elements if slide is disabled
				if(options.slide_enabled){
					if(options.auto_play){
						$playing = true;
						$play_anchor.hide();
						$stop_anchor.show();
					}else{
						$play_anchor.show();
						$stop_anchor.hide();
					}
				}
				
				//resizes and centers thumbs
				prep_thumbs();
				//previous link to go back an image
				$previous_image_anchor.bind("click",previous_image);
				//ditto for forward, also the item that gets auto clicked for slideshow
				$next_image_anchor.bind("click",next_image);	
			}//end activate function
			
	
			function prep_thumbs(){
					//now we know the first and last images
					$images.filter(":last").addClass("rotate_last");
					$images.filter(":first").addClass("rotate_first");
					//parse images
					$images.each(function(){
											var licss = {
							width: options.thumb_width+"px",
							height: options.thumb_height+"px",
							"list-style": "none",
							overflow: "hidden"
						};
						$(this).parent('li').css(licss);
						$(this).hover(
							function(){$(this).fadeTo(250,1);},
							function(){if(!$(this).hasClass("rotate_selected")){$(this).fadeTo(250);}}
						);
					});

			}//end fix thumbs functions
			function image_click(event, how){
					//catch when user clicks on an image Then cancel current slideshow
					if(how!="auto"){
						if(options.slide_enabled){
							$stop_anchor.hide();
							$play_anchor.show();
							$playing=false;
						}
						$sub_div.stop();
						$sub_div.dequeue();
					}
					//all our image variables
					var $image_source = $(this).attr("rastate-src");
                    var $image_source=options.basePath+"getImage.jsp?path="+$image_source+"&issmail=2";
					var $image_link = $(this).attr("ref");
					var $image_caption = $(this).attr("title");
                    $("#rotateme li").css("border-color","black");
                    $(this).parent().css("border-color","white");
					//fade out the old thumb
					$images.filter(".rotate_selected").fadeTo(250);
					$images.filter(".rotate_selected").removeClass("rotate_selected"); 
					//fade in the new thumb
					$(this).fadeTo(250,1);
					$(this).addClass("rotate_selected");
					//fade the old image out and the new one in
					$sub_div.fadeTo(500,0.05,function(){
						$main_img.attr("src",$image_source);
						$main_link.attr("href", $image_link);
						if(options.show_captions){$caption_div.html($image_caption);}
					});
					$sub_div.fadeTo(800,1,function(){
						if($playing){
							$(this).animate({top:0},options.slide_speed, function(){
								//redudency needed here to catch the user clicking on an image during a change.
								if($playing){$next_image_anchor.trigger("click",["auto"]);}
							});
						}
					});
			}//end image_click function
			
			function next_image(event, how){
				if($images.filter(".rotate_selected").hasClass("rotate_last")){
					$images.filter(":first").trigger("click",how);
				}else{
					$images.filter(".rotate_selected").parent('li').next('li').children('img').trigger("click",how);
				}
			}//end next image function
			
			function previous_image(event, how){
				if($images.filter(".rotate_selected").hasClass("rotate_first")){
					$images.filter(":last").trigger("click",how);
				}else{
					$images.filter(".rotate_selected").parent('li').prev('li').children('img').trigger("click",how);
				}
			}//end previous image function
			
			function play_button(){
				$main_div.hover(
					function(){$play_div.fadeIn(400);},
					function(){$play_div.fadeOut(400);}
				);
				$play_anchor.bind("click", function(){
					$playing = true;
					$next_image_anchor.trigger("click",["auto"]);
					$(this).hide();
					$stop_anchor.show();
				});
				$stop_anchor.bind("click", function(){
					$playing = false;
					$(this).hide();
					$play_anchor.show();
				});
			}
			if(options.slide_enabled){play_button();}
			activate();

		});//end return this.each
	}//end build function
	
	//activate applies the appropriate actions to all the different parts of the structure.
	//and loads the sets the first image
};//end jquery.imageRotate


jQuery.fn.ImageRotate = jQuery.iImageRotate.build;