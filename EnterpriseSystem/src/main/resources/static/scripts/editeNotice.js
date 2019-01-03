function save(){
	  layedit.sync(index);
	  var content=layedit.getContent(index);
	  var title=$(".title").val().trim();
	  if(content!="" && title!=""){
		  $.ajax({
			  url:"notice/noticeContent.io",
			  type:"post",
			  dataType:"json",
			  data:{"title":title,"content":content},
			  success:function(result){
				  alert(result.msg);
				  window.location.href="editeNotice";
			  },
			  error:function(){
				  alert("保存失败");
			  }
		  });
	  }
  }
