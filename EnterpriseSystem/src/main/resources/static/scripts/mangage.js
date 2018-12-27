//删除信息
function deleteInfo(){
	$(".opacity_bg").show();
	$(".sure_del").show();
	//将tr一行绑定在删除按钮上
	$(".del_sure").data("del",$(this).parent().parent());
	var tr=$(this).parent().parent().find("td");
	//在确认删除按钮上绑定empId
	var empId=$(this).parent().parent().data("empId");
	$(".del_sure").data("empId",empId);
}
//点击搜索
function doSearch(value,name){
	page=1;
	delTr();
	searAjax(value,page);
	Gvalue=value;
}
//搜索的ajax
function searAjax(value,page){
	$.ajax({
		url:"mangage/allSearch.io",
		dataType:"json",
		type:"post",
		data:{"value":value,"page":page},
		success:function(result){
			var data=result.data;
			for(var i=0;i<data.length;i++){
					createTr(data[i].empId,data[i].empName,data[i].empSex,data[i].empDept,data[i].empNum,data[i].empPhone,data[i].empPhone,data[i].empMail)	
				}
			if(data.length<6){
				$("#down").attr('disabled',true);
			}else{
				$("#down").attr('disabled',false);
			}
		},
		error:function(){
			alert("查询错误");
		}
	});
}
//下一页
function downPage(){
	delTr();
	$("#up").attr('disabled',false);
	page+=1;
		searAjax(Gvalue,page);
}
//删除表格行
function delTr(){
	//获取元表格数据，逐行删除
	var data=$(".table_info").find("tr");
	for(var i=1;i<data.length;i++){
		data[i].remove();
	}
}
//上一页
function upPage(){
	$("#down").attr('disabled',false);
	delTr();
	page-=1;
		searAjax(Gvalue,page);
	if(page==1){
		$("#up").attr('disabled',true);
	}
}
//修改信息
function updateInfo(){
	
	$(".opacity_bg").show();
	$(".up_show").show();
	var tableInfo=$(this).parent().parent().find("td");
	//把修改的信息绑定到修改确认按钮上
	$('#up_sure').data("tr",tableInfo);
	//easyui框架
	$('#up_name').textbox("setValue",$(tableInfo[0]).text());
	//获取到radio的value
	var sex=$(tableInfo[1]).text();
	//通过value选定该值
	if(sex=='男'){
		$('input:radio[value='+sex+']').prop("checked",true);	
	}else{
		$('input:radio[value='+sex+']').prop("checked",true);
	}
	//更新渲染
	form.render('radio');	
	//监听单选事件
	  $("[name=Sex]").change(function(){
		  //单选
		  $('[name=Sex]:checked');
	  }); 
	//获取当前个人部门信息用于反选
	var perDept=$(tableInfo[2]).text();
	var deptSelect=$("#seledept").html();
	var res=$(deptSelect);
	for(var j=0;j<res.length;j++){
		if(perDept==res[j].text){
			 $("#seledept option[value='"+res[j].value+"']").prop("selected", true); 
		}
	}
	form.render("select");	

	$('#up_num').textbox("setValue",$(tableInfo[3]).text());
	$('#up_tele').textbox("setValue",$(tableInfo[4]).text());
	$('#up_email').textbox("setValue",$(tableInfo[5]).text());
}
//点击添加
function clickAdd(){
	$(".opacity_bg").show();
	$('.show').show();
	$('input:radio[value="男"]').prop('checked', false);
	$('input:radio[value="女"]').prop('checked', false);
	form.render('radio');	
	$('#name').textbox("setValue","");
	//$('#dept').textbox("setValue","");
	$('#num').textbox("setValue","");
	$('#tele').textbox("setValue","");
	$('#email').textbox("setValue","");
	
}
//点击取消
function clickClose(){
	$('.show').hide();
	$('.up_show').hide();
	$(".opacity_bg").hide();
	$(".sure_del").hide();	
}
//确认添加员工信息
function clickSure(){
	//获取参数
	var empName=$('#name').val();
	//var empDept=$('#dept').val();
	var empDept=$("select#chooseDept option:selected").text().trim();
	var empNum=$('#num').val();
	var empPhone=$('#tele').val();
	var empSex=$('input:radio:checked').val();
	var empMail=$('#email').val();
	var empId="";
	if(empName!="" && empDept!="" && empNum!="" && empPhone!="" && empSex!="" && empMail!=""){
	$.ajax({
		url:"mangage/addEmp.io",
		type:"post",
		async:true,
		dataType:"json",
		data:{"empName":empName,"empDept":empDept,
			"empNum":empNum,"empPhone":empPhone,
			"empSex":empSex,"empMail":empMail},
		success:function(result){
			if(result.state==1){
				empId=result.data;
				delTr();
				createTr(empId,empName,empSex,empDept,empNum,empPhone,empPhone,empMail);
			}
		},
		error:function(){
			alert("添加员工失败");
		}
	});
	
	}

	$('.show').hide();	
	$(".opacity_bg").hide();
}
//动态创建tr
function createTr(empId,empName,empSex,empDept,empNum,empPhone,empPhone,empMail){
	var state=0;
	$.ajax({
		url:"mangage/authorityUrl.io",
		type:"post",
		async : false,
		dataType:"json",
		data:{"url":"empManageAuthority"},
		success:function(result){
			state=result.state;
		},
		error:function(){
			alert("权限url查询失败");
		}
	});
	var str="<tr>";
	str+="<td>"+empName+"</td>";
	str+="<td>"+empSex+"</td>";
	str+="<td>"+empDept+"</td>";
	str+="<td>"+empNum+"</td>";
	str+="<td>"+empPhone+"</td>";
	str+="<td>"+empMail+"</td>";
	if(state==1){
		str+="<td><button type='button' shiro:hasPermission='/empManageAuthority' class='layui-btn layui-btn-normal delete'>删除</button></td>";
		str+="<td><button type='button' shiro:hasPermission='/empManageAuthority' class='layui-btn layui-btn-normal update'>修改</button></td>";
	}
	str+="</tr>";
	var $str=$(str);
	$str.data("empId",empId);
	$('.table_info').append($str);
}
//点击修改确认
function upSure(){
	//获取表格tr
	var tableInfo=$(this).data("tr");
	//获取修改值
	var empName=$('#up_name').val();
	var empSex=$('[name=Sex]:checked').prop('value');
	var empDept=$("select#seledept option:selected").text().trim();
	var empNum=$('#up_num').val();
	var empPhone=$('#up_tele').val();
	var empMail=$('#up_email').val();
	var empId=$(tableInfo).parent().data("empId");
	
	$(tableInfo[0]).text(empName);
	$(tableInfo[1]).text(empSex);
	$(tableInfo[2]).text(empDept);
	$(tableInfo[3]).text(empNum);
	$(tableInfo[4]).text(empPhone);
	$(tableInfo[5]).text(empMail);
	$.ajax({
		url:"mangage/upEmpInfo.io",
		dataType:"json",
		type:"post",
		data:{"empId":empId,"empName":empName,"empDept":empDept,
			"empNum":empNum,"empPhone":empPhone,
			"empSex":empSex,"empMail":empMail},
		success:function(result){
			},
		error:function(){
			alert("更新信息失败");	
			}
	});
	$('.up_show').hide();
	$(".opacity_bg").hide();
}
//确认删除
function delSure(){
	$(this).data("del").remove();
	var empId=$(this).data("empId");
	$.ajax({
		url:"mangage/delEmpInfo.io",
		dataType:"json",
		Type:"post",
		data:{"empId":empId},
		success:function(result){
			if(result.state==1){
				delTr();
				searAjax(Gvalue,page);
			}
		},
		error:function(){
			alert("删除失败");
		}
	});
	
	$(".sure_del").hide();
	$(".opacity_bg").hide();
}

