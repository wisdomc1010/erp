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

 
 function doSubmit() {
 	var name_flag = false; //代表name是否通过检测
     var price_flag = false; //代表price是否通过检测
     var supplier_flag = false; //代表supplier是否通过检测
     //检查商品名称是否为空
     var v_name = $("#name").val();
     if (v_name == "") {
         $("#name_error").html("商品名称不能为空");
         $("#name_error").addClass("error_msg");
         name_flag = false; //此处将false作为doSubmit函数返回值
     }else{
     	$("#name_error").html("");
         $("#name_error").removeClass("error_msg");
         name_flag = true;
     }
     //检查零售价是否为空
     var v_price = $("#price").val();
     if (v_price == "") {
         $("#price_error").html("零售价不能为空");
         $("#price_error").addClass("error_msg");
         price_flag = false; //此处将false作为doSubmit函数返回值
     }else{
     	$("#price_error").html("");
         $("#price_error").removeClass("error_msg");
         price_flag = true;
     }
     //检查供应商是否为空
     var v_supplier = $("#supplierId").val();
     if (v_supplier == "") {
         $("#supplier_error").html("供应商不能为空");
         $("#supplier_error").addClass("error_msg");
         supplier_flag = false; //此处将false作为doSubmit函数返回值
     }else{
     	$("#supplier_error").html("");
         $("#supplier_error").removeClass("error_msg");
         supplier_flag = true;
     }
     return name_flag&&price_flag&&supplier_flag;
     //return false;//阻止提交
     //return true;//允许提交
 }