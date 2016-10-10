<!DOCTYPE HTML>
<html lang="en">
<head>
<title>web scoket demo</title>
</head>
<body onload="disconnect()">
	<noscript>
		<h2 style="color: #ff0000">Seems your browser doesn't support
			Javascript! Websocket relies on Javascript being enabled. Please
			enable Javascript and reload this page!</h2>
	</noscript>
	<div>
		<div>
			<button id="connect" onclick="connect();">Connect</button>
			<button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
		</div>
		<div id="conversationDiv">
			<label>Send your message:</label><input type="text" id="message" />
			<button id="sendName" onclick="sendName();">Send</button>
			<p id="response"></p>
		</div>
	</div>
	<script src="//cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script>
	<script src="js/stomp.js" type="text/javascript"></script>
	<script src="js/app.js" type="text/javascript"></script>
</body>
</html>