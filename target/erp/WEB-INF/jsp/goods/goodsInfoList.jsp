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
    <title>商品信息管理</title>
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
        $(document)
            .ready(
                function() {
                	
                	 //全选checkbox，选中行
                    $("#chk_all").click(
                        function() {
                            if ($("#chk_all").attr("checked")) {
                                $(":checkbox").attr("checked", true);
                                $(":checkbox").parent("td")
                                    .parent("tr").children()
                                    .toggleClass("trbg");
                            } else {
                                $(":checkbox").attr("checked", false);
                                $(":checkbox").parent("td")
                                    .parent("tr").children()
                                    .toggleClass("trbg");
                            }
                        });
                    //选中行，选中checkbox
                    $("tbody tr").click(
                        function() {
                            //为点击的这一行切换样式bgRed里的代码：background-color:#FF0000;
                            $(this).children().toggleClass("trbg");
                            //判断td标记的背景颜色和body的背景颜色是否相同;
                            if ($(this).children().css(
                                    "background-color") != $(
                                    document.body).css(
                                    "background-color")) {
                                //如果相同，CheckBox.checked=true;
                                $(this).children().first().children()
                                    .attr("checked", true);
                            } else {
                                //如果不同,CheckBox.checked=false;
                                $(this).children().first().children()
                                    .attr("checked", false);
                            }
                        });
                    
                    $("#edit")
                        .click(
                            function() {
                                var item = $("tbody input[type=checkbox]:checked");
                                var len = item.length;
                                if (len > 1) {
                                    window.confirm('不能批量修改!');
                                    return false;
                                }
                                if (len == 0) {
                                    window.confirm("至少选择一项");
                                    return false;
                                }
                                if (len == 1) {
                                    item
                                        .each(function() {
                                            var id = $(this).parent("td").parent("tr").find("#goodsId").html();
    	                                    var state_flag = false; //代表审核状态是否为已审核
    	                                  	$.ajax({
    	                                          type: "get",
    	                                          async: false,
    	                                          url: window.basePath +"goods/goodsCheckState/" + id,
    	                                          success: function(ok) {
    	                                          	if($.trim(ok)=="1"){
    	                          				     	alert("商品信息已审核，请取消审核后，再编辑修改");
    	                          				     	state_flag = false;
    	                          				     }else{
    	                          				    	state_flag = true;
    	                          				     }
    	                                          }
    	                                      });
    	                                  	if(state_flag){
    	                                       window.location = window.basePath +
    	                                           "goods/goodsInfoEdit/toEdit/" + id;
    	                                       return true;
    	                                  	}
                                        });
                                }
                            });

                    $("#updateState")
                        .click(
                            function() {
                                var item = $("tbody input[type=checkbox]:checked");
                                var len = item.length;
                                var str = "";
                                if (len == 0) {
                                    window.confirm("至少选择一项");
                                    return false;
                                } else {
                                    item
                                        .each(function() {
                                            str += $(this)
                                                .parent(
                                                    "td")
                                                .parent(
                                                    "tr")
                                                .find(
                                                    "#goodsId")
                                                .html() +
                                                ",";
                                        });
                                    var r = window
                                        .confirm("确定要更新此 " +
                                            len +
                                            " 条数据吗？");
                                    if (r) {
                                        $
                                            .ajax({
                                                url: window.basePath +
                                                    "goods/updateState?",
                                                type: "POST",
                                                dataType: "json",
                                                data: "strArr=" +
                                                    str,
                                                success: function(
                                                    ok) {
                                                    if (ok) {
                                                        window
                                                            .confirm("更新操作成功！");
                                                        window.location = window.basePath +
                                                            "goods/goodsInfoList/*/*/*/-1/-1/1";
                                                    }
                                                }
                                            });
                                    }
                                }
                            });

                    $("#updateUseState")
                        .click(
                            function() {
                                var item = $("tbody input[type=checkbox]:checked");
                                var len = item.length;
                                var str = "";
                                if (len == 0) {
                                    window.confirm("至少选择一项");
                                    return false;
                                } else {
                                    item
                                        .each(function() {
                                            str += $(this)
                                                .parent(
                                                    "td")
                                                .parent(
                                                    "tr")
                                                .find(
                                                    "#goodsId")
                                                .html() +
                                                ",";
                                        });
                                    var r = window
                                        .confirm("确定要更新此 " +
                                            len +
                                            " 条数据吗？");
                                    if (r) {
                                        $
                                            .ajax({
                                                url: window.basePath +
                                                    "goods/updateUseState?",
                                                type: "POST",
                                                dataType: "json",
                                                data: "strArr=" +
                                                    str,
                                                success: function(
                                                    ok) {
                                                    if (ok) {
                                                        window
                                                            .confirm("更新操作成功！");
                                                        window.location = window.basePath +
                                                            "goods/goodsInfoList/*/*/*/-1/-1/1";
                                                    }
                                                }
                                            });
                                    }
                                }
                            });
                   
                    
                  //搜索按钮处理
                    $("#search").click(function() {
                    	var num = $("#num").val();
                    	var name = $("#name").val();
                    	var supplier = $("#supplier").val();
                    	var state = $("#state").val();
                    	var useState = $("#useState").val();
                    	//根据条件变量状态生成一个rest风格查询的url
                    	var url = "/goods/goodsInfoList";
                    	//添加商品编号条件
                    	if(num == ""){
                    		url = url +"/*";
                    	}else{
                    		url = url +"/"+num;
                    	}
                    	//添加商品名称条件
                    	if(name == ""){
                    		url = url +"/*";
                    	}else{
                    		url = url +"/"+name;
                    	}
                    	//添加供应商名称条件
                    	if(supplier == ""){
                    		url = url +"/*";
                    	}else{
                    		url = url +"/"+supplier;
                    	}
                    	//添加审核状态条件
                    	url = url +"/"+state;
                    	//添加使用状态条件
                    	url = url +"/"+useState;
                    	//添加要显示的页数
                    	url = url +"/"+1;
                    	alert(url);
                    	window.location = window.basePath + url;//对应get请求
                    });
                });
    </script>

</head>


<body>

    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li>基础管理</li>
            <li>商品信息管理</li>
            <li>商品列表</li>
        </ul>
    </div>

    <div class="rightinfo">

        <form:form commandName="page">
            <div class="tools">
                <ul class="toolbar">
                    <li><label>商品编码：&nbsp;&nbsp;</label> 
                    <form:input path="num"  cssClass="dfsearchinput" /> 
                    <label>商品名称：&nbsp;&nbsp;</label>
                    <form:input path="name" cssClass="dfsearchinput" />
                    <label>供应商名称：&nbsp;&nbsp;</label>
                    <form:input path="supplier" cssClass="dfsearchinput" />
                    <label>审核状态：&nbsp;&nbsp;</label>
					<form:select path="state" id="state" cssClass="select_search">
						<form:option value="-1">全部</form:option>
						<form:option value="0">未审核</form:option>
						<form:option value="1">审核</form:option>
        			</form:select>
        			<label>使用状态：&nbsp;&nbsp;</label>
					<form:select path="useState" id="useState" cssClass="select_search">
						<form:option value="-1">全部</form:option>
						<form:option value="0">停用</form:option>
						<form:option value="1">在用</form:option>
        			</form:select></li>
                        </ul>
                    </div>
                    <div>
                        <ul class="searchtoolbar">
                            <li>
                            	<a href="goods/goodsInfoAdd"><span><img src="images/t01.png" /></span>添加</a>
                            </li>
                            <li class="click">
                            	<a id="edit"><span><img src="images/t02.png" /></span>修改</a>
                            </li>
                            <li>
                            	<a id="updateState"><span><img src="images/t06.png" /></span>审核</a>
                           	</li>
                            <li>
                            	<a id="updateUseState"><span><img src="images/t03.png" /></span>停用</a>
                            </li>
                            <li>
                            	<a id="search"><span><img src="images/t07.png" /></span>查询</a>
                            </li>
                        </ul>
                    </div>

                    <table class="tablelist">
                <thead>
                    <tr>
				        <th><input id="chk_all" type="checkbox"/></th>
				        <th>商品编码</th>
				        <th>商品名称</th>
                        <th>审核状态</th>
                        <th>使用状态</th>
				        <th>零售价</th>
				        <th>成本价</th>
				        <th>供应商</th>
				        <th>创建人</th>
				        <th>创建时间</th>
				        <th>更新人</th>
				        <th>更新时间</th>
				        <th>备注</th>
			        </tr>
                </thead>
                <tbody>
                    <c:forEach items="${goods}" var="g">
                        <tr>
                            <td width="30px"><input name="" type="checkbox" value="" /></td>
                            <td id="goodsId" style="display: none">${g.id}</td>
                            <td width="70px">${g.num}</td>
                            <td width="100px">${g.name}</td>
                            <td width="70px">${g.state=="0"?"未审核":"审核"}</td>
                            <td width="70px">${g.useState=="0"?"停用":"在用"}</td>
                            <td width="70px">${g.price}</td>
                            <td width="70px">${g.cost}</td>
                            <td width="130px">${g.supplier}</td>
                            <td width="70px">${g.createOperator}</td>
                            <td width="130px"><fmt:formatDate value="${g.createTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long"/></td>
                            <td width="70px">${g.updateOperator}</td>
                            <td width="130px"><fmt:formatDate value="${g.updateTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long"/></td>
                            <td>${g.remark}</td>
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
                            <li class="paginItem"><a href="goods/goodsInfoList/*/*/*/-1/-1/1">
                            <span class="pagepreEndCurrent"></span></a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="paginItem"><a href="javascript:;">
                            <span class="pagepreEnd"></span></a></li>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${page.page>1}">
                            <li class="paginItem"><a href="goods/goodsInfoList/*/*/*/-1/-1/${page.page-1}">
                            <span class="pagepreCurrent"></span></a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="paginItem"><a href="javascript:;">
                            <span class="pagepre"></span></a></li>
                        </c:otherwise>
                    </c:choose>
                    <c:forEach var="i" begin="1" end="${page.totalPage}">
                        <c:choose>
                            <c:when test="${i==page.page}">
                                <li class="paginItem current"><a href="goods/goodsInfoList/*/*/*/-1/-1/${i}">${i}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li class="paginItem"><a href="goods/goodsInfoList/*/*/*/-1/-1/${i}">${i}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${page.page<page.totalPage && page.totalPage!=1}">
                            <li class="paginItem"><a href="goods/goodsInfoList/*/*/*/-1/-1/${page.page+1}">
                            <span class="pagenxtCurrent"></span></a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="paginItem"><a href="javascript:;">
                            <span class="pagenxt"></span></a></li>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${page.page<page.totalPage}">
                            <li class="paginItem"><a href="goods/goodsInfoList/*/*/*/-1/-1/${page.totalPage}">
                            <span class="pagenxtEndCurrent"></span></a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="paginItem"><a href="javascript:;">
                            <span class="pagenxtEnd"></span></a></li>
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