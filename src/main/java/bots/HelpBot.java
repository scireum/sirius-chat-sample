/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */

package bots;

import com.alibaba.fastjson.JSONObject;
import sirius.kernel.commons.Strings;
import sirius.kernel.di.std.Register;

import java.util.function.BiConsumer;

/**
 * Handles :hilfe messages and responds with a short message.
 * <p>
 * This acts as a sample for a very basic chat bot.
 */
@Register
public class HelpBot implements ChatBot {

    private static final String HANDLED_MESSAGE = ":hilfe";

    @Override
    public boolean shouldHandleMessage(JSONObject message) {
        return HANDLED_MESSAGE.equals(message.getString("text"));
    }

    @Override
    public void handleMessage(JSONObject message, BiConsumer<String, String> outgoingMessageHandler) {
        String sender = message.getString("sender");
        outgoingMessageHandler.accept(Strings.apply("Hallo %s, ich helfe dir gerne!", sender),
                                      getClass().getSimpleName());
    }
}
