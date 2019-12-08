/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */

package bots;

import parsii.eval.Expression;
import parsii.eval.Parser;
import parsii.tokenizer.ParseException;
import server.ChatMessage;
import sirius.kernel.commons.Strings;
import sirius.kernel.di.std.Register;

import java.util.function.Consumer;

/**
 * Handles :calc messages by trying to evaluate the following mathematical expression using {@link Parser} and responding with the result.
 * <p>
 * This acts as a sample for a more advanced chat bot that interacts with the whole message as a kind of parameter.
 */
@Register
public class CalcBot implements ChatBot {

    private static final String HANDLED_MESSAGE_PREFIX = ":calc ";

    @Override
    public boolean shouldHandleMessage(ChatMessage chatMessage) {
        return chatMessage.getText().startsWith(HANDLED_MESSAGE_PREFIX);
    }

    @Override
    public void handleMessage(ChatMessage message,
                              Consumer<ChatMessage> responsesToUser,
                              Consumer<ChatMessage> responsesToEveryBody) {
        String calculation = message.getText().substring(HANDLED_MESSAGE_PREFIX.length());

        try {
            Expression expression = Parser.parse(calculation);

            responsesToEveryBody.accept(message);
            responsesToEveryBody.accept(new ChatMessage(Strings.apply("The result is: %.2f", expression.evaluate()),
                                                        getClass().getSimpleName()));
        } catch (ParseException e) {
            responsesToEveryBody.accept(new ChatMessage("The given formula cannot be parsed.",
                                                        getClass().getSimpleName()));
        }
    }
}
