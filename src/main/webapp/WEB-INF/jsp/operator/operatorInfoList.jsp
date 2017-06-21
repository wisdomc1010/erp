<%@ page language="java" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <title>员工信息列表</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="erp_js/operator/operatorInfoList.js"></script>
</head>


<body>

    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li>系统管理</li>
            <li>员工信息管理</li>
            <li>员工列表</li>
        </ul>
    </div>

    <div class="rightinfo">

        <form:form commandName="page">
            <div class="tools">
                <ul class="toolbar">
                   <li><label>&nbsp;&nbsp;员工账号：&nbsp;&nbsp;</label> 
                   <form:input path="name"  cssClass="dfsearchinput" style="width:120px"/> 
                   <label>&nbsp;&nbsp;员工姓名：&nbsp;&nbsp;</label>
                   <form:input path="realname" cssClass="dfsearchinput" style="width:120px"/>
                   <label>&nbsp;&nbsp;审核状态：&nbsp;&nbsp;</label>
				<form:select path="state" id="state" cssClass="dfsearchinput" style="width:70px">
					<form:option value="-1">全部</form:option>
					<form:option value="0">未审核</form:option>
					<form:option value="1">审核</form:option>
       			</form:select>
       			<label>&nbsp;&nbsp;使用状态：&nbsp;&nbsp;</label>
				<form:select path="useState" id="useState" cssClass="dfsearchinput" style="width:70px">
					<form:option value="-1">全部</form:option>
					<form:option value="0">停用</form:option>
					<form:option value="1">在用</form:option>
       			</form:select></li>
                </ul>
                   </div>
                   <div>
                       <ul class="searchtoolbar">
                           <li><a href="operator/operatorInfoAdd"><span><img src="images/t01.png" /></span>添加</a></li>
                           <li class="click"><a id="edit"><span><img src="images/t02.png" /></span>修改</a></li>
                           <li><a id="updateState"><span><img src="images/t06.png" /></span>审核</a></li>
                           <li><a id="updateUseState"><span><img src="images/t03.png" /></span>停用</a></li>
                           <li><a id="search"><span><img src="images/t07.png" /></span>查询</a></li>
                       </ul>
                   </div>

                   <table class="tablelist" style="width: 60%">
               <thead>
                   <tr>
                       <th width='30px'><input id="chk_all" type="checkbox" /></th>
                       <th width='100px'>员工账号</th>
                       <th width='100px'>员工姓名</th>
                       <th width='100px'>审核状态</th>
                       <th width='100px'>使用状态</th>
                   </tr>
               </thead>
               <tbody style="width: 60%">
                   <c:forEach items="${operators}" var="o">
                       <tr>
                           <td width="30px"><input name="" type="checkbox" value="" /></td>
                           <td id="operatorId" style="display: none">${o.id}</td>
                           <td>${o.name}</td>
                           <td>${o.realname}</td>
                           <td>${o.state=="0"?"未审核":"审核"}</td>
                           <td>${o.useState=="0"?"停用":"在用"}</td>
                       </tr>
                   </c:forEach>
               </tbody>
           </table>


           <div class="pagin" style="width: 60%">
               <div class="message">
                   共&nbsp;<i class="blue">${totalRows}</i>&nbsp;条记录，当前显示第&nbsp;<i class="blue">${page.page}&nbsp;</i>页，共&nbsp;<i class="blue">${page.totalPage}&nbsp;</i>页
               </div>
               <ul class="paginList">
                   <c:choose>
                       <c:when test="${page.page>=page.totalPage && page.totalPage != 1}">
                                  <li class="paginItem"><a href="operator/operatorInfoList/*/*/-1/-1/1"><span class="pagepreEndCurrent"></span></a></li>
                              </c:when>
                       <c:otherwise>
                                  <li class="paginItem"><a href="javascript:;"><span class="pagepreEnd"></span></a></li>
                              </c:otherwise>
                   </c:choose>
                   <c:choose>
                       <c:when test="${page.page>1}">
                           <li class="paginItem"><a href="operator/operatorInfoList/*/*/-1/-1/${page.page-1}"><span class="pagepreCurrent"></span></a></li>
                              </c:when>
                       <c:otherwise>
                                  <li class="paginItem"><a href="javascript:;"><span class="pagepre"></span></a></li>
                              </c:otherwise>
                   </c:choose>
                   <c:forEach var="i" begin="1" end="${page.totalPage}">
                       <c:choose>
                           <c:when test="${i==page.page}">
                               <li class="paginItem current"><a href="operator/operatorInfoList/*/*/-1/-1/${i}">${i}</a></li>
                           </c:when>
                           <c:otherwise>
                               <li class="paginItem"><a href="operator/operatorInfoList/*/*/-1/-1/${i}">${i}</a></li>
                           </c:otherwise>
                       </c:choose>
                   </c:forEach>

                   <c:choose>
                       <c:when test="${page.page<page.totalPage && page.totalPage!=1}">
                           <li class="paginItem"><a href="operator/operatorInfoList/*/*/-1/-1/${page.page+1}"><span class="pagenxtCurrent"></span></a></li>
                              </c:when>
                       <c:otherwise>
                                  <li class="paginItem"><a href="javascript:;"><span class="pagenxt"></span></a></li>
                              </c:otherwise>
                   </c:choose>

                   <c:choose>
                       <c:when test="${page.page<page.totalPage}">
                           <li class="paginItem"><a href="operator/operatorInfoList/*/*/-1/-1/${page.totalPage}"><span class="pagenxtEndCurrent"></span></a></li>
                              </c:when>
                       <c:otherwise>
                                  <li class="paginItem"><a href="javascript:;"><span class="pagenxtEnd"></span></a></li>
                              </c:otherwise>
                   </c:choose>
               </ul>
           </div>
       </form:form>

   </div>

   <script type="text/javascript">
       $('.tablelist tbody tr:odd').addClass('odd');
   </script>


</body>

</html>