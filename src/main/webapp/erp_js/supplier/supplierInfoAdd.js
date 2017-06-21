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

 var name_flag = false; //代表name是否通过检测

 function checkName() {
 	name_flag = false;
     //检查供应商名称是否为空
     var v_name = $("#name").val();
     if (v_name == "") {
         $("#name_error").html("供应商名称不能为空");
         $("#name_error").addClass("error_msg");
         return false; //此处将false作为doSubmit函数返回值
     }
     //检查联系人是否为空
     var v_contacts = $("#contacts").val();
     if (v_contacts == "") {
         $("#contacts_error").html("联系人不能为空");
         $("#contacts_error").addClass("error_msg");
         return false; //此处将false作为doSubmit函数返回值
     }
   //检查联系电话是否为空
     var v_phone = $("#phone").val();
     if (v_phone == "") {
         $("#phone_error").html("联系电话不能为空");
         $("#phone_error").addClass("error_msg");
         return false; //此处将false作为doSubmit函数返回值
     }else{
     	var reg = /^1(3|4|5|7|8)\d{9}$/;  
    	    if(reg.test(v_phone) === false)  
    	    {  
    	       $("#phone_error").html("手机号码有误，请重新输入！");
            $("#phone_error").addClass("error_msg");
            return false;  
    	    }  
     }
     //检查详细地址是否为空
     var v_address = $("#address").val();
     var v_province = $("#province").val();
     var v_city = $("#city").val();
     if (v_province == "" || v_city == "" || v_address == "") {
         $("#address_error").html("详细地址不能为空");
         $("#address_error").addClass("error_msg");
         return false; //此处将false作为doSubmit函数返回值
     }
     //检查供应商名称是否重复
     $.ajax({
         type: "get",
         async: false,
         url: window.basePath + "supplier/supplierCheck/" + v_name,
         success: function(ok) {
         	if(ok==-1){
			     	$("#name_error").html("供应商可用！");
			     	$("#name_error").removeClass("error_msg");
			     	name_flag = true;//允许提交
			     }else{
         		$("#name_error").html("供应商已存在,请核对后输入！");
         		$("#name_error").addClass("error_msg");
         		name_flag = false;
			     }
         }
     });
     //通过检查返回true,未通过返回false
     //前面ajax用同步,目的是等前面ajax请求
     //回调函数执行完毕再执行此行代码
     return name_flag;
 }

 function doSubmit() {
     name_flag = checkName(); //检测商品编号
     return name_flag;
     //return false;//阻止提交
     //return true;//允许提交
 }