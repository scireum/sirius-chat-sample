package server;

import sirius.kernel.di.std.Register;
import sirius.web.http.WebContext;
import sirius.web.http.WebsocketDispatcher;
import sirius.web.http.WebsocketSession;

/**
 * Simple {@link WebSocketDispatcher} that propagates calls to '/websocket' into a {@link ChatSession}.
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
