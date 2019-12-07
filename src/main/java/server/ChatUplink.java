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
public class ChatUplink {

    /**
     * Forwards  the received chat message
     *
     * @param message the message to forward
     */
    public void distributeMessage(ChatMessage message) {
        // TODO CHALLENGE-2 Call the "sendToUser" for all available sessions
        // TODO use @Part to obtain the instance of ChatSessionRegistry needed for this task

        // TODO CHALLENGE-3 broadcast the message to subscribers using the method available in the super class instead

        // TODO SIDE-QUEST-4 - create a new instance of SearchableChatMessage
        // TODO use @Part to obtain the database connector "Elastic" which can insert entities into Elasticsearch.
        // TODO invoke elastic.update to persist the entity in Elasticsearch....
    }
}
