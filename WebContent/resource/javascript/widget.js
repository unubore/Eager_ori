if(document.all){ 
	top.window.resizeTo(screen.availWidth,screen.availHeight); 
}
else if(document.layers||document.getElementById){
	if(top.window.outerHeight<screen.availHeight||top.window.outerWidth<screen.availWidth){
		top.window.outerHeight = screen.availHeight;
		top.window.outerWidth = screen.availWidth;
	}
}
var startDate=new Date();
var isPopLoginDialog=false;
$.extend({
	httpData: function( xhr, type, s ) {
		var ct = xhr.getResponseHeader("content-type") || "",
			xml = type === "xml" || !type && ct.indexOf("xml") >= 0,
			data = xml ? xhr.responseXML : xhr.responseText;

		if ( xml && data.documentElement.nodeName === "parsererror" ) {
			jQuery.error( "parsererror" );
		}

		// Allow a pre-filtering function to sanitize the response
		// s is checked to keep backwards compatibility
		if ( s && s.dataFilter ) {
			data = s.dataFilter( data, type );
		}

		// The filter can actually parse the response
		if ( typeof data === "string" ) {
			// Get the JavaScript object, if JSON is used.
			if ( type === "json" || !type && ct.indexOf("json") >= 0 ) {
				data = jQuery.parseJSON( data );

			// If the type is "script", eval it in global context
			} else if ( type === "script" || !type && ct.indexOf("javascript") >= 0 ) {
				jQuery.globalEval( data );
			}
		}

		return data;
	},
	handleError: function( s, xhr, status, e ) {
		// If a local callback was specified, fire it
		if ( s.error ) {
			s.error.call( s.context, xhr, status, e );
		}

		// Fire the global callback
		if ( s.global ) {
			
			jQuery.triggerGlobal( s, "ajaxError", [xhr, s, e] );
		}
	},
	layout:function(option){
		function layoutFun(){
			var documentHeight=document.documentElement.clientHeight
				-$(".ui-layout-north").outerHeight(true)
				-$(".ui-layout-south:visible").outerHeight(true);
			var rightWidth=document.documentElement.clientWidth-$(".ui-layout-west:visible").width();
			
			$(".ui-layout-center").height(documentHeight);
			$("#areaContainer .highcharts-container").css("width","100%");
			//$(".ui-layout-center").width(rightWidth-1);
			$(".ui-layout-north dl.sysMenuList").width(document.documentElement.clientWidth);//ie6 bug
			if(window._currentGrid!=undefined && !window._currentGrid.closest(".leaderCon")[0] && !window._currentGrid.closest(".workbenchTabList")[0] && window._currentGrid.closest(".ui-layout-center")[0]){
				window._currentGrid.setGridWidth(rightWidth-$(".superviseCenterRight:visible").width()-$("#contentDiv .center-left").width()-$("#contentDiv .center-content-right").width()-10).setGridHeight(documentHeight-$("#nav:visible").outerHeight(true)-$("#thisCrumbs:visible").outerHeight(true)-$(".content-top").height()-$("#content-top").height()-$(".groupNav").height()-$("#commonPopulation:visible").outerHeight(true)-$("#tabList .ui-tabs-nav").outerHeight(true)-$("#contractCommonPopulation").outerHeight(true)-$("#statistics").height()-$(".center-right .newNavBottom").outerHeight(true)-$(".center-right .newNavTopSpe").outerHeight(true)-$("#contentDiv>.newNavTop").outerHeight(true)-78);
			}
		}
		layoutFun();
		$(window).resize(function(){
			clearTimeout(window._layoutTimer);
			window._layoutTimer=setTimeout(function(){
				layoutFun();
			},300);
		});
		$(".slideResizer .slideToggler").toggle(function(){//缩进条按钮事件
			$(".ui-layout-west").hide();
			$(".slideResizer .slideToggler").addClass("slideTogglerCur").attr("title","展开");
			layoutFun();
			$(window).trigger("resize");
		},function(){
			$(".ui-layout-west").show();
			$(".slideResizer .slideToggler").removeClass("slideTogglerCur").attr("title","缩进");
			
			layoutFun();
			$(window).trigger("resize");
		});
	},
	versionIntroduction:function(options){
		var dfop={
			data:[
				{url:'javascript:;',content:'<img src="/resource/system/images/version/3.1.0/version1.png" />'},
				{url:'javascript:;',content:'<img src="/resource/system/images/version/3.1.0/version2.png" />'},
				{url:'javascript:;',content:'<img src="/resource/system/images/version/3.1.0/version3.png" />'},
				{url:'javascript:;',content:'<img src="/resource/system/images/version/3.1.0/version4.png" />'},
				{url:'javascript:;',content:'<img src="/resource/system/images/version/3.1.0/version5.png" />'},
				{url:'javascript:;',content:'<img src="/resource/system/images/version/3.1.0/version6.png" />'},
				{url:'javascript:;',content:'<img src="/resource/system/images/version/3.1.0/version7.png" />'},
				{url:'javascript:;',content:'<img src="/resource/system/images/version/3.1.0/version8.png" />'},
				{url:'javascript:;',content:'<img src="/resource/system/images/version/3.1.0/version9.png" />'},
				{url:'javascript:;',content:'<img src="/resource/system/images/version/3.1.0/version10.png" />'},
				{url:'javascript:;',content:'<img src="/resource/system/images/version/3.1.0/version11.png" />'},
				{url:'javascript:;',content:'<img src="/resource/system/images/version/3.1.0/version13.png" />'},
				{url:'javascript:;',content:'<img src="/resource/system/images/version/3.1.0/version14.png" />'}
			],
			close:function(){
				
			}
		};
		$.extend(dfop, options);
		var $stepFocusBox;
		function init(){//初始化
			if($("#stepFocusBox")[0]){
				$stepFocusBox=$("#stepFocusBox");
			}else{
				$stepFocusBox=$('<div class="focusBox" id="stepFocusBox"><div class="innerBox"><a href="javascript:;" title="关闭" class="close" id="stepFocusBoxClose"></a><a class="prev" href="javascript:;"></a><a class="next" href="javascript:;"></a><ul class="pic" id="stepBox"></ul><ul class="hd" id="stepBoxPager"></ul></div></div><div class="version-overlay"></div>');
				$("body").prepend($stepFocusBox);
			}
		}
		function bindEvent(){//绑定事件
			$("#stepFocusBox").hover(function(){
				$(this).find(".prev,.next").show();
			},function(){
				$(this).find(".prev,.next").hide();
			});
			$('#stepBox').imageready(function () {
				if(navigator.userAgent.indexOf('MSIE 6')>-1){
		    		$("#stepBox img").each(function(){
		    			var thisW=$(this).width();
		    			var thisH=$(this).height();
		    			setPNG(this,thisW,thisH);
		    		})
				}
		    });
		    $("#stepClose,#stepFocusBoxClose").click(close);
			$("#stepFocusBox").bgiframe();
			$("#stepFocusBox").slide({mainCell:".pic",effect:"left",pnLoop:false, autoPlay:false, delayTime:300, trigger:"click"});
		}
		function build(){//构建内容
			function buildStep(stepIndex){
				var $thisStep=$("<li />");
				$self.append($thisStep);
				$("#"+selfId+"Pager").append('<li title="'+stepIndex+'" />');
				return $thisStep;
			}
			function buildStepCtt($step,thisData,index){
				var $link=$("<a />").attr("href",thisData.url).html(thisData.content),
					$thisStepCtt=$("<div />").addClass("step").append($link);
				if((index+1)%3==2){
					$thisStepCtt.addClass("right");
				}
				$step.append($thisStepCtt);
			}
			var stepIndex=1,$step=buildStep(1);
			for(var i=0;i<dfop.data.length;i++){
				buildStepCtt($step,dfop.data[i],i);
				if(i==dfop.data.length-1){
					$closeBtn=$('<a href="javascript:;" class="stepBtn" id="stepClose">立即使用</a>');
					$step.append($closeBtn);
				}
				if((i+1) % 3==0){
					$step=buildStep(++stepIndex);
				}
			}
		}
		function close(){
			dfop.close();
			$("#stepFocusBox").fadeOut("slow",function(){
				$(this).remove();
			});
			$(".version-overlay").remove();
		}
		init();
		var selfId='stepBox';
		var $self=$("#"+selfId);
		build();
		bindEvent();
	},
	messageBox : function(options) {
		var dfop={
			message: false,
			level: "success",
			speed: 500,
			life:3000
		};
		$.extend(dfop, options);
		if(options=='close'){
			$("#jGrowl").removeAttr("style").empty();
			return false;
		}
		if(!$("#jGrowl")[0]){
			$("body").append("<div id='jGrowl'></div>")
		}else{
			$("#jGrowl").removeAttr("style").empty();
		}
		dfop.message='<div class="'+dfop.level+'"><span></span>'+dfop.message+'</div>';
		$("#jGrowl").addClass("jGrowl").append(dfop.message).animate({top:0},dfop.speed);
		function hideMessageBox(){
			clearTimeout(window._messageBox);
			window._messageBox=setTimeout(function(){
				$("#jGrowl").remove();
			},dfop.life);
		}
		hideMessageBox();
		$("#jGrowl").hover(function(){
			clearTimeout(window._messageBox);
		},function(){
			hideMessageBox();
		})
	},
	tab:function(option){
		  var defaultOption={
		   tab:"",
		   box:"",
		   hover:""
		  }
		  $.extend(defaultOption,option);
		  var _tab=$(defaultOption.tab);
		  var _box=$(defaultOption.box);
		  var _hover=defaultOption.hover;
		  var _index;
		  _tab.click(function(){
		   _index=_tab.index(this);// 获取当前点击的索引值
		   $(this).addClass(_hover).siblings().removeClass(_hover);
		   _box.eq(_index).show().siblings().hide();
		  }).eq(0).click();
	},
	dialogLoading:function(option){
		var _init=function(){
			$("body").prepend('<div class="dialog_loading"><div id="dialogLoading" style="margin-top:250px;"></div></div>')
			var opts = {
			  lines: 8,
			  length: 13,
			  width: 10,
			  radius: 10,
			  corners: 1, 
			  rotate: 0, 
			  direction: 1,
			  color: '#000',
			  speed: 1,
			  trail: 100,
			  shadow: false,
			  hwaccel: false,
			  className: 'spinner',
			  zIndex: 2e9,
			  top: 'auto',
			  left: 'auto' 
			};
			var target = document.getElementById('dialogLoading');
			var spinner = new Spinner(opts).spin(target);
		};
		if(typeof(option)=='string'){
				if(option=="open"){
					_init();
					$(".dialog_loading").show();
				};
				if(option=="close"){
					$(".dialog_loading").remove();
				}
		};
	},
	loadingComp:function(option){
		var _init=function(){
			$("body").prepend('<div class="dialog_loading"><div class="loadingcon"></div></div>')
		};
		if(typeof(option)=='string'){
				if(option=="open"){
					_init();
					$(".dialog_loading").show();
				};
				if(option=="close"){
					$(".dialog_loading").remove();
				}
		};
	},
	navDropdownBtn:function(){
		$(".nav-dropdownBtn").live("mouseover",function(event){
			if($(this).hasClass("disabled")){
				return false;
			}
			var that=this;
			clearTimeout(window._navBtnOutTimer);
			window._navBtnInTimer=setTimeout(function(){
				var top=$(that).position().top+$(that).outerHeight(true);
				var marginLeft = parseInt($(".importExport").css("marginLeft"),10);
				var left=$(that).position().left;
				if( marginLeft ){ left = left + marginLeft}

				$(".nav-sub-buttons").hide();
				$(that).next(".nav-sub-buttons").css({
					top:top+2,
					left:left,
					width:$(that).outerWidth()-2
				}).fadeIn(200);
			},200)
		}).live("mouseout",function(){
			clearTimeout(window._navBtnInTimer);
			window._navBtnOutTimer=setTimeout(function(){
				$(".nav-sub-buttons").hide()
			},500)
		})
		$(".nav-sub-buttons").live("mouseover",function(){
			clearTimeout(window._navBtnOutTimer);
		})
		$(".nav-sub-buttons").live("mouseout",function(){
			window._navBtnOutTimer=setTimeout(function(){
				$(".nav-sub-buttons").hide()
			},500)
		})
	},
	dateChoose:function(options){
			var drop = {
					start:'',
					end: ''
			}
			$.extend(drop,options || {});
			
			var startDate=$(drop.start);
			var endDate=$(drop.end);
			
			$(startDate).datePicker({
				yearRange:'1900:2030',
				dateFormat:'yy-mm-dd',
				maxDate:'+0d',
				onClose:function(dateText, inst){
					if(dateText==''){
						var date=new Date();
						$(endDate).datepicker("option", "maxDate",date);
					}
				},
		   		onSelect:function(dateText, inst) {
					if(dateText!=null&&dateText!=''){
						var dateUnit=dateText.split('-');
						var date=new Date(dateUnit[0],dateUnit[1]-1,dateUnit[2]);
						$(endDate).datepicker("option", "minDate",date);
					}
				}
				
			});
			$(endDate).datePicker({
				yearRange: '1900:2030',
				dateFormat:'yy-mm-dd',
				maxDate:'+0d',
				onClose:function(dateText, inst){
					if(dateText==''){
						var date=new Date();
						$(startDate).datepicker("option", "maxDate",date);
					}
				},
		        onSelect: function(dateText, inst) {
					if(dateText!=null&&dateText!=''){
						var dateUnit=dateText.split('-');
						var date=new Date(dateUnit[0],dateUnit[1]-1,dateUnit[2]);
						$(startDate).datepicker("option", "maxDate",date);
					}
				}
			});
		}
});
function enableSaveButton(option) {
	var dlgFirstButton = $('.ui-dialog-buttonpane:visible').find('button:first');
	if (dlgFirstButton == null) {
		return;
	}
	if (option) {
		dlgFirstButton.show();
	} else {
		dlgFirstButton.hide();
	}
}

//播放提示音
function playAudio(){
    $('#audioHelp1').html('<embed src="/resource/audio/remind.mp3" loop="0" autostart="true" hidden="true"></embed>');
}

/*$.extend($.ech.multiselect.prototype.options, {
	checkAllText: '全选',
	uncheckAllText: '清空',
	noneSelectedText: '请选择',
	selectedText: '# 已选择'
});*/
$.fn.extend({
	uiMultiselect:function(o){
		var self=$(this);
		var dfop = {
			click: function(event, ui){},
			beforeopen: function(){},
			open: function(){},
			beforeclose: function(){},
			close: function(){},
			checkAll: function(){},
			uncheckAll: function(){},
			optgrouptoggle: function(event, ui){
				/*
				var values = $.map(ui.inputs, function(checkbox){
				return checkbox.value;
				}).join(", ");
				$callback.html("Checkboxes " + (ui.checked ? "checked" : "unchecked") + ": " + values);
				*/
			},
			selectedList:10
		}
		$.extend(dfop,o);
		self.multiselect(dfop);
	},
	gridRowRightClick:function(o){
		var self=$(this);
		var dfop = { 
			width: 150, 
			items: [
				{text: "新增记录", icon: "/resource/external/contextmenu/css/images/icons/add.png", alias: "add", action: function(){
					$("#add").click();
				}},
				{text: "修改记录", icon: "/resource/external/contextmenu/css/images/icons/edit.png", alias: "edit", action: function(){
					var selectId=self.data("selectid");
					if(selectId && typeof updateOperator=='function'){
						updateOperator(selectId);
					}
				}},
				{text: "删除记录", icon: "/resource/external/contextmenu/css/images/icons/del.png", alias: "del", action: function(){
					var selectId=self.data("selectid");
					if(selectId && typeof deleteOperator=='function'){
						deleteOperator(selectId);
					}
				}},
				{text: "高级搜索", icon: "/resource/external/contextmenu/css/images/icons/search.png", alias: "search", action: function(){
					$("#search").click();
				}},
				{text: "刷新", icon: "/resource/external/contextmenu/css/images/icons/refresh.png", alias: "reload", action: function(){
					$("#reload").click();
				}}
			], 
			onShow: applyrule,
			onContextMenu: BeforeContextMenu
		};
		$.extend(dfop,o);
		
		function applyrule(menu) {               
			if (this.id == "target2") {
				menu.applyrule({ 
					name: "target2",
					disable: true,
					items: ["1-2", "2-3", "2-4", "1-6"]
				});
			}else {
				menu.applyrule({ 
					name: "all",
					disable: true,
					items: []
				});
			}
		}
		
		function BeforeContextMenu() {
			return this.id != "target3";
		}
		self.contextmenu(dfop);
	},
	resetFormData:function(o){
		var self=$(this);
		var dfop={
			not:''
		}
		$.extend(dfop,o);
		self.find(":input").not(':button, :submit, :reset, :hidden').not(dfop.not).val('').removeAttr('checked').removeAttr('selected');  
	},
	serializeObject:function(option) {
		var defaultOption = {
				excludeWhenNull:true
			};
			$.extend(defaultOption,option);
			function excludeWhenNull(value){
				if(defaultOption.excludeWhenNull &&(value==""||value==null||value==undefined)){
						return false;
				}
				return true;
			}
		    var o = {};
		    var a = this.serializeArray();
		    $.each(a, function() {
				if(excludeWhenNull(this.value)){
			        if (o[this.name]) {
			            if (!o[this.name].push) {
			                o[this.name] = [ o[this.name] ];
			            }
		            	o[this.name].push(this.value || '');
			        } else {
						o[this.name] = this.value || '';
			        }
				}
		    });
		return o;
	},
	
	getTabId:function(indexId){
		return $($($(this).children()[0]).children()[indexId]).find("a").attr("id");
	},
	
	tabFunction:function(options,formName){
		if(null!=formName){
			$.extend(options,{
				select: function(event, ui) {
					if($("#"+formName).hasClass('ui-tabs-hide')){
						enableSaveButton(true);
					}else{
						enableSaveButton(false);
					}
				}
			});
		}
		var defaultOption={
			select: function(event, ui) {$(".tip-yellowsimple,.tip-error").remove();$("input[createMsg='true']",$(this)).attr("createMsg","false")}
		};
		$.extend(defaultOption,options);
		$(this).tabs(defaultOption);
	},

	popup:function(popDom,closeButton){
			$(this).click(function(){
				$(popDom).hide();
				$(this).parent().children(popDom).show();
				$(this).parent().css("z-index","1");
				$(popDom).bgiframe();
			});
			$(closeButton).click(function(){
				$(this).parent().parent().hide();
			});
		},
	messageTip : function(options,isShow){

			
		var self=$(this);
		var defaultOption={
			url:"#",
			show:false,
			width:120,
			cache:true,
			arrowPositionLeft:70,
			notAcceptNote:{title:"未受理短信",link:"javascript:void(0)",num:0,limits:false},
			notAcceptOnlineAppeals:{title:"未受理平台消息",link:"javascript:void(0)",num:0,limits:false},
			backlog:{title:"待办事项",link:"javascript:void(0)",num:0,limits:false},
			data:{smsReceivedBoxNum:0,personnelMessageNum:0,myNeedDoNum:0},
			sessionTimeout:7200000
		};
        $.extend(defaultOption,options);
        defaultOption.notAcceptOnlineAppeals.num=defaultOption.data.personnelMessageNum;
        defaultOption.backlog.num=defaultOption.data.myNeedDoNum;
        
        var data=defaultOption.data;
        if(!data){
        	return false;
        }
		if(data!=null){
			data.messageNum = 0;
			if(defaultOption.notAcceptNote.limits){
				 data.messageNum = data.messageNum+data.smsReceivedBoxNum;
	        };
	        if(defaultOption.notAcceptOnlineAppeals.limits){
	        	data.messageNum = data.messageNum+data.personnelMessageNum;
	        };
	       if(defaultOption.backlog.limits){
	    		data.messageNum = data.messageNum+data.myNeedDoNum;
	       };
			if(data.messageNum == 0 || data.messageNum == '0' ){
				$("#message .mesnNoticeRight").hide();
			}else{
				$("#message .mesnNoticeRight").show();
				$("#message #messageNum").html(data.messageNum);
			}
			
		}
	   
		var notAcceptNoteHtml='';
        var notAcceptOnlineAppealsHtml='';
        var backlogHtml='';
        var speaker='';
        if(defaultOption.notAcceptNote.limits){
        	notAcceptNoteHtml='<li>'+defaultOption.notAcceptNote.title +' <a href="'+defaultOption.notAcceptNote.link+'" id="noAcceptNote"><span>'+defaultOption.notAcceptNote.num+'</span></a> 条  </li>';
        };
        if(defaultOption.notAcceptOnlineAppeals.limits){
        	notAcceptOnlineAppealsHtml='<li>'+defaultOption.notAcceptOnlineAppeals.title +' <a href="'+defaultOption.notAcceptOnlineAppeals.link+'" id="unreadPlatformNews"><span>'+defaultOption.notAcceptOnlineAppeals.num+'</span></a> 条</li>';
        };
       if(defaultOption.backlog.limits){
    	    backlogHtml='<li>'+defaultOption.backlog.title +' <a href="'+defaultOption.backlog.link+'" id="backlogMessage"><span>'+defaultOption.backlog.num+'</span></a> 条<span class="speakerSign"></span></li>';
       };
       var html='<div class="message-tip">'
	    	   +'<div class="tip-top"></div>'
    	   	   +'<div class="tip-msgCon">'
	    	   +'<h3 class="tip-tit"><strong class="tip-callBells"></strong>消息提醒<a href="javascript:;" class="tip-close"></a></h3>'
	    	   +'<ul class="tip-content">'
	    	   + notAcceptNoteHtml
			   + notAcceptOnlineAppealsHtml
			   + backlogHtml
	    	   +'</ul>'
	    	   +'</div>'
	    	   +'<div class="tip-bottom"></div>'
    	   +'</div>';
       
		if($(".message-tip")[0]){
			
			//$(".message-tip").remove();
			$(".message-tip").last().show().siblings(".message-tip").remove();
		}
		
		//judgeIssueCounts();
//		$.ajax({
//			url:"issues/audioManage/judgeIssueCounts.action",
//			type:'post',
//			dataType:'json',
//			success:function(data){
//				if(data){
//				       if(defaultOption.data.myNeedDoNum>0 || isShow){
//				    		$(html).appendTo("body")//.fadeIn(300);
//				    		$(".message-tip .tip-close").click(function(){
//								$(".message-tip").remove();
//							});
//				    		//play audio
//				    		if(isShow){
//				    		}else{
//				    			playAudio();
//				    		}
//				        }else{
//				    		$(html).hide().appendTo("body");
//				        }					
//				}
//			}
//		});	
		
		if(isShow){
			$(html).appendTo("body")//.fadeIn(300);
		}
		
    	$(".message-tip .tip-close").click(function(){
			$(".message-tip").remove();
		});
	},
	dialogtip:function(option){
		var defaultOption={
			className: 'tip-error',
			showOn: 'none',
			alignTo: 'target',
			hideTimeout:0,
			showTimeout:0,
			alignX: 'center',
			alignY: 'bottom',
			offsetX: 0,
			offsetY: 5
		}
		$.extend(defaultOption,option);
		$(this).poshytip(defaultOption);
	},
	defaultTip:function(option){
		var defaultOption={
			className: 'tip-default',
			alignTo: 'target',
			hideTimeout:0,
			showTimeout:0,
			alignX: 'center',
			alignY: 'top',
			offsetX: 0,
			offsetY: 5
		}
		$.extend(defaultOption,option);
		$(this).poshytip(defaultOption);
		return $(this);
	},
	showTip:function(option){
		var self=$(this);
		var defaultOption={
			className: 'tip-yellowsimple',
			hideTimeout:0,
			showTimeout:0,
			offsetX: 5,
			offsetY: 5,
			showOn: 'focus',
			alignTo: 'target',
			alignX: 'right',
			alignY: 'center',
			positionRight:false
		};
		var outTimer,inTimer;
		$.extend(defaultOption,option);
		self.poshytip(defaultOption);
		self.hover(function(){
			clearTimeout(outTimer);
			self.poshytip('show');
		},function(){
			outTimer=setTimeout(function(){
				self.poshytip('hide')
			},3000);
			$(".tip-yellowsimple").hover(function(){
				clearTimeout(outTimer);
				self.poshytip('show');
			},function(){
				outTimer=setTimeout(function(){
					self.poshytip('hide')
				},1000);
			});
		});
		self.removeAttr("title");
	},
	inputtip:function(option){
		var inputtipName="inputtip";
		var inputtipValue=$.cookie("inputtip");
		if(inputtipValue==null){
			$.cookie(inputtipName,true, { path: '/', expires: 10 });
		};
		if(inputtipValue=='false'){
			return;
		}else{
			var self=$(this);
			var defaultOption={
				className: 'tip-yellowsimple',
				hideTimeout:0,
				showTimeout:0,
				offsetX: 5,
				offsetY: 5,
				showOn: 'focus',
				alignTo: 'target',
				alignX: 'right',
				alignY: 'center',
				liveEvents:true
			}
			$.extend(defaultOption,option);
			var tipMsg = self.attr("tipMsg");
			if(tipMsg && tipMsg!=""){
				defaultOption.content=tipMsg;
			}
			self.poshytip(defaultOption);
			self.bind("change",function(){
				$(this).poshytip('hide');
			})
			$(".tip-yellowsimple").bgiframe();
			// $(this).removeAttr("title");
		}
	},
	
	setButtonEnabled:function(enabled){
		if (enabled){
			$(this).buttonEnable();
		}else{
			$(this).buttonDisable();
		}
	},

	isButtonEnabled:function(){
		return !($(this).attr("disabled")=="true" || $(this).attr("disabled")=="disabled");
	},
	
	buttonDisable:function(){
		$(this).addClass("disabled");
	},
	buttonEnable:function(){
		$(this).removeClass("disabled");
	},
	datePicker : function(o) {
		var self = $(this);
		var dfop={
			showWeek: false,
			changeMonth: true,
			changeYear: true,
			yearSuffix: '',
			dateFormat:'yy-mm-dd',
			showButtonPanel: true,
			showClearButton:true
		};
		$.extend(dfop,o);
		if(!$("#ui-datepicker-div").attr("id")){
			$.datepicker.initialized = false;
		}
		$(this).datepicker(dfop);
	},
	dateTimePicker : function(o) {
		var self = $(this);
		var dfop={
			showWeek: false,
			changeMonth: true,
			changeYear: true,
			yearSuffix: '',
			dateFormat:'yy-mm-dd',
			showButtonPanel: true,
			showClearButton:true,
			showSecond: true,
			timeFormat: 'HH:mm:ss',
			stepHour: 1,
			stepMinute: 1,
			stepSecond: 15,
			showSecond:false,
			showMinute:true,
			currentText: '今天',
			clearText: '清除',
			closeText: '确定',
			amNames: ['上午', 'A'],
			pmNames: ['下午', 'P'],
			timeSuffix: '',
			timeOnlyTitle: '选择时间',
			timeText: '时间',
			hourText: '时：',
			minuteText: '分：',
			secondText: '秒：',
			controlType:{
				create: function(tp_inst, obj, unit, val, min, max, step){
					$('<input class="ui-timepicker-input" value="'+val+'" style="width:50%">')
						.appendTo(obj)
						.spinner({
							min: min,
							max: max,
							step: step,
							change: function(e,ui){ // key events
									// don't call if api was used and not key press
									if(e.originalEvent !== undefined)
										tp_inst._onTimeChange();
									tp_inst._onSelectHandler();
								},
							spin: function(e,ui){ // spin events
									tp_inst.control.value(tp_inst, obj, unit, ui.value);
									tp_inst._onTimeChange();
									tp_inst._onSelectHandler();
								}
						});
					return obj;
				},
				options: function(tp_inst, obj, unit, opts, val){
					if(typeof(opts) == 'string' && val !== undefined)
							return obj.find('.ui-timepicker-input').spinner(opts, val);
					return obj.find('.ui-timepicker-input').spinner(opts);
				},
				value: function(tp_inst, obj, unit, val){
					if(val !== undefined)
						return obj.find('.ui-timepicker-input').spinner('value', val);
					return obj.find('.ui-timepicker-input').spinner('value');
				}
			}
		};
		$.extend(dfop,o);
		$(this).datetimepicker(dfop);
//		if(self.val()!=''){
//			var thisValue=self.val();
//			var arr=thisValue.split(" ");
//			var date=arr[0].split("-");
//			var time=arr[1].split(":");
//			self.datetimepicker('setDate',new Date(parseInt(date[0],10),parseInt(date[1],10)-1,parseInt(date[2],10),time[0],time[1],time[2]));
//		}
		
	},
	accordionFunction:function(selector,content,option){
		// 左边菜单
		var self=$(this);
		var defaultOption={
				collapsible: true,
				header: "> " + content + " > " + selector,
				autoHeight: false
		};
		$.extend(defaultOption,option);
		self.accordion(defaultOption);
	},
	hoverDisplay:function(biaoqian){
		// 显示隐藏
	   $(this).children(biaoqian).hide();
	   $(this).hover(function(){
			$(this).children(biaoqian).stop(true,true).slideDown(400);
		},function(){
			$(this).children(biaoqian).stop(true,true).slideUp("fast");
		});
	},
	hoverChange:function(hoverClass){
		// 指向更改class
	   $(this).hover(
		  function () {
			$(this).css("cursor","hand");
			$(this).addClass(hoverClass);
		  },
		  function () {
			$(this).removeClass(hoverClass);
		  }
		);
	},
	pop:function(options){
		var self=$(this);
		var selfId=$(this).attr("id");
		var conId=selfId+new Date().getTime();
		var thisWindow = {
			l: $(window).scrollLeft(),
			t: $(window).scrollTop(),
			w: $(window).width(),
			h: $(window).height()
		};
		var defaultOption={
			className: 'tip-yellowsimple',
			hideTimeout:0,
			showTimeout:0,
			offsetX: 5,
			offsetY: 0,
			showOn: 'none',
			alignTo: 'target',
			alignX: 'right',
			alignY: 'center',
			openNew:true,
			content:function(){}
		}
		$.extend(defaultOption,options);
		var target='_blank';
		if(defaultOption.openNew!=true){
			target='_self'
		}
		if(defaultOption.content==null || defaultOption.content==""){
			defaultOption.content='<div class="popupcon" id="'+conId+'">暂无人员类型</div>';
		}else{
			defaultOption.content='<div class="popupcon" id="'+conId+'">重点关注人员：'+defaultOption.content+'</div>';
		}
		
			
		var init=function(){
			var tipMsg = self.parent().attr("title");
			if(tipMsg && tipMsg!=""){
				 self.parent().attr("title","");
			}
			self.poshytip(defaultOption);
			$(".tip-yellowsimple").bgiframe();
		};
		
		self.hover(
			function(){
				if(self.offset().left+300>thisWindow.w){
					defaultOption.alignX="left";
				}
				else{
					defaultOption.alignX="right";
				};
				init();
				self.poshytip("show");
			},
			function(){
				self.poshytip("hide");
		});
	},
	popUpMore:function(options){
		var self=$(this);
		var selfId=$(this).attr("id");
		var conId=selfId+new Date().getTime();
		var popUpCon='';
		var thisWindow = {
			l: $(window).scrollLeft(),
			t: $(window).scrollTop(),
			w: $(window).width(),
			h: $(window).height()
		};
		var defaultOption={
			data:[],
			className: 'tip-yellowsimple',
			hideTimeout:0,
			showTimeout:0,
			offsetX: 5,
			offsetY: 0,
			showOn: 'none',
			alignTo: 'target',
			alignX: 'right',
			alignY: 'center',
			openNew:true,
			imgsrc:false,
			content:function(){
				for(var i=0;i<defaultOption.data.length;i++){
					if(defaultOption.data[i].dailyDirectoryid){
						popUpCon=popUpCon+'<li><a href="'+defaultOption.data[i].url+'" id="'+selfId+'_item'+defaultOption.data[i].id+'" thisId = "'+defaultOption.data[i].id+'" dailyDirectoryId="'+defaultOption.data[i].dailyDirectoryid+'"  class="dealFileImg" target="'+target+'" title="'+defaultOption.data[i].text+'">'+defaultOption.data[i].text+'<input type="hidden" imgsrc="'+defaultOption.data[i].fileActualUrl+'"/></a></li>';
					}else if(defaultOption.imgsrc){
						if(defaultOption.data[i].id){
							popUpCon=popUpCon+'<li><a target="_blank" href="'+defaultOption.data[i].url+'" id="item'+defaultOption.data[i].id+'" thisId = "'+defaultOption.data[i].id+'" class="dealFileImg" title="'+defaultOption.data[i].text+'">'+defaultOption.data[i].text+'<input type="hidden" imgsrc="'+defaultOption.data[i].url+'"/></a></li>';
						}
					}else{
						popUpCon=popUpCon+'<li><a href="'+defaultOption.data[i].url+'" id="'+selfId+'_item'+defaultOption.data[i].id+'" thisId = "'+defaultOption.data[i].id+'"  class="dealFileImg" target="'+target+'" title="'+defaultOption.data[i].text+'">'+defaultOption.data[i].text+'<input type="hidden" imgsrc="'+defaultOption.data[i].fileActualUrl+'"/></a></li>';
					}
					setTimeout($.imageGallery,50);
				}
				return '<div class="popupcon" id="'+conId+'">'+'<a class="tip_close"></a>'+popUpCon+'</div>';
			}
		}
		$.extend(defaultOption,options);
		var target='_blank';
		if(defaultOption.openNew!=true){
			target='_self'
		}
		bindItemEvent=function(){
			for(var i=0;i<defaultOption.data.length;i++){
				var thisItem=$(".popupcon").find("li:eq("+i+") a");
				thisItem.bind("click",defaultOption.data[i].clickFun);
			};
		};

		var init=function(){
			var tipMsg = self.attr("tipMsg");
			if(tipMsg && tipMsg!=""){
				inputOption.content=tipMsg;
			}
			self.poshytip(defaultOption);
			$(".tip-yellowsimple").bgiframe();
		};
		var style=function(){
			var thisTop=$("#"+conId).closest(".tip-yellowsimple:first").css("top");
			thisTop=Number(thisTop.substring(0,thisTop.length-2));
		}
		self.bind("click",function(){// alert(33333333)
			$(".tip-yellowsimple:first").remove();
			popUpCon='';
			if(self.offset().left+300>thisWindow.w){
				defaultOption.alignX="left";
			}
			else{
				defaultOption.alignX="right";
			};
			init();
			self.poshytip("show");
			style();
			bindItemEvent();
			$("#"+conId+" .tip_close:first").bind("click",function(){
				self.poshytip("hide");
				$("#"+conId).closest(".tip-yellowsimple:first").remove();
			})
			$(document).unbind("click").click(function(e){
				if(!$(e.target).closest(".tip-yellowsimple")[0]){
					self.poshytip("hide");
					$("#"+conId).closest(".tip-yellowsimple:first").remove();
				}
			})
			$("body .ui-jqgrid-bdiv").scroll(function(){
				self.poshytip("hide");
				$("#"+conId).closest(".tip-yellowsimple:first").remove();
			})
			return false;
		});
	},
	
	previewPopupMore:function(options){
		var self=$(this);
		var selfId=$(this).attr("id");
		var conId=selfId+new Date().getTime();
		var popUpCon='';
		var thisWindow = {
			l: $(window).scrollLeft(),
			t: $(window).scrollTop(),
			w: $(window).width(),
			h: $(window).height()
		};
		var defaultOption={
			data:[],
			className: 'tip-yellowsimple',
			hideTimeout:0,
			showTimeout:0,
			offsetX: 5,
			offsetY: 0,
			showOn: 'none',
			alignTo: 'target',
			alignX: 'right',
			alignY: 'center',
			openNew:true,
			imgsrc:false,
			content:function(){
				for(var i=0;i<defaultOption.data.length;i++){
					popUpCon=popUpCon+'<li><a href="'+defaultOption.data[i].url+'" onclick="viewAttachmentPreview('+defaultOption.data[i].id+')" id="'+selfId+'_item'+defaultOption.data[i].id+'" thisId = "'+defaultOption.data[i].id+'"  class="dealFileImg" target="'+target+'" title="'+defaultOption.data[i].text+'">'+defaultOption.data[i].text+'<input type="hidden" imgsrc="'+defaultOption.data[i].fileActualUrl+'"/></a></li>';
				}
				return '<div class="popupcon" id="'+conId+'">'+'<a class="tip_close"></a>'+popUpCon+'</div>';
			}
		}
		$.extend(defaultOption,options);
		var target='_blank';
		if(defaultOption.openNew!=true){
			target='_self'
		}
		bindItemEvent=function(){
			for(var i=0;i<defaultOption.data.length;i++){
				var thisItem=$(".popupcon").find("li:eq("+i+") a");
				thisItem.bind("click",defaultOption.data[i].clickFun);
			};
		};

		var init=function(){
			var tipMsg = self.attr("tipMsg");
			if(tipMsg && tipMsg!=""){
				inputOption.content=tipMsg;
			}
			self.poshytip(defaultOption);
			$(".tip-yellowsimple").bgiframe();
		};
		var style=function(){
			var thisTop=$("#"+conId).closest(".tip-yellowsimple:first").css("top");
			thisTop=Number(thisTop.substring(0,thisTop.length-2));
		}
		self.bind("click",function(){// alert(33333333)
			$(".tip-yellowsimple:first").remove();
			popUpCon='';
			if(self.offset().left+300>thisWindow.w){
				defaultOption.alignX="left";
			}
			else{
				defaultOption.alignX="right";
			};
			init();
			self.poshytip("show");
			style();
			bindItemEvent();
			$("#"+conId+" .tip_close:first").bind("click",function(){
				self.poshytip("hide");
				$("#"+conId).closest(".tip-yellowsimple:first").remove();
			})
			$(document).unbind("click").click(function(e){
				if(!$(e.target).closest(".tip-yellowsimple")[0]){
					self.poshytip("hide");
					$("#"+conId).closest(".tip-yellowsimple:first").remove();
				}
			})
			$("body .ui-jqgrid-bdiv").scroll(function(){
				self.poshytip("hide");
				$("#"+conId).closest(".tip-yellowsimple:first").remove();
			})
			return false;
		});
	},
	
	passwordCheck:function(options){
		digitalspaghetti.password.el.passwordMinChar=null;
		digitalspaghetti.password.el.passwordStrengthBar=null;
		var self=$(this);
		var selfId=$(this).attr("id");
		var selfScore=0;
		var defaultOption={
			displayMinChar: false,
			minChar: 6,
			minCharText: '密码个数最少为 %d 个字符',
			colors: ["#ff0000", "#ff0099", "#99cc00", "#00ccff", "#00ccff"],
			scores: [20, 20, 50, 50],
			verdicts:	['弱', '弱', '中等', '强', '强'],
			raisePower: 1.4,
			debug: false,
			scoreVal:0,
			keyUp:function(selfScore){}
		}
		$.extend(defaultOption,options);
		self.pstrength(defaultOption);
		var word,score;
		self.keyup(function(){
			var word=self.attr("value");
			// defaultOption.scoreVal=self.pstrength.validationRules(word,score);
			selfScore=defaultOption.scoreVal;
			defaultOption.keyUp(selfScore);
		});
	},
	hoverEvent:function(thisEvent){
		var self=$(this);
		var selfId=self.children("a:first").attr("name");
		self.hover(
			function(){
				self.addClass("hover");
				self.children("span:first").addClass("delItem");
				self.children("span:first").bind("click",thisEvent);
			},
			function(){
				self.removeClass("hover");
				self.children("span:first").removeClass("delItem");
				self.children("span:first").unbind("click");
			}
		)
	},
	datepickers:function(o){
		var self=$(this);
		var dfop={
			defaultDate: "+1w",
			changeMonth: true,
			changeYear: true,
			showWeek:false,
			yearSuffix: '',
			numberOfMonths: 1,
			datas:'.dates',
			yearRange: '1900:2030',
			maxDate:'+0d',
			onSelect: function(selectedDate) {
				
			},
			onClose:function(selectedDate){
				var option = $(this).data("flag") == "from" ? "minDate" : "maxDate",
				instance = $( this ).data( "datepicker" ),
				date = $.datepicker.parseDate(
					instance.settings.dateFormat ||
					$.datepicker._defaults.dateFormat,
					selectedDate, instance.settings );
				if(dates.not(this).attr("value")=='' && $(this).attr("value") != ''){
					dates.not(this).focus();
				}
			}
		}
		$.extend(dfop,o);
		self.eq(0).data("flag","from");
		var dates=self.datepicker(dfop);
		self.eq(0).datepicker("option",dfop.from);
		self.eq(1).datepicker("option",dfop.to);
	},
	dateWeek:function(o){
		var self=$(this);
		var dfop={
			timer:1000
		}
		$.extend(dfop,o)
		var init=function(){
			var todayDate = new Date();
			var date = todayDate.getDate();
			var month = todayDate.getMonth() + 1;
			var year = todayDate.getFullYear();
			var weeks = [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ];
			var hh = todayDate.getHours();
			var mm = todayDate.getMinutes();
			var ss = todayDate.getTime() % 60000;
			ss = (ss - (ss % 1000)) / 1000;
			var clock = hh + ':';
			if (mm < 10){
				clock += '0';
			}
			clock += mm + ':';
			if (ss < 10){
				clock += '0';
			}
			clock += ss;
			var string;
			string = year + "-" + month + "-" + date + "," + clock;
			self.html(string);
			return string;
		}
		setInterval(init,dfop.timer);
	},
	scrollWay:function(options){
		var $this=$(this);
		var maxLength=0;
		
		dfop={
			floor:'.displayFloor',
			up:'',
			down:'',
			left:'',
			right:'',
			upLine:'1',
			leftLine:'11',
			speed:'3000',
			num:'1',
			pObject:'table',
			cObject:'tr',
			crObject:'td'
		};
		$.extend(dfop,options);
		
		/*button*/
		var _btnUp    = $("#"+dfop.up);
		var _btnDown  = $("#"+dfop.down);
		var _btnLeft  = $("#"+dfop.left);
		var _btnRight = $("#"+dfop.right);
		
		/*elements*/
		var scrollObj =$this.find(dfop.pObject);
		var floorCon=$(dfop.floor);
		
		var displayFloor=$("<ul/>")
					.addClass("floorList")
					.appendTo(floorCon);
		
		var marginUpSpace	=parseInt(scrollObj
				.find(dfop.cObject+":first")
				.css("marginTop"),10)
				+parseInt(scrollObj
						.find(dfop.cObject+":first")
						.css("marginBottom"),10);
		
		var marginLeftSpace	=parseInt(scrollObj
				.find(dfop.cObject+":first "+dfop.crObject+":first")
				.css("marginLeft"),10)
				+parseInt(scrollObj
						.find(dfop.cObject+":first "+dfop.crObject+":first")
						.css("marginRight"),10);
		
		var paddingLeftSpace=parseInt(scrollObj
				.find(dfop.cObject+":first "+dfop.crObject+":first")
				.css("paddingLeft"),10)
				+parseInt(scrollObj
						.find(dfop.cObject+":first "+dfop.crObject+":first")
						.css("paddingRight"),10);
		
		var paddingUpSpace	=parseInt(scrollObj
				.find(dfop.cObject+":first "+dfop.crObject+":first")
				.css("paddingTop"),10)
				+parseInt(scrollObj
						.find(dfop.cObject+":first "+dfop.crObject+":first")
						.css("paddingBottom"),10);
		
		var borderTopSpace	=parseInt(scrollObj
				.find(dfop.cObject+":first "+dfop.crObject+":first")
				.css("border-bottom-width"),10);
		
		var trHeight=parseInt(scrollObj
				.find(dfop.cObject+":first")
				.height(),10);
		
		var tdWidth	=parseInt(scrollObj
				.find(dfop.cObject+":first")
				.find(dfop.crObject+":first")
				.outerWidth(),10);
	
		var lineTrH=marginUpSpace+trHeight+borderTopSpace;
		var lineTdW=tdWidth;
		var m=trSingleHeightLine = dfop.upLine ? parseInt(dfop.upLine, 10):parseInt($this.height()/lineTrH,10);
		var n=tdSingleWidthLine = dfop.leftLine ? parseInt(dfop.leftLine, 10):parseInt($this.width()/lineTdW,10);
		
		var upHeight = trSingleHeightLine * lineTrH;
		var leftWidth = tdSingleWidthLine * lineTdW;
		
		var spd = dfop.speed ? parseInt(dfop.speed,10):600;
		var upCount = scrollObj.find(dfop.cObject).length/dfop.num;
		
		/*max length*/
		scrollObj.find(dfop.cObject).each(function(index){
			var thisLength=$(this).children(dfop.crObject).size();
			if(maxLength<thisLength){
				maxLength=thisLength;
			}
		});
		var leftCount=maxLength/dfop.num;
		
		/*create Floor*/
		function screateFloor(){
			var length=scrollObj.find(dfop.cObject).length;
			scrollObj.find(dfop.cObject).each(function(index){
				var index=length-index;
				var createLi=$("<li/>")
						.html("<div>第</div><div>"+index+"</div><div>层</div>")
						.appendTo(displayFloor)
						.height(lineTrH-1);
						
				createLi.children("div").css("lineHeight",lineTrH/3+'px');
				
				$(this).find(dfop.crObject).each(function(Tindex){
					var Tindex=Tindex+1;
					if(Tindex<10){
						$(this).find("input:first").attr("value",index+'0'+parseInt(Tindex,10));
					}else{
						$(this).find("input:first").attr("value",index+''+parseInt(Tindex,10));
					}
				})
			})
		}
		screateFloor();
		
		/*scroll Floor*/
		function scrollUp() {
			if (!scrollObj.is(":animated")) {
				if (m < upCount) {
					m += trSingleHeightLine;
					scrollObj.animate({ marginTop: "-=" + upHeight + "px" }, spd);
					displayFloor.animate({ 
						marginTop: "-=" + upHeight + "px" 
						}, spd);
					
					if(m==upCount){
						_btnUp.removeClass("upEnable")
							.addClass("upDisable upHover");
					}else{
						_btnUp.addClass("upEnable")
							.removeClass("upDisable");
						
						_btnDown.addClass("downEnable")
						.removeClass("downDisable");
					}
				}
			} 
		};
		function scrollDown() { 
			if (!scrollObj.is(":animated")) {
				if (m > trSingleHeightLine) {
					m -= trSingleHeightLine; 
					scrollObj.animate({
							marginTop: "+=" + upHeight + "px"
						}, spd);
					displayFloor.animate({
							marginTop: "+=" + upHeight + "px"
						}, spd);
					
					if(m==trSingleHeightLine){
						
						_btnDown.removeClass("downEnable")
						.addClass("downDisable downHover");

					}else{
						_btnDown.addClass("downEnable")
						.removeClass("downDisable");
						
			
						_btnUp.addClass("upEnable")
						.removeClass("upDisable");
						
					}
					
				}
			} 
		};
		function scrollLeft() { 
			
			if (!scrollObj.is(":animated")) {
				if (n < leftCount) {
					n += tdSingleWidthLine;
					scrollObj.animate({
							marginLeft:"-="+leftWidth+"px"
						}, spd);
					
					if(n==leftCount){
						_btnLeft.removeClass("leftEnable")
							.addClass("leftDisable leftHover");
					}else{
						_btnLeft.addClass("leftEnable")
							.removeClass("leftDisable");
						_btnRight.addClass("rightEnable")
							.removeClass("rightDisable");
					}
					
				}
			}
		};
		function scrollRight() {
			if (!scrollObj.is(":animated")) { 
				if (n > tdSingleWidthLine) {
					n -= tdSingleWidthLine; 
					scrollObj.animate({
							marginLeft: "+=" + leftWidth + "px" 
						}, spd);
					
					if(n==tdSingleWidthLine){
						_btnRight.removeClass("rightEnable")
							.addClass("rightDisable rightHover");
					}else{
						_btnRight.addClass("rightEnable")
							.removeClass("rightDisable");
						_btnLeft.addClass("leftEnable")
							.removeClass("leftDisable");
					}	
				}
			} 
		};
		function scorllLeftFunc(){
			if(n<leftCount){
				scrollLeft();
				scrollRight();
			}else{
				return false;
			}
		}
		
		function scorllRightFunc(){
			if(n>dfop.leftLine){
				scrollRight();
			}else{
				return false;
			}
		}
		
		/*triggle event*/	
		function triggleEvent(){
			_btnUp.bind("click", scrollUp); 
			_btnDown.bind("click", scrollDown); 
			_btnLeft.bind("click", scorllLeftFunc); 
			_btnRight.bind("click", scorllRightFunc); 
		};
		triggleEvent();
	}
});
$.extend({
	sigmaReportLayout:function(){
		// 列表信息 和 柱图 饼图 外层容器计算高度
		$(".SigmaReport .highcharts-container").height(
			$(".ui-layout-center").outerHeight() - $("#chartsTabs .ui-tabs-nav").outerHeight() - $("#thisCrumbs").outerHeight()-$("#globalOrgBtnMod").outerHeight()-$("#nav").outerHeight()-65
		).width(
			$(".ui-layout-center").outerWidth()-$("#chartsTabs ul:eq(0)").width() -40
		);
		
	},
	gridboxHeight:function(){
		var sigmaReportHeight=$(".ui-layout-center").outerHeight() - $("#chartsTabs .ui-tabs-nav").outerHeight() - $("#thisCrumbs").outerHeight()-$("#globalOrgBtnMod").outerHeight()-$("#nav").outerHeight()-$(".newChartsStyle .hd").outerHeight()-$(".statisticsTip").outerHeight()-30
		$(".SigmaReport").height(sigmaReportHeight);
	},
	
	// 点击隐藏input框中的文字
	showTxtValue:function(o,obj){
		var config={
				keyInputId:'',
				keyPressSearch:function(){}
		}
		$.extend(config,obj||{});
		$(o).bind("focusin",function(){
			var that=this;
			$(that).removeClass("colorAc").addClass("colorBlack");  
            if( that.value === that.defaultValue ){
            	that.value = '';
                var event_name = 'input';
                if (navigator.userAgent.indexOf("MSIE") != -1){
                	event_name = 'propertychange';
                }
                $(that).unbind(event_name).bind(event_name, function(){
                	if( that.value != that.defaultValue && that.value !=""){
	                	$(that).nextAll(".searchDel").show();
	                	$(that).nextAll(".searchDel").click(function(){              		
	                		$(that).val("").trigger("focusout");       		
	                	})
                	}
                })
            	$(that).unbind("keypress").keypress(function(e) {	
            		if (e.which =='13'){
            			config.keyPressSearch();
            		}
            	})
            }
        }).bind("focusout",function(){
        	var that=this;
            if( that.value === '' ){
            	that.value = that.defaultValue;
                $(that).nextAll(".searchDel").hide();
                $(that).removeClass("colorBlack").addClass("colorAc");
            }
        })
	}
});
$.fn.imageready = function (callback, userSettings) {
	function loaded() {
		unloadedImages--, !unloadedImages && callback()
	}
	function bindLoad() {
		this.one("load", loaded);
		if ($.browser.msie) {
			var src = this.attr("src"),
				param = src.match(/\?/) ? "&" : "?";
			param += options.cachePrefix + "=" + (new Date).getTime(), this.attr("src", src + param)
		}
	}
	var options = $.extend({}, $.fn.imageready.defaults, userSettings),
		$images = this.find("img").add(this.filter("img")),
		unloadedImages = $images.length;
	return $images.each(function () {
		var $this = $(this);
		if (!$this.attr("src")) {
			loaded();
			return
		}
		this.complete || this.readyState === 4 ? loaded() : bindLoad.call($this)
	})
};
$.fn.imageready.defaults = {
	cachePrefix: "random"
}

$.centerLayout=function(){
	function layoutFun(){
		clearTimeout(window._fileListTimer);
		$(".center-left").height($(".ui-layout-center").height()-$("#thisCrumbs").height()-$(".newNavTop").outerHeight(true)-5)
	}
	layoutFun();
	$(window).resize(function(){
		window._fileListTimer=setTimeout(function(){
			layoutFun();
		},200);
	});
}

$.fn.selFun = function(options){
	//定义常量
	var settings = $.extend({url:''},options);
	
	this.each(function() {
		//html template
		var $html = $('<span class="ui-select"><span class="default"><label class="ui_sj"><label></label></label><span></span></span><ul class="newUl"></ul></span>');
		//将下拉框隐藏，把模版插入其后
		var $this = $(this).hide().after($html);
		//声明全局变量
		var $list = $html.find('ul'),$default = $html.find('.default'),$span = $default.find('span'),$label = $default.find('label');
		//从网络加载数据
		if(settings.url){
			$.ajax({
				url: settings.url,
				dataType:'json',
				async : false,
				success: function(data){
					//得到已经存在的option个数
					var size = $this.find('option').size();
					$.each(data,function(i,option){
						//由于ie6 的bug ，不得不采用原生的方式对DOM进行操作
						$this[0].options[i+size] = new Option(option.domainName,option.id);
					});
				}
			});
		}
		
		//将option遍历到li中
		$this.find('option').each(function(){
			var $option = $(this);
			$('<li val="'+$option.val()+'"><a href="javascript:;">'+$option.text()+'</a></li>').appendTo($list);
			if($option.prop('selected') === true){
				$this.val($option.val());
				$span.text($option.text());
			}
		});
		//计算下拉框宽度
		if($span.text() === ''){
			var $li = $list.find('li').first();
			$this.val($li.attr('val'));
			$span.text($li.text());
		}
		$span.width($(this).width());
		//click 事件
		$default.width($span.outerWidth()+$label.outerWidth(true)).click(function(event){
			
			//阻止事件冒泡
			event.stopPropagation();
			if(!$list.find('li').size())
				return ;
			$list.slideToggle(200);
			
			
		});
		$html.width($default.outerWidth());
		//$list.width($default.outerWidth());
		
		$list.find('li').click(function(){
			
			var $li = $(this);
			$span.text($li.text());
			if($this.val() != $li.attr('val'))
				$this.val($li.attr('val')).change();
		}).hover(function(){
			$(this).toggleClass('active');
		});
		
		$this.change(function(){
			var index = $this[0].selectedIndex,$li = $list.find('li:eq('+index+')');
			$span.text($li.text());
		});

		$(document).click(function(){
			$list.slideUp(200);
			
		});
	});
	return this;
};
$.fn.peopleSelect=function(o){
	var self=$(this);
	var selfId=self.attr("id");
	var selfHolder=$("#holder_"+selfId);
	var dfop={
		url:''
	}
	$.extend(dfop,o)
	var init=function(){
		var coordinate={
			left:selfHolder.offset().left,
			top:selfHolder.offset().top+selfHolder.outerHeight()
		}
		var peopleBox;
		$(".peopleSelectDlg").remove();
		peopleBox=$('<div class="peopleSelectDlg" id="'+selfId+'Dlg" />').css(coordinate).appendTo("body");
		$.ajax({
			url:dfop.url,
			data:dfop.postData,
			success:function(html){
				peopleBox.html(html);
				$(".peopleSelectDlg,.changeBtn,"+"#holder_"+selfId).click(function(event){
					event.stopPropagation();
				})
			}
		});
	}
	selfHolder.next().click(init);
	if( !dfop.noTextareaClick ){
		selfHolder.click(init);
	}
	selfHolder.delegate(".closebutton","click",function(event){
		var thisLi=$(this).closest("li");
		var data=thisLi.data("data");
		self.removePersonnelCompeleteValue(data);
		thisLi.remove();
		event.stopPropagation();
	})
}
$.unique = function(arr) {
	var len = arr.length;
	for (var i=0, il = len; i < il; i++) {
	    var it = arr[i];
	    for (var j = len - 1; j>i; j--) {
	        if (arr[j] == it) arr.splice(j, 1);
	    }
	}
    return arr;
};

$.fn.selYearFun = function(options){
	//定义常量
	var settings = $.extend({url:''},options);
	
	this.each(function() {
		//html template
		var $html = $('<span class="ui-select ui-yearselect"><span class="default"><label class="ui_sj"><label></label></label><span></span></span><ul class="newUl"></ul></span>');
		//将下拉框隐藏，把模版插入其后
		var $this = $(this).hide().after($html);
		//声明全局变量
		var $list = $html.find('ul'),$default = $html.find('.default'),$span = $default.find('span'),$label = $default.find('label');
		
		var yearArray=[];
		var fullYearArray=[];
		var newArray=[];
		
		//将option生成到数组里
		$this.find('option').each(function(i){
			var $option = $(this);
			var value=$option.val();
			if(value>0){
				yearArray.push(value);
			}
		});
		for(var i=0;i<yearArray.length;i++){
			newArray.push(yearArray[i].substring(0,3));
		}
		newArray=$.unique(newArray).sort();
		for(var i=newArray.length-1;i>=0;i--){
			var $li=$('<li />');
			var $yearList='';
			for(var j=yearArray.length-1;j>=0;j--){
				if(yearArray[j].substring(0,3)===newArray[i]){
					$yearList=$yearList+'<a href="javascript:;" val="'+yearArray[j]+'">'+yearArray[j]+'</a>';
				}
			}
			$list.append($li.append($yearList));
		}
		
		if($span.text() === ''){
			var $li = $list.find('a').first();
			$this.val(-1);
			$span.text("请选择");
		}
		$span.width($(this).width());
		//click 事件
		$default.width($span.outerWidth()+$label.outerWidth(true)).click(function(event){
			//阻止事件冒泡
			event.stopPropagation();
			if(!$list.find('li').size())
				return ;
			$list.slideToggle(200);
		});
		$html.width($default.outerWidth());
		//$list.width($default.outerWidth());
		
		$list.find("a").click(function(){
			var $a = $(this);
			$span.text($a.text());
			if($this.val() != $a.attr('val'))
				$this.val($a.attr('val')).change();
			$list.find("a").removeClass("cur");
			$a.addClass("cur");
		}).hover(function(){
			$(this).parent().toggleClass('active');
		});
		$list.find("li").each(function(){
			$(this).find("a").eq(0).addClass("first");
		})
		
		$this.change(function(){
			var thisValue=$this.val();
			$span.text(thisValue);
			if(thisValue === '' || thisValue==-1){
				var $li = $list.find('a').first();
				$this.val(-1);
				$span.text("请选择");
				return false;
			}
			$list.find("a").removeClass("cur").filter("a[val="+thisValue+"]").addClass("cur");
		});
		
		$(document).click(function(){
			$list.slideUp(200);
		});
	});
	return this;
};

$.fn.countrySelect = function(options){
	var settings = $.extend({
		alph:['常用', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'],
		data:[
			
		],
		url:PATH+'/baseinfo/overseaPersonnelManage/getNationalityByFirstWord.action',
		defaultVal:'快速搜索国家...'
	},options);
	
	this.each(function() {
		var countryData=settings.data;
		var alphData=settings.alph;
		var url=settings.url;
		//html template
		var $html = $('<span class="ui-countryselect ui-yearselect"><span class="default"><label class="ui_sj"><label></label></label><span></span></span><div class="countryBox"><div class="countryBoxTop"><input type="text" class="countryInput" /></div><div class="countryBoxMenu"></div><ul class="newUl"></ul></div></span>');
		//将下拉框隐藏，把模版插入其后
		var $this = $(this).hide().after($html);
		//声明全局变量
		var $countryBox=$html.find(".countryBox"),$countryInput=$html.find(".countryInput"),$countryBoxMenu=$html.find(".countryBoxMenu"),$countryTop=$html.find(".countryTop"),$selectAlph = $html.find(".countryBoxMenu"), $list = $html.find('ul'),$default = $html.find('.default'),$span = $default.find('span'),$label = $default.find('label');
		$countryBox.css({zIndex:9999});		
		$countryInput.autocomplete({
			source: function(request, response) {
				$.ajax({
					url: "/baseinfo/overseaPersonnelManage/getNationalityByFirstWord.action",
					data:{"firstWord":request.term},
					success: function(data) {
						response($.map(data, function(item,i) {
							return {
								label: item,
								id: i
							}
						}))
					},
					error : function(){
						$.messageBox({
							message:"搜索失败，请重新登入！",
							level:"error"
						});
					}
				})
			},
			select: function(event, ui) {
				$this.val(ui.item.id);
				$span.text(ui.item.label);
			}
		});
		$html.find(".countryBox").click(function(event){
			//阻止事件冒泡
			event.stopPropagation();
		})
		
		var $alphList='';
		for(var i=0,length=alphData.length;i<length;i++){
			$alphList=$alphList+'<a href="javascript:;">'+alphData[i]+'</a>';
		}
		$countryBoxMenu.append($alphList);
		
		var $countryList='';
		for(var i=0,length=countryData.length;i<length;i++){
			$countryList=$countryList+'<li><a href="javascript:;" val="'+countryData[i].code+'" code="'+countryData[i].code+'">'+countryData[i].name+'</a></li>';
		}
		$list.append($countryList);
		
		//将option遍历到li中
		$this.find('option').each(function(){
			var $option = $(this);
			if($option.prop('selected') === true){
				$this.val($option.val());
				$span.text($option.text());
			}
		});
		
		if($span.text() === ''){
			var $li = $list.find('a').first();
			$this.val(-1);
			$span.text("请选择");
		}
		$span.width($(this).width());
		//click 事件
		$default.width($span.outerWidth()+$label.outerWidth(true)).click(function(event){
			//阻止事件冒泡
			event.stopPropagation();
			$countryBox.slideToggle(200);
		});
		$html.width($default.outerWidth());
		//$list.width($default.outerWidth());
		
		$countryInput.val(settings.defaultVal); //默认值
		
		$countryInput.focusin(function(){
			if($countryInput.val() == settings.defaultVal){
				$countryInput.attr("value","");
			}
		}).focusout(function(){
			if($countryInput.val() == ''){
				$countryInput.attr("value",settings.defaultVal);
			}	
		})
		
		$list.on("click","a",function(){
			var $a = $(this);
			if($this.find("option[value="+$a.attr('val')+"]")[0]===undefined){
				$this.append("<option value="+$a.attr('val')+">"+$a.text()+"</option>");
			}
			if($this.val() != $a.attr('val'))
				$this.val($a.attr('val')).change();
			$span.text($a.text());
			$list.find("a").removeClass("cur");
			$a.addClass("cur");
			$countryBox.slideUp(200);
		}).hover(function(){
			$(this).parent().toggleClass('active');
		});
		$list.find("li").each(function(){
			$list.find("li").eq(0).find("a").addClass("cur");
		})
		var $li = '';
		$selectAlph.find("a:first").addClass("first");
		$selectAlph.find("a").click(function(index){
			var self = $(this);
			var selfVal=$.trim(self.text());
			self.addClass("selected").siblings().removeClass("selected");	
			var $itemlist='';
			$.ajax({
				url:url+"?firstWord="+encodeURI(selfVal),
				success:function(data){
					var $itemlist='';//模拟数据
					for (name in data) {
						$li ='<li><a href="javascript:;" val="'+name+'">'+data[name]+'</a></li>';
						$itemlist=$itemlist+$li;
					}
					$list.empty().append($itemlist);		
				}
			})
		}).eq(0).click();
		
		$this.change(function(){
			var thisValue=$this.val();
			$span.text(thisValue);
			if(thisValue === '' || thisValue==-1){
				var $li = $list.find('a').first();
				$this.val(-1);
				$span.text("请选择");
				return false;
			}
			$list.find("a").removeClass("cur").filter("a[val="+thisValue+"]").addClass("cur");
		});
		$(document).click(function(){
			$countryBox.slideUp(200);
		});
	});
	return this;
};

$.statisticsAutoHeight=function(){
		var wrapHeight;
		var layout=function(){
			var wrapWidth=$(".ui-layout-center").width()-400;
			wrapHeight=($(".ui-layout-center").height()-$("#thisCrumbs").outerHeight()-$("#contentDiv > .btnbanner").outerHeight()-$(".leaderTit").outerHeight()*2)/2-28;
			var tableHeight=wrapHeight-50;
			$(".highcharts-container,.warpTable").height(wrapHeight);
			$(".highcharts-container").width(wrapWidth);
		}
		layout();
		var tableHeight=wrapHeight-50;
		$(window).resize(function(){
			layout();
		})
		return {
			wrapHeight:wrapHeight,
			tableHeight:tableHeight
		};
}
$.imageGallery=function(){
	$(".dealFileImg").each(function(){
		var fileEndName = $(this).text().substring($(this).text().lastIndexOf(".")+1,$(this).text().length).toUpperCase().trim();
		if(fileEndName == "GIF" || fileEndName == "JPG" || fileEndName == "PNG" ){
			$(this).addClass("canView");
			var imgSrc=$(this).find("input").attr("imgsrc");
			$(this).prop("href",imgSrc);
			
		}	
	})
	$('a.canView').imagegallery();
}

