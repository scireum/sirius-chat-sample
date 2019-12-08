/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */

package bots;

import server.ChatMessage;
import sirius.kernel.commons.Strings;
import sirius.kernel.di.std.Register;

import java.util.function.Consumer;

/**
 * Handles :help messages and responds with a short message.
 * <p>
 * This acts as a sample for a very basic chat bot.
 */
@Register
public class HelpBot implements ChatBot {

    private static final String HANDLED_MESSAGE = ":help ";

    @Override
    public boolean shouldHandleMessage(ChatMessage message) {
        return HANDLED_MESSAGE.equals(message.getText());
    }

    @Override
    public void handleMessage(ChatMessage message,
                              Consumer<ChatMessage> responsesToUser,
                              Consumer<ChatMessage> responsesToEveryBody) {
        responsesToUser.accept(new ChatMessage(Strings.apply("Hello %s, I'm pleased to help you!", message.getSender()),
                                               getClass().getSimpleName()));

        // Have fun here - tell everyone that someone needs help
    }
}
