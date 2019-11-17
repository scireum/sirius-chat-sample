package client;

import sirius.biz.isenguard.Isenguard;
import sirius.biz.isenguard.RateLimitingInfo;
import sirius.biz.web.BizController;
import sirius.kernel.async.CallContext;
import sirius.kernel.di.std.ConfigValue;
import sirius.kernel.di.std.Part;
import sirius.kernel.di.std.Register;
import sirius.web.controller.Controller;
import sirius.web.controller.DefaultRoute;
import sirius.web.controller.Routed;
import sirius.web.http.WebContext;

/**
 * Simple Controller that serves a html file under the main route.
 */
@Register(classes = Controller.class)
public class ChatClientController extends BizController {

    @Part
    private static Isenguard isenguard;

    private static final String RATE_LIMIT_REALM_REQUEST = "request";

    @ConfigValue("client.server")
    private String server;

    /**
     * Simple route that calls the pasta template containing the name chooser.
     *
     * @param webContext the context of the web request
     */
    @DefaultRoute
    @Routed("/")
    public void chooseName(WebContext webContext) {
        webContext.respondWith().template("/templates/name.html.pasta");
    }

    /**
     * Simple route that calls the pasta template containing the JavaScript cleint and provides it with some parameters.
     *
     * @param webContext the context of the web request
     */
    @DefaultRoute
    @Routed("/chat")
    public void client(WebContext webContext) {
        isenguard.enforceRateLimiting(CallContext.getNodeName(),
                                      RATE_LIMIT_REALM_REQUEST,
                                      () -> new RateLimitingInfo(null, null, null));

        String userName = webContext.get("username").asString(CallContext.getNodeName());
        String webSocketUrl = "ws://" + server + "/websocket";
        webContext.respondWith().template("/templates/client.html.pasta", userName, webSocketUrl);
    }
}
