var BOT_NAME = "SKIP";
var BOT_MSG = "Hi, ich bin Skip! Ich bin dein Platzhalter - Gesprächspartner.😄";
var chatForm;
var chatInput;
var chatWall;


document.addEventListener("DOMContentLoaded", function () {
    chatForm = document.querySelector(".chat-input-area");
    chatInput = document.querySelector(".chat-input");
    chatWall = document.querySelector(".chat-wall");
    botResponse();
    chatForm.addEventListener("submit", function (event) {
        event.preventDefault();
        sendMessage();
    });
});

function appendMessage(name, side, text) {
    var msgHTML = "\n" +
        "    <div class=\"msg ".concat(side, "-msg\">\n" +
            "      <div class=\"msg-bubble\">\n" +
            "        <div class=\"msg-info\">\n" +
            "          <div class=\"msg-info-name\">").concat(name, "</div>\n" +
            "          <div class=\"msg-info-time\">").concat(getTimeString(), "</div>\n" +
            "        </div>\n\n" +
            "        <div class=\"msg-text\">").concat(text, "</div>\n" +
            "    </div>\n  ");
    chatWall.insertAdjacentHTML("beforeend", msgHTML);
    chatWall.scrollTop += 500;
}

function sendMessage() {
    var msgText = chatInput.value;
    if (!msgText) return;
    appendMessage(PERSON_NAME, "right", msgText);
    chatInput.value = "";
    botResponse();
}

function botResponse() {
    setTimeout(function () {
        appendMessage(BOT_NAME, "left", BOT_MSG);
    }, 1200);
}


function getTimeString() {
    var date = new Date();
    var h = "0" + date.getHours();
    var m = "0" + date.getMinutes();
    return "".concat(h.slice(-2), ":").concat(m.slice(-2));
}

