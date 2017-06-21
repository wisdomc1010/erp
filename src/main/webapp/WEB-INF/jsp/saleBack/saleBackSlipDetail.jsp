<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<title>退货单明细详情</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>

<script type="text/javascript">
$(document).ready(function() {
    $(".click").click(function() {
        $(".tip").fadeIn(200);
    });

    $(".tiptop a").click(function() {
        $(".tip").fadeOut(200);
    });

    $(".sure").click(function() {
        $(".tip").fadeOut(100);
    });

    $(".cancel").click(function() {
        $(".tip").fadeOut(100);
    });

});
</script>

</head>


<body>

    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li>销售管理</li>
            <li>退货单管理</li>
            <li>退货单明细详情</li>
        </ul>
    </div>

    <div class="rightinfo">

            <div class="tools">
	                <ul class="toolbar">
	                   
	                   <li><label>&nbsp;&nbsp;客户名称：&nbsp;&nbsp;</label>${saleBackSlip.customer}</li>
	                   <li><label>&nbsp;&nbsp;销售单号：&nbsp;&nbsp;</label>${saleBackSlip.saleNum}</li>
	                   <li><label>&nbsp;&nbsp;制单日期：&nbsp;&nbsp;</label>
						<fmt:formatDate value="${saleBackSlip.createTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long"/>
					   </li>
	                   <li><label>&nbsp;&nbsp;审核状态：&nbsp;&nbsp;</label>
                          	<c:if test="${saleBackSlip.state==0}">未审核</c:if>
                          	<c:if test="${saleBackSlip.state==1}">已审核</c:if>
                          	<c:if test="${saleBackSlip.state==2}">已出库</c:if>
                          	<c:if test="${saleBackSlip.state==3}">已记账</c:if>
                       </li>
	                </ul>
               </div>
               <div class="tools">
	                <ul class="toolbar">
	                	<li><label>&nbsp;&nbsp;单据备注：&nbsp;&nbsp;</label>${saleBackSlip.remark}</li>
	                </ul>
               </div>
               <div>
                   <ul class="searchtoolbar">
                       <li><a href="saleBack/saleBackSlipManage" target="_parent"><span><img src="images/t11.png" /></span>返回</a></li>
                   </ul>
               </div>

               <table class="tablelist" style="width: 60%">
               <thead>
                   <tr>
                       <th width='100px'>商品编码</th>
                       <th width='120px'>商品名称</th>
                       <th width='80px'>数量</th>
                       <th width='80px'>零售价</th>
                       <th width='80px'>实际售价</th>
                       <th>备注</th>
                   </tr>
               </thead>
               <tbody>
                   <c:forEach items="${saleBackSlipDetail}" var="s">
                       <tr>
                           <td>${s.goodsNum}</td>
                           <td>${s.goodsName}</td>
                           <td>${s.number}</td>
                           <td>${s.price}</td>
                           <td>${s.realPrice}</td>
                           <td>${s.remark}</td>
                       </tr>
                   </c:forEach>
               </tbody>
           </table>
   </div>

   <script type="text/javascript">
       $('.tablelist tbody tr:odd').addClass('odd');
   </script>


</body>

</html>