<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
	<title>商品信息修改</title>
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="erp_js/goods/goodsInfoEdit.js"></script>
	<link rel="stylesheet" href="chosen/docsupport/style.css" />
	<link rel="stylesheet" href="chosen/docsupport/prism.css" />
	<link rel="stylesheet" href="chosen/chosen.css" />
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
       <li>基础管理</li>
	<li>商品信息管理</li>
	<li>商品信息修改</li>
    </ul>
</div>

<form:form action="goods/goodsInfoEdit/${goods.id}" onsubmit="return doSubmit();" method="post" commandName="goods">
	<div class="formbody">
	    <div class="formtitle">
	        <span>商品基本信息</span>
	    </div>
	    <ul class="forminfo">
	        <li>
	            <form:hidden path="id" />
	        </li>
	        <li><label>商品编号</label>
	            <form:input path="num" cssClass="dfinputreadonly" readonly="true" style="width: 190px;"/>
	        </li>
	        <li><label>商品名称</label>
	            <form:input path="name" cssClass="dfinput" style="width: 190px;"/>
	            <div class="validate_msg_short" id="name_error"></div>
	        </li>
	        <li><label>零售价</label>
	            <form:input path="price" cssClass="dfinput" style="width: 190px;"/>
	            <div class="validate_msg_short" id="price_error"></div>
	        </li>
	        <li><label>成本价</label>
	            <form:input path="cost" cssClass="dfinput" style="width: 190px;"/>
	        </li>
	        <li><label>供应商</label>
	         <form:select path="supplierId" data-placeholder="请选择供应商" cssClass="chosen-select" tabindex="2">
	         	<form:option value=""></form:option>
	         	<form:options items="${supplier}"  itemLabel="name" itemValue="id"/>
	         </form:select>
			</li>
			<li><div class="validate_msg_short" id="supplier_error"></div></li>
	        <li><label>备注</label>
	            <form:input path="remark" cssClass="dfinput" style="width: 190px;"/>
	        </li>
	        <li><input type="submit" class="btn" value="保存" />
	            <input type="button" class="btn" value="取消" onclick="javascript:history.go(-1);" /></li>
	    </ul>
	</div>
	<script src="chosen/chosen.jquery.js" type="text/javascript"></script>
    <script src="chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
    <script src="chosen/docsupport/init.js" type="text/javascript" charset="utf-8"></script>
</form:form>
</body>
</html>