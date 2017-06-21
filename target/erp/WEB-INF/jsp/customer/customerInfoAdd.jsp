﻿<%@ page language="java" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <title>客户信息添加</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.idTabs.min.js"></script>
	<script type="text/javascript" src="js/select-ui.min.js"></script>
    <script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
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

    <script language="javascript" type="text/javascript">
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
            //检查客户名称是否为空
            var v_name = $("#name").val();
            if (v_name == "") {
                $("#name_error").html("客户名称不能为空");
                $("#name_error").addClass("error_msg");
                return false; //此处将false作为doSubmit函数返回值
            } 
            //检查身份证号是否为空
            var v_idcard = $("#idcard").val();
            if (v_idcard == "") {
                $("#idcard_error").html("身份证号不能为空");
                $("#idcard_error").addClass("error_msg");
                return false; //此处将false作为doSubmit函数返回值
            }else{
            	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
           	    if(reg.test(v_idcard) === false)  
           	    {  
           	       $("#idcard_error").html("身份证输入不合法");
                   $("#idcard_error").addClass("error_msg");
           	       return  false;  
           	    } 
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
           	       return  false;  
           	    }  
            }
            //检查详细地址是否为空
            var v_address = $("#address").val();
            var v_province = $("#province").val();
            var v_city = $("#city").val();
            if (v_province == "" || v_address == "" || v_city == "") {
                $("#address_error").html("详细地址不能为空");
                $("#address_error").addClass("error_msg");
                return false; //此处将false作为doSubmit函数返回值
            }
            //检查身份证号是否重复
            $.ajax({
                type: "get",
                async: false,
                url: window.basePath + "customer/customerCheck/" + v_idcard,
                success: function(ok) {
                	if($.trim(ok)=="true"){
				     	$("#idcard_error").html("身份证号可用！");
				     	$("#idcard_error").removeClass("error_msg");
				     	name_flag = true;//允许提交
				     }else{
	            		$("#idcard_error").html("身份证号已存在,请核对后输入！");
	            		$("#idcard_error").addClass("error_msg");
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
    </script>
</head>


<body>

    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li>基础管理</li>
            <li>客户信息管理</li>
            <li>添加客户</li>
        </ul>
    </div>

    <form action="customer/customerAdd" onsubmit="return doSubmit();" method="post" class="main_form">

        <div class="formbody">
            <div class="formtitle">
                <span>客户基本信息</span>
            </div>

            <ul class="forminfo">
                <li><label>客户名称</label><input name="name" id="name" type="text" class="dfinput" style="width: 190px;" />
                    <div class="validate_msg_short" id="name_error"></div>
                </li>

                <li><label>生日</label><input name="birthday" id="birthday" type="text" class="dfinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"   style="width: 190px;"/>
                  </li>
                  <li><label>性别</label>
                  <input type="radio" name="sex" id="male" value="1" checked="checked"/>男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="radio" name="sex" id="female" value="0"/>女
                  </li>
                  <li><label>身份证号</label><input name="idcard" id="idcard" type="text" class="dfinput" style="width: 190px;" />
                <div class="validate_msg_short" id="idcard_error"></div>
                </li>
                <li><label>联系电话</label><input name="phone" id="phone" type="text" class="dfinput" style="width: 190px;" />
                <div class="validate_msg_short" id="phone_error"></div>
                </li>
                <li><label>详细地址</label>
                <input name="province" id="province" type="text" class="dfinput" style="width: 70px;" />&nbsp;省&nbsp;
                <input name="city" id="city" type="text" class="dfinput" style="width: 70px;" />&nbsp;市&nbsp;
                <input name="address" id="address" type="text" class="dfinput" style="width: 190px;" />
                <div class="validate_msg_short" id="address_error"></div></li>
                <li>
                </li>
                <li><label>邮箱</label><input name="email" id="email" type="text" class="dfinput" style="width: 190px;" />
                </li>
                <li><label>备注</label><input name="remark" id="remark" type="text" class="dfinput" style="width: 190px;" />
                </li>
                <li><input type="submit" class="btn" value="保存" /> <input type="button" class="btn" value="取消" /></li>
            </ul>


        </div>
    </form>

</body>

</html>