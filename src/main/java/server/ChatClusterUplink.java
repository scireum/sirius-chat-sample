package server;

import com.alibaba.fastjson.JSONObject;
import sirius.biz.cluster.Interconnect;
import sirius.biz.cluster.InterconnectHandler;
import sirius.kernel.di.std.Part;
import sirius.kernel.di.std.Register;

import javax.annotation.Nonnull;

/**
 * Publishes/subscribes messages using {@link Interconnect} to/from other nodes in the cluster
 * Hint: Redis is being used as the "database" here!
 */
@Register(classes = {InterconnectHandler.class})
public class ChatClusterUplink implements InterconnectHandler {

    @Part
    private static Interconnect interconnect;

    @Part
    private static ChatSessionRegistry registry;

    @Override
    public void handleEvent(JSONObject event) {
        // TODO CHALLENGE-3 propagate the event as JSON to all available sessions in ChatSessionRegistry
        // TODO right now you definitely know how to grab ChatSessionRegistry :-)
        // TODO Hint! this code looks familiar? Explains why you might get messages in double ;-)
        for (ChatSession session : registry.getAllSessions()) {
            session.sendToUser(ChatMessage.fromJSON(event));
        }
    }

    /**
     * Publishes a message identified as "sirius-chat" to other subscribers
     * @param message message to send
     */
    public void broadcastMessage(ChatMessage message) {
        interconnect.dispatch(getName(), message.toJSON());
    }

    @Nonnull
    @Override
    public String getName() {
        return "sirius-chat";
    }
}
