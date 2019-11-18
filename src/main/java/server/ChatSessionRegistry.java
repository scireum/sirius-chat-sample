package server;

import sirius.kernel.di.std.Register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A registry of all currently open {@link ChatSession}s.
 */
//TODO CHALLENGE-2 only remove the ChatUplink class from the register annotation
@Register(classes = {ChatSessionRegistry.class, ChatUplink.class})
public class ChatSessionRegistry implements ChatUplink {

    // TODO CHALLENGE-1
    // TODO keep a list of all active sessions (wrap it using Collections.synchronizedList to make it threadsafe)
    // TODO Provide methods to add or remove a session

    @Override
    public void distributeMessage(ChatMessage message) {
        // TODO Call the "sendToUser" for all available messages..
    }
}
