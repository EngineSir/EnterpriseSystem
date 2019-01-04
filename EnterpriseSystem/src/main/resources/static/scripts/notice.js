$(document).ready(function() {
	$(".com").load("com");
	queryNoticeCount();
})
function queryNoticeCount() {
	var count = 0;
	$.ajax({
		url : "notice/queryNoticeCount.io",
		type : "get",
		async : false,
		success : function(result) {
			if (result.state == 1) {
				console.log(result);
				count = result.data;
			}
		},
		error : function() {
			alert("查询通知总数出错");
		}
	});

	layui.use([ 'laypage', 'layer' ], function() {
		var laypage = layui.laypage, layer = layui.layer;
		laypage.render({
			elem : 'demo1',
			count : count,
			groups : 4,
			jump : function(obj) {
				queryNoticeTitle(obj.curr);
			}
		});
	});

}

function queryNoticeTitle(page) {
	$.ajax({
		url : "notice/queryNoticeTitle.io",
		type : "get",
		dataType : "json",
		data : {
			"page" : page
		},
		success : function(result) {
			if (result.state == 1) {
				var data = result.data;
				delTr();
				for (var i = 0; i < data.length; i++) {
					createTr(data[i].title, data[i].time, data[i].id);
				}
			}
		},
		error : function() {
			alert("查询失败");
		}
	});
}
function createTr(title, time, id) {
	var times = new Date();
	times.setTime(time);
	var tr = "<tr>";
	tr += "<td><a href='notice.io?id=" + id + "'>" + title + "</a></td>";
	tr += "<td>" + times.toLocaleString() + "</td>"
	tr += "</tr>";
	var $tr = $(tr);
	$(".notice_list").append($tr);
}
// 删除表格行
function delTr() {
	// 获取元表格数据，逐行删除
	var data = $(".notice_list").find("tr");
	for (var i = 0; i < data.length; i++) {
		data[i].remove();
	}
}