package server;

import search.SearchableChatMessage;
import sirius.kernel.di.std.Part;
import sirius.kernel.di.std.Register;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sends a received chat message to all connected users.
 */
// TODO CHALLENGE-3 extend ChatClusterUplink
@Register(classes = {ChatUplink.class})
public class ChatUplink extends ChatClusterUplink {

    //TODO CHALLENGE-2 initialize ChatSessionRegistry as @Part
    //@Part
    //private static ChatSessionRegistry registry; not needed in challenge-3

    /**
     * Forwards  the received chat message
     *
     * @param message the message to forward
     */
    public void distributeMessage(ChatMessage message) {
        // TODO CHALLENGE-2 Call the "sendToUser" for all available sessions
        // TODO use @Part to obtain the instance of ChatSessionRegistry needed for this task
        //for (ChatSession session : registry.getAllSessions()) {
        //    session.sendToUser(message);
        //} inactivated for challenge-3

        // TODO CHALLENGE-3 broadcast the message to subscribers using the method available in the super class instead
        broadcastMessage(message);

        // TODO SIDE-QUEST-4 - create a new instance of SearchableChatMessage
        // TODO use @Part to obtain the database connector "Elastic" which can insert entities into Elasticsearch.
        // TODO invoke elastic.update to persist the entity in Elasticsearch....
    }
}
