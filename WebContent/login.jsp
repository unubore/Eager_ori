<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<%
	String path = request.getContextPath();
	
%>
<head>
<%-- <link rel="stylesheet" type="text/css"
	href="<%=basePath%>/newstyles/config.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/newstyles/right.css" /> --%>

<script type="text/javascript"
	src="<%=path%>/resource/jquery/jquery-2.2.2.js"></script>
<script type="text/javascript"
	src="<%=path%>/resource/jquery/jquery.md5.js"></script>
<script type="text/javascript"
	src="<%=path%>/resource/jquery/jquery.cookie.js"></script>
<script type="text/javascript"
	src="<%=path%>/resource/jquery/jquery.poshytip.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/resource/jquery/jquery.bgiframe.js"></script>
<script type="text/javascript"
	src="<%=path%>/resource/jquery/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=path%>/resource/jquery/jquery.validate.js"></script>
<script type="text/javascript"
	src="<%=path%>/resource/javascript/formValidate.js"></script>
<script type="text/javascript"
	src="<%=path%>/resource/javascript/widget.js"></script>
<script type="text/javascript">
 var imagePath = "<%=path%>/images";
 var scriptPath = "<%=path%>/scripts";
</script>

</head>
<body>
	<div class="">
		<form method="post" action="<%=path%>/core/tysysusrAction/login.action"
			id="loginForm">
			<label> <span>用户账号:</span> <input name="tysysusr.username"
				type="text" id="username" class="">
			</label> <label > <span>用户密码:</span> <input
				name="tysysusr.password" type="password" id="password">
			</label>
			<div style="display: block; margin: 10px 0 0 45px;">
				<input type="submit" value="登录" id="submithref" class=""
					name="submithref">
				<!-- <input type="reset" value="" id="jsdl_resize" class=""> -->
			</div>
		</form>
	</div>
	<%-- 	<%--div模式显示
	<%@ include file="/common/popupdiv.jsp"%> --%>
</body>
<script type="text/javascript">



$(function(){
	
    $("#username").focus();

    $("#submithref").click(function(){
        $('#loginForm').submit();
    });
    $("#username,#password").bind("keydown",function(evt){
        var evt = window.event?window.event:evt;
        if(evt.keyCode==13){
            $('#loginForm').submit();
        }
    });

    $("#loginForm").formValidate({
        submitHandler: function(form) {
    	/* $("#loginInfo").html('<span>系统登录中...</span>'); */
        $("#submithref").attr('disabled', true);
        $("#password").val($.md5($("#password").val()));
        /* $("#password").val($.md5($("#password").val())); */
            $(form).ajaxSubmit({
                success:function(data){
                    $("#submithref").attr('disabled', false);
                    if(data.id){
                        document.location.href="<%=path%>/index.jsp";
                    }else{
                    	alert(data);
                    }
                }
            });
            return false;
        },
   
        rules: {
            userName:{
                required:true
                //isDigitStrAndUnderline:true
            },
            password:{
                required:true
            }
        },
        messages: {
            userName:{
                required:"用户名不能为空！"
                //isDigitStrAndUnderline:"用户名只能由数字、字母、下划线组成"
            },
            password:{
                required:"密码不能为空！"
            }
        }
    });


});
</script>
</html>
