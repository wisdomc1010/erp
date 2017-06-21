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
    <title>供应商基本信息修改</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="erp_js/supplier/supplierInfoEdit.js"></script>
</head>

<body>

 <div class="place">
     <span>位置：</span>
     <ul class="placeul">
         <li>基础管理</li>
         <li>供应商信息管理</li>
         <li>供应商信息修改</li>
     </ul>
 </div>

 <div class="formbody">

     <div class="formtitle">
         <span>供应商基本信息</span>
     </div>

     <form:form action="supplier/supplierInfoEdit/${supplier.id}" onsubmit="return doSubmit();" method="post" commandName="supplier">
         <ul class="forminfo">
             <li>
                 <form:hidden path="id" />
             </li>
             <li><label>供应商名称：</label>
                 <form:input path="name" cssClass="dfinput" style="width:190px"/>
                 <div class="validate_msg_short" id="name_error"></div>
             </li>
             <li><label>联系人：</label>
                 <form:input path="contacts" cssClass="dfinput" style="width:190px"/>
                 <div class="validate_msg_short" id="contacts_error"></div>
             </li>
             <li><label>联系电话：</label>
                 <form:input path="phone" cssClass="dfinput" style="width:190px"/>
                 <div class="validate_msg_short" id="phone_error"></div>
             </li>
             <li><label>详细地址</label>
		        <form:input path="province" cssClass="dfinput" style="width:70px"/>&nbsp;省&nbsp;
		        <form:input path="city" cssClass="dfinput" style="width:70px"/>&nbsp;市&nbsp;
		        <form:input path="address" cssClass="dfinput" style="width:190px"/>
		        <div class="validate_msg_short" id="address_error"></div>
		    </li>
		    <li><label>备注</label>
		        <form:input path="remark" cssClass="dfinput" style="width:190px"/>
		    </li>
             <li><input type="submit" class="btn" value="保存" />
                 <input type="button" class="btn" value="取消" onclick="javascript:history.go(-1);" /></li>
         </ul>
     </form:form>
 </div>

</body>

</html>