<%@page import="com.study.user.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	HttpSession s = request.getSession();
	String userId = (s.getAttribute("userId")==null)?null:(String)s.getAttribute("userId");
	if(userId == null){
		response.sendRedirect("login.jsp");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WebSocket</title>
<link rel="stylesheet" type="text/css" href="/Messenger/css/semantic.css">
<style type="text/css">
	.edge{
		border: 1px solid black;
		padding: 10px;
		width: 500px;
		height: 600px;
	}
	.commentBox{
		width: 450px;
		height: 450px;
		overflow-y: scroll;
	}
	.highlight{
		background-color: lightgreen;
		font-weight: bold;
	}
</style>
</head>
<body>
	
	<div class="edge">
		<div class="ui comments">
		<h3 class="ui dividing header">Chatting Room</h3>
		<div class="commentBox"></div>

		</div>
		<div class="field">
			<textarea cols="65"></textarea>
		</div>
		<div class="ui blue labeled submit icon button" id="sendBtn">
			<i class="icon edit"></i>SEND
		</div>
	</div>
	<p>//list : 유저리스트</p>
<script src="/Messenger/js/sockjs.min.js"></script>
<script src="/Messenger/js/stomp.min.js"></script>
<script
  src="https://code.jquery.com/jquery-3.1.1.min.js"
  integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
  crossorigin="anonymous">	
</script>
<script src="/Messenger/js/semantic.js"></script>	
<script type="text/javascript">

	$(function(){
		WebSocket.init();

	})
	
	
	
	var WebSocket = {
		stompClient:null,
		connect:function(){
			var socket = new SockJS("this_is_endpoint");//sockjs초기화
			this.stompClient = Stomp.over(socket);		//stomp초기화
			
			this.stompClient.connect({}, function(){	//stomp연결
				var stompClient = WebSocket.stompClient;
				
				//구독
				stompClient.send("/m/brokerA/subscribe", {}, "");
				
				//브로커선택, 이 브로커한테서 받은 메시지 처리
				stompClient.subscribe("/brokerA", function(msg){
					inMsgFilter(msg);
				});
			});
		},
		disconnect:function(){
			var stompClient = this.stompClient;
			if(stompClient){
				stompClient.send("/m/brokerA/out", {}, "");
				stompClient.send("/m/brokerA/disconnect", {}, "");
			//	stompClient.disconnect();
			}
		},
		sendMsg:function(msg){
			WebSocket.stompClient.send("/m/brokerA/msg", {}, msg);
			
		},
		getList:function(){
			WebSocket.stompClient.send("/m/brokerA/userList", {}, "");
			
		},
		init:function(){
			this.connect();
		}
	};
	
	//받은메시지처리
	function inMsgFilter(rawmsg){
		var msgJson = JSON.parse(rawmsg.body);
		var msg = msgJson.contents;
		if(msg == 'close'){
			WebSocket.disconnect();
		}else{
			appendMsgToHTML(msgJson, ".commentBox", true);
		}
	}
	
	//보낼메시지처리
	function outMsgFilter(rawmsg){
		var msg = rawmsg;
		if(msg == "//list"){
			WebSocket.getList();
		}else{
			WebSocket.sendMsg(JSON.stringify({name:"${userId}", contents:msg}));
		}
	}
	
	//엔터처리
	$("textarea").keydown(function(key) {
		if (key.keyCode == 13) {
			key.preventDefault();
			$("#sendBtn").click();
		}
	});
	
	//보내기버튼
	$("#sendBtn").on("click", function(){
		var text = $("textarea").val();
		if(text == "") return;
		outMsgFilter(text)
		
		$("textarea").val("");
	});
	
	
	//대화창에 텍스트추가 (메시지, 메시지넣을 div이름, 스크롤아래로내릴껀지유무)
	function appendMsgToHTML(msgJson, divName, scroll){
		var name = msgJson.name;
		var contents = msgJson.contents;
		var date = msgJson.date;
		
		var html =  "<div class='comment'>";
			html += "<a class='avatar'>";
			html += "<img src='https://semantic-ui.com/images/avatar/small/jenny.jpg'>";
			html += "</a>";
			html += "<div class='content'>";
			if(name == "${userId}"){
				html += "<a class='author highlight'>"+name+"</a>";
			}else
				html += "<a class='author'>"+name+"</a>";
			html += "<div class='metadata'>";
			if(isToday(date)){
				html += "<span class='date'>오늘</span>";
			}else{
				html += "<span class='date'>"+date+"</span>";
			}
			
			html += "</div>";
			html += "<div class='text'>";
			html += "<p>"+contents+"</p>";
			html += "</div></div></div>";
		$(divName).append(html);
		if(scroll) scrollFullDown(divName);
	}
	
	function scrollFullDown(div){
		var h = $(div)[0].scrollHeight;
		$(div).scrollTop(h);
	}
	
	setInterval(function(){
		isTodayAll();
	},5000);	
	
	function isTodayAll(){
		var $dArr = $(".date");
		var today = new Date();
		var date = today.getFullYear() + "/" + eval(today.getMonth()+1) + "/" + today.getDate();
 
		for(var i=0 ; i<$dArr.length ; i++){
			var d = $dArr[i].textContent;
			var arr = d.split(" ");

			if(arr[0] == date){
				$dArr[i].textContent = "오늘";
			}else{
				$dArr[i].textContent = arr[0] + " " + arr[1];
			}
		}alert("asd");
	}
	
	function isToday(d){
		var today = new Date();
		var date = today.getFullYear() + "/" + eval(today.getMonth()+1) + "/" + today.getDate();
 
		var arr = d.split(" ");

		if(arr[0] == date){
			return true;
		}else{
			return false;
		}
		
	}
</script>
</body>
</html>