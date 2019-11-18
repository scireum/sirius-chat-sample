package server;

/**
 * Sends a received chat message to all connected users.
 */
public interface ChatUplink {

    /**
     * Forwards  the received chat message
     *
     * @param message the message to forward
     */
    void distributeMessage(ChatMessage message);
}
