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
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxtabs.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxbuttons.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxscrollbar.js"></script>
<script type="text/javascript" src="<%=path%>/resource/jqwidgets/jqxpanel.js"></script>

<script type="text/javascript">
        $(document).ready(function () {
            $('#jqxTabs').jqxTabs({ height: document.body.offsetHeight , width: 510, showCloseButtons: true  , reorder: true, scrollPosition: 'right' , animationType: 'fade',selectionTracker:true });
            // Create and initialize Buttons
            $('#Add').jqxButton({ width: '100px'});
            $('#Remove').jqxButton({ width: '100px'});
            $('#Disable').jqxButton({ width: '100px'});
            $('#Enable').jqxButton({ width: '100px'});
            $('#EnableAll').jqxButton({ width: '100px'});
            $('#DisableAll').jqxButton({ width: '100px'});
            // Add 
            $('#Add').click(function () {
                $('#jqxTabs').jqxTabs('addLast', 'Sample title', 'abc');
                $('#jqxTabs').jqxTabs('ensureVisible', -1);
            });
            var loadPage = function (url, a) {
                $.get(url, function (data) {
                    a.text(data);
                });
            }
            $('#jqxTabs').on('selected', function (event) {
            	
                $('#jqxTabs').jqxTabs('getContentAt', event.args.item).load('tmp.ftl');
               //console.log(a);
               //a.load('menu.jsp') ;
               //loadPage('menu.jsp', a);
            });
            // Remove 
            $('#Remove').click(function () {
                var selectedItem = $('#jqxTabs').jqxTabs('selectedItem');
                var disabledItems = $('#jqxTabs').jqxTabs('getDisabledTabsCount');
                var items = $('#jqxTabs').jqxTabs('length');
                if (items > disabledItems + 1) {
                    $('#jqxTabs').jqxTabs('removeAt', selectedItem);
                }
            });
            // Disable
            $('#Disable').click(function () {
                var selectedItem = $('#jqxTabs').jqxTabs('selectedItem');
                $('#jqxTabs').jqxTabs('disableAt', selectedItem);
            });
            // Enable
            $('#Enable').click(function () {
                var selectedItem = $('#jqxTabs').jqxTabs('selectedItem');
                $('#jqxTabs').jqxTabs('enableAt', selectedItem);
            });
            // EnableAll All
            $('#EnableAll').click(function () {
                $('#jqxTabs').jqxTabs('enable');
            });
            // DisableAll All
            $('#DisableAll').click(function () {
                $('#jqxTabs').jqxTabs('disable');
            });
            $('#jqxTabs').on('tabclick', function (event) 
            		{ 
            		    var clickedItem = event.args.item; 
            		    
            		}); 
        });
    </script>
</head>

<body class='default'>
    <div id='jqxWidget'>
        <div style='float: left;'>
            <div id='jqxTabs'>
            <ul style='margin-left: 10px;'>
                    <li>欢迎</li>
                </ul>
                <div>
                    主菜单
                </div>
              </div> 
        </div>
    </div>
    <div style='margin-left: 60px; float: left;'>
        <div style='margin-top: 10px;'>
            <input type="button" id='Add' value="Add" />
        </div>
        <div style='margin-top: 10px;'>
            <input type="button" id='Remove' value="Remove" />
        </div>
        <div style='margin-top: 10px;'>
            <input type="button" id='Disable' value="Disable" />
        </div>
        <div style='margin-top: 10px;'>
            <input type="button" id='Enable' value="Enable" />
        </div>
        <div style='margin-top: 10px;'>
            <input type="button" id='EnableAll' value="Enable All" />
        </div>
        <div style='margin-top: 10px;'>
            <input type="button" id='DisableAll' value="Disable All" />
        </div>
    </div>
</body>
</html>
