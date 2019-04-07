var Gvalue = "";
var form;
var layer;
var globalStatue=1;
var phoneReg=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
var emailReg=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
var numReg=/^[A-Za-z0-9]+$/;
$(document).ready(function() {
	layui.use(['form','layer'], function() {
		form = layui.form;
		layer = layui.layer;
	});
	// 加载公共页面
	$(".com").load("com.html");
	// 点击添加
	$('#add').click(clickAdd);
	// 点击取消
	$('.close').click(clickClose);
	// 点击添加确定
	$('#sure').click(clickSure);
	// 点击删除
	$(".table_info").on("click", ".delete", deleteInfo);
	// 点击修改
	$(".table_info").on("click", ".update", updateInfo);
	// 点击修改确认
	$("#up_sure").click(upSure);
	// 确认删除
	$(".del_sure").click(delSure);
	//密码重置
	$(".table_info").on("click",".reset",reset);
	queryDept();
	//初始化员工数据
	getInitInfo();
	
	$('.resetBtn').click(reload);
})

function reload(){
	getInitInfo();
}
// 删除信息
function deleteInfo() {
	$(".opacity_bg").show();
	$(".sure_del").show();
	// 将tr一行绑定在删除按钮上
	$(".del_sure").data("del", $(this).parent().parent());
	var tr = $(this).parent().parent().find("td");
	// 在确认删除按钮上绑定empId
	var empId = $(this).parent().parent().data("empId");
	$(".del_sure").data("empId", empId);
}

function getInitInfo(){
	globalStatue=1;
	var count=0;
	$.ajax({
		url:"mangage/getInitInfoCount.io",
		type:"get",
		async:false,
		success:function(result){
			count=result.count;
		},
		error:function(){
			alert("查询出错");
		}
	});
	
	layui.use('laypage', function() {
		var laypage = layui.laypage;
		if(count>0){
			laypage.render({
				elem : 'page',
				count : count,
				theme : '#FFB800',
				groups : 4,
				jump : function(obj, first) {
					var str = "第"
							+ ((obj.curr - 1) * 10+ 1)
							+ "条到第"
							+ (obj.curr * 10 > obj.count ? obj.count
									: obj.curr * 10) + "条，共" + (obj.count) + "条";
					$("#countRed").text(str);
					delTr();
					getAllEmp(obj.curr);

				}
			});
		}else{
			layer.msg("没有改查询记录");
		}
	});
}

function getAllEmp(page){
	$.ajax({
		url : "mangage/getInitInfo.io",
		dataType : "json",
		type : "get",
		data : {"page" : page},
		success : function(result) {
			var data = result.data;
			for (var i = 0; i < data.length; i++) {
				createTr(i,data[i].empId, data[i].empName, data[i].empSex,
						data[i].empDept, data[i].empNum, data[i].empPhone,
						data[i].empPhone, data[i].empMail)
			}
		},
		error : function() {
			alert("查询错误");
		}
	});
}







// 点击搜索
function doSearch(value, name) {
	
	Gvalue=value;
	globalStatue=2;
	if(value==null || value==""){
		layer.msg("搜索值不能为空");
		return false;
	}
	if(value.length<2){
		layer.msg("搜索值长度不能小于2");
		return false;
	}
	var count=0;
	$.ajax({
		url:"mangage/queryCount.io",
		type:"get",
		dataType:"json",
		data:{"searchValue":value},
		async:false,
		success:function(result){
			count=result.count;
		},
		error:function(){
			alert("查询出错");
		}
	});
	
	layui.use('laypage', function() {
		var laypage = layui.laypage;
		if(count>0){
			$("#page").show();
			$(".showCount").show();
			laypage.render({
				elem : 'page',
				count : count,
				theme : '#FFB800',
				groups : 4,
				jump : function(obj, first) {
					var str = "第"
							+ ((obj.curr - 1) * 10+ 1)
							+ "条到第"
							+ (obj.curr * 10 > obj.count ? obj.count
									: obj.curr * 10) + "条，共" + (obj.count) + "条";
					$("#countRed").text(str);
					delTr();
					searAjax(value, obj.curr);

				}
			});
		}else{
			layer.msg("没有改查询记录");
		}
	});

}
// 搜索的ajax
function searAjax(searchValue, page) {
	$.ajax({
		url : "mangage/allSearch.io",
		dataType : "json",
		type : "get",
		data : {
			"searchValue" : searchValue,
			"page" : page
		},
		success : function(result) {
			var data = result.data;
			for (var i = 0; i < data.length; i++) {
				createTr(i,data[i].empId, data[i].empName, data[i].empSex,
						data[i].empDept, data[i].empNum, data[i].empPhone,
						data[i].empPhone, data[i].empMail)
			}
		},
		error : function() {
			alert("查询错误");
		}
	});
}

// 删除表格行
function delTr() {
	// 获取元表格数据，逐行删除
	var data = $(".table_info").find("tr");
	for (var i = 1; i < data.length; i++) {
		data[i].remove();
	}
}

// 修改信息
function updateInfo() {

	$(".opacity_bg").show();
	$(".up_show").show();
	var tableInfo = $(this).parent().parent().find("td");
	// 把修改的信息绑定到修改确认按钮上
	$('#up_sure').data("tr", tableInfo);
	// easyui框架
	$('#up_name').textbox("setValue", $(tableInfo[1]).text());
	// 获取到radio的value
	var sex = $(tableInfo[2]).text();
	// 通过value选定该值
	if (sex == '男') {
		$('input:radio[value=' + sex + ']').prop("checked", true);
	} else {
		$('input:radio[value=' + sex + ']').prop("checked", true);
	}
	// 更新渲染
	form.render('radio');
	// 监听单选事件
	$("[name=Sex]").change(function() {
		// 单选
		$('[name=Sex]:checked');
	});
	// 获取当前个人部门信息用于反选
	var perDept = $(tableInfo[3]).text();
	var deptSelect = $("#seledept").html();
	var res = $(deptSelect);
	for (var j = 0; j < res.length; j++) {
		if (perDept == res[j].text) {
			$("#seledept option[value='" + res[j].value + "']").prop(
					"selected", true);
		}
	}
	form.render("select");

	$('#up_num').textbox("setValue", $(tableInfo[4]).text());
	$('#up_tele').textbox("setValue", $(tableInfo[5]).text());
	$('#up_email').textbox("setValue", $(tableInfo[6]).text());
}
// 点击添加
function clickAdd() {
	$(".opacity_bg").show();
	$('.show').show();
	$('input:radio[value="男"]').prop('checked', false);
	$('input:radio[value="女"]').prop('checked', false);
	form.render('radio');
	$('#name').textbox("setValue", "");
	// $('#dept').textbox("setValue","");
	$('#num').textbox("setValue", "");
	$('#tele').textbox("setValue", "");
	$('#email').textbox("setValue", "");

}
// 点击取消
function clickClose() {
	$('.show').hide();
	$('.up_show').hide();
	$(".opacity_bg").hide();
	$(".sure_del").hide();
}
// 确认添加员工信息
function clickSure() {

	// 获取参数
	var empName = $('#name').val();
	if(empName=="" || empName==null){
		layer.msg("名字不能为空");
		return true;
	}
	// var empDept=$('#dept').val();
	var empDept = $("select#chooseDept option:selected").text().trim();
	var empNum = $('#num').val().trim();
	
	var empPhone = $('#tele').val().trim();
	
	var empSex = $('input:radio:checked').val();
	var empMail = $('#email').val().trim();
	if(!regformat(empSex,empNum,empPhone,empMail)){
		return false;
	}
	var empId = "";
	if (empName != "" && empDept != "" && empNum != "" && empPhone != ""
			&& empSex != null && empMail != "") {
		$.ajax({
			url : "mangage/addEmp.io",
			type : "post",
			async : true,
			dataType : "json",
			data : {
				"empName" : empName,
				"empDept" : empDept,
				"empNum" : empNum,
				"empPhone" : empPhone,
				"empSex" : empSex,
				"empMail" : empMail
			},
			success : function(result) {
				if (result.state == 1) {
					empId = result.data;
					delTr();
					createTr(0,empId, empName, empSex, empDept, empNum, empPhone,
							empPhone, empMail);
				}
			},
			error : function() {
				alert("添加员工失败");
			}
		});

	}

	$('.show').hide();
	$(".opacity_bg").hide();
	$("#page").hide();
	$(".showCount").hide();
}

//检查格式
function regformat(empSex,empNum,empPhone,empMail){
	if(empSex==null){
		layer.msg("请选择性别");
		return false;
	}
	if(!numReg.test(empNum)){
		layer.msg("编号只能输入英文或数字");
		return false;
	}
	if(empNum.length<5){
		layer.msg("编号长度至少五位");
		return false;
	}
	if(!phoneReg.test(empPhone)){
		layer.msg("请输入有效手机号码");
		return false;
	}
	if(!emailReg.test(empMail)){
		layer.msg("请输入有效邮箱");
		return false;
	}
	return true;
}


// 动态创建tr
function createTr(num,empId, empName, empSex, empDept, empNum, empPhone, empPhone,
		empMail) {
	var state = 0;
	$.ajax({
		url : "mangage/authorityUrl.io",
		type : "get",
		async : false,
		dataType : "json",
		data : {
			"url" : "empManageAuthority"
		},
		success : function(result) {
			state = result.state;
		},
		error : function() {
			alert("权限url查询失败");
		}
	});
	var str = "<tr>";
	str += "<td>" + (num+1) + "</td>";
	str += "<td>" + empName + "</td>";
	str += "<td>" + empSex + "</td>";
	str += "<td>" + empDept + "</td>";
	str += "<td>" + empNum + "</td>";
	str += "<td>" + empPhone + "</td>";
	str += "<td>" + empMail + "</td>";
	if (state == 1) {
		/*
		 * str += "<td><button type='button' class='layui-btn layui-btn-normal delete'>删除</button></td>";
		str += "<td><button type='button' class='layui-btn layui-btn-normal update'>修改</button></td>";
		 * */
		str += "<td><span class='update'>修改</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class='delete'>删除</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class='reset'>重置</span></td>";
	}
	str += "</tr>";
	var $str = $(str);
	$str.data("empId", empId);
	$('.table_info').append($str);
}
// 点击修改确认
function upSure() {
	// 获取表格tr
	var tableInfo = $(this).data("tr");
	// 获取修改值
	var empName = $('#up_name').val();
	var empSex = $('[name=Sex]:checked').prop('value');
	var empDept = $("select#seledept option:selected").text().trim();
	var empNum = $('#up_num').val();
	var empPhone = $('#up_tele').val();
	var empMail = $('#up_email').val();
	var empId = $(tableInfo).parent().data("empId");

	if(!regformat(empSex,empNum,empPhone,empMail)){
		return false;
	}
	$(tableInfo[1]).text(empName);
	$(tableInfo[2]).text(empSex);
	$(tableInfo[3]).text(empDept);
	$(tableInfo[4]).text(empNum);
	$(tableInfo[5]).text(empPhone);
	$(tableInfo[6]).text(empMail);
	$.ajax({
		url : "mangage/upEmpInfo.io",
		dataType : "json",
		type : "put",
		data : {
			"empId" : empId,
			"empName" : empName,
			"empNum" : empNum,
			"empDept" : empDept,
			"empPhone" : empPhone,
			"empSex" : empSex,
			"empMail" : empMail
		},
		success : function(result) {
		},
		error : function() {
			alert("更新信息失败");
		}
	});
	$('.up_show').hide();
	$(".opacity_bg").hide();
}
// 确认删除
function delSure() {
	$(this).data("del").remove();
	var empId = $(this).data("empId");
	$.ajax({
		url : "mangage/delEmpInfo.io",
		dataType : "json",
		type : "delete",
		data : {
			"empId" : empId
		},
		success : function(result) {
			if (result.state == 1) {
				delTr();
				if(globalStatue==1){
					getInitInfo();
				}
				if(globalStatue==2){
					doSearch(Gvalue, "");
				}
				
			}
		},
		error : function() {
			alert("删除失败");
		}
	});

	$(".sure_del").hide();
	$(".opacity_bg").hide();
}


//密码重置
function reset(){
	var empId= $(this).parent().parent().data("empId");
	if(empId==null || empId==""){
		layer.msg("服务器出现错误");
		return false;
	}
	$.ajax({
		url:"mangage/passwordReset.io",
		type:"post",
		dataType:"json",
		data:{"empId" : empId},
		success:function(result){
			layer.msg(result.msg);
		},
		error:function(){
			alert("密码重置失败");
		}
	});
}