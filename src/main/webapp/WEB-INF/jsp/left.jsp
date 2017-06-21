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
<title>无标题文档</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery.js"></script>

<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active");
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
});
</script>


</head>

<body style="background:#f0f9fd;">
	<div class="lefttop"><span></span></div>
    
    <dl class="leftmenu">
        
    <dd>
    <div class="title">
    <span><img src="images/leftico01.png" /></span>基础管理
    </div>
    	<ul class="menuson">
        <li><cite></cite><a href="goods/goodsInfoManage" target="_parent">商品信息管理</a><i></i></li>
        <li><cite></cite><a href="customer/customerInfoManage" target="_parent">客户信息管理</a><i></i></li>
        <li><cite></cite><a href="supplier/supplierInfoManage" target="_parent">供应商信息管理</a><i></i></li>
        </ul>    
    </dd>
        
    
    <dd>
    <div class="title">
    <span><img src="images/leftico02.png" /></span>销售管理
    </div>
    <ul class="menuson">
        <li><cite></cite><a href="sale/saleSlipManage" target="_parent">销售单管理</a><i></i></li>
        <li><cite></cite><a href="saleBack/saleBackSlipManage" target="_parent">退货单管理</a><i></i></li>
    </ul>  
    </dd> 
    
    
    <dd><div class="title"><span><img src="images/leftico04.png" /></span>仓库管理</div>
    <ul class="menuson">
         <li><cite></cite><a href="inport/inportSlipManage" target="_parent">到货单管理</a><i></i></li>
         <li><cite></cite><a href="inportBack/inportBackSlipManage" target="_parent">退厂单管理</a><i></i></li>
    </ul>    
    </dd>  
    
    
    <dd><div class="title"><span><img src="images/leftico05.png" /></span>财务管理</div>
    <ul class="menuson">
		<li><cite></cite><a href="account/accountInfoManage" target="_parent">余额管理</a><i></i></li>
		<li><cite></cite><a href="accountDetail/accountDetailInfoManage" target="_parent">往来账管理</a><i></i></li>
    </ul>
    </dd>   
    
    <dd><div class="title"><span><img src="images/leftico07.png" /></span>报表管理</div>
    <ul class="menuson">
        <li><cite></cite><a href="stock/stockInfoManage" target="_parent">库存查询</a><i></i></li>
    </ul>    
    </dd>  
  
    <dd><div class="title"><span><img src="images/leftico06.png" /></span>系统管理</div>
    <ul class="menuson">
        <li><cite></cite><a href="operator/operatorInfoManage" target="_parent">员工管理</a><i></i></li>
    </ul>    
    </dd>  
      
    </dl>
    
</body>
</html>
