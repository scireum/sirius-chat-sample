package search;

import sirius.biz.elastic.SearchContent;
import sirius.biz.elastic.SearchableEntity;
import sirius.db.mixing.Mapping;
import sirius.db.mixing.annotations.BeforeSave;

import java.time.LocalDateTime;


public class ChatMessage extends SearchableEntity {

    public static final Mapping SENDER = Mapping.named("sender");
    @SearchContent
    private String sender;

    public static final Mapping TEXT = Mapping.named("text");
    @SearchContent
    private String text;

    public static final Mapping SEND_AT = Mapping.named("sendAt");
    private LocalDateTime sendAt;

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
