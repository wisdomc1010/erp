<%@ page language="java" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <title>销售单列表</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="erp_js/sale/saleSlipList.js"></script>

</head>
<body>
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li>销售管理</li>
            <li>销售单管理</li>
            <li>销售单列表</li>
        </ul>
    </div>

    <div class="rightinfo">
     <form:form commandName="page">
         <div class="tools">
              <ul class="toolbar">
                 <li><label>&nbsp;&nbsp;客户名称：&nbsp;&nbsp;</label> 
                 <form:input path="customer"  cssClass="dfsearchinput" style="width:120px"/> 
                 <label>&nbsp;&nbsp;审核状态：&nbsp;&nbsp;</label>
				 <form:select path="state" id="state" cssClass="dfsearchinput" style="width:70px">
					<form:option value="-1">全部</form:option>
					<form:option value="0">未审核</form:option>
					<form:option value="1">已审核</form:option>
					<form:option value="2">已出库</form:option>
					<form:option value="3">已记账</form:option>
     			 </form:select>
 				 <label>&nbsp;&nbsp;制单日期：&nbsp;&nbsp;</label> 
        		 <form:input path="createTime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" cssClass="dfsearchinput" style="width:120px"/>
			</li>
              </ul>
            </div>
        <div>
            <ul class="searchtoolbar">
                <li><a href="sale/saleSlipAdd"><span><img src="images/t01.png" /></span>添加</a></li>
                <li><a id="edit"><span><img src="images/t02.png" /></span>修改</a></li>
 			    <li><a id="delete"><span><img src="images/t12.png" /></span>删除</a></li>
			    <li><a id="updateState"><span><img src="images/t06.png" /></span>单据审核</a></li>
			    <li><a id="updateBackState"><span><img src="images/t13.png" /></span>单据反审</a></li>
			    <li><a id="updateReviewState"><span><img src="images/t08.png" /></span>出库审核</a></li>
 			    <li><a id="updateAccountState"><span><img src="images/t09.png" /></span>财务审核</a></li>
                <li><a id="search"><span><img src="images/t07.png" /></span>查询</a></li>
            </ul>
        </div>

            <table class="tablelist">
            <thead>
                <tr>
                    <th width='30px'><input id="chk_all" type="checkbox" /></th>
                    <th style="display: none">ID</th>
                    <th width='100px'>单据号</th>
                    <th width='80px'>客户</th>
                    <th width='70px'>总数量</th>
                    <th width='70px'>总金额</th>
                    <th width='70px'>审核状态</th>
                    <th width='70px'>创建人</th>
			        <th width='90px'>创建时间</th>
			        <th width='70px'>更新人</th>
			        <th width='90px'>更新时间</th>
			        <th width='70px'>入库人</th>
			        <th width='90px'>入库时间</th>
			        <th width='70px'>记账人</th>
			        <th width='90px'>记账时间</th>
			        <th>备注</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${saleSlip}" var="s">
                    <tr>
                        <td width="30px"><input name="" type="checkbox" value="" /></td>
                        <td id="saleId" style="display: none">${s.id}</td>
                        <td><a href="sale/showSaleDetail/${s.id}"><span id="saleNum">${s.saleNum}</span></a></td>
                        <td>${s.customer}</td>
                        <td>${s.sumNumber}</td>
                        <td>${s.sumPrice}</td>
                        <td>
                         	<c:if test="${s.state==0}">未审核</c:if>
                         	<c:if test="${s.state==1}">已审核</c:if>
                         	<c:if test="${s.state==2}">已出库</c:if>
                         	<c:if test="${s.state==3}">已记账</c:if>
                        </td>
                        <td>${s.createOperator}</td>
                        <td><fmt:formatDate value="${s.createTime}" pattern="yyyy-MM-dd" type="date" dateStyle="long"/></td>
                        <td>${s.updateOperator}</td>
                        <td><fmt:formatDate value="${s.updateTime}" pattern="yyyy-MM-dd" type="date" dateStyle="long"/></td>
                        <td>${s.reviewOperator}</td>
                        <td><fmt:formatDate value="${s.reviewTime}" pattern="yyyy-MM-dd" type="date" dateStyle="long"/></td>
                        <td>${s.accountOperator}</td>
                        <td><fmt:formatDate value="${s.accountTime}" pattern="yyyy-MM-dd" type="date" dateStyle="long"/></td>
                        <td>${s.remark}</td>
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
                               <li class="paginItem"><a href="sale/saleSlipList/0/*/*/-1/1"><span class="pagepreEndCurrent"></span></a></li>
                           </c:when>
                    <c:otherwise>
                               <li class="paginItem"><a href="javascript:;"><span class="pagepreEnd"></span></a></li>
                           </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${page.page>1}">
                        <li class="paginItem"><a href="sale/saleSlipList/0/*/*/-1/${page.page-1}"><span class="pagepreCurrent"></span></a></li>
                           </c:when>
                    <c:otherwise>
                               <li class="paginItem"><a href="javascript:;"><span class="pagepre"></span></a></li>
                           </c:otherwise>
                </c:choose>
                <c:forEach var="i" begin="1" end="${page.totalPage}">
                    <c:choose>
                        <c:when test="${i==page.page}">
                            <li class="paginItem current"><a href="sale/saleSlipList/0/*/*/-1/${i}">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="paginItem"><a href="sale/saleSlipList/0/*/*/-1/${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:choose>
                    <c:when test="${page.page<page.totalPage && page.totalPage!=1}">
                        <li class="paginItem"><a href="sale/saleSlipList/0/*/*/-1/${page.page+1}"><span class="pagenxtCurrent"></span></a></li>
                           </c:when>
                    <c:otherwise>
                               <li class="paginItem"><a href="javascript:;"><span class="pagenxt"></span></a></li>
                           </c:otherwise>
                </c:choose>

                <c:choose>
                    <c:when test="${page.page<page.totalPage}">
                        <li class="paginItem"><a href="sale/saleSlipList/0/*/*/-1/${page.totalPage}"><span class="pagenxtEndCurrent"></span></a></li>
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