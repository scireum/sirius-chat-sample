var BOT_NAME = "SKIP";
var BOT_MSGS = ["Hi, ich bin Skip! Ich bin dein Platzhalter - Gesprächspartner.😄"];
var chatForm;
var chatInput;
var chatChat;

document.addEventListener("DOMContentLoaded", function () {
    chatForm = get(".chat-inputarea");
    chatInput = get(".chat-input");
    chatChat = get(".chat-chat");
    botResponse();
    chatForm.addEventListener("submit", function (event) {
        event.preventDefault();
        var msgText = chatInput.value;
        if (!msgText) return;
        appendMessage(PERSON_NAME, "right", msgText);
        chatInput.value = "";
        botResponse();
    });
});

function appendMessage(name, side, text) {
    var msgHTML = "\n" +
        "    <div class=\"msg ".concat(side, "-msg\">\n" +
            "      <div class=\"msg-bubble\">\n" +
            "        <div class=\"msg-info\">\n" +
            "          <div class=\"msg-info-name\">").concat(name, "</div>\n" +
            "          <div class=\"msg-info-time\">").concat(formatDate(new Date()), "</div>\n" +
            "        </div>\n\n" +
            "        <div class=\"msg-text\">").concat(text, "</div>\n" +
            "    </div>\n  ");
    chatChat.insertAdjacentHTML("beforeend", msgHTML);
    chatChat.scrollTop += 500;
}

function botResponse() {
    var r = random(0, BOT_MSGS.length - 1);
    var msgText = BOT_MSGS[r];
    var delay = msgText.split(" ").length * 100;
    setTimeout(function () {
        appendMessage(BOT_NAME, "left", msgText);
    }, delay);
}


function get(selector) {
    var root = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : document;
    return root.querySelector(selector);
}

function formatDate(date) {
    var h = "0" + date.getHours();
    var m = "0" + date.getMinutes();
    return "".concat(h.slice(-2), ":").concat(m.slice(-2));
}

function random(min, max) {
    return Math.floor(Math.random() * (max - min) + min);
}
