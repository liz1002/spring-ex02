<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="innerMulti" method="post" enctype="multipart/form-data">
		내용 : <input type="text" name="test"><br>
		<input type="file" name="files" multiple="multiple">
		<input type="submit" value="업로드">
	</form>
</body>
</html>