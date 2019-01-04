$(document).ready(function() {
	var a=[],b=[];
	a.push(1);
	a.push(2);
	a.push(3);
	console.log(a);
	var json={"label":"迟到","data":a};
	console.log(json);
	b.push(json);
	b.push(json);
	b.push(json);
	console.log(b);
	
	$(".com").load("com");
	var ctx = document.getElementById('MyChart1').getContext('2d');
	var chart = new Chart(ctx, {
		type : 'bar',

		data : {
			labels : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月",
					"九月", "十月", "十一月", "十二月" ],
			datasets : [ {
				label : "迟到次数",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : [ 0, 3, 10, 19, 15, 6, 10, 20, 9, 14, 19, 2 ],
				backgroundColor : "#84c1ff"
			}, {
				label : "早退次数",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : [ 2, 8, 4, 9, 6, 7, 10, 0, 3, 5, 1, 9 ],
				backgroundColor : "#aaffaa"
			}, {
				label : "加班小时数",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : [ 28, 4, 10, 19, 6, 7, 10, 20, 3, 0, 6, 9 ],
				backgroundColor : "#ffb693"
			} ]
		},
		options : {
			responsive : false,
			aspectRatio : 1
		}
	});

	var ctx1 = document.getElementById('MyChart2').getContext('2d');
	var chart = new Chart(ctx1, {
		type : 'line',
		data : {
			labels : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月",
					"九月", "十月", "十一月", "十二月" ],
			datasets : [ {
				label : "上班天数",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				borderColor : "#84c1ff",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : [ 0, 3, 10, 19, 15, 6, 10, 20, 9, 14, 19, 2 ]
			} ]
		},
		options : {
			responsive : false,
			aspectRatio : 1
		}
	});
	
	
	
	$("#link1").click(function(){
		var url_base64 = document.getElementById("MyChart1").toDataURL("image/png");
        link1.href = url_base64;
        var url = link1.href.replace(/^data:image\/[^;]/, 'data:application/octet-stream');
	});
})