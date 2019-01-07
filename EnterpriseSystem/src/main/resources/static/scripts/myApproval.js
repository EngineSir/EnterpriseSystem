$(document)
		.ready(
				function() {
					$(".com").load("com.html");
					$(".adopt").click(
							function() {
								// $("#appStatus").text("通过");
								$(this).parent().parent().find("#appStatus")
										.text("通过");
							});
					$(".refuse").click(
							function() {
								// $("#appStatus").text("拒绝");
								$(this).parent().parent().find("#appStatus")
										.text("拒绝");
							});
					$(".del").click(function() {
						$(this).parent().parent().remove();
					});
					var count = 0;
					$.ajax({
						url : "approval/queryCount.io",
						type : "get",
						dataType : "json",
						data : {
							"approverStatue" : 2
						},
						async : false,
						success : function(result) {
							count = result.count;
						},
						error : function() {
							alert("查询失败");
						}
					});

					layui
							.use(
									[ 'form', 'laydate', 'laypage' ],
									function() {
										var form = layui.form, layer = layui.layer, laydate = layui.laydate, laypage = layui.laypage;
										laydate.render({
											elem : '#start',
											calendar : true
										});
										laydate.render({
											elem : '#end',
											calendar : true
										});
										laydate.render({
											elem : '#start1',
											calendar : true
										});
										laydate.render({
											elem : '#end1',
											calendar : true
										});
										laypage
												.render({
													elem : 'paging',
													count : count,// 数据总数,
													groups : 4,
													jump : function(obj) {
														var str = "第"
																+ ((obj.curr - 1) * 10 + 1)
																+ "条到第"
																+ (obj.curr * 10 > obj.count ? obj.count
																		: obj.curr * 10)
																+ "条，共"
																+ (obj.count)
																+ "条";
														$("#countRed")
																.text(str);
													}
												});
										laypage
												.render({
													elem : 'paging1',
													count : 708,// 数据总数,
													groups : 4,
													jump : function(obj) {
														var str = "第"
																+ ((obj.curr - 1) * 10 + 1)
																+ "条到第"
																+ (obj.curr * 10 > obj.count ? obj.count
																		: obj.curr * 10)
																+ "条，共"
																+ (obj.count)
																+ "条";
														$("#countRed1").text(
																str);
														paging(obj.curr,2);
													}
												});
									});
				})
function paging(page,approverStatue){
	$.ajax({
		url:"approval/getPendingApproval.io",
		type:"get",
		dataType:"json",
		data:{"page":page,"approverStatue":approverStatue},
		success:function(result){
			if(result.state==1){
				var data=result.data;
				for(var i=0;i<data.length;i++){
					createTr(data[i]);
				}
			}
		},
		error:function(){
			alert("请求数据失败");
		}
	});
}

function createTr(data) {
	console.log(data);
	var state="";
	if(data.approverStatue==0){
		state="拒绝";
	}
	if(data.approverStatue==1){
		state="同意";
	}
	if(data.approverStatue==2){
		state="待审批";
	}
	var tr = '<tr>';
	tr += '<th style="width: 100px;">'+data.applicant+'</th>';
	tr += '<th style="width: 100px;">'+data.leaveType+'</th>';
	tr += '<th style="width: 100px;" id="appStatus">'+state+'</th>';
	tr += '<th style="width: 150px;">'+data.startTime+' 至' +data.endTime+'</th>';
	tr += '<th style="width: 100px;">'+data.leaveNum+'</th>';
	tr += '<th style="width: 289px;"><textarea class="layui-textarea">'+data.leaveRegard+'</textarea>';
	tr += '</th>';
	tr += '<th style="width: 250px;">';
	tr += '<button class="layui-btn layui-btn-primary layui-btn-s adopt">';
	tr += '	<i class="layui-icon" style="font-size: 15px">&#xe672;</i>';
	tr += '</button>通过';
	tr += '<button class="layui-btn layui-btn-primary layui-btn-s refuse">';
	tr += '	<i class="layui-icon" style="font-size: 15px">&#x1006;</i>';
	tr += '</button>拒绝';
	tr += '</th>';
	tr += '</tr>';
	var $tr=$(tr);
	$tr.data("id",data.id);
	$("#pendingApproval").append($tr);
}