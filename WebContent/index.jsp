<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script type="text/javascript" src="<%=path%>/js/jquery-1.4.3.js"></script>
<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
<style type="text/css">
body, html {
	width: 100%;
	height: 100%;
	margin: 0;
	font-family: "微软雅黑";
}

#allmap {
	width: 100%;
	height: 90%;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#from").focus();
	});
	var pointArray = new Array();
	function search() {
		var fromText = $("#from").val();
		var toText = $("#to").val();
		var lll = fromText + "," + toText;
		var info = "info=" + lll;
		alert(fromText + "=" + toText);
		$.ajax({
			type : "POST",
			async : true,
			url : "SearchLines",
			data : info,
			dataType : "JSON",
			success : function(datas) {
				var theDatas = JSON.parse(datas);
				drawLines(theDatas.route);

			}
		});
	}

	function drawLines(routes) {
		var points = new Array();
		var x = 0;
		var str = "[";
		$.each(routes, function(i, li) {
			$.each(pointArray, function(i, point) {
				var title = point.getLabel().getTitle();
				if (title == li) {
					if (point.getPosition().lng > 0) {
						points[x] = point;
						x = x + 1;
						str = str + "new BMap.Point(" + point.getPosition().lng
								+ ", " + point.getPosition().lat + "),";
					}
				}
			});

		});
		str = str + "new BMap.Point(" + points[x - 1].getPosition().lng + ", "
				+ points[x - 1].getPosition().lat + ")]"
		var polyline = new BMap.Polyline(eval(str), {
			strokeColor : "blue",
			strokeWeight : 10,
			strokeOpacity : 0.5
		});
		map.addOverlay(polyline); //增加折线
	}
</script>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=MS9nczGbGFj2sGnpFv5MBnPf"></script>
<title>地图</title>
</head>
<body>
	<div id="allmap"></div>
	<input type="text" id="from">到
	<input type="text" id="to">
	<input type="button" onclick="search()" value="查询路线" />
	<script type="text/javascript">
		// 百度地图API功能
		var map = new BMap.Map("allmap");
		var point = new BMap.Point(116.404, 39.915);
		map.centerAndZoom(point, 15);
		map.disableDoubleClickZoom(true);
		map.enableScrollWheelZoom(true);

		var frombool = false;
		var toboole = false;
		// 编写自定义函数,创建标注
		function addMarker(point, label) {
			map.addOverlay(point);
			point.setLabel(label);
		}

		function attribute(e) {
			var p = e.target;
			var text = p.getLabel().getTitle();
			if (frombool == false) {
				$("#from").val(text);
				frombool = true;
				$("#to").focus();
				toboole = false;
			} else {
				$("#to").val(text);
				tobool = true;
				$("#from").focus();
				frombool = false;
			}
		}

		//对json对象objs遍历，然后输出到网页上
		function createList(objs) {
			$.each(objs, function(i, li) {
				var marker = new BMap.Marker(new BMap.Point(li.location.lng,
						li.location.lat)); // 创建点
				//增加点

				marker.addEventListener("click", attribute);
				var label = new BMap.Label(li.name + "" + i, {
					offset : new BMap.Size(20, -10)
				});
				label.setTitle(li.name)
				addMarker(marker, label);
				pointArray[i] = marker;
			});
		}
		$.ajax({
			type : "POST",
			async : true,
			url : "GetAllSubways",
			data : "",
			dataType : "JSON",
			success : function(datas) {
				var theDatas = JSON.parse(datas);
				createList(theDatas.resultsList);

			}
		});

		function deletePoint() {
			var allOverlay = map.getOverlays();
			for (var i = 0; i < allOverlay.length - 1; i++) {
				if (allOverlay[i].getLabel().content == "我是id=1") {
					map.removeOverlay(allOverlay[i]);
					return false;
				}
			}
		}
	</script>
</body>
</html>
