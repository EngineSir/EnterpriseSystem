//确定添加请假信息
function sureLeave(){
	var name=$("#name").val().trim();
	var dept=$("select.chooseDept option:selected").text().trim();
	var start=$("#start").val().trim();
	var end=$("#end").val().trim();
	if(name!="" && dept!="" && start!="" && end!=""){
		$.ajax({
			url:"leave/addLeave.io",
			type:"post",
			dataType:"json",
			data:{"empName":name,"dept":dept,"startDate":start,"endDate":end},
			success:function(result){
				if(result.state==1){
					$(".opacity_bg").hide();
					$(".show").hide();
					$(".record_show").hide();
				}else{
					alert(result.msg);
				}
			},
			error:function(){
				alert("添加请假信息出错");
			}
		});
	}
}//查询请假信息
function searchLeave(){

	jsonData.value = $('input:radio:checked').val();
	jsonData.name=$("#text").val();
	//替换所有-
	jsonData.startDate=$("#date").val().replace(/-/g,"/");
	jsonData.endDate=$("#date1").val().replace(/-/g,"/");

	if(jsonData.value!="" && jsonData.startDate<=jsonData.endDate && jsonData.endDate!=""){
		console.log(jsonData.value);
		searchAjax(jsonData);
	}
}
//ajax
function searchAjax(jsonData){
	$.ajax({
		url:"leave/searchLeave.io",
		type:"post",
		dataType:"json",
		data:{"name":jsonData.name,"value":jsonData.value,"startDate":jsonData.startDate,"endDate":jsonData.endDate,"page":jsonData.page},
		success:function(result){
			delDeptTr();
			var data=result.data;
			for(var i=0;i<data.length;i++){
				createTr(data[i].empName,data[i].dept,data[i].startDate,data[i].endDate);
			}
			if(data.length<6){
				$("#down").attr('disabled',true);
			}else{
				$("#down").attr('disabled',false);
			}
		},
		error:function(){
			alert("查询出错");
		}
	});
}
//下一页
function downPage(){
	delTr();
	$("#up").attr('disabled',false);
	jsonData.page+=1;
	searchAjax(jsonData);
}
//上一页
function upPage(){
	$("#down").attr('disabled',false);
	delTr();
	jsonData.page-=1;
	searchAjax(jsonData);
	if(jsonData.page==1){
		$("#up").attr('disabled',true);
	}
}

//创建tr
function createTr(name,dept,start,end){
	var tr="<tr>";
	   tr+="<td>"+name+"</td>";
	   tr+="<td>"+dept+"</td>";
	   tr+="<td>"+start+"-----"+end+"</td>";
	   tr+="</tr>";
	  var $tr=$(tr);
	  $(".showtab").append($tr);
}
