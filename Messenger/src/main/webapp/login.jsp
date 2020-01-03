<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="/Messenger/css/semantic.css">
<style type="text/css">
	.edge{
		border: 1px solid black;
		padding: 10px;
		margin: 50px;
		width: 500px;
		height: 600px;
	}
	.modal{
		left: ;
	}
	.joinFormDiv{
		margin: 25px;
	}
</style>
</head>
<body>
	<div class="edge">
		<div class="ui inverted segment">
			<div class="ui inverted form">
				<div class="field">
					<label>ID
						<input placeholder="ID" type="text">
					</label>
				</div>
				<div class="field">
					<label>Password
						<input placeholder="Password" type="text">
					</label>
				</div>
				<div class="ui submit positive button" id="loginBtn">Login</div>
				<div class="ui mini compact button" id="joinBtn">Join</div>
				
			</div>
		</div>
	</div>

	<div class="ui modal">
		<i class="close icon"></i>
		<div class="header">
			Join
		</div>
		
		<div class="joinFormDiv">
			<form class="ui form">
				<div class="field">
					<label>ID
						<input type="text" name="userId" id="newId" placeholder="ID">
					</label>
				</div>
				<div class="field">
					<label>Password
						<input type="password" name="pw" id="newPw" placeholder="Password">
					</label>
				</div>
			</form>
		</div>
		
		<div class="actions">
			<div class="ui positive labeled icon button" id="joinSubmitBtn">
				Join
				<i class="checkmark icon"></i>
			</div>
			<div class="ui black deny button">
				cancel
			</div>
		</div>
	</div>

<script
	src="https://code.jquery.com/jquery-3.1.1.min.js"
	integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
	crossorigin="anonymous">	
</script>
<script src="/Messenger/js/semantic.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script type="text/javascript">

	$("#joinBtn").on("click", function(){
		$("#newId").val("");
		$("#newPw").val("");
		$('.modal').modal('show');
	});
	
	$("#joinSubmitBtn").on("click", function(){
		joinValidation(true);
		return false;
	});
	
	function joinValidation(goSubmit){
		var id = $("#newId").val();
		if(id.length == 0){
			alert("null");
			return false;
		}else{
			loadingBtn("joinSubmitBtn", true);
			var promise = $.ajax({
				type : "POST", 
				url : "validation",
				dataType : "json",
				data : {
					userId : id
				}
				});
			
			promise.done(function(data){
				if(data.id == 1){
					alert("없는아이디");
					if(goSubmit) joinSubmit();
				}else if(data.id == 0){
					alert("있는아이디");
				}
			});
			
			promise.fail(function(data){
				alert("통신실패");
			});
			
			promise.always(function(){
				loadingBtn("joinSubmitBtn", false);
			})
		}
	}
	
	function loadingBtn(id, bool){
		var s = id.charAt(0);
		if(s != "#"){
			id = "#"+id;
		}
		
		if(bool == true){
			$(id).addClass("loading");
			$(id).addClass("disabled");
		}else{
			$(id).removeClass("disabled");
			$(id).removeClass("loading");
		}
	}
	
	function joinSubmit(){
		var id = $("#newId").val();
		var password = $("#newPw").val();
		if(id.length == 0 || password.length == 0){
			alert("0");
			return false;
		}else{
			loadingBtn("joinSubmitBtn", true);
			$.ajax({
				type : "POST",
				url : "join",
				dataType : "json",
				data : {
					userId : id,
					pw : password
				}
			}).done(function(data){
				if(data.id == 1){
					alert("등록");
					$('.modal').modal('hide');
				}else if(data.id == 0){
					alert("실패");
				}
			}).fail(function(){
				alert("통신실패");
			}).always(function(){
				loadingBtn("joinSubmitBtn", false);
			});
		}
	}

	
</script>
</body>
</html>