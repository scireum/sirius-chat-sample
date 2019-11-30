package server;

import sirius.biz.cluster.InterconnectHandler;
import sirius.kernel.di.std.Register;

/**
 * TODO CHALLENGE-3 - Make this class implement {@link InterconnectHandler}.
 * Add a {@link Register} for the classes {@link ChatUplink} and {@link InterconnectHandler} AND REMOVE
 * the <tt>ChatUplink</tt> class form {@link ChatSessionRegistry}. This way this class becomes visible to the framework
 * and the {@link ChatSession} will forward messages here instead of the <tt>ChatSessionRegistry</tt>.
 */
public class ChatClusterUplink {

}
