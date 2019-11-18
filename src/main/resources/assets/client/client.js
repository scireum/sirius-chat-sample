var chatForm;
var chatInput;
var chatWall;
var socket;


document.addEventListener("DOMContentLoaded", function () {
    chatForm = document.querySelector(".chat-input-area");
    chatInput = document.querySelector(".chat-input");
    chatWall = document.querySelector(".chat-wall");
    openSocket();
    chatForm.addEventListener("submit", function (event) {
        event.preventDefault();
        sendMessage();
    });
});

function appendMessage(messageText, senderName) {
    var side = "left";
    if (USER_NAME === senderName) {
        side = "right";
    }

    var msgHTML = "\n" +
        "    <div class=\"msg ".concat(side, "-msg\">\n" +
            "      <div class=\"msg-bubble\">\n" +
            "        <div class=\"msg-info\">\n" +
            "          <div class=\"msg-info-name\">").concat(senderName, "</div>\n" +
            "          <div class=\"msg-info-time\">").concat(getTimeString(), "</div>\n" +
            "        </div>\n\n" +
            "        <div class=\"msg-text\">").concat(messageText, "</div>\n" +
            "    </div>\n  ");
    chatWall.insertAdjacentHTML("beforeend", msgHTML);
    chatWall.scrollTop += 500;
}

function sendMessage() {
    var msgText = chatInput.value;
    if (!msgText) return;
    chatInput.value = "";
    socket.send(JSON.stringify({
        type: 'text',
        text: msgText,
        sender: USER_NAME
    }));
}


function getTimeString() {
    var date = new Date();
    var h = "0" + date.getHours();
    var m = "0" + date.getMinutes();
    return "".concat(h.slice(-2), ":").concat(m.slice(-2));
}

function openSocket() {
    socket = new WebSocket("ws://" + window.location.host + "/websocket");

    socket.onopen = function () {
        console.log("[websocket] Connection established");
        console.log("[websocket] Sending 'hello' to server");
        socket.send(JSON.stringify({
            type: 'hello',
            sender: USER_NAME
        }));
    };

    socket.onmessage = function (event) {
        console.log('[websocket] Data received from server');
        var message = JSON.parse(event.data);
        appendMessage(message.text, message.sender);
    };

    socket.onclose = function () {
        console.log('[websocket] Connection closed');
        setTimeout(openSocket, 0);
    };

    socket.onerror = function (error) {
        console.log('[websocket] Error: ' + error.message);
        setTimeout(openSocket, 0);
    };
}
