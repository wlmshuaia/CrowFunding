//数组添加求和函数
Array.prototype.sum = function () {
 for (var sum = i = 0; i < this.length; i++)
 	sum += parseInt(this[i]);
      return sum;
};
//////////////////////////////////////上传图片
function uploadBtn(){$('#upfile').click();}
function uploadImg(obj) {
	var files = obj.files;
	window.URL = window.URL || window.webkitURL;
	img = new Image();
	if(window.URL){
	img.src = window.URL.createObjectURL(files[0]); //创建一个object URL
	c_default_img.src=img.src;
	}
}
//显示隐藏栏目
$(function(){							
	//跳转到创作页面
	$("div.c_footer_menu h2.c_second_title a").on("tap",function(){
		$("div.c_footer_menu,div.c_editable").hide();
		$("div.c_choose_imgs,div.c_footer_page").fadeIn();
	})
	//显示隐藏图片
	$("div.c_footer_page table tr td:nth-child(2) a").on("tap",function(){
		$("div.c_choose_imgs").show();
		$("div.c_img_create_box,div.c_images_filter_box,div.c_font_create,div.c_font_default,div.c_font_fami_panel,div.c_input_page_font,div.c_painter,div.c_footer_menu").hide();
	})	
	//点击图片创作区域出现图片工具箱	
	$('.c_default_img').on("touchstart",function(){
		$('div.c_img_create_box,div.c_footer_page').fadeIn();
		$("div.c_choose_imgs,div.c_images_filter_box,div.c_font_create,div.c_font_default,div.c_font_fami_panel,div.c_input_page_font,div.c_painter,div.c_footer_menu,div.c_editable,div.c_shopping").hide();	
		})	
	//图片工具箱返回上一层
	$('div.c_img_tool_back img').on("tap",function(){
		$('div.c_choose_imgs').show();
		$("div.c_img_create_box").hide();
		})		
	//显示隐藏文字
	$("div.c_footer_page table tr td:nth-child(3) a").on("tap",function(){
		$("div.c_font_create,div.c_input_font").show();
		$("div.c_choose_imgs,div.c_img_create_box,div.c_images_filter_box,div.c_font_default,div.c_font_fami_panel,div.c_input_page_font,div.c_painter,div.c_footer_menu").hide();
	})
	$("div.c_input_font").on("touchstart",function(){
		$(this).css({"border":"1px dashed #fff"});
		$("div.c_font_create,div.c_footer_page").fadeIn();
		$("div.c_choose_imgs,div.c_img_create_box,div.c_images_filter_box,div.c_font_default,div.c_input_page_font,div.c_painter,div.c_footer_menu,div.c_font_fami_panel,div.c_editable,div.c_shopping").hide();
	})			
	//文字框获取焦点--出现输入框
	$('div.c_input_font').one("focus",function(){
		$(this).html('');
		$('div.c_extend').attr("contenteditable","true").focus();
		$("div.c_choose_imgs,div.c_img_create_box,div.c_images_filter_box,div.c_font_create,div.c_footer_page,div.c_input_font,div.c_font_default,div.c_saved_wrapper,div.c_painter,div.c_footer_menu").hide();
		$('div.c_input_page_font,div.c_font_fami_panel').show();	
		$('div.c_clothes_box').css({"position":"relative","top":"0px"});
		})
	//文字边框消失
	$('.c_index_clothes').on("click",function(){
		$('div.c_input_font').css({"border":"none"});
	})				
	//文字预设
	$('div.c_font_title a:nth-child(2)').on("tap",function(){
		$("div.c_font_create").hide();
		$('div.c_font_default').fadeIn();	
		})	
	//文字预设返回
	$('div.c_font_default_title img').on("tap",function(){
		$("div.c_font_default").hide();
		$('div.c_font_create').fadeIn();	
		})	
	//文字修改
	$('div.c_font_title a:nth-child(3),div.c_input_font').on("click",function(){
		$('div.c_input_page_font,div.c_font_fami_panel').show();	
		$("div.c_choose_imgs,div.c_footer_page,div.c_img_create_box,div.c_images_filter_box,div.c_font_create,div.c_font_default,div.c_painter,div.c_footer_menu,div.c_saved_wrapper").hide();	
		$('div.c_clothes_box').css({"position":"relative","top":"0px"});
		$('div.c_extend').attr("contenteditable","true").focus();
		})
	//选择字体按钮--字体类型面板隐藏
	var count=0;
	$('div.c_input_font_title a:nth-child(1)').on("tap",function(){
		count+=1;
		if(count%2==1){
			$('div.c_font_fami').fadeIn();
			$('div.c_extend').attr("contenteditable","false");
			$('div.c_extend').focus();
			$(this).html("键盘");
		}
		if(count%2==0){
			$('div.c_extend').attr("contenteditable","true");
			$('div.c_font_fami').hide();
			$(this).html("字体");	
		}
	})
	//选择字体输入框--字体类型面板隐藏
	$('div.c_extend').on("tap",function(){
		$('div.c_font_fami').hide();
		$(this).attr("contenteditable","true").css("color","#000").focus();
		$('div.c_input_font_title a:nth-child(1)').html("字体");
	})
	//选择完成按钮
	$('div.c_input_font_title a:nth-child(2)').on("tap",function(){
		$('div.c_font_create,div.c_footer_page,div.c_input_font').show();
		$("div.c_saved_wrapper").fadeIn();
		$('div.c_choose_imgs,div.c_img_create_box,div.c_images_filter_box,div.c_font_default,div.c_font_fami_panel,div.c_input_page_font,div.c_painter,div.c_footer_menu').hide();
		$('div.c_input_font').html($('div.c_extend').html());
		$('div.c_input_font').attr("contenteditable","false");
		$('div.c_clothes_box').css({"position":"fixed","top":"0px"});
		$('div.c_input_font').panzoom();	
		})
	//显示隐藏涂鸦
	$("div.c_footer_page table tr td:nth-child(4) a").on("tap",function(){
		$("div.c_painter,.c_tuya_img").show();
		$("div.c_choose_imgs,div.c_img_create_box,div.c_images_filter_box,div.c_font_create,div.c_font_default,div.c_font_fami_panel,div.c_input_page_font,div.c_footer_menu").hide();
	})
	$(".c_tuya_img").on("touchstart",function(){
		$("div.c_painter,div.c_footer_page").fadeIn();
		$("div.c_choose_imgs,div.c_img_create_box,div.c_images_filter_box,div.c_font_create,div.c_font_default,div.c_font_fami_panel,div.c_input_page_font,div.c_footer_menu,div.c_editable,div.c_shopping").hide();
	})
	//显示隐藏滤镜
	$("div.c_footer_page table tr td:nth-child(5) a").on("tap",function(){
		$("div.c_images_filter_box").show();
		$("div.c_choose_imgs,div.c_img_create_box,div.c_font_create,div.c_font_default,div.c_font_fami_panel,div.c_input_page_font,div.c_painter,div.c_footer_menu").hide();
	})	
	//返回主菜单
	$('div.c_footer_page img.c_true').on("tap",function(){
		$("div.c_choose_imgs,div.c_img_create_box,div.c_font_create,div.c_font_default,div.c_painter,div.c_images_filter_box,div.c_footer_page,div.c_editable").hide();
		$("div.c_footer_menu").slideDown();
		})
	//清除创作界面
	$('div.c_footer_page img.c_false').on("tap",function(){
		$(".c_default_img,.c_tuya_img,div.c_input_font,div.c_choose_imgs,div.c_img_create_box,div.c_font_create,div.c_font_default,div.c_painter,div.c_images_filter_box,div.c_footer_page,div.c_editable").hide();
		$("div.c_footer_menu").slideDown();
		})		
	//////////////////////////////////////////////////////////////////图像边框背景切换
	//创作页面菜单标题栏背景切换
	$("div.c_footer_page table tr td a").on("click",function(){
		$(this).css({"color":"#007d65"});
		})
	$("div.c_footer_page table tr td a").on("mouseout",function(){
		$(this).css({"color":"rgba(0,0,0,0.8)"});
		})
	//显示隐藏图片边框
	$("div.c_images_display img").on("touchstart",function(){
		$(this).css({"border":"2px solid #fb3475"});
		})
	$("div.c_images_display img").on("mouseleave",function(){
		$(this).css({"border":"none"});
		})
	//显示隐藏文字颜色块边框
	$("div.c_font_color table tr td a").on("touchstart",function(){
		$(this).css({"border":"2px solid #fb3475"});
		})
	$("div.c_font_color table tr td a").on("mouseleave",function(){
		$(this).css({"border":"none"});
		})
	//显示隐藏涂鸦颜色块边框
	$("div.c_painter_menu img").on("touchstart",function(){
		$(this).css({"border":"2px solid #fb3475"});
		})
	$("div.c_painter_menu img").on("mouseleave",function(){
		$(this).css({"border":"none"});
		})		
	//显示隐藏文字预设颜色边框
	$("div.c_font_style div").on("tap",function(){
		$(this).css({"border":"1px solid #fb3475"});
		})
	$("div.c_font_style div").on("mouseout",function(){
		$(this).css({"border":"1px ridge #007d65"});
		})										
	//显示隐藏颜色块边框
	$("div.c_color_block div").on("tap",function(){
		$(this).css("border","1px solid #000");
		})
	$("div.c_color_block div").on("mouseout",function(){
		$(this).css("border","none");
		})
	//切换主服装图片背景颜色
	var oObj=$("div.c_color_block div");
	var oImg=$(".c_index_clothes");
	for(var i=0;i<6;i++){
		var n=0;
		var imgUrl="c_images/c_clothes/c_clothes1.png";
		switch(n){
			case 0:
			oObj.eq(0).on("tap",function(){
				oImg.attr("src",imgUrl);});
			case 1:
			oObj.eq(1).on('tap',function(){
				oImg.attr("src",imgUrl.replace(/1/g,'2'));
			});
			case 2:
			oObj.eq(2).on('tap',function(){
				oImg.attr("src",imgUrl.replace(/1/g,'3'));
			});			
			case 3:
			oObj.eq(3).on('tap',function(){
				oImg.attr("src",imgUrl.replace(/1/g,'4'));
			});			
			case 4:
			oObj.eq(4).on('tap',function(){
				oImg.attr("src",imgUrl.replace(/1/g,'5'));
			});			
			case 5:
			oObj.eq(5).on('tap',function(){
				oImg.attr("src",imgUrl.replace(/1/g,'6'));
			});			
	
		}
	}
	/////////////////////////////////////////////图像操作工具栏	
 	//删除图像
    $('div.c_img_create table tr td:nth-child(1) img').on("tap",function(){
    	$(".c_default_img").hide();
    })	
     //图片重置
    $('div.c_img_tool_back a').on("tap",function(){
    	$(".c_default_img").width(100).height(100);
    	$(".c_default_img").css({"position":"absolute","left":"50%","top":"50%","margin-top":"-50px","margin-bottom":"-50%","-webkit-transform":"rotate(0deg)"});
    })
	//图片旋转
	var angleT =0;
	//左旋
	$('div.c_img_create table tr td:nth-child(4) img').on("click",function(){
		angleT-=2;
		$('.c_default_img').css ('-webkit-transform','rotate('+angleT+'deg)');
	})
	//右旋
	$('div.c_img_create table tr td:nth-child(5) img').on("click",function(){
		angleT+=2;
		$('.c_default_img').css ('-webkit-transform','rotate('+angleT+'deg)');
	})	    
     //图片缩放
    var zoom=0
    $('div.c_img_create table tr td:nth-child(2) img').on("tap",function(){ 
    	 zoom+=1;
		var wPos=$(".c_default_img").width();
    	var hPos=$(".c_default_img").height();    	
		$(".c_default_img").animate({width:wPos+ zoom,height:wPos+ zoom},200);
    })
	$('div.c_img_create table tr td:nth-child(3) img').on("tap",function(){ 
    	zoom+=0;
    	var wPos=$(".c_default_img").width();
    	var hPos=$(".c_default_img").height();
    	$(".c_default_img").animate({width:wPos-zoom,height:wPos-zoom},200);
    	if(wPos<30&&hPos<30){
    		$(".c_default_img").animate({width:"30",height:"30"});
    	} 	
    }) 
	//点击标题交换图案
    var tabArr=[$("div.c_diffpage table tr td:nth-child(2) a"),$("div.c_diffpage table tr td:nth-child(3) a"),$("div.c_diffpage table tr td:nth-child(4) a"),$("div.c_diffpage table tr td:nth-child(5) a"),$("div.c_diffpage table tr td:nth-child(6) a"),$("div.c_diffpage table tr td:nth-child(7) a")];
	var diffArr=[$("#c_img_list1"),$("#c_img_list2"),$("#c_img_list3"),$("#c_img_list4"),$("#c_img_list5"),$("#c_img_list6")];
	var n=0;
	switch(n){
		case 0:switchImage(0);
		case 1:switchImage(1);
		case 2:switchImage(2);
		case 3:switchImage(3);		
		case 4:switchImage(4);
		case 5:switchImage(5);		
	}	
	function switchImage(num){
		tabArr[num].on("click",function(){
				diffArr[num].fadeIn();
				for(var i=0;i<diffArr.length;i++){
					if(i!=num){
						diffArr[i].hide();
					}
				}
			})					
	}
    //点击交换图片
    $("div.c_images_display img:nth-child(n)").on("tap",function(){
    	$(".c_default_img").attr("src",this.src).show("explode");
		//滤镜图片添加
		 $("div.c_images_filter img:nth-child(n)").attr("src",""+this.src+"");  
		})   
    ///////////////////////////////////////////文字控制工具栏
	//移动和缩放文字
	//文字重置
	$('.c_font_reset').on('click',function(){
		$('div.c_input_font').css({"position":"absolute","top":"1em","left":"10%","width":"80%","height":"4em","fontSize":"1.2rem","transform":"rotate(0deg)"});
	})	
   	//改变文字颜色
   	$("div.c_font_color a:nth-child(n)").on("tap",function(){
   		var bgColor=$(this).css('backgroundColor');
		$("div.c_input_font").css("color",bgColor);
	})
	//改变文字字体
	$("div.c_font_fami a:nth-child(n)").on("tap",function(){
   		var ft=$(this).css('fontFamily');
		$("div.c_input_font,div.c_extend").css("fontFamily",ft);
	})
	//文字删除
	$('div.c_font_recreate table tr td:nth-child(1) img').on("tap",function(){
		$("div.c_input_font").html("请输入文字");
		$("div.c_extend").html('');
	})	
	//文字放大
	$('div.c_font_recreate table tr td:nth-child(2) img').on("tap",function(){
		var ft=parseInt($('div.c_input_font').css("fontSize"));
		ft+=3;
		$('div.c_input_font').css("fontSize",ft+'px');
		})
	//文字缩小
	$('div.c_font_recreate table tr td:nth-child(3) img').on("tap",function(){
		var ft=parseInt($('div.c_input_font').css("fontSize"));
		ft-=3;
		$('div.c_input_font').css("fontSize",ft+'px');
		})			
	//文字左旋	
	$('div.c_font_recreate table tr td:nth-child(4) img').on("tap",function(){
		var x = $ ('div.c_input_font').css ('-webkit-transform');	
		var start=x.indexOf("(")+1;
		var end=x.indexOf(")");
		var str=x.substring(start,end);
		var arrStr=str.split(",");		
		angle-=3;
		var cosVal = Math.cos(angle * Math.PI / 180), sinVal = Math.sin(angle * Math.PI / 180);
		$('div.c_input_font').css("transform",'matrix('+ cosVal.toFixed(6) +','+ sinVal.toFixed(6) +','+ (-1 * sinVal).toFixed(6) +','+ cosVal.toFixed(6) +','+arrStr[4]+','+arrStr[5]+')');
	})
	//文字右旋
	$('div.c_font_recreate table tr td:nth-child(5) img').on("tap",function(){
		var x = $ ('div.c_input_font').css ('-webkit-transform');	
		var start=x.indexOf("(")+1;
		var end=x.indexOf(")");
		var str=x.substring(start,end);
		var arrStr=str.split(",");		
		angle+=3;
		var cosVal = Math.cos(angle * Math.PI / 180), sinVal = Math.sin(angle * Math.PI / 180);
		$('div.c_input_font').css("transform",'matrix('+ cosVal.toFixed(6) +','+ sinVal.toFixed(6) +','+ (-1 * sinVal).toFixed(6) +','+ cosVal.toFixed(6) +','+arrStr[4]+','+arrStr[5]+')');
	})
	//文字逆时针旋转90度
	$('div.c_font_recreate table tr td:nth-child(6) img').on("tap",function(){
		$('div.c_input_font').css("transform","rotate(0deg)");
		$('div.c_input_font').removeClass('shupai');
	})
	//文字逆时针旋转90度
	$('div.c_font_recreate table tr td:nth-child(7) img').on("tap",function(){
		$('div.c_input_font').addClass('shupai');
	})
	//输入框获取焦点开始输入文字
	$('div.c_extend').one("focus",function(){
		$(this).html('');
	})
	//文字预设文字交换
	$('div.c_font_style div:nth-child(n)').on("tap",function(){
		$("div.c_input_font").html($(this).html());
		$("div.c_extend").html($(this).html());
	})
	$('.c_default_img').on('touchstart',function(event){
	 	event.preventDefault();
	})
	$('.c_default_img').on('touchmove',function(event){
    	event.preventDefault();
		posX=$('div.c_create_area').offset().left;
    	posY=$('div.c_create_area').offset().top;
    	touch = event.touches[0];
    	x=touch.pageX-posX-2;
    	y=touch.pageY-posY-2;    	
    	$(this).css({"position":"absolute","left":x,"top":y}); 	
    })
	$('.c_default_img').on('pinchopen',function(event){
		event.preventDefault();
		var inch;
		inch+=5;
		$(this).css({"transform":'scale('+inch+')'}); 	
	})
    //滤镜删除
    $("div.c_images_filter_title a").on("tap",function(){
    	var cName=document.getElementsByClassName("c_default_img")[0].className.split(" ");	
    	$('.c_default_img').removeClass(cName[1]);
    })
	//添加滤镜
	$('div.c_images_filter img').on("click",function(){
		$('.c_default_img').css("-webkit-mask-image",$(this).css("-webkit-mask-image"))
	})
	///////////////////////////////////////////开始涂鸦创作
	//添加涂鸦图案
  	$('div.c_painter_menu img').on("tap",function(){
    	$(".c_tuya_img").attr("src",this.src).show({"drop":"up"});
    })
    //删除涂鸦		
    $('div.c_painter_create table tr td:nth-child(1) img').on("tap",function(){
    	$(".c_tuya_img").hide();
    })	  
	//移动和缩放图案
    $('.c_create_area').find('.c_tuya_img').panzoom();	
    //图案重置
    $('.c_tuya_reset').on("tap",function(){
    	$(".c_tuya_img").width(100).height(100).css({"position":"absolute","left":"50%","top":"50%","margin-top":"-50%","margin-bottom":"-50%","transform":"rotate(0deg)"});
    })
	//涂鸦图片缩放
    var tZoom=0
    $('div.c_painter_create table tr td:nth-child(2) img').on("tap",function(){ 
    	tZoom+=2;
		var wPos=$(".c_tuya_img").width();
    	var hPos=$(".c_tuya_img").height(); 
		$(".c_tuya_img").animate({width:wPos+tZoom,height:wPos+tZoom},200);
    })
	$('div.c_painter_create table tr td:nth-child(3) img').on("tap",function(){ 
    	tZoom+=0;
    	var wPos=$(".c_tuya_img").width();
    	var hPos=$(".c_tuya_img").height();
    	$(".c_tuya_img").animate({width:wPos-tZoom,height:wPos-tZoom},200);
    	if(wPos<30||hPos<30){
    		$(".c_tuya_img").animate({width:"30",height:"30"});
    	} 	
    })             
	//涂鸦图案旋转
	var angle = 0;
	//左旋
	$('div.c_painter_create table tr td:nth-child(4) img').on("tap",function(){		
	var x = $ ('.c_tuya_img').css ('-webkit-transform');	
		var start=x.indexOf("(")+1;
		var end=x.indexOf(")");
		var str=x.substring(start,end);
		var arrStr=str.split(",");
		angle-=3;
		var cosVal = Math.cos(angle * Math.PI / 180), sinVal = Math.sin(angle * Math.PI / 180);
		$('.c_tuya_img').css("transform",'matrix('+ cosVal.toFixed(6) +','+ sinVal.toFixed(6) +','+ (-1 * sinVal).toFixed(6) +','+ cosVal.toFixed(6) +','+arrStr[4]+','+arrStr[5]+')');
	})
	//右旋
	$('div.c_painter_create table tr td:nth-child(5) img').on("tap",function(){
		var x = $ ('.c_tuya_img').css ('-webkit-transform');	
		var start=x.indexOf("(")+1;
		var end=x.indexOf(")");
		var str=x.substring(start,end);
		var arrStr=str.split(",");
		angle+=3;
		var cosVal = Math.cos(angle * Math.PI / 180), sinVal = Math.sin(angle * Math.PI / 180);
		$('.c_tuya_img').css("transform",'matrix('+ cosVal.toFixed(6) +','+ sinVal.toFixed(6) +','+ (-1 * sinVal).toFixed(6) +','+ cosVal.toFixed(6) +','+arrStr[4]+','+arrStr[5]+')');
	})
	////////////////////////////////创作界面
	$("div.c_editable table tr td:nth-child(1)").on("click",function(){
		$("div.c_editable").hide();
		$("div.c_choose_imgs,div.c_footer_page").fadeIn();
	})		
	////////////////////////////////购买界面
	$('div.c_footer_menu h2.c_third_title a,div.c_editable table tr td:nth-child(2)').on("click",function(){
		$("div.c_shopping,div.c_shopping_zhedang").fadeIn("normal");
		$("div.c_footer_menu,div.c_editable").hide();
		})
})
$(function(){
	//选择款式栏目切换	
	$("div.c_footer_style table tr td:nth-child(1) a").on("tap",function(){
		$("div.c_style_page1").show('drop');
		$("div.c_style_page2,div.c_style_page3,div.c_style_page4").hide();
		})
	$("div.c_footer_style table tr td:nth-child(2) a").on("tap",function(){
		$("div.c_style_page2").show('drop');
		$("div.c_style_page1,div.c_style_page3,div.c_style_page4").hide();
		})	
	$("div.c_footer_style table tr td:nth-child(3) a").on("tap",function(){
		$("div.c_style_page3").show('drop');
		$("div.c_style_page1,div.c_style_page2,div.c_style_page4").hide();
		})
	$("div.c_footer_style table tr td:nth-child(4) a").on("tap",function(){
		$("div.c_style_page4").show('drop');
		$("div.c_style_page1,div.c_style_page2,div.c_style_page3").hide();
		})
	//全部界面
	$("div.c_style_page1 div.c_page1_style1").on("tap",function(){
		$("div.c_dx1").show();
		$(this).css("border","1px ridge #000");
		})
	$("div.c_style_page1 div.c_page1_style1").on("mouseout",function(){
		$("div.c_dx1").hide();
		$(this).css("border","none");
		})
	$("div.c_style_page1 div.c_page1_style2").on("tap",function(){
		$("div.c_dx2").show();
		$(this).css("border","1px ridge #000");
		})
	$("div.c_style_page1 div.c_page1_style2").on("mouseout",function(){
		$("div.c_dx2").hide();
		$(this).css("border","none");
		})	
	$("div.c_style_page1 div.c_page1_style3").on("tap",function(){
		$("div.c_cx1").show();
		$(this).css("border","1px ridge #000");
		})
	$("div.c_style_page1 div.c_page1_style3").on("mouseout",function(){
		$("div.c_cx1").hide();
		$(this).css("border","none");
		})
	$("div.c_style_page1 div.c_page1_style4").on("tap",function(){
		$("div.c_cx2").show();
		$(this).css("border","1px ridge #000");
		})
	$("div.c_style_page1 div.c_page1_style4").on("mouseout",function(){
		$("div.c_cx2").hide();
		$(this).css("border","none");
		})	
	$("div.c_style_page1 div.c_page1_style5").on("tap",function(){
		$("div.c_wy1").show();
		$(this).css("border","1px ridge #000");
		})
	$("div.c_style_page1 div.c_page1_style5").on("mouseout",function(){
		$("div.c_wy1").hide();
		$(this).css("border","none");
		})	
	$("div.c_style_page1 div.c_page1_style6").on("tap",function(){
		$("div.c_wy2").show();
		$(this).css("border","1px ridge #000");
		})
	$("div.c_style_page1 div.c_page1_style6").on("mouseout",function(){
		$("div.c_wy2").hide();
		$(this).css("border","none");
		})
	//短袖界面
	$("div.c_style_page2 div.c_page2_style1").on("tap",function(){
		$("div.c_dx1").show();
		$(this).css("border","1px ridge #000");
		})
	$("div.c_style_page2 div.c_page2_style1").on("mouseout",function(){
		$("div.c_dx1").hide();
		$(this).css("border","none");
		})
	$("div.c_style_page2 div.c_page2_style2").on("tap",function(){
		$("div.c_dx2").show();
		$(this).css("border","1px ridge #000");
		})
	$("div.c_style_page2 div.c_page2_style2").on("mouseout",function(){
		$("div.c_dx2").hide();
		$(this).css("border","none");
		})
	//长袖界面
	$("div.c_style_page3 div.c_page3_style1").on("tap",function(){
		$("div.c_cx1").show();
		$(this).css("border","1px ridge #000");
		})
	$("div.c_style_page3 div.c_page3_style1").on("mouseout",function(){
		$("div.c_cx1").hide();
		$(this).css("border","none");
		})
	$("div.c_style_page3 div.c_page3_style2").on("tap",function(){
		$("div.c_cx2").show();
		$(this).css("border","1px ridge #000");
		})
	$("div.c_style_page3 div.c_page3_style2").on("mouseout",function(){
		$("div.c_cx2").hide();
		$(this).css("border","none");
		})
	//卫衣界面
	$("div.c_style_page4 div.c_page4_style1").on("tap",function(){
		$("div.c_wy1").show();
		$(this).css("border","1px ridge #000");
		})
	$("div.c_style_page4 div.c_page4_style1").on("mouseout",function(){
		$("div.c_wy1").hide();
		$(this).css("border","none");
		})	
	$("div.c_style_page4 div.c_page4_style2").on("tap",function(){
		$("div.c_wy2").show();
		$(this).css("border","1px ridge #000");
		})
	$("div.c_style_page4 div.c_page4_style2").on("mouseout",function(){
		$("div.c_wy2").hide();
		$(this).css("border","none");
		})
	//菜单栏显示背景
	$("div.c_footer_style table tr td").on("tap",function(){
		$(this).css({"background":"#eee"});
		})
	$("div.c_footer_style table tr td").on("mouseout",function(){
		$(this).css({"background":"#fff"});
		})
	//购物车显示切换背景
	$("div.c_shopping_button a").on("tap",function(){
		$(this).css({"background":"rgba(145,145,145,0.4)","color":"#fff","text-shadow":"none","font-weight":"lighter"});
		})
	$("div.c_shopping_button a").on("mouseout",function(){
		$(this).css({"background":"#fff","color":"#000","text-shadow":"none","font-weight":"lighter"});
		})
	$("div.c_footer_shopping table tr td").on("tap",function(){
		$(this).css({"color":"#fff","background":"#00b88f"});
		$(this).find("a").css("color","#fff");
		})
	$("div.c_footer_shopping table tr td").on("mouseout",function(){
		$(this).css({"color":"rgba(0,0,0,0.6)","background":"#fff"});
		$(this).find("a").css("color","rgba(0,0,0,0.6)");
		})
	//购物车点击后创作界面显示
	$("div.c_footer_shopping tr td:nth-child(1) a").on('click',function(){
		$('div.c_shopping').hide();
		$('div.c_footer_menu').slideDown(400);
	})
	//图片分类文字背景切换
	$("a.c_page_list:not(.c_arrow_left .c_arrow_right)").on("tap",function(){
		$(this).css({"color":"#007d65","font-size":"1.6rem"});
		})
	$("a.c_page_list").on("mouseout",function(){
		$(this).css({"color":"rgba(0,0,0,0.6)","font-size":"1.4rem"});
		})
	//添加地址
	$("div.c_shop_addr a").on("tap",function(){
		$(this).css({"background":"rgba(145,145,145,0.4)"});
		})
	$("div.c_shop_addr a").on("mouseout",function(){
		$(this).css({"background":"none"});
		})
	//创作主界面底部菜单栏背景切换
	$("div.c_footer_menu td.c_pri_menu").on("tap",function(){
		$(this).css({"background":"#eeeeee"});
		})
	$("div.c_footer_menu td.c_pri_menu").on("mouseout",function(){
		$(this).css({"background":"#fff"});
		})
	//我的设计编辑界面底部菜单栏背景切换
	$("div.c_edit_designed table tr td,div.c_editable table tr td").on("tap",function(){
		$(this).css({"background":"#eeeeee"});
		})
	$("div.c_edit_designed table tr td,div.c_editable table tr td").on("mouseout",function(){
		$(this).css({"background":"#fff"});
		})
	//图案类型切换
	function arrowSwitch(num){
	for(var i=0;i<6;i++){
    if(i==num){
        arr1[i].css({"color":"#007d65","font-size":"1.6rem"});
       	 arr2[i].fadeIn();
       }else{
              arr1[i].css({"color":"rgba(0,0,0,0.6)","font-size":"1.4rem"});
             	arr2[i].hide();
           }
 	}	 
 	 } 
	 var num=0;
     var arr1=[$('div.c_diffpage td:nth-child(2) a'),$('div.c_diffpage td:nth-child(3) a'),$('div.c_diffpage td:nth-child(4) a'),$('div.c_diffpage td:nth-child(5) a'),$('div.c_diffpage td:nth-child(6) a'),$('div.c_diffpage td:nth-child(7) a')];
     var arr2=[$('div#c_img_list1'),$('div#c_img_list2'),$('div#c_img_list3'),$('div#c_img_list4'),$('div#c_img_list5'),$('div#c_img_list6')];		
     //左箭头向左移动图片
     $('div.c_diffpage td:nth-child(1) a img').on('click',function(){
     num++;
     var res=num%6;
     switch(res){
       	case 0: arrowSwitch(0);break;
        case 5: arrowSwitch(1);break;
        case 4: arrowSwitch(2);break;
        case 3: arrowSwitch(3);break;
       	case 2: arrowSwitch(4);break;
        case 1: arrowSwitch(5);break;
      } 
      })
    //右箭头向右移动图片
	$('div.c_diffpage td:nth-child(8) a img').on('click',function(){
     num++;
     var res=num%6;
     switch(res){
       	case 1: arrowSwitch(0);break;
        case 2: arrowSwitch(1);break;
        case 3: arrowSwitch(2);break;
        case 4: arrowSwitch(3);break;
       	case 5: arrowSwitch(4);break;
        case 0: arrowSwitch(5);break;
      } 
      })
   //文字颜色框切换	
   function fontSwitch(num){
		for(var i=0;i<10;i++){
             if(i==num){
             	var bgColor=fontArr[i].css('backgroundColor');
                fontArr[i].css({"border":"2px solid #fb3475"});
				$("div.c_input_font").css("color",bgColor);                             
               }else{
                    fontArr[i].css({"border":"none"});         
               }
        }   	
   } 
 var num=0;
 var fontArr=[$('div.c_font_color td:nth-child(2) a'),$('div.c_font_color td:nth-child(3) a'),$('div.c_font_color td:nth-child(4) a'),$('div.c_font_color td:nth-child(5) a'),$('div.c_font_color td:nth-child(6) a'),$('div.c_font_color td:nth-child(7) a'),$('div.c_font_color td:nth-child(8) a'),$('div.c_font_color td:nth-child(9) a'),$('div.c_font_color td:nth-child(10) a'),$('div.c_font_color td:nth-child(11) a')];
 //文字左箭头移动文字颜色块切换
 $('div.c_font_color td:nth-child(1) img').on('click',function(){
     num++; 
     var res=num%10;
     switch(res){
     	case 0:fontSwitch(0);break;
     	case 9:fontSwitch(1);break;
     	case 8:fontSwitch(2);break;
     	case 7:fontSwitch(3);break;
     	case 6:fontSwitch(4);break;
     	case 5:fontSwitch(5);break;
     	case 4:fontSwitch(6);break;
     	case 3:fontSwitch(7);break;
     	case 2:fontSwitch(8);break;
     	case 1:fontSwitch(9);break;
     	} 
     })
  //文字左箭头移动文字颜色块切换
  $('div.c_font_color td:nth-child(12) img').on('click',function(){
     num++; 
     var res=num%10;
     switch(res){
     	case 1:fontSwitch(0);break;
     	case 2:fontSwitch(1);break;
     	case 3:fontSwitch(2);break;
     	case 4:fontSwitch(3);break;
     	case 5:fontSwitch(4);break;
     	case 6:fontSwitch(5);break;
     	case 7:fontSwitch(6);break;
     	case 8:fontSwitch(7);break;
     	case 9:fontSwitch(8);break;
     	case 0:fontSwitch(9);break;
     	} 
     }) 
   //款式页面实现图片切换
	$('div.c_row img').on('click',function(){
		$('.c_index_clothes').attr({"src":$(this).attr("src"),"data-styleInfo":$(this).attr("data-styleInfo"),"data-price":$(this).attr("data-price"),"data-sex":$(this).attr("data-sex"),"data-color":$(this).attr("data-color")});	
	})	
	//订单标题背景切换
	$("div.c_myorder_title table tr td a").on("tap",function(){
	$(this).css({"color":"#45b97c","border-bottom":"5px solid #45b97c"});
	})
	$("div.c_myorder_title table tr td a").on("mouseout",function(){
	$(this).css({"color":"#000","border-bottom":"none"});
	})
	//订单按钮切换
	$("a.c_submit").on("tap",function(){
		$(this).css({"border":"1px solid #ccc","border-radius":"10px","background":"#45b97c","color":"#fff"});
	})
	$("a.c_submit").on("mouseout",function(){
		$(this).css({"border":"none","border-radius":"none","background":"none","color":"rgba(0,0,0,0.8)"});
	})
	//菜单按钮切换	
	$("a.c_order1").on("click",function(){
	$("div#c_order_menu1,div#c_order_menu2,div#c_order_menu3,div.c_blank").show();
	$("div#c_order_menu2").css({"position":"relative","top":"2em"});
	$("div#c_order_menu3").css({"position":"relative","top":"3.5em"});
	$("div.b4").css({"position":"relative","top":"5em","height":"5em"});
	})
	$("a.c_order2").on("click",function(){
		$("div#c_order_menu2").show().css({"position":"relative","top":"0em"});
		$("div#c_order_menu1,div#c_order_menu3,div.b2,div.b3").hide();
		$("div.b4").css({"position":"relative","top":"1.5em","height":"40em"});					
	})
	$("a.c_order3").on("click",function(){
		$("div#c_order_menu3").show().css({"position":"relative","top":"0em"});
		$("div#c_order_menu2,div#c_order_menu1,div.b2").hide();
		$("div.b4").css({"position":"relative","top":"1.5em","height":"40em"});
	})
	//手势切换函数
	function gesSwitch(flag,color,num){
		var fzImg='c_images/c_clothes/c_clothes1.png';
		$(".c_index_clothes").attr({"src":fzImg.replace(/1/g,flag),"data-color":color});
		for(var i=0;i<6;i++){
          if(i==num){
              arr[i].css("border","1px solid #000");
                }else{
                   arr[i].css("border","none");
                }
           }
	}	
	//服装颜色根据手势上下切换
    		var num=0;
            var arr=[$('div.c_color_block div:nth-child(1)'),$('div.c_color_block div:nth-child(2)'),$('div.c_color_block div:nth-child(3)'),$('div.c_color_block div:nth-child(4)'),$('div.c_color_block div:nth-child(5)'),$('div.c_color_block div:nth-child(6)')];
            $('div.c_turn_color_area').on('scrollstart',function(){
                num++;
                var result=num%6;
                switch(result){
                	case 1:gesSwitch('1','白色',0);break;
                	case 2:gesSwitch('2','黑色',1);break;
                	case 3:gesSwitch('3','红色',2);break;
                	case 4:gesSwitch('4','黄色',3);break;
                	case 5:gesSwitch('5','蓝色',4);break;              			
					case 0:gesSwitch('6','绿色',5);break;	                   
					default:alert('颜色切换出错');
                }
                  })	
          
	//加入购物车
	//购物车按钮计数器
	$("div.c_add").on("click",function(){
		var value=parseInt($("div.c_shopping_num input[type='text']").val());
		value=value+1;
		cost=$(".c_index_clothes").attr("data-price")*value;
		$("div.c_shopping_num input[type='text']").val(value);
	})
	$("div.c_minus").on("click",function(){
		var value=parseInt($("div.c_shopping_num input[type='text']").val());
		value=value-1;
		cost=$(".c_index_clothes").attr("data-price")*value;
		if(value<1){
			value=1;
		}
		$("div.c_shopping_num input[type='text']").val(value);
	})
	//计算订单单件价格
	function cPrice(oObj){
		var value=parseInt($(oObj).find('div.c_shop_counter_box h5').html());
		var price=parseInt($(oObj).find('div.c_add_shop_dusbin h5 span').html());
		$(oObj).find("div.c_shop_add").on("click",function(){
		value=value+1;
		price=$(".c_index_clothes").attr("data-price")*value;		
		$(oObj).find('div.c_shop_counter_box h5').html(value);
		$(oObj).find('div.c_add_shop_dusbin h5 span').html(price);
		totalPrice();			
	})
		$(oObj).find("div.c_shop_minus").on("click",function(){
		value=value-1;
		price=$(".c_index_clothes").attr("data-price")*value;		
		if(value<1){
			value=1;
		}
		$(oObj).find('div.c_shop_counter_box h5').html(value);
		$(oObj).find('div.c_add_shop_dusbin h5 span').html(price);
		totalPrice();			
	})								
	}
	//计算款式总价
	function totalPrice(){
		var amount=$('div.c_add_shop');
		var count=[];
		var sum=0;
		for(var i=0;i<amount.length;i++){
		   	count.push(parseInt($(amount[i]).find('div.c_add_shop_dusbin h5 span').html()));
		}
		totalSum=count.sum();
		$('span.c_total_price').html(count.sum());
	}	
	var styleInch,cost; 
	//购物车插入订单
	function insertShoppingCarts(cSrc,cStyleInfo,cStyleInch,cSex,cColor,cNum,sCost){
		var oDiv=$("<div class='c_add_shop'><table><tr><td><div><label><input type='radio' name='c_radio' data-role='none'></label></div></td><td><div class='c_add_shop_img'><img src="+cSrc+" style='width:5em;height:5em' class='cSrc'><h5>"+cStyleInfo+"</h5></div></td><td><div class='c_add_shop_style'><h5><span>"+cStyleInch+"</span>("+cSex+")</h5><h5>"+cColor+"</h5></div></td><td><div class='c_shop_counter_box'><h5>"+cNum+"</h5><div class='c_counter_wrapper'><div class='c_shop_add'>+</div><div class='c_shop_minus'>-</div></div></div></td><td><div class='c_add_shop_dusbin'><h5>￥<span class='c_cost'>"+ sCost +"</span>.00</h5><img src='c_images/c_order_dusbin.png' width='26' height='30' class='c_order_dusbin'></div></td></tr></table></div>");
			$(oDiv).insertBefore("div.c_addr_intro");
			cPrice(oDiv);
			$(oDiv).find('img.c_order_dusbin').on('click',function(){
				$(this).parents('.c_add_shop').hide().remove();
				var value=Number($(this).siblings('h5').children('.c_cost').html());
				var balance=Number($('span.c_total_price').html())-value;
				$('span.c_total_price').html(balance);
				if(balance==0||balance==null||balance=='undefined'){
					$('span.c_total_price').html('00');
				}
			})
			$(oDiv).find("img.cSrc").on("click",function(){
					$(this).css({"transform":"scale(2,2)","position":"relative","top":"1em","box-shadow":"2px 2px 2px #888888","border":"1px ridge #fff","border-radius":"5px"})
			})
			$(oDiv).find("img.cSrc").on("mouseout",function(){
					$(this).css({"transform":"scale(1,1)","position":"relative","top":"0em","box-shadow":"none","border":"none","border-radius":"none"})
			})			
			$('div.c_pay:nth-child(1)').on('click',function(){

				if($(oDiv).find('input[type="radio"]').prop('checked')==true){
				$.post("test2.php",{describe:cStyleInfo,size:cStyleInch,sex:cSex,color:cColor,number:cNum,singleprice:sCost,totalprice:totalSum},
					function(data,status){
						alert(data+"\n"+status);
				})
				/*
				var data={};
				data['objname']='测试文本';
				data['pid']=11111111111;
					$.ajax({
						url:'http://121.40.109.160/front/Order/addOrder.do',
						method:'post',
						contentType:'application/json',
						data:JSON.stringify(data),
						success:function(data){
							console.log(data);
							alert(data);
						},
						error:function(data){
							console.log(data);
							alert(data);
						}
					})
					*/
				}else{
					return false;
				}
			})						
	}
	//获取款式尺寸
	$("div.c_shopping_button a").click(function(){			
		styleInch=$(this).html();
	})
	//获得设计图片url
	var i=-1;
	function getDesignUrl(){
		i++;
		$("div.c_create_area,div.c_input_font").css({border:"none"});
		html2canvas($("div.c_pri_cloth"), { 
			onrendered: function(canvas) { 
				$("<div class='pic'></div>").append(canvas);
				var cxt=canvas.getContext("2d");
				url = canvas.toDataURL();
				var oDesign=$("<div class='pic'><img src="+url+" class='c_design'></div>");
				$('.cSrc').eq(i).attr("src",url);
				$("div.c_image_wrapper").append(oDesign);
				$.post("test1.php",{cSrc:url,cCount:i},function(data,status){
					alert(data+"\n"+status);
				})
			}
	})	
	}
	//获取款式路径，款式信息，性别，款式颜色，款式件数	
	$("div.c_footer_shopping tr td a").on("click",function(){	
		var src,styleInfo,sex,color,num;
		src=getDesignUrl();
		sex=$(".c_index_clothes").attr("data-sex");
		styleInfo=$(".c_index_clothes").attr("data-styleInfo");
		color=$(".c_index_clothes").attr("data-color");
		num=$('div.c_shopping_num input:text').val();
		if(styleInch=='undefined'||styleInch==null||styleInch==''){
					styleInch='XL';
				}
		if(cost=='undefined'||cost==null||cost==''){
					cost=$(".c_index_clothes").attr("data-price");
				}			
	    insertShoppingCarts(src,styleInfo,styleInch,sex,color,num,cost);
	    totalPrice();
	})	
	//保存
	$("a#c_clothing_save").on("tap",function(){
		$(this).css({"border":"1px inset #000"});
		})
	$("a#c_clothing_save").on("mouseout",function(){
		$(this).css({"border":"1px solid #000"});
		})
	$("a#c_clothing_save,img.c_true").on("tap",function(){
		$("div.c_create_area,div.c_input_font").css({border:"none"});
		html2canvas($("div.c_pri_cloth"), { 
			onrendered: function(canvas) { 
				var cxt=canvas.getContext("2d");
				var url = canvas.toDataURL('image/png');
				var oDesign=$("<div class='pic'><img src="+url+" class='c_design'></div>");
				$("div.c_image_wrapper").append(oDesign);
				enterEdit();	
				}
			});		
	})
	//点击图片进入编辑页	
	function enterEdit(){
		$('div.pic').each(function(){
			$(this).on("tap",function(){
			window.location.href="#pageone";
			$("div.c_choose_imgs,div.c_footer_menu,div.c_footer_page,div.c_img_create_box,div.c_images_filter_box,div.c_font_create,div.c_font_default,div.c_font_fami_panel,div.c_input_page_font,div.c_painter").hide();
			$("div.c_editable").show();				
			})
		})
	}
	function addMask(obj) {
        var picH = $(obj).height();
        var picW = $(obj).width();
        var picTop = $(obj).position().top;
        var picLeft = $(obj).position().left;
        var oDiv = $("<div class='imageChecked'></div>");
        $(oDiv).css("height", picH + "px").css("width", picW + "px").appendTo($(obj));
    }
   //按钮选择
	$("a.c_design_choose").on("click",function(){
		$(this).hide();
		$("div.c_chose").show();
		$("div.pic").unbind("tap");
		$("div.pic").each(function () {
		        $(this).click(function () {
		            addMask($(this));
		         })
		     })				
	})
	//按钮删除
	$('a.c_chose_delete').on('click',function(){
		$('div.pic').has('.imageChecked').hide();
	})
	//按钮取消
	$("a.c_chose_cancel").on("click",function(){
		$("div.c_chose").hide();
		$("a.c_design_choose").fadeIn();
		$('div.pic').children('div').hide();
		$("div.pic").unbind("click");
		//点击图片进入编辑页	
		enterEdit();			
	})	
	//按钮背景切换
	$("a.c_chose_delete,a.c_chose_cancel").on("tap",function(){
		$(this).css({"background":"rgb(0,184,143)","color":"#fff"});
	})
	$("a.c_chose_delete,a.c_chose_cancel").on("mouseout",function(){
		$(this).css({"background":"#fff","color":"#000"});
	})
	//禁用鼠标右键点击事件
	$(document).on('contextmenu selectstart select taphold',function(){
		return false;
	});		                 																									
})

