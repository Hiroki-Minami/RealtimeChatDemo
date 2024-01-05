package com.hirokiminami.chatroom.back.dto.chat;

import com.hirokiminami.chatroom.back.constant.chat.command.ChatCommand;
import com.hirokiminami.chatroom.back.constant.chat.type.ChatResponseType;
import com.hirokiminami.chatroom.back.model.supportive.Poster;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaRequestMessage {
    private String roomId;
    private String type;
    private String command;
    private Poster poster;
    private String messageId;
    private String message;
    public KafkaRequestMessage(String roomId, ChatResponseType type, ChatCommand command, Poster poster, String messageId, String message) {
        this.roomId = roomId;
        this.type = type.getStringValue();
        this.command = command.getStringValue();
        this.poster = poster;
        this.messageId = messageId;
        this.message = message;
    }
}
