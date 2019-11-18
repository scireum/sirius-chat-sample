package server;

import sirius.kernel.di.std.Register;
import sirius.web.http.WebContext;
import sirius.web.http.WebsocketDispatcher;
import sirius.web.http.WebsocketSession;

/**
 * Simple {@link WebSocketDispatcher} that propagates calls to '/websocket' into a {@link ChatSession}.
 * <p>
 * Note that the description below is provided to understand the framework and its inner workings. You do not need to
 * read and understand all that to master the challenges... :)
 * <p>
 * As this class wears a {@link Register} annotation and implements {@link WebSocketDispatcher} the
 * {@link sirius.kernel.di.std.AutoRegisterAction} of <tt>sirius-kernel</tt> will instantiate this class and put
 * it into the {@link sirius.kernel.di.PartRegistry} of the {@link sirius.kernel.di.Injector}.
 * <p>
 * If now the <tt>web server</tt> (the {@link sirius.web.http.WebsocketHandler} to be exact) detects an incoming
 * web socket, if checks which dispatcher is responsible via {@link #getWebsocketUri()} and invokes the respective
 * {@link #createSession(WebContext)}.
 *
 * @see ChatSession
 */
@Register
public class WebSocketDispatcher implements WebsocketDispatcher {

    @Override
    public String getWebsocketUri() {
        return "/websocket";
    }

    @Override
    public WebsocketSession createSession(WebContext webContext) {
        return new ChatSession(webContext);
    }
}
