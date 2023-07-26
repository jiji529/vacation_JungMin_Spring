<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test</title>
</head>
<body>
	<h1>${s}</h1>
	<h2>vo.x: {${x}}, vo.t: {${y}}</h2>
	
	<!-- EL에서는 'get'를 쓰지 않음. -->
	<h2>vo.x: {${vo.x}}, vo.t: {${vo.y}}</h2>
	<h2>vo.x: {${myVo.x}}, vo.t: {${myVo.y}}</h2>
</body>
</html>