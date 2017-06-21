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
     
     $(".select1").uedSelect({
  		width : 195
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
     //检查商品编号是否为空
 var v_num = $("#num").val();
 if (v_num == "") {
 $("#num_error").html("商品编号不能为空");
 $("#num_error").addClass("error_msg");
 return false; //此处将false作为doSubmit函数返回值
 }
 //检查商品名称是否为空
 var v_name = $("#name").val();
 if (v_name == "") {
 $("#name_error").html("商品名称不能为空");
 $("#name_error").addClass("error_msg");
 return false; //此处将false作为doSubmit函数返回值
 }
 //检查零售价是否为空
 var v_price = $("#price").val();
 if (v_price == "") {
 $("#price_error").html("零售价不能为空");
 $("#price_error").addClass("error_msg");
 return false; //此处将false作为doSubmit函数返回值
 }
 //检查供应商是否为空
 var v_supplier = $("#suppilerSelect").val();
 if (v_supplier == "") {
 $("#supplier_error").html("供应商不能为空");
 $("#supplier_error").addClass("error_msg");
 return false; //此处将false作为doSubmit函数返回值
 }
 //检查员工账号是否重复
 $.ajax({
     type: "get",
 async: false,
 url: window.basePath + "/goods/goodsCheck/" + v_num,
 success: function(ok) {
 	if(ok){
         $("#num_error").html("商品编码可用！");
 $("#num_error").removeClass("error_msg");
 name_flag = true; //允许提交
 } else {
     $("#num_error").html("商品编码已使用,请重新输入！");
 $("#num_error").addClass("error_msg");
 name_flag = false;
 //注意:此处用return false只是退出当前回调函数,
 //没有将false作为doSubmit函数的返回值,
 //因此不能阻止表单提交
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
