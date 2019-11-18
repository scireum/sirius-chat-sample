/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */

package bots;

import server.ChatMessage;

import java.util.function.Consumer;

/**
 * Describes a chat bot that can intercept incoming messages and react to them when appropriate.
 * <p>
 * Only messages beginning with the bot call prefix (:) are handled.
 */
public interface ChatBot {

    /**
     * Returns <tt>true</tt> when a bot feels responsible for handling the incoming bot call message.
     *
     * @param message the received message that should be handled
     * @return <true> when the bot can handle the message, <tt>false</tt> otherwise
     */
    boolean shouldHandleMessage(ChatMessage message);

    /**
     * Actually processes and interacts with the received message.
     *
     * @param message              the received message that can be interacted on
     * @param responsesToUser      consumes all message which are only sent back to the calling user
     * @param responsesToEveryBody consumes all message which are sent to all users
     */
    void handleMessage(ChatMessage message,
                       Consumer<ChatMessage> responsesToUser,
                       Consumer<ChatMessage> responsesToEveryBody);
}
