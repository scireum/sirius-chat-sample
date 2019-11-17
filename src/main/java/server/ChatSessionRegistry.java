package server;

import com.alibaba.fastjson.JSON;
import sirius.db.redis.Subscriber;
import sirius.kernel.di.std.Register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A registry of all currently open {@link ChatSession}s.
 */
@Register(classes = {ChatSessionRegistry.class, Subscriber.class})
public class ChatSessionRegistry implements Subscriber {

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

    @Override
    public String getTopic() {
        return "sirius-chat-messages";
    }

    @Override
    public void onMessage(String message) {
        getAllSessions().forEach(chatSession -> chatSession.recieveMessage(JSON.parseObject(message)));
    }
}
