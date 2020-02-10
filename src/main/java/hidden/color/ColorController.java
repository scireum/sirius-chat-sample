/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */

package hidden.color;

import org.apache.commons.lang3.RandomStringUtils;
import sirius.biz.web.BizController;
import sirius.kernel.di.std.Register;
import sirius.web.controller.Controller;
import sirius.web.controller.DefaultRoute;
import sirius.web.controller.Routed;
import sirius.web.http.WebContext;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Oh no! This Controller is not well documented :(
 * <p>
 * This class might have bad code as its part of a debugging exercise
 */
@Register(classes = Controller.class)
public class ColorController extends BizController {
    
    @DefaultRoute
    @Routed("/colors")
    public void client(WebContext webContext) {
        int amount = webContext.get("amount").asInt(100);
        List<Color> colors = ColorCreator.getColors(amount);
        List<String> names = new ArrayList<>(amount);
        while (amount-- > 0) {
            names.add(RandomStringUtils.randomAlphabetic(3, 10));
        }
        String htmlColors = ColorCreator.createHTML(colors, names);
        webContext.respondWith().template("/templates/colors.html.pasta", htmlColors);
    }
}
