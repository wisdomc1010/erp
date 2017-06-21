<%@ page language="java" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
                <title>员工信息修改</title>
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

                    function checkName() {
                        var v_pwd = $("#pwd").val();
                        if (v_pwd == "") {
                            $("#pwd_error").html("账号密码为空");
                            $("#pwd_error").addClass("error_msg");
                        }
                        var v_realname = $("#realname").val();
                        if (v_realname == "") {
                            $("#realname_error").html("真实姓名为空");
                            $("#realname_error").addClass("error_msg");
                        }
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

                <div class="formbody">

                    <div class="formtitle">
                        <span>员工基本信息</span>
                    </div>

                    <form:form action="operator/operatorInfoEdit/${operator.id}" method="post" commandName="operator">
                        <ul class="forminfo">
                            <li>
                                <form:hidden path="id" />
                            </li>
                            <li><label>员工账号</label>
                                <form:input path="name" cssClass="dfinputreadonly" readonly="true" />
                                <div class="validate_msg_short" id="name_error"></div>
                            </li>
                            <li><label>账号密码</label>
                                <form:input path="pwd" cssClass="dfinput" />
                                <div class="validate_msg_short" id="pwd_error"></div>
                            </li>
                            <li><label>真实姓名</label>
                                <form:input path="realname" cssClass="dfinput" />
                                <div class="validate_msg_short" id="realname_error"></div>
                            </li>
                            <li><input type="submit" class="btn" value="保存" />
                                <input type="button" class="btn" value="取消" onclick="history.go(-1);" /></li>
                        </ul>
                    </form:form>
                </div>

            </body>

            </html>