<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>

<script type="text/javascript">
$(document).ready(function(){
  $(".click").click(function(){
  $(".tip").fadeIn(200);
  });
  
  $(".tiptop a").click(function(){
  $(".tip").fadeOut(200);
});

  $(".sure").click(function(){
  $(".tip").fadeOut(100);
});

  $(".cancel").click(function(){
  $(".tip").fadeOut(100);
});

});
</script>


</head>


<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">销售管理</a></li>
    <li><a href="#">销售单管理</a></li>
    <li><a href="#">销售单列表</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
<ul class="toolbar">
    		<label>客户名称：&nbsp;&nbsp;</label><input name="" type="text" class="dfsearchinput" />
				<label>单据状态：&nbsp;&nbsp;</label>
				<select class="select">
					<option value="">全部</option>	
					<option value="0">未审核</option>	
					<option value="1">已审核</option>	
					<option value="2">已出库</option>	
					<option value="3">已记账</option>	
				</select>
				<label>单据日期：&nbsp;&nbsp;</label><input name="" type="text" class="dfsearchinput" />
				<ul class="searchtoolbar">
         <li class="click"><span><img src="images/t01.png" /></span>添加</li>
       	 <li class="click"><span><img src="images/t02.png" /></span>修改</li>
       	 <li><span><img src="images/t03.png" /></span>删除</li>
       	 <li><span><img src="images/t06.png" /></span>单据审核</li>
       	 <li><span><img src="images/t08.png" /></span>出库审核</li>
       	 <li><span><img src="images/t09.png" /></span>财务审核</li>
       	 <li><span><img src="images/t07.png" /></span>查询</li>
        </ul>
        
			</ul>
        

    </div>
    
    
    <table class="tablelist">
    	<thead>
    	<tr>
        <th><input name="" type="checkbox" value="" checked="checked"/></th>
        <th>单据号</th>
        <th>客户</th>
        <th>总数量</th>
        <th>总金额</th>
        <th>单据状态</th>
        <th>制单人</th>
        <th>制单日期</th>
        <th>修改人</th>
        <th>修改日期</th>
        <th>记账人</th>
        <th>记账日期</th>
        <th>备注</th>
        <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr>
        <td><input name="" type="checkbox" value="" /></td>
        <td>20130908</td>
        <td>20130908</td>
        <td>着没意思</td>
        <td>admin</td>
        <td>江苏南京</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>已审核</td>
        <td><a href="#" class="tablelink">查看明细</a></td>
        </tr> 
        
        <tr>
        <td><input name="" type="checkbox" value="" /></td>
        <td>20130908</td>
        <td>20130908</td>
        <td>着没意思</td>
        <td>admin</td>
        <td>江苏南京</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>已审核</td>
        <td><a href="#" class="tablelink">查看明细</a></td>
        </tr> 
        
        <tr>
        <td><input name="" type="checkbox" value="" /></td>
        <td>20130908</td>
        <td>20130908</td>
        <td>着没意思</td>
        <td>admin</td>
        <td>江苏南京</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>已审核</td>
        <td><a href="#" class="tablelink">查看明细</a></td>
        </tr> 
        
        <tr>
        <td><input name="" type="checkbox" value="" /></td>
        <td>20130908</td>
        <td>20130908</td>
        <td>着没意思</td>
        <td>admin</td>
        <td>江苏南京</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>已审核</td>
        <td><a href="#" class="tablelink">查看明细</a></td>
        </tr> 
        
        <tr>
        <td><input name="" type="checkbox" value="" /></td>
        <td>20130908</td>
        <td>20130908</td>
        <td>着没意思</td>
        <td>admin</td>
        <td>江苏南京</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>已审核</td>
        <td><a href="#" class="tablelink">查看明细</a></td>
        </tr> 
        
        <tr>
        <td><input name="" type="checkbox" value="" /></td>
        <td>20130908</td>
        <td>20130908</td>
        <td>着没意思</td>
        <td>admin</td>
        <td>江苏南京</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>已审核</td>
        <td><a href="#" class="tablelink">查看明细</a></td>
        </tr> 
        
        <tr>
        <td><input name="" type="checkbox" value="" /></td>
        <td>20130908</td>
        <td>20130908</td>
        <td>着没意思</td>
        <td>admin</td>
        <td>江苏南京</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>已审核</td>
        <td><a href="#" class="tablelink">查看明细</a></td>
        </tr> 
        
         <tr>
        <td><input name="" type="checkbox" value="" /></td>
        <td>20130908</td>
        <td>20130908</td>
        <td>着没意思</td>
        <td>admin</td>
        <td>江苏南京</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>已审核</td>
        <td><a href="#" class="tablelink">查看明细</a></td>
        </tr> 
        
        <tr>
        <td><input name="" type="checkbox" value="" /></td>
        <td>20130908</td>
        <td>20130908</td>
        <td>着没意思</td>
        <td>admin</td>
        <td>江苏南京</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>2013-09-09 15:05</td>
        <td>已审核</td>
        <td>已审核</td>
        <td><a href="#" class="tablelink">查看明细</a></td>
        </tr>       
        </tbody>
    </table>
    
   
    <div class="pagin">
    	<div class="message">共<i class="blue">1256</i>条记录，当前显示第&nbsp;<i class="blue">2&nbsp;</i>页</div>
        <ul class="paginList">
        <li class="paginItem"><a href="javascript:;"><span class="pagepre"></span></a></li>
        <li class="paginItem"><a href="javascript:;">1</a></li>
        <li class="paginItem current"><a href="javascript:;">2</a></li>
        <li class="paginItem"><a href="javascript:;">3</a></li>
        <li class="paginItem"><a href="javascript:;">4</a></li>
        <li class="paginItem"><a href="javascript:;">5</a></li>
        <li class="paginItem more"><a href="javascript:;">...</a></li>
        <li class="paginItem"><a href="javascript:;">10</a></li>
        <li class="paginItem"><a href="javascript:;"><span class="pagenxt"></span></a></li>
        </ul>
    </div>
    
    
    <div class="tip">
    	<div class="tiptop"><span>提示信息</span><a></a></div>
        
      <div class="tipinfo">
        <span><img src="images/ticon.png" /></span>
        <div class="tipright">
        <p>是否确认对信息的修改 ？</p>
        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
        </div>
        </div>
        
        <div class="tipbtn">
        <input name="" type="button"  class="sure" value="确定" />&nbsp;
        <input name="" type="button"  class="cancel" value="取消" />
        </div>
    
    </div>
    
    
    
    
    </div>
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>

</body>

</html>
