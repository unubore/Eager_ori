<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
	String path = request.getContextPath();
%>
<html>
<head>
<link rel="stylesheet" href="<%=path%>/resource/jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript" src="<%=path%>/resource/jquery/jquery-2.2.2.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jquery/demos.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxbuttons.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxscrollbar.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxmenu.js"></script>
<script type="text/javascript">
$(document).ready(function () {
    $.ajax({
				url:'<%=path%>/core/tysysusrAction/menu.action',
				success:function(data){
					if(data=="[]"){
						$("#eagerMenu").text("失败啦");
						//$.messageBox({message:"删除成功"});
					}else{
					  var source =
					    {
					        datatype: "json",
					        datafields: [
					            { name: 'id' },
					            { name: 'parentid' },
					            { name: 'text' },
					        ],
					        id: 'id',
					        localdata: data
					    };
					    var dataAdapter = new $.jqx.dataAdapter(source);
					    dataAdapter.dataBind();
					    var records = dataAdapter.getRecordsHierarchy('id', 'parentid', 'items', [{ name: 'text', map: 'label'}]);
					    $('#eagerMenu').jqxMenu({ source: records,  width: '200px',mode: 'vertical' });
					    $("#eagerMenu").on('itemclick', function (event) {
					    	console.log(event.args.id);
					    });
					}
				}
			});
});
</script>
</head>
<body>
 <div id="eagerMenu"></div>
</body>
</html>
