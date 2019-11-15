package server;

import bots.ChatBot;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import search.ChatMessage;
import sirius.biz.isenguard.Isenguard;
import sirius.biz.isenguard.RateLimitingInfo;
import sirius.db.es.Elastic;
import sirius.db.redis.Redis;
import sirius.kernel.async.CallContext;
import sirius.kernel.commons.Strings;
import sirius.kernel.di.PartCollection;
import sirius.kernel.di.std.Part;
import sirius.kernel.di.std.Parts;
import sirius.kernel.health.Exceptions;
import sirius.web.http.WebContext;
import sirius.web.http.WebsocketSession;

public class ChatSession extends WebsocketSession {

    private static final String KEY_SENDER = "sender";
    private static final String KEY_TEXT = "text";

    /**
     * Message type for incoming bot calls (messages beginning with a : prefix)
     */
    private static final String KEY_BOT_CALL = "botcall";

    @Part
    private static ChatSessionRegistry chatSessionRegistry;

    @Part
    private static Redis redis;

    @Part
    private static Elastic elastic;

    @Part
    private static Isenguard isenguard;

    /**
     * List of all {@link sirius.kernel.di.std.Register registered} {@link ChatBot chat bots} that can handle bot calls.
     * <p>
     * This list is automatically filled by the framework with all {@link ChatBot} implementation that are marked with
     * the {@link sirius.kernel.di.std.Register} annotation. These can then be accessed by iterating the collection.
     */
    @Parts(ChatBot.class)
    private static PartCollection<ChatBot> chatBots;

    private static final String RATE_LIMIT_REALM_FRAME = "frame";

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
        isenguard.enforceRateLimiting(CallContext.getNodeName(),
                                      RATE_LIMIT_REALM_FRAME,
                                      () -> new RateLimitingInfo(null, null, null));

        if (webSocketFrame instanceof TextWebSocketFrame) {
            String textFrame = ((TextWebSocketFrame) webSocketFrame).text();

            JSONObject jsonObject = JSON.parseObject(textFrame);
            String type = jsonObject.getString("type");
            if ("hello".equals(type)) {
                sendHelloMessage(jsonObject);
            } else if (KEY_TEXT.equals(type)) {
                redis.publish(chatSessionRegistry.getTopic(), textFrame);
            }

            // TODO:
            // When a message of type "botcall" (KEY_BOT_CALL) is received it should be handled separately by first
            // displaying the received message to the user and then calling tryHandleBotCall where the actual processing occurs.
            // Doing so will handle bot calls locally (no other user sees the bot call and the response), in a second step
            // you could try to broadcast the bot call and the bot responses via redis to all users.
        }
    }

    private void sendHelloMessage(JSONObject jsonObject) {
        String sender = jsonObject.getString(KEY_SENDER);
        propagateMessageToUser(Strings.apply("Willkommen im sirius Chat, %s!", sender), "SKIP");
    }

    public void handleText(JSONObject message) {
        try {
            String messageText = message.getString(KEY_TEXT);
            String sender = message.getString(KEY_SENDER);
            storeMessage(messageText, sender);
            propagateMessageToUser(messageText, sender);
        } catch (Exception e) {
            Exceptions.handle(e);
        }
    }

    /**
     * Handles the provided bot call message by finding a responsible bot and passing it the message
     * or displaying a fallback message to the user when no matching bot is found.
     *
     * @param message the bot call message to handle (containing the message as property "text"
     */
    private void tryHandleBotCall(JSONObject message) {
        // TODO:
        // Handle the incoming bot call (*message*) by finding a responsible bot from the *chatBots* collection
        // (have a look at the *ChatBot* interface to see which method could be used for this.
        // When a bot is found that can process the message pass it the message and a callback method for displaying the
        // generated messages of the bot to the user (this could maybe also be a method reference from this class).
        // When no matching bot is found display a message to the user explaining that the message could not be handled.
    }

    private void storeMessage(String messageText, String sender) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setText(messageText);
        chatMessage.setSender(sender);
        elastic.update(chatMessage);
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
