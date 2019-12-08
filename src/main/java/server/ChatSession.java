package server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import sirius.biz.isenguard.Isenguard;
import sirius.biz.isenguard.RateLimitingInfo;
import sirius.kernel.commons.Strings;
import sirius.kernel.di.std.Part;
import sirius.web.http.WebContext;
import sirius.web.http.WebsocketSession;

/**
 * A {@link WebsocketSession} with a single user capable of receiving and sending chat messages.
 * <p>
 * This class is instantiated by the {@link WebSocketDispatcher} for each incoming web socket.
 */
public class ChatSession extends WebsocketSession {

    /**
     * Contains the username of the user connected to this session.
     */
    private String username;

    /**
     * Holds a reference to the implementation of {@link ChatUplink}.
     */
    @Part
    private static ChatUplink uplink;

    //TODO CHALLENGE-2
    //TODO initialize ChatSessionRegistry as @Part (just like uplink above)

    /**
     * Creates a new session for the given channel and request.
     *
     * @param webContext the channel (context) which started the websocket
     */
    ChatSession(WebContext webContext) {
        super(webContext);
    }

    /**
     * Invoked for each incoming frame form the client side.
     *
     * @param webSocketFrame the frame to handle
     */
    @Override
    public void onFrame(WebSocketFrame webSocketFrame) {
        // There are several frame types but we're only interested in text frames...
        if (webSocketFrame instanceof TextWebSocketFrame) {
            onTextFrame(((TextWebSocketFrame) webSocketFrame).text());
        }
    }

    /**
     * Invoked for each text frame received.
     * <p>
     * This will parse the received JSON and then dispatch to the appropriate handler.
     *
     * @param text the received chat message
     */
    private void onTextFrame(String text) {
        JSONObject messageObject = JSON.parseObject(text);
        String type = messageObject.getString("type");
        if ("hello".equals(type)) {
            // This is the initial hello message from the client - handle appropriately
            handleHelloMessage(messageObject);
        } else if ("text".equals(type)) {
            // This is a regular chat message...
            handleChatMessage(ChatMessage.fromJSON(messageObject));
        }
    }

    /**
     * Handles the "hello" message which is sent by the client just after connecting.
     *
     * @param messageObject the message which was received from the client
     */
    private void handleHelloMessage(JSONObject messageObject) {
        // TODO CHALLENGE-1 fill the "username" field from the "sender" value in the given message object
        username = messageObject.getString("sender");
        sendToUser(new ChatMessage(Strings.apply("Welcome to the sirius chat, %s!", username), "SKIP"));
        // TODO send a message back to the user like "Welcome [username]..." with an artificial sender
        // e.g. "server" or "Bot"....

        //TODO feel free to play around here. Send additional messages, like the joke of the day. Or notify everyone
        //TODO that s.b. joined the chat.
    }

    /**
     * Handles an incoming message.
     *
     * @param chatMessage the message which was received from the client
     */
    private void handleChatMessage(ChatMessage chatMessage) {
        //TODO CHALLENGE-1 send the received message right back to the user connected to this websocket
        sendToUser(chatMessage);
        //TODO CHALLENGE-2 forward the received message to the ChatUplink instead

        // Feel free to play around here - censor curse words / replace emoji by their unicode code points or the like...

        //TODO SIDE-QUEST-2 - get hold of all available {@link ChatBot} implementations by using a @Parts annotation
        // on a static List<ChatBot> field.
        // Iterate over all bots while they return false and finally distribute the message

        //TODO SIDE-QUEST-3 Enforce rate limiting
    }

    /**
     * Sends a text message to the user connected to this web socket.
     *
     * @param chatMessage the message to send
     */
    public void sendToUser(ChatMessage chatMessage) {
        sendMessage(chatMessage.toJSON().toJSONString());
    }

    @Override
    public void onWebsocketOpened() {
        //TODO CHALLENGE-2 notify the ChatSessionRegistry about this new session
        //TODO SIDE-QUEST-3 Enforce rate limiting
    }

    @Override
    public void onWebsocketClosed() {
        //TODO CHALLENGE-2 notify the ChatSessionRegistry about the end of this session
    }
}
