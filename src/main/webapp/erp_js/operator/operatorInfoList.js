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
    
    $("#edit").click(function() {
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
            item.each(function() {
                var id = $(this).parent("td").parent("tr").find("#operatorId").html();
                var state_flag = false; //代表审核状态是否为已审核
              	$.ajax({
                      type: "get",
                      async: false,
                      url: window.basePath +"operator/operatorCheckState/" + id,
                      success: function(ok) {
                      	if(ok){
      				     	alert("员工信息已审核，请取消审核后，再编辑修改");
      				     	state_flag = false;
      				     }else{
      				    	state_flag = true;
      				     }
                      }
                  });
              	if(state_flag){
                   window.location = window.basePath +
                       "operator/operatorInfoEdit/toEdit/" + id;
                   return true;
              	}
          });
        }
    });

    $("#updateState").click(function() {
        var item = $("tbody input[type=checkbox]:checked");
        var len = item.length;
        var str = "";
        if (len == 0) {
            window.confirm("至少选择一项");
            return false;
        } else {
            item.each(function() {
                    str += $(this).parent("td").parent("tr").find("#operatorId").html() + ",";
                });
            var r = window.confirm("确定要审核此 " + len + " 条数据吗？");
            if (r) {
                $.ajax({
                    url: window.basePath + "operator/updateState?",
                    type: "POST",
                    dataType: "json",
                    data: "strArr=" + str,
                    success : function(data) {
		     			if(data.state){
		      			   alert("审核操作成功！");
		      			   window.location = window.basePath + data.servlet;
		      			}
					 },
					 error : function(error) {
						alert("审核操作失败！");
					 }
                });
            }
        }
    });

    $("#updateUseState").click(function() {
        var item = $("tbody input[type=checkbox]:checked");
        var len = item.length;
        var str = "";
        if (len == 0) {
            window.confirm("至少选择一项");
            return false;
        } else {
            item.each(function() {
                    str += $(this).parent("td").parent("tr").find("#operatorId").html() + ",";
                });
            var r = window.confirm("确定要停用此 " + len + " 条数据吗？");
            if (r) {
                $.ajax({
                    url: window.basePath + "operator/updateUseState?",
                    type: "POST",
                    dataType: "json",
                    data: "strArr=" + str,
                    success : function(data) {
		     			if(data.state){
		      			   alert("停用操作成功！");
		      			   window.location = window.basePath + data.servlet;
		      			}
					 },
					 error : function(error) {
						alert("停用操作失败！");
					 }
                });
            }
        }
    });
   
    $("body").keydown(function() {
	    if (event.keyCode == "13") {//keyCode=13是回车键
	        $('#search').click();
	    }
	});
  //搜索按钮处理
    $("#search").click(function() {
    	var name = $("#name").val();
    	var realname = $("#realname").val();
    	var state = $("#state").val();
    	var useState = $("#useState").val();
    	//根据条件变量状态生成一个rest风格查询的url
    	var url = "/operator/operatorInfoList";
    	//添加员工账号条件
    	if(name == ""){
    		url = url +"/*";
    	}else{
    		url = url +"/"+name;
    	}
    	//添加员工姓名条件
    	if(realname == ""){
    		url = url +"/*";
    	}else{
    		url = url +"/"+realname;
    	}
    	//添加审核状态条件
    	url = url +"/"+state;
    	//添加使用状态条件
    	url = url +"/"+useState;
    	//添加要显示的页数
    	url = url +"/"+1;
    	window.location = window.basePath + url;//对应get请求
    });
});