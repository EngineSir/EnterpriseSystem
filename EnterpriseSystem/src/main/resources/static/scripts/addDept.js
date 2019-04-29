var layer;
$(document).ready(function() {
	$(".com").load("com");
	layui.use([ 'form', 'laydate', 'table' ],function(){
		layer = layui.layer;
	}),
	$(".sub_btn").click(addDept);
	
})

function addDept(){
	var deptName=$(".deptName").val().trim();
	var remark=$(".remark").val().trim();
	var director=$(".director").val().trim();
	var address=$(".address").val().trim();
	var phone=$(".phone").val().trim();
		$.ajax({
			url:"dept/addDept.io",
			type:"post",
			dataType:"json",
			data:{"deptName":deptName,"remark":remark,"director":director,"address":address,"phone":phone},
			success:function(result){
				
				if(result.state==1){
					layer.msg("添加部门成功");
					//$(".deptName").val("");
					//$(".remark").val("");
				}else{
					layer.msg("重复添加部门");
				}
			},
			error:function(){
				alert("添加部门失败");
			}
		});
	return false;
}