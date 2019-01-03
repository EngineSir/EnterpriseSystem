/*
 * 添加部门
 */
function addDept(){
	var deptName=$("#addDept").val();
	if(deptName!=""){
		$.ajax({
			url:"dept/addDept.io",
			type:"post",
			dataType:"json",
			data:{"deptName":deptName},
			success:function(result){
				if(result.state==1){
					delTr();
					createTr(result.data,deptName);
				}
			},
			error:function(){
				alert("添加部门失败");
			}
		});
	}
}
/*
 * 删除部门
 * 
 */
function delDept(){
	var $tr=$(this).parent().parent();
	var id=$tr.data("id");
	if(id!=""){
		$.ajax({
			url:"dept/delDept.io",
			type:"post",
			dataType:"json",
			data:{"id":id},
			success:function(result){
				if(result.state==1){
					$tr.remove();	
				}
			},
			error:function(){
				alert("删除失败");
			}
		});	
	}
	
}
/*
 * 查询部门
 */
function queryDept(){
	$.ajax({
		url:"dept/queryDept.io",
		success:function(result){
			if(result.state==1){
				delTr();
				var data=result.data;
				for(var i=0;i<data.length;i++){
					createTr(data[i].id,data[i].deptName);
				}
			}
		},
		error:function(){
			alert("查询部门失败");
		}
	});
}





/*
 * 修改部门
 */
function updateDept(){
	var $tr=$(this).parent().parent();
	var deptName=$tr.find("td").first().text();
	$("#upDept").val(deptName);
	$(".show").show();
	$(".opacity_bg").show();
	$("#sureUP").data("tr",$tr);
}
/*
 * 修改确定
 */
function sureUp(){
	var deptName=$("#upDept").val();
	var $tr=$(this).data("tr");
	var id=$tr.data("id");
	if(id!="" && deptName!=""){
		$.ajax({
			url:"dept/upDept.io",
			type:"post",
			dataType:"json",
			data:{"id":id,"deptName":deptName},
			success:function(result){
				if(result.state==1){
					$tr.find("td").first().text(deptName);
				}
			},
			error:function(){
				alert("修改部门名称失败");
			}
		});
	}
	$(".show").hide();
	$(".opacity_bg").hide();
}
/*
 * 动态创建tr
 * */
function createTr(id,deptName){
	var state=0;
	$.ajax({
		url:"dept/authorityUrl.io",
		type:"post",
		async : false,
		dataType:"json",
		data:{"url":"deptauthority"},
		success:function(result){
			state=result.state;
		},
		error:function(){
			alert("权限url查询失败");
		}
	});
	var str="<tr>";
	str+="<th style='width:200px;'>"+deptName+"</th>";
	if(state==1){
		str+="<th style='width:200px;'><button type='button' class='layui-btn layui-btn-normal delete'>删除</button></th>";
		str+="<th style='width:200px;'><button type='button' class='layui-btn layui-btn-normal update'>修改</button></th>";
	}
	str+="</tr>";
	var $str=$(str);
	$str.data("id",id);
	$('#tab').append($str);
}
//删除表格行
function delTr(){
	//获取元表格数据，逐行删除
	var data=$("#tab").find("tr");
	for(var i=1;i<data.length;i++){
		data[i].remove();
	}
}