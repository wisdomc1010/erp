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
<title>库存查询</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="erp_js/stock/stockInfoList.js"></script>
</head>
<body>
<div class="place">
   <span>位置：</span>
   <ul class="placeul">
   <li><a href="#">报表管理</a></li>
   <li><a href="#">库存查询</a></li>
   <li><a href="#">库存列表</a></li>
   </ul>
   </div>
   
   <div class="rightinfo">
   
   <form:form commandName="page">
           <div class="tools">
               <ul class="toolbar">
                  <li><label>&nbsp;&nbsp;商品编号：&nbsp;&nbsp;</label> 
                  <form:input path="goodsNum"  cssClass="dfsearchinput" style="width:120px"/> 
                  <label>&nbsp;&nbsp;商品名称：&nbsp;&nbsp;</label> 
                  <form:input path="goodsName"  cssClass="dfsearchinput" style="width:120px"/> 
                  <label>&nbsp;&nbsp;供应商：&nbsp;&nbsp;</label> 
                  <form:input path="supplier"  cssClass="dfsearchinput" style="width:120px"/> 
      			</li>
               </ul>
                  </div>
                  <div>
                      <ul class="searchtoolbar">
                          <li><a id="search"><span><img src="images/t07.png" /></span>查询</a></li>
                      </ul>
                  </div>

                  <table class="tablelist" style="width: 60%">
              <thead>
                  <tr>
                      <th>商品编码</th>
                      <th>商品名称</th>
                      <th>供应商</th>
                      <th>零售价</th>
                      <th>库存数</th>
                  </tr>
              </thead>
              <tbody>
                  <c:forEach items="${stocks}" var="s">
                      <tr>
                           <td width="80px">${s.goodsNum}</td>
                           <td width="150px">${s.goodsName}</td>
                           <td width="150px">${s.supplier}</td>
                           <td width="80px">${s.price}</td>
                           <td width="80px">${s.num}</td>
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
                                 <li class="paginItem"><a href="stock/stockInfoList/*/*/*/1"><span class="pagepreEndCurrent"></span></a></li>
                             </c:when>
                      <c:otherwise>
                                 <li class="paginItem"><a href="javascript:;"><span class="pagepreEnd"></span></a></li>
                             </c:otherwise>
                  </c:choose>
                  <c:choose>
                      <c:when test="${page.page>1}">
                          <li class="paginItem"><a href="stock/stockInfoList/*/*/*/${page.page-1}"><span class="pagepreCurrent"></span></a></li>
                             </c:when>
                      <c:otherwise>
                                 <li class="paginItem"><a href="javascript:;"><span class="pagepre"></span></a></li>
                             </c:otherwise>
                  </c:choose>
                  <c:forEach var="i" begin="1" end="${page.totalPage}">
                      <c:choose>
                          <c:when test="${i==page.page}">
                              <li class="paginItem current"><a href="stock/stockInfoList/*/*/*/${i}">${i}</a></li>
                          </c:when>
                          <c:otherwise>
                              <li class="paginItem"><a href="stock/stockInfoList/*/*/*/${i}">${i}</a></li>
                          </c:otherwise>
                      </c:choose>
                  </c:forEach>

                  <c:choose>
                      <c:when test="${page.page<page.totalPage && page.totalPage!=1}">
                          <li class="paginItem"><a href="stock/stockInfoList/*/*/*/${page.page+1}"><span class="pagenxtCurrent"></span></a></li>
                             </c:when>
                      <c:otherwise>
                                 <li class="paginItem"><a href="javascript:;"><span class="pagenxt"></span></a></li>
                             </c:otherwise>
                  </c:choose>

                  <c:choose>
                      <c:when test="${page.page<page.totalPage}">
                          <li class="paginItem"><a href="stock/stockInfoList/*/*/*/${page.totalPage}"><span class="pagenxtEndCurrent"></span></a></li>
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
