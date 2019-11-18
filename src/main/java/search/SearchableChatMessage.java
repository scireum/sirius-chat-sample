package search;

import sirius.biz.elastic.SearchContent;
import sirius.biz.elastic.SearchableEntity;
import sirius.db.mixing.Mapping;
import sirius.db.mixing.annotations.BeforeSave;

import java.time.LocalDateTime;

/**
 * Entity that represents a text message sent in a chat.
 */
public class SearchableChatMessage extends SearchableEntity {

    /**
     * The sender of the message.
     */
    public static final Mapping SENDER = Mapping.named("sender");
    @SearchContent
    private String sender;

    /**
     * The text of the message.
     */
    public static final Mapping TEXT = Mapping.named("text");
    @SearchContent
    private String text;
    /**
     * The time the message was sent.
     */
    public static final Mapping SEND_AT = Mapping.named("sendAt");
    private LocalDateTime sendAt;

    /**
     * Invoked before the entity is saved to elastic.
     * As the entity is saved right when its send, fills {@link #SEND_AT} with the current time.
     */
    @BeforeSave
    protected void fillSendAt() {
        if (sendAt == null) {
            sendAt = LocalDateTime.now();
        }
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSendAt() {
        return sendAt;
    }

    public void setSendAt(LocalDateTime sendAt) {
        this.sendAt = sendAt;
    }
}
