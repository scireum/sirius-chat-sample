package server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import sirius.kernel.commons.Strings;
import sirius.kernel.di.std.Part;
import sirius.kernel.health.Exceptions;
import sirius.web.http.WebContext;
import sirius.web.http.WebsocketSession;

public class ChatSession extends WebsocketSession {

    private static final String KEY_SENDER = "sender";
    private static final String KEY_TEXT = "text";

    @Part
    private static ChatSessionRegistry chatSessionRegistry;

    /**
     * Creates a new session for the given channel and request.
     *
     * @param webContext the channel (context) which started the websocket
     */
    ChatSession(WebContext webContext) {
        super(webContext);
    }

    @Override
    public void onFrame(WebSocketFrame webSocketFrame) {
        if (webSocketFrame instanceof TextWebSocketFrame) {
            String textFrame = ((TextWebSocketFrame) webSocketFrame).text();

            JSONObject jsonObject = JSON.parseObject(textFrame);
            String type = jsonObject.getString("type");
            if ("hello".equals(type)) {
                sendHelloMessage(jsonObject);
            } else if (KEY_TEXT.equals(type)) {
                handleText(jsonObject);
            }
        }
    }

    private void sendHelloMessage(JSONObject jsonObject) {
        String sender = jsonObject.getString(KEY_SENDER);
        propagateMessageToUser(Strings.apply("Willkommen im sirius Chat, %s!", sender), "SKIP");
    }

    private void handleText(JSONObject message) {
        try {
            String messageText = message.getString(KEY_TEXT);
            String sender = message.getString(KEY_SENDER);
            sendMessageToAllSessions(messageText, sender);
        } catch (Exception e) {
            Exceptions.handle(e);
        }
    }

    private void sendMessageToAllSessions(String messageText, String sender) {
        chatSessionRegistry.getAllSessions()
                           .forEach(chatSession -> chatSession.propagateMessageToUser(messageText, sender));
    }

    public void propagateMessageToUser(String text, String sender) {
        JSONObject message = new JSONObject();
        message.put(KEY_TEXT, text);
        message.put(KEY_SENDER, sender);
        sendMessage(message.toJSONString());
    }

    @Override
    public void onWebsocketOpened() {
        super.onWebsocketOpened();
        chatSessionRegistry.registerNewSession(this);
    }

    @Override
    public void onWebsocketClosed() {
        super.onWebsocketClosed();
        chatSessionRegistry.removeSession(this);
    }
}
