<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	*{
		margin: 0;
		padding: 0;
	}
	#dropBox{
		width: 500px;
		height: 500px;
		border: 1px solid black;
		overflow: auto;
	}
	#dropBox img{
		width: 100px;
	}
	div.imgBox{
		width: 130px;
		height: 120px;
		margin: 5px;
		position: relative;
		float: left;
	}
	.imgBox .btnRemove{
		position: absolute;
		top: 5px;
		right: 5px;
		background: #FFE4E1;
		outline: none;
		border: 1px solid #FFC0CB;
		color: #FFC0CB;
		padding: 1px 3px;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>
	<form id="f1">
		<input type="text" id="writer">
		<input type="submit">
	</form>
	<div id="dropBox">
	
	</div>
	
	<!-- SCRIPT -->
	<script>
		var formData = new FormData(); //ajax로 데이터 전송을 위해 필요
	
		$("#dropBox").on("dragenter dragover", function(e) {
			console.log("drAG 이벤트");
			e.preventDefault();
		})
		
		$("#dropBox").on("drop", function(e) {
			console.log("drOP 이벤트");
			e.preventDefault();
			
			var files = e.originalEvent.dataTransfer.files; //여러 파일 동시 드래그 가능
			var file = files[0];
			console.log(file);
			
			var reader = new FileReader();
			if(file){
				reader.readAsDataURL(file); //file 읽어들임
			}
			
			reader.addEventListener("load", function() { //파일을 모두 읽은 후 호출될 callback 함수
				var $div = $("<div>").addClass("imgBox");
				var $img = $("<img>").attr("src", reader.result);
				$div.append($img);
				$("#dropBox").append($div);
			})
			
			formData.append("files", file); //files 키에 file 정보를 넣음
		})
		
		$("#f1").submit(function() {
			formData.append("writer", $("#writer").val());
			$.ajax({
				url: "drag",
				type: "post", //파일 내용이 많기 때문에 주소줄 길어지므로 post로 보내야 함
				data: formData,
				processData: false, //ajax로 file 전송 시 꼭 필요한 설정 1
				contentType: false, //2
				success: function(res) {
					console.log(res);
					$("#dropBox").empty();
					
					if(res.result == "success"){
						$(res.list).each(function(i, obj) {
							var $div = $("<div>").addClass("imgBox");
							var $img = $("<img>").attr("src", "${pageContext.request.contextPath}/upload/displayFile?filename="+obj);
							var $button = $("<button>").text("X").addClass("btnRemove").attr("data-src", obj);
							$div.append($img);
							$div.append($button);
							$("#dropBox").append($div);	
																	
						})
					}
				}, error: function(res) {
					console.log(res);					
				}
			})
			return false;
		})
		
		$(document).on("click", ".btnRemove", function() {
			var fileValue = $(this).attr("data-src");
	
			$.ajax({
				url: "deleteFile",
				type: "get",
				data: {"filename": fileValue},
				dataType: "text",
				success: function(res) {
					console.log(res);
				}, error: function(err) {
					console.log(err);					
				}
			})
			
			//화면에서 삭제
			$(this).parent().remove();
		})
	</script>
</body>
</html>