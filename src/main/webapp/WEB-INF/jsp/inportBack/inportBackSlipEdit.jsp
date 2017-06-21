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
	<title>退厂单修改</title>
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="erp_js/inportBack/inportBackSlipEdit.js"></script>
	<link rel="stylesheet" href="chosen/docsupport/style.css" />
	<link rel="stylesheet" href="chosen/docsupport/prism.css" />
	<link rel="stylesheet" href="chosen/chosen.css" />
</head>

<body>

	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li>仓库管理</li>
			<li>退厂单管理</li>
			<li>退厂单修改</li>
		</ul>
	</div>

	<div class="rightinfo">
	<form id="saveDetail">
	<div class="tools">
		<ul>
			<li><label>&nbsp;&nbsp;供应商名称：&nbsp;&nbsp;</label> 
				<select data-placeholder="请选择供应商"  name="supplierId" id="suppilerSelect" class="chosen-select" tabindex="2">
                	<option value=""></option>
                    <c:forEach items="${supplier}" var="s">
						<c:choose>
							<c:when test="${s.id==inportSlip.supplierId}">
								<option selected="selected" value="${s.id}">${s.name}</option>
							</c:when>
							<c:otherwise>
								<option value="${s.id}">${s.name}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>  
                </select>
				<label>&nbsp;&nbsp;单据备注：&nbsp;&nbsp;</label> 
				<input name="mainRemark" id="mainRemark" type="text" class="dfsearchinput" style="width: 190px;" value="${inportSlip.remark }"/>
	        </li>
		</ul>
		<input style="display: none" id="inportId" value="${inportSlip.id}"></input>
	          <input style="display: none" id="inportNum" value="${inportSlip.inportNum}"></input>
	</div>
	<div>
		<ul class="searchtoolbar">
			<li><a id="addDetail"><span><img src="images/t01.png" /></span>添加明细</a></li>
			<li><a id="removeDetail"><span><img src="images/t03.png" /></span>删除明细</a></li>
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
			<c:forEach items="${inportSlipDetail}" var="i">
				<tr>
					<td width="30px"><input id="chk_all" type="checkbox" /></td>
					<td style="display: none"><input id="goodsId" name="goodsId" value="${i.goodsId}" /></td>
					<td>${i.goodsNum}</td>
					<td>${i.goodsName}</td>
					<td><input id="number" name="number" class="dfsearchinput" value="${i.number}" /></td>
				    <td><input id="remark" name="remark" class="dfsearchinput" value="${i.remark}" /></td>
				</tr>
			</c:forEach>
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
			<span>入库单明细添加</span><a></a>
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
	            <li><div class="validate_msg_short" id="goodId_error"></div></li>
	               <li><label>数量</label>
	               <input name="numberAdd" id="numberAdd" type="text" class="dfinput" style="width: 190px;" />
	               <input name="goodsStock" id="goodsStock" readonly="readonly" type="text" class="dfinput" style="width: 60px;display: none;border-color: #f00" />
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