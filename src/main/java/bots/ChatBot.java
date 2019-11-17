/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */

package bots;

import com.alibaba.fastjson.JSONObject;

import java.util.function.BiConsumer;

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
    boolean shouldHandleMessage(JSONObject message);

    /**
     * Actually processes and interacts with the received message.
     *
     * @param message                the received message that can be interacted on
     * @param outgoingMessageHandler a consumer for displaying messages to the user, receiving a message for the first
     *                               parameter and the sender name for the second parameter
     */
    void handleMessage(JSONObject message, BiConsumer<String, String> outgoingMessageHandler);
}
