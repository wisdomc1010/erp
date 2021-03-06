﻿﻿<%@ page language="java" pageEncoding="UTF-8"%>
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
    <title>供应商信息添加</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.idTabs.min.js"></script>
	<script type="text/javascript" src="js/select-ui.min.js"></script>
    <script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="erp_js/supplier/supplierInfoAdd.js"></script>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li>基础管理</li>
        <li>供应商信息管理</li>
        <li>供应商信息添加</li>
    </ul>
</div>
<form action="supplier/supplierAdd" onsubmit="return doSubmit();" method="post" class="main_form">
    <div class="formbody">
        <div class="formtitle">
            <span>供应商基本信息</span>
        </div>

        <ul class="forminfo">
            <li>
            	<label>供应商名称</label>
            	<input name="name" id="name" type="text" class="dfinput" style="width: 190px;" />
                <div class="validate_msg_short" id="name_error"></div>
            </li>
            <li>
            	<label>联系人</label>
            	<input name="contacts" id="contacts" type="text" class="dfinput" style="width: 190px;"/>
            	<div class="validate_msg_short" id="contacts_error"></div>
              </li>
            <li>
            	<label>联系电话</label>
            	<input name="phone" id="phone" type="text" class="dfinput" style="width: 190px;" />
            	<div class="validate_msg_short" id="phone_error"></div>
            </li>
            <li><label>详细地址</label>
             <input name="province" id="province" type="text" class="dfinput" style="width: 70px;" />&nbsp;省&nbsp;
             <input name="city" id="city" type="text" class="dfinput" style="width: 70px;" />&nbsp;市&nbsp;
             <input name="address" id="address" type="text" class="dfinput" style="width: 190px;" />
             <div class="validate_msg_short" id="address_error"></div></li>
            <li><label>备注</label><input name="remark" id="remark" type="text" class="dfinput" style="width: 190px;" />
            </li>
            <li><input type="submit" class="btn" id="save" value="保存" /> 
            <input type="button" class="btn" value="取消" onclick="javascript:history.go(-1);"/></li>
        </ul>
    </div>
</form>
</body>
</html>