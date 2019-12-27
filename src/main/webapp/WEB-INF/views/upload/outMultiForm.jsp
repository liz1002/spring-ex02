<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#box{
		min-width: 100px;
		min-height: 100px;
		border: 1px solid black;
	}
	#box img{
		width: 150px;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>
	<form action="outMulti" method="post" enctype="multipart/form-data">
		내용 : <input type="text" name="test"><br>
		<input type="file" name="files" multiple="multiple">
		<input type="submit" value="업로드">
	</form>
	<div id="box"></div>
	
	<script>
		$("input[name='file']").change(function() {
			$("#box").empty();
			
			//$(this) : jquery obj
			//$(this)[0] : javascript obj
			//File Read는 javascript만 가능
			for(var i=0; i<$(this)[0].files.length; i++){
				var file = $(this)[0].files[i];
				var reader = new FileReader();
				reader.readAsDataURL(file);
				reader.onload = function(e) {
					var $img = $("<img>").attr("src", e.target.result);
					$("#box").append($img);
				}
			}
		})
	</script>
</body>
</html>