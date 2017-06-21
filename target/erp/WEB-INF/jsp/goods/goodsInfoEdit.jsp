<%@ page language="java" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
                <title>商品信息修改</title>
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

                    function checkName() {
                    	//检查商品编号是否为空
                        var v_num = $("#num").val();
                        if (v_num == "") {
                            $("#num_error").html("商品编号为空");
                            $("#num_error").addClass("error_msg");
                        }
                        //检查商品名称是否为空
                        var v_name = $("#name").val();
                        if (v_name == "") {
                            $("#name_error").html("商品名称为空");
                            $("#name_error").addClass("error_msg");
                        }
                        //检查零售价是否为空
                        var v_price = $("#price").val();
                        if (v_price == "") {
                            $("#price_error").html("零售价为空");
                            $("#price_error").addClass("error_msg");
                        }
                        //检查供应商是否为空
                        var v_supplier = $("#suppilerSelect").val();
                        if (v_supplier == "") {
                            $("#supplier_error").html("供应商为空");
                            $("#supplier_error").addClass("error_msg");
                        }
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

                <form:form action="goods/goodsInfoEdit/${goods.id}" method="post" commandName="goods">
                
                <div class="formbody">
                    <div class="formtitle">
                        <span>商品基本信息</span>
                    </div>

                        <ul class="forminfo">
                        <c:forEach items="${suppiler}" var="s">
                            <li>
                                <form:hidden path="id" />
                            </li>
                            <li><label>商品编号</label>
                                <form:input path="num" cssClass="dfinputreadonly" readonly="true" />
                                <div class="validate_msg_short" id="num_error"></div>
                            </li>
                            <li><label>商品名称</label>
                                <form:input path="name" cssClass="dfinput" />
                                <div class="validate_msg_short" id="name_error"></div>
                            </li>
                            <li><label>零售价</label>
                                <form:input path="price" cssClass="dfinput" />
                                <div class="validate_msg_short" id="price_error"></div>
                            </li>
                            <li><label>成本价</label>
                                <form:input path="cost" cssClass="dfinput" />
                            </li>
                            <li><label>供应商</label>
			                <div class="vocation">
			                <select class="select1" name="supplierId" id="suppilerSelect">  
		               		  <option value="">--请选择--</option>
		             		  <c:forEach items="${suppiler}" var="s">
		             		  	<c:if test="${s.id }==su"></c:if>
		                         <option value="${s.id }">${s.name }</option>
		                      </c:forEach>
			                </select> 
				            </div>
				            </li>
                            <li><label>备注</label>
                                <form:input path="remark" cssClass="dfinput" />
                            </li>
                            <li><input type="submit" class="btn" value="保存" />
                                <input type="button" class="btn" value="取消" onclick="history.go(-1);" /></li>
                           </c:forEach>
                        </ul>
                	</div>
                 </form:form>

            </body>

            </html>