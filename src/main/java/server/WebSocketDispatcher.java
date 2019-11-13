package server;

import sirius.web.http.WebContext;
import sirius.web.http.WebsocketDispatcher;
import sirius.web.http.WebsocketSession;

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
