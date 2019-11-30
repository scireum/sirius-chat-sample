package server;

import search.SearchableChatMessage;
import sirius.kernel.di.std.Register;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A registry of all currently open {@link ChatSession}s.
 */
//TODO CHALLENGE-3 only remove the ChatUplink class from the register annotation
@Register(classes = {ChatSessionRegistry.class, ChatUplink.class})
public class ChatSessionRegistry implements ChatUplink {

    // TODO CHALLENGE-2
    // TODO keep a list of all active sessions (wrap it using Collections.synchronizedList to make it threadsafe)
    // TODO Provide methods to add or remove a session

    @Override
    public void distributeMessage(ChatMessage message) {
        // TODO Call the "sendToUser" for all available messages..

        // TODO SIDE-QUEST-4 - create a new instance of SearchableChatMessage
        // TODO use @Part to obtain the database connector "Elastic" which can insert entities into Elasticsearch.
        // TODO invoke elastic.update to persist the entity in Elasticsearch....
    }
}
