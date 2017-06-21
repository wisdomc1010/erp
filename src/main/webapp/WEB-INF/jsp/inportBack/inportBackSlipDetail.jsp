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
<title>退厂单明细详情</title>
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
            <li>仓库管理</li>
            <li>退厂单管理</li>
            <li>退厂单明细详情</li>
        </ul>
    </div>

    <div class="rightinfo">

            <div class="tools">
	                <ul class="toolbar">
	                   
	                   <li><label>&nbsp;&nbsp;供应商名称：&nbsp;&nbsp;</label>${inportBackSlip.supplier}</li>
	                   <li><label>&nbsp;&nbsp;退厂单号：&nbsp;&nbsp;</label>${inportBackSlip.inportNum}</li>
	                   <li><label>&nbsp;&nbsp;制单日期：&nbsp;&nbsp;</label>
						<fmt:formatDate value="${inportBackSlip.createTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long"/>
					   </li>
	                   <li><label>&nbsp;&nbsp;审核状态：&nbsp;&nbsp;</label>
                          	<c:if test="${inportBackSlip.state==0}">未审核</c:if>
                          	<c:if test="${inportBackSlip.state==1}">已审核</c:if>
                          	<c:if test="${inportBackSlip.state==2}">已出库</c:if>
                          	<c:if test="${inportBackSlip.state==3}">已记账</c:if>
                       </li>
	                </ul>
               </div>
               <div class="tools">
	                <ul class="toolbar">
	                	<li><label>&nbsp;&nbsp;单据备注：&nbsp;&nbsp;</label>${inportBackSlip.remark}</li>
	                </ul>
               </div>
               <div>
                   <ul class="searchtoolbar">
                       <li><a href="inportBack/inportBackSlipManage" target="_parent"><span><img src="images/t11.png" /></span>返回</a></li>
                   </ul>
               </div>

               <table class="tablelist" style="width: 60%">
               <thead>
                   <tr>
                       <th width='100px'>商品编码</th>
                       <th width='100px'>商品名称</th>
                       <th width='80px'>数量</th>
                       <th width='70px'>备注</th>
                   </tr>
               </thead>
               <tbody>
                   <c:forEach items="${inportBackSlipDetail}" var="isd">
                       <tr>
                           <td>${isd.goodsNum}</td>
                           <td>${isd.goodsName}</td>
                           <td>${isd.number}</td>
                           <td>${isd.remark}</td>
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