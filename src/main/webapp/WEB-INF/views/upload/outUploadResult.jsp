<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>test : ${test}</p>
	<p>file : ${file}</p>
	<img src="${pageContext.request.contextPath}/upload/displayFile?filename=${file}">
</body>
</html>