package server;

import sirius.kernel.di.std.Register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A registry of all currently open {@link ChatSession}s.
 */
@Register(classes = {ChatSessionRegistry.class})
public class ChatSessionRegistry {

    // TODO CHALLENGE-2
    // TODO keep a list of all active sessions (wrap it using Collections.synchronizedList to make it threadsafe)
    // TODO provide methods to add or remove a session
    // TODO add a method to return the registered sessions as an unmodifiableList
}
