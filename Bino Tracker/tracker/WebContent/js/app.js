var stompClient = null;

function setConnected(connected) {
	document.getElementById('connect').disabled = connected;
	document.getElementById('disconnect').disabled = !connected;
	document.getElementById('conversationDiv').style.visibility = connected ? 'visible': 'hidden';
	document.getElementById('response').innerHTML = '';
}

function connect() {
	var socket = new SockJS('/tracker/hello');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/greetings', function(data) {
			// showGreeting(JSON.parse(data.body).message);
			showPositions(JSON.parse(data.body))
		});
	});
}

function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	setConnected(false);
}

function sendName() {
	var message = document.getElementById('message').value;
	stompClient.send("/app/hello", {}, JSON.stringify({
		'message' : message,
		'id': 1234,
	}));
}

function showGreeting(message) {
	var response = document.getElementById('response');
	var p = document.createElement('p');
	p.style.wordWrap = 'break-word';
	p.appendChild(document.createTextNode(message));
	response.appendChild(p);
}

function showPositions(positions) {
	// alert('showPositions()' + positions.length);
}