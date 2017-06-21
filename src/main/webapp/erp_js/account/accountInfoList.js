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
  
 
//搜索按钮处理
  $("#search").click(function() {
  	var clientName = $("#clientName").val();
  	var type = $("#type").val();
  	//根据条件变量状态生成一个rest风格查询的url
  	var url = "account/accountInfoList";
  	//添加客户/供应商条件
  	if(clientName == ""){
  		url = url +"/*";
  	}else{
  		url = url +"/"+clientName;
  	}
  	//添加客户/供应商类型条件
  	url = url +"/"+type;
  	//添加要显示的页数
  	url = url +"/"+1;
  	window.location = window.basePath + url;//对应get请求
  });
  
  $("body").keydown(function() {
    if (event.keyCode == "13") {//keyCode=13是回车键
        $('#search').click();
    }
  });
});