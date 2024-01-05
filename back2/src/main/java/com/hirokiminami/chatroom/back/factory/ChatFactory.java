package com.hirokiminami.chatroom.back.factory;

import com.hirokiminami.chatroom.back.constant.chat.command.ChatCommand;
import com.hirokiminami.chatroom.back.constant.chat.type.ChatResponseType;
import com.hirokiminami.chatroom.back.dto.chat.ChatResponseMessage;
import com.hirokiminami.chatroom.back.dto.chat.KafkaResponseMessage;

public class ChatFactory {
    public static ChatResponseMessage convertFromKafkaChatMessage(KafkaResponseMessage kafkaChatMessage) {
        return new ChatResponseMessage(ChatResponseType.fromValue(kafkaChatMessage.getType()), ChatCommand.fromValue(kafkaChatMessage.getCommand()), null, null, kafkaChatMessage.getMessage());
    }
}
