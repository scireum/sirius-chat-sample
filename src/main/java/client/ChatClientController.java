package client;

import sirius.biz.web.BizController;
import sirius.kernel.async.CallContext;
import sirius.kernel.di.std.Register;
import sirius.web.controller.Controller;
import sirius.web.controller.DefaultRoute;
import sirius.web.controller.Routed;
import sirius.web.http.WebContext;

@Register(classes = Controller.class)
public class ChatClientController extends BizController {

    @DefaultRoute
    @Routed("/")
    public void client(WebContext webContext) {
        String userName = CallContext.getNodeName();
        webContext.respondWith().template("/templates/client.html.pasta", userName);
    }
}
