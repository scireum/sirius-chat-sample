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

@Register(classes = Controller.class)
public class ChatClientController extends BizController {

    @Part
    private static Isenguard isenguard;

    private static final String RATE_LIMIT_REALM_REQUEST = "request";

    @ConfigValue("client.server")
    private String server;

    @DefaultRoute
    @Routed("/")
    public void client(WebContext webContext) {
        isenguard.enforceRateLimiting(CallContext.getNodeName(), RATE_LIMIT_REALM_REQUEST, () -> new RateLimitingInfo(null, null, null));

        String userName = CallContext.getNodeName();
        String webSocketUrl = "ws://" + server + "/websocket";
        webContext.respondWith().template("/templates/client.html.pasta", userName, webSocketUrl);
    }
}
