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
  	//取消
	$("#cancel").live("click",function(){
		top.window.closePopWin();
		top.window.callback();
	});
});

//保存结果的提示
 function showResult() {
     showResultDiv(true);
     window.setTimeout("showResultDiv(false);", 3000);
 }

 function showResultDiv(flag) {
     var divResult = document.getElementById("save_result_info");
     if (flag)
         divResult.style.display = "block";
     else
         divResult.style.display = "none";
 }

 function doSubmit() {
 	var pwd_flag = false; //代表pwd是否通过检测
     var realname_flag = false; //代表realname是否通过检测
     //检查密码是否为空
     var v_pwd = $("#pwd").val();
     if (v_pwd == "") {
         $("#pwd_error").html("密码不能为空");
         $("#pwd_error").addClass("error_msg");
         pwd_flag = false; //此处将false作为doSubmit函数返回值
     }else{
     	$("#pwd_error").html("");
         $("#pwd_error").removeClass("error_msg");
         pwd_flag = true;
     }
     //检查真实姓名是否为空
     var v_realname = $("#realname").val();
     if (v_realname == "") {
         $("#realname_error").html("真实姓名不能为空");
         $("#realname_error").addClass("error_msg");
         realname_flag = false; //此处将false作为doSubmit函数返回值
     }else{
     	$("#realname_error").html("");
         $("#realname_error").removeClass("error_msg");
         realname_flag = true;
     }
     return pwd_flag&&realname_flag;
     //return false;//阻止提交
     //return true;//允许提交
 }