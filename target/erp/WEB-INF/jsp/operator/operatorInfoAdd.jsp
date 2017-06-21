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
                <title>员工信息添加</title>
                <link href="css/style.css" rel="stylesheet" type="text/css" />
                <script type="text/javascript" src="js/jquery.js"></script>

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
                            	if($.trim(ok)=="true"){
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
                </script>
            </head>


            <body>

                <div class="place">
                    <span>位置：</span>
                    <ul class="placeul">
                        <li>系统管理</li>
                        <li>员工管理</li>
                        <li>添加员工</li>
                    </ul>
                </div>

                <form action="operator/operatorAdd" onsubmit="return doSubmit();" method="post" class="main_form">

                    <div class="formbody">

                        <div class="formtitle">
                            <span>员工基本信息</span>
                        </div>

                        <ul class="forminfo">
                            <li><label>员工账号</label><input name="name" id="name" type="text" class="dfinput" style="width: 190px;" />
                                <div class="validate_msg_short" id="name_error"></div>
                            </li>
                            <li><label>账号密码</label><input name="pwd" id="pwd" type="text" class="dfinput" style="width: 190px;" />
                                <div class="validate_msg_short" id="pwd_error"></div>
                            </li>
                            <li><label>真实姓名</label><input name="realname" id="realname" type="text" class="dfinput" style="width: 190px;" />
                                <div class="validate_msg_short" id="realname_error"></div>
                            </li>
                            <li><input type="submit" class="btn" value="保存" /> <input type="button" class="btn" value="取消" /></li>
                        </ul>


                    </div>
                </form>

            </body>

            </html>