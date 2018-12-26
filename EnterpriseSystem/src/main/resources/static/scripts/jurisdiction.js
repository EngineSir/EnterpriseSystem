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
	str += "<th style='width: 500px; height: 30px'><input type='checkbox' name='/empManageAuthority'/>员工管理"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type='checkbox' name='/deptauthority'/>部门管理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+ "<input type='checkbox' name='/authorityManage'/>权限管理 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type='checkbox' name='/approvalauthority'/>请假审批 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
			+ "<input type='checkbox' name='/dataImportauthority'/>数据录入</th>";
	str+="<th><button class='layui-btn layui-btn-primary layui-btn-sm'>分配权限</button></th>";
	str += "</tr>";
	var $str = $(str);
	$str.data("empId", empId);
	$('.table_info').append($str);
}
// 删除表格行
function delTr() {
	// 获取元表格数据，逐行删除
	var data = $(".table_info").find("tr");
	for (var i = 1; i < data.length; i++) {
		data[i].remove();
	}
}