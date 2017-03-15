<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:include page="/base.jsp" />
<!DOCTYPE HTML>
<html>
<body>
	<div>
		<h3>用户管理</h3>
	</div>
	<div>
		<div class="eager_buttonGroupLeft">
			<input type="button" value="新增" id='core_tysysusr_add' /> <input type="button" value="修改" id='core_tysysusr_update' /> <input type="button" value="删除" id='core_tysysusr_delete' />
		</div>
		<div class="eager_buttonGroupRight">
			<input type="button" value="查询" id='core_tysysusr_query' /> <input type="button" value="重置" id='core_tysysusr_fresh' />
		</div>
	</div>
	<div>
		<div style="width: 100%">
			<div id="coreTysysusrList"></div>
			<div id="coreTysysusrListPager"></div>
		</div>
	</div>
	<div id="coreTysysusrQueryBox">
		<div class="eager_container_20 eager_queryGroupLeft">
			<div class="grid_2">
				<label>姓名</label>
			</div>
			<div class="grid_8">
				<input type="text" id="coreTysysusrVoname" />
			</div>
		</div>
		<div class="eager_queryGroupRight">
			<div class="queryButton">
				<input type="button" value="查询" id="core_tysysusr_doQuery" />
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var defaultOption = {
		url : basepath + "/core/tysysusrAction/findAllUsers.action", postDataBean : "tysysusrVo", selectionmode : "checkbox", datafields : [ {
			name : 'id', type : 'int'
		}, {
			name : 'username', type : 'string'
		}, {
			name : 'password', type : 'string'
		}, {
			name : 'name', type : 'string'
		}, {
			name : 'phonenumber', type : 'string'
		}, {
			name : 'email', type : 'string'
		}, {
			name : 'group.name', type : 'string'
		}, {
			name : 'remark', type : 'string'
		} ], columns : [ {
			text : '操作', datafield : 'yemiancaozuo', width : '8%', sortable : false, align : 'center', resizable : false, createwidget : function(row, column, value, cellElement){
				var id = $('#coreTysysusrList').jqxGrid('getrowid', row.boundindex);
				if (id > 0) {
					var deleteButton = $("<input type='button' value='删除'></input>");
					var updateButton = $("<input type='button' value='修改'></input>");
					updateButton.eagerButton({
						width : column.width / 2 - 3, height : row.height - 4
					});
					deleteButton.eagerButton({
						width : column.width / 2 - 3, height : row.height - 4
					});
					updateButton.css({
						"margin" : "2px", "margin-left" : "2px", "padding" : "0px", "left" : "0%", "top" : "0%", "position" : "absolute", "opacity" : "0.99"
					});
					deleteButton.css({
						"margin-top" : "2px", "margin-right" : "2px", "padding" : "0px", "right" : "0%", "top" : "0%", "position" : "absolute", "opacity" : "0.99"
					});
					updateButton.on('click', function(){
						updateCoreTysysusr(id);
					});
					deleteButton.on('click', function(){
						deleteCoreTysususr(id);
					});
					updateButton.appendTo(cellElement);
					deleteButton.appendTo(cellElement);
				}
			}, initwidget : function(row, column, value, cellElement){
			}
		}, {
			text : 'ID', datafield : 'id', hidden : true
		}, {
			text : '用户名', datafield : 'username'
		}, {
			text : '密码', datafield : 'password'
		}, {
			text : '姓名', datafield : 'name'
		}, {
			text : '手机号码', datafield : 'phonenumber'
		}, {
			text : '邮件地址', datafield : 'email'
		}, {
			text : '用户组', datafield : 'group.name'
		}, {
			text : '备注', datafield : 'remark'
		} ]
	};
	$(document).ready(
						function(){
							$("#coreTysysusrList").eagerJqxgrid(defaultOption);
							$('#coreTysysusrList').on('celldoubleclick', function(event){
								if (event.args.datafield != 'yemiancaozuo') {
									var id = $('#coreTysysusrList').jqxGrid('getrowid', event.args.rowindex);
									if (id > 0) {
										eagerJqxWindow({
											id : "coreTysysusrDlg", title : '浏览详情', url : basepath + '/core/tysysusrAction/dispatch.action?opt=read&tysysusrId=' + id
										})
									}
								}
							});
							$("#coreTysysusrQueryBox").jqxPopover(
																	{
																		theme : baseTheme, offset : {
																			left : -210, top : 0
																		}, arrowOffsetValue : 210, width : 600, height : 80, isModal : false, position : "bottom", title : "查询条件",
																		showCloseButton : true, selector : $("#core_tysysusr_query"), initContent : function(){
																			$("#core_tysysusr_doQuery").eagerButton();
																			$("#coreTysysusrVoname").eagerInput();
																		}
																	});
							$('#core_tysysusr_doQuery').on('click', function(){
								defaultOption.postData = {
									'tysysusrVo.name' : $("#coreTysysusrVoname").val()
								};
								$("#coreTysysusrList").eagerJqxgrid(defaultOption);
								$('#tysysusrQueryBox').jqxPopover('close');
							});
							$("#core_tysysusr_delete").eagerButton();
							$("#core_tysysusr_query").eagerButton();
							$("#core_tysysusr_add").eagerButton();
							$("#core_tysysusr_update").eagerButton();
							$("#core_tysysusr_fresh").eagerButton();

							$("#core_tysysusr_add").on('click', function(){
								eagerJqxWindow({
									id : "coreTysysusrDlg", title : '新增用户', url : basepath + '/core/tysysusrAction/dispatch.action?opt=add', okValue : '保存', okFunc : function(){
										doSubmitThisWindow();
									}, closeFunc : function(){
										refreshCoreTysysusr();
									}
								})

							});
							$("#core_tysysusr_update").on('click', function(){
								var ids = $("#coreTysysusrList").getEagerJqxgridDataId();
								if (ids.length == 0) {
									showMessage('warning', '请至少选择一条记录');
								} else if (ids.indexOf(',') > 0) {
									showMessage('warning', '请选择一条记录');
								} else {
									updateCoreTysysusr(ids);
								}
							});
							$("#core_tysysusr_delete").on('click', function(){
								var ids = $("#coreTysysusrList").getEagerJqxgridDataId();
								if (ids.length == 0) {
									showMessage('warning', '请至少选择一条记录');
								} else {
									deleteCoreTysususr(ids);
								}
							});
							$("#core_tysysusr_fresh").on('click', function(){
								refreshCoreTysysusr();
							});
						})

	function refreshCoreTysysusr(){
		defaultOption.postData = {};
		$("#coreTysysusrList").eagerJqxgrid(defaultOption);
	}
	function updateCoreTysysusr(id){
		eagerJqxWindow({
			id : "coreTysysusrDlg", title : '修改用户', url : basepath + '/core/tysysusrAction/dispatch.action?opt=update&tysysusrId=' + id, okValue : '保存', okFunc : function(){
				doSubmitThisWindow();
			}, closeFunc : function(){
				refreshCoreTysysusr();
			}
		})
	}
	function deleteCoreTysususr(ids){
		if (confirm("删除后不能恢复，确定要删除吗?")) {
			$.ajax({
				url : basepath + '/core/tysysusrAction/deleteUsersByIds.action?ids=' + ids, success : function(data){
					if (data == true || data == "true") {
						showMessage('success', '删除成功');
					} else {
						showMessage('error', data);
					}
					refreshCoreTysysusr()
				}
			});
		}
	}
</script>
</html>
