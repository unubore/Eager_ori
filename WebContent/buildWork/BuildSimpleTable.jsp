<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:include page="/base.jsp" />
<!DOCTYPE HTML>
<html>
<body>
	<h3>单表操作</h3>
	<form id="buildSimpleTableForm" action="">
		<input type="hidden" id="tables_tysysper_parent_id" name="tables.tysysper.parent.id" />
		 <input type="hidden" id="buildTableJsonData" name="jsonData" />
		<div class="eager_container_20">
			<div class="grid_2 lable-right">
				<em class="form-req">*</em><label>功能标识:</label>
			</div>
			<div class="grid_3">
				<input type="text" id="tables_tysysper_name" name="tables.tysysper.name" />
			</div>
			<div class="grid_2 lable-right">
				<em class="form-req">*</em><label>功能显示名:</label>
			</div>
			<div class="grid_3">
				<input type="text" id="tables_tysysper_showtext" name="tables.tysysper.showtext" />
			</div>
			<div class="grid_2 lable-right">
				<label>所属功能页:</label>
			</div>
			<div class="grid_3">
				<div id="tables_tysysper_parent_dropDown">
					<div style="border: none;" id='tables_tysysper_parent_dropDown_jqxTree'></div>
				</div>
			</div>
			<div class="grid_1"></div>
			<div class="grid_3">
				<input type="button" id="buildSumbitButton" value="提交" />
			</div>
			<div class="clearRow"></div>
			<div class="grid_2 lable-right">
				<label>表名:</label>
			</div>
			<div class="grid_3">
				<input type="text" id="tables_tableName" name="tables.tableName" />
			</div>
			<div class="grid_2 lable-right">
				<label>页面标题:</label>
			</div>
			<div class="grid_3">
				<input type="text" id="tables_pageTitle" name="tables.pageTitle" />
			</div>
			<div class="grid_2">
				<input id="tables_doAdd" name="tables.doAdd" type="checkbox" />添加功能
			</div>
			<div class="grid_2">
				<input id="tables_doUpdate" name="tables.doUpdate" type="checkbox" />修改功能
			</div>
			<div class="grid_2">
				<input id="tables_doDelete" name="tables.doDelete" type="checkbox" />删除功能
			</div>
			<div class="clearRow"></div>
			<div class="grid_2 lable-right">
				<label>查询过滤:</label>
			</div>
			<div class="grid_15">
				<input type="text" id="tables_selectSqlFilter" name="tables.selectSqlFilter" />
			</div>
			<div class="clearRow"></div>
			<div class="grid_2 lable-right">
				<label>删除验证:</label>
			</div>
			<div class="grid_15">
				<input type="text" id="tables_deleteValidateFilter" name="tables.deleteValidateFilter" />
			</div>
			<div class="clearRow"></div>
		</div>
		<div style="width: 100%">
			<div id="buildList"></div>
		</div>
	</form>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		
		
		$('#buildSumbitButton').eagerButton();
		$('#tables_tysysper_showtext').eagerInput();
		$('#tables_tysysper_name').eagerInput();
		$('#tables_tableName').eagerInput();
		$('#tables_pageTitle').eagerInput();
		$('#tables_deleteValidateFilter').eagerInput();
		$('#tables_selectSqlFilter').eagerInput();
		$("#tables_doAdd").jqxCheckBox({
			width : '85%', height : 25, theme : baseTheme
		});
		$("#tables_doUpdate").jqxCheckBox({
			width : '85%', height : 25, theme : baseTheme
		});
		$("#tables_doDelete").jqxCheckBox({
			width : '85%', height : 25, theme : baseTheme
		});
		var sourceDlst;
		var dataAdapterDlst;
		$.ajax({
			url : basepath + '/biuld/buildThisWorldAction/getAllDlsts.action', success : function(data){
				sourceDlst = {
					datatype : "json", datafields : [ {
						name : 'name'
					}, {
						name : 'showtext'
					} ], localdata : data
				};
				dataAdapterDlst = new $.jqx.dataAdapter(sourceDlst);

			}
		})
		$.ajax({
			url : basepath + '/biuld/buildThisWorldAction/getAllpermissions.action', success : function(data){
				if (data != "[]") {
					var source = {
						datatype : "json", datafields : [ {
							name : 'id'
						}, {
							name : 'parent.id'
						}, {
							name : 'name'
						}, ], id : 'id', localdata : data
					};
					var dataAdapter = new $.jqx.dataAdapter(source);
					dataAdapter.dataBind();
					var records = dataAdapter.getRecordsHierarchy('id', 'parent.id', 'items', [ {
						name : 'name', map : 'label'
					} ]);
					$('#tables_tysysper_parent_dropDown_jqxTree').jqxTree({
						source : records, theme : baseTheme
					});
					$("#tables_tysysper_parent_dropDown").jqxDropDownButton({
						theme : baseTheme, width : '85%', height : 25
					});
					$('#tables_tysysper_parent_dropDown_jqxTree').on('itemClick', function(event){
						var args = event.args;
						var item = $('#tables_tysysper_parent_dropDown_jqxTree').jqxTree('getItem', args.element);
						if ($("#tables_tysysper_parent_dropDown_showText") && $("#tables_tysysper_parent_dropDown_showText").text() == item.label) {
							$("#tables_tysysper_parent_dropDown").jqxDropDownButton('setContent', '');
							$("#tables_tysysper_parent_id").val('');
						} else {
							$("#tables_tysysper_parent_id").val(item.id);
							var dropDownContent = '<div id="tables_tysysper_parent_dropDown_showText" style="position: relative; margin-left: 3px; margin-top: 5px;">' + item.label + '</div>';
							$("#tables_tysysper_parent_dropDown").jqxDropDownButton('setContent', dropDownContent);
						}
					});
				}
			}
		});
		var sourceTables;
		$.ajax({
			url : basepath + '/biuld/buildThisWorldAction/getAllTables.action', success : function(data){
				sourceTables=data;
					$('#tables_tableName').jqxInput({
						source : sourceTables
					});
			}
		});

		var defaultOption = {
			url : '', doSort : false, doPage : false, editable : true, datafields : [ {
				name : 'columnName', type : 'string'
			}, {
				name : 'comment', type : 'string'
			}, {
				name : 'dataType', type : 'string'
			}, {
				name : 'notNull', type : 'bool'
			} , {
				name : 'dataLength', type : 'int'
			} , {
				name : 'dataScale', type : 'int'
			} ], columns : [ {
				text : '列名', datafield : 'columnName', editable : false
			}, {
				text : '中文名', datafield : 'comment'
			}, {
				text : '数据类型', datafield : 'dataType', editable : false
			}, {
				text : '必填', datafield : 'notNull', columntype : 'checkbox', editable : true
			}, {
				text : '长度', datafield : 'dataLength', columntype : 'checkbox', editable : true,columntype:'numberinput'
			}, {
				text : '精度', datafield : 'dataScale', columntype : 'checkbox', editable : true,columntype:'numberinput'
			}, {
				text : '关联表', datafield : 'relateTable', editable : true, columntype : 'textbox', createeditor : function(row, value, editor){
					editor.jqxInput({
						source : sourceTables
					});
				}
			}, {
				text : '关联表隐藏对比项', datafield : 'relateTableHide', hidden : true
			}, {
				text : '关联显示用字段', datafield : 'relateTableShowColumns', columntype : 'custom', editable : true, initeditor : function(row, cellvalue, editor, celltext, pressedChar){
					var tablename = $("#buildList").jqxGrid('getcelltext', row, "relateTable");
					var oldTablename = $("#buildList").jqxGrid('getcelltext', row, "relateTableHide");
					var width = $('#buildList').jqxGrid('getcolumnproperty', 'relateTableShowColumns', 'width');
					if ((tablename && tablename.length != 0 && (!oldTablename || (oldTablename && oldTablename.length != 0 && oldTablename != tablename)))) {
						$("#buildList").jqxGrid('setcellvalue', row, "relateTableHide", tablename);
						if (tablename == 'TYJCDICT') {
							editor.jqxInput({
								width : width - 2
							});
						} else if (tablename == 'TYJCDLST') {
							editor.jqxDropDownList({
								width : width - 2, source : dataAdapterDlst, displayMember : "showtext", valueMember : "showtext"
							});
						} else {
							$.ajax({
								url : basepath + '/biuld/buildThisWorldAction/getSimpleColumnsByTablename.action?tablename=' + tablename, success : function(data){

									editor.jqxDropDownList({
										width : width - 2, source : data, checkboxes : true
									});
								}
							});
						}
					}
				}
			}, {
				text : '是否编辑', datafield : 'doEdit', columntype : 'checkbox', editable : true
			}, {
				text : '编辑序列', datafield : 'editQueue', editable : true,columntype:'numberinput',validation: function (cell, value) {
					var isEdit = $("#buildList").jqxGrid('getcelltext', cell.row, "isEdit");
					if(isEdit&&isEdit=='1'){
						if (value < 0) {
				            return { result: false, message: "当确认编辑后，序列为必填项" };
				        }
				        return true;
					}
					return true;
			    }
			}, {
				text : '编辑宽度格子', datafield : 'editWidth', editable : true,columntype:'numberinput',validation: function (cell, value) {
					var isEdit = $("#buildList").jqxGrid('getcelltext', cell.row, "isEdit");
					if(isEdit&&isEdit=='1'){
						if (value < 0) {
				            return { result: false, message: "当确认编辑后，格子为必填项" };
				        }
				        return true;
					}
					return true;
			    }
			}, {
				text : '是否显示', datafield : 'doShow', columntype : 'checkbox', editable : true
			}, {
				text : '显示序列', datafield : 'showQueue', editable : true,columntype:'numberinput',validation: function (cell, value) {
					var isShow = $("#buildList").jqxGrid('getcelltext', cell.row, "isShow");
					if(isShow&&isShow=='1'){
						if (value < 0) {
				            return { result: false, message: "当确认显示后，序列为必填项" };
				        }
				        return true;
					}
					return true;
			    }
			}, {
				text : '显示宽度百分比', datafield : 'showWidth', editable : true,columntype:'numberinput',validation: function (cell, value) {
					var isShow = $("#buildList").jqxGrid('getcelltext', cell.row, "isShow");
					if(isShow&&isShow=='1'){
						if (value < 0) {
				            return { result: false, message: "当确认显示后，百分百为必填项" };
				        }
				        return true;
					}
					return true;
			    }
			}, {
				text : '是否查询', datafield : 'doQuery', columntype : 'checkbox', editable : true
			}, {
				text : '查询序列', datafield : 'queryQueue', editable : true,columntype:'numberinput',validation: function (cell, value) {
					var isQuery = $("#buildList").jqxGrid('getcelltext', cell.row, "isQuery");
					if(isQuery&&isQuery=='1'){
						if (value < 0) {
				            return { result: false, message: "当确认查询后，序列为必填项" };
				        }
				        return true;
					}
					return true;
			    }
			}, {
				text : '是否排序', datafield : 'doSort', columntype : 'checkbox', editable : true
			} ]
		};

		$('#tables_tableName').on('change', function(){
			defaultOption.url = basepath + "/biuld/buildThisWorldAction/getAllColumnsByTablename.action?tablename=" + $('#tables_tableName').val();
			$("#buildList").eagerJqxgrid(defaultOption);
		});
		
		$('#buildSumbitButton').on('click', function () { 
			$('#buildSimpleTableForm').attr("action", basepath + "/biuld/buildThisWorldAction/buildSimpleTable.action");
			$('#buildSimpleTableForm').jqxValidator('validate');
		}); 
		
		$('#buildSimpleTableForm').jqxValidator({
			rules : [ {
				input : '#tables_tysysper_name',
				message : '请输入',
				action : 'keyup, blur',
				rule : 'required'
			},{
				input : '#tables_tysysper_showtext',
				message : '请输入',
				action : 'keyup, blur',
				rule : 'required'
			}  ],
			onSuccess : function() {
				if(buildTheDataGridToString()){
					
				}
				 $('#buildSimpleTableForm').ajaxSubmit({
					success : function(data) {
						/* if (data.id) {
							refreshCoreTysysusr();
							showMessage('success', '保存成功');
							cleanTheWindows();
						} else {
							showMessage('error', data);
						} */
					}
				}); 
			},
			onError : function() {
				showMessage('error', '请填写比必填项');
			},
			focus : false

		});
		function buildTheDataGridToString(){
			alert('1');
			//$("#buildList").
			console.log($("#tables_tableName").val());
			if($("#tables_tableName").val().length>0){
				alert('2');
				var rowidS=$("#buildList").jqxGrid('getboundrows');
				//console.log(rowidS);
				$("#buildTableJsonData").val(JSON.stringify(rowidS))
				return true;
			}else{
				return true;
			}
			
		}
	});
</script>
</html>