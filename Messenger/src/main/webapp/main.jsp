<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
				stompClient.subscribe("/brokerA", function(msg){//브로커선택
					messagefilter(msg);
				});
			});
		},
		disconnect:function(){
			var stompClient = this.stompClient;
			if(stomClient){
				stomClient.send("/m/brokerA/out", {}, "stompClient 종료");
				stomClient.disconnect();
			}
		},
		sendMsg:function(msg){
			WebSocket.stompClient.send("/m/brokerA/msg", {}, msg);
			
		},
		init:function(){
			this.connect();
		}
	};
	
	//받은메시지처리
	function messagefilter(msg){
		if(msg == 'close'){
			WebSocket.disconnect();
		}
		else{
			appendMsgToHTML(msg, ".commentBox", true);
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
		
		WebSocket.sendMsg(JSON.stringify({name:"name", contents:text}));
		$("textarea").val("");
	});
	
	
	//대화창에 텍스트추가 (메시지, 메시지넣을 div이름, 스크롤아래로내릴껀지유무)
	function appendMsgToHTML(msg, divName, scroll){
		var name = JSON.parse(msg.body).name;
		var contents = JSON.parse(msg.body).contents;
		
		$(divName).append(
				"<div class='comment'>"
				+"<a class='avatar'>"
				+"<img src='https://semantic-ui.com/images/avatar/small/jenny.jpg'>"
				+"</a>"
				+"<div class='content'>"
				+"<a class='author'>"+name+"</a>"
				+"<div class='metadata'>"
				+"<span class='date'>Yesterday at 12:30AM</span>"
				+"</div>"
				+"<div class='text'>"
				+"<p>"+contents+"</p>"
				+"</div>"
				+"</div>"
				+"</div>"
		);
		if(scroll) scrollFullDown(divName);
	}
	
	function scrollFullDown(div){
		var h = $(div)[0].scrollHeight;
		$(div).scrollTop(h);
	}

</script>
</body>
</html>