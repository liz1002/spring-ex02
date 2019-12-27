<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	div#form-wrap{
		width: 400px;
		margin: 20px auto; 
		text-align: center;
		border: 1px solid #ddd;
	}
	#form-wrap label {
		width: 100px;
		float: left;
		text-align: right;
		margin-right: 10px;
	}
	div#list-wrap{
		width: 600px;
		margin: 0 auto;
		position: relative;
	}
	#list-wrap table{
		width: 100%;
		border-collapse: collapse;
		text-align: center;
	}
	th, td{
		border: 1px solid #ddd;
		padding: 5px 3px;
	}
	div#modify-wrap{
		width: 300px;
		padding: 10px;
		text-align: center;
		border: 1px solid #ddd;
		background: #eee;
		position: absolute;
		top: 40%;
		left: 40%;
		display: none;
	}
	#modify-wrap label {
		width: 70px;
		float: left;
		text-align: right;
		margin-right: 10px;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.5.3/handlebars.min.js"></script>
</head>
<body>
	<div id="form-wrap">
		<p>
			<label>아이디 </label>
			<input type="text" id="userid">
		</p>
		<p>
			<label>이름 </label>
			<input type="text" id="username">
		</p>
		<p>
			<label>비밀번호 </label>
			<input type="password" id="userpw">
		</p>
		<p>
			<label>이메일 </label>
			<input type="email" id="email">
		</p>
		<p>
			<button id="btnAdd">추가</button>
			<button id="btnList">리스트</button>
		</p>
	</div>
	<div id="list-wrap">
		<table id="table">
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>비밀번호</th>
				<th>이메일</th>
				<th>수정/삭제</th>
			</tr>
		</table>
	</div>
	
	<div id="modify-wrap">
		<p>
			<label>아이디 </label>
			<input type="text" id="modUserid" disabled="disabled">
		</p>
		<p>
			<label>이름 </label>
			<input type="text" id="modUsername">
		</p>
		<p>
			<label>비밀번호 </label>
			<input type="password" id="modUserpw">
		</p>
		<p>
			<label>이메일 </label>
			<input type="email" id="modEmail">
		</p>
		<p>
			<button id="btnModSave">수정</button>
			<button id="btnModClose">닫기</button>
		</p>
	</div>
	
	<script id="template" type="text/t-handlebars-template">
		{{#each.}}
			<tr class="addTr">
				<td class="userid">{{userid}}</td>
				<td>{{username}}</td>
				<td>{{userpw}}</td>
				<td>{{email}}</td>
				<td>
					<button class="btnMod">수정</button>
					<button class="btnDel">삭제</button>
				</td>
			</tr>
		{{/each}}
	</script>
	
	<script>
	/* 리스트  */
		$("#btnList").click(function() {
			$.ajax({
				url: "${pageContext.request.contextPath}/member/list",
				type: "get",
				dataType: "json",
				success: function(res) {
					console.log(res);
					$(".addTr").remove();
					var source = $("#template").html();
					var func = Handlebars.compile(source);
					var str = func(res);
					$("#table").append(str);
				},error: function(err) {
					console.log(err);
				}
			})
		})
	
	/* 추가 */
		$("#btnAdd").click(function() {
			var userid = $("#userid").val();
			var username = $("#username").val();
			var userpw = $("#userpw").val();
			var email = $("#email").val();
			var data = JSON.stringify({
				userid: userid,
				username: username,
				userpw: userpw,
				email: email
			});
			
			$.ajax({
				url: "${pageContext.request.contextPath}/member/",
				type: "post",
				data: data,
				headers: {"Content-Type" : "application/json"},
				dataType: "text",
				success: function(res) {
					console.log(res);
					$("input").val("");
					$("#btnList").click();
				},error: function(err) {
					console.log(err);
				}
			})
		})
		
	/* 수정창 */
		$(document).on("click", ".btnMod", function() {
			var userid = $(this).parents(".addTr").find(".userid").text();
			$("#modify-wrap").css("display", "block");
			
			$.ajax({
				url: "${pageContext.request.contextPath}/member/" + userid,
				type: "get",
				dataType: "json",
				success: function(res) {
					console.log(res);
					
					$("#modUserid").val(userid);
					$("#modUsername").val(res.username);
					$("#modUserpw").val(res.userpw);
					$("#modEmail").val(res.email);
				},error: function(err) {
					console.log(err);
				}
			})
		})	
		
	/* 수정 내용 저장 */
		$("#btnModSave").click(function() {
			var userid = $("#modUserid").val();
			var username = $("#modUsername").val();
			var userpw = $("#modUserpw").val();
			var email = $("#modEmail").val();
			var data = JSON.stringify({
				userid: userid,
				username: username,
				userpw: userpw,
				email: email
			});
			
			$.ajax({
				url: "${pageContext.request.contextPath}/member/" + userid,
				type: "put",
				data: data,
				headers: {"Content-Type" : "application/json"},
				dataType: "text",
				success: function(res) {
					console.log(res);
					$("#modify-wrap").css("display", "none");
					alert("수정 되었습니다.");
					$("#btnList").click();
				},error: function(err) {
					console.log(err);
				}
			})
		})	
		
	/* 수정창 닫기 */
		$("#btnModClose").click(function() {
			$("#modify-wrap").css("display", "none");
		})
	
	/* 삭제 */
		$(document).on("click", ".btnDel", function() {
			var userid = $(this).parents(".addTr").find(".userid").text();
			
			$.ajax({
				url: "${pageContext.request.contextPath}/member/" + userid,
				type: "delete",
				dataType: "text",
				success: function(res) {
					console.log(res);
					alert("삭제 되었습니다.");
					$("#btnList").click();
				},error: function(err) {
					console.log(err);
				}
			})
		})
	</script>
</body>
</html>