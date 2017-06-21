<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath %>" />
<script>
	window.basePath = "<%=basePath%>";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎登录进销存管理系统</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery-1.7.2.js"></script>
<script src="js/cloud.js" type="text/javascript"></script>

<script language="javascript">
	$(function() {
		$('.loginbox').css({
			'position' : 'absolute',
			'left' : ($(window).width() - 692) / 2
		});
		$(window).resize(function() {
			$('.loginbox').css({
				'position' : 'absolute',
				'left' : ($(window).width() - 692) / 2
			});
		});
	});
</script>
<script type="text/javascript">
$(function(){
	$("#cancel").click(function(){
		$("#name").val("用户名");
		$("#pwd").val("密码");
	});
	$("body").keydown(function() {
	    if (event.keyCode == "13") {//keyCode=13是回车键
	        $('#login').click();
	    }
	});
	$('#login').click(function(){
		//提交表单
		var name = $("#name").val();
		var pwd = $("#pwd").val();
		//TODO 格式检查
		//发送请求
		$.ajax({
			url : "/erp/login/login",
			type : "get",
			beforeSend : function(xhr) {
				//将账号和密码放入HTTP请求的Header部分
				xhr.setRequestHeader("name", name);
				xhr.setRequestHeader("pwd", pwd);
			},
			success : function(data) {//data是服务器返回内容
				var ok = data.login;
				if(ok){//登录成功,进入index.jsp
					window.location = window.basePath + "login/toIndex";
				} else {
					//登录未成功,将返回的错误信息显示
					$("#error").html(data.error);
				}
			}
		});
	});
});
</script>
</head>

<body
	style="background-color: #1c77ac; background-image: url(images/light.png); background-repeat: no-repeat; background-position: center top; overflow: hidden;">



	<div id="mainBody">
		<div id="cloud1" class="cloud"></div>
		<div id="cloud2" class="cloud"></div>
	</div>


	<div class="logintop">
		<span>欢迎登录进销存管理系统</span>
	</div>

	<div class="loginbody">

		<span class="systemlogo"></span>

		<div class="loginbox">

			<ul>
				<li><input name="name" id="name" type="text" class="loginuser"
					value="" onclick="JavaScript:this.value=''" /></li>
				<li><input name="pwd" id="pwd" type="password" class="loginpwd"
					value="" onclick="JavaScript:this.value=''" /></li>
				<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input 
					type="submit" id="login" class="loginbtn" value="登录"  />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input  type="button" id="cancel" class="loginbtn" value="重置"/>
				</li>
				<li><span id="error" style="color:#f00;font-size: 10px;font-weight: bold;"></span></li>
			</ul>
		</div>
	</div>



</body>

</html>
