/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */

package bots;

import com.alibaba.fastjson.JSONObject;
import parsii.eval.Expression;
import parsii.eval.Parser;
import parsii.tokenizer.ParseException;
import sirius.kernel.commons.Strings;
import sirius.kernel.di.std.Register;

import java.util.function.BiConsumer;

/**
 * Handles :calc messages by trying to evaluate the following mathematical expression using {@link Parser} and responding with the result.
 * <p>
 * This acts as a sample for a more advanced chat bot that interacts with the whole message as a kind of parameter.
 */
@Register
public class CalcBot implements ChatBot {

    private static final String HANDLED_MESSAGE_PREFIX = ":calc ";

    @Override
    public boolean shouldHandleMessage(JSONObject message) {
        return message.getString("text").startsWith(HANDLED_MESSAGE_PREFIX);
    }

    @Override
    public void handleMessage(JSONObject message, BiConsumer<String, String> outgoingMessageHandler) {
        String calculation = message.getString("text").substring(HANDLED_MESSAGE_PREFIX.length());

        try {
            Expression expression = Parser.parse(calculation);

            outgoingMessageHandler.accept(Strings.apply("Das Ergebnis ist: %.2f", expression.evaluate()),
                                          getClass().getSimpleName());
        } catch (ParseException e) {
            outgoingMessageHandler.accept("Die übergebene Rechnung konnte nicht verarbeitet werden.",
                                          getClass().getSimpleName());
        }
    }
}
