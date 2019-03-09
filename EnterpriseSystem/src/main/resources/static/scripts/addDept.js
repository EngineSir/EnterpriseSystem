var layer;
$(document).ready(function() {
	$(".com").load("com");
	layui.use([ 'form', 'laydate', 'table' ],function(){
		layer = layui.layer;
	}),
	$(".sub_btn").click(addDept);
	
})

function addDept(){
	var flog=true;
	var deptName=$(".deptName").val().trim();
	var remark=$(".remark").val().trim();
	if(deptName==""||deptName==null){
		layer.msg("部门名称不能为空");
		flog=false;
		return false;
	}
	if(deptName.length<2){
		layer.msg("部门名称不能小于2");
		flog=false;
		return false;
	}
	if(remark==""||remark==null){
		layer.msg("备注内容不能为空");
		flog=false;
		return false;
	}
	if(flog){
		$.ajax({
			url:"dept/addDept.io",
			type:"post",
			dataType:"json",
			data:{"deptName":deptName,"remark":remark},
			success:function(result){
				
				if(result.state==1){
					layer.msg("添加部门成功");
					$(".deptName").val("");
					$(".remark").val("");
				}else{
					layer.msg("重复添加部门");
				}
			},
			error:function(){
				alert("添加部门失败");
			}
		});
	}
	return false;
}