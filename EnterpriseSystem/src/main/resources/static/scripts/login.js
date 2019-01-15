$(document).ready(function() {
	var layer="";
	layui.use('layer', function(){ 
		 layer = layui.layer;});
	
	
	$("#login").click(function(){
		var username=$("#count").val().trim();
		var pass=$("#password").val().trim();
		if(username==""||pass==""){
			layer.msg("用户名或密码不能为空");
		}else if(pass.length>=5){
			$.ajax({
				url:"mangage/login.io",
				type:"post",
				dataType:"json",
				data:{"username":username,"pass":pass},
				success:function(result){
					console.log(result);
					if(result.state==1){
						location.href="index";
					}else{
						console.log(layui+" "+layer);
						layer.msg(result.msg);
					}
				},
				error:function(){
					alert("登陆失败");
				}
			});
		}else{
			layer.msg("密码长度不能小于5位");
		}
		return false;
	});
});