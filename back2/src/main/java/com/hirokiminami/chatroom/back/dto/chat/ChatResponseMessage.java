package com.hirokiminami.chatroom.back.dto.chat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hirokiminami.chatroom.back.constant.chat.command.ChatCommand;
import com.hirokiminami.chatroom.back.constant.chat.command.ChatCommandDeserializer;
import com.hirokiminami.chatroom.back.constant.chat.command.ChatCommandSerializer;
import com.hirokiminami.chatroom.back.constant.chat.type.ChatResponseType;
import com.hirokiminami.chatroom.back.constant.chat.type.ChatResponseTypeDeserializer;
import com.hirokiminami.chatroom.back.constant.chat.type.ChatResponseTypeSerializer;
import com.hirokiminami.chatroom.back.model.supportive.Poster;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatResponseMessage {
    @JsonSerialize(using = ChatResponseTypeSerializer.class)
    @JsonDeserialize(using = ChatResponseTypeDeserializer.class)
    private ChatResponseType type;
    @JsonSerialize(using = ChatCommandSerializer.class)
    @JsonDeserialize(using = ChatCommandDeserializer.class)
    private ChatCommand command;
    private Poster poster;
    private String messageId;
    private String message;
    private List<Poster> participants;
}
