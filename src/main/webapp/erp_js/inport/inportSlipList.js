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
                $(":checkbox").parent("td").parent("tr").children().toggleClass("trbg");
            } else {
                $(":checkbox").attr("checked", false);
                $(":checkbox").parent("td").parent("tr").children().toggleClass("trbg");
            }
        });
    //选中行，选中checkbox
    $("tbody tr").click(
        function() {
            //为点击的这一行切换样式bgRed里的代码：background-color:#FF0000;
            $(this).children().toggleClass("trbg");
            //判断td标记的背景颜色和body的背景颜色是否相同;
            if ($(this).children().css("background-color") != $(document.body).css("background-color")) {
                //如果相同，CheckBox.checked=true;
                $(this).children().first().children().attr("checked", true);
            } else {
                //如果不同,CheckBox.checked=false;
                $(this).children().first().children().attr("checked", false);
            }
        });
    
    $("#edit").click(function() {
         var item = $("tbody input[type=checkbox]:checked");
         var search_flag = false;
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
            	var inportId = $(this).parent("td").parent("tr").find("#inportId").html();
            	state_flag = searchState(inportId);
 	      	if(state_flag==0){
 	            search_flag = true;
 	      	}else{
 	      		search_flag = false;
 	      	}
 	      	if(search_flag){
 	      		window.location = window.basePath +	"inport/inportSlipEdit/" + inportId;
 	      	}else{
 	      		 alert("只能修改【未审核】单据，请检查后重新选择！");
 	      	}
             
           });
         }
     });
    
    $("#delete").click(function() {
        var item = $("tbody input[type=checkbox]:checked");
        var len = item.length;
        if (len > 1) {
            window.confirm('不能批量删除!');
            return false;
        }
        if (len == 0) {
            window.confirm("至少选择一项");
            return false;
        } 
        if (len == 1) {
            item.each(function() {
           	var inportId = $(this).parent("td").parent("tr").find("#inportId").html();
           	state_flag = searchState(inportId);
	      	if(state_flag==0){
	            search_flag = true;
	      	}else{
	      		search_flag = false;
	      	}
	      	if(search_flag){
	      		$.ajax({
	                 type: "get",
	                 async: false,
	                 url: window.basePath +"inport/inportDelete/" + inportId,
	                 success: function(ok) {
	                     if (ok) {
	                         window.confirm("删除操作成功！");
	                         window.location = window.basePath + "inport/inportSlipList/0/*/*/-1/1";
	                     }
	                 }
	             });
	      	}else{
	      		 alert("只能删除【未审核】单据，请检查后重新选择！");
	      	}
            
          });
        }
    });

   	function searchState(inpotId){
   		var state_flag = 0; //代表单据状态
   	   	$.ajax({
           type: "get",
           async: false,
           url: window.basePath +"inport/inportState/" + inpotId,
           success: function(ok) {
        	   state_flag = ok;
           }
       });
   	   return state_flag;
   	}
   	
    $("#updateState").click(function() {
          var item = $("tbody input[type=checkbox]:checked");
          var len = item.length;
          var str = "";
          var inportId = "";
          var state_flag = "";
          var search_flag = false;
          if (len == 0) {
              window.confirm("至少选择一项");
              return false;
          } else {
              item.each(function() {
            	  inportId = $(this).parent("td").parent( "tr").find("#inportId").html();
            	  state_flag = searchState(inportId);
            	  if(state_flag==0){
                  	str += inportId + ",";
                  	search_flag = true;
            	  }else{
            		 search_flag = false;
            	  }
              });
              if(search_flag){
	             var r = window.confirm("确定要审核此 " + len + " 条数据吗？");
	             if (r) {
	                 $.ajax({
	                         url: window.basePath + "inport/updateState?",
	                         type: "POST",
	                         dataType: "json",
	                         data: "strArr=" + str,
	                         success : function(data) {
	     		     			if(data.state){
	     		      			   alert("单据审核成功！");
	     		      			 	window.location = window.basePath + data.servlet;
	     		      			}
	     					 },
	     					 error : function(error) {
	     						alert("单据审核失败！");
	     					 }
	                     });
	             }
              }else{
            	  alert("只能审核【未审核】单据，请检查后重新选择！");
              }
          }
      });
    
    $("#updateBackState").click(function() {
        var item = $("tbody input[type=checkbox]:checked");
        var len = item.length;
        var str = "";
        var inportId = "";
        var state_flag = "";
        var search_flag = false;
        if (len == 0) {
            window.confirm("至少选择一项");
            return false;
        } else {
            item.each(function() {
          	  inportId = $(this).parent("td").parent( "tr").find("#inportId").html();
          	  state_flag = searchState(inportId);
          	  if(state_flag==1){
                	str += inportId + ",";
                	search_flag = true;
          	  }else{
          		 search_flag = false;
          	  }
            });
            if(search_flag){
	             var r = window.confirm("确定要反审此 " + len + " 条数据吗？");
	             if (r) {
	                 $.ajax({
                         url: window.basePath + "inport/updateBackState?",
                         type: "POST",
                         dataType: "json",
                         data: "strArr=" + str,
                         success : function(data) {
     		     			if(data.backState){
     		      			   alert("单据反审成功！");
     		      			 	window.location = window.basePath + data.servlet;
     		      			}
     					 },
     					 error : function(error) {
     						alert("单据反审失败！");
     					 }
                     });
	             }
            }else{
          	  alert("只能反审【已审核】单据，请检查后重新选择！");
            }
        }
    });


    $("#updateReviewState").click(function() {
        var item = $("tbody input[type=checkbox]:checked");
        var len = item.length;
        if (len > 1) {
            window.confirm('不能批量入库审核!');
            return false;
        }
        if (len == 0) {
            window.confirm("至少选择一项");
            return false;
        } 
        if (len == 1) {
            item.each(function() {
           	var inportId = $(this).parent("td").parent("tr").find("#inportId").html();
           	state_flag = searchState(inportId);
	      	if(state_flag==1){
	            search_flag = true;
	      	}else{
	      		search_flag = false;
	      	}
	      	if(search_flag){
	      		$.ajax({
	                 type: "get",
	                 async: false,
	                 url: window.basePath +"inport/inportReview/" + inportId,
	                 success : function(data) {
  		     			if(data.reviewState){
  		      			   alert("入库审核成功！");
  		      			   window.location = window.basePath + data.servlet;
  		      			}
					 },
					 error : function(error) {
						alert("入库审核失败！");
					 }
	             });
	      	}else{
	      		 alert("只能入库【已审核】单据，请检查后重新选择！");
	      	}
            
          });
        }
    });
    
    $("#updateAccountState").click(function() {
        var item = $("tbody input[type=checkbox]:checked");
        var len = item.length;
        if (len > 1) {
            window.confirm('不能批量财务审核!');
            return false;
        }
        if (len == 0) {
            window.confirm("至少选择一项");
            return false;
        } 
        if (len == 1) {
            item.each(function() {
           	var inportId = $(this).parent("td").parent("tr").find("#inportId").html();
           	state_flag = searchState(inportId);
	      	if(state_flag==2){
	            search_flag = true;
	      	}else{
	      		search_flag = false;
	      	}
	      	if(search_flag){
	      		$.ajax({
	                 type: "get",
	                 async: false,
	                 url: window.basePath +"inport/inportAccount/" + inportId,
	                 success : function(data) {
		     			if(data.accountState){
		      			  alert("财务审核成功！");
		      			  window.location = window.basePath + data.servlet;
		      			}
					 },
					 error : function(error) {
					    alert("财务审核失败！");
					 }
	             });
	      	}else{
	      		 alert("只能入库【已入库】单据，请检查后重新选择！");
	      	}
            
          });
        }
    });
   
    $("body").keydown(function() {
	    if (event.keyCode == "13") {//keyCode=13是回车键
	        $('#search').click();
	    }
	});
  //搜索按钮处理
    $("#search").click(function() {
    	var supplier = $("#supplier").val();
    	var createTime = $("#createTime").val();
    	var state = $("#state").val();
    	//根据条件变量状态生成一个rest风格查询的url
    	var url = "/inport/inportSlipList/0";
    	//添加供应商条件
    	if(supplier == ""){
    		url = url +"/*";
    	}else{
    		url = url +"/"+supplier;
    	}
    	//添加制单日期
    	if(createTime == ""){
    		url = url +"/*";
    	}else{
    		url = url +"/"+createTime;
    	}
    	//添加审核状态条件
    	url = url +"/"+state;
    	//添加要显示的页数
    	url = url +"/"+1;
    	window.location = window.basePath + url;//对应get请求
    });
});