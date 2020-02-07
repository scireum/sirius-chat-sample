/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */

package hidden.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Have fun with the code =)
 * <p>
 * This class might have bad code as its part of a debugging exercise
 */
public class ColorCreator extends Helper {

    /**
     * Call this to get colors, duh!
     */
    public static List<Color> getColors(int amount) {
        List<Color> c = new ArrayList<>();
        iterate(amount, c, ColorCreator::generateColor);
        return c;
    }

    private static Color generateColor() {
        //next float
        float h = r.nextFloat();
        //create one
        return createOne(h);
    }

    public static String createHTML(List<Color> colors, List<String> names) {
        StringBuilder sb = new StringBuilder("<ul class=\"list-group\">");
        names.forEach(string -> {
            sb.append("<li class=\"list-group-item\" style=\"color:black;background-color:");
            sb.append(hexhex(getFromList(colors, names.indexOf(string))));
            sb.append(";\">");
            sb.append(names.indexOf(string) + 1);
            sb.append(". ");
            sb.append(string);
            sb.append("</li>");
        });
        sb.append("</ul>");
        return sb.toString();
    }
}
