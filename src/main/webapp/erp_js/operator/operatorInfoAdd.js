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
    //检查员工账号是否为空
    var v_name = $("#name").val();
    if (v_name == "") {
        $("#name_error").html("员工账号不能为空");
        $("#name_error").addClass("error_msg");
        return false; //此处将false作为doSubmit函数返回值
    }
    var v_pwd = $("#pwd").val();
    if (v_pwd == "") {
        $("#pwd_error").html("账号密码不能为空");
        $("#pwd_error").addClass("error_msg");
        return false; //此处将false作为doSubmit函数返回值
    }
    var v_realname = $("#realname").val();
    if (v_realname == "") {
        $("#realname_error").html("真实姓名不能为空");
        $("#realname_error").addClass("error_msg");
        return false; //此处将false作为doSubmit函数返回值
    }
    //检查员工账号是否重复
    $.ajax({
        type: "get",
        async: false,
        url: window.basePath +"operator/operatorCheck/" + v_name,
        success: function(ok) {
        	if(ok){
		     	$("#name_error").html("员工工号可用！");
		     	$("#name_error").removeClass("error_msg");
		     	name_flag = true;//允许提交
		     }else{
        		$("#name_error").html("员工工号已存在,请重新输入！");
        		$("#name_error").addClass("error_msg");
        		name_flag = false;
        		//注意:此处用return false只是退出当前回调函数,
		     	//没有将false作为doSubmit函数的返回值,
		     	//因此不能阻止表单提交
		     }
        }
    });
    return name_flag;
}

function doSubmit() {
    name_flag = checkName(); //检测员工账号
	return name_flag;
	//return false;//阻止提交
	//return true;//允许提交
}