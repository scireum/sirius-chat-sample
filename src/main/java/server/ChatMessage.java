package server;

import com.alibaba.fastjson.JSONObject;

/**
 * Provides a simple data wrapper which contains a chat message (text) along with its sender.
 */
public class ChatMessage {

    private String text;
    private String sender;

    /**
     * Creates a new message with the given contents and sender.
     *
     * @param text   the message contents
     * @param sender the sender name
     */
    public ChatMessage(String text, String sender) {
        this.text = text;
        this.sender = sender;
    }

    /**
     * Parses a message from a given JSON object.
     *
     * @param messageObject the object to parse
     * @return the resulting message
     */
    public static ChatMessage fromJSON(JSONObject messageObject) {
        return new ChatMessage(messageObject.getString("text"), messageObject.getString("sender"));
    }

    /**
     * Wraps the message into a JSON object.
     *
     * @return the message as JSON object
     */
    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        result.put("text", text);
        result.put("sender", sender);

        return result;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }
}
