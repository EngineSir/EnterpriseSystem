function queryEmp() {
	var count = 0;
	$.ajax({
		url : "authority/queryCount.io",
		type : "post",
		async : false,
		success : function(result) {
			count = result.data;
		},
		error : function() {
			alert("查询总数出错");
		}
	});

	layui.use([ 'laypage', 'layer' ], function() {
		var laypage = layui.laypage, layer = layui.layer;
		// 总页数大于页码总数
		laypage.render({
			elem : 'demo1',
			count : count,// 数据总数,
			groups : 4,
			jump : function(obj) {
				var str = "第" + ((obj.curr - 1) * 8 + 1) + "条到第"
						+ (obj.curr * 8 > obj.count ? obj.count : obj.curr * 8)
						+ "条，共" + (obj.count) + "条";
				$("#countRed").text(str);
				paging(obj.curr);
			}
		});
	});
}
function paging(page) {
	$.ajax({
		url : "authority/empInfo.io",
		type : "post",
		dataType : "json",
		data : {
			"page" : page
		},
		success : function(result) {
			if (result.state == 1) {
				var data = result.data;

				delTr();
				for (var i = 0; i < data.length; i++) {
					createTr(data[i].empName, data[i].empSex, data[i].empDept,
							data[i].empId);
				}
			}
		},
		error : function() {
			alert("查询出错");
		}
	});
}
function createTr(empName, empSex, empDept, empId) {
	
	var str = "<tr>";
	str += "<th>" + empName + "</th>";
	str += "<th>" + empSex + "</th>";
	str += "<th>" + empDept + "</th>";
	str += "";
	str += "<th style='width: 500px; height: 30px'><input type='checkbox' name='1' id='"+empId+"1'/>员工管理"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type='checkbox' name='2' id='"+empId+"2'/>部门管理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+ "<input type='checkbox' name='3' id='"+empId+"3'/>权限管理 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type='checkbox' name='4' id='"+empId+"4'/>请假审批 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+ "<input type='checkbox' name='5' id='"+empId+"5'/>数据录入</th>";
	str += "<th><button class='layui-btn layui-btn-primary layui-btn-sm'>分配权限</button></th>";
	str += "</tr>";
	var $str = $(str);
	$str.data("empId", empId);
	$('.table_info').append($str);
	
	$.ajax({
		url:"authority/queryAuthorityId.io",
		type:"post",
		dataType:"json",
		async : false,
		data:{"empId":empId},
		success:function(result){
			var data=result.data;
			if(data!=null || data.length!=0){
				for(var i=0;i<data.length;i++){
					var ss="#"+empId+""+data[i];
					$(ss).attr('checked',true);				
				}
			}
		},
		error:function(){
			alert("查询权限失败");
		}
	});
	
//	
}
// 删除表格行
function delTr() {
	// 获取元表格数据，逐行删除
	var data = $(".table_info").find("tr");
	for (var i = 1; i < data.length; i++) {
		data[i].remove();
	}
}
function getAuthority() {
	var $tr = $(this).parent().parent();
	var empId = $tr.data("empId");
	var $th = $tr.find("th").eq(3);
	var $input = $th.find("input");
	var resourceId=new Array();
	resourceId.push(-1);
	var index=0;
	for (var k = 0; k < $input.length; k++) {

		if ($input[k].checked == true) {
			resourceId.push($input[k].name);
			index++;
		}
	}
	
	if(empId!=null&&empId.length!=0){
		$.ajax({
			url:"authority/addAuthority.io",
			type:"post",
			dataType:"json",
			data:{"empId":empId,"resourceId":resourceId},
			success:function(result){
				alert(result.msg);
				if(result.state==-1){
					window.location.href="index";
				}
			},
			error:function(){
				alert("添加权限失败");
			}
		});
	}
}