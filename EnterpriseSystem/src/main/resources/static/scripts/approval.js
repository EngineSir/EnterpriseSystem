$(document).ready(function() {
	$(".com").load("com.html");
	$('#add').click(approver);
	layui.use([ 'form', 'laydate' ], function() {
		var form = layui.form, layer = layui.layer, laydate = layui.laydate;
		laydate.render({
			elem : '#start',
			type : 'datetime'
		});
		laydate.render({
			elem : '#end',
			type : 'datetime'
		});
	});
})

function approver() {
	$(".opacity_bg").show();
	$(".show").show();
}