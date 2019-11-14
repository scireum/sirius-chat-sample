package server;

import sirius.kernel.di.std.Register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A registry of all currently open {@link ChatSession}s.
 */
@Register(classes = ChatSessionRegistry.class)
public class ChatSessionRegistry {

    private List<ChatSession> chatSessions = new ArrayList<>();

    public void registerNewSession(ChatSession chatSession) {
        chatSessions.add(chatSession);
    }

    public void removeSession(ChatSession chatSession) {
        chatSessions.remove(chatSession);
    }

    public List<ChatSession> getAllSessions() {
        return Collections.unmodifiableList(chatSessions);
    }
}
