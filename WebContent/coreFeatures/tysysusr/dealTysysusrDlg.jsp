<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<body>
	<form id="coreTysysusrDataForm"
		action="${pageContext.request.contextPath}/core/coreTysysusrAction/saveTheTysysusr.action?opt=${opt}"
		method="post">
		<input type="hidden" id="core_tysysusr_group_id" name="tysysusr.group.id" />
		<input type="hidden" id="core_tysysusr_id" name="tysysusr.id" value="${tysysusr.id}" />
		<div class="eager_container_20">
		<div class="grid_2 lable-right">
		 <em class="form-req">*</em><label>用户名:</label>
		</div>
		<div class="grid_8">
		<input type="text" id="core_tysysusr_username" name="tysysusr.username"
			value="${tysysusr.username}" /> 
		</div>
		<div class="grid_2 lable-right">
		<label>
		 姓名:</label>
		</div>
		<div class="grid_8">
		<input type="text"
			id="core_tysysusr_name" name="tysysusr.name" value="${tysysusr.name}" />
		</div>
		<div class="clearRow"></div>
		<!-- <label>用户权限组</label>
<div id="tysysusr_group_id_select"></div> -->
<div class="grid_2 lable-right">
		 <label>联系电话:</label>
		</div>
		<div class="grid_8">
		 <input type="text" id="core_tysysusr_phonenumber"
			name="tysysusr.phonenumber" value="${tysysusr.phonenumber}" />
		</div>
		<div class="grid_2 lable-right">
		<label> 邮件地址:</label>
		</div>
		<div class="grid_8">
		 <input type="text" id="core_tysysusr_email" name="tysysusr.email"
			name="tysysusr.email" value="${tysysusr.email}" />
		</div>
		<div class="grid_2 lable-right">
		 <label>备注:</label>
		</div>
		<div class="grid_18 heightAuto">
		 <textarea id="core_tysysusr_remark" name="tysysusr.remark">${tysysusr.remark}</textarea>
		</div>
		
		</div>
	</form>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$('#core_tysysusr_username').eagerInput();
		$('#core_tysysusr_name').eagerInput();
		$('#core_tysysusr_phonenumber').eagerInput();
		$('#core_tysysusr_email').eagerInput();
		$('#core_tysysusr_remark').jqxTextArea({
			height : 90,
			width : '85%'
		});

		$('#coreTysysusrDataForm').jqxValidator({
			rules : [ {
				input : '#core_tysysusr_username',
				message : '请输入',
				action : 'keyup, blur',
				rule : 'required'
			} ],
			onSuccess : function() {
				$('#coreTysysusrDataForm').ajaxSubmit({
					success : function(data) {
						if (data.id) {
							refreshCoreTysysusr();
							showMessage('success', '保存成功');
							cleanTheWindows();
						} else {
							showMessage('error', data);
						}
					}
				});
			},
			onError : function() {
				showMessage('error', '请填写比必填项');
			},
			focus : false

		});

		/* $.ajax({
			url : '${path}/',
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
						source : dataAdapter,
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
					$("#baseNotification").showMessage('warning', data);
				}
			}
		})
		$("#tysysusr_group_id_select").jqxDropDownList({
		    selectedIndex: 2, source: dataAdapter, displayMember: "ContactName", valueMember: "CompanyName", width: 200, height: 25
		});
		// subscribe to the select event.
		$("#tysysusr_group_id_select").on('select', function (event) {
		    if (event.args) {
		        var item = event.args.item;
		        if (item) {
		            var valueelement = $("<div></div>");
		            valueelement.text("Value: " + item.value);
		            var labelelement = $("<div></div>");
		            labelelement.text("Label: " + item.label);
		            $("#selectionlog").children().remove();
		            $("#selectionlog").append(labelelement);
		            $("#selectionlog").append(valueelement);
		        }
		    }
		}); */
	});

	function doSubmitThisWindow() {
		$('#coreTysysusrDataForm').jqxValidator('validate');

	}
</script>
</html>

