package com.hirokiminami.chatroom.back.dto.chat;

import com.hirokiminami.chatroom.back.model.supportive.Poster;
import lombok.Getter;
import lombok.Setter;
//public class KafkaResponseMessage extends ChatResponseMessage {
//    @Getter
//    @Setter
//    private String roomId;
//    public KafkaResponseMessage(ChatResponseType type, ChatCommand command, Poster poster, String messageId, String message, String roomId) {
//        super(type, command, poster, messageId, message);
//        this.roomId = roomId;
//    }
//}
@Getter
@Setter
public class KafkaResponseMessage {
    private String roomId;
    private String type;
    private String command;
    private Poster poster;
    private String messageId;
    private String message;

    public KafkaResponseMessage(){}
    public KafkaResponseMessage(String roomId, String type, String command, Poster poster, String messageId, String message) {
        this.roomId = roomId;
        this.type = type;
        this.command = command;
        this.poster = poster;
        this.messageId = messageId;
        this.message = message;
    }
}
