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
    <title>商品信息添加</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/select.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="js/select-ui.min.js"></script>


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
            
            $(".select1").uedSelect({
        		width : 195
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
                	if($.trim(ok)=="true"){
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
    </script>
</head>


<body>

    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li>基础管理</li>
            <li>商品信息管理</li>
            <li>添加商品</li>
        </ul>
    </div>
    <form action="goods/goodsAdd" onsubmit="return doSubmit();" method="post" class="main_form">

        <div class="formbody">

            <div class="formtitle">
                <span>商品基本信息</span>
            </div>

            <ul class="forminfo">
            	<li><label>商品编号</label><input name="num" id="num" type="text" class="dfinput" style="width: 190px;" />
                    <div class="validate_msg_short" id="num_error"></div>
                </li>
                <li><label>商品名称</label><input name="name" id="name" type="text" class="dfinput" style="width: 190px;" />
                    <div class="validate_msg_short" id="name_error"></div>
                </li>
                <li><label>零售价</label><input name="price" id="price" type="text" class="dfinput" style="width: 190px;" />
                    <div class="validate_msg_short" id="price_error"></div>
                </li>
                <li><label>成本价</label><input name="cost" id="cost" type="text" class="dfinput" style="width: 190px;" />
                    <div class="validate_msg_short" id="cost_error"></div>
                </li>
                 <li><label>供应商</label>
                 <div class="vocation">
	                    <select class="select1" name="supplierId" id="suppilerSelect">  
	                       <option value="">--请选择--</option>  
	                       <c:forEach items="${suppiler}" var="s">
	                         <option value="${s.id }">${s.name }</option>  
	                       </c:forEach>  
	                    </select> 
	             </div>
                </li>
                <li><div class="validate_msg_short" id="supplier_error"></div></li>
                <li><label>备注</label><input name="remark" id="remark" type="text" class="dfinput" style="width: 190px;" />
                </li>
                <li><input type="submit" class="btn" value="保存" /> <input type="button" class="btn" value="取消" /></li>
            </ul>


        </div>
    </form>
</body>

</html>