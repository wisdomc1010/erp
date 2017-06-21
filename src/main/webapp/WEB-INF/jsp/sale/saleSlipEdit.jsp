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
	<title>销售单修改</title>
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="erp_js/sale/saleSlipEdit.js"></script>
	<link rel="stylesheet" href="chosen/docsupport/style.css" />
	<link rel="stylesheet" href="chosen/docsupport/prism.css" />
	<link rel="stylesheet" href="chosen/chosen.css" />
</head>

 <body>

   <div class="place">
	<span>位置：</span>
	<ul class="placeul">
		<li>销售管理</li>
		<li>销售单管理</li>
		<li>销售单修改</li>
	</ul>
</div>

	<div class="rightinfo">
	<form id="saveDetail">
	<div class="tools">
		<ul>
			<li><label>&nbsp;&nbsp;客户名称：&nbsp;&nbsp;</label> 
				<select data-placeholder="请选择客户"  name="customerId" id="customerSelect" class="chosen-select" tabindex="2">
                	<option value=""></option>
                    <c:forEach items="${customer}" var="c">
						<c:choose>
							<c:when test="${c.id==saleSlip.customerId}">
								<option selected="selected" value="${c.id}">${c.name}</option>
							</c:when>
							<c:otherwise>
								<option value="${c.id}">${c.name}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>  
                </select>
				<label>&nbsp;&nbsp;单据备注：&nbsp;&nbsp;</label> 
				<input name="mainRemark" id="mainRemark" type="text" class="dfsearchinput" style="width: 190px;" value="${saleSlip.remark }"/>
	        </li>
		</ul>
		<input style="display: none" id="saleId" value="${saleSlip.id}"></input>
	</div>
	<div>
		<ul class="searchtoolbar">
			<li><a id="addDetail"><span><img
						src="images/t01.png" /></span>添加明细</a></li>
			<li><a id="removeDetail"><span><img
						src="images/t03.png" /></span>删除明细</a></li>
		</ul>
	</div>
	
	<table id="saleSlipDetail" class="tablelist" style="width: 65%">
		<thead>
			<tr>
				<th width='30px'><input id="chk_all" type="checkbox" /></th>
				<th width="80px">商品编号</th>
				<th width="120px">商品名称</th>
				<th width="100px">数量</th>
				<th width="100px">零售价</th>
				<th width="100px">实际售价</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody id='saleDetail'>
			<c:forEach items="${saleSlipDetail}" var="s">
				<tr>
					<td><input id="chk_all" type="checkbox" /></td>
					<td style="display: none"><input id="goodsId" name="goodsId" value="${s.goodsId}" /></td>
					<td>${s.goodsNum}</td>
					<td>${s.goodsName}</td>
					<td><input id="number" name="number" class="dfsearchinput" style="width:80px" value="${s.number}" /></td>
					<td><input id="goodsPrice" name="goodsPrice" readonly="readonly" value="${s.price}" /></td>
					<td><input id="realPrice" name="realPrice" class="dfsearchinput" style="width:80px" value="${s.realPrice}" /></td>
				    <td><input id="remark" name="remark" class="dfsearchinput" value="${s.remark}" /></td>
				</tr>
			</c:forEach>
	          </tbody>
	</table>
	</form>
	</div>
	<div style="padding-left: 40%;">
		<ul class="forminfo">
			<li><input type="button" class="btn" id="save" value="保存" /> <input
				type="button" class="btn" value="取消" id="saveCancel"/></li>
		</ul>
	</div>
	
	<div class="saleTip">
		<div class="tiptop">
			<span>销售单明细添加</span><a id="close"></a>
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
	                <input name="goodsStockShow" id="goodsStockShow" readonly="readonly" type="text" class="dfinput" style="width: 60px;display: none;border-color: #f00" />
	                </li>
	                <li><div class="validate_msg_short" id="number_error"></div></li>
	                <li><label>实际售价</label>
	                <input name="realPriceAdd" id="realPriceAdd" type="text" class="dfinput" style="width: 190px;" />
	                <input name="goodsPriceShow" id="goodsPriceShow" readonly="readonly" type="text" class="dfinput" style="width: 60px;display: none;border-color: #f00" />
	                </li>
	                <li><div class="validate_msg_short" id="realPrice_error"></div></li>
	                <li><label>备注</label>
	                <input name="remarkAdd" id="remarkAdd" type="text" class="dfinput" style="width: 190px;" />
	                </li>
	           </ul>
	       </div>
	       <div class="tipbtn" style="margin-left: 110px;">
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