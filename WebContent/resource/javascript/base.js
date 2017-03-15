/**
 * 
 */
$.fn.extend({
	eagerJqxgrid : function(o) {
		var self = $(this);
		var selfId = self.attr("id");
		var defaultOption = {
			doPage : true,
			doSort : true,
			indexPage : 0,
			allPage: 0,
			pageSize : 20,
			postData : {},
			postDataBean : 'baseDomain',
			sortName : 'id',
			sortType : "desc",
			url : "",
			datafields : [],
			columns : [],
			editable:false,
			autoheight : true,
			columnsresize : true,
			selectionmode : "singlerow"
		}
		$.extend(defaultOption, o);
		
		var source;
		var dataAdapter;
		/* 自定义排序 */
		var customsortfunc = function(column, direction) {
			defaultOption.sortName = column;
			if (direction == 'ascending') {
				defaultOption.sortType = 'asc';
			}
			if (direction == 'descending') {
				defaultOption.sortType = 'desc';
			}
			if (defaultOption.doPage) {
				defaultOption.indexPage = 0;
			}

			$.ajax({
				url : dealTheUrl(defaultOption),
				data : defaultOption.postData,
				success : function(data) {
					if (data.dataJson) {
						dataToGrid(self, defaultOption, data, customsortfunc);
					} else {
						showMessage('warning', data);
					}
				}
			})
			self.jqxGrid('updatebounddata', 'sort');
		}
		$.ajax({
			url : dealTheUrl(defaultOption),
			data : defaultOption.postData,
			success : function(data) {
				if (data.dataJson) {
					source = {
						datatype : "json",
						datafields : defaultOption.datafields,
						sort : customsortfunc,
						id : 'id',
						localdata : data.dataJson,
					};
					dataAdapter = new $.jqx.dataAdapter(source);
					self.jqxGrid({
						altrows: true,
						theme:baseTheme,
						width:'100%',
						source : dataAdapter,
						editable:defaultOption.editable,
						selectionmode : defaultOption.selectionmode,
						sortable : defaultOption.doSort,
						columnsresize : defaultOption.columnsresize,
						autoheight : defaultOption.autoheight,
						columns : defaultOption.columns,
					});
					if (defaultOption.doPage) {
						$("#" + selfId + "Pager").empty();
						setEagerPageDiv(self, defaultOption, data,
								customsortfunc);
					}
				} else {
					showMessage('warning', data);
				}
			}
		})
	},
	getEagerJqxgridDataId:function(){
		var self = $(this);
		var rowindexes = self.jqxGrid('getselectedrowindexes');
		var ids='';
		if(rowindexes.length>0){
			for (var int = 0; int < rowindexes.length; int++) {
				if(self.jqxGrid('getrowid', rowindexes[int])<0){
					continue;
				}
				ids+=self.jqxGrid('getrowid', rowindexes[int]);
				if(int+1!=rowindexes.length){
					ids+=','
				}
			}
		}
		if(ids.length>0&&ids.substr(ids.length-1,1)==','){
			ids=ids.substr(0,ids.length-1);
		}
		return ids;
	},
	eagerButton : function(o) {
		var self = $(this);
		var defaultOption = {
			width : 60,
			height : 30,
			textImageRelation : "imageBeforeText",
			textPosition : "left",
			imgSrc : "",
			roundedCorners : 'all',
			template : 'default',
			hasImg : false
		}
		$.extend(defaultOption, o);
		if (defaultOption.hasImg) {
			self.jqxButton({
				textImageRelation : defaultOption.textImageRelation,
				textPosition : defaultOption.textImageRelation,
				imgSrc : defaultOption.textImageRelation,
				theme : baseTheme,
				width : defaultOption.width,
				height : defaultOption.height,
				roundedCorners : defaultOption.roundedCorners,
				template : defaultOption.template
			});
		}else{
			self.jqxButton({
				theme : baseTheme,
				width : defaultOption.width,
				height : defaultOption.height,
				roundedCorners : defaultOption.roundedCorners,
				template : defaultOption.template
			});
		}
	},
	eagerInput:function(o){
		var self = $(this);
		var defaultOption = {
			width : '85%',
			height : 25,
			placeHolder:''
		}
		$.extend(defaultOption, o);
		self.jqxInput({
			height : defaultOption.height,
			width : defaultOption.width,
			placeHolder:defaultOption.placeHolder,
			theme : baseTheme
		});
	},
	eagerNumberInput:function(o){
		var self = $(this);
		var defaultOption = {
			width : '85%',
			height : 25,
			inputMode:'simple',
			length:8,
			scale:0
		}
		$.extend(defaultOption, o);
		self.jqxNumberInput({
			height : defaultOption.height,
			width : defaultOption.width,
			inputMode:defaultOption.inputMode,
			theme : baseTheme,
			decimalDigits: defaultOption.scale,
			digits: defaultOption.length
		});
	}

});

function setEagerPageDiv(self, defaultOption, data, customsortfunc) {
	var selfId = self.attr("id");
	defaultOption.allPage=data.allPage;
	defaultOption.indexPage=data.indexPage;
	var leftButton = $("<div id='"
				+ selfId
				+ "PagerLeftButton' style='padding: 0px;margin: 2px 3px; float: left;'><div style='margin-left: 9px; width: 16px; height: 16px;'></div></div>");
		leftButton.find('div').addClass('jqx-icon-arrow-left');
		leftButton.width(36);
		leftButton.jqxButton({
			theme:baseTheme,
			disabled : true
		});
		var rightButton = $("<div id='"
				+ selfId
				+ "PagerRightButton' style='padding: 0px; margin: 2px 3px; float: left;'><div style='margin-left: 9px; width: 16px; height: 16px;'></div></div>");
		rightButton.find('div').addClass('jqx-icon-arrow-right');
		rightButton.width(36);
		if(data.pageSize> data.allRows){
			rightButton.jqxButton({
				theme:baseTheme,
				disabled : true
			});
		}else{
			rightButton.jqxButton({
				theme:baseTheme
			});
		}
		leftButton.appendTo($("#" + selfId + "Pager"));
		rightButton.appendTo($("#" + selfId + "Pager"));
		var label = $("<div id='"
				+ selfId
				+ "PagerText' style='font-size: 11px; margin: 4px 10px; float: right;'></div>");
		label.text(1 + data.indexPage * data.pageSize + "-"
				+ Math.min(data.allRows, (data.indexPage + 1) * data.pageSize)
				+ ' of ' + data.allRows);
		label.appendTo($("#" + selfId + "Pager"));
		$("#" + selfId + "Pager").jqxPanel({ width: '100%', height: '22px',theme:baseTheme});
		$("#" + selfId + "Pager").addClass('jqx-grid-pager');
	// update buttons states.
	var handleStates = function(event, button, className, add) {
		button.on(event, function() {
			if (add == true) {
				button.find('div').addClass(className);
			} else
				button.find('div').removeClass(className);
		});
	}
	if (defaultOption.theme) {
		handleStates('mousedown', rightButton, 'jqx-icon-arrow-right-selected-'
				+ defaultOption.theme, true);
		handleStates('mouseup', rightButton, 'jqx-icon-arrow-right-selected-'
				+ defaultOption.theme, false);
		handleStates('mousedown', leftButton, 'jqx-icon-arrow-left-selected-'
				+ defaultOption.theme, true);
		handleStates('mouseup', leftButton, 'jqx-icon-arrow-left-selected-'
				+ defaultOption.theme, false);
		handleStates('mouseenter', rightButton, 'jqx-icon-arrow-right-hover-'
				+ defaultOption.theme, true);
		handleStates('mouseleave', rightButton, 'jqx-icon-arrow-right-hover-'
				+ defaultOption.theme, false);
		handleStates('mouseenter', leftButton, 'jqx-icon-arrow-left-hover-'
				+ defaultOption.theme, true);
		handleStates('mouseleave', leftButton, 'jqx-icon-arrow-left-hover-'
				+ defaultOption.theme, false);
	}
	rightButton.on('click', function () {
		if (defaultOption.indexPage < defaultOption.allPage-1) {
		defaultOption.indexPage += 1;
			$.ajax({
				url : dealTheUrl(defaultOption),
				data : defaultOption.postData,
				success : function(data) {
					if (data.dataJson) {
							dataToGrid(self, defaultOption, data, customsortfunc);
					} else {
						showMessage('warning', data);
					}
				}
			})
		}
	});
	leftButton.on('click', function () {
		if (defaultOption.indexPage > 0) {
		defaultOption.indexPage += -1;
		$.ajax({
			url : dealTheUrl(defaultOption),
			data : defaultOption.postData,
			success : function(data) {
				if (data.dataJson) {
						dataToGrid(self, defaultOption, data, customsortfunc);
				} else {
					showMessage('warning', data);
				}
			}
		})
		}
	});
}
function dataToGrid(self, defaultOption, data, customsortfunc) {
	var source = {
		datatype : "json",
		datafields : defaultOption.datafields,
		sort : customsortfunc,
		id : 'id',
		localdata : data.dataJson
	};
	defaultOption.allPage=data.allPage;
	defaultOption.indexPage=data.indexPage;
	defaultOption.sortName = data.sortName;
	defaultOption.sortType = data.sortType;
	self.jqxGrid('source', new $.jqx.dataAdapter(source));
	if (defaultOption.doPage) {
		$("#" + self.attr("id") + "PagerText").text(
				1
						+ data.indexPage
						* data.pageSize
						+ "-"
						+ Math.min(data.allRows, (data.indexPage + 1)
								* data.pageSize) + ' of ' + data.allRows);
		var leftButton = $("#" + self.attr("id") + "PagerLeftButton");
		var rightButton = $("#" + self.attr("id") + "PagerRightButton");
		if (data.indexPage == 0) {
			leftButton.jqxButton({
				disabled : true
			});
			rightButton.jqxButton({
				disabled : false
			});
		} else if (data.allRows <= (data.indexPage + 1) * data.pageSize) {
			leftButton.jqxButton({
				disabled : false
			});
			rightButton.jqxButton({
				disabled : true
			});
		} else {
			leftButton.jqxButton({
				disabled : false
			});
			rightButton.jqxButton({
				disabled : false
			});
		}
	}
}
function dealTheUrl(defaultOption) {
	var url;
	if (defaultOption.url) {
		url = defaultOption.url;
		if (defaultOption.doPage) {
			if (url.substring(url.length - 7) == ".action") {
				url += "?" + defaultOption.postDataBean + ".indexPage="
						+ defaultOption.indexPage + "&"
						+ defaultOption.postDataBean + ".pageSize="
						+ defaultOption.pageSize + "&"
						+ defaultOption.postDataBean + ".doPage="
						+ defaultOption.doPage;
			} else {
				url += "&" + defaultOption.postDataBean + ".indexPage="
						+ defaultOption.indexPage + "&"
						+ defaultOption.postDataBean + ".pageSize="
						+ defaultOption.pageSize + "&"
						+ defaultOption.postDataBean + ".doPage="
						+ defaultOption.doPage;
			}
		} else {
			if (url.substring(url.length - 7) == ".action") {
				url += "?" + defaultOption.postDataBean + ".doPage=false";
			} else {
				url += "&" + defaultOption.postDataBean + ".doPage=false";
			}
		}
		if (defaultOption.doSort) {
			if (url.substring(url.length - 7) == ".action") {
				url += "?" + defaultOption.postDataBean + ".sortName="
						+ defaultOption.sortName + "&"
						+ defaultOption.postDataBean + ".sortType="
						+ defaultOption.sortType + "&"
						+ defaultOption.postDataBean + ".doSort="
						+ defaultOption.doSort;
			} else {
				url += "&" + defaultOption.postDataBean + ".sortName="
						+ defaultOption.sortName + "&"
						+ defaultOption.postDataBean + ".sortType="
						+ defaultOption.sortType + "&"
						+ defaultOption.postDataBean + ".doSort="
						+ defaultOption.doSort;
			}
		} else {
			if (url.substring(url.length - 7) == ".action") {
				url += "?" + defaultOption.postDataBean + ".doSort=false";
			} else {
				url += "&" + defaultOption.postDataBean + ".doSort=false";
			}
		}
	}
	return url;
}
function eagerJqxWindow(o) {
	var defaultOption = {
		id : 'newWindow',
		title : '请选择',
		url : '',
		buttonGroup : {},
		height : 500,
		weight : 700,
		closeAnimationDuration : 1000,
		draggable : true,
		position : [ 0, 0 ],
		okValue : '',
		okFunc : function() {
		}
	};
	$.extend(defaultOption, o);
	$(document.body)
			.append(
					'<div id="'
							+ defaultOption.id
							+ '"><div></div><div><div id="'
							+ defaultOption.id
							+ 'Content" style="overflow:visible;height:'
							+ (defaultOption.height - 45)
							+ 'px;width:'
							+ (defaultOption.weight-10)
							+ 'px;"></div><div><div id="'
							+ defaultOption.id
							+ 'ButtonGroup" style="height:15px;position:absolute; right:20px; bottom:20px;"></div></div></div></div>');
	$.ajax({
		url:defaultOption.url,
		async:false,
		cache:false,
		success:function(data){
			$('#' + defaultOption.id + 'Content').html("");
			$('#' + defaultOption.id + 'Content').html(data);
		}
	})
	/*$('#' + defaultOption.id + 'Content').load(defaultOption.url);*/
	if (defaultOption.okValue && defaultOption.okValue != '') {
		$('#' + defaultOption.id + 'ButtonGroup').append(
				'<input type="button" id="' + defaultOption.id + 'Ok" value="'
						+ defaultOption.okValue
						+ '" style="margin-right: 10px;" />')
	}
	$('#' + defaultOption.id + 'ButtonGroup').append(
			'<input type="button" id="' + defaultOption.id
					+ 'Cancel" value="关闭" />');

	$('#' + defaultOption.id).jqxWindow({
		theme:baseTheme,
		cancelButton : $('#' + defaultOption.id + 'Cancel'),
		title : defaultOption.title,
		height : defaultOption.height,
		width : defaultOption.weight,
		isModal:true,
		position : defaultOption.position,
		closeButtonAction : 'close',
		animationType : 'combined',
		closeAnimationDuration : defaultOption.closeAnimationDuration,
		draggable : defaultOption.draggable,
		initContent : function() {
			$('#' + defaultOption.id + 'Cancel').eagerButton();
			if (defaultOption.okValue && defaultOption.okValue != '') {
				$('#' + defaultOption.id + 'Ok').eagerButton({
				});
			}
		}
	});
	$('#' + defaultOption.id + 'Cancel').on('click', function() {
		if(defaultOption.closeFunc){
			defaultOption.closeFunc();
		}
	});
	if (defaultOption.okValue && defaultOption.okValue != '') {
		$('#' + defaultOption.id + 'Ok').on('click', function() {
			if(defaultOption.okFunc){
				defaultOption.okFunc();
			}
		});
	}
}

function showMessage(temp, message) {
	if(!$('#coreBaseNotification').length==0){
		$('#coreBaseNotification').remove();
	}
	$(document.body)
	.append('<div style="display: none;" id="coreBaseNotification"></div>');
	/*
	 * template:"info", "warning", "success", "error", "mail",
	 * "time", "null"
	 */
	$('#coreBaseNotification').html(message);
	$('#coreBaseNotification').jqxNotification({
		theme:baseTheme,
		autoOpen : true,
		template : temp,
		blink : true
	});
}
function cleanTheWindows(){
	$('.jqx-window').remove();
	$('.jqx-window-modal').remove();
}