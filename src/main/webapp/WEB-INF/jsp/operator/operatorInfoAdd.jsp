﻿<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<base href="<%=basePath%>" />
<script>
    window.basePath = "<%=basePath%>";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>员工信息添加</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="erp_js/operator/operatorInfoEdit.js"></script>
</head>

<body>
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li>系统管理</li>
            <li>员工管理</li>
            <li>员工信息添加</li>
        </ul>
    </div>

	<form action="operator/operatorAdd" onsubmit="return doSubmit();" method="post" class="main_form">
	    <div class="formbody">
	        <div class="formtitle">
	            <span>员工基本信息</span>
	        </div>
	        <ul class="forminfo">
	            <li><label>员工账号</label><input name="name" id="name" type="text" class="dfinput" style="width: 190px;" />
	                <div class="validate_msg_short" id="name_error"></div>
	            </li>
	            <li><label>账号密码</label><input name="pwd" id="pwd" type="password" class="dfinput" style="width: 190px;" />
	                <div class="validate_msg_short" id="pwd_error"></div>
	            </li>
	            <li><label>真实姓名</label><input name="realname" id="realname" type="text" class="dfinput" style="width: 190px;" />
	                <div class="validate_msg_short" id="realname_error"></div>
	            </li>
	            <li><input type="submit" class="btn" value="保存" /> 
	            <input type="button" class="btn" value="取消" onclick="javascript:history.go(-1);"/></li>
	        </ul>
	    </div>
	</form>
</body>
</html>