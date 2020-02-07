/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */

package hidden.color;

import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 * This class might have bad code as its part of a debugging exercise
 */
public class Helper {
    static Random r = new Random();
    static float s = 0.8f;
    static float l = 0.9f;

    protected static Color createOne(float h) {
        return Color.getHSBColor(h, s, l);
    }

    protected static void iterate(int number, List<Color> list, Supplier<Color> supply) {
        for (int i = 1; i < number; i++) {
            list.add(supply.get());
        }
    }

    /**
     * Bibi Blocksberg :)
     */
    protected static String hexhex(Color color) {
        return "#" + Integer.toHexString(color.getRGB()).substring(2);
    }

    protected static <C> C getFromList(List<C> list, int index) {
        if (index >= list.size()) {
            return null;
        }
        return list.get(index);
    }
}
