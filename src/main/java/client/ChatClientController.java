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
     * Simple route that calls the pasta template containing the JavaScript client and provides it with some parameters.
     *
     * @param webContext the context of the web request
     */
    @DefaultRoute
    @Routed("/chat")
    public void client(WebContext webContext) {
        // TODO CHALLENGE-6, here is an example of Isenguard...
        isenguard.enforceRateLimiting(
                // We limit per remote IP address
                webContext.getRemoteIP().toString(),
                // Names the config section in application.conf from where to load the limits
                RATE_LIMIT_REALM_REQUEST,
                // In case the limit is hit, we need some more infos to log the incident, by default we extract
                // this from the current web request....
                RateLimitingInfo::fromCurrentContext);

        String userName = webContext.get("username").asString(CallContext.getNodeName());
        webContext.respondWith().template("/templates/client.html.pasta", userName);
    }
}
