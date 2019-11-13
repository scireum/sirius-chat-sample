package server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import sirius.kernel.commons.Strings;
import sirius.kernel.health.Exceptions;
import sirius.web.http.WebContext;
import sirius.web.http.WebsocketSession;

public class ChatSession extends WebsocketSession {

    private static final String PING = "ping";
    private static final String PONG = "pong";

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
            String textframe = ((TextWebSocketFrame) webSocketFrame).text();

            if (Strings.areEqual(textframe, PING)) {
                sendMessage(PONG);
            } else {
                onJSONMessage(JSON.parseObject(textframe));
            }
        }
    }

    private void onJSONMessage(JSONObject message) {
        try {
            String messageText = message.getString("text");
            String sender = message.getString("sender");

            propagateMessageToUser(messageText, sender);
        } catch (Exception e) {
            Exceptions.handle(e);
        }
    }

    private void propagateMessageToUser(String text, String sender) {
        JSONObject message = new JSONObject();
        message.put("text", text);
        message.put("sender", sender);
        sendMessage(message.toJSONString());
    }
}
