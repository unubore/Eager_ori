<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>

<html>
<head>
<style type="text/css">
.iframeGo{border-style: none}
</style>
<link rel="stylesheet" href="<%=path%>/resource/jqwidgets/styles/jqx.base.css" type="text/css" />
<link rel="stylesheet" href="<%=path%>/resource/jqwidgets/styles/jqx.light.css" type="text/css" />

<script type="text/javascript" src="<%=path%>/resource/jquery/jquery-2.2.2.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jquery/demos.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxbuttons.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxscrollbar.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxmenu.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxsplitter.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxtabs.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxpanel.js"></script>
<script type="text/javascript">
var basePath="<%=path%>";

$(document).ready(function () {
	var width = document.body.offsetWidth-2;
	var height = document.body.offsetHeight-2;
    $('#splitContainer').jqxSplitter({ width: width, height: height, orientation: 'horizontal',resizable:false,showSplitBar:false, panels: [{ size: 100 }] });
    $('#splitter').jqxSplitter({ width: width, height: height,resizable:false,showSplitBar:false, panels: [{ size: 150 }] });
    $('#eagerMenuTabs').jqxTabs({ height: height-102 , width: width-175, showCloseButtons: true  , reorder: true, scrollPosition: 'right' , animationType: 'fade',selectionTracker:true,scrollable:false });
    $.ajax({
				url:'<%=path%>/core/tysysusrAction/findTysysperByUser.action',
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
					            { name: 'parent.id' },
					            { name: 'showtext' },
					        ],
					        id: 'id',
					        localdata: data
					    };
					    var dataAdapter = new $.jqx.dataAdapter(source);
					    dataAdapter.dataBind();
					    var records = dataAdapter.getRecordsHierarchy('id', 'parent.id', 'items', [{ name: 'showtext', map: 'label'}]);
					    
					    $('#eagerMenu').jqxMenu({ source: records,  width: 150,height:height-102,mode: 'vertical' });
					    $("#eagerMenu").on('itemclick', function (event) {
    							goTheMenu(event.args.id);
						});
					}
				}
		});
    /*   $('#eagerMenuTabs').on('add', function (event) {
          //$('#eagerMenuTabs').jqxTabs('getContentAt', event.args.item).load('/Eager_ori/coreFeatures/tysysusr/tysysusr.jsp');
           //var urlObj = $("<div></div>");
           //urlObj.load('/Eager_ori/coreFeatures/tysysusr/tysysusr.jsp');
           //$('#eagerMenuTabs').jqxTabs('getContentAt', event.args.item).append(urlObj);

          
    });  */
});
function goTheMenu(id){
	 $.ajax({
			url:'<%=path%>/core/tysysusrAction/getTysysperById.action?menuId='+id,
			success : function(data) {
				 var cunzai=false;
				 var tabLength = $('#eagerMenuTabs').jqxTabs('length');
				 for (var i = 0; i < tabLength; i++) {
					if($('#eagerMenuTabs').jqxTabs('getTitleAt', i)==data.showtext){
						cunzai=true;
						$('#eagerMenuTabs').jqxTabs('select', i);
					}
				}
				 if(!cunzai){
					 if(data.url){
						 $('#eagerMenuTabs').jqxTabs('addLast', data.showtext,'');
						 $('#eagerMenuTabs').jqxTabs('getContentAt', tabLength).append("<iframe width='100%' frameborder='0' height='100%;'  src='"+basePath+data.url+"'></iframe>");
			             $('#eagerMenuTabs').jqxTabs('ensureVisible', -1);
		             }
				 }
			}
		});
}
</script>
</head>
<body>
<body class='default'>
    <div style="border-color: white;" id="splitContainer">
        <div>
            <iframe class="iframeGo" style="width: 100%;" src="top.jsp"  ></iframe>
        </div>
        <div>
            <div style="border-color: white;margin-left: 10px;" id="splitter">
                <div   id="eagerMenu" ></div>
                <div>
                    <div style="margin-left: 5px;" id="eagerMenuTabs">
						<ul style='margin-left: 10px;'>
							<li>欢迎</li>
						</ul>
						<div>主菜单</div>
					</div>
                </div>
            </div>
        </div>
    </div>
</body>


<!-- <body class=''>
    <div id=''>
        <div id="mainSplitter" style="border-color: white;">
            <div class="splitter-panel">
               <iframe class="iframeGo" style="width: 100%;" src="top.jsp"  ></iframe></div>
            <div id="branchSplitter" class="splitter-panel" style="margin-left: 10px;">
               <div   id="eagerMenu" ></div>
               <div   id="eagerPageinfo" >
                    <iframe class="iframeGo" style="width: 100%;" src="pageinfo.jsp"  ></iframe></div>
               </div> 
        </div>
    </div>
</body>
 -->

<!-- <main>


<div style="">
 
 <div><iframe class="iframeGo" style="" src="pageinfo.jsp"   ></iframe></div>
</div>
</main> -->
	</body>
</html>
