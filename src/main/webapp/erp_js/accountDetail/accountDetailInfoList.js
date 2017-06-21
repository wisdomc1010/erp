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
  	var clientName = $("#clientName").val();
  	var type = $("#type").val();
  	var listType = $("#listType").val();
  	var createTime = $("#createTime").val();
  	var accountTime = $("#accountTime").val();
  	//根据条件变量状态生成一个rest风格查询的url
  	var url = "accountDetail/accountDetailInfoList";
  	//添加客户/供应商条件
  	if(clientName == ""){
  		url = url +"/*";
  	}else{
  		url = url +"/"+clientName;
  	}
  	//添加客户/供应商类型条件
  	url = url +"/"+type;
    //添加单据类型条件
  	url = url +"/"+listType;
  	//添加制单日期条件
  	if(createTime == ""){
  		url = url +"/*";
  	}else{
  		url = url +"/"+createTime;
  	}
  	//添加记账日期条件
  	if(accountTime == ""){
  		url = url +"/*";
  	}else{
  		url = url +"/"+accountTime;
  	}
  	//添加要显示的页数
  	url = url +"/"+1;
  	window.location = window.basePath + url;//对应get请求
  });
});