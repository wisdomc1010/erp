$(document).ready(function(){
  $(".click").click(function(){
  $(".tip").fadeIn(200);
  });
  
  $(".tiptop a").click(function(){
	  $(".tip").fadeOut(200);
  });

  $(".sure").click(function(){
	  $(".tip").fadeOut(100);
  });

  $(".cancel").click(function(){
	  $(".tip").fadeOut(100);
  });
  $("body").keydown(function() {
	    if (event.keyCode == "13") {//keyCode=13是回车键
	        $('#search').click();
	    }
	}); 
//搜索按钮处理
$("#search").click(function() {
	var goodsNum = $("#goodsNum").val();
	var goodsName = $("#goodsName").val();
	var supplier = $("#supplier").val();
	//根据条件变量状态生成一个rest风格查询的url
	var url = "/stock/stockInfoList";
	//添加商品编码条件
	if(goodsNum == ""){
		url = url +"/*";
	}else{
		url = url +"/"+goodsNum;
	}
	//添加商品名称条件
	if(goodsName == ""){
		url = url +"/*";
	}else{
		url = url +"/"+goodsName;
	}
	//添加供应商名称条件
	if(supplier == ""){
		url = url +"/*";
	}else{
		url = url +"/"+supplier;
	}
	//添加要显示的页数
	url = url +"/"+1;
	window.location = window.basePath + url;//对应get请求
});
});
