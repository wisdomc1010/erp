﻿<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	<title>到货单添加</title>
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="erp_js/inport/inportSlipAdd.js"></script>
	<link rel="stylesheet" href="chosen/docsupport/style.css" />
	<link rel="stylesheet" href="chosen/docsupport/prism.css" />
	<link rel="stylesheet" href="chosen/chosen.css" />
</head>

<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li>仓库管理</li>
			<li>到货单管理</li>
			<li>到货单添加</li>
		</ul>
	</div>

		
	<div class="rightinfo">
	<form id="saveDetail">
	<input type="text" name="token" style="display: none;" value="${token}"/>
	<div class="tools">
		<ul>
			<li><label>&nbsp;&nbsp;供应商名称：&nbsp;&nbsp;</label> 
			<select data-placeholder="请选择供应商"  name="supplierId" id="suppilerSelect" class="chosen-select" tabindex="2">
                	<option value=""></option>
                    <c:forEach items="${supplier}" var="s">
                    	<option value="${s.id }">${s.name }</option>  
                    </c:forEach>  
                </select>
			<label>&nbsp;&nbsp;单据备注：&nbsp;&nbsp;</label> 
			<input name="mainRemark" id="mainRemark" type="text" class="dfinput" style="width: 190px;" /></li>
		</ul>
	</div>
	<div>
		<ul class="searchtoolbar">
			<li><a id="addDetail"><span><img
						src="images/t01.png" /></span>添加明细</a></li>
			<li><a id="removeDetail"><span><img
						src="images/t03.png" /></span>删除明细</a></li>
		</ul>
	</div>

	<table id="inportSlipDetail" class="tablelist" style="width: 60%">
		<thead>
			<tr>
				<th width='30px'><input id="chk_all" type="checkbox" /></th>
				<th width="80px">商品编号</th>
				<th width="120px">商品名称</th>
				<th width="80px">数量</th>
				<th width="80px">备注</th>
			</tr>
		</thead>
		<tbody id='inportDetail'>
	
	    </tbody>
	</table>
	</form>
	</div>
	<div style="padding-left: 35%;">
		<ul class="forminfo">
			<li><input type="button" class="btn" id="save" value="保存" /> <input
				type="button" class="btn" value="取消" id="saveCancel"/></li>
		</ul>
	</div>

	<div class="inportTip">
		<div class="tiptop">
			<span>入库单明细添加</span><a id="close"></a>
		</div>
	
		<div class="tipinfo">
	        <div class="formbody">
	            <ul class="forminfo">
	             	<li>
	             		<label>商品编码</label>
	                 	<div class="vocation">
	                     <select data-placeholder="请选择商品"  id="goodsSelect" class="chosen-select" tabindex="2">
						   <option value=""></option>
					       <c:forEach items="${goods}" var="g">
					       	 <option value="${g.id}">${g.num} : ${g.name}</option>  
					       </c:forEach>  
						 </select>
	             	</div>
	            </li>
	            <li><div class="validate_msg_short" id="goodsId_error"></div></li>
	               <li><label>数量</label>
	               <input name="numberAdd" id="numberAdd" type="text" class="dfinput" style="width: 190px;" />
	               </li>
	               <li><div class="validate_msg_short" id="number_error"></div></li>
	               <li><label>备注</label>
	               <input name="remarkAdd" id="remarkAdd" type="text" class="dfinput" style="width: 190px;" />
	               </li>
	           </ul>
	       </div>
	       <div class="tipbtn" style="margin-left: 120px;">
		        <input type="submit" id="detailAdd" class="sure" value="保存" />&nbsp;
		        <input type="button"  class="cancel" value="取消" />
	        </div>
		</div>
	</div>
	<script src="chosen/chosen.jquery.js" type="text/javascript"></script>
    <script src="chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
    <script src="chosen/docsupport/init.js" type="text/javascript" charset="utf-8"></script>
</body>

</html>