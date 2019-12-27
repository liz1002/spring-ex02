<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	h1{
		text-align: center;
	}
	#reply-wrap{
		width: 400px;
		margin: 0 auto;
	}
	label{
		width: 70px;
		float: left;
		margin-right: 10px
	}
	#btnAdd, #btnList{
		margin-left: 80px;
		width: 80px;
		padding: 5px;
	}
	.replyLi{
		width: 400px; 
		margin: 50px auto;
	}
	.replyLi .item{
		border-bottom: 1px #ddd solid;
		padding: 5px;
		width: 400px;
		position: relative;
	}
	.replyLi .item .btnWrap{
		position: absolute;
		right: 10px;
		top: 5px;
	}
	.replyLi .item .rno{
		font-weight: bold;
	}
	#modWrap{
		width: 300px;
		height: 150px;
		background: #ddd;
		position: absolute;
		top: 40%;
		left: 40%;
		padding: 10px;
		display: none;
		z-index: 100;
	}
	ul{
		width: 400px;
		margin: 0 auto;
	}
	ul#replyList li{
		margin: 10px auto;
	}
	ul.pagination li{
		float: left;
		width: 30px;
		height: 30px;
		text-align: center;
		border: 1px solid #ddd;
		list-style: none;
		margin: 0px 5px;
	}
	li.on{
		background: #e0bfff;
	}
	.pagination li a{
		width: 100%;
		height: 100%;
		text-decoration: none;
		color: black;
		font-size: 14px;
		cursor: pointer;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.5.3/handlebars.min.js"></script>
</head>
<body>
	<h1>Ajax Test</h1>
	<div id="reply-wrap">
		<p>
			<label>bno</label>
			<input type="text" id="bno" value="1530">
		</p>
		<p>
			<label>replyer</label>
			<input type="text" id="replyer">
		</p>
		<p>
			<label>replytext</label>
			<textarea rows="5" cols="40" id="replytext"></textarea>
		</p>
		<p>
			<button id="btnAdd">Add</button>
			<button id="btnList">Show List</button>
		</p>
	</div>
	
	<div id="modWrap">
		<div class="mod-rno">rno</div>
		<div>
			<textarea rows="5" cols="30" id="modReplyText">내용</textarea>
		</div>
		<button id="btnModSave">수정</button>
		<button id="btnModCancel">취소</button>
	</div>
	
	<ul id="replyList">
		<!-- <li class="replyLi">
			<div class="item">
				<span class="rno">1</span> : <span class="writer">작성자</span><br>
				<span class="text">댓글 내용</span>
				<div class="btnWrap">
					<button class="btnMod">수정</button>
					<button class="btnDel">삭제</button>
				</div>
			</div>
		</li> -->
	</ul>
	
	<script id="template" type="text/t-handlebars-template">
		{{#list}}
			<li class="replyLi">
			<div class="item">
				<span class="rno">{{rno}}</span> : <span class="writer">{{replyer}}</span><br>
				<span class="text">{{replytext}}</span>
				<div class="btnWrap">
					<button class="btnMod">수정</button>
					<button class="btnDel">삭제</button>
				</div>
			</div>
		</li>
		{{/list}}
	</script>
	
	<ul class="pagination">
	</ul>
	
	<!-- script -->
	<script>
		var pageNo = 1; //현재 선택한 페이지 번호
		
		function getPageList(page) { //page : 페이지 번호
			$("#replyList").empty(); //리스트 초기화
		
			$.ajax({
				url: "replies/" + $("#bno").val() + "/" + page,
				type: "get",
				dataType: "json",
				success: function(res) {
					console.log(res);
					
					var source = $("#template").html();
					var func = Handlebars.compile(source);
					var str = func(res);
					$("#replyList").append(str);
					
					/* $(res.list).each(function(i, obj) {
						var $li = $("<li>").addClass("replyLi");
						
						var $divItem = $("<div>").addClass("item");
						var $spanRNo = $("<span>").addClass("rno").text(obj.rno);
						var $spanWriter = $("<span>").addClass("writer").text(obj.replyer);
						var $spanText = $("<span>").addClass("text").text(obj.replytext);
						
						var $divBtnWrap = $("<div>").addClass("btnWrap");
						var $buttonMod = $("<button>").addClass("btnMod").text("수정");
						var $buttonDel = $("<button>").addClass("btnDel").text("삭제");
						
						$divBtnWrap.append($buttonMod, $buttonDel);
						$divItem.append($spanRNo, " : ", $spanWriter, "<br>", $spanText, $divBtnWrap);
						$li.append($divItem);
						$("#replyList").append($li);
					}) */
					
					var startPage = res.pageMaker.startPage;
					var endPage = res.pageMaker.endPage;

					$(".pagination").empty();
					/* 이전 버튼 */
					if(res.pageMaker.prev == true){
						var $li = $("<li>");
						var $a = $("<a>").attr("data-page", startPage-1).attr("href", "#").html("&ltrif;");
						$li.append($a);
						$(".pagination").append($li);
					}
					
					/* 페이지 번호 버튼 */
					for(var i=startPage; i<=endPage; i++){
						var $li = $("<li>");
						if(i == page){ //선택한 페이지 번호에만 class
							$li.addClass("on");
						}
						var $a = $("<a>").attr("data-page", i).attr("href", "#").text(i);
						$li.append($a);
						$(".pagination").append($li);
					}
					
					/* 다음 버튼 */
					if(res.pageMaker.next == true){
						var $li = $("<li>");
						var $a = $("<a>").attr("data-page", endPage+1).attr("href", "#").html("&rtrif;");
						$li.append($a);
						$(".pagination").append($li);
					}
				}, 
				error: function(err) {
					console.log(err);
				}
			})
		}//getPageList()
	
		/* 리스트 */
		$("#btnList").click(function() {
			getPageList(pageNo);
			
		}) //#btnList.click()
		
		/* 페이지 번호 */
		$(document).on("click", "ul.pagination a", function() {
			var page = $(this).attr("data-page");
			pageNo = page; //전역 변수에 현재 선택한 페이지 번호 담기
			getPageList(page);
		})//.pagination a.click()
		
		/* 추가 */
		$("#btnAdd").click(function() {
			var bno = $("#bno").val();
			var replyer = $("#replyer").val();
			var replytext = $("#replytext").val();
			var data = JSON.stringify({
					bno : bno,
					replyer : replyer,
					replytext : replytext,
			});
			
			$.ajax({
				url: "replies/",
				type: "post",
				data: data,
				headers: {"Content-Type" : "application/json"}, //ajax에서 json String 형식으로 값 넘겨줄 때
				dataType: "text",
				success: function(res) {
					console.log(res);
					if(res == "success"){
						$("#bno").val("1530");
						$("#replyer").val("");
						$("#replytext").val("");
						$("#btnList").click();
					}
				}, 
				error: function(err) {
					console.log(err);
				}
			})
		}) //#btnAdd.click()
		
		/* 삭제 */
		$(document).on("click", ".btnDel", function() {
			var rno = $(this).parents(".item").find(".rno").text();
			
			$.ajax({
				url: "replies/" + rno,
				type: "delete",
				success: function(res) {
					console.log(res);
					if(res == "success"){
						alert("삭제 되었습니다.");
					}
					getPageList(pageNo);
				}, 
				error: function(err) {
					console.log(err);
				}
			})
		}) //.btnDel.click()
		
		/* 수정창 */
		$(document).on("click", ".btnMod", function() {
			$("#modWrap").css("display", "block");
			var rno = $(this).parents(".item").find(".rno").text();
			var replytext = $(this).parents(".item").find(".text").text();
			$(".mod-rno").text(rno);
			$("#modReplyText").val(replytext);
		})//.btnMod.click()
		
		/* 수정 */
		$("#btnModSave").click(function() {
			var rno = $(".mod-rno").text();
			var replytext = $("#modReplyText").val();
		
			$.ajax({
				url: "replies/" + rno,
				type: "put",
				data: JSON.stringify({replytext : replytext}),
				headers: {"Content-Type" : "application/json"}, //ajax에서 json String 형식으로 값 넘겨줄 때
				dataType: "text",
				success: function(res) {
					console.log(res);
					if(res == "success"){
						$("#modWrap").css("display", "none");
						getPageList(pageNo);
					}
				}, 
				error: function(err) {
					console.log(err);
				}
			})
		})
		
		/* 취소 */
		$("#btnModCancel").click(function() {
			$("#modWrap").css("display", "none");			
		})
	</script>
</body>
</html>

