<%@ page language="java" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath %>" />
<script>
    window.basePath = "<%=basePath%>";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>往来账管理</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="erp_js/accountDetail/accountDetailInfoList.js"></script>
</head>

<body>

<div class="place">
<span>位置：</span>
<ul class="placeul">
<li><a href="#">财务管理</a></li>
<li><a href="#">往来账管理</a></li>
<li><a href="#">往来账列表</a></li>
</ul>
</div>

<div class="rightinfo">

<form:form commandName="page">
    <div class="tools">
        <ul class="toolbar">
			<li>&nbsp;&nbsp;<label>客户/供应商名称：&nbsp;&nbsp;</label> 
			<form:input path="clientName"  cssClass="dfsearchinput" style="width:120px"/> 
			<label>&nbsp;&nbsp;客户/供应商类型：&nbsp;&nbsp;</label>
			<form:select path="type" id="type" cssClass="dfsearchinput" style="width:70px">
				<form:option value="-1">全部</form:option>
				<form:option value="0">客户</form:option>
				<form:option value="1">供应商</form:option>
			</form:select>
			<label>&nbsp;&nbsp;单据类型：&nbsp;&nbsp;</label>
			<form:select path="listType" id="listType" cssClass="dfsearchinput" style="width:70px">
				<form:option value="-1">全部</form:option>
				<form:option value="0">销售</form:option>
				<form:option value="1">退货</form:option>
				<form:option value="2">到货</form:option>
				<form:option value="3">退厂</form:option>
			</form:select>
			<label>&nbsp;&nbsp;制单日期：&nbsp;&nbsp;</label> 
			<form:input path="createTime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" cssClass="dfsearchinput" style="width:120px"/> 
			<label>&nbsp;&nbsp;记账日期：&nbsp;&nbsp;</label> 
			<form:input path="accountTime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" cssClass="dfsearchinput" style="width:120px"/> 
			</li>
        </ul>
     </div>
     <div>
         <ul class="searchtoolbar">
             <li><a id="search"><span><img src="images/t07.png" /></span>查询</a></li>
         </ul>
     </div>

     <table class="tablelist" >
       <thead>
           <tr>
               <th>客户/供应商名称</th>
               <th>类型</th>
               <th>单据号</th>
               <th>单据类型</th>
               <th>单据数量</th>
               <th>单据金额</th>
               <th>制单日期</th>
               <th>记账日期</th>
           </tr>
       </thead>
       <tbody>
           <c:forEach items="${accountDetails}" var="a">
              <tr>
                  <td>${a.clientName}</td>
                  <td>${a.type=="0"?"客户":"供应商"}</td>
                  <td>${a.listNum}</td>
                  <td>
                  		<c:if test="${a.listType==0}">销售</c:if>
                  		<c:if test="${a.listType==1}">退货</c:if>
                 		<c:if test="${a.listType==2}">到货</c:if>
                    	<c:if test="${a.listType==3}">退厂</c:if>
                  </td>
                  <td>${a.listNumber}</td>
                  <td>${a.listPrice}</td>
                  <td><fmt:formatDate value="${a.createTime}" pattern="yyyy-MM-dd" type="date" dateStyle="long"/></td>
                  <td><fmt:formatDate value="${a.accountTime}" pattern="yyyy-MM-dd" type="date" dateStyle="long"/></td>
              </tr>
           </c:forEach>
       </tbody>
   </table>


   <div class="pagin">
       <div class="message">
           共&nbsp;<i class="blue">${totalRows}</i>&nbsp;条记录，当前显示第&nbsp;<i class="blue">${page.page}&nbsp;</i>页，共&nbsp;<i class="blue">${page.totalPage}&nbsp;</i>页
       </div>
       <ul class="paginList">
           <c:choose>
               <c:when test="${page.page>=page.totalPage && page.totalPage != 1}">
                          <li class="paginItem"><a href="accountDetail/accountDetailInfoList/*/-1/-1/*/*/1"><span class="pagepreEndCurrent"></span></a></li>
                      </c:when>
               <c:otherwise>
                          <li class="paginItem"><a href="javascript:;"><span class="pagepreEnd"></span></a></li>
                      </c:otherwise>
           </c:choose>
           <c:choose>
               <c:when test="${page.page>1}">
                   <li class="paginItem"><a href="accountDetail/accountDetailInfoList/*/-1/-1/*/*/${page.page-1}"><span class="pagepreCurrent"></span></a></li>
                      </c:when>
               <c:otherwise>
                          <li class="paginItem"><a href="javascript:;"><span class="pagepre"></span></a></li>
                      </c:otherwise>
           </c:choose>
           <c:forEach var="i" begin="1" end="${page.totalPage}">
               <c:choose>
                   <c:when test="${i==page.page}">
                       <li class="paginItem current"><a href="accountDetail/accountDetailInfoList/*/-1/-1/*/*/${i}">${i}</a></li>
                   </c:when>
                   <c:otherwise>
                       <li class="paginItem"><a href="accountDetail/accountDetailInfoList/*/-1/-1/*/*/${i}">${i}</a></li>
                   </c:otherwise>
               </c:choose>
           </c:forEach>

           <c:choose>
               <c:when test="${page.page<page.totalPage && page.totalPage!=1}">
                   <li class="paginItem"><a href="accountDetail/accountDetailInfoList/*/-1/-1/*/*/${page.page+1}"><span class="pagenxtCurrent"></span></a></li>
                      </c:when>
               <c:otherwise>
                          <li class="paginItem"><a href="javascript:;"><span class="pagenxt"></span></a></li>
                      </c:otherwise>
           </c:choose>

           <c:choose>
               <c:when test="${page.page<page.totalPage}">
                   <li class="paginItem"><a href="accountDetail/accountDetailInfoList/*/-1/-1/*/*/${page.totalPage}"><span class="pagenxtEndCurrent"></span></a></li>
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

