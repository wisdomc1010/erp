﻿<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
<title>员工信息修改</title>
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
            <li>员工信息修改</li>
        </ul>
    </div>

    <div class="formbody">
        <div class="formtitle">
        <span>员工基本信息</span>
		</div>

        <form:form action="operator/operatorInfoEdit/${operator.id}" onsubmit="return doSubmit();" method="post" commandName="operator">
  		<ul class="forminfo">
	         <li>
	             <form:hidden path="id" />
	         </li>
	         <li><label>员工账号</label>
	             <form:input path="name" cssClass="dfinputreadonly" readonly="true" style="width: 190px;"/>
	         </li>
	         <li><label>账号密码</label>
	             <form:password path="pwd" cssClass="dfinput" showPassword="true" style="width: 190px;"/>
	             <div class="validate_msg_short" id="pwd_error"></div>
	         </li>
	         <li><label>真实姓名</label>
	             <form:input path="realname" cssClass="dfinput" style="width: 190px;"/>
	             <div class="validate_msg_short" id="realname_error"></div>
	         </li>
	         <li><input type="submit" class="btn" value="保存" />
	             <input type="button" class="btn" value="取消" onclick="javascript:history.go(-1);"/></li>
	     </ul>
 	</form:form>
 </div>

</body>

</html>