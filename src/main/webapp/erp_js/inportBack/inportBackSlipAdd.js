$(document).ready(function() {
	$('.inportTip').hide();
		var goodsId_flag = false; //代表商品信息是否通过检测
		var number_flag = false;//代表数量信息是否通过检测
		var add_flag = false;//代表整个添加是否通过检测
		var v_goodsId = "";
        var v_goodInfo = "";
        var v_number = "";
        var v_remark = "";
        function checkAdd() {
        	v_goodsId = $("#goodsSelect").val();
        	v_goodInfo = $("#goodsSelect").find("option:selected").text();
        	v_number = $("#numberAdd").val();
        	v_remark = $("#remarkAdd").val();
        	num_flag = false;
            //检查商品是否为空
            if (v_goodsId == "") {
                $("#goodsId_error").html("商品不能为空");
                $("#goodsId_error").addClass("error_msg");
                goodsId_flag = false;
            }else{
            	$("#goodsId_error").html("");
                $("#goodsIderror").removeClass("error_msg");
                goodsId_flag = true;
            }
            //检查商品数量是否为空
            if (v_number == "") {
                $("#number_error").html("数量不能为空");
                $("#number_error").addClass("error_msg");
                number_flag = false;
            }else{
            	 $("#number_error").html("");
                 $("#number_error").removeClass("error_msg");
                 number_flag = true;
            }
            //通过检查返回true,未通过返回false
            //前面ajax用同步,目的是等前面ajax请求
            //回调函数执行完毕再执行此行代码
            return goodsId_flag&&number_flag;
        }
		$("#addDetail").click(function() {
			$(".inportTip").fadeIn(200);
			$("#goodsSelect").val("");
			$("#goodsSelect").trigger("chosen:updated");
			$("#numberAdd").val("");
			$("#remarkAdd").val("");
			$("#goodsStock").val("");
			$("#goodsStock").hide();
		});

		$("#close").click(function() {
			$(".inportTip").fadeOut(200);
		});

		$(".sure").click(function() {
			add_flag = checkAdd(); 
			if(add_flag){
				if(confirm('确定要保存当前填写信息吗？')){
					$(".inportTip").fadeOut(100);
					var goodInfo = v_goodInfo.split(':'); 
					var v_goodNum = goodInfo[0]; 
					var v_goodName = goodInfo[1]; 
					var content = ('<tr><td width="30px"><input id="chk_all" type="checkbox" /></td>'+
					'<td style="display: none"><input id="goodsId" name="goodsId" value="' + v_goodsId + '" /></td>'+
					'<td>' + v_goodNum + '</td>'+
					'<td>' + v_goodName + '</td>'+
					'<td><input id="number" name="number" class="dfsearchinput" value="' + v_number + '" /></td>'+
					'<td><input id="remark" name="remark" class="dfsearchinput" value="' + v_remark + '" /></td></tr>');
					$("#inportDetail").append(content);
					
				}
			}
		});

		$(".cancel").click(function() {
			if(confirm('确定要取消当前填写信息吗？')){
				$(".inportTip").fadeOut(100);
			}
		});
		
		$("#saveCancel").click(function() {
			if(confirm('确定不保存吗？')){
				parent.location.href = window.basePath + "inportBack/inportBackSlipManage";
			}
		});

	//全选checkbox，选中行
	$("#chk_all").click(function() {
		if ($("#chk_all").attr("checked")) {
			$(":checkbox").attr("checked", true);
			$(":checkbox").parent("td").parent("tr").children().toggleClass("trbg");
		} else {
			$(":checkbox").attr("checked", false);
			$(":checkbox").parent("td").parent("tr").children().toggleClass("trbg");
		}
	});
	//选中行，选中checkbox
	$("tbody tr").live("click",function(){
		//为点击的这一行切换样式bgRed里的代码：background-color:#FF0000;
		$(this).children().toggleClass("trbg");
		//判断td标记的背景颜色和body的背景颜色是否相同;
		if ($(this).children().css(
				"background-color") != $(document.body).css("background-color")) {
			//如果相同，CheckBox.checked=true;
			$(this).children().first().children().attr("checked", true);
		} else {
			//如果不同,CheckBox.checked=false;
			$(this).children().first().children().attr("checked", false);
		}
	});

	//检查商品库存
	$("#goodsSelect").live("change",function(){
		var goodsId =  $("#goodsSelect").find("option:selected").val();
		$("#goodsStock").empty();
		$.ajax({
		   url:window.basePath + "inportBack/inportBackStock/" + goodsId,
		   dataType:"json",
		   type:"GET",
		   success: function(data){
			 $("#goodsStock").show();
			 $("#goodsStock").val(data.goodsNum);
		   }
		}); 
	});
	//检查退厂数量是否超过库存数
	$("#numberAdd").live("blur",function(){
		var stockNumber = $("#goodsStock").val();
		var inputNumber = parseInt($(this).val());
		if(inputNumber > stockNumber){
			alert('\"退厂数\"不能大于\"库存数\"，请重新输入！');
			$(this).select();
			return false;
		}
	});

	/**
	 * 删除明细
	 */
	$("#removeDetail").click(function() {
		var item = $("#inportSlipDetail tbody input[type=checkbox]:checked");
		var len = item.length;
		if (len == 0) {
			window.confirm("至少选择一项");
			return false;
		} else {
			var r = window.confirm("确定要删除此 " + len + " 条数据吗？");
			if (r) {
				item.each(function() {
					$(this).parent("td").parent("tr").remove();
				});
			}
		}
	});
	
	var detailNumber_flag = false;
	//遍历汇总件数是否有效
	var checkNumber = function(jqObject){
		detailNumber_flag = false;
		jqObject.find("tbody tr").each(function(){
			if(!($(this).find("td #number").val())){
				detailNumber_flag = true;
				return false;
			}
		});
	};
	
	//保存界面数据
	$("#save").click(function(){
		var mainRows = $("#inportSlipDetail tbody tr").length;
		var supplierId = $("#suppilerSelect").find("option:selected").val();
		if(!supplierId){
			alert('请选择\"供应商\"！');
			return false;
		}
		if(mainRows == 0){
			alert("请在输入有效数据！");
			return false;
		}
		if(mainRows > 0 ){
			checkNumber($("#inportSlipDetail"));
			if(detailNumber_flag){
				alert('请检查\"退厂数量\"是否填写！');
				return false;
			}else{
				$.ajax({
		    		url:window.basePath + "/inportBack/inportBackDetailSave",
		    		type:"POST",
		     		dataType:"json",
		     		data:$("#saveDetail").serialize(),
		     		success : function(data) {//data是服务器返回内容
						var ok = data.success;
						if(ok){
							alert("保存成功！");
		      			 	parent.location = window.basePath + "/inportBack/inportBackSlipManage";
						} else {
							alert(data.fail);
						}
					},
					error : function(error) {
						alert("保存失败！请检查【退厂单明细】中数据有效性！");
					}
		    	});
			}
		}
	});

});