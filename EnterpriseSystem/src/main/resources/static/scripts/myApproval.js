$(document).ready(function() {
	$(".com").load("com.html");
	$(".adopt").click(function() {
		//$("#appStatus").text("通过");
		$(this).parent().parent().find("#appStatus").text("通过");
	});
	$(".refuse").click(function() {
		//$("#appStatus").text("拒绝");
		$(this).parent().parent().find("#appStatus").text("拒绝");
	});
	$(".del").click(function() {
		$(this).parent().parent().remove();
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
							count : 708,//数据总数,
							groups : 4,
							jump : function(obj) {
								var str = "第"
										+ ((obj.curr - 1) * 10 + 1)
										+ "条到第"
										+ (obj.curr * 10 > obj.count ? obj.count
												: obj.curr * 10)
										+ "条，共" + (obj.count) + "条";
								$("#countRed").text(str);
							}
						});
				laypage
						.render({
							elem : 'paging1',
							count : 708,//数据总数,
							groups : 4,
							jump : function(obj) {
								var str = "第"
										+ ((obj.curr - 1) * 10 + 1)
										+ "条到第"
										+ (obj.curr * 10 > obj.count ? obj.count
												: obj.curr * 10)
										+ "条，共" + (obj.count) + "条";
								$("#countRed1").text(str);
							}
						});
			});
})